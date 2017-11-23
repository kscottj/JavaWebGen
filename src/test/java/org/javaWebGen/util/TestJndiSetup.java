package org.javaWebGen.util;


import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJndiSetup {
	private final static Logger log= LoggerFactory.getLogger(TestJndiSetup.class);
	 
	   /**
     * Setup the Data Source
	 * @throws NamingException 
     */
    public static void doSetup(String ds_name) throws NamingException {
        

   		 //System.setProperty("java.naming.factory.initial", "org.osjava.sj.SimpleContextFactory");
   		/*System.setProperty("org.osjava.sj.root", "target/test-classes");
   		 System.setProperty("org.osjava.jndi.delimiter", "/");
   		 System.setProperty("org.osjava.sj.jndi.shared", "true");
   		 System.setProperty("testDb.driver", "true");
   		 System.setProperty("testDb.driver.url", "jdbc:derby:memory:testDB;create=true");
   		 System.setProperty("testDb.user", "na");
   		 System.setProperty("testDb.password", "na");*/
         InitialContext ctxt = new InitialContext();
         log.debug("ctx="+ctxt);

        	 log.debug("lookup="+"jdbc/"+ds_name);
        	 DataSource  ds = (DataSource) ctxt.lookup("jdbc.testDB");
        	// DataSource  ds = (DataSource) ctxt.lookup("java:/comp/env/jdbc/testDB");
         log.debug("ds="+ds);
         
         
            // rebind for alias if needed
            ctxt.rebind("/jdbc/"+ds_name, ds);
            log.debug("bind="+"/jdbc/"+ds_name);
        
    }
}
