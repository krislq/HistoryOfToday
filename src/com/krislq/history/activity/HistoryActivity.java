package com.krislq.history.activity;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.krislq.history.R;
import com.krislq.history.json.HistoryJson;
import com.krislq.history.manager.DownloadManager;
import com.krislq.history.manager.HttpManager;
import com.krislq.history.manager.ITransaction;
import com.krislq.history.util.DateUtil;
import com.krislq.history.util.L;

public class HistoryActivity extends BaseActivity implements OnClickListener{
	public static final int 		HANDLER_SHOW_DATA = 0x0001;
	public static final int 		HANDLER_SHOW_ERROR = 0x0002;
	
	private static final int 		DISPLAY_SELF_EVENT = 0x0000;
	private static final int 		DISPLAY_LIST_EVENT = 0x0001;
	
	private Button			mBtnShare;
	public 	ObjectMapper 	mObjectMapper = null;
	private HistoryJson 	mResponseObject = null;
	
	private ViewFlow		mViewFlowSelfEvent;
	private ViewFlow		mViewFlowListEvent;
	private CircleFlowIndicator mCircleFlowIndicator;
	private TitleFlowIndicator 	mTitleFlowIndicator;
	private UIHandler		mHandler;
	private DownloadManager mDownloadManager;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mObjectMapper = new ObjectMapper();
		mHandler = new UIHandler();
		mDownloadManager = new DownloadManager(mContext, mHandler);
		setContentView(R.layout.history_of_today);
		mBtnShare = (Button)findViewById(R.id.btn_share);
		mBtnShare.setOnClickListener(this);
		
		mViewFlowSelfEvent = (ViewFlow)findViewById(R.id.viewflow_self_event);
		mViewFlowListEvent = (ViewFlow)findViewById(R.id.viewflow_list_event);
		mCircleFlowIndicator = (CircleFlowIndicator)findViewById(R.id.circle_flow_indicato);
		mTitleFlowIndicator = (TitleFlowIndicator)findViewById(R.id.title_flow_indicator);
		//get today
		getHistoryData(DateUtil.toTime(System.currentTimeMillis(), DateUtil.DATE_FORMATE_HISTORY));
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_share:
			break;

		default:
			break;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	class UIHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLER_SHOW_DATA:
				TitleIndicatorAdapter titleIndicatorAdapter = new TitleIndicatorAdapter(HistoryActivity.this,mResponseObject.getListEvent());
				mViewFlowListEvent.setAdapter(titleIndicatorAdapter, 0);
				mTitleFlowIndicator.setTitleProvider(titleIndicatorAdapter);
				mViewFlowListEvent.setFlowIndicator(mTitleFlowIndicator);
				
				CircleIndicatorAdapter circleIndicatorAdapter = new CircleIndicatorAdapter(mContext, mResponseObject.getListEvent().getSelfEvent(), mDownloadManager);
				mViewFlowSelfEvent.setAdapter(circleIndicatorAdapter);
				mViewFlowSelfEvent.setFlowIndicator(mCircleFlowIndicator);
				break;
			case HANDLER_SHOW_ERROR:
				
				break;

			default:
				break;
			}
		}
		

	}
}