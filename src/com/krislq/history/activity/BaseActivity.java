package com.krislq.history.activity;

import com.krislq.history.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * the base activity, could do something all the activity will to do.
 * 
 * @author <a href="mailto:kris@matchmovegames.com">Kris.lee</a>
 * @since 1.0.0 ä¸??04:11:39
 * @version 1.0.0
 */
public abstract class BaseActivity extends Activity {
	protected BaseActivity 		mContext = null;
	private TextView			mtvTitle = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
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

	@Override
	protected void onStart() {
		super.onStart();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	
	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
