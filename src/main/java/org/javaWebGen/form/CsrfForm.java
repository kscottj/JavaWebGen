/*
 * =================================================================== *
 * Copyright (c) 2017 Kevin Scott All rights  reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 * if any, must include the following acknowledgment:
 * "This product includes software developed by "Kevin Scott"
 * Alternately, this acknowledgment may appear in the software itself,
 * if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "Kevin Scott must not be used to endorse or promote products
 * derived from this software without prior written permission. For
 * written permission, please contact kevscott_tx@yahoo.com
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL KEVIN SCOTT BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */
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
