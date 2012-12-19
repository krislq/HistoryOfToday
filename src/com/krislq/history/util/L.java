package com.krislq.history.util;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import android.util.Log;

import com.krislq.history.Constants;

/**
 * log util
 * @author <a href="mailto:kris@matchmovegames.com">Kris.lee</a>
 * @since 1.0.0 ä¸??11:09:00
 * @version 1.0.0
 */
public class L {
	private static final String TAG				= Constants.APP_NAME;
	/**
	 * log file name
	 */
	public static final String  PERSIST_PATH	= Constants.EXTERNAL_DIR + File.separator+ "log";
	
	public static void p(String log)
	{
		System.out.println(log);
	}
	
	
	public static void v(String text)
	{
		print(text, Log.VERBOSE);
	}
	
	public static void d(String text)
	{
		print(text, Log.DEBUG);
	}
	
	public static void i(String text)
	{
		print(text, Log.INFO);
	}
	
	public static void w(String text)
	{
		print(text, Log.WARN);
	}
	
	public static void e(String text)
	{
		print(text, Log.ERROR);
	}
	
	public static void e(String text,Throwable throwable)
	{
		print(text+"#message:"+throwable.getMessage(),Log.ERROR);
		StackTraceElement[] elements = throwable.getStackTrace();
		for(StackTraceElement e : elements)
		{
			print(e.toString(), Log.ERROR);
		}
	}
	
	private static synchronized void print(String text, int level)
	{
		if(HistoryUtil.isEmpty(text))
		{
			return;
		}
		switch (level) {
		case Log.VERBOSE:
			if(Constants.DEBUG)
			{
				Log.v(TAG, text);
			}
			break;
		case Log.DEBUG:
			if(Constants.DEBUG)
			{
				Log.d(TAG, text);
			}
			break;
		case Log.INFO:
			if(Constants.DEBUG)
			{
				Log.i(TAG, text);
			}
			break;
		case Log.WARN:
			Log.w(TAG, text);
			break;
		case Log.ERROR:
			Log.e(TAG, text);
			break;
		}
		if(Constants.PERSIST_LOG)
		{
			writeLog(text, level);
		}
	}
	/**
	 * write the log into the file
	 * @param text
	 * @param level
	 */
	private static synchronized void writeLog(String text, int level )
	{
		StringBuilder sb = new StringBuilder();
//		sb.append("["+DateUtil.toTime(System.currentTimeMillis())+"]");
		sb.append(" ");
		switch (level) {
		case Log.VERBOSE:
			sb.append("[VERBOSE]");
			break;
		case Log.DEBUG:
			sb.append("[DEBUG]");
			break;
		case Log.INFO:
			sb.append("[INFO]");
			break;
		case Log.WARN:
			sb.append("[WARN]");
			break;
		case Log.ERROR:
			sb.append("[ERROR]");
			break;
		}
		sb.append(" ");
		
		RandomAccessFile raf = null;
		try {
			String fileName = PERSIST_PATH+"_"+DateUtil.toTime(System.currentTimeMillis(), DateUtil.DATE_DEFAULT_FORMATE);
			File logFile = new File(fileName);
			if(!logFile.exists())
			{
				logFile.createNewFile();
			}
			raf = new RandomAccessFile(fileName, "rw");
			raf.seek(raf.length());
			raf.writeBytes(sb.toString()+text+"\n");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally
		{
			if(raf!=null)
			{
				try {
					raf.close();
				} catch (IOException e) {
				}
			}
		}
	}
	
}
