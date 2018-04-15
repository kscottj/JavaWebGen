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

import java.io.Serializable;

public interface HtmlFieldAware extends  Serializable{
	/**
	 * Custom field validation
	 * @param value
	 * @return valid
	 */
	public boolean validate(String value);
	/**
	 * The real value of the field.  Generally contained in value="" attribute in an HTML form
	 * @return real text value of field
	 */
	
	@Override
	public boolean equals(Object obj);
	
	/**
	 * renders field in HTML without any formating. Just a basic <INPUT> tag. 
	 * From a JSP call like this ${field}
	 * @return html field
	 */
	@Override
	public String toString();
	/**
	 * Get field with formatting <DIV> tags.  Includes Labels.
	 * from a JSP call like this ${field.divTag}
	 * @return html field
	 */
	public String getDivTag();
	/**
	 * Get just the HTML INPUT field.  Does not include a label.  Just a basic form field that will 
	 * include CSS classes references and placeholder IE Tool tip
	 * @return HTML input field
	 */
	public String getField();
	/**
	 * Get field with formatting using an HTML TABLE.  Includes Labels <TR> and <TD> tags.
	 * Does not include the <TABLE> or</TABLE> Tag.
	 * From a JSP call like this ${field.table}
	 * @return html field
	 */
	public String getTable();
	/**
	 * Returns any JQUERY scripts that this field needs to run.  An example is the date picker
	 * @return JQery JavaScript
	 */
	public String getJQueryFieldScript();
	/**
	 * Returns any JQUERY scripts that validates the field
	 * @return JQery JavaScript
	 */
	public String getJQueryFieldValidate();
	/**
	 * clean the data before running the server side validation
	 */
	public void cleanField();
	/**
	 * get error from servers side validation
	 */
	public String getErrorMessage();
	
	/**
	 * set field to view only mode uses a hidden tag to pass values.
	 * Use this when a user does not have authority to update this field
	 * 
	 * @param isViewOnly
	 */
	public void setViewOnly(boolean isViewOnly);
	
}
