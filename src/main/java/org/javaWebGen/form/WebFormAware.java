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
