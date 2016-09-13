/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.assignment2.model;

/**
 *
 * @author varanasiavinash
 */
import com.assignment2.beans.Mail;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EmailDB {

    public static boolean sendMessage(Mail mail) {

        Properties properties = new Properties();
        properties.setProperty("mail.smtp.host", "smtp.gmail.com");
        properties.setProperty("mail.smtp.socketFactory.port", "465");
        properties.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "465");
        
        boolean state=true;
        // Get the default Session object.
        Session session = Session.getDefaultInstance(properties,
                new Authenticator() {

                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("researchparticipant09@gmail.com","poilkjmnb");
                    }
                });
        try {
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);
            // Set From: header field of the header.
            message.setFrom(new InternetAddress("researchparticipant09@gmail.com","DoNotReply"));
            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(mail.getReceiverEmail(),mail.getReceiverName()));
            // Set Subject: header field
            message.setSubject(mail.getSubject());
            // Now set the actual message
            
            if(mail.getMailType().equals("contact")){
                message.setText(getContactMsg(mail));
            }
            if(mail.getMailType().equals("recommend")){
                message.setText(getRecommendation(mail));
            }
            if(mail.getMailType().equals("forgotpassword")){
                message.setContent(getforgotpassword(mail),"text/html");
             }    
            
            // Send message
            Transport.send(message);
            state=true;
        } catch (MessagingException mex) {
            mex.printStackTrace();
            state=false;
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(EmailDB.class.getName()).log(Level.SEVERE, null, ex);
            state=false;
        }
        return state;
    }

    private static String getforgotpassword(Mail mail)
    {
        return "<HTML>"
                + "<HEAD>"
                + "</HEAD>"
                + "<BODY>"
                + "<h1>Please click on the following <a href=" + mail.getMessage() + ">link</a> Click here to set your new password</h1>"
                + "</BODY>"
                + "</HTML>";
    }
    
    private static String getRecommendation(Mail mail) {
    
    return 
    "Hi "+mail.getReceiverName()+ "\n" +mail.getMessage()+ "\n";
	
}

    private static String getContactMsg(Mail mail) {
        
    return 
    "Hi "+mail.getReceiverName()+"\n" +mail.getMessage()+ "\n";
	

    }
    
    public static boolean sendmail(String tomail, String frommail, String subject, String body, String pwd) {

		//toId = request.getParameter("mailId");
        //subject = request.getParameter("subject");
        //body = request.getParameter("body");
        //messaging work starts here
        final String username = frommail;
        final String password = pwd;
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
         boolean state=true;
        Session session2;
        session2 = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            
        @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
        try {
            MimeMessage message = new MimeMessage(session2);

            message.setRecipients(javax.mail.Message.RecipientType.TO, InternetAddress.parse(tomail));
            message.setSubject(subject);
            message.setText(body, "utf-8", "html");
            Transport.send(message);
            state=true;
        } catch (Exception e) {
            e.printStackTrace();
            state=false;
        }
        return state;
    }

    
}
