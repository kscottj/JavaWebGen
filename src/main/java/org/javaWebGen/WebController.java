/*
 * =================================================================== *
 * Copyright (c) 2018 Kevin Scott All rights  reserved.
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

 
import org.javaWebGen.exception.WebAppException;
import org.javaWebGen.form.HtmlForm;
import org.javaWebGen.util.StringUtil;
import org.javaWebGen.web.LoginBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.javaWebGen.config.WebConst;
import org.javaWebGen.data.FormBeanAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: WebController</p>
 * <p>Description: Generic Web based controller that should be extended to
 * process web forms using the correct model classes for the business logic</p>
 * This class should be thread safe.  Each thread should work with new a Web Controller instance. 
 * @author Kevin Scott
 */
public abstract class WebController {
	private static final Logger log = LoggerFactory.getLogger(WebController.class);
	private static String jspRoot="/WEB-INF/jsp";

  /**
   * 
   */
  public WebController() {}

/**
 * 
 * @param req
 * @param res
 * @return SeverAction get/post for next page
 * @throws WebAppException
 */  
  public ServerAction exec(HttpServletRequest req, HttpServletResponse res) throws WebAppException{  
	  throw new WebAppException(WebAppException.APP_ERROR,"Not inplemented");
  }
  /**
   * get Login bean from request
   * @param req used to get login
   * @return login bean
   */
  public LoginBean getLogin(HttpServletRequest req) {
    Object temp = req.getAttribute(WebConst.LOGIN);
    LoginBean login = null;
    if (temp != null) {
      login = (LoginBean) temp;
    
      log.debug("login="+login.toXML() );
    }else{
    	login = new LoginBean();
    	req.setAttribute(WebConst.LOGIN, login); 
    }
    return login;
  }
  /**
   * Get login object from session.
   * @param session HTTP
   * @return login JavaBean with login info
   */
  protected LoginBean getLogin(HttpSession session) {
	 
    if(session==null) {
    	 log.debug("default login user no session=" );
    	return new LoginBean();
    	
    }
    
    LoginBean login = (LoginBean) session.getAttribute(WebConst.LOGIN);
    log.debug("login="+login);

    return login;
  }

  /**
   * return current session
   * @param req HTTP request
   * @return current session
   */
  protected HttpSession getSession(HttpServletRequest req) {
	 HttpSession session=req.getSession(false);
	 return session;
  }

	/**
	 * Validate current session checks for changes since session was created
	 * might also check hash someday.  This might stop BOTs since they will have different IP and user agents
	 * <ul>
	 * <li>UserAgent</li>
	 * <li>ip</li>
	 * </ul> 
	 * @param req HTTP request
	 */
  public boolean isValidSession(HttpServletRequest req){
	  //log.debug(">validateSession "+req.getRequestedSessionId() );
	  HttpSession session=this.getSession(req);
	 
    //validate session
	  if(req==null||session==null) {
		  log.info("empty session does not match kick them out");
		 
		  return false;
	  }
	  String sagent=(String) session.getAttribute(WebConst.CSRF_AGENT);
	  String sip =(String) session.getAttribute(WebConst.CSRF_IP);
	  String ssead =(String) session.getAttribute(WebConst.CSRF_SEED);
	  if(sagent==null || sip==null||ssead==null) {
			log.info("session attribs are NULL kick them out");
			//session.invalidate();
		  return false;
	  }
		String userAgent = req.getHeader("User-Agent");
		String ip=req.getLocalAddr();
		if(!userAgent.equals(sagent)) {
			log.info("user agent session does not match kick them out");
			//session.invalidate();
			return false;
		}
		if(!sip.equals(ip)) {
			log.info("IP session does not match kick them out");
			//session.invalidate();
			return false;
		}
		log.debug("?isValidSession "+req.getRequestedSessionId()+" true" );
		//return this.validateCsrfHash(req);
		return true;
  }
  /**
   * validate CSRF hash from request against value in session
   * @param req HTTP
   * @return true if valid else false
   */
  public boolean validateCsrfHash(HttpServletRequest req) {
	  HttpSession session=this.getSession(req);
	  String sead=(String) session.getAttribute(WebConst.CSRF_SEED);
	  String nonce=req.getParameter(WebConst.CSRF_NONCE);
	  String rsead=req.getParameter(WebConst.CSRF_SEED);
	  String rhash=req.getParameter(WebConst.CSRF_HASH);
	  
	  if(rhash==null||rsead==null||rhash==null) {
		  return false;
	  }
	  String hash=StringUtil.sha2Base64(nonce+sead);
	  if(rhash.equals(hash)) {
		  return true;
	  }else {
		  return false;  
	  }
	  
  }
  /*******************************************************************************
  *Get the current command passed on the request
  *@param req request
  *@return cmd string
  *******************************************************************************/
  public static String getCmd(HttpServletRequest req){
	  if(req!=null){
		  return req.getParameter(WebConst.CMD);
	  }
  	return null;
  }
  /*******************************************************************************
  *Get the request page on the request
  *@param req request
  *@return page string
  *******************************************************************************/
  public static String getPage(HttpServletRequest req){
  	String page=req.getParameter(WebConst.PAGE);
  	return page;
  }
 
  /*******************************************************************************
   *Get the data bound JTO bean in the request
   *@param req request
   *@return JTO bean
   *******************************************************************************/
   protected static FormBeanAware getFormBean(HttpServletRequest req){
	   FormBeanAware  bean=(FormBeanAware) req.getAttribute(WebConst.FORM_BEAN);
	   return bean;
   }
   /**
    * Get current web form
    * @param req request
    * @return web form
    */
   protected static HtmlForm getForm(HttpServletRequest req){
	   HtmlForm form = (HtmlForm) req.getAttribute(WebConst.FORM);
	   	return form;
   }
   protected static void setForm(HttpServletRequest req, HtmlForm form){
	   req.setAttribute(WebConst.FORM,form);
   }

   protected void setJspRoot(String dir){
		  jspRoot=dir;
   }
   public String getJspRoot(){
	  return jspRoot;
   }

}