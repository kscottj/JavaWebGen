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

/**
 * Style sheet definitions.  Use this interface to define a custom style
 * @see BootstrapStyle for exmple implementation for bootstrap CSS
 *
 */
public interface StyleAware {
	public String FORM=null;
	public String FORM_FIELD=null;
	public String DEFAULT_BUTTON=null;
	public String PRIMARY_BUTTON=null;;
	public String SUBMIT_BUTTON=null;
	public String WARN_BUTTON=null; 
	public String FORM_GROUP=null; 
	public String FORM_GROUP_ERROR=null; 
	public String FORM_ERROR=null; 
	public String FIELD_ERROR=null; 
	public String CONTROL_LABEL=null; 
	public String COL_SM_10=null; 
	public String COL_SM_2=null; 
	public String STRIPED_TABLE=null; 
	public String ROW = null;
	public String getForm();
	public String getFormField();
	public String getDefaultButton();
	public String getPrimaryButton();
	public String getSubmitButton();
	public String getWarnButton();
	public String getFormGroup();
	public String getControlLabel();
	public String getFormError();
	public String getColSm10();
	public String getColSm2();
	public String getStripedTable();
	public String getRow();
	public String getFieldError();
	
}