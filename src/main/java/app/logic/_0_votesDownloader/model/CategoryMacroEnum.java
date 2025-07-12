package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public enum CategoryMacroEnum implements Serializable{
	BENI_IMMOBILI("") , BENI_MOBILI("591"), CREDITI_VALORI(""), AZIENDE(""), ALTRO("");


	private final String code;
	
	CategoryMacroEnum(String code) {
	        this.code = code;
	    }

	    public String getCode() {
	        return code;
	    }
}
