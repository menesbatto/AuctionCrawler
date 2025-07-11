package app.logic._0_votesDownloader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.SerieATeamDao;
import app.dao.VoteDao;
import app.logic._0_votesDownloader.model.PlayerVoteComplete;
import app.logic._0_votesDownloader.model.RoleEnum;
import app.logic._0_votesDownloader.model.VotesSourceEnum;
import app.utils.AppConstants;
import app.utils.HttpUtils;
import app.utils.IOUtils;
//import fantapianto._1_realChampionshipAnalyzerFINAL.MainSeasonAnalyzerFINAL;
import app.utils.UsefulMethods;

@Service
public class MainSeasonVotesDowloader {
	
	@Autowired
	private VoteDao voteDao;
	
	@Autowired
	private SerieATeamDao serieATeamDao;
	

	
	public Map<VotesSourceEnum, Map<String, Map<String, List<PlayerVoteComplete>>>> execute(){
		
		populateSerieATeam();
		 
		String finalSeasonDayVotesUrl;

		Integer lastSerieASeasonDay = retrieveLastSerieASeasonDay();
		
		int lastSeasonDayCalculated = voteDao.calculateLastSeasonDayCalculated();
		
		String tvStamp = getTVUrlParameter();
		
		for (int i = lastSeasonDayCalculated + 1; i <= lastSerieASeasonDay; i++) {
			
			
			System.out.print(i + "\t");
			finalSeasonDayVotesUrl = AppConstants.SEASON_DAY_VOTES_URL_TEMPLATE.replace("[SEASON_DAY]", i+"").replace("[DATE_TIME_MILLIS]", tvStamp+"");
			
			// Creo la mappa con tutti i voti di giornata
			Map<VotesSourceEnum, Map<String, List<PlayerVoteComplete>>> trisVote = calculateSingleSeasonDay(finalSeasonDayVotesUrl);
			
			
			Map<String, List<PlayerVoteComplete>> napoliVotes = trisVote.get(VotesSourceEnum.NAPOLI);
			voteDao.saveVotesBySeasonDayAndVoteSource(napoliVotes, i, VotesSourceEnum.NAPOLI);
			
			Map<String, List<PlayerVoteComplete>> milanoVotes = trisVote.get(VotesSourceEnum.MILANO);
			voteDao.saveVotesBySeasonDayAndVoteSource(milanoVotes, i, VotesSourceEnum.MILANO);

			Map<String, List<PlayerVoteComplete>> italiaVotes = trisVote.get(VotesSourceEnum.ITALIA);
			voteDao.saveVotesBySeasonDayAndVoteSource(italiaVotes, i, VotesSourceEnum.ITALIA);
			
			
		}
		
	
		return null;
	}
	
	
	private void populateSerieATeam() {
		Map<String, String> existing = getTeamsIds();
		if (!existing.isEmpty())
			return;
		
		
		Map<String, String> map = serieATeamDao.findAllTeamIds();
		
		map = new HashMap<String, String>();
		Document doc = HttpUtils.getHtmlPageLight(AppConstants.TEAMS_IDS_URL);
		Elements teamsIds = doc.select("div.row.no-gutter.tbvoti");
		for(Element team : teamsIds){
			String gazzettaTeamId = team.attr("data-team");
			String name = team.attr("id").toUpperCase();
			map.put(name, gazzettaTeamId);
		}
	
		serieATeamDao.saveTeamIds(map);
		
		System.out.println(map);

	}
	
	private Integer retrieveLastSerieASeasonDay() {
		
		Document doc = HttpUtils.getHtmlPageLight(AppConstants.LAST_SEASON_DAY_URL);
		String lastSerieASeasonDayString = doc.getElementsByClass("xlsgior").get(1).text();
		Integer lastSerieASeasonDay = Integer.valueOf(lastSerieASeasonDayString);
		
		return lastSerieASeasonDay;
		
	}




	private String getTVUrlParameter() {
		Document doc = HttpUtils.getHtmlPageLight(AppConstants.SEASON_DAY_ALL_VOTES_URL + "/1");
		String tvStamp = doc.select("#tvstamp").val();
		return tvStamp;
	}


