package com.krislq.history.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.krislq.history.Constants;
import com.krislq.history.R;
import com.krislq.history.manager.PreferenceManager;
import com.umeng.analytics.MobclickAgent;

/**
 * 
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @date 2012-12-26
 * @version 1.0.0
 *
 */
public abstract class BaseActivity extends Activity {
	protected BaseActivity 		mContext = null;
	private TextView			mtvTitle = null;
	
	protected PreferenceManager  mPrefereceManager = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
		mPrefereceManager = new PreferenceManager(mContext);
		MobclickAgent.setDebugMode(Constants.DEBUG);
		MobclickAgent.onError(this);
	}

	
	@Override
	public void setContentView(int layoutResID) {
		// TODO Auto-generated method stub
		super.setContentView(layoutResID);
		mtvTitle = (TextView)findViewById(R.id.tv_title);
	}

	public void setTitle(int resId) {
		setTitle(getString(resId));
	}
	
	public void setTitle(String title) {
		if(mtvTitle != null) {
			mtvTitle.setText(title);
		}
	}
	public void setTitleOnClickListener(OnClickListener l) {
		if(mtvTitle != null) {
			mtvTitle.setOnClickListener(l);
		}
	}

	@Override
	protected void onStart() {
		super.onStart();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
