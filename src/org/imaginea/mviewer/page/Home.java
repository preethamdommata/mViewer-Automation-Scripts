package org.imaginea.mviewer.page;

import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class Home extends BaseClass {
	// ********************************************************************************
	public Home(WebDriver driver) {
		this.driver = driver;
		//reader = docFinder();
	}

	// ********************************************************************************
	public void checkAllElementsDisplay(String elementLocator) {
		super.checkAllElementsDisplay(elementLocator);
	}

	// ********************************************************************************
	public void verifyDefaultElementValues() {
		String hostLable = "127.0.0.1";
		String portLable = "27017";
		String userLable = "Guest";

		Assert.assertEquals(
				driver.findElement(
						By.cssSelector(getLocator("hostLableLocator")))
						.getText(), hostLable + ":" + portLable);
		Assert.assertEquals(
				driver.findElement(
						By.cssSelector(getLocator("userLableLocator")))
						.getText(), userLable);
	}

	// *********************************************************************************
	public ShowConsole showConsole() {
		driver.findElement(By.cssSelector("button#console.bttn.navigable"))
				.click();
		driver.switchTo().frame(
				driver.findElement(By.cssSelector("#_yuiResizeMonitor")));
		return new ShowConsole(driver);
	}
}
