package org.javaWebGen.generator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.javaWebGen.exception.UtilException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * write HTTP request and response mock classes for unit testing action (controller) classes
 * @author kevin
 *
 */
public abstract class TestCaseHelpers extends CodeGenerator{
	private final static Logger log= LoggerFactory.getLogger(TestCaseHelpers.class);
    private String requestHelper=
    		 ""
    		 + "package  org.javaWebGen.test;\r\n" + 
    		 "\r\n" + 
    		 "import java.util.HashMap;\r\n" + 
    		 "import java.util.Set;\r\n" + 
    		 "import java.util.Vector;\r\n" + 
    		 "\r\n" + 
    		 "import javax.servlet.http.HttpServletRequest;\r\n" + 
    		 "import javax.servlet.http.HttpServletResponse;\r\n" + 
    		 "import javax.servlet.http.HttpSession;\r\n" + 
    		 "import org.javaWebGen.config.WebConst;\r\n" + 
    		 "import org.mockito.Mockito;\r\n" + 
    		 "\r\n" + 
    		 "/**\r\n" + 
    		 " * Mock request helper class set request parameters so it can\r\n" + 
    		 " * be tested outside a server.  Wraps Mockito methods\r\n" + 
    		 " * @author home\r\n" + 
    		 " *\r\n" + 
    		 " */\r\n" + 
    		 "public class MockRequestHelper {\r\n" + 
    		 "	public static final String FAKE_SEED=\"csrfseed\";\r\n" + 
    		 "	public static final String FAKE_AGENT=\"csrfagent\";\r\n" + 
    		 "	public static final String FAKE_IP=\"127.0.0.1\";\r\n" + 
    		 "\r\n" + 
    		 "	\r\n" + 
    		 "	public static HttpServletRequest mapName(String key, String value){\r\n" + 
    		 "		HttpServletRequest req = Mockito.mock(HttpServletRequest.class);\r\n" + 
    		 "		Mockito.when(req.getParameter(key))\r\n" + 
    		 "			.thenReturn(value); \r\n" + 
    		 "		Vector <String>names= new Vector<String>();\r\n" + 
    		 "		names.add(key);\r\n" + 
    		 "		\r\n" + 
    		 "		Mockito.when(req.getParameterNames())\r\n" + 
    		 "			.thenReturn(names.elements()); \r\n" + 
    		 "		return req;\r\n" + 
    		 "	}\r\n" + 
    		 "	public static HttpServletRequest mapNames(HashMap<String,String> valueMap){\r\n" + 
    		 "		HttpServletRequest req = Mockito.mock(HttpServletRequest.class);\r\n" + 
    		 "		Set<String> keys=valueMap.keySet();\r\n" + 
    		 "		Vector <String>names= new Vector<String>();\r\n" + 
    		 "		for(String key:keys){\r\n" + 
    		 "			Mockito.when(req.getParameter(key))\r\n" + 
    		 "				.thenReturn(valueMap.get(key)); \r\n" + 
    		 "			names.add(key);\r\n" + 
    		 "		}\r\n" + 
    		 "		Mockito.when(req.getParameterNames())\r\n" + 
    		 "			.thenReturn(names.elements()); 	\r\n" + 
    		 "		return req;\r\n" + 
    		 "	}\r\n" + 
    		 "	\r\n" + 
    		 "	private static HttpSession setupSession(HttpServletRequest req, HashMap<String,String> sessionMap ) {\r\n" + 
    		 "		//session\r\n" + 
    		 "		/*setup CSRF data*/\r\n" + 
    		 "\r\n" + 
    		 "		sessionMap.put(WebConst.CSRF_SEED, FAKE_SEED);\r\n" + 
    		 "		//sessionMap.put(WebConst.CSRF_HASH, \"csrfhash\");\r\n" + 
    		 "		\r\n" + 
    		 "		HttpSession session = Mockito.mock(HttpSession.class);\r\n" + 
    		 "		Mockito.when(req.getSession() )\r\n" + 
    		 "			.thenReturn(session);  \r\n" + 
    		 "		Mockito.when(req.getSession(false))\r\n" + 
    		 "			.thenReturn(session);  \r\n" + 
    		 "		Mockito.when(req.getSession(true))\r\n" + 
    		 "			.thenReturn(session);\r\n" + 
    		 "		\r\n" + 
    		 "		Set<String> sKeys=sessionMap.keySet();\r\n" + 
    		 "		\r\n" + 
    		 "		for(String key:sKeys){\r\n" + 
    		 "			Mockito.when(session.getAttribute(key))\r\n" + 
    		 "				.thenReturn(sessionMap.get(key));\r\n" + 
    		 "\r\n" + 
    		 "		}\r\n" + 
    		 "		//session validation will fail without this\r\n" + 
    		 "		Mockito.when(session.getAttribute(WebConst.CSRF_AGENT))\r\n" + 
    		 "			.thenReturn(FAKE_AGENT);\r\n" + 
    		 "		Mockito.when(session.getAttribute(WebConst.CSRF_IP))\r\n" + 
    		 "			.thenReturn(FAKE_IP);\r\n" + 
    		 "		return session;\r\n" + 
    		 "		\r\n" + 
    		 "	}\r\n" + 
    		 "	public static HttpServletRequest mapNamesAndSession(HashMap<String,String> valueMap,HashMap<String,String> sessionMap){\r\n" + 
    		 "		if( valueMap==null || sessionMap==null){\r\n" + 
    		 "			return null;\r\n" + 
    		 "		}\r\n" + 
    		 "		\r\n" + 
    		 "		HttpServletRequest req = Mockito.mock(HttpServletRequest.class);\r\n" + 
    		 "		\r\n" + 
    		 "		Set<String> keys=valueMap.keySet();\r\n" + 
    		 "		Vector <String>names= new Vector<String>();\r\n" + 
    		 "		for(String key:keys){\r\n" + 
    		 "			Mockito.when(req.getParameter(key))\r\n" + 
    		 "				.thenReturn(valueMap.get(key)); \r\n" + 
    		 "			names.add(key);\r\n" + 
    		 "		}\r\n" + 
    		 "		Mockito.when(req.getParameterNames())\r\n" + 
    		 "			.thenReturn(names.elements()); 	\r\n" + 
    		 "		//session\r\n" + 
    		 "		/*setup CSRF data*/\r\n" + 
    		 "\r\n" + 
    		 "		/*sessionMap.put(WebConst.CSRF_SEED, \"csrfseed\");\r\n" + 
    		 "		sessionMap.put(WebConst.CSRF_HASH, \"csrfhash\");\r\n" + 
    		 "		\r\n" + 
    		 "		HttpSession session = Mockito.mock(HttpSession.class);\r\n" + 
    		 "		Mockito.when(req.getSession(false))\r\n" + 
    		 "			.thenReturn(session);  \r\n" + 
    		 "		Mockito.when(req.getSession(true))\r\n" + 
    		 "			.thenReturn(session);\r\n" + 
    		 "		\r\n" + 
    		 "		Set<String> sKeys=sessionMap.keySet();\r\n" + 
    		 "		\r\n" + 
    		 "		for(String key:sKeys){\r\n" + 
    		 "			Mockito.when(session.getAttribute(key))\r\n" + 
    		 "				.thenReturn(sessionMap.get(key));\r\n" + 
    		 "\r\n" + 
    		 "		}\r\n" + 
    		 "		//session validation will fail without this\r\n" + 
    		 "		Mockito.when(session.getAttribute(WebConst.CSRF_AGENT))\r\n" + 
    		 "			.thenReturn(\"testAgent\");\r\n" + 
    		 "		Mockito.when(session.getAttribute(WebConst.CSRF_IP))\r\n" + 
    		 "			.thenReturn(\"127.0.0.1\");\r\n" + 
    		 "		\r\n" + 
    		 "*/\r\n" + 
    		 "		setupSession(req,sessionMap);\r\n" + 
    		 "		return req;\r\n" + 
    		 "	}\r\n" + 
    		 "	public static HttpServletRequest mapUri(String uri){\r\n" + 
    		 "		\r\n" + 
    		 "		HttpServletRequest req = Mockito.mock(HttpServletRequest.class);\r\n" + 
    		 "		HttpSession session= Mockito.mock(HttpSession.class);\r\n" + 
    		 "		Mockito.when(req.getSession())\r\n" + 
    		 "		.thenReturn(session ); \r\n" + 
    		 "		Mockito.when(req.getServletPath())\r\n" + 
    		 "			.thenReturn(uri); \r\n" + 
    		 "		HashMap<String,String> sessionMap=new HashMap<String,String>();\r\n" + 
    		 "		setupSession(req,sessionMap);\r\n" + 
    		 "		return req;\r\n" + 
    		 "	}\r\n" + 
    		 "	public static HttpServletResponse resp() {\r\n" + 
    		 "		HttpServletResponse res= Mockito.mock(HttpServletResponse.class);\r\n" + 
    		 "		return res;\r\n" + 
    		 "		\r\n" + 
    		 "	}\r\n" + 
    		 "}";
    		 
