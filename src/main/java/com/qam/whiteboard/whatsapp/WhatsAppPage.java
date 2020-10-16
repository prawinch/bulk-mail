package com.qam.whiteboard.whatsapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class WhatsAppPage {
    static WebDriver driver;

    public static void sendMessageTo(String phoneNumber) {
        String message = "Helloo From F2H";
        driver.findElement(By.xpath("//div[@title='New chat']")).click();
        driver.findElement(By.xpath("//input[@title='Search contacts']")).sendKeys(phoneNumber);
        driver.findElement(By.xpath("//span[@title='" + phoneNumber + "']")).click();
        driver.findElement(By.xpath("//div[text()='Type a message']/../div[@data-tab]")).sendKeys(message);
        driver.findElement(By.xpath("//span[@data-icon='send']")).click();
    }
//    public static void main(String[] args) {

    public static void launchBrowser() {
        System.setProperty("webdriver.chrome.driver", "src\\main\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://web.whatsapp.com/");

    }
}
