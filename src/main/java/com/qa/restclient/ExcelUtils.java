package com.qa.restclient;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExcelUtils {

	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;
	private static String Path;

	public static OutputStream fileOut = null;
	public static FileInputStream ExcelFile = null;
	// 设置Excel文件路径，方便读取到文件
	public static void setExcelFile(String Path) throws Exception {
		ExcelUtils.Path = Path;
		ExcelFile = new FileInputStream(Path);
		ExcelWBook = new XSSFWorkbook(ExcelFile);

	}

	// 读取Excel文件单元格数据
	// 新增sheetname参数，这样就可以去读取Test Steps和Test Cases两个工作表的单元格数据
	public static String getCellData(int RowNum, int ColNum, String SheetName)
			throws Exception {

		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		try {
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			ExcelWSheet.getRow(RowNum).getCell(ColNum).setCellType(Cell.CELL_TYPE_STRING);
			String CellData = Cell.getStringCellValue();
			return CellData;
		} catch (Exception e) {
			return "";
		}

	}

	// 得到一共多少行数据
	public static int getRowCount(String SheetName) {
		int number = 0;

		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		number = ExcelWSheet.getLastRowNum() + 1;

		// System.out.println(number);
		return number;
	}

	// 得到测试用例的行号,参数分别是Test Case ID, Test Case第一个步骤和sheet名称，返回值是测试用例的步骤数。
	public static int getRowContains(String sTestCaseName, int colNum,
			String SheetName) throws Exception {
		int i = 0;
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		int rowCount = ExcelUtils.getRowCount(SheetName);
		for (; i < rowCount; i++) {
			if (ExcelUtils.getCellData(i, colNum, SheetName).equalsIgnoreCase(
					sTestCaseName)) {
				break;
			}
		}

		return i;
	}

	// 计算一个测试用例有多少个步骤
	public static int getTestStepsCount(String SheetName, String sTestCaseID,
			int iTestCaseStart) throws Exception {

		try {
			for (int i = iTestCaseStart; i <= ExcelUtils.getRowCount(SheetName); i++) {
				if (!sTestCaseID.equals(ExcelUtils.getCellData(i,
					Constants.Col_TestCaseID, SheetName))) {
					int number = i;
					return number;
				}
			}
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			int number = ExcelWSheet.getLastRowNum() + 1;
			return number;

		} catch (Exception e) {

			return 0;
		}

	}

	// 构造一个往单元格写数据的方法，主要是用来写结果pass还是fail
	public static void setCellData(String Result, int RowNum, int ColNum,
			String SheetName) throws IOException {

		try {

			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			Row = ExcelWSheet.getRow(RowNum);
			Cell = Row.getCell(ColNum);
			if (Cell == null) {
				Cell = Row.createCell(ColNum);
				Cell.setCellType(Cell.CELL_TYPE_BLANK);
				Cell.setCellValue(Result);
			} else {
				Cell.setCellType(Cell.CELL_TYPE_BLANK);
				Cell.setCellValue(Result);
			}
			// Constant variables Test Data path and Test Data file name
			fileOut = new FileOutputStream(Path);
			ExcelWBook.write(fileOut);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				ExcelFile.close();
				if (fileOut != null) {
					fileOut.flush();
					fileOut.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
