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

import javax.servlet.http.*;
import javax.servlet.*;
import java.util.*;
import javax.servlet.ServletException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.javaWebGen.config.WebConst;
import org.javaWebGen.exception.WebAppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Main application traffic controller that calls
 * on classes that implement the WebController class to do all the work. Example /Dispatcher/test/MockForm/detail would call
 * the class com.kscott.test.MockFormAction calling the method named detail.  If no controller is found it returns an HTTP 404.
 * Use with Router Filter class {@link Router} if you wish to direct all request to the framework to use more SEO friendly URLs
 * Someday will work more like the Python Django framework with a simple file defines URL mappings urls.properties 
 * 
 * @author Kevin Scott

 * 
 */
public class Dispatcher  {

	private static final Logger log = LoggerFactory.getLogger(Dispatcher.class);
	
	public static final String ERROR_PAGE = "/error.jsp";
	public static final String IS_PROD = "isProd";
	public static final String PREFIX = "prefix";
	public static final String JSP_ROOT = "jspRoot";
	public static final String DEFAULT_CLASS_PREFIX = "org.javaWebGen";
	public static final boolean DEFAULT_IS_PROD = false;
	public static final String DEFAULT_JSP_ROOT = "/WEB-INF/jsp";
	protected HashMap <String,WebController> mapping = new HashMap <String,WebController>();
	private boolean isProd=DEFAULT_IS_PROD;
	private String classPrefix = DEFAULT_CLASS_PREFIX;
	private String jspRoot=DEFAULT_JSP_ROOT;
	
	/**user for unit test only*/
	private String currentUrl=null;
	/**unit for test only*/
	private WebController currentController=null;
	private static Dispatcher dispatcher=null;  //instance
	private Properties urlProp=null;
	private HashMap<String,String> prodMapping=null;
	 
	/**
	 * Constructor
	 */
	public Dispatcher( ) {
		urlProp=this.findUrlProp();
		init();

	}

	/**
	 * 
	 * @param classPrefix package name prefix IE org.javawebgen
	 * @param isProd
	 */
	public Dispatcher(String classPrefix, boolean isProd) {
		this.urlProp=this.findUrlProp();
		this.classPrefix=classPrefix;
		this.isProd=isProd;
		init();
	}
	

	
	/**
	 *  Get Dispatcher instance
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
	 * Get Dispatcher instance
	 * @return instance to dispatcher
	 */
	public  static  synchronized  Dispatcher  getInstance (){
 
		if(dispatcher==null){
			dispatcher= new Dispatcher(Dispatcher.DEFAULT_CLASS_PREFIX,Dispatcher.DEFAULT_IS_PROD);
		}
			
		return dispatcher;
	}
	
	/**
	 * load URLs and methods into methodMap
	 */
	private void loadUrls() {
		Enumeration<Object> ke=urlProp.keys();
		while(ke.hasMoreElements()) {
			String key=ke.nextElement()+"";
			String value=urlProp.getProperty(key.toString() );
				prodMapping.put(key, value);
		}
		
	}

	public void init() {
		loadUrls();
	}
	/**
	 * Dispatch to correct web controller
	 * @param webName controller name to call
	 * @param method to run
	 * @param req http
	 * @param res http
	 * @throws IOException IO error
	 * @throws ServletException generic error
	 */
	protected void dispatch(String webName,String method,HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		
		ServerAction action = null;
		String hash =req.getParameter(WebConst.LINK_HASH);
		String controllerClass=null;
		if(hash!=null) {
			req.setAttribute("hash",hash);
		}
 
		if(isProd) {
			controllerClass =this.prodMapping.get(webName);
		}else {
			controllerClass=this.classPrefix+"."+webName+"Action";
		}
		if(req.getServletPath().endsWith("/")) {
			this.jump(req, res, ServerAction.viewAction(req.getServletPath()+"index.jsp"));
			return;
		}else if(req.getServletPath().endsWith("index.jsp")){
			this.jump(req, res, ServerAction.viewAction(req.getServletPath() ));
			return;			
		}
				 
		try {
			WebController controller = this.getController(controllerClass);
			this.currentController = controller;//only used for unittesting
			//action = controller.exec(req, res);
			action =invokeWebController(req,res,controller,method);
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
				HashMap<String,String> hmap= wsa.getAdditionalHeaders();
				for(String key:hmap.keySet()) {
					String value=hmap.get(key);
					res.setHeader(key, value);
				}
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
			log.info("WebController NOT found|"+webName+"#"+method);
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
					
					//wc.setJspRoot(this.jspRoot);
					WebController wc = (WebController) obj;
					mapping.put(className, wc);
				}else{
					log.error(className+" is not a WebControler ignoring");
				}
				
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
				log.info("Error calling Controller:"+className+" exception="+e);
			}
	}
	/**
	 * Execute a method on a web controller Action class and transfer control to it.  Creates new instance before calling.
	 * Webcontrollers should be considered thread safe.
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
	private ServerAction invokeWebController(HttpServletRequest req, HttpServletResponse res, WebController controller, String methodName) throws IllegalAccessException, IllegalArgumentException,InvocationTargetException,WebAppException {
		log.debug("Invoke "+controller+"#"+methodName);
	 
		if(controller==null || methodName==null ){
			throw new WebAppException (WebAppException.Controller_ERROR,"");
		}
		
		Object o;
		try {
			WebController wc = (WebController) controller.getClass().newInstance();
			wc.setJspRoot(this.jspRoot);		
			Method m = wc.getClass().getMethod(methodName,HttpServletRequest.class, HttpServletResponse.class );
			o=m.invoke(wc, req, res);
		} catch (NoSuchMethodException | SecurityException | InstantiationException  e) {
			throw new WebAppException (WebAppException.Controller_ERROR,e);
		}
		//log.debug("!!!invoke "+controller.getClass()+" ."+cmdStr) ;
		
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
	public String getJspRoot() {
		return jspRoot;
	}
	public void setJspRoot(String jspRoot) {
		this.jspRoot = jspRoot;
	}
	public boolean isProd() {
		return isProd;
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

	public Properties getUrlProp() {
		return urlProp;
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
}