/*
Copyright (c) 2003-2006 Kevin Scott

Permission is hereby granted, free of charge, to any person obtaining a copy of 
this software and associated documentation files (the "Software"), to deal in 
the Software without restriction, including without limitation the rights to 
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do 
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
SOFTWARE.
 */
package org.javaWebGen;

import java.util.Properties;

import org.javaWebGen.config.*;
import org.javaWebGen.util.PropertiesReader;
import org.javaWebGen.web.LoginBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Generic Websession class that tracks current settings for the JSPs. Such as
 * current LoginBean and style sheet locations and web root
 */
@Deprecated
public class WebSession implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 490707287317245777L;
	private static final Logger log = LoggerFactory.getLogger(WebSession.class);

	// LoginBean login =null;
	String webroot = "/";
	String theme = "theme/default";
	String staticRoot = "/web/";
	String stylesheetName = "styleInc.css";
	String title = "";
	// String team = "s05";
	String error = "";
	Exception webException = null;

	private static PropertiesReader messages = null;

	// private static PropertiesReader configReader = null;
	// private Conf config=null;
	private LoginBean login = new LoginBean();
	private String formHash = null;

	public WebSession() {
		try {
			Properties prop = Conf.getConfig();
			webroot = prop.getProperty(ConfigConst.WEB_ROOT, "/");
			theme = prop.getProperty(ConfigConst.THEME, "web/theme/default");
			staticRoot = prop.getProperty(ConfigConst.STATIC_ROOT, "/web");
			stylesheetName = prop.getProperty(ConfigConst.STYLESHEET, "styleInc.css");
			if (messages == null) { // not needed due to JSTL just set for legacy stuff
				messages = PropertiesReader.getReader(ConfigConst.MESSAGE);
			}
		} catch (Exception e) {
			log.error("could not open config files", e);
		}
		// accept defaults

	}

	/**
	 * 
	 * @return image uri
	 */
	public String getImage() {
		return staticRoot + "/" + theme + "/images";
	}

	/**
	 * 
	 * @return image uri
	 */
	public String images() {
		return getImage();
	}

	/**
	 * 
	 * @return stylesheet uri
	 */
	public String getStyle() {
		return staticRoot + "/" + theme + "/css/" + stylesheetName;
	}

	/**
	 * 
	 * @return stylesheet uri
	 */
	public String stylesheet() {
		return getStyle();
	}

	/**
	 * 
	 * @return include uri
	 */
	public String getInclude() {
		return webroot + theme;
	}

	/**
	 * 
	 * @return theme name
	 */
	public String getTheme() {
		return webroot + theme;
	}

	/**
	 * 
	 * @param newTheme
	 *            new theme name
	 */
	public void setTheme(String newTheme) {
		theme = newTheme;
	}

	/**
	 * 
	 * @return webroot url
	 */
	public String getWebRoot() {
		return webroot;
	}

	/**
	 * 
	 * @return webroot url
	 */
	public String webRoot() {
		return webroot;
	}

	/**
	 * set web root url
	 * 
	 * @param url
	 */
	public void setWebRoot(String url) {
		webroot = url;
	}

	/**
	 * has an error been generated?
	 * 
	 * @return true if error status has been set
	 */
	public boolean hasError() {
		if (error != null && error.length() > 0) {
			return true;
		}
		return false;
	}

	/**
	 * get error message that was set
	 * 
	 * @return error message
	 */
	public String getError() {
		return error;
	}

	/**
	 * get the exception that caused the error
	 * 
	 * @return exception that caused the error
	 */
	public Exception getException() {
		return webException;
	}

	/**
	 * set an error message generally this is done only by a controller
	 * 
	 * @param error
	 */
	public void setError(String error) {
		this.error = error;
	}

	/**
	 * set an error message generally this is done only by a controller
	 * 
	 * @param error
	 * @param e
	 *            exception
	 */
	public void setError(String error, Exception e) {
		this.error = error;
		webException = e;
	}

	/**
	 * 
	 * @param e
	 *            exceptiion
	 */
	public void setException(Exception e) {
		webException = e;
		error = getMessage(MsgConst.ERROR_GENERIC, "Unknown error");
		log.error(error, e);
		e.printStackTrace();
	}

	/**
	 * clear error status
	 * 
	 */
	public void clearError() {
		this.error = "";
		webException = null;
	}

	/**
	 * return a message from message file by key
	 * 
	 * @param key
	 * @return message based on local
	 */
	public String getMessage(String key) {
		return messages.getProperty(key);
	}

	/**
	 * return a message from message file by key
	 * 
	 * @param key
	 * @param defaultText
	 *            text if key is not found
	 * @return message based on current local
	 */
	public String getMessage(String key, String defaultText) {
		return messages.getProperty(key, defaultText);
	}

	/**
	 * return a message from message file by key
	 * 
	 * @param key
	 * @param parms
	 *            parameters to pass in @see MessageFormat
	 * @return message based on current local
	 */
	public String getFormattedMessage(String key, Object[] parms) {
		return messages.getFormattedMessage(key, parms);
	}

	/**
	 * make xml string of current web session mainly useful for debugging
	 * 
	 * @return xml
	 */
	public String toXml() {
		String xml = "<WebSession>\n" + "\t<usersname>" + login.getUserName() + "</usersname>\n" + "\t<isLoggedIn>"
				+ login.isLoggedIn() + "</isLoggedIn>\n" + "\t<error>" + error + "</error>\n" + "</WebSession>\n";
		return xml;
	}

	/**
	 * dump a message to log file this should not be left in a JSP page is only for
	 * debug use
	 * 
	 * @param msg
	 *            message for log file
	 */
	public void log(String msg) {
		log.debug(msg);

	}

	public void setLogin(LoginBean login) {
		this.login = login;
	}

	/**
	 * Get current login
	 * 
	 * @return login JavaBean
	 */
	public LoginBean getLogin() {
		if (login == null) {
			log.info("no loging return guest login");
			// return new LoginBean();
		}
		return login;
	}

	/**
	 * Get current configuration item from properties file
	 * 
	 * @param name
	 *            of key
	 * @param defaultValue
	 *            default if not found in properties
	 * @return value of configuration item
	 */
	public String getConfig(String name, String defaultValue) {
		return Conf.getConfig().getProperty(name, defaultValue);
	}

	/**
	 * Handy method for JSP to check status with
	 * 
	 * @return true if logged in
	 */

	public boolean isLoggedIn() {
		if (this.getLogin() != null) {
			return this.getLogin().isLoggedIn();
		} else {
			return false;
		}
	}

	/**
	 * handy method for JSP to check status with
	 * 
	 * @return true if logged in
	 */
	@Deprecated
	public Boolean getLoggedIn() {
		if (this.getLogin() != null) {
			return this.getLogin().isLoggedIn();
		} else {
			return false;
		}
	}

	/**
	 * Handy method for JSP to check status with
	 * 
	 * @return true if logged in
	 */
	@Deprecated
	public boolean loggedIn() {
		if (this.getLogin() != null) {
			return this.getLogin().isLoggedIn();
		} else {
			return false;
		}
	}

	/**
	 * handy method for JSP to check status with
	 * 
	 * @return true if logged in
	 */
	@Deprecated
	public String getLoggedStatus() {
		if (this.getLogin() != null) {
			return "" + this.getLogin().isLoggedIn();
		} else {
			return "" + false;
		}
	}
}