package org.javaWebGen.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;

import org.javaWebGen.config.DBConst;
import org.javaWebGen.exception.DBException;
import org.javaWebGen.exception.SqlAppException;
import org.javaWebGen.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/*******************************************************************************
 * Base DAO(Data Access Object) object that handles running SQL against the
 * database using raw SQL.  To be replaced someday with spring
 * 
 * @version $Revision: 1.2 $
 * @author Kevin Scott
 ******************************************************************************/
@Deprecated  
public class JdbcDao extends DAO{
	/***************************************************************************
	 * @param dataManager data manager to use to talk to the database
	 **************************************************************************/
	private DataManager manager = null;
	private final Logger log=LoggerFactory.getLogger(JdbcDao.class);
	//begin exec
	protected JdbcDao(DataManager dataManager) {
		log.debug(">JdbcDao("+dataManager+")");
		if (dataManager == null) {
			Exception e = new Exception("offending stack trace");
			log.error("DataManager should not be null in DAO constructor",e);

		}
		
		manager = dataManager;
		log.debug("<JdbcDao()="+manager);
	}
	/***************************************************************************
	 * will get a data manager specified in the config file if it fails it will
	 * try to use the MySQL as a DataManager
	 * 
	 **************************************************************************/
	protected JdbcDao() {
		try {
			log.debug(">JdbcDao()");
			manager = DataManagerFactory.getDataManager();

		} catch (Throwable t) {
			log.error(this + "unable to get the data manager\n" + manager
					+ "\nCONFIG file="+ DBConst.DB_CONFIG_FILE);
		}
		// Util.debug("manger="+_manager);
		log.debug("<DAO()"+manager);

	}
	/***************************************************************************
	 * @return current DataManager
	 **************************************************************************/
	protected DataManager getDataManager() {
		return manager;
	}

	
	/***************************************************************************
	 * returns a open connection to the database
	 * 
	 * @return db connection
	 **************************************************************************/
	protected synchronized Connection getConnection() throws SQLException {
		Connection con = null;
		 
			con = manager.getConnection();
		 
		return con;
	}

	/***************************************************************************
	 * @param con db connection to close
	 **************************************************************************/
	protected synchronized void close(Connection con) {
		manager.close(con);
	}

	/***************************************************************************
	 * make a change to a database using sql
	 * 
	 * @return in number of rows affected
	 * @param sql statement to change database
	 **************************************************************************/
	protected synchronized long update(String sql) throws SQLException {
		log.debug(">update(sql)");
		long rows = 0;
		Connection con = null;
		try {
			con = getConnection();
			rows = update(con, sql);

		} finally {
			close(con);
		}
		log.debug("<update(sql)");
		return rows;
	}

	/***************************************************************************
	 * make a change to a database using sql calling objects should handle the
	 * database connection
	 * 
	 * @return number of rows affected
	 * @param con db connection
	 * @param sql statement to change database
	 **************************************************************************/
	protected synchronized long update(Connection con, String sql)
			throws SQLException {
		// enter(this+"update(parm)");
		int rows = -1;
		Statement stmt=null;
		try{
			stmt = con.createStatement();
			rows = stmt.executeUpdate(sql);
			Util.sqlDump(sql, rows);
		}catch(SQLException se){
			Util.sqlDump(sql);
			throw se;
		}finally{
			if(stmt!=null){
				stmt.close();
			}
		}
		return rows;
	}
	/***************************************************************************
	 * make a change to a database using sql
	 * 
	 * @return in number of rows affected
	 * @param sql statement to change database
	 * @param parms list of parms to pass the sql statement
	 **************************************************************************/
	protected synchronized long update(String sql,Object[] parms) throws SQLException {
		log.debug(this + "update(sql)");
		long rows = 0;
		Connection con = null;
		try {
			con = getConnection();
			rows = update(con, sql,parms);

		} finally {
			close(con);
		}
		return rows;
	}

