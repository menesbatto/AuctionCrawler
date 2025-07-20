package app.logic._0_votesDownloader.model;                                                                                                                    
                                                                                                                                           
public class FilterDTO {                                                                                                                   
//	https://www.astagiudiziaria.com/ricerca/mobili?                                                                                        
                                                                                                                                           
	private String category;			//filter[genre][0]=					MOBILI&					CATMACRO                               
	private String categoryC0;			//filter[category][0]=				AUTOVEICOLI E CICLI&	CAT0                                   
	private String categoryC1;			//filter[subcategory][0]=			VEICOLI&				cat1                                   
	private String ivgId;				//filter[ivg_short_name][0]=		IVG Roma &                    
	private String description;			//query=							DESC&p					description                            
	
	private String page;				//page=								1&						page corrente                          
	private String rpp;					//rpp								=20						inserzioni per pagena                  

	private String sellStartDateFrom;	//filter[data_vendita_search][0]=	1752876000&				Scadenza 2 giorni
	private String sellStartDateTo;		//filter[data_vendita_search][1]=	1753135199&				Scadenza                               
	
	private String sellStartPriceFrom;	//filter[price][0]=					10&						prezz min                              
	private String sellStartPriceTo;	//filter[price][1]=					10000&					prezz max                              

	
	
	private String sellState;			//filter[status][0]=				In vendita&				stato                                  
	private String latestSearchFrom;	//filter[latest_search][0]=			1752530399&				Ultime pubblicazioni 3 giorni          
	private String latestSearchTo;		//filter[latest_search][1]=			1752876000&				Ultime pubblicazioni                   
	private String position;			//filter[position]=& 
	private String visible;				//filter[visibile_su][0]=1&                                                                        

	public FilterDTO() {                                                                                                                      
		super();                                                                                                                           
		// TODO Auto-generated constructor stub                                                                                            
	}                                                                                                                                      
	public String getCategory() {                                                                                                          
		return category;                                                                                                                   
	}                                                                                                                                      
	public void setCategory(String category) {                                                                                             
		this.category = category;                                                                                                          
	}                                                                                                                                      
	public String getCategoryC0() {                                                                                                        
		return categoryC0;                                                                                                                 
	}                                                                                                                                      
	public void setCategoryC0(String categoryC0) {                                                                                         
		this.categoryC0 = categoryC0;                                                                                                      
	}                                                                                                                                      
	public String getCategoryC1() {                                                                                                        
		return categoryC1;                                                                                                                 
	}                                                                                                                                      
	public void setCategoryC1(String categoryC1) {                                                                                         
		this.categoryC1 = categoryC1;                                                                                                      
	}                                                                                                                                      
	public String getSellState() {                                                                                                         
		return sellState;                                                                                                                  
	}                                                                                                                                      
	public void setSellState(String sellState) {                                                                                           
		this.sellState = sellState;                                                                                                        
	}                                                                                                                                      
	public String getIvgId() {                                                                                                             
		return ivgId;                                                                                                                      
	}                                                                                                                                      
	public void setIvgId(String ivgId) {                                                                                                   
		this.ivgId = ivgId;                                                                                                                
	}                                                                                                                                      
	public String getSellStartPriceFrom() {                                                                                                
		return sellStartPriceFrom;                                                                                                         
	}                                                                                                                                      
	public void setSellStartPriceFrom(String sellStartPriceFrom) {                                                                         
		this.sellStartPriceFrom = sellStartPriceFrom;                                                                                      
	}                                                                                                                                      
	public String getSellStartPriceTo() {                                                                                                  
		return sellStartPriceTo;                                                                                                           
	}                                                                                                                                      
	public void setSellStartPriceTo(String sellStartPriceTo) {                                                                             
		this.sellStartPriceTo = sellStartPriceTo;                                                                                          
	}                                                                                                                                      
	public String getSellStartDateFrom() {                                                                                                 
		return sellStartDateFrom;                                                                                                          
	}                                                                                                                                      
	public void setSellStartDateFrom(String sellStartDateFrom) {                                                                           
		this.sellStartDateFrom = sellStartDateFrom;                                                                                        
	}                                                                                                                                      
	public String getSellStartDateTo() {                                                                                                   
		return sellStartDateTo;                                                                                                            
	}                                                                                                                                      
	public void setSellStartDateTo(String sellStartDateTo) {                                                                               
		this.sellStartDateTo = sellStartDateTo;                                                                                            
	}                                                                                                                                      
	public String getLatestSearchFrom() {                                                                                                  
		return latestSearchFrom;                                                                                                           
	}                                                                                                                                      
	public void setLatestSearchFrom(String latestSearchFrom) {                                                                             
		this.latestSearchFrom = latestSearchFrom;                                                                                          
	}                                                                                                                                      
	public String getLatestSearchTo() {                                                                                                    
		return latestSearchTo;                                                                                                             
	}                                                                                                                                      
	public void setLatestSearchTo(String latestSearchTo) {                                                                                 
		this.latestSearchTo = latestSearchTo;                                                                                              
	}                                                                                                                                      
	public String getPosition() {                                                                                                          
		return position;                                                                                                                   
	}                                                                                                                                      
	public void setPosition(String position) {                                                                                             
		this.position = position;                                                                                                          
	}                                                                                                                                      
	public String getVisible() {                                                                                                           
		return visible;                                                                                                                    
	}                                                                                                                                      
	public void setVisible(String visible) {                                                                                               
		this.visible = visible;                                                                                                            
	}                                                                                                                                      
	public String getDescription() {                                                                                                       
		return description;                                                                                                                
	}                                                                                                                                      
	public void setDescription(String description) {                                                                                       
		this.description = description;                                                                                                    
	}                                                                                                                                      
	public String getPage() {                                                                                                              
		return page;                                                                                                                       
	}                                                                                                                                      
	public void setPage(String page) {                                                                                                     
		this.page = page;                                                                                                                  
	}                                                                                                                                      
	public String getRpp() {                                                                                                               
		return rpp;                                                                                                                        
	}                                                                                                                                      
	public void setRpp(String rpp) {                                                                                                       
		this.rpp = rpp;                                                                                                                    
	}                                                                                                                                      
	@Override                                                                                                                              
	public String toString() {                                                                                                             
		return "Research [category=" + category + ", categoryC0=" + categoryC0 + ", categoryC1=" + categoryC1                              
				+ ", sellState=" + sellState + ", ivgId=" + ivgId + ", sellStartPriceFrom=" + sellStartPriceFrom                           
				+ ", sellStartPriceTo=" + sellStartPriceTo + ", sellStartDateFrom=" + sellStartDateFrom                                    
				+ ", sellStartDateTo=" + sellStartDateTo + ", latestSearchFrom=" + latestSearchFrom                                        
				+ ", latestSearchTo=" + latestSearchTo + ", position=" + position + ", visible=" + visible                                 
				+ ", description=" + description + ", page=" + page + ", rpp=" + rpp + "]";                                                
	}                                                                                                                                      
	                                                                                                                                       
	                                                                                                                                       
	                                                                                                                                       
			                                                                                                                               
			                                                                                                                               
}                                                                                                                                          
                                                                                                                                           