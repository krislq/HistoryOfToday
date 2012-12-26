package com.krislq.history.activity;

import android.view.View;
import android.view.View.OnClickListener;
/**
 * 
 * @author <a href="mailto:kris1987@qq.com">Kris.lee</a>
 * @date 2012-12-26
 * @version 1.0.0
 *
 */
public abstract class ItemClickListener implements OnClickListener {
	protected int index;
	protected View view;
	public ItemClickListener(View view, int index)
	{
		this.view = view;
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}
	
}
