/**
 * 
 */
package com.marina.dataproviders;


import org.testng.annotations.DataProvider;

import com.marina.utils.ExcelLibrary;



public class DataProviders {
	


	@DataProvider(name = "")
	public Object[][] getSpacesData() {
		
		String path = System.getProperty("user.dir") + "/src/test/resources/testdata/sheetName.xlsx";
		ExcelLibrary obj = new ExcelLibrary(path);
		
		// Totals rows count
		int rows = obj.getRowCount("sheetName");
		// Total Columns
		int column = obj.getColumnCount("sheetName");
		int actRows = rows - 1;

		Object[][] data = new Object[actRows][column];

		for (int i = 0; i < actRows; i++) {
			for (int j = 0; j < column; j++) {
				data[i][j] = obj.getCellData("sheetName", j, i + 2);
			}
		}
		return data;
	}
	

	

}
