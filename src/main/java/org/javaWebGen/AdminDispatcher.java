package org.javaWebGen;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AdminDispatcher extends Dispatcher{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4368718409126062439L;
	private static final Logger log = LoggerFactory.getLogger(AdminDispatcher.class);
	private static AdminDispatcher dispatcher=null;
	private static String  classPrefix="org.javaWebGen";
	private boolean isProdMode=false;
	
	public AdminDispatcher(boolean isProd) {
		super(classPrefix,isProd);
		isProdMode=isProd;
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
	 
		
		
		classPrefix=config.getInitParameter(PREFIX);
		

	}


	/**
	 * 
	 * @param isProd is Prod or not
	 * @return dispatcher reference
	 */
	public  static  synchronized  Dispatcher  getInstance (boolean isProd){
 
		if(dispatcher==null){
			dispatcher= new AdminDispatcher(isProd);
		}
			
		return dispatcher;
	}
	
	/**
	 * 
	 * @param page web controller - Action IE Book=BookAction
	 * @param cmd method name to call
	 * @param req
	 * @param res
	 * @throws IOException IO error
	 * @throws ServletException  generic error
	 */
	protected void dispatch(String page,String cmd,HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException{
		super.dispatch(page, cmd, req, res);
		log.info("User "+req.getUserPrincipal()+" is attenpting to use admin pages IP="+req.getRemoteAddr()+" proxy IP="+req.getHeader("X-FORWARDED-FOR") );
		if(req.getUserPrincipal()==null) {  
			throw new ServletException("Admin page accessed without login user page="+page+" cmd="+cmd+"IP="+req.getRemoteAddr()+" proxy IP="+req.getHeader("X-FORWARDED-FOR") );
		}
		if(this.isProdMode) {
			throw new ServletException("Admin pages should not be deployed to run in prod mode="+page+" cmd="+cmd+"IP="+req.getRemoteAddr()+" proxy IP="+req.getHeader("X-FORWARDED-FOR") );
		}
	
	}

}
