package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public enum IVGEnum implements Serializable{
	IVG_MONZA("1030", "IVG Monza"),
	IVG_UDINE_TRIESTE_GORIZIA("903", "IVG Udine - Trieste - Gorizia"),
	IVG_ROMA("620", "IVG Roma"),
	IVG_MANTOVA("466", "IVG Mantova"),
	IVG_MILANO_SIVAG("309", "IVG Milano - Sivag"),
	IVG_VARESE_E_BUSTO_ARSIZIO("305", "IVG Varese e Busto Arsizio"),
	IVG_BOLOGNA("292", "IVG Bologna"),
	IVG_REGGIO_EMILIA("280", "IVG Reggio Emilia"),
	IVG_BRESCIA("178", "IVG Brescia"),
	IVG_PARMA("155", "IVG Parma"),
	IVG_PIEMONTE("154", "IVG Piemonte"),
	IVG_ITALIANI_RIUNITI("110", "IVG Italiani Riuniti"),
	IVG_FORLÌ_SRL("104", "IVG Forlì s.r.l."),
	IVG_LUCCA_SOFIRSRL("104", "IVG Lucca - SO.FI.R.srl"),
	ISVEG_SRL("88", "ISVEG Srl"),
	IVG_GENOVA("85", "IVG Genova"),
	IVG_BERGAMO("74", "IVG Bergamo"),
	IVG_UMBRIA("70", "IVG Umbria"),
	IVG_VERONA("63", "IVG Verona"),
	IVG_NOVARA("60", "IVG Novara"),
	IVG_MODENA("60", "I.V.G. Modena"),
	IVG_VENEZIA("50", "IVG Venezia"),
	IVG_TREVISO_E_BELLUNO("48", "IVG Treviso e Belluno"),
	IVG_PAVIA_LODI("36", "IVG Pavia-Lodi"),
	IVG_DI_COMO_E_LECCO("34", "IVG di Como e Lecco"),
	IVG_ROVIGO("31", "IVG Rovigo"),
	IVG_LA_SPEZIA("26", "IVG La Spezia"),
	IVG_PISA("21", "IVG Pisa"),
	IVG_RIMINI("20", "IVG Rimini"),
	IVG_TEMPIO_PAUSANIA("19", "IVG Tempio Pausania"),
	IVG_DI_CREMA("14", "IVG di Crema"),
	IVG_SALERNO_E_NOCERA_INFERIORE("14", "IVG Salerno e Nocera Inferiore"),
	IVG_POTENZA_MATERA("12", "IVG Potenza - Matera"),
	IVG_SASSARI("11", "IVG Sassari"),
	IVG_MASSA_CARRARA_SOFIRSRL("7", "IVG Massa Carrara - SO.FI.R.srl"),
	IVG_FOGGIA("6", "IVG Foggia"),
	IVG_IMPERIA("5", "IVG Imperia"),
	IVG_TRENTO("2", "IVG Trento"),
	IVG_LAGONEGRO("2", "IVG Lagonegro"),
	IVG_LECCE_OXANET("1", "IVG Lecce - Oxanet"),
	IVG_CAGLIARI_E_ORISTANO("1", "IVG Cagliari e Oristano");
	
	private final String code;
	private final String description;
	
	IVGEnum(String code, String description) {
	        this.code = code;
	        this.description= description;
	    }

	    public String getCode() {
	        return code;
	    }
	    
		public static IVGEnum findByDescription(String description){
		    for(IVGEnum v : values()){
		        if( v.getDescription().equals(description)){
		            return v;
		        }
		    }
//		    System.out.println(description);
		    return null;
		}

		public String getDescription() {
			return description;
		}
		
		
}
