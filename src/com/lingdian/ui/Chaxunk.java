package com.lingdian.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lingdian.warehouse.R;

/**
 * 查询客户页面
 * 
 * @author
 * 
 */

public class Chaxunk extends BaseActivity {
	private ListView listview;

	String id[];
	String cname[];
	String pname[];
	String add[];
	String city[];
	String diqu[];
	String youbian[];
	String tel[];
	String chuanzhen[];
	String web[];

	SqlHelpdemo db;
	int i = 0;
	SQLiteDatabase sDatabase = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shanchuk1);
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
		title_back_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		title_name = (TextView) findViewById(R.id.title_name);
		title_name.setText("查询顾客");
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		listview = (ListView) findViewById(R.id.kehulist);
		List<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
		String selectStr = "select _id,comname,pername,addr,city,diqu,youbian,tel,chuangzhen,web  from guke";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);

		cursor.moveToFirst();

		int count = cursor.getCount();
		id = new String[count];
		cname = new String[count];
		pname = new String[count];
		add = new String[count];
		city = new String[count];
		diqu = new String[count];
		youbian = new String[count];
		tel = new String[count];
		chuanzhen = new String[count];
		web = new String[count];

		do {
			try {
				id[i] = cursor.getString(0);
				cname[i] = cursor.getString(1);
				pname[i] = cursor.getString(2);
				add[i] = cursor.getString(3);
				city[i] = cursor.getString(4);
				diqu[i] = cursor.getString(5);
				youbian[i] = cursor.getString(6);
				tel[i] = cursor.getString(7);
				chuanzhen[i] = cursor.getString(8);
				web[i] = cursor.getString(9);

				i++;

			} catch (Exception e) {
				// TODO: handle exception

			}

		} while (cursor.moveToNext());

		for (int i = 0; i < id.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id[i]);
			map.put("cname", cname[i]);
			map.put("pname", pname[i]);
			map.put("add", add[i]);
			map.put("city", city[i]);
			map.put("diqu", diqu[i]);
			map.put("youbian", youbian[i]);
			map.put("tel", tel[i]);
			map.put("chuanzhen", chuanzhen[i]);
			map.put("web", web[i]);
			slist.add(map);
		}
		SimpleAdapter simple = new SimpleAdapter(this, slist,
				R.layout.shanchukadpter, new String[] { "id", "cname", "pname",
						"add", "city", "diqu", "youbian", "tel", "chuanzhen",
						"web" }, new int[] { R.id.t1, R.id.t2, R.id.t3,
						R.id.t4, R.id.t5, R.id.t6, R.id.t7, R.id.t8, R.id.t9,
						R.id.t10, });
		listview.setAdapter(simple);

	}

}
