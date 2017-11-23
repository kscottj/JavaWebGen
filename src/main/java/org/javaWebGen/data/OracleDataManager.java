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
import java.text.*;
import java.util.ArrayList;

import org.javaWebGen.data.DataManager;
import org.javaWebGen.exception.DBException;
import org.javaWebGen.util.PropertiesReader;
import org.javaWebGen.util.StringUtil;
import org.javaWebGen.util.Util;

/*******************************************************************************
 * Handles talking to an oracle database. This has not been tested much because
 * I do not have oracle installed. This should work! SOMEDAY It will use Oracle
 * Sequence tables for the next value stuff.
 ******************************************************************************/
 
@Deprecated
public class OracleDataManager implements DataManager {

	// private Connection _con= null;
	//private ResultSetMetaData meta = null;

	private String driverName = "oracle.jdbc.driver.OracleDriver";
	public final String BIG_DECIMAL = "java.math.BigDecimal";
	public final String LONG = "java.lang.Long";
	public final String INT = "java.lang.Integer";
	public final String STRING = "java.lang.String";
	public final String OBJECT = "java.lang.Object";
	public final String DATE = "java.sql.Date";
	public final String TIMESTAMP = "java.sql.TimeStamp";
	private PropertiesReader reader = null;
	private Sequence sequence = null;
	private OldJDBCConnectionPool pool = null;

	/* default oracle date format yyyy-MMM-DD */
	private SimpleDateFormat oracleDF = new SimpleDateFormat("yyyy-MMM-DD");


	 @Override
	 /**
	  * 
	  */
	public void setReader(PropertiesReader propReader) {
		reader = propReader;
	}

	 @Override
	 /**
	  * 
	  */
	public Sequence getSequence() {
		return sequence;
	}

	 @Override
	 /**
	  * 
	  */
	public void setSequence(Sequence seq) {
		sequence = seq;
	}

	 @Override
	 /**
	  * 
	  */
	public void init() {
		Util.enter(this + "init()");
		try {

			// Class.forName(driverName); //load dirver()
			// DriverManager.registerDriver(new
			// oracle.jdbc.driver.OracleDriver());
			//GenericSequence seq = new GenericSequence(DataManagerFactory.getDataManager(reader) );
			//setSequence(seq);  
			pool= new OldJDBCConnectionPool(reader,driverName);

		} catch (Exception e) {
			System.err.print("ClassNotFoundException: ");
			System.err.println(e.getMessage());
		}
		Util.leave(this + "init()");
	}

	 @Override
	 /**
	  * 
	  */
	public Connection getConnection() throws SQLException {

		// Util.debug("url="+url+"usr="+user+"pwd="+passwd);
		Connection con = null;
		try {
			// con = DriverManager.getConnection(url, user, passwd);
			con = pool.getConnection();
			Util.trace("open DB conneciton" + con);

		} catch (SQLException ex) {
			Util.error("Cannot connect to this database.");
			throw ex;
		}

		// Util.leave(this+"getConnection()" );
		return con;
	}

	 @Override
	 /**
	  * 
	  */
	public void close(Connection con) {
		// Util.enter(this+"close()"+con );

		try {
			if (con != null)
				// con.close();
				pool.close(con);
			Util.trace("close DB connection " + con);
		} catch (Exception ex) {
			Util.error("Cannot return Connection to this database.");
		}

		// Util.leave(this+".close()"+con );
	}

	 @Override
	 /**
	  * 
	  */
	public int getNextValue(String tablename) throws DBException {
		return sequence.getNextValue(tablename);
	}

	 @Override
	 /**
	  * 
	  */
	public int getNextValue(Connection con, String tablename)
			throws SQLException {
		return sequence.getNextValue(con, tablename);
	}

	 @Override
	 /**
	  * 
	  */
	public void startTransaction(Connection con) throws SQLException {
		con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

	}
	 @Override
	 /**
	  * 
	  */
	public void endTransaction(Connection con) throws SQLException {
		con.commit();
	}

	 @Override
	public void rollbackTransaction(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				Util
						.error("Unable to rollback transaction on conection "
								+ con);
			}
		}
	}

	/***************************************************************************
	 * get a String that the Oracle data will accept
	 * 
	 **************************************************************************/
	 @Override
	public String formatDate(java.util.Date date) {
		String sql = null;
		if (date != null) {
			sql = "'" + oracleDF.format(date) + "'";
		}
		return sql;
	}

	/***************************************************************************
	 * get a date object by parsing a string the databases native format useful
	 * for doing updates etc.. when not using databean objects oracle uses
	 * DD-MMM-YYYY by default
	 * 
	 * @param dateStr date text
	 * @return formatted string of the date
	 * @throws ParseException
	 **************************************************************************/
	 @Override
	public java.util.Date getDate(String dateStr) throws ParseException {

		if (dateStr != null) {
			// SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");
			return oracleDF.parse(dateStr);
		}
		return null;
	}

    /***************************************************************************
	 * get a String that is escaped (handles ')
	 * Oracle likes '' not \'
	 * @param text input text
	 **************************************************************************/
	 @Override
	public String getSqlText(String text) {
		return StringUtil.sqlEscWithSingleQuote(text);		
	}
/**
 * 
 * EXPERIMENTAL returns a list of tables
 * 
 */
	 @Override
	public String[] getTableList() throws SQLException {
		Connection con = null;
		ArrayList <String> al = new ArrayList <String> ();
		String[] tables = null;
		try {
			con = getConnection();
			String sql = "'select tname from sys_Tables";
			Statement stmt = con.createStatement();
			ResultSet result = stmt.executeQuery(sql);
			while (result.next()) {
				al.add(result.getString(1));
			}
			tables = new String[al.size()];

		} finally {
			close(con);
		}
		return tables;
	}
}