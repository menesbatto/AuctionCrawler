package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.logic._0_votesDownloader.AuctionEventDownloader;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /demo (after Application path)
public class FacadeController {


	@Autowired
	private AuctionEventDownloader auctionEventDownloader;

	
		
		// ###################################################
		// ##########            1                ############
		// ###################################################
		
		@RequestMapping(value = "/downloadAuctionEventFromSearchPage", method = RequestMethod.GET)
		public ResponseEntity<String> downloadAuctionEventFromSearchPage() {
			
			String d = auctionEventDownloader.executeDownloadAuctionEventFromSearchPage();
//			User p = personDao.findById(1L);
			String body = "Downloading Single Page COMPLETED";
			
			ResponseEntity<String> response = new ResponseEntity<String>(d, HttpStatus.OK);
			return response;
		}
		
		
		
				
		
		
		
		// ###################################################
		// ##########            2                ############
		// ###################################################
				
		@RequestMapping(value = "/downloadAuctionEventDetails", method = RequestMethod.GET)
		public ResponseEntity<String> downloadAuctionEventDetails() {
			
			String d = auctionEventDownloader.executeDownloadAuctionEventDetails();
//					User p = personDao.findById(1L);
			String body = "Downloading Single Page COMPLETED";
			
			ResponseEntity<String> response = new ResponseEntity<String>(d, HttpStatus.OK);
			return response;
		}
	

}