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

import java.util.HashMap;

public class WebServiceAction extends ServerAction {
	public static String XML_MIME_TYPE="application/xml";
	public static String DEFAULT_MIME_TYPE="text/html";
	public static String BINARY="Application/Octet-Stream";
	public static String HTML_MIME_TYPE=DEFAULT_MIME_TYPE;
	private String message=null;
	private String contentType=null;
	private HashMap<String,String> headers = new HashMap<String,String>();
	
	/**
	 * Return with a message to print by dispatcher
	 * @param response to print by dispatcher
	 */
	public WebServiceAction(String response){
		this.message=response;
		this.contentType="html/text";
	}
	/**
	 * 
	 * @param response to print by dispatcher
	 * @param contentType mimetype to display in browswe
	 */
	public WebServiceAction(String response,String contentType){
		this.message=response;
		this.contentType=contentType;
	}
	/**
	 * Set HTTP Response Header
	 * @param key header name
	 * @param value header value
	 */
	public void addRespHeader(String key,String value) {
		headers.put(key,value);
	}
 
	public String getResponse(){
		return message;
	}
	public String getContentType(){
		return this.contentType;
	}

	public HashMap<String,String> getAdditionalHeaders() {
		return headers;
	}
	public static WebServiceAction respMessage(String msg){
		return new WebServiceAction(msg);
	}
}
