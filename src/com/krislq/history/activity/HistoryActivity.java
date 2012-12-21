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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.krislq.history.R;
import com.krislq.history.json.ContentJson;
import com.krislq.history.json.HistoryJson;
import com.krislq.history.manager.HttpManager;
import com.krislq.history.manager.ITransaction;
import com.krislq.history.util.DateUtil;
import com.krislq.history.util.HistoryUtil;
import com.krislq.history.util.L;
import com.krislq.history.view.ContentListEventView;
import com.krislq.history.view.ContentSelftEventView;
import com.krislq.history.view.ContentTitleView;

public class HistoryActivity extends BaseActivity implements OnClickListener{
	private static final int 		HANDLER_SHOW_DATA = 0x0001;
	private static final int 		HANDLER_SHOW_ERROR = 0x0002;
	private static final int 		HANDLER_CHANGE_SELF_EVENT = 0x0003;
	private static final int 		HANDLER_CHANGE_LIST_EVENT = 0x0004;
	
	private static final int 		DISPLAY_SELF_EVENT = 0x0000;
	private static final int 		DISPLAY_LIST_EVENT = 0x0001;
	

	private static final int 		DISPLAY_LIST_EVENT_BIG_EVENT = 0x0000;
	private static final int 		DISPLAY_LIST_EVENT_BIRTH = 0x0001;
	private static final int 		DISPLAY_LIST_EVENT_DIE = 0x0002;
	private Button			mBtnShare;
	public 	ObjectMapper 	mObjectMapper = null;
	private HistoryJson 	mResponseObject = null;
	
	private ViewGroup		mGroundOne;
	private ViewGroup		mGroundTwo;
	private Handler			mHandler;
	
	private int 			mSelectSelfEventIndex = 0;
	private int 			mSelectListEventIndex = DISPLAY_LIST_EVENT_BIG_EVENT;
	private int 			mDisplayIndex = DISPLAY_SELF_EVENT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mObjectMapper = new ObjectMapper();
		mHandler = new UIHandler();
		setContentView(R.layout.history_of_today);
		mBtnShare = (Button)findViewById(R.id.btn_share);
		mBtnShare.setOnClickListener(this);
		
		mGroundOne = (ViewGroup)findViewById(R.id.animation_layout_one);
		mGroundTwo = (ViewGroup)findViewById(R.id.animation_layout_two);
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
	
	public void initLayoutPosition() {
		LayoutParams twoParams = (LayoutParams) mGroundTwo.getLayoutParams();
		if(mDisplayIndex == DISPLAY_SELF_EVENT) {
			twoParams.topMargin = HistoryUtil.getSceenHeight(mContext) -400;
		}
		mGroundTwo.setLayoutParams(twoParams);
		mGroundTwo.requestLayout();
	}
	
	class UIHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLER_SHOW_DATA:
				mHandler.obtainMessage(HANDLER_CHANGE_SELF_EVENT, mSelectSelfEventIndex, 0).sendToTarget();
				mHandler.obtainMessage(HANDLER_CHANGE_LIST_EVENT, mSelectListEventIndex, 0).sendToTarget();
				initLayoutPosition();
				break;
			case HANDLER_CHANGE_SELF_EVENT:
				int selectSelfIndex = msg.arg1;
				List<ContentJson> selfEvenContents = mResponseObject.getListEvent().getSelfEvent();
				List<String> selfEventTitles = fitTitles(selfEvenContents);
				ContentTitleView selfEventTitleView = new ContentTitleView(HistoryActivity.this);
				selfEventTitleView.setTitles(selfEventTitles);
				selfEventTitleView.setSelectIndex(selectSelfIndex);
				selfEventTitleView.setOnClickListener(new TabTitleClickListener() {
					@Override
					public void onTitleClick(ContentTitleView contentTitleView, View view,
							int index) {
						L.e("Index:"+index);
						mHandler.obtainMessage(HANDLER_CHANGE_SELF_EVENT, index, 0).sendToTarget();
					}
				});
				mGroundOne.removeAllViews();
				mGroundOne.addView(selfEventTitleView.generateTitle());
				L.e("OK");
				ContentJson content = selfEvenContents.get(selectSelfIndex);
				ContentSelftEventView contentSelftEventView = new ContentSelftEventView(HistoryActivity.this);
				contentSelftEventView.setContent(content);
				mGroundOne.addView(contentSelftEventView.generateContentView());
				break;
			case HANDLER_CHANGE_LIST_EVENT:
				int selectListIndex = msg.arg1;
				List<String> listEventTitles = fitListEventTitles();
				ContentTitleView listEventTitleView = new ContentTitleView(HistoryActivity.this);
				listEventTitleView.setTitles(listEventTitles);
				listEventTitleView.setSelectIndex(selectListIndex);
				listEventTitleView.setOnClickListener(new TabTitleClickListener() {
					@Override
					public void onTitleClick(ContentTitleView contentTitleView, View view,
							int index) {
						L.e("Index:"+index);
						mHandler.obtainMessage(HANDLER_CHANGE_LIST_EVENT, index, 0).sendToTarget();
					}
				});
				mGroundTwo.removeAllViews();
				mGroundTwo.addView(listEventTitleView.generateTitle());
				L.e("OK");
				
				ContentListEventView   contentListEventView = new ContentListEventView(HistoryActivity.this);
				contentListEventView.setContents(getListEvents(selectListIndex));
				mGroundTwo.addView(contentListEventView.generateContentView());
				break;
			case HANDLER_SHOW_ERROR:
				
				break;

			default:
				break;
			}
		}
		
	};
	
	private List<ContentJson> getListEvents(int index) {
		List<ContentJson> listEvenContents = new ArrayList<ContentJson>(10);
		LinearLayout layoutListEvent = (LinearLayout)LayoutInflater.from(mContext).inflate(R.layout.list_event, null);
		for(ContentJson json:listEvenContents) {
			TextView title = new TextView(mContext);
		}
		switch (index) {
		case DISPLAY_LIST_EVENT_BIRTH:
			listEvenContents = mResponseObject.getListEvent().getBirth().getContent();
			break;
		case DISPLAY_LIST_EVENT_DIE:
			listEvenContents = mResponseObject.getListEvent().getDied().getContent();
			break;
		default:
			listEvenContents = mResponseObject.getListEvent().getBigEvent().getContent();
			break;
		}
		return listEvenContents;
	}

	private List<String> fitTitles(List<ContentJson> content) {
		List<String> titles = new ArrayList<String>(content.size());
		for(ContentJson json :content) {
			titles.add(json.getTitle());
		}
		return titles;
	}
	private List<String> fitListEventTitles() {
		List<String> titles = new ArrayList<String>(3);
		titles.add(getString(R.string.title_big_event));
		titles.add(getString(R.string.title_birth));
		titles.add(getString(R.string.title_die));
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
}
