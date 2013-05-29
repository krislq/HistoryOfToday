package com.krislq.history.activity;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import net.youmi.android.AdManager;
import net.youmi.android.banner.AdSize;
import net.youmi.android.banner.AdView;
import net.youmi.android.offers.OffersManager;
import net.youmi.android.offers.PointsManager;
import net.youmi.android.spot.SpotManager;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.taptwo.android.widget.CircleFlowIndicator;
import org.taptwo.android.widget.TitleFlowIndicator;
import org.taptwo.android.widget.ViewFlow;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.krislq.history.R;
import com.krislq.history.json.HistoryJson;
import com.krislq.history.manager.DownloadManager;
import com.krislq.history.manager.HttpManager;
import com.krislq.history.manager.ITransaction;
import com.krislq.history.util.DateUtil;
import com.krislq.history.util.L;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.UMFeedbackService;

/**
 * 
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @date 2012-12-26
 * @version 1.0.0
 *
 */
public class HistoryActivity extends BaseActivity implements OnClickListener{
	public static final int 		HANDLER_SHOW_DATA = 0x0001;
	public static final int 		HANDLER_SHOW_ERROR = 0x0002;
	public static final int 		HANDLER_REFRESH_VIEW = 0x0003;
	public static final int 		HANDLER_GO_TO_PEW = 0x0004;
	public static final int 		HANDLER_GO_TO_NEXT = 0x0005;
	
	private static final int 		DIALOG_PRE = 0x1000;
	private static final int 		DIALOG_NEXT = 0x2000;
	
	private Button			mBtnLeft;
	private Button			mBtnRight;
	public 	ObjectMapper 	mObjectMapper = null;
	private HistoryJson 	mResponseObject = null;
	
	private LinearLayout	mContentLayout = null;
	private LinearLayout	mEmptyLayout = null;
	
	private ProgressBar		mProgressBar = null;
	private TextView		mtvMessage = null;
	private Button			mbtnRetry = null;
	
	private ViewFlow		mViewFlowSelfEvent;
	private ViewFlow		mViewFlowListEvent;
	private CircleFlowIndicator mCircleFlowIndicator;
	private TitleFlowIndicator 	mTitleFlowIndicator;
	private TitleIndicatorAdapter mTitleIndicatorAdapter;
	private CircleIndicatorAdapter mCircleIndicatorAdapter;
	private UIHandler		mHandler;
	private DownloadManager mDownloadManager;
	
	private Calendar		mCalendar;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MobclickAgent.onEvent(mContext, "Start", "HistoryActivity");
		AdManager.getInstance(this).init("7f738b41c9cac277 ","b8d621c7e1349c9f", false);
        // 如果使用积分广告，请务必调用积分广告的初始化接口:
        OffersManager.getInstance(this).onAppLaunch();
		mObjectMapper = new ObjectMapper();
		mHandler = new UIHandler();
		mCalendar = Calendar.getInstance();
		mDownloadManager = new DownloadManager(mContext, mHandler);
		setContentView(R.layout.history_of_today);
		
		setTitleOnClickListener(this);
		mBtnLeft = (Button)findViewById(R.id.btn_left);
		mBtnLeft.setOnClickListener(this);
		mBtnRight = (Button)findViewById(R.id.btn_right);
		mBtnRight.setOnClickListener(this);
		
		mContentLayout = (LinearLayout)findViewById(R.id.layout_content);
		mEmptyLayout = (LinearLayout)findViewById(R.id.layout_empty);
		mProgressBar = (ProgressBar)findViewById(R.id.progress_bar);
		mtvMessage = (TextView)findViewById(R.id.tv_message);
		mbtnRetry = (Button)findViewById(R.id.btn_retry);
		mbtnRetry.setOnClickListener(this);
		
		mViewFlowSelfEvent = (ViewFlow)findViewById(R.id.viewflow_self_event);
		mViewFlowListEvent = (ViewFlow)findViewById(R.id.viewflow_list_event);
		mCircleFlowIndicator = (CircleFlowIndicator)findViewById(R.id.circle_flow_indicato);
		mTitleFlowIndicator = (TitleFlowIndicator)findViewById(R.id.title_flow_indicator);

