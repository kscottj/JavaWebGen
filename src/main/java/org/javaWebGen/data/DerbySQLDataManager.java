//
//Copyright (c) 2003-2006 Kevin Scott
//
//Permission is hereby granted, free of charge, to any person obtaining a copy of 
//this software and associated documentation files (the "Software"), to deal in 
//the Software without restriction, including without limitation the rights to 
//use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
//of the Software, and to permit persons to whom the Software is furnished to do 
//so, subject to the following conditions:
//
//The above copyright notice and this permission notice shall be included in all 
//copies or substantial portions of the Software.
//
//THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
//IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
//FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
//AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
//LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
//OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
//SOFTWARE.

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
*handles talking to an embedded Derby database.
*This is EXPERMINTAL CODE
*IN FACT Derby currently has a bug that will not allow this to work
*in the way it does the META data.  It does not return the correct number of columns
*that messes up this library big time.  I need to re-test with a new version of Derby
*
***********************************/
//TODO re-test with new version of Derby

/**
 * @author Kevin Scott
 *
 */
@Deprecated
public class DerbySQLDataManager implements DataManager{


	private  String driverName="org.apache.derby.jdbc.EmbeddedDriver";


	public final String BIG_DECIMAL ="java.math.BigDecimal";
	public final String LONG ="java.lang.Long";
	public final String INT ="java.lang.Integer";
	public final String STRING="java.lang.String";
	public final String OBJECT="java.lang.Object";
	public final String DATE="java.sql.Date";
	public final String TIMESTAMP="java.sql.TimeStamp";

	private Sequence sequence = null;
	private PropertiesReader reader = null;
	//private OldJDBCConnectionPool pool=null;
	/*derby default format I think need to check this */
	private final SimpleDateFormat derbyDF=new SimpleDateFormat("yyyy-MM-DD");


	private String url=null;
	private String user=null;
	private String passwd=null;

    /********************************************
	*set db properties reader
    *@param propReader data base properties reader
	**********************************************/
	public void setReader(PropertiesReader propReader){
          reader =propReader;
        }
	/**************************************
	*used to change the default database driver
	*@param name of class of driver
	***************************************/
	public void setDriverClass(String name){
	    driverName=name;
	} 
	
	/********************************************
	*
	**********************************************/
	 @Override
	public Sequence getSequence(){
		return sequence;
	}

	/********************************************
	*
	**********************************************/
	 @Override
	public void setSequence(Sequence seq){
		sequence = seq;
	}

	/********************************************
	*
	**********************************************/
	 @Override
	public void init(){
            Util.enter(this+"init()");
            try {
                if(reader==null){
                    reader = PropertiesReader.getReader(DBConst.DB_CONFIG_FILE);
                    //context = new InitialContext();
                }
        		this.url = reader.getProperty(DBConst.JDBC_URL);
        		Util.info("use jdbc URL="+url);
        		this.user = reader.getProperty(DBConst.JDBC_USERID);
        		this.passwd =reader.getProperty(DBConst.JDBC_PASSWORD);
                //pool= new OldJDBCConnectionPool(reader,driverName);
        		 Class.forName(driverName); 
            } catch(Exception e) {
                    Util.error("Driver init Error",e);
            }

            Util.leave(this+"init()");
	}

	/******************************************************
	*@return JDBC connection object
	*******************************************************/
	 @Override
	public final Connection getConnection() throws SQLException{
		//enter(this+".getConnection()" );
		Connection con =null;
   		try {
			//Class.forName(driverName);
			con = DriverManager.getConnection(url, user, passwd);
			//con=pool.getConnection();

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
	*@param con close a JDBC connection object
	*
	*******************************************************/
	 @Override
	public final void close(Connection con){

		if(con !=null){
			//pool.close(con);
			try {
				con.close();
			} catch (SQLException e) {
				Util.error("could not close DB con!!!",e);
			}
		}

	}

	/*********************************************
	*@param tablename table name to get next key for
	*@return the next unique value
	***********************************************/
	 @Override
	public final int getNextValue(String tablename) throws DBException{
		return sequence.getNextValue(tablename);
	}

	/*********************************************
	*
	***********************************************/
	 @Override
	public final int getNextValue(Connection con, String tablename) throws SQLException{
		return sequence.getNextValue(con, tablename);
	}


	/*************************************************
	*
	*@param con connection
	**************************************************/
	 @Override
	public void startTransaction(Connection con) throws SQLException{
		//odbc can not do this c
		con.setAutoCommit(false);
		con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

	}

	/*************************************************
	*commit transaction
	*@param con connection
	**************************************************/
	@Override
	public void endTransaction(Connection con) throws SQLException{
		con.commit();
		con.setAutoCommit(true);

		//odbc can not do this con.commit();
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
	    *derby uses YYYY-MM-DD by default
	    *@param date
	    *@return formatted string of the date
	    **************************************************/
	 	@Override
	    public String formatDate(java.util.Date date) {
	        String sql =null;
	        if(date!=null){
	            //SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");
	            sql=derbyDF.format(date);
	        }
	        return sql;
	    } 
	    @Override
	    /*************************************************
	     *get a date object by parsing a string the databases native format
	     *useful for doing updates etc.. when not using databean objects
	     *derby uses YYYY-MM-DD by default
	     *@param dateStr date text
	     *@return formatted string of the date
	     * @throws ParseException
	     **************************************************/
	     public java.util.Date getDate(String dateStr) throws ParseException {
	         
	         if(dateStr!=null){
	             //SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");
	             return derbyDF.parse(dateStr);
	         }
	         return null;
	     }
    
		 /***************************************************************************
		 * get a String that is escaped (handles ')
		 * derby likes \' not ''
		 * @param text input text
		 **************************************************************************/
	     @Override
		 public String getSqlText(String text) {
			return StringUtil.sqlEscWithBackslash(text); 
			
		}  
		 
	    public String[] getTableList(){
	    	return null;
	    }

}



