package org.javaWebGen;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseAction extends WebController{

	public ServerAction index(HttpServletRequest req, HttpServletResponse res){
		return ServerAction.viewAction(this.getJspRoot()+"/index.jsp");
	}
	public ServerAction about(HttpServletRequest req, HttpServletResponse res){
		return ServerAction.viewAction(this.getJspRoot()+"/about.jsp");
	}
}
