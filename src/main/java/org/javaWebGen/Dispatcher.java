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

import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;
import javax.servlet.ServletException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.apache.commons.text.RandomStringGenerator;
import org.javaWebGen.config.WebConst;
import org.javaWebGen.exception.WebAppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Main application Servlet Controller is a traffic controller that calls
 * on classes that implement the WebController class to do all the work. Example /Dispatcher/test/MockForm/detail would call
 * the class com.kscott.test.MockForm calling the method named detail.  If no controller is found it returns an HTTP 404.
 * Use with Router Filter class {@link Router} if you wish to direct all request to the framework to use more SEO friendly URLs
 * Someday will work more like the Python Django framework with a simple file defines URL mappings urls.properties 
 * 
 * @author Kevin Scott

 * 
 */

public class Dispatcher extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6151861766667956551L;
	private static final Logger log = LoggerFactory.getLogger(Dispatcher.class);
	
	public static final String ERROR_PAGE = "/error.jsp";
	public static final String IS_PROD = "isProd";
	public static final String PREFIX = "prefix";
	public static final String JSP_ROOT = "jspRoot";
	public static final String DEFAULT_CLASS_PREFIX = "org.javaWebGen";
	public static final boolean DEFAULT_IS_PROD = false;
	
	protected HashMap <String,WebController> mapping = new HashMap <String,WebController>();
	protected HashMap <String,Method> methodMap = new HashMap <String,Method>();

	private boolean isProd=DEFAULT_IS_PROD;
	//private static final WebSession defaultWebSession = new WebSession();
	private String classPrefix = DEFAULT_CLASS_PREFIX;
	private String jspRoot="/WEB-INF/jsp";
	
	/**unit test only*/
	private String currentUrl=null;
	/**unit test only*/
	private WebController currentController=null;
	private static Dispatcher dispatcher=null;  //instance
	private Properties urlProp=null;
	 
	public Dispatcher( ) {
		urlProp=this.findUrlProp();

	}
	public Dispatcher(String classPrefix, boolean isProd) {
		urlProp=this.findUrlProp();
		this.classPrefix=classPrefix;
		this.isProd=isProd;
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
	@Override
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
		String jsproot=config.getInitParameter(JSP_ROOT);
		log.info("parm from web.xml jsproot="+jsproot);
		if( jsproot!=null  && jsproot.length()>0){
			this.jspRoot=jsproot;
		}
		log.info(this+".init()");
		log.info(this+".init() prefix="+this.classPrefix);
		log.info(this+".init() JSP Root="+this.jspRoot);
	}
	/**
	 * 
	 */
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
		serviceRequest( req,  res);
	}
	/**
	 * 
	 */
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		serviceRequest( req,  res);
	}
	/**
	 * Main web controller entry point calls a registered Web Controller based
	 * on the URLjumps to a URL returned by the WebController
	 * 
	 * @param req request
	 * @param res response
	 * @throws ServletException generic error
	 * @throws IOException RW error
	 */
	private void serviceRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//String page = req.getParameter("page");
		/*log.debug("queryStr="+req.getQueryString());
		log.debug("user="+req.getUserPrincipal() );
		log.debug("req.uri="+req.getRequestURI() );
		log.debug("req.ContextPath="+req.getContextPath() );
		log.debug("req.getPathInfo="+req.getPathInfo() );
		log.debug("req.getServletPath="+req.getServletPath() );*/
		//log.debug("req.getPathInfo="+req.getPathInfo() ); 
		
		String uri = req.getPathInfo();
		log.debug(">>>serviceRequest.uri="+uri);
		
		if(uri==null){
			String page=req.getParameter("page");
			//String cmd=req.getParameter("cmd");
			if(page==null){
				
				page="Home";
			}
			
			log.warn("Legacy Webcontroller call exec method.  Should convert SEO freindly URL");
			dispatch(page,"exec",req,res);
		}else{
			String action="";
			String cmd=null;
			if(uri.endsWith("/index.jsp") ){
				this.jump(req, res, ServerAction.viewAction(uri));
				return;
			}
			if(uri.endsWith("/") ){
				log.debug("default to index method");
				uri+="index";
			}
			
			String parms[]=uri.split("/");
			//String queryStr=req.getQueryString();
			//log.debug("prarm[0]="+uri);
	
			if(parms.length==2)  { //single slash default to home
				//log.debug("default to Home");
				action="Home";
				cmd="index";
			}else{
				cmd=parms[parms.length-1];
				
				if(parms.length>1){
					action+=parms[0];
				}
				
				for (int i=1;i<parms.length-1;i++){
					if(i==1){
						action+=parms[i];
					}else{
						action+="."+parms[i];
					}
				}
				if(req.getParameter("page")!=null && req.getParameter("cmd")!=null ){
					log.warn("legacy cmd update this to SEO friendly page="+req.getParameter("page")+"&cmd="+req.getParameter("cmd") );
					dispatch(req.getParameter("page"),req.getParameter("cmd"),req,res);
				}
			
			}
			dispatch(action,cmd,req,res);
		}
		//log.debug("action="+action+"&cmd="+cmd);
	

	}

	/**
	 * Dispatch to correct web controller
	 * @param page controller name to call
	 * @param cmd command IE method to run
	 * @param req 
	 * @param res 
	 * @throws IOException IO error
	 * @throws ServletException generic error
	 */
	protected void dispatch(String page,String cmd,HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		
		ServerAction action = null;
	
		String hash =req.getParameter(WebConst.LINK_HASH);
		if(hash!=null)
			req.setAttribute("hash",hash); 
		//log.debug(req.getServletPath());
		if(req.getServletPath().endsWith("/")) {
			this.jump(req, res, ServerAction.viewAction(req.getServletPath()+"index.jsp"));
			return;
		}else if(req.getServletPath().endsWith("index.jsp")){
			this.jump(req, res, ServerAction.viewAction(req.getServletPath() ));
			return;			
		}
		try {			 
			String controllerClass=this.classPrefix+"."+page+"Action";
			WebController controller = this.getController(controllerClass);
			this.currentController = controller;//only used for unittesting
			//action = controller.exec(req, res);
			action =invokeWebController(req,res,controller,cmd);
			if(action==null){ //just return controller will handle response
				log.debug("WebController will handle response");
				return;
			}else if(action instanceof WebServiceAction){
				WebServiceAction wsa= (WebServiceAction) action;
				if(wsa.getResponse()==null){
					log.error("response message was [null]");
					return;
				}
				int size=wsa.getResponse().length();
				res.setCharacterEncoding("UTF-8");
				res.setContentLength(size);
				res.setContentType(wsa.getContentType() );
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
		} catch (WebAppException wae){//unable to call web controller return 404
			log.info("WebController NOT found|"+page+"#"+cmd  );
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {// error calling controller method
			Throwable t= e.getCause(); //try to get real cause if any
			if(t!=null){
				log.error("WebController Error|"+t.getMessage() );
				throw new ServletException(t);
			}else{
				log.error("calling WebController|"+e.getMessage() );
				throw new ServletException(e);
			}
		} 
	}
	/**
	 *  Safest method to call another page.  Use when processing an HTML FORM.  Use after a POST or any 
	 *  action that might modify data
	 * @param res http response
	 * @param action to perform
	 * @throws IOException
	 */
	private void redirect(HttpServletResponse res, ServerAction action) throws IOException{
		log.debug("Redirect to >" + action.getPostURL() );
		String url=action.getPostURL();
		 
		//log.debug("Jump to " + url + ">>>>>>>>>>>");
		if (url == null) {
			log.error("url was [NULL] redirect to ''");
			url = "";
		}else if( url.indexOf("/")==1){
			url=action.getServletRoot()+url;
		}
		this.currentUrl=url;
		res.sendRedirect(url);
	}
	/**
	 * Jump to next page.  Only use this if calling a page/url that does not modify data.
	 * forwards to next controller or normally a JSP page
	 * 
	 * @param url
	 */
	private void jump(HttpServletRequest req, HttpServletResponse res,
		ServerAction action) throws ServletException, IOException {
		String url=this.jspRoot+action.getGetURL();
		log.debug("Jump to >" + url );
		if (url == null ||url.isEmpty()) {
			log.info("url was [NULL] Jump to ''");
			url = this.jspRoot+"/";
		}
		this.currentUrl=url;
		RequestDispatcher rd = req.getRequestDispatcher(url);
		if(rd!=null){
			rd.forward(req, res);
		}else{
			log.warn("RequestDispatcher=[NULL] shold only see this in a UNIT TEST");
		}
	}

	/**
	 * map controllers based on a class name
	 * Class name must follow the following rules
	 * <ul>
	 * <li>must be a sub class of WebController</li>
	 * <li>name must end in Action </li>
	 * <li>use the configurable package prefix.  org.javaWebGen by default</li>
	 * </ul>
	 * @param class name IE Page=
	 */
	private void mapController(String className) throws WebAppException {

		 
		try {
			//log.debug("try to create Instance of classname=" + className);
			Class<?>  cl =  Class.forName(className)  ;
			Object obj = cl.newInstance();
			if(	obj instanceof WebController){
				//log.debug("map "+obj.getClass() );
				WebController wc = (WebController) obj;
				wc.setJspRoot(this.jspRoot);
				mapping.put(className, wc);
			}else{
				log.error(className+" is not a WebControler ignoring");
			}
			
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			log.info("Error calling Controller:"+className+" exception="+e);
		}
	}
	/**
	 * Execute a method on a web controller Action class and transfer control to it.
	 * @param req request
	 * @param res response
	 * @param controller to process request
	 * @param method name to call on controller
	 * @return server action IE next page to display
	 * @throws InvocationTargetException error invoking controller
	 * @throws IllegalArgumentException error invoking controller
	 * @throws IllegalAccessException error invoking controller
	 * @throws WebAppException could not find correct class and method
	 */
	private ServerAction invokeWebController(HttpServletRequest req, HttpServletResponse res, WebController controller, String methodName) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, WebAppException {
		log.debug("Invoke "+controller+"#"+methodName);
		if(controller==null || methodName==null){
			throw new WebAppException (WebAppException.Controller_ERROR,"");
		}
		Method m=methodMap.get(methodName);
		Object o;
 
		try {
			m = controller.getClass().getMethod(methodName,HttpServletRequest.class, HttpServletResponse.class );
		} catch (NoSuchMethodException | SecurityException e) {
			throw new WebAppException (WebAppException.Controller_ERROR,e);
		}
			
		if(m!=null && this.isProd  ){
			methodMap.put(methodName, m);
		}
		//log.debug("!!!invoke "+controller.getClass()+" ."+cmdStr) ;
		o=m.invoke(controller, req, res);
		//log.debug("!!!invoked returned = "+o ) ;
		return (ServerAction) o;

	}
	/**
	 * return Controller by class name 
	 * 
	 * @param page name IE class name
	 * @return web controller
	 */
	private WebController getController(String name) throws WebAppException {
		WebController control = mapping.get(name);
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
	/**
	 * Warning use for unit testing only.  This is not thread safe
	 * @return current WebController the router will return.  W
	 */
	public WebController currentCorntroller(){
		return this.currentController;
	}

	/**
	 * find URL mapping
	 * 
	 *  uri= class controller class name
	 * @return properties file with url to class mapping
	 */
	private Properties findUrlProp(){
		urlProp=new Properties();
		try {
			urlProp.load(Dispatcher.class.getResourceAsStream("urls"));
		} catch (Exception e ) {
			log.warn("urls.properties not found using dynamic URIs");
			//this.setProd(false);
		}
		return urlProp;
		
	}
	 /**
	   * return current session. If new session set the following
	   * <ul><li>random seed</li>
	   * 	<li>IP</li>
	   * 	<li>"User-Agent</li></ul>
	   * @param req HTTP
	   * @return current session
	   */

	/**
	 * 
	 * @param classPrefix class prefix
	 * @param isProd
	 * @return instance to dispatcher
	 */
	public  static  synchronized  Dispatcher  getInstance (String classPrefix,boolean isProd){
 
		if(dispatcher==null){
			dispatcher= new Dispatcher(classPrefix,isProd);
		}
			
		return dispatcher;
	}
	/**
	 * 
	 * @return instance to dispatcher
	 */
	public  static  synchronized  Dispatcher  getInstance (){
 
		if(dispatcher==null){
			dispatcher= new Dispatcher(Dispatcher.DEFAULT_CLASS_PREFIX,Dispatcher.DEFAULT_IS_PROD);
		}
			
		return dispatcher;
	}
}