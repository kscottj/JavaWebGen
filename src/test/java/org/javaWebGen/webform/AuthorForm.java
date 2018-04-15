/*
 Copyright (c) 2014 Kevin Scott All rights  reserved.
 Permission is hereby granted, free of charge, to any person obtaining a copy of 
 this software and associated documentation files (the "Software"), to deal in 
 the Software without restriction, including without limitation the rights to 
 use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 of the Software, and to permit persons to whom the Software is furnished to do 
 so.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
 SOFTWARE.
 */ package org.javaWebGen.webform;
  
   
 import org.javaWebGen.form.*;
 import javax.servlet.http.HttpServletRequest;

/******************************************************************************
* This class is generated by GenerateWebForm v1_05 based on Database schema     
* This class <b>should</b> be modified.   This class will <b>NOT</b> get
* regenerated and is just generated as a place holder.
* @author Kevin Scott                                                        
* @version $Revision: 1.00 $                                               
*******************************************************************************/
public class AuthorForm extends CsrfForm{
 private static final long serialVersionUID = 453453453788212L;
 	/*form fields*/
   	private HtmlNumberField  authorId= new HtmlNumberField("authorId" ,false);
	private HtmlField  firstName= new HtmlTextField("firstName" ,false);
	private HtmlField  lastName= new HtmlTextField("lastName" ,false);
 
  /**constructor that builds form*/
 	public AuthorForm(HttpServletRequest req){
		super(req);
		this.addField(authorId);
		this.addField(firstName);
		this.addField(lastName);
	}
 
 	/** get form name */
	public String getWebFormName(){
 		return "AuthorForm";
 	}
 //getters
	public HtmlNumberField getAuthorId(){
		 return authorId;
	}
	public HtmlField getFirstName(){
		 return firstName;
	}
	public HtmlField getLastName(){
		 return lastName;
	}
}
 