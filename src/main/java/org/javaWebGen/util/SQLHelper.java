package org.javaWebGen.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * helper methods for dealing with raw SQL
 * @author kevin
 *
 */
public class SQLHelper {
	 public final static Logger log = LoggerFactory.getLogger(SQLHelper.class);
		/**
	    *Generate the SQL like what a prepared statement would use.  Replaces ? with params passed in.
	    *@param sql statement for prepared statement
	    *@param parms array to substitute into  prepared statement
	    */
	    public static String getSQL(String sql, Object... parms){
	        StringBuffer buffer = null;
	        if(sql!=null && parms!=null && parms.length >=1 ){
	            buffer=new StringBuffer(sql);
	            for(int i=0;i< parms.length;i++){
	                int index = buffer.toString().indexOf("?");
	                if(index != -1 ||index+1 <= buffer.length()){
	                    if(parms[i] == null){
	                        buffer.replace(index,index+1,"NULL" ); //replace ?	with null
	                    }else{
	                        if(parms[i] instanceof java.lang.String){
	                                buffer.replace(index,index+1,"'"+parms[i].toString()+"'" ); //replace ?
	                        }else if(parms[i] instanceof java.util.Date){
	                                buffer.replace(index,index+1,"'"+parms[i].toString()+"'" ); //replace ?
	                        }else{
	                                buffer.replace(index,index+1,parms[i].toString() ); //replace ?
	                        }
	                    }
	                }else{ //parms do not match query
	                    buffer.append("|umatch parm="+parms[i]);
	                }
	            }
	        } //end if

	        if(buffer==null){
	            return "";
	        }else{
	            return buffer.toString();
	        }
	    }

	    /**
	     * log SQL like used by prepared statement
	     * @param sql
	     */
	    public static void dump(String sql){
	    	log.debug(getSQL(sql) );
	    }
	    /**
	     * log SQL like used by prepared statement
	     * @param sql
	     */
	    public static void dump(String sql, Object... parms){
	    	log.debug(getSQL(sql,parms) );
	    }
}
