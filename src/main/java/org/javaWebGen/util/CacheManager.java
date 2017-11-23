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
package org.javaWebGen.util;

import org.javaWebGen.config.ConfigConst;
import org.javaWebGen.config.DBConst;
import org.javaWebGen.util.PropertiesReader;

import java.util.*;
import java.net.*;
import java.io.*;

/*******************************************************************************
 * Experimental and not well tested.  Be very careful if you turn on
 * cluster mode!
 * Class provides a Simple Object cache. For keeping objects for reuse in memory
 * instead of always creating a new object. Each object type is kept in their own
 * repository generally by class name. EXAMPLE.
 * 
 * <code>
 *  CacheManager cache = CacheManager.getInstance().add("1", Integer.class, New Integer(1) );
 * </code>
 * the object will be stored in the reposition for object of class Integer with
 * 1 as it's key
 * 
 * @version $Revision: 1.2 $
 ******************************************************************************/
//TODO convert to apache JCS
@SuppressWarnings("unchecked")
@Deprecated
public class CacheManager {

	//private PropertiesReader reader = null;

	/** default time that an object will live in the cache * */
	public static final long DEFAULT_EXPIRE_TIME = 1000 * 60 ; //3 min

	/** used to build UDP DataPackets * */
	public static final char DELIM = '|';

	private long expirationTime = DEFAULT_EXPIRE_TIME;

	//private long lastCheckOut;

	/** stores objects of a common type(Class) * */
	@SuppressWarnings("rawtypes")
	private Map repository = Collections.synchronizedMap(new HashMap());

	private int maxObjects = 1000;

	private static CacheManager cmanager = null;

	//private boolean running = true;

	private boolean isCluster = false;

	/** UDP socket for cache updates * */
	private MulticastSocket broadcastSock = null;

	private int broadCastPort = 5555;

	private InetAddress broadCastAddress = null;

	/** cleans up unused cached objects * */
	private CacheListener updater = null;

	/** Listens for updates to cached objects across a cluster* */
	private CacheReaper cleaner;

	/*
	 * @param reader
	 */
	private CacheManager(PropertiesReader reader) {
		String temp = null;

		maxObjects = Integer.parseInt(reader.getProperty(ConfigConst.CACHE_SIZE,"1000") );
		expirationTime = Long.parseLong(reader.getProperty(ConfigConst.CACHE_TIMEOUT,DEFAULT_EXPIRE_TIME+"") );
		cleaner = new CacheReaper(this, expirationTime);
		cleaner.setDaemon(true);
		cleaner.start();

		temp = reader.getProperty(ConfigConst.CACHE_CLUSTER,"false");
			Boolean test = new Boolean(temp);
			isCluster = test.booleanValue();
			if (isCluster) {
				Util.info(this + " CACHE is running in cluster mode");
				try {
					//init multcast socket
					temp = reader.getProperty(ConfigConst.CACHE_ADDRESS);
					if (temp != null) {
						broadCastAddress = InetAddress.getByName(temp
								.toString());
						Util.debug("broadCastAddress" + broadCastAddress);
					} else {
						return;
					}
					temp=reader.getProperty(ConfigConst.CACHE_PORT,broadCastPort+"") ;
					broadCastPort = Integer.parseInt(temp.toString());
					Util.debug("broadCastPort" + broadCastPort);
					broadcastSock = new MulticastSocket(broadCastPort);

					Util.debug("multcast sock=" + broadcastSock.toString());
					updater = new CacheListener(broadcastSock,
							broadCastAddress, this);
					Util.debug("CACHE|Start cache listener");
					updater.start();
				} catch (IOException ioe) {
					Util.error("IO ERROR opening Cache Broadcast Port "
							+ ioe.getMessage());
				}//end try

			}

		Util.info(" CACHE|Cache Manager is ready...");
	}

	/**
	 *  
	 */
	private CacheManager() {
		expirationTime = DEFAULT_EXPIRE_TIME;
		cleaner = new CacheReaper(this, expirationTime);
		cleaner.start();
	}

	/***************************************************************************
	 * 
	 * @param time in seconds to expire a object in the cache
	 **************************************************************************/
	private CacheManager(int time) {
		expirationTime = time * 1000;
		//lastCheckOut = System.currentTimeMillis();
		cleaner = new CacheReaper(this, expirationTime);
		cleaner.start();
	}

