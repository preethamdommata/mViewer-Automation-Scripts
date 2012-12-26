package org.imaginea.mviewer.page;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * DataBase class provides methods to work on the DataBase operations like
 * finding the Databases present, creating new DataBase and many other..
 * 
 * @author preethamd
 * 
 */
public class DataBase extends BaseClass {
	ArrayList<String> presentDBNames = new ArrayList<String>();
	String dbSubmitButtonLocator = "button#yui-gen0-button";
	String dbCancelButtonLocator = "button#yui-gen1-button";
	String createDBDialogLocator = "#addDBDialog_h.hd";
	String dbCloseButtonLocator = "div#addDBDialog_c div#addDBDialog>a.container-close";

	List<WebElement> dbElements;

	public DataBase(WebDriver driver) {
		this.driver = driver;
	}

	/**
	 * Method returns DB WebElement when Data Base Name is provided. Will Return DB
	 * WebElement only if DB with that name exists in DB Section List.
	 * 
	 * @param dbName
	 *            - Name of the DataBase in String format
	 * @return Data Base WebElement
	 */
	public WebElement getdbElement(String dbName) {
		WebElement returnElement = null;
		dbElements = driver.findElements(By.cssSelector(dbCss));
		// dbElements = getList(dbCss);

		for (WebElement dbelement : dbElements) {
			String alldbName = dbelement.getAttribute("label");
			if (alldbName.equals(dbName)) {
				returnElement = dbelement;
				break;
			}
		}
		return returnElement;
	}

	/**Method provides all existing Data Bases names in ArrayList
	 * @return ArrayList of DataBases present
	 */
	public ArrayList<String> getDBNames() {
		ArrayList<String> dbNames = new ArrayList<String>();
		dbElements = driver.findElements(By.cssSelector(dbCss));
		for (WebElement element : dbElements) {
			String dbName1 = element.getAttribute("label");
			dbNames.add(dbName1);
		}
		return dbNames;
	}

	/**Method creates a DataBase with the DBName provided in mViewer. If a DB with that name already exists it will skip and displayes a message and Skips creatin it.
	 * If DB with that Name does not exists, it will create one. 
	 * @param dbName - Name of the Data Base that need to created.
	 */
	public void createDB(String dbName) {
		presentDBNames = getDBNames();
		if (presentDBNames.contains(dbName)) {
			System.out.println("this DB is already present");
		} else {

			openDB();
			clearDBName();
			setDBName(dbName);
			clickNewDBSubmit();
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			System.out.println("DB is created successfully");
			presentDBNames.add(dbName);
			// confirmation message should be added here. Functionality not
			// present here as of now.
		}
	}

	/**Method verifies whether the provided DB WebElement is present and displayed in the DB List or not and prints out the message accordingly. 
	 * @param dbElement - DB WebElement that need to verified.
	 */
	public void verifyDBPresentDisplay(WebElement dbElement) {
		dbElements = driver.findElements(By.cssSelector(dbCss));
		if (dbElements.contains(dbElement) && dbElement.isDisplayed())
			System.out.println("DB is present and Displayed");
	}

	/**Method to click on the DB options Menu
	 * @param dbElement - Db WebElement for which the option should be displayed
	 */
	public void clickDbOptionsMenu(WebElement dbElement) {
		String optionClick = "span.yui3-menu-label>a.yui3-menu-toggle";
		WebElement dbOptionClick = dbElement.findElement(By
				.cssSelector(optionClick));
		dbOptionClick.click();
	}

	/**Data Method having default options that will be displayed when clicked up on the DB options Menu
	 * @return - ArrayList of the Options
	 */
	public ArrayList<String> defaultdbOptionsList() {
		ArrayList<String> optionNameList = new ArrayList<String>();
		optionNameList.add("Add Collection");
		optionNameList.add("Add GridFS Bucket");
		optionNameList.add("Drop Database");
		optionNameList.add("Statistics");
		return optionNameList;
	}

	/**Method to capture the Actual DB options displayed when clicked on a DB options Menu
	 * @param dbElement - DB WebElement for which options to be verified
	 * @return - DB options present for that DB
	 */
	public List<WebElement> actualDBOptionsElements(WebElement dbElement) {
		String dbName = dbElement.getAttribute("label");
		String optionCss = "#" + dbName + "_subMenu ul.first-of-type > li > a";
		clickDbOptionsMenu(dbElement);
		List<WebElement> dbOptionsElements = dbElement.findElements(By
				.cssSelector(optionCss));
		clickDbOptionsMenu(dbElement);
		return dbOptionsElements;
	}

	/**Method to Verify the Displayed Options with the Default options that need to be displayed
	 * @param dbElement - DB WebElement for which options to be verified with the dafault values
	 */
	public void dbOptionsCheck(WebElement dbElement) {
		pause(2);
		ArrayList<String> expectedOptions = defaultdbOptionsList();
		ArrayList<String> options = new ArrayList<String>();
		List<WebElement> dboptions = actualDBOptionsElements(dbElement);
		clickDbOptionsMenu(dbElement);
		pause(2);
		for (WebElement o : dboptions) {
			String optionName = o.getText();
			Assert.assertTrue(o.isDisplayed(), "the Element is not displayed");
			if (o.isDisplayed()) {
				System.out.println(o.getText() + " is displayed correctly");
			}
			options.add(optionName);
		}
		clickDbOptionsMenu(dbElement);
		Assert.assertEquals(options, expectedOptions);
	}

