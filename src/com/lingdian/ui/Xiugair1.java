package com.lingdian.ui;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lingdian.warehouse.R;

/**
 * 修改入库信息页面
 * 
 * @author 
 * 
 */
public class Xiugair1 extends BaseActivity {
	SqlHelpdemo db;
	SQLiteDatabase sDatabase = null;
	TextView gsmc;
	TextView lxr;
	TextView lxdh;
	TextView spmc;
	TextView spgg;
	TextView jldw;
	EditText spdj;
	EditText spgs;
	String gsname[];
	String spname[];
	String gs;
	String sp;
	String da;
	int i = 0;
	int j = 0;
	DatePicker date;
	int year;
	int mon;
	int day;
	Calendar c;
	String postId;

	
	@SuppressLint("ResourceAsColor")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiugai_ruhuo_layout);
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
		title_back_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		title_name = (TextView)findViewById(R.id.title_name);
		title_name.setText("修改入库信息");
		
		Intent inte = getIntent();
		postId = inte.getStringExtra("chuangzhen");
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		gsmc = (TextView) findViewById(R.id.gsmcs);
		spmc = (TextView) findViewById(R.id.spmcs);
		lxr = (TextView) findViewById(R.id.lxre);
		lxdh = (TextView) findViewById(R.id.lxdhe);
		spgg = (TextView) findViewById(R.id.spgge);
		jldw = (TextView) findViewById(R.id.jldwe);
		spdj = (EditText) findViewById(R.id.jhdje);
		spgs = (EditText) findViewById(R.id.jhsle);
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
				Xiugair1.this.year = year;
				Xiugair1.this.mon = month;
				Xiugair1.this.day = day;
				// 显示当前日期、时间
				da = year + "年" + (month + 1) + "月" + day + "日";
			}
		});
		
		nowRukuInfo();
	}

	public void save(View v) {
		if (spdj.getText().toString().equals("")
				|| spgs.getText().toString().equals("")) {
			DialogDemo.builder(Xiugair1.this, "提示", "请填写完整信息");

		} else {

			// 查询语句
			String espdj = spdj.getText().toString();
			String espgs = spgs.getText().toString();
			// 定义ID
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
			sDatabase.execSQL("update ruku set danjia='"
					+ espdj + "',num='" + espgs + "',date='" + da
					+ "'where _id='" + postId + "'");
			Toast.makeText(Xiugair1.this, "修改成功", Toast.LENGTH_LONG).show();
			seCursor.close();
			
			finish();
			Xiugair.instance.finish();
			Intent i = new Intent(Xiugair1.this,Xiugair.class);
			startActivity(i);
		}

	}

	public void back(View v) {
		finish();
	}
	
	/** 当前要修改的详情 */
	public void nowRukuInfo() {
		String selectStr4 = "select * from ruku where _id='"
				+ postId + "'";
		Cursor cursor4 = sDatabase.rawQuery(selectStr4, null);
		cursor4.moveToFirst();
		
		String gys = null;
		String lianxiren = null;
		String tel = null;
		String goodsName = null;
		String goodsBianhao = null;
		String danwei = null;
		String danjia = null;
		String num = null;
		do {
			try {
				gys= cursor4.getString(1);
				lianxiren= cursor4.getString(2);
				tel= cursor4.getString(3);
				goodsName= cursor4.getString(4);
				goodsBianhao= cursor4.getString(5);
				danwei= cursor4.getString(6);
				danjia= cursor4.getString(7);
				num= cursor4.getString(8);
			} catch (Exception e) {
				// TODO: handle exception
				gys = "";
				lianxiren = "";
				tel = "";
				goodsName = "";
				goodsBianhao = "";
				danwei = "";
			    danjia = "";
				num = "";
			}

		} while (cursor4.moveToNext());

		gsmc.setText(gys);
		lxr.setText(lianxiren);
		lxdh.setText(tel);
		spmc.setText(goodsName);
		spgg.setText(goodsBianhao);
		jldw.setText(danwei);
		spdj.setText(danjia);
		spgs.setText(num);
	}
	
	
	
}
