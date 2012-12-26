package org.imaginea.mviewer.common;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

public class ReadExcel implements DocReader{

	int numberOfRows = 0;
	int numberOfColumns = 0;
	String filename = null;
	String sheetname = null;
	FileInputStream fileinputstream = null;
	POIFSFileSystem fsFileSystem;
	HSSFWorkbook workbook;
	HSSFSheet worksheet;

	public ReadExcel(String filenamein) {
		this(filenamein, 0);
	}

	public ReadExcel(String filename, String sheetname) {
		FileInputStream fileinputstream = null;
		try {
			fileinputstream = new FileInputStream(filename);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		POIFSFileSystem fsFileSystem;
		try {
			fsFileSystem = new POIFSFileSystem(fileinputstream);
			workbook = new HSSFWorkbook(fsFileSystem);
			worksheet = workbook.getSheet(sheetname);
			numberOfRows = worksheet.getLastRowNum()+1;
			//numberOfRows = worksheet.getPhysicalNumberOfRows();
			HSSFRow row = worksheet.getRow(0);
			numberOfColumns = row.getLastCellNum();
			
		} catch (IOException e) {
			System.out.println("I/O exception of some sort has occurred");
			e.printStackTrace();
		}
	}

	public ReadExcel(String filePath, int index) {
		filename = filePath;
		FileInputStream fileinputstream = null;
		try {
			fileinputstream = new FileInputStream(filename);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		POIFSFileSystem fsFileSystem;
		try {
			fsFileSystem = new POIFSFileSystem(fileinputstream);
			workbook = new HSSFWorkbook(fsFileSystem);
			worksheet = workbook.getSheetAt(index);
			numberOfRows = worksheet.getLastRowNum()+1;
			//numberOfRows = worksheet.getPhysicalNumberOfRows();
			HSSFRow row = worksheet.getRow(0);
			numberOfColumns = row.getLastCellNum();

		} catch (IOException e) {
			System.out.println("I/O exception of some sort has occurred");
			e.printStackTrace();
		}

	}

	public int getnumberOfRows() {
		System.out.println("Number of Rows: " + numberOfRows);
		return numberOfRows;
	}

	public int getnumberOfColumns() {
		System.out.println("Number of Columns: " + numberOfColumns);
		return numberOfColumns;
	}

	public String getvalue(int rownumber, int columnnumber) {
		HSSFRow row = worksheet.getRow(rownumber - 1);
		HSSFCell cell = row.getCell(columnnumber - 1);
		return cell.getStringCellValue();
	}
}
