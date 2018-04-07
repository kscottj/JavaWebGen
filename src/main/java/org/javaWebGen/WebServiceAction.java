package org.javaWebGen;

public class WebServiceAction extends ServerAction {
	public static String XML_MIME_TYPE="application/xml";
	public static String DEFAULT_MIME_TYPE="text/html";
	public static String HTML_MIME_TYPE=DEFAULT_MIME_TYPE;
	private String message=null;
	private String contentType=null;
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
 
	public String getResponse(){
		return message;
	}
	public String getContentType(){
		return this.contentType;
	}
	public static WebServiceAction respMessage(String msg){
		return new WebServiceAction(msg);
	}
}
