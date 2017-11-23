/*
Copyright (c)2003 Kevin Scott

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

package org.javaWebGen.generator;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import org.javaWebGen.exception.DBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**********************************************************
 * Helper object used by the code Generator classes to map db columns to java
 * object, methods, and variables
 * 
 * @version $Revision: 1.2 $
 * @author Kevin Scott
 ***********************************************************/
public class DataMapper {

	/* same tables use case sensitive tables names */
	public static boolean useUpCaseTableName = false;
	private final static Logger log= LoggerFactory.getLogger(DataMapper.class);
	// get private vars
	/**
	 *map vars
	 */
	public static String mapVar(String fieldName, Integer value) {
		String varDef = "private Integer " + formatVarName(fieldName)
				+ " = null;\n";
		return varDef;
	}

	/**
	 *map vars
	 */
	public static String mapVar(String fieldName, Long value) {
		String varDef = "private Long " + formatVarName(fieldName)
				+ " = null;\n";
		return varDef;
	}

	/**
	 * /** map vars
	 */
	public static String mapVar(String fieldName, Byte value) {
		String varDef = "private Byte " + formatVarName(fieldName)
				+ " = null;\n";
		return varDef;
	}

	public static String mapVar(String fieldName, Short value) {
		String varDef = "private Short " + formatVarName(fieldName)
				+ " = null;\n";
		return varDef;
	}

	public static String mapVar(String fieldName, Float value) {
		String varDef = "private Float " + formatVarName(fieldName)
				+ " = null;\n";
		return varDef;
	}

	public static String mapVar(String fieldName, Double value) {
		String varDef = "private Double " + formatVarName(fieldName)
				+ " = null;\n";
		return varDef;
	}

	/**
	 *map vars
	 */
	public static String mapVar(String fieldName, String value) {
		String varDef = "private String " + formatVarName(fieldName)
				+ " = null;\n";
		return varDef;
	}

	/**
	 *map vars
	 */
	public static String mapVar(String fieldName, java.util.Date value) {
		String varDef = "private java.util.Date " + formatVarName(fieldName)
				+ " = null;\n";
		return varDef;
	}

	/**
	 * map vars
	 */
	public static String mapVar(String fieldName, Boolean value) {
		String varDef = "private Boolean " + formatVarName(fieldName)
				+ " = null;\n";
		return varDef;
	}

	/**
	 * map vars
	 */
	public static String mapVar(String fieldName, Object value) {
		String varDef = "private Object " + formatVarName(fieldName)
				+ " = null;\n";
		return varDef;
	}

	/**
	 *map vars
	 */
	public static String mapVar(String fieldName, BigDecimal value) {
		String varDef = "private BigDecimal  " + formatVarName(fieldName)
				+ " = null;\n";
		return varDef;
	}

	/**
	 *mapParm
	 */
	public static String mapParm(String fieldName, Integer value) {
		String varDef = "private Integer " + formatVarName(fieldName);
		return varDef;
	}

	/**
	 *mapParm
	 */
	public static String mapParm(String fieldName, Short value) {
		String varDef = "private Short " + formatVarName(fieldName);
		return varDef;
	}

	/**
	 *mapParm
	 */
	public static String mapParm(String fieldName, Byte value) {
		String varDef = "private Byte " + formatVarName(fieldName);
		return varDef;
	}

	/**
	 *mapParm
	 */
	public static String mapParm(String fieldName, Float value) {
		String varDef = "private Float " + formatVarName(fieldName);
		return varDef;
	}

	/**
	 *mapParm
	 */
	public static String mapParm(String fieldName, Double value) {
		String varDef = "private Double " + formatVarName(fieldName);
		return varDef;
	}

	public static String mapParm(String fieldName, Object value) {
		String varDef = "private Object " + formatVarName(fieldName);
		return varDef;
	}

	/**
	 *map PARM
	 */
	public static String mapParm(String fieldName, String value) {
		String varDef = "String " + formatVarName(fieldName);
		return varDef;
	}

	/**
	 *map PARM
	 */
	public static String mapParm(String fieldName, java.util.Date value) {
		String varDef = "java.util.Date " + formatVarName(fieldName);
		return varDef;
	}

	/**
	 * map PARM
	 */
	public static String mapParm(String fieldName, Boolean value) {
		String varDef = "boolean " + formatVarName(fieldName) + " = null;\n";
		return varDef;
	}

	/**
	 *Map PARM
	 */
	public static String mapParm(String fieldName, BigDecimal value) {
		String varDef = "BigDecimal  " + formatVarName(fieldName)
				+ " = null;\n";
		return varDef;
	}

	// generate getters and setters

	/**
	 * build getter setter
	 */
	public static String mapMethod(String fieldName, Integer value) {
		String getMethod = "\n\t/********************************************\n"
				+ "\t*Warning Generated method gets value of field\n"
				+ "\t*@return value from database column\n"
				+ "\t***********************************************/\n"
				+ "\tpublic int get"
				+ formatMethodName(fieldName)
				+ "() {\n"
				+ "\t\tif("
				+ formatVarName(fieldName)
				+ "==null){\n"
				+ "\t\t\treturn 0;\n"
				+ "\t\t}\n"
				+ "\t\treturn "
				+ formatVarName(fieldName) + ".intValue();\n" + "\t}\n";
		String setMethod = "\n\t/*********************************************\n"
				+ "\t*Warning Generated method sets field value\n"
				+ "\t*@param value for database column\n"
				+ "\t*************************************************/\n"
				+ "\tpublic void set"
				+ formatMethodName(fieldName)
				+ "(int value){\n"
				+ "\t\t"
				+ formatVarName(fieldName)
				+ "=new Integer(value);\n" + "\t}\n";
		return getMethod + setMethod;
	}

