package org.imaginea.mviewer.page;

import java.util.List;
import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

/**
 * @author preethamd
 *
 */
/**
 * @author preethamd
 *
 */
public class Document extends BaseClass {
	WebElement collElement;
	
	public Document(WebDriver driver, WebElement collElement){
		this.driver = driver;
		this.collElement = collElement;
	}
	
	/**Returns Collection WebElement that is selected.
	 * @return
	 */
	public WebElement getCollElement(){
		return collElement;
	}
	
	/**Adds the Document with no value, for a selected Collection.
	 * 
	 */
	public void addDocument(){
		openDocument();
		Assert.assertTrue(driver.findElement(By.cssSelector("div#addDocDialog_c div#addDocDialog>div#addDocDialog_h.hd")).isDisplayed(), "The Add Document popup is not displayed");
		setDocumentPopData("");
		clickDocumentPopButton("Submit");
	}
	
	/**Clicks on Add Document option present in Collections Option Menu.
	 * Add Document pop up will be displayed.
	 * 
	 */
	public void openDocument(){
		String collName = collElement.getAttribute("label");
		String openDocCss = "div#"+collName+"_subMenu div.yui3-menu-content ul.first-of-type>li>a[index='1']";
		driver.findElement(By.cssSelector(openDocCss)).click();
	}
	
	/**Sets data into JSON field present on Add Document pop up.
	 * @param data - Data that need to be entered into the JSON field
	 */
	public void setDocumentPopData(String data){
		WebElement textElement = driver.findElement(By.cssSelector("div#addDocDialog_c form[method='POST']>textarea[name='document']"));
		String text = textElement.getText();
		StringBuffer str = new StringBuffer(text);
		str.insert(text.length()-1, data);
		textElement.clear();
		textElement.sendKeys(str);
		//textElement.sendKeys(data);
	}
	
	/**Clicks on the "Submit" or "Cancel" buttons present on the Add Document pop up.
	 * @param action - ""Submit" or "Cancel"
	 */
	public void clickDocumentPopButton(String action){
		String buttonsLocator = "div#addDocDialog_c div#addDocDialog span.first-child>button";
		List<WebElement>buttons = driver.findElements(By.cssSelector(buttonsLocator));
		for(WebElement button:buttons){
			if(button.getText().equalsIgnoreCase(action)){
			button.click();
		break;
			}
		}
	}
	
	/**Clicks on the Close button present on the Add Document pop up.
	 * 
	 */
	public void clickPopCloseButton(){
		String closeButtonLocator = "div#addDocDialog_c div#addDocDialog>a.container-close";
		WebElement closeElement = driver.findElement(By.cssSelector(closeButtonLocator));
		closeElement.click();
	}
	
	/**Returns all the Document provided for a selected Document WebElement.
	 * @param docElement - Selected Document WebElement
	 * @return - List of Buttons WebElements
	 */
	public List<WebElement> getDocumentButtons(WebElement docElement){
		String buttonsLocator = "div.actionsDiv>button";
		List<WebElement>buttons = docElement.findElements(By.cssSelector(buttonsLocator));
		return buttons;
	}
	
	/**Clicks on Selected button, which are provided against each Document WebElement.
	 * Each Document is provided with "Edit", "Delete", "Save" and "Cancel" buttons.
	 * @param docElement - Selected Document WebElement for which the Button need to be selected. 
	 * @param action - "Edit", "Delete", "Save" and "Cancel" actions on the selected Document.
	 */
	public void clickDocumentButton(WebElement docElement, String action){
	WebElement button = getDocumentButton(docElement, action);
	button.click();
	}
	
	/**Clicks on actions buttons present on the Document Drop Confirmation Pop Up.
	 * This pop up has "Yes" and "No" buttons. "Yes" to deleted the selected Document, "No" to not deleted the Document. 
	 * @param buttonName - "Yes" or "No"
	 */
	public void clickDeletePopButton(String buttonName){
		WebElement returnElement=null;
		String buttonsLocators = "div#simpledialog_c div#simpledialog span.button-group>span>span>button";
		List<WebElement>buttons = driver.findElements(By.cssSelector(buttonsLocators));
		for(WebElement button: buttons){
			if(button.getText().equalsIgnoreCase(buttonName)){
				returnElement = button;
				break;
			}
		}
		returnElement.click();
	}
	
	/**Returns selected button for a selected Document WebElement. 
	 * Each Document is provided with "Edit", "Delete", "Save" and "Cancel" buttons.
	 * 
	 * @param docElement - Document WebElement for which button returned 
	 * @param buttonName - Button Name that is required ("Edit", "Delete", "Save" and "Cancel")
	 * @return - Button WebElement
	 */
	public WebElement getDocumentButton(WebElement docElement, String buttonName){
		buttonName = buttonName+"0";
		WebElement returnElement=null;
		List<WebElement> buttons = getDocumentButtons(docElement);
		for (WebElement button : buttons) {
			String buttonid = button.getAttribute("id");
			if (buttonid.equalsIgnoreCase(buttonName)) {
				returnElement = button;
				break;
			}
		}
		return returnElement;
	}
	
