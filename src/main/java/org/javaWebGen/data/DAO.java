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

package org.javaWebGen.data;

import javax.sql.DataSource;


/*******************************************************************************
 * Base DAO(Data Access Object) object that handles running sql against the
 * database.  Should be replace with @see DaoAware interface.
 * 
 * @version $Revision: 1.2 $
 * @author Kevin Scott
 ******************************************************************************/
@Deprecated
public abstract class DAO implements DaoAware{
	public static final String DB_JNDI="webGen.db.jndi";
	private DataSource datasource=null;
		
	 
		protected DAO() {
		
	}
	/**
	 * set datasource 
	 * @return Datasource configuted for container
	 */
	public DataSource getDataSource(){
		return this.datasource;
		
	}
 /**
  * 
  * @param datasource that is configured already
  */
	public void setDataSource(DataSource datasource){
		this.datasource=datasource;
	}

	

}