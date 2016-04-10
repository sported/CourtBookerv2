package com.utilties.util;

import java.util.Date;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class CourtUtil {
	public static int START_TIME = 420;
	public static int END_TIME = 1240;
//	public static int[] COURT_NUMBERS = {30,31,54,55,56,57};
	public static String LOGIN_URL  = "http://mail.theparklangleyclub.co.uk:82/source1/index.php";
	
	public HtmlForm login (String userId, String password ) throws Exception
	{
		  WebClient wb = new WebClient ();
		  HtmlPage p = (HtmlPage) wb.getPage(LOGIN_URL);
		 
		  HtmlForm f = p.getFormByName("Form");
		  HtmlTextInput logonU = (HtmlTextInput) f.getInputByName("LogonU");
		  HtmlTextInput logonPW = (HtmlTextInput) f.getInputByName("LogonPW");
		  HtmlSubmitInput submit = (HtmlSubmitInput) f.getInputByName("logonSubmit");
		  
		  logonU.setValueAttribute(userId);
		  logonPW.setValueAttribute(password);
		  
//		  logonU.setValueAttribute("bhatti");
//		  logonPW.setValueAttribute("143113141");
		  
		  HtmlPage menuPage = (HtmlPage) submit.click();
		  HtmlForm menuForm= menuPage.getFormByName("Form");
		  return menuForm;
	}
	
	public HtmlPage selectCourt (HtmlForm menuPage ) throws Exception
	{
		 HtmlSubmitInput courtBkngBtn = (HtmlSubmitInput) menuPage.getInputByName("BtnCourtBookings");
		  
		  // Select Court Page
		  HtmlPage courtPage = (HtmlPage) courtBkngBtn.click();
		  HtmlForm courtForm= courtPage.getFormByName("Form");
		  
		 // HtmlSubmitInput croydonCourtBtn = (HtmlSubmitInput) courtForm.getInputByName("BtnCourtGroupIDArray[7]");
		  
		  HtmlSubmitInput willetWayBtn = (HtmlSubmitInput) courtForm.getInputByName("BtnCourtGroupIDArray[17]"); 
		  //Calendar Page
		  HtmlPage calendarPage = (HtmlPage) willetWayBtn.click();
		  return calendarPage;

	}
	 public HtmlPage nextWeek(HtmlPage calendarPage) throws Exception {
		  HtmlForm calendarForm = (HtmlForm) calendarPage.getFormByName("Form");
		  HtmlSubmitInput nextWeekBtn = (HtmlSubmitInput) calendarForm.getInputByName("BtnNextWeek");
		  HtmlPage nextWeekCalendar = (HtmlPage) nextWeekBtn.click();
		  return nextWeekCalendar;
	 }
	 public HtmlPage nextDay(HtmlPage calendarPage) throws Exception {
		  HtmlForm calendarForm = (HtmlForm) calendarPage.getFormByName("Form");
		  HtmlSubmitInput nextDayBtn = (HtmlSubmitInput) calendarForm.getInputByName("BtnNextDay");
		  HtmlPage nextDayCalendar = (HtmlPage) nextDayBtn.click();
		  return nextDayCalendar;
	 }
	 public HtmlPage moveTo(Date date, HtmlPage calendarPage) throws Exception {
		 HtmlPage calendarPageTarget =  calendarPage;
		 double noDaysdouble = (double)(date.getTime() - new Date().getTime())/(1000*3600*24);
		  int noDays = (int) Math.ceil(noDaysdouble);
		  System.out.println("Moving " + noDays +" days forward");
		  for (int i=0; i<noDays; i++){
			  calendarPageTarget = this.nextDay( calendarPageTarget);
		  }
		  return calendarPageTarget;
	 }
		public String convertTime(Integer itime, boolean lastSlot){
			StringBuilder strTime = new StringBuilder();
			if(lastSlot){
				itime = itime+30;
			}
			float fTime = (float) itime.floatValue()/60;
			int hour = (int) fTime;
			int min = (int)((fTime-hour)*60);
			strTime.append(hour);
			strTime.append(":");
			if(min>0) {
				strTime.append(min);
			} else {
				strTime.append("00");
			}
			return strTime.toString();
		}
		
	  
}
