
/*
Copyright (c) 2004 Kevin Scott

Permission is hereby granted, free of charge, to any person obtaining a copy of 
this software and associated documentation files (the "Software"), to deal in 
the Software without restriction, including without limitation the rights to 
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do 
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
SOFTWARE.
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Title: WebController</p>
 * <p>Description: Generic Web based controller that should be extended to
 * process web forms using the correct model classes for the business logic</p>
 * @author Kevin Scott
 * @version $Revision: 1.0 $
 */
public abstract class WebController {
	private static final Logger log = LoggerFactory.getLogger(WebController.class);
	private static String jspRoot="/WEB-INF/jsp";

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
   * get WebSession object from session
   * @param session http
   * @return web session
   */
  @Deprecated
  protected WebSession getWebSession(HttpSession session) {
    Object temp = session.getAttribute(WebConst.WEB_SESSION);
    WebSession ws = null;
    if (temp != null) {
      ws = (WebSession) temp;
      ws.clearError();
      log.debug("ws="+ws.toXml() );
    }else{
    	ws = new WebSession();
    	session.setAttribute(WebConst.WEB_SESSION,ws);
    }
    log.debug("WebSession="+ws.toXml() );
    return ws;
  }

  /**
   * get WebSession object from session
   * @param req HTTP request
   * @return web session
   */
  @Deprecated
  protected WebSession getWebSession(HttpServletRequest req) {
	  HttpSession session=getSession(req);
	  Object temp=session.getAttribute(WebConst.WEB_SESSION);
	  if(temp!=null){
		  return (WebSession) temp;
	  }else{
		  return new WebSession();
	  }
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
			//log.info("user agent session does not match kick them out");
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
   * validate CSRF hash from request against value in sesion
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
   *Get the request page on the request
   *@param req request
   *@return page string
   *******************************************************************************/
   protected static Object getFormBean(HttpServletRequest req){
   	Object obj=req.getAttribute(WebConst.FORM_BEAN);
   	return obj;
   }
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