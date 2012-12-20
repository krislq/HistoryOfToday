package com.krislq.history.view;

import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.krislq.history.R;
import com.krislq.history.activity.HistoryActivity;
import com.krislq.history.activity.ItemClickListener;
import com.krislq.history.activity.TabTitleClickListener;
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
	
	private int 			mSelectIndex = 0;
	
	private TabTitleClickListener	mClickListener;
	
	public ContentTitleView(HistoryActivity context) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mLayoutTab = mInflater.inflate(R.layout.tab_header, null);
		mTitleView = (ViewGroup)mLayoutTab.findViewById(R.id.layout_tab);
	}
	public List<String> getTitles() {
		return mTitles;
	}
	public void setTitles(List<String> titles) {
		this.mTitles = titles;
	}
	
	public int getTitleWidth() {
		return mTitleWidth;
	}
	public void setTitleWidth(int titleWidth) {
		this.mTitleWidth =titleWidth;
	}
	public void setTitleLength(int titleLength) {
		this.mTitleLength = titleLength;
	}
	
	
	public int getSelectIndex() {
		return mSelectIndex;
	}
	public void setSelectIndex(int selectIndex) {
		this.mSelectIndex = selectIndex;
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
//			view.setText(title);
			L.e("title:"+title);
			//TODO index == select index
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
