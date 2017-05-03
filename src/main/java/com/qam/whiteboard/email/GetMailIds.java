package com.qam.whiteboard.email;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen on 4/27/2017.
 */
public class GetMailIds {

    List<String> allMails = new ArrayList<String>();

    public static void main(String[] args) throws IOException {


        GetMailIds getMailIds = new GetMailIds();
        getMailIds.getMailIds();
    }

    public List<String> getMailIds() throws IOException {
        InputStream file = getClass().getResourceAsStream("/mail-recepients.xls");
        XSSFWorkbook wb = new XSSFWorkbook(file);
        //   HSSFWorkbook workbook = new HSSFWorkbook(file);

        XSSFSheet sheet = wb.getSheetAt(0);
        System.out.println(sheet.getPhysicalNumberOfRows());
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            try {
                allMails.add(sheet.getRow(i).getCell(2).getStringCellValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
//@TODO: Remove hardcoded text
//        allMails.removeAll(allMails);
//        allMails.add("praveen.cherukuri@hotmail.com");
        return allMails;
    }

}
