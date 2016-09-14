package com.lingdian.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.lingdian.ui.goods.GoodsAddAvtivity;
import com.lingdian.ui.sale.SaleSearchActivity;
import com.lingdian.util.ui.dialog.ChooseRemindDialog;
import com.lingdian.warehouse.R;
import com.zxing.activity.CaptureActivity;

/**
 * 主菜单页面
 * 
 * @author 田志远
 * 
 */

public class MenuDemo extends TabActivity {
	/**
	 * 添加用户键
	 */
	private Button tianjia1;
	/**
	 * 修改用户键
	 */
	private Button xiugai1;
	/**
	 * 删除用户键
	 */
	private Button shanchu1;
	/**
	 * 查询用户键
	 */
	private Button chaxun1;
	/**
	 * 添加商品键
	 */
	private Button tianjia2;
	/**
	 * 修改商品键
	 */
	private Button xiugai2;
	/**
	 * 删除商品键
	 */
	private Button shanchu2;
	/**
	 * 查询商品键
	 */
	private Button chaxun2;
	/**
	 * 添加供应商键
	 */
	private Button tianjia3;
	/**
	 * 修改供应商键
	 */
	private Button xiugai3;
	/**
	 * 删除供应商键
	 */
	private Button shanchu3;
	/**
	 * 查询供应商键
	 */
	private Button chaxun3;
	/**
	 * 添加入库键
	 */
	private Button tianjia4;
	/**
	 * 修改入库键
	 */
	private Button xiugai4;
	/**
	 * 删除入库键
	 */
	private Button shanchu4;
	/**
	 * 查询入库键
	 */
	private Button chaxun4;
	/**
	 * 添加出库键
	 */
	private Button tianjia5;
	/**
	 * 修改出库键
	 */
	private Button xiugai5;
	/**
	 * 删除出库键
	 */
	private Button shanchu5;
	/**
	 * 查询出库键
	 */
	private Button chaxun5;
	/**
	 * 用户管理键
	 */
	private Button yonghu;
	/**
	 * 修改密码键
	 */
	private Button mima;

	private LinearLayout detail_linear_layout_maihuo;
	private TextView all_prise;

	String names = null;
	TabHost tab;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tab = getTabHost();
		tab.setPadding(0, -20, 0, 0);

		tab.setDrawingCacheBackgroundColor(Color.GREEN);
		LayoutInflater inf = getLayoutInflater();
		inf.inflate(R.layout.menudemo, tab.getTabContentView());

		Bundle name = getIntent().getExtras();
		names = name.getString("username");
		System.out.println(names);

		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();

