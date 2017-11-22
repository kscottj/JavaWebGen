package org.javaWebGen.form;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Does not support multiple selections 
 * @author scotkevi
 *
 */
public class HtmlSelectField extends HtmlField{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8103669342938503518L;
	private Map<String,String>valueMap=null;
	private int numOfOptions=1;
 
	 
	public HtmlSelectField(String name){
		super(name);
		setValueList( new LinkedHashMap<String,String>() );
		
		 
	}
	public HtmlSelectField(String name,boolean required){
		super(name,required);
		setValueList( new LinkedHashMap<String,String>() );
		
	}
	
	public HtmlSelectField(String name,boolean required,String label){
		super(name,required,label);
		setValueList( new LinkedHashMap<String,String>() );
		
	}
	public HtmlSelectField(String name,boolean required,String label,String attributes){
		super(name,required,label,attributes);
		setValueList( new LinkedHashMap<String,String>() );
		
	}
	/**
	 * Set the value list for the drop down used to populate the <option> fields.  Use a LinkedHashMap to
	 * preserve the order they the options are displayed in
	 * @param map of values
	 */
	public void setValueList(Map<String,String> map){
		this.valueMap=map;
	}
	/**
	 * 
	 * @param number
	 */
	public void setNumberOfOptionstoDisplay(int number){
		this.numOfOptions=number;
	}

	/**
	 * generate select field
	 */
	public String getField(){
		StringBuffer htmlBuffer=new StringBuffer();
	
		if(this.isViewOnly() ){
			htmlBuffer.append(" "+this.getValue()+" \n");
			htmlBuffer.append("<input type='hidden' id='"+this.getDefaultAttributes()+"/>\n");
		}else{

			htmlBuffer.append("\n<select size='"+numOfOptions+"' "+this.getDefaultAttributes());
			htmlBuffer.append(" placeholder='"+this.getToolTip()+"'");
			if(this.isRequired()){
				htmlBuffer.append(" required");
			}
			if(this.isViewOnly()){
				htmlBuffer.append(" readonly");
			}
			htmlBuffer.append(">\n");
			Iterator<String> i=valueMap.keySet().iterator();
			while( i.hasNext() ){
				String key=i.next();
				String value=valueMap.get(key);
				htmlBuffer.append("<option value=\""+value+"\" ");
				if(this.getValue()!=null && this.getValue().equals(value ) ) {
					htmlBuffer.append("selected");
				}
				htmlBuffer.append(">"+key+"</option>\n");
 
			}
			htmlBuffer.append("</select>\n");
			if(this.isFieldValid){
				 
			}else{
				htmlBuffer.append("<label class='help-block has-error' for='"+this.getName()+"'> "+this.getErrorMessage()+"</label>");
			} 
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
