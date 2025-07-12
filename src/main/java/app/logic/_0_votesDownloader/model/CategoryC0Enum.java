package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public enum CategoryC0Enum implements Serializable{
	AUTOVEICOLI_E_CICLI(CategoryMacroEnum.BENI_MOBILI, "592"), NAUTICA(CategoryMacroEnum.BENI_MOBILI, ""), MACCHINARI_UTENSILI_MATERIE_PRIME(CategoryMacroEnum.BENI_MOBILI, ""), 
	INFORMATICA_E_ELETTRONICA(CategoryMacroEnum.BENI_MOBILI, ""),	ARREDAMENTO_ED_ELETTRODOMESTICI(CategoryMacroEnum.BENI_MOBILI, ""), ARTE_OREFICERIA_OROLOGERIA_ANTIQUARIATO(CategoryMacroEnum.BENI_MOBILI, ""), 
	ABBIGLIAMENTO_E_CALZATURE(CategoryMacroEnum.BENI_MOBILI, ""), 	ALTRA_CATEGORIA(CategoryMacroEnum.BENI_MOBILI, "");

	
	private final CategoryMacroEnum macro;
	private final String code;

	CategoryC0Enum(CategoryMacroEnum macro, String code) {
		this.macro = macro;
		this.code = code;

	}

	public String getCode() {
		return code;
	}

	public CategoryMacroEnum getMacro() {
		return macro;
	}
}