	/**
	 * build getter setter
	 */
	public static String mapMethod(String fieldName, Short value) {
		String getMethod = "\n\t/********************************************\n"
				+ "\t*Warning Generated method gets value of field\n"
				+ "\t*@return value from database column\n"
				+ "\t***********************************************/\n"
				+ "\tpublic short get"
				+ formatMethodName(fieldName)
				+ "() {\n"
				+ "\t\tif("
				+ formatVarName(fieldName)
				+ "==null){\n"
				+ "\t\t\treturn 0;\n"
				+ "\t\t}\n"
				+ "\t\treturn "
				+ formatVarName(fieldName) + ".shortValue();\n" + "\t}\n";
		String setMethod = "\n\t/*********************************************\n"
				+ "\t*Warning Generated method sets field value\n"
				+ "\t*@param value for database column\n"
				+ "\t*************************************************/\n"
				+ "\tpublic void set"
				+ formatMethodName(fieldName)
				+ "(short value){\n"
				+ "\t\t"
				+ formatVarName(fieldName)
				+ "=new Short(value);\n" + "\t}\n";
		return getMethod + setMethod;
	}

	/**
	 * build getter setter
	 */
	public static String mapMethod(String fieldName, Byte value) {
		String getMethod = "\n\t/********************************************\n"
				+ "\t*Warning Generated method gets value of field\n"
				+ "\t*@return value from database column\n"
				+ "\t***********************************************/\n"
				+ "\tpublic byte get"
				+ formatMethodName(fieldName)
				+ "() {\n"
				+ "\t\tif("
				+ formatVarName(fieldName)
				+ "==null){\n"
				+ "\t\t\treturn 0;\n"
				+ "\t\t}\n"
				+ "\t\treturn "
				+ formatVarName(fieldName) + ".byteValue();\n" + "\t}\n";
		String setMethod = "\n\t/*********************************************\n"
				+ "\t*Warning Generated method sets field value\n"
				+ "\t*@param value for database column\n"
				+ "\t*************************************************/\n"
				+ "\tpublic void set"
				+ formatMethodName(fieldName)
				+ "(byte value){\n"
				+ "\t\t"
				+ formatVarName(fieldName)
				+ "=new Byte(value);\n" + "\t}\n";
		return getMethod + setMethod;
	}

	/**
	 * build getter setter
	 */
	public static String mapMethod(String fieldName, Float value) {
		String getMethod = "\n\t/********************************************\n"
				+ "\t*Warning Generated method gets value of field\n"
				+ "\t*@return value from database column\n"
				+ "\t***********************************************/\n"
				+ "\tpublic float get"
				+ formatMethodName(fieldName)
				+ "() {\n"
				+ "\t\tif("
				+ formatVarName(fieldName)
				+ "==null){\n"
				+ "\t\t\treturn 0;\n"
				+ "\t\t}\n"
				+ "\t\treturn "
				+ formatVarName(fieldName) + ".floatValue();\n" + "\t}\n";
		String setMethod = "\n\t/*********************************************\n"
				+ "\t*Warning Generated method sets field value\n"
				+ "\t*@param value for database column\n"
				+ "\t*************************************************/\n"
				+ "\tpublic void set"
				+ formatMethodName(fieldName)
				+ "(float value){\n"
				+ "\t\t"
				+ formatVarName(fieldName)
				+ "=new Float(value);\n" + "\t}\n";
		return getMethod + setMethod;
	}

	/**
	 * build getter setter
	 */
	public static String mapMethod(String fieldName, Long value) {
		String getMethod = "\n\t/********************************************\n"
				+ "\t*Warning Generated method gets value of field\n"
				+ "\t*@return value from database column\n"
				+ "\t***********************************************/\n"
				+ "\tpublic Long get"
				+ formatMethodName(fieldName)
				+ "() {\n"
				+ "\t\treturn "
				+ formatVarName(fieldName) + ";\n" + "\t}\n";
		String setMethod = "\n\t/*********************************************\n"
				+ "\t*Warning Generated method sets field value\n"
				+ "\t*@param value for database column\n"
				+ "\t*************************************************/\n"
				+ "\tpublic void set"
				+ formatMethodName(fieldName)
				+ "(Long value){\n"
				+ "\t\t"
				+ formatVarName(fieldName)
				+ "=value;\n" + "\t}\n";
		return getMethod + setMethod;
	}

	/**
	 * build getter setter
	 */
	public static String mapMethod(String fieldName, String value) {
		String getMethod = "\n\t/********************************************\n"
				+ "\t*Warning Generated method gets value of field\n"
				+ "\t*@return value from database column\n"
				+ "\t***********************************************/\n"
				+ "\tpublic String get"
				+ formatMethodName(fieldName)
				+ "() {\n"
				//+ "\t\tif("
				//+ formatVarName(fieldName)
				//+ "==null){\n"
				//+ "\t\t\treturn \"\";\n"
				//+ "\t\t}else{\n"
				+ "\t\t\treturn "
				+ formatVarName(fieldName)
				+ ";\n"
				//+ "\t\t}\n"
				+ "\t}\n";
		String setMethod = "\n\t/*********************************************\n"
				+ "\t*Warning Generated method sets field value\n"
				+ "\t*@param value for database column\n"
				+ "\t*************************************************/\n"
				+ "\tpublic void set"
				+ formatMethodName(fieldName)
				+ "(String value){\n"
				+ "\t\tif(value==null){\n"
				+ "\t\t\t"
				+ formatVarName(fieldName)
				+ "=null;\n"
				+ "\t\t}else{\n"
				+ "\t\t\t"
				+ formatVarName(fieldName)
				+ "= value;\n"
				+ "\t\t}\n" + "\t}\n";
		return getMethod + setMethod;
	}

