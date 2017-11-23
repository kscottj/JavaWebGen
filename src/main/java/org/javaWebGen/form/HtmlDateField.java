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
import java.util.Date;

import org.javaWebGen.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTML date field.  Uses JQuery validate for client side check.  Will generate a 
 * JQuery datepicker if javascript is available
 * @author home
 *
 */
public class HtmlDateField extends HtmlField implements DateFieldAware{
 	/**
	 * 
	 */
	private static final long serialVersionUID = -1029740496998524968L;
	//private StringBuffer htmlBuffer =new StringBuffer();
	private static final Logger log=LoggerFactory.getLogger(HtmlDateField.class);//begin exec
	private static final String INPUT_TYPE="type='text'";
	//public static final String DATEPATTERN = "MM/dd/yyyy";
	public static final String INVALID_MESSAGE="Invalid date use the "+StringUtil.DATE_PATTERN+" format";
	public static final String INVALID_MSG_KEY="form.error.date";
	//private static final String INVALID_DATE_KEY="type='error.date.field'";
	private java.util.Date date=null;
 
	public HtmlDateField(String fieldName){
		super(fieldName);
		
	}
	public HtmlDateField(String fieldName,boolean required){
		super(fieldName,required);
		
	}
	
	public HtmlDateField(String fieldName,boolean required,String label){
		super(fieldName,required,label);
	}
	public HtmlDateField(String fieldName,boolean required,String label,String attributes){
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
	public String getField(){
		StringBuffer htmlBuffer=new StringBuffer();
			htmlBuffer.append("<input "+this.getDefaultAttributes()+INPUT_TYPE); 
			htmlBuffer.append(" placeholder='"+this.getToolTip()+"' ");
			if(this.isRequired()){
				htmlBuffer.append(" required");
			}
			if(this.isViewOnly()){
				htmlBuffer.append(" readonly");
			}
			htmlBuffer.append(" />\n");
			if(this.isFieldValid){
				 
			}else{
				htmlBuffer.append("<label class='help-block has-error'> "+this.getErrorMessage()+"</label>");
				 
			}
 
		return htmlBuffer.toString();
	}
	
	@Override
	public void setDate(java.util.Date date){
		this.date=date;
		log.debug(this+"before date format="+date);
		 
		this.setValue(StringUtil.formatDate(this.date) );
		log.debug(this+"value="+this.getValue() );
	}
	@Override
	public Date getDate(){
		/*try {
			return dateFormat.parse(this.getValue() );
		} catch (ParseException e) {
			return null;
		}*/
		return this.date;
	}
	
	@Override
	public boolean validate(String value)  {
		log.debug("validate>"+value);
		try {
			//DateUtils.parseDate(value);
			StringUtil.convertToDate(value);
		} catch (ParseException e) {
			log.warn(value+"is invalid date"+e.getMessage());
			this.setErrorMessage(this.getProps(INVALID_MSG_KEY, INVALID_MESSAGE) );
			return false;
		}
	 
		return true;
 
		
	/*	try {
			dateFormat.parse(value);
		} catch (ParseException e) {
			this.setErrorMessage(this.getProps(INVALID_MSG_KEY, INVALID_MESSAGE) );  
			return false;
		}*/
		 
	 
	}

	@Override
	public void cleanField() {
		if(this.getValue()!=null){
			String dateValue=this.getValue().trim() ;
			setValue(dateValue); 
		}	
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
