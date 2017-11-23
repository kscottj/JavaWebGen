/*
 * =================================================================== *
 * Copyright (c) 2017 Kevin Scott All rights  reserved.
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
package org.javaWebGen.exception;

import java.sql.*;

/**
 * Application Authentication Eroor
 * Action not allowed
 * @author Kevin Scott
 * @version $Revision: 1.2 $
 */
 public class WebAuthException extends WebAppException{

/**********************************************
* Web Authorization Error
*
* @author Kevin Scott
* @version $Revision: 1.2 $
*************************************************/
	private static final long serialVersionUID = -1575404432536179907L;
	
public static final int NONE =0;
public static final int GENERIC =1;
public static final int AUTH_ERROR =2;


public int code=0;

public static String[] messages={
	"none ",
	"Unknown Error ",
	"You are not Authorized for this action = "};
private Exception error=null;

public WebAuthException(){}

public WebAuthException(String msg){
	super(msg);

}

public WebAuthException(int code,SQLException e){
	super(messages[code]+"|"+e.getMessage() );
	error=e;
	this.code=code;
}

public WebAuthException(int code){
	super(messages[code]+"|");
	this.code=code;
}

public WebAuthException(int code, String msg){
	super(messages[code]+msg);
	this.code=code;
}

public WebAuthException(int code, String msg,Exception e){
	super(messages[code]+"|"+msg+"|"+e.getMessage() );
	this.code=code;
	error=e;
}

public WebAuthException(int code,Exception e){
	super(messages[code]+"|"+"|"+e.getMessage() );
	this.code=code;
}

public String getError(){
	if( error!=null){
		return error.toString();
	}else{
		return "";
	}
}

}


