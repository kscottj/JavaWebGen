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
import org.javaWebGen.util.StringUtil;
import org.javaWebGen.util.Util;



/**********************************
*handles talking to an mySQL database.
*NOTE transactions may not do anything if the database was built with ISAM
*instead of INODB
*This is the <B>MOST TESTED</B> Datamanger because I have My SQL installed.
***********************************/
@Deprecated
public class MySQLDataManager implements DataManager{

	private  String driverName="org.gjk.mm.mysql.Driver";
	public final String BIG_DECIMAL ="java.math.BigDecimal";
	public final String LONG ="java.lang.Long";
	public final String INT ="java.lang.Integer";
	public final String STRING="java.lang.String";
	public final String OBJECT="java.lang.Object";
	public final String DATE="java.sql.Date";
	public final String TIMESTAMP="java.sql.TimeStamp";
	private Sequence sequence = null;
	private PropertiesReader reader = null;
	private OldJDBCConnectionPool pool=null;
	private SimpleDateFormat mySqlDF=new SimpleDateFormat("yyyy-MM-DD");


    /********************************************
	*set db properties reader
    *@param propReader base properties reader
	**********************************************/
	 @Override
	public void setReader(PropertiesReader propReader){
            this.reader =propReader;
        }


	/********************************************
	*@param seq Sequence object
	**********************************************/
	 @Override
	public void setSequence(Sequence seq){
		sequence = seq;
	}

	/********************************************
	*
	**********************************************/
	 @Override
	public void  init(){
            Util.enter(this+"init()");
            try {
                if(reader==null){
                    reader = PropertiesReader.getReader(DBConst.DB_CONFIG_FILE);
                    //context = new InitialContext();
                }
             
                pool= new OldJDBCConnectionPool(reader,driverName);

            } catch(Exception e) {
                    Util.fatal("DataManager init Error",e);
                    
            }
            Util.leave(this+"init()");
	}

	/******************************************************
	*@return JDBC connection object
	*******************************************************/
	 @Override
	public Connection getConnection() throws SQLException{
		Util.enter(this+".getConnection()" );
		Connection con =null;
   		try {
			//con = DriverManager.getConnection(url, user, passwd);
			con=pool.getConnection();
			Util.enter("open connection)="+con );

		}catch (SQLException ex) {
			Util.error("Cannot connect to this database.",ex);
			//handleException(ex);
			throw ex;
		}

		con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		return con;
	}
	/******************************************************
	*Return a connection back to the pool of available connections
	*@param con JDBC Connection object
	*
	*******************************************************/
	 @Override
	public void close(Connection con){
		
		if(con !=null){
			pool.close(con);
			Util.leave("closed connection="+con);
		}

	}

	/*********************************************
	*@param tablename table name to get next key for
	*@return the next unique value
	***********************************************/
	 @Override
	public int getNextValue(String tablename) throws DBException{
		return getSequence().getNextValue(tablename);
	}
	/*********************************************
	*
	***********************************************/
	 @Override
	public int getNextValue(Connection con, String tablename) throws SQLException{
		return getSequence().getNextValue(con, tablename);
	}


	/*************************************************
	*start a DB transaction
	*@param con JDBC connection
	**************************************************/
	 @Override
	public void startTransaction(Connection con) throws SQLException{
		//odbc can not do this c
		con.setAutoCommit(false);
		con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

	}

	/*************************************************
	*commit transaction
	*@param con JDBC Connection
	**************************************************/
	 @Override
	public void endTransaction(Connection con) throws SQLException{
		con.commit();
		con.setAutoCommit(true);

	}

	/*************************************************
	*rollback transaction
	*@param con connection
	**************************************************/
	 @Override
	public void rollbackTransaction(Connection con) {
		if(con !=null){
			try{
				con.rollback();
			}catch(SQLException e){
				Util.error("Unable to rollback transaction on conection "+con);
			}
		}
	}
    /*************************************************
    *get a String of a date object in the databases native format
    *useful for doing updates etc.. when not using databean objects
    *mysql uses YYYY-MM-DD by default
    *@param date
    *@return formatted string of the date
    **************************************************/
	 @Override
    public String formatDate(java.util.Date date) {
        String sql =null;
        if(date!=null){
            //SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");
            sql=mySqlDF.format(date);
        }
        return sql;
    } 
    /*************************************************
     *get a date object by parsing a string the databases native format
     *useful for doing updates etc.. when not using databean objects
     *mysql uses YYYY-MM-DD by default
     *@param dateStr date text
     *@return formatted string of the date
     * @throws ParseException
     **************************************************/
	 @Override
     public java.util.Date getDate(String dateStr) throws ParseException {
         
         if(dateStr!=null){
             //SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");
             return mySqlDF.parse(dateStr);
         }
         return null;
     } 
    /***************************************************************************
	 * get a String that is escaped (handles ')
	 * mysql like \' not ''
	 * @param text input text
	 **************************************************************************/
	 @Override
	public String getSqlText(String text) {
		return StringUtil.sqlEscWithBackslash(text); 
	}
    /**
	 * get a list of tables from the system catalog
	 */
	 @Override
	public String[] getTableList() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

/**
 * 
 */
	 @Override
	public Sequence getSequence() {
		/*if(sequence==null){
			sequence= new GenericSequence();
		}*/
		return sequence;
	}   

}



