package app.logic._0_votesDownloader.model;

import java.io.Serializable;

public enum CourtEnum implements Serializable {
	TRIBUNALE_DI_MONZA("883", "Tribunale di MONZA"),
	TRIBUNALE_DI_UDINE("807", "Tribunale di UDINE"),
	TRIBUNALE_DI_MANTOVA("465", "Tribunale di MANTOVA"),
	TRIBUNALE_DI_ROMA("457", "Tribunale di ROMA"),
	TRIBUNALE_DI_MILANO("335", "Tribunale di MILANO"),
	TRIBUNALE_DI_BOLOGNA("292", "Tribunale di BOLOGNA"),
	TRIBUNALE_DI_REGGIO_EMILIA("289", "Tribunale di REGGIO EMILIA"),
	TRIBUNALE_DI_BUSTO_ARSIZIO("210", "Tribunale di BUSTO ARSIZIO"),
	ALTRO("208", "Altro"),
	TRIBUNALE_DI_BRESCIA("151", "Tribunale di BRESCIA"),
	TRIBUNALE_DI_TORINO("109", "Tribunale di TORINO"),
	TRIBUNALE_DI_FORLI("104", "Tribunale di FORLI'"),
	TRIBUNALE_DI_LUCCA("92", "Tribunale di LUCCA"),
	TRIBUNALE_DI_GENOVA("85", "Tribunale di GENOVA"),
	TRIBUNALE_DI_PIACENZA("79", "Tribunale di PIACENZA"),
	TRIBUNALE_DI_BERGAMO("72", "Tribunale di BERGAMO"),
	TRIBUNALE_DI_MODENA("63", "Tribunale di MODENA"),
	TRIBUNALE_DI_VARESE("63", "Tribunale di VARESE"),
	TRIBUNALE_DI_VERONA("63", "Tribunale di VERONA"),
	TRIBUNALE_DI_PISA("59", "Tribunale di PISA"),
	TRIBUNALE_DI_PARMA("57", "Tribunale di PARMA"),
	TRIBUNALE_DI_PERUGIA("57", "Tribunale di PERUGIA"),
	TRIBUNALE_DI_FIRENZE("56", "Tribunale di FIRENZE"),
	TRIBUNALE_DI_PAVIA("45", "Tribunale di PAVIA"),
	TRIBUNALE_DI_TREVISO("44", "Tribunale di TREVISO"),
	TRIBUNALE_DI_VELLETRI("43", "Tribunale di VELLETRI"),
	TRIBUNALE_DI_PORDENONE("35", "Tribunale di PORDENONE"),
	TRIBUNALE_DI_LATINA("33", "Tribunale di LATINA"),
	TRIBUNALE_DI_ROVIGO("31", "Tribunale di ROVIGO"),
	TRIBUNALE_DI_TRIESTE("31", "Tribunale di TRIESTE"),
	TRIBUNALE_DI_LIVORNO("31", "Tribunale di LIVORNO"),
	TRIBUNALE_DI_GORIZIA("30", "Tribunale di GORIZIA"),
	TRIBUNALE_DI_COMO("29", "Tribunale di COMO"),
	AGENZIA_DELLE_ENTRATE("29", "Agenzia delle Entrate"),
	TRIBUNALE_DI_CUNEO("25", "Tribunale di CUNEO"),
	TRIBUNALE_DI_VITERBO("23", "Tribunale di VITERBO"),
	TRIBUNALE_DI_LA_SPEZIA("23", "Tribunale di LA SPEZIA"),
	TRIBUNALE_DI_VERBANIA("22", "Tribunale di VERBANIA"),
	TRIBUNALE_DI_TIVOLI("21", "Tribunale di TIVOLI"),
	TRIBUNALE_DI_SPOLETO("21", "Tribunale di SPOLETO"),
	TRIBUNALE_DI_RIMINI("20", "Tribunale di RIMINI"),
	TRIBUNALE_DI_GROSSETO("20", "Tribunale di GROSSETO"),
	TRIBUNALE_DI_NOVARA("19", "Tribunale di NOVARA"),
	TRIBUNALE_DI_TEMPIO_PAUSANIA("19", "Tribunale di TEMPIO PAUSANIA"),
	TRIBUNALE_DI_PISTOIA("18", "Tribunale di PISTOIA");

	
	private final String code;
	private final String description;
	
	CourtEnum(String code, String description) {
	        this.code = code;
	        this.description = description;
	    }

	    public String getCode() {
	        return code;
	    }

		public String getDescription() {
			return description;
		}
	    
		
		public static CourtEnum findByDescription(String description){
		    for(CourtEnum v : values()){
		        if( v.getDescription().equals(description)){
		            return v;
		        }
		    }
		    System.out.println(description);
		    return null;
		}
	
}
