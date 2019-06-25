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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AdminDispatcher extends Dispatcher{


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
	/*@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	 
		
		
		classPrefix=config.getInitParameter(PREFIX);
		

	}*/


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
		/* TODO should i enable this check? It verifies you have a logged in user
		 * if(req.getUserPrincipal()==null) {  
			throw new ServletException("Admin page accessed without login user page="+page+" cmd="+cmd+"IP="+req.getRemoteAddr()+" proxy IP="+req.getHeader("X-FORWARDED-FOR") );
		}*/
		if(this.isProdMode) {
			throw new ServletException("Admin pages should not be deployed to run in prod mode="+page+" cmd="+cmd+"IP="+req.getRemoteAddr()+" proxy IP="+req.getHeader("X-FORWARDED-FOR") );
		}
	
	}

}
