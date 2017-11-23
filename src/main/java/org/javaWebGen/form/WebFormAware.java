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

import org.javaWebGen.data.FormBeanAware;

/**
 * Represents a HTML Field.  IE a <INPUT> field
 *
 */
public interface WebFormAware {
	/**
	 * Custom form Validation
	 * @return true if errors found
	 */
	public boolean validate();
	/**
	 * clean the data values normal spaces strip bad chars etc.. Called before validate method
	 */
	public void clean();
	/**
	 * get current form name
	 * @return name of webform to be used in form name 
	 */
	public String getWebFormName();
	/**
	 * Get Current HTML Id for element.  Useful for getting element out of DOM for scripting.
	 * @return DOM id
	 */
	public String getHtmlId();
	 
	/**
	 * return databean that is bound to the form
	 * @return data bound bean
	 */
	public FormBeanAware getData();
	/**
	 * Does form validate.  This is you customer validation not included in
	 * by default by the fields
	 * @return true if form data is valid
	 */
	public boolean isValid();
	/**
	 * set form action type IE <form action='' method ='post'>
	 * @param action url
	 */
	public void setAction(String action);
	/**
	 * Get form as HTML without any formating such a <DIV> or <TABLE> tag.
	 * @return html including <FORM> tag and any JQuery javascript
	 */
	public String getForm();
	/**
	 * renders form with <div> tags.  Default to twitter bootstrap CSS classes.  does not 
	 * Include the <form> tag.  So you will need to provide the <form> tag
	 * @return HTML fragment
	 */
	public String getDivTag();
	/**
	 * renders form as a HTML table.  Does not include the <form> tag
	 * @return HTML form  fields
	 */
	public String getTable();
	/**
	 * reset errors and data that is bound to the form
	 */
	public void reset();
	
	/**
	 * set all fields registered in form to view mode
	 * @param isViewOnly true for view only mode false for editable
	 */
	public void setIsViewOnly(boolean isViewOnly);
	
	/**
	 * set the style for form and all registered fields
	 * 
	 * @param style
	 */
	public void setStyle(StyleAware style);
	


}
