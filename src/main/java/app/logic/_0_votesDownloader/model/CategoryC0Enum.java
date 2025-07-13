package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public enum CategoryC0Enum implements Serializable{
	AUTOVEICOLI_E_CICLI(CategoryMacroEnum.BENI_MOBILI, "592", "AUTOVEICOLI E CICLI"), 
	NAUTICA(CategoryMacroEnum.BENI_MOBILI, "","NAUTICA"), 
	MACCHINARI_UTENSILI_MATERIE_PRIME(CategoryMacroEnum.BENI_MOBILI, "","MACCHINARI-UTENSILI-MATERIE PRIME"), 
	INFORMATICA_E_ELETTRONICA(CategoryMacroEnum.BENI_MOBILI, "","INFORMATICA E ELETTRONICA"),	
	ARREDAMENTO_ED_ELETTRODOMESTICI(CategoryMacroEnum.BENI_MOBILI, "","ARREDAMENTO - ELETTRODOMESTICI"), 
	ARTE_OREFICERIA_OROLOGERIA_ANTIQUARIATO(CategoryMacroEnum.BENI_MOBILI, "","ARTE -OREFICERIA - OROLOGERIA- ANTIQUARIATO"), 
	ABBIGLIAMENTO_E_CALZATURE(CategoryMacroEnum.BENI_MOBILI, "","ABBIGLIAMENTO E CALZATURE"), 	
	ALTRA_CATEGORIA(CategoryMacroEnum.BENI_MOBILI, "","ALTRA CATEGORIA");

	
	private final CategoryMacroEnum macro;
	private final String code;
	private final String description;

	CategoryC0Enum(CategoryMacroEnum macro, String code, String description) {
		this.macro = macro;
		this.code = code;
		this.description = description;

	}

	
	public static CategoryC0Enum findByDescription(String description){
	    for(CategoryC0Enum v : values()){
	        if( v.getDescription().equals(description)){
	            return v;
	        }
	    }
//	    System.out.println(description);
	    return null;
	}
	
	public String getDescription() {
		return description;
	}


	public String getCode() {
		return code;
	}

	public CategoryMacroEnum getMacro() {
		return macro;
	}
}
