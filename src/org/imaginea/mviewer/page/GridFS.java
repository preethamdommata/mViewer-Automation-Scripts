package org.imaginea.mviewer.page;

import java.util.ArrayList;
import java.util.List;

import org.testng.Assert;

import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class GridFS extends BaseClass {
	ArrayList<String> gridList;
	WebElement dbElement;
	public GridFS(WebDriver driver, WebElement dbElement) {
		this.driver = driver;
		this.dbElement = dbElement;
	}

	public void openGrid() {
		String collectionLocator = "#" + dbElement.getAttribute("label")
				+ "_subMenu ul.first-of-type > li > a[index='2']";
		dbElement.findElement(By.cssSelector(collectionLocator)).click();
	}

	public List<WebElement> getGrids() {
		String gridsCss = "div.wrapper div#gridFSBuffer div#bucketNames ul.lists.first-of-type>li";
		List<WebElement> gridElements = driver.findElements(By
				.cssSelector(gridsCss));
		return gridElements;
	}

	public WebElement getGrid(String gridName) {
		List<WebElement> gridElements = getGrids();
		WebElement returnElement = null;
		for (WebElement grid : gridElements) {
			if ((grid.getAttribute("label")).equals(gridName)) {
				returnElement = grid;
				break;
			}
		}
		return returnElement;
	}

	public ArrayList<String> getGridsNameList(List<WebElement> gridElements) {
		ArrayList<String> gridList = new ArrayList<String>();
		for (WebElement element : gridElements) {
			String gridName = element.getAttribute("label");
			gridList.add(gridName);
		}
		return gridList;
	}

	public ArrayList<String> getGridsNameList() {
		List<WebElement> gridElements = getGrids();
		ArrayList<String> gridList = new ArrayList<String>();
		for (WebElement element : gridElements) {
			String gridName = element.getAttribute("label");
			gridList.add(gridName);
		}
		return gridList;
	}

	public void clickGrid(String action) {
		List<WebElement> buttons = driver.findElements(By
				.cssSelector("div#addGridFSDialog_c div.ft button"));
		for (WebElement element1 : buttons) {
			if (element1.getText().equalsIgnoreCase(action)) {
				element1.click();
				break;
			}
		}
	}

	public void clickGridClose() {
		explicitWait(driver,
				"div#addGridFSDialog_c div#addGridFSDialog>div#addGridFSDialog_h");
		driver.findElement(
				By.cssSelector("div#addGridFSDialog_c div#addGridFSDialog>a.container-close"))
				.click();
	}

	public void clearBucketName() {
		WebElement bucketNameElement = driver
				.findElement(By
						.cssSelector("div#addGridFSDialog_c div.bd form[method='GET']>input[name='name']"));
		bucketNameElement.clear();
	}

	public void setBucketName(String bucketName) {
		WebElement bucketNameElement = driver
				.findElement(By
						.cssSelector("div#addGridFSDialog_c div.bd form[method='GET']>input[name='name']"));
		bucketNameElement.clear();
		bucketNameElement.sendKeys(bucketName);

	}

	public void addGrid(String gridName) {
		gridList = getGridsNameList();
		String dbName = dbElement.getAttribute("label");
		if (gridList.contains(gridName)) {
			System.out.println("The Grid with this name already exists..!!");
		} else {
			int bucketsBeforeAdding = getGrids().size();
			openGrid();
			explicitWait(driver, "div#addGridFSDialog_c div#addGridFSDialog>div#addGridFSDialog_h");
			setBucketName(gridName);
			clickGrid("Submit");
			pause(2);
			int bucketsAfterAdding = getGrids().size();
			Assert.assertEquals(bucketsAfterAdding, bucketsBeforeAdding + 1);
			String expectedConfirmationMess = "GridFS bucket [" + gridName
					+ "] added to database [" + dbName + "].";
			String actualConfirmationMess = getActConfirmationMess();
			Assert.assertEquals(actualConfirmationMess, expectedConfirmationMess);
		}

	}

	public void clickOnGrid(String bucketName) {
		String gridCss = "li[label='"+bucketName+"']>span>a#"+bucketName+"[label='"+bucketName+"']";
		WebElement bucket = getGrid(bucketName);
		bucket.findElement(By.cssSelector(gridCss)).click();
	}

	public void clickGridOptions(String bucketName){
		String gridCssOption = "li[label='"+bucketName+"']>span>a[href='#"+bucketName+"_subMenu']";
		WebElement bucket = getGrid(bucketName);
		clickOnGrid(bucketName);
		bucket.findElement(By.cssSelector(gridCssOption)).click();
	}
	
	public List<WebElement> getGridOptions(WebElement gridElement){
		String gridName = gridElement.getAttribute("label");
		String gridsCss = "div#bucketNames	div#"+gridName+"_subMenu>div>ul>li>a";
		List<WebElement> gridOptionsElements = driver.findElements(By.cssSelector(gridsCss));
		return gridOptionsElements;
	}
	
	public ArrayList<String> getDefaultGridOptions(){
		ArrayList<String> defaultGridOptions = new ArrayList<String>();
		defaultGridOptions.add("Add File(s)");
		defaultGridOptions.add("Drop Bucket");
		defaultGridOptions.add("Statistics");
		return defaultGridOptions;
	}
	
	public void verifyGridOptions(WebElement gridElement) {
		ArrayList<String> expectedGridOptions = getDefaultGridOptions();
		ArrayList<String> actualGridOptions = new ArrayList<String>();
		List<WebElement> actualGrid = getGridOptions(gridElement);
		for(WebElement grid: actualGrid){
			Assert.assertTrue(grid.isDisplayed(), "Element is not displayed");
			if(grid.isDisplayed()){
				System.out.println(grid.getText()+" is present and displayed properly");
			}
			actualGridOptions.add(grid.getText());
		}
		Assert.assertEquals(actualGridOptions, expectedGridOptions);
	}

	public void verifyGridPresentDisplay(WebElement gridElement){
		List<WebElement> gridElements = getGrids();
		if (gridElements.contains(gridElement) && gridElement.isDisplayed())
			System.out.println("Grid is present and Displayed");
	}
	
	public void clickOnGridOption(WebElement gridElement, String action){
		String gridName = gridElement.getAttribute("label");
		String gridsCss = "div#bucketNames	div#"+gridName+"_subMenu>div>ul>li>a";
		List<WebElement> gridOptionsElements = driver.findElements(By.cssSelector(gridsCss));
		for(WebElement gridOption:gridOptionsElements){
			if(gridOption.getText().equalsIgnoreCase(action)){
				gridOption.click();
			}
		}
	}
	
	public void addFile(){
		//String fileName = "file1";
		String gridName = "grid1";
		WebElement gridElement = getGrid(gridName);
		clickOnGridOption(gridElement, "Add Files(s)");
		//Need to finish the code as soon as the requirements are provided.
		
	}
	
	public void Statistics(){
		//Functionality need to be added once the application works properly.
		
	}
}
