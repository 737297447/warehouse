package com.lingdian.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
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
 * 添加入库信息页面
 * 
 * @author
 * 
 */
public class TianjiaRukuActivity extends BaseActivity {
	SqlHelpdemo db;
	SQLiteDatabase sDatabase = null;
	Spinner spinner1;
	
	TextView lxr;
	TextView lxdh;
	TextView shangpinBianhao;
	TextView jldw;
	EditText jinhuoDanjia;
	EditText jinhuoNum;
	Spinner spinner2;
	
	private ArrayAdapter<String> firstSpinnerAdapter;
	private ArrayAdapter<String> secondSpinnerAdapter;

	
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
		setContentView(R.layout.activity_tianjia_ruhuo_layout);
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
	title_back_layout.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();
		}
	});
	title_name = (TextView)findViewById(R.id.title_name);
	title_name.setText("添加入库信息");
		Intent inte = getIntent();
		Bundle name = inte.getExtras();
		names = inte.getStringExtra("username");
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		spinner1 = (Spinner) findViewById(R.id.gsmcs);
		spinner2 = (Spinner) findViewById(R.id.spmcs);
		lxr = (TextView) findViewById(R.id.lxre);
		lxdh = (TextView) findViewById(R.id.lxdhe);
		shangpinBianhao = (TextView) findViewById(R.id.spgge);
		jldw = (TextView) findViewById(R.id.jldwe);
		jinhuoDanjia = (EditText) findViewById(R.id.jhdje);
		jinhuoNum = (EditText) findViewById(R.id.jhsle);
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
				TianjiaRukuActivity.this.year = year;
				TianjiaRukuActivity.this.mon = month;
				TianjiaRukuActivity.this.day = day;
				// 显示当前日期、时间
				da = year + "年" + (month + 1) + "月" + day + "日";
				System.out.println(da);

			}
		});
		initSpinner1();

	}

	public void save(View v) {
		if (jinhuoDanjia.getText().toString().equals("")
				|| jinhuoNum.getText().toString().equals("")) {
			DialogDemo.builder(TianjiaRukuActivity.this, "提示", "请填写完整信息");

		} else {

			String elxr = lxr.getText().toString();
			String elxdh = lxdh.getText().toString();
			String sPbianhao = shangpinBianhao.getText().toString();
			String ejldw = jldw.getText().toString();
			String danjia = jinhuoDanjia.getText().toString();
			String espgs = jinhuoNum.getText().toString();
			nowRukuBianhao(gs, elxr, elxdh, sp, sPbianhao, ejldw, danjia, espgs, da);
		}

	}

	/** 检测入库中是不是已经存在这个商品 */
	
	public void nowRukuBianhao(String gs,String elxr,String elxdh,String sp,String sPbianhao,String ejldw,String danjia,String espgs,String da) {
		String rukuGuige[];
		String selectStr4 = "select guige from ruku";
		Cursor cursor = sDatabase.rawQuery(selectStr4, null);
		int i = 0;
		cursor.moveToFirst();
		int count = cursor.getCount();
		if (count != 0) {
			rukuGuige = new String[count];
			do {
				try {
					rukuGuige[i] = cursor.getString(0);
					i++;
				} catch (Exception e) {
					// TODO: handle exception
				}
			} while (cursor.moveToNext());
			cursor.close();
			
			boolean Insert = false;
			for (int j = 0; j < rukuGuige.length; j++) {
				if (sPbianhao.equals(rukuGuige[j])) {
					System.out.println("存在，更新");
					String oldRukushu = null;
					String selectStr = "select num from ruku where guige='" + sPbianhao
							+ "'";
					Cursor cursor4 = sDatabase.rawQuery(selectStr, null);
					do {
						try {
							oldRukushu = cursor4.getString(0);

						} catch (Exception e) {
							// TODO: handle exception
						}
					} while (cursor4.moveToNext());

					int oldRukuInt = Integer.valueOf(oldRukushu);// 仓库中数量
					int nowRukuInt = Integer.valueOf(espgs);
					int newRukuInt = oldRukuInt + nowRukuInt;
					sDatabase.execSQL("update ruku set num='" + newRukuInt + "',danjia='" + danjia + "' where guige ='" + sPbianhao + "'");
					Toast.makeText(TianjiaRukuActivity.this, "添加成功",
							Toast.LENGTH_LONG).show();
					jinhuoDanjia.setText("");
					jinhuoNum.setText("");
					return;
				}else{
					Insert = true;
				}
			}
			
			if(Insert){
				System.out.println("不存在，添加");
				int id = 0;
				String select = "select max(_id) from ruku";
				Cursor seCursor = sDatabase.rawQuery(select, null);
				try {
					seCursor.moveToFirst();
					id = Integer.parseInt(seCursor.getString(0));
					id += 1;
				} catch (Exception e) {
					// TODO: handle exception
					id = 0;
				}
				sDatabase.execSQL("insert into ruku values('" + id + "','" + gs
						+ "','" + elxr + "','" + elxdh + "','" + sp + "','"
						+ sPbianhao + "','" + ejldw + "','" + danjia + "','"
						+ espgs + "','" + da + "')");
				Toast.makeText(TianjiaRukuActivity.this, "添加成功",
						Toast.LENGTH_LONG).show();
				seCursor.close();
				jinhuoDanjia.setText("");
				jinhuoNum.setText("");
			}
			
		}else{
			System.out.println("第一次，添加");
			int firstId = 0;
			sDatabase.execSQL("insert into ruku values('" + firstId + "','" + gs
					+ "','" + elxr + "','" + elxdh + "','" + sp + "','"
					+ sPbianhao + "','" + ejldw + "','" + danjia + "','"
					+ espgs + "','" + da + "')");
			Toast.makeText(TianjiaRukuActivity.this, "添加成功",
					Toast.LENGTH_LONG).show();
			jinhuoDanjia.setText("");
			jinhuoNum.setText("");
			
		}
		
	}

	
	
	public void back(View v) {
		finish();
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
		

			gs = gsname[position];
			String selectStr2 = "select comname,pername,tel from gongys where comname='"
					+ gs + "'";
			Cursor cursor2 = sDatabase.rawQuery(selectStr2, null);
			cursor2.moveToFirst();
			
			String comname = null;
			String name = null;
			String tel = null;
			do {
				try {
					comname = cursor2.getString(0);
					name = cursor2.getString(1);
					tel = cursor2.getString(2);
				} catch (Exception e) {
					// TODO: handle exception
					comname = "";
					name = "";
					tel = "";

				}

			} while (cursor2.moveToNext());
			initSpinner2(comname);
			lxr.setText(name);
			lxdh.setText(tel);
			jinhuoDanjia.setText("");
			jinhuoNum.setText("");
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
			System.out.println("goodsList========"+goodsList);
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
			sp = spname[arg2];
			String selectStr3 = "select pguige,pdanwei from products where pname='"
					+ sp + "'";
			Cursor cursor3 = sDatabase.rawQuery(selectStr3, null);
			cursor3.moveToFirst();
			String guige = null;
			String danwei = null;
			do {
				try {
					guige = cursor3.getString(0);
					danwei = cursor3.getString(1);
				} catch (Exception e) {
					// TODO: handle exception
					guige = "";
					danwei = "";
				}

			} while (cursor3.moveToNext());
			shangpinBianhao.setText(guige);
			jldw.setText(danwei);
			jinhuoDanjia.setText("");
			jinhuoNum.setText("");
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

}
