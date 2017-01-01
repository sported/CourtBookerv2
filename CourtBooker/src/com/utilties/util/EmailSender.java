package com.utilties.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



public class EmailSender {

	private static String USER_NAME = "courts.booker";  // GMail user name (just the part before "@gmail.com")
    private static String PASSWORD = "gpsingh123"; // GMail password
    private static String RECIPIENT = "s.gurprit@gmail.com";
   // private static String RECIPIENT2 = "Schyok@hotmail.com";
    private static String FROM = "courts.booker@gmail.com";
    private static String REPLY = "reply@courtbooker001.appspotmail.com";
    
    private static String FROM_NAME = "Tennis Courts";

   
   public static void send(Exception ex) throws Exception {
	   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	   EmailSender.send("Error in booking courts on " + dateFormat.format(new Date()) , ex.getMessage() +"\n"+ ex.getStackTrace().toString() );
    }
   
   public static void send(String message, Exception ex) throws Exception {
	   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	   EmailSender.send(message , ex.getMessage() +"\n"+ ex.getStackTrace().toString() );
    }
   
   
   
   public static void send(String subject, String body)throws Exception{
	   Properties props = new Properties();
	   Session session = Session.getDefaultInstance(props, null);

	  
	 
	       Message msg = new MimeMessage(session);
	       msg.setFrom(new InternetAddress(FROM, FROM_NAME));
	       msg.addRecipient(Message.RecipientType.TO,
	        new InternetAddress(RECIPIENT));
//	       msg.addRecipient(Message.RecipientType.TO,
//	   	        new InternetAddress(RECIPIENT2));
	       msg.setSubject(subject);
	       msg.setReplyTo(new InternetAddress[]{new InternetAddress(REPLY)});
	       msg.setText(body);
	       Transport.send(msg); 

	  
   }
   
 
	
//   public static void send(String subject, String body)throws Exception{
//		
//		Properties props = System.getProperties();
//        String host = "smtp.gmail.com";
//        String from = USER_NAME;
//        String pass = PASSWORD;
//       
//        String[] to = { RECIPIENT };
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", host);
//        props.put("mail.smtp.user", from);
//        props.put("mail.smtp.password", pass);
//        props.put("mail.smtp.port", "587");
//        props.put("mail.smtp.auth", "true");
//       
//
//        Session session = Session.getDefaultInstance(props);
//        MimeMessage message = new MimeMessage(session);
//
//      
//            message.setFrom(new InternetAddress("courts.booker@gmail.com"));
//            InternetAddress[] toAddress = new InternetAddress[to.length];
//
//            // To get the array of addresses
//            for( int i = 0; i < to.length; i++ ) {
//                toAddress[i] = new InternetAddress(to[i]);
//            }
//
//            for( int i = 0; i < toAddress.length; i++) {
//                message.addRecipient(Message.RecipientType.TO, toAddress[i]);
//            }
//
//           
//            message.setSubject(subject );
//            message.setText(body);
//            final Transport transport = session.getTransport("smtp");
//            transport.connect(host, from, pass);
//            transport.sendMessage(message, message.getAllRecipients());
//            transport.close();
//        
//        
//    
//	}
//   
  
	
	public static void main (String[] args) {
		try{
		EmailSender.send("test","test");
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
