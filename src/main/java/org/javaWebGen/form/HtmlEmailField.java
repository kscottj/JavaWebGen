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
		boolean isFieldValid= EmailValidator.getInstance().isValid(value);
		if (!isFieldValid){
			this.setErrorMessage(this.getProps(INVALID_MSG_KEY, INVALID_MESSAGE) );  
		}
		return isFieldValid;
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
