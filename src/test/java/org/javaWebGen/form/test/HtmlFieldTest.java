package org.javaWebGen.form.test;

 
//import org.javaWebGen.webform.BookForm;
//import org.javaWebGen.webform.PublisherForm;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;

import org.javaWebGen.form.HtmlDateField;
import org.javaWebGen.form.HtmlNumberField;
import org.javaWebGen.form.HtmlSelectField;
import org.javaWebGen.form.HtmlTextField;
import org.javaWebGen.form.HtmlUrlField;
import org.javaWebGen.util.MockRequestHelper;

import org.junit.Assert;
import org.junit.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UNIT test for webform classes
 * @author scotkevi
 *
 */
public class HtmlFieldTest {
	private static final Logger log=LoggerFactory.getLogger(HtmlFieldTest.class);//begin exec
	

	@Test
	public void testDate() throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, ParseException{
		DateConverter dateConverter = new DateConverter(null);
		dateConverter.setPattern("MM/dd/yyyy");
		ConvertUtils.register(dateConverter, java.util.Date.class);
		MockDataBean bean = new MockDataBean();
		String dateStr="09/06/2014";
		java.util.Date td=DateFormat.getDateInstance(DateFormat.SHORT).parse(dateStr);
		
		java.util.Date d=(Date) ConvertUtils.convert(dateStr, java.util.Date.class);
		log.debug("date="+d);
		PropertyUtils.setProperty(bean,"date",  d);
		log.debug("bean.date="+bean.getDate()+"?value="+dateStr );
		Assert.assertTrue("bean.date="+bean.getDate()+"?"+td,bean.getDate().equals(td));
	}
	@Test
	public void testDateField(){
		HtmlDateField field= new HtmlDateField("testDate",true);
		field.setValue("  "); 
		Assert.assertNotNull("error is?"+field.getErrorMessage(),field.getErrorMessage() );
	 	Assert.assertFalse("''  is not a date"+field.getValue(),field.getValue().length()<1 );
	 	System.out.println(field);
	  
	 	field.setValue(null); 
	 	Assert.assertFalse("null required date?"+field.getValue()+field.isValid(),field.isValid() ); 
	 	//Assert.assertTrue("field is emptyString?"+field.getValue()+"|",field.getValue().length()<1 );
	 	
	 	field.setValue(" "); 
		Assert.assertFalse("is valid date ?"+field.getValue()+field.isValid(),field.isValid() ); 
	
		//System.out.println(field);
	 
		
		field.setValue("04/04/2014");
		Assert.assertTrue("is valid date?"+field.getValue(),field.isValid() );
		//System.out.println(field);
		 		
		field.setValue("04.04.14");
		Assert.assertFalse("is valid date?"+field.getValue()+field.isValid(),field.isValid() );
		
		field.setValue("04.04.2018");
		Assert.assertFalse("is valid date?"+field.getValue()+field.isValid(),field.isValid() );
		//System.out.println(field);
		 
		field.setValue("04-04-2018");
		Assert.assertFalse("is valid date?"+field.getValue()+field.isValid(),field.isValid() );
		
		field.setValue("2019-03-04");
		Assert.assertTrue("is valid date?"+field.getValue()+field.isValid(),field.isValid() );
		//System.out.println(field); 

		 
	}
	@Test
	public void testNumberField(){
		HtmlNumberField field= new HtmlNumberField("testNumber",true);
		Assert.assertFalse("is valid num?"+field.getValue()+field.isValid(),field.isValid() ); 
		System.out.println(field);
		
		HtmlNumberField field1= new HtmlNumberField("testNumber");
		Assert.assertTrue("is valid num?"+field1.getValue()+field1.isValid(),field1.isValid() ); 
	}
	@Test
	public void testTextField(){
		HtmlTextField field= new HtmlTextField("testText",true);
 
		Assert.assertFalse("is valid ?"+field.getValue()+field.isValid(),field.isValid() ); 
		
		
		HtmlTextField field1= new HtmlTextField("testText" );
		Assert.assertTrue("is empy non required field allowed?"+field1.getValue()+field1.isValid(),field1.isValid() );
		field1.setValue("lkdfjslkj");
		Assert.assertTrue("is valid num?"+field1.getValue()+field1.isValid(),field1.isValid() ); 
		
		System.out.println(field1);
	}
	@Test
	public void testUrlfield(){
		HtmlTextField field= new HtmlTextField("url",true);
		 
		Assert.assertFalse("ifield is requred?"+field.getValue()+field.isValid(),field.isValid() ); 
		
		
		HtmlUrlField field1= new HtmlUrlField("field1",true );
		Assert.assertFalse("is empy non required field allowed?"+field1.getValue()+field1.isValid(),field1.isValid() );
		String testUrl="http://www.google.com/";
		field1.setValue(testUrl);
		Assert.assertTrue(field1.getValue()+"?"+testUrl,field1.getValue().equals(testUrl) ); 
		Assert.assertTrue("is valid url?"+field1.getValue()+field1.isValid(),field1.isValid() ); 
		MockForm form = new MockForm();
		MockDataBean bean = new MockDataBean();
		HttpServletRequest req = MockRequestHelper.mapName("url", testUrl);
		
		//when(request.getAttribute(b)).thenReturn("20");mockReq.se
		//mockReq.set.setParameter("url", testUrl);
		form.setData(bean,req);
		Assert.assertTrue("form.value"+form.url.getValue(),form.url.getValue().equals(testUrl) ); 

 
	}
	
