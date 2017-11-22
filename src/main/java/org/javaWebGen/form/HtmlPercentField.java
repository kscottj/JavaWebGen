package org.javaWebGen.form;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import org.apache.commons.validator.routines.PercentValidator;


public class HtmlPercentField extends HtmlNumberField{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4403234346785914723L;
	public static final NumberFormat DECIMAL_FORMAT= DecimalFormat.getNumberInstance(); 
	public static final String INVALID_MESSAGE="Enter a valid Percent";
	public static final String INVALID_MSG_KEY="form.error.percent";
	
	public static final String DECIMAL_INPUT_ATTR=" min='0' max='100' step='0.01'";
	 
	public HtmlPercentField(String fieldName){
		super(fieldName);
		this.addAttributes(DECIMAL_INPUT_ATTR);
		
	}
	public HtmlPercentField(String fieldName,boolean required){
		super(fieldName,required);
		this.addAttributes(DECIMAL_INPUT_ATTR);
		
	}
	
	public HtmlPercentField(String fieldName,boolean required,String label){
		super(fieldName,required,label);
		this.addAttributes(DECIMAL_INPUT_ATTR);
	}
	public HtmlPercentField(String fieldName,boolean required,String label,String attributes){
		super(fieldName,required,label,attributes);
		this.addAttributes(DECIMAL_INPUT_ATTR);
	}
	
	@Override
	public boolean validate(String value){
		boolean val=PercentValidator.getInstance().isValid(value);
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
