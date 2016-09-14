package wrapper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import atu.testng.reports.ATUReports;
import atu.testng.reports.logging.LogAs;
import atu.testng.reports.utils.Utils;
import atu.testng.selenium.reports.CaptureScreen;
import atu.testng.selenium.reports.CaptureScreen.ScreenshotOf;

public class GenericWrapperMethods {
	RemoteWebDriver driver;
	int i = 1;

	public GenericWrapperMethods() {

	}

	public GenericWrapperMethods(RemoteWebDriver driver) {
		this.driver = driver;
	}

	/**
	 * This method will launch only firefox and maximise the browser and set the
	 * wait for 30 seconds and load the url
	 * 
	 * @param url - The url with http or https
	 * 
	 */
	public void launchApp(String url) throws IOException {

		try {
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
			driver.get(url);

			ATUReports.setWebDriver(driver);
			ATUReports.indexPageDescription = "Keyword Driven Project";
			ATUReports.setAuthorInfo("Venu",  Utils.getCurrentTime(), "1.0");

			ATUReports.add("The browser is launched successfully", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} catch (Exception e) {
			ATUReports.add("The browser could not loaded", LogAs.FAILED, new CaptureScreen(ScreenshotOf.DESKTOP));
		}

		takeSnapShot();
	}

	/**
	 * This method will launch any browser and maximise the browser and set the
	 * wait for 30 seconds and load the url
	 * 
	 * @param browser - Browser of type firefox or chrome or ie
	 * 
	 * @param url - The url with http or https
	 * @author Babu
	 *  
	 */
	public RemoteWebDriver launchApp(String browser, String url) throws IOException {

		/*
		 * DesiredCapabilities dc = new DesiredCapabilities();
		 * dc.setPlatform(Platform.WINDOWS);
		 * 
		 */

		// if the browser is firefox
		if (browser.equals("firefox")) {
			driver = new FirefoxDriver();

			/*
			 * dc.setBrowserName("firefox"); driver = new RemoteWebDriver(new
			 * URL("http://192.168.1.9:5555/wd/hub"),dc);
			 */

		} else if (browser.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");

			driver = new ChromeDriver();
			/*
			 * // Set Browser Name dc.setBrowserName("chrome");
			 * dc.setVersion("44.0"); driver = new RemoteWebDriver(new
			 * URL("http://192.168.1.9:5555/wd/hub"),dc);
			 */

		} else if (browser.equals("ie")) {
			System.setProperty("webdriver.ie.driver", "drivers\\IEDriverServer.exe");

			driver = new InternetExplorerDriver();

		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(url);
		takeSnapShot();

		return driver;

	}

	// take snapshot
	public void takeSnapShot() throws IOException {
		//File src = driver.getScreenshotAs(OutputType.FILE);
		//i++;
	}

	/**
	 * This method will enter the value to the text field using id attribute to locate
	 * 
	 * @param idValue - id of the webelement
	 * @param data - The data to be sent to the webelement
	 *  
	 */
	public void enterById(String idValue, String data) throws IOException {
		try {
			driver.findElement(By.id(idValue)).clear();
			driver.findElement(By.id(idValue)).sendKeys(data);
			ATUReports.add("The element with the id: " + idValue + " is entered with value :"+data, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		} catch (NoSuchElementException e) {
			ATUReports.add("The element with the id: " + idValue + " is not present", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} catch (WebDriverException e1) {
			ATUReports.add("The browser is unavailable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

	}

	/**
	 * This method will enter the value to the text field using name attribute to locate
	 * 
	 * @param nameValue - name of the webelement
	 * @param data - The data to be sent to the webelement
	 *  
	 */
    public void enterByName(String nameValue, String data) throws IOException {
		try{
		driver.findElement(By.name(nameValue)).clear();
		driver.findElement(By.name(nameValue)).sendKeys(data);
		ATUReports.add("The element with the name: " + nameValue + " is entered with value :"+data, LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		} catch (NoSuchElementException e) {
			ATUReports.add("The element with the name: " + nameValue + " is not present", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} catch (WebDriverException e1) {
			ATUReports.add("The browser is unavailable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	// Click by class name
	public void clickByClassName(String classValue) throws IOException {
		try {
			driver.findElement(By.className(classValue)).click();
			ATUReports.add("The element with the class: " + classValue + " is clicked successfully", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		} catch (Exception e) {
			ATUReports.add("The element with the class: " + classValue + " is not present", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		}
	}

	// Verify title
	public void verifyTitle(String title) throws IOException {
		if (driver.getTitle().equalsIgnoreCase(title)) {
			ATUReports.add("The title matches successfully", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} else {
			ATUReports.add("The title do not match", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}
	}

	// Close browser
	public void quitBrowser() {
		driver.quit();
		ATUReports.add("The browser closed successfully", LogAs.PASSED, new CaptureScreen(ScreenshotOf.DESKTOP));

	}

	public void clickLink(String linkName) throws IOException {
		try{
		driver.findElement(By.linkText(linkName)).click();
		ATUReports.add("The element with the link: " + linkName + " is clicked successfully", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		} catch (NoSuchElementException e) {
			ATUReports.add("element with the link: " + linkName + " is not present", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} catch (WebDriverException e1) {
			ATUReports.add("The browser is unavailable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

	}
	
	public void clickByName(String name) throws IOException {
		try{
		driver.findElement(By.name(name)).click();
		ATUReports.add("The element with the name: " + name + " is clicked successfully", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

		} catch (NoSuchElementException e) {
			ATUReports.add("element with the name: " + name + " is not present", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		} catch (WebDriverException e1) {
			ATUReports.add("The browser is unavailable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
		}

	}

	// Click by class name
	public void clickByXpath(String xpath) throws IOException {
		try{
		driver.findElement(By.xpath(xpath)).click();
		ATUReports.add("The element with the xpath: " + xpath + " is clicked successfully", LogAs.PASSED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));

	} catch (NoSuchElementException e) {
		ATUReports.add("element with the xpath: " + xpath + " is not present", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	} catch (WebDriverException e1) {
		ATUReports.add("The browser is unavailable", LogAs.FAILED, new CaptureScreen(ScreenshotOf.BROWSER_PAGE));
	}

	}

	// accept alert
	public void acceptAlert() {
		driver.switchTo().alert().accept();
	}

}
