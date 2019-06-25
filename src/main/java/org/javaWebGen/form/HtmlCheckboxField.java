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

public class HtmlCheckboxField extends HtmlField{
	 
	private static final long serialVersionUID = 1734562497037842429L;
	private static final String INPUT_TYPE="type='checkbox'";
	private boolean isChecked = false;
	public HtmlCheckboxField(String name){
		super(name);
		
	}
	public HtmlCheckboxField(String name,boolean required){
		super(name,required);
		
	}
	
	public HtmlCheckboxField(String name,boolean required,String label){
		super(name,required,label);
	}
	public HtmlCheckboxField(String name,boolean required,String label,String attributes){
		super(name,required,label,attributes);
	}
	
	/**
	 * 
	 */
	@Override
	public String getField(){
		StringBuffer htmlBuffer=new StringBuffer();
 
			htmlBuffer.append("<input "+this.getDefaultAttributes()+INPUT_TYPE); 
			htmlBuffer.append(" placeholder='"+this.getToolTip()+"'");
			if(this.isRequired()){
				htmlBuffer.append(""); //required is not needed
			}
			if(this.isViewOnly()){
				htmlBuffer.append(" readonly");
			}
			if(this.isChecked){
				htmlBuffer.append(" checked ");	
			}
			htmlBuffer.append("/>");
			if(this.isFieldValid){
				htmlBuffer.append("<!--"+this.getName()+" valid-->"); 
			}else{
				htmlBuffer.append("<label class='help-block has-error' for='"+this.getName()+"'> "+this.getErrorMessage()+"</label>");
			}
 
 
		return htmlBuffer.toString();
	}
	 
	@Override
	public boolean validate(String value){ 
		boolean val=super.validate(value);
//		if(this.isRequired()) {
//			if(value!=null&&value.length()>1){
//				return true;
//			}else{
//				this.setErrorMessage(MsgConst.);
//				return false;
//			}
//		}else {
//			return true;
//		}

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
			json.append("    required: true,\n");
			json.append("},\n");
		}
		return json.toString();
	}
	/**
	 * should field be checked
	 * @param checked render check mark in field IE selected or not
	 */
	public void isCheck(boolean checked){
		this.isChecked=checked;
	}

}
