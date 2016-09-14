package com.lingdian.ui.main.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lingdian.ui.main.tools.RadialMenuWidget;
import com.lingdian.warehouse.R;

public class SaleFragment extends BaseFragment implements OnClickListener{

	private LinearLayout add_maihuo;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_sale_layout, null);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	
	public void initView(){
		add_maihuo = (LinearLayout) this.getView().findViewById(R.id.add_maihuo);
		add_maihuo.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_maihuo:
			PieMenu = new RadialMenuWidget(this.getActivity().getBaseContext());
			PieMenu.setAnimationSpeed(0L);
			int[] locations = new int[2];
			add_maihuo.getLocationOnScreen(locations);
			int x = locations[0];// 获取组件当前位置的横坐标
			int y = locations[1];// 获取组件当前位置的纵坐标
			int xLayoutSize = x + getWidth(add_maihuo) / 2;
			int yLayoutSize = y + getHeight(add_maihuo) / 2;

			PieMenu.setCenterLocation(xLayoutSize, yLayoutSize);
			PieMenu.setIconSize(15, 30);
			PieMenu.setTextSize(13);
			PieMenu.setCenterCircle(new Close(add_maihuo, null));
			PieMenu.addMenuEntry(new Menu1("13", "点击卖货"));
			PieMenu.addMenuEntry(new Menu1("14", "生成二维码"));
			PieMenu.setHeader("包括'点击卖货'和'生成二维码'", 14);
			ll.addView(PieMenu);
			add_maihuo.setVisibility(View.GONE);
			break;
		}
		
	}
	
	
	
	
	

	

}
