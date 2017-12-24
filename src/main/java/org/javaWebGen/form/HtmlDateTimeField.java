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
package org.javaWebGen.form;


import java.text.ParseException;


import org.apache.commons.validator.routines.DateValidator;
import org.javaWebGen.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTML date field.  Uses JQuery validate for client side check.  Will generate a 
 * JQuery datepicker if javascript is available
 * @author home
 *
 */
public class HtmlDateTimeField extends HtmlDateField implements DateFieldAware{
 	/**
	 * 
	 */
	private static final long serialVersionUID = -1029740496998524968L;
	//private StringBuffer htmlBuffer =new StringBuffer();
	private static final Logger log=LoggerFactory.getLogger(HtmlDateField.class);//begin exec

	//public static final String DATEPATTERN = "DATE_TIME_PATTERN";
	public static final String INVALID_MESSAGE="Invalid date and time use the "+StringUtil.DATE_TIME_PATTERN+" format";
	public static final String INVALID_MSG_KEY="form.error.datetime";
	//private static final String INVALID_DATE_KEY="type='error.date.field'";
	private java.util.Date date=null;

 
	public HtmlDateTimeField(String fieldName){
		super(fieldName);
		
	}
	public HtmlDateTimeField(String fieldName,boolean required){
		super(fieldName,required);
		
	}
	
	public HtmlDateTimeField(String fieldName,boolean required,String label){
		super(fieldName,required,label);
	}
	public HtmlDateTimeField(String fieldName,boolean required,String label,String attributes){
		super(fieldName,required,label,attributes);
	}
	public String getValue(){
		String value=super.getValue();
		if(value==null||value.equals("null")){
			return "";
		}else{
			return value;
		}
	}
	
	@Override
	public void setDate(java.util.Date date){
		this.date=date;
		this.setValue(StringUtil.formatDateTime(this.date) );
		log.debug(this+"value="+this.getValue() );
	}
	
	@Override
	public boolean validate(String value)  {
		boolean val=super.validate(value);
		
 
			try {
				StringUtil.convertToDateTime(value);
			} catch (ParseException e) {
				log.warn(value+" invalid datetime "+e.getMessage());
				this.setErrorMessage(this.getProps(INVALID_MSG_KEY, INVALID_MESSAGE) );
				this.isFieldValid=false;
				val=false;			
			}
 
	 
		return val;	 
	 
	}
	@Override
	public String getJQueryFieldScript() {
		if(this.isViewOnly()){
			return "<!--read only datepicker-->\n";
		}else{
			return "$('#"+this.getName()+"').datepicker();\n";	
		}
		
	}
	@Override
	public String getJQueryFieldValidate() {
		StringBuffer json=new StringBuffer("");
		
		json.append(this.getName()+":{\n");
		json.append("    date: true,\n");
		if(this.isRequired()){
			json.append("    required: true,\n");
		}
		json.append("},\n");
	return json.toString();

	}

}
