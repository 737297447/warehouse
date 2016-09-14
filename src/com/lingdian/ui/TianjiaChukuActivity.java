package com.lingdian.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lingdian.ui.adapter.MyAdapter;
import com.lingdian.warehouse.R;

/**
 * 添加出库信息页面
 * 
 * @author
 * 
 */
public class TianjiaChukuActivity extends BaseActivity {

	private TextView lxr;
	private TextView lxdh;
	private Spinner spinner2;
	private TextView shangpinBianhao;
	private TextView rukuNum;// 入库中还有多少数量货物
	private EditText chuhuoDanjia;// 出货的单价
	private EditText chuhuoshu;// 出货的数量，出货数量不能大于入货数量
	private TextView jiliangdanwei;

	SqlHelpdemo db;
	SQLiteDatabase sDatabase = null;
	Spinner spinner1;
	String gsname[];
	String spname[];
	String gs;
	String sp;
	String da;
	DatePicker date;
	int year;
	int mon;
	int day;
	Calendar c;
	String names;

	@SuppressLint("ResourceAsColor")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tianjia_chuhuo_layout);
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
	title_back_layout.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();
		}
	});
	title_name = (TextView)findViewById(R.id.title_name);
	title_name.setText("添加出库信息");
		
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		spinner1 = (Spinner) findViewById(R.id.gsmcs);
		spinner2 = (Spinner) findViewById(R.id.spmcs);
		lxr = (TextView) findViewById(R.id.lxre);
		lxdh = (TextView) findViewById(R.id.lxdhe);
		shangpinBianhao = (TextView) findViewById(R.id.spgge);
		rukuNum = (TextView) findViewById(R.id.jldwe);
		chuhuoDanjia = (EditText) findViewById(R.id.jhdje);
		chuhuoshu = (EditText) findViewById(R.id.jhsle);
		jiliangdanwei = (TextView) findViewById(R.id.jiliangdanwei);
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		mon = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		da = year + "年" + (mon + 1) + "月" + day + "日";
		date = (DatePicker) findViewById(R.id.jhrqd);
		date.init(year, mon, day, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker arg0, int year, int month,
					int day) {
				TianjiaChukuActivity.this.year = year;
				TianjiaChukuActivity.this.mon = month;
				TianjiaChukuActivity.this.day = day;
				// 显示当前日期、时间
				da = year + "年" + (month + 1) + "月" + day + "日";
				System.out.println(da);

			}
		});
		initSpinner1();
	}

	public void save(View v) {
		if (chuhuoDanjia.getText().toString().equals("")
				|| chuhuoshu.getText().toString().equals("")) {
			DialogDemo.builder(TianjiaChukuActivity.this, "提示", "请填写完整信息");

		} else {
			// 查询语句
			String elxr = lxr.getText().toString();
			String elxdh = lxdh.getText().toString();
			String bianhao = shangpinBianhao.getText().toString();
			String rukuSting = rukuNum.getText().toString();
			String chDanjia = chuhuoDanjia.getText().toString();
			String chukuString = chuhuoshu.getText().toString();
			String jiliangDw = jiliangdanwei.getText().toString();

			int rukuInt = Integer.valueOf(rukuSting);
			int chukuInt = Integer.valueOf(chukuString);
			int num = rukuInt - chukuInt;
			String numString = String.valueOf(num);
			if (chukuInt > rukuInt) {
				Toast.makeText(TianjiaChukuActivity.this,
						"仓库中没有这么多货物，请修改出库数量，必须小于仓库数量", Toast.LENGTH_SHORT)
						.show();
			} else {

				nowChukuBianhao(gs, elxr, elxdh, sp, bianhao, jiliangDw, chDanjia, chukuString, da, rukuInt, numString);
				
			}

		}

	}

	public void back(View v) {
		finish();
	}
	
	/** 检测入库中是不是已经存在这个商品 */
	public void nowChukuBianhao(String gs, String elxr, String elxdh,
			String sp, String bianhao, String jiliangDw, String chDanjia,
			String chukuString, String da,int rukuInt,String numString) {

		String chukuGuige[];
		String selectStr4 = "select guige from chuku";
		Cursor cursor = sDatabase.rawQuery(selectStr4, null);
		int i = 0;
		cursor.moveToFirst();
		int count = cursor.getCount();
		if (count != 0) {
			chukuGuige = new String[count];
			do {
				try {
					chukuGuige[i] = cursor.getString(0);
					i++;
				} catch (Exception e) {
					// TODO: handle exception
				}
			} while (cursor.moveToNext());
			cursor.close();
			boolean Insert = false;
			for (int j = 0; j < chukuGuige.length; j++) {
				if (bianhao.equals(chukuGuige[j])) {
					// 当前库中已经入库过一次商品，第二次入库只更新价格和数量
					System.out.println("******************更新****");
					String oldChushu = null;
					String selectStr = "select num from chuku where guige='"
							+ bianhao + "'";
					Cursor cursor4 = sDatabase.rawQuery(selectStr, null);
					do {
						try {
							oldChushu = cursor4.getString(0);
						} catch (Exception e) {
							// TODO: handle exception
						}
					} while (cursor4.moveToNext());
					int oldChukuInt = Integer.valueOf(oldChushu);// 仓库中数量
					int nowChukuInt = Integer.valueOf(chukuString);
					int newChukuInt = oldChukuInt + nowChukuInt;
					// 更新入库中的数量
					int num1 = rukuInt - newChukuInt;
					String numString1 = String.valueOf(num1);
					sDatabase.execSQL("update chuku set num='" + newChukuInt
							+ "',danjia='" + chDanjia + "' where guige ='"
							+ bianhao + "'");

					sDatabase.execSQL("update ruku set num='" + numString1
							+ "' where products ='" + sp + "'");

					Toast.makeText(TianjiaChukuActivity.this, "添加成功",
							Toast.LENGTH_LONG).show();
					chuhuoDanjia.setText("");
					chuhuoshu.setText("");
					nowRukuNum();
					
				} else {
					Insert = true;
				}
			}
			if(Insert){
				System.out.println("******************添加****");
				// 定义ID
				int id = 0;
				String select = "select max(_id) from chuku";
				Cursor seCursor = sDatabase.rawQuery(select, null);
				try {
					seCursor.moveToFirst();
					id = Integer.parseInt(seCursor.getString(0));
					id += 1;
				} catch (Exception e) {
					// TODO: handle exception
					id = 0;
				}
				sDatabase.execSQL("insert into chuku values('" + id + "','"
						+ gs + "','" + elxr + "','" + elxdh + "','" + sp
						+ "','" + bianhao + "','" + jiliangDw + "','"
						+ chDanjia + "','" + chukuString + "','" + da
						+ "')");

				sDatabase.execSQL("update ruku set num='" + numString
						+ "' where products ='" + sp + "'");

				Toast.makeText(TianjiaChukuActivity.this, "添加成功",
						Toast.LENGTH_LONG).show();
				seCursor.close();
				chuhuoDanjia.setText("");
				chuhuoshu.setText("");
				nowRukuNum();
			}
		}else{
			System.out.println("第一次，添加");
			int firstId = 0;
			sDatabase.execSQL("insert into chuku values('" + firstId + "','"
					+ gs + "','" + elxr + "','" + elxdh + "','" + sp
					+ "','" + bianhao + "','" + jiliangDw + "','"
					+ chDanjia + "','" + chukuString + "','" + da
					+ "')");
			sDatabase.execSQL("update ruku set num='" + numString
					+ "' where products ='" + sp + "'");

			chuhuoDanjia.setText("");
			chuhuoshu.setText("");
			nowRukuNum();
		}
		
		
		
	}

	/** 当出库完成时现在入库存中还有的数量 */
	public void nowRukuNum() {
		String nowRukushu = null;
		String danwei = null;
		String selectStr4 = "select danwei,num from ruku where products='" + sp
				+ "'";
		Cursor cursor4 = sDatabase.rawQuery(selectStr4, null);
		do {
			try {
				danwei = cursor4.getString(0);
				nowRukushu = cursor4.getString(1);

			} catch (Exception e) {
				// TODO: handle exception
				nowRukushu = "仓库中已经没有这个商品了";
				danwei = "暂无该商品计量单位";
			}
		} while (cursor4.moveToNext());

		jiliangdanwei.setText(danwei);
		rukuNum.setText(nowRukushu);
		if (jiliangdanwei.getText().toString().equals("暂无该商品计量单位")) {
			jiliangdanwei.setTextColor(Color.RED);
			rukuNum.setTextColor(Color.RED);
			chuhuoDanjia.setEnabled(false);
			chuhuoshu.setEnabled(false);
		} else {
			jiliangdanwei.setTextColor(Color.rgb(42, 0, 255));
			rukuNum.setTextColor(Color.rgb(42, 0, 255));
			chuhuoDanjia.setEnabled(true);
			chuhuoshu.setEnabled(true);
		}

		cursor4.close();
	}

	public void initSpinner1() {
		List<String> comnameList = new ArrayList<String>();
		int num = 0;
		try {
			String selectStr = "select comname  from gongys";
			Cursor cursor = sDatabase.rawQuery(selectStr, null);
			cursor.moveToFirst();
			int count = cursor.getCount();
			gsname = new String[count];
			do {
				gsname[num] = cursor.getString(0);
				System.out.println(gsname[num]);
				num++;
			} while (cursor.moveToNext());

			for (int i = 0; i < gsname.length; i++) {
				comnameList.add(gsname[i]);
			}
			MyAdapter myAdapter = new MyAdapter(this, comnameList);
			spinner1.setAdapter(myAdapter);
			spinner1.setOnItemSelectedListener(new SpinnerOnSelectedListener1());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	class SpinnerOnSelectedListener1 implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> adapterView, View view,
				int position, long id) {
			String pcode = ((String) adapterView.getItemAtPosition(position));
			initSpinner2(pcode);

			gs = gsname[position];
			String selectStr2 = "select pername,tel from gongys where comname='"
					+ gs + "'";
			Cursor cursor2 = sDatabase.rawQuery(selectStr2, null);
			cursor2.moveToFirst();
			String name = null;
			String tel = null;
			do {
				try {
					name = cursor2.getString(0);
					tel = cursor2.getString(1);
				} catch (Exception e) {
					// TODO: handle exception
					name = "";
					tel = "";

				}

			} while (cursor2.moveToNext());
			lxr.setText(name);
			lxdh.setText(tel);

		}

		public void onNothingSelected(AdapterView<?> adapterView) {
			// TODO Auto-generated method stub
		}
	}

	public void initSpinner2(String comname) {
		List<String> goodsList = new ArrayList<String>();
		int j = 0;
		try {
			String selectStr3 = "select pname from products where comname='"
					+ comname + "'";
			Cursor cursor = sDatabase.rawQuery(selectStr3, null);
			cursor.moveToFirst();
			int count = cursor.getCount();
			spname = new String[count];
			do {
				spname[j] = cursor.getString(0);
				j++;
			} while (cursor.moveToNext());

			for (int i = 0; i < spname.length; i++) {
				goodsList.add(spname[i]);
			}
			MyAdapter myAdapter = new MyAdapter(this, goodsList);
			spinner2.setAdapter(myAdapter);
			spinner2.setOnItemSelectedListener(new SecondSpinnerSelectedListener());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	class SecondSpinnerSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			// TODO Auto-generated method stub
			sp = spname[arg2];
			String selectStr3 = "select pguige from products where pname='"
					+ sp + "'";
			Cursor cursor3 = sDatabase.rawQuery(selectStr3, null);
			cursor3.moveToFirst();
			String guige = null;
			do {
				try {
					guige = cursor3.getString(0);
				} catch (Exception e) {
					// TODO: handle exception
					guige = "这商品缺少编号，请修改";
				}
			} while (cursor3.moveToNext());
			shangpinBianhao.setText(guige);
			nowRukuNum();
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

}
