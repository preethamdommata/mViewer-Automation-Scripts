package org.imaginea.mviewer.common;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;

import java.util.List;

import au.com.bytecode.opencsv.CSVReader;

public class CSVutil implements DocReader{
	String[] rowData;
	String[][] finalData;
	List<String[]> data;
	ArrayList<String[]> data1 = new ArrayList<String[]>();
	int numberOfRows;
	int numberOfColumns;

	public CSVutil(String filePath) throws IOException {
		InputStream in = new FileInputStream(filePath);
		Reader reader = new InputStreamReader(in);
		CSVReader csvRead = new CSVReader(reader, ',', '"');
		
		numberOfRows = 0;
		numberOfColumns = 0;
		while ((rowData = csvRead.readNext()) != null) {
			data1.add(rowData);
			numberOfColumns = rowData.length;
			for (int i = 0; i < rowData.length; i++) {
				
				System.out.println(rowData[i]);
			}
			numberOfRows++;
		}
		System.out.println("Number of rows: " + numberOfRows);
		System.out.println("Number of Columns: " + numberOfColumns);
	}

	public int getnumberOfRows() {
		return numberOfRows;
	}

	public int getnumberOfColumns() {
		return numberOfColumns;
	}

	public String getvalue(int rowNumber, int columnNumber) {
		String returnData="";
		if(data1.size()>rowNumber){
			String value1[] = data1.get(rowNumber);
			if(value1.length>columnNumber)
				returnData=value1[columnNumber];
		}
		return returnData;
	}
}
