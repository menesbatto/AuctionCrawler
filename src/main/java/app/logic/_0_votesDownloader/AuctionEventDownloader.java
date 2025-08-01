package app.logic._0_votesDownloader;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.dao.AuctionEventDao;
import app.logic._0_votesDownloader.model.AuctionDTO;
import app.logic._0_votesDownloader.model.AuctionEventDTO;
import app.logic._0_votesDownloader.model.CategoryC0Enum;
import app.logic._0_votesDownloader.model.CategoryC1Enum;
import app.logic._0_votesDownloader.model.CategoryMacroEnum;
import app.logic._0_votesDownloader.model.CourtEnum;
import app.logic._0_votesDownloader.model.FilterDTO;
import app.logic._0_votesDownloader.model.IVGEnum;
import app.logic._0_votesDownloader.model.ProceedingDTO;
import app.logic._0_votesDownloader.model.ProcessStatusEnum;
import app.logic._0_votesDownloader.model.SellStateEnum;
import app.logic._0_votesDownloader.model.SellTypeEnum;
import app.logic._0_votesDownloader.model.WareHouseLocationDTO;
import app.utils.AppConstants;
import app.utils.HttpUtils2;
//import fantapianto._1_realChampionshipAnalyzerFINAL.MainSeasonAnalyzerFINAL;
import app.utils.UsefulMethods;

@Service
public class AuctionEventDownloader {
	
	
	@Autowired
	private AuctionEventDao auctionEventDao;
	
	private int z = 1;
	
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
	 
	public String openAllSeemedGoodDeals(){
//		List<AuctionEventDTO> auctionEvents = auctionEventDao.retrieveGoodAuctionEvents();
		List<AuctionEventDTO> auctionEvents = auctionEventDao.retrieveAuctionEvents(ProcessStatusEnum.DETAIL_INFO_DOWNLOADED);
		for (AuctionEventDTO dto: auctionEvents){
			String detailPageUrl = AppConstants.ASTE_GIUDIZIARIE_BASE_URL + dto.getDetailPageUrl();
			HttpUtils2.openNewTab(detailPageUrl);
			System.out.println("pagina aperta: " + detailPageUrl);
		}
		return "";
	}
	
	
	
