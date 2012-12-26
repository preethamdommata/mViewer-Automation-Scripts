package org.imaginea.mviewer.page;

import java.util.ArrayList;
import java.util.List;

import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/** Collection class provides all the methods related to collection to perform actions on the Collection.
 * The Collection under a Particular DB can be accessed for which that DB WebElement need to be passed to access the collections under that DB.
 * @author preethamd
 *
 */
public class Collection extends BaseClass {
	ArrayList<String> collList;
	WebElement dbElement;

	public Collection(WebDriver driver, WebElement dbElement) {
		this.driver = driver;
		this.dbElement = dbElement;
	}

	/**Provides the List of Collection WebElements present under a DB.
	 * @return - List of Collection WebElemnts
	 */
	public List<WebElement> getCollections() {
		String collectionsCss = "div.wrapper div#collectionBuffer div#collNames ul.lists.first-of-type>li";
		List<WebElement> collectionElements = driver.findElements(By
				.cssSelector(collectionsCss));
		return collectionElements;
	}

	/**Provides the Collection upon passing the Collection Name.
	 * @param collectionName - Collection Name for which Collection WebElement is required
	 * @return - Collection WebElement
	 */
	public WebElement getCollection(String collectionName) {
		List<WebElement> collectionElements = getCollections();
		WebElement returnElement = null;
		for (WebElement collection : collectionElements) {
			if ((collection.getAttribute("label")).equals(collectionName)) {
				returnElement = collection;
				break;
			}
		}
		return returnElement;
	}

	/**Provides the ArrayList of collections Names present under a DB.
	 * @param collectionElements - List of Collection WebElements
	 * @return - ArrayList of Collection Names
	 */
	public ArrayList<String> getCollectionsNameList(
			List<WebElement> collectionElements) {
		ArrayList<String> collectionList = new ArrayList<String>();
		for (WebElement element : collectionElements) {
			String collName = element.getAttribute("label");
			collectionList.add(collName);
		}
		return collectionList;
	}

	/**Provides the ArrayList of collections Names present under a DB.
	 * No need of providing Collection WebElemnts to get the Name List. 
	 * It by default searches for the collection WebElemnts present under selected DB and returns Collections Name List.
	 * @return
	 */
	public ArrayList<String> getCollectionsNameList() {
		List<WebElement> collectionElements = getCollections();
		ArrayList<String> collectionNameList = getCollectionsNameList(collectionElements);
		return collectionNameList;
	}

	/**Clicks on the Add Collection option from the Collections Option Menu.
	 * Add Collection pop up is displayed
	 * 
	 */
	public void openCollection() {
		// clickDBMenu(dbElement);
		String collectionLocator = "#" + dbElement.getAttribute("label")
				+ "_subMenu ul.first-of-type > li > a[index='1']";
		explicitWait(driver, collectionLocator);
		dbElement.findElement(By.cssSelector(collectionLocator)).click();
		pause(2);
	}

	/**Clears the Collection Name field on Add Collection pop up.
	 * 
	 */
	public void clearCollectionName() {
		driver.findElement(By.id("newCollName")).clear();
	}

	/**Sets the Collection Name field with the Collection Name provided.
	 * @param collectionName - Collection Name
	 */
	public void setCollectionName(String collectionName) {
		clearCollectionName();
		driver.findElement(By.id("newCollName")).sendKeys(collectionName);
	}

	/**Click on the buttons provided on the Add Collection pop up displayed.
	 * Options include: "Submit" and "Cancel"
	 * @param action - "Submit" and "Cancel"
	 */
	public void clickCollection(String action) {
		List<WebElement> buttons = driver.findElements(By
				.cssSelector("div#addColDialog_c div.ft button"));
		for (WebElement element : buttons) {
			if (element.getText().equalsIgnoreCase(action)) {
				element.click();
				break;
			}
		}
	}

	
	/** clicks on the Close button present on the Add Collection pop up displayed.
	 * 
	 */
	public void clickCollectionClose() {
		explicitWait(driver,
				"div#addColDialog_c div#addColDialog>a.container-close");
		driver.findElement(
				By.cssSelector("div#addColDialog_c div#addColDialog>a.container-close"))
				.click();
	}

