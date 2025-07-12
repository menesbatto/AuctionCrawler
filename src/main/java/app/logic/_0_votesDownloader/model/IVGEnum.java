package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public enum IVGEnum implements Serializable{
	ROMA(""), CIVITAVECCHIA("");
	
	private final String code;
	
	IVGEnum(String code) {
	        this.code = code;
	    }

	    public String getCode() {
	        return code;
	    }
}
