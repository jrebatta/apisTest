package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Excel {

    public String filepathX = "data/dataTest.xlsx";

    public String getCellValue(String sheetName, int rowNumber, int cellNumber) throws IOException {

        File file = new File(filepathX);

        FileInputStream inputStream = new FileInputStream(file);

        XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);

        XSSFSheet newSheet = newWorkbook.getSheet(sheetName);

        XSSFRow row = newSheet.getRow(rowNumber);

        XSSFCell cell = row.getCell(cellNumber);

        return cell.getStringCellValue();

    }

    public void setCellValue(String sheetName, int rowNumber, int cellNumber, String valor) throws IOException {

        File file = new File(filepathX);

        FileInputStream inputStream = new FileInputStream(file);

        XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);

        XSSFSheet newSheet = newWorkbook.getSheet(sheetName);


        Cell cell = null;

        XSSFRow sheetrow = newSheet.getRow(rowNumber);
        if(sheetrow == null){
            sheetrow = newSheet.createRow(rowNumber);
        }
        cell = sheetrow.getCell(cellNumber);
        if(cell == null){
            cell = sheetrow.createCell(cellNumber);
        }
        cell.setCellValue(valor);


        inputStream.close();

        FileOutputStream outputStream = new FileOutputStream(file);

        newWorkbook.write(outputStream);

        outputStream.close();

    }

}



