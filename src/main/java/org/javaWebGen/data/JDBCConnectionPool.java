
package org.javaWebGen.data;
import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.SQLException;

//
// Here are the dbcp-specific classes.
// Note that they are only used in the setupDriver
// method. In normal use, your classes interact
// only with the standard JDBC API
//



import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
 
import org.apache.commons.dbcp2.PoolingDataSource;

 
import org.javaWebGen.config.DBConst;
import org.javaWebGen.util.PropertiesReader;
import org.javaWebGen.util.Util;
//
// Here's a simple example of how to use the PoolingDriver.
// In this example, we'll construct the PoolingDriver manually,
// just to show how the pieces fit together, but you could also
// configure it using an external conifguration file in
// JOCL format (and eventually Digester).
//

//
// To compile this example, you'll want:
//  * commons-pool.jar
//  * commons-dbcp.jar
// in your classpath.
//
// To run this example, you'll want:
//  * commons-collections.jar
//  * commons-pool.jar
//  * commons-dbcp.jar
//  * the classes for your (underlying) JDBC driver
// in your classpath.
//
// Invoke the class using two arguments:
//  * the connect string for your underlying JDBC driver
//  * the query you'd like to execute
// You'll also want to ensure your underlying JDBC driver
// is registered.  You can use the "jdbc.drivers"
// property to do this.
//
// For example:
//  java -Djdbc.drivers=oracle.jdbc.driver.OracleDriver \
//       -classpath commons-collections.jar:commons-pool.jar:commons-dbcp.jar:oracle-jdbc.jar:. \
//       ManualPoolingDriverExample
//       "jdbc:oracle:thin:scott/tiger@myhost:1521:mysid"
//       "SELECT * FROM DUAL"
//
/**************************************************
*this is untested connection pool using the
* Datasource pooling from the JDBC driver IT uses JNDI
*to find the correct driver.  This will not work with
*older versions of JDBC like sun's odbc-jdbc bridge.
*@see org.javaWebGen.data.OldJDBCConnectionPool
*@deprecated use containers pool instead.  
****************************************************/
 
@Deprecated 
public class JDBCConnectionPool {
	private DataSource dataSource=null;


    public JDBCConnectionPool(PropertiesReader reader) {
        //
        // First, we set up and register the PoolingDriver.
        // Normally this would be handled auto-magically by
        // an external configuration, but in this example we'll
        // do it manually.
        //
        try{
			System.setProperty("jdbc.drivers","sun.jdbc.odbc.JdbcOdbcDriver");
			dataSource=setupDataSource(reader.getProperty(DBConst.JDBC_URL));
			System.out.println("Done.");
		}catch(Exception e){
			Util.error(e);
		}
        //
        // Now, we can use JDBC as we normally would.
        // Using the connect string
        //  jdbc:apache:commons:dbcp:example
        // The general form being:
        //  jdbc:apache:commons:dbcp:<name-of-pool>
        //

    }

    protected Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}

	protected void close(Connection con) throws SQLException{
		if(con !=null){
			con.close();
		}
	}


  
	private DataSource setupDataSource(String connectURI) throws Exception {
        //
        // First, we'll need a ObjectPool that serves as the
        // actual pool of connections.
        //
        // We'll use a GenericObjectPool instance, although
        // any ObjectPool implementation will suffice.
        //
        ObjectPool connectionPool = new GenericObjectPool(null);

        //
        // Next, we'll create a ConnectionFactory that the
        // pool will use to create Connections.
        // We'll use the DriverManagerConnectionFactory,
        // using the connect string passed in the command line
        // arguments.
        //
      //  ConnectionFactory connectionFactory = new DriverManagerConnectionFactory(connectURI,null);

        //
        // Now we'll create the PoolableConnectionFactory, which wraps
        // the "real" Connections created by the ConnectionFactory with
        // the classes that implement the pooling functionality.
        //
       // PoolableConnectionFactory poolableConnectionFactory = new PoolableConnectionFactory(connectionFactory,connectionPool,null,null,false,true);

        //
        // Finally, we create the PoolingDriver itself,
        // passing in the object pool we created.
        //
        PoolingDataSource dataSource = new PoolingDataSource(connectionPool);

        return dataSource;
    }
}