        // 将广告条adView添加到需要展示的layout控件中
        LinearLayout adLayout = (LinearLayout) findViewById(R.id.adLayout);
        AdView adView = new AdView(this, AdSize.SIZE_320x50);
        adLayout.addView(adView);
		//get today
		getHistoryData(mCalendar);
	}

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        // 如果使用积分广告，请务必调用积分广告的初始化接口:
        OffersManager.getInstance(this).onAppExit();
    }

	private void setAccessStatus(boolean isLoading) {
		if(isLoading) {
			mContentLayout.setVisibility(View.GONE);
			mEmptyLayout.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.VISIBLE);
			mtvMessage.setText(R.string.msg_loading);
			mbtnRetry.setVisibility(View.GONE);
		} else {
			mContentLayout.setVisibility(View.VISIBLE);
			mEmptyLayout.setVisibility(View.GONE);
		}
	}
	
	private void refreshTitle(Calendar calendar) {
		setTitle(getString(R.string.date, calendar.get(Calendar.MONTH)+1,calendar.get(Calendar.DAY_OF_MONTH)));
		
		Calendar preCalendar = addDay(calendar, -1);
		mBtnLeft.setText(DateUtil.toTime(preCalendar.getTimeInMillis(), DateUtil.DATE_FORMATE_HISTORY));
		mBtnLeft.setText(getString(R.string.date, preCalendar.get(Calendar.MONTH)+1,preCalendar.get(Calendar.DAY_OF_MONTH)));
		
		Calendar lastCalendar = addDay(calendar, 1);
		mBtnRight.setText(DateUtil.toTime(lastCalendar.getTimeInMillis(), DateUtil.DATE_FORMATE_HISTORY));
		mBtnRight.setText(getString(R.string.date, lastCalendar.get(Calendar.MONTH)+1,lastCalendar.get(Calendar.DAY_OF_MONTH)));
	}
	
	private Calendar addDay(Calendar calendar, int addDay) {
		int dateOdYear = calendar.get(Calendar.DAY_OF_YEAR);
		Calendar newCalendar = Calendar.getInstance();
		newCalendar.setTimeInMillis(calendar.getTimeInMillis());
		newCalendar.set(Calendar.DAY_OF_YEAR, dateOdYear + addDay);
		return newCalendar;
	}
	
	private void getHistoryData(Calendar calender)
	{
		mCalendar = calender;
		//refresh titile
		refreshTitle(mCalendar);
		setAccessStatus(true);
		
		String date = (mCalendar.get(Calendar.MONTH)+1 )+"-"+mCalendar.get(Calendar.DAY_OF_MONTH);
		//http://baike.baidu.com/app/historyontoday?asyn=1&date=12-19
		String url = "http://baike.baidu.com/app/historyontoday";
		Map<String,String> requestData = new HashMap<String, String>(2);
		requestData.put("asyn", "1");
		requestData.put("date", date);
		HttpManager httpManager = new HttpManager(url,requestData, HttpManager.GET, getHistoryTransaction);
		httpManager.start();
	}
	
	
	@Override
	protected Dialog onCreateDialog(final int id) {
		return new AlertDialog.Builder(mContext)
		.setCancelable(false)
        .setTitle(R.string.dialog_title)
        .setMessage(R.string.dialog_content)
        .setPositiveButton(R.string.dialog_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				mPrefereceManager.setFirstChangeDate(false);
				switch (id) {
				case DIALOG_NEXT:
					mHandler.sendEmptyMessage(HANDLER_GO_TO_NEXT);
					break;

				case DIALOG_PRE:
					mHandler.sendEmptyMessage(HANDLER_GO_TO_PEW);
					break;
				}
			}
		})
        .create();
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
				mHandler.obtainMessage(HANDLER_SHOW_ERROR,mContext.getString(R.string.msg_error_json)).sendToTarget();
			}
		}
		
		@Override
		public void transactionException(int erroCode,String result, Exception e) {
			mHandler.obtainMessage(HANDLER_SHOW_ERROR,mContext.getString(R.string.msg_error_exception)).sendToTarget();
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_left:
			if(mPrefereceManager.isFirstChangeDate()) {
				showDialog(DIALOG_PRE);
			} else {
				mHandler.sendEmptyMessage(HANDLER_GO_TO_PEW);
			}
			break;
		case R.id.btn_right:
			if(mPrefereceManager.isFirstChangeDate()) {
				showDialog(DIALOG_NEXT);
			} else {
				mHandler.sendEmptyMessage(HANDLER_GO_TO_NEXT);
			}
			break;
		case R.id.btn_retry:
			getHistoryData(mCalendar);
			break;
		case R.id.tv_title:
			MobclickAgent.onEvent(mContext, "ChangeDate", "GoToday");
			getHistoryData(Calendar.getInstance());
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
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_about:
			Intent aboutIntent = new Intent(this,AboutActivity.class);
			startActivity(aboutIntent);
			break;
		case R.id.menu_feedback:
			UMFeedbackService.openUmengFeedbackSDK(this);
			break;
		case R.id.menu_offer:
		    OffersManager.getInstance(this).showOffersWall();
		    break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}


	class UIHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case HANDLER_SHOW_DATA:
				mTitleIndicatorAdapter = new TitleIndicatorAdapter(HistoryActivity.this,mResponseObject.getListEvent());
				mViewFlowListEvent.setAdapter(mTitleIndicatorAdapter, 0);
				mTitleFlowIndicator.setTitleProvider(mTitleIndicatorAdapter);
				mViewFlowListEvent.setFlowIndicator(mTitleFlowIndicator);
				
				mCircleIndicatorAdapter = new CircleIndicatorAdapter(mContext, mResponseObject.getListEvent().getSelfEvent(), mDownloadManager);
				mViewFlowSelfEvent.setAdapter(mCircleIndicatorAdapter);
				mViewFlowSelfEvent.setFlowIndicator(mCircleFlowIndicator);
				setAccessStatus(false);
				break;
			case HANDLER_SHOW_ERROR:
				mtvMessage.setText((String)msg.obj);
				mProgressBar.setVisibility(View.GONE);
				mbtnRetry.setVisibility(View.VISIBLE);
				break;
			case HANDLER_REFRESH_VIEW:
				mTitleIndicatorAdapter.notifyDataSetChanged();
				mCircleIndicatorAdapter.notifyDataSetChanged();
				break;
			case HANDLER_GO_TO_NEXT:
				MobclickAgent.onEvent(mContext, "ChangeDate", "Next");
				getHistoryData(addDay(mCalendar, 1));
				break;
			case HANDLER_GO_TO_PEW:
				MobclickAgent.onEvent(mContext, "ChangeDate", "Pre");
				getHistoryData(addDay(mCalendar, -1));
				break;
			default:
				break;
			}
		}
		

	}
}