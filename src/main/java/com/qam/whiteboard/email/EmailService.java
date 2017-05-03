package com.qam.whiteboard.email;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Praveen on 4/27/2017.
 */

public class EmailService {
    List<String> allMails = new ArrayList<String>();

    public static void main(String[] args) throws IOException {
        EmailService em = new EmailService();
        em.sendEmailToIds();
    }

    public void sendEmailToIds() throws IOException {
        getEmailIds();

        String userName = "praveen@qa-masters.com";
        String serverUrl = "md-98.webhostbox.net";
        String password = "Jasmine123#";
        String serverPort = "465";

        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", true); // added this line
        props.put("mail.smtp.host", serverUrl);
        props.put("mail.smtp.user", userName);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", serverPort);
        props.put("mail.smtp.auth", true);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        Session session = Session.getInstance(props, null);
        MimeMessage message = new MimeMessage(session);

        // Create the email addresses involved
        try {
            InternetAddress from = new InternetAddress(userName);
            message.setSubject("Webinar 2017");
            message.setFrom(from);
            addAllReciepients(message);

            // Create a multi-part to combine the parts
            Multipart multipart = new MimeMultipart("alternative");

            // Create your text message part
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setText("Here comes text 1");

            // Add the text part to the multipart
            multipart.addBodyPart(messageBodyPart);

            // Create the html part
            messageBodyPart = new MimeBodyPart();
            String htmlMessage = "Webinar details";
            messageBodyPart.setContent(htmlMessage, "text/html");


            // Add html part to multi part
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);


            messageBodyPart = new MimeBodyPart();
            String filename = "D:\\Download\\11.jpg";
            DataSource source = new FileDataSource(filename);
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("Webinar - details");
            multipart.addBodyPart(messageBodyPart);
            message.setContent(multipart);

            // Send message
            Transport transport = session.getTransport("smtp");
            transport.connect(serverUrl, userName, password);
            System.out.println("Transport: " + transport.toString());
            transport.sendMessage(message, message.getAllRecipients());
        } catch (AddressException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (MessagingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void getEmailIds() throws IOException {
        GetMailIds getMailIds = new GetMailIds();
        allMails = getMailIds.getMailIds();
    }


    private void addAllReciepients(MimeMessage message) throws MessagingException {
        for (String mailId : allMails) {
            try {
                System.out.println("Sending mail to " + mailId);
                message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(mailId));
            } catch (Exception e) {
                System.out.println("Sending failed to " + mailId);
            }
        }
    }
}