package org.javaWebGen.form.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaWebGen.ServerAction;
import org.javaWebGen.WebController;

public class TestAction extends WebController{
	
	public ServerAction index(HttpServletRequest req, HttpServletResponse res){
		req.getParameter("testID");
		return ServerAction.viewAction("/index.jsp");
	}
	public ServerAction list(HttpServletRequest req, HttpServletResponse res){
		req.getParameter("testID");
		return ServerAction.viewAction("/list.jsp");
	}
	public ServerAction exec(HttpServletRequest req, HttpServletResponse res){
		req.getParameter("testID");
		return ServerAction.viewAction("/index.jsp");
	}
	public ServerAction form(HttpServletRequest req, HttpServletResponse res){
		return ServerAction.updateAction("/form.jsp");
	}
	public ServerAction save(HttpServletRequest req, HttpServletResponse res){
		MockForm form = new MockForm();
		form.setData(req);
		if(form.isValid()){
			return ServerAction.updateAction("/index.jsp");
		}else{
			return ServerAction.viewAction("/form.jsp");
		}
	}
}
