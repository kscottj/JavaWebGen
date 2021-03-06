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

import java.io.IOException;
import java.util.StringTokenizer;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaWebGen.form.CsrfFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Routes SEO friendly URLs.  Mainly forwards controller URLs to Dispatcher class for processing.  Non Controller URLS(in exclude param)
 * are passed for further processing.  Example /test/MockForm/detail would call passed to the dispatcher{@link Dispatcher} for processing.
 * @author home
 *
 */
@WebFilter(
        urlPatterns = "/*",
        initParams = {
        	@WebInitParam(name = "prefix", value = "org.javaWebGen"),
        	@WebInitParam(name = "adminUri", value = "/admin"),	
        	@WebInitParam(name = "prodMode", value = "false"),	
        }
)
public class Router  extends CsrfFilter{

	//private FilterConfig filterConfig;
	private String controller="/Controller";
	private String staticDir="/static/";
	private String adminUri="/admin";
	private String extensionURI=null;

	private static final Logger log = LoggerFactory.getLogger(Router.class);
	
	
	private String[] excludeList ={staticDir,".js",".css",".jsp",".htm","_ah","favicon","robots.txt"};
	private String prefix=null;
	private boolean isProd=false;
	/** not thread safe unit test only */
	private boolean isTestController=false;
	/** not thread safe unit test only */
	private String currentUrl=null;	

	/**
	 * filters URLs and calls a Dispatcher instance to process any controller URIs
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		 
		HttpServletRequest   httpRequest  = (HttpServletRequest)  req;
		HttpServletResponse   httpResponse  = (HttpServletResponse)  res;
		
		this.setupSession(httpRequest);
		String uri =  httpRequest.getServletPath();
		//log.info("doFilter.uri="+uri);
		//boolean isAdmin=isAdminController(uri);

		if(this.isStaticContent(uri) ){
			try{
				//log.debug("<doFilter on static:"+uri );
				chain.doFilter(req, res);
			}catch(IllegalStateException ie){
				log.error("resp alread commit HTTP ERROR TERMINATING filter processing");
			}
		}else if(this.isAdminController(uri) ){
			this.routeAdmin(uri,httpRequest,httpResponse);
		}else {
			this.route(uri,httpRequest,httpResponse);
		}
	}
 
	/**
	 * route URL to webcontroller for processing
	 * @param uri to process
	 * @param req http request
	 * @param res http response
	 * @throws IOException IO error
	 * @throws ServletException web error
	 */
	private void route(String uri,  HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
 
		if(extensionURI!=null) {
			String parms[]=uri.split(extensionURI);
			if(parms[0]!=null) {
				uri=parms[0];
			}
		}
		String action="";
		String method="";
		//log.debug(">route.uri="+uri);	
		String parms[]=uri.split("/");		
		 
		//log.debug("route parm="+parms.length);
		//log.debug(Arrays.toString(parms) );
		if(parms.length==0){
			action="Home";
			method="index";
		}else if(parms.length==1){ //single slash default to home
			//log.debug("default to Home");
			action=parms[0];
			method="index";
			//
		}else if(parms.length==2){
			if(parms[0]==null || parms[1].equals("index.jsp")){
				action="Home";
				method="index";				
			}else{
				action=parms[0];
				method=parms[1];
			}
		}else if(parms.length>2){
			method=parms[parms.length-1];
			
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
		}
		this.currentUrl=uri; //just saving for unit testing
		//log.debug("action="+action+" method="+method);
		Dispatcher.getInstance(this.prefix, isProd).dispatch(action, method, req, res);
		//Dispatcher disp=new Dispatcher(this.prefix,isProd);  //get new instance for each thread
		//disp.dispatch(action, method, req, res);           
	}
	/**
	 * route URL to webcontroller for processing
	 * @param uri to process
	 * @param req http request
	 * @param res http response
	 * @throws IOException IO error
	 * @throws ServletException web error
	 */
	private void routeAdmin(String uri,  HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
		
		String action="";
		String method="";
		log.debug(">routeAdmin.uri="+uri);	
		String parms[]=uri.split("/");		
		 
		//log.debug("route parm="+parms.length);
		//log.debug(Arrays.toString(parms) );
		if(parms.length==0){
			action="Home";
			method="index";
		}else if(parms.length==1){ //single slash default to home
			//log.debug("default to Home");
			action=parms[0];
			method="index";
			//
		}else if(parms.length==2){
			/*if(parms[0]==null || parms[1].equals("index.jsp")){
				action="Home";
				method="index";				
			}else{
				action=parms[0];
				method=parms[1];
			}*/
			action="AdminHome";
			method="index";	
		}else if(parms.length>2){
			method=parms[parms.length-1];
			
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
		}
		this.currentUrl=uri; //just saving for unit testing
		 
		//log.debug("Admin action="+action+" method="+method);
		AdminDispatcher.getInstance(isProd).dispatch(action, method, req, res);
	}

