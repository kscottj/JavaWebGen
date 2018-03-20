package org.javaWebGen.form.test;

import org.javaWebGen.form.HtmlCheckboxField;
import org.javaWebGen.form.HtmlCurrencyField;
import org.javaWebGen.form.HtmlDateField;
import org.javaWebGen.form.HtmlDecimalField;
import org.javaWebGen.form.HtmlEmailField;
import org.javaWebGen.form.HtmlForm;
import org.javaWebGen.form.HtmlHiddenField;
import org.javaWebGen.form.HtmlNumberField;
import org.javaWebGen.form.HtmlRadioField;
import org.javaWebGen.form.HtmlSelectField;
import org.javaWebGen.form.HtmlTextAreaField;
import org.javaWebGen.form.HtmlTextField;
import org.javaWebGen.form.HtmlUrlField;

public class MockForm extends HtmlForm{

	/**
	 * Test form used for UNIT testing
	 */
	private static final long serialVersionUID = -1537946690453306416L;

	
	public HtmlDecimalField decimal= new HtmlDecimalField("decimal",false);
	public HtmlCurrencyField currency= new HtmlCurrencyField("currency",false);
	public HtmlEmailField email= new HtmlEmailField("email",false);
	public HtmlNumberField number= new HtmlNumberField("number",false);
	public HtmlTextField text= new HtmlTextField("text",false);
	public HtmlTextAreaField textArea= new HtmlTextAreaField("text",false);
	public HtmlHiddenField hidden= new HtmlHiddenField("hidden",false);
	public HtmlDateField dateField= new HtmlDateField("dateField",false);
	 
	public HtmlRadioField radio= new HtmlRadioField("radio",false);
	public HtmlCheckboxField checkbox= new HtmlCheckboxField("checkbox",false);
	public HtmlSelectField dropdown= new HtmlSelectField("dropdown",false);
	public HtmlUrlField url= new HtmlUrlField("url",false);
	
	 
	public MockForm(){

		this.addField(decimal);
		this.addField(currency);
		this.addField(email);
		this.addField(text);
		this.addField(textArea);
		this.addField(number);
		this.addField(hidden);
		this.addField(dateField);
		this.addField(radio);
		this.addField(checkbox);
		this.addField(dropdown);
		this.addField(url);
	}
 
	public HtmlTextAreaField getTextArea() {
		return textArea;
	}

	public void setTextArea(HtmlTextAreaField textArea) {
		this.textArea = textArea;
	}

	@Override
	public String getWebFormName() {
		return "mockForm";
	}

	public HtmlDecimalField getDecimal() {
		return decimal;
	}

	public void setDecimal(HtmlDecimalField decimal) {
		this.decimal = decimal;
	}

	public HtmlCurrencyField getCurrency() {
		return currency;
	}

	public void setCurrency(HtmlCurrencyField currency) {
		this.currency = currency;
	}

	public HtmlEmailField getEmail() {
		return email;
	}

	public void setEmail(HtmlEmailField email) {
		this.email = email;
	}

	public HtmlNumberField getNumber() {
		return number;
	}

	public void setNumber(HtmlNumberField number) {
		this.number = number;
	}

	public HtmlTextField getText() {
		return text;
	}

	public void setText(HtmlTextField text) {
		this.text = text;
	}

	public HtmlHiddenField getHidden() {
		return hidden;
	}

	public void setHidden(HtmlHiddenField hidden) {
		this.hidden = hidden;
	}

	public HtmlDateField getDateField() {
		return dateField;
	}

	public void setDateField(HtmlDateField dateField) {
		this.dateField = dateField;
	}

	public HtmlRadioField getRadio() {
		return radio;
	}

	public void setRadio(HtmlRadioField radio) {
		this.radio = radio;
	}

	public HtmlCheckboxField getCheckbox() {
		return checkbox;
	}

	public void setCheckbox(HtmlCheckboxField checkbox) {
		this.checkbox = checkbox;
	}

	public HtmlSelectField getDropdown() {
		return dropdown;
	}

	public void setDropdown(HtmlSelectField dropdown) {
		this.dropdown = dropdown;
	}

	public HtmlUrlField getUrl() {
		return url;
	}

	public void setUrl(HtmlUrlField url) {
		this.url = url;
	}


}
