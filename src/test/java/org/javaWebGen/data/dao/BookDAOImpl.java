/*
Copyright (c) 2015 Kevin Scott All rights  reserved.
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
 package org.javaWebGen.data.dao;

/** data Object talks to DB **/
package org.javaWebGen.data.dao;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import java.util.*;
import org.javaWebGen.data.bean.*;
import org.javaWebGen.exception.DBException;
import org.javaWebGen.data.JdoDao;
/******************************************************************************
* WARNING this class is generated by GenerateJDO v1_17 based on torque schema     
* This class should not be modified, but may be extended.
* This class will be regenerated if the database schema is changed. 
* Note, you should only use one primary key per data object
* <ul>
* <li>GAE only supports one Primary keys directly without custom code</li>
* <li>GAE only supports  Primary keys of Long or String</li>
* <li>this framework does not handle String Primary keys yet
</li>
* <li>this framework does not handle BLOBS yet</li>
</li>
* </ul>
* @author Kevin Scott                                                        
* @version $Revision: 1.00 $                                               
*******************************************************************************/
public abstract class BookDAOImpl extends JdoDao { 
//begin private Vars


//find by Primary Key

	/***************************************************
	*Warning Generated method. get a DataBean with table data in it
	*@param JDO persist 
	*@param primary key or id for entity 
	@return DataBean with data
	******************************************************/
	protected Book findByPrimaryKey(PersistenceManager pm,Long id) throws DBException{
		if (id==null){
			throw new DBException(DBException.DAO_ERROR,"Book id=null");
		}
		try{
			return pm.getObjectById(Book.class,id);
		}catch(Exception ex){
			throw new DBException(DBException.DAO_ERROR,ex);
		}
	} //find by primary key

	/**
	*Warning Generated method. Get a DataBean with table data in it
	*@param primary key or id for entity
	@return DataBean with data
	*/
	public Book findByPrimaryKey(Long id) throws DBException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Book entity=null;
		try{
			entity = findByPrimaryKey(pm,id);
		}catch(Exception ex){
			throw new DBException(DBException.DAO_ERROR,ex);
		}finally{
			pm.close();
		}
		return entity;
	}

//begin insert (create)

	/**
	*Warning Generated method inserts new DataBean into data store
	*@param DabaBean with entity data in it
	*@return id inserted into datastore
	*/
	public Long insert(Book entity) throws DBException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Long id=null;
		try{
			entity = pm.makePersistent(entity);
			id=entity.getBookId() ;
		}catch(Exception e){
			throw new DBException(DBException.DAO_ERROR,e);
		}finally{
			pm.close();
		}
		return id;
	} //end insert

//begin update(store)

	/**
	*Warning Generated method updates the data store with a DataBean 
	*@param DabaBean with entity data in it	*
	*/
	public void update(Book entity) throws DBException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			Book e=findByPrimaryKey(pm,entity.getBookId () );
			 // leave key field alone e.setBookId (entity.getBookId () );
			e.setTitle (entity.getTitle () );
			e.setIsbn (entity.getIsbn () );
			e.setPublisherId (entity.getPublisherId () );
			e.setAuthorId (entity.getAuthorId () );
			e.setCreateDate (entity.getCreateDate () );
			pm.makePersistent(e);
		}catch(Exception e){
			throw new DBException(DBException.DAO_ERROR,e);
		}finally{
			pm.close();
		}
	} //end update by primary key

//begin delete

	/**
	*Warning Generated method updates the database with a DataBean 
	*@param DabaBean with entity data in it
	*/
	public void delete(Book entity) throws DBException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try{
			Book e=findByPrimaryKey(pm,entity.getBookId()  );
			pm.deletePersistent(e);
		}catch(Exception e){
			throw new DBException(DBException.DAO_ERROR,e);
		}finally{
			pm.close();
		}
	} // delete by primary key

//begin find All 

	/**
	*Warning Generated method. get a DataBean with table data in it
	*@param data bound Bean
	*@return list of all DataBean in data store
	*/
	@SuppressWarnings("unchecked")
	public List<Book> findAll() throws DBException{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Book> results=null;
		try{
			Query query =pm.newQuery(Book.class);
			results=(List<Book>) query.execute();
		}catch(Exception e){
			throw new DBException(DBException.DAO_ERROR,e);
		}finally{
			pm.close();
		}
		return new ArrayList<Book>(results);
	} //findAll

}