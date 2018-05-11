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

import java.text.NumberFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

 

public class HtmlNumberField extends HtmlField{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 4161612693105051545L;
	private static final Logger log=LoggerFactory.getLogger(HtmlField.class); 
	private static final String INPUT_TYPE="type='number'";
	public static final String INVALID_NUMBER_MESSAGE="Enter a valid number";
	public static final String INVALID_NUMBER_KEY="form.errors.number";
	public static final NumberFormat INTEGER_FORMAT= NumberFormat.getIntegerInstance();
	public HtmlNumberField(String fieldName){
		super(fieldName);
		
	}
	public HtmlNumberField(String fieldName,boolean required){
		super(fieldName,required);
		
	}
	
	public HtmlNumberField(String fieldName,boolean required,String label){
		super(fieldName,required,label);
	}
	public HtmlNumberField(String fieldName,boolean required,String label,String attributes){
		super(fieldName,required,label,attributes);
	}
 

	public String getField(){
		StringBuffer htmlBuffer=new StringBuffer();
		
			htmlBuffer.append("<input "+this.getDefaultAttributes()+INPUT_TYPE); 
			 
			htmlBuffer.append(" placeholder='"+this.getToolTip()+"'");
			if(this.isRequired()){
				htmlBuffer.append(" required");
			}
			if(this.isViewOnly()){
				htmlBuffer.append(" readonly");
			}
			htmlBuffer.append(" />\n");

			if(this.isFieldValid){
				htmlBuffer.append("<!-- valid number "+this.getValue()+"-->");	 
			}else{
				htmlBuffer.append("<label class='help-block has-error' for='"+this.getName()+"'> "+this.getErrorMessage()+"</label>");
	 
			}
		
		return htmlBuffer.toString();
	}
	/**
	 * is this really a number
	 */
	@Override
	public boolean validate(String value){
		boolean val=super.validate(value);
		log.info(this+".validate("+value+")");
		if(val&&value!=null) { 
			try{
				Long.parseLong(value );
			}catch(NumberFormatException e){ //not a number
				log.info("not a number "+value );
				this.setErrorMessage(this.getProps(INVALID_NUMBER_KEY, INVALID_NUMBER_MESSAGE) ); 
				 
				val=false;
				this.isFieldValid=false;
			}
		}
		return val;
	}
	@Override
	public void cleanField() {
		if(this.getValue()!=null){
			this.setValue (this.getValue().trim() );
		}
		
	}

	@Override
	public String getJQueryFieldValidate() {
		StringBuffer json=new StringBuffer("");
		
		json.append(this.getName()+":{\n");
		json.append("    number:true,\n");
		if(this.isRequired() ){
			json.append("    required:true,\n");
		}
		json.append("},\n");
		return json.toString();
	}
	@Override
	public String getJQueryFieldScript() {
		 
		return EMPTY;
	}
	/*
	 *@return value as a long number
	 *@throws NumberFormatException if value is not a number  
	 * 
	 */
	public Long getLongValue()throws NumberFormatException {
		String idstr=this.getValue();
		Long id=Long.parseLong(idstr);
		return id;
	}

}
