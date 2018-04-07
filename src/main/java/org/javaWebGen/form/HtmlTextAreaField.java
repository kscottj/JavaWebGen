package org.javaWebGen.form;
/**
 * represents a HTML <TEXTAREA TAG>
 * Will HTML Escape any values 
 * @author scotkevi
 *
 */
public class HtmlTextAreaField extends HtmlTextField{
	/**
	 * 
	 */
	private static final long serialVersionUID = -695985332457264506L;
	public HtmlTextAreaField(String name){
		super(name);
		
	}
	public HtmlTextAreaField(String name,boolean required){
		super(name,required);
		
	}
	
	public HtmlTextAreaField(String name,boolean required,String label){
		super(name,required,label);
	}
	public HtmlTextAreaField(String name,boolean required,String label,String attributes){
		super(name,required,label,attributes);
	}
	@Override
	public String getField(){
		StringBuffer htmlBuffer=new StringBuffer();

 
			htmlBuffer.append("<textarea "+this.getIdAttribute()+this.getNameAttribute()+this.getSizeAttribute()+this.getClassAttribute() ); 
			htmlBuffer.append(" placeholder='"+this.getToolTip()+"'");
			if(this.isRequired()){
				htmlBuffer.append(" required");
			}
			if(this.isViewOnly()){
				htmlBuffer.append(" readonly");
			}
			String valStr=this.getValue();
			if(valStr==null){
				htmlBuffer.append("></textarea>");
			}else{
				htmlBuffer.append(">"+this.getValue()+"</textarea>");
			}
			if(this.isFieldValid){
				 
			}else{
				htmlBuffer.append("<label class='help-block has-error' for='"+this.getName()+"'> "+this.getErrorMessage()+"</label>");
			}
 		return htmlBuffer.toString();
	}
}
