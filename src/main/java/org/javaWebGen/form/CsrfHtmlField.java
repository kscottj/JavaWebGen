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
package org.javaWebGen.form;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.javaWebGen.config.MsgConst;
import org.javaWebGen.config.WebConst;

import org.javaWebGen.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Cross site scripting CSRF field.  Using hashing to stop most hacks
 * 
 * hash = base64(sha256(current time+ random seed from session) )
 * WebController should also validate against a values stored in session on important pages @see org.javaWebGen.WebController#isValidSession(HttpServletRequest)
 * @author kevin
 *
 */
public class CsrfHtmlField extends HtmlField{
 
	public static final String FIELD_NAME="org.javaWebGen.form.CsrfHtmlField";
	private static final String INVALID_MESSAGE = "Web form is invalid.  Please try again.";
	public static final String INVALID_MESSAGE_KEY="form.errors.csrf";
	private static final Logger log = LoggerFactory.getLogger(CsrfHtmlField.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 5109955892471971726L;
	private final long time= System.currentTimeMillis();
	private HttpServletRequest req=null;
	
	/**
	 * constructor
	 * @param req request 
	 */
	public CsrfHtmlField(HttpServletRequest req){
		super(FIELD_NAME,true);	
		this.req=req;
	 
		this.setHash(req);
	}
	/**
	 * set value to current hash
	 * @param req request
	 */
	private void setHash(HttpServletRequest req) {
		 HttpSession session = req.getSession(false);
		 if(session!=null&& req!=null) {
			 String sead=(String) session.getAttribute(WebConst.CSRF_SEED);
			 log.debug("set sead="+sead);
			 if(sead!=null) {
				 String hash=makeHash(this.time,sead);
				 log.debug("set hash="+hash);
				 this.setValue(hash);
			 }
		 }else {
			 log.warn("request or sesion is [NULL]");
		 }
		 
		
	}
	/* *
	 * make csrf hash
	 */
	private String makeHash(long time,String seed) {
		return StringUtil.sha2Base64(time+seed);	
	}
	/**
	 * Constructor field is set to required IE must be validated
	 * @param name of field is ignored set to FIELD_NAME always
	 */
	public CsrfHtmlField(String name){
		super(FIELD_NAME,true);	
	}

	/** render field as two hidden HTML fields
	 * 1) timestamp 2) current hash(timestamp+random)
	 */
	@Override
	public String getField() {
		StringBuffer htmlBuffer=new StringBuffer();
		htmlBuffer.append("\n<hidden name='"+WebConst.CSRF_NONCE+"' value='"+time+"'/><hidden name='"+WebConst.CSRF_HASH+"' value='"+this.getValue()+"'/>\n");
		if(this.isFieldValid){
			htmlBuffer.append("<!--valid-->"); 
		}else{
		 
			htmlBuffer.append("<div class='alert alert-danger' > "+this.getErrorMessage()+"</div>");
		}
		return htmlBuffer.toString();
	}
	@Override
	public String getDivTag() {
		return getField();
	}

	@Override
	public String getTable() {
		return getField();
	}
	@Override
	public String getJQueryFieldScript() {
	 
		return EMPTY;
	}

	@Override
	public String getJQueryFieldValidate() {
	 
		return EMPTY;
	}

	@Override
	public void cleanField() {
		//do nothing do not cleans hash
	}
	/** 
	* Custom validate field
	 * validate value as sha2(current time + csrf random session password)
	 * @param value
	 * @return true if csrf hash is valid
	 */
	 
	@Override
	public boolean validate(String value) {
		boolean val=super.validate(value);
		log.debug("validate>"+value );
		if(this.req==null) {
			log.warn("Request is null in CSRF Field "+this.getName());
			val= false;
			this.isFieldValid=false;
		}
		HttpSession session=this.req.getSession(false);
		if(session==null) {
			log.warn("Session is null in CSRF Field "+this.getName());
			this.setErrorMessage(this.getProps(MsgConst.ERROR_CSRF, INVALID_MESSAGE) );  
			val= false;
			this.isFieldValid=false;
		}
		String seed=(String) session.getAttribute(WebConst.CSRF_SEED);
		if(seed==null) {
			log.error("Random SEED for CSRF field not set in session"+this.getName());
			this.setErrorMessage(this.getProps(MsgConst.ERROR_CSRF, INVALID_MESSAGE) );  
			val= false;
			this.isFieldValid=false;
		}
		//log.debug("session sead="+sead);
		//log.debug("time="+time);
		String hash=makeHash(time,seed);
		//log.debug("hash="+hash);
		if(hash.equals(this.getValue() )){
			log.debug("<<<<<validate"+value +" hash is valid");  
			val= true;	
		}else {
			log.warn(hash+"?value="+value );
			this.setErrorMessage(this.getProps(MsgConst.ERROR_CSRF, INVALID_MESSAGE) );  
			val= false;
			this.isFieldValid=false;
		} 
		log.debug("<validate="+val );
		return val;
	}
 
	
}
