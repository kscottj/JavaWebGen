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

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Does not support multiple selections 
 * @author scotkevi
 *
 */
public class HtmlSelectField extends HtmlField{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8103669342938503518L;
	private Map<String,String>valueMap=null;
	private int numOfOptions=1;
 
	 
	public HtmlSelectField(String name){
		super(name);
		setValueList( new LinkedHashMap<String,String>() );
		
		 
	}
	public HtmlSelectField(String name,boolean required){
		super(name,required);
		setValueList( new LinkedHashMap<String,String>() );
		
	}
	
	public HtmlSelectField(String name,boolean required,String label){
		super(name,required,label);
		setValueList( new LinkedHashMap<String,String>() );
		
	}
	public HtmlSelectField(String name,boolean required,String label,String attributes){
		super(name,required,label,attributes);
		setValueList( new LinkedHashMap<String,String>() );
		
	}
	/**
	 * Set the value list for the drop down used to populate the <option> fields.  Use a LinkedHashMap to
	 * preserve the order they the options are displayed in
	 * @param map of values
	 */
	public void setValueList(Map<String,String> map){
		this.valueMap=map;
	}
	/**
	 * 
	 * @param number
	 */
	public void setNumberOfOptionstoDisplay(int number){
		this.numOfOptions=number;
	}

	/**
	 * generate select field
	 */
	public String getField(){
		StringBuffer htmlBuffer=new StringBuffer();
	
		if(this.isViewOnly() ){
			htmlBuffer.append(" "+this.getValue()+" \n");
			htmlBuffer.append("<input type='hidden' id='"+this.getDefaultAttributes()+"/>\n");
		}else{

			htmlBuffer.append("\n<select size='"+numOfOptions+"' "+this.getDefaultAttributes());
			htmlBuffer.append(" placeholder='"+this.getToolTip()+"'");
			if(this.isRequired()){
				htmlBuffer.append(" required");
			}
			if(this.isViewOnly()){
				htmlBuffer.append(" readonly");
			}
			htmlBuffer.append(">\n");
			Iterator<String> i=valueMap.keySet().iterator();
			while( i.hasNext() ){
				String key=i.next();
				String value=valueMap.get(key);
				htmlBuffer.append("<option value=\""+value+"\" ");
				if(this.getValue()!=null && this.getValue().equals(value ) ) {
					htmlBuffer.append("selected");
				}
				htmlBuffer.append(">"+key+"</option>\n");
 
			}
			htmlBuffer.append("</select>\n");
			if(this.isFieldValid){
				 
			}else{
				htmlBuffer.append("<label class='help-block has-error' for='"+this.getName()+"'> "+this.getErrorMessage()+"</label>");
			} 
		}
		return htmlBuffer.toString();
	}
	@Override
	public boolean validate(String value){ //maybe check for odd encoding?
		boolean val=super.validate(value);
		this.isFieldValid=val;
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
