/*
 * =================================================================== *
 * Copyright (c) 2003 Kevin Scott All rights  reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in
 * the documentation and/or other materials provided with the
 * distribution.
 *
 * 3. The end-user documentation included with the redistribution,
 * if any, must include the following acknowledgment:
 * "This product includes software developed by "Kevin Scott"
 * Alternately, this acknowledgment may appear in the software itself,
 * if and wherever such third-party acknowledgments normally appear.
 *
 * 4. The name "Kevin Scott must not be used to endorse or promote products
 * derived from this software without prior written permission. For
 * written permission, please contact kevscott_tx@yahoo.com
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL KEVIN SCOTT BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 */

package org.javaWebGen.util;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*******************************************************************************
 * Generic class for handling  Object pooling
 * 
 * @deprecated use Apache commons pool
 * @author Kevin Scott
 * @version $Revision: 1.2 $
 ******************************************************************************/
@Deprecated
public abstract class ObjectPool {
	@SuppressWarnings("unused")
	private static final Logger log=LoggerFactory.getLogger(ObjectPool.class);
	//private long lastCheckOut;

	@SuppressWarnings("rawtypes")
	private Hashtable locked = new Hashtable(0);

	@SuppressWarnings("rawtypes")
	private Hashtable unlocked = new Hashtable(0);
	/*Pool Cleaner  */
	private Cleaner cleaner = null;
	private int maxPoolSize = 10;
	long expirationTime = 1000 * 30; // 30 sec
	
	/**
	 * 
	 */
	protected ObjectPool() {
		//lastCheckOut = System.currentTimeMillis();
		 startCleaner();

	}

	/***************************************************************************
	 * @param time in seconds to expire a object in the pool
	 **************************************************************************/
	protected ObjectPool(int time) {
		expirationTime = time * 1000;
		//lastCheckOut = System.currentTimeMillis();
		startCleaner(time);

	}
	public void startCleaner(){
		this.cleaner=new Cleaner(this);
		Thread cleanupThread= new Thread(this.cleaner);
		cleanupThread.setDaemon(true);	
		cleanupThread.start();
	}
	public void startCleaner(int time){
		cleaner.setSleeptime(time);
		Thread cleanupThread= new Thread(cleaner);
		cleanupThread.setDaemon(true);	
		cleanupThread.start();
	}
	/***************************************************************************
	 * @param milis time in mili seconds to expire a object in the pool
	 **************************************************************************/
	protected void setRecyleTime(long milis) {
		expirationTime = milis;
		cleaner.setSleeptime(milis);
	}

	/***************************************************************************
	 * @param size size of pool
	 **************************************************************************/
	protected void setPoolSize(int size) {
		maxPoolSize = size;
	}

	protected abstract Object create() throws Exception;

	/***************************************************************************
	 * validate the object is valid
	 * 
	 * @param o the object to check
	 **************************************************************************/
	protected abstract boolean validate(Object o);

	/***************************************************************************
	 * expire the object in the pool (remove it)
	 * 
	 * @param o the object to check
	 **************************************************************************/
	protected abstract void expire(Object o);

	/***************************************************************************
	 * get an object from the pool
	 * 
	 * @return object from pool
	 **************************************************************************/
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected synchronized Object checkOut() throws Exception {
		long now = System.currentTimeMillis();
		// int poolSize = 0;
		//lastCheckOut = now;
		Object o = null;

		if (unlocked.size() > 0) {

			Enumeration e = unlocked.keys();

			while (e.hasMoreElements()) {
				o = e.nextElement();
				if (validate(o)) {
					unlocked.remove(o);
					locked.put(o, new Long(now));
					return (o);
				} else {
					unlocked.remove(o);
					expire(o);
					o = null;
				}
			}
		}

		int poolSize = unlocked.size() + locked.size();

		// Util.debug("pool"+poolSize+">"+maxPoolSize+"|ulocked
		// s="+unlocked.size() );
		if (poolSize < maxPoolSize) {
			o = create();
		} else {
			Util.fatal("Maximum Pool Sized reached");
			return null;
		}

		locked.put(o, new Long(now));
		// Util.enter("Ckeck out of Pool ="+o+" pool
		// size="+locked.size()+unlocked.size() );
		return (o);
	}

	/***************************************************************************
	 * return an object from the pool
	 * 
	 * @param o object from pool
	 **************************************************************************/
	@SuppressWarnings("unchecked")
	protected synchronized void checkIn(Object o) {
		if (o != null) {
			locked.remove(o);
			unlocked.put(o, new Long(System.currentTimeMillis()));
			// Util.leave("Ckecin into Pool ="+o+" pool
			// size="+locked.size()+unlocked.size() );
		}
	}

	/***************************************************************************
	 * check to see if it can be expired
	 * 
	 **************************************************************************/

	@SuppressWarnings("rawtypes")
	protected synchronized void cleanUp() {

		Object o;

		long now = System.currentTimeMillis();

		Enumeration e = unlocked.keys();

		while (e.hasMoreElements()) {
			o = e.nextElement();

			if ((now - ((Long) unlocked.get(o)).longValue()) > expirationTime) {
				Util.trace("--cleandup " + o);
				unlocked.remove(o);
				expire(o);
				o = null;
			}
		}

		// System.gc();
	}
}

/*******************************************************************************
 * INNER CLASS cleans up expired object from the pool
 ******************************************************************************/
 class Cleaner implements Runnable{
	private ObjectPool pool;

	private long sleepTime = 30000L;
	private boolean running =true;
	private static final Logger log=LoggerFactory.getLogger(Cleaner.class);

	Cleaner(ObjectPool pool) {
		this.pool = pool;
		this.sleepTime = pool.expirationTime;
	}

	void setSleeptime(long time) {
		sleepTime = time;
	}
	
	synchronized void  clean() throws InterruptedException{
		wait(sleepTime);
		pool.cleanUp();
		//Util.debug("wake up cleanup thread.  DO I have work to do?");
	}

	public void run() {
		
		while (running) {
			//Util.enter(this+"run()");
			try {
				clean();
			} catch (InterruptedException e) {

				log.info("cleanup thread interupted");
			}
			//Util.leave(this+"run");
		}
	}
}