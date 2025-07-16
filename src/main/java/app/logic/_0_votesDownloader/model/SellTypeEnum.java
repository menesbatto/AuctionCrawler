package app.logic._0_votesDownloader.model;

public enum SellTypeEnum {
	ASINCRONA_TELEMATICA("", ""), 
	SINCRONA_TELEMATICA("", ""), 
	MANIFESTAZIONE_DI_INTERESSE("", ""), 
	RACCOLTA_OFFERTE("", ""), 
	COMPRA_SUBITO("",""), 
	PUBBLICITA("",""), 
	SINCRONA_MISTA("","");
	
	private final String code;
	private final String description;
	
	SellTypeEnum(String code, String description) {
	        this.code = code;
	        this.description = description;
	    }

	
	
	public String getDescription() {
		return description;
	}



	public String getCode() {
	     return code;
	}

	public static SellTypeEnum findByDescription(String description){
	    for(SellTypeEnum v : values()){
	        if( v.getDescription().equals(description)){
	            return v;
	        }
	    }
//	    System.out.println(description);
	    return null;
	}

}
