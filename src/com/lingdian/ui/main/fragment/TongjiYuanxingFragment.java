package com.lingdian.ui.main.fragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lingdian.ui.SqlHelpdemo;
import com.lingdian.ui.main.bean.SaleBean;
import com.lingdian.ui.main.tools.chart.ChartProp;
import com.lingdian.ui.main.tools.chart.ChartPropChangeListener;
import com.lingdian.ui.main.tools.chart.MyButton;
import com.lingdian.ui.main.tools.chart.PieView;
import com.lingdian.warehouse.R;

public class TongjiYuanxingFragment extends Fragment implements OnClickListener {

	private PieView pieView;
	private MyButton myButton;
	private TextView textView;

	private String id[];
	private String spname[];
	private String spprise[];
	private String spnum[];

	private int i = 0;
	private SqlHelpdemo db;
	private SQLiteDatabase sDatabase = null;
	private List<Float> percents;
	private List<Integer> colors;
	private List<String> names;

	List<SaleBean> listSale = new ArrayList<SaleBean>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.activity_yuanxing_tongji, null);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		getDataFromDb();
		initView();
	}

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		pieView.rotateEnable();
	}

	public void onPause() {
		super.onPause();

		if (pieView != null) {
			pieView.rotateDisable();
		}
	}

	public void initView() {

		pieView = (PieView) this.getView().findViewById(R.id.lotteryView);
		myButton = (MyButton) this.getView().findViewById(R.id.MyBt);
		textView = (TextView) this.getView().findViewById(R.id.MyTV);

		initItem();
		Message msg = new Message();
		msg.obj = pieView.getCurrentChartProp();
		handler.sendMessage(msg);

		pieView.setChartPropChangeListener(new ChartPropChangeListener() {

			@Override
			public void getChartProp(ChartProp chartProp) {
				Message msg = new Message();
				msg.obj = chartProp;
				handler.sendMessage(msg);

			}
		});

		pieView.start();

	}

	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			ChartProp chartProp = (ChartProp) msg.obj;
			myButton.setBackgroundPaintColor(chartProp.getColor());
			textView.setText(chartProp.getName());
			textView.setTextColor(chartProp.getColor());

		};
	};

	public void initItem() {
		int color[] = new int[] { Color.rgb(255, 193, 37),
				Color.rgb(255, 99, 71), Color.rgb(255, 0, 255),
				Color.rgb(192, 255, 62), Color.rgb(178, 58, 238),
				Color.rgb(178, 34, 34), Color.rgb(30, 144, 255),
				Color.rgb(0, 229, 238), Color.rgb(0, 0, 205),
				Color.rgb(139, 139, 0) };

		int allPrise = 0;
		List<ChartProp> acps = null;

		if (listSale.size() < 11 && listSale.size() > 0) {
			percents = new ArrayList<Float>();
			colors = new ArrayList<Integer>();
			names = new ArrayList<String>();

			for (int i = 0; i < listSale.size(); i++) {
				allPrise += listSale.get(i).getSpprise();
			}

			if (allPrise != 0) {
				for (int i = 0; i < listSale.size(); i++) {
					float p = listSale.get(i).getSpprise();
					float allP = (float) allPrise;
					float f = p / allP;
					percents.add(f);
					colors.add(color[i]);
					names.add("卖出  " + listSale.get(i).getSpname() + ":"
							+ listSale.get(i).getSpprise() + "元");
				}
			}

			acps = pieView.createCharts(listSale.size());
			pieView.setZjText(allPrise + "元");
		} else if (listSale.size() >= 11) {
			percents = new ArrayList<Float>();
			colors = new ArrayList<Integer>();
			names = new ArrayList<String>();

			for (int i = 0; i < 10; i++) {
				allPrise += listSale.get(i).getSpprise();
			}
			if (allPrise != 0) {
				for (int i = 0; i < 10; i++) {
					float p = listSale.get(i).getSpprise();
					float allP = (float) allPrise;
					float f = p / allP;
					percents.add(f);
					colors.add(color[i]);
					names.add("卖出  " + listSale.get(i).getSpname() + ":"
							+ listSale.get(i).getSpprise() + "元");
				}
			}
			acps = pieView.createCharts(10);
			pieView.setZjText(allPrise + "元");
		} else if (listSale.size() == 0) {

			percents = new ArrayList<Float>();
			colors = new ArrayList<Integer>();
			names = new ArrayList<String>();

			for (int i = 0; i < 10; i++) {
				percents.add(1.0f);
				colors.add(color[0]);
				names.add("统计卖出货物前十圆盘");
			}
			acps = pieView.createCharts(1);
			pieView.setZjText("还未卖出货物");
		}

		int size = acps.size();
		for (int i = 0; i < size; i++) {
			ChartProp chartProp = acps.get(i);
			chartProp.setName(names.get(i));
			chartProp.setColor(colors.get(i));
			chartProp.setPercent(percents.get(i));
		}

		pieView.initPercents();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

	}

	/** 从数据库中查询数据 */
	public void getDataFromDb() {
		db = new SqlHelpdemo(this.getActivity(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		List<SaleBean> slist = new ArrayList<SaleBean>();
		String selectStr = "select * from sale";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);
		cursor.moveToFirst();
		int count = cursor.getCount();

		id = new String[count];
		spname = new String[count];
		spprise = new String[count];
		spnum = new String[count];
		do {
			try {
				id[i] = cursor.getString(0);
				spname[i] = cursor.getString(1);
				spprise[i] = cursor.getString(2);
				spnum[i] = cursor.getString(3);
				i++;

			} catch (Exception e) {
				// TODO: handle exception
			}

		} while (cursor.moveToNext());

		for (int i = 0; i < id.length; i++) {
			SaleBean saleBean = new SaleBean();
			saleBean.setId(id[i]);
			saleBean.setSpname(spname[i]);
			int prise = Integer.valueOf(spprise[i]);
			saleBean.setSpprise(prise);
			saleBean.setSpnum(spnum[i]);
			slist.add(saleBean);
		}
		Collections.sort(slist);
		for (int i = slist.size() - 1; i >= 0; i--) {
			listSale.add(slist.get(i));
		}
	}

}