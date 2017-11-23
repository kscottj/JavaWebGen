package org.javaWebGen;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*import org.slf4j.Logger;
import org.slf4j.LoggerFactory;*/

/**
 * Admin controller
 * requires an Google admin to be logged in otherwise it throws HTTP 500 errors
 *
 * @author kscott
 *
 */
@SuppressWarnings("deprecation")
public class GaeController extends FrameworkController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2743710184048831432L;
	//private static final Logger log = LoggerFactory.getLogger(GaeController.class);
	
	@Override
	protected boolean requiresAdmin(String url){
		return true;
	}
	@Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		super.service(req,res);
		if(req.getUserPrincipal()==null){
			throw new ServletException("Must be Logged in");
		}
		/*UserService userService = UserServiceFactory.getUserService();
		log.info("Check for Admin login!!!");
		if (!userService.isUserAdmin() ){
			log.error("not logged into admin EXIT!");
			throw new ServletException("Authorization Error");
		}*/
		
	}
}
