/*
Copyright (c) 2012-2014 Kevin Scott All rights  reserved.
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
/* data Acees Object talks to DB */
package org.javaWebGen.admin;
import java.util.*;
import org.javaWebGen.WebController;
import org.javaWebGen.data.bean.*;
import org.javaWebGen.util.HtmlUtil;
import org.javaWebGen.util.StringUtil;
import org.javaWebGen.exception.*;
import org.javaWebGen.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
/******************************************************************************
* WARNING this class is generated by GenerateController v4_06 based on Database schema     
* This class should not be modified!  It can be regenerated by the code generator.* when the database schema is modifued
* If you need to change the this code you should override what you do not need.
*******************************************************************************/
@SuppressWarnings("unused") //StringUtil might be needed depending for some data fields
public abstract class LocationActionImpl extends WebController{ 
private static final Logger log=LoggerFactory.getLogger(LocationActionImpl.class);//begin private Vars
	
/** model for this object **/;
	private LocationModel model= null;

//find by Primary Key

	/***************************************************
	*Warning Generated method. get a DataBean with table data in it.
	* for one row in the database
	*@return DataBean with data from the Model
	******************************************************/
	protected Location getById(Long id) throws WebAppException{
        Location bean =getModel().getById(id);
        return bean;
    } //find by primary key

//begin insert (create)

	/***************************************************
	*Warning Generated method inserts new data based on the
	*values in a DataBean. 
	*
	*@param bean data Bound JavaBean
	*@see org.javaWebGen.data.DataBean
	******************************************************/
	protected void create(org.javaWebGen.data.bean.Location bean) throws WebAppException{
		getModel().create(bean);
	} //end create

//begin update(store)

	/***************************************************
	*Warning Generated method updates the database with values 	*in a DataBean 
	*@param bean data Bound  JavaBean
	*@see org.javaWebGen.data.DataBean
	******************************************************/
	protected void update(Location bean) throws WebAppException{
		getModel().save(bean);
	} //end create

//begin delete(store)

	/***************************************************
	*Warning Generated method updates the database with a Databean 
	*@param bean data bound JavaBean
	*@see org.javaWebGen.data.DataBean
	*
	******************************************************/
	protected void delete(Location bean) throws WebAppException{
		getModel().remove(bean);
	} //end delete

//begin listAll)
//
	/***************************************************
	*Warning Generated method list all rows in table 
	*@return list of databeans
	******************************************************/
	protected List <Location> list() throws WebAppException{
		return getModel().list();
	} //end list

//begin getDataBean
	/************************************
	*fills in a databean based on data in a request
	************************************/
	protected static Location getDataBean(HttpServletRequest req) throws WebAppException{
			Location dataBean=new Location();
		try{
			dataBean.setLocationId(HtmlUtil.stripTags(req.getParameter("locationId") ) );
			dataBean.setCreateDate(StringUtil.convertToTime(req.getParameter("createDate") ) );
			dataBean.setUpdateDate(StringUtil.convertToTime(req.getParameter("updateDate") ) );
			dataBean.setUpdateBy(HtmlUtil.stripTags(req.getParameter("updateBy") ) );
			dataBean.setComment(HtmlUtil.stripTags(req.getParameter("comment") ) );
		}catch(Exception e){
			throw new WebAppException(WebAppException.APP_ERROR,e);
}		return dataBean;
	}//end getDataBean


	/************************************
	*Generated method
	*get the correct model class
	*@return model class
	************************************/
	protected synchronized LocationModel getModel() throws WebAppException{
		if (model==null){
			if(model==null){
				model = new LocationModel();
			}
		}
		return model;
	}//end getModel


 //end getModel
}//end impl class
