package com.qam.whiteboard.whatsapp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SendMsgWhatsappUI {
    static WebDriver driver;

    public static void sendMessageTo(String phoneNumber) {

        String message = "*Free Webinar* from *QA Masters* - Training by experts \n" +
                "\n" +
                "*Date* June 24\n" +
                "*Time* 19:00 (IST)\n" +
                "*Duration* 1hr\n" +
                "*Venue* Online \n" +
                " \n" +
                "A unique program to enrich your IT awareness and create a magnificent career in Software Testing. \n" +
                "\n" +
                "Attend our free webinar and get your doubts and myths related to Software Testing clarified. \n" +
                "\n" +
                "Mohan JS is a renowned industry expert with *16 years* of experience and remarkable exposure in Software Testing.\n" +
                "\n" +
                "In this webinar, he will talk about his experience & analysis on Software Testing and share his expert tips in planning an excellent Software Career. \n" +
                "\n" +
                "Register for the webinar by clicking the below link. \n" +
                "\n" +
                "http://qa-masters.com/freebies/webinars\n" +
                "\n" +
                "\n" +
                "Early registrations will reward you with attractive *free gifts*\n";

        driver.findElement(By.xpath("//button[@title='New chat']")).click();

        driver.findElement(By.xpath("//input[@title='Search contacts']")).sendKeys(phoneNumber);

        driver.findElement(By.xpath("//span[@title='" + phoneNumber + "']")).click();

        driver.findElement(By.xpath("//div[@class='input']")).sendKeys(message);

        driver.findElement(By.xpath("//button[contains(@class,'compose-btn-send')]")).click();

        System.out.println("Now here... ");


    }
//    public static void main(String[] args) {

    public static void launchBrowser() {
        System.setProperty("webdriver.chrome.driver", "D:\\Selenium_Support\\Jar Files\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://web.whatsapp.com/");

    }
}
