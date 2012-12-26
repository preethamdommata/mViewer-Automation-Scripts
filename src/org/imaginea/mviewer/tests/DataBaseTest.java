package org.imaginea.mviewer.tests;

import java.util.ArrayList;
import java.util.List;

import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class DataBaseTest extends BaseClass{
	String dbName;
	
	@Parameters({"newDBName"})
	@Test(alwaysRun=true)
	public void createDB(String dbName) {
		db.createDB(dbName);
		explicitWait(driver, "a#disconnect.disconnect.navigable");
		WebElement newDB = db.getdbElement(dbName);
		db.verifyDBPresentDisplay(newDB);
		db.dbOptionsCheck(newDB);
	}

	@Test(dependsOnMethods = "createDB",alwaysRun=true)
	public void verifyOptionNewDB(){
		String dbName = "DataBase1";
		WebElement newDB = db.getdbElement(dbName);
		db.dbOptionsCheck(newDB);
	}
	
	@Test(dependsOnMethods = "verifyOptionNewDB")
	public void checkdbOptionsallDBs() {
		// Checking the DB options for all the dbs created till now.
		List<WebElement> dbElements = dbElementsList();
		for(WebElement element:dbElements ){
			db.dbOptionsCheck(element);
		}
	}
	
	@Test(dependsOnMethods = "checkdbOptionsallDBs")
	public void verifyErrorMsgDBNameBlank(){
		//verify error message when the DB Name field is left blank and clicked on submit button.
		db.openDB();
		db.clearDBName();
		db.clickNewDBSubmit();
		db.clickNewDBCancel();
		String actErrorMess = db.getErrorMess();
		Assert.assertEquals(actErrorMess, "Enter the database name!");
	}
	
	@Test(dependsOnMethods = "verifyErrorMsgDBNameBlank")
	public void verifyErrorMsgInvalidDataDBName(){
		//verify error message when the DB Name field is left blank and clicked on submit button.
		String invalidData = "*&^%";
		db.openDB();
		db.clearDBName();
		db.setDBName(invalidData);
		explicitWait(driver, "button#yui-gen0-button");
		db.clickNewDBSubmit();
		//db.clickNewDBCancel();
		String actErrorMess = db.getErrorMess();
		Assert.assertEquals(actErrorMess, "DB creation failed ! Invalid ns ["+invalidData+".system.namespaces].");
	}
	
	@Test(dependsOnMethods = "verifyErrorMsgInvalidDataDBName")
	public void verifyDBCancelOp(){
		ArrayList<String> beforeCancelOperation = db.getDBNames();
		db.openDB();
		db.clearDBName();
		db.setDBName("pramati");
		db.clickNewDBCancel();
		ArrayList<String> afterCancelOperation = db.getDBNames();
		Assert.assertEquals(afterCancelOperation, beforeCancelOperation);
	}
	
	@Test(dependsOnMethods = "verifyDBCancelOp")
	public void verifyDBCloseOp(){
		pause(2);
		db.openDB();
		db.clickNewDBClose();
		pause(2);
		String pageTitle = driver.getTitle();
		Assert.assertEquals(pageTitle, "mViewer");
	}
	
	@Test(dependsOnMethods = "verifyDBCloseOp")
	public void verifyErrorMessExistingDBNameUsed(){
		//db.openDB();
		ArrayList<String> exisitingDBName = db.getDBNames();
		db.openDB();
		db.setDBName(exisitingDBName.get(0));
		db.clickNewDBSubmit();
		String actErrMess = db.getErrorMess();
		Assert.assertEquals(actErrMess, "DB creation failed ! DB with name '"+exisitingDBName.get(0)+"' ALREADY EXISTS.");
	}
	
	@Test(dependsOnMethods = "verifyErrorMessExistingDBNameUsed")
	public void dropDBOpNoButton(){
		String dbName = db.getDBNames().get(0);
		//WebElement dbElement = db.getdbElement(dbName);
		List<WebElement> dbsBeforeOp = db.getDBs();
		db.dropDB(dbName, "No");
		pause(1);
		List<WebElement> dbsAfterOp = db.getDBs();
		Assert.assertEquals(dbsAfterOp.size(), dbsBeforeOp.size());
		Assert.assertEquals(dbsAfterOp, dbsBeforeOp);
	}
	
	@Test(dependsOnMethods = "dropDBOpNoButton")
	public void verifyStatics(){
		//createDB("DataBase1");
		stat = db.clickDBStatistics("DataBase1");
		stat.verifyStatistics();
	}
	
	@Test(dependsOnMethods = "verifyStatics")
	public void verifyStaticsByClickingOnDB(){
		WebElement dbElement = db.getDBs().get(0);
		stat = db.clickDBStatistics(dbElement);
		stat.verifyStatistics();
		
	}
	@Test(dependsOnMethods = "verifyStaticsByClickingOnDB")
	public void dropDBOp(){
		String dbName = "DataBase1";
		//String dbName = db.getDBNames().get(1);
		//WebElement dbElement = db.getdbElement(dbName);
		List<WebElement> dbsBeforeOp = db.getDBs();
		int dbSizeAfterOp = dbsBeforeOp.size();
		dbSizeAfterOp = dbSizeAfterOp-1;
		db.dropDB(dbName, "Yes");
		pause(2);
		List<WebElement> dbsAfterOp = db.getDBs();
		List<String> dbNamesAfterOp = db.getDBNames();
		Assert.assertEquals(dbsAfterOp.size(), dbSizeAfterOp);
		Assert.assertFalse(dbNamesAfterOp.contains(dbName), "Db didnt get deleted");
		
	}
	
	@BeforeMethod
	public void beforeMethod(){
		driver.navigate().refresh();
	}
	
	
	@BeforeClass
	public void beforeClass() {
		db = loginPage.dataBase();
		//stat = db.clickStatistics();
		//grid = db.gridFS();
	}
	
}