    private String mockRespTemplete=
    	 "package  org.javaWebGen.test;\r\n" + 
   		 "\r\n" + 
   		 "import java.io.IOException;\r\n" + 
   		 "import java.io.PrintWriter;\r\n" + 
   		 "import java.io.StringWriter;\r\n" + 
   		 "import java.util.Collection;\r\n" + 
   		 "import java.util.Locale;\r\n" + 
   		 "\r\n" + 
   		 "import javax.servlet.ServletOutputStream;\r\n" + 
   		 "import javax.servlet.http.Cookie;\r\n" + 
   		 "import javax.servlet.http.HttpServlet;\r\n" + 
   		 "import javax.servlet.http.HttpServletResponse;\r\n" + 
   		 "\r\n" + 
   		 "/**\r\n" + 
   		 " * This mock class is created to enable basic unit testing of the\r\n" + 
   		 " * Only methods used in the unit test\r\n" + 
   		 " * have a non-trivial implementation.\r\n" + 
   		 " * \r\n" + 
   		 " * Feel free to change this class or replace it using other ways for testing\r\n" + 
   		 " * {@link HttpServlet}s, e.g. Spring MVC Test or Mockito to suit your needs.\r\n" + 
   		 " */\r\n" + 
   		 "public class MockHttpResponse implements HttpServletResponse {\r\n" + 
   		 "\r\n" + 
   		 "  private String contentType;\r\n" + 
   		 "  private StringWriter writerContent = new StringWriter();\r\n" + 
   		 "  private PrintWriter writer = new PrintWriter(writerContent);\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void setContentType(String contentType) {\r\n" + 
   		 "    this.contentType = contentType;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public String getContentType() {\r\n" + 
   		 "    return contentType;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public PrintWriter getWriter() throws IOException {\r\n" + 
   		 "    return writer;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  public StringWriter getWriterContent() {\r\n" + 
   		 "    return writerContent;\r\n" + 
   		 "  }\r\n" + 
   		 "  \r\n" + 
   		 "  // anything below is the default generated implementation\r\n" + 
   		 "  \r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void flushBuffer() throws IOException {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public int getBufferSize() {\r\n" + 
   		 "    return 0;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public String getCharacterEncoding() {\r\n" + 
   		 "    return null;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public Locale getLocale() {\r\n" + 
   		 "    return null;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public ServletOutputStream getOutputStream() throws IOException {\r\n" + 
   		 "    return null;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public boolean isCommitted() {\r\n" + 
   		 "    return false;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void reset() {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void resetBuffer() {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void setBufferSize(int arg0) {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void setCharacterEncoding(String arg0) {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void setContentLength(int arg0) {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void setLocale(Locale arg0) {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void addCookie(Cookie arg0) {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void addDateHeader(String arg0, long arg1) {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void addHeader(String arg0, String arg1) {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void addIntHeader(String arg0, int arg1) {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public boolean containsHeader(String arg0) {\r\n" + 
   		 "    return false;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public String encodeRedirectURL(String arg0) {\r\n" + 
   		 "    return null;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public String encodeRedirectUrl(String arg0) {\r\n" + 
   		 "    return null;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public String encodeURL(String arg0) {\r\n" + 
   		 "    return null;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public String encodeUrl(String arg0) {\r\n" + 
   		 "    return null;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void sendError(int arg0) throws IOException {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void sendError(int arg0, String arg1) throws IOException {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void sendRedirect(String arg0) throws IOException {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void setDateHeader(String arg0, long arg1) {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void setHeader(String arg0, String arg1) {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void setIntHeader(String arg0, int arg1) {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void setStatus(int arg0) {\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  @Override\r\n" + 
   		 "  public void setStatus(int arg0, String arg1) {\r\n" + 
   		 "  }\r\n" + 
   		 "  \r\n" + 
   		 "  // Servlet API 3.0 and 3.1 methods\r\n" + 
   		 "  public void setContentLengthLong(long length) {  \r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  public int getStatus() {\r\n" + 
   		 "    return 0;\r\n" + 
   		 "  }\r\n" + 
   		 "  \r\n" + 
   		 "  public String getHeader(String name) {\r\n" + 
   		 "    return null;\r\n" + 
   		 "  }\r\n" + 
   		 "\r\n" + 
   		 "  public Collection<String> getHeaders(String name) {\r\n" + 
   		 "    return null;\r\n" + 
   		 "  }\r\n" + 
   		 "  \r\n" + 
   		 "  public Collection<String> getHeaderNames() {\r\n" + 
   		 "    return null;\r\n" + 
   		 "  }\r\n" + 
   		 "}\r\n" + 
   		 "";


	@Override
	protected void postExecute() throws UtilException {
	      String fileName=getFilePath()+File.separator+"MockRequestHelper"+".java";
	      File file = new File(fileName);
	      if(!file.exists() ){  //only write if file does not exist
		   	   try {
		       //if(true){  //only write if file does not exist
		       	PrintWriter out = new PrintWriter(file);
			        out.print(this.requestHelper);
			        out.flush();
			        out.close();
		   	   }catch(IOException e) {
		   		   throw new UtilException(UtilException.CODE_GENERATOR_EXEC,e);
		   	   }
		       log.info("---Write TestCase "+fileName);   
	      } 
	      fileName=getFilePath()+File.separator+"MockHttpResponse"+".java";
	      file = new File(fileName);
	      if(!file.exists() ){  //only write if file does not exist
		   	   try {
		       //if(true){  //only write if file does not exist
		       	PrintWriter out = new PrintWriter(file);
			        out.print(this.mockRespTemplete);
			        out.flush();
			        out.close();
		   	   }catch(IOException e) {
		   		   throw new UtilException(UtilException.CODE_GENERATOR_EXEC,e);
		   	   }
		       log.info("---Write TestCase "+fileName);
   
	      } 
		
	}


}
