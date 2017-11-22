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