package org.javaWebGen.data;
/**
 * DAO access object
 * @author kevin
 *
 */
public interface DaoAware {
	public static final int TOO_MANY_ROWS=1000;  //row limit warning threshold
	public static final String TOO_MANY_ROWS_WARNING=
		"Query returned too many rows.  Keep in mind this is all loaded into memeory"; 


}
