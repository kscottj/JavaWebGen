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

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.validator.routines.PercentValidator;


public class HtmlPercentField extends HtmlField{

	private static final String INPUT_TYPE="type='number'";
	private static final long serialVersionUID = -4403234346785914723L;
	public static final NumberFormat DECIMAL_FORMAT= DecimalFormat.getNumberInstance(); 
	public static final String INVALID_MESSAGE="Enter a valid Percent";
	public static final String INVALID_MSG_KEY="form.error.percent";
	
	public static final String DECIMAL_INPUT_ATTR=" min='0' max='100' step='0.01'";
	 
	public HtmlPercentField(String fieldName){
		super(fieldName);
		this.addAttributes(DECIMAL_INPUT_ATTR);
		
	}
	public HtmlPercentField(String fieldName,boolean required){
		super(fieldName,required);
		this.addAttributes(DECIMAL_INPUT_ATTR);
		
	}
	
	public HtmlPercentField(String fieldName,boolean required,String label){
		super(fieldName,required,label);
		this.addAttributes(DECIMAL_INPUT_ATTR);
	}
	public HtmlPercentField(String fieldName,boolean required,String label,String attributes){
		super(fieldName,required,label,attributes);
		this.addAttributes(DECIMAL_INPUT_ATTR);
	}
	
	@Override
	public boolean validate(String value){
		boolean val=super.validate(value);
		if(val&&value!=null&&value.length()>0) { 
			val=PercentValidator.getInstance().isValid(value);
			if(!val){
	
				this.setErrorMessage(this.getProps(INVALID_MSG_KEY, INVALID_MESSAGE) ); 
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
					 
			}else{
				htmlBuffer.append("<label class='help-block has-error' for='"+this.getName()+"'> "+this.getErrorMessage()+"</label>");
	 
			}
		
		return htmlBuffer.toString();
	}
}
