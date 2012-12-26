package org.imaginea.mviewer.page;

import java.util.Iterator;
import java.util.Set;

import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Contains methods related to mViewer Login Page. Login Page class is provided
 * with Methods to support actions on Login page and even to handle the control
 * from this page to other pages.
 * 
 * @author preethamd
 * 
 */
public class Login{

	BaseClass base = new BaseClass();
	WebDriver driver;
	
	public Login(WebDriver driver) {
		this.driver = driver;
		//base.driver = this.driver;
		
		/*
		 * if (!(driver.getTitle().equals("Login"))) {
		 * System.out.println("This is not Login Page"); }
		 */
	}
	
	//public void checkAllElementsDisplay(String elementLocator,
	//		String elementValue) {
	//	super.checkAllElementsDisplay(elementLocator, elementValue);
	//}

	/**
	 * Returns Login page control to Home Page.
	 * 
	 * @return Login page WebDriver
	 */
	public Home login() {
		driver.findElement(By.cssSelector(base.getLocator("connectButtonLocator")))
				.click();
		return new Home(driver);
	}

	/**
	 * Returns Login page control to HomeLeft Page.
	 * 
	 * @return Login Page WebDriver
	 */
	public HomeLeft loginleft() {
		driver.findElement(By.cssSelector(base.getLocator("connectButtonLocator")))
				.click();
		return new HomeLeft(driver);
	}

	/**
	 * Returns Login Page control to DataBase operations.
	 * 
	 * @return Login Page WebDriver
	 */
	public DataBase dataBase() {
		driver.findElement(By.cssSelector(base.getLocator("connectButtonLocator")))
				.click();
		base.pause(2);
		return new DataBase(driver);
	}

	/**
	 * Returns Error Message displayed on Login Screen
	 * 
	 * @return Error message(String)
	 */
	public String errorMessage() {
		return driver.findElement(By.cssSelector("div#errorMsg.notify"))
				.getText();
	}

	
	/** Method clears All the fields present on the Login Page.
	 * Fields include: Host, Port, Username, Password.
	 * 
	 */
	public void fieldsBlank() {
		driver.findElement(By.cssSelector(base.getLocator("hostLocator"))).clear();
		driver.findElement(By.cssSelector(base.getLocator("portLocator"))).clear();
		driver.findElement(By.cssSelector(base.getLocator("userNameLocator")))
				.clear();
		driver.findElement(By.cssSelector(base.getLocator("passwordLocator")))
				.clear();
	}

	/**Method provides the option to switch from the Main window to Page created in new Window.
	 *  
	 * @param parentWindowHandle - Main Window Handle
	 * @param newWindowTitle - Title of new page opened in a separate window. 
	 */
	public void handleMultipleWindows(String parentWindowHandle,
			String newWindowTitle) {
		Set<String> windows = driver.getWindowHandles();
		Iterator<String> windowIterator = windows.iterator();
		while (windowIterator.hasNext()) {
			String windowHandle = windowIterator.next();
			driver.switchTo().window(windowHandle);
			if (driver.getTitle().equals(newWindowTitle)) {
				break;
			}
		}
	}

	/**Method moves the control from Page opened in New window back to Main Window by closing the New Window.
	 * @param parentWindowHandle - Main Window Handle
	 */
	public void controlBackToMainWindow(String parentWindowHandle) {
		driver.close();
		driver.switchTo().window(parentWindowHandle);
	}

	//public void disconnect() {
	//	loginPage = disconnect(driver);
	//}
}
