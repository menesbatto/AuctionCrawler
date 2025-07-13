package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public enum SellStateEnum implements Serializable{
	IN_VENDITA("", "IN VENDITA"), 
	IN_AGGIORNAMENTO("", "IN AGGIORNAMENTO"),
	NON_AGGIUDICATO("", "NON AGGIUDICATO"),
	VENDUTO("", "VENDUTO"),
	SOSPESO("", "SOSPESO"),
	ESTINTO("", "ESTINTO"),
	DA_DEFINIRE("", "DA DEFINIRE"),
	CONCLUSO("", "CONCLUSO"),
	PARZIALMENTE_VENDUTO("", "PARZIALMENTE VENDUTO"),
	IN_ATTESA_DI_OFFERTE_MIGLIORATIVE("", "IN ATTESA DI OFFERTE MIGLIORATIVE"),
	
	APERTA("1", ""), CHIUSA_AGGIUDICATA("2", ""), CHIUSA_NON_AGGIUDICATA("3",""), CHIUSA("4",""), ;
	
	private final String code;
	private final String description;
	
	public String getDescription() {
		return description;
	}

	SellStateEnum(String code, String description) {
	        this.code = code;
	        this.description= description;
	    }

	    public String getCode() {
	        return code;
	    }
	    
	    public static SellStateEnum findByDescription(String description){
		    for(SellStateEnum v : values()){
		        if( v.getDescription().equals(description)){
		            return v;
		        }
		    }
//		    System.out.println(description);
		    return null;
		}
	    
}
