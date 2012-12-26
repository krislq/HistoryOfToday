package com.krislq.history;

import java.io.File;

import com.krislq.history.util.HistoryUtil;

public class Constants {

	public static 	 	String  APP_NAME				= "HistoryOfToday";
	public static  		int 	VERSION_CODE		= 0;
	public static 		String  VERSION_NAME		= null;
	public static 		String  PACKAGE_NAME		= null;
	

	/**
	 * true is  debug model.
	 */
	public static final boolean DEBUG 			= true;
	/**
	 * true will  persist the log
	 */
	public static final boolean PERSIST_LOG	= false;

	public static  		String		EXTERNAL_DIR 		= HistoryUtil.getExternalStoragePath()+File.separator+APP_NAME;
	public static  		String		CACHE_DIR 			= EXTERNAL_DIR+File.separator+"cache";
	public static  		String		LOG_DIR 			= EXTERNAL_DIR+File.separator+"log";
}
