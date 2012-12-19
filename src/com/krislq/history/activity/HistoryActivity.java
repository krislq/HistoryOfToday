package com.krislq.history.activity;

import java.util.HashMap;
import java.util.Map;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.krislq.history.R;
import com.krislq.history.manager.HttpManager;
import com.krislq.history.manager.ITransaction;
import com.krislq.history.util.DateUtil;
import com.krislq.history.util.L;

public class HistoryActivity extends BaseActivity implements OnClickListener{
	private static final int 		HANDLER_SHOW_DATA = 0x0001;
	private static final int 		HANDLER_SHOW_ERROR = 0x0002;
	private ListView		mListView;
	private Button			mBtnShare;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.history_of_today);
		mListView = (ListView)findViewById(R.id.list_view);
		mBtnShare = (Button)findViewById(R.id.btn_share);
		mBtnShare.setOnClickListener(this);
		getHistoryData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	private void getHistoryData()
	{
		//http://baike.baidu.com/app/historyontoday?asyn=1&date=12-19
		String url = "http://baike.baidu.com/app/historyontoday";
		Map<String,String> requestData = new HashMap<String, String>(2);
		requestData.put("asyn", "1");
		requestData.put("date", DateUtil.toTime(System.currentTimeMillis(), DateUtil.DATE_FORMATE_HISTORY));
		HttpManager httpManager = new HttpManager(url,requestData, HttpManager.GET, getHistoryTransaction);
		httpManager.start();
	}
	
	private void mapperJson(String result){
		
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
				
				break;

			default:
				break;
			}
		}
		
	};
	public ITransaction getHistoryTransaction = new ITransaction() {

		@Override
		public void transactionOver(String result) {
			mapperJson(result);
		}
		
		@Override
		public void transactionException(int erroCode,String result, Exception e) {
			mHandler.obtainMessage(HANDLER_SHOW_ERROR).sendToTarget();
		}
	};
}
