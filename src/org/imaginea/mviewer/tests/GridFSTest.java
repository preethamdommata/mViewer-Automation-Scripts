package org.imaginea.mviewer.tests;

import java.util.ArrayList;

import org.imaginea.mviewer.common.BaseClass;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GridFSTest extends BaseClass {

	@Test
	public void addGridBucket() {
		String gridName = "grid11";
		db.gridFS("Preetham8");
		grid.addGrid(gridName);
		WebElement gridElement = grid.getGrid(gridName);
		grid.verifyGridPresentDisplay(gridElement);
	}

	
	//Need to correct this script..failing with Null exception error
	//@Test
	public void verifyGridOptions() {
		String gridName = "grid1";
		WebElement gridElement = grid.getGrid(gridName);
		db.gridFS("Preetham8");
		grid.clickOnGrid(gridName);
		grid.clickGridOptions(gridName);
		grid.verifyGridOptions(gridElement);
	}

	@Test
	public void verifyErrorMessBucketBlank() {
		db.gridFS("Preetham8");
		grid.openGrid();
		grid.clearBucketName();
		grid.clickGrid("Submit");
		grid.clickGrid("Cancel");
		String actualErrorMess = getErrorMess();
		String expectedErrorMess = "Enter the bucket name!";
		Assert.assertEquals(actualErrorMess, expectedErrorMess);
	}

	// Error message is not displayed as of now. Need to correct accordingly
	// once the error message is received.
	// @Test
	public void verifyErrorMessExistingBucket() {
		ArrayList<String> exisitingBuckets = grid.getGridsNameList();
		db.gridFS("Preetham8");
		grid.openGrid();
		grid.clearBucketName();
		grid.setBucketName(exisitingBuckets.get(0));
		grid.clickGrid("Submit");
		grid.clickGrid("Cancel");
		String actualErrorMess = getErrorMess();
		String expectedErrorMess = "";
		Assert.assertEquals(actualErrorMess, expectedErrorMess);
	}

	 @Test
	public void verifyGridCancelOp() {
		String gridName = "grid2";
		db.gridFS("Preetham8");
		int gridsBeforeOp = grid.getGrids().size();
		grid.openGrid();
		grid.clearBucketName();
		grid.setBucketName(gridName);
		grid.clickGrid("Cancel");
		int gridsAfterOp = grid.getGrids().size();
		Assert.assertEquals(gridsAfterOp, gridsBeforeOp);
	}

	 @Test
	public void verifyGridCloseOp() {
		db.gridFS("Preetham8");
		int gridsBeforeOp = grid.getGrids().size();
		grid.openGrid();
		grid.clickGridClose();
		int gridsAfterOp = grid.getGrids().size();
		Assert.assertEquals(gridsAfterOp, gridsBeforeOp);
	}

	@BeforeClass
	public void beforeClass() {
		homeLeft = loginPage.loginleft();
		db = homeLeft.dataBase();
		grid = db.gridFS("Preetham8");
	}
}
