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
package org.javaWebGen.exception;

import java.sql.*;
import org.javaWebGen.util.SQLHelper;

/**********************************************
* Data Exception
*
* @author Kevin Scott
* @version $Revision: 1.2 $
*************************************************/
public class DBException extends WebAppException{

/**
	 * 
	 */
	private static final long serialVersionUID = -8736516577157300189L;
	
public static final int GENERIC =0;
public static final int BAD_QUERY =1;
public static final int DATABEAN_ERROR =2;
public static final int DAO_ERROR =3;
public static final int WRONG_NUMBER_OF_ROWS =4;
public static final int INVALID_COLUMN_NAME=5;
public static final int DATAMANGER_ERROR=6;
public static final int SEQUENCE_ERROR=7;
public static final int SQL_ERROR=8;
public static final int NULLPARM_ERROR=8;
public static final int CONNECT_ERROR=9;
public static final int NOT_FOUND_ERROR=10;

public static String[] messages={
	"",
	"Bad Query sql=",
	"Error in generated databean sql=",
	"DAO error sql=",
	"Query returned wrong number of rows ",
	"Query did not contain column =",
	"Counld not ceate a new DataManager(Database Manager)",
	"Cound not get next value in table",
	"SQLEXCEPTION in Data object",
	"Data Object can not Query NULL parms",
	"Failed to retrun a valid DB connection after many attempts",
	"Did not find any data"
	};

public DBException(){}

public DBException(String msg){
	super(msg);

}

public DBException(int code,Throwable t){
	super(messages[code]+"|"+t.getMessage() );
}

public DBException(int code, String msg){
	super(messages[code]+msg);
}


public DBException(int code){
	super(messages[code]);
}
public DBException(int code, String msg,SQLException e){
	super(messages[code]+"|"+msg+"|Root SQL ERROR="+e.getMessage() );
}

public DBException(int code, String msg,Exception e){
	super(messages[code]+"|"+msg+"|Root Error="+e.toString() );
}

public DBException(int code,Exception e){
	super(messages[code]+"|Root Error="+e.toString() );
}

public DBException(int code, String sql,Object[] parms){
	super(messages[code]+"|"+SQLHelper.getSQL(sql,parms)+"|" );
}

public DBException(int code, String sql,Object[] parms, SQLException e){
	super(messages[code]+"|"+SQLHelper.getSQL(sql,parms)+"|Root SQL ERROR="+e.getMessage() );

}

}


