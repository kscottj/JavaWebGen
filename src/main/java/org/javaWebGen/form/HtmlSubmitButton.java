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

public class HtmlSubmitButton  extends HtmlButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7095595251983184774L;
	public final static  String DEFAULT_LABEL="Submit";
	public HtmlSubmitButton(String name){
		super(name);
		this.setLabel(DEFAULT_LABEL);
		
	}
	public HtmlSubmitButton(String name,boolean required){
		super(name,required);
		this.setLabel(DEFAULT_LABEL);
		
	}
	
	public HtmlSubmitButton(String name,boolean required,String label){
		super(name,required,label);
	}
	public HtmlSubmitButton(String name,boolean required,String label,String attributes){
		super(name,required,label,attributes);
	}
	
	@Override
	public String getField() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<button type='submit' class='"+style.getPrimaryButton()+"' ");
		buffer.append(this.extraAttributes);
		buffer.append("  "); 
		buffer.append(" id='"+this.getName()+"' >");
		buffer.append(this.getProps(this.getLabel(), "Submit"));
		buffer.append("</button>");
		return buffer.toString();
	}

}