	public Map<VotesSourceEnum, Map<String, List<PlayerVoteComplete>>> calculateSingleSeasonDay(String seasonDayVotesUrl) {

		Map<String, String> teamIds = getTeamsIds();
		
		Map<VotesSourceEnum, Map<String, List<PlayerVoteComplete>>> trisVotes = new HashMap<VotesSourceEnum, Map<String, List<PlayerVoteComplete>>>();
		Map<String, List<PlayerVoteComplete>> votes1 = new HashMap<String, List<PlayerVoteComplete>>();
		Map<String, List<PlayerVoteComplete>> votes2 = new HashMap<String, List<PlayerVoteComplete>>();
		Map<String, List<PlayerVoteComplete>> votes3 = new HashMap<String, List<PlayerVoteComplete>>();

		// Recupero i voti di giornata
		String seasonDayVotesUrlFinal;
		Document doc;
		List<PlayerVoteComplete> teamPlayersVotes = null;
		String gazzettaTeamId;
		
		for (String teamShortName : teamIds.keySet()) {
			//seasonDayVotesUrlFinalhttps =//www.fantagazzetta.com/Servizi/Voti.ashx?s=2016-17&g=1&tv=225959671391&t=8
			//https://www.fantagazzetta.com/Servizi/Voti.ashx?s=2016-17&g=1&tv=225959671391&t=22
			gazzettaTeamId = teamIds.get(teamShortName);
			seasonDayVotesUrlFinal = seasonDayVotesUrl.replace("[GAZZETTA_TEAM_ID]", gazzettaTeamId);
			
			doc = HttpUtils.getHtmlPageLight(seasonDayVotesUrlFinal);
			
			teamPlayersVotes = getTeamPlayersVotes(doc, teamShortName, VotesSourceEnum.NAPOLI);
			votes1.put(teamShortName, teamPlayersVotes);
			trisVotes.put(VotesSourceEnum.NAPOLI, votes1);
			
			teamPlayersVotes = getTeamPlayersVotes(doc, teamShortName, VotesSourceEnum.MILANO);
			votes2.put(teamShortName, teamPlayersVotes);
			trisVotes.put(VotesSourceEnum.MILANO, votes2);
			
			teamPlayersVotes = getTeamPlayersVotes(doc, teamShortName, VotesSourceEnum.ITALIA);
			votes3.put(teamShortName, teamPlayersVotes);
			trisVotes.put(VotesSourceEnum.ITALIA, votes3);
			
			
			System.out.print(".");
		
		}

		System.out.println(); 
	

		return trisVotes;

	}

	

	private Map<String, String> getTeamsIds() {
		Map<String, String> map = serieATeamDao.findAllTeamIds();
		
		return map;
	}



