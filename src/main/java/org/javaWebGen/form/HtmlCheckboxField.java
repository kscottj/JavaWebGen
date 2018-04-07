package org.javaWebGen.form;

public class HtmlCheckboxField extends HtmlField{
	 
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1734562497037842429L;
	private static final String INPUT_TYPE="type='checkbox'";
	private boolean isChecked = false;
	public HtmlCheckboxField(String name){
		super(name);
		
	}
	public HtmlCheckboxField(String name,boolean required){
		super(name,required);
		
	}
	
	public HtmlCheckboxField(String name,boolean required,String label){
		super(name,required,label);
	}
	public HtmlCheckboxField(String name,boolean required,String label,String attributes){
		super(name,required,label,attributes);
	}
	
	/**
	 * 
	 */
	@Override
	public String getField(){
		StringBuffer htmlBuffer=new StringBuffer();
 
			htmlBuffer.append("<input "+this.getDefaultAttributes()+INPUT_TYPE); 
			htmlBuffer.append(" placeholder='"+this.getToolTip()+"'");
			if(this.isRequired()){
				htmlBuffer.append(""); //required is not needed
			}
			if(this.isViewOnly()){
				htmlBuffer.append(" readonly");
			}
			if(this.isChecked){
				htmlBuffer.append(" checked ");	
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
		if(value!=null){
			return true;
		}else{
			return false;
		}
		
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
			json.append("    required: true,\n");
			json.append("},\n");
		}
		return json.toString();
	}
	/**
	 * should field be checked
	 * @param checked render check mark in field IE selected or not
	 */
	public void isCheck(boolean checked){
		this.isChecked=checked;
	}

}
