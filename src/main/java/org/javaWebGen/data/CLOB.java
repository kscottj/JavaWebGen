package org.javaWebGen.data;

import java.io.Serializable;

/**
 * Used to store large sting object to backing store
 * GAE limits a normal string to 500 chars.  Serializing the obj should allow String up to the datastore limit of 1 meg
 * @author scotkevi
 *
 */
public class CLOB implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8580301593241010596L;
	public String text=null;

}
