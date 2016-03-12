package com.utilties;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gargoylesoftware.htmlunit.javascript.host.MessageEvent;
import com.utilties.util.EmailSender;

public class CourtCancelServlet extends HttpServlet{
	 @Override
	    public void doPost(HttpServletRequest req, HttpServletResponse resp) 
	            throws IOException { 
		 String dateStr="";
		 try{
	                Properties props = new Properties(); 
	                Session session = Session.getDefaultInstance(props, null); 
	                MimeMessage message = new MimeMessage(session, req.getInputStream());
	                String subject = message.getSubject();
	                Pattern pat = Pattern.compile("\\d{2}-\\d{2}-\\d{4}");
	                Matcher mat = pat.matcher(subject);
	                while(mat.find()){
	                    dateStr = mat.group();
	                 }
	                if (!dateStr.isEmpty()){
	                	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	                	CourtCancel canceller= new CourtCancel();
	                	canceller.cancelDay(sdf.parse(dateStr));
	                }
	                
	                
	                
		 } catch  (Exception mex){
			 try {
			 EmailSender.send("Error in cancelling courts on " + dateStr ,mex);
			 } catch (Exception ex){
				 throw new IOException(ex);
			 }
		 }
		 
	 }
}
