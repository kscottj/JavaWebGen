package org.javaWebGen.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;

import org.javaWebGen.exception.DBException;
import org.javaWebGen.exception.SqlAppException;

/*******************************************************************************
 * Base DAO(Data Access Object) object that handles running sql against the
 * database using raw SQL.  To be replaced someday with spring
 * 
 * @version $Revision: 1.2 $
 * @author Kevin Scott
 ******************************************************************************/

public class SpringJdbcDao extends JdbcDaoSupport implements DaoAware{
	private static final Logger log=LoggerFactory.getLogger(SpringJdbcDao.class);

	/***************************************************************************
	 * will get a data manager specified in the config file if it fails it will
	 * try to use the MySQL as a DataManager
	 * 
	 **************************************************************************/
	protected SpringJdbcDao() {

			 
			//manager = DataManagerFactory.getDataManager();
	}
	
	
	/***************************************************************************
	 * @param con db connection to close
	 **************************************************************************/
	protected void close(Connection con) {
		this.releaseConnection(con); //spring way
		//this.c
		//manager.close(con);
	}

	/***************************************************************************
	 * make a change to a database using sql calling objects should handle the
	 * database connection
	 * 
	 * @return number of rows affected
	 * @param con db connection
	 * @param sql statement to change database
	 **************************************************************************/
	protected int update(String sql) throws SQLException {
		return this.getJdbcTemplate().update(sql);
	}
 	
	/***************************************************************************
	 * runs an update to database based on a DataBean
	 * 
	 * @param sql with where clause
	 * @param databean with table data to update
	 * @see org.javaWebGen.data.DataBean
	 * @return row count
	 **************************************************************************/
	public int updateDataBean(String sql, DataBean databean) throws DBException {
		return getJdbcTemplate().update(sql,databean.getData(),databean.getDataTypes());
	}

	/***************************************************************************
	 * 
	 * find the Object for a specified column
	 * 
	 * @param column  number
	 * @param result resultset to work on (get matadata for)
	 * @return java object from resultset instead of sql datatypes
	 **************************************************************************/
	protected Object getColumnClass(int column, ResultSet result)
			throws SQLException {
		column++;
		// enter(this+"getColumnClass(");
		ResultSetMetaData meta = result.getMetaData();
		int type = meta.getColumnType(column);

		switch (type) {
		case Types.CHAR:
		case Types.VARCHAR:
		case Types.LONGVARCHAR:
 
			return result.getString(column);
		// return String.class;

		case Types.BIT:
 
			return new Boolean(result.getBoolean(column));

		case Types.TINYINT:
		case Types.SMALLINT:
		case Types.INTEGER:
 
			return new Integer(result.getInt(column));
		case Types.BIGINT:
 
			return new Long(result.getLong(column));

		case Types.FLOAT:
		case Types.DOUBLE:
 
			return new Double(result.getDouble(column));

		case Types.DECIMAL:
		 
			return result.getBigDecimal(column);
		case Types.DATE:
	 
			return (java.util.Date) result.getDate(column);
		case Types.TIMESTAMP:
	 
			return (java.util.Date) result.getTimestamp(column);

		default:
			log.warn("UNLKNOW Data Object of type=" + column);

			return result.getObject(column);
		}

	}