	/**
	 * build getter setter
	 */
	public static String mapMethod(String fieldName, java.util.Date value) {
		String getMethod = "\n\t/********************************************\n"
				+ "\t*Warning Generated method gets value of field\n"
				+ "\t*@return value from database column\n"
				+ "\t***********************************************/\n"
				+ "\tpublic java.util.Date get"
				+ formatMethodName(fieldName)
				+ "() {\n"
				+ "\t\treturn "
				+ formatVarName(fieldName)
				+ ";\n"
				+ "\t}\n";

		String setMethod = "\n\t/*********************************************\n"
				+ "\t*Warning Generated method sets field value\n"
				+ "\t*@param value for database column\n"
				+ "\t*************************************************/\n"
				+ "\tpublic void set"
				+ formatMethodName(fieldName)
				+ "(java.util.Date value){\n"
				+ "\t\t"
				+ formatVarName(fieldName) + "=value;\n" + "\t}\n";
		return getMethod + setMethod;
	}

	/**
	 * build getter setter
	 */
	public static String mapMethod(String fieldName, Boolean value) {
		String getMethod = "\n\t/*********************************************\n"
				+ "\t*Warning Generated method sets field value\n"
				+ "\t*@param value from database column\n"
				+ "\t*************************************************/\n"
				+ "\tpublic boolean get"
				+ formatMethodName(fieldName)
				+ "() {\n"
				+ "\t\tif("
				+ formatVarName(fieldName)
				+ "==null){\n"
				+ "\t\t\treturn false;\n"
				+ "\t\t}\n"
				+ "\t\treturn "
				+ formatVarName(fieldName)
				+ ".booleanValue();\n" + "\t}\n";
		String setMethod = "\n\t/*********************************************\n"
				+ "\t*Warning Generated method sets field value\n"
				+ "\t*@param value for database column\n"
				+ "\t*************************************************/\n"
				+ "\tpublic void set"
				+ formatMethodName(fieldName)
				+ "(boolean value){\n"
				+ "\t\t"
				+ formatVarName(fieldName)
				+ "=new Boolean(value);\n" + "\t}\n";
		return getMethod + setMethod;
	}

	/**
	 *build getter setter
	 */
	public static String mapMethod(String fieldName, BigDecimal value) {
		String getMethod = "\n\t/********************************************\n"
				+ "\t*Warning Generated method gets value of field\n"
				+ "\t*@return value from data base column\n"
				+ "\t***********************************************/\n"
				+ "\tpublic BigDecimal get"
				+ formatMethodName(fieldName)
				+ "() {\n"
				+ "\t\treturn "
				+ formatVarName(fieldName)
				+ ";\n"
				+ "\t}\n";
		String setMethod = "\n\t/*********************************************\n"
				+ "\t*Warning Generated method sets field value\n"
				+ "\t*@param value for data base column\n"
				+ "\t*************************************************/\n"
				+ "\tpublic void set"
				+ formatMethodName(fieldName)
				+ "(BigDecimal value){\n"
				+ "\t\t"
				+ formatVarName(fieldName)
				+ "=value;\n" + "\t}\n";
		return getMethod + setMethod;

	}

	/**
	 *build getter setter
	 */
	public static String mapMethod(String fieldName, Double value) {
		String getMethod = "\n\t/********************************************\n"
				+ "\t*Warning Generated method get value of field\n"
				+ "\t*@return value from data base column\n"
				+ "\t***********************************************/\n"
				+ "\tpublic double get"
				+ formatMethodName(fieldName)
				+ "() {\n"
				+ "\t\tif("
				+ formatVarName(fieldName)
				+ "==null){\n"
				+ "\t\t\treturn 0.0;\n"
				+ "\t\t}\n"
				+ "\t\treturn "
				+ formatVarName(fieldName)
				+ ".doubleValue();\n" + "\t}\n";
		String setMethod = "\n\t/*********************************************\n"
				+ "\t*Warning Generated method set field value\n"
				+ "\t*@param value from data base column/\n"
				+ "\t*************************************************/\n"
				+ "\tpublic void set"
				+ formatMethodName(fieldName)
				+ "(double value){\n"
				+ "\t\t"
				+ formatVarName(fieldName)
				+ "=new Double(value);\n" + "\t}\n";
		return getMethod + setMethod;

	}

	/**
	 * build getter setter for Blobs may not work!
	 */
	public static String mapMethod(String fieldName, Object value) {
		String getMethod = "\n\t/*********************************************\n"
				+ "\t*Warning Generated method sets field value\n"
				+ "\t*Warning seting BLOB is not tested\n"
				+ "\t*@param value from database column\n"
				+ "\t*************************************************/\n"
				+ "\tpublic Object get"
				+ formatMethodName(fieldName)
				+ "() {\n"
				+ "\t\treturn "
				+ formatVarName(fieldName)
				+ ";\n"
				+ "\t}\n";
		String setMethod = "\n\t/*********************************************\n"
				+ "\t*Warning Generated method sets field value\n"
				+ "\t*Warning seting BLOB is not tested\n"
				+ "\t*@param value for database column\n"
				+ "\t*************************************************/\n"
				+ "\tpublic void set"
				+ formatMethodName(fieldName)
				+ "(Object value){\n"
				+ "\t\t"
				+ formatVarName(fieldName)
				+ "=value;\n" + "\t}\n";
		return getMethod + setMethod;
	}

	/**
	 *generate getData method
	 */
	public static String mapGetData(String fieldNames, String type, int position) {
		String method = "\t\t";
		method += "data[" + position + "]=" + formatVarName(fieldNames) + ";\n";
		return method;
	}

	/**
	 *build get data method
	 */
	public static String mapGetData(String fieldNames, Integer type,
			int position) {
		String method = "\t\t";
		method += "data[" + position + "]=" + formatVarName(fieldNames) + ";\n";
		return method;
	}

