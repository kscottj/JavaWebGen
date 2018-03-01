/*
 * =================================================================== *
 * Copyright (c) 2017 Kevin Scott All rights  reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 * if any, must include the following acknowledgment:
 * "This product includes software developed by "Kevin Scott"
 * Alternately, this acknowledgment may appear in the software itself,
 * if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "Kevin Scott must not be used to endorse or promote products
 * derived from this software without prior written permission. For
 * written permission, please contact kevscott_tx@yahoo.com
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL KEVIN SCOTT BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
package org.javaWebGen;

import org.javaWebGen.config.WebConst;

/**
 * Used by @see Dispatcher to route a request to the new a web controller or page
 * @author kevin
 *
 */
public class ServerAction {
	
	private String postURL=null;
	private String getURL=null;
	private String servletroot=null;
	
	public ServerAction(String postUrl){
		this.postURL=postUrl;
	}
	public ServerAction(){}
	
	public void setServletRoot(String url){
		this.servletroot=url;
	}
	public String getServletRoot(){
		if(servletroot==null){
			return "";
		}
		return this.servletroot;
	}
	/**
	 * get a Instance of the ServerAction class for view actions (HTTP GET)
	 * @param url
	 * @return next Server action
	 */
	public static ServerAction viewAction(String url){
		ServerAction action = new ServerAction();
		action.setGetURL(url);
		return action;
	}
	
	/**
	 * get a Instance of the ServerAction class for update actions (HTML FORMS)
	 * 
	 * @param url
	 * @return next Server action
	 */
	public static ServerAction updateAction(String text){
		return new ServerAction(text);
	}
	/**
	 * get a Instance of the ServerAction class for update actions (REST action)
	 * @param response output text of web service 
	 * @return Server action
	 */
	public static ServerAction webService(String url){
		return new ServerAction(url);
	}
	/**
	 * get next page and return to current URL afterwords 
	 * @param returnObj uri to return to IE controller
	 * @param returnCmd controller method to call on return
	 * @return action to call next 
	 */	
	public static ServerAction getNextAction(String returnObj, String returnCmd, String parm){
		if(returnObj!=null&&returnCmd!=null&&parm!=null){
			return new ServerAction(WebConst.WEB_CONTROLLER+"?page="+returnObj+"&cmd="+returnCmd+parm);
		}else if(returnObj!=null&&returnCmd!=null){
			return new ServerAction(WebConst.WEB_CONTROLLER+"?page="+returnObj+"&cmd="+returnCmd);
		}else{
			return new ServerAction("/");
		}
		
	}

	/**
	 * Controller set this when a HTTP GET request is processed that will not modify data
	 * Such as an HTML page 
	 * The FrameworkController will us A forward when the WebController is finished.
	 * 
	 * @param url for HTTP get
	 */
	
	public void setGetURL(String url){
		this.getURL=url;
		
	}

	/**
	 * Controller set this when process a post request that can modify data
	 * Such as an HTML form that updated the data
	 * The FrameworkController will us A HTTP redirect when the WebController is finished.
	 * This prevents any rePosting data to the form cause by the refresh button	 
	 * @param url for POST
	 */
	public void setPostURL(String url){
		this.postURL=url;
		
	}
	/**
	 * Get the actual URL that is being used in the redirect
	 * @return url
	 */
	public String getPostURL() {
		return postURL;
	}
	/**
	 * Get the actual URL that is being used in the used
	 * @return url
	 */
	public String getGetURL() {
		return getURL;
	}
}
