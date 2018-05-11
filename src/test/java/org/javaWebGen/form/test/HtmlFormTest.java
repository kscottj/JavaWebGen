package org.javaWebGen.form.test;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.javaWebGen.data.DataBean;
import org.javaWebGen.form.HtmlField;
import org.javaWebGen.form.HtmlForm;
import org.javaWebGen.form.HtmlNumberField;
import org.javaWebGen.form.HtmlTextField;
import org.javaWebGen.util.MockRequestHelper;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 



public class HtmlFormTest {
	private static final Logger log=LoggerFactory.getLogger(HtmlFormTest.class);//begin exec
	private HtmlForm simpleForm = new HtmlForm();
	private MockForm mform= new MockForm();
	HttpServletRequest req=null;
	SimpleDataBean bean= null;
	
	
	@Before
	public void initForm(){
		log.debug("+++init form+++");
		simpleForm.addField(new HtmlNumberField("id"));	
		simpleForm.addField(new HtmlTextField("name"));
		simpleForm.addField(new HtmlNumberField("testNumber",true));
		simpleForm.setAction("/Controller/testForm");
		bean= new SimpleDataBean();
		 
		
	}

	@Test
	public void testCreateSimpleForm(){
		HashMap<String,String> simpleMap = new HashMap<String,String>();
		simpleMap.put("id", "1");
		simpleMap.put("name", "testName");
		HttpServletRequest req=MockRequestHelper.mapNames(simpleMap);
		simpleForm.setData(req);
		log.debug("field="+simpleForm);
		
		HtmlField numf=simpleForm.getField("id");
		Assert.assertEquals( "1",numf.getValue() );
		
		Assert.assertTrue("form.isvalid?", simpleForm.isValid() );
		//findbugs error this ok for a testing equals that actually is 
		//get value
 
		
 
		
		HtmlField textf=simpleForm.getField("name");
		//findbugs error this ok for a testing equals that actually is 
		//get value
		Assert.assertEquals( "testName",textf.getValue() );
		
	}
	@Test
	public void testUpdateSimpleForm(){
		
		
		bean.setId("1");
		bean.setName("testName1");
		HashMap<String,String> simpleMap = new HashMap<String,String>();
		//simpleMap.put("id", "2");
		simpleMap.put("name", "testName2");
		HttpServletRequest req=MockRequestHelper.mapNames(simpleMap);

		//request should overright any values in the DTO bean
		simpleForm.setData(bean, req);
		log.debug("field="+simpleForm);
		Assert.assertTrue("form.isvalid?", simpleForm.isValid() );
		
		HtmlField numf=simpleForm.getField("id");
		Assert.assertEquals("1",numf.getValue() );
		
		HtmlField textf=simpleForm.getField("name");
		Assert.assertEquals( "testName2",textf.getValue());
		
	}
	
	@Test
	public void testBadIdSimpleForm(){
		HashMap<String,String> simpleMap = new HashMap<String,String>();
		simpleMap.put("id", "bad number");
		simpleMap.put("name", "testName");
		HttpServletRequest req=MockRequestHelper.mapNames(simpleMap);
		
		simpleForm.setData(req);
		log.debug("field="+simpleForm);
		Assert.assertFalse("form.isvalid"+simpleForm.getField("id")+"?", simpleForm.isValid() );
		log.debug("BEGIN FORM\n"+simpleForm+"\nEND FORM" );
		
		
	}
	//@Test
	public void testMockFormSetData(){
		HtmlNumberField numf= mform.getNumber();
		Assert.assertEquals("", numf.getValue() );
		HtmlTextField textf= mform.getText();
		HashMap<String,String> simpleMap = new HashMap<String,String>();
		simpleMap.put("number", "1");
		simpleMap.put("text", "testName");
		HttpServletRequest req=MockRequestHelper.mapNames(simpleMap);
		mform.setData(req);
		Assert.assertTrue("",mform.isValid() );
		//Assert.assertTrue(numf.getValue()+"?"+"1",numf.equals("1") );
	 
		Assert.assertEquals("testName", textf.getValue() );
		
	}
	
	
	public class SimpleDataBean implements DataBean{
		public SimpleDataBean(){
			
		}
		
		private String id=null;
		private String name=null;
		
			
		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

 
		public String toXML() {
			// TODO Auto-generated method stub
			return null;
		}
	
 
		public String toJSON() throws JSONException {
			// TODO Auto-generated method stub
			return null;
		}
	
	 
		public Object[] getData() {
			// TODO Auto-generated method stub
			return null;
		}
	
 
		public void setData(Object[] parms) throws IllegalArgumentException {
			// TODO Auto-generated method stub
			
		}
	
		public int[] getDataTypes() {
			// TODO Auto-generated method stub
			return null;
		}
	}
}			
