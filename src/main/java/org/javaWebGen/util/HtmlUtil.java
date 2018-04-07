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
package org.javaWebGen.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.text.StringEscapeUtils;
import org.javaWebGen.WebSession;
import org.javaWebGen.config.WebConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 

/**
 * HTML helper methods
 * @author Kevin Scott
 *
 */
public class HtmlUtil{
	@SuppressWarnings("unused")
	private static final Logger log=LoggerFactory.getLogger(HtmlUtil.class); 
 
	/**
	 * URL encode a string (escape it)
	 * @param html
	 * @return encoded string
	 */
	//@TODO convert to use apache commans
	@SuppressWarnings("deprecation")
	public static final String urlEncode(String html){
		if(html==null) {
			return "";
		}
		try {
			return URLEncoder.encode(html,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			//log.error("UTF8 not supported return somthing encoded");
			return URLEncoder.encode(html);
		}
	}
	/**
	 * Changes all spaces to %20  Facebook API likes this for some reason
	 * @param html
	 * @return with spaces converted to %20
	 */
	public static final String htmlSpace(String html){
		if(html==null) {
			return "";
		}
		return html.replaceAll(" ", "%20");
	}
	/**
	 * Generates HTML to display an error message
	 * Please note, it displays the real stack trace in a HTML comment.  Only use for testing.
	 * 
	 * @param t error
	 * @return HTML message
	 */
	public static final String htmlErrorMsg(Throwable t){
		if(t==null) {
			return "";
		}
		String html="<P class='error'>System is having Technical Problems please try again at later date"+
			"Production supprot has been notified of the error.</P>";
		if(t!=null){
			html+="\n\n<!--"+StringUtil.getStackTrace(t)+"-->";
		}
		return html;
	}
	/**
	 * Removed all tags( IE <BLOCKQUOTE><SCRIPT> </SCRIPT> </BLOCKQUOTE>)  from input string.
	 * This should be used on all input text fields
	 * to prevent cross site scripting attacks
	 * 
	 * @param html
	 * @return text with TAGS removed
	 */
	//@TODO escape the results for safety.  maybe only allow alphanumeric and basic punctuation?
	public static final String stripTags(String html){
		
		if(html!=null&&!html.equalsIgnoreCase("null") ){
			return html.replaceAll("\\<[^>]*>","");
		   /* StringBuffer returnMessage = new StringBuffer(html);
		    int startPosition = html.indexOf("<"); // encountered the first opening brace
		    int endPosition = html.indexOf(">"); // encountered the first closing braces
		    while( startPosition != -1 ) {
	 	
		    	  returnMessage.delete( startPosition, endPosition+1 ); // remove the tag
 
		      startPosition = (returnMessage.toString()).indexOf("<"); // look for the next opening brace
		      endPosition = (returnMessage.toString()).indexOf(">"); // look for the next closing brace
		      if(endPosition<0){
		    	  break;
		      }
		    }
		    html=returnMessage.toString();
		   */
		}
		if(html==null||html.length()==0||html.equalsIgnoreCase("null") ){
		    	return null;
		}
		//html=StringEscapeUtils.escapeHtml4(html);
		return html;
		    
 

	}
	/**
	 * return only alphanumeric and basic punctuation. 
	 * @param html
	 * @return cleaned string
	 */
	public static final String strip(String html){
		if(html==null){
			return null;
		}
		if(html!=null&&!html.equalsIgnoreCase("null") ){
			html=stripTags(html);
			html = html.replaceAll("[^\\p{IsAlphabetic}^\\p{IsDigit}].,?-_<> ", "");

		}
		if(html.length()==0||html.equalsIgnoreCase("null") ){
		    	return null;
		}
		html=StringEscapeUtils.escapeHtml4(html);
	 
		return html;

	}
	/**
	 * Strip tags and truncate input to a reasonable size
	 * 
	 * @param html
	 * @param size 
	 * @return html minus tags and truncate to size
	 */
	public static final String stripTags(String html, int size){
		return stripTags(StringUtil.truncate(size, html) );
		
		
	}
	/**
	 * removed all tags( IE <SCRIPT> </SCRIPT>)  from input string
	 * This should be used on all input text fields
	 * to prevent cross site scripting attacks. Removes scripting tags
	 * @param html text
	 * @return clean text with TAGS removed null if passed a null
	 */
	public static final String stripEvilTags(String html){
		if(html!=null ){
			 return html
				     .replaceAll("(?i)<script.*?>.*?</script.*?>", "")   // case 1
				     .replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "") // case 2
				     .replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");     // case 3
		}else{
			return null;
		}

	}

	  /**
	   * get WebSession object from the request
	   * @param req 
	   * @return web session from request
	   */
	  @Deprecated
	  public static WebSession getWebSession(HttpServletRequest req) {
		 if(req==null) {
			 return null;
		 }
	    Object temp = req.getAttribute(WebConst.WEB_SESSION);
	    WebSession ws = null;
	    if (temp != null) {
	      ws = (WebSession) temp;
	      ws.clearError();
	      //log.debug("ws="+ws.toXml() );
	    }else{
	    	ws = new WebSession();
	    	req.setAttribute(WebConst.WEB_SESSION,ws);
	    }
	    return ws;
	  }
	  /**
	   * get WebSession object from session
	   * @param session
	   * @return web session
	   */
	 /* public static WebSession getWebSession(HttpSession session) {
	    Object temp = session.getAttribute(WebConst.WEB_SESSION);
	    WebSession ws = null;
	    if (temp != null) {
	      ws = (WebSession) temp;
	      ws.clearError();
	      //log.debug("ws="+ws.toXml() );
	    }else{
	    	ws = new WebSession();
	    	session.setAttribute(WebConst.WEB_SESSION,ws);
	    }
	    return ws;
	  }*/
	
}