	/**Method performs Add Collection. User can Create Capped collection or Normal collection by providing all the parameters provided.
	 * @param collectionName - Collection that need to be created
	 * @param capped - "True" if capped, "False" if Normal
	 * @param maxSize - Max Size of the Capped collection in Bytes.
	 * @param maxDocsvalue - Cap on number of maximum number of Documents per Collection. 
	 * @return - Return "True" if new Collection is created, "false" if not.
	 */
	public boolean addCollection(String collectionName, boolean capped, String maxSize, String maxDocsvalue) {
		boolean resultValue = false;
		boolean addCollectionResult=false;
		collList = getCollectionsNameList();
		if (collList.contains(collectionName)) {
			System.out
					.println("This collection is already present in the present DB.");
			clickCollectionsOptionMenu(collectionName);
			//db.clickDbOptionsMenu(dbElement);
		} else {
			openCollection();
			setCollectionName(collectionName);
			if (capped==true) {
				setCollectionCapped(maxSize);
				if (!(maxDocsvalue.isEmpty())) {
					setCollectionCapped(maxSize, maxDocsvalue);
				}
			}
			pause(2);
			clickCollection("Submit");
			pause(2);
			WebElement collectionElement = getCollection(collectionName);
			resultValue = verifyCollectionPresentDisplay(collectionElement);
			if(resultValue==true){
				addCollectionResult = true;
			}
		}
		return addCollectionResult;
	}
	/**Method performs Add Collection. User can Create Capped collection or Normal collection by providing all the parameters provided.
	 * @param collectionName - Collection that need to be created
	 * @param capped - "True" if capped, "False" if Normal
	 * @param maxSize - Max Size of the Capped collection in Bytes. 
	 * @return - Return "True" if new Collection is created, "false" if not.
	 */
	public boolean addCollection(String collectionName, boolean capped, String maxSize) {
		boolean returnValue=false;
		returnValue = addCollection(collectionName,capped, maxSize, "");
		return returnValue;
	}
	/**Method performs Add Collection. User can Create Normal collection by providing all the parameters provided.
	 * @param collectionName - Collection that need to be created 
	 * @return - Return "True" if new Collection is created, "false" if not.
	 */
	public boolean addCollection(String collectionName) {
		boolean returnValue=false;
		returnValue = addCollection(collectionName, false, "");
		return returnValue;
	}

	/**Verifies the Collection whether that is present under that DB and is displayed.
	 * @param collectionElement - Collection WebElement that need to be verified
	 * @return - "true" if displayed, "false" if not displayed
	 */
	public boolean verifyCollectionPresentDisplay(WebElement collectionElement) {
		boolean returnValue = false;
		List<WebElement> collectionElements = getCollections();
		if (collectionElements.contains(collectionElement)
				&& collectionElement.isDisplayed()) {
			System.out.println("Collection is present and diplayed");
			returnValue = true;
		}
		return returnValue;
	}

	/**Clicks on the Update Collection Option provided in the Collection Menu options.
	 * A Update Collection pop up will be displayed
	 * This provides the functionality to Update a Collection.
	 * @param collName - Collection Name which need to be updated
	 */
	public void clickUpdateColletion(String collName) {
		String updateCollectionCss = "div#"
				+ collName
				+ "_subMenu div.yui3-menu-content ul.first-of-type>li>a[index='3']";
		WebElement updateElement = driver.findElement(By
				.cssSelector(updateCollectionCss));
		updateElement.click();
	}
	
	/**Clicks on Drop Collection option from Collection Menu Options.
	 * Drop Collection pop up will be displayed.
	 * @param collName - Collection Name for which the Drop Collection Pop up should be displayed.
	 */
	public void clickDropColletion(String collName) {
		WebElement collElement = getCollection(collName);
		String dropCollectionCss = "div#"
				+ collName
				+ "_subMenu div.yui3-menu-content ul.first-of-type>li>a[index='2']";
		WebElement dropElement = collElement.findElement(By
				.cssSelector(dropCollectionCss));
		dropElement.click();
	}

