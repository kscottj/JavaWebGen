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
 */package org.javaWebGen.form;

public class HtmlButton extends HtmlField{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3540529420783996710L;
	public final static  String DEFAULT_LABEL="Button";
	public HtmlButton(String name){
		super(name);
		this.setLabel(DEFAULT_LABEL);
		
	}
	public HtmlButton(String name,boolean required){
		super(name,required);
		this.setLabel(DEFAULT_LABEL);
		
	}
	
	public HtmlButton(String name,boolean required,String label){
		super(name,required,label);
	}
	public HtmlButton(String name,boolean required,String label,String attributes){
		super(name,required,label,attributes);
	}
	@Override
	public boolean validate(String value) {
		return true;
	}
	@Override
	public String getDivTag() {
		String html="<div class='"+this.style.getRow()+"'>"+this.getField()+"</div>\n";
		return html;
	}
	@Override
	public String getField() {
		
		StringBuffer buffer = new StringBuffer();

			buffer.append("<button class='"+this.style.getPrimaryButton()+ "' ");
			buffer.append(this.extraAttributes);
			buffer.append(" value='"+this.getLabel()+"' "); 
			buffer.append(" name='"+this.getName()+"' ");
			buffer.append(" id='"+this.getName()+"' />");
			return buffer.toString();
	}
	
	@Override
	public String getTable() {
		String html="<tr><td colspan='2'>"+this.getField()+"</td></tr><\n";
		return html;
	}
	/**
	 *  does not do anything for a button
	 */
	@Override
	public String getJQueryFieldScript() {
		return "";
	}
	/**
	 *  does not do anything for a button
	 */
	@Override
	public String getJQueryFieldValidate() {// TODO Auto-generated method stub
		return "";
	}
	/**
	 * does not do anything for a button
	 */
	@Override
	public void cleanField() {
	
	}
}
