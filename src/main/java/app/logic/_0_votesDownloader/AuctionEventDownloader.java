package app.logic._0_votesDownloader;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.AuctionEventDao;
import app.dao.SerieATeamDao;
import app.dao.VoteDao;
import app.dao.entity.Auction;
import app.logic._0_votesDownloader.model.AuctionDTO;
import app.logic._0_votesDownloader.model.AuctionEventDTO;
import app.logic._0_votesDownloader.model.CategoryC0Enum;
import app.logic._0_votesDownloader.model.CategoryC1Enum;
import app.logic._0_votesDownloader.model.CategoryMacroEnum;
import app.logic._0_votesDownloader.model.IVGEnum;
import app.logic._0_votesDownloader.model.PlayerVoteComplete;
import app.logic._0_votesDownloader.model.ProceedingDTO;
import app.logic._0_votesDownloader.model.ProcessStatusEnum;
import app.logic._0_votesDownloader.model.RoleEnum;
import app.logic._0_votesDownloader.model.SellStateEnum;
import app.logic._0_votesDownloader.model.VotesSourceEnum;
import app.logic._0_votesDownloader.model.WareHouseLocationDTO;
import app.utils.AppConstants;
import app.utils.HttpUtils;
import app.utils.HttpUtils2;
import app.utils.IOUtils;
//import fantapianto._1_realChampionshipAnalyzerFINAL.MainSeasonAnalyzerFINAL;
import app.utils.UsefulMethods;

@Service
public class AuctionEventDownloader {
	
	@Autowired
	private VoteDao voteDao;
	
	@Autowired
	private SerieATeamDao serieATeamDao;
	
	@Autowired
	private AuctionEventDao auctionEventDao;
	
