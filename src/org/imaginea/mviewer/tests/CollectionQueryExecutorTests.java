package org.imaginea.mviewer.tests;

import java.util.List;

import org.imaginea.mviewer.common.BaseClass;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class CollectionQueryExecutorTests extends BaseClass {

	@Parameters({ "queryCommand" }) 
	@Test
	public void verifyInsertCommand(String command){
		WebElement collElement = document.getCollElement();
		List<WebElement> documentsBeforeOp = document.getDocuments();
		qe.SetDataInQueryBox(command);
		document.clickExecuteQueryButton();
		collection.clickOnCollection(collElement.getAttribute("label"));
		List<WebElement> documentsAfterOp = document.getDocuments();
		Assert.assertEquals(documentsBeforeOp.size()+1, documentsAfterOp.size());
	}
	@Parameters({"queryCommandofAnotherCollection", "otherCollectionName"})
	@Test
	public void verifyInsertCommandOtherCollection(String command, String otherCollName){
		//String otherCollName = "coll";
		WebElement mainCollElement = document.getCollElement();
		//WebElement collElement = collection.getCollection(otherCollName);
		collection.clickOnCollection(otherCollName);
		List<WebElement> documentsBeforeOp = document.getDocuments();
		collection.clickOnCollection(mainCollElement.getAttribute("label"));
		qe.SetDataInQueryBox(command);
		document.clickExecuteQueryButton();
		collection.clickOnCollection(otherCollName);
		List<WebElement> documentsAfterOp = document.getDocuments();
		Assert.assertEquals(documentsBeforeOp.size()+1, documentsAfterOp.size());
	}
	
	
	
	@BeforeMethod
	public void beforeMethod(){
		driver.navigate().refresh();
		collection = db.collection("DataBase1");
		document = collection.document("collection1");
		qe = collection.collectionQueryExecutor("collection1");
	}
	
	@BeforeClass
	public void beforeClass() {
		homeLeft = loginPage.loginleft();
		db = homeLeft.dataBase();
		
		
	}
}
