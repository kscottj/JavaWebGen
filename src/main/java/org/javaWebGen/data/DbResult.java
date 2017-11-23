/*
Copyright (c) 2003-2006 Kevin Scott

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
 package org.javaWebGen.data;


import java.util.*;
import org.javaWebGen.exception.*;
import java.io.*;


/**
* class that provides a way of saving metadata Data from an SQL query for the data objects
* without using any Data base resources(ie an open connection)
* 
*/
public final class DbResult implements  Serializable{
	private static final long serialVersionUID = -714492447892220268L;
	private int[] sqlTypes = null;
	private String[] colNames = null;
	private String errorMsg=null;
	private  List <Object[]> objectList = null;

	/*******************************************
	* builds a DbResult Object by passing in a Array List of Object arrays
	* containing the results of a SQL statement and a list of column names in the table and
	* a array of column types SQL.TYPES
	* 
	*@param list of Object arrays containing the results of a query
	*@param colNames column Names
	*@param types of columns
	********************************************/
	public DbResult ( List <Object[]> list,String[] colNames, int[] types){
	    this.objectList = list;
	    this.colNames= colNames;
	    this.sqlTypes = types;
	}
	
	/*public DbResult (ArrayList <Object[]> list,DataBean bean){
	    this.objectList = list;
	    this.colNames= bean.ColNames();
	    this.sqlTypes = bean.getDataTypes() ;
	}*/
	/***********************************
	* creates an object storeing an error message
	* @param e exception
	*
	*************************************/
	public DbResult (Exception e){
		objectList=new ArrayList <Object[]>();
		colNames=new String[0];
		sqlTypes=new int[0];
		errorMsg=e.toString();
	}


	/*******************************************
	*get a row of data
	*
	*@param row number
	*@return object from database query
	********************************************/
	public Object[] get(int row){
	   Object o=null;
	   if( objectList !=null){
	  	 o= objectList.get(row);
	 	 return (Object[]) o ;
	 }else{
		 return null;
	 }
	}

	/*******************************************
	*get a row of data
	*
    *@param row number
    *@param name column
	*@return row data at index
	********************************************/
	public Object getByName(int row, String name) throws DBException{
            boolean found = false;
            if (colNames != null){
                for (int i=0 ; i< colNames.length;i++){
                    if (name.equalsIgnoreCase( colNames[i] ) ){
                        Object o= objectList.get(row);
                        Object[] objects = (Object[]) o;
                        found =true;
                        return objects[i];
                    }
                }
            }
            if (!found){
                    throw new DBException(DBException.INVALID_COLUMN_NAME, name);
            }
            return null;
	}

	/*******************************************
	*get column names
	*
	*@return array of col names
	********************************************/
	public String[] getNames(){
            if(colNames!=null){
                return colNames;
            }else{
                String[] t = new String[0];
                return t;
            }
	}

	/******************************************
	*return number of rows in results
	*
	*@return number of rows in the result set
	********************************************/
	public int size(){
		if(objectList!=null){
			return objectList.size();
		}else{
			return 0;
		}
	}
	/******************************************
	*change the column name
	*
	*if the index is out of range it will not change anything
	*@param name column name
	*@param index
	********************************************/
	public void setColName(String name,int index){
            if(objectList!=null && index < colNames.length && index >=0){
                    colNames[index]=name;
            }
	}
	/******************************************
	*return all the rows in the result set
	*
	*@return list of Object arrays 
	********************************************/
	public List <Object[]> getList(){
		 return  objectList ;
	}

	/******************************************
	*get the error message of running the query null if no errors
	*
	*@return error
	********************************************/
	public String getError(){
		return errorMsg;
	}

	/******************************************
	*return all the rows int the result set
	*
	*@param msg error message
	********************************************/
	public void setError(String msg){
		errorMsg=msg;
	}
	/**
	 * get the column type
	 * @param index
	 * @return type
	 */
	public int getColType(int index){
		return sqlTypes[index];
	}
	
	

}


