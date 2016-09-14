package com.lingdian.ui.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lingdian.warehouse.R;

public class MyAdapter extends BaseAdapter {

	private List<String> list = null;
	private Context mContext;
	
	public MyAdapter(Context mContext, List<String> myList) {
		this.mContext = mContext;
		this.list = myList;
	}
	public int getCount() {
		return this.list.size();
	}

	public Object getItem(int position) {
		return list.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	ViewHolder viewHolder = null;
	public View getView(final int position, View view, ViewGroup arg2) {
		if (view == null) {
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(mContext).inflate(R.layout.layout_dlist_itme, null);
			viewHolder.tvTitle = (TextView) view.findViewById(R.id.listtv);
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		viewHolder.tvTitle.setText(this.list.get(position));
//		if(position==0){
//			view.setBackgroundResource(R.drawable.dsxz_img);
//		}
		
		return view;

	}
	final static class ViewHolder {
		TextView tvTitle;
	}
}