	/**
	 *build get data method
	 */
	public static String mapGetData(String fieldNames, Short type, int position) {
		String method = "\t\t";
		method += "data[" + position + "]=" + formatVarName(fieldNames) + ";\n";
		return method;
	}

	/**
	 *build get data method
	 */
	public static String mapGetData(String fieldNames, Byte type, int position) {
		String method = "\t\t";
		method += "data[" + position + "]=" + formatVarName(fieldNames) + ";\n";
		return method;
	}

	/**
	 *build get data method
	 */
	public static String mapGetData(String fieldNames, Float type, int position) {
		String method = "\t\t";
		method += "data[" + position + "]=" + formatVarName(fieldNames) + ";\n";
		return method;
	}

	/**
	 *build get data method
	 */
	public static String mapGetData(String fieldNames, Long type, int position) {
		String method = "\t\t";
		method += "data[" + position + "]=" + formatVarName(fieldNames) + ";\n";
		return method;
	}

	/**
	 * build get data method
	 */
	public static String mapGetData(String fieldNames, Boolean type,
			int position) {
		String method = "\t\t";
		method += "data[" + position + "]=" + formatVarName(fieldNames) + ";\n";
		return method;
	}

	/**
	 *build get data method
	 */
	public static String mapGetData(String fieldNames, BigDecimal type,
			int position) {
		String method = "\t\t";
		method += "data[" + position + "]=" + formatVarName(fieldNames) + ";\n";
		return method;
	}

	/**
	 *build get data method
	 */
	public static String mapGetData(String fieldNames, Double type, int position) {
		String method = "\t\t";
		method += "data[" + position + "]=" + formatVarName(fieldNames) + ";\n";
		return method;
	}

	/**
	 *build get data method
	 */
	public static String mapGetData(String fieldNames, java.util.Date type,
			int position) {
		String method = "\t\t";
		method += "data[" + position + "]=" + formatVarName(fieldNames) + ";\n";
		return method;
	}

	/**
	 *build get data method
	 */
	public static String mapGetData(String fieldNames, Object type, int position) {
		String method = "\t\t";
		method += "data[" + position + "]=" + formatVarName(fieldNames) + ";\n";
		return method;
	}

	// gen setData
	/**
	 *build get data method
	 * 
	 */
	public static String mapSetData(String fieldNames, String type, int position) {
		// bug fix check for nulls before setting field
		String method = "\t\tif (data[" + position + "]!=null){\n" + "\t\t\t"
				+ formatVarName(fieldNames) + " =  data[" + position
				+ "].toString();\n" + "\t\t}else{\n" + "\t\t\t"
				+ formatVarName(fieldNames) + "=null;\n" + "\t\t}\n";
		return method;

	}

	/**
	 *build set data method
	 */
	public static String mapSetData(String fieldNames, Integer type,
			int position) {
		String method = "\t\t";
		method += formatVarName(fieldNames) + " = (Integer) data[" + position
				+ "];\n";
		return method;

	}

	/**
	 *build set data method
	 */
	public static String mapSetData(String fieldNames, Long type, int position) {
		String method = "\t\t";
		method += formatVarName(fieldNames) + " = (Long) data[" + position
				+ "];\n";
		return method;

	}

	/**
	 *build set data method
	 */
	public static String mapSetData(String fieldNames, Byte type, int position) {
		String method = "\t\t";
		method += formatVarName(fieldNames) + " = (Byte) data[" + position
				+ "];\n";
		return method;

	}

	/**
	 *build set data method
	 */
	public static String mapSetData(String fieldNames, Short type, int position) {
		String method = "\t\t";
		method += formatVarName(fieldNames) + " = (Short) data[" + position
				+ "];\n";
		return method;

	}

	/**
	 *build set data method
	 */
	public static String mapSetData(String fieldNames, Float type, int position) {
		String method = "\t\t";
		method += formatVarName(fieldNames) + " = (Float) data[" + position
				+ "];\n";
		return method;

	}

	/**
	 *build set data method
	 */
	public static String mapSetData(String fieldNames, Double type, int position) {
		String method = "\t\t";
		method += formatVarName(fieldNames) + " = (Double) data[" + position
				+ "];\n";
		return method;
	}

	/**
	 *build set data method
	 */
	public static String mapSetData(String fieldNames, BigDecimal type,
			int position) {
		String method = "\t\t";
		method += formatVarName(fieldNames) + " = (BigDecimal) data["
				+ position + "];\n";
		return method;

	}

	/**
	 *build set data method
	 */
	public static String mapSetData(String fieldNames, Boolean type,
			int position) {
		String method = "\t\t";

		method += formatVarName(fieldNames) + " = (Boolean) data[" + position
				+ "];\n";

		return method;

	}

	/**
	 *build set data method
	 */
	public static String mapSetData(String fieldNames, java.util.Date type,
			int position) {
		String method = "\t\t";
		method += formatVarName(fieldNames) + " = (java.util.Date) data["
				+ position + "];\n";
		return method;

	}

	/**
	 *build set data method
	 */
	public static String mapSetData(String fieldNames, Object type, int position) {
		String method = "\t\t";
		method += formatVarName(fieldNames) + " = data[" + position + "];\n";
		return method;

	}

	// gen SQL for Object
	/**
	 *build insert SQL
	 */
	public static String mapInsertSQL(String[] fieldNames, String tableName) {
		String sql = "INSERT INTO " + tableName + " (" + fieldNames[0];
		for (int i = 1; i < fieldNames.length; i++) {
			sql += "," + fieldNames[i];
		}
		sql += ") VALUES(?";
		for (int x = 1; x < fieldNames.length; x++) {
			sql += ",?";
		}
		sql += ")";
		return sql;
	}

