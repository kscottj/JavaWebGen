package org.javaWebGen.web;

import java.io.Serializable;
import java.util.Arrays;

/**
 * generic model(Javabean) of logged on user.  Should be extended with real model from schema
 * @author kevin
 *
 */
public class LoginBean implements Serializable{
 
	private static final long serialVersionUID = -1590639500433271622L;
	public final static String DEFAULT_USER="guest";
	public final static String DEFAULT_ROLE="guest";
	public final static String[] DEFAULT_ROLE_LIST={DEFAULT_ROLE};
	public static final String ADMIN="admin";
	public static final String USER="user";
	
	private String userName=DEFAULT_USER;
	private Long userId=new Long(0);  //login id
	private String passwd=null;
	private String email=null;
	private String[] roles=DEFAULT_ROLE_LIST;
	
	private boolean isLoggedin =false;
	private boolean isRegisteredUser=false;
	
	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}

	

	
	public boolean isAuthorized(String role){
		return false;
	}
	public boolean isRegisteredUser(){
		return this.isRegisteredUser;
	}
	public void setRegisteredUser(boolean isRegistered){
		this.isRegisteredUser =isRegistered;
	}

	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String[] getRoles() {
		return roles;
	}


	public void setUserRole(String[] userRoles) {
		this.roles = userRoles;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	protected String getPasswd() {
		return passwd;
	}


	protected void setPasswd(String passwd) {
		this.passwd = passwd;
	}


	public boolean isLoggedIn() {
		return isLoggedin;
	}
	
	public void setLogInStatus(boolean status){
		isLoggedin=status;
	}
	
	public String toXML(){
		String xml="<LoginBean "+
			"getUserId='"+this.getUserId()+"' "+
			"userName='"+this.getUserName()+"' "+
			"role='"+Arrays.toString(roles)+"' "+
			"isLoggedIn='"+this.isLoggedIn()+"' "+
			
			"/>\n";
		return xml;
			
			
		
	}

	
}
