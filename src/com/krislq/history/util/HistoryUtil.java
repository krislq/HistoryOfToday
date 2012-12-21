package com.krislq.history.util;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.krislq.history.Constants;

public class HistoryUtil {
	/**
	 * get the external storage file
	 * 
	 * @return the file
	 */
	public static File getExternalStorageDir() {
		return Environment.getExternalStorageDirectory();
	}

	/**
	 * get the external storage file path
	 * 
	 * @return the file path
	 */
	public static String getExternalStoragePath() {
		return getExternalStorageDir().getAbsolutePath();
	}

	/**
	 * get the external storage state
	 * 
	 * @return
	 */
	public static String getExternalStorageState() {
		return Environment.getExternalStorageState();
	}

	/**
	 * check the usability of the external storage.
	 * 
	 * @return enable -> true, disable->false
	 */
	public static boolean isExternalStorageEnable() {
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			return true;
		}
		return false;

	}

	/**
	 * show a progress dialog
	 * 
	 * @param context
	 * @param message
	 * @param cancelable
	 * @return return the progress dialog
	 */
	public static ProgressDialog showProgressDialog(Context context,
			CharSequence message, boolean cancelable) {
		ProgressDialog progressDialog = new ProgressDialog(context);
		progressDialog.setCancelable(cancelable);
		progressDialog.setMessage(message);
		progressDialog.show();
		return progressDialog;
	}

	public static ProgressDialog showProgressDialog(Context context, int resId,
			boolean cancelable) {
		String text = context.getString(resId);
		return showProgressDialog(context, text, cancelable);
	}

	/**
	 * dismiss the progress diaog
	 * 
	 * @param dialog
	 */
	public static void dismissProgressDialog(ProgressDialog dialog) {
		if (dialog != null) {
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}

	/**
	 * judge the list is null or isempty
	 * 
	 * @param list
	 * @return
	 */
	public static boolean isEmpty(final List<? extends Object> list) {
		if (list == null || list.isEmpty()) {
			return true;
		}
		return false;
	}

	public static boolean isEmpty(final Set<? extends Object> sets) {
		if (sets == null || sets.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the string is null or 0-length.
	 * 
	 * @param text
	 * @return
	 */
	public static boolean isEmpty(final String text) {
		return TextUtils.isEmpty(text);
	}

	/**
	 * return true ,if the string is numeric
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNumeric(final String str) {
		if (isEmpty(str)) {
			return false;
		}
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	/**
	 * 获取设备屏幕的宽度
	 * 
	 * @param context
	 * @return
	 */
	public static int getSceenWidth(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 获取设备屏幕的高度
	 * 
	 * @param context
	 * @return
	 */
	public static int getSceenHeight(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static float getSceenDensity(Context context) {
		return context.getResources().getDisplayMetrics().density;
	}

	/**
	 * 将　dip转化成px
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}




	/**
	 * 获取出程序的版本号
	 * 
	 * @param context
	 * @return
	 */
	public static int getVerCode(Context context) {
		int verCode = -1;
		try {
			verCode = context.getPackageManager().getPackageInfo(
					Constants.PACKAGE_NAME, 0).versionCode;
		} catch (NameNotFoundException e) {
			L.e("not find the name package", e);
		}
		return verCode;
	}

	/**
	 * 获取出程序的版本名称
	 * 
	 * @param context
	 * @return
	 */
	public static String getVerName(Context context) {
		String verName = "";
		try {
			verName = context.getPackageManager().getPackageInfo(
					Constants.PACKAGE_NAME, 0).versionName;
		} catch (NameNotFoundException e) {
			L.e("not find the name package", e);
		}
		return verName;
	}

	/**
	 * 获取出程序的包名
	 * 
	 * @param context
	 * @return
	 */
	public static String getPackageName(Context context) {
		return context.getApplicationInfo().packageName;
	}
	

	/**
	 * hide the input method
	 * 
	 * @param view
	 */
	public static void hideSoftInput(View view) {
		if (view == null)
			return;
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
		}
	}

	/**
	 * show the input method
	 * 
	 * @param view
	 */
	public static void showSoftInput(View view) {
		if (view == null)
			return;
		InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.showSoftInput(view, 0);
	}
	public static String toCharString(String str) {
		if (TextUtils.isEmpty(str)) {
			str = "null";
		}
		String strBuf = "";
		for (int i = 0; i < str.length(); i++) {
			int a = str.charAt(i);
			strBuf += Integer.toHexString(a).toUpperCase();
		}
//		strBuf=String.valueOf(str.hashCode());
		return strBuf;
	}

	public static void initExternalDir(boolean cleanFile)
	{
		if(HistoryUtil.isExternalStorageEnable())
		{
			File external = new File(Constants.EXTERNAL_DIR);
			if(!external.exists())
			{
				external.mkdirs();
			}
			//check the cache whether exist
			File cache = new File(Constants.CACHE_DIR);
			if(!cache.exists())
			{
				cache.mkdirs();
			}
			else
			{
				if(cleanFile)
				{
					//if exist,so clear the old file
					cleanFile(cache, DateUtil.WEEK_MILLIS);
				}
			}
			//check the log dir
			File logs = new File(Constants.LOG_DIR);
			if(!logs.exists())
			{
				logs.mkdirs();
			}
			else
			{
				if(cleanFile)
				{
					cleanFile(logs, DateUtil.WEEK_MILLIS);
				}
			}
		}
	}
	private static int cleanFile(File dir, long maxInterval)
	{
		File[] files = dir.listFiles();
		if(files == null) return 0;
		int beforeNum = 0;
		long current = System.currentTimeMillis();
		for(File file:files)
		{
			long lastModifiedTime = file.lastModified();
			if((current-lastModifiedTime) > maxInterval)
			{
				//if the file is exist more than a week , so need to delete.
				file.delete();
				beforeNum ++;
			}
		}
		return beforeNum;
	}

}
