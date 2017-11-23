package org.javaWebGen.util;

import java.util.*;
import org.javaWebGen.data.*;
import org.json.JSONException;
/**
 * class is used to format JSON list in a format that the DOJO js library understand natively
 * using a javascript Dojo ItemName class
 * 
 * @author scotkevi
 *
 */
public class DojoHelper {
	/**
	 * get JSON list
	 * @param beanList list 
	 * @return JSON string for dojo
	 * @throws JSONException error
	 */
	public String toItemList(List<DataBean> beanList) throws JSONException{
		StringBuffer buffer = new StringBuffer("ITEMS[");
		boolean isFirst=true;
		int size=beanList.size();
		for (int i=0;i<size;i++){
			DataBean bean=(DataBean) beanList.get(i);
			String jsonData=bean.toJSON();
			if(!isFirst){
				buffer.append(",");
				isFirst=false;
			}
			buffer.append(jsonData);
		
		}
		buffer.append("]");
		return buffer.toString();
	}
}
