package org.javaWebGen.form;

import javax.servlet.http.HttpServletRequest;

import org.javaWebGen.data.FormBeanAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Construct object that can generate HTML based on data fields Automatically.
 * Adds @see CsrfHtmlFild field to form to protect against request forging.
 * Requires @see WebConst#CSRF value be available in the session otherwise
 * validation will fail.  The session values will be setup by the @see Dispatcher automatically.
 * 
 * @author kevin
 *
 */
public class CsrfForm extends HtmlForm {

	private static final long serialVersionUID = -2643638282079319182L;
	private CsrfHtmlField csrf = null;
	private static final Logger log = LoggerFactory.getLogger(CsrfForm.class);

	/**
	 * 
	 * @param bean populates with form bean by match getters
	 * @param req uses request to populate the form by matching field names
	 */
	public CsrfForm(FormBeanAware bean, HttpServletRequest req) {
		super(bean, req);
		csrf = new CsrfHtmlField(req);

		addField(csrf);
		log.debug("add csrf"+csrf+bean.toJSON() );
	}
	/**
	 * 
	 * @param req uses request to populate the form hidden CSRF field from session
	 */
	public CsrfForm( HttpServletRequest req) {
		super(req);
		
		csrf = new CsrfHtmlField(req);

		addField(csrf);
		log.debug("add csrf"+csrf);
	}
 	/**
	 * return CSRF field called by view
	 * 
	 * @return CSRF field to JSP
	 */
	public CsrfHtmlField getCsrf() {
		return this.csrf;
	}

}
