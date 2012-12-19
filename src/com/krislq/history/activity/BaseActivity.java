package com.krislq.history.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 * the base activity, could do something all the activity will to do.
 * 
 * @author <a href="mailto:kris@matchmovegames.com">Kris.lee</a>
 * @since 1.0.0 ä¸??04:11:39
 * @version 1.0.0
 */
public abstract class BaseActivity extends Activity {
	protected BaseActivity 		mContext = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mContext = this;
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
