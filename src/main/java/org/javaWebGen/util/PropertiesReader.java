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

import java.io.*;
import java.util.ListResourceBundle;
import java.util.ResourceBundle;
import java.util.PropertyResourceBundle;
import java.util.MissingResourceException;
import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/******************************
*class reads messages from a resource bundle
* or config properties
* @version $Revision: 1.2 $
* @author Kevin Scott
********************************/
public class PropertiesReader implements  Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 6977704876067962990L;
	private String bundleName = null;
    private ResourceBundle bundle = null;
    private static final Object[] EMPTY_ARR = {};
    private static final Logger log = LoggerFactory.getLogger(PropertiesReader.class);
    
    /********************************************************
    * get a propertiesReader using a file object pointing to a valid file
    *@param propertiesFile file object of a valid properties file
    *********************************************************/
    public static synchronized PropertiesReader getReader(File propertiesFile) throws IOException
    {
      FileInputStream fis = new FileInputStream(propertiesFile);
      PropertyResourceBundle bundle = new PropertyResourceBundle(fis);
      return new PropertiesReader( bundle, propertiesFile.getAbsolutePath() );
    }
    
    /********************************************************
    * get a propertiesReader using a resourse bundle
    *@param bundleName name of the resouce bundle
    *********************************************************/
    public static synchronized PropertiesReader getReader(String bundleName) throws MissingResourceException
    {
    	ResourceBundle bundle=null;
    	try{
    		bundle = ResourceBundle.getBundle( bundleName );
    	}catch(MissingResourceException e){
    		bundle = new EmptyResources();
    		log.warn("count not find "+bundleName +" in classpath defaulting to empty message bundle");
    	}
    	log.debug("got bundle="+bundle);
        return new PropertiesReader( bundle, bundleName );
    }
    
    public String getName()
    {
        return this.bundleName;
    }
    
    /**
    * Returns a value from the StringBundle they matches the key value
    *
    * @param key key to look for in the String Bungle
    * @return value from the string bundle in boolean format
    */
    public boolean getBooleanValue(String key) throws NumberFormatException
    {
        String message = getProperty( key );
        boolean value = message.equalsIgnoreCase("true");
        return value;
    }
    
    /**
    * Returns a value from the StringBundle they matches the key value
    *
    * @param key key to look for in the String Bundle
    * @return value from the string bundle in double format
    */
    public double getDoubleValue(String key) throws NumberFormatException
    {
        return Double.parseDouble( getProperty( key ) );
    }
    
    
    /**
    * Returns a value from the StringBundle they matches the key value
    *
    * @param key key to look for in the String Bungle
    * @return value from the string bundle in long format
    */
    public long getLongValue(String key) throws NumberFormatException
    {
        return Long.parseLong( getFormattedMessage( key, EMPTY_ARR ) );
    }
    
    /**
    * Returns a value from the StringBundle they matches the key value
    *
    * @param key key to look for in the String Bungle
    * @return value from the string bundle
    */
    public String getFormattedMessage( String key )
    {
        return getFormattedMessage( key, EMPTY_ARR );
    }  //end getMessage
    
    /**
    * Returns a value from the StringBundle they matches the key value
    * will also substitute {} with values in a Object Array
    *
    * @see java.text.MessageFormat
    * @param key key to look for in the String Bungle
    * @return value from the string bundle
    */
    public String getFormattedMessage( String key, String parm )
    {
        Object[] o = {parm};
        return getFormattedMessage( key, o );
    }  //end getMessage
    
    /**
    * Returns a value from the StringBundle they matches the key value
    * will also substitute {} with values in a Object Array
    *
    * @see java.text.MessageFormat
    * @param key key to look for in the String Bungle
    * @return value from the string bundle
    */
    public String getFormattedMessage( String key, String parm1, String parm2 )
    {
        Object[] o = {parm1,parm2};
        return getFormattedMessage( key, o );
    }  //end getMessage
    
    /**
    * Returns a value from the StringBundle they matches the key value
    * will also substitute {} with values in a Object Array
    *
    * @see java.text.MessageFormat
    * @param key key to look for in the String Bungle
    * @return value from the string bundle
    */
    public String getFormattedMessage( String key, String parm1, String parm2,String parm3 )
    {
        Object[] o = {parm1,parm2,parm3};
        return getFormattedMessage( key, o );
    }  //end getMessage
    
    
    /**
    * Returns a value from the StringBundle that matches the key value
    *
    * @param key key to look for in the String Bungle
    * @return value from the string bundle
    */
    public String getFormattedMessage( String key, Object[] parms )
    {
        String value = getResource( key );
        if(parms.length <=10){
            value = MessageFormat.format( value, parms );
            return value;
        }/*else{
            value=StringUtil.replace(value,parms);
           // log.info("MessageFormat recieved too many parms using StringUtil.replace()\n");
            return value;
        }*/
        return value;
    }  //end getMessage
    
    /**
    * Returns a value a value from the StringBundle that matches the key value
    * this method is used to get configuration values as opposed to
    * messages like the getMessage method
    * it does not try to format the value returned in any way
    *
    * @param key key to look for in the String Bungle
    * @return value from the string bundle
    */
    public String getProperty( String key )
    {
        String value = getResource( key );
        return value;
    }  //end getProperty
    
    /**
     * Returns a value a value from the StringBundle that matches the key value
     * this method is used to get configuration values as opposed to
     * messages like the getMessage method
     * it does not try to format the value returned in any way
     *
     * @param key key to look for in the String Bungle
     * @param defaultValue value if not found
     * @return value from the string bundle
     */
     public String getProperty( String key,String defaultValue )
     {
     	String value=null;
        try{
        	value = getResource( key);
        }catch(MissingResourceException e){
         	log.warn("Could not find property for key="+key+" using default="+defaultValue);
         	return defaultValue;         	   		
        }
         if(value==null){
         	log.warn("value for key="+key+" was [NULL] using default="+defaultValue);
         	return defaultValue;   
         }else{
         	//log.debug("for property key="+key+" found value="+value);
         	return value;
         }
     }  //end getProperty
      
    private PropertiesReader(ResourceBundle bundle, String bundleName)
    {
        this.bundle = bundle;
        this.bundleName = bundleName;
    }
    /**
     * get a value from a resource bundle
     * @param key
     * @return value
     * @throws MissingResourceException
     */
    private String getResource(String key) throws MissingResourceException {
		String resource = "";
		if(key!=null){
			resource = bundle.getString(key);
		}else{
			log.warn("key was [NULL] will return an empty String! I doubt this is what you want!!!");
		}
		return resource;
	}
    public static class EmptyResources extends ListResourceBundle {
        protected Object[][] getContents() {
            return new Object[][] {
           };
        }
    }
}