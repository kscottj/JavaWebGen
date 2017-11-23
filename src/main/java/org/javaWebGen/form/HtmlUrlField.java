package org.javaWebGen.form;

import org.apache.commons.validator.routines.UrlValidator
;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlUrlField extends HtmlField{
	/**
	 * 
	 */
	private static final long serialVersionUID = -149370241883400917L;
	@SuppressWarnings("unused")
	private static final Logger log=LoggerFactory.getLogger(HtmlUrlField.class);
	private static final String INPUT_TYPE = "text";
	public static final String INVALID_MSG_KEY = "form.error.url";
	public static final String INVALID_MESSAGE = "Invalid Web Address";
	public HtmlUrlField(String name){
		super(name);
		
	}
	public HtmlUrlField(String name,boolean required){
		super(name,required);
		
	}
	
	public HtmlUrlField(String name,boolean required,String label){
		super(name,required,label);
	}
	public HtmlUrlField(String name,boolean required,String label,String attributes){
		super(name,required,label,attributes);
	}
	
	@Override
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
		htmlBuffer.append(" />");
		if(this.isFieldValid){
			 
		}else{
			htmlBuffer.append("<label class='help-block has-error' for='"+this.getName()+"'> "+this.getErrorMessage()+"</label>");
		}
		return htmlBuffer.toString();
	}
	
	@Override
	public boolean validate(String value){ 
		boolean val= UrlValidator.getInstance().isValid(value);
		
		if(!val){
			
			this.setErrorMessage(this.getProps(INVALID_MSG_KEY, INVALID_MESSAGE) );  
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
		return new String();
	}
	@Override
	public String getJQueryFieldValidate() {
		StringBuffer json=new StringBuffer("");
		json.append("    url:true,\n");
		if(this.isRequired() ){
			json.append(this.getName()+":{\n");
			json.append("    minlength: 2,\n");
			json.append("    required: true,\n");
			json.append("},\n");
		}
		return json.toString();
	}
}
