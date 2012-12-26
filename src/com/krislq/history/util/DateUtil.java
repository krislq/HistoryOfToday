package com.krislq.history.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.text.TextUtils;
/**
 * 
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @date 2012-12-26
 * @version 1.0.0
 *
 */
public class DateUtil {
	public static final long 		HOURS_MILLIS 					= 60 * 60 * 1000;
	public static final long 		HALF_DAY_MILLIS 				= HOURS_MILLIS * 12;
	public static final long 		DAY_MILLIS 						= HALF_DAY_MILLIS * 2;
	public static final long 		WEEK_MILLIS 					= DAY_MILLIS * 7;
	public static final long 		MONTH_MILLIS 					= WEEK_MILLIS * 30;
	/**
	 * yyyyMMdd
	 */
	public static final String 		DATE_DEFAULT_FORMATE 			= "yyyyMMdd";
	/**
	 * yyyy-MM-dd HH:mm:ss
	 */
	public static final String 		DATE_FORMATE_ALL 				= "yyyy-MM-dd HH:mm:ss";
	public static final String 		DATE_FORMATE_RATE 				= "MM-dd HH:mm:ss";
	/**
	 * dd/MM/yyyy, hh:mm
	 */
	public static final String 		DATE_FORMATE_TRANSACTION		= "dd/MM/yyyy, hh:mm";
	/**
	 * MM/dd HH:mm
	 */
	public static final String 		DATE_FORMATE_DAY_HOUR_MINUTE 	= "MM/dd HH:mm";
	public static final String 		DATE_FORMATE_HISTORY 	= "MM-dd";
	/**
	 * HH:mm
	 */
	public static final String 		DATE_FORMATE_HOUR_MINUTE 		= "HH:mm";
	
	public static SimpleDateFormat	 dateFormate =new SimpleDateFormat();
	/**
	 * 
	 * @param milliseconds 
	 * @return the time formated by "yyyy-MM-dd HH:mm:ss"
	 */
	public static String toTime(long milliseconds){
		return toTime(new Date(milliseconds),null);
	}
	/**
	 * 
	 * @param milliseconds
	 * @param pattern
	 * @return the time formated
	 */
	public static String toTime(long milliseconds,String pattern){
		return toTime(new Date(milliseconds),pattern);
	}
	/**
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String toTime(Date date,String pattern){
		if(TextUtils.isEmpty(pattern))
		{
			pattern = DATE_FORMATE_ALL;
		}
		dateFormate.applyPattern(pattern);
		if(date ==null)
		{
			date = new Date();
		}
		return dateFormate.format(date);
	}
	/**
	 * 
	 * @param context 
	 * @param createTime the time to be formated
	 * @return like X seconds/minutes/hours/weeks ago ,
	 */
	public static String getTimeAgo(Context context,long createTime) {
		long curTime = System.currentTimeMillis();
		long between = (curTime - createTime) / 1000;
		
		String str = "";
		
		long day = between / (24 * 3600);
		long hour = between % (24 * 3600) / 3600;
		long minute = between % 3600 / 60;
		if (day >= 1) {
			if (day > 7) {
				str = 1 + " weeks ago";
			} else {
				str = day + " days ago";
			}
		} else if (hour > 0) {
			str = hour + " hours ago";
		} else if(minute > 0 ){
			str = ((minute == 0) ? 1 : minute) + " mimutes ago";
		}
		else
		{
			str = between + " seconds ago";
		}
		
		return str;
	}
	
}
