package org.apache.poi.wp.usermodel;


import java.io.FileNotFoundException;
import org.apache.poi.wp.usermodel.mail;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * A dirty simple program that reads an Excel file.
 * 
 * @author www.codejava.net
 *
 */

public class Envio {
	@SuppressWarnings("deprecation")
	static String cellString(Cell columna) {
		String columnaString = null;
		switch (columna.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			columnaString = columna.getStringCellValue();
			break;

		case Cell.CELL_TYPE_NUMERIC:
			double c = columna.getNumericCellValue();
			columnaString = String.valueOf((int) c);
			break;
		}
		return columnaString;
	}

	private static String removePipe(String str) {
		str = str.replaceAll("Item:", "");
		str = str.replaceAll("\\¦", ",");
		return str;
	}

	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException {

		String excelFilePath = "test.xlsx";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));

		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		while (iterator.hasNext()) {
			Row nextRow = iterator.next();

			String cliente = cellString(nextRow.getCell(1));
			String type = cellString(nextRow.getCell(8));

			if (type.equals("FTP")) {
				String itemsRaw = cellString(nextRow.getCell(10));

				String arr = removePipe(itemsRaw);

				mail.test(cliente, arr, type, "lvizzari@neotel.com.ar", "magate101458");

			}

		}

		workbook.close();
		inputStream.close();
	}

}
