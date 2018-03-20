package org.javaWebGen.form.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaWebGen.ServerAction;
import org.javaWebGen.WebController;

public class MockAction extends WebController{
	public ServerAction redirect(HttpServletRequest req, HttpServletResponse res){
		return ServerAction.updateAction("/form/test/redirect");
	}
	public ServerAction jump(HttpServletRequest req, HttpServletResponse res){
		return ServerAction.viewAction("/test/test.jsp");
	}
	public ServerAction list(HttpServletRequest req, HttpServletResponse res){
		return ServerAction.viewAction("/test/test.jsp");
	}
}
