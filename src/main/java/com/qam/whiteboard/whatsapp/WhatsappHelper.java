package com.qam.whiteboard.whatsapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen on 6/9/2017.
 */
public class WhatsappHelper {

    static WebDriver driver;
    private String platform;
    private String workbookName;


    public WhatsappHelper(String platform, String workbookName) {
        this.platform = platform;
        this.workbookName = workbookName;
    }

    public static void launchWhatsApp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.get("https://web.whatsapp.com/");
        JOptionPane.showMessageDialog(null, "Click okay after logging into whatsapp");

    }

    public StringBuilder promotionMessage() {
        String abc = Keys.SHIFT + "\n" + Keys.SHIFT;
        StringBuilder sb = new StringBuilder();
        sb.append("Greetings from Npai!").append(abc);
        sb.append(" ").append(abc);
        sb.append("Thank you for downloading NPai. ").append(abc);
        sb.append("Due to some technical reasons, We have updated our application.").append(abc);
        sb.append("Kindly note our previous version won’t work. Sorry for the inconvenience.").append(abc);
        sb.append(" ").append(abc);
        sb.append("Please download/update our latest version from Play Store / App Store to enjoy hassle free services.").append(abc);
        sb.append(" ").append(abc);
        sb.append("Play Store: https://play.google.com/store/apps/details?id=cpj.npai.in").append(abc);
        sb.append("App Store : https://apps.apple.com/us/app/npai/id1528150577").append(abc);
        sb.append("Kindly let us know, if you face any issues..!").append(abc);
        return (sb);
    }

    public void findContactAndOpen() {
        File contactsFile = fetchContactSheet();
        List<ContactInfo> allContacts = getContacts(contactsFile);
        List<ContactInfo> resultContacts = new ArrayList<>();

       /* for (ContactInfo contact : allContacts) {

            if (contact.getSubscribed().equalsIgnoreCase("y")) {
                boolean sendStatus = sendMessage(contact, promotionMessage());
                contact.setStatus(sendStatus);
                contact.setSubscribed("N");
            }
        }*/
        updateStatusReport(contactsFile, allContacts);
    }

    @SneakyThrows
    private List<ContactInfo> getContacts(File contactsFile) {
        Workbook workbook = Workbook.getWorkbook(contactsFile);
        Sheet sheet = workbook.getSheet("bulk-promotions");
        int rowCount = sheet.getRows();
        System.out.println("Total " + rowCount + " number of contacts found in the excel sheet");
        List<ContactInfo> allContacts = new ArrayList<ContactInfo>();
        ContactInfo contact;

        for (int row = 1; row < rowCount; row++) {
            contact = new ContactInfo();
            contact.setSubscribed(sheet.getCell(0, row).getContents());
            contact.setName(sheet.getCell(1, row).getContents());
            contact.setPhoneNumber(sheet.getCell(2, row).getContents());

            allContacts.add(contact);
        }
        return allContacts;
    }

    private File fetchContactSheet() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL resource = classLoader.getResource(workbookName);
            if (resource == null) {
                throw new IllegalArgumentException("file not found!");
            } else {
                return new File(resource.toURI());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean sendMessage(ContactInfo contact, StringBuilder message) {
        try {
            driver.findElement(By.xpath("//div[text()='Search or start new chat']/..")).click();
            hardWait(1);
            driver.findElement(By.xpath("//div[text()='Search or start new chat']/../label/div/div[2]")).sendKeys(contact.getPhoneNumber());
            driver.findElement(By.xpath("//div[@aria-label='Search results.']//span[@title='" + contact.getPhoneNumber() + "']")).click();
            driver.findElement(By.xpath("//div[text()='Type a message']/../div[2]")).sendKeys(message);
            driver.findElement(By.xpath("//*[@data-testid='send']")).click();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @SneakyThrows
    public void updateStatusReport(File contactsFile, List<ContactInfo> resultContacts) {
        Workbook workbook = Workbook.getWorkbook(contactsFile);

        WritableWorkbook copy = Workbook.createWorkbook(new File("output.xls"), workbook);
        WritableSheet sheet2 = copy.getSheet(0);

        for (int i = 0; i < resultContacts.size(); i++) {
            System.out.println(i + " - - " + resultContacts.get(i).getName());
            Label l1 = new Label(0, (i + 1), "N");
            Label l2 = new Label(1, (i + 1), resultContacts.get(i).getName());
            Label l3 = new Label(2, (i + 1), resultContacts.get(i).getPhoneNumber());

            sheet2.addCell(l1);
            sheet2.addCell(l2);
            sheet2.addCell(l3);
        }
        copy.write();
        copy.close();
        workbook.close();
    }

    protected void hardWait(int timeInSeconds) {
        try {
            Thread.sleep(timeInSeconds * 1000);
        } catch (Exception e) {
            System.out.println("Exception while waiting");
            e.printStackTrace();
        }
    }

}
