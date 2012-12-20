package com.krislq.history.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.krislq.history.R;
import com.krislq.history.json.ContentJson;
import com.krislq.history.json.HistoryJson;
import com.krislq.history.manager.HttpManager;
import com.krislq.history.manager.ITransaction;
import com.krislq.history.util.DateUtil;
import com.krislq.history.util.L;

public class HistoryActivity extends BaseActivity implements OnClickListener{
	private static final int 		HANDLER_SHOW_DATA = 0x0001;
	private static final int 		HANDLER_SHOW_ERROR = 0x0002;
	private Button			mBtnShare;
	public 	ObjectMapper 	mObjectMapper = null;
	private HistoryJson 	mResponseObject = null;
	
	private ViewGroup		mGroundOne;
	private ViewGroup		mGroundTwo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mObjectMapper = new ObjectMapper();
		setContentView(R.layout.history_of_today);
		mBtnShare = (Button)findViewById(R.id.btn_share);
		mBtnShare.setOnClickListener(this);
		
		mGroundOne = (ViewGroup)findViewById(R.id.animation_layout_one);
		mGroundTwo = (ViewGroup)findViewById(R.id.animation_layout_two);
		//get today
		getHistoryData(DateUtil.toTime(System.currentTimeMillis(), DateUtil.DATE_FORMATE_HISTORY));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	private void getHistoryData(String date)
	{
		//http://baike.baidu.com/app/historyontoday?asyn=1&date=12-19
		String url = "http://baike.baidu.com/app/historyontoday";
		Map<String,String> requestData = new HashMap<String, String>(2);
		requestData.put("asyn", "1");
		requestData.put("date", date);
		HttpManager httpManager = new HttpManager(url,requestData, HttpManager.GET, getHistoryTransaction);
		httpManager.start();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_share:
			
			break;

		default:
			break;
		}
	}
	private Handler mHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLER_SHOW_DATA:
				List<ContentJson> content = mResponseObject.getListEvent().getSelfEvent();
				List<String> titles = fitTitles(content);
				ContentTitleView titleView = new ContentTitleView(HistoryActivity.this);
				titleView.setmTitles(titles);
				titleView.setOnClickListener(new TabTitleClickListener() {
					
					@Override
					public void onTitleClick(ContentTitleView contentTitleView, View view,
							int index) {
						L.e("Index:"+index);
					}
				});
				View selfEventTitleView = titleView.generateTitle();
				mGroundOne.removeAllViews();
				mGroundOne.addView(selfEventTitleView);
				L.e("OK");
				
				break;
			case HANDLER_SHOW_ERROR:
				
				break;

			default:
				break;
			}
		}
		
	};

	private List<String> fitTitles(List<ContentJson> content) {
		List<String> titles = new ArrayList<String>(content.size());
		for(ContentJson json :content) {
			titles.add(json.getTitle());
		}
		return titles;
	}
	public ITransaction getHistoryTransaction = new ITransaction() {
		@Override
		public void transactionOver(String result) {
			
			try {
				mResponseObject = mObjectMapper.readValue(result, new TypeReference<HistoryJson>() { });
				mHandler.sendEmptyMessage(HANDLER_SHOW_DATA);
			} catch (Exception e) {
				L.e("getGiftsTransaction exception", e);
				mResponseObject = null;
				mHandler.sendEmptyMessage(HANDLER_SHOW_ERROR);
			}
		}
		
		@Override
		public void transactionException(int erroCode,String result, Exception e) {
			mHandler.obtainMessage(HANDLER_SHOW_ERROR).sendToTarget();
		}
	};
}
