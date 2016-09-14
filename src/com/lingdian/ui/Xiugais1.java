package com.lingdian.ui;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lingdian.warehouse.R;

/**
 * 修改商品信息页面
 * @author 田志远
 *
 */
public class Xiugais1 extends BaseActivity implements OnClickListener{
	private EditText name;
	private EditText guige;
	private EditText danwei;
	SqlHelpdemo db;
	SQLiteDatabase sDatabase = null;
	String did;
	Spinner gsmc;
	String gsname[];
	String gs;
	String comname;
	int i = 0;
	private Button shangpin_save;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tianjias);
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
		title_back_layout.setOnClickListener(this);
		title_name = (TextView)findViewById(R.id.title_name);
		title_name.setText("修改商品信息");
		Intent inte = getIntent();
		Bundle namee = inte.getExtras();
		did = inte.getStringExtra("did");
		
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		name=(EditText) findViewById(R.id.tianjiashangme);
		guige=(EditText) findViewById(R.id.tianjiashangge);
		danwei=(EditText) findViewById(R.id.tianjiashangje);
		gsmc = (Spinner) findViewById(R.id.gsmcs);
		shangpin_save = (Button)findViewById(R.id.shangpin_save);
		shangpin_save.setOnClickListener(this);
		
		String selectStr = "select pname,pguige,pdanwei,comname from products where _id='"
					+ did + "'";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);
		cursor.moveToFirst();
		String pnam = null;
		String pguig = null;
		String pdanwe = null;
		
		do {
			try {
				pnam = cursor.getString(0);
				pguig = cursor.getString(1);
				pdanwe= cursor.getString(2);
				comname= cursor.getString(3);
			} catch (Exception e) {
				// TODO: handle exception
				pnam = "";
				pguig = "";
				pdanwe = "";
				comname= "";
			}
		} while (cursor.moveToNext());
		name.setText(pnam);
		guige.setText(pguig);
		danwei.setText(pdanwe);
		addGysSpinner();
		cursor.close();
	}
	
	
	public void save(){
		
		if(name.getText().toString().equals("")){
			DialogDemo.builder(Xiugais1.this, "提示","请输入商品名" );
		}
		else {
			// 查询语句
			String ename=name.getText().toString();
			String eguige=guige.getText().toString();
			String edanwei=danwei.getText().toString();
//			String selectStr = "select pname,pguige,pdanwei from products";
//			Cursor cursor = sDatabase.rawQuery(selectStr, null);
//			cursor.moveToFirst();
//			String nam = null;
//			String guig = null;
//			String danwe = null;
//			do {
//				try {
//					nam = cursor.getString(0);
//					guig = cursor.getString(1);
//					danwe= cursor.getString(2);
//				} catch (Exception e) {
//					// TODO: handle exception
//					nam = "";
//					guig = "";
//					danwe = "";
//				}
//				if (nam.equals(ename)&&guig.equals(eguige)&&danwe.equals(edanwei)) {
//					DialogDemo.builder(Xiugais1.this, "错误信息","该商品信息已存在");
//					cursor.close();
//					break;
//
//				}
//			} while (cursor.moveToNext());
//		
//			if (!(nam.equals(ename)&&guig.equals(eguige)&&danwe.equals(edanwei))) {
				// 定义ID
				int id = 0;
				String select = "select max(_id) from products";
				Cursor seCursor = sDatabase.rawQuery(select, null);
				try {
					seCursor.moveToFirst();
					id = Integer.parseInt(seCursor.getString(0));
					id += 1;
				} catch (Exception e) {
					// TODO: handle exception
					id = 0;
				}
				System.out.println("gs===="+gs);
				sDatabase.execSQL("update products set pname='"+ename+"',pguige='"+eguige+"',pdanwei='"+edanwei+"',comname='"+gs+"' where _id='"+did+"'");
				Toast.makeText(Xiugais1.this, "修改成功", Toast.LENGTH_LONG).show();
				seCursor.close();
				finish();
				Xiugais.instance.finish();
				Intent i = new Intent(Xiugais1.this,Xiugais.class);
				startActivity(i);
			}
//		}
		
	}
	
	public void back(View v){
	   finish();
	}
	
	public void addGysSpinner(){
		String selectStr = "select comname  from gongys";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);
		cursor.moveToFirst();
		int count = cursor.getCount();
		gsname = new String[count];
		do {
			try {
				gsname[i] = cursor.getString(0);
				i++;
			} catch (Exception e) {
				// TODO: handle exception

			}

		} while (cursor.moveToNext());
		cursor.close();
		BaseAdapter ba = new BaseAdapter() {
			@Override
			public int getCount() {
				// 指定一共包含10个选项
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

			// 重写该方法，该方法返回的View将作为列表框的每项
			@SuppressLint("ResourceAsColor")
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				TextView text = new TextView(Xiugais1.this);
				text.setText(gsname[position]);
				text.setTextSize(20);
				text.setTextColor(R.color.red);
				return text;
			}

		};
		gsmc.setAdapter(ba);
		setGysSpinner();
		gsmc.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				gs = gsname[arg2];
			}
		
			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
	}
	
	/** 设置修改商品的供应商名称*/
	public void setGysSpinner(){
		String selectStr4 = "select _id  from gongys where comname='"
				+ comname + "'";
		Cursor cursor4 = sDatabase.rawQuery(selectStr4, null);
		cursor4.moveToFirst();
		int position = 0;
		do {
			try {
				position = Integer.valueOf(cursor4.getString(0));
			} catch (Exception e) {
				// TODO: handle exception
				position = 0;
			}

		} while (cursor4.moveToNext());
		cursor4.close();
		gsmc.setSelection(position);
	}


	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.shangpin_save:
			save();
			break;
		case R.id.title_back_layout:
			finish();
			break;
		}
		
	}
	

}
