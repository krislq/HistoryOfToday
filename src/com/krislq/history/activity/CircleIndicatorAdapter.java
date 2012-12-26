package com.krislq.history.activity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.krislq.history.R;
import com.krislq.history.json.ContentJson;
import com.krislq.history.manager.DownloadManager;


public class CircleIndicatorAdapter extends BaseAdapter{
	public static final int 	LIST_EVENT_BIG_EVENT = 0x0000;
	public static final int 	LIST_EVENT_BIRTH = 0x0001;
	public static final int 	LIST_EVENT_DIE = 0x0002;

	private LayoutInflater mInflater;
	
	private BaseActivity 	mContext;
	
	private Map<Integer, View> 	viewMap;
	private List<ContentJson>	contents;
	
	
	private DownloadManager mDownloadManager;
	
	public CircleIndicatorAdapter(BaseActivity context,List<ContentJson> contents,DownloadManager downloadManager) {
		mContext = context;
		this.mDownloadManager = downloadManager;
		this.contents = contents;
		mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		viewMap = new HashMap<Integer, View>();
	}

	public List<ContentJson> getContents() {
		return contents;
	}

	public void setContents(List<ContentJson> contents) {
		this.contents = contents;
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
		ViewHolder holder = null;
		convertView = viewMap.get(position);
		ContentJson content = contents.get(position);
		if(convertView == null) {
			holder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.self_event_item, null);
			TextView titleView = (TextView)convertView.findViewById(R.id.tv_self_event_title);
			titleView.setText(content.getTitle());
			holder.imageView = (ImageView)convertView.findViewById(R.id.im_self_ecent_image);
			convertView.setTag(holder);
			viewMap.put(position, convertView);
		}
		else {
			holder = (ViewHolder)convertView.getTag();
		}
		mDownloadManager.add(content.getPicUrl(), holder.imageView, R.drawable.default_image);
		return convertView;
	}


	@Override
	public int getCount() {
		return contents == null ?0 :contents.size();
	}
	
	class ViewHolder {
		ImageView imageView;

		public ImageView getImageView() {
			return imageView;
		}

		public void setImageView(ImageView imageView) {
			this.imageView = imageView;
		}
		
	}
}