	/***************************************************************************
	 * make a change to a database using sql
	 * 
	 * @return in number of rows affected
	 * @param con db connection
	 * @param sql statement to change database
	 * @param parms to pass the sql
	 **************************************************************************/
	protected synchronized long update(Connection con, String sql, Object[] parms) throws SQLException {
		//log.debug(this + "update(con,sql,parms)");
		int rows = 0;
		PreparedStatement pstmt=null;
		try {
			pstmt=con.prepareStatement(sql);
			
			for (int i = 0; i < parms.length; i++) {
				if (parms[i+1] == null) {
					pstmt.setNull(i+1, java.sql.Types.NULL); // not sur if this
					// works on all dbs
				} else if (parms[i] instanceof java.util.Date) {
					java.util.Date tempDate = (java.util.Date) parms[i];
					pstmt.setDate(i + 1, new java.sql.Date(tempDate.getTime()));
				} else {
					// Util.debug("set col "+i+1);
					pstmt.setObject(i + 1, parms[i]);
				}// end if
			}
			Util.sqlDump(sql, parms,rows);
			rows=pstmt.executeUpdate();
		} finally {
			if(pstmt!=null){
				pstmt.close();				
			}
		}
		//log.debug(this + "update(con,sql,parms)");
		return rows;
	}

	/***************************************************************************
	 * returns the results of running a SQL statement Calling object is
	 * responsible for handling the data base connection
	 * 
	 * @param con db connection
	 * @param sql to run
	 * @param parms to pass to query
	 * @return array list of Object[] that represent the data object
	 **************************************************************************/
	protected ArrayList <Object[]> query(Connection con, String sql, Object[] parms)
			throws SQLException {
		// enter(this+"query()");

		ArrayList <Object[]> list = new ArrayList <Object[]> ();
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql);
			for (int i = 0; i < parms.length; i++) {
				// Util.debug(parms[i].toString() );
			}
			// Util.debug("parms size="+parms.length );

			for (int i = 0; i < parms.length; i++) {
				if (parms[i] == null) {
					pstmt.setNull(i+1, java.sql.Types.NULL); // not sur if this
					// works on all dbs
				} else if (parms[i] instanceof java.util.Date) {
					java.util.Date tempDate = (java.util.Date) parms[i];
					pstmt.setDate(i+1, new java.sql.Date(tempDate.getTime()));
				} else {
					// Util.debug("set col "+i+1);
					pstmt.setObject(i + 1, parms[i]);
				}// end if
			}
			// Util.debug("ready to run query");
			ResultSet rs = pstmt.executeQuery();
			ResultSetMetaData meta = rs.getMetaData();
			int cols = meta.getColumnCount();

			Object[] obj = null;
			int rowCount = 0;

