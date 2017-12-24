package org.javaWebGen.util;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.javaWebGen.config.WebConst;
import org.mockito.Mockito;

/**
 * Mock request helper class set request parameters so it can
 * be tested outside a server.  Wraps Mockito methods
 * @author home
 *
 */
public class MockRequestHelper {

	
	public static HttpServletRequest mapName(String key, String value){
		HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
		Mockito.when(req.getParameter(key))
			.thenReturn(value); 
		Vector <String>names= new Vector<String>();
		names.add(key);
		
		Mockito.when(req.getParameterNames())
			.thenReturn(names.elements()); 
		return req;
	}
	public static HttpServletRequest mapNames(HashMap<String,String> valueMap){
		HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
		Set<String> keys=valueMap.keySet();
		Vector <String>names= new Vector<String>();
		for(String key:keys){
			Mockito.when(req.getParameter(key))
				.thenReturn(valueMap.get(key)); 
			names.add(key);
		}
		Mockito.when(req.getParameterNames())
			.thenReturn(names.elements()); 	
		return req;
	}
	
	private static HttpSession setupSession(HttpServletRequest req, HashMap<String,String> sessionMap ) {
		//session
		/*setup CSRF data*/

		sessionMap.put(WebConst.CSRF_SEED, "csrfseed");
		sessionMap.put(WebConst.CSRF_HASH, "csrfhash");
		
		HttpSession session = Mockito.mock(HttpSession.class);
		Mockito.when(req.getSession(false))
			.thenReturn(session);  
		Mockito.when(req.getSession(true))
			.thenReturn(session);
		
		Set<String> sKeys=sessionMap.keySet();
		
		for(String key:sKeys){
			Mockito.when(session.getAttribute(key))
				.thenReturn(sessionMap.get(key));

		}
		//session validation will fail without this
		Mockito.when(session.getAttribute(WebConst.CSRF_AGENT))
			.thenReturn("testAgent");
		Mockito.when(session.getAttribute(WebConst.CSRF_IP))
			.thenReturn("127.0.0.1");
		return session;
		
	}
	public static HttpServletRequest mapNamesAndSession(HashMap<String,String> valueMap,HashMap<String,String> sessionMap){
		if( valueMap==null || sessionMap==null){
			return null;
		}
		
		HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
		
		Set<String> keys=valueMap.keySet();
		Vector <String>names= new Vector<String>();
		for(String key:keys){
			Mockito.when(req.getParameter(key))
				.thenReturn(valueMap.get(key)); 
			names.add(key);
		}
		Mockito.when(req.getParameterNames())
			.thenReturn(names.elements()); 	
		//session
		/*setup CSRF data*/

		/*sessionMap.put(WebConst.CSRF_SEED, "csrfseed");
		sessionMap.put(WebConst.CSRF_HASH, "csrfhash");
		
		HttpSession session = Mockito.mock(HttpSession.class);
		Mockito.when(req.getSession(false))
			.thenReturn(session);  
		Mockito.when(req.getSession(true))
			.thenReturn(session);
		
		Set<String> sKeys=sessionMap.keySet();
		
		for(String key:sKeys){
			Mockito.when(session.getAttribute(key))
				.thenReturn(sessionMap.get(key));

		}
		//session validation will fail without this
		Mockito.when(session.getAttribute(WebConst.CSRF_AGENT))
			.thenReturn("testAgent");
		Mockito.when(session.getAttribute(WebConst.CSRF_IP))
			.thenReturn("127.0.0.1");
		
*/
		setupSession(req,sessionMap);
		return req;
	}
	public static HttpServletRequest mapUri(String uri){
		
		HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
		HttpSession session= Mockito.mock(HttpSession.class);
		Mockito.when(req.getSession())
		.thenReturn(session ); 
		Mockito.when(req.getServletPath())
			.thenReturn(uri); 
		HashMap<String,String> sessionMap=new HashMap<String,String>();
		setupSession(req,sessionMap);
		return req;
	}
}
