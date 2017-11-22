package org.javaWebGen.form;

public class HtmlButton extends HtmlField{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3540529420783996710L;
	public final static  String DEFAULT_LABEL="Button";
	public HtmlButton(String name){
		super(name);
		this.setLabel(DEFAULT_LABEL);
		
	}
	public HtmlButton(String name,boolean required){
		super(name,required);
		this.setLabel(DEFAULT_LABEL);
		
	}
	
	public HtmlButton(String name,boolean required,String label){
		super(name,required,label);
	}
	public HtmlButton(String name,boolean required,String label,String attributes){
		super(name,required,label,attributes);
	}
	@Override
	public boolean validate(String value) {
		return true;
	}
	@Override
	public String getDivTag() {
		String html="<div class='"+this.style.getRow()+"'>"+this.getField()+"</div>\n";
		return html;
	}
	@Override
	public String getField() {
		
		StringBuffer buffer = new StringBuffer();

			buffer.append("<button class='"+this.style.getPrimaryButton()+ "' ");
			buffer.append(this.extraAttributes);
			buffer.append(" value='"+this.getLabel()+"' "); 
			buffer.append(" name='"+this.getName()+"' ");
			buffer.append(" id='"+this.getName()+"' />");
			return buffer.toString();
	}
	
	@Override
	public String getTable() {
		String html="<tr><td colspan='2'>"+this.getField()+"</td></tr><\n";
		return html;
	}
	/**
	 *  does not do anything for a button
	 */
	@Override
	public String getJQueryFieldScript() {
		return "";
	}
	/**
	 *  does not do anything for a button
	 */
	@Override
	public String getJQueryFieldValidate() {// TODO Auto-generated method stub
		return "";
	}
	/**
	 * does not do anything for a button
	 */
	@Override
	public void cleanField() {
	
	}
}
