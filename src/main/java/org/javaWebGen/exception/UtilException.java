/*
Copyright (c) 203-2006 Kevin Scott

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
* utility Exception
*
* @author Kevin Scott
* @version $Revision: 1.2 $
*************************************************/
public class UtilException extends Exception{

/**
	 * 
	 */
	private static final long serialVersionUID = 6146516237433483544L;
	
public static final int GENERIC =0;
public static final int OBJECT_POOL =1;
public static final int CACHE_MANAGER =2;
public static final int PROPREADER =3;
public static final int STRING_REPLACE =4;
public static final int CODE_GENERATOR_INIT =5;
public static final int CODE_GENERATOR_EXEC =6;

public static String[] messages={
	"Unknown Util Error",
	"Object Pool Error=",
	"Cache Manager Error=",
	"PropertiesReader Error=",
        "Tag not found in text=",
        "Error in CodeGenerator init",
        "Error in CodeGenerator exec"};

public UtilException(){}

public UtilException(String msg){
	super(msg);

}

public UtilException(int code,Exception e){
	super(messages[code]+"|",e );
}

public UtilException(int code, String msg){
	super(messages[code]+msg);
}


public UtilException(int code){
	super(messages[code]);
}

public UtilException(int code, String msg,Exception e){
	super(messages[code]+"|"+msg+"|",e );
}

}


