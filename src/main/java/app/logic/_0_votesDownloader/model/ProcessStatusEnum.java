
package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public enum ProcessStatusEnum implements Serializable{
	LIGHT_INFO_DOWNLOADED("1", "LIGHT_INFO_DOWNLOADED"), 
	DETAIL_INFO_DOWNLOADED("2", "DETAIL_INFO_DOWNLOADED"),
	AUCTION_PAGE_INFO_DOWNLOADED("3", "AUCTION_PAGE_INFO_DOWNLOADED");
	
	private final String code;
	private final String description;
	
	public String getDescription() {
		return description;
	}

	ProcessStatusEnum(String code, String description) {
	        this.code = code;
	        this.description= description;
	    }

	    public String getCode() {
	        return code;
	    }
	    
	    public static ProcessStatusEnum findByDescription(String description){
		    for(ProcessStatusEnum v : values()){
		        if( v.getDescription().equals(description)){
		            return v;
		        }
		    }
//		    System.out.println(description);
		    return null;
		}
	    
}
