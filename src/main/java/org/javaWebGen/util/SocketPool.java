/*
 * =================================================================== *
 * Copyright (c) 2006 Kevin Scott All rights  reserved.
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

import java.net.*;
import java.io.*;

import org.javaWebGen.util.PropertiesReader;
import org.javaWebGen.util.Util;


/*************************************************
*Very Simple Pool of Socket network Connections
*NOTE: Expermintal IE untested
*@deprecated Use Java concurrent 
*@version $Revision: 1.2 $
*@author Kevin Scott
**************************************************/
@Deprecated 
public class SocketPool extends ObjectPool
{
    private String address;
    private int port;

    public final int MAX_RETRIES = 10;

/**
 * 
 * @param address
 * @param port
 */
    public SocketPool( String address, int port) {
        this.address = address;
        this.port = port;

    }

    /**
    *NOT USED YET
    *@param propReader reader
    */
    public SocketPool( PropertiesReader propReader){
    }


    /****************************************************
    *Creates a new socket connection
    *@return new socket
    *
    ******************************************************/
    protected Object create() throws IOException,SecurityException
    {
        Util.debug("Create NEW Socket"+ address+port);
        return( new Socket( address,port) );
    }

    /*********************************************
    *validate socket is still open
    *@param o Socket
    **********************************************/
    protected boolean validate( Object o ) {
        Util.debug("Validate socket="+o);
        if(o !=null){
	    	Socket socket = (Socket) o;
	    	
	    	return socket.isConnected();    	
        }
        return(  o!=null );
    }

    /************************************************
    *close socket connection
    *@param o socket
    *************************************************/
    protected void expire( Object o )
    {
        try{
            Util.leave("remove con " +o);
            ( ( Socket ) o ).close();
        }catch( IOException e ) {
             Util.error(e);
        }
    }

    /************************************************
    *get open socket connection
    *@return socket connection
    *************************************************/
    public Socket getSocket() throws IOException,SecurityException{
        Object o=null;
        try{
            o =super.checkOut();
            if(o==null){
                int count = 0;
                while (o==null){
                    try{
                        Thread.sleep(1000L);
                    }catch(InterruptedException e){}
                    o=super.checkOut();
                    count++;
                    if (count > MAX_RETRIES){
                        throw new IOException(" tried connecting"+count+" times");
                    }
                }
            }
        }catch(Exception e){
            if (e instanceof IOException){
                throw (IOException) e;
            }else if(e instanceof SecurityException){
                throw (SecurityException) e;
            }
            Util.error("UNKNOW ERROR="+e);
        }
        return (Socket) o;
    }

    /************************************************
    *return open socket connection to pool
    *@param socket connection
    *************************************************/
    public void close( Socket socket )
    {
        //Util.leave("recyle db con="+c);
        super.checkIn( socket );
    }

}