	public String executeDownloadAuctionEventFromSearchPage(){
		String s;
		List<AuctionEventDTO> auctionEventList = new ArrayList<>();
		Document doc= null;
		for (int i = 13; i<=14; i++) {
			s = AppConstants.ASTE_GIUDIZIARIE_HOME_PAGE_URL.replace("[PAGE_NUMBER]" , i+"");
			doc = HttpUtils2.getHtmlPageLogged(s,"","");
			
			List<AuctionEventDTO> extractedAuctionEvents = extractAuctionEvents(doc);
//			auctionEventList.addAll(extractedAuctionEvents);
			System.out.println();
			auctionEventDao.saveAuctionEvents(extractedAuctionEvents);
		}
		
//		System.out.println(doc.toString());
		return doc.toString();
	}
	
	
	public String executeDownloadAuctionEventDetails(){
		List<AuctionEventDTO> auctionEventList = auctionEventDao.retrieveAuctionEvents(ProcessStatusEnum.LIGHT_INFO_DOWNLOADED);
		for (AuctionEventDTO dto : auctionEventList) {
			Document doc = HttpUtils2.getHtmlPageLogged(AppConstants.ASTE_GIUDIZIARIE_BASE_URL + dto.getDetailPageUrl(),"","");
//			Document doc = HttpUtils2.getHtmlPageLogged("https://www.astagiudiziaria.com/inserzioni/macchinari-accessori-auto-po-1260-1264255","","");
			
			enrichAuctionEventWithDetailPage(dto, doc);
			auctionEventDao.saveAuctionEvent(dto);
			
			List<AuctionEventDTO> createAuctionEventCronology = createAuctionEventCronology(dto, doc);
			auctionEventDao.saveAuctionEvents(createAuctionEventCronology);
			
			System.out.println(dto.getAuction().getDescription()); 
			System.out.println(createAuctionEventCronology.size());
		}
		return "";
	}
	
	
	private List<AuctionEventDTO> createAuctionEventCronology (AuctionEventDTO dto, Document doc) {
		Element storicoAste = doc.getElementById("storico-aste");
		List<AuctionEventDTO> list = new ArrayList<>();
		if (storicoAste!= null) {
//			System.out.println(doc);
					
			Elements tryList = storicoAste.select("tbody > tr");
			
			String dataString;
			String sellState;
			String startPrice;
			String detailPageUrl;
			
			for (Element auctionEventElem : tryList) {
				Elements auctionEventProperties = auctionEventElem.select("td");
				dataString = auctionEventProperties.get(0).text();
				sellState = auctionEventProperties.get(1).text();
				startPrice = auctionEventProperties.get(2).text();
//				startPrice = UsefulMethods.getCleanNumberString(startPrice);
				detailPageUrl = auctionEventProperties.get(3).select("a").attr("href");
				if (detailPageUrl.equals(""))
					if ("Corrente".equals(auctionEventProperties.get(3).text()))
						continue;
				
				
				AuctionEventDTO otherAuctionEvent = new AuctionEventDTO();
				otherAuctionEvent.setSellStartDate(UsefulMethods.getDate(dataString));
				otherAuctionEvent.setSellState(SellStateEnum.findByDescription(sellState.toUpperCase()));
				otherAuctionEvent.setStartPrice(Double.valueOf(startPrice));
				otherAuctionEvent.setDetailPageUrl(detailPageUrl!=""?detailPageUrl:null);
				
				otherAuctionEvent.setAuction(dto.getAuction());
				otherAuctionEvent.setProcessStatus(ProcessStatusEnum.BLOCKED_NO_NEED_DETAIL_PAGE);
				
				list.add(otherAuctionEvent);
			}
		}
		return list;
	}
	private void enrichAuctionEventWithDetailPage(AuctionEventDTO dto, Document doc) {
			
			
			
			// DA POPOLARE AUCTION EVENT
			Element infoVendita = doc.getElementById("informazioni-vendita");
//			System.out.println(doc);
			
			Element dettaglioLotto = doc.getElementById("dettaglio-lotto");
			Element tabDocumenti= doc.getElementById("tab-documenti");
	//		other-docs-beni-0
	//		allDocuments
			String description = doc.getElementById("description").text();
//			System.out.println();
			dto.getAuction().setDescription(description);
			
			
			Element indirizzoLabel = dettaglioLotto.select("div:containsOwn(Indirizzo)").first();
			
			
			Elements select = doc.select("script");
			String scriptContent = "";
			for (Element elem : select) {
				 scriptContent = elem.html(); // contenuto tra <script>...</script>
	            if (scriptContent.contains("function(a,b,c,d,e,f,g")) {
//	                System.out.println("SCRIPT TROVATO:");
//	                System.out.println(scriptContent);
	                break;
	            }
			}
			//https:\u002F\u002Fsivag.fallcoaste.it\u002Fvendita\u002Flotto-48-4-paia-di-orecchini-in-oro-pietre-coralli-gr-13-75-1260246.html"
//			System.out.println(scriptContent);
			try {
			int	start = scriptContent.indexOf("\"Inizio gara\"");
			if (start < 0)
				start = scriptContent.indexOf("annunci\\");
			else if (start<0) {
				start = scriptContent.indexOf("vendita\\");
			}
//			start = start -100;
//			if (start < 0)
//				start = scriptContent.indexOf("ivgreggioemilia")-60;
			
			String url = scriptContent.substring(start); // ".html" = 5 caratteri
			start = url.indexOf("http");
			url = url.substring(start); // ".html" = 5 caratteri
			int end = url.indexOf("\"", start);
			url = url.substring(0, end ); // ".html" = 5 caratteri
			url = url.replace("\\u002F", "/");
			
//			Pattern pattern = Pattern.compile("\"https:(?:\\\\u002F|[^\"\\s])*?\\.html\"");
//	        Matcher matcher = pattern.matcher(scriptContent);
//
//	        String auctionPageUrl = "";
//	        if (matcher.find()) {
//	            String raw = matcher.group();
//	            // Rimuovi virgolette e decodifica \u002F → /
//	            auctionPageUrl = raw.substring(1, raw.length() - 1).replace("\\u002F", "/");
//	            System.out.println(auctionPageUrl);
//	        }
	        dto.setAuctionPageUrl(url);
			}
			catch (Exception e) {
				System.out.println(scriptContent);
			}
			dto.setProcessStatus(ProcessStatusEnum.DETAIL_INFO_DOWNLOADED);
			
//			if (indirizzoLabel != null) {
//			    Element indirizzoValore = indirizzoLabel.parent().select("div").get(2);
//			    WareHouseLocationDTO warehouseLocation = dto.getAuction().getWarehouseLocation();
//			    warehouseLocation.setCity(indirizzoValore.text());
//			
//			
//		}
		
	}




