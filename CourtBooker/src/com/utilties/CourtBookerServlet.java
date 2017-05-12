/*
 * Copyright (c) 2013 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.utilties;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.services.gmail.Gmail;
import com.utilties.util.EmailSender;
import com.utilties.util.gmail.GmailSender;
import com.utilties.util.gmail.Utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Entry sevlet for the Plus App Engine Sample. Demonstrates how to make an authenticated API call
 * using OAuth2 helper classes.
 *
 * @author Nick Miceli
 */
public class CourtBookerServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException, ServletException {
	  
		try {
			 CourtBooker booker1 = new CourtBooker ("singh","gpsingh",5,8.5 );
			 booker1.book ();
			 //CourtBooker booker2 = new CourtBooker ("bhatti","143113141",5,12.5 );
			// booker2.book ();
			 resp.getWriter().println("Done");
			 
			 } catch (Exception ex){
			  try{
			    EmailSender.send( ex);
			  }
			    catch (Exception mex){
			    	throw new IOException(mex);
			    }
			 }		    
  }

 
}
