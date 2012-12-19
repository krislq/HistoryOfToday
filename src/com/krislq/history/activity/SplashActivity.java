package com.krislq.history.activity;

import android.content.Intent;
import android.os.Bundle;

import com.krislq.history.R;
import com.krislq.history.util.ThreadPoolUtil;

public class SplashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash);
		ThreadPoolUtil.execute(new Runnable() {
			
			@Override
			public void run() {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
				}
				Intent intent = new Intent(SplashActivity.this,HistoryActivity.class);
				SplashActivity.this.startActivity(intent);
			}
		});
	}
}
