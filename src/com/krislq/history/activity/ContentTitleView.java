package com.krislq.history.activity;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krislq.history.R;
import com.krislq.history.util.L;

public class ContentTitleView {
	public static final int 	MATCH_TEXT = -1;
	
	private HistoryActivity mContext;
	private LayoutInflater	mInflater;
	private View			mLayoutTab;
	private ViewGroup		mTitleView;
	private List<String> 	mTitles;
	
	private int 			mTitleWidth;
	private int 			mTitleHeight;
	private int 			mTitleLength = 5;
	
	private TabTitleClickListener	mClickListener;
	
	public ContentTitleView(HistoryActivity context) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mLayoutTab = mInflater.inflate(R.layout.tab_header, null);
		mTitleView = (ViewGroup)mLayoutTab.findViewById(R.id.layout_tab);
	}
	public List<String> getmTitles() {
		return mTitles;
	}
	public void setmTitles(List<String> titles) {
		this.mTitles = titles;
	}
	
	public int getmTitleWidth() {
		return mTitleWidth;
	}
	public void setmTitleWidth(int titleWidth) {
		this.mTitleWidth =titleWidth;
	}
	public void setmTitleLength(int titleLength) {
		this.mTitleLength = titleLength;
	}
	
	public int getTitleHeight() {
		return mTitleHeight;
	}
	public View generateTitle() {
		if(mTitles == null || mTitles.size() ==0)
			return mLayoutTab;
		mTitleView.removeAllViews();
		int size = mTitles.size();
		for(int i=0;i<size;i++){
			String title = mTitles.get(i);
			TextView view = (TextView)mInflater.inflate(R.layout.tab_header_iterm, null);
			view.setText(title.length()>mTitleLength ? title.subSequence(0, mTitleLength):title);
			L.e("title:"+title);
			view.setOnClickListener(new ItemClickListener(view,i) {
				@Override
				public void onClick(View v) {
					if(mClickListener!=null) {
						mClickListener.onTitleClick(ContentTitleView.this, view, index);
					}
					L.e("Index:"+index);
					if(mTitleWidth == MATCH_TEXT) {
						//TODO match the text
					}
				}
			});
			mTitleView.addView(view);
		}
		return mLayoutTab;
	}
	public void setOnClickListener(TabTitleClickListener l){
		mClickListener = l;
	}
}
