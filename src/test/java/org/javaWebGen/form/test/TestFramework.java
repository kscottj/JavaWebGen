package org.javaWebGen.form.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.javaWebGen.Dispatcher;
import org.javaWebGen.Router;
import org.javaWebGen.ServerAction;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.mockito.Mockito.*;



public class TestFramework {
	private final Logger log=LoggerFactory.getLogger(TestFramework.class);
    private HttpServletRequest mockReq;
    private HttpServletResponse mockRes;
    private HttpSession mockSession;
    private  FilterChain mockChain;
    private RequestDispatcher rd;
    private ServletContext appContext;
    private PrintWriter out= null;
    private StringWriter sw= null;
   
 
    @Before
    public void setUp() {
    	mockReq = mock(HttpServletRequest.class);
        mockRes = mock(HttpServletResponse.class);
        rd = mock(RequestDispatcher.class);
        appContext = mock(ServletContext.class);
        mockChain=mock(FilterChain.class);
        mockSession=mock(HttpSession.class);
        sw=new StringWriter();
        out=new PrintWriter(sw);
        Mockito.when(mockReq.getSession(true) ).thenReturn(mockSession);
        Mockito.when(mockReq.getSession() ).thenReturn(mockSession);
    }
    @After
    public void cleanUp(){
    	out.close();
    }
	 @Test
	 public void testRouter() throws IOException, ServletException{
			 
	    Router router = new Router();
	    router.init(null);
	    router.setPrefix("org.javaWebGen.form.test");
	   
		String reqUrl="/Test/list";
		Mockito.when(mockReq.getServletPath() ).thenReturn(reqUrl);
		router.doFilter(mockReq, mockRes, mockChain);
		
		Assert.assertTrue("router.url="+router.currentTestUrl(),router.currentTestUrl().equals("/Test/list"));
 
		log.debug("res="+mockRes.toString());
		log.debug("res="+mockRes.getContentType() );
		//log.debug("res="+mockRes.getst );

		
	}
	 
	 @Test
	 public void testDispatcher() throws ServletException, IOException {
		log.debug(">>>testDispatcher");
 		Mockito.when(mockReq.getParameter("page") ).thenReturn("form.test.Mock");					
		Mockito.when(mockReq.getParameter("cmd") ).thenReturn("redirect");
		Mockito.when(mockReq.getContextPath() ).thenReturn("");
		Mockito.when(mockReq.getRequestURI() ).thenReturn("/Controller/test/Test/index");
		Mockito.when(mockReq.getServletPath() ).thenReturn("/Controller");
		Mockito.when(mockReq.getPathInfo() ).thenReturn("/test/Test/index");
		Mockito.when(mockRes.getWriter()).thenReturn(out);
		Mockito.when(appContext.getRequestDispatcher(anyString())).thenReturn(rd);
 
		log.debug("testController.doPost");
		Dispatcher disp=new Dispatcher();
		disp.setClassPrefix("org.javaWebGen.form");
		
		disp.doPost(mockReq, mockRes);
		
				
		Mockito.when(mockReq.getQueryString() ).thenReturn("testID=1234");
		Mockito.when(mockReq.getPathInfo() ).thenReturn("/test/Test/index");
		disp.doPost(mockReq, mockRes);
		Assert.assertNotNull("No controller selected",disp.currentCorntroller() );
		Assert.assertTrue("used TestAction WC",disp.currentCorntroller().getClass().equals(TestAction.class) );
		log.debug("mockreq.testID",mockReq.getParameter("testID") );
		
        //test legacy action cmd
		Mockito.when(mockReq.getQueryString() ).thenReturn("page=test.Test&cmd=index");
		Mockito.when(mockReq.getParameter("page") ).thenReturn("test.Test");
		Mockito.when(mockReq.getParameter("cmd") ).thenReturn("index");
		disp.doPost(mockReq, mockRes);
		Assert.assertNotNull("No controller selected",disp.currentCorntroller() );
		log.debug("class="+disp.currentCorntroller().getClass()+"" );
		Assert.assertTrue("used TestAction WC",disp.currentCorntroller().getClass().equals(TestAction.class) );
		
		//Assert.assertTrue("uri!=/test/mock",disp.currentTestUrl().equals("/test/Mock") );
		Assert.assertNotNull("No controller selected",disp.currentCorntroller() );
		log.debug("class="+disp.currentCorntroller().getClass()+"" );
		Assert.assertTrue("used TestAction WC",disp.currentCorntroller().getClass().equals(TestAction.class) );
		//log.debug("mockreq.testID",mockReq.getParameter("testID") );
		//Mockito.verify(mockRes).setStatus(200);
        out.flush(); // it
        
        
        log.debug("<<<testDispatcher");
	 }
	 
	 @Test
	 public void testDispatcherDefaultURL() throws IOException, ServletException{
		 

			Mockito.when(mockReq.getContextPath() ).thenReturn("");
			Mockito.when(mockReq.getRequestURI() ).thenReturn("/Controller/test/Test/index");
			Mockito.when(mockReq.getServletPath() ).thenReturn("/Controller");
			Mockito.when(mockReq.getPathInfo() ).thenReturn("/test/Test/");
			Mockito.when(mockRes.getWriter()).thenReturn(out);
			Mockito.when(appContext.getRequestDispatcher(anyString())).thenReturn(rd);
	 
			log.debug("testController.doPost");
			Dispatcher disp=new Dispatcher();
			disp.setClassPrefix("org.javaWebGen.form.test");
				
			Mockito.when(mockReq.getPathInfo() ).thenReturn("/");	
			log.debug("post test.uri="+disp.currentTestUrl());
			disp.doPost(mockReq, mockRes);
		 

			
	 
		 
	 }
	 @Test
	 public void testLegacyFramework() throws ServletException, IOException{
			Mockito.when(mockReq.getContextPath() ).thenReturn("");
			Mockito.when(mockReq.getRequestURI() ).thenReturn(null);
			Mockito.when(mockReq.getServletPath() ).thenReturn("/Controller");
			Mockito.when(mockReq.getPathInfo() ).thenReturn(null);
			Mockito.when(mockReq.getParameter("page") ).thenReturn("test.Test");
			Mockito.when(mockReq.getParameter("exec") ).thenReturn("exec");
			
		 
			Mockito.when(appContext.getRequestDispatcher(anyString())).thenReturn(rd); 
			Dispatcher disp=new Dispatcher();
			disp.setClassPrefix("org.javaWebGen.form");
			disp.doPost(mockReq, mockRes);
			Assert.assertNotNull("should call TestAction#exec",disp.currentCorntroller() );
			Assert.assertTrue("used TestAction WC",disp.currentCorntroller().getClass().equals(TestAction.class) );
	 }
	 @Test
	 public void testTestAction() throws ServletException, IOException{
			HttpServletRequest mockReq = Mockito.mock(HttpServletRequest.class);
			HttpServletResponse mockRes = Mockito.mock(HttpServletResponse.class);
			
			Mockito.when(mockReq.getParameter("page") ).thenReturn("Home");
			Mockito.when(mockReq.getParameter("cmd") ).thenReturn("index");
			Mockito.when(mockReq.getParameter("id") ).thenReturn("100");
			TestAction wc= new TestAction();
			ServerAction action = wc.index(mockReq, mockRes);
			Assert.assertEquals("/index.jsp",action.getGetURL() );
			
			
	 }
}
	