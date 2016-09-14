package com.lingdian.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lingdian.warehouse.R;


/**
 * 修改商品页面
 * 
 * @author 
 * 
 */

public class Xiugais extends BaseActivity implements android.view.View.OnClickListener{
	private ListView listview;

	String id[];
	String name[];
	String bianhao[];
	String num[];
	String comname[];
	SqlHelpdemo db;
	int i = 0;
	SQLiteDatabase sDatabase = null;
	public static Xiugais instance = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shanchus);
		instance = this;
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
		title_back_layout.setOnClickListener(this);
		title_name = (TextView)findViewById(R.id.title_name);
		title_name.setText("修改商品");
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		listview = (ListView) findViewById(R.id.shangplist);
		List<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
		String selectStr = "select _id,pname,pguige,pdanwei,comname from products";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);

		cursor.moveToFirst();

		int count = cursor.getCount();
		bianhao = new String[count];
		num = new String[count];
		id = new String[count];
		name = new String[count];
		comname = new String[count];
		do {
			try {
				id[i] = cursor.getString(0);
				name[i] = cursor.getString(1);
				bianhao[i] = cursor.getString(2);
				num[i] = cursor.getString(3);
				comname[i] = cursor.getString(4);
				i++;

			} catch (Exception e) {
				// TODO: handle exception

			}

		} while (cursor.moveToNext());

		for (int i = 0; i < id.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("did", id[i]);
			map.put("dname", name[i]);
			map.put("dpass", bianhao[i]);
			map.put("dnum", num[i]);
			map.put("comname", comname[i]);
			slist.add(map);
		}
		SimpleAdapter simple = new SimpleAdapter(this, slist,
				R.layout.yonghuadapter, new String[] { "did", "dname", "dpass",
						"dnum","comname"}, new int[] { R.id.t1, R.id.t2, R.id.t3,
						R.id.t4,R.id.t5 });
		listview.setAdapter(simple);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				for(int i=0;i<name.length;i++){
					if(arg2==i){
						
						builder.setTitle("确认消息");
						builder.setMessage("确定要修改该商品吗？");
						final int j=i;
						builder.setPositiveButton("确定", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
					
								Intent intent=new Intent();
								Bundle bundle = new Bundle();
								bundle.putString("did",id[j]);
								intent.putExtras(bundle);
								intent.setClass(Xiugais.this, Xiugais1.class);
								startActivity(intent);
							}
						});
						builder.setNegativeButton("取消", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
								
							}
						});
						builder.create().show();
						
					}
					
				}
				
			}
		});
	}
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.title_back_layout:
			finish();
			break;
		}
	}
	

}
