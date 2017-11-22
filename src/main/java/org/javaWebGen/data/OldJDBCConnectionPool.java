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

import org.javaWebGen.config.DBConst;
import org.javaWebGen.exception.DBException;
import org.javaWebGen.util.ObjectPool;
import org.javaWebGen.util.PropertiesReader;
import org.javaWebGen.util.Util;


/*************************************************
*Very Simple Database(JDBC) connection pool for keeping a pool
*of open database connections.  This will work with any JDBC driver.
*Even the JDBC-ODBC Bridge.  
*
*Does not require a datasource from the container
*@deprecated use Apache commons DBCP
*@version $Revision: 1.2 $
**************************************************/
 
@Deprecated
public class OldJDBCConnectionPool extends ObjectPool
{
    private String dsn, usr, pwd;
    private long recycleTime;
    private int maxCon,maxRetires;
    //private String defaultDriver="org.gjk.mm.mysql.Drive";
    public final String DEFAULT_MAX_RETRIES = "10";
    public final String DEFAULT_TIMEOUT = ""+1000*60*5;
    public final String DEFAULT_CONNECTION_SIZE = "10";
    

/**
 * 
 * @param driver
 * @param dsn
 * @param usr
 * @param pwd
 */
    public OldJDBCConnectionPool( String driver, String dsn, String usr, String pwd )
    {
        try
        {
            Class.forName( driver ).newInstance();
        }
        catch( Exception e )
        {
            Util.error(e);
        }
        this.dsn = dsn;
        this.usr = usr;
        this.pwd = pwd;
    }

/**
 * 
 * @param reader
 * @param defaultDriver default driver name
 */
    public OldJDBCConnectionPool( PropertiesReader reader,String defaultDriver)
	{
		String driver=reader.getProperty(DBConst.JDBC_DRIVER, defaultDriver);
		try
		{
			Class.forName( driver ).newInstance();
		}
		catch( Exception e )
		{
			Util.error(e);
		}
		this.dsn = reader.getProperty(DBConst.JDBC_URL);
		this.usr = reader.getProperty(DBConst.JDBC_USERID);
		this.pwd =reader.getProperty(DBConst.JDBC_PASSWORD);
		this.recycleTime=Long.parseLong(reader.getProperty(DBConst.CONECTION_RECYCLE,DEFAULT_TIMEOUT) );
		this.maxCon =Integer.parseInt(reader.getProperty(DBConst.CONECTION_MAX,DEFAULT_CONNECTION_SIZE) );
		this.maxRetires=Integer.parseInt(reader.getProperty(DBConst.CONECTION_RETRIES,DEFAULT_MAX_RETRIES) );
		setRecyleTime(recycleTime);
		setPoolSize(maxCon);

		Util.info("POOL DSN="+dsn+" USR="+usr+" pwd="+pwd);
		Util.info("recyleTime="+recycleTime+" MAX Connection="+maxCon);
	}


    /****************************************************
    *Creates a new Database connection
    *@return new connection
    *
    ******************************************************/
    protected Object create() throws SQLException
    {
        Util.debug("Create NEW DB CON"+ dsn+usr+pwd);
        
        		
        return( DriverManager.getConnection( dsn, usr, pwd ) );
    }

    /*********************************************
    *validate to connection is still open
    *
    **********************************************/
    //TODO add the ability to check DB connection IE select * from DUAL
    protected boolean validate( Object o )
    {
        try
        {
           // Util.debug("Validate con "+o);
            return( ! ( ( Connection ) o ).isClosed() );

        }
        catch( SQLException e )
        {
            Util.error(e);
            return false;
        }
    }

    /************************************************
    *close database connection
    *
    *************************************************/
    protected void expire( Object o )
    {
        try
        {//
           // Util.leave("remove con " +o);
            ( ( Connection ) o ).close();
        }
        catch( SQLException e )
        {

             Util.error(e);
        }
    }

    /************************************************
    *get open database connection
    *@return database connection
    *************************************************/
    public Connection getConnection() throws SQLException
    {
       try
       {
            Object o =super.checkOut();  //synchronized method
			if(o==null){
				int count = 0;
				while (o==null){
					try{
						Util.fatal("!!!!NO DATA BASE CONNECTIONS ARE AVAILABE GO TO SLEEP AND WAIT!!!!!!!!!");
						Thread.sleep(1000L);  //if there is no available DB connection what do I do?
									          
					}catch(InterruptedException e){}
					o=super.checkOut();
					count++;
					if (count > maxRetires){
						throw new DBException(DBException.CONNECT_ERROR," tried connecting "+count+" times");
					}
				}

			}
			Util.debug("con="+o);
			return (Connection) o;
       }
       catch( Exception e )
       {
          	if(e instanceof SQLException){
          		throw( (SQLException) e );
			}else{
				throw new SQLException("UNKNOWN ERROR getting DB Connection="+Util.getStackTrace(e) );
			}
       }
    }
  
    /************************************************
    *return open database connection to pool
    *@param con db connection
    *************************************************/
    public void close( Connection con )
    {
        //Util.leave("recyle db con="+c);
        super.checkIn( con ); //synchronized method
    }
}

