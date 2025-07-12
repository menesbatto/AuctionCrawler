package app.logic._0_votesDownloader.model;

public enum SellTypeEnum {
	ASINCRONA_TELEMATICA(""), SINCRONA_TELEMATICA(""), MANIFESTAZIONE_DI_INTERESSE(""), RACCOLTA_OFFERTE(""), COMPRA_SUBITO(""), PUBBLICITA(""), SINCRONA_MISTA("");
	
	private final String code;
	
	SellTypeEnum(String code) {
	        this.code = code;
	    }

	
	public String getCode() {
	     return code;
	}


}
