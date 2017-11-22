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