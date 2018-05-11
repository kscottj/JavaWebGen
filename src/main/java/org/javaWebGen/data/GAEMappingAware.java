package org.javaWebGen.data;

import org.javaWebGen.data.bean.Author;

import com.google.appengine.api.datastore.Entity;

public interface GAEMappingAware {
	/*****************************************************
	*Map entity to JTO DataBean
	*@param entity GAE entity aka table
	*@return  bean data bean that maps to entity
	******************************************************/
	public FormBeanAware mapRow(Entity entity) ;
	/*************************************************************
	*map entity to JTO DataBean
	*@param entity GAE entity aka table
	*@param bean data bound bean with data
	**************************************************************/	
	public void mapEntity(Entity entity,FormBeanAware bean) ;
}
