package org.javaWebGen.form;

import java.util.Date;
import org.apache.commons.validator.routines.DateValidator;
import org.javaWebGen.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTML date field.  Uses JQuery validate for client side check.  Will generate a 
 * JQuery datepicker if javascript is available
 * @author home
 *
 */
public class HtmlTimeField extends HtmlField implements DateFieldAware{
 	/**
	 * 
	 */
	private static final long serialVersionUID = -1029740496998524968L;
	//private StringBuffer htmlBuffer =new StringBuffer();
	private static final Logger log=LoggerFactory.getLogger(HtmlTimeField.class);//begin exec
	private static final String INPUT_TYPE="type='text'";
	public static final String INVALID_MESSAGE="Invalid date use the "+StringUtil.TIME_PATTERN+" format";
	public static final String INVALID_MSG_KEY="form.error.time";
	//private static final String INVALID_DATE_KEY="type='error.date.field'";
	private java.util.Date date=null;
	 
 
	public HtmlTimeField(String fieldName){
		super(fieldName);
		
	}
	public HtmlTimeField(String fieldName,boolean required){
		super(fieldName,required);
		
	}
	
	public HtmlTimeField(String fieldName,boolean required,String label){
		super(fieldName,required,label);
	}
	public HtmlTimeField(String fieldName,boolean required,String label,String attributes){
		super(fieldName,required,label,attributes);
	}
	public String getValue(){
		String value=super.getValue();
		if(value==null||value.equals("null")){
			return "";
		}else{
			return value;
		}
	}
 
	@Override
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
			htmlBuffer.append(" />\n");
			if(this.isFieldValid){
				 
			}else{
				htmlBuffer.append("<label class='help-block has-error'> "+this.getErrorMessage()+"</label>");
				 
			}
 
		return htmlBuffer.toString();
	}
	
	@Override
	public void setDate(java.util.Date date){
		this.date=date;
		//log.debug(this+"before date format="+date);
		 
		this.setValue(StringUtil.formatTime(date));
		log.debug(this+"value="+this.getValue() );
	}
	@Override
	public Date getDate(){
		/*try {
			return dateFormat.parse(this.getValue() );
		} catch (ParseException e) {
			return null;
		}*/
		return this.date;
	}
	
	@Override
	public boolean validate(String value){
	 
		boolean val=DateValidator.getInstance().isValid(value);
		if(!val){
			this.setErrorMessage(this.getProps(INVALID_MSG_KEY, INVALID_MESSAGE) );  
		}
		return val;
	}

	@Override
	public void cleanField() {
		if(this.getValue()!=null){
			String dateValue=this.getValue().trim() ;
			setValue(dateValue); 
		}	
	}

	@Override
	public String getJQueryFieldScript() {
		if(this.isViewOnly()){
			return "<!--read only datepicker-->\n";
		}else{
			return "$('#"+this.getName()+"').timepicker('showWidget');\n";	
			
		}
		
	}
	@Override
	public String getJQueryFieldValidate() {
		StringBuffer json=new StringBuffer("");
		
		json.append(this.getName()+":{\n");
		json.append("    date: true,\n");
		if(this.isRequired()){
			json.append("    required: true,\n");
		}
		json.append("},\n");
	return json.toString();

	}

}
