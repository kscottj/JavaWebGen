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

import org.apache.commons.validator.routines.EmailValidator;


public class HtmlEmailField extends HtmlField{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8972986574473398997L;

	private static final String INPUT_TYPE="type='email'";
	private static final String INVALID_MSG_KEY = "form.error.email";
	private static final String INVALID_MESSAGE = "Invalid email Address";
	public HtmlEmailField(String name){
		super(name);
		
	}
	public HtmlEmailField(String name,boolean required){
		super(name,required);
		
	}
	
	public HtmlEmailField(String name,boolean required,String label){
		super(name,required,label);
	}
	public HtmlEmailField(String name,boolean required,String label,String attributes){
		super(name,required,label,attributes);
	}

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
			htmlBuffer.append(" />");
			if(this.isFieldValid){
				 
			}else{
				htmlBuffer.append("<label class='help-block has-error'  for='"+this.getName()+"'>"+this.getErrorMessage()+"</label>");
				 
			}
 
		 
		return htmlBuffer.toString();
	}

	/**
	 * validate email address
	 */
	@Override
	public boolean validate(String value){ //maybe check for odd encoding?
		boolean val=super.validate(value);
		if(val&&value!=null&&value.length()>0) { 
			val= EmailValidator.getInstance().isValid(value);
			if (!val){
				this.isFieldValid=false;
				this.setErrorMessage(this.getProps(INVALID_MSG_KEY, INVALID_MESSAGE) );  
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
	public String getJQueryFieldScript() {
		return EMPTY;
	}
	@Override
	public String getJQueryFieldValidate() {
		StringBuffer json=new StringBuffer("");
	
			json.append(this.getName()+":{\n");
			json.append("    email: true,\n");
			if(this.isRequired() ){
				json.append("    required: true,\n");
			}
			json.append("},\n");
		return json.toString();
	}
}
