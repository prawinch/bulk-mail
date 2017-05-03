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
            String htmlMessage = "<h1>Webinar details</h1>";
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