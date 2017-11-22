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

import org.javaWebGen.config.DBConst;
import org.javaWebGen.data.DataManager;
import org.javaWebGen.exception.DBException;
import org.javaWebGen.util.PropertiesReader;
import org.javaWebGen.util.Util;

/*******************************************************************************
 * factory to create the correct DataManager and initialize it.
 * 
 * @see DataManager for more details on talking to a database
 * @version $Revision: 1.1.1.1 $
 *  
 ******************************************************************************/
@Deprecated
public class DataManagerFactory {

	private static DataManager manager = null;
	private static PropertiesReader reader = null;

	/***************************************************************************
	 * returns a data manager object based on a Properties file
	 * 
	 * @param prop name of property resource bundle
	 **************************************************************************/
 
	@SuppressWarnings({ "rawtypes" })
	public static synchronized DataManager getDataManager(PropertiesReader prop)
			throws DBException {
		Util.enter("getDataManager()"+prop);
		if (manager == null) {
			try {
				String classname = prop.getProperty(DBConst.DB_DATAMANAGER);
				Util.debug("mangerClass="+classname);
				Class cobj = Class.forName(classname);
				manager = (DataManager) cobj.newInstance();
				Util.debug("_manger="+manager);
				manager.setReader(prop);
				manager.init();
				Util.debug("manger_after init="+manager);
				GenericSequence seq = new GenericSequence(manager);
				manager.setSequence(seq);

			} catch (Exception ie) {
				Util.error("Unable to start Datamanager",ie);
				try{
					Util.warn("Using default MySQLDataManager");
					manager=new MySQLDataManager();
					manager.setReader(prop);
					manager.init();
					manager.setSequence(new GenericSequence(manager));
				}catch(Exception e){
				
					throw new DBException(DBException.DATAMANGER_ERROR, ie);
				}
			} catch (Throwable t) {
				Util.error(t);
				throw new DBException(DBException.DATAMANGER_ERROR, t);
				
			}
		}
		Util.leave("getDataManager="+manager);
		return manager;
	}
	 /**
	  * 
	  * @return data manager instance
	  * @throws DBException
	  */
		public static synchronized DataManager getDataManager() throws DBException {
			if(reader==null){
				reader=PropertiesReader.getReader(DBConst.DB_CONFIG_FILE);
			}
			return getDataManager(reader);
		 
	 }


}