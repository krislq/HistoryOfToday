package com.krislq.history.activity;

import com.krislq.history.R;
import com.krislq.history.util.HistoryUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * 
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @date 2012-12-26
 * @version 1.0.0
 *
 */
public class AboutActivity extends BaseActivity {
	private TextView	tvVersion;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		setTitle(R.string.about);
		tvVersion = (TextView)findViewById(R.id.tv_version);
		tvVersion.setText(HistoryUtil.getVerName(this));
		Button btnLeft = (Button)findViewById(R.id.btn_left);
		btnLeft.setVisibility(View.GONE);
		Button btnRight = (Button)findViewById(R.id.btn_right);
		btnRight.setVisibility(View.GONE);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

}
