package org.imaginea.mviewer.tests;

import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseClass {

//	mViewerTestDataClass testData = new mViewerTestDataClass();

/*	@DataProvider(name = "elementLocatorValues")
	public Object[][] elementdata() {
		return testData.loginPageelementLocatorValues();
	}

	@DataProvider(name = "elementLocatorValuesHome")
	public Object[][] elementdataHome() {
		return testData.loginPageelementLocatorValues1();
	}
*/
	@AfterMethod
	public void afterMethod(){
		pause(2);
		String pageTitle = driver.getTitle();
		if(pageTitle.equalsIgnoreCase("Login")){
		driver.navigate().refresh();}
		else if(pageTitle.equalsIgnoreCase("mViewer")){
			driver.findElement(By.cssSelector("a#disconnect.disconnect.navigable")).click();
		}
	}
	
	@Test()
	public void loginmViewer() {
		// checking the Login functionality with Default values
		System.out.println("Started checking the login functionality");
		homePage = loginPage.login();
		Assert.assertTrue((driver.findElement(By
				.cssSelector(getLocator("homeLocator")))).isDisplayed(),
				"Login is not successful");
		System.out.println("Login is successful");
	}

	@Test
	public void verifyErrorMessageFieldsBlank(){
		loginPage.fieldsBlank();
		driver.findElement(By.cssSelector(getLocator("connectButtonLocator"))).click();
		pause(2);
		String actualErrorMess = loginPage.errorMessage();
		Assert.assertEquals(actualErrorMess, "Please fill in all the login fields !");
	}
	
	@Test
	public void verifyErrorMessageIncorrectValues(){
		loginPage.fieldsBlank();
		driver.findElement(By.cssSelector(getLocator("hostLocator"))).sendKeys("9030303");
		driver.findElement(By.cssSelector(getLocator("portLocator"))).sendKeys("9030303");
		driver.findElement(By.cssSelector(getLocator("connectButtonLocator"))).click();
		pause(2);
		String actErrorMessage = loginPage.errorMessage();
		Assert.assertEquals(actErrorMessage, "You have entered an invalid input data !");
	}
	
	@Test
	public void verifyErrorMessInvalidHostvalue(){
		driver.findElement(By.cssSelector(getLocator("hostLocator"))).clear();
		driver.findElement(By.cssSelector(getLocator("hostLocator"))).sendKeys("9030303");
		driver.findElement(By.cssSelector(getLocator("connectButtonLocator"))).click();
		pause(2);
		String actErrorMess = loginPage.errorMessage();
		Assert.assertEquals(actErrorMess, "Connection Failed ! Please check if MongoDB is running at the given host and port !");
	}
	
	@Test
	public void verifyErrorMessInvalidPortValue(){
		driver.findElement(By.cssSelector(getLocator("portLocator"))).clear();
		driver.findElement(By.cssSelector(getLocator("portLocator"))).sendKeys("safsdffs");
		driver.findElement(By.cssSelector(getLocator("connectButtonLocator"))).click();
		pause(2);
		String actErrorMess = loginPage.errorMessage();
		Assert.assertEquals(actErrorMess, "You have entered an invalid port number !");
	}
	
	@Test
	public void verifyHelpLink(){
		String expHelpUrl = "http://imaginea.github.com/mViewer/0.9/";
		String parentWindowHandle = driver.getWindowHandle();
	      driver.findElement(By.cssSelector("div#helpDiv.loginHelp>a[href='http://imaginea.github.com/mViewer/']")).click();
	    loginPage.handleMultipleWindows(parentWindowHandle, "mViewer by Imaginea @ GitHub");
		Assert.assertEquals(driver.getCurrentUrl(), expHelpUrl);
		loginPage.controlBackToMainWindow(parentWindowHandle);
	}
	
	@Test
	public void verifyByImagineaLink(){
		String expImagineaUrl = "http://www.imaginea.com/";
		String parentWindowHandle = driver.getWindowHandle();
		driver.findElement(By.cssSelector("div.imaginea>a[href='http://www.imaginea.com/']")).click();
		loginPage.handleMultipleWindows(parentWindowHandle, "Imaginea: Experience Design and Engineering in Cloud, Mobile, Social Products");
		Assert.assertEquals(driver.getCurrentUrl(), expImagineaUrl);
		loginPage.controlBackToMainWindow(parentWindowHandle);
	}

	@Test
	public void verifyLoginRandomDataInOptionalFileds(){
		driver.findElement(By.cssSelector(getLocator("userNameLocator"))).sendKeys("ared");
		driver.findElement(By.cssSelector(getLocator("passwordLocator"))).sendKeys("kjasd");
		driver.findElement(By.cssSelector(getLocator("dataBasesLocator"))).sendKeys("sdasds");
		driver.findElement(By.cssSelector(getLocator("connectButtonLocator"))).click();
		Assert.assertTrue((driver.findElement(By
				.cssSelector(getLocator("homeLocator")))).isDisplayed(),
				"Optional fields accepted random values.");
	}
	
	@Test
	public void verifyDefaultValuesAllFields(){
		String defaultHostValue = "127.0.0.1";
		String defaultPortValue = "27017";
		String defaultUsernameValue = "optional";
		String defaultPasswordValue = "optional";
		String defaultDataBasesValue = "optional";	
		Assert.assertEquals(driver.findElement(By.cssSelector("div.loginFormContents>div>input#host")).getAttribute("value"), defaultHostValue);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.loginFormContents>div>input#port")).getAttribute("value"), defaultPortValue);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.loginFormContents>div>input#username")).getAttribute("placeholder"), defaultUsernameValue);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.loginFormContents>div>input#password")).getAttribute("placeholder"), defaultPasswordValue);
		Assert.assertEquals(driver.findElement(By.cssSelector("div.loginFormContents>div>input#databases")).getAttribute("placeholder"), defaultDataBasesValue);
	}
/*	@Test(dataProvider = "elementLocatorValuesHome", dependsOnMethods = { "loginmViewer" })
	public void verifyHomePage(String elementLocator) {

		// checking the presence of webelements on the Home page

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		System.out.println("Started verifying the webelements on the Homepage");
		homePage.checkAllElementsDisplay(elementLocator);
		
	}
*/
/*	@Test(dataProvider = "elementLocatorValues")
	public void verifyLoginpage(String elementLocator, String elementValue) {
		// checking the presence of the WebElemets on the Login Page
		System.out.println("Started verifying webelements on the Login Page");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		loginPage.checkAllElementsDisplay(elementLocator, elementValue);

	}
*/
}