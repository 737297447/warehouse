package com.lingdian.ui.main.fragment;


import com.lingdian.warehouse.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class KucunFragment extends Fragment{
	static Context mContext;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		View v = inflater.inflate(R.layout.fragment_kucun_layout, null);
		return v;
	}


}
