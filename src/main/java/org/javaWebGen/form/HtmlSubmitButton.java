package org.javaWebGen.form;

public class HtmlSubmitButton  extends HtmlButton {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7095595251983184774L;
	public final static  String DEFAULT_LABEL="Submit";
	public HtmlSubmitButton(String name){
		super(name);
		this.setLabel(DEFAULT_LABEL);
		
	}
	public HtmlSubmitButton(String name,boolean required){
		super(name,required);
		this.setLabel(DEFAULT_LABEL);
		
	}
	
	public HtmlSubmitButton(String name,boolean required,String label){
		super(name,required,label);
	}
	public HtmlSubmitButton(String name,boolean required,String label,String attributes){
		super(name,required,label,attributes);
	}
	
	@Override
	public String getField() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<button type='submit' class='"+style.getPrimaryButton()+"' ");
		buffer.append(this.extraAttributes);
		buffer.append("  "); 
		buffer.append(" id='"+this.getName()+"' >");
		buffer.append(this.getProps(this.getLabel(), "Submit"));
		buffer.append("</button>");
		return buffer.toString();
	}

}