	@Test
	public void testHidden(){
		MockForm form = new MockForm();
		MockDataBean bean = new MockDataBean();
		 
		String hiddenStr="hidden form data";
	 
		HttpServletRequest req = MockRequestHelper.mapName("hidden", hiddenStr);
		form.setData(bean,req);
		Assert.assertTrue("form.value="+form.hidden.getValue(),form.hidden.getValue().equals(hiddenStr));
		bean=(MockDataBean)form.getData();
		Assert.assertTrue("bean.value="+bean.getHidden(),bean.getHidden().equals(bean.getHidden()));
	}

	@Test
	public void testDecimal(){
		MockForm form = new MockForm();
		MockDataBean bean = new MockDataBean();
		String decimalStr="127.03";
		 
		 
		BigDecimal decimal= BigDecimal.valueOf(127.03);
		bean.setDecimal(decimal);
		Assert.assertTrue(decimal.equals(bean.getDecimal() ) );
		HttpServletRequest req = MockRequestHelper.mapName("decimal", decimalStr);
		form.setData(bean,req);
		form.getData();
		//Assert.assertTrue("form.decimal="+formBean.getDecimal()+" formBean.decimal="+formBean.getDecimal(),decimal.compareTo(formBean.getDecimal() )==0 );
		Assert.assertTrue(form.decimal.getValue().equals(decimalStr) );
		String badStr="<a href='javascript:alert('gotya');'";
		req = MockRequestHelper.mapName("decimal", badStr);
		 
		form.setData(bean,req);
		log.debug(form.decimal.getValue() );
		Assert.assertFalse("invlid form",form.decimal.isValid() );
		Assert.assertTrue("has and error messege",form.decimal.getErrorMessage().length()>0); 
	 
		Assert.assertFalse(form.decimal.getValue().equals(decimalStr) ); 
		Assert.assertTrue("form="+form.decimal.getValue()+"?"+badStr,form.decimal.getValue().equals(badStr) );
		log.debug("form="+form.decimal.getValue()+"?"+badStr);
		badStr="$10,000.00";
		req = MockRequestHelper.mapName("decimal", badStr);
		 
		form.setData(bean,req);
		log.debug(form.decimal.getValue() );	
		Assert.assertFalse("invlid form",form.decimal.isValid() );
		
		form.getDecimal().setValue(null);
		Assert.assertTrue("invlid form",form.decimal.isValid() );
	}
	@Test
	public void testCurrency(){
		MockForm form = new MockForm();
		MockDataBean bean = new MockDataBean();
		//HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
		String currencyStr="1,270.03";
		 

		BigDecimal currency= new BigDecimal("1270.03");
		
		bean.setCurrency(currency);
		Assert.assertTrue(currency.equals(bean.getCurrency() ) );
		HttpServletRequest req = MockRequestHelper.mapName("currency", currencyStr);
		form.setData(bean,req);
		bean=(MockDataBean)form.getData();
		//Assert.assertTrue("form.decimal="+formBean.getDecimal()+" formBean.decimal="+formBean.getDecimal(),decimal.compareTo(formBean.getDecimal() )==0 );
		Assert.assertTrue("form.value="+form.currency.getValue(),form.currency.getValue().equals(currencyStr) );
		String badStr="<a href='javascript:alert('gotya')>;'";
		req = MockRequestHelper.mapName("currency", badStr);
		 
		
		form.setData(bean,req);
		log.debug("bad="+form.currency.getValue() );
		
		Assert.assertFalse("invlid form.currency"+form.currency.isValid(),form.currency.isValid() );
		String msg=form.currency.getErrorMessage();
		log.debug("err.msg="+msg+"");
		
		Assert.assertTrue("has and error messege",form.currency.getErrorMessage().length() >0); 
		//Assert.assertTrue(form.getErrors().containsKey("currency"));
		Assert.assertTrue(form.currency.getValue().equals(";'") ); //field was cleaned
		req = MockRequestHelper.mapName("currency", "");
		 
		form.currency.setRequired(true);
		form.setData(bean,req);
		Assert.assertFalse("req field form.currency"+form.currency.isValid(),form.currency.isValid() );
		
		form.currency.setRequired(false);
		form.setData(bean,req);
		Assert.assertTrue("good form.currency"+form.currency.isValid(),form.currency.isValid() );
		badStr="gggggggggg";
		req = MockRequestHelper.mapName("currency", badStr);
		form.setData(bean,req);
		Assert.assertTrue(badStr+" bad string is valid currency?"+form.currency.isValid(),! form.currency.isValid() );
	}
	@Test
	public void testEmail(){
		MockForm form = new MockForm();
		MockDataBean bean = new MockDataBean();
		
		String emailStr="kevin.scott@hp.com";
	 
		 
		bean.setEmail(emailStr);
		HttpServletRequest req = MockRequestHelper.mapName("email", emailStr);
		form.setData(bean,req);
		Assert.assertTrue(form.email.getValue().equals(emailStr) );
		Assert.assertTrue(form.email.isValid() );
		MockForm badForm= new MockForm();
		
		String badStr="<a href='javascript:alert('gotya');'></a>test";
		req = MockRequestHelper.mapName("email", badStr); 
		log.debug("=============parm="+req.getParameter("email"));
		badForm.setData(bean,req);
		Assert.assertTrue("form.value"+badForm.email.getValue(),badForm.email.getValue().equals("test") );
	    //is field valid
		Assert.assertFalse("value="+badForm.email.isValid(),badForm.email.isValid() );
		badStr="kevin@ hp.com";
		req = MockRequestHelper.mapName("email", badStr); 
 
		badForm.setData(bean,req);
		Assert.assertTrue("badform email value is wrong="+badForm.email.getValue(),badForm.email.getValue().equals(badStr ) );
		Assert.assertFalse("badform isValid="+badForm.email.getValue()+"?"+badForm.email.isValid(),badForm.email.isValid() );
		log.debug("email.isValid="+badForm.email.isValid());
		badStr="kevin.hp.com";
		req = MockRequestHelper.mapName("email", badStr); 
		 
		badForm.setData(bean,req);
		Assert.assertFalse(badForm.email.getValue()+"?"+badForm.email.isValid(),badForm.email.isValid() );
		
		
	}
	@Test
	public void testCheckbox(){
		MockForm form = new MockForm();
		new MockDataBean();
		
		String emailStr="kevin.scott@hp.com";
		HttpServletRequest req = MockRequestHelper.mapName("checkbox", emailStr);
		 
	 
		log.debug("req"+req);
		log.debug("req.parm:"+req.getParameter("checkbox"));
		log.debug("req.parm:"+req.getParameter("checkbox"));
		form.setData(req);
		Assert.assertTrue("", form.checkbox.getValue().equals(emailStr));
		
	
	}
	@Test
	public void testRadio(){
		MockForm form = new MockForm();
		new MockDataBean();
		String emailStr="kevin.scott@hp.com";
		 
		HttpServletRequest req = MockRequestHelper.mapName("radio", emailStr);

	 
		form.setData(req);
		Assert.assertTrue("", form.radio.getValue().equals(emailStr));
		
	
	}
	
	@Test
	public void testDropdown(){
		MockForm form = new MockForm();
		new MockDataBean();
		HttpServletRequest req = MockRequestHelper.mapName("dropdown", "white");

		LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
		map.put("red","red");
		map.put("white","white");
		map.put("blue","blue");
		
		form.dropdown.setValueMap(map);

	 
		form.setData(req);
		log.debug("select field="+form.dropdown);
		Assert.assertTrue("form.isValid=",form.dropdown.isValid() );
		Assert.assertTrue("form.value="+form.dropdown.getValue(),form.dropdown.getValue().equals("white"));
		req=MockRequestHelper.mapName("dropdown", "white");
		form.dropdown = new HtmlSelectField("dropdown",true);
		form.addField(form.dropdown);
		form.setData(req);
		
		Assert.assertTrue(form.dropdown.isValid() );
		
	}
	
	
}
