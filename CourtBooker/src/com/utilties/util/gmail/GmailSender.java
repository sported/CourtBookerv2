package com.utilties.util.gmail;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.*;
import com.google.api.services.gmail.Gmail;
import com.utilties.util.EmailSender;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class GmailSender {
	 private static HttpTransport HTTP_TRANSPORT;
	 private static final String APPLICATION_NAME ="courtbooker001";
	// private static FileDataStoreFactory DATA_STORE_FACTORY;
	 private static final JsonFactory JSON_FACTORY  =  JacksonFactory.getDefaultInstance();


	    /** Global instance of the scopes required by this quickstart.
	     *
	     * If modifying these scopes, delete your previously saved credentials
	     * at ~/.credentials/gmail-java-quickstart.json
	     */
	    private static final List<String> SCOPES =
	        Arrays.asList(GmailScopes.GMAIL_LABELS);

	    static {
	        try {
	            HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
	            //DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
	        } catch (Throwable t) {
	            t.printStackTrace();
	            System.exit(1);
	        }
	    }
	
	public static void send(String subject, String body) throws IOException{
		Gmail gmail = getGmailService();
		
		MimeMessage email = createEmail("s.gurprit@gmail.com","courtbooker001@appspot.gserviceaccount.com", "test", "test");
		sendMessage(gmail, "me", email);
	}
	
	public static void send(Gmail gmail,Exception ex) throws IOException {
		   DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		   GmailSender.send(gmail, "Error in booking courts on " + dateFormat.format(new Date()) , ex.getMessage() +"\n"+ ex.getStackTrace().toString() );
	    }
	   
	public static void send(Gmail gmail, String subject, String body) throws IOException{
		//Gmail gmail = getGmailService();
		
		MimeMessage email = createEmail("s.gurprit@gmail.com","courts.booker@gmail.com", subject, body);
		sendMessage(gmail, "me", email);
	}
	
	
	public static Gmail getGmailService() throws IOException {
		GoogleCredential credential = GoogleCredential.getApplicationDefault();
		Collection GMAIL_SCOPES =
			    Collections.singletonList(GmailScopes.GMAIL_SEND);
			if (credential.createScopedRequired()) {
			    credential = credential.createScoped(GMAIL_SCOPES);
			}
			System.out.println (credential.getServiceAccountId());
	        return new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
	                .setApplicationName(APPLICATION_NAME)
	                .build();
	    }

	
	public static void sendMessage(Gmail service, String userId, MimeMessage email)
      throws IOException {
	    Message message = createMessageWithEmail(email);
	    message = service.users().messages().send(userId, message).execute();
	
	    System.out.println("Message id: " + message.getId());
	    System.out.println(message.toPrettyString());
	}
	
	public static MimeMessage createEmail(String to, String from, String subject,
		      String bodyText) throws IOException {
		 MimeMessage email = null;
		try{
	    Properties props = new Properties();
	    Session session = Session.getDefaultInstance(props, null);

	     email = new MimeMessage(session);
	    InternetAddress tAddress = new InternetAddress(to);
	    InternetAddress fAddress = new InternetAddress(from);

	    email.setFrom(new InternetAddress(from));
	    email.addRecipient(javax.mail.Message.RecipientType.TO,
	                       new InternetAddress(to));
	    email.setSubject(subject);
	    email.setText(bodyText);
		} catch ( MessagingException mex){
			throw new IOException(mex);
		}
	    return email;
	 }
	public static Message createMessageWithEmail(MimeMessage email)
		      throws IOException {
		Message message = new Message();
		try{
		    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
		    email.writeTo(bytes);
		    String encodedEmail = Base64.encodeBase64URLSafeString(bytes.toByteArray());
		    
		    message.setRaw(encodedEmail);
		} catch ( MessagingException mex){
			throw new IOException(mex);
		}
		    return message;
		  }
}
