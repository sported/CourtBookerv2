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
	
	 private String[] courtTimes = {
			 "bcsynp.php?a=56|720|80|9|",
			 "bcsynp.php?a=56|750|80|9|",
			 "bcsynp.php?a=56|780|80|9|",
			 "bcsynp.php?a=56|810|80|9|"
	 };
	 public CourtBooker (String url) throws Exception {
	 
	 }
	 
	 public void book () throws Exception {
	 
	  HtmlForm menuForm= htmlUtil.login();
	  
	 
	  //Calendar Page
	  HtmlPage calendarPage =htmlUtil.selectCourt(menuForm);
	  HtmlPage calendarPage2Weeks = htmlUtil.nextWeek(htmlUtil.nextWeek(calendarPage));
	  for (String courtTime:courtTimes) {
		  calendarPage2Weeks = bookCourt(calendarPage2Weeks, courtTime);
		//  Thread.sleep(2000);
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
		 CourtBooker booker = new CourtBooker ("http://mail.theparklangleyclub.co.uk:82/source1/index.php");
		 booker.book ();
		 } catch (Exception ex){
			 EmailSender.send(ex);
		 }
	  
	 
	 }
	 
}
