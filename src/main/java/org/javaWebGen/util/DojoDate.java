package org.javaWebGen.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DojoDate extends Date {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2644414954679745506L;
	public static final String DOJO_DATE_FORMAT="yyyy-mm-dd";
	public DojoDate(Date date){
		super(date.getTime());
	}
	/**
	 * takes a date formated from the dojo js library 
	 * returns a
	 * @param dateStr formatted in yyyy-mm-dd
	 * @return DojoDate 
	 * @throws ParseException
	 */
	public static DojoDate parseDojoDate(String dateStr) throws ParseException{
		SimpleDateFormat  sdf=new SimpleDateFormat(DOJO_DATE_FORMAT);
		Date date = sdf.parse(dateStr);
		DojoDate dojoDate = new DojoDate(date);
		return dojoDate;
	}
	/**
	 * takes a date formated from the dojo js library 
	 * returns a
	 * @param dateStr formatted in yyyy-mm-dd
	 * @return DojoDate 
	 * @throws ParseException
	 */
	public static DojoDate parseDojoTime(String dateStr) throws ParseException{
		SimpleDateFormat  sdf=new SimpleDateFormat(DOJO_DATE_FORMAT);
		Date date = sdf.parse(dateStr);
		DojoDate dojoDate = new DojoDate(date);
		return dojoDate;
	}

	/**
	 * returns this date formated in as an ISO date
	 * foramt used by the Dojo js library IE yyyy-mm-dd
	 */
	public String toString(){
		SimpleDateFormat  sdf=new SimpleDateFormat(DOJO_DATE_FORMAT);
		return sdf.format(this);
	}
}
