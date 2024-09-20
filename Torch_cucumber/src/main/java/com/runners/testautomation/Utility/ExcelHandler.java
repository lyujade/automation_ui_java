package com.runners.testautomation.Utility;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.codoid.products.exception.FilloException;
import com.codoid.products.fillo.Connection;
import com.codoid.products.fillo.Fillo;
import com.codoid.products.fillo.Recordset;

public class ExcelHandler
{
    public static Map<String,String> getTestDataInMap(String testDataFile,String sheetName,String testCaseId) throws Exception
    {
        Map<String,String> TestDataInMap=new TreeMap<String,String>();
        String query=null;
        query=String.format("SELECT * FROM %s WHERE Run='Yes' and TestCaseId='%s'",
                sheetName,testCaseId);
        Fillo fillo=new Fillo();
        Connection conn=null;
        Recordset recordset=null;
        try
        {
            conn=fillo.getConnection(testDataFile);
            recordset=conn.executeQuery(query);
            //recordset=((com.codoid.products.fillo.Connection) conn).executeQuery(query);
            while(recordset.next())
            {
                for(String field:recordset.getFieldNames())
                {
                    TestDataInMap.put(field, recordset.getField(field));
                }
            }
        }
        catch(FilloException e)
        {
            e.printStackTrace();
            throw new Exception("Test data cannot find . . .");
        }
        conn.close();
        return TestDataInMap;
    }

    public static void UpdateTestResultsToExcel(String testDataFilePath,String sheetName,String tcStatus,String testCaseId)
    {
        Connection conn=null;
        Fillo fillo =new Fillo();
        try{
            conn=fillo.getConnection(testDataFilePath);
            String query=String.format("UPDATE %s SET TestCaseStatus='%s' where TestCaseID='%s'", sheetName,tcStatus,testCaseId);
            conn.executeUpdate(query);
        } catch(FilloException e){
            e.printStackTrace();
        }
    }

    public static Object[][] getTestData(String excelFilePath, String sheetName) throws IOException {
        FileInputStream file = new FileInputStream(excelFilePath);
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheet(sheetName);

        int rowCount = sheet.getLastRowNum();
        int columnCount = sheet.getRow(0).getLastCellNum();
        Object[][] data = new Object[rowCount][columnCount];

        DataFormatter formatter = new DataFormatter(); // to handle all types of cell formats
        for (int i = 1; i <= rowCount; i++) {
            Row row = sheet.getRow(i);
            for (int j = 0; j < columnCount; j++) {
                data[i - 1][j] = formatter.formatCellValue(row.getCell(j));
            }
        }

        workbook.close();
        return data;
    }

    public void readExcel(String filePath, String fileName, String sheetName) throws IOException {
        File file = new File(filePath + "\\" + fileName);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook Wb = null;
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        if (fileExtensionName.equals(".xlsx")) {
            Wb = new XSSFWorkbook(inputStream);
        } else if (fileExtensionName.equals(".xls")) {
            Wb = new HSSFWorkbook(inputStream);
        }
        Sheet gSheet = Wb.getSheet(sheetName);
        int rowCount = gSheet.getLastRowNum() - gSheet.getFirstRowNum();
        for (int i = 0; i < rowCount + 1; i++) {
            Row row = gSheet.getRow(i);
            for (int j = 0; j < row.getLastCellNum(); j++) {
                System.out.print(row.getCell(j).getStringCellValue() + "|| ");
            }
            System.out.println();
        }
    }
    public void writeExcel(String filePath, String fileName, String sheetName, String[] dataToWrite)
            throws IOException {
        File file = new File(filePath + "\\" + fileName);
        FileInputStream inputStream = new FileInputStream(file);
        Workbook Wb = null;
        String fileExtensionName = fileName.substring(fileName.indexOf("."));
        if (fileExtensionName.equals(".xlsx")) {
            Wb = new XSSFWorkbook(inputStream);
        } else if (fileExtensionName.equals(".xls")) {
            Wb = new HSSFWorkbook(inputStream);
        }
        Sheet sheet = Wb.getSheet(sheetName);
        int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
        Row row = sheet.getRow(0);
        Row newRow = sheet.createRow(rowCount + 1);
        for (int j = 0; j < row.getLastCellNum(); j++) {
            Cell cell = newRow.createCell(j);
            cell.setCellValue(dataToWrite[j]);
        }
        inputStream.close();
        FileOutputStream outputStream = new FileOutputStream(file);
        Wb.write(outputStream);
        outputStream.close();
    }

}

