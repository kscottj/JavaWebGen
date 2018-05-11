/*
 Copyright (c) 2012-2018 Kevin Scott All rights  reserved.
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
 */ 
/** data Acees Object talks to DB **/
package org.javaWebGen.model;
import java.util.*;
import org.javaWebGen.data.bean.*;
import org.javaWebGen.*;
import org.javaWebGen.exception.WebAppException;
import org.javaWebGen.data.dao.*
;import org.javaWebGen.JavaWebGenContext
;import org.springframework.stereotype.Service;
/******************************************************************************
* WARNING this class is generated by GenerateSpringModel v1_06 based on Database schema     
* This class should not be modified, but may be extended to add required logic 
* It will be regenerated if the database schema changes 
*******************************************************************************/
@Service
public abstract class BookModelImpl implements BusinessModel { 
//begin private Vars

//find by Primary Key

	/**
	*Get a Databean with table data in it
	* @return databean with data
	*/
	public Book getById(Long id) throws WebAppException{
		try{
			Book bean=JavaWebGenContext.getDao().getBookDao().findByPrimaryKey(id);
			 return bean;
		}catch(Exception e){
			throw new WebAppException(WebAppException.APP_ERROR,e);
}
	} //end getById

	/***************************************************
	*Get a Databean with table data in it
	*method takes string parms and converts them the correct data type
	* @return databean with data
	******************************************************/
	public Book getByIdParm(String idparm) throws WebAppException{
		 try{
			Long id =new Long(idparm);
			return getById(id);
		}catch(Exception e){
			throw new WebAppException(WebAppException.GENERIC,e);
		}
	}

//begin insert (create)

	/***************************************************
	*Inserts new Databean 
	*@param bean populated from data store
	******************************************************/
	public Long create(Book bean) throws WebAppException{
		try{
			return JavaWebGenContext.getDao().getBookDao().insert(bean);
		}catch(Exception e){
			throw new WebAppException(WebAppException.APP_ERROR,e);
		}
	} //end create

//begin update(store)

	/***************************************************
	* Updates the database with a Databean 
	*@param bean populated with data from data store
	******************************************************/
	public void save(Book bean) throws WebAppException{
		try{
			JavaWebGenContext.getDao().getBookDao().update(bean);
		}catch(Exception e){
			throw new WebAppException(WebAppException.APP_ERROR,e);
		}
	} //end update

//begin delete(store)

	/***************************************************
	*Deletes record from the data store 
	*@param bean populated with data from data store
	******************************************************/
	public void remove(Book bean) throws WebAppException{
		try{
			JavaWebGenContext.getDao().getBookDao().delete(bean);
		}catch(Exception e){
			throw new WebAppException(WebAppException.APP_ERROR,e);
		}
	} //end delete

//begin listAll)
 
	/***************************************************
	* List of all records in a table 
	* @return array of DataBeans
	******************************************************/
	public List<Book> list() throws WebAppException{
		List<Book> dataBeans =null;
 		try{
		dataBeans = JavaWebGenContext.getDao().getBookDao().findAll();
		}catch(Exception e){
			throw new WebAppException(WebAppException.APP_ERROR,e);
		}
		return dataBeans;
	} //end list

}
