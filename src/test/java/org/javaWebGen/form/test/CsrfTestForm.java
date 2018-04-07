package org.javaWebGen.form.test;

import javax.servlet.http.HttpServletRequest;

import org.javaWebGen.form.CsrfForm;
import org.javaWebGen.form.HtmlField;
import org.javaWebGen.form.HtmlTextField;

public class CsrfTestForm extends CsrfForm{
	
	private HtmlField textField= new HtmlTextField("textField",true);
	

	public CsrfTestForm(HttpServletRequest req) {
		super(req);
		this.addField(textField);
		
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1674676146644979243L;

}