	/***************************************************************************
	 * set a column in a prepared statement based on the java.sql.Type for that
	 * column
	 * 
	 * @param index of the row to start with
	 * @param type SQL data Type
	 * @param column data
	 * @param pstmt the actual preparedStatement to set paramanters for
	 * 
	 **************************************************************************/
	protected void setColumn(int index, int type, Object column,
			PreparedStatement pstmt) throws SQLException {
		index++;
		// Util.debug(index+" is type="+type);

		switch (type) {
		case Types.CHAR:
		case Types.VARCHAR:
		case Types.LONGVARCHAR:
			// Util.debug("\tstring");
			if (column == null || column.toString().equals("")) {
				pstmt.setNull(index, Types.VARCHAR);
			} else {
				pstmt.setObject(index, column);
			}
			return;
		case Types.BIT:
			// Util.debug("\tboolean"+column);
			if (column == null) {
				pstmt.setNull(index, Types.BIT);
			} else {
				// Boolean flag= (Boolean) column;
				pstmt.setObject(index, column);

			}
			return;
		case Types.TINYINT:
			if (column == null) {
				// Util.sqlDump("integer==null");
				pstmt.setNull(index, Types.TINYINT);
				// pstmt.setObject(index, new Integer(0) );
			} else {
				pstmt.setObject(index, column);
			}
			return;
		case Types.SMALLINT:
			if (column == null) {
				// Util.sqlDump("integer==null");
				pstmt.setNull(index, Types.SMALLINT);
				// pstmt.setObject(index, new Integer(0) );
			} else {
				pstmt.setObject(index, column);
			}
			return;
		case Types.INTEGER:
			if (column == null) {
				// Util.sqlDump("integer==null");
				pstmt.setNull(index, Types.INTEGER);
				// pstmt.setObject(index, new Integer(0) );
			} else {
				pstmt.setObject(index, column);
			}
			return;
		case Types.BIGINT:
			if (column == null) {
				// Util.sqlDump("bigint==null");
				pstmt.setNull(index, Types.BIGINT);
			} else {
				pstmt.setObject(index, column);
			}
			return;
		case Types.FLOAT:
			if (column == null) {
				// Util.sqlDump("float==null");
				pstmt.setNull(index, Types.FLOAT);
			} else {
				pstmt.setObject(index, column);
			}

		case Types.DOUBLE:
			if (column == null) {
				// Util.sqlDump("double=null");
				pstmt.setNull(index, Types.DOUBLE);
			} else {
				pstmt.setObject(index, column);
			}
			return;
		case Types.DECIMAL:
			if (column == null) {
				// Util.sqlDump("decimal==null");

				// do this pstmt.setObject(index, new java.math.BigDecimal(0));
				pstmt.setNull(index, Types.DECIMAL);

			} else {
				pstmt.setObject(index, column);
			}
			return;
		case Types.NUMERIC:
			if (column == null) {
				// Util.sqlDump("decimal==null");

				// do this pstmt.setObject(index, new java.math.BigDecimal(0));
				pstmt.setNull(index, Types.NUMERIC);

			} else {
				pstmt.setObject(index, column);
			}
			return;
		case Types.DATE:
			// Util.debug("date");
			if (column == null) {
				// acess does not like this !!!
				// need to add a test that if ODBC

				pstmt.setNull(index, Types.DATE);
			} else {
				java.util.Date date = (java.util.Date) column;
				pstmt.setDate(index, new java.sql.Date(date.getTime()));
			}
			return;
		case Types.TIMESTAMP:
			// Util.debug("timestamp");
			if (column == null) {
				pstmt.setNull(index, Types.TIMESTAMP);
			} else {
				java.util.Date date = (java.util.Date) column;
				java.sql.Timestamp time = new java.sql.Timestamp(date.getTime());
				// Util.debug("time="+time);
				pstmt.setTimestamp(index, time);
			}
			return;
		case Types.JAVA_OBJECT:
			if (column == null) {
				// pstmt.setNull(index, Types.JAVA_OBJECT);
				pstmt.setNull(index, Types.JAVA_OBJECT);
			} else {
				pstmt.setObject(index, column);
			}
			return;
		default:

			throw new SqlAppException("Unknown Object Type" + column);
		}

	}

	/***************************************************************************
	 * Calls the DataManager object for the next value for a primary key
	 * 
	 * @param tablename 
	 * @return the next value for a primary key
	 * @see org.javaWebGen.data.DataManager
	 **************************************************************************/
	protected int getNextValue(String tablename) throws DBException {
		throw new DBException("Not implemented for Spring yet!");
		//return manager.getNextValue(tablename);
	}

	/***************************************************************************
	 * Calls the DataManager object for the next value for a primary key
	 * 
	 * @param con DB connection
	 * @param tablename
	 * @return the next value for a primary key
	 * @see org.javaWebGen.data.DataManager
	 **************************************************************************/
	protected int getNextValue(Connection con, String tablename)
			throws SQLException {
		//return manager.getNextValue(con, tablename);
		throw new SQLException("Not implemented for Spring yet!");
	}
}
