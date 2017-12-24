/*
 * =================================================================== *
 * Copyright (c) 2017 Kevin Scott All rights  reserved.
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

import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
//import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
//import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.DateTimeConverter;
import org.javaWebGen.data.DataBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Misc static String methods that needed a home somewhere.  Many date and object conversions from strings
 * 
 * @author Kevin Scott
 * @version $Revision: 1.2 $
 */
public abstract class StringUtil {

    public final static String ISO8601date_FORMAT = "yyyy-MM-dd";
    public final static String ISO8601time_FORMAT = "'T'HH:mm:ss";
    public final static String ISO8601Datetime_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    public static  DateConverter dateConverter = null ;
    public static DateTimeConverter dateTimeCoverter=null;
	public static final String DATE_PATTERN="MM/dd/yyyy";
	public static final String DATE_TIME_PATTERN="MM/dd/yyyy HH:mm:ss";
	public static final String TIME_PATTERN="HH:mm:ss";
	public static final String[] VALID_DATE_FORMATS= {DATE_PATTERN,ISO8601date_FORMAT};
	public static final String[] VALID_TIME_FORMATS= {DATE_TIME_PATTERN,ISO8601Datetime_FORMAT,ISO8601time_FORMAT,TIME_PATTERN};
	public static final String[] VALID_DATES= {DATE_PATTERN,DATE_TIME_PATTERN,TIME_PATTERN };
    @SuppressWarnings("unused")
	private static final Logger log=LoggerFactory.getLogger(StringUtil.class); 
	private static final FastDateFormat dateFormat = FastDateFormat.getInstance(DATE_PATTERN);
	private static final FastDateFormat dateTimeFormat = FastDateFormat.getInstance(DATE_TIME_PATTERN);
	private static final FastDateFormat timeFormat = FastDateFormat.getInstance(TIME_PATTERN);

	
static{
		
		dateConverter = new DateConverter(null);
		dateConverter.setPatterns(VALID_DATES);
		ConvertUtils.register(dateConverter, java.util.Date.class);
		ConvertUtils.register(new org.apache.commons.beanutils.converters.BigDecimalConverter(null), BigDecimal.class);
		ConvertUtils.register(new org.apache.commons.beanutils.converters.LongConverter(null), Long.class);
		ConvertUtils.register(new org.apache.commons.beanutils.converters.IntegerConverter(null), Integer.class);
}
    //begin private Vars
   /* public static final String EMAIL_PATTERN = 
    		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
    		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";*/
   // private static Logger log=LoggerFactory.getLogger(ConfigConst.APP_LOG);//begin private Vars
	/**
	 * 
	 * @param dateStr
	 * @return date
	 * @throws ParseException
	 */
    public static Date convertToISO8601Date(String dateStr) throws ParseException {
    	SimpleDateFormat ISO8601date = new SimpleDateFormat (ISO8601date_FORMAT);
    	return ISO8601date.parse(dateStr);
    }
    /**
     * Formatted using default java  MM/dd/YY.  Not usefull due to two digit year
     * @param date
     * @return formatted string
     */
    public static String formatShortDate(Date date){
    	if(date!=null){

    		return DateFormat.getDateInstance(DateFormat.SHORT).format(date);
    	}else{
    		return "";
    	}
    }
    /**
     * Format date using using normal MM/dd/yyyy format
     * @param date
     * @return formatted string
     */
    public static String formatDate(Date date){
    	if(date!=null){

    		return dateFormat.format(date);
    	}else{
    		return "";
    	}
    }
    /**
     * Format As as time 
     * @param date
     * @return formatted string
     */
    public static String formatTime(Date date){
    	if(date!=null){

    		return timeFormat.format(date);
    	}else{
    		return "";
    	}
    }
    /**
     * Date formated as a date time
     * @param date
     * @return formatted String
     */
    public static String formatDateTime(Date date){
    	if(date!=null){

    		return dateTimeFormat.format(date);
    	}else{
    		return "";
    	}
    }
    /**
     * Date formated as a time DateFormat.SHORT.  Not  useful due two digit year
     * @param date
     * @return formatted String
     */
    public static String formatShortTime(Date date){
    	if(date!=null){
    		return DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
    	}else{
    		return "";
    	}
    }
    
