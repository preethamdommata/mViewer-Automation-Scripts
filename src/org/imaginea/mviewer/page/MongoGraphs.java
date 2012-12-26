package org.imaginea.mviewer.page;

import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class MongoGraphs extends BaseClass{
WebDriver driver;
	public MongoGraphs(WebDriver driver){
		this.driver = driver;
		
	}
	
	public void verifyGraphsPage(){
		
		Assert.assertTrue(driver.findElement(By.cssSelector("button#animation.bttn")).isDisplayed(),"Stop Animation button is not displayed");
	}
	
public void Controlback(){
	
}

}