		/**
		 * 所有按钮初始化
		 */
		tianjia1 = (Button) findViewById(R.id.tianjia1);
		xiugai1 = (Button) findViewById(R.id.xiugai1);
		shanchu1 = (Button) findViewById(R.id.shanchu1);
		chaxun1 = (Button) findViewById(R.id.chaxun1);
		tianjia2 = (Button) findViewById(R.id.tianjia2);
		xiugai2 = (Button) findViewById(R.id.xiugai2);
		shanchu2 = (Button) findViewById(R.id.shanchu2);
		chaxun2 = (Button) findViewById(R.id.chaxun2);
		tianjia3 = (Button) findViewById(R.id.tianjia3);
		xiugai3 = (Button) findViewById(R.id.xiugai3);
		shanchu3 = (Button) findViewById(R.id.shanchu3);
		chaxun3 = (Button) findViewById(R.id.chaxun3);
		tianjia4 = (Button) findViewById(R.id.tianjia4);
		xiugai4 = (Button) findViewById(R.id.xiugai4);
		shanchu4 = (Button) findViewById(R.id.shanchu4);
		chaxun4 = (Button) findViewById(R.id.chaxun4);
		tianjia5 = (Button) findViewById(R.id.tianjia5);
		xiugai5 = (Button) findViewById(R.id.xiugai5);
		shanchu5 = (Button) findViewById(R.id.shanchu5);
		chaxun5 = (Button) findViewById(R.id.chaxun5);
		yonghu = (Button) findViewById(R.id.yonghu);
		all_prise = (TextView) findViewById(R.id.all_prise);
		detail_linear_layout_maihuo = (LinearLayout) findViewById(R.id.detail_linear_layout_maihuo);
		mima = (Button) findViewById(R.id.mima);
		if (!names.equals("pushaolong")) {
			System.out.println("111111111111111111");
			yonghu.setVisibility(View.INVISIBLE);
		}
		/**
		 * tabhost.tabspec创建
		 */
		final TabHost.TabSpec tabs1 = tab.newTabSpec("基本信息");
		tabs1.setContent(R.id.li1);
		final TabHost.TabSpec tabs2 = tab.newTabSpec("库存管理");
		tabs2.setContent(R.id.li2);
		final TabHost.TabSpec tabs3 = tab.newTabSpec("信息查询");
		tabs3.setContent(R.id.li3);
		final TabHost.TabSpec tabs4 = tab.newTabSpec("扫码售货");
		tabs4.setContent(R.id.li4);
		final TabHost.TabSpec tabs5 = tab.newTabSpec("用户管理");
		tabs5.setContent(R.id.li5);
		tab.addTab(tabs1);
		tab.addTab(tabs2);
		tab.addTab(tabs3);
		tab.addTab(tabs5);
		tab.addTab(tabs4);
		updateTab(tab);
		tab.setOnTabChangedListener(new OnTabChangedListener());
	}

	class OnTabChangedListener implements OnTabChangeListener {

		@Override
		public void onTabChanged(String tabId) {
			tab.setCurrentTabByTag(tabId);
			updateTab(tab);
		}
	}

	/**
	 * 更新Tab标签的颜色，和字体的颜色
	 * 
	 * @param tabHost
	 */
	private void updateTab(final TabHost tabHost) {
		for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			View view = tabHost.getTabWidget().getChildAt(i);
			TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i)
					.findViewById(android.R.id.title);
			tv.setTextSize(16);
			tv.setTypeface(Typeface.SERIF, 2); // 设置字体和风格
			if (tabHost.getCurrentTab() == i) {// 选中
				view.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.shequ_wzxq_huifu_send_press));// 选中后的背景
				tv.setTextColor(this.getResources().getColorStateList(
						android.R.color.white));
			} else {// 不选中
				view.setBackgroundDrawable(getResources().getDrawable(
						R.drawable.gonggao_zhuye_gonggaobg_press));// 非选择的背景
				tv.setTextColor(this.getResources().getColorStateList(
						R.color.green));
			}
		}
	}

	/**
	 * 商品信息按钮监听
	 * 
	 * @param v
	 */
	public void onshangpin(View v) {
		tianjia1.setVisibility(View.VISIBLE);
		xiugai1.setVisibility(View.VISIBLE);
		shanchu1.setVisibility(View.VISIBLE);
		chaxun1.setVisibility(View.VISIBLE);
		tianjia2.setVisibility(View.INVISIBLE);
		xiugai2.setVisibility(View.INVISIBLE);
		shanchu2.setVisibility(View.INVISIBLE);
		chaxun2.setVisibility(View.INVISIBLE);
		tianjia3.setVisibility(View.INVISIBLE);
		xiugai3.setVisibility(View.INVISIBLE);
		shanchu3.setVisibility(View.INVISIBLE);
		chaxun3.setVisibility(View.INVISIBLE);
	}

	/**
	 * 供应商信息按钮监听
	 * 
	 * @param v
	 */
	public void ongongyingshang(View v) {
		tianjia3.setVisibility(View.VISIBLE);
		xiugai3.setVisibility(View.VISIBLE);
		shanchu3.setVisibility(View.VISIBLE);
		chaxun3.setVisibility(View.VISIBLE);
		tianjia2.setVisibility(View.INVISIBLE);
		xiugai2.setVisibility(View.INVISIBLE);
		shanchu2.setVisibility(View.INVISIBLE);
		chaxun2.setVisibility(View.INVISIBLE);
		tianjia1.setVisibility(View.INVISIBLE);
		xiugai1.setVisibility(View.INVISIBLE);
		shanchu1.setVisibility(View.INVISIBLE);
		chaxun1.setVisibility(View.INVISIBLE);
	}

	/**
	 * 客户信息按钮监听
	 * 
	 * @param v
	 */
	public void onkehu(View v) {
		tianjia2.setVisibility(View.VISIBLE);
		xiugai2.setVisibility(View.VISIBLE);
		shanchu2.setVisibility(View.VISIBLE);
		chaxun2.setVisibility(View.VISIBLE);
		tianjia1.setVisibility(View.INVISIBLE);
		xiugai1.setVisibility(View.INVISIBLE);
		shanchu1.setVisibility(View.INVISIBLE);
		chaxun1.setVisibility(View.INVISIBLE);
		tianjia3.setVisibility(View.INVISIBLE);
		xiugai3.setVisibility(View.INVISIBLE);
		shanchu3.setVisibility(View.INVISIBLE);
		chaxun3.setVisibility(View.INVISIBLE);
	}

	/**
	 * 商品入库信息按钮监听
	 * 
	 * @param v
	 */
	public void onruku(View v) {
		tianjia4.setVisibility(View.VISIBLE);
		xiugai4.setVisibility(View.VISIBLE);
		shanchu4.setVisibility(View.VISIBLE);
		chaxun4.setVisibility(View.VISIBLE);
		tianjia5.setVisibility(View.INVISIBLE);
		xiugai5.setVisibility(View.INVISIBLE);
		shanchu5.setVisibility(View.INVISIBLE);
		chaxun5.setVisibility(View.INVISIBLE);

	}

	/**
	 * 商品出库信息按钮监听
	 * 
	 * @param v
	 */
	public void onchuku(View v) {
		tianjia5.setVisibility(View.VISIBLE);
		xiugai5.setVisibility(View.VISIBLE);
		shanchu5.setVisibility(View.VISIBLE);
		chaxun5.setVisibility(View.VISIBLE);
		tianjia4.setVisibility(View.INVISIBLE);
		xiugai4.setVisibility(View.INVISIBLE);
		shanchu4.setVisibility(View.INVISIBLE);
		chaxun4.setVisibility(View.INVISIBLE);

	}

	/**
	 * 添加供应商按钮监听
	 * 
	 * @param v
	 */
	public void tianjiag(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("username", names);
		intent.putExtras(bundle);
		intent.setClass(getApplicationContext(), Tianjiag.class);
		startActivity(intent);
	}

	/**
	 * 查询供应商按钮监听
	 * 
	 * @param v
	 */
	public void chaxung(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("username", names);
		intent.putExtras(bundle);
		intent.setClass(getApplicationContext(), ChaxungGysActivity.class);
		startActivity(intent);

	}

	/**
	 * 添加入库信息按钮监听
	 * 
	 * @param v
	 */
	public void tianjiar(View v) {

		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("username", names);
		intent.putExtras(bundle);
		intent.setClass(getApplicationContext(), TianjiaRukuActivity.class);
		startActivity(intent);

	}

	/**
	 * 查询入库信息按钮监听
	 * 
	 * @param v
	 */
	public void chaxunruku(View v) {

		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("username", names);
		intent.putExtras(bundle);
		intent.setClass(getApplicationContext(), ChaxunRukuActivity.class);
		startActivity(intent);

	}

	/**
	 * 添加商品信息按钮监听
	 * 
	 * @param v
	 */
	public void ontianjias(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("username", names);
		intent.putExtras(bundle);
		intent.setClass(getApplicationContext(), GoodsAddAvtivity.class);
		startActivity(intent);

	}

	/**
	 * 添加客户信息按钮监听
	 * 
	 * @param v
	 */
	public void tianjiak(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("username", names);
		intent.putExtras(bundle);
		intent.setClass(getApplicationContext(), Tianjiak.class);
		startActivity(intent);

	}

	/**
	 * 查询客户信息按钮监听
	 * 
	 * @param v
	 */
	public void chaxunk(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("username", names);
		intent.putExtras(bundle);
		intent.setClass(getApplicationContext(), ChaxunKehuAvtivity.class);
		startActivity(intent);

	}

	/**
	 * 添加出库按钮监听
	 * 
	 * @param v
	 */
	public void tianjiac(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("username", names);
		intent.putExtras(bundle);
		intent.setClass(getApplicationContext(), TianjiaChukuActivity.class);
		startActivity(intent);
	}

	/**
	 * 查询出库按钮监听
	 * 
	 * @param v
	 */
	public void chaxunc(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("username", names);
		intent.putExtras(bundle);
		intent.setClass(getApplicationContext(), ChaxunChukuActivity.class);
		startActivity(intent);
	}

	/**
	 * 修改密码按钮监听
	 * 
	 * @param v
	 */

	public void mima(View v) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("username", names);
		intent.putExtras(bundle);
		intent.setClass(getApplicationContext(), Xiugai.class);
		startActivity(intent);
	}

	/**
	 * 用户管理按钮监听
	 * 
	 * @param v
	 */
	public void yonghu(View v) {

		Intent intent = new Intent();
		Bundle bundle = new Bundle();
		bundle.putString("username", names);
		intent.putExtras(bundle);
		intent.setClass(getApplicationContext(), Yonghu.class);
		startActivity(intent);
	}

	/**
	 * 删除商品按钮监听
	 * 
	 * @param v
	 */
	public void shanchus(View v) {
		Intent intent = new Intent();
		intent.setClass(MenuDemo.this, Shanchus.class);
		startActivity(intent);
	}

	/**
	 * 删除客户按钮监听
	 * 
	 * @param v
	 */

	public void shanchuk(View v) {
		Intent intent = new Intent();
		intent.setClass(MenuDemo.this, Shanchuk.class);
		startActivity(intent);
	}

	/**
	 * 查询商品按钮监听
	 * 
	 * @param v
	 */
	public void chaxuns(View v) {
		Intent intent = new Intent();
		intent.setClass(MenuDemo.this, ChaxunShangpinActivity.class);
		startActivity(intent);
	}

	/**
	 * 删除供应商按钮监听
	 * 
	 * @param v
	 */
	public void shanchug(View v) {
		Intent intent = new Intent();
		intent.setClass(MenuDemo.this, Shanchug.class);
		startActivity(intent);
	}

	/**
	 * 修改商品按钮监听
	 * 
	 * @param v
	 */
	public void xiugais(View v) {
		Intent intent = new Intent();
		intent.setClass(MenuDemo.this, Xiugais.class);
		startActivity(intent);
	}

	/**
	 * 修改客户按钮监听
	 * 
	 * @param v
	 */
	public void xiugaik(View v) {
		Intent intent = new Intent();
		intent.setClass(MenuDemo.this, Xiugaik.class);
		startActivity(intent);
	}

	/**
	 * 修改供应商按钮监听
	 * 
	 * @param v
	 */
	public void xiugaig(View v) {
		Intent intent = new Intent();
		intent.setClass(MenuDemo.this, Xiugaig.class);
		startActivity(intent);
	}

	/**
	 * 删除出库按钮监听
	 * 
	 * @param v
	 */
	public void shanchuc(View v) {
		Intent intent = new Intent();
		intent.setClass(MenuDemo.this, Shanchuc.class);
		startActivity(intent);
	}

	/**
	 * 删除入库按钮监听
	 * 
	 * @param v
	 */
	public void shanchur(View v) {
		Intent intent = new Intent();
		intent.setClass(MenuDemo.this, Shanchur.class);
		startActivity(intent);
	}

	/**
	 * 修改入库按钮监听
	 * 
	 * @param v
	 */
	public void xiugair(View v) {
		Intent intent = new Intent();
		intent.setClass(MenuDemo.this, Xiugair.class);
		startActivity(intent);
	}

	/**
	 * 修改出库按钮监听
	 * 
	 * @param v
	 */
	public void xiugaic(View v) {
		Intent intent = new Intent();
		intent.setClass(MenuDemo.this, Xiugaic.class);
		startActivity(intent);
	}

	/**
	 * 库存信息查询按钮监听
	 * 
	 * @param v
	 */
	public void kucun(View v) {
		Intent intent = new Intent();
		intent.setClass(MenuDemo.this, Kucun.class);
		startActivity(intent);
	}

	/**
	 * 库存信息查询按钮监听
	 * 
	 * @param v
	 */
	public void Sale(View v) {
		Intent intent = new Intent();
		intent.setClass(MenuDemo.this, SaleSearchActivity.class);
		startActivity(intent);
	}

	
	
	
	/**
	 * 扫码卖货
	 * 
	 * @param nzl
	 */
	SqlHelpdemo db;
	SQLiteDatabase sDatabase = null;

	/** 卖货 */
	public void maihuo(View v) {
		Intent openCameraIntent = new Intent(MenuDemo.this,
				CaptureActivity.class);
		startActivityForResult(openCameraIntent, 0);
	}

	/** 结账 */
	public void jiezhang(View v) {

		if (detail_linear_layout_maihuo.getChildCount() != 0) {
			getAllData(detail_linear_layout_maihuo);
			all_prise.setText("0");
			detail_linear_layout_maihuo.removeAllViews();
		} else {
			Toast.makeText(MenuDemo.this, "当前扫码单中还没有扫描货物，请点击“扫码卖货”进行扫码！",
					Toast.LENGTH_SHORT).show();
		}

	}

	String huowuName;
	String huowuPrise;
	String huowuNum;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");

			String selectStr = "select * from chuku where guige like ?";
			Cursor cursor = sDatabase.rawQuery(selectStr,
					new String[] { scanResult });
			int count = cursor.getCount();
			if (count != 0) {
				if (cursor.moveToFirst()) {
					huowuName = cursor.getString(4);
					huowuPrise = cursor.getString(7);
					huowuNum = cursor.getString(8);
				}
				cursor.close();

				if (!TextUtils.isEmpty(huowuName)) {
					// 第一次添加货物页面没有数据
					if (detail_linear_layout_maihuo.getChildCount() == 0) {
						addField(huowuName, huowuPrise);
					} else {
						checkName(detail_linear_layout_maihuo, huowuName,
								huowuPrise);
					}
					int allPrise = getPrise(detail_linear_layout_maihuo);
					all_prise.setText(allPrise + "");
				}
			} else {
				DialogDemo.builder(MenuDemo.this, "提示","该货物还没有出库，请选择“添加出库”按钮选择出库，才能销售");
			}
		}
	}

	/** 添加字段 */
	private void addField(String keyName, String valueName) {
		final View view = getLayoutInflater().inflate(
				R.layout.activity_maihuo_details_layout, null);
		final ViewHolder holder = new ViewHolder();
		holder.detail_layout_key = (TextView) view
				.findViewById(R.id.detail_layout_key);
		holder.detail_layout_valye = (TextView) view
				.findViewById(R.id.detail_layout_valye);
		holder.detail_layout_close = (Button) view
				.findViewById(R.id.detail_layout_close);
		holder.detail_layout_num = (TextView) view
				.findViewById(R.id.detail_layout_num);

		detail_linear_layout_maihuo.addView(view);
		view.setTag(holder);

		holder.detail_layout_key.setText(keyName);
		holder.detail_layout_valye.setText(valueName);
		holder.detail_layout_num.setText("1");

		holder.detail_layout_close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				String nowNum = holder.detail_layout_num.getText().toString();
				if (!nowNum.equals("1")) {
					int iNowNum = Integer.valueOf(nowNum);
					--iNowNum;
					holder.detail_layout_num.setText(iNowNum + "");
					int allPrise = getPrise(detail_linear_layout_maihuo);
					all_prise.setText(allPrise + "");
					Toast.makeText(MenuDemo.this, "( ͡° ͜ʖ ͡°) 当前货物数量 -1 ！",
							Toast.LENGTH_SHORT).show();
				} else {
					// 删除状态
					ChooseRemindDialog rmdDlg = new ChooseRemindDialog(
							MenuDemo.this, R.style.oa_MyDialogStyleTop, "请确认",
							"确定要删除此货物么", "删除", "取消") {
						@Override
						public void doBtn1Click() {
							this.dismiss();

							detail_linear_layout_maihuo.removeView(view);

							int allPrise = getPrise(detail_linear_layout_maihuo);
							all_prise.setText(allPrise + "");
						}

						@Override
						public void doBtn2Click() {
							this.dismiss();
						}
					};
					rmdDlg.show();
				}
			}
		});

	}

	public void updataNumView(ViewHolder holder) {
		String num = holder.detail_layout_num.getText().toString();
		int i = Integer.valueOf(num);
		++i;
		holder.detail_layout_num.setText(i + "");
	}

	class ViewHolder {
		public TextView detail_layout_key;
		public TextView detail_layout_valye;
		public TextView detail_layout_num;
		public Button detail_layout_close;
	}

	private int getPrise(LinearLayout linearLayout) {
		int onePrise = 0;
		int prise = 0;
		for (int i = 0; i < linearLayout.getChildCount(); i++) {
			View view = linearLayout.getChildAt(i);
			ViewHolder holder = (ViewHolder) view.getTag();
			if (holder != null) {
				String sPrise = holder.detail_layout_valye.getText().toString()
						.trim();
				String sNum = holder.detail_layout_num.getText().toString()
						.trim();
				if (!TextUtils.isEmpty(sPrise)) {
					int iPrise = Integer.valueOf(sPrise);
					int iNum = Integer.valueOf(sNum);
					onePrise = iPrise * iNum;
					prise += onePrise;
				}
			}
		}
		return prise;
	}

	/** 检测第二次以后添加的货物在页面上是否有数据，如果有，在后面数据+1，如果没有，新增一条新货物 */
	private void checkName(LinearLayout linearLayout, String name, String prise) {
		boolean isAdd = false;
		for (int i = 0; i < linearLayout.getChildCount(); i++) {
			View view = linearLayout.getChildAt(i);
			ViewHolder holder = (ViewHolder) view.getTag();
			String lName = holder.detail_layout_key.getText().toString();
			if (lName.equals(name)) {
				if (holder != null) {
					updataNumView(holder);
				}
				// 当判断第二次扫的书本名称在之前已经有了，直接跳出，不再继续循环
				return;
			} else {
				isAdd = true;
			}
		}
		if (isAdd) {
			addField(name, prise);
		}

	}

	private void getAllData(LinearLayout linearLayout) {

		for (int i = 0; i < linearLayout.getChildCount(); i++) {
			View view = linearLayout.getChildAt(i);
			ViewHolder holder = (ViewHolder) view.getTag();
			if (holder != null) {
				String sName = holder.detail_layout_key.getText().toString()
						.trim();
				String sPrise = holder.detail_layout_valye.getText().toString()
						.trim();
				String sNum = holder.detail_layout_num.getText().toString()
						.trim();
				deleteChukuData(sName, sPrise, sNum);

			}
		}

	}

	/** 插入到商品销售的表中 */
	private void insertSale(String sName, String sPrise, String sNum) {
		String names[];
		String prises[];
		String nums[];
		int i = 0;
		String sqlSelect = "select spname,spprise,spnum from sale";
		Cursor cursor = sDatabase.rawQuery(sqlSelect, null);
		cursor.moveToFirst();
		int count = cursor.getCount();
		if (count != 0) {
			names = new String[count];
			prises = new String[count];
			nums = new String[count];
			do {
				names[i] = cursor.getString(0);
				prises[i] = cursor.getString(1);
				nums[i] = cursor.getString(2);
				i++;
			} while (cursor.moveToNext());
			cursor.close();

			boolean updataOrInsert = false;

			for (int j = 0; j < names.length; j++) {

				if (sName.equals(names[j])) {
					int oldPrise = Integer.valueOf(prises[j]);
					int nowPrise = oldPrise + Integer.valueOf(sPrise);
					int oldNum = Integer.valueOf(nums[j]);
					int nowNum = oldNum + Integer.valueOf(sNum);
					sDatabase.execSQL("update sale set spprise='" + nowPrise
							+ "',spnum='" + nowNum + "' where spname ='"
							+ sName + "'");
					return;
				} else {
					updataOrInsert = true;
				}
			}

			if (updataOrInsert) {
				int id = 0;
				String select = "select max(_id) from sale";
				Cursor seCursor = sDatabase.rawQuery(select, null);
				try {
					seCursor.moveToFirst();
					id = Integer.parseInt(seCursor.getString(0));
					id += 1;
				} catch (Exception e) {
					// TODO: handle exception
					id = 0;
				}
				sDatabase.execSQL("insert into sale values('" + id + "','"
						+ sName + "','" + sPrise + "','" + sNum + "')");
				seCursor.close();
			}

		} else {
			int firstId = 0;
			sDatabase.execSQL("insert into sale values('" + firstId + "','"
					+ sName + "','" + sPrise + "','" + sNum + "')");
			return;
		}

	}

	/** 当卖货完成时，从出库数据库将卖出的货物数量删除 */
	public void deleteChukuData(String sName, String sPrise, String sNum) {

		String nowChukushu = null;
		String selectStr4 = "select num from chuku where products='" + sName
				+ "'";
		Cursor cursor4 = sDatabase.rawQuery(selectStr4, null);
		do {
			try {
				nowChukushu = cursor4.getString(0);

			} catch (Exception e) {
				// TODO: handle exception
			}
		} while (cursor4.moveToNext());

		int chukuInt = Integer.valueOf(nowChukushu);// 仓库中数量
		int iNum = Integer.valueOf(sNum);// 卖出的数量
		if (chukuInt < iNum) {
			DialogDemo
					.builder(MenuDemo.this, "提示",
							"仓库中没有这么多货物，交易不成功，请不要将一个商品扫码两次！！如果操作正确，请点击“查询出库”查看库存数量，是否出库正确");
		} else {
			int shengyuInt = chukuInt - iNum;// 剩余多少
			sDatabase.execSQL("update chuku set num='" + shengyuInt
					+ "' where products ='" + sName + "'");
			
			insertSale(sName, sPrise, sNum);
			String money = all_prise.getText().toString().trim();
			Toast.makeText(MenuDemo.this, "结账成功，请收取" + money + "元钱",
					Toast.LENGTH_SHORT).show();
		}

	}

	private long mExitTime;

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Object mHelperUtils;
				Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
				mExitTime = System.currentTimeMillis();

			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
