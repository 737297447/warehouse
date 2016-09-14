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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lingdian.warehouse.R;
import com.zxing.activity.CaptureActivity;


public class Tianjias extends BaseActivity {
	private EditText name;
	private EditText guige;
	private EditText danwei;
	SqlHelpdemo db;
	SQLiteDatabase sDatabase = null;
	String names;
	Spinner gsmc;
	String gsname[];
	int i = 0;
	String gs;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tianjias);
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
	title_back_layout.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			finish();
		}
	});
	title_name = (TextView)findViewById(R.id.title_name);
	title_name.setText("添商品信息");
		Intent inte = getIntent();
		Bundle namee = inte.getExtras();
		names = inte.getStringExtra("username");
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		name=(EditText) findViewById(R.id.tianjiashangme);
		guige=(EditText) findViewById(R.id.tianjiashangge);
		danwei=(EditText) findViewById(R.id.tianjiashangje);
		gsmc = (Spinner) findViewById(R.id.gsmcs);
		

		String selectStr = "select comname  from gongys";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);
		cursor.moveToFirst();
		int count = cursor.getCount();
		gsname = new String[count];
		do {
			try {
				gsname[i] = cursor.getString(0);
				System.out.println(gsname[i]);
				i++;

			} catch (Exception e) {
				// TODO: handle exception

			}

		} while (cursor.moveToNext());
		
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
				TextView text = new TextView(Tianjias.this);
				text.setText(gsname[position]);
				text.setTextSize(20);
				text.setTextColor(R.color.red);
				return text;
			}

		};
		gsmc.setAdapter(ba);
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
	public void save(View v){
		
		if(name.getText().toString().equals("")){
			DialogDemo.builder(Tianjias.this, "提示","请输入商品名" );
		}
		else {
			// 查询语句
			String ename=name.getText().toString();
			String eguige=guige.getText().toString();
			String edanwei=danwei.getText().toString();
			String selectStr = "select pname,pguige,pdanwei from products";
			Cursor cursor = sDatabase.rawQuery(selectStr, null);
			cursor.moveToFirst();
			String nam = null;
			String guig = null;
			String danwe = null;
			do {
				try {
					nam = cursor.getString(0);
					guig = cursor.getString(1);
					danwe= cursor.getString(2);
				} catch (Exception e) {
					// TODO: handle exception
					nam = "";
					guig = "";
					danwe = "";
				}
				if (nam.equals(ename)&&guig.equals(eguige)&&danwe.equals(edanwei)) {
					DialogDemo.builder(Tianjias.this, "错误信息","该商品信息已存在");
					cursor.close();
					break;

				}
			} while (cursor.moveToNext());
		
			if (!(nam.equals(ename)&&guig.equals(eguige)&&danwe.equals(edanwei))) {
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
				sDatabase.execSQL("insert into products values('" + id + "','"
						+ ename + "','" + eguige + "','" 
						+ edanwei + "','" + gs +"')");
				Toast.makeText(Tianjias.this, "添加成功", Toast.LENGTH_LONG).show();
				
				seCursor.close();
			
				
			}
		}
		
	}
	public void back(View v){
		finish();
	}
	
	public void saomiao(View v){
		Intent openCameraIntent = new Intent(Tianjias.this, CaptureActivity.class);
		startActivityForResult(openCameraIntent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			guige.setText(scanResult);
		}
	}

}
