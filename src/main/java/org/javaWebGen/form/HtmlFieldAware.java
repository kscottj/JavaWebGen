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
