package com.krislq.history.activity;

import android.content.Intent;
import android.os.Bundle;

import com.krislq.history.Constants;
import com.krislq.history.R;
import com.krislq.history.util.HistoryUtil;
import com.krislq.history.util.ThreadPoolUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.NotificationType;
import com.umeng.fb.UMFeedbackService;
import com.umeng.update.UmengUpdateAgent;

/**
 * 
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @date 2012-12-26
 * @version 1.0.0
 *
 */
public class SplashActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobclickAgent.onEvent(mContext, "Start", "SplashActivity");
		UMFeedbackService.enableNewReplyNotification(this, NotificationType.NotificationBar);
		UmengUpdateAgent.update(this);
		setContentView(R.layout.splash);
		ThreadPoolUtil.execute(new Runnable() {
			
			@Override
			public void run() {
				Constants.PACKAGE_NAME = HistoryUtil.getPackageName(mContext);
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
				}
				Intent intent = new Intent(SplashActivity.this,HistoryActivity.class);
				SplashActivity.this.startActivity(intent);
				SplashActivity.this.finish();
			}
		});
	}
}
