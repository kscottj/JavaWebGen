package org.javaWebGen.form.test;


import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import org.javaWebGen.config.WebConst;
import org.javaWebGen.util.MockRequestHelper;
import org.javaWebGen.util.StringUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.junit.Assert;

 

public class CsrfTest {
	
	private HttpServletRequest req=null;
	//private HttpServletResponse res=null;
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(CsrfTest.class);
	

	@Test
	public void testNoCsrf() {
		HashMap<String,String> valueMap=new HashMap<String,String>();
		HashMap<String,String> sessionMap=new HashMap<String,String>();
	
		
		req=MockRequestHelper.mapNamesAndSession(valueMap, sessionMap);
		CsrfTestForm form = new CsrfTestForm(req);
		valueMap.put("textField", "test");
		req=MockRequestHelper.mapNamesAndSession(valueMap, sessionMap);
	
		
		form.setData(req);
		boolean isValid=form.isValid();
		Assert.assertFalse(isValid );
	}
	@Test
	public void testWithValidCsrf() {
		HashMap<String,String> valueMap=new HashMap<String,String>();
	
		HashMap<String,String> sessionMap=new HashMap<String,String>();
		sessionMap.put(WebConst.CSRF_SEED,MockRequestHelper.FAKE_SEED);
		req=MockRequestHelper.mapNamesAndSession(valueMap, sessionMap);
		
		CsrfTestForm form = new CsrfTestForm(req);
		String nounce=form.getCsrf().getNounce();
		
		String hash=StringUtil.sha2Base64(nounce+MockRequestHelper.FAKE_SEED);


		valueMap.put(CsrfTestForm.DEFAULT_CSRF_NAME,nounce+","+hash);		
		valueMap.put("textField", "test");
	
		req=MockRequestHelper.mapNamesAndSession(valueMap, sessionMap);
		form.setData(req);
		boolean isValid=form.isValid();
		Assert.assertTrue(isValid );
		

	}
	@Test
	public void testBadCsrfHash() {
		HashMap<String,String> valueMap=new HashMap<String,String>();
		
		HashMap<String,String> sessionMap=new HashMap<String,String>();
		sessionMap.put(WebConst.CSRF_SEED,MockRequestHelper.FAKE_SEED);
		req=MockRequestHelper.mapNamesAndSession(valueMap, sessionMap);
		
		CsrfTestForm form = new CsrfTestForm(req);
		String nounce=form.getCsrf().getNounce();
		
		String hash=StringUtil.sha2Base64(nounce+MockRequestHelper.FAKE_SEED+"bad");


		valueMap.put(CsrfTestForm.DEFAULT_CSRF_NAME,nounce+","+hash );		
		valueMap.put("textField", "test");
	
		req=MockRequestHelper.mapNamesAndSession(valueMap, sessionMap);
		form.setData(req);
		boolean isValid=form.isValid();
		Assert.assertTrue(!isValid );
		

	}

}
