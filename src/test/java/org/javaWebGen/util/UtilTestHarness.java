/*
 * =================================================================== *
 * Copyright (c) 2006 Kevin Scott All rights  reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 * if any, must include the following acknowledgment:
 * "This product includes software developed by "Kevin Scott"
 * Alternately, this acknowledgment may appear in the software itself,
 * if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "Kevin Scott must not be used to endorse or promote products
 * derived from this software without prior written permission. For
 * written permission, please contact kevscott_tx@yahoo.com
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL KEVIN SCOTT BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */


package org.javaWebGen.util;
//import org.kev.data.dao.lookup.*;

import java.text.ParseException;
import java.util.Date;
import java.util.Properties;

import org.javaWebGen.config.Conf;

//import org.kev.data.bean.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
 
import org.junit.Assert;
import org.junit.Test;





import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import junit.framework.TestCase;


/**
 * Check harness for Utility methods
 */
@SuppressWarnings("deprecation")//test deprecated classes
public class UtilTestHarness extends TestCase{
    //private static int insertid=-1;
	private static final Logger log=LoggerFactory.getLogger(UtilTestHarness.class);
     public  UtilTestHarness(String name) {
             super(name);
     }

 
	public void testLog() throws Exception{
        Util.enter("testLog");
        Util.debug("Debug");
        Util.error("error");
        Util.info("info");
        
        log.debug("debug");
        log.info("info");
        log.warn("warn");
    	log.error("error");
        
    }

	  @Test
    public void testReplace() throws Exception{
       
        Object[] parms = new Object[4];
        parms[0]="zero";
        parms[1]="one";
        parms[2]="two";
        parms[3]="three";

        String text =" this is a test {0}{1}={2} {3}";
        text = StringUtil.replace(text,parms);
        log.debug("result ="+text);
        String answer = " this is a test zeroone=two three";
        
        assertTrue("incorrect replace return="+text, answer.equals(text) );
        parms = new Object[11];
        parms[0]="0";
        parms[1]="1";
        parms[2]="2";
        parms[3]="3";
        parms[4]="4";
        parms[5]="5";
        parms[6]="6";
        parms[7]="7";
        parms[8]="8";
        parms[9]="9";
        parms[10]="10";

        text ="{0}{1}{2}{3}{4}{5}{6}{7}{8}{9}{{10}";
        text=StringUtil.replace(text,parms);
        answer = "0123456789{10";
        log.debug("result ="+text);
        assertTrue("incorrect replace return="+text, answer.equals(text) );
        
        //next test
        text=" this a test find me find me";
        text=StringUtil.replace(text,"find me", "replace me");
        answer=" this a test replace me replace me";
        assertTrue("incorrect replace return="+text, answer.equals(text) );
        log.debug("result="+text);
       
    }
   /* public void testCache() throws Exception{
        Util.enter("testCache()");
            Integer[] ilist = new Integer[2];
            
            ilist[0]=new Integer(1);
            ilist[1]=new Integer(2);
            String key="1-1";
            CacheManager cache = CacheManager.getInstance();
            Object temp = cache.get(key,ilist.getClass() );
            if(temp==null){
                Util.debug("not cached so add it " );
                cache.add(key,ilist.getClass(),ilist);
            }else{
                ilist =(Integer[]) temp;
                Util.debug("got "+temp+"from cache");
            }
            temp=cache.get(key,ilist.getClass() );
            assertFalse("can I get it back out="+temp,temp==null);
            boolean found =false;
            found = cache.isCached(key,ilist.getClass() );
            assertTrue("object was not cached="+found,found);
            
            long time =1000*60*1;
            
            Util.debug(" TestDB wait for cache reaper to cleanup");
            Thread.sleep(time);
            cache.add("3-1",ilist.getClass(),ilist);
            Util.debug("size="+cache.getSize() );
            assertTrue("storing "+cache.getSize()+" in cache",cache.getSize()==1);
            
            cache.clear();
            cache.add(key,ilist.getClass(),ilist);
            Util.debug("try a change");
            cache.change(key,ilist.getClass(),ilist );
            Thread.sleep(time/6000); //allow time for network activity
            assertTrue("Size of cache after refresh="+cache.getSize(),cache.getSize()==0 );
          
        Util.leave("testCache()");
            
    }*/
	  @Test
    public void testJSon() throws JSONException{
    	JSONObject jo = new JSONObject();
    	jo.put("name", "value" );
    	jo.put("name1", "value1" );
;
    	//ArrayList list = new ArrayList();
    	StringBuffer buf = new StringBuffer("[");
    	buf.append(jo.toString()+"," );
    	
    	buf.append(jo.toString()+"," );
    	JSONArray ja = new JSONArray(buf.toString()+"]") ;
    	//JSONArray ja = JSONArray.
    	jo = ja.getJSONObject(1);
    	log.debug("jo="+jo.toString() );
    	
    }
   /* @SuppressWarnings("deprecation")
	@Test
    public void testStringUtil() throws Exception{
    	assertFalse("email=''",StringUtil.isEmail(""));
    	assertFalse("email='test#test'",StringUtil.isEmail("test#test"));
    	assertFalse("email='test#test'",StringUtil.isEmail("test@test"));
    	assertFalse("email='test#test'",StringUtil.isEmail("test.test.com"));
    	assertTrue("email='test#test'",StringUtil.isEmail("test@test.com"));
    
    }*/

