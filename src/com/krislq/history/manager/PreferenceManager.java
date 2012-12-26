package com.krislq.history.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.krislq.history.Constants.AppConfig;

/**
 * 
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @date 2012-12-26
 * @version 1.0.0
 *
 */
public class PreferenceManager {
	private Context mContext;
	public PreferenceManager(Context context)
	{
		mContext = context;
	}
	/**
	 * get the shared proferences for getting or setting 
	 * @return
	 */
	private SharedPreferences getSharedPreferences()
	{
		return mContext.getSharedPreferences(AppConfig.PERFERENCE_NAME, Context.MODE_PRIVATE);
	}
	/**
	 * get the editor  for saving the key value
	 * @return
	 */
	private Editor getEditer()
	{
		return getSharedPreferences().edit();
	}
	
	public boolean isFirstChangeDate() {
		SharedPreferences preference = getSharedPreferences();
		
		return preference.getBoolean(AppConfig.PERFEN_KEY_CHANGE_DATE,true);
	}
	
	public void setFirstChangeDate(boolean value) {
		Editor editor = getEditer();
		editor.putBoolean(AppConfig.PERFEN_KEY_CHANGE_DATE, value);
		editor.commit();
	}
}
