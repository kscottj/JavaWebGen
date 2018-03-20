package org.javaWebGen.form;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.validator.routines.BigDecimalValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 

public class HtmlDecimalField extends HtmlNumberField{
	@SuppressWarnings("unused")
	private static final Logger log=LoggerFactory.getLogger(HtmlCurrencyField.class); 
	public static final NumberFormat DECIMAL_FORMAT= DecimalFormat.getNumberInstance(); 
	public static final String INVALID_MESSAGE="Enter a valid number";
	public static final String INVALID_MSG_KEY="form.errors.decimal";
	 
	public HtmlDecimalField(String fieldName){
		super(fieldName);
		
	}
	public HtmlDecimalField(String fieldName,boolean required){
		super(fieldName,required);
		
	}
	
	public HtmlDecimalField(String fieldName,boolean required,String label){
		super(fieldName,required,label);
	}
	public HtmlDecimalField(String fieldName,boolean required,String label,String attributes){
		super(fieldName,required,label,attributes);
	}
	

	@Override
	public boolean validate(String value){
		boolean val=BigDecimalValidator.getInstance().isValid(value);
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

}
