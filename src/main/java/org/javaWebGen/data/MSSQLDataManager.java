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

import org.javaWebGen.config.DBConst;
import org.javaWebGen.data.DataManager;
import org.javaWebGen.exception.DBException;
import org.javaWebGen.util.PropertiesReader;
import org.javaWebGen.util.Util;

/*******************************************************************************
 * handles talking to a Microsoft's SQL server using Microsoft's buggy JDBC
 * driver. This has <b>NOT</b> been tested, because I have not tested SQL
 * server's driver
 * 
 * @version $Revision: 1.1.1.1 $
 * @author Kevin Scott
 * 
 ******************************************************************************/
@Deprecated
public class MSSQLDataManager implements DataManager {

	private String driverName = "com.ms.JdbcDriver";

	public final String BIG_DECIMAL = "java.math.BigDecimal";

	public final String LONG = "java.lang.Long";

	public final String INT = "java.lang.Integer";

	public final String STRING = "java.lang.String";

	public final String OBJECT = "java.lang.Object";

	public final String DATE = "java.sql.Date";

	public final String TIMESTAMP = "java.sql.TimeStamp";

	private Sequence sequence = null;

	private OldJDBCConnectionPool pool = null;

	private PropertiesReader reader = null;

	/* need to set this correctly! */
	SimpleDateFormat mssqlDF = new SimpleDateFormat("yyyy-MM-DD");

	// TODO find out what this is

	/***************************************************************************
	 * set db properties reader
	 * 
	 * @param propReader data base properties reader
	 **************************************************************************/
	public void setReader(PropertiesReader propReader) {
		this.reader = propReader;
	}

	/***************************************************************************
	 * 
	 **************************************************************************/
	public Sequence getSequence() {
		return sequence;
	}

	/***************************************************************************
	 * 
	 **************************************************************************/
	public void setSequence(Sequence seq) {
		sequence = seq;
	}

	/***************************************************************************
	 * 
	 **************************************************************************/
	public void init() {
		Util.enter(this + "init()");
		try {
			if (reader == null) {
				reader = PropertiesReader.getReader(DBConst.DB_CONFIG_FILE);
				// context = new InitialContext();
			}
			 pool= new OldJDBCConnectionPool(reader,driverName);
				//GenericSequence seq = new GenericSequence();
				//setSequence(seq);  
		} catch (Exception e) {
			Util.fatal("Driver init Error", e);

		}
		
		Util.leave(this + "init()");
	}

	/***************************************************************************
	 * 
	 **************************************************************************/
	public Connection getConnection() throws SQLException {
		// enter(this+".getConnection()" );
		Connection con = null;
		try {
			// Class.forName(driverName);
			con = pool.getConnection();
		} catch (SQLException ex) {
			Util.error("Cannot connect to this database.");
			// handleException(ex);
			throw ex;
		}

		// leave(this+"getConnection()" );
		return con;
	}

	/***************************************************************************
	 * 
	 **************************************************************************/
	public void close(Connection con) {
		// enter(this+"returnConnection()" );

		try {
			if (con != null)
				pool.close(con);
		} catch (Exception ex) {
			Util.error("Cannot return Connection to this database.");

		}

		// leave(this+".returnConnection" );
	}

	/***************************************************************************
	 * 
	 **************************************************************************/
	public int getNextValue(String tablename) throws DBException {
		return sequence.getNextValue(tablename);
	}

	/***************************************************************************
	 * 
	 **************************************************************************/
	public int getNextValue(Connection con, String tablename)
			throws SQLException {
		return sequence.getNextValue(con, tablename);
	}

	/***************************************************************************
	 * 
	 * 
	 **************************************************************************/
	public void startTransaction(Connection con) throws SQLException {
		con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		con.setAutoCommit(false);

	}

	/***************************************************************************
	 * 
	 * 
	 **************************************************************************/
	public void endTransaction(Connection con) throws SQLException {
		con.commit();
		con.setAutoCommit(true);

	}

	/***************************************************************************
	 * 
	 * 
	 **************************************************************************/
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
	 * get a String of a date object in the databases native format usefull for
	 * doing updates etc.. when not using databean objects mysql uses YYYY-MM-DD
	 * by default
	 * 
	 * @param date
	 * @return formatted string of the date
	 **************************************************************************/
	public String formatDate(java.util.Date date) {
		String sql = null;
		if (date != null) {
			// SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");
			sql = mssqlDF.format(date);
		}
		return sql;
	}

	/***************************************************************************
	 * get a date object by parsing a string the databases native format usefull
	 * for doing updates etc.. when not using databean objects mysql uses
	 * YYYY-MM-DD by default
	 * 
	 * @param dateStr date
	 * @return formatted string of the date
	 * @throws ParseException
	 **************************************************************************/
	public java.util.Date getDate(String dateStr) throws ParseException {

		if (dateStr != null) {
			// SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");
			return mssqlDF.parse(dateStr);
		}
		return null;
	}

	/***************************************************************************
	 * get a String that is escapted( handles ')
	 * 
	 **************************************************************************/
	public String getSqlText(String text) {
		return text;
	}

	/**
	 * returns a list of tables in
	 * 
	 * 
	 */
	// TODO need to implement this someday
	public String[] getTableList() {
		return null;
	}
}
