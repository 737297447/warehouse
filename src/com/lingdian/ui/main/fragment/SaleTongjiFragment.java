package com.lingdian.ui.main.fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.lingdian.ui.main.tools.RadialMenuWidget;
import com.lingdian.warehouse.R;

public class SaleTongjiFragment extends BaseFragment implements OnClickListener {

	private LinearLayout add_sale;

	private FragmentTransaction ft;
	private TongjiYuanxingFragment fragment;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_saletongji_layout, null);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	public void initView() {
		add_sale = (LinearLayout) this.getView().findViewById(R.id.add_sale);
		add_sale.setOnClickListener(this);
		ft = this.getActivity().getSupportFragmentManager().beginTransaction();
		// �����ص����е�Fragment���Է�ֹ�ж��Fragment��ʾ�ڽ����ϵ����
		hideFragments(ft);
		if (fragment == null) {
			fragment = new TongjiYuanxingFragment();
			ft.add(R.id.content, fragment);
		} else {
			ft.show(fragment);
		}
		ft.commit();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.add_sale:
			PieMenu = new RadialMenuWidget(this.getActivity().getBaseContext());
			PieMenu.setAnimationSpeed(0L);
			int[] locations = new int[2];
			add_sale.getLocationOnScreen(locations);

			int x = locations[0];// ��ȡ�����ǰλ�õĺ�����
			int y = locations[1];// ��ȡ�����ǰλ�õ�������
			int xLayoutSize = x + getWidth(add_sale) / 2;
			int yLayoutSize = y + getHeight(add_sale) / 2;

			PieMenu.setCenterLocation(xLayoutSize, yLayoutSize);
			PieMenu.setIconSize(15, 30);
			PieMenu.setTextSize(13);
			PieMenu.setCenterCircle(new Close(add_sale, null));
			PieMenu.addMenuEntry(new Menu1("11", "��������"));
			PieMenu.addMenuEntry(new Menu1("12", "���ͳ��"));

			PieMenu.setHeader("�ұ�Բ���Ƕ�����ǰ10����Ʒͳ��-->", 14);
			ll.addView(PieMenu);
			add_sale.setVisibility(View.GONE);
			break;
		}
	}

	/**
	 * �����е�Fragment����Ϊ����״̬��
	 * 
	 * @param transaction
	 *            ���ڶ�Fragmentִ�в���������
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (fragment != null) {
			transaction.hide(fragment);
		}
	}
	
}
