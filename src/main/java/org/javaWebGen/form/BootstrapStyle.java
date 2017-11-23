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

public class BootstrapStyle implements StyleAware,Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7449103560416106950L;
	public final String FORM="form-horizontal";
	public final String FORM_FIELD="form-control";
	public final String DEFAULT_BUTTON="btn btn-default  btn-large";
	public final String PRIMARY_BUTTON="btn btn-primary  btn-large";
	public final String SUBMIT_BUTTON="btn btn-primary  btn-large";
	public final String WARN_BUTTON="btn btn-warning  btn-large";
	public final String FORM_GROUP="form-group";
	public final String FORM_GROUP_ERROR="form-group has-error";
	public final String FORM_ERROR="has-error";
	public final String FIELD_ERROR="help-block has-error";
	public final String CONTROL_LABEL="control-label";
	public final String COL_SM_10="col-sm-10";
	public final String COL_SM_2="col-sm-2";
	public final String STRIPED_TABLE="table table-striped table-bordered table-condensed";
	public final String ROW = "row";
	@Override
	public String getForm(){
		return FORM;
	}
	@Override
	public String getFormField() {
		return FORM_FIELD;
	}
	@Override
	public String getDefaultButton() {
		return DEFAULT_BUTTON;
	}
	@Override
	public String getPrimaryButton() {
		return PRIMARY_BUTTON;
	}
	@Override
	public String getSubmitButton() {
		return SUBMIT_BUTTON;
	}
	@Override
	public String getWarnButton() {
		return WARN_BUTTON;
	}
	@Override
	public String getFormGroup() {
		return FORM_GROUP;
	}
	@Override
	public String getFormError() {
		return FORM_ERROR;
	}
	@Override
	public String getColSm10() {
		return COL_SM_10;
	}
	@Override
	public String getColSm2() {
		return COL_SM_2;
	}
	@Override
	public String getStripedTable() {
		return STRIPED_TABLE;
	}
	@Override
	public String getRow() {
		return ROW;
	}
	@Override
	public String getControlLabel() {
		return CONTROL_LABEL;
	}
	@Override
	public String getFieldError() {
		return FIELD_ERROR;
	}
	
}
