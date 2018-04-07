package org.javaWebGen;

import org.javaWebGen.config.WebConst;

/**
 * Used by @see Dispatcher to route a request to the new a web controller or page
 * @author kevin
 *
 */
public class ServerAction {
	
	private String postURL=null;
	private String getURL=null;
	private String servletroot=null;
	
	public ServerAction(String postUrl){
		this.postURL=postUrl;
	}
	public ServerAction(){}
	
	public void setServletRoot(String url){
		this.servletroot=url;
	}
	public String getServletRoot(){
		if(servletroot==null){
			return "";
		}
		return this.servletroot;
	}
	/**
	 * get a Instance of the ServerAction class for view actions (HTTP GET)
	 * @param url
	 * @return next Server action
	 */
	public static ServerAction viewAction(String url){
		ServerAction action = new ServerAction();
		action.setGetURL(url);
		return action;
	}
	
	/**
	 * get a Instance of the ServerAction class for update actions (HTML FORMS)
	 * 
	 * @param url
	 * @return next Server action
	 */
	public static ServerAction updateAction(String url){
		return new ServerAction(url);
	}
	/**
	 * get a Instance of the ServerAction class for update actions (REST action)
	 * @param url of web service 
	 * @return Server action
	 */
	public static ServerAction webService(String url){
		return new ServerAction(url);
	}
	/**
	 * get next page and return to current URL afterwords 
	 * @param returnObj uri to return to IE controller
	 * @param returnCmd controller method to call on return
	 * @return action to call next 
	 */	
	public static ServerAction getNextAction(String returnObj, String returnCmd, String parm){
		if(returnObj!=null&&returnCmd!=null&&parm!=null){
			return new ServerAction(WebConst.WEB_CONTROLLER+"?page="+returnObj+"&cmd="+returnCmd+parm);
		}else if(returnObj!=null&&returnCmd!=null){
			return new ServerAction(WebConst.WEB_CONTROLLER+"?page="+returnObj+"&cmd="+returnCmd);
		}else{
			return new ServerAction("/");
		}
		
	}

	/**
	 * Controller set this when a HTTP GET request is processed that will not modify data
	 * Such as an HTML page 
	 * The FrameworkController will us A forward when the WebController is finished.
	 * 
	 * @param url for HTTP get
	 */
	
	public void setGetURL(String url){
		this.getURL=url;
		
	}

	/**
	 * Controller set this when process a post request that can modify data
	 * Such as an HTML form that updated the data
	 * The FrameworkController will us A HTTP redirect when the WebController is finished.
	 * This prevents any rePosting data to the form cause by the refresh button	 
	 * @param url for POST
	 */
	public void setPostURL(String url){
		this.postURL=url;
		
	}
	/**
	 * Get the actual URL that is being used in the redirect
	 * @return url
	 */
	public String getPostURL() {
		return postURL;
	}
	/**
	 * Get the actual URL that is being used in the used
	 * @return url
	 */
	public String getGetURL() {
		return getURL;
	}
}
