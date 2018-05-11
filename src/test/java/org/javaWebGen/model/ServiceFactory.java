/*
Copyright (c) 2018 Kevin Scott All rights  reserved. Permission is hereby granted, free of charge, to any person obtaining a copy of 
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
 */ 
/** data Object talks to DB **/
package org.javaWebGen.model;

/******************************************************************************
* WARNING this class is generated by GenerateSpringModel v1_06 based on Database schema     
* This class should not be modified, but may be extended.
  This class will be regenerated if the database schema is changed. 
* @author Kevin Scott                                                        
* @version $Revision: 1.00 $                                               
*******************************************************************************/
public  class ServiceFactory{ 
//begin private Vars
private PublisherModel publisherModel=null;
private AuthorModel authorModel=null;
private BookModel bookModel=null;

//begin getters and setters
/** DataBase Access Object
*@return model set by Spring Context*/
public PublisherModel getPublisherModel(){
    return publisherModel;
}
/**Spring Context will set these at init time
*@Param model injected by Spring Context*/
public void setPublisherModel(PublisherModel PublisherModel){
    this.publisherModel = PublisherModel;
}
/** DataBase Access Object
*@return model set by Spring Context*/
public AuthorModel getAuthorModel(){
    return authorModel;
}
/**Spring Context will set these at init time
*@Param model injected by Spring Context*/
public void setAuthorModel(AuthorModel AuthorModel){
    this.authorModel = AuthorModel;
}
/** DataBase Access Object
*@return model set by Spring Context*/
public BookModel getBookModel(){
    return bookModel;
}
/**Spring Context will set these at init time
*@Param model injected by Spring Context*/
public void setBookModel(BookModel BookModel){
    this.bookModel = BookModel;
}

}
