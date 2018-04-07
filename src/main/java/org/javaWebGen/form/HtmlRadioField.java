package org.javaWebGen.form;

import java.util.LinkedHashMap;

public class HtmlRadioField extends HtmlField{
	 
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 6566739791094556886L;

	private static final String INPUT_TYPE="type='radio'";

	private LinkedHashMap<String,String> radioMap=null;
	public HtmlRadioField(String name){
		super(name);
		LinkedHashMap<String,String>valueMap = new LinkedHashMap<String,String>();
		valueMap.put(name,name);
		setLabels(valueMap);
	}
	public HtmlRadioField(String name,boolean required){
		super(name,required);
		radioMap = new LinkedHashMap<String,String>();
		radioMap.put(name,name);	
		setLabels(radioMap);
	}
	/**
	 * 
	 * @param name
	 * @param required 
	 * @param valueMap names and values for Radio box 
	 */
	public HtmlRadioField(String name,boolean required,LinkedHashMap<String,String>valueMap){
		super(name,required);
		setLabels(valueMap);
	}
	public HtmlRadioField(String name,boolean required,LinkedHashMap<String,String>valueMap,String htmlAttributes){
		super(name,required);
		setLabels(valueMap);
		this.addAttributes(htmlAttributes);
	}

	public void setLabels(LinkedHashMap<String, String> map){
		
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
			htmlBuffer.append("/>");
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