	/***************************************************************************
	 * Get a configured CacheManager instance configured with a properties file
	 * 
	 * @return the current instance of the Cache Manager
	 **************************************************************************/
	public synchronized static CacheManager getInstance() {
		try {
			if (cmanager == null) {
				PropertiesReader prop = PropertiesReader.getReader(ConfigConst.CONFIG_FILE);
				cmanager = new CacheManager(prop);
			}
		} catch (NumberFormatException e) {
			cmanager = new CacheManager();
			Util.error("Counld not find cache config item in "
					+ DBConst.DB_CONFIG_FILE);
		}
		return cmanager;
	}

	/***************************************************************************
	 * 
	 * @return a new CacheMananager
	 *  
	 **************************************************************************/
	public static CacheManager getCacheManager() {
		return new CacheManager();
	}

	/***************************************************************************
	 * returns a new CacheMananager
	 * 
	 * @param time expire time
	 * @return a new CacheMananager
	 **************************************************************************/
	public static CacheManager getCacheManager(int time) {
		return new CacheManager(time);
	}

	/***************************************************************************
	 * remove a Object from the cache
	 * 
	 * @param key 
	 * @param type class being removed from the cache
	 *  
	 **************************************************************************/

	@SuppressWarnings("rawtypes")
	protected synchronized void remove(Object key, Class type) {
		remove(key,type.getName());
	}

	/***************************************************************************
	 * removes an Object from the cache. NOTE used by CacheListener to remove
	 * objects.
	 * 
	 * @see CacheListener
	 * @param key name of the class type being removed from the 
	 * @param type name of the class type being removed from the 
	 * cache(IE result of Class.getName() )
	 *  
	 **************************************************************************/

	@SuppressWarnings("rawtypes")
	protected synchronized void remove(Object key, String type) {

		Object temp = repository.get(type);
		Util.debug("repos = " + temp);
		if (temp != null) {
			Map cache = (Map) temp;
			cache.remove(key);
		}
		Util.trace("CACHE|Remove key=" + key + "type=" + type);
	}

	/***************************************************************************
	 * The number of total Object(s) in Cache
	 * 
	 * @return number of Object Cached
	 **************************************************************************/
	@SuppressWarnings("rawtypes")
	public int getSize() {
		Collection c = repository.values();
		Iterator ri = c.iterator();
		int size = 0;

		while (ri.hasNext()) {
			Map cache = (Map) ri.next();
			size += cache.size();
		}
		return size;
	}

	/***************************************************************************
	 * Add an Object to the cache. objects are stored internally in a
	 * repository(HashMap) by Class type this allows the cache to cache multiple
	 * types of objects(classes)
	 * 
	 * @param key name
	 * @param ctype object type(class) being added
	 * @param o object being added
	 **************************************************************************/
	@SuppressWarnings("rawtypes")
	public synchronized void add(Object key, Class ctype, Object o) {
		if (getSize() < maxObjects) {
			Map cache = null;
			String type = ctype.getName();

			Object temp = repository.get(type);
			if (temp == null) {
				cache = Collections.synchronizedMap(new HashMap());
				repository.put(type, cache);
				Util.trace("add object "+o+" to repository type="+type);
			} else {
				cache = (Map) temp;
			}
			CacheRec rec = new CacheRec(o);
			cache.put(key, rec);
			//Util.debug("CACHE|adding object " + o + " to cache");
			;
		}
	}

	/***************************************************************************
	 * get an object from the cache returns null if empty
	 * 
	 * @param key name
	 * @param type class type 
	 * @return an object from the cache null if empty
	 **************************************************************************/
	@SuppressWarnings("rawtypes")
	public Object get(Object key, Class type) {
		Map cache = null;
		Object temp = repository.get(type.getName());
		//Util.debug("found repository "+type+"="+temp);
		if (temp == null) { //new repository
			return null;
		} else {
			cache = (Map) temp;
		}
		CacheRec rec = null;
		temp = cache.get(key);
		if (temp != null) {
			rec = (CacheRec) temp;
			Util.trace("CACHE|Object " + rec.object
					+ " was returned from the CACHE");
			rec.updateTime();
			return rec.object;
		} else {
			return null;
		}
	}

	/***************************************************************************
	 * used to broadcast changes tin cluster mode
	 * 
	 * @param key to store object under
	 * @param ctype class of object to store
	 **************************************************************************/

