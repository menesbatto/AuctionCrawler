package app.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;

import app.dao.entity.User;
import app.dao.entity.Vote;
import app.logic._0_votesDownloader.model.PlayerVoteComplete;
import app.logic._0_votesDownloader.model.VotesSourceEnum;

@Service
@EnableCaching
public class VoteDao {

	@Autowired
	private VoteRepo voteRepo;


	


	public int calculateLastSeasonDayCalculated() {
		List<Integer> seasonDays = voteRepo.findAllDistinct();
		if (seasonDays.isEmpty())
			return 0;
		
		return seasonDays.get(seasonDays.size()-1);
	}




	public void saveVotesBySeasonDayAndVoteSource(Map<String, List<PlayerVoteComplete>> votesByTeam, Integer serieASeasonDay, VotesSourceEnum voteSource) {
		
		Vote v;
		List<Vote> voteEnts = new ArrayList<Vote>();
		for ( Entry<String, List<PlayerVoteComplete>> entry : votesByTeam.entrySet()) {
//			String team = entry.getKey();
			List<PlayerVoteComplete> votes = entry.getValue();
			for (PlayerVoteComplete voteBean : votes) {
				v = populateVote(voteBean, serieASeasonDay, voteSource);
				voteEnts.add(v);
			}
		}
		
		voteRepo.save(voteEnts);
	}



	private Vote populateVote(PlayerVoteComplete voteBean, Integer serieASeasonDay, VotesSourceEnum voteSource) {
		Vote ent = new Vote();
		ent.setSerieASeasonDay(serieASeasonDay);
		ent.setSource(voteSource.name());
		
		ent.setAutogoals(voteBean.getAutogoals());
		ent.setEvenGoal(voteBean.getEvenGoal());
		ent.setMissedPenalties(voteBean.getMissedPenalties());
		ent.setMovementAssists(voteBean.getMovementAssists());
		ent.setName(voteBean.getName());
		ent.setRedCard(voteBean.getRedCard());
		ent.setRole(voteBean.getRole().toString());
		ent.setSavedPenalties(voteBean.getSavedPenalties());
		ent.setScoredGoals(voteBean.getScoredGoals());
		ent.setScoredPenalties(voteBean.getScoredPenalties());
		ent.setStationaryAssists(voteBean.getStationaryAssists());
		ent.setSubIn(voteBean.getSubIn());
		ent.setSubOut(voteBean.getSubOut());
		ent.setTakenGoals(voteBean.getTakenGoals());
		ent.setTeam(voteBean.getTeam());
		ent.setVote(voteBean.getVote());
		ent.setWinGoal(voteBean.getWinGoal());
		ent.setYellowCard(voteBean.getYellowCard());
		
		return ent;
	}



	public List<String> findAllSerieATeam() {
		List<String> seasonDays = voteRepo.findAllSerieATeam();
		if (seasonDays.isEmpty())
			return null;
		return seasonDays;
	}

	
	
	
}
