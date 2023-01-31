package utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class Excel {

    public String filepathX = "data/dataTest.xlsx";

//    public String searchCampo (String sheetName, String nameCampo, String CP) {
//        String Resultado = "";
//        try {
//
//            File file = new File(filepathX);
//
//            FileInputStream inputStream = new FileInputStream(file);
//
//            XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);
//
//            XSSFSheet newSheet = newWorkbook.getSheet(sheetName);
//
//            int fila_CP = searchPositionCP(sheetName, CP);
//
//            for (int i = 0; i < 1; i++) {
//                XSSFRow row = newSheet.getRow(i);
//
//                for (int j = 0; j < row.getLastCellNum(); j++) {
//
//                    if(row.getCell(j).getStringCellValue().equals(nameCampo))
//                    {
//                        Resultado = getCellValue(sheetName, fila_CP, j);
//                    }
//                }
//            }
//            return Resultado;
//        }catch (IOException e)
//        {
//            e.printStackTrace();
//            return Resultado;
//        }
//
//    }

    public String getCellValue(String sheetName, int rowNumber, int cellNumber) throws IOException {

        File file = new File(filepathX);

        FileInputStream inputStream = new FileInputStream(file);

        XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);

        XSSFSheet newSheet = newWorkbook.getSheet(sheetName);

        XSSFRow row = newSheet.getRow(rowNumber);

        XSSFCell cell = row.getCell(cellNumber);

        return cell.getStringCellValue();

    }

//    public int searchPositionCP(String sheetName, String CP) throws IOException
//    {
//        int Resultado = 0;
//        File file = new File(filepathX);
//
//        FileInputStream inputStream = new FileInputStream(file);
//
//        XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);
//
//        XSSFSheet newSheet = newWorkbook.getSheet(sheetName);
//
//        int rowCount = newSheet.getLastRowNum() - newSheet.getFirstRowNum();
//        for (int i = 1; i <= rowCount; i++) {
//            XSSFRow row = newSheet.getRow(i);
//            String[] codigo = CP.split("_");
//            String codigoPMICE = codigo[0];
//            if(row.getCell(0).getStringCellValue().equals(codigoPMICE))
//            {
//                Resultado = i;
//                break;
//            }
//
//        }
//        return Resultado;
//    }

//    public void writeCellValue(String sheetName, String nameCampo, String CP, String valor){
//
//        try {
//
//            File file = new File(filepathX);
//
//            FileInputStream inputStream = new FileInputStream(file);
//
//            XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);
//
//            XSSFSheet newSheet = newWorkbook.getSheet(sheetName);
//
//            int fila_CP = searchPositionCP(sheetName, CP);
//
//            for (int i = 0; i < 1; i++) {
//                XSSFRow row = newSheet.getRow(i);
//
//                for (int j = 0; j < row.getLastCellNum(); j++) {
//
//                    if(row.getCell(j).getStringCellValue().equals(nameCampo))
//                    {
//                        setCellValue(sheetName, fila_CP, j,valor);
//                    }
//
//
//                }
//            }
//        }catch (IOException e)
//        {
//            e.printStackTrace();
//        }
//
//    }

    public void setCellValue(String sheetName, int rowNumber, int cellNumber, String valor) throws IOException {

        File file = new File(filepathX);

        FileInputStream inputStream = new FileInputStream(file);

        XSSFWorkbook newWorkbook = new XSSFWorkbook(inputStream);

        XSSFSheet newSheet = newWorkbook.getSheet(sheetName);


        Cell cell = null;

        //Retrieve the row and check for null
        XSSFRow sheetrow = newSheet.getRow(rowNumber);
        if(sheetrow == null){
            sheetrow = newSheet.createRow(rowNumber);
        }
        //Update the value of cell
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



