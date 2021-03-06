/*
Copyright (c) 2018 Kevin Scott
Permission is hereby granted, free of charge, to any person obtaining a copy of 
this software and associated documentation files (the "Software"), to deal in 
the Software without restriction, including without limitation the rights to 
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do 
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 SOFTWARE.
 */ 
package org.javaWebGen.data.bean;

import java.text.*;
import java.math.*;
import java.io.*;
import java.util.ArrayList;
import javax.jdo.annotations.*;
import javax.annotation.Generated;
import org.javaWebGen.exception.DBException;
import org.javaWebGen.data.DbResult;
import org.javaWebGen.data.DataBean;
import org.javaWebGen.data.FormBeanAware;
import org.javaWebGen.util.StringUtil;
import org.json.*;
/******************************************************************************
* WARNING this JTO object is generated by GenerateJDODataBean v2_12 based on Database config
* This class should not be modified, but, may be extended.
* It will be regenerated when the database schema changes.
*******************************************************************************/
@Generated(value = { "org.javaWebGen.generator.GenerateJDODataBean " })
@PersistenceCapable(identityType = IdentityType.APPLICATION)
@SuppressWarnings({ "serial", "unused" }) 
public class Publisher implements DataBean, Serializable{ 
//begin private Vars
	private static int[] types=null;
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Long publisherId = null;
	@Persistent 
	private String name = null;

//begin getters and setters

	/************************************************
	*Get value from bound field publisher_id
	*@return value from bound data field
	************************************************/
	public Long getPublisherId() {
		return publisherId;
	}

	/************************************************
	*Set value to bound field publisher_id
	*@param value for  bound data field
	************************************************/
	public void setPublisherId(Long value){
		publisherId=value;
	}

	/************************************************
	*Get value from bound field name
	*@return value from bound data field
	**************************************************/
	public String getName() {
			return name;
	}

	/************************************************
	*Set value to bound field name
	*@param value for  bound data field
	*************************************************/
	public void setName(String value){
		if(value==null){
			name=null;
		}else{
			name= value;
		}
	}
	/*******************************************************
	*Set a long column publisher_id based on a string
	*@param input String
	******************************************************/
	public void setPublisherId(String input) throws ParseException{
		if(input!=null && input.trim().length()>0 ){
		publisherId=new Long(input);
		}
	}

//begin getData

	/************************************************
	*Get all data Objects bound to data bean  
	*@return data from object as array of objects 
	****************************************************/
	public Object[] getData(){
		Object[] data = new Object[2];
		data[0]=publisherId;
		data[1]=name;
		return data;
	} //end getData

//begin setData

	/*****************************************************************
	*Populates object with data
	*@param data matching the data from a table
	*@see org.javaWebGen.data.DAO
	*@see org.javaWebGen.data.DbResult
	*******************************************************************/
	public void setData(Object[] data) throws IllegalArgumentException{
		if( data.length != 2){
			throw new IllegalArgumentException("query return wrong number of rows "+data.length);
		} //end if

		publisherId = (Long) data[0];
		if (data[1]!=null){
			name =  data[1].toString();
		}else{
			name=null;
		}
	}//end setData

//begin get Insert

	/****************************************************
	*Build SQL insert statement without a where clause 
	*@return sqle  
	*
	*******************************************************/ 
	public static final String getInsertSQL(){
		String sql = "INSERT INTO publisher (publisher_id,name) VALUES(?,?)";
		return sql;
	}

//begin get update

	/***************************************************
	*Build SQL update statement without a where clause 
	*@return update sql without where clause
	*****************************************************/ 
	public static final String getUpdateSQL(){
		String sql ="UPDATE publisher SET publisher_id=? ,name=? ";
		return sql;
	}

//begin get select

	/**************************************************
	*Build SQL select statement without a where clause 
	*@return sqlect SQL without where clause
	*****************************************************/ 
	public static final String getSelectSQL(){
		String sql = "Select publisher_id,name from publisher ";
		return sql;
	}

//begin get makeLoad

	/*******************************************************************************************
	*Loads DbResult into object
	*@param result
	*@see org.javaWebGen.data.DbResult
	**********************************************************************************************/ 
	public static ArrayList<Publisher> load(DbResult result) throws  IllegalArgumentException{
		ArrayList <Publisher>list= new ArrayList<Publisher>(result.size() );
		for (int i=0; i< result.size(); i++){
			Publisher o= new Publisher();
			o.setData( result.get(i) );
			list.set(i,o);
		 }
		 return list;
	}

//begin make Type 
	/*****************************************************
	*Get an array of column types that match DB table
	*@return array of Types
	******************************************************/
	public int[] getDataTypes(){
		if(types == null){
		 types = new int[2];
			 types[0]=-5;
			 types[1]=12;
		} //end if
	return types;
	}


//begin toXML()

	/********************************************
	*Builds an XML String based on object data
	*@return XML value of bound data
	***********************************************/
	public String toXML(){
		String xml="<Publisher publisherId=\""+getPublisherId()+"\" name=\""+getName()+"\" />\n";
		return xml;
	}//end toXML()

//make JSON text 

	/*********************************************
	*Builds a JSON String based on object data
	*@return JSON text string
	************************************************/
	public String toJSON() {

		JSONObject jo = new JSONObject();
		try{
			jo.append("publisherId",getPublisherId() );
			jo.append("name",getName() );
			return jo.toString();
 		}catch(JSONException je){
			return " Publisher{exception:'"+je.getMessage()+"}'";
 		}
	} //end to Json

//  make  Overrides 
	@Override
	/***********************************************
	* Check value of data bound object.   
	*@param object to check value of
	************************************************/
	public boolean equals(Object o){
		if( o!=null && o instanceof FormBeanAware){
			FormBeanAware bean =(FormBeanAware) o;
			return bean.toXML().equals(bean.toXML() );
		}else{
			return false;
		}
	}

}//end Generated class