	public String downloadAuctionEventFromSearchPage(){
		String s;
		List<AuctionEventDTO> auctionEventList = new ArrayList<>();
		Document doc= null;
		for (int i = 1; i<=20 ; i++) {
			s = AppConstants.ASTE_GIUDIZIARIE_HOME_PAGE_URL.replace("[PAGE_NUMBER]" , i+"");
			doc = HttpUtils2.getHtmlPageLogged(s,"","");
			
 			List<AuctionEventDTO> extractedAuctionEvents = extractAuctionEvents(doc);
			auctionEventList.addAll(extractedAuctionEvents);
			auctionEventDao.saveAuctionEvents(extractedAuctionEvents);
			
		}
//		System.out.println(doc.toString());
		return doc.toString();
	}
	
	
	public String downloadAuctionEventDetails(){
		
		List<AuctionEventDTO> auctionEventList = auctionEventDao.retrieveAuctionEvents(ProcessStatusEnum.LIGHT_INFO_DOWNLOADED);
		for (AuctionEventDTO dto : auctionEventList) {
			Document doc = HttpUtils2.getHtmlPageLogged(AppConstants.ASTE_GIUDIZIARIE_BASE_URL + dto.getDetailPageUrl(),"","");
			System.out.println();
			enrichAuctionEventWithDetailPage(dto, doc);
			String auctionPageUrl = HttpUtils2.getAuctionPagelFromDetailPage("");
			System.out.println("detail page downloaded");
			System.out.println("auctionPageURL: " + auctionPageUrl);
			dto.setAuctionPageUrl(auctionPageUrl);
			auctionEventDao.saveAuctionEvent(dto);
//			System.out.println();
			List<AuctionEventDTO> createAuctionEventCronology = createAuctionEventCronology(dto, doc);
			
			auctionEventDao.saveAuctionEvents(createAuctionEventCronology);
//			System.out.println(dto.getAuction().getDescription()); 
//			System.out.println(createAuctionEventCronology.size());
			LocalTime oraCorrente = LocalTime.now();
		     
			
			System.out.println("######### " + oraCorrente.format(formatter) + " ########");
			
			System.out.println();

		}
		return "";
	}
	
	
	private List<AuctionEventDTO> createAuctionEventCronology (AuctionEventDTO dto, Document doc) {
		Element storicoAste = doc.getElementById("storico-aste");
		List<AuctionEventDTO> list = new ArrayList<>();
		if (storicoAste!= null) {
//			System.out.println(doc);
					
			Elements tryList = storicoAste.select("tbody > tr");
			
			String dataString;
			String sellState;
			String startPrice;
			String detailPageUrl;
			
			for (Element auctionEventElem : tryList) {
				Elements auctionEventProperties = auctionEventElem.select("td");
				dataString = auctionEventProperties.get(0).text();
				sellState = auctionEventProperties.get(1).text();
				startPrice = auctionEventProperties.get(2).text();
//				startPrice = UsefulMethods.getCleanNumberString(startPrice);
				detailPageUrl = auctionEventProperties.get(3).select("a").attr("href");
				if (detailPageUrl.equals(""))
					if ("Corrente".equals(auctionEventProperties.get(3).text()))
						continue;
				
				
				AuctionEventDTO otherAuctionEvent = new AuctionEventDTO();
				otherAuctionEvent.setSellStartDate(UsefulMethods.getDate(dataString));
				otherAuctionEvent.setSellState(SellStateEnum.findByDescription(sellState.toUpperCase()));
				otherAuctionEvent.setStartPrice(Double.valueOf(startPrice));
				otherAuctionEvent.setDetailPageUrl(detailPageUrl!=""?detailPageUrl:null);
				otherAuctionEvent.setAuction(dto.getAuction());
				otherAuctionEvent.setProcessStatus(ProcessStatusEnum.BLOCKED_NO_NEED_DETAIL_PAGE);
				
				list.add(otherAuctionEvent);
			}
		}
		return list;
	}
	private void enrichAuctionEventWithDetailPage(AuctionEventDTO dto, Document doc) {
			
		// DA POPOLARE AUCTION EVENT
		Element infoVendita = doc.getElementById("informazioni-vendita");
		Element dettaglioLotto = doc.getElementById("dettaglio-lotto");
		Element tabDocumenti= doc.getElementById("tab-documenti");
		Element allDocuments= doc.getElementById("allDocuments");
		Element otherDocsBeni= doc.getElementById("other-docs-beni-0");
//			System.out.println(doc);
//			System.out.println();

		AuctionDTO auctionDTO = dto.getAuction();
		try {
			String description = doc.getElementById("description").text();
			auctionDTO.setDescription(description);
		}
		catch (Exception e) {
			dto.setProcessStatus(ProcessStatusEnum.BLOCKED_NO_DETAIL_PAGE);
			String retu = "Errore" ;
			System.out.println("Errora manca ID");
			return;
		}
		
		
		
		String sellEndDateString = null;
		String courtString = null;
		String idIVGString = null;
		String sellTypeString = null;
		String titleString = null;
		String categoryC0String = null;
		String categoryC1String = null;
		String categoryMacroString = null;
		String startPriceString = null;
		String addressString = null;
		String proceedingString = null;
		String lotCodeString = null;
		
		Elements containers = infoVendita.select("div.py-075.px-2.border-bottom.col-12");
        for (Element container : containers) {
            Elements divs = container.select("div.row > div");
            if (divs.size() >= 2) {
                String label = divs.get(0).text().trim();
                String value = divs.get(1).text().trim();
                //informazioni-vendita
                if ("Tribunale".equalsIgnoreCase(label))
                	courtString = value;
                if ("Fine gara".equalsIgnoreCase(label))
                	sellEndDateString = value;
                if ("Genere".equalsIgnoreCase(label))
                	categoryMacroString = value;
                if ("Prezzo base".equalsIgnoreCase(label))
                	startPriceString = value;
                if ("Nome".equalsIgnoreCase(label))
                	idIVGString = value;
                if ("Numero Procedura".equals(label))
                	proceedingString = value;
            }
        }
        
        String numberP= proceedingString.split("/")[0];
		String year= proceedingString.split("/")[1];;
		String description= null;
//		CERCALO PERCHE STA A DESTRA
		ProceedingDTO proceeding = new ProceedingDTO(numberP, year, description);
//		System.out.println(doc);
		auctionDTO.setProceeding(proceeding);
        
        
        containers = dettaglioLotto.select("div.py-075.px-2.border-bottom.col-12");
        for (Element container : containers) {
            Elements divs = container.select("div.row > div");
            if (divs.size() >= 2) {
                String label = divs.get(0).text().trim();
                String value = divs.get(1).text().trim();
        //tipologia altra categoria
                //2 categoria è quello specifico baso
		     // dettaglio-lotto
		        if ("Titolo".equalsIgnoreCase(label))
		        	titleString = value;
		        if ("Genere".equalsIgnoreCase(label))
		        	categoryMacroString = value;
		        if ("Tipologia".equalsIgnoreCase(label))
		        	categoryC0String = value;
		        if ("Categoria".equalsIgnoreCase(label))
		        	categoryC1String = value;
		        if ("Indirizzo".equalsIgnoreCase(label))
		        	addressString = value;
		        if ("Codice lotto".equalsIgnoreCase(label))
		        	lotCodeString = value;
            }
        }
        System.out.println();
        if (lotCodeString== null)
        	lotCodeString = "0";
//        	System.out.println("problema");
        if (	startPriceString!= null && !startPriceString.equals("Non presente"))  {
        	startPriceString = UsefulMethods.getCleanNumberString(startPriceString);
        	dto.setStartPrice(new Double(startPriceString));
        }
        
//        System.out.println(sellEndDateString);
        if (sellEndDateString != null) {
        	Date sellEndDate = UsefulMethods.getDate(sellEndDateString);
			dto.setSellEndDate(sellEndDate);
        }
        
        
        CategoryMacroEnum categoryMacro = CategoryMacroEnum.findByDescription(categoryMacroString);
        auctionDTO.setCategoryMacro(categoryMacro);
        
		CategoryC0Enum categoryC0 = CategoryC0Enum.findByDescription(categoryC0String);
		auctionDTO.setCategoryC0(categoryC0);

		CategoryC1Enum categoryC1= CategoryC1Enum.findByDescription(categoryC1String);
		auctionDTO.setCategoryC1(categoryC1);
		
		CourtEnum court = CourtEnum.findByDescription(courtString);
		auctionDTO.setCourt(court);
		
		auctionDTO.setLotCode(lotCodeString);
		
		 //allDocuments 
        idIVGString = doc.getElementsByClass("badge").get(0).text();
        IVGEnum idIVG = IVGEnum.findByDescription(idIVGString);
		auctionDTO.setIdIVG(idIVG);
		
		//MANCA
		SellTypeEnum sellType = SellTypeEnum.findByDescription(sellTypeString );
		auctionDTO.setSellType(sellType);

		auctionDTO.setTitle(titleString);
		
		
		WareHouseLocationDTO warehouseLocation = auctionDTO.getWarehouseLocation();
		if (warehouseLocation== null) {
			warehouseLocation = new WareHouseLocationDTO();
		}
		String cap = null;
		warehouseLocation.setCap(cap);
		String lat = null;
		warehouseLocation.setLat(lat);
		String lon = null;
		warehouseLocation.setLon(lon);
		String number = null;
		warehouseLocation.setNumber(number);
		String province = null;
		warehouseLocation.setProvince(province);
		String street = addressString;
		warehouseLocation.setStreet(street);
		
//		Element indirizzoLabel = dettaglioLotto.select("div:containsOwn(Indirizzo)").first();
		
		
		Elements select = doc.select("script");
		String scriptContent = "";
		for (Element elem : select) {
			 scriptContent = elem.html(); // contenuto tra <script>...</script>
            if (scriptContent.contains("function(a,b,c,d,e,f,g")) {
//	                System.out.println("SCRIPT TROVATO:");
//	                System.out.println(scriptContent);
                break;
            }
		}
		try {
			int	start = scriptContent.indexOf("\"Inizio gara\"");
			if (start < 0)
				start = scriptContent.indexOf("annunci\\");
			if (start<0)
				start = scriptContent.indexOf("venditetraprivati")-20;
			if (start<0)
				start = scriptContent.indexOf("vendita\\");
			if (start<0)
				start = scriptContent.indexOf("linkInserzionePvp");
			
//			System.out.println(scriptContent);
//			String url = scriptContent.substring(start); // ".html" = 5 caratteri
//			start = url.indexOf("http");
//			url = url.substring(start); // ".html" = 5 caratteri
//			int end = url.indexOf("\"", start);
//			url = url.substring(0, end ); // ".html" = 5 caratteri
//			url = url.replace("\\u002F", "/");
//			dto.setAuctionPageUrl(url);
//			if (url.length()>255) {
//				System.out.print ("Errore" + " - ");
//				System.out.println(url);
//				System.out.println(scriptContent);
//				System.out.println(doc);
//				doc.select("button[type=button]:containsOwn(Vai alla gara online)");
//				
//			}
				
		
//			Pattern pattern = Pattern.compile("\"https:(?:\\\\u002F|[^\"\\s])*?\\.html\"");
//	        Matcher matcher = pattern.matcher(scriptContent);
//
//	        String auctionPageUrl = "";
//	        if (matcher.find()) {
//	            String raw = matcher.group();
//	            // Rimuovi virgolette e decodifica \u002F → /
//	            auctionPageUrl = raw.substring(1, raw.length() - 1).replace("\\u002F", "/");
//	            System.out.println(auctionPageUrl);
//	        }
		}
		catch (Exception e) {
			System.out.println(scriptContent);
		}
		dto.setProcessStatus(ProcessStatusEnum.DETAIL_INFO_DOWNLOADED);
		
//			if (indirizzoLabel != null) {
//			    Element indirizzoValore = indirizzoLabel.parent().select("div").get(2);
//			    WareHouseLocationDTO warehouseLocation = dto.getAuction().getWarehouseLocation();
//			    warehouseLocation.setCity(indirizzoValore.text());
//			
//			
//		}
		
	}




