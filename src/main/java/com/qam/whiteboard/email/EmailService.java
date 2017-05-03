package com.qam.whiteboard.email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Praveen on 4/27/2017.
 */

public class EmailService {
    List<String> allMails = new ArrayList<String>();
    Properties props;
    Session session;
    Transport transport;
    MimeMessage message;
    String userName = "praveen@qa-masters.com";
    String serverUrl = "md-98.webhostbox.net";
    String password = "Jasmine123#";
    String serverPort = "465";
    String emailText = "<h1>Webinar details</h1>";

    public EmailService() throws NoSuchProviderException, IOException {
        getEmailIds();

        props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.host", serverUrl);
        props.put("mail.smtp.user", userName);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", serverPort);
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        session = Session.getInstance(props, null);
        transport = session.getTransport("smtp");
    }

    public static void main(String[] args) throws IOException, NoSuchProviderException {
        EmailService em = new EmailService();
        em.createMessage();
    }

    public String getEmailText() {
//        http://freeemaileditor.com/edit/default.asp?n=47469
        return "<tbody><tr><td><div id=\"innerContent\"><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"padding: 0px; padding-top: 0px; padding-bottom: 15px;\"><tbody><tr><td><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"\"><tbody><tr><td bgcolor=\"\"><div id=\"txtHolder-2\" class=\"txtEditorClass\" style=\"color: #000000; font-size: 26px; font-family: 'Impact'; text-align: Left\"><div style=\"text-align: center;\"><span style=\"font-size: xx-large;\">Free webinar from QA Masters</span></div></div></td></tr></tbody></table></td></tr></tbody></table><table width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"padding: 0px; padding-top: 0px; padding-bottom: 15px;\"><tbody><tr><td><table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" style=\"\"><tbody><tr><td bgcolor=\"\"><div id=\"txtHolder-3\" class=\"txtEditorClass\" style=\"color: #666666; font-size: 14px; font-family: 'Arial'; text-align: Left\"><div style=\"text-align: center;\"><span style=\"font-family: Arial, Verdana; font-size: 10pt; font-weight: bold;\">Are you ready for the next generation testing jobs..??&nbsp;</span></div><div style=\"font-weight: normal; text-align: center;\"><span style=\"font-family: Arial, Verdana; font-size: 13.3333px;\"><br></span></div><span style=\"font-weight: normal; font-family: Arial, Verdana; font-size: 10pt; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal;\"><div style=\"text-align: center;\"><span style=\"font-size: 10pt;\"><a href=\"http://qa-masters.com\">QA Masters</a> is coming forward to help you decide your future milestones.&nbsp;</span></div></span><div style=\"font-weight: normal; text-align: center;\"><br></div><span style=\"font-weight: normal; font-family: Arial, Verdana; font-size: 10pt; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal;\"><div style=\"text-align: center;\"><span style=\"font-size: 10pt;\">Do you want to know how.??</span></div></span><div style=\"font-weight: normal; text-align: center; font-family: Arial, Verdana; font-size: 10pt; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal;\"><br></div><div style=\"font-weight: normal; text-align: center; font-family: Arial, Verdana; font-size: 10pt; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal;\">Very simple, Join this webinar and know what is current market requirements and suggestions from Industry experts...&nbsp;</div><div style=\"font-weight: normal; font-family: Arial, Verdana; font-size: 10pt; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal;\"><br></div><div style=\"font-weight: normal; font-family: Arial, Verdana; font-size: 10pt; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; text-align: center;\"><span style=\"font-family: Arial, Verdana;\">Why wait.!!</span></div><div style=\"font-weight: normal; font-family: Arial, Verdana; font-size: 10pt; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; text-align: center;\"><span style=\"font-family: Arial, Verdana;\">Signup now...!!!&nbsp;</span></div><div style=\"font-weight: normal; font-family: Arial, Verdana; font-size: 10pt; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal;\"><div style=\"text-align: center;\"><span style=\"font-size: 10pt;\">You might be the lucky one to Win a </span><span style=\"font-size: 10pt; font-family: &quot;Comic Sans MS&quot;; color: rgb(255, 0, 0);\">PENDRIVE </span><span style=\"font-size: 10pt; font-family: &quot;Comic Sans MS&quot;;\">and</span><span style=\"font-size: 10pt; font-family: &quot;Comic Sans MS&quot;; color: rgb(255, 0, 0);\"> </span><span style=\"font-size: 10pt; font-family: &quot;Comic Sans MS&quot;;\">many more</span><span style=\"font-size: 10pt; font-family: &quot;Comic Sans MS&quot;; color: rgb(255, 0, 0);\"> Exciting Discount Offers</span><span style=\"font-size: 10pt; font-family: &quot;Comic Sans MS&quot;;\"> waiting for you</span></div><br></div></div></td></tr></tbody></table></td></tr></tbody></table><table width=\"100%\" cellspacing=\"0\" cellpadding=\"10\"><tbody><tr><td align=\"Center\" bgcolor=\"#f1f1f1\"><div><div id=\"txtHolder-4\" class=\"txtEditorClass\" style=\"color: #5d5d5d; font-size: 11px; font-family: 'Arial';\">Click <a href=\"http://qa-masters.com/feebies/webinar\">here</a> to signup</div></div></td></tr></tbody></table></div></td></tr></tbody>";
    }

    public void createMessage() {
        try {
            message = new MimeMessage(session);

            message.setFrom(new InternetAddress(userName));
            message.setSubject("Webinar 2017");

            Multipart multipart = new MimeMultipart("alternative");

            // Create your text message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Here comes text 1");

            // Add the text part to the multipart
            multipart.addBodyPart(messageBodyPart);

            // Create the html part
            messageBodyPart = new MimeBodyPart();

            String htmlMessage = getEmailText();
            messageBodyPart.setContent(htmlMessage, "text/html");

            // Add html part to multi part
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);
//            message.setContent(someHtmlMessage, "text/html; charset=utf-8");


            messageBodyPart = new MimeBodyPart();
            String filename = "D:\\Download\\11.jpg";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Webinar - details");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);


            addAllReciepients(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getEmailIds() throws IOException {
        GetMailIds getMailIds = new GetMailIds();
        allMails = getMailIds.getMailIds();
    }


    private void addAllReciepients(MimeMessage message) throws MessagingException {
        transport.connect(serverUrl, userName, password);
        System.out.println("Transport: " + transport.toString());
        System.out.println("Sending mails to : " + allMails.size());
        for (String mailId : allMails) {
            try {
                System.out.println("Sending mail to " + mailId);
                message.setRecipient(Message.RecipientType.TO, new InternetAddress(mailId.trim()));
                System.out.println("Total recipients before sending mail : " + message.getAllRecipients().length);
                transport.sendMessage(message, message.getAllRecipients());
                System.out.println("Total recipients after sending mail : " + message.getAllRecipients().length);
//                message.setRecipients(Message.RecipientType.TO, "");
//                System.out.println("Total recipients after reset : " + message.getAllRecipients().length);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Sending failed to " + mailId);
            }
        }
    }
}