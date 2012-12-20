package com.krislq.history.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.krislq.history.R;
import com.krislq.history.activity.HistoryActivity;
import com.krislq.history.json.ContentJson;

public class ContentSelftEventView {

	private HistoryActivity mContext;
	private LayoutInflater	mInflater;
	private View			mContentSelfEventView;
	
	private TextView		mtvTitle;
	private ImageView		mimThumb;
	private TextView		mtvContent;
	private TextView		mtvMore;
	
	private ContentJson		mContent;
	public ContentSelftEventView(HistoryActivity context) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mContentSelfEventView = mInflater.inflate(R.layout.self_event, null);
		mtvTitle = (TextView)mContentSelfEventView.findViewById(R.id.tv_self_event_title);
		mimThumb = (ImageView)mContentSelfEventView.findViewById(R.id.im_preview);
		mtvContent = (TextView)mContentSelfEventView.findViewById(R.id.tv_self_event_content);
		mtvMore = (TextView)mContentSelfEventView.findViewById(R.id.tv_self_event_more);
	}
	
	
	public ContentJson getContent() {
		return mContent;
	}
	
	public void setContent(ContentJson content) {
		this.mContent = content;
	}
	
	public View generateContentView(){
		if(mContent != null) {
			mtvTitle.setText(mContent.getTitle());
			mtvContent.setText(mContent.getSummary());
			mtvMore.setText(R.string.more);
		}
		return mContentSelfEventView;
	}
}
