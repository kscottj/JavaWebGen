package org.javaWebGen.form;

public class TestForm extends HtmlForm{
	HtmlField email = new HtmlEmailField("email",true);
	HtmlField text = new HtmlTextField("text",true);
	HtmlField textarea = new HtmlTextAreaField("textarea",true);
	HtmlField hidden = new HtmlHiddenField("hidden",true);
	HtmlField percent = new HtmlPercentField("percent",true);
	HtmlField currency = new HtmlCurrencyField("currency",true);
	
	HtmlField number = new HtmlNumberField("number",true);
	HtmlField date = new HtmlDateField("date",true);
	HtmlField time = new HtmlTimeField("time",true);
	HtmlField datetime = new HtmlDateTimeField("datetime",true);

	/**
	 * 
	 */
	private static final long serialVersionUID = -8684971002907058267L; 
	public TestForm() {
	 
		this.addField(email);	
		this.addField(text);
		this.addField(textarea);
		this.addField(hidden);
		this.addField(number);
		this.addField(percent);
		this.addField(currency);
		
		this.addField(date);
		this.addField(time);
		this.addField(datetime); 
		
		
	}
	public HtmlField getCurrency() {
		return currency;
	}
	public HtmlField getEmail() {
		return email;
	}
	public HtmlField getText() {
		return text;
	}
	public HtmlField getDate() {
		return date;
	}
	public HtmlField getTime() {
		return time;
	}
	public HtmlField getDatetime() {
		return datetime;
	}
	public HtmlField getPercent() {
		return percent;
	}
	public HtmlField getNumber() {
		return number;
	}
	public HtmlField getHidden() {
		return hidden;
	}
	public HtmlField getTextArea() {
		 
		return textarea;
	}

}
