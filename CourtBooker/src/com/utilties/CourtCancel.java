package com.utilties;

import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import  java.util.GregorianCalendar;
import java.util.List;

import com.utilties.util.EmailSender;
import com.utilties.util.CourtUtil;

import java.util.Date;
public class CourtCancel {
	private CourtUtil htmlUtil = new CourtUtil();
	
	public CourtCancel(String userId, String password){
		this.userid = userId;
		this.password = password;
	}
	 
	public ArrayList <Court> isBooked (Date date) throws Exception
	{
		
		ArrayList<Court> bookedCourts = new ArrayList<Court>();
		HtmlForm menuForm= htmlUtil.login(userid, password);
		//Calendar Page
		HtmlPage calendarPageToday =htmlUtil.selectCourt(menuForm);
		HtmlPage calendarPageTarget =  htmlUtil.moveTo(date, calendarPageToday);
		

		  for(int courtNumber : Court.courtArray){
			  for (int courtTime = CourtUtil.START_TIME; courtTime<=CourtUtil.END_TIME; courtTime=courtTime+30){
				  HtmlForm calendarForm = (HtmlForm) calendarPageTarget.getFormByName("Form");
				  String url = "vbdt.php?a="+String.valueOf(courtNumber)+"|"+String.valueOf(courtTime)+"|";
				//  System.out.println("URL is " + url);
				  List<HtmlAnchor> links = calendarForm.getElementsByAttribute("a", "HREF", url); 
				  if(links.size()>0){
					  
					  bookedCourts.add(new Court(courtTime,courtNumber));
				  }
			  }
		  }
		 
	  // update the flag on last court
		  if(bookedCourts.size()>1){
			bookedCourts.get(bookedCourts.size()-1).setLast(true);
		  }
		  
		return bookedCourts;
	}
	

	
	public void cancelDay(Date date) throws Exception
	{
		 
		  HtmlForm menuForm= htmlUtil.login(userid, password);
		  //Calendar Page
		  HtmlPage calendarPageToday =htmlUtil.selectCourt(menuForm);
		  HtmlPage calendarPageTarget =  htmlUtil.moveTo(date, calendarPageToday);
		  
		
		  for(int courtNumber : Court.courtArray){
			  for (int courtTime = CourtUtil.START_TIME; courtTime<=CourtUtil.END_TIME; courtTime=courtTime+30){
				  HtmlForm calendarForm = (HtmlForm) calendarPageTarget.getFormByName("Form");
				  String url = "vbdt.php?a="+String.valueOf(courtNumber)+"|"+String.valueOf(courtTime)+"|";
				//  System.out.println("URL is " + url);
				  List<HtmlAnchor> links = calendarForm.getElementsByAttribute("a", "HREF", url); 
				  if(links.size()>0){
					  System.out.println("Cancelling " + url);
					  calendarPageTarget = cancelCourt(links.get(0));
				  }
			  }
		}
		  
		
	}
	
	public HtmlPage cancelCourt (HtmlAnchor link) throws Exception{
		
		  // Confirmation Page
		  HtmlPage confirmPage = (HtmlPage) link.click();
		  HtmlForm confirmForm = (HtmlForm) confirmPage.getFormByName("Form");
		  HtmlSubmitInput cancelBtn = (HtmlSubmitInput) confirmForm.getInputByName("cancel");
		  
		  // Go back screen
		  
		  HtmlPage goBackPage = (HtmlPage) cancelBtn.click();
		  HtmlForm goBackForm = (HtmlForm) goBackPage.getFormByName("Form");
		  HtmlSubmitInput okBtn = (HtmlSubmitInput) goBackForm.getInputByName("BtnCancelBooking");
		  
		  HtmlPage resultPage = (HtmlPage) okBtn.click();
		  return resultPage;
	}
	
//	public void cancelDay(){
//		
//	}
	
	 private String userid;
	 private String password;
	 
	
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
	
	 public static void main (String args[]) throws Exception {
		  
//		 try {
//			 CourtCancel canceller = new CourtCancel ();
//			 SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
//			 Date date = sdf.parse("13-03-2016");
//			 canceller.cancelDay(date);
//		 } catch (Exception ex){
//			 EmailSender.send(ex);
//		 }
//	  
	 }
}