    /**
     * parse ISO date format MM/dd/yyyy
     * @param dateStr
     * @return java date object
     * @throws ParseException  invalid date
     */
    public static Date convertToDate(String dateStr) throws ParseException {
    	Date date=null;
    	if(dateStr==null || dateStr.equals("null")|| dateStr.equals("")){
    		 
    		return null;
    	}
		date=DateUtils.parseDateStrictly(dateStr,VALID_DATE_FORMATS);
 
    	return date;
    }
    /**
     * parse ISO date format MM/dd/yyyy HH:mm:ss
     * @param dateStr text to be converted
     * @return java date object
     * @throws ParseException  invalid date
     */
    public static Date convertToDateTime(String dateStr) throws ParseException {
    	Date date=null;
    	if(dateStr==null || dateStr.equals("null")|| dateStr.equals("")){
    		 
    		return null;
    	}
		date=DateUtils.parseDateStrictly(dateStr,DATE_TIME_PATTERN);
 
    	return date;
    }   
    /**
     * parse ISO time format 'T'HH:mm:ss
     * @param dateStr
     * @return date time
     * @throws ParseException invalid time
     */
    public static Date convertToTime(String dateStr) throws ParseException{
    	 
    	if(dateStr==null || dateStr.equals("null") || dateStr.equals("")){
    		
    		return null;
    	}
    	Date date=null;
    //	try{
    		 
 
				date=DateUtils.parseDate(dateStr,VALID_TIME_FORMATS);
 
    		
    	/*}catch(ParseException e){//try a different format?
    		try {
    			SimpleDateFormat iso8601Datetime = new SimpleDateFormat (ISO8601Datetime_FORMAT);
				date =iso8601Datetime.parse(dateStr);
				 
			} catch (ParseException e1) {
				 
				try {
					date=DateFormat.getTimeInstance().parse(dateStr);
				} catch (ParseException e2) {
					try{
						date=DateFormat.getTimeInstance(DateFormat.SHORT).parse(dateStr);
					}catch(ParseException e3){
						log.error("Error parsing date ignoring! date="+dateStr,e3);
					}
				}
			}
    	}*/
    	return date;
    }
    /**
     * get a date in ISO foramat yyyy-mm-dd
     * @param date
     * @return formatted date
     */
    public static String convertDateToISOString(Date date){
    	if(date==null)
    		return null;
    	SimpleDateFormat iso8601Date = new SimpleDateFormat (ISO8601date_FORMAT);
    	return iso8601Date.format(date);
    }
    /**
     * get time in ISO format  'T'HH:mm:ss
     * @param date
     * @return formatted date
     */
    public static String convertTimeToISOString(Date date){
    	if(date==null)
    		return null;
    	SimpleDateFormat iso8601time = new SimpleDateFormat (ISO8601time_FORMAT);
    	return iso8601time.format(date);
    }
	/*********************
	 * converts a byte array to a printable string will ignore empty byte that
	 * =0 used to covert byte[] buffers to a string
	 * 
	 * @param buffer of bytes to convert to a string
	 * @return a string from a byte array
	 * 
	 ********************************/
	public static String covertBytes(byte[] buffer) {
		StringBuffer str = new StringBuffer();
		int size = buffer.length;
		for (int i = 0; i < size; i++) {
			byte[] bs = new byte[1];
			bs[0] = buffer[i];
			byte b = bs[0];
			if (b != 0) {
				String temp = new String(bs);
				str.append(temp);
			}
		}
		return str.toString();

	}

	/*********************************************************
	 *replace text special tags(IE {1}) in a string like MessageFormat except
	 * it can handle unlimited number of parms (IE >10). Will skip parms if it
	 * can not find the correct tag
	 *@deprecated Use org.apache.commons.lang3.text.StrSubstitutor instead
	 *@param input text
	 *@param parms parameters
	 *@return finished string with substitution
	 *********************************************************/
	@Deprecated 
	public static String replace(String input, Object[] parms) {
		StringBuffer buffer = new StringBuffer(input);

		int size = parms.length;
		int index = -1;
		for (int i = 0; i < size; i++) {
			String tag = "{" + i + "}";
			String text = buffer.toString();
			index = text.indexOf(tag);
			if (index > -1) { // found tag
				if (parms[i] != null) {
					buffer.replace(index, index + tag.length(), parms[i]
							.toString());
				} else {
					buffer.replace(index, index + tag.length(), "[NULL]");
				}
			}
		}
		return buffer.toString();
	}