    /*  public static TestSuite suite() throws Exception{
        
         

    
        TestSuite suite= new TestSuite();
        try{
            suite.addTest(new UtilTestHarness("testLog"));
            suite.addTest(new UtilTestHarness("testReplace"));
           //suite.addTest(new UtilTestHarness("testCache"));
            suite.addTest(new UtilTestHarness("testJSon"));
        }catch(Throwable t){
            Util.error(t);
        }
       
    
        return suite; 
     
    }*/
    /*public static void main (String[] args) throws Exception {
        System.out.println("\n--==Begin TestCase==--\n\n");
        junit.textui.TestRunner.run(suite());
         System.out.println("\n--== End TestCase ==--\n\n");
    }*/
    @Test
    public void testHtmlUtil(){
	    String xssAttack="<a href=\"javascript:alert('I own you');\">ha</a>";
	    String embededlink="<a href=\"http://badurl.com\">ha</a>";
	    String imbagelink="<img src='http://iwillownyou.com/badImg.png'/>";
	    String badLink=" I have > zero change of hacking this < or better most<a href='javascript:alert('gotya');'ha</a> what you gona do <a href='javascript:alert('i own you');'/>a<a>";
	    String html=null;
	    html = HtmlUtil.stripTags(xssAttack);
	    Assert.assertTrue(html,html.equals("ha"));    
	    html = HtmlUtil.stripTags(embededlink);
	    Assert.assertTrue(html,html.equals("ha"));    
	    html = HtmlUtil.stripTags(imbagelink);
	    Assert.assertTrue("html="+html+"|",html.equals(""));  
	    html = HtmlUtil.stripTags(badLink);
	    Assert.assertTrue("|"+html+"|",html.equals(" I have > zero change of hacking this  what you gona do a")); 
	
	    html = HtmlUtil.stripEvilTags(xssAttack);  
	    Assert.assertTrue("html="+html+"|",html.equals(""));  
	    html = HtmlUtil.stripEvilTags(embededlink);
	    Assert.assertTrue("html="+html+"|",html.equals(embededlink)); //no change 
	    html = HtmlUtil.stripEvilTags(imbagelink);
	    Assert.assertTrue("html="+html+"|",html.equals(imbagelink)); //no change 
	    html = HtmlUtil.stripEvilTags(badLink);
	    Assert.assertTrue("html="+html+"|",html.equals(badLink)); //no change 
    }
	@Test
	public void testConf() {
		Properties prop = Conf.getConfig();
		String value=prop.getProperty("webGen.db.jndi");
		Assert.assertTrue(value.equals("jdbc.testDB"));
		value=prop.getProperty("test","default");
		Assert.assertTrue(value.equals("1"));
		prop = Conf.getConfig("empty");//non existing properties file
		value=prop.getProperty("webGen.db.jndi");
		Assert.assertTrue(value==null);
	}
	@Test
	public void testStringUtil()   {
		
		Date date=null;
		String dateStr="10/10/2017";
		try {
			date=StringUtil.convertToDate(dateStr);
			log.info(StringUtil.formatDate(date));
		} catch (ParseException e) {
			//log.error("data error",e );
			Assert.assertTrue("invalid date "+dateStr,false);
			 
		}
		
		
		dateStr="10/10/17";
		try {
			date=StringUtil.convertToDate(dateStr);
			log.warn("format.date="+StringUtil.formatDate(date));
		} catch (ParseException e) {
			log.error("data error",e);
			Assert.assertTrue("invalid date "+dateStr,false);
		 
		}
	 
		
		dateStr="2017/10/10"; //would parse to wrong date so should reject now
		try {
			date=StringUtil.convertToDate(dateStr);
			log.info(StringUtil.formatDate(date));
		
		} catch (ParseException e) {
			log.debug(dateStr+" invalid date"+e.getMessage() );
			
			log.error(StringUtil.formatDate(date));
		}
	 		
		dateStr="2017-10-10";
		try {
			date=StringUtil.convertToDate(dateStr);
			
			log.info(StringUtil.formatDate(date));
		} catch (ParseException e) {
			log.error(dateStr+" invalid date"+e.getMessage() );
			Assert.assertTrue("should be valid date "+dateStr,false); 
		}
	 
		dateStr="10.10.2017";
		try {
			date=StringUtil.convertToDate(dateStr);
		} catch (ParseException e) {
			log.debug(dateStr+" invalid date"+e.getMessage() );
			Assert.assertFalse("should be invalid time "+dateStr,false); 
		}
		dateStr="11:10 PM";
		try {
			date=StringUtil.convertToTime(dateStr);
			//log.info(StringUtil.formatTime(date));
		
		} catch (ParseException e) {
			log.error(dateStr+" invalid date"+e);		 
			Assert.assertTrue("should be invalid time "+dateStr,false); 
			
		}
	 
		dateStr="23:10";
		try {
			date=StringUtil.convertToTime(dateStr);
			//log.info(StringUtil.formatTime(date));
		} catch (ParseException e) {
			log.error(dateStr+" invalid date"+e.getMessage() );	
			Assert.assertTrue("should be valid date "+dateStr,false); 
		}	
		dateStr="10/10/2017 21:10:10";
		try {
			date=StringUtil.convertToDateTime(dateStr);
			//log.info(StringUtil.formatDateTime(date));
		} catch (ParseException e) {
			log.error(dateStr+" invalid date"+e.getMessage() );	
			Assert.assertTrue("should be valid date "+dateStr,false); 
		}
		dateStr="10/10/2017";
		try {
			date=StringUtil.convertToDate(dateStr);
			log.info(StringUtil.formatDate(date));
		} catch (ParseException e) {
			log.error(dateStr+" invalid date"+e.getMessage() );	
			Assert.assertTrue("should be valid date "+dateStr,false); 
		}
	}

}
