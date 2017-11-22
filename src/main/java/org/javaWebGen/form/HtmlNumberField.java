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
		log.info(this+".validate("+value+")");
		try{
			Long.parseLong(this.getValue() );
		}catch(NumberFormatException e){ //not a number
			this.setErrorMessage(this.getProps(INVALID_NUMBER_KEY, INVALID_NUMBER_MESSAGE) ); 
			 
			return false;
		}
		return true;
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