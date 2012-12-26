package org.imaginea.mviewer.page;

import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class Statistics extends BaseClass {
	
	WebElement dbCollElement;
	
	public Statistics(WebDriver driver, WebElement dbCollElement) {
		this.driver = driver;
		this.dbCollElement = dbCollElement;
	}
	
public void verifyStatistics(){
	pause(2);
	WebElement headerElement = driver.findElement(By.cssSelector("div.wrapper div.right-cont.gen-float>div#mainBodyHeader.tab-cont"));
	String actualPageHeadingName = headerElement.getText();
	String expectedPageHeadingName = "Statistics : "+dbCollElement.getAttribute("label");
	Assert.assertEquals(actualPageHeadingName, expectedPageHeadingName);
}
}