	/**
	 *build update sql
	 */
	public static String mapUpdateSQL(String[] fieldNames, String tableName) {
		String sql = "UPDATE " + tableName + " SET " + fieldNames[0] + "=? ";
		for (int i = 1; i < fieldNames.length; i++) {
			sql += "," + fieldNames[i] + "=? ";
		}

		return sql;
	}

	/**
	 *build select statment
	 */
	public static String mapSelectSQL(String[] fieldNames, String tableName) {
		String sql = "Select " + fieldNames[0];
		for (int i = 1; i < fieldNames.length; i++) {
			sql += "," + fieldNames[i];
		}
		return sql;

	}

	/**
	 * build select for loadData method
	 */
	public static String mapLoad(String[] fieldNames, String tableName) {
		String sql = "Select " + fieldNames[0];
		for (int i = 1; i < fieldNames.length; i++) {
			sql += "," + fieldNames[i];
		}
		return sql;

	}

	// name formaters
	/**
	 *formats a name
	 */
	public static String formatVarName(String name) {
		StringBuffer buffer = new StringBuffer("");
		buffer.append(name.toLowerCase());
		char underScore = '_';
		char dash = '-';

		buffer.setCharAt(0, Character.toLowerCase(buffer.charAt(0)));
		for (int i = 1; i < buffer.length(); i++) {
			if (buffer.charAt(i) == underScore || buffer.charAt(i) == dash) {
				buffer.delete(i, i + 1);
				buffer.setCharAt(i, Character.toUpperCase(buffer.charAt(i)));
			}
		}
		return buffer.toString();

	}

	/**
	 *formats a name
	 */
	public static String formatMethodName(String name) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(name.toLowerCase());
		char underScore = '_';
		char dash = '-';

		buffer.setCharAt(0, Character.toUpperCase(buffer.charAt(0)));
		for (int i = 1; i < buffer.length(); i++) {
			if (buffer.charAt(i) == underScore || buffer.charAt(i) == dash) {
				buffer.delete(i, i + 1);
				buffer.setCharAt(i, Character.toUpperCase(buffer.charAt(i)));
			}
		}

