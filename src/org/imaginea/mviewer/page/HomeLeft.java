package org.imaginea.mviewer.page;

import java.util.ArrayList;
import java.util.List;
import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
/**
 * Contains methods related to HomeLeft page
 * @author preethamd
 *
 */

public class HomeLeft extends BaseClass {
	ArrayList<String> collList;

	public HomeLeft(WebDriver driver) {
		this.driver = driver;
	}

	public void verifyCenterContentImage() {
		String imageLocator = "div.wrapper div#resultBuffer.buffer.resultBuffer>div#mainBody>center>img[title='mViewer Logo']";
		WebElement imageElement = driver.findElement(By
				.cssSelector(imageLocator));
		Assert.assertTrue(imageElement.isDisplayed(),
				"mViewer image is not Displayed");
	}

	public ArrayList<String> getCenterContentText() {
		String textLocator = "div.wrapper div#resultBuffer.buffer.resultBuffer>div#mainBody>center";
		List<WebElement> textElements = driver.findElements(By
				.cssSelector(textLocator));
		ArrayList<String> textList = new ArrayList<String>();
		for (WebElement textElement : textElements) {
			textList.add(textElement.getText());
		}
		return textList;
	}

	public ArrayList<String> actualTextContent() {
		ArrayList<String> actualContent = new ArrayList<String>();
		actualContent.add("a mongoDB management Tool" + "\n"
				+ "Pramati Technologies" +"\n"+"\n"+"\n"
				+ "Please click on the database name to start working");
		return actualContent;
	}

	public DataBase dataBase() {
		pause(3);
		return new DataBase(driver);
	}
}
