package com.lingdian.ui;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lingdian.warehouse.R;

/**
 * ��ӳ�����Ϣҳ��
 * 
 * @author
 * 
 */
public class Tianjiac extends BaseActivity {
	SqlHelpdemo db;
	SQLiteDatabase sDatabase = null;
	Spinner gsmc;
	TextView lxr;
	TextView lxdh;
	Spinner spmc;
	TextView spgg;
	TextView rukuNum;// ����л��ж�����������
	EditText chuhuoDanjia;// �����ĵ���
	EditText chuhuoshu;// �����������������������ܴ����������
	EditText jiliangdanwei;
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
	title_name.setText("��ӳ�����Ϣ");
	
		Intent inte = getIntent();
		Bundle name = inte.getExtras();
		names = inte.getStringExtra("username");
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		gsmc = (Spinner) findViewById(R.id.gsmcs);
		spmc = (Spinner) findViewById(R.id.spmcs);
		lxr = (TextView) findViewById(R.id.lxre);
		lxdh = (TextView) findViewById(R.id.lxdhe);
		spgg = (TextView) findViewById(R.id.spgge);
		rukuNum = (TextView) findViewById(R.id.jldwe);
		chuhuoDanjia = (EditText) findViewById(R.id.jhdje);
		chuhuoshu = (EditText) findViewById(R.id.jhsle);
		jiliangdanwei = (EditText) findViewById(R.id.jiliangdanwei);
		Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		mon = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		da = year + "��" + (mon + 1) + "��" + day + "��";
		date = (DatePicker) findViewById(R.id.jhrqd);
		date.init(year, mon, day, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker arg0, int year, int month,
					int day) {
				Tianjiac.this.year = year;
				Tianjiac.this.mon = month;
				Tianjiac.this.day = day;
				// ��ʾ��ǰ���ڡ�ʱ��
				da = year + "��" + (month + 1) + "��" + day + "��";
				System.out.println(da);

			}
		});

		String selectStr = "select comname  from gongys";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);
		String selectStr1 = "select pname  from products";
		Cursor cursor1 = sDatabase.rawQuery(selectStr1, null);
		cursor.moveToFirst();
		cursor1.moveToFirst();
		int count = cursor.getCount();
		int count1 = cursor1.getCount();
		gsname = new String[count];
		spname = new String[count1];
		do {
			try {
				gsname[i] = cursor.getString(0);
				System.out.println(gsname[i]);
				i++;

			} catch (Exception e) {
				// TODO: handle exception

			}

		} while (cursor.moveToNext());
		do {
			try {
				spname[j] = cursor1.getString(0);
				System.out.println(spname[j]);
				j++;

			} catch (Exception e) {
				// TODO: handle exception

			}

		} while (cursor1.moveToNext());
		BaseAdapter ba = new BaseAdapter() {
			@Override
			public int getCount() {
				// ָ��һ������10��ѡ��
				return gsname.length;
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			// ��д�÷������÷������ص�View����Ϊ�б���ÿ��
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView text = new TextView(Tianjiac.this);
				text.setText(gsname[position]);
				text.setTextSize(20);
				text.setTextColor(R.color.red);
				return text;

			}

		};
		gsmc.setAdapter(ba);
		BaseAdapter ba1 = new BaseAdapter() {
			@Override
			public int getCount() {
				// ָ��һ������10��ѡ��
				return spname.length;
			}

			@Override
			public Object getItem(int position) {
				return null;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			// ��д�÷������÷������ص�View����Ϊ�б���ÿ��
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView text = new TextView(Tianjiac.this);
				text.setText(spname[position]);
				text.setTextSize(20);
				text.setTextColor(R.color.red);
				return text;

			}

		};
		spmc.setAdapter(ba1);
		gsmc.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				gs = gsname[arg2];
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

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		spmc.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				sp = spname[arg2];
				String selectStr3 = "select pguige from products where pname='"
						+ sp + "'";

				String selectStr4 = "select num from ruku where products='"
						+ sp + "'";

				Cursor cursor3 = sDatabase.rawQuery(selectStr3, null);
				cursor3.moveToFirst();
				String guige = null;
				String rukushu = null;
				do {
					try {
						guige = cursor3.getString(0);
					} catch (Exception e) {
						// TODO: handle exception
						guige = "�Ȿ��ȱ�ٱ�ţ����޸�";
					}

				} while (cursor3.moveToNext());

				spgg.setText(guige);
				nowRukuNum();
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	public void save(View v) {
		if (chuhuoDanjia.getText().toString().equals("")
				|| chuhuoshu.getText().toString().equals("")) {
			DialogDemo.builder(Tianjiac.this, "��ʾ", "����д������Ϣ");

		} else {

			// ��ѯ���

			String elxr = lxr.getText().toString();
			String elxdh = lxdh.getText().toString();
			String espgg = spgg.getText().toString();
			String rukuSting = rukuNum.getText().toString();
			String chDanjia = chuhuoDanjia.getText().toString();
			String chukuString = chuhuoshu.getText().toString();
			String jiliangDw = jiliangdanwei.getText().toString();
			

			int rukuInt = Integer.valueOf(rukuSting);
			int chukuInt = Integer.valueOf(chukuString);
			int num = rukuInt - chukuInt;
			String numString = String.valueOf(num);
			if (chukuInt > rukuInt) {
				Toast.makeText(Tianjiac.this, "�ֿ���û����ô�������޸ĳ�������������С�ڲֿ�����",
						Toast.LENGTH_SHORT).show();
			} else {

				// ����ID
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
						+ "','" + espgg + "','" + jiliangDw + "','" + chDanjia
						+ "','" + chukuString + "','" + da + "')");

				sDatabase.execSQL("update ruku set num='" + numString
						+ "' where products ='" + sp + "'");

				Toast.makeText(Tianjiac.this, "��ӳɹ�", Toast.LENGTH_LONG).show();
				seCursor.close();
				chuhuoDanjia.setText("");
				chuhuoshu.setText("");
				nowRukuNum();
			}

		}

	}

	public void back(View v) {
		finish();
	}

	/** ��������������������л��е����� */
	public void nowRukuNum() {

		String nowRukushu = null;
		String selectStr4 = "select num from ruku where products='" + sp + "'";
		Cursor cursor4 = sDatabase.rawQuery(selectStr4, null);
		do {
			try {
				nowRukushu = cursor4.getString(0);
			} catch (Exception e) {
				// TODO: handle exception
				nowRukushu = "�ֿ����Ѿ�û���Ȿ����";

			}

		} while (cursor4.moveToNext());

		rukuNum.setText(nowRukushu);
		cursor4.close();
	}

}
