package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public class AuctionDTO implements Serializable {

	private static final long serialVersionUID = -8182568610565154390L;

	private ProceedingDTO proceeding;// numero e anno evento giudiziario che ha portato alla vendita di vari oggetti
	
	private String lotCode; // Codice Lotto identificativo dell'auction - Lotto relativo ad una procedure su
							// cui è stata creata una asta

	private WareHouseLocationDTO warehouseLocation; // Località

	private String name;

	private CategoryMacroEnum categoryMacro; 		// Categoria ENUM
	private CategoryC0Enum categoryC0; 			// Categoria ENUM
	private CategoryC1Enum categoryC1; 			// Categoria ENUM
	
	private SellTypeEnum sellType; 	// Tipo vendita ENUM

	private CourtEnum court; 		// Tribunale
	private String description; 	// Descrizione
	private IVGEnum idIVG; 			// Identificativo IVG

	
	
	
	public CourtEnum getCourt() {
		return court;
	}

	public void setCourt(CourtEnum court) {
		this.court = court;
	}

	public ProceedingDTO getProceeding() {
		return proceeding;
	}

	public void setProceeding(ProceedingDTO proceeding) {
		this.proceeding = proceeding;
	}

	public String getLotCode() {
		return lotCode;
	}

	public void setLotCode(String lotCode) {
		this.lotCode = lotCode;
	}

	public WareHouseLocationDTO getWarehouseLocation() {
		return warehouseLocation;
	}

	public void setWarehouseLocation(WareHouseLocationDTO warehouseLocation) {
		this.warehouseLocation = warehouseLocation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public SellTypeEnum getSellType() {
		return sellType;
	}

	public void setSellType(SellTypeEnum sellType) {
		this.sellType = sellType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	

	public IVGEnum getIdIVG() {
		return idIVG;
	}

	public void setIdIVG(IVGEnum idIVG) {
		this.idIVG = idIVG;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CategoryMacroEnum getCategoryMacro() {
		return categoryMacro;
	}

	public void setCategoryMacro(CategoryMacroEnum categoryMacro) {
		this.categoryMacro = categoryMacro;
	}

	public CategoryC0Enum getCategoryC0() {
		return categoryC0;
	}

	public void setCategoryC0(CategoryC0Enum categoryC0) {
		this.categoryC0 = categoryC0;
	}

	public CategoryC1Enum getCategoryC1() {
		return categoryC1;
	}

	public void setCategoryC1(CategoryC1Enum categoryC1) {
		this.categoryC1 = categoryC1;
	}

	@Override
	public String toString() {
		return "AuctionDTO [proceeding=" + proceeding + ", lotCode=" + lotCode + ", warehouseLocation="
				+ warehouseLocation + ", name=" + name + ", categoryMacro=" + categoryMacro + ", categoryC0="
				+ categoryC0 + ", categoryC1=" + categoryC1 + ", sellType=" + sellType + ", court=" + court
				+ ", description=" + description + ", idIVG=" + idIVG + "]";
	}


}
