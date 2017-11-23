package org.javaWebGen.form;


import java.text.ParseException;
import org.javaWebGen.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HTML date field.  Uses JQuery validate for client side check.  Will generate a 
 * JQuery datepicker if javascript is available
 * @author home
 *
 */
public class HtmlDateTimeField extends HtmlDateField implements DateFieldAware{
 	/**
	 * 
	 */
	private static final long serialVersionUID = -1029740496998524968L;
	//private StringBuffer htmlBuffer =new StringBuffer();
	private static final Logger log=LoggerFactory.getLogger(HtmlDateField.class);//begin exec

	//public static final String DATEPATTERN = "DATE_TIME_PATTERN";
	public static final String INVALID_MESSAGE="Invalid date use the "+StringUtil.DATE_TIME_PATTERN+" format";
	public static final String INVALID_MSG_KEY="form.error.datetime";
	//private static final String INVALID_DATE_KEY="type='error.date.field'";
	private java.util.Date date=null;

 
	public HtmlDateTimeField(String fieldName){
		super(fieldName);
		
	}
	public HtmlDateTimeField(String fieldName,boolean required){
		super(fieldName,required);
		
	}
	
	public HtmlDateTimeField(String fieldName,boolean required,String label){
		super(fieldName,required,label);
	}
	public HtmlDateTimeField(String fieldName,boolean required,String label,String attributes){
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
	public void setDate(java.util.Date date){
		this.date=date;
		this.setValue(StringUtil.formatDateTime(this.date) );
		log.debug(this+"value="+this.getValue() );
	}
	
	@Override
	public boolean validate(String value)  {
		log.debug("validate>"+value);
		try {
			StringUtil.convertToTime(value);
		} catch (ParseException e) {
			log.warn(value+"is invalid date"+e.getMessage());
			this.setErrorMessage(this.getProps(INVALID_MSG_KEY, INVALID_MESSAGE) );
			return false;
		}
	 
		return true;	 
	 
	}
	@Override
	public String getJQueryFieldScript() {
		if(this.isViewOnly()){
			return "<!--read only datepicker-->\n";
		}else{
			return "$('#"+this.getName()+"').datepicker();\n";	
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