			while (rs.next()) {
				obj = new Object[cols];
				for (int i = 0; i < cols; i++) {
					obj[i] = getColumnClass(i, rs);
				} // end for loop of cols
				list.add(obj);
				rowCount++;
				if(rowCount>TOO_MANY_ROWS){
					log.warn(TOO_MANY_ROWS_WARNING);
				}
			}// end while
			Util.sqlDump(sql, parms, rowCount, 0);
			// }catch(Exception e){
			// e.printStackTrace();
			// throw new DBException(DBException.BAD_QUERY,sql,e);
		} catch (SQLException se) {
			Util.sqlDump(sql, parms);
			throw se;
		}finally{
			if(pstmt!=null){
				pstmt.close();
			}
		}

		// leave(this+"query()");
		Util.debug(list.size() + " Rows");
		return list;
	}

	/***************************************************************************
	 * returns the results of running a sql statement and handles the database
	 * connection
	 * 
	 * @param sql statement to run against the db
	 * @param parms to pass to the prepared staement
	 * @return array list of Object[] that represent the data object
	 **************************************************************************/
	protected ArrayList <Object[]> query(String sql, Object[] parms) throws SQLException {
		Connection con = null;
		try {
			con = getConnection();
		} finally {
			close(con);
		}
		return query(con, sql, parms);
	}

	/***************************************************************************
	 * returns the results of running a SQL statement Calling objects should use
	 * the runQuery method instead it returns more information
	 * 
	 * @param sql statement
	 * @return array list of Object[] that represent the data
	 **************************************************************************/
	protected ArrayList <Object[]> query(String sql) throws SQLException {
		Connection con = null;
		ArrayList <Object[]> list = null;
		try {
			con = getConnection();
			list = query(con, sql);
		} finally {
			close(con);
		}
		return list;
	}

	/***************************************************************************
	 * returns the results of running a sql statement Calling object is
	 * responsible for handling data base connection Calling object should use
	 * the runQuery method because it returns more information
	 * 
	 * @param con db  connection
	 * @param sql to run
	 * @return array list of Object[] that represent the data
	 **************************************************************************/
	protected ArrayList <Object[]> query(Connection con, String sql) throws SQLException {
		// enter(this+"query()");
		Statement stmt = null;
		ArrayList <Object[]> list = new ArrayList<Object[]>();
		// Util.debug("SQL="+sql);
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData meta = rs.getMetaData();
			int cols = meta.getColumnCount();

			Object[] obj = null;

			int rows = 0;
			while (rs.next()) {
				rows++;
				obj = new Object[cols];
				for (int i = 0; i < cols; i++) {
					obj[i] = getColumnClass(i, rs);
				} // end for loop of cols
				list.add(obj);
			}
			Util.sqlDump(sql, rows);
			// }catch(Exception e){
			// throw new DBException(DBException.BAD_QUERY,sql,e);
		} catch (SQLException se) {
			Util.sqlDump(sql);
			throw se;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}
		// Util.debug(list.size()+" Rows");
		return list;

	}

	/***************************************************************************
	 * runs a SQL statement returning the results in a DbResult object The
	 * calling object is responsible for handling the connection to the data
	 * base. Calling objects should use this method
	 * 
	 * @param con connection to DB
	 * @param sql statement
	 * @return results of running a query including some meta data
	 **************************************************************************/
	public DbResult runQuery(Connection con, String sql) throws SQLException {
		// log.debug(this + "runQuery(con,sql)");
		Statement stmt = null;
		ArrayList <Object[]> list = new ArrayList <Object[]> ();
		String[] colNames = null;
		int[] sqlTypes = null;
		// Util.debug("SQL="+sql);
		try {

			stmt = con.createStatement();

			ResultSet rs = stmt.executeQuery(sql);
			ResultSetMetaData meta = rs.getMetaData();
			int cols = meta.getColumnCount();
			colNames = new String[cols];
			sqlTypes = new int[cols];
			for (int c = 0; c < cols; c++) {
				colNames[c] = meta.getColumnName(c + 1);
				sqlTypes[c] = meta.getColumnType(c + 1);
			}

			Object[] obj = null;
			int rows = 0;
			while (rs.next()) {
				rows++;
				obj = new Object[cols];
				for (int i = 0; i < cols; i++) {
					obj[i] = getColumnClass(i, rs);
				} // end for loop of cols
				list.add(obj);
			}
			Util.sqlDump(sql, rows);
			// }catch(Exception e){
			// throw new DBException(DBException.BAD_QUERY,sql,e);
		} catch (SQLException se) {
			Util.sqlDump(sql);
			throw se;
		} finally {
			if (stmt != null) {
				stmt.close();
			}
		}

		// log.debug(this + "runQuery");
		return new DbResult(list, colNames, sqlTypes);
	}

	/***************************************************************************
	 * runs an SQL statement returning the results in a DbResult object data
	 * base. Calling objects should use this method
	 * 
	 * @param sql statement
	 * @return results of running a query including some meta data
	 **************************************************************************/
	public DbResult runQuery(String sql) throws SQLException {
		log.debug(this + "runQuery");
		DbResult result = null;
		Connection con = null;
		try {
			con = getConnection();
			//Util.debug("got connection");
			result = runQuery(con, sql);

		} finally {
			close(con);
		}

		log.debug(this + "runQuery");
		return result;
	}

	/***************************************************************************
	 * runs an SQL statemnt returning the results in a DbResult object calling
	 * object should use this method. Please note this method uses db metadate
	 * in order to work. The normal JDBC PreparedStatement will be slightly
	 * faster. Also it will return the entire result into memory. Large results
	 * should use the normal JDBC resultset
	 * 
	 * @param con connection to DB
	 * @param sql statement
	 * @param parms object[] of parameters to pass into the prepared statement
	 * @return results of running a query including some meta data
	 **************************************************************************/
	public DbResult runQuery(Connection con, String sql, Object[] parms)
			throws SQLException {
		// log.debug(this+"runQuery("+con+"|"+"|"+sql+"|"+parms);

		ArrayList <Object[]> list = new ArrayList <Object[]>();
		PreparedStatement pstmt = null;
		String[] colNames = null;

		int[] sqlTypes = null;
		try {
			pstmt = con.prepareStatement(sql);

			for (int i = 0; i < parms.length; i++) {
				// Util.debug("parm["+i+"]="+parms[i].toString() );
			}

			// Util.debug("parms size="+parms.length );
			for (int i = 0; i < parms.length; i++) {
				if (parms[i] == null) {
					pstmt.setNull(i+1, java.sql.Types.NULL);
				} else if (parms[i] instanceof java.util.Date) {
					java.util.Date tempDate = (java.util.Date) parms[i];
					pstmt.setDate(i+1, new java.sql.Date(tempDate.getTime()));
				} else {
					// Util.debug("set col "+i+1);
					pstmt.setObject(i + 1, parms[i]);
				}// end if
			}
			// Util.debug("ready to run query");
			ResultSet rs = pstmt.executeQuery();

			ResultSetMetaData meta = rs.getMetaData();
			int cols = meta.getColumnCount();
			colNames = new String[cols];
			sqlTypes = new int[cols];
			for (int c = 0; c < cols; c++) {
				colNames[c] = meta.getColumnName(c + 1);
				sqlTypes[c] = meta.getColumnType(c + 1);
			}

			Object[] obj = null;
			int rowCount = 0;

			while (rs.next()) {
				obj = new Object[cols];
				// Util.debug(" found result row");
				for (int i = 0; i < cols; i++) {
					obj[i] = getColumnClass(i, rs);
				} // end for loop of cols
				list.add(obj);
				rowCount++;
			}// end while
			Util.sqlDump(sql, parms, rowCount, 0);
		} catch (SQLException se) {
			Util.sqlDump(sql,parms);
			throw se;
		} finally {
			if (pstmt != null) {
				pstmt.close();
			}
		}

		// log.debug(this+"runQuery(con,sql,parms)");
		return new DbResult(list, colNames, sqlTypes);
	}

	/***************************************************************************
	 * runs an SQL statement returning the results in a DbResult object calling
	 * objects should use this method
	 * 
	 * @param sql statement
	 * @param parms to pass into the prepared statement returns
	 * @return results of running a query including some meta data
	 **************************************************************************/
	public DbResult runQuery(String sql, Object[] parms) throws SQLException {
		DbResult result = null;
		Connection con = null;

		try {
			con = getConnection();
			result = runQuery(con, sql, parms);
		} finally {
			close(con);
		}

		return result;
	}

	/***************************************************************************
	 * runs an update to database based on a DataBean
	 * 
	 * @param sql with where clause
	 * @param databean  with table data to update
	 * @throws DBException 
	 * @see org.javaWebGen.data.DataBean
	 **************************************************************************/
	public int updateDataBean(String sql, DataBean databean) throws SQLException, DBException {

		Connection con = null;
		int rows = 0;
		try {
			con = getConnection();
			rows = updateDataBean(con, sql, databean);
		} finally {
			close(con);
		}
		return rows;
	}

	/***************************************************************************
	 * runs an update to database based on a DataBean
	 * 
	 * @param con db connection
	 * @param sql with where clause
	 * @param databean with table data to update
	 * @throws DBException 
	 * @see org.javaWebGen.data.DataBean
	 **************************************************************************/
	public int updateDataBean(Connection con, String sql, DataBean databean)
			throws SQLException, DBException {
		log.debug(this + "updateDataBean");
		int rows = 0;
		PreparedStatement pstmt = null;
		Object[] parms = databean.getData();

		try {
			pstmt = con.prepareStatement(sql);
			// Util.debug("SQL="+sql);
			// parms = new
			int[] types = databean.getDataTypes();
			// Util.debug( "found "+parms.length);

			for (int i = 0; i < parms.length; i++) {
				//trace("type=" + types[i] + " object==" + parms[i] + "col="+ i);
				setColumn(i, types[i], parms[i], pstmt);
			}
			// Util.debug("ready to run query");
			rows = pstmt.executeUpdate();
			Util.sqlDump(sql, parms, rows, 0);
		} catch (SQLException se) {
			Util.sqlDump(sql,parms);
			throw new DBException(DBException.BAD_QUERY, sql, parms, se);
		} finally {
			try {
				if (pstmt != null) {
					pstmt.close();
				}
			} catch (SQLException e) {
				throw new DBException(DBException.DATABEAN_ERROR,
						"error closing " + pstmt, e);
			}
		}
		// Util.debug(rows+" Rows");
		log.debug(this + "updateDataBean");
		return rows;
	}

	/***************************************************************************
	 * 
	 * find the Object for a specified column
	 * 
	 * @param column  number
	 * @param result resultset to work on (get matadata for)
	 * 
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
			// Util("\tstring");
			return result.getString(column);
		// return String.class;

		case Types.BIT:
			// Util("\tboolean");
			return new Boolean(result.getBoolean(column));

		case Types.TINYINT:
		case Types.SMALLINT:
		case Types.INTEGER:
			// Util("\tInteger");
			return new Integer(result.getInt(column));
		case Types.BIGINT:
			// Util("\tlong");
			return new Long(result.getLong(column));

		case Types.FLOAT:
		case Types.DOUBLE:
			// Util("\tfloat/double");
			return new Double(result.getDouble(column));

		case Types.DECIMAL:
			// Util("\tdecimal");
			return result.getBigDecimal(column);
		case Types.DATE:
			// Util("\tdate");
			return (java.util.Date) result.getDate(column);
		case Types.TIMESTAMP:
			// Util("\ttimestamp");
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
	 * get the result set after running a sql statment generaly only used to get
	 * the metadata by one of the code generators. Calling object should use a
	 * the runQuery method AND should <B>CLOSE THE RESULTSET when done with IT!
	 * </b>
	 * @param con db connection
	 * @param sql string
	 * @return the result set for a sql statement
	 **************************************************************************/
	protected ResultSet getResultSet(Connection con, String sql)
			throws SQLException {

	   log.debug(this+"getResultSet()");
		Statement stmt = null;
		ResultSet rs = null;

		stmt = con.createStatement();
		rs = stmt.executeQuery(sql);
		log.debug(this+".getResultset="+rs);
		return rs;
	}

	/***************************************************************************
	 * Calls the DataManager object for the next value for a primary key
	 * 
	 * @param tablename 
	 * @return the next value for a primary key
	 * @see org.javaWebGen.data.DataManager
	 **************************************************************************/
	protected int getNextValue(String tablename) throws DBException {
		return manager.getNextValue(tablename);
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
		return manager.getNextValue(con, tablename);
	}
}
