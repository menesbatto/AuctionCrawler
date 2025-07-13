package app.logic._0_votesDownloader.model;

import java.io.Serializable;
import java.util.Date;

import app.dao.entity.Auction;

public class AuctionEventDTO implements Serializable{

	private static final long serialVersionUID = -584699076780093831L;

	private AuctionDTO auction;
	
	private Date sellStartDate;				//Data Vendita INIZIO
	private Date sellEndDate;				//Data Vendita INIZIO
	
	private SellStateEnum sellState;	//Stato Vendita 
	private String sellCode;			//Codice Vendita				?????	identificativo dell'auctionEvent in fallcoaste sta 1 a 1 con il idPVP
	private String idPVP;				//Identificativo PVP (Portale delle Vendite Pubbliche)	identificativo dell'auctionEvent 	- Istanze, Evento, Esperimento dell'asta
	
	private Double startedPrice;		//Prezzo			
	private Double currentPrice;		//Prezzo al momento ha senso solo se l'asta è in corso.
	private Double endPrice;			//Prezzo finale 		
	
	private String urlDetailPage;
	
	
	
	public AuctionEventDTO() {
		super();
		this.auction = new AuctionDTO();
	}
	
	
	public AuctionDTO getAuction() {
		return auction;
	}


	public void setAuction(AuctionDTO auction) {
		this.auction = auction;
	}


	public SellStateEnum getSellState() {
		return sellState;
	}
	public void setSellState(SellStateEnum sellState) {
		this.sellState = sellState;
	}
	public String getSellCode() {
		return sellCode;
	}
	public void setSellCode(String sellCode) {
		this.sellCode = sellCode;
	}
	public String getIdPVP() {
		return idPVP;
	}
	public void setIdPVP(String idPVP) {
		this.idPVP = idPVP;
	}
	public Double getStartedPrice() {
		return startedPrice;
	}
	public void setStartedPrice(Double startedPrice) {
		this.startedPrice = startedPrice;
	}
	public Double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(Double currentPrice) {
		this.currentPrice = currentPrice;
	}
	public Double getEndPrice() {
		return endPrice;
	}
	public void setEndPrice(Double endPrice) {
		this.endPrice = endPrice;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Date getSellStartDate() {
		return sellStartDate;
	}
	public void setSellStartDate(Date sellStartDate) {
		this.sellStartDate = sellStartDate;
	}
	public Date getSellEndDate() {
		return sellEndDate;
	}
	public void setSellEndDate(Date sellEndDate) {
		this.sellEndDate = sellEndDate;
	}
	@Override
	public String toString() {
		return "AuctionEventDTO [auction=" + auction + ", \nsellStartDate=" + sellStartDate + ", \\nsellEndDate="
				+ sellEndDate + ", \\nsellState=" + sellState + ", \\nsellCode=" + sellCode + ", \\nidPVP=" + idPVP
				+ ", \\nstartedPrice=" + startedPrice + ", \\ncurrentPrice=" + currentPrice + ", \\nendPrice=" + endPrice + "]";
	}


	public String getUrlDetailPage() {
		return urlDetailPage;
	}


	public void setUrlDetailPage(String urlDetailPage) {
		this.urlDetailPage = urlDetailPage;
	}

	
	
	
}
