package com.tomtop.management.common;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;

public class ExcelUtils {

	private final Logger logger = org.slf4j.LoggerFactory.getLogger(this
			.getClass());

	private SimpleDateFormat sdf;

	public byte[] arrayToXSL(ArrayList<ArrayList<Object>> data) {
		return arrayToXLS(data, null);
	}

	public byte[] arrayToXLS(ArrayList<ArrayList<Object>> data, String sheetName) {
		try {
			ByteArrayOutputStream file = new ByteArrayOutputStream();
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = null;
			if (null != sheetName)
				sheet = workbook.createSheet(sheetName);
			else {
				sheet = workbook.createSheet();
			}
			int i = 0;
			HSSFRow row;
			int j;
			Iterator<Object> localIterator2 = null;
			for (ArrayList<Object> rowdata : data) {
				row = sheet.createRow(i++);
				j = 0;
				for (localIterator2 = rowdata.iterator(); localIterator2
						.hasNext();) {
					Object column = localIterator2.next();
					HSSFCell cell = row.createCell(j++);
					if (null == column)
						cell.setCellValue("");
					else
						cell.setCellValue(column.toString());
				}
			}
			workbook.write(file);
			file.close();
			return file.toByteArray();
		} catch (Exception e) {
			logger.error("arrayToXLS is error", e);
			throw new RuntimeException(e);
		}
	}

	public byte[] arrayToXLSX(ArrayList<ArrayList<Object>> data) {
		return arrayToXLSX(data, null);
	}

	public byte[] arrayToXLSX(ArrayList<ArrayList<Object>> data,
			String sheetName) {
		try {

			XSSFWorkbook workbook = new XSSFWorkbook();
			ByteArrayOutputStream file = new ByteArrayOutputStream();
			XSSFSheet sheet = null;
			if (null != sheetName)
				sheet = workbook.createSheet(sheetName);
			else {
				sheet = workbook.createSheet();
			}
			int i = 0;
			XSSFRow row;
			int j;
			Iterator localIterator2;
			for (ArrayList<Object> rowdata : data) {
				row = sheet.createRow(i++);
				j = 0;
				for (localIterator2 = rowdata.iterator(); localIterator2
						.hasNext();) {
					Object column = localIterator2.next();
					XSSFCell cell = row.createCell(j++);
					if (null == column)
						cell.setCellValue("");
					else
						cell.setCellValue(column.toString());
				}
			}
			workbook.write(file);
			file.close();
			return file.toByteArray();
		} catch (Exception e) {
			logger.error("arrayToXLSX is error", e);
			throw new RuntimeException(e);
		}
	}

	public static boolean isExcel2003(String fileName) {
		if (null == fileName) {
			return false;
		}
		return fileName.matches("^.+\\.(?i)(xls)$");
	}

	public static boolean isExcel2007(String fileName) {
		if (null == fileName) {
			return false;
		}
		return fileName.matches("^.+\\.(?i)(xlsx)$");
	}
	
	public List<List<String>> read(File file, boolean isExcel2003) {
		try {
			return read(new FileInputStream(file),isExcel2003);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<List<String>>();
	}
	public List<List<String>> read(InputStream file,String fileName) {
		return read(file,ExcelUtils.isExcel2003(fileName));
	}

	public List<List<String>> read(InputStream file, boolean isExcel2003) {
		List<List<String>> dataList = new ArrayList<List<String>>();
		try {
			Workbook wb = null;
			if (isExcel2003)
				wb = new HSSFWorkbook(file);
			else {
				wb = new XSSFWorkbook(file);
			}
			dataList = read(wb);
		} catch (IOException e) {
			logger.error("read xls error: ", e);
		}
		return dataList;
	}

	private List<List<String>> read(Workbook wb) {
		List dataList = new ArrayList();

		Sheet sheet = wb.getSheetAt(0);

		int totalRows = sheet.getPhysicalNumberOfRows();

		int totalCells = 0;
		if ((totalRows >= 1) && (sheet.getRow(0) != null)) {
			totalCells = sheet.getRow(0).getPhysicalNumberOfCells();
		}

		for (int r = 0; r < totalRows; ++r) {
			Row row = sheet.getRow(r);
			if (row == null) {
				continue;
			}
			List rowList = new ArrayList();

			for (int c = 0; c < totalCells; ++c) {
				Cell cell = row.getCell(c);
				String cellValue = "";
				if (null != cell) {
					switch (cell.getCellType()) {
					case 0:
						if (HSSFDateUtil.isCellDateFormatted(cell))
							cellValue = getSdf().format(
									Long.valueOf(HSSFDateUtil.getJavaDate(
											cell.getNumericCellValue())
											.getTime()));
						else {
							cellValue = cell.getNumericCellValue() + "";
						}
						break;
					case 1:
						cellValue = cell.getStringCellValue();
						break;
					case 4:
						cellValue = cell.getBooleanCellValue() + "";
						break;
					case 2:
						cellValue = cell.getCellFormula() + "";
						break;
					case 3:
						cellValue = "";
						break;
					case 5:
						cellValue = "Illegal Character";
						break;
					default:
						cellValue = "Unknown Type";
					}
				}

				rowList.add(cellValue);
			}

			dataList.add(rowList);
		}
		return dataList;
	}

	public SimpleDateFormat getSdf() {
		if (this.sdf == null) {
			this.sdf = DateUtil.getSimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		}
		return this.sdf;
	}

	public void setSdf(SimpleDateFormat sdf) {
		this.sdf = sdf;
	}
}
