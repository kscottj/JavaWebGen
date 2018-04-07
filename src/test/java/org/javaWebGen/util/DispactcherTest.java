package org.javaWebGen.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaWebGen.Router;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class DispactcherTest {

 @Test
 public void testRouter(){
	 
 }

 @Test
 public void testRouterIndex() throws IOException, ServletException{
	 Router router = new Router();
	 HttpServletRequest req =MockRequestHelper.mapUri("/");
	 HttpServletResponse res = Mockito.mock(HttpServletResponse.class);
	 FilterChain chain = Mockito.mock(FilterChain.class);
	 router.doFilter(req,res,chain) ;
	 Assert.assertEquals("/index.jsp", router.currentTestUrl()); 
	 
	 
 }

 
 @Test
 public void testDispacter(){
	 
 }
 @Test
 public void testDispacterIndex(){
	 
 }

}
