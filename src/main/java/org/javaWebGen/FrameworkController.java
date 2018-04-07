/*
 Copyright (c) 2003-2006 Kevin Scott

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

import javax.servlet.http.*;
import javax.servlet.*;

import java.util.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.javaWebGen.config.WebConst;
import org.javaWebGen.exception.WebAppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Main application Servlet Controller is a traffic controller that calls
 * on classes that implement the WebController class to do all the work. Someday
 * I will get around to changing it to a filter that uses a SEO friendly URL pattern.  
 * Someday will work more like the Python Django framework with a simple file defines URL mappings  
 * 
 * Use more modern Dispatcher class no more need to pass page= and cmd = on url
 * {@link Dispatcher}
 * 
 * @author Kevin Scott
 * 
 */
@Deprecated 
public class FrameworkController extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6151861766667956551L;
	private static final Logger log = LoggerFactory.getLogger(FrameworkController.class);
	 
	public static final String PAGE = "page";
	public static final String ERROR_PAGE = "/error.jsp";
	public static final String IS_PROD = "isProd";
	public static final String PREFIX = "prefix";
	public static final String JSP_ROOT = "jsp";
	protected HashMap <String,WebController> mapping = new HashMap <String,WebController>();
	protected HashMap <String,Method> methodMap = new HashMap <String,Method>();
	//private static final String xmlFile = "WebAppConfig.xml";
	//private Document root=null;

	private boolean isProd=false;
	private static final WebSession defaultWebSession = new WebSession();
	private String classPrefix = "org.javaWebGen.controller";
	private String jspRoot="";
	private String currentUrl;

	 
	public FrameworkController() {
		// register controllers
		// mapping.put("Login",new LoginProcessor() );
	}
 

	public void setProd(boolean isProd) {
		this.isProd = isProd;
	}


	public void setClassPrefix(String classPrefix) {
		this.classPrefix = classPrefix;
	}


	protected boolean requiresAdmin(String url){
		return true;
	}
	/**
	 * read config file location if it exists xml file location
	 * 
	 * @param config
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		String isProdStr=config.getInitParameter(IS_PROD);
		
		if( isProdStr!=null && isProdStr.equals("true") ){
			this.isProd=true;
		}else{
			this.isProd=false;
		}
		String prefix=config.getInitParameter(PREFIX);
		
		if( prefix!=null && prefix.length()>1 ){
			this.classPrefix=prefix;
		}
	}
	
	/**
	 * 
	 */
	public void doPost(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
		//TODO validate hash 
		 
		serviceRequest( req,  res);
		
		
	}
	/**
	 * 
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		serviceRequest( req,  res);
		
		
	}
	/**
	 * Main web controller entry point calls a registered Web Controller based
	 * on a req. parm called page(page=) jumps to a URL returned by the
	 * WebController
	 * 
	 * @param req
	 * @param res
	 * @throws ServletException
	 * @throws IOException
	 */
	private void serviceRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//String page = req.getParameter("page");
		/*log.info("queryStr="+req.getQueryString());
		log.info("user="+req.getUserPrincipal() );
		log.info("req.uri="+req.getRequestURI() );
		log.info("req.ContextPath="+req.getContextPath() );
		log.info("req.getPathInfo="+req.getPathInfo() );
		log.info("req.getServletPath="+req.getServletPath() );*/
		
		String page=req.getParameter("page");
		String cmd=req.getParameter("cmd");
		dispatch(page,cmd,req,res);
	}
	/**
	 * 
	 * @param page web controller - Action IE Book=BookAction
	 * @param cmd method name to call
	 * @param req
	 * @param res
	 * @throws IOException 
	 * @throws ServletException 
	 */
	public void dispatch(String page,String cmd,HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		Object temp = req.getAttribute(WebConst.WEB_SESSION);
		WebSession ws = null;
		ServerAction action = null;
		if (temp != null) {
			ws = (WebSession) temp;
		} else {
			ws = defaultWebSession;
		}
		
		req.setAttribute(WebConst.WEB_SESSION, ws);
		//session.setAttribute(WebConst.WEB_SESSION,ws);
		String hash =req.getParameter("hash");
		if(hash!=null)
			req.setAttribute("hash",hash);

		ws.setError("");
		if(page==null){
			log.warn("calling default controller HomeAction");
			page="Home";
		}
		if(cmd==null){
			log.warn("calling default index method on controller");
			cmd="index";
		}
		try {			 
			String controllerClass=this.classPrefix+"."+page+"Action";
			WebController controller = this.getController(controllerClass);
			//action = controller.exec(req, res);
	
			action=controller.exec(req, res);
			if(action instanceof WebServiceAction){
				WebServiceAction wsa= (WebServiceAction) action;
				//int size=wsa.getResponse().length();
				res.getWriter().print(wsa.getResponse());
			}else if(action.getPostURL()!=null){
				res.setHeader("P3P", WebConst.P3P_HEADER_VALUE);
				String appPrefix=req.getContextPath();
				action.setPostURL(appPrefix+action.getPostURL());
				action.setServletRoot(req.getContextPath());
				redirect(res,action);
			}else{
				res.setHeader("P3P", WebConst.P3P_HEADER_VALUE);
				jump(req, res, action);
			}
		} catch (WebAppException we) {
			action=new ServerAction(ERROR_PAGE);
			//ws.setError("Unable handle URL", we);
			log.error("WebController Unable handle URL",we);
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} catch (Exception e) {// deal with unknown Exceptions like
			// NullPointers
			action=new ServerAction(ERROR_PAGE);
			//ws.setError(e.getMessage(), e);
			throw new ServletException("Uknown Error",e);
		}
		/*
		if(action==null){
			log.error("queryStr="+req.getQueryString());
			log.error("user="+req.getUserPrincipal() );
			log.error("req.uri="+req.getRequestURI() );
			//action=new ServerAction(ws.getInclude()+ERROR_PAGE);
			//jump(req, res, action);
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			//res.sendError();
			//return;
		}else*/ 

	}
	/**
	 *  Safest method to call another page.  Use when processing an HTML FORM.  Use after a POST or any 
	 *  action that might modify data
	 * @param res
	 * @param action
	 * @throws IOException
	 */
	private void redirect(HttpServletResponse res, ServerAction action) throws IOException{
		log.debug("Jump redirect to " + action.getPostURL() + ">>>>>>>>>>>");
		String url=action.getPostURL();
		 
		//log.debug("Jump to " + url + ">>>>>>>>>>>");
		if (url == null) {
			log.error("url was [NULL] redirect to ''>>>>>>>>>>>");
			url = "";
		}else if( url.indexOf("/")==1){
			url=action.getServletRoot()+url;
		}
		this.currentUrl=url;
		res.sendRedirect(url);
	}
	/**
	 * jump to next page.  only use this if calling a page/url that does not modify data.
	 * forwards to next controller command
	 * 
	 * @param url
	 */
	private void jump(HttpServletRequest req, HttpServletResponse res,
			ServerAction action) throws ServletException, IOException {
		String url=this.jspRoot+action.getGetURL();
		log.debug("Jump to " + url + ">>>>>>>>>>>");
		if (url == null ||url.isEmpty()) {
			log.info("url was [NULL] Jump to ''>>>>>>>>>>>");
			url = this.jspRoot+"/";
		}
		this.currentUrl=url;
		RequestDispatcher rd = req.getRequestDispatcher(url);
		if(rd!=null){
			rd.forward(req, res);
		}else{
			log.warn("RequestDispatcher=[null] shold only see this in a UNIT TEST");
		}
	}

	/**
	 * map controllers based on a class name
	 * 
	 * @param class name IE Page=
	 */
	private void mapController(String className) throws WebAppException {

		 
		try {
			log.debug("try to create Instance of classname=" + className);
			Class<?>  cl =  Class.forName(className)  ;
			Object obj = cl.newInstance();
			if(	obj instanceof WebController){
				log.debug("map "+obj.getClass() );
				WebController wc = (WebController) obj;
				wc.setJspRoot(this.jspRoot);
				mapping.put(className, wc);
			}else{
				log.error(className+" is not a WebControler ignoring");
			}
			
		} catch (Exception e) {
			log.error("Error creating class"+className, e);
		}
	}
	/**
	 * execute command from on controller
	 * @param req
	 * @param res
	 * @param controller to execute command
	 * @param cmdStr command to pass to controller
	 * @return server action IE next page to display
	 * @throws WebAppException
	 */
	public ServerAction callCmd(HttpServletRequest req, HttpServletResponse res, WebController controller, String cmdStr) throws WebAppException{
		log.debug(">>>>callCmd(req,res"+controller+","+cmdStr);
		if(controller==null || cmdStr==null){
			return null;
		}
		Method m=methodMap.get(cmdStr);
		Object o;
		 
			
			try {
				m = controller.getClass().getMethod(cmdStr,HttpServletRequest.class, HttpServletResponse.class );
					
				if(m!=null && this.isProd  ){
					methodMap.put(cmdStr, m);
				}
					log.debug("!!!invoke "+controller.getClass()+" ."+cmdStr) ;
					o=m.invoke(controller, req, res);
					
					//log.debug("!!!invoked returned = "+o ) ;
					return (ServerAction) o;
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				log.error("Error dynanicly calling controller ="+controller+"."+cmdStr);
				throw new WebAppException(WebAppException.VALIDATE_ERROR,e);
			}

		
	}
	/**
	 * return Controller by class name page= on URL
	 * 
	 * @param page name IE class anem
	 * @return webcontroller
	 */
	private WebController getController(String name) throws WebAppException {
		WebController control = (WebController) mapping.get(name);
		if (control == null) {
			mapController(name);
			control =  mapping.get(name);
		}
		return control;
	}

	/**
	 * Warning use for unit testing only.  This is not thread safe
	 * @return current URL the router will return.  W
	 */
	public String currentTestUrl(){
		return this.currentUrl;
	}
}