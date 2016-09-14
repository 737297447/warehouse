package com.lingdian.ui.main.fragment;


import java.util.ArrayList;
import java.util.List;

import com.lingdian.ui.about.fragment.TestFragment;
import com.lingdian.ui.about.fragment.TextBean;
import com.lingdian.ui.about.fragment.Utils;
import com.lingdian.ui.about.view.CardAdapter;
import com.lingdian.ui.about.view.CardView;
import com.lingdian.ui.about.view.CardView.OnCardClickListener;
import com.lingdian.warehouse.R;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AboutFragment extends BaseFragment implements OnCardClickListener{

	private List<TextBean> titleList;
	private TestFragment frag;
	
	protected static final String[] title = new String[] { "关于我们", 
		"首页详细说明", "统计详细说明", "用户详细说明","售货详细说明" };
	protected static final int[] type1 = new int[] { R.string.guanyuwomen, 
		R.string.shouyeshuoming, R.string.tongjishuoming, R.string.yonghushuoming,R.string.shouhuoshuoming };
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_kucun_layout, null);
		return v;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
	}
	
	public void initView(){
		CardView cardView = (CardView) this.getActivity().findViewById(R.id.cardView1);
		cardView.setOnCardClickListener(this);
		cardView.setItemSpace(Utils.convertDpToPixelInt(mContext, 20));
		
		MyCardAdapter adapter = new MyCardAdapter(mContext);
		adapter.addAll(initData());
		
		cardView.setAdapter(adapter);
		
		FragmentManager manager = this.getActivity().getSupportFragmentManager();
		frag = new TestFragment();
		manager.beginTransaction().add(R.id.contentView, frag).commit();
		
	}

	
	private List<TextBean> initData() {
		titleList = new ArrayList<TextBean>();
		for(int i=0;i< title.length;i++){
			TextBean textBean = new TextBean();
			textBean.setTitle(title[i]);
			textBean.setContact(type1[i]);
			titleList.add(textBean);
		}
		return titleList;
	}
	
	
	public class MyCardAdapter extends CardAdapter<TextBean>{

		public MyCardAdapter(Context context) {
			super(context);
		}
		
		@Override
		public int getCount() {
			return Integer.MAX_VALUE;
		}
		
		@Override
		protected View getCardView(int position,
				View convertView, ViewGroup parent) {
			if(convertView == null) {
				LayoutInflater inflater = LayoutInflater.from(mContext);
				convertView = inflater.inflate(R.layout.item_layout, parent, false);
			}
			TextView tv1 = (TextView) convertView.findViewById(R.id.textView1);
			TextView tv2 = (TextView) convertView.findViewById(R.id.textView2);
			TextBean textBean = getItem(position%titleList.size());
			
			tv1.setText(textBean.getTitle());
			tv2.setText(textBean.getContact());
			return convertView;
		}
	}




	@Override
	public void onCardClick(View view, int position) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putString("title", titleList.get(position%titleList.size()).getTitle());
		bundle.putInt("contact", titleList.get(position%titleList.size()).getContact());
		frag.show(view,bundle);
	}
}
