package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public enum SellStateEnum implements Serializable{
	APERTA("1"), CHIUSA_AGGIUDICATA("2"), CHIUSA_NON_AGGIUDICATA("3"), CHIUSA("4"), ;
	
	private final String code;
	
	SellStateEnum(String code) {
	        this.code = code;
	    }

	    public String getCode() {
	        return code;
	    }
}
