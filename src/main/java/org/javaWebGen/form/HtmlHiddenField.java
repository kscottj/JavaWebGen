package org.javaWebGen.form;

public class HtmlHiddenField extends HtmlField{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8951396673908445981L;
	private static final String INPUT_TYPE="type='hidden'";
	
	
	public HtmlHiddenField(String name){
		super(name);
		
	}
	public HtmlHiddenField(String name,boolean required){
		super(name,required);
		
	}
	
	public HtmlHiddenField(String name,boolean required,String label){
		super(name,required,label);
	}
	public HtmlHiddenField(String name,boolean required,String label,String attributes){
		super(name,required,label,attributes);
	}


	@Override
	public String getField(){
		StringBuffer htmlBuffer=new StringBuffer();
		if(this.getValue()!=null){
			htmlBuffer.append("\n<input "+INPUT_TYPE+" name='"+this.getName()+"'"+this.getValueAttribute()+"/>\n");
		}else{
			htmlBuffer.append("\n<!-- "+this.getName()+" empty -->\n");
		}
		return htmlBuffer.toString();
	}
	@Override
	public String getDivTag(){
		return getField();
		 
	}

	@Override
	public String getTable(){
		return this.getField();
		 
	}
	@Override
	public boolean validate(String value) {
		return true;
	}
	@Override
	public void cleanField() {
		if(this.getValue()!=null){
			this.setValue (this.getValue().trim() );
		}
	}
	@Override
	public String getErrorMessage() {

		return EMPTY;
	}
	@Override
	public String getJQueryFieldScript() {
		
		return EMPTY;
	}
	@Override
	public String getJQueryFieldValidate() {
		
		return EMPTY;
	}
}
