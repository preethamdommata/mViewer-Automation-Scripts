package org.imaginea.mviewer.tests;

import java.util.Collections;
import java.util.List;
import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class DocumentTest extends BaseClass {

	@Test
	public void verifyAddDocumentOp() {
		WebElement collElement = document.getCollElement();
		List<WebElement> beforeAddDocument = document.getDocuments();
		document.addDocument();
		pause(2);
		List<WebElement> AfterAddDocument = document.getDocuments();
		String expConfMess = "New document added successfully to collection '"
				+ collElement.getAttribute("label") + "'";
		String ActConfMess = db.getActConfirmationMess();
		Assert.assertEquals(AfterAddDocument.size(),
				beforeAddDocument.size() + 1);
		Assert.assertEquals(ActConfMess, expConfMess);
		// Need to think about the verification of the confirmation Message.
	}

	@Test
	public void verifyDocumentsDisplay() {
		String collName = document.getCollElement().getAttribute("label");
		collection.clickCollectionsOptionMenu(collName);
		List<WebElement> docElements = document.getDocuments();
		for (WebElement docElement : docElements) {
			WebElement textElement = document
					.getDocumentTextElement(docElement);
			Assert.assertFalse(textElement.isEnabled(),
					"Text Element is not disabled");
		}
	}

	@Test
	public void verifyDocElements() {
		document.addDocument();
		// document = collection.document("collection12");
		String collName = document.getCollElement().getAttribute("label");
		collection.clickCollectionsOptionMenu(collName);
		List<WebElement> docElements = document.getDocuments();
		WebElement docElement = docElements.get(0);
		WebElement editButton = document.getDocumentButton(docElement, "Edit");
		WebElement deleteButton = document.getDocumentButton(docElement,
				"Delete");
		WebElement saveButton = document.getDocumentButton(docElement, "save");
		WebElement cancelButton = document.getDocumentButton(docElement,
				"cancel");
		WebElement textElement = document.getDocumentTextElement(docElement);
		Assert.assertTrue(textElement.isDisplayed(),
				"Document is not Displayed");
		Assert.assertFalse(textElement.isEnabled(), "Document is enabled");
		Assert.assertTrue(editButton.isDisplayed(),
				"Edit button is not displayed or Enabled");
		Assert.assertTrue(deleteButton.isDisplayed(),
				"Delete button is not displayed or Enabled");
		Assert.assertFalse(saveButton.isDisplayed(), "Save button is displayed");
		Assert.assertFalse(cancelButton.isDisplayed(),
				"Cancel button is displayed");
		document.clickDocumentButton(docElement, "Edit");
		Assert.assertTrue(textElement.isDisplayed(),
				"Document is not Displayed");
		Assert.assertTrue(textElement.isEnabled(), "Document is not enabled");
		pause(2);
		Assert.assertTrue(saveButton.isDisplayed(),
				"Save button is not displayed or Enabled");
		Assert.assertTrue(cancelButton.isDisplayed(),
				"Cancel button is not displayed or Enabled");
		Assert.assertFalse(editButton.isDisplayed(),
				"Edit button is displayed or Enabled");
		Assert.assertFalse(deleteButton.isDisplayed(),
				"Delete button is displayed or Enabled");
		// document.clickDocumentButtons(docElement, "Cancel");
	}

	@Test
	public void verifySaveOp() {
		document.addDocument();
		String collName = document.getCollElement().getAttribute("label");
		collection.clickCollectionsOptionMenu(collName);
		String dataEntered = "\"aid\" : 10";
		dataEntered = "," + dataEntered;
		List<WebElement> docElements = document.getDocuments();
		WebElement docElement = docElements.get(0);
		WebElement editButton = document.getDocumentButton(docElement, "Edit");
		Assert.assertTrue(editButton.isDisplayed(),
				"Edit button is not displayed or Enabled");
		WebElement textElement = document.getDocumentTextElement(docElement);
		document.clickDocumentButton(docElement, "Edit");
		WebElement saveButton = document.getDocumentButton(docElement, "Save");
		System.out.println(dataEntered);
		document.setDataDocument(textElement, dataEntered);
		pause(10);
		saveButton.click();
		List<WebElement> docsAfterOp = document.getDocuments();
		String actConfMess = db.getActConfirmationMess();
		String expConfMess = "Document updated successfully.";
		Assert.assertEquals(actConfMess, expConfMess);
		Assert.assertEquals(docsAfterOp.size(), docElements.size());
		// Code to verify the data edited.
	}

	@Test(dependsOnMethods = "verifyDeleteOpNoButtonSelected", alwaysRun = true)
	public void verifyDeleteOp() {
		boolean result = true;
		String collName = document.getCollElement().getAttribute("label");
		collection.clickCollectionsOptionMenu(collName);
		List<WebElement> expectedDocElements = document.getDocuments();
		WebElement docElement = expectedDocElements.get(0);
		WebElement deleteButton = document.getDocumentButton(docElement,
				"Delete");
		Assert.assertTrue(deleteButton.isDisplayed(),
				"Delete button is not displayed or Enabled");
		WebElement textElement = document.getDocumentTextElement(docElement);
		String text = textElement.getText();
		document.clickDocumentButton(docElement, "Delete");
		document.clickDeletePopButton("Yes");
		String expConfMess = "Document deleted successfully.";
		String actConfMess = db.getActConfirmationMess();
		Assert.assertEquals(actConfMess, expConfMess);
		List<WebElement> actualDocElements = document.getDocuments();
		Assert.assertEquals(actualDocElements.size(),
				expectedDocElements.size() - 1);
		for (WebElement document1 : actualDocElements) {
			WebElement textEl = document.getDocumentTextElement(document1);
			String textInElement = textEl.getText();
			if (textInElement.equals(text)) {
				result = false;
				break;
			}
		}
		Assert.assertTrue(result,
				"The Document used for Delete and Yes operation is not deleted successfully");
	}

	@Test
	public void verifyDeleteOpNoButtonSelected() {
		document.addDocument();
		boolean result = false;
		String collName = document.getCollElement().getAttribute("label");
		collection.clickCollectionsOptionMenu(collName);
		List<WebElement> expectedDocElements = document.getDocuments();
		WebElement docElement = expectedDocElements.get(0);
		WebElement deleteButton = document.getDocumentButton(docElement,
				"Delete");
		Assert.assertTrue(deleteButton.isDisplayed(),
				"Delete button is not displayed or Enabled");
		WebElement textElement = document.getDocumentTextElement(docElement);
		String text = textElement.getText();
		document.clickDocumentButton(docElement, "Delete");
		document.clickDeletePopButton("No");
		List<WebElement> actualDocElements = document.getDocuments();
		Assert.assertEquals(actualDocElements.size(),
				expectedDocElements.size());
		for (WebElement document1 : actualDocElements) {
			WebElement textEl = document.getDocumentTextElement(document1);
			String textInElement = textEl.getText();
			if (textInElement.equals(text)) {
				result = true;
				break;
			}
		}
		Assert.assertTrue(result,
				"The document used for Delete and No operation is not found");
	}

	@Test
	public void verifyCancelOp() {
		// document.addDocument();
		String data = "\"aid\" : 15";
		List<WebElement> expectedDocElements = document.getDocuments();
		WebElement docElement = expectedDocElements.get(0);
		document.clickDocumentButton(docElement, "Edit");
		WebElement textElement = document.getDocumentTextElement(docElement);
		String textBeforeOp = textElement.getText();
		WebElement cancelButton = document.getDocumentButton(docElement,
				"Cancel");
		Assert.assertTrue(cancelButton.isDisplayed(),
				"Cancel button is not displayed or Enabled");
		document.setDataDocument(textElement, data);
		document.clickDocumentButton(docElement, "cancel");
		String textAfterOp = textElement.getText();
		List<WebElement> actualDocElements = document.getDocuments();
		Assert.assertEquals(textAfterOp, textBeforeOp);
		Assert.assertEquals(actualDocElements, expectedDocElements);
	}

	@Test
	public void verifyErrMessAddDocJSONFieldBlank() {
		List<WebElement> beforeAddDocument = document.getDocuments();
		document.openDocument();
		pause(2);
		WebElement textElement = driver
				.findElement(By
						.cssSelector("div#addDocDialog_c form[method='POST']>textarea[name='document']"));
		textElement.clear();
		document.clickDocumentPopButton("Submit");
		document.clickDocumentPopButton("Cancel");
		String expErrMess = "Enter the document in valid JSON format";
		String actErrMess = db.getErrorMess();
		Assert.assertEquals(actErrMess, expErrMess);
		List<WebElement> AfterAddDocument = document.getDocuments();
		Assert.assertEquals(AfterAddDocument.size(), beforeAddDocument.size());
	}

	@Test
	public void verifyErrMessAddDocInvalidJSONData() {
		String invalidData = "aid: 5";
		List<WebElement> beforeAddDocument = document.getDocuments();
		document.openDocument();
		document.setDocumentPopData(invalidData);
		document.clickDocumentPopButton("Submit");
		document.clickDocumentPopButton("Cancel");
		String expErrMess = "Enter the document in valid JSON format";
		String actErrMess = db.getErrorMess();
		Assert.assertEquals(actErrMess, expErrMess);
		List<WebElement> AfterAddDocument = document.getDocuments();
		Assert.assertEquals(AfterAddDocument.size(), beforeAddDocument.size());
	}

	@Test
	public void verifyDocumentCancelOption() {
		List<WebElement> beforeAddDocument = document.getDocuments();
		document.openDocument();
		pause(2);
		document.clickDocumentPopButton("Cancel");
		List<WebElement> AfterAddDocument = document.getDocuments();
		Assert.assertEquals(AfterAddDocument.size(), beforeAddDocument.size());
	}

	@Test
	public void verifyDocumentCloseOption() {
		String closeButtonLocator = "div#addDocDialog>a.container-close";
		List<WebElement> beforeAddDocument = document.getDocuments();
		document.openDocument();
		pause(2);
		WebElement closeButton = driver.findElement(By
				.cssSelector(closeButtonLocator));
		closeButton.click();
		List<WebElement> AfterAddDocument = document.getDocuments();
		Assert.assertEquals(AfterAddDocument.size(), beforeAddDocument.size());
	}

	// @Test
	public void verifyNumberOfDocumentsLabel() {
		String collName = document.getCollElement().getAttribute("label");
		collection.clickCollectionsOptionMenu(collName);
		// List<WebElement>lableList = document.getLableList();
		// String label = document.getNumberOfDocumentsLabel(lableList);
		int totalNumberDocuments = document.gettotalNumberOfDocumentsCount();
		System.out.println(totalNumberDocuments);
	}

	// @Test
	public void VerifySelectAllUnselectAllLinks() {

	}

	@Test
	public void verifyMaxPageSizeFunctionality() {
		String collName = document.getCollElement().getAttribute("label");
		collection.clickCollectionsOptionMenu(collName);
		int documentsCount = document.getDocuments().size();
		Assert.assertTrue(documentsCount <= 10,
				"The Documents displayed or more than the MaxPage Size");
		document.setMaxPageSize("25");
		document.clickExecuteQueryButton();
		documentsCount = document.getDocuments().size();
		Assert.assertTrue(documentsCount <= 25,
				"The Documents displayed or more than the MaxPage Size");
		document.setMaxPageSize("50");
		document.clickExecuteQueryButton();
		documentsCount = document.getDocuments().size();
		Assert.assertTrue(documentsCount <= 50,
				"The Documents displayed or more than the MaxPage Size");
	}

	// @Test
	public void VerifySkipRecordsFunctionality() {

	}

	// @Test
	public void verifyNavigationLinks() {

	}

	// @Test
	public void verifySortByFields() {
		String sortFieldValue = "_id:-1";
		String collName = document.getCollElement().getAttribute("label");
		collection.clickCollectionsOptionMenu(collName);
		List<WebElement> docElementsBeforeSorting = document.getDocuments();
		Collections.reverse(docElementsBeforeSorting);
		document.setSortByFields(sortFieldValue);
		document.clickExecuteQueryButton();
		List<WebElement> docElementsAfterSorting = document.getDocuments();
		Assert.assertEquals(docElementsAfterSorting, docElementsBeforeSorting);
	}

	@Test
	public void verifyTreeTab() {
		document.addDocument();
		String collName = document.getCollElement().getAttribute("label");
		collection.clickCollectionsOptionMenu(collName);
		List<WebElement> documentsInJSON = document.getDocuments();
		document.clickTab("Tree Table");
		List<WebElement> documentsInTreeTable = document
				.getDocumentsTreeStructure();
		Assert.assertEquals(documentsInTreeTable.size(), documentsInJSON.size());
	}

	@Test(dependsOnMethods = "verifyDocDeleteNoOpInTreeTable", alwaysRun = true)
	public void verifyDocDeleteYesOpInTreeTable() {
		String collName = document.getCollElement().getAttribute("label");
		collection.clickCollectionsOptionMenu(collName);
		document.clickTab("Tree Table");
		List<WebElement> docsBeforeOp = document.getDocumentsTreeStructure();
		WebElement docElement = document.getDocumentsTreeStructure().get(0);
		String deleteButtonLocator = "td>div>a.delete-icon";
		WebElement deleteButton = docElement.findElement(By
				.cssSelector(deleteButtonLocator));
		deleteButton.click();
		document.clickDeletePopButton("Yes");
		List<WebElement> docsAfterOp = document.getDocumentsTreeStructure();
		String expConfMess = "Document deleted successfully.";
		String actConfMess = db.getActConfirmationMess();
		Assert.assertEquals(actConfMess, expConfMess);
		Assert.assertEquals(docsAfterOp.size(), docsBeforeOp.size() - 1);
	}

	@Test
	public void verifyDocDeleteNoOpInTreeTable() {
		document.addDocument();
		String collName = document.getCollElement().getAttribute("label");
		collection.clickCollectionsOptionMenu(collName);
		document.clickTab("Tree Table");
		List<WebElement> docsBeforeOp = document.getDocumentsTreeStructure();
		WebElement docElement = document.getDocumentsTreeStructure().get(0);
		String deleteButtonLocator = "td>div>a.delete-icon";
		WebElement deleteButton = docElement.findElement(By
				.cssSelector(deleteButtonLocator));
		deleteButton.click();
		document.clickDeletePopButton("No");
		List<WebElement> docsAfterOp = document.getDocumentsTreeStructure();
		Assert.assertEquals(docsAfterOp.size(), docsBeforeOp.size());
	}

	@BeforeMethod
	public void beforeMethod() {
		driver.navigate().refresh();
		collection = db.collection("DataBase1");
		document = collection.document("collection1");
	}

	@BeforeClass
	public void beforeClass() {
		homeLeft = loginPage.loginleft();
		db = homeLeft.dataBase();
	}

	@AfterClass
	public void afterClass() {
		db.dropDB("DataBase1", "Yes");
	}

}
