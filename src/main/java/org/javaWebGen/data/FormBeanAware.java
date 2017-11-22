package org.javaWebGen.data;

import org.json.JSONException;

public interface FormBeanAware {
	/** 
	*return as XML
	*@return xml text
	*/
	public String toXML();
	/** 
	*return as JSON 
	*@return json text
	*/
	public String toJSON()throws JSONException;

	 

}
