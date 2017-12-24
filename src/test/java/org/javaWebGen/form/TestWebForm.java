package org.javaWebGen.form;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.javaWebGen.config.WebConst;
import org.javaWebGen.util.MockRequestHelper;
import org.javaWebGen.webform.BookForm;
import org.javaWebGen.webform.InventoryForm;
import org.javaWebGen.webform.LocationForm;
import org.junit.Assert;
import org.junit.Test;

public class TestWebForm {
	
	@Test
	public void testBookForm() {
		HashMap<String,String> mapParms = new HashMap<String,String>();
		HashMap<String,String> sessionMap = new HashMap<String,String>();
		sessionMap.put(WebConst.CSRF_SEED, "csrfseed");
		sessionMap.put(WebConst.CSRF_HASH, "csrfhash");
		
		HttpServletRequest req=MockRequestHelper.mapNamesAndSession(mapParms,sessionMap);
		
		BookForm form = new BookForm(req);
		//Assert.assertTrue(form.isValid() );
	}
	@Test
	public void tesInventoryForm() {
		HashMap<String,String> mapParms = new HashMap<String,String>();
		HashMap<String,String> sessionMap = new HashMap<String,String>();
		sessionMap.put(WebConst.CSRF_SEED, "csrfseed");
		sessionMap.put(WebConst.CSRF_HASH, "csrfhash");
		
		HttpServletRequest req=MockRequestHelper.mapNamesAndSession(mapParms,sessionMap);
		InventoryForm form = new InventoryForm(req);
		//Assert.assertTrue(form.isValid() );
		
	}
	@Test
	public void testLocationForm() {
		HashMap<String,String> mapParms = new HashMap<String,String>();
		HashMap<String,String> sessionMap = new HashMap<String,String>();
		sessionMap.put(WebConst.CSRF_SEED, "csrfseed");
		sessionMap.put(WebConst.CSRF_HASH, "csrfhash");
		
		HttpServletRequest req=MockRequestHelper.mapNamesAndSession(mapParms,sessionMap);
		LocationForm form = new LocationForm(req);
		//Assert.assertTrue(form.isValid() );
		
		
	}
	@Test
	public void testSecurForm() {
		HashMap<String,String> mapParms = new HashMap<String,String>();
		HashMap<String,String> sessionMap = new HashMap<String,String>();

		
		HttpServletRequest req=MockRequestHelper.mapNamesAndSession(mapParms,sessionMap);
		SecureForm form = new SecureForm(req);
		
		 Assert.assertTrue(form.isValid() );
		
		
	}
	@Test
	public void testTestForm() {
		TestForm form = new TestForm();

		form.getEmail().setValue("g@g.com");

		form.getNumber().setValue("11");
		form.getPercent().setValue("99.9");
		form.getCurrency().setValue("$199.01");
		form.getText().setValue("text");
		form.getTextArea().setValue("textarea");
		form.getHidden().setValue("hidden");
		form.getDate().setValue("1/10/2017");
		form.getTime().setValue("21:10:10");
		form.getDatetime().setValue("10/10/2017 21:10:10");
		boolean valid= form.isValid();
		Assert.assertTrue("form is Valid"+valid,valid );
		
		
	}

}
