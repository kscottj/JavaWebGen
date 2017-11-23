package org.javaWebGen.util;

import java.text.ParseException;
import java.util.Date;

/**
 * Class converts values that the Dojo framework will understand
 * @author home
 *
 */
public class DojoTime extends Date {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2644414954679745506L;
	//private static SimpleDateFormat  sdf = new SimpleDateFormat("yyyy-mm-dd");
	public DojoTime(Date date){
		super(date.getTime());
	}
	/**
	 * takes a date formated from the dojo js library 
	 * returns a
	 * @param dateStr formatted in yyyy-mm-dd
	 * @return DojoDate format
	 * @throws ParseException error readin date
	 */
	public static DojoTime parseDojoDate(String dateStr) throws ParseException{
		Date date = StringUtil.convertToTime(dateStr);
		DojoTime dojoTime = new DojoTime(date);
		return dojoTime;
	}
	/**
	 * takes a date formated from the dojo js library 
	 * returns a
	 * @param dateStr formatted in yyyy-mm-dd
	 * @return DojoDate 
	 * @throws ParseException
	 */
	public static DojoTime parseDojoTime(String dateStr) throws ParseException{
		Date date = StringUtil.convertToTime(dateStr);
		DojoTime dojoTime = new DojoTime(date);
		return dojoTime;
	}

	/**
	 * returns this date formated in as an ISO date
	 * foramt used by the Dojo js library IE yyyy-mm-dd
	 */
	public String toString(){
		return StringUtil.convertTimeToISOString(this);
	}
}
