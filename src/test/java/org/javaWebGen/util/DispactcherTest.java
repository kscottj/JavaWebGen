package org.javaWebGen.util;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javaWebGen.Router;
import org.javaWebGen.data.bean.Book;
import org.javaWebGen.test.MockHttpResponse;
import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.commons.annotation.Testable;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Testable
public class DispactcherTest {
	private static final Logger log=LoggerFactory.getLogger(DispactcherTest.class);//begin exec

 @Test
 public void testRouterIndex() throws IOException, ServletException{
	 Router router = new Router();
	 router.setPrefix("org.javaWebGen");
	 HttpServletRequest req =MockRequestHelper.mapUri("/");
	 HttpServletResponse res = Mockito.mock(HttpServletResponse.class);
	 FilterChain chain = Mockito.mock(FilterChain.class);
	 router.doFilter(req,res,chain) ;
	 Assert.assertEquals("/", router.currentTestUrl()); 
 }

 
 @Test
 public void testResp() throws IOException, ServletException{
	 Router router = new Router();

	 FilterConfig c = Mockito.mock(FilterConfig.class);
	 //router.setPrefix("org.javaWebGen");
	 router.init(c);
	// log.debug(router.getPrefix() );
	 HttpServletRequest req =MockRequestHelper.mapUri("/util/Test/resp");
	 MockHttpResponse res=new MockHttpResponse();
	 FilterChain chain = Mockito.mock(FilterChain.class);

	 router.setPrefix("org.javaWebGen");
	 router.doFilter(req,res,chain) ;
	 Assert.assertTrue(res.getWriterContent().toString().equals(TestAction.TEST_MSG) );
 }
 @Test
 public void testJson() throws IOException, ServletException{
	 Router router = new Router();
	 FilterConfig c = Mockito.mock(FilterConfig.class);
	 router.setPrefix("org.javaWebGen");
	 router.init(c);
	
	 log.debug(router.getPrefix() );
	 HttpServletRequest req =MockRequestHelper.mapUri("/util/Test/json");
	 MockHttpResponse res=new MockHttpResponse();
	 FilterChain chain = Mockito.mock(FilterChain.class);
	 router.doFilter(req,res,chain) ;
	 Book book=new Book();
	 Assert.assertTrue(res.getWriterContent().toString().equals(book.toJSON()) );
	 
	log.debug(res.getWriterContent().toString() );
	
 }
 @Test
 public void testXml() throws IOException, ServletException{
	 Router router = new Router();
	 FilterConfig c = Mockito.mock(FilterConfig.class);
	 router.setPrefix("org.javaWebGen");
	 router.init(c);
	
	 log.debug(router.getPrefix() );
	 HttpServletRequest req =MockRequestHelper.mapUri("/util/Test/xml");
	 MockHttpResponse res=new MockHttpResponse();
	 FilterChain chain = Mockito.mock(FilterChain.class);
	 router.doFilter(req,res,chain) ;
	 Book book=new Book();
	 Assert.assertTrue(res.getWriterContent().toString().equals(book.toXML() ) );
	 
	log.debug(res.getWriterContent().toString() );
	
 }
 @Test
 public void testView() throws IOException, ServletException{
	 Router router = new Router();
	 FilterConfig c = Mockito.mock(FilterConfig.class);
	 router.setPrefix("org.javaWebGen");
	 router.init(c);
	
	 log.debug(router.getPrefix() );
	 HttpServletRequest req =MockRequestHelper.mapUri("/util/Test/view");
	 MockHttpResponse res=new MockHttpResponse();
	 FilterChain chain = Mockito.mock(FilterChain.class);
	 router.doFilter(req,res,chain) ;

	// Assert.assertTrue(res.getWriterContent().toString().equals(book.toXML() ) );
	 
	 
	
 }
 @Test
 public void testUpdate() throws IOException, ServletException{
	 Router router = new Router();
	 FilterConfig c = Mockito.mock(FilterConfig.class);
	 router.setPrefix("org.javaWebGen");
	 router.init(c);
	
	 log.debug(router.getPrefix() );
	 HttpServletRequest req =MockRequestHelper.mapUri("/util/Test/update");
	 MockHttpResponse res=new MockHttpResponse();
	 FilterChain chain = Mockito.mock(FilterChain.class);
	 router.doFilter(req,res,chain) ;

	 
	 
	
	
 }
}
