package org.javaWebGen.form.test;

import javax.servlet.http.HttpServletRequest;

import org.javaWebGen.form.CsrfForm;
import org.javaWebGen.form.HtmlField;
import org.javaWebGen.form.HtmlNumberField;
import org.javaWebGen.form.HtmlTextField;

public class CsrfTestForm extends CsrfForm{
	
	private HtmlField textField= new HtmlTextField("textField",true);
	private HtmlField numberField= new HtmlNumberField("numberField",false);

	public CsrfTestForm(HttpServletRequest req) {
		super(req);
		this.addField(textField);
		this.addField(numberField);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1674676146644979243L;

}
