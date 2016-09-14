package com.lingdian.ui.goods;

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

import com.lingdian.ui.BaseActivity;
import com.lingdian.ui.DialogDemo;
import com.lingdian.ui.SqlHelpdemo;
import com.lingdian.warehouse.R;
import com.zxing.activity.CaptureActivity;


public class GoodsAddAvtivity extends BaseActivity implements OnClickListener{
	private EditText goodsName;
	private EditText goodsTiaoma;
	private EditText goodsDanwei;
	private Button shangpin_save;
	
	SqlHelpdemo db;
	SQLiteDatabase sDatabase = null;
	Spinner gsmc;
	String gsname[];
	int i = 0;
	String gs;
	int gysCount;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tianjias);
		initView();
	}
	
	public void initView(){

		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
		title_back_layout.setOnClickListener(this);
		title_name = (TextView)findViewById(R.id.title_name);
		title_name.setText("添加商品信息");
		
		goodsName=(EditText) findViewById(R.id.tianjiashangme);
		goodsTiaoma=(EditText) findViewById(R.id.tianjiashangge);
		goodsDanwei=(EditText) findViewById(R.id.tianjiashangje);
		shangpin_save = (Button)findViewById(R.id.shangpin_save);
		shangpin_save.setOnClickListener(this);
		
		gsmc = (Spinner) findViewById(R.id.gsmcs);
		
		String selectStr = "select comname  from gongys";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);
		cursor.moveToFirst();
		gysCount = cursor.getCount();
		gsname = new String[gysCount];
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
				TextView text = new TextView(GoodsAddAvtivity.this);
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
	
	public void save(){
		
		if(goodsName.getText().toString().equals("")&&goodsTiaoma.getText().toString().equals("")&&goodsDanwei.getText().toString().equals("")){
			DialogDemo.builder(GoodsAddAvtivity.this, "提示","请将商品信息填写完整" );
			return;
		}
		if(gysCount == 0){
			DialogDemo.builder(GoodsAddAvtivity.this, "提示","还未添加供应商，请返回首页点击添加供应商信息" );
			return;
		}
			// 查询语句
			String ename=goodsName.getText().toString();
			String eguige=goodsTiaoma.getText().toString();
			String edanwei=goodsDanwei.getText().toString();
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
				if (guig.equals(eguige)) {
					DialogDemo.builder(GoodsAddAvtivity.this, "错误信息","该商品信息已存在");
					cursor.close();
					goodsName.setText("");
					goodsTiaoma.setText("");
					break;

				}
			} while (cursor.moveToNext());
		
			if (!guig.equals(eguige)) {
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
				Toast.makeText(GoodsAddAvtivity.this, "添加成功", Toast.LENGTH_LONG).show();
				seCursor.close();
				goodsTiaoma.setText("");
				goodsName.setText("");
			}
		
	}
	public void back(View v){
	   finish();
	}
	
	public void saomiao(View v){
		Intent openCameraIntent = new Intent(GoodsAddAvtivity.this, CaptureActivity.class);
		startActivityForResult(openCameraIntent, 0);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		//处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			goodsTiaoma.setText(scanResult);
		}
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
