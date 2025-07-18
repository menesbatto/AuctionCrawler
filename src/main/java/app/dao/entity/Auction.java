package app.dao.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
@Entity
public class Auction {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	private String category;	//Categoria				ENUM
	private String categoryC0;	//Categoria				ENUM
	private String categoryC1;	//Categoria				ENUM
	
	@ManyToOne(cascade = {CascadeType.ALL})
	private Proceeding proceeding;//numero e anno											evento giudiziario che ha portato alla vendita di vari oggetti
	private String lotCode;		//Codice Lotto											identificativo dell'auction 		- Lotto relativo ad una procedure su cui è stata creata una asta 
  	
	
	@OneToOne(cascade = {CascadeType.ALL})
	private WareHouseLocation warehouseLocation;	//Località  			
	
	@Column(length = 500, unique = true, nullable = false)
	@Lob 
	private String title;
	
	private String sellType;	//Tipo vendita			ENUM
	
	private String idIVG;		//Identificativo IVG	ENUM???		
	
	@Column(length = 1000)
	@Lob 
	private String description;	//Descrizione	
	private String court;		//Tribunale				ENUM
	
	
	
	
	
	
	
	
	//private String startedPrice;//Prezzo			Replicato dalla prima AuctionEvent
		

	



	public long getId() {
		return id;
	}



	public void setId(long id) {
		this.id = id;
	}




	public Proceeding getProceeding() {
		return proceeding;
	}



	public void setProceeding(Proceeding proceeding) {
		this.proceeding = proceeding;
	}



	public String getLotCode() {
		return lotCode;
	}



	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}






	public String getTitle() {
		return title;
	}



	public void setTitle(String title) {
		this.title = title;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getSellType() {
		return sellType;
	}



	public void setSellType(String sellType) {
		this.sellType = sellType;
	}



	public WareHouseLocation getWarehouseLocation() {
		return warehouseLocation;
	}



	public void setWarehouseLocation(WareHouseLocation warehouseLocation) {
		this.warehouseLocation = warehouseLocation;
	}



	public String getCourt() {
		return court;
	}



	public void setCourt(String court) {
		this.court = court;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public String getIdIVG() {
		return idIVG;
	}



	public void setIdIVG(String idIVG) {
		this.idIVG = idIVG;
	}





	@Override
	public String toString() {
		return "Auction [id=" + id + ", proceeding=" + proceeding + ", lotCode=" + lotCode + ", warehouseLocation="
				+ warehouseLocation + ", title=" + title + ", category=" + category + ", categoryC0=" + categoryC0
				+ ", categoryC1=" + categoryC1 + ", sellType=" + sellType + ", court=" + court + ", description="
				+ description + ", idIVG=" + idIVG + "]";
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
	
	
	
}
