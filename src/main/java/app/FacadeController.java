package app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.logic._0_credentialsSaver.UserExpert;
import app.logic._0_credentialsSaver.model.ConfirmUser;
import app.logic._0_credentialsSaver.model.Credentials;
import app.logic._0_credentialsSaver.model.UserBean;
import app.logic._0_votesDownloader.AuctionEventDownloader;
import app.logic._0_votesDownloader_0_rulesDownloader.RulesExpertMain;

@Controller // This means that this class is a Controller
@RequestMapping(path = "/api") // This means URL's start with /demo (after Application path)
public class FacadeController {


	@Autowired
	private AuctionEventDownloader auctionEventDownloader;

	@Autowired
	private RulesExpertMain rulesExpertMain;
	
	@Autowired
	private UserExpert userExpert;
	
	
		
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
	
	// ###################################################
	// ##########            1                ############
	// ###################################################
	
	@RequestMapping(value = "/downloadVotes", method = RequestMethod.GET)
	public ResponseEntity<String> downloadVotes() {
		
		auctionEventDownloader.execute();
//		User p = personDao.findById(1L);
		String body = "Downloading Votes COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	//###################################################################
	
	@RequestMapping(value = "/confirmUser", params = { "username", "rnd" },  method = RequestMethod.GET)
	public ResponseEntity<String> confirmUser(@RequestParam String username, @RequestParam String rnd) {
		
		ConfirmUser confirm = new ConfirmUser();
		confirm.setUsername(username);
		confirm.setRnd(rnd);
		
		Boolean confirmed = userExpert.confirmUser(confirm);
		String body;
		if (confirmed)
			body = "Saving new User COMPLETED";
		else
			body = "Saving new User ERROR";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	//###################################################################
	
	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@RequestBody UserBean user) {
		
		userExpert.createUser(user);
		String body = "Saving new User COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	//###################################################################
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<String> login(@RequestBody Credentials credentials) {
		
		Boolean confirmed = userExpert.login(credentials);
		String body;
		if (confirmed)
			body = "Login COMPLETED";
		else
			body = "Login ERROR";
		
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	
	//###################################################################
	
	@RequestMapping(value = "/saveFantaGazzettaCredentials", method = RequestMethod.POST)
	public ResponseEntity<String> saveFantaGazzettaCredentials(@RequestBody Credentials credentials) {
		
		userExpert.saveGazzettaCredentials(credentials);
		String body = "Saving Gazzeta Credentials COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	
	//###################################################################
	
	@RequestMapping(value = "/downloadLeagues", method = RequestMethod.GET)
	public ResponseEntity<String> downloadLeagues() {
		
		rulesExpertMain.execute();
//		User p = personDao.findById(1L);
		String body = "Downloading Rules COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
	
	//###################################################################
	
	@RequestMapping(value = "/downloadRules", method = RequestMethod.GET)
	public ResponseEntity<String> downloadRules() {
		
		rulesExpertMain.execute();
//		User p = personDao.findById(1L);
		String body = "Downloading Rules COMPLETED";
		
		ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);
		return response;
	}
}