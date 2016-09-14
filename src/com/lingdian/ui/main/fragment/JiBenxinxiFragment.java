package com.lingdian.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lingdian.ui.main.tools.RadialMenuWidget;
import com.lingdian.warehouse.R;

public class JiBenxinxiFragment extends BaseFragment implements OnClickListener {
	

	private LinearLayout add_shangpin;
	private TextView text_details;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_jibenxinxi_layout, null);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	public void initView() {
		add_shangpin = (LinearLayout) this.getView().findViewById(
				R.id.add_shangpin);
		text_details = (TextView) this.getView().findViewById(R.id.text_details);
		add_shangpin.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_shangpin:
			PieMenu = new RadialMenuWidget(this.getActivity().getBaseContext());
			PieMenu.setAnimationSpeed(0L);

			int[] locations = new int[2];
			add_shangpin.getLocationOnScreen(locations);
			int x = locations[0];// 获取组件当前位置的横坐标
			int y = locations[1];// 获取组件当前位置的纵坐标
			int xLayoutSize = x + getWidth(add_shangpin) / 2;
			int yLayoutSize = y + getHeight(add_shangpin) / 2;

			PieMenu.setCenterLocation(xLayoutSize, yLayoutSize);
			PieMenu.setIconSize(15, 30);
			PieMenu.setTextSize(13);
			PieMenu.setCenterCircle(new Close(add_shangpin,text_details));
			PieMenu.addMenuEntry(new CircleOptions(text_details,"1", "供应商信息","11","添加供应商","12","查询","13","修改供应商","14","删除"));
			PieMenu.addMenuEntry(new CircleOptions(text_details,"2", "商品信息","21","添加商品","22","查询","23","修改商品","24","删除"));
			PieMenu.addMenuEntry(new CircleOptions(text_details,"3", "商品入库","31","添加入库","32","查询","33","修改入库","34","删除"));
			PieMenu.addMenuEntry(new CircleOptions(text_details,"4", "商品出库","41","添加出库","42","查询","43","修改出库","44","删除"));
			PieMenu.setHeader("顺序：从添加'供应商'-->'商品'-->'入库'-->'出库'", 14);
			ll.addView(PieMenu);
			add_shangpin.setVisibility(View.GONE);
			break;
		}

	}

}
