package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public enum CourtEnum implements Serializable {
	ROMA(""), CIVITAVECCHIA("");
	
	private final String code;
	
	CourtEnum(String code) {
	        this.code = code;
	    }

	    public String getCode() {
	        return code;
	    }
	
}
