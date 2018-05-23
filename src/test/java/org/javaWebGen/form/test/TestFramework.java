package org.javaWebGen.form.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
    //private RequestDispatcher rd;
    //private ServletContext appContext;
    private PrintWriter out= null;
    private StringWriter sw= null;
   
 
    @Before
    public void setUp() {
    	mockReq = mock(HttpServletRequest.class);
        mockRes = mock(HttpServletResponse.class);
       // rd = mock(RequestDispatcher.class);
       // appContext = mock(ServletContext.class);
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
	