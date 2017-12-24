package org.javaWebGen.form;

import javax.servlet.http.HttpServletRequest;

public class SecureForm extends CsrfForm{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4646530013553679331L;

	public SecureForm(HttpServletRequest req) {
		super(req);
		
	}

}