	/**Method to Click on New DB option provided to add a new DB. Add Data Base pop up will be displayed after clicking on the New DB button. 
	 * 
	 */
	public void openDB() {
		driver.findElement(By.cssSelector(getLocator("dbNewButtonLocator")))
				.click();
		// explicitWait(driver, createDBDialogLocator);
		pause(2);
		Assert.assertTrue(
				driver.findElement(By.cssSelector(createDBDialogLocator))
						.isDisplayed(), "The Pop window is not displayed.");
		// explicitWait(driver, dbCloseButtonLocator);
	}

	/**Clears tje DB Name field on Add Data Base pop up.
	 * 
	 */
	public void clearDBName() {
		driver.findElement(By.name("name")).clear();
	}

	/**Clicks on Submit button on Add Data Base pop up.
	 * 
	 */
	public void clickNewDBSubmit() {
		driver.findElement(By.cssSelector(dbSubmitButtonLocator)).click();
	}

	/**clicks on Close Button on Add Data Base pop up.
	 * 
	 */
	public void clickNewDBClose() {
		driver.findElement(By.cssSelector(dbCloseButtonLocator)).click();
	}

	/**clicks on Cancel button on Add Data Base pop up.
	 * 
	 */
	public void clickNewDBCancel() {
		driver.findElement(By.cssSelector(dbCancelButtonLocator)).click();
	}

	/**Sets DB Name field on Add Data Base field with the DB name passed.
	 * @param dbName - DB Name for DB Name field
	 */
	public void setDBName(String dbName) {
		clearDBName();
		driver.findElement(By.name("name")).sendKeys(dbName);

	}

	/**Clicks on DB WebElement to select it. 
	 * After selecting the DB, the collections, GridFS and Statistics realated to that DB will be displayed.
	 * 
	 * @param dbElement
	 */
	public void clickDBMenu(WebElement dbElement) {
		// explicitWait(driver, getLocator("dbNewButtonLocator"));
		String dbClickCss = "span>a[role='menuitem']";
		dbElement.findElement(By.cssSelector(dbClickCss)).click();
	}

	/**Clicks on Drop DB option and a Drop DB pop up will be displayed.
	 * @param dbElement - DB WebElement which need to dropped
	 */
	public void openDropDB(WebElement dbElement) {
		// WebElement element = dbElement;
		// db.clickDBMenu(dbElement);
		String collectionLocator = "#" + dbElement.getAttribute("label")
				+ "_subMenu ul.first-of-type > li > a[index='3']";
		dbElement.findElement(By.cssSelector(collectionLocator)).click();
		pause(2);
	}

	/**Provides the List of DB Elements present
	 * @return - List of DB WebElements present
	 */
	public List<WebElement> getDBs() {
		List<WebElement> dbElements = driver
				.findElements(By.cssSelector(dbCss));
		return dbElements;
	}

	/**Action to be performed after the DB Drop pop up is displayed.
	 * Actions include selecting "Yes" or "No" to proceed with the dropping the DB.
	 * @param action - "Yes" or "No"
	 */
	public void clickDBdrop(String action) {
		List<WebElement> buttons = driver
				.findElements(By
						.cssSelector("div#simpleDialogContainer div.ft span.first-child>button"));
		for (WebElement element : buttons) {
			if (element.getText().equalsIgnoreCase(action)) {
				element.click();
				break;
			}
		}
	}

	public void dropDB(String dbName, String action){
		WebElement dbElement = getdbElement(dbName);
		clickDBMenu(dbElement);
		clickDbOptionsMenu(dbElement);
		openDropDB(dbElement);
		clickDBdrop(action);
		if(action.equalsIgnoreCase("Yes")){
			driver.switchTo().alert().accept();
		}
	}
	
	/**clicking on the Statistics option from the DB options Menu. Hand over the control to Statistics Page.
	 * @param dbName - DB WebElement for which the Statistics should be checked 
	 * @return - Statistics WebDriver
	 */
	public Statistics clickDBStatistics(String dbName) {
		// String dbName = "Preetham8";
		WebElement dbElement = getdbElement(dbName);
		clickDbOptionsMenu(dbElement);
		String dbStatisticsLocator = "#" + dbElement.getAttribute("label")
				+ "_subMenu ul.first-of-type > li > a[index='4']";
		dbElement.findElement(By.cssSelector(dbStatisticsLocator)).click();
		return new Statistics(driver, dbElement);
	}

	/**Clicking on the DB WebElement will pass over the control to Statistics page.
	 * @param dbElement - DB WebElement for which the Statistics should be displayed
	 * @return - Statistics WebDriver
	 */
	public Statistics clickDBStatistics(WebElement dbElement) {
		// explicitWait(driver, getLocator("dbNewButtonLocator"));
		String dbClickCss = "span>a[role='menuitem']";
		dbElement.findElement(By.cssSelector(dbClickCss)).click();
		return new Statistics(driver, dbElement);
	}

	public HomeLeft loginleft() {
		return new HomeLeft(driver);
	}

	/**Passes over the Control to GridFS under that DB to perform actions related to GridFS.
	 * @param dbName - GridFS under DBName for which the control will be passed.
	 * @return - GridFS WebDriver
	 */
	public GridFS gridFS(String dbName) {
		pause(5);
		// String dbName = "Preetham8";
		WebElement dbElement = getdbElement(dbName);
		clickDBMenu(dbElement);
		clickDbOptionsMenu(dbElement);
		return new GridFS(driver, dbElement);
	}

	/**Passes over the Control to collection under that DB to perform actions related to Collection.
	 * @param dbName - Collection under DBName for which the control will be passed.
	 * @return - Collection WebDriver
	 */
	public Collection collection(String dbName) {
		// String dbName = "Preetham8";
		createDB(dbName);
		pause(3);
		WebElement dbElement = getdbElement(dbName);
		clickDBMenu(dbElement);
		pause(2);
		clickDbOptionsMenu(dbElement);
		return new Collection(driver, dbElement);
	}
}
