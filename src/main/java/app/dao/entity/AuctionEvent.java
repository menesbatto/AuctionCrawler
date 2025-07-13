package app.dao.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class AuctionEvent {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	
	@ManyToOne
	private Auction auction;
	
	private Date sellStartDate;			//Data Vendita INIZIO
	private Date sellEndDate;			//Data Vendita FINE
	
	
	private String sellState;		//Stato Vendita 		ENUM
	private String sellCode;		//Codice Vendita				?????	identificativo dell'auctionEvent in fallcoaste sta 1 a 1 con il idPVP
	private String idPVP;			//Identificativo PVP (Portale delle Vendite Pubbliche)	identificativo dell'auctionEvent 	- Istanze, Evento, Esperimento dell'asta
	
	private Double startedPrice;			//Prezzo
	private Double startedPriceWithTaxes;	//Prezzo piu' tasse
	private Double currentPrice;			//Prezzo al momento ha senso solo se l'asta è in corso.
	private Double currentPriceWithTaxes;			//Prezzo al momento ha senso solo se l'asta è in corso.
	
	private Double endPrice;				//Prezzo finale 		
	private Double endPriceWithTaxes;			//Prezzo al momento ha senso solo se l'asta è in corso.
	
	private String urlDetailPage;
	

	public Double getStartedPriceWithTaxes() {
		return startedPriceWithTaxes;
	}
	public void setStartedPriceWithTaxes(Double startedPriceWithTaxes) {
		this.startedPriceWithTaxes = startedPriceWithTaxes;
	}
	public Double getCurrentPriceWithTaxes() {
		return currentPriceWithTaxes;
	}
	public void setCurrentPriceWithTaxes(Double currentPriceWithTaxes) {
		this.currentPriceWithTaxes = currentPriceWithTaxes;
	}
	public Double getEndPriceWithTaxes() {
		return endPriceWithTaxes;
	}
	public void setEndPriceWithTaxes(Double endPriceWithTaxes) {
		this.endPriceWithTaxes = endPriceWithTaxes;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	public Auction getAuction() {
		return auction;
	}
	public void setAuction(Auction auction) {
		this.auction = auction;
	}






	public String getSellState() {
		return sellState;
	}
	public void setSellState(String sellState) {
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
		return "AuctionEvent [id=" + id + ", auction=" + auction + ", sellStartDate=" + sellStartDate + ", sellEndDate="
				+ sellEndDate + ", sellState=" + sellState + ", sellCode=" + sellCode + ", idPVP=" + idPVP
				+ ", startedPrice=" + startedPrice + ", startedPriceWithTaxes=" + startedPriceWithTaxes
				+ ", currentPrice=" + currentPrice + ", currentPriceWithTaxes=" + currentPriceWithTaxes + ", endPrice="
				+ endPrice + ", endPriceWithTaxes=" + endPriceWithTaxes + "]";
	}
	public String getUrlDetailPage() {
		return urlDetailPage;
	}
	public void setUrlDetailPage(String urlDetailPage) {
		this.urlDetailPage = urlDetailPage;
	}
	







}
