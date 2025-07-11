package app.logic._0_votesDownloader_0_rulesDownloader.model;

import java.io.Serializable;

import app.logic._0_votesDownloader.model.VotesSourceEnum;

public class DataSources  implements Serializable{

	private static final long serialVersionUID = 2886189272962700751L;

	private VotesSourceEnum votesSource;
	private VotesSourceEnum bonusMalusSource;
	
	
	public VotesSourceEnum getVotesSource() {
		return votesSource;
	}
	public void setVotesSource(VotesSourceEnum votesSource) {
		this.votesSource = votesSource;
	}
	public VotesSourceEnum getBonusMalusSource() {
		return bonusMalusSource;
	}
	public void setBonusMalusSource(VotesSourceEnum bonusMalusSource) {
		this.bonusMalusSource = bonusMalusSource;
	}
	@Override
	public String toString() {
		return "DataSources [votesSource=" + votesSource + "\n bonusMalusSource=" + bonusMalusSource + "]";
	}

	
	
}