	/**Action that need to perform after the Drop Collection pop up is opened.
	 * Actions include "Yes" or "No". Selecting "Yes" will delete the Collection. selecting "No" will cancel the Drop operation.
	 * @param action - "Yes" or "No"
	 */
	public void clickDropCollectionButton(String action) {
		List<WebElement> buttons = driver
				.findElements(By
						.cssSelector("div#simpleDialogContainer div#simpledialog span.first-child>button"));
		for (WebElement button : buttons) {
			if (button.getText().equalsIgnoreCase(action)) {
				button.click();
				break;
			}
		}
	}

	/**Clicks on Collection Options Menu for selected Collection
	 * @param collName - Collection Name for which the Collection Menu options need to be displayed
	 */
	public void clickCollectionsOptionMenu(String collName) {
		WebElement collElement = getCollection(collName);
		String collOptionsMenuCss = "span.yui3-menu-label>a[href='#" + collName
				+ "_subMenu']";
		WebElement collOptionMenuElement = collElement.findElement(By
				.cssSelector(collOptionsMenuCss));
		collOptionMenuElement.click();
	}

	/**Clicks on Collection WebElement. This selects the Collection selected.
	 * And this displayed the Documents present in that collection.
	 * @param collName - Collection which need to be selected
	 */
	public void clickOnCollection(String collName) {
		WebElement collElement = getCollection(collName);
		String collMenuCss = "span.yui3-menu-label>a#" + collName + "";
		WebElement collMenuElement = collElement.findElement(By
				.cssSelector(collMenuCss));
		collMenuElement.click();
	}

	/**Returns the control from Collection to Document Page
	 * @param collName - Collection selected.
	 * @return - Document WebDriver, Collection WebElement
	 */
	public Document document(String collName) {
		boolean result = addCollection(collName);
		WebElement collElement = getCollection(collName);
		clickOnCollection(collName);
		if(result==true){
		clickCollectionsOptionMenu(collName);
		}
		//pause(5);
		return new Document(driver, collElement);
	}

	/**Sets the Data in the Add Collection pop up by selecting the Capped check box and entering the Data in Max Size and Max Docs fields.
	 * @param sizeInBytes - Max collection size in bytes
	 * @param maxDocs - Max Documents
	 */
	public void setCollectionCapped(String sizeInBytes, String maxDocs) {
		driver.findElement(By.id("isCapped")).click();
		driver.findElement(By.name("capSize")).sendKeys(sizeInBytes);
		driver.findElement(By.name("maxDocs")).sendKeys(maxDocs);
	}

	/**Sets the Data in the Add Collection pop up by selecting the Capped check box and entering the Data only in Max Size.
	 * @param sizeInBytes - Max collection size in bytes
	 */
	public void setCollectionCapped(String sizeInBytes) {
		setCollectionCapped(sizeInBytes, "");
	}

	/**Returns DB WebElement when DB Name is passed.
	 * @return - DB WebElement
	 */
	public WebElement getDBElement() {
		WebElement dbName = dbElement;
		return dbName;
	}

	/**Clicks on the Statistics option present in the Collection Options Menu.
	 * Clicking on this will displayed Statistics page and pass over the control to Statistics Page.
	 * @param collName - Collection for which the Statistics need to be checked.
	 * @return - Statistics WebDriver, Collection WebElement
	 */
	public Statistics clickStatistics(String collName) {
		WebElement collElement = getCollection(collName);
		String updateCollectionCss = "div#"
				+ collName
				+ "_subMenu div.yui3-menu-content ul.first-of-type>li>a[index='4']";
		WebElement statisticsElement = driver.findElement(By
				.cssSelector(updateCollectionCss));
		statisticsElement.click();
		return new Statistics(driver, collElement);
	}
	
	/**Clicks on Collection Name provided and returns the driver to the assigned element.
	 * @param collName - Selected Collection name to be clicked
	 * @return - Driver, DB WebElement
	 */
	public CollectionQueryExecutor collectionQueryExecutor(String collName){
		clickOnCollection(collName);
		return new CollectionQueryExecutor(driver, getDBElement());
	}
}