	@SuppressWarnings("rawtypes")
	private void broadcast(Object key, Class ctype) {

		if (broadcastSock != null) {
			Util.debug("boadcast change" + broadcastSock);
			String type = ctype.getName();
			DatagramPacket packet = null;
			try {
				String temp = key.toString() + DELIM + type;
				Util.debug("data buffer = " + temp);
				byte[] buffer = temp.getBytes();
				packet = new DatagramPacket(buffer, buffer.length,
						broadCastAddress, broadCastPort);
				broadcastSock.joinGroup(broadCastAddress);
				broadcastSock.send(packet);
			} catch (java.io.IOException ioe) {
				Util.error("IO Error broadcasting cache cache=" + ioe);
			} catch (SecurityException see) {
				Util.error("Security Error broadcasting cache change=" + see);
			}
		}
	}

	/***************************************************************************
	 * is this object cached?
	 * 
	 * @param key
	 * @param type class of object
	 **************************************************************************/
	@SuppressWarnings("rawtypes")
	public boolean isCached(Object key, Class type) {

		//Map cache = null;
		Object temp = get(key, type);
		if (temp == null) {
			return false;
		} else {
			return true;
		}
	}

	/***************************************************************************
	 * return an object from the pool
	 * 
	 * @param key to store object under
	 * @param type class of object being stored
	 * @param o object from pool
	 **************************************************************************/
	@SuppressWarnings("rawtypes")
	public synchronized void change(Object key, Class type, Object o) {
		delete(key, type);
		if (!isCluster) {
			add(key, type, o);
		}
	}

	/***************************************************************************
	 * removes an object from cache
	 * 
	 * @param key of object
	 * @param type of object
	 **************************************************************************/
	@SuppressWarnings("rawtypes")
	public synchronized void delete(Object key, Class type) {
		// remove(key,type);
		if (isCluster) {
			broadcast(key, type);
			//remove(key,type); //make sure local copy get purged fast
		} else {
			remove(key, type);
		}
	}

	/***************************************************************************
	 * removes all object from the cache
	 **************************************************************************/
	public synchronized void clear() {
		repository.clear();
	}

	/***************************************************************************
	 * check to see if any objects need to be removed
	 **************************************************************************/
	@SuppressWarnings("rawtypes")
	protected synchronized void cleanUp() {
		//Object o;

		long now = System.currentTimeMillis();
		Collection cr = repository.values();
		Iterator ri = cr.iterator();
		int size = getSize();
		if (size > 0) { //only clean up if somthing is in the cache
			Util.debug("CACHE|Running Cache Reaper on " + getSize()+ " object(s)");
			while (ri.hasNext()) {
				Object temp = ri.next();
				Map cache = (Map) temp;
				Set cset = cache.keySet();
				if (cset.isEmpty()) {
					Util.debug("remove empty rep");
					repository.remove(cache);
				}//end if
				Iterator ci = cset.iterator();
				while (ci.hasNext()) {
					Object key = ci.next();
					CacheRec rec = (CacheRec) cache.get(key);
					long dtime = now - expirationTime;
					//Util.debug("time check "+rec.time+ "<"+dtime );
					if (rec.time < dtime) {
						//Util.debug("CACHE|remove object key=" + key + "obj="+ rec.object);
						ci.remove();
					}
				} //end while

			}//end while
		}//end if anything in cache
	} //end cleanUp

	/***************************************************************************
	 * this method simply notifies the background thread to stop running
	 **************************************************************************/
	protected void finalize() throws Throwable {
		super.finalize();
		//ok allow all backround threads a chance to stop nicely
		Util.info("CACHE|Cache Manager is cleaning up..");
		updater.notify();
		cleaner.notify();
		if (broadcastSock != null) {
			try {
				broadcastSock.close();
			} catch (Exception e) {
				System.err.println("unable to close UDP socket!!!!");
			}
		}
	}

	/***************************************************************************
	 * INNER CLASS CACHE record
	 **************************************************************************/
	final class CacheRec {
		Object object = null;

		long time = 0;

		/***********************************************************************
		 * 
		 * 
		 * @param o Object to place in cache
		 **********************************************************************/
		CacheRec(Object o) {
			this.object = o;
			this.time = System.currentTimeMillis();
		}

		/**************************
		 *update time stamp
		 *****************************/
		void updateTime() {
			time = System.currentTimeMillis();
		}
	} //end inner class
}

