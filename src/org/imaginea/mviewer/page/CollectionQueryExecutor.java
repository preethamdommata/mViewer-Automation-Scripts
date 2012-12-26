package org.imaginea.mviewer.page;

import java.util.ArrayList;

import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CollectionQueryExecutor extends BaseClass {
	ArrayList<String> collList;
	WebElement dbElement;
	
	public CollectionQueryExecutor(WebDriver driver, WebElement dbElement) {
		this.driver = driver;
		this.dbElement = dbElement;
	}
	
	
	public void SetDataInQueryBox(String queryCommand){
		WebElement queryBox = driver.findElement(By.id("queryBox"));
		queryBox.clear();
		queryBox.sendKeys(queryCommand);
	}
	
	
}
