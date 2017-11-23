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

/**********************************************
* Web Appliiction error
*
* @author Kevin Scott
* @version $Revision: 1.2 $
*************************************************/
public class WebAppException extends Exception {
	private static final long serialVersionUID = 1820335672029538691L;

  public static final int GENERIC =0;
  public static final int CONFIG_ERROR=1;
  public static final int APP_ERROR=2;
  public static final int DATA_ERROR=3;
  public static final int AUTH_ERROR=4;
  public static final int VALIDATE_ERROR=5;
  public static final int UPDATE_DID_NOTHING=6;
  public static final int Controller_ERROR =7;
  private int _code =0;
  private Throwable _exception =null;
/**
 * Error message
 */
public final static String[] messages={
	"Unknown APP Error=",
	"Unable to read configuration=",
	"Business Logic Error=",
    "Data Error=",
    "Authorization Error=",
	"Validate Error=",
    "Update did not update anything",
	"Error calling Web Controller"
	};



public WebAppException(){}

/**
 * 
 * @param msg
 */
public WebAppException(String msg){
	super(msg);

}

/**
 * 
 * @param code error code
 * @param e error
 */
public WebAppException(int code,Throwable e){
	super(messages[code]+"|"+e.getMessage(),e );
	_code=code;
	_exception=e;
}

/**
 * 
 * @param code error 
 * @param message
 */
public WebAppException(int code, String message){
	super(messages[code]+message);
	_code=code;
}
/**
 * 
 * @param code error code
 */
public WebAppException(int code){
	super(messages[code]);
	_code=code;
}
/**
 * 
 * @param code error code
 * @param msg message
 * @param e error
 */
public WebAppException(int code, String msg,Throwable e){
	super(messages[code]+"|"+msg+"|"+e.getMessage() );
	_code=code;
	_exception=e;
}

/**
 * 
 * @return error code 
 */
public int getCode(){
	return _code;
}

/**
 * 
 * @return original Excption if any
 */
public Throwable getException(){
	return _exception;
}


}