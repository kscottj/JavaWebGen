/*
Copyright (c) 2012-2013 Kevin Scott All rights  reserved. Permission is hereby granted, free of charge, to any person obtaining a copy of 
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
package org.javaWebGen.data.dao;

/******************************************************************************
* WARNING this class is generated by GenerateSpringDAO v0_12 based on Database schema     
* This class should not be modified, but may be extended.
  This class will be regenerated if the database schema is changed. 
* @author Kevin Scott                                                        
* @version $Revision: 1.00 $                                               
*******************************************************************************/
public  class DaoFactory{ 
//begin private Vars
private BookDAO bookDao=null;
private InventoryDAO inventoryDao=null;
private LocationDAO locationDao=null;

//begin getters and setters
/** DataBase Access Object
*@return dao set by Spring Context*/
public BookDAO getBookDao(){
    return bookDao;
}
/**Spring Contest will set these at init time
*@Param dao set by Spring Context*/
public void setBookDao(BookDAO BookDao){
    this.bookDao = BookDao;
}
/** DataBase Access Object
*@return dao set by Spring Context*/
public InventoryDAO getInventoryDao(){
    return inventoryDao;
}
/**Spring Contest will set these at init time
*@Param dao set by Spring Context*/
public void setInventoryDao(InventoryDAO InventoryDao){
    this.inventoryDao = InventoryDao;
}
/** DataBase Access Object
*@return dao set by Spring Context*/
public LocationDAO getLocationDao(){
    return locationDao;
}
/**Spring Contest will set these at init time
*@Param dao set by Spring Context*/
public void setLocationDao(LocationDAO LocationDao){
    this.locationDao = LocationDao;
}

}