		return buffer.toString();
	}

	/**
	 *converts Table name to a proper class name
	 */
	public static String formatClassName(String name) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(name.toLowerCase());
		char underScore = '_';
		char dash = '-';
		if (buffer.length() < 1) {

			return "";
		}

		buffer.setCharAt(0, Character.toUpperCase(buffer.charAt(0)));
		for (int i = 1; i < buffer.length(); i++) {
			if (buffer.charAt(i) == underScore || buffer.charAt(i) == dash) {
				buffer.delete(i, i + 1);
				buffer.setCharAt(i, Character.toUpperCase(buffer.charAt(i)));
			}
		}

		return buffer.toString();
	}

	/**
	 * get list of columns with a primary key
	 * 
	 * @param con
	 * @param tableName
	 * @return list of primary keys
	 * @throws SQLException
	 */
	public static ArrayList<String> getPrimaryKeys(Connection con,
			String tableName) throws SQLException {
		ArrayList<String> primaryKeys = new ArrayList<String>();
		DatabaseMetaData meta = con.getMetaData();
		ResultSet result = null;
		log.debug("table name=" + tableName);
		result = meta.getPrimaryKeys(null, null, tableName);
		boolean foundKey = false;

		while (result.next()) {
			log.debug("found a key!!!!!!!!!");
			log.debug("found key=" + result.getString(4) + " "
					+ result.getString(6));
			primaryKeys.add(result.getString(4));
			foundKey = true;
		}
		try {
			if (result != null) {
				result.close();
			}
		} catch (Exception e) {
			log.error("Error closeing Db resources");
		}
		if (!foundKey) { // try upperCase table name some DB will UPCase the
							// table name
			// such HSQL, and Derby
			result = meta.getPrimaryKeys(null, null, tableName.toUpperCase());

			while (result.next()) {
				log.debug("found a key!!!!!!!!!");
				log.debug("found key=" + result.getString(4) + " "
						+ result.getString(6));
				primaryKeys.add(result.getString(4));
				useUpCaseTableName = true; // use an upper case table name in
											// all SQL
			}
			try {
				if (result != null) {
					result.close();
				}
			} catch (Exception e) {
				log.error("main",e);
			}
		}

		return primaryKeys;
	}

	/**********************************************************
    *
    **********************************************************/
	public static int[] getPrimaryType(ArrayList<String> primaryKeys,
			String[] colNames, int[] colType) throws DBException {

		int[] types = new int[primaryKeys.size()];
		// log.debug("p keys size"+primaryKeys.size());
		for (int i = 0; i < primaryKeys.size(); i++) {
			String name = primaryKeys.get(i).toString();
			// log.debug("p key name["+i+"]="+name ) ;
			boolean found = false;
			for (int x = 0; x < colNames.length; x++) {
				// log.debug("name="+name+"?colName="+colNames[x]);
				if (name.equals(colNames[x])) {
					found = true;
					types[i] = colType[x];
					break;
				}
			}
			if (!found) {
				throw new DBException(DBException.GENERIC,
						"Unable to find Primary Key Type for col=" + name);
			}
		}
		return types;
	}

	/**
	 * currently only allows Long ids
	 * 
	 * @param cols
	 * @param types
	 * @return JDO private values
	 * @throws Exception
	 */
	protected static String  makeJDOPrivateVars(Object[] cols, int[] types)
			throws Exception {
		String text = "";
		for (int i = 0; i < cols.length; i++) {
			text += "@PrimaryKey\n@Persistent\n";
			DataMapper.mapVar(cols[i].toString(), new Long(-1));
		}
		return text;
		

	}


	/**
	 *gen all private vars
	 */
	protected static String makeJDBCPrivateVars(String[] cols, int[] types)
			throws Exception {
		String text = "";

		for (int i = 0; i < cols.length; i++) {

			switch (types[i] ) {
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				text+=DataMapper.mapVar(cols[i].toString(), new String());
			break;
			case Types.BOOLEAN:
			case Types.BIT:
				text += DataMapper.mapVar(cols[i], new Boolean(false));
				log.debug("bit(boolean");
			break;

			case Types.TINYINT:
			case Types.SMALLINT:
			case Types.INTEGER:
				text += DataMapper.mapVar(cols[i], new Integer(-1));
			break;
			case Types.BIGINT:
				
				 text+=DataMapper.mapVar(cols[i], new Long(-1));
		
			break;
			case Types.REAL:
			case Types.FLOAT:
			case Types.DOUBLE:
				text += DataMapper.mapVar(cols[i], new Double(-1.0));
				break;
			case Types.NUMERIC:
			case Types.DECIMAL:
				log.debug("bigDecimal!!!");
				text += DataMapper.mapVar(cols[i], new BigDecimal(-1));
				break;
			case Types.DATE:
			case Types.TIMESTAMP:
				text +=  DataMapper.mapVar(cols[i], new java.util.Date());
				break;
			case Types.LONGVARBINARY:
			case Types.BINARY:
			case Types.BLOB:
			case Types.JAVA_OBJECT:
				text +=" //blob and clobs not supported";

				break;
			}// end switch

		}
		return text;
	}
	/**
	 *gen all private vars
	 */
	protected static String makeJDOPrivateVars(String[] cols, int[] types,boolean[] isKey)
			throws Exception {
		String text =

		"";

		for (int i = 0; i < cols.length; i++) {
			switch (types[i]) {
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				text += "@Persistent \n"
						+ DataMapper.mapVar(cols[i], new String());
				break;
			/*
			 * not directly supported could serialize it case Types.BOOLEAN:
			 * case Types.BIT: text+="@Persistent \n"+DataMapper.mapVar(cols[i],
			 * new Boolean(false) ); log.debug("bit(boolean"); break;
			 */
			case Types.TINYINT:
			case Types.SMALLINT:
			case Types.INTEGER:
				text += "@Persistent \n"
						+ DataMapper.mapVar(cols[i], new Integer(-1));
				break;
			case Types.BIGINT:
				if(isKey[i]){
					text += "@PrimaryKey\n@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)\n"+
					DataMapper.mapVar(cols[i], new Long(-1));
				}else{
					text += "@Persistent\n"+DataMapper.mapVar(cols[i], new Long(-1));
				}
		
				break;
			case Types.REAL:
			case Types.FLOAT:
			case Types.DOUBLE:
				text += "@Persistent \n"
						+ DataMapper.mapVar(cols[i], new Double(-1.0));
				break;
			case Types.NUMERIC:
			case Types.DECIMAL:
				log.debug("bigDecimal!!!");
				text += "@Persistent \n"
						+ DataMapper.mapVar(cols[i], new BigDecimal(-1));
				break;
			case Types.DATE:
			case Types.TIMESTAMP:
				text += "@Persistent \n"
						+ DataMapper.mapVar(cols[i], new java.util.Date());
				break;
			case Types.LONGVARBINARY:
			case Types.BINARY:
			case Types.BLOB:
			case Types.JAVA_OBJECT:
			case Types.CLOB:
			case Types.OTHER:
				text += "@Persistent \n"
						+ DataMapper.mapVar(cols[i], new Object());
				break;
			}// end switch

		}
		return text;
	}

	/**
	 * returns GetData Method
	 * 
	 * @param cols
	 *            names
	 * @param types
	 * @return get data text
	 */

	protected static String mapGetDataMethod(String[] cols, int[] types) {
		String text = "";
		for (int i = 0; i < cols.length; i++) {
			switch (types[i]) {
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				text += DataMapper.mapGetData(cols[i], new String(), i);
				break;
			case Types.BOOLEAN:
			case Types.BIT:
				// debug("\tboolean");
				text += DataMapper.mapGetData(cols[i], new Boolean(false), i);
				break;
			case Types.TINYINT:
				// integer
				text += DataMapper.mapGetData(cols[i], new Byte((byte) -1), i);
				break;

			case Types.SMALLINT:
				// integer
				text += DataMapper
						.mapGetData(cols[i], new Short((short) -1), i);
				break;

			case Types.INTEGER:
				// integer
				text += DataMapper.mapGetData(cols[i], new Integer(-1), i);
				break;
			case Types.BIGINT:
				// long
				text += DataMapper.mapGetData(cols[i], new Long(0), i);
				break;
			case Types.REAL:
				text += DataMapper.mapGetData(cols[i], new Float(-1.0F), i);
				break;
			case Types.FLOAT:
			case Types.DOUBLE:
				text += DataMapper.mapGetData(cols[i], new Double(-1.0), i);
				break;
			case Types.NUMERIC:
			case Types.DECIMAL:
				text += DataMapper.mapGetData(cols[i], new BigDecimal(-1), i);
				break;
			case Types.DATE:
				text += DataMapper.mapGetData(cols[i], new java.util.Date(), i);
				break;
			case Types.TIMESTAMP:
				text += DataMapper.mapGetData(cols[i], new java.util.Date(), i);
				break;
			case Types.LONGVARBINARY:
			case Types.BINARY:
			case Types.BLOB:
			case Types.JAVA_OBJECT:
			case Types.CLOB:
			case Types.OTHER:
				text += DataMapper.mapGetData(cols[i], new Object(), i);
				break;
			}

		}

		return text;
	}

	/****************************************
	 * gen setData metod
	 ****************************************/
	protected static String makeSetDataMethod(String[] cols, int[] types) {
		String text = "";

		for (int i = 0; i < cols.length; i++) {
			switch (types[i]) {
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				text += DataMapper.mapSetData(cols[i], new String(), i);
				break;
			case Types.BOOLEAN:
			case Types.BIT:
				text += DataMapper.mapSetData(cols[i], new Boolean(false), i);
				break;
			case Types.TINYINT: // could be byte
				text += DataMapper.mapSetData(cols[i], new Byte((byte) -1), i);
				break;
			case Types.SMALLINT: // could be short
				text += DataMapper
						.mapSetData(cols[i], new Short((short) -1), i);
				break;
			case Types.INTEGER:
				text += DataMapper.mapSetData(cols[i], new Integer(-1), i);
				break;
			case Types.BIGINT:
				text += DataMapper.mapSetData(cols[i], new Long(-1), i);
				break;
			case Types.REAL:
				text += DataMapper.mapSetData(cols[i], new Float(-1.0F), i);
				break;
			case Types.FLOAT:
			case Types.DOUBLE:
				text += DataMapper.mapSetData(cols[i], new Double(-1.0), i);
				break;
			case Types.DECIMAL:
			case Types.NUMERIC:
				text += DataMapper.mapSetData(cols[i], new BigDecimal(-1), i);
				break;
			case Types.DATE:
				text += DataMapper.mapSetData(cols[i], new java.util.Date(), i);
				break;
			case Types.TIMESTAMP:
				text += DataMapper.mapSetData(cols[i], new java.util.Date(), i);
				break;
			case Types.LONGVARBINARY:
			case Types.BINARY:
			case Types.BLOB:
			case Types.VARBINARY:
			case Types.JAVA_OBJECT:
			case Types.CLOB:
			case Types.OTHER:
				text += DataMapper.mapSetData(cols[i], new Object(), i);
				break;
			}

		}
		return text;
	}

	/**
	 * gen setter for non String data Will convert from string input
	 */
	protected static String makeOverloadSetters(String[] cols, int[] types)
			throws Exception {
		String text = "";
		String methodName = "";
		for (int i = 0; i < cols.length; i++) {
			methodName = DataMapper.formatMethodName(cols[i]);
			switch (types[i]) {
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				// do nothing already a string
				break;
			case Types.BOOLEAN:
			case Types.BIT:
				text += "\t/****************************************\n"
						+ "\t*set a boolean column(BIT) based on a string\n"
						+ "\t*@param input String\n"
						+ "\t*******************************************************/\n"
						+ "\tpublic void set" + methodName
						+ "(String input) throws ParseException{\n"
						+ "\t\tif(input!=null && input.trim().length()>0 ){\n"
						+ "\t\t" + DataMapper.formatVarName(cols[i])
						+ "=new Boolean(input);\n" + "\t\t}\n" + "\t}\n";
				break;
			case Types.TINYINT:
				text += "\t/****************************************\n"
						+ "\t*set a byte column based on a string\n"
						+ "\t*@param input String\n"
						+ "\t*******************************************************/\n"
						+ "\tpublic void set" + methodName
						+ "(String input) throws ParseException{\n"
						+ "\t\tif(input!=null && input.trim().length()>0 ){\n"
						+ "\t\t" + DataMapper.formatVarName(cols[i])
						+ "=new Byte(input);\n" + "\t\t}\n" + "\t}\n";
				break;
			case Types.SMALLINT:
				text += "\t/****************************************\n"
						+ "\t*set a smallint column based on a string\n"
						+ "\t*@param input String\n"
						+ "\t*******************************************************/\n"
						+ "\tpublic void set" + methodName
						+ "(String input) throws ParseException{\n"
						+ "\t\tif(input!=null && input.trim().length()>0 ){\n"
						+ "\t\t" + DataMapper.formatVarName(cols[i])
						+ "=new Short(input);\n" + "\t\t}\n" + "\t}\n";
				break;
			case Types.INTEGER:
				text += "\t/****************************************\n"
						+ "\t*set a integer column based on a string\n"
						+ "\t*@param input String\n"
						+ "\t*******************************************************/\n"
						+ "\tpublic void set" + methodName
						+ "(String input) throws ParseException{\n"
						+ "\t\tif(input!=null && input.trim().length()>0 ){\n"
						+ "\t\t" + DataMapper.formatVarName(cols[i])
						+ "=new Integer(input);\n" + "\t\t}\n" + "\t}\n";
				break;
			case Types.BIGINT:
				text += "\t/****************************************\n"
						+ "\t*set a long column based on a string\n"
						+ "\t*@param input String\n"
						+ "\t*******************************************************/\n"
						+ "\tpublic void set" + methodName
						+ "(String input) throws ParseException{\n"
						+ "\t\tif(input!=null && input.trim().length()>0 ){\n"
						+ "\t\t" + DataMapper.formatVarName(cols[i])
						+ "=new Long(input);\n" + "\t\t}\n" + "\t}\n";
				break;
			case Types.REAL:
				text += "\t/****************************************\n"
						+ "\t*set a Real(Float) column based on a string\n"
						+ "\t*@param input String\n"
						+ "\t*******************************************************/\n"
						+ "\tpublic void set" + methodName
						+ "(String input) throws ParseException{\n"
						+ "\t\tif(input!=null && input.trim().length()>0 ){\n"
						+ "\t\t\t" + DataMapper.formatVarName(cols[i])
						+ "=new Float(input);\n" + "\t\t}\n" + "\t}\n";
				break;
			case Types.FLOAT:
			case Types.DOUBLE:
				text += "\t/****************************************\n"
						+ "\t*set a Double column based on a string\n"
						+ "\t*@param input String\n"
						+ "\t*******************************************************/\n"
						+ "\tpublic void set" + methodName
						+ "(String input) throws ParseException{\n"
						+ "\t\tif(input!=null && input.trim().length()>0 ){\n"
						+ "\t\t\t" + DataMapper.formatVarName(cols[i])
						+ "=new Double(input);\n" + "\t\t}\n" + "\t}\n";
				break;

			case Types.NUMERIC:
			case Types.DECIMAL:
				text += "\t/****************************************\n"
						+ "\t*set a DECIMAL or NUMERIC column based on a string\n"
						+ "\t*@param input String\n"
						+ "\t*******************************************************/\n"
						+ "\tpublic void set" + methodName
						+ "(String input) throws ParseException{\n"
						+ "\t\tif(input!=null && input.trim().length()>0 ){\n"
						+ "\t\t\t" + DataMapper.formatVarName(cols[i])
						+ "=new BigDecimal(input);\n" + "\t\t}else{\n"
						+ "\t\t\t" + DataMapper.formatVarName(cols[i])
						+ "=null;\n" + "\t\t}\n" + "\t}\n";
				break;
			case Types.DATE:
				text += "\t/****************************************\n"
					+ "\t*set a DATE or TIMESTAMP column based on a string\n"
					+ "\t*@param input String\n"
					+ "\t*******************************************************/\n"
					+ "\tpublic void set"
					+ methodName
					+ "(String input) throws ParseException{\n"
					+ "\t\tif(input!=null && input.trim().length()>0 ){\n"

					// this may seem odd but the toString method on the date
					// object defaults to DateFormat.MEDIUM
					+ "\t\t\t"+DataMapper.formatVarName(cols[i])
					+ "=StringUtil.convertToDate(input);\n"
					+ "\t\t}\n" 
					+ "\t}\n";
				break;
			case Types.TIMESTAMP:
				text += "\t/****************************************\n"
						+ "\t*set a DATE or TIMESTAMP column based on a string\n"
						+ "\t*@param input String\n"
						+ "\t*******************************************************/\n"
						+ "\tpublic void set"
						+ methodName
						+ "(String input) throws ParseException{\n"
						+ "\t\tif(input!=null && input.trim().length()>0 ){\n"

						// this may seem odd but the toString method on the date
						// object defaults to DateFormat.MEDIUM
						+ "\t\t\t"+DataMapper.formatVarName(cols[i])
						+ "=StringUtil.convertToTime(input);\n"
						+ "\t\t}\n" 
						+ "\t}\n";

				break;

			}

		}
		return text;

	}

	/**
	 * gen getter and setter methods
	 */
	protected static String makeGettersSetters(String[] cols, int[] types)
			throws Exception {
		String text = "";

		for (int i = 0; i < cols.length; i++) {

			switch (types[i]) {
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				text += DataMapper.mapMethod(cols[i], new String());
				break;
			case Types.BOOLEAN:
			case Types.BIT:
				text += DataMapper.mapMethod(cols[i], new Boolean(false));
				break;
			case Types.TINYINT:
				text += DataMapper.mapMethod(cols[i], new Byte((byte) 1));
				break;
			case Types.SMALLINT:
				text += DataMapper.mapMethod(cols[i], new Short((short) -1));
				break;
			case Types.INTEGER:
				text += DataMapper.mapMethod(cols[i], new Integer(-1));
				break;
			case Types.BIGINT:
				text += DataMapper.mapMethod(cols[i], new Long(-1));
				break;
			case Types.REAL:
				text += DataMapper.mapMethod(cols[i], new Float(-1.0F));
				break;
			case Types.FLOAT:
			case Types.DOUBLE:
				text += DataMapper.mapMethod(cols[i], new Double(-1.0));
				break;

			case Types.NUMERIC:
			case Types.DECIMAL:
				log.debug("BigDecimal!!!");
				text += DataMapper.mapMethod(cols[i], new BigDecimal(-1));
				break;
			case Types.DATE:
			case Types.TIMESTAMP:
				text += DataMapper.mapMethod(cols[i], new java.util.Date());
				break;
			case Types.LONGVARBINARY:
			case Types.BINARY:
			case Types.BLOB:
			case Types.JAVA_OBJECT:
			case Types.CLOB:
			case Types.OTHER:
				log.debug("getSet BLOB type=" + types[i]);
				text += DataMapper.mapMethod(cols[i], new Object());
				break;
			}

		}
		return text;

	}
	/**
	 * gen getter and setter methods
	 */
	public static String getJavaTypeFromSQLType(int type) {
			
		String javaType = "";

			switch (type) {
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				javaType ="String";
				break;
			case Types.BOOLEAN:
			case Types.BIT:
				javaType ="String";
				break;
			case Types.TINYINT:
				javaType ="Short";
				break;
			case Types.SMALLINT:
				javaType ="Short";
				break;
			case Types.INTEGER:
				javaType ="Integer";
				break;
			case Types.BIGINT:
				javaType ="Long";
				break;
			case Types.REAL:
				javaType ="Float";
				break;
			case Types.FLOAT:
				javaType ="Float";
				break;
			case Types.DOUBLE:
				javaType =" Double";
				break;

			case Types.NUMERIC:
			case Types.DECIMAL:
				log.debug("BigDecimal!!!");
				javaType ="BigDecimal";
				break;
			case Types.DATE:
			case Types.TIMESTAMP:
				javaType ="Date";
				break;
			case Types.LONGVARBINARY:
			case Types.BINARY:
			case Types.BLOB:
			case Types.JAVA_OBJECT:
			case Types.CLOB:
			case Types.OTHER:
				log.debug("getSet BLOB type=" + type);
				javaType ="Object";
				break;
			}

		return javaType;

	}
}