	private boolean isAdminController(String uri) {
		boolean isAdmin=false; 
		//log.info(uri+"?"+this.adminUri+uri.contains(this.adminUri));
		if(uri!=null&& uri.contains(this.adminUri)){
			isAdmin=true;
			log.info("is Admin!");
		}

		return isAdmin;
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if(filterConfig==null){
			return;
		}
		 //this.filterConfig = filterConfig;
		 if(filterConfig.getInitParameter("controller")!=null ){
			 this.controller=filterConfig.getInitParameter("controller");
			 log.info("old style controller URI"+  this.controller);
		 }
		 if(filterConfig.getInitParameter("static")!=null ){
			 this.staticDir=filterConfig.getInitParameter("static");
			 log.info("CUSTOM static DIR="+ this.staticDir);
			
		 }
		/* if(filterConfig.getInitParameter("admin")!=null ){
			 adminUri=filterConfig.getInitParameter("admin");
		 }*/		 
		 if(filterConfig.getInitParameter("exclude")!=null ){
			 String excludeStr=filterConfig.getInitParameter("exclude");
			 log.info("CUSTOM exclude URI="+excludeStr);
			 StringTokenizer tok = new StringTokenizer(excludeStr," ");
			 excludeList=new String[tok.countTokens()+1];
			 excludeList[0]=this.staticDir;
			 for(int i=1;i<tok.countTokens();i++){
				 excludeList[i]=tok.nextToken();
			 }
			 log.info(" exclude dir.count="+excludeList.length);
			 
		 }	
		 
		 if(filterConfig.getInitParameter("prefix")!=null ){
			 prefix=filterConfig.getInitParameter("prefix");
			 log.debug("CUSTOM Controller class prefix="+prefix);
		 }	
		 
		 if(filterConfig.getInitParameter("prodMode")!=null &&  filterConfig.getInitParameter("prodMode").equals("true") ){
			 isProd=true;
		 }	
		 if(filterConfig.getInitParameter("ext")!=null ){
			 
			 extensionURI=filterConfig.getInitParameter("ext");
		 }

	}
	/*private boolean isController(String url){
		boolean isController=true;
		for(String excludeUri:excludeList){
			//log.info(url+"?"+url.contains(excludeUri)+excludeUri  );
			if(url.contains(excludeUri) ){
				log.info("url="+url+"exclude.uri="+excludeUri);
				
				log.info("<isControlle=false"   );
				//log.info("exclude :"+url);
				return false;
			}
		}
		this.isTestController=isController;
		return isController;
	}*/
	/**
	 * 
	 * @param uri to check
	 * @return true is this is static content that anouther filter should handle
	 */
	private boolean isStaticContent(String uri){
		boolean isStatic=false;
		for(String excludeUri:excludeList){
			//log.info(uri+"?"+excludeUri);
			if(uri.contains(excludeUri) ){
				//log.info("uri="+uri+"exclude.uri="+excludeUri);
				
				//log.info("isStatic=true"   );
				//log.info("exclude :"+url);
				return true;
			}
		}
		this.isTestController=false;
		return isStatic;
	}
	/**
	 * Warning use for unit testing only.  This is not thread safe
	 * @return current URL the router will return.  W
	 */
	public String currentTestUrl( ){
		return currentUrl;
	}

	/**
	 * Warning use for unit testing only.  This is not thread safe
	 * @return was URL filtered a controller
	 */
	public boolean isTestController(){
		return this.isTestController;
	}

	public void setPrefix(String prefix) {
		this.prefix=prefix;
		
	}
	public String getPrefix() {
		return this.prefix;
		
	}
}
