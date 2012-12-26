package org.imaginea.mviewer.tests;

import java.util.ArrayList;
import org.imaginea.mviewer.common.BaseClass;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;

public class HomeLeftTest extends BaseClass {
	
	@Test
	public void verifyCenterContent(){
		homeLeft.verifyCenterContentImage();
		ArrayList<String>actualCenterContentText = homeLeft.getCenterContentText();
		ArrayList<String>expectedCenterContentText = homeLeft.actualTextContent();
		Assert.assertEquals(actualCenterContentText, expectedCenterContentText);
	}

	@BeforeClass
	public void beforeClass() {
		homeLeft = loginPage.loginleft();
		db = homeLeft.dataBase();
	}
}
