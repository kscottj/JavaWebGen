package org.javaWebGen.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

import org.javaWebGen.config.DBConst;
import org.javaWebGen.exception.DBException;
import org.javaWebGen.util.PropertiesReader;
import org.javaWebGen.util.StringUtil;
import org.javaWebGen.util.Util;

/**
 * Handles talking to an HSQL embedded DataBase
 * 
 * @author Kevin Scott
 *
 */
@Deprecated
public class HSQLDataManager  implements DataManager{

	private  String driverName="org.hsqldb.jdbcDriver";


	public final String BIG_DECIMAL ="java.math.BigDecimal";
	public final String LONG ="java.lang.Long";
	public final String INT ="java.lang.Integer";
	public final String STRING="java.lang.String";
	public final String OBJECT="java.lang.Object";
	public final String DATE="java.sql.Date";
	public final String TIMESTAMP="java.sql.TimeStamp";

	private Sequence sequence = null;
	private Properties dbProp = null;
	private PropertiesReader reader = null;
	//private OldJDBCConnectionPool pool=null;
	/*HSQL default format I think need to check this */
	private final SimpleDateFormat hsSqlDF=new SimpleDateFormat("yyyy-MM-DD");


	private String url;
	private String user;
	private String passwd;

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
        		this.user = reader.getProperty(DBConst.JDBC_USERID);
        		this.passwd =reader.getProperty(DBConst.JDBC_PASSWORD);
        		Class.forName( driverName ).newInstance();
        		dbProp= new Properties();
        		dbProp.put("", "true");
                
              //  pool= new OldJDBCConnectionPool(reader,driverName);
            } catch(Exception e) {
                    Util.error("Driver init Error",e);
            }

            Util.leave(this+"init()");
	}

	/******************************************************
	 * 
	 *******************************************************/
	 @Override
	public Connection getConnection() throws SQLException{
		//Util.enter(this+".getConnection()" );
		Connection con =null;
   		try {
			//Class.forName(driverName);
			con = DriverManager.getConnection(url, user, passwd);
			//con=pool.getConnection();

		}catch (SQLException ex) {
			Util.error("Cannot connect to this database.",ex);
			throw ex;
		}
		//con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
		return con;
	}
	/******************************************************
	 *
	*******************************************************/
	 @Override
	public void close(Connection con){

		if(con !=null){
			try {
				//Util.leave(this+".close()"+con);
				con.close();
			} catch (SQLException e) {
				Util.error("unable to close DB",e);
			}
		}

	}

	/*********************************************
	 * 
	 * 
	 * 
	 **********************************************/
	 @Override
	public int getNextValue(String tablename) throws DBException{
		return sequence.getNextValue(tablename);
	}
	/*********************************************
	*
	***********************************************/
	 @Override
	public int getNextValue(Connection con, String tablename) throws SQLException{
		return sequence.getNextValue(con, tablename);
	}


	/*************************************************
	*
	**************************************************/
	 @Override
	public void startTransaction(Connection con) throws SQLException{
		//odbc can not do this c
		con.setAutoCommit(false);
		con.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

	}

	/*************************************************
	*commit transaction
	**************************************************/
	 @Override
	public void endTransaction(Connection con) throws SQLException{
		con.commit();
		con.setAutoCommit(true);

		//odbc can not do this con.commit();
	}

	/*************************************************
	*
	*
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
	    *hsql uses YYYY-MM-DD by default
	    **************************************************/
	 	@Override
	    public String formatDate(java.util.Date date) {
	        String sql =null;
	        if(date!=null){
	            //SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");
	            sql=hsSqlDF.format(date);
	        }
	        return sql;
	    } 
	    /*************************************************
	     *get a date object by parsing a string the databases native format
	     *useful for doing updates etc.. when not using databean objects
	     *hsql uses YYYY-MM-DD by default
	     **************************************************/
		 @Override
	     public java.util.Date getDate(String dateStr) throws ParseException {
	         
	         if(dateStr!=null){
	             //SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-DD");
	             return hsSqlDF.parse(dateStr);
	         }
	         return null;
	     }
    
	     /***************************************************************************
	 	 * get a String that is escaped (handles ')
	 	 * hssql likes \' not ''
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
