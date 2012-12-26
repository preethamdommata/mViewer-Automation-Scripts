package org.imaginea.mviewer.tests;

import java.util.List;

import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CollectionTest extends BaseClass {
	@Parameters({ "collectionName" })
	@Test
	// (dependsOnMethods = "verifyErrorMessExistingDBNameUsed", alwaysRun=true)
	public void addNewCollection(String newCollectionName) {
		boolean addCollectionResult = false;
		String dbName = collection.getDBElement().getAttribute("label");
		int colletionsBeforeAdding = collection.getCollections().size();
		addCollectionResult = collection.addCollection(newCollectionName);
		int colletionsAfterAdding = collection.getCollections().size();
		if (addCollectionResult == true) {
			Assert.assertEquals(colletionsAfterAdding,
					colletionsBeforeAdding + 1);
			String ActualConfirmationMessage = getActConfirmationMess();
			String expectedConfirmationMessage = "Collection ["
					+ newCollectionName
					+ "] was successfully added to Database [" + dbName + "].";
			Assert.assertEquals(ActualConfirmationMessage,
					expectedConfirmationMessage);
		}
	}

	@Test
	// (dependsOnMethods = "addNewCollection", alwaysRun = true)
	public void verifyCollectionCancelOp() {
		// db.collection();
		int collectionsBeforeAdding = collection.getCollections().size();
		collection.openCollection();
		pause(2);
		collection.setCollectionName("pramati1");
		collection.clickCollection("Cancel");
		pause(2);
		int collectionsAfterAdding = collection.getCollections().size();
		Assert.assertEquals(collectionsAfterAdding, collectionsBeforeAdding);
	}

	@Test
	// (dependsOnMethods = "verifyCollectionCancelOp", alwaysRun = true)
	public void verifyCollectionCloseOp() {
		// db.collection();
		collection.openCollection();
		collection.clickCollectionClose();
		explicitWait(driver, "a#disconnect.disconnect.navigable");
		String pageTitle = driver.getTitle();
		Assert.assertEquals(pageTitle, "mViewer");
	}

	@Test
	public void verifyErrorMessCollectionNameBlank() {
		// db.collection();
		collection.openCollection();
		collection.clearCollectionName();
		collection.clickCollection("Submit");
		collection.clickCollection("Cancel");
		String actErrMessage = db.getErrorMess();
		String expectedErrMess = "Name should be entered to create a Collection!";
		Assert.assertEquals(actErrMessage, expectedErrMess);
	}

	@Test
	public void verifyDropCollectionOp() {
		String collName = "collection2";
		WebElement dbElement = collection.getDBElement();
		// collection = db.collection("Preetham8");
		addNewCollection(collName);
		WebElement collElement = collection.getCollection(collName);
		// String collectionName = collElement.getAttribute("label");
		// if(collectionName.equalsIgnoreCase("collection17"))
		// {
		// collElement = collection.getCollections().get(1);
		// collectionName = collElement.getAttribute("label");
		// }
		// WebElement collectionElement =
		// collection.getCollection(collectionName);
		// db.collection();
		pause(2);
		List<WebElement> collectionsBeforeOp = collection.getCollections();
		collection.clickCollectionsOptionMenu(collName);
		collection.clickDropColletion(collName);
		collection.clickDropCollectionButton("Yes");
		pause(2);
		String actConfMess = collection.getActConfirmationMess();
		String expConfMess = "Collection [" + collName
				+ "] was successfully deleted from Database ["
				+ dbElement.getAttribute("label") + "].";
		Assert.assertEquals(actConfMess, expConfMess);
		pause(2);
		List<WebElement> collectionsAfterOp = collection.getCollections();
		Assert.assertEquals(collectionsAfterOp.size(),
				collectionsBeforeOp.size() - 1);
		Assert.assertFalse(collectionsAfterOp.contains(collElement),
				"Collection is not deleted successfully");
	}

	@Test
	public void verifyDropCollectionNoButton() {
		addNewCollection("collection7");
		pause(2);
		WebElement collElement = collection.getCollections().get(0);
		String collectionName = collElement.getAttribute("label");
		// collection.clickOnCollection(collectionName);
		WebElement collectionElement = collection.getCollection(collectionName);
		pause(2);
		List<WebElement> collectionsBeforeOp = collection.getCollections();
		collection.clickCollectionsOptionMenu(collectionName);
		collection.clickDropColletion(collectionName);
		collection.clickDropCollectionButton("No");
		pause(2);
		List<WebElement> collectionsAfterOp = collection.getCollections();
		Assert.assertEquals(collectionsAfterOp.size(),
				collectionsBeforeOp.size());
		Assert.assertTrue(collectionsAfterOp.contains(collectionElement),
				"Collection has been deleted");
	}

	@Test(dependsOnMethods = "addNewCollection", alwaysRun = true)
	public void verifyMessExistingCollectionNameUsed() {
		// db.collection();
		String collectionName = collection.getCollectionsNameList().get(0);
		collection.openCollection();
		collection.clearCollectionName();
		collection.setCollectionName(collectionName);
		collection.clickCollection("Submit");
		String actErrMessage = db.getErrorMess();
		String expectedErrMess = "Could not add Collection! A collection with the given name already exists ! Try another name.";
		Assert.assertEquals(actErrMessage, expectedErrMess);
	}

	@Parameters({ "CappedcollectionName" })
	@Test
	public void addNewCappedCollection(String newCollectionName) {
		// String newCollectionName = "collection57";
		// db.collection("Preetham8");
		String dbName = collection.getDBElement().getAttribute("label");
		int colletionsBeforeAdding = collection.getCollections().size();
		boolean addNewCollection = collection.addCollection(newCollectionName,
				true, "1000");
		pause(5);
		int colletionsAfterAdding = collection.getCollections().size();
		if (addNewCollection == true) {
			Assert.assertEquals(colletionsAfterAdding,
					colletionsBeforeAdding + 1);
			if (colletionsAfterAdding == colletionsBeforeAdding + 1) {
				String ActualConfirmationMessage = getActConfirmationMess();
				String expectedConfirmationMessage = "Collection ["
						+ newCollectionName
						+ "] was successfully added to Database [" + dbName
						+ "].";
				Assert.assertEquals(ActualConfirmationMessage,
						expectedConfirmationMessage);
			}
		}
		// need to add steps to check whether the Collection is capped
	}

	@Test
	public void convertCollectionToCapped() {
		// collection.clickCollectionsOptionMenu(collectionName);
		WebElement dbElement = collection.getDBElement();
		String collectionName = "collection4";
		addNewCollection(collectionName);
		List<WebElement> collsBeforeOp = collection.getCollections();
		db.clickDBMenu(dbElement);
		collection.clickCollectionsOptionMenu(collectionName);
		collection.clickUpdateColletion(collectionName);
		explicitWait(driver, "#isCapped");
		pause(2);
		collection.setCollectionCapped("1000");
		collection.clickCollection("submit");
		List<WebElement> collsAfterOp = collection.getCollections();
		String actConfirMess = db.getActConfirmationMess();
		String expConfirMess = "Collection [" + collectionName
				+ "] was successfully converted to capped collection";
		Assert.assertEquals(actConfirMess, expConfirMess);
		Assert.assertEquals(collsAfterOp.size(), collsBeforeOp.size());
		// need to add steps to check whether the Collection is capped
	}

	@Test
	public void covertCappedToCollection() {
		// Need to write code
		WebElement dbElement = collection.getDBElement();
		String collectionName = "collection5";
		addNewCappedCollection(collectionName);
		List<WebElement> collsBeforeOp = collection.getCollections();
		db.clickDBMenu(dbElement);
		collection.clickCollectionsOptionMenu(collectionName);
		collection.clickUpdateColletion(collectionName);
		pause(2);
		driver.findElement(By.id("isCapped")).click();
		collection.clickCollection("submit");
		List<WebElement> collsAfterOp = collection.getCollections();
		String expConfMess = "Capped Collection [" + collectionName
				+ "] was successfully converted to normal collection";
		String actConfMess = db.getActConfirmationMess();
		Assert.assertEquals(actConfMess, expConfMess);
		Assert.assertEquals(collsAfterOp.size(), collsBeforeOp.size());
		// need to add steps to check whether the Collection is normal

	}

	@Test
	public void verifyErrMessMaxSizeBlank() {
		String collectionName = "collection6";
		collection.addCollection(collectionName, true, "");
		String actErrMessge = db.getErrorMess();
		String expErrMessage = "Size should be entered to create a Capped Collection!";
		Assert.assertEquals(actErrMessge, expErrMessage);
	}

	@Test
	public void verifyErrMessInvalidDataMaxSizeField() {
		String collectionName = "collection6";
		String invalidData = "abcd";
		collection.addCollection(collectionName, true, invalidData);
		pause(2);
		String actErrMessge = db.getErrorMess();
		String expErrMessage = "Collection creation failed!";
		Assert.assertEquals(actErrMessge, expErrMessage);
	}

	@Test
	public void verifyCollStatistics() {
		String collectionName = collection.getCollections().get(0)
				.getAttribute("label");
		collection.clickCollectionsOptionMenu(collectionName);
		stat = collection.clickStatistics(collectionName);
		stat.verifyStatistics();
	}

	@BeforeMethod
	public void beforeMethod() {
		driver.navigate().refresh();
		collection = db.collection("DataBase2");
	}

	@BeforeClass
	public void beforeClass() {
		homeLeft = loginPage.loginleft();
		db = homeLeft.dataBase();
		collection = db.collection("DataBase2");

	}

	@AfterClass
	public void afterClass() {
		db.dropDB("DataBase2", "Yes");
	}

}
