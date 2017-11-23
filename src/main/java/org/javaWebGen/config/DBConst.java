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


package org.javaWebGen.config;


/**
 * DataBase Constants used to set up persistence
 *
 * @author Kevin Scott
 * @version $Revision: 1.1.1.1 $
 */
@Deprecated
public interface DBConst {

	//JDBC info
	public final static String JDBC_DRIVER="webGen.db.jdbc.driver";
	public final static String JDBC_URL="webGen.db.jdbc.url";
	public final static String JDBC_USERID="webGen.db.jdbc.userId";
	public final static String JDBC_PASSWORD="webGen.db.jdbc.password";
	public final static String CONECTION_MAX="webGen.db.connection.max";
	public final static String CONECTION_WAIT="webGen.db.connection.wait";
	public final static String CONECTION_RETRIES="webGen.db.connection.retries";
	public final static String CONECTION_RECYCLE="webGen.db.connection.recycle";


	/** data manager to user **/
	public final static String DB_DATAMANAGER="webGen.db.jdbc.dataManager";
	public static final String CACHE_REFRESH_INTERVAL="webGen.db.cache.refreshInterval";
	public static final String USE_TRANSACTION="webGen.db.usTransaction";
	public static final String DB_CONFIG_FILE="db";
	public static final String DB_JNDI="webGen.db.jndi";
     /*  next value table name*/
	public static final String NEXT_VALUE_TABLE="id_gen";
	public static final String DAO_FACTORY = "DaoFactory";
	 
            


}
