package org.javaWebGen.test;


import org.javaWebGen.util.LocalTestaware;

import org.junit.After;
import org.junit.Before;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;

/*
 * import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;*/

	public class LocalServiceGAETestCase implements LocalTestaware{
		 private final LocalServiceTestHelper helper =  
		            new LocalServiceTestHelper(new LocalDatastoreServiceTestConfig()); 

	    @Before
	    public void setUp()  {     
	    	
	    	helper.setUp();
	    	log("setUp");
	    }

	    @After
	    public void tearDown() {
	    	
	        // not strictly necessary to null these out but there's no harm either
	    	helper.tearDown();
	    	log("tearDown");

	    }
		public void log(String msg){
			System.out.println(msg);
		}
	}

