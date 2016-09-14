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
 * ɾ����Ʒҳ��
 * 
 * @author ��־Զ
 * 
 */

public class Shanchus extends BaseActivity implements android.view.View.OnClickListener {
	private ListView listview;

	String id[];
	String name[];
	String pass[];
	String num[];
	SqlHelpdemo db;
	int i = 0;
	SQLiteDatabase sDatabase = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shanchus);
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
		title_back_layout.setOnClickListener(this);
		title_name = (TextView)findViewById(R.id.title_name);
		title_name.setText("ɾ����Ʒ");
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		listview = (ListView) findViewById(R.id.shangplist);
		List<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
		String selectStr = "select _id,pname,pguige,pdanwei  from products";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);

		cursor.moveToFirst();

		int count = cursor.getCount();
		pass = new String[count];
		num = new String[count];
		id = new String[count];
		name = new String[count];
		do {
			try {
				id[i] = cursor.getString(0);
				name[i] = cursor.getString(1);
				pass[i] = cursor.getString(2);
				num[i] = cursor.getString(3);
				i++;

			} catch (Exception e) {
				// TODO: handle exception

			}

		} while (cursor.moveToNext());

		for (int i = 0; i < id.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("did", id[i]);
			map.put("dname", name[i]);
			map.put("dpass", pass[i]);
			map.put("dnum", num[i]);
			slist.add(map);
		}
		SimpleAdapter simple = new SimpleAdapter(this, slist,
				R.layout.yonghuadapter, new String[] { "did", "dname", "dpass",
						"dnum" }, new int[] { R.id.t1, R.id.t2, R.id.t3,
						R.id.t4, });
		listview.setAdapter(simple);
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				for(int i=0;i<name.length;i++){
					if(arg2==i){
						
						builder.setTitle("ȷ����Ϣ");
						builder.setMessage("ȷ��Ҫɾ������Ʒ��");
						final int j=i;
						builder.setPositiveButton("ȷ��", new OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								
					sDatabase.execSQL("delete from products where pname='"+name[j]+"'");
								Intent intent=new Intent();
								intent.setClass(Shanchus.this, Shanchus.class);
								startActivity(intent);
								finish();
								
							}
						});
						builder.setNegativeButton("ȡ��", new OnClickListener() {
							
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
