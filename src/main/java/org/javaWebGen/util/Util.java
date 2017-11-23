/*
 * =================================================================== *
 * Copyright (c) 2003 Kevin Scott All rights  reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 * if any, must include the following acknowledgment:
 * "This product includes software developed by "Kevin Scott"
 * Alternately, this acknowledgment may appear in the software itself,
 * if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "Kevin Scott must not be used to endorse or promote products
 * derived from this software without prior written permission. For
 * written permission, please contact kevscott_tx@yahoo.com
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL KEVIN SCOTT BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

package org.javaWebGen.util;

import java.io.*;

import org.javaWebGen.config.ConfigConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



 /**
  * Misc static methods that needed a home somewhere
  * @deprecated do not use this class it has been split up into multiple more focused classes and is an example of how not to use a logger.
  * @author Kevin Scott
 */
@Deprecated
public abstract class Util{

 
    public static int SQL_DUMP=9;
    public static int TRACE=8;
    public static int DEBUG=7;
    public static int INFO=6;
    public static int WARN=5;
    public static int ERROR=4;
  
    private static boolean dumpSQL = true;
    private static final Logger logger = LoggerFactory.getLogger(ConfigConst.APP_LOG);   


    /*******************************
    * get a stackTrace of an exception
    *@param t error obj
    *@return stackTrace as String
    ********************************/
    public static String getStackTrace(Throwable t){

        StringWriter out = new StringWriter();
        PrintWriter writer = new PrintWriter(out);
        t.printStackTrace(writer) ;
        return out.toString();
    }

    /*********************************
    * write debug message to log file
    ********************************/
    public static void debug(Object msg){
        if(msg!=null){
            logger.debug(msg.toString() );
        }else{
            logger.debug("[NULL]");
        }
    }

    /***********************************
    * write an error message to log file
    *************************************/
    public static void error(Object msg){

        if(msg!=null){
        	logger.debug(msg.toString() );
        }else{
            logger.debug("[NULL]");
        }

    }

    /***********************************
    * write an error message to log file
    *************************************/
    public static void error(Object msg, Throwable e){

        if(msg!=null && e!=null){
        	logger.error(msg.toString(),e);
        }else{
            logger.error("[NULL]",e);
        }

    }

    /***********************************
    * write an error message to log file
    *************************************/
    public static void error(Throwable e){
        if( e!=null){
        	logger.error("",e);
        }else{
            logger.error("[NULL]");

        }


    }

    /***********************************
    * write an information message to log 
    *************************************/
    public static void info(Object msg){

        if(msg!=null ){
        	logger.info(msg.toString() );
        }else{
            logger.info("[NULL]");
        }
    }

    /*******************************************
    * write an information message to log
    ****************************************/
    public static void warn(Object msg){
    
        if(msg!=null ){
        	logger.warn(msg.toString() );
        }else{
            logger.warn("[NULL]");    
        }

    }
    public static void trace(Object msg){
        if(msg!=null ){
        	logger.trace(msg.toString() );
        }else{
            logger.trace("[NULL]");    
        }    	
    }
    /**
     * 
     * @param msg
     */
    public static void fatal(Object msg) {
        if(msg!=null ){
        	logger.error("FATAL: "+msg.toString() );
        }else{
            logger.error("FATAL: [NULL]");    
        }    	
	}
	public static void fatal(Object msg, Exception e) {
        if(msg!=null ){
        	logger.error("FATAL: "+msg.toString(),e );
        }else{
            logger.error("FATAL: [NULL]",e);    
        } 
	}
    /*************************************************
    *write out the real sql statement to the log 
    *@param sql statement for prepared statement
 
    *
    **************************************************/
    public static void sqlDump(String sql){
        if(sql!=null  && dumpSQL){
        	logger.trace("SQL|"+sql.toString() );
        }else{
            logger.trace("SQL| NULL");

        }
    }
    /*************************************************
    *write out the real sql statement to the log 
    *@param sql statement for prepared statement
     * @param rows 
    *
    **************************************************/
    public static void sqlDump(String sql, long rows){
        if(sql!=null  && dumpSQL){
        	logger.trace("SQL|"+sql.toString() );
        }else{
            logger.trace("SQL| NULL");

        }
    }

    /*************************************************
    *write out sql statement to log
    *@param sql statement for prepared statement
    *@param args list of parms for prepared statement
    **************************************************/
    public static void sqlDump(String sql, Object[] args){
        if(dumpSQL){
            sqlDump(" | "+" | "+getSQL(sql,args), 1L );
        }
    }

    /*************************************************
    *write out actually sql statement to log
    *@param sql statement for prepared statement
    *@param rows number of rows
    **************************************************/
    public static void sqlDump(String sql, int rows){
        if(dumpSQL){
            sqlDump(" | "+rows+" | "+sql,rows );
        }
    }

    /*************************************************
    *write out actually sql statement to log file
    *@param sql statement for prepared statement
    *@param rows changed
    *@param time  
    **************************************************/
    public static void sqlDump(String sql, long rows, int time){
        if(dumpSQL){
            sqlDump(time+" | "+rows+" | "+sql,rows);
        }
    }

    /*************************************************
    *write out actually sql statement to log file
    *@param sql statement for prepared statement
    *@param parms list of parms for prepared statement
    *@param rows effected
    **************************************************/
    public static void sqlDump(String sql, Object[] parms, long rows){
        if(dumpSQL){
            sqlDump(" | "+rows+" | "+getSQL(sql,parms),rows );
        }
    }

    /*************************************************
    *write out actually sql statement to log file
    *@param sql statement for prepared statement
    *@param parms of parms for prepared statement
    *@param rows affected
    *@param time 
    **************************************************/
    public static void sqlDump(String sql, Object[] parms, long rows, int time){

        if(dumpSQL){
            sqlDump(time+"| "+rows+" | "+getSQL(sql,parms),rows );
        }
    }

    /*************************************************
    *generate actual SQL to be used by a prepared statement
    *@param sql statement for prepared statement
    *@param parms list of parms for prepared statement
    **************************************************/
    public static String getSQL(String sql, Object[] parms){
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
    * write a trace statement when entering a method
    */
    public static void enter(String method){
            debug(">>>>>"+method);
    }

    /**
    *write a trace statement when leavening a method
    */
    public static void leave(String method){
            debug("<<<<<"+method);
    }
    /**
    *determine if SQL statements are sent to logger
    */
    public static void logSQL(boolean showSQL){
        dumpSQL = showSQL;
    }



}
