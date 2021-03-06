/*
Copyright (c) 203-2006 Kevin Scott

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

import java.sql.*;
import java.io.*;

import org.javaWebGen.data.JdbcDao;
import org.javaWebGen.util.StringUtil;
import org.javaWebGen.util.Util;


@SuppressWarnings("deprecation")
public class GenerateCachedTable extends JdbcDao {

	 public static String VERSION ="GenerateLookup v1.00";
	 private String SQL = new String("select * from ");
	 private String _tableName = null;
	 private String _filePath = null;
	 private int[] _colTypes=null;
	 private String[] _colNames=null;
	 private String classTemplate= null;


	/**
	* object will generate the DAO for a database table that will be cached in memory
	* and will not be updated.  Meaning a lookup table that might return multiple
	* rows.  Generally this will be a lookup table with sys_company, and contry_code
	* columns
	*/
 
	public GenerateCachedTable(String tableName, String filePath){
		super();
		_tableName = tableName;
		_filePath = filePath;
	}

	/**
	*
	*/
	public void init(){

		classTemplate=
			"package org.kev.data.dao.lookup;\n\n"+
			 	"import java.sql.*;\n"+
			 	"import java.util.*;\n"+
			 	"import java.text.*;\n"+
			 	"import java.math.*;\n"+
			 	"import java.io.*;\n"+
			 	"import org.kev.util.Debug;\n"+
			 	"import org.kev.exception.DBException;\n"+
			 	"import org.kev.data.DbResult;"+
			 	"import org.kev.data.bean.*;\n"+
			 	"import org.kev.data.dao.DAO;\n"+
			 	"import org.kev.data.DataManagerFactory;\n"+
			 	"import org.kev.data.DataManager;\n"+
			 	 "/***************************************************************************\n"+
			 	 "* Copyright 2001 by Kevin Scott All rights reserved                        *\n"+
			 	 "* there are no warrenty on this product either expressed of implied        *\n"+
			 	 "* use at your own risk                                                     *\n"+
			 	 "* WARNING this class is generated by"+VERSION+" based on Database schema  *\n"+
			 	 "* This class should not be modified, but may be extended                   *\n"+
			 	 "* @Author: Kevin Scott                                                     *\n"+
			 	 "* @Version: $Revision: 1.1.1.1 $                                                     *\n"+
			 	 "****************************************************************************/\n"+
			 	 "public class {1}Lookup extends DAO {0} \n"+
			 	 "\tprivate  static {3}[] objectList = null;\n"+
			 	 "//constructor\n"+
			 	 "{4}\n"+
				 "//INIT\n"+
			 	 "\t{5}\n"+
			 	 "\t//findByMethods\n"+
			 	 "\t{6}\n"+
			 	 "{7}\n";
	}

	/**
	*
	*/
	public String makeConstructor() throws Exception{
		String text=
		"\n/**************************************************************************\n"+
		"* WARNING this method is gennerated based on Database schema\n"+
		"* This method load all rows of a lookup table into read only memory(cached)\n"+
		"*****************************************************************************/\n"+
		"public "+_tableName+"Lookup(DataManager manager) {\n"+
		"	super(manager);\n"+
		"    init();\n"+
		"}\n";
		return text;
	}


	/**
	*
	*/
	protected String makeInit(String[] cols)throws Exception{
		String text =
		"\n/**************************************************************************\n"+
		"* WARNING this method is gennerated based on Database schema\n"+
		"* This method load all rows of a lookup table into read only memory(cached)\n"+
		"*****************************************************************************/\n"+
		"public void init() {\n"+
		"\ttry{\n"+
		"\t\tload();\n"+
		"\t}catch(DBException e){\n"+
		"\t\tUtil.error(e);\n"+
		"\t}\n"+
		"}\n\n"+
		"\n/**************************************************************************\n"+
		"* WARNING this method is gennerated based on Database schema\n"+
		"* This method load all rows of a lookup table into read only memory(cached)\n"+
		"*****************************************************************************/\n"+
		"private void load() throws DBException{\n"+
		"\ttry{\n"+
		"\t\tString sql = "+_tableName+".getSelectSQL();\n"+
		"\t\tDbResult result = runQuery(sql);\n"+
		"\t\tobjectList = "+_tableName+".load(result);\n"+
		"\t}catch(SQLException e){\n"+
		"\t\tthrow new DBException(DBException.BAD_QUERY,e);\n"+
		"\t}\n"+
		"}\n"+
		"\n/**************************************************************************\n"+
		"* WARNING this method is gennerated based on Database schema\n"+
		"* This method load all rows of a lookup table into read only memory(cached)\n"+
		"*****************************************************************************/\n"+
		"public void refresh(){\n"+
		"\ttry{\n"+
		"\t\tload();\n"+
		"\t}catch(DBException e){\n"+
		"\t\tUtil.error(e);\n"+
		"\t}\n"+
		"}\n";
		return text;

	}

	/**
	*generate finderMethod
	*/
	protected String makeFindByMethod(String[] cols, int[] types )throws Exception{
		String text ="";


		for(int i=0;i<cols.length;i++){
			Util.debug("methodName="+DataMapper.formatMethodName(cols[i]) );
			text+=
			"\n/*************************************************************************\n"+
			"* WARNING this method is gennerated based on Database schema\n"+
			"* This method returns an onbject that has a field that is equal to the parm\n"+
			"****************************************************************************/\n"+
			"public List"+_tableName+" findBy"+DataMapper.formatMethodName(cols[i])+"("+getDataType(types[i])+" parm) throws DBException{\n"+
			"\tArrayList list = new ArrayList();\n"+
			"\tfor(int i=0;i< objectList.length; i++){\n"+
			"\t"+getDataType(types[i])+" holder= "+convertDataType(types[i])+"objectList[i].get"+DataMapper.formatMethodName(cols[i])+"() );\n"+
			"\t\tif(holder.equals(parm)) {\n"+
			"\t\t\t list.add objectList[i];\n"+
			"\t\t}\n"+
			"\t}\n"+
			"\t\treturn list;\n"+
			"}\n";
		}

		return text;
	}
	/*****************************************************************
	*find the parameter type based on DB
	*
	******************************************************************/
	private String getDataType(int type){
		String text=null;
		switch (type){
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				text="String";
				break;
			case Types.BIT:
				text="Boolean";
				break;
			case Types.TINYINT:
			case Types.SMALLINT:
			case Types.INTEGER:
				text="Integer";
				break;
			case Types.BIGINT:
				text="Long";
				break;

			case Types.FLOAT:
			case Types.DOUBLE:
				text="Double";
				break;

			case Types.DECIMAL:
				text="BigDecimal";
				break;
			case Types.DATE:
				text="java.util.Date";
				break;
			 case Types.TIMESTAMP:
				text="java.util.Date";
				break;
			}
			return text;
		}
	/*****************************************************************
	*find the parameter type based on DB
	*
	******************************************************************/
	private String convertDataType(int type){

		String text="";
		switch (type){
			case Types.CHAR:
			case Types.VARCHAR:
			case Types.LONGVARCHAR:
				text="(String) (";
				break;
			case Types.BIT:
				text="new Boolean(";
				break;
			case Types.TINYINT:
			case Types.SMALLINT:
			case Types.INTEGER:
				text=" new Integer(";
				break;
			case Types.BIGINT:
				text="new (Long";
				break;

			case Types.FLOAT:
			case Types.DOUBLE:
				text="new(Double";
				break;

			case Types.DECIMAL:
				text="(BigDecimal)(";
				break;
			case Types.DATE:
				text="(java.util.Date)(";
				break;
			 case Types.TIMESTAMP:
				text="(java.util.Date)(";
				break;
			}
			return text;
	}

	/********************************************
	*
	*********************************************/
	private void mapData()throws SQLException{
		Util.enter(this+".+mapData()");
		//Object[] parms = new Object[1];
		//parms[0] = _tableName;
		SQL+=_tableName;
		Connection con = null;
		try{
			con = getDataManager().getConnection();

			//RCollection list = getResultSet(con,SQL);

			 
			ResultSet rs = getResultSet(con,SQL);
			ResultSetMetaData meta=rs.getMetaData();
			int cols = meta.getColumnCount();
		//	debug("found columns="+cols);
			_colNames = new String[cols];
			_colTypes = new int[cols];

			for(int i=0;i<cols;i++){
				 _colNames[i] = meta.getColumnName(i+1);
				 if(meta.isCurrency(i+1) ){
					 _colTypes[i] =Types.DECIMAL;
				 }else{
					 _colTypes[i] = meta.getColumnType(i+1);
				 }
			 }
		 }finally{
			close(con);
		 }
		Util.leave(this+".mapData()");
	}



	/*******************************************************
	*
	********************************************************/
	protected String buldClass() throws Exception{

		//String setData=makeSetData(_colNames,_colTypes);
		String[] p = new String[10];
		p[0]= "{";
		p[1]= _tableName;
		p[2]= _tableName;
		p[3]= _tableName;
		p[4]=makeConstructor();
		p[5]= makeInit(_colNames);
		p[6]= makeFindByMethod(_colNames,_colTypes);
		p[7]="}";

		//debug(classTemplate);
		String classText = StringUtil.replace(classTemplate,p);

		return classText;
	}


	/**
	*
	*/
	protected void execute() throws Exception{
		writeJavaClass(buldClass() );
	}

	/**
	*
	*/
	private void writeJavaClass(String text) throws IOException{
		String fileName=_filePath+File.separator+_tableName+"Lookup.java";
		FileWriter file = new FileWriter(fileName);
		PrintWriter out = new PrintWriter(file);
		out.print(text);
		out.flush();
		out.close();
	}

	/**
	*
	*/
	public static void main(String[] args){
		if (args.length != 2){
			System.out.println("USAGE GenerateLookup <tablename> <path>");
			System.exit(911);
		}
		try{

			GenerateCachedTable app = new GenerateCachedTable(args[0], args[1] );
			app._tableName = args[0];
			app.init();
			app.mapData();
			app.execute();
		}catch(Throwable t){
			t.printStackTrace();
		}
	}

}