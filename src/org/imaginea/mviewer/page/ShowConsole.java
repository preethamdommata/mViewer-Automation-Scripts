package org.imaginea.mviewer.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class ShowConsole {
	WebDriver driver;
	public ShowConsole(WebDriver driver){
		this.driver=driver;
	}
	
	public void verifyConsole(){
		WebElement consoleHeader = driver.findElement(By.cssSelector("div.yui3-console-hd>h4.yui3-console-title"));
		Assert.assertTrue(consoleHeader.isDisplayed(), "Console is not displayed");
	}
}