	private List<AuctionEventDTO> extractAuctionEvents(Document doc) {
		
		List<AuctionEventDTO> auctionEventList = new ArrayList<>();
		AuctionEventDTO auctionEvent;
//		Elements teamsIds = doc.select("div.row.no-gutter.tbvoti");
		Elements elements = doc.getElementsByClass("asta-card");
		System.out.println(elements.size());
		int i = 1;
		for(Element current : elements){
//			System.out.println(current);
//			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			auctionEvent = new AuctionEventDTO();
			
							auctionEvent.getAuction().setCategoryMacro(CategoryMacroEnum.BENI_MOBILI);
			
			String detailPageUrl = current.getElementsByAttribute("href").get(0).attr("href");
			auctionEvent.setDetailPageUrl(detailPageUrl);
			
			String ivgString = current.getElementsByClass("badge").get(0).text();
			auctionEvent.getAuction().setIdIVG(IVGEnum.findByDescription(ivgString));
			
			String description = current.getElementsByClass("asta-card-title").get(0).text();
			auctionEvent.getAuction().setDescription(description);
							//auctionEvent.getAuction().setTitle();
			System.out.println(i++ + " - " + description);
			
			
			String categoryC0String = current.getElementsByClass("category-label").get(0).text();
			CategoryC0Enum categoryC0 = CategoryC0Enum.findByDescription(categoryC0String);
			auctionEvent.getAuction().setCategoryC0(categoryC0 );
			
			
			String categoryC1String = current.getElementsByClass("subcategory-label").get(0).text();
			CategoryC1Enum categoryC1 = CategoryC1Enum.findByDescription(categoryC1String);
			auctionEvent.getAuction().setCategoryC1(categoryC1 );
//			System.out.println(current);
int a = 1;			
			String priceString = current.getElementsByClass("price-tag").get(0).text();
			if (priceString.equals("Vendita a lotti singoli")) {
			
			}
			else if (priceString.equals("Non presente")) {
				auctionEvent.setStartPrice(new Double(0));
			} else {
				priceString = UsefulMethods.getCleanNumberString(priceString);
				try {
					auctionEvent.setStartPrice(new Double(priceString));
				}
				catch (Exception e) {
					System.out.println(priceString);
				}
			}
				
			Element element = current.getElementsByClass("card-footer").get(0);
			Elements footerElements = element.getElementsByClass("text-truncate");
			
			String dateString = footerElements.get(0).text();
			String regex = "\\b(\\d{2}/\\d{2}/\\d{4})\\b";
		    Pattern pattern = Pattern.compile(regex);
		    Matcher matcher = pattern.matcher(dateString);
		    matcher.find();
		    String  startDateString = matcher.group(1);
			
//			String startDateString = dateString.split(" ")[1];
			Date startDate = UsefulMethods.getDate(startDateString);
			auctionEvent.setSellStartDate(startDate);
			
			String proceedingString = footerElements.get(1).text();
			String proceedingTypeString = proceedingString.split(" Nr. ")[0];
			String proceedingNumberString = proceedingString.split(" Nr. ")[1].split("/")[0];
			String proceedingYearString = proceedingString.split(" Nr. ")[1].split("/")[1];
			ProceedingDTO proceeding = new ProceedingDTO(proceedingNumberString, proceedingYearString, proceedingTypeString);
			auctionEvent.getAuction().setProceeding(proceeding);
			

			String lotString = footerElements.get(2).text();
			auctionEvent.getAuction().setLotCode(lotString);
			
			String cityString = footerElements.get(3).text();
			WareHouseLocationDTO warehouseLocation = new WareHouseLocationDTO();
			warehouseLocation.setCity(cityString);
			auctionEvent.getAuction().setWarehouseLocation(warehouseLocation);
		
			
			String sellStateString = footerElements.get(4).text();
			auctionEvent.setSellState(SellStateEnum.findByDescription(sellStateString));
			
			auctionEvent.setProcessStatus(ProcessStatusEnum.LIGHT_INFO_DOWNLOADED);
			auctionEventList.add(auctionEvent);
			
//			String gazzettaTeamId = current.attr("data-team");
//			String name = current.attr("id").toUpperCase();
			
		}
		
//		Elements teamsIds = doc.select("div.row.no-gutter.tbvoti");
//		for(Element team : teamsIds){
//			String gazzettaTeamId = team.attr("data-team");
//			String name = team.attr("id").toUpperCase();
//			
//		}
//		
//		String lastSerieASeasonDayString = doc.getElementsByClass("card").get(1).text();
//		Integer lastSerieASeasonDay = Integer.valueOf(lastSerieASeasonDayString);
		
		
		return auctionEventList ;
	}


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

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