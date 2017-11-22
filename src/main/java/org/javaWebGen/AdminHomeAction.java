package org.javaWebGen;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaWebGen.config.WebConst;
import org.javaWebGen.web.LoginBean;
/**
 * Default controller to handle /.  Provide you own class if you need something different
 * @author home
 *
 */
public class AdminHomeAction extends WebController{
	/**
	 * Index page
	 * @param req http
	 * @param res http
	 * @return server action
	 */
	public ServerAction index(HttpServletRequest req, HttpServletResponse res){
		LoginBean bean= this.getLogin(req);
		req.setAttribute(WebConst.LOGIN, bean);
		
		return ServerAction.viewAction("/admin/index.jsp");
	}

}