	private static List<PlayerVoteComplete> getTeamPlayersVotes(Document doc, String teamShortName, VotesSourceEnum source) {
		Elements elements = doc.select("td.pname");
		Node cardNode;
		String playerName, roleString, scoredGoalString, scoredPenaltiesString, takenGoalsString, savedPenaltiesString, missedPenaltiesString, autoGoalsString, assistTotalString, assistStationaryString;
		Double scoredGoal, scoredPenalties, takenGoals, savedPenalties, missedPenalties, autoGoals, assistTotal, assistStationary, assistMovement;
		Boolean winGoal = false, evenGoal = false, subIn = false, subOut = false;
		List<Node> siblingNodes;
		PlayerVoteComplete pv;
		List<PlayerVoteComplete> playerVoteCompleteList = new ArrayList<PlayerVoteComplete>();
		for (Element e : elements) {
			winGoal = false;
			evenGoal = false;
			subIn = false;
			subOut = false;
			Elements nameElement = e.getElementsByTag("a");
			playerName = nameElement.text();
			roleString = nameElement.parents().get(1).getElementsByClass("role").text();
			RoleEnum role = null;
			try {
				role = RoleEnum.valueOf(roleString);
			} catch (Exception ex) {
				continue;
			}

			Elements generalInfoElems = e.getElementsByClass("aleft");
			if (generalInfoElems.size() > 0) {
				Elements generalEvents = generalInfoElems.get(0).getElementsByTag("em");
				//e.getElementsByClass("pull-right").get(0);
				for (Element ne : generalEvents) {
					String text = ne.text();
					if (text.equals("V"))
						winGoal = true;
					else if (text.equals("P")) {
						evenGoal = true;
					}
					String className = ne.className();
					if (className.contains("fa-arrow-right"))
						subOut = true;
					if (className.contains("fa-arrow-left"))
						subIn = true;
				}
			}
			siblingNodes = e.siblingNodes();
			Integer sc = 0;
			
			switch (source) {
				case NAPOLI:	sc = 0*2;	break;
				case MILANO:	sc = 1*2;	break;
				case ITALIA:	sc = 2*2;	break;
				default:		sc = 0*2; 			}

			Double vote;
			
			Boolean isVoteSV = siblingNodes.get(1 + sc).childNode(0).attr("class").indexOf("label-lgrey") > 0;
			if (isVoteSV){
				vote = null;
			}
			else {
				String voteString = siblingNodes.get(1 + sc).childNode(0).childNode(0).toString();
				vote = UsefulMethods.getNumber(voteString);
			}
			Boolean yellow = false;
			Boolean red = false;

			if (siblingNodes.get(1 + sc).childNodes().size() > 1) {
				cardNode = siblingNodes.get(1 + sc).childNode(1);
				yellow = cardNode.attr("class").contains("trn-ry");
				red = cardNode.attr("class").contains("trn-rr");
			}

			Node scoredGoalsNode = siblingNodes.get(7).childNode(0);
			scoredGoalString = scoredGoalsNode.childNodeSize() > 0 ? scoredGoalsNode.childNode(0).toString() : scoredGoalsNode.toString();
			scoredGoal = UsefulMethods.getNumber(scoredGoalString);

			Node scoredPenaltiesNode = siblingNodes.get(8).childNode(0);
			scoredPenaltiesString = scoredPenaltiesNode.childNodeSize() > 0 ? scoredPenaltiesNode.childNode(0).toString() : scoredPenaltiesNode.toString();
			scoredPenalties = UsefulMethods.getNumber(scoredPenaltiesString);

			
			Node takenGoalsNode = siblingNodes.get(9).childNode(0);
			takenGoalsString = takenGoalsNode.childNodeSize() > 0 ? takenGoalsNode.childNode(0).toString() : takenGoalsNode.toString();
			takenGoals = UsefulMethods.getNumber(takenGoalsString);

			Node savedPenaltieslsNode = siblingNodes.get(10).childNode(0);
			savedPenaltiesString = savedPenaltieslsNode.childNodeSize() > 0 ? savedPenaltieslsNode.childNode(0).toString() : savedPenaltieslsNode.toString();
			savedPenalties = UsefulMethods.getNumber(savedPenaltiesString);

			Node missedPenaltiesNode = siblingNodes.get(11).childNode(0);
			missedPenaltiesString = missedPenaltiesNode.childNodeSize() > 0 ? missedPenaltiesNode.childNode(0).toString() : missedPenaltiesNode.toString();
			missedPenalties = UsefulMethods.getNumber(missedPenaltiesString);

			Node autoGoalsNode = siblingNodes.get(12).childNode(0);
			autoGoalsString = autoGoalsNode.childNodeSize() > 0 ? autoGoalsNode.childNode(0).toString() : autoGoalsNode.toString();
			autoGoals = UsefulMethods.getNumber(autoGoalsString);

			Node assistTotalNode = siblingNodes.get(13).childNode(0);
			assistTotalString = assistTotalNode.childNodeSize() > 0 ? assistTotalNode.childNode(0).toString() : assistTotalNode.toString();
			assistTotal = UsefulMethods.getNumber(assistTotalString);

			assistStationaryString = "0";
			assistStationary = 0.0;

			if (siblingNodes.get(13).childNode(0).childNodes().size() > 1) {
				assistStationaryString = siblingNodes.get(13).childNode(0).childNode(1).childNode(0).toString();
				assistStationary = UsefulMethods.getNumber(assistStationaryString);
			}

			assistMovement = assistTotal - assistStationary;

			pv = new PlayerVoteComplete(playerName, teamShortName, role, vote, yellow, red, scoredGoal,
					scoredPenalties, assistMovement, assistStationary, autoGoals, missedPenalties, savedPenalties,
					takenGoals, winGoal, evenGoal, subIn, subOut);
			playerVoteCompleteList.add(pv);

		}
		return playerVoteCompleteList;
	}
	
	
	
	public static Map<VotesSourceEnum,Map<String, Map<String, List<PlayerVoteComplete>>>> getAllVotes() {
		return retrieveAllVotes();
	}	
	
	private static Map<VotesSourceEnum,Map<String, Map<String, List<PlayerVoteComplete>>>> retrieveAllVotes() {
		
		
		Map<VotesSourceEnum,Map<String, Map<String, List<PlayerVoteComplete>>>> map = IOUtils.read(AppConstants.REAL_CHAMPIONSHIP_VOTES_DIR	+ AppConstants.REAL_CHAMPIONSHIP_VOTES_FILE_NAME, HashMap.class);
		if (map== null){
			map = new HashMap<VotesSourceEnum, Map<String,Map<String,List<PlayerVoteComplete>>>>();
			map.put(VotesSourceEnum.NAPOLI, new HashMap<String, Map<String,List<PlayerVoteComplete>>>());
			map.put(VotesSourceEnum.MILANO, new HashMap<String, Map<String,List<PlayerVoteComplete>>>());
			map.put(VotesSourceEnum.ITALIA, new HashMap<String, Map<String,List<PlayerVoteComplete>>>());
		}
		return map;
	}

	
	
	
	
	
	
}