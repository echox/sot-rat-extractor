package sot;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Keys;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverLogLevel;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Run {
	
	final static String HTML_ID_USER = "i0116";
	final static String HTML_ID_PASSWORD = "i0118";
	final static String HTML_ID_BUTTON = "idSIButton9";
	final static String GECKO_DRIVER_PATH = "/extractor/geckodriver";
	final static long TIMEOUT_SECONDS = 5;

	
	public static void main(String[] args) {
		
		String login = System.getenv("LOGIN");
		String password = System.getenv("PASSWORD");
		
		if(login == null || password == null) {
			System.out.println("You need to set the environment vars LOGIN and PASSWORD");
			System.exit(1);
		}
		
    	System.setProperty("webdriver.gecko.driver",GECKO_DRIVER_PATH);
		
    	FirefoxOptions options = new FirefoxOptions();
    	options.setAcceptInsecureCerts(false);
    	options.setLogLevel(FirefoxDriverLogLevel.WARN);
    	options.setHeadless(true);
    	
    	FirefoxDriver driver = new FirefoxDriver(options);

        String url = "https://www.seaofthieves.com/login";
        driver.get(url);
        System.out.println("visiting " + url + "...");
        System.out.println("(this should redirect us to Microsoft Live Login)");

        System.out.println("-> " + driver.getTitle());
        System.out.println("entering credentials...");
        
        new WebDriverWait(driver, TIMEOUT_SECONDS).until(d -> d.findElement(By.id(HTML_ID_USER))).sendKeys(login + Keys.ENTER);;        
        new WebDriverWait(driver, TIMEOUT_SECONDS).until(d -> d.findElement(By.id(HTML_ID_PASSWORD))).sendKeys(password);
        new WebDriverWait(driver, TIMEOUT_SECONDS).until(ExpectedConditions.elementToBeClickable(By.id(HTML_ID_BUTTON))).click();

        System.out.println("login finished, should redirect to SoT...");
        System.out.println("-> " + driver.getTitle());
        System.out.println("searching for RAT...");

        Set<Cookie> cookies = driver.manage().getCookies();
 
        Cookie rat = null;
        Iterator<Cookie> it = cookies.iterator();
        while (it.hasNext()) {
        	Cookie cookie = it.next();
        	
        	if ("rat".equals(cookie.getName())) {
        		rat = cookie;
        		break;
        	}	
        }
        
        System.out.println("closing driver...");
        driver.close();
        System.out.println("driver closed");
        
        if (rat != null) {
        	System.out.println("your RAT expiry date: " + rat.getExpiry());
			System.out.println("your RAT: " + rat.getValue());
        } else {
        	System.out.println("No RAT received, something went wrong :-/");
        }
	}
}
