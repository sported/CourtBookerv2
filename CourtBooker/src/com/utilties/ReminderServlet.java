package com.utilties;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.services.gmail.Gmail;
import com.utilties.util.EmailSender;
import com.utilties.util.gmail.GmailSender;
import com.utilties.util.gmail.Utils;

public class ReminderServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		try {
			 ReminderSender reminder = new ReminderSender();
			 String subject = reminder.isBookedTomorrow();
			 if(!subject.isEmpty()){
				String body = "Reply to cancel the booking!";
				System.out.println("Sending email " +subject);
//				AuthorizationCodeFlow authFlow = initializeFlow();
//			    Credential credential = authFlow.loadCredential(getUserId(req));
//			    // Build the Plus object using the credentials
//			    Gmail gmail = new Gmail.Builder(
//			        Utils.HTTP_TRANSPORT, Utils.JSON_FACTORY, credential).setApplicationName("").build();
			    // Make the API call
				EmailSender.send(subject,body);
			 }
			 } catch (Exception ex){
				 ex.printStackTrace();
//				 EmailSender.send(ex);
				 throw new IOException(ex);
			 }
	
	}
	
//	  @Override
//	  protected AuthorizationCodeFlow initializeFlow() throws ServletException, IOException {
//	    return Utils.initializeFlow();
//	  }
//
//	  @Override
//	  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
//	    return Utils.getRedirectUri(req);
//	  }

}
