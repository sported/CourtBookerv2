package com.utilties;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.client.utils.DateUtils;

import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.utilties.util.CourtUtil;
import com.utilties.util.EmailSender;
import com.utilties.util.gmail.GmailSender;

public class ReminderSender {
	private CourtUtil courtUtil = new CourtUtil();
	
	public String isBookedTomorrow () throws Exception{
		String subject ="";
		Calendar tomorrowCal = Calendar.getInstance();
		tomorrowCal.add(Calendar.DATE, 1);
		Date tomorrow = tomorrowCal.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    	CourtCancel canceller1= new CourtCancel("singh","gpsingh");
    	ArrayList<Court> bookedTimes = canceller1.isBooked(tomorrow);
    	
    	//CourtCancel canceller2= new CourtCancel("singh","gpsingh");
    	
    	//ArrayList<Court> bookedTimes2 = canceller2.isBooked(tomorrow);
    	//bookedTimes.addAll(bookedTimes2);
		if(bookedTimes.size() >0) {
			 subject =  sdf.format(tomorrow)+ " - Court number " + bookedTimes.get(0).getCourtNumber() + " booked from " +  bookedTimes.get(0).getDisplayTime()
					+ " to " + bookedTimes.get(bookedTimes.size()-1).getDisplayTime();
	
		}
		return subject;
		
	}
	
	public static void main (String args[]) throws Exception {
		  
		 try {
			 ReminderSender reminder = new ReminderSender();
			 String subject = reminder.isBookedTomorrow();
			 if(!subject.isEmpty()){
				String body ="";// "Reply to cancel the booking!";
				System.out.println("Sending email " +subject);
				EmailSender.send(subject,body);
			 }
		 } catch (Exception ex){
			ex.printStackTrace();
		 }
	  
	 }
	
}
