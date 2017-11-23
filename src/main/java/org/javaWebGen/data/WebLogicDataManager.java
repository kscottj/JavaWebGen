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
import org.javaWebGen.util.Util;
import org.javaWebGen.config.*;




/**********************************
*Handles talking to Weblogic to get a database conneciton.
*only can be used inside the Weblogic container(IE inside a EJB, or JSP)
*sure will be more efficient than doing a JNDI lookup!
*This has not been tested much because I do not have WebLogic installed.
*And only has been tried with an Oracle Database.  Will use the built in weblogic connection pool
*
* WARNING this is Experimental(
***********************************/

@Deprecated
public class WebLogicDataManager implements DataManager{


	
	
	private  String driverName="oracle.jdbc.driver.OracleDriver";
	private  String url="jdbc:weblogic:dev";
	//private  String user=null;
	//private  String passwd = null;

	public final String BIG_DECIMAL ="java.math.BigDecimal";
	public final String LONG ="java.lang.Long";
	public final String INT ="java.lang.Integer";
	public final String STRING="java.lang.String";
	public final String OBJECT="java.land.Object";
	public final String DATE="java.sql.Date";
	public final String TIMESTAMP="java.sql.TimeStamp";
	private Sequence sequence = null;
    private Driver sqlDriver=null;
    private PropertiesReader reader=null;
//TODO will need to allow the default dateformat to be put in the properties file
    /*default oracle date format yyyy-MMM-DD */
    private SimpleDateFormat oracleDF=new SimpleDateFormat("yyyy-MMM-DD");


        protected WebLogicDataManager(){
          //  init();
        }
        
       	/********************************************
	*set db properties reader
        *@param propReader data base properties reader
	**********************************************/
	public void setReader(PropertiesReader propReader){
            reader =propReader;
        }

        /********************************************
        *
        **********************************************/
        public Sequence getSequence(){
                return sequence;
        }

        /********************************************
        *
        **********************************************/
        public void setSequence(Sequence seq){
                sequence = seq;
        }

        /********************************************
        *
        **********************************************/
 
		@SuppressWarnings("rawtypes")
		public void init(){
                Util.enter(this+"init()");
                driverName=reader.getProperty(DBConst.JDBC_DRIVER,driverName);
                url=reader.getProperty(DBConst.JDBC_URL);
                try {
                    Util.debug("Try driver name "+driverName);
                    
                    Class myclass= Class.forName(driverName);
                    Util.debug("Driver Class="+myclass);
                    Object obj = Class.forName(driverName).newInstance();
                    Util.debug("===driver obj="+obj.getClass()+" "+obj);
                   sqlDriver = (Driver) Class.forName(driverName).newInstance();
                  // GenericSequence seq = new GenericSequence( this );
                   //setSequence(seq);
                } catch(Exception e) {
                    Util.fatal("Problem Opening JDBC driver",e);
                }
                Util.leave(this+"init()");
        }

        /******************************************************
        *
        *******************************************************/
        public Connection getConnection() throws SQLException{
            Util.enter(this+".getConnection()" );
            //Util.debug("url="+url+"usr="+user+"pwd="+passwd);
            Connection con = null;
            try {
                //Class.forName(driverName);  
                //_con = DriverManager.getConnection(url, user, passwd);
                //con = DriverManager.getConnection(url,user,passwd);
                con = sqlDriver.connect(url, null);
                
            }catch (SQLException ex) {
                Util.error("Cannot connect to this database.");
                //handleException(ex);
                throw ex;
            }

            Util.leave(this+"getConnection()" );
            return con;
        }
        /******************************************************
        * close db connection actally just return it it ppol
        *@param con db connection
        *******************************************************/
        public void close(Connection con){
                //enter(this+"returnConnection()" );

                try {
                    if(con !=null)
                        con.close();
                }catch (Exception ex) {
                    Util.error("Cannot return Connection to this database.");

                }

                //leave(this+".returnConnection" );
        }

        /*********************************************
        *
        ***********************************************/
        public int getNextValue(String tablename) throws DBException{
                return sequence.getNextValue(tablename);
        }
        /*********************************************
        *
        ***********************************************/
        public int getNextValue(Connection con, String tablename) throws SQLException{
                return sequence.getNextValue(con, tablename);
        }

        /*************************************************
        *
        *
        **************************************************/
        public void startTransaction(Connection con) throws SQLException{
                con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

        }

        /*************************************************
        *
        *
        **************************************************/
        public void endTransaction(Connection con) throws SQLException{
                con.commit();
        }

        /*************************************************
        *
        *
        **************************************************/
        public void rollbackTransaction(Connection con) {
                if(con !=null){
                        try{
                                con.rollback();
                        }catch(SQLException e){
                                Util.error("Unable to rollback transaction on conection "+con);
                        }
                }
        }
        
    	/***************************************************************************
    	 * get a String that the Oracle data will accept
    	 * 
    	 **************************************************************************/
    	public String formatDate(java.util.Date date) {
    		String sql = null;
    		if (date != null) {
    			sql = "'" + oracleDF.format(date) + "'";
    		}
    		return sql;
    	}



	/***************************************************************************
	 * get a date object by parsing a string the databases native format usefull
	 * for doing updates etc.. when not using databean objects mysql uses
	 * YYYY-MM-DD by default
	 * 
	 * @param dateStr ate string
	 * @return formatted string of the date
	 * @throws ParseException error converting date
	 **************************************************************************/
	public java.util.Date getDate(String dateStr) throws ParseException {

		if (dateStr != null) {
			// SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");
			return oracleDF.parse(dateStr);
		}
		return null;
	}

	/***************************************************************************
	 * get a String that is escapted( handles ') WARNING is not tested
	 * 
	 **************************************************************************/
	// TODO test this implementation somday
	public String getSqlText(String text) {
		return text;
	}

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