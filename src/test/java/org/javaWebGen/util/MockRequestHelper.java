package org.javaWebGen.util;

import java.util.HashMap;
import java.util.Set;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
	public static HttpServletRequest mapUri(String uri){
		
		HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
		HttpSession session= Mockito.mock(HttpSession.class);
		Mockito.when(req.getSession())
		.thenReturn(session ); 
		Mockito.when(req.getServletPath())
			.thenReturn(uri); 	
		return req;
	}
}
