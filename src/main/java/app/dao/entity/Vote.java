package app.dao.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vote {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private static final long serialVersionUID = -8182568610565154395L;

	private String source; // I (Italia), F (Fantagazzetta, ex Napoli), S (Statistico)
	
	private Integer serieASeasonDay;
	
	private String name;
	private String team;
	private String role;
	private Double vote;

	private Boolean yellowCard;
	private Boolean redCard;
	private Double scoredGoals;
	private Double scoredPenalties;
	private Double movementAssists;
	private Double stationaryAssists;

	private Double autogoals;
	private Double missedPenalties;
	
	private Double savedPenalties;
	private Double takenGoals;
	
	
	private Boolean winGoal;
	private Boolean evenGoal;
	private Boolean subIn;
	private Boolean subOut;
	
	public Vote() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Integer getSerieASeasonDay() {
		return serieASeasonDay;
	}

	public void setSerieASeasonDay(Integer serieASeasonDay) {
		this.serieASeasonDay = serieASeasonDay;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Double getVote() {
		return vote;
	}

	public void setVote(Double vote) {
		this.vote = vote;
	}

	public Boolean getYellowCard() {
		return yellowCard;
	}

	public void setYellowCard(Boolean yellowCard) {
		this.yellowCard = yellowCard;
	}

	public Boolean getRedCard() {
		return redCard;
	}

	public void setRedCard(Boolean redCard) {
		this.redCard = redCard;
	}

	public Double getScoredGoals() {
		return scoredGoals;
	}

	public void setScoredGoals(Double scoredGoals) {
		this.scoredGoals = scoredGoals;
	}

	public Double getScoredPenalties() {
		return scoredPenalties;
	}

	public void setScoredPenalties(Double scoredPenalties) {
		this.scoredPenalties = scoredPenalties;
	}

	public Double getMovementAssists() {
		return movementAssists;
	}

	public void setMovementAssists(Double movementAssists) {
		this.movementAssists = movementAssists;
	}

	public Double getStationaryAssists() {
		return stationaryAssists;
	}

	public void setStationaryAssists(Double stationaryAssists) {
		this.stationaryAssists = stationaryAssists;
	}

	public Double getAutogoals() {
		return autogoals;
	}

	public void setAutogoals(Double autogoals) {
		this.autogoals = autogoals;
	}

	public Double getMissedPenalties() {
		return missedPenalties;
	}

	public void setMissedPenalties(Double missedPenalties) {
		this.missedPenalties = missedPenalties;
	}

	public Double getSavedPenalties() {
		return savedPenalties;
	}

	public void setSavedPenalties(Double savedPenalties) {
		this.savedPenalties = savedPenalties;
	}

	public Double getTakenGoals() {
		return takenGoals;
	}

	public void setTakenGoals(Double takenGoals) {
		this.takenGoals = takenGoals;
	}

	public Boolean getWinGoal() {
		return winGoal;
	}

	public void setWinGoal(Boolean winGoal) {
		this.winGoal = winGoal;
	}

	public Boolean getEvenGoal() {
		return evenGoal;
	}

	public void setEvenGoal(Boolean evenGoal) {
		this.evenGoal = evenGoal;
	}

	public Boolean getSubIn() {
		return subIn;
	}

	public void setSubIn(Boolean subIn) {
		this.subIn = subIn;
	}

	public Boolean getSubOut() {
		return subOut;
	}

	public void setSubOut(Boolean subOut) {
		this.subOut = subOut;
	}

	@Override
	public String toString() {
		return "Vote [id=" + id + ", source=" + source + ", serieASeasonDay=" + serieASeasonDay + ", name=" + name
				+ ", team=" + team + ", role=" + role + ", vote=" + vote + ", yellowCard=" + yellowCard + ", redCard="
				+ redCard + ", scoredGoals=" + scoredGoals + ", scoredPenalties=" + scoredPenalties
				+ ", movementAssists=" + movementAssists + ", stationaryAssists=" + stationaryAssists + ", autogoals="
				+ autogoals + ", missedPenalties=" + missedPenalties + ", savedPenalties=" + savedPenalties
				+ ", takenGoals=" + takenGoals + ", winGoal=" + winGoal + ", evenGoal=" + evenGoal + ", subIn=" + subIn
				+ ", subOut=" + subOut + "]";
	}


	
	
	
	
}
