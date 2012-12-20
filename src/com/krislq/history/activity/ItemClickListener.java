package com.krislq.history.activity;

import android.view.View;
import android.view.View.OnClickListener;
/**
 * item click listener.<br>
 * accept a id variable. may be a string or integer.<br>
 * app will try to convert the id variable between string and integre.<br>
 * so the user can set a string id,and get a integer id.
 * 
 * @author <a href="mailto:kris@matchmovegames.com">Kris.lee</a>
 * @since  2011-12-13  1:07:31 pm
 * @version 1.0.0
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