	/**Provides Text WebElement for a selected Document WebElement.
	 * @param docElement - Document WebElement
	 * @return - Text WebElement
	 */
	public WebElement getDocumentTextElement(WebElement docElement){
		String textFieldLoctor = "div.textAreaDiv>pre>textarea";
		WebElement textElement = docElement.findElement(By.cssSelector(textFieldLoctor));
		return textElement;
	}
	
	/**provides List of Documents WebElements present in that collection
	 * @return - List of WebElements
	 */
	public List<WebElement> getDocuments(){
		String elementLocator = "div.wrapper div#jsonBuffer.buffer.jsonBuffer.navigable.navigateTable>div.docDiv";
		List<WebElement> documents = driver.findElements(By.cssSelector(elementLocator));
		return documents;
	}
	
	/**Sets Data in Document in the Text Element after the text WebElement is selected into Edit Mode.
	 * @param textElement - Document text WebElement
	 * @param data - Data that need to be passed.
	 */
	public void setDataDocument(WebElement textElement, String data){
		String defaultString = textElement.getText();
		StringBuffer str = new StringBuffer(defaultString);
		str.insert(defaultString.length()-1, data);
		textElement.clear();
		textElement.sendKeys(str);
	}
	
	/**Clicks on Tab according to the TabName provided
	 * @param tabName - "JSON", "Tree Table"
	 */
	public void clickTab(String tabName){
		List<WebElement> tabs = driver.findElements(By.cssSelector("ul.yui-nav>li>a>em"));
		for(WebElement tab: tabs){
			String tabNa = tab.getText();
			if(tabNa.equalsIgnoreCase(tabName)){
				tab.click();
				break;
			}
		}
	}
	
	/**Sets the Sort by field with sorting value
	 * @param sortFieldValue - Sorting value
	 */
	public void setSortByFields(String sortFieldValue){
		WebElement sortField = driver.findElement(By.name("sort"));
		sortField.clear();
		sortField.sendKeys(sortFieldValue);
	}
	
	/**Returns the List of Documents found in Tree structure.
	 * @return - List<WebElement> of Documents
	 */
	public List<WebElement> getDocumentsTreeStructure(){
		List<WebElement> documents = driver.findElements(By.cssSelector("div#treeTable tbody.yui-dt-data>tr"));
		return documents;
	}
	
	/**Clicks on Execute Query Button.
	 * 
	 */
	public void clickExecuteQueryButton(){
		WebElement executeQueryButton = driver.findElement(By.id("execQueryButton"));
		executeQueryButton.click();
	}
	
	/**Returns the Number of Documents Label present in the Query Executor Page
	 * @param lableList - The List of the Label
	 * @return - Number of Documents Label. 
	 */
	public String getNumberOfDocumentsLabel(List<WebElement>lableList){
		String finalLabel = "";
		for(WebElement label:lableList){
			finalLabel = finalLabel+label.getText()+" ";
		}
		finalLabel = finalLabel.trim();
		return finalLabel;
	}
	
	/**Returns WebElement of Selected Navigation Link
	 * @param linkName - Selected Link Name
	 * @return - Selected Link WebElement
	 */
	public WebElement getNavigationLink(String linkName){
		String idLocator=null;
		linkName = linkName.toLowerCase();
		switch(linkName){
		case "next": idLocator = "next";
		break;
		case "last": idLocator = "last";
		break;
		case "first": idLocator = "first";
		break;
		case "previous": idLocator = "prev";
		break;
		}
		return driver.findElement(By.id(idLocator));
	}
	
	/**Returns Total Number of Documents for a particular Collection
	 * @return - int - Number of Documents present for a collection
	 */
	public int gettotalNumberOfDocumentsCount(){
		WebElement next = getNavigationLink("Next");
		String active = next.getAttribute("href");
		int totalDocs = getDocuments().size();
		while(active!=null){
			next.click();
			pause(2);
			totalDocs = totalDocs+getDocuments().size();
			active = next.getAttribute("href");
		}
		return totalDocs;
	}
	
	/**Returns the Number of Documents Label present on the Page
	 * @return - Label present on the page.
	 */
	public List<WebElement> getLableList(){
		String labelLocator = "div#paginator>label";
		List<WebElement> showLabel = driver.findElements(By.cssSelector(labelLocator));
		return showLabel;
	}
	
	/**Sets Max Page Size page field with Selected Value
	 * @param pageSize - Number of Documents to be displayed(10, 25, 50)
	 */
	public void setMaxPageSize(String pageSize){
		WebElement maxPageSizeElement = driver.findElement(By.id("limit"));
		maxPageSizeElement.click();
		maxPageSizeElement.sendKeys(pageSize);
		maxPageSizeElement.sendKeys(Keys.RETURN);
	}
}