	/*********************************************************
	 *Replace text in a string will replaces <b>All</b> occurrences of the
	 * search String. <b>BEWARE</b> it returns null if searchText and
	 * replaceText are equal
	 * 
	 *@param input text to search in
	 *@param searchText  to search for
	 *@param replaceText to replace search string with
	 *@return finished text
	 *********************************************************/
	public static String replace(String input, String searchText,
			String replaceText) {
		StringBuffer buffer = new StringBuffer(input);
		if (searchText.equals(replaceText)) {
			return null;
		}
		boolean found = true;
		// int size = input.length();
		int index = -1;
		String text = buffer.toString();
		while (found) {
			index = text.indexOf(searchText);
			if (index == -1) {
				found = false;
			} else {
				buffer.replace(index, index + searchText.length(), replaceText);
				text = buffer.toString();
			}
		}

		return buffer.toString();
	}

	/**
	 * SHA1 Message Digest as hex 
	 * 
	 * @param input string
	 * @return hash of input string in Hex
	 */
	public static String sha1Hex(String input) {
		if(input==null){
			return null;
		}
		input = input.trim();
		// int size = input.length();
		/*
		byte[] inputBuff = input.getBytes();
		// byte[] digestBytes =digestPass.getBytes();
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA");
		} catch (NoSuchAlgorithmException ex) {
			log.error("Digest not found", ex);
			return null;
		}
		digest.update(inputBuff);
		byte[] inputHash = digest.digest();
		String hexHash = HexUtils.convert(inputHash);
		return hexHash;*/
		return DigestUtils.sha1Hex(input);

	}
	/**
	 * make sha2 string hash with base64
	 * @param input string to hash
	 * @return based64 encode hash
	 */
	public static String sha2Base64(String input) {
		if(input==null){
			return null;
		}
		input = input.trim();
		
		byte[] bytes =DigestUtils.sha256(input);
		return Base64.encodeBase64String(bytes);
	}

	
	/**
	 * Returns a string truncated to a specific size ignoring training spaces
	 * 
	 * @param size number of chars to allow
	 * @param input
	 * @return truncated String
	 */
	public static String truncate(int size, String input) {
		if (input != null) {
			input = input.trim();
			return input.substring(0,size);
		}
		return null;
	}

	/**
	 * Used to escape sql strings replaces ' whith \'
	 * @param input
	 * @return escaped string 
	 */
	public static String sqlEscWithSingleQuote(String input) {
		return sqlEsc(input, "'");
	}

	/**
	 * Used to escape sql strings replaces ' whith \'
	 * 
	 * @param input text
	 * @return text that is escaped  
	 */
	public static String sqlEscWithBackslash(String input) {
		return sqlEsc(input, "\\");
	}

	/**
	 * Used to escape ' with withText
	 * 
	 * @param text to add escape chars
	 * @param withText string to escape with
	 * @return text that is escaped
	 */
	public static String sqlEsc(String text, String withText) {
		StringTokenizer st = new StringTokenizer(text, "'");
		boolean firstToken = true;
		while (st.hasMoreTokens()) {
			if (firstToken) {
				text = st.nextToken();
				firstToken = false;
			} else {
				text = text + withText + "'" + st.nextToken();
			}
		}
		return new StringBuffer().append("'").append(text).append("'").toString();
	}
	
	 /**
	  * Removes email part of an email address
	  * use this to return a user name from an address
	  * In the following example: kscott.j@gmail.com
	  * It would return the kscott.j
	  * @param email
	  * @return email minus email domain
	  */
	 public static String removeEmail(String email){
		 if(email==null){
			 return null;
		 }
		 int index = email.indexOf("@");
		 if(index>0 && index+1<=email.length() ){
			 email=email.substring(0,index+1)+"...";
		 } 
		 return email;
	 }
	/*******************************
	 * get a stackTrace of an exception
	 * 
	 * @param t error
	 *@return stackTrace
	 ********************************/
	public static String getStackTrace(Throwable t) {

		StringWriter out = new StringWriter();
		PrintWriter writer = new PrintWriter(out);
		t.printStackTrace(writer);
		return out.toString();
	}
 
	/**
	 * list of data baound JavaBeans to xml
	 * @param list data bound JavaBeans
	 * @return xml of beans
	 */
	public static String dumpDataBeanList(List<DataBean> list ){
		if(list==null){
			return null;
		}
		StringBuffer xml=new StringBuffer();
		for(DataBean bean:list  ){
			xml.append(bean.toXML() );
		}
		return xml.toString();
	}
	 
}
