package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public enum CategoryMacroEnum implements Serializable{
	BENI_IMMOBILI("","BENI IMMOBILI") , BENI_MOBILI("591","BENI MOBILI"), CREDITI_VALORI("","CREDITI VALORI"), AZIENDE("","AZIENDE"), ALTRO("","ALTRO");


	private final String code;
	private final String description;
	
	CategoryMacroEnum(String code, String description) {
	        this.code = code;
	        this.description = description;
	    }

	    public String getCode() {
	        return code;
	    }

		public String getDescription() {
			return description;
		}
	    
}
