package com.qam.whiteboard.whatsapp;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.*;

import java.io.File;
import java.io.IOException;

/**
 * Created by Praveen on 6/9/2017.
 */
public class ReadExcels {
    public static void main(String[] args) throws IOException, BiffException, WriteException {
        Workbook workbook = Workbook.getWorkbook(new File("D:\\Eclipse_WS\\bulk-mail\\src\\main\\resources\\Campaing.xls"));

        WritableWorkbook copy = Workbook.createWorkbook(new File("output.xls"), workbook);
        WritableSheet sheet2 = copy.getSheet(0);
        SendMsgWhatsappUI.launchBrowser();
        WritableCell cell;


        Sheet sheet = workbook.getSheet(0);

        int rowCount = sheet.getRows();

        for (int i = 0; i < rowCount; i++) {
            System.out.println(sheet.getCell(0, i).getContents());

            SendMsgWhatsappUI.sendMessageTo(sheet.getCell(0, i).getContents());

            Label l = new Label(2, i, "done");
            cell = l;
            sheet2.addCell(cell);

        }
        copy.write();
        copy.close();
        workbook.close();

    }
}
