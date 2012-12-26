package org.imaginea.mviewer.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.imaginea.mviewer.page.Collection;
import org.imaginea.mviewer.page.CollectionQueryExecutor;
import org.imaginea.mviewer.page.DataBase;
import org.imaginea.mviewer.page.Document;
import org.imaginea.mviewer.page.GridFS;
import org.imaginea.mviewer.page.Home;
import org.imaginea.mviewer.page.HomeLeft;
import org.imaginea.mviewer.page.Login;
import org.imaginea.mviewer.page.Statistics;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

public class BaseClass {
	// Variables

	protected WebDriver driver;
	protected Login loginPage;
	protected Home homePage;
	protected HomeLeft homeLeft;
	protected DataBase db;
	protected Collection collection;
	protected GridFS grid;
	protected Statistics stat;
	protected Document document;
	protected CollectionQueryExecutor qe;
	protected DocReader reader;
	String docPath = "C:\\Users\\preethamd\\Desktop\\sampleData.txt";
	protected String dbCss = "#dbNames ul.lists > li";

	public List<WebElement> dbElementsList() {
		List<WebElement> dbElementsList = driver.findElements(By
				.cssSelector(dbCss));
		return dbElementsList;
	}

	public String getActConfirmationMess() {
		explicitWait(driver,
				"div.wrapper div.title-head span#infoMsg>span#infoText.infoText");
		return driver
				.findElement(
						By.cssSelector("div.wrapper div.title-head span#infoMsg>span#infoText.infoText"))
				.getText();
	}
	
	public String getErrorMess() {
		pause(2);
		return driver
				.findElement(
						By.cssSelector("div.wrapper span#infoMsg.infoMsg>span#infoText.infoText"))
				.getText();
	}
	
	@Parameters({ "Browser" })
	@BeforeClass
	public void beforeClassSuper(String Browser) {
		try {
			this.driver = driverInitialization(driver, Browser);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		loginPage = new Login(driver);
	}

	@AfterClass(alwaysRun = true)
	public void afterClassSuper() {
		driver.close();
		driver.quit();
	}

	@BeforeMethod
	public void beforeMethodSuper() {
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@AfterMethod
	public void afterMethodSuper() {
		// driver.manage().deleteAllCookies();
		pause(2);
	}

	public String getLocator(String elementLocator) {
		Properties prop = new Properties();

		try {
			prop.load(new FileInputStream(
					"C:\\Users\\preethamd\\workspace\\mViewerAutomationTestScripts\\resources\\testdata\\mViewerLocatordata.properties"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

		elementLocator = prop.getProperty(elementLocator);
		return elementLocator;
	}

	public Login disconnect(WebDriver driver) {
		driver.findElement(By.cssSelector("a#disconnect.disconnect.navigable"))
				.click();
		return new Login(driver);
	}

	public void checkAllElementsDisplay(String elementLocator,
			String elementValue) {
		checkAllElementsDisplay(elementLocator);
		Assert.assertEquals(driver.findElement(By.cssSelector(elementLocator))
				.getAttribute("value"), elementValue);
		System.out.println(elementLocator
				+ " and its default values had been verified");
	}

	public void checkAllElementsDisplay(String elementLocator) {
		Assert.assertTrue(driver.findElement(By.cssSelector(elementLocator))
				.isDisplayed(), elementLocator + " is not displayed");
		System.out.println(elementLocator + " had been verified");
	}

	public WebDriver driverInitialization(WebDriver driver, String driverName)
			throws MalformedURLException {

		DesiredCapabilities capability = null;
		String remoteURL = getLocator("remoteURL");
		String appURL = getLocator("appURL");

		if (driverName.equalsIgnoreCase("firefox")) {
			capability = DesiredCapabilities.firefox();
			
		} else if (driverName.equalsIgnoreCase("IE")) {
			capability = DesiredCapabilities.internetExplorer();
		} else if (driverName.equalsIgnoreCase("Chrome")) {
			// System.setProperty("webdriver.chrome.driver",
			// "C:\\Users\\preethamd\\AppData\\Local\\Google\\Chrome\\Application\\chrome.exe");
			// driver = new ChromeDriver();
			capability = DesiredCapabilities.chrome();
		}

		driver = new RemoteWebDriver(new URL(remoteURL), capability);
		driver.get(appURL);
		driver.manage().window().maximize();
		return driver;
	}

	public void Reader() {
		reader.getnumberOfRows();
		reader.getnumberOfColumns();
	}

	public DocReader docFinder() {
		DocReader reader1 = null;
		String excel = ".xls";
		String csv = ".txt";
		String docext = docPath.substring(docPath.length() - 4);
		if (excel.equalsIgnoreCase(docext)) {
			reader1 = new ReadExcel(docPath);
		} else if (csv.equalsIgnoreCase(docext)) {
			try {
				reader1 = new CSVutil(docPath);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return reader1;
	}

	public static void explicitWait(WebDriver driver, String elementLocator) {
		WebDriverWait wait = new WebDriverWait(driver, 30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By
				.cssSelector(elementLocator)));
	}

	public void pause(long seconds) {
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
