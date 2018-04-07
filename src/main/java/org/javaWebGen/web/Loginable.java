package org.javaWebGen.web;

import javax.servlet.http.HttpServletRequest;
import org.javaWebGen.BusinessModel;
import org.javaWebGen.exception.WebAppException;

public interface Loginable extends BusinessModel {
	
/**
 * try to login to the app
 * if user id and password are not found it will set the user_name and password 
 * to a default 
 * 
 * 
 * Feel free to extend this class to provide you own logic
 * for example throwing an WebAppException exception if login attempts are excessive
 * 
 * @param userId
 * @param pass
 * @return LoginBean of user logged in
 */
	public LoginBean login(Long userId, String pass) throws WebAppException;
	/**
	 * is their role authorized to to this role?
	 * 
	 * @param login
	 * @param role
	 * @return true if authorized
	 */
	public boolean isAuthorized(LoginBean login, String role) throws WebAppException;
	
	public LoginBean login(HttpServletRequest req);
	 
}
