package com.krislq.history;

import java.io.File;

import com.krislq.history.util.HistoryUtil;
/**
 * 
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @date 2012-12-26
 * @version 1.0.0
 *
 */
public class Constants {

	public static 	 	String  APP_NAME				= "HistoryOfToday";
	public static  		int 	VERSION_CODE		= 0;
	public static 		String  VERSION_NAME		= null;
	public static 		String  PACKAGE_NAME		= null;
	

	public static  		String		EXTERNAL_DIR 		= HistoryUtil.getExternalStoragePath()+File.separator+APP_NAME;
	public static  		String		CACHE_DIR 			= EXTERNAL_DIR+File.separator+"cache";
	public static  		String		LOG_DIR 			= EXTERNAL_DIR+File.separator+"log";
	
	/**
	 * 
	 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
	 * @date 2012-12-26
	 * @version 1.0.0
	 *
	 */
	public static class AppConfig
	{
		/**
		 * the perfenrence file name
		 */
		public static final String PERFERENCE_NAME 	= Constants.APP_NAME;

		public static final String PERFEN_KEY_CHANGE_DATE		= "first_change_date";
	}
}
