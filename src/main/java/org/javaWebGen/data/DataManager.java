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

import java.sql.*;
import java.text.ParseException;



import org.javaWebGen.exception.DBException;
import org.javaWebGen.util.PropertiesReader;
/**
 * @version $Revision: 1.1.1.1 $
 * @author Kevin Scott
 *
 */
@Deprecated
public interface DataManager {

	/********************************************
	 *@param reader 
	 **********************************************/
	public void setReader(PropertiesReader reader);

	/********************************************
	 *
	 **********************************************/
	public Sequence getSequence();



	/********************************************
	 *Use this sequence generator
	 *NOTE this should not be used by a calling class
	 *@param seq sequence object
	 **********************************************/
	public void setSequence(Sequence seq);

	/********************************************
	 *
	 **********************************************/
	public void init();

	/******************************************************
	 *get db connection
	 *@exception sql error
	 *******************************************************/
	public Connection getConnection() throws SQLException;

	/******************************************************
	 *close this connection ignoring any errors
	 *@param con db connection
	 *******************************************************/
	public void close(Connection con);

	/*********************************************
	 *get next value for the primary key
	 *@param tablename table name
	 ***********************************************/
	public int getNextValue(String tablename) throws DBException;

	/*********************************************
	 *get next value for the primary key
	 *@param con db connection
	 *@param tablename table name
	 ***********************************************/
	public int getNextValue(Connection con, String tablename)
			throws SQLException;

	/*************************************************
	 *start Db tranacton
	 *@param con db connection
	 **************************************************/
	public void startTransaction(Connection con) throws SQLException;

	/*************************************************
	 *end Db tranacton commiting any uncommited data
	 *@param con  db connection
	 **************************************************/
	public void endTransaction(Connection con) throws SQLException;

	/*************************************************
	 *rollback uncommited data ignoring any errors
	 *@param con db connection
	 **************************************************/
	public void rollbackTransaction(Connection con);

	/*************************************************
	 * get a text String ve
	 * rsion of a date that the 
	 *@param date to convert
	 *@return date string the database understands
	 **************************************************/
	public String formatDate(java.util.Date date);

	/*************************************************
	 * get a date from a date String based on default database format 
	 *database understands
	 *@param dateStr date text
	 *@return date object
	 **************************************************/
	
	public java.util.Date getDate(String dateStr) throws ParseException;
	
	/*************************************************
	 *get a String that is escaped( handles ')
	 *@param text sql
	 *@return sql text
	 **************************************************/
	public String getSqlText(String text);
	/**
	 * 
	 * @return list of current tables
	 */
	public String[] getTableList() throws SQLException;
}