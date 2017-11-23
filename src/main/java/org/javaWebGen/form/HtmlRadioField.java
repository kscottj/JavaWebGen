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

import java.util.LinkedHashMap;

public class HtmlRadioField extends HtmlField{
	 
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 6566739791094556886L;

	private static final String INPUT_TYPE="type='radio'";

	private LinkedHashMap<String,String> radioMap=null;
	public HtmlRadioField(String name){
		super(name);
		LinkedHashMap<String,String>valueMap = new LinkedHashMap<String,String>();
		valueMap.put(name,name);
		setLabels(valueMap);
	}
	public HtmlRadioField(String name,boolean required){
		super(name,required);
		radioMap = new LinkedHashMap<String,String>();
		radioMap.put(name,name);	
		setLabels(radioMap);
	}
	/**
	 * 
	 * @param name
	 * @param required 
	 * @param valueMap names and values for Radio box 
	 */
	public HtmlRadioField(String name,boolean required,LinkedHashMap<String,String>valueMap){
		super(name,required);
		setLabels(valueMap);
	}
	public HtmlRadioField(String name,boolean required,LinkedHashMap<String,String>valueMap,String htmlAttributes){
		super(name,required);
		setLabels(valueMap);
		this.addAttributes(htmlAttributes);
	}

	public void setLabels(LinkedHashMap<String, String> map){
		
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
			htmlBuffer.append("/>");
			if(this.isFieldValid){
				 
			}else{
				htmlBuffer.append("<label class='help-block has-error' for='"+this.getName()+"'> "+this.getErrorMessage()+"</label>");
			}
 
		return htmlBuffer.toString();
	}
	
	@Override
	public boolean validate(String value){ //maybe check for odd encoding?
		return true;
		
	}
	@Override
	public void cleanField() {
		if(this.getValue()!=null){
			this.setValue (this.getValue().trim() );
		}
		
	}

	@Override
	public String getJQueryFieldScript() {
		return new String();
	}
	@Override
	public String getJQueryFieldValidate() {
		StringBuffer json=new StringBuffer("");
		if(this.isRequired() ){
			json.append(this.getName()+":{\n");
			json.append("    minlength: 2,\n");
			json.append("    required: true,\n");
			json.append("},\n");
		}
		return json.toString();
	}

}
