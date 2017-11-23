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


