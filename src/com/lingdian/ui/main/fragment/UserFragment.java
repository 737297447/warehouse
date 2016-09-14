package com.lingdian.ui.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lingdian.ui.main.tools.RadialMenuWidget;
import com.lingdian.warehouse.R;

public class UserFragment extends BaseFragment implements OnClickListener {

	private LinearLayout add_user;
	private LinearLayout add_kehu;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_user_layout, null);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	public void initView() {
		add_user = (LinearLayout) this.getView().findViewById(R.id.add_user);
		add_kehu = (LinearLayout) this.getView().findViewById(R.id.add_kehu);
		add_user.setOnClickListener(this);
		add_kehu.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_user:
			PieMenu = new RadialMenuWidget(this.getActivity().getBaseContext());
			PieMenu.setAnimationSpeed(0L);
			int[] locations = new int[2];
			add_user.getLocationOnScreen(locations);
			
			int x = locations[0];// 获取组件当前位置的横坐标
			int y = locations[1];// 获取组件当前位置的纵坐标
			int xLayoutSize = x + getWidth(add_user) / 2;
			int yLayoutSize = y + getHeight(add_user) / 2;

			PieMenu.setCenterLocation(xLayoutSize, yLayoutSize);
			PieMenu.setIconSize(15, 30);
			PieMenu.setTextSize(13);
			PieMenu.setCenterCircle(new Close(add_user,null));
			PieMenu.addMenuEntry(new Menu1("5", "用户管理"));
			PieMenu.addMenuEntry(new Menu1("6", "修改密码"));
			
			PieMenu.setHeader("用户管理", 14);
			ll.addView(PieMenu);
			add_user.setVisibility(View.GONE);
			break;

		case R.id.add_kehu:
			PieMenu = new RadialMenuWidget(this.getActivity().getBaseContext());
			PieMenu.setAnimationSpeed(0L);
			int[] locations1 = new int[2];
			add_kehu.getLocationOnScreen(locations1);
			
			int x1 = locations1[0];// 获取组件当前位置的横坐标
			int y1 = locations1[1];// 获取组件当前位置的纵坐标
			int xLayoutSize1 = x1 + getWidth(add_kehu) / 2;
			int yLayoutSize1 = y1 + getHeight(add_kehu) / 2;

			PieMenu.setCenterLocation(xLayoutSize1, yLayoutSize1);
			PieMenu.setIconSize(15, 30);
			PieMenu.setTextSize(13);
			PieMenu.setCenterCircle(new Close(add_kehu,null));
			PieMenu.addMenuEntry(new Menu1("7", "添加顾客"));
			PieMenu.addMenuEntry(new Menu1("8", "查询"));
			PieMenu.addMenuEntry(new Menu1("9", "修改顾客"));
			PieMenu.addMenuEntry(new Menu1("10", "删除"));
			
			PieMenu.setHeader("顾客管理", 14);
			ll.addView(PieMenu);
			add_kehu.setVisibility(View.GONE);
			break;

		}
	}

}
