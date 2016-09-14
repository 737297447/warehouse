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
			int x = locations[0];// ��ȡ�����ǰλ�õĺ�����
			int y = locations[1];// ��ȡ�����ǰλ�õ�������
			int xLayoutSize = x + getWidth(add_shangpin) / 2;
			int yLayoutSize = y + getHeight(add_shangpin) / 2;

			PieMenu.setCenterLocation(xLayoutSize, yLayoutSize);
			PieMenu.setIconSize(15, 30);
			PieMenu.setTextSize(13);
			PieMenu.setCenterCircle(new Close(add_shangpin,text_details));
			PieMenu.addMenuEntry(new CircleOptions(text_details,"1", "��Ӧ����Ϣ","11","��ӹ�Ӧ��","12","��ѯ","13","�޸Ĺ�Ӧ��","14","ɾ��"));
			PieMenu.addMenuEntry(new CircleOptions(text_details,"2", "��Ʒ��Ϣ","21","�����Ʒ","22","��ѯ","23","�޸���Ʒ","24","ɾ��"));
			PieMenu.addMenuEntry(new CircleOptions(text_details,"3", "��Ʒ���","31","������","32","��ѯ","33","�޸����","34","ɾ��"));
			PieMenu.addMenuEntry(new CircleOptions(text_details,"4", "��Ʒ����","41","��ӳ���","42","��ѯ","43","�޸ĳ���","44","ɾ��"));
			PieMenu.setHeader("˳�򣺴����'��Ӧ��'-->'��Ʒ'-->'���'-->'����'", 14);
			ll.addView(PieMenu);
			add_shangpin.setVisibility(View.GONE);
			break;
		}

	}

}
