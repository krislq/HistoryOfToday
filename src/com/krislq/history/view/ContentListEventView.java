package com.krislq.history.view;

import java.util.List;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krislq.history.R;
import com.krislq.history.activity.HistoryActivity;
import com.krislq.history.json.ContentJson;
import com.krislq.history.util.L;

public class ContentListEventView {

	private HistoryActivity mContext;
	private LayoutInflater	mInflater;
	private ViewGroup		mContentListEventView;
	
	
	private List<ContentJson>		mContents;
	public ContentListEventView(HistoryActivity context) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mContentListEventView = (ViewGroup)mInflater.inflate(R.layout.list_event, null);
	}
	
	public List<ContentJson> getContents() {
		return mContents;
	}

	public void setContents(List<ContentJson> mContents) {
		this.mContents = mContents;
	}

	public View generateContentView() {
		if(mContents == null || mContents.size() ==0)
			return mContentListEventView;
		mContentListEventView.removeAllViews();
		int size = mContents.size();
		for(int i=0;i<size;i++){
			ContentJson content = mContents.get(i);
			View view =mInflater.inflate(R.layout.list_event_item, null);
			TextView titleView = (TextView)view.findViewById(R.id.tv_self_event_content_item);
			String title = content.getTitle();
			titleView.setText(Html.fromHtml(title));
			L.e("title:"+title);
			mContentListEventView.addView(view);
		}
		return mContentListEventView;
	}
}