	private List<AuctionEventDTO> extractAuctionEvents(Document doc) {
		
		List<AuctionEventDTO> auctionEventList = new ArrayList<>();
		AuctionEventDTO auctionEvent;
//		Elements teamsIds = doc.select("div.row.no-gutter.tbvoti");
		Elements elements = doc.getElementsByClass("asta-card");
		System.out.println(elements.size());
//		System.out.println(doc);
	 //<section id="searchbar" genretitle="MOBILI" data-v-48136df8="">
		
		String categoryMacroString = doc.getElementById("searchbar").attr("genretitle");
		CategoryMacroEnum categoryMacro = CategoryMacroEnum.findByDescription(categoryMacroString);
		
		for(Element current : elements){
//			System.out.println(current);
//			System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
			auctionEvent = new AuctionEventDTO();
			
			//XXXauctionEvent.getAuction().setCategoryMacro(CategoryMacroEnum.BENI_MOBILI);
			
			String detailPageUrl = current.getElementsByAttribute("href").get(0).attr("href");
			auctionEvent.setDetailPageUrl(detailPageUrl);
			
			auctionEvent.getAuction().setCategoryMacro(categoryMacro);
			
			String ivgString = current.getElementsByClass("badge").get(0).text();
			auctionEvent.getAuction().setIdIVG(IVGEnum.findByDescription(ivgString));
			
			String title = current.getElementsByClass("asta-card-title").get(0).text();
			auctionEvent.getAuction().setTitle(title);
			System.out.println("card search page download " + z++ + " - " + title);
			
			
			String categoryC0String = current.getElementsByClass("category-label").get(0).text();
			CategoryC0Enum categoryC0 = CategoryC0Enum.findByDescription(categoryC0String);
			auctionEvent.getAuction().setCategoryC0(categoryC0 );
			
			
			String categoryC1String = current.getElementsByClass("subcategory-label").get(0).text();
			CategoryC1Enum categoryC1 = CategoryC1Enum.findByDescription(categoryC1String);
			auctionEvent.getAuction().setCategoryC1(categoryC1 );
//			System.out.println(current);
int a = 1;			
			String priceString = current.getElementsByClass("price-tag").get(0).text();
			if (priceString.equals("Vendita a lotti singoli")) {
				auctionEvent.setStartPrice(new Double(0));
			}
			else if (priceString.equals("Non presente")) {
				auctionEvent.setStartPrice(new Double(0));
			} 
			else if (priceString.equals("Offerta libera")) {
				auctionEvent.setStartPrice(new Double(0));
			} 
//			else if (priceString.equals("OFFERTA LIBERA")) {
//				auctionEvent.setStartPrice(new Double(0));
//			} 
			else {
				priceString = UsefulMethods.getCleanNumberString(priceString);
				try {
					auctionEvent.setStartPrice(new Double(priceString));
				}
				catch (Exception e) {
					System.out.println(priceString);
				}
			}
				
			Element element = current.getElementsByClass("card-footer").get(0);
			Elements footerElements = element.getElementsByClass("text-truncate");
			
			String dateString = footerElements.get(0).text();
			String regex = "\\b(\\d{2}/\\d{2}/\\d{4})\\b";
		    Pattern pattern = Pattern.compile(regex);
		    Matcher matcher = pattern.matcher(dateString);
		    matcher.find();
		    String  firstDateString = matcher.group(0);
		    try {
			    Date firstDate = UsefulMethods.getDate(firstDateString);
			    if (matcher.find()) {
			    	auctionEvent.setSellStartDate(firstDate);
	
			    	String  secondDateString = matcher.group(0);
			    	Date secondDate = UsefulMethods.getDate(secondDateString);
			    	auctionEvent.setSellEndDate(secondDate);
			       }
			    else {
			    	auctionEvent.setSellEndDate(firstDate);
			    }
		    }
			catch (Exception e) {
				System.out.println(dateString);
				Date endDate = UsefulMethods.getDate(null);
				auctionEvent.setSellEndDate(endDate);
			}
			
			String proceedingString = footerElements.get(1).text();
			String proceedingTypeString = proceedingString.split(" Nr. ")[0];
			String proceedingNumberString = proceedingString.split(" Nr. ")[1].split("/")[0];
			String proceedingYearString = proceedingString.split(" Nr. ")[1].split("/")[1];
			ProceedingDTO proceeding = new ProceedingDTO(proceedingNumberString, proceedingYearString, proceedingTypeString);
			auctionEvent.getAuction().setProceeding(proceeding);
			
			String lotString = footerElements.get(2).text();
			auctionEvent.getAuction().setLotCode(lotString);
			System.out.println(lotString);
			
			String cityString = footerElements.get(3).text();
			WareHouseLocationDTO warehouseLocation = new WareHouseLocationDTO();
			warehouseLocation.setCity(cityString);
			auctionEvent.getAuction().setWarehouseLocation(warehouseLocation);
		
			
			String sellStateString = footerElements.get(4).text();
			auctionEvent.setSellState(SellStateEnum.findByDescription(sellStateString));
			
			auctionEvent.setProcessStatus(ProcessStatusEnum.LIGHT_INFO_DOWNLOADED);
			auctionEventList.add(auctionEvent);
			
//			String gazzettaTeamId = current.attr("data-team");
//			String name = current.attr("id").toUpperCase();
			
		}
		
//		Elements teamsIds = doc.select("div.row.no-gutter.tbvoti");
//		for(Element team : teamsIds){
//			String gazzettaTeamId = team.attr("data-team");
//			String name = team.attr("id").toUpperCase();
//			
//		}
//		
//		String lastSerieASeasonDayString = doc.getElementsByClass("card").get(1).text();
//		Integer lastSerieASeasonDay = Integer.valueOf(lastSerieASeasonDayString);
		
		
		return auctionEventList ;
	}


	
	
	
	
	
	
public String downloadAuctionEventFromSearchPageWithFilter(FilterDTO filterIn){
		
		String url = AppConstants.ASTE_GIUDIZIARIE_MOBILE_PAGE_WITH_FILTER_URL;
		
//		url = "https://www.astagiudiziaria.com/ricerca/mobili?filter[genre][0]=MOBILI&"
//				
//				+ "filter[genre][0]=MOBILI&"
//				+ "filter[ivg_short_name][0]=IVG Roma&"
//				+ "filter[category][0]=AUTOVEICOLI E CICLI&"
//				+ "filter[subcategory][0]=VEICOLI&"
//				+ "filter[visibile_su][0]=1&filter[position]=&"
//				+ "filter[data_vendita_search][0]=&"
//				+ "filter[price][0]=>&filter[price][1]=1000&"
//				+ "filter[data_vendita_search][1]=1763135199&"
//				
//				
//				+ "query=lotto&"
//				+ "page=1&rpp=20";
//		
		
		
		FilterDTO 
		filter = new FilterDTO();
//		filter.setCategory(CategoryMacroEnum.BENI_MOBILI.getDescription());
//		filter.setCategoryC0(CategoryC0Enum.ARTE_OREFICERIA_OROLOGERIA_ANTIQUARIATO.getDescription());
//		filter.setCategoryC1("VEICOLI");
		filter.setDescription(null);
		filter.setIvgId(IVGEnum.IVG_ROMA.getDescription());
		filter.setSellStartDateFrom("19/01/2025");
		filter.setSellStartDateTo("02/10/2028");
		filter.setSellStartPriceFrom("0");
		filter.setSellStartPriceTo("100000");
		filter.setRpp("100");
		
		System.out.println();
		
		url = AppConstants.ASTE_GIUDIZIARIE_MOBILE_PAGE_URL;
		
		
		if (filter.getCategory()!= null)
			url+="filter[genre][0]=" + filter.getCategory() + "&";
		if (filter.getCategoryC0()!= null)
			url+="filter[category][0]=" + filter.getCategoryC0() + "&";
		if (filter.getCategoryC1()!= null)
			url+="filter[subcategory][0]=" + filter.getCategoryC1() + "&";
		if (filter.getIvgId()!= null)
			url+="filter[ivg_short_name][0]=" + filter.getIvgId() + "&";
		if (filter.getDescription() != null)
			url += "query=" + filter.getDescription() + "&";
		
		// DATE
		if (filter.getSellStartDateFrom()!= null) {
			Date date = UsefulMethods.getDate(filter.getSellStartDateFrom());
			long time = date.getTime()/1000;
			url+="filter[data_vendita_search][0]=" + time + "&";
		}
		if (filter.getSellStartDateTo()!= null) {
			Date date = UsefulMethods.getDate(filter.getSellStartDateTo());
			long time = date.getTime()/1000;
			url+="filter[data_vendita_search][1]=" + time + "&";
		}
		
		// PRICE
		String from = filter.getSellStartPriceFrom();
		String to = filter.getSellStartPriceTo(); 
		if (from != null  && to != null)
			url+= "filter[price][0]=" + new Double (from)+ "&filter[price][1]=" + new Double (to) + "&";
		else if (from == null  && to != null)
			url+= "filter[price][0]=<&filter[price][1]=" + new Double (to) + "&";
		else if (from != null  && to == null)
			url+= "filter[price][0]=>&filter[price][1]=" + new Double (from) + "&";
		
		
		url += "filter[position]=&";
		url += "filter[visibile_su][0]=1&";
		url	+= "page=1&";
		url += "rpp=" + filter.getRpp();
		
		//OK + "filter[price][0]=100&filter[price][1]=500000&"
		//OK + "filter[price][0]=>&filter[price][1]=1000&"
		
		
//		filter[price][0]=>&filter[price][1]=100&		maggiore di minimino	solo minimo 100
//OK + "filter[price][0]=<&filter[price][1]=1000&"		//minore di massimo		solo massimo 50000
//		filter[price][0]=100&filter[price][1]=500000&	compreso tra i 2		minimo e massimo 100 e 50000
		
		
		System.out.println(url);
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}