package org.javaWebGen.form;

import org.apache.commons.text.StringEscapeUtils;

public class HtmlTextField extends HtmlField{
	 
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 5225247344251217567L;
	private static final String INPUT_TYPE="type='text'";
	public HtmlTextField(String name){
		super(name);
		
	}
	public HtmlTextField(String name,boolean required){
		super(name,required);
		
	}
	
	public HtmlTextField(String name,boolean required,String label){
		super(name,required,label);
	}
	public HtmlTextField(String name,boolean required,String label,String attributes){
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
		htmlBuffer.append("/>\n");
		if(this.isFieldValid){
			 
		}else{
			htmlBuffer.append("<label class='help-block has-error' for='"+this.getName()+"'> "+this.getErrorMessage()+"</label>");
		}
 	 
		return htmlBuffer.toString();
	}
	@Override
	public boolean validate(String value){ //maybe check for odd encoding?
		return true;
		
	}
	@Override
	public void cleanField() {
		if(this.getValue()!=null){
			this.setValue (this.getValue().trim() );
		}
		
	}
	/**
	 * HTML3 escape all text to prevent XSS 
	 * that pass input validation
	 */
	@Override
	public String getValue(){
		String str=super.getValue();
		return StringEscapeUtils.escapeHtml3(str);
		/*if(str==null||str.equals("null")){
			return "";
		}else{
			return StringEscapeUtils.escapeHtml3(str);
		}*/
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
			json.append("    minlength: 2,\n");
			json.append("    required: true,\n");
			json.append("},\n");
		}
		return json.toString();
	}

}
