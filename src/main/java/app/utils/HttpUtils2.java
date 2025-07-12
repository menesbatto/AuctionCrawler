package app.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
public class HttpUtils2 {

	private static WebDriver loggedWebDriver;
	

	public static Document getHtmlPageNoLogged(String url){
		Document doc = null;
		Connection connect = Jsoup.connect(url);
	
		try {
			doc = connect.ignoreContentType(true).get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return doc;
	}
	
	
	public static Document getHtmlPageLogged(String url, String username, String password){
		Document doc = null;
		System.out.print(".");
		
		try {

			if (getLoggedWebDriver() == null)
				initLoggedWebDriver(username, password);
			
			Thread.sleep(1000);
			
			getLoggedWebDriver().get(url);
			Thread.sleep(1000);
		    String pageSource = getLoggedWebDriver().getPageSource();
			doc = Jsoup.parse(pageSource);
			
		}
		catch (Exception e){
			setLoggedWebDriver(null);
			System.out.print("Errore durante il recupero della pagina");
		}
		
		return doc;
		
	}
//	
//	public static Document loginOnFantagazzetta(String username, String password) {
//		if (loggedWebDriver == null)
//			initLoggedWebDriver(username, password);
//		String pageSource = loggedWebDriver.getPageSource();
//		Document doc = Jsoup.parse(pageSource);
//		
//		return doc;
//	}
	
	

	private static WebDriver initLoggedWebDriver(String username, String password) {
		WebDriver driver = null;
		int i = 1;

		while (i <= 3){
			try {
				// 1 - Crea driver pronto per navigare
				driver = initDriver();
				
				// 2 - Esegui login su fantagazzetta
				String url = AppConstants.ASTE_GIUDIZIARIE_HOME_PAGE_URL;
				
				driver.get(url);
		//		champDriver.navigate().refresh();
		//		WebElement navBar   = driver.findElement(By.id("myNav"));
//				
//				WebElement loginBtnPage = driver.findElement(By.className("hidden-logged")); 
//				WebDriverWait wait = new WebDriverWait(driver,5);
//			    wait.until(ExpectedConditions.presenceOfElementLocated(By.className("qc-cmp-button")));
//
//				WebElement cookieInfoCloseButton = driver.findElement(By.className("qc-cmp-button"));
//				WebDriverWait wait2 = new WebDriverWait(driver,2);
//				wait2.until(ExpectedConditions.elementToBeClickable(cookieInfoCloseButton));
//				cookieInfoCloseButton.click();
//			    
//						 
//			    WebElement id = driver.findElement(By.name("username"));
//			    WebElement pass = driver.findElement(By.name("password"));
//			   try {
//			    	loginBtnPage.click();
//			    }
//			    catch(org.openqa.selenium.StaleElementReferenceException ex)
//			    {
//			    	loginBtnPage = driver.findElement(By.className("hidden-logged")); 
//			    	loginBtnPage.click();
//			    }
//			    
//			   WebElement loginButtonModal = driver.findElement(By.id("buttonLogin"));
//			    wait = new WebDriverWait(driver,5);
//			    wait.until(ExpectedConditions.elementToBeClickable(loginButtonModal));
//			    
//			    
//			    id.sendKeys(username);
//			    pass.sendKeys(password);
//			    
//				loginButtonModal.click();
//			   
//			    // 3 - Setta il Web Driver nel field della classe
			    setLoggedWebDriver(driver);
			    break;
			}
			catch (Exception e) {
				System.out.println("Errore durante la creazione del Driver loggato. Tentativo: " + i);
				setLoggedWebDriver(null);
				driver.close();
			}
			i++;
		}
	    
	    return driver;
	}


	
	private static void getLeagueName() {
		// Prende dalla sessione lo user e da DB recupera il nome della league
		
	}


//	private static WebDriver initDriver() {
//		ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File("C:/driver/chromedriver.exe")).build(); 
//		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//
//		ChromeOptions options = new ChromeOptions();
////		options.addArguments("--headless", "--disable-gpu");
//		options.addArguments("--allow-file-access-from-files");
//		options.addArguments("--verbose");
//		options.addArguments("load-extension=C:\\Users\\Menesbatto\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\cfhdojbkjhnklbpkdaibdccddilifddb\\1.13.4_0");
//		capabilities.setVersion("");
//		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
//		
//		
//		
//			long startTime = System.nanoTime();
//			System.out.println("1 CARICAMENTO DRIVER...");
//
//		WebDriver driver = new ChromeDriver(service, capabilities);
//		
//			long currentTime = System.nanoTime();
//			long duration = (currentTime - startTime);  //divide by 1000000 to get milliseconds.
//			System.out.println("DONE\t" + duration / 1000000);
//			System.out.println();
//			
////			WebDriverWait wait = new WebDriverWait(driver,2);
////			wait.until(ExpectedConditions.elementToBeClickable(loginButton));
//			try {
//				Thread.sleep(3000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
////			WebDriverWait wait2 = new WebDriverWait(driver,20);
////			WebElement body   = driver.findElement(By.tagName("body"));
////			body.findElement(By.id("title-main"));
////			wait2.until(ExpectedConditions.textToBePresentInElement(body, "stata installata"));
//			
//
//			driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "/w");
//		return driver;
//	}

	
	
	
	private static WebDriver initDriver() throws InterruptedException {
		
		
		
		
		
//		ClassLoader classLoader = HttpUtils.class.getClassLoader();
//        URL resource = classLoader.getResource("resources/drivers/chromedriver.exe");
//        File f = new File("Driver");
//        if (!f.exists()) {
//            f.mkdirs();
//        }
//        File chromeDriver = new File("Driver" + File.separator + "chromedriver.exe");
//        if (!chromeDriver.exists()) {
//            try {
//				chromeDriver.createNewFile();
//				org.apache.commons.io.FileUtils.copyURLToFile(resource, chromeDriver);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
//        System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());
      
		
		
		
		
		
		
		
		
		
		
		
		
		ChromeDriverService service = new ChromeDriverService.Builder().usingDriverExecutable(new File("C:/driver/Nuova Cartella/chromedriver.exe")).build(); 
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();

		ChromeOptions options = new ChromeOptions();
		options.addArguments("--allow-file-access-from-files");
		options.addArguments("--verbose");
		options.addArguments("load-extension=C:\\Users\\Menesbatto-PC\\AppData\\Local\\Google\\Chrome\\User Data\\Default\\Extensions\\cfhdojbkjhnklbpkdaibdccddilifddb\\3.4.3_0");
		//C:\Users\Menesbatto-PC\AppData\Local\Google\Chrome\User Data\Default\Extensions\cfhdojbkjhnklbpkdaibdccddilifddb\3.3.2_1
		capabilities.setVersion("");
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		WebDriver driver = new ChromeDriver(service, capabilities);
		
		Thread.sleep(1000);
		
		//driver.findElement(By.cssSelector("body")).sendKeys(Keys.CONTROL + "/w");
		
		// CHiude tutti i tab tranne il primo
		String originalHandle = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!handle.equals(originalHandle)) {
				driver.switchTo().window(handle);
				driver.close();
			}
		}
		driver.switchTo().window(originalHandle);
		
		
		return driver;
	}


	public static void closeDrivers(String username) {
		getLoggedWebDriver().close();
		setLoggedWebDriver(null);
		
	}


	public static WebDriver getLoggedWebDriver() {
		return loggedWebDriver;
	}


	public static void setLoggedWebDriver(WebDriver loggedWebDriver) {
		HttpUtils2.loggedWebDriver = loggedWebDriver;
	}


	
//	public static void shutdown(ChampEnum champ) {
//		WebDriver driver = getChampDriver(champ.getNextMatchesUrl());
//		driver.close();
//		String champName = getChampName(champ.getResultsUrl());
//		driversMap.remove(champName);
//	}
	
}
