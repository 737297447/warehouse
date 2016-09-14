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
 * 修改出库信息页面
 * 
 * @author
 * 
 */
public class Xiugaic1 extends BaseActivity {
	SqlHelpdemo db;
	SQLiteDatabase sDatabase = null;
	TextView gongsiName;
	TextView lxr;
	TextView lxdh;
	TextView shangpinName;
	TextView shangPinBianhao;
	TextView rukuNum;
	EditText chuhuoDanjia;
	EditText chuhuoshu;
	String gsname[];
	String spname[];
	String gs;
	String shubenName;// 书本名称
	String da;
	int i = 0;
	int j = 0;
	DatePicker date;
	int year;
	int mon;
	int day;
	Calendar c;
	String ids;

	@SuppressLint("ResourceAsColor")
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiugai_chuhuo_layout);
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
		title_back_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		title_name = (TextView)findViewById(R.id.title_name);
		title_name.setText("修改出库信息");
		
		Intent inte = getIntent();
		Bundle name = inte.getExtras();
		ids = inte.getStringExtra("chuangzhen");
		shubenName = inte.getStringExtra("shuName");
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		gongsiName = (TextView) findViewById(R.id.gsmcs);
		shangpinName = (TextView) findViewById(R.id.spmcs);
		lxr = (TextView) findViewById(R.id.lxre);
		lxdh = (TextView) findViewById(R.id.lxdhe);
		shangPinBianhao = (TextView) findViewById(R.id.spgge);
		rukuNum = (TextView) findViewById(R.id.jldwe);
		chuhuoDanjia = (EditText) findViewById(R.id.jhdje);
		chuhuoshu = (EditText) findViewById(R.id.jhsle);
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
				Xiugaic1.this.year = year;
				Xiugaic1.this.mon = month;
				Xiugaic1.this.day = day;
				// 显示当前日期、时间
				da = year + "年" + (month + 1) + "月" + day + "日";
				System.out.println(da);

			}
		});

		nowchukuInfo();
		nowRukuNum();
	}

	public void save(View v) {
		if (chuhuoDanjia.getText().toString().equals("")
				|| chuhuoshu.getText().toString().equals("")) {
			DialogDemo.builder(Xiugaic1.this, "提示", "请填写完整信息");

		} else {
			// 查询语句

			String elxr = lxr.getText().toString();
			String elxdh = lxdh.getText().toString();
			String espgg = shangPinBianhao.getText().toString();
			String rukuSting = rukuNum.getText().toString();

			String chDanjia = chuhuoDanjia.getText().toString();
			String chukuString = chuhuoshu.getText().toString();

			int rukuInt = Integer.valueOf(rukuSting);
			int chukuInt = Integer.valueOf(chukuString);
			int numInt = Integer.valueOf(num);

			int chaNum = (numInt + rukuInt) - chukuInt;
			if (chukuInt > (numInt + rukuInt)) {
				Toast.makeText(Xiugaic1.this, "仓库中没有这么多货物，请修改出库数量，必须小于仓库数量",
						Toast.LENGTH_SHORT).show();
			} else {

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
				sDatabase.execSQL("update chuku set comname='" + gs
						+ "',pername='" + elxr + "',tel='" + elxdh
						+ "',products='" + shubenName + "',guige='" + espgg
						+ "',danwei='" + rukuSting + "',danjia='" + chDanjia
						+ "',num='" + chukuString + "',date='" + da
						+ "'where _id='" + ids + "'");

				sDatabase.execSQL("update ruku set num='" + chaNum
						+ "' where products ='" + shubenName + "'");

				Toast.makeText(Xiugaic1.this, "修改成功", Toast.LENGTH_LONG).show();
				seCursor.close();

				finish();
				Xiugaic.instance.finish();
				Intent i = new Intent(Xiugaic1.this,Xiugaic.class);
				startActivity(i);
			}

		}

	}

	public void back(View v) {
		finish();
	}

	/** 当出库完成是现在入库存中还有的数量 */
	public void nowRukuNum() {
		String nowRukushu = null;
		String selectStr4 = "select num from ruku where products='"
				+ shubenName + "'";
		Cursor cursor4 = sDatabase.rawQuery(selectStr4, null);
		do {
			try {
				nowRukushu = cursor4.getString(0);
			} catch (Exception e) {
				// TODO: handle exception
				nowRukushu = "仓库中已经没有这本书了";

			}

		} while (cursor4.moveToNext());

		rukuNum.setText(nowRukushu);
		cursor4.close();
	}

	String num;

	/** 当出库修改完成是现在出库中的数量 */
	public void nowchukuInfo() {
		String selectStr4 = "select comname,guige,products,pername,danjia,tel,num from chuku where _id='"
				+ ids + "'";
		Cursor cursor4 = sDatabase.rawQuery(selectStr4, null);
		cursor4.moveToFirst();
		String gongsiming = null;
		String shangpinbianhao = null;
		String shangpinming = null;
		String lianxiren = null;
		String danjia = null;
		String dianhua = null;
		do {
			try {
				gongsiming = cursor4.getString(0);
				shangpinbianhao = cursor4.getString(1);
				shangpinming = cursor4.getString(2);
				lianxiren = cursor4.getString(3);
				danjia = cursor4.getString(4);
				dianhua = cursor4.getString(5);
				num = cursor4.getString(6);
			} catch (Exception e) {
				// TODO: handle exception
				gongsiming = "";
				shangpinbianhao = "";
				shangpinming = "";
				lianxiren = "";
				danjia = "";
				dianhua = "";
				num = "";
			}

		} while (cursor4.moveToNext());

		gongsiName.setText(gongsiming);
		shangPinBianhao.setText(shangpinbianhao);
		shangpinName.setText(shangpinming);
		lxr.setText(lianxiren);
		chuhuoDanjia.setText(danjia);
		lxdh.setText(dianhua);
		chuhuoshu.setText(num);
	}
}
