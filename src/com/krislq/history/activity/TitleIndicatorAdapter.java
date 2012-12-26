package com.krislq.history.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.taptwo.android.widget.TitleProvider;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.krislq.history.R;
import com.krislq.history.json.ContentJson;
import com.krislq.history.json.ListEventJson;
import com.krislq.history.manager.DownloadManager;
import com.krislq.history.util.L;

/**
 * 
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @date 2012-12-26
 * @version 1.0.0
 *
 */
public class TitleIndicatorAdapter extends BaseAdapter implements TitleProvider {
	public static final int 	LIST_EVENT_BIG_EVENT = 0x0000;
	public static final int 	LIST_EVENT_BIRTH = 0x0001;
	public static final int 	LIST_EVENT_DIE = 0x0002;

	private LayoutInflater mInflater;
	
	private BaseActivity 	mContext;
	
	private Map<Integer, View> 	viewMap;
	private ListEventJson		listEvents;
	
	public TitleIndicatorAdapter(BaseActivity context,ListEventJson ListEvents) {
		mContext = context;
		this.listEvents = ListEvents;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		viewMap = new HashMap<Integer, View>();
	}

	public ListEventJson getListEvents() {
		return listEvents;
	}

	public void setListEvents(ListEventJson listEvents) {
		this.listEvents = listEvents;
		notifyDataSetChanged();
	}

	@Override
	public String getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return position; 
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = viewMap.get(position);
		if(convertView == null) {
			convertView = mInflater.inflate(R.layout.list_event, null);
			LinearLayout layout = (LinearLayout)convertView.findViewById(R.id.layout_list_event);
			List<ContentJson>  contents = getContents(position);
			for(ContentJson content:contents) {
				View itemView = mInflater.inflate(R.layout.list_event_item, null);
				TextView title = (TextView)itemView.findViewById(R.id.tv_list_event_content_item);
				title.setText(Html.fromHtml(content.getTitle()).toString());
				layout.addView(itemView);
			}
			viewMap.put(position, convertView);
		}
		return convertView;
	}


	@Override
	public int getCount() {
		return 3;
	}
	
	private List<ContentJson> getContents(int position){

		switch (position) {
		case LIST_EVENT_BIG_EVENT:
			return listEvents.getBigEvent().getContent();
		case LIST_EVENT_BIRTH:
			return listEvents.getBirth().getContent();
		case LIST_EVENT_DIE:
			return listEvents.getDied().getContent();
		}
		return null;
	}

	@Override
	public String getTitle(int position) {
		switch (position) {
		case LIST_EVENT_BIG_EVENT:
			return mContext.getString(R.string.title_big_event);
		case LIST_EVENT_BIRTH:
			return mContext.getString(R.string.title_birth);
		case LIST_EVENT_DIE:
			return mContext.getString(R.string.title_die);
		}
		return null;
	}

}
