package com.utilties;

import java.util.List;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlLink;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.html.HTMLLinkElement;
import com.utilties.util.EmailSender;
import com.utilties.util.CourtUtil;

public class CourtBooker {
	private CourtUtil htmlUtil = new CourtUtil();
	
//	 private String[] courtTimes = {
//			 "bcsynp.php?a=56|720|80|9|",
//			 "bcsynp.php?a=56|750|80|9|",
//			 "bcsynp.php?a=56|780|80|9|",
//			 "bcsynp.php?a=56|810|80|9|"
//	 };
	 
	 private int[] courtTimes = {30,31,54,55,56,57};
	 
	 private String url = "bcsynp.php?a=";
	 private String end = "|73|9|";
	 private int courtNumber;
	
	 public CourtBooker (String userid, String password, int courtNumber, int startTime)  {
		 this.userid= userid;
		 this.password = password;
		 this.courtNumber= courtNumber;
		 this.startTime = startTime;
	 
	 }
	 
	 public void book () throws Exception {
	 
	  HtmlForm menuForm= htmlUtil.login(userid, password);
	  
	 
	  //Calendar Page
	  HtmlPage calendarPage =htmlUtil.selectCourt(menuForm);
	  HtmlPage calendarPage2Weeks = htmlUtil.nextWeek(htmlUtil.nextWeek(calendarPage));
	  int numberSlot = 4;
	  for (int i=0; i<numberSlot; i++) {
		  String courtTime = url+Integer.toString(courtTimes[courtNumber-1])+"|"+Integer.toString(startTime)+end;
		  calendarPage2Weeks = bookCourt(calendarPage2Weeks, courtTime);
		  startTime = startTime +30;
	
	  }
	 
	 }
	 
	
	 
	 private HtmlPage bookCourt(HtmlPage calendarPage, String courtTime) throws Exception {
		 HtmlForm calendarForm = (HtmlForm) calendarPage.getFormByName("Form");
		  List<HtmlAnchor> links = calendarForm.getElementsByAttribute("a", "HREF", courtTime); 
		  System.out.println("Number of links for "+ courtTime +" are " + links.size());
		  
		  if(links.size() ==0){
			  throw new Exception("- Court is already booked");
		  }
		  
		  // Confirmation Page
		  HtmlPage confirmPage = (HtmlPage) links.get(0).click();
		  HtmlForm confirmForm = (HtmlForm) confirmPage.getFormByName("Form");
		  HtmlSubmitInput acceptBtn = (HtmlSubmitInput) confirmForm.getInputByName("accept");
		  
		  // Go back screen
		  
		  HtmlPage goBackPage = (HtmlPage) acceptBtn.click();
		  HtmlForm goBackForm = (HtmlForm) goBackPage.getFormByName("Form");
		  HtmlSubmitInput okBtn = (HtmlSubmitInput) goBackForm.getInputByName("BtnOK");
		  
		  HtmlPage resultPage = (HtmlPage) okBtn.click();
		  return resultPage;
	 }
			 
			
	 
	 public static void main (String args[]) throws Exception {
	  
		 try {
			 CourtBooker booker1 = new CourtBooker ("bhatti","143113141",5,720 );
			 booker1.book ();
			 CourtBooker booker2 = new CourtBooker ("singh","gpsingh",5,840 );
			 booker2.book ();
		 } catch (Exception ex){
			 EmailSender.send(ex);
		 }
	 
	 }
	 
	 private int startTime;
	 private String userid;
	 private String password;
	 
	 public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

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

	public int getCourtNumber() {
		return courtNumber;
	}

	public void setCourtNumber(int courtNumber) {
		this.courtNumber = courtNumber;
	}

	 
}
