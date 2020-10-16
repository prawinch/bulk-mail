package com.qam.whiteboard.whatsapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import jxl.Sheet;
import jxl.Workbook;
import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;
import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Praveen on 6/9/2017.
 */
public class WhatsappHelper {

    private String platform;
    private String workbookName;
    static WebDriver driver;


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

    public String promotionMessage() {
        return "Hello from " + platform;
    }

    @SneakyThrows
    public void findContactAndOpen() {
        File contactsFile = fetchContactSheet();
        List<ContactInfo> allContacts = getContacts(contactsFile);

        for (ContactInfo contact : allContacts) {

            if (contact.getFarm2Home().equalsIgnoreCase("y")) {
                boolean sendStatus = sendMessage(contact, promotionMessage());
                contact.setStatus(sendStatus);
            }


        }


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
            contact.setFarm2Home(sheet.getCell(0, row).getContents());
            contact.setPizza2Home(sheet.getCell(1, row).getContents());
            contact.setPickUpServices(sheet.getCell(2, row).getContents());
            contact.setCountry(sheet.getCell(3, row).getContents());
            contact.setName(sheet.getCell(4, row).getContents());
            contact.setPhoneNumber(sheet.getCell(5, row).getContents());

            allContacts.add(contact);
        }
        return allContacts;
    }

    private File fetchContactSheet() throws URISyntaxException {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource(workbookName);
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            return new File(resource.toURI());
        }
    }

    public boolean sendMessage(ContactInfo contact, String message) {
        try {
            driver.findElement(By.xpath("//div[text()='Search or start new chat']/..")).click();
            driver.findElement(By.xpath("//div[text()='Search or start new chat']/../label/div/div[2]")).sendKeys(contact.getPhoneNumber());
            driver.findElement(By.xpath("//div[@aria-label='Search results.']//span[@title='" + contact.getPhoneNumber() + "']")).click();
            driver.findElement(By.xpath("//div[text()='Type a message']/../div[2]")).sendKeys(message);
            driver.findElement(By.xpath("//*[@data-testid='send']")).click();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public void updateStatusReport() {

    }
}
