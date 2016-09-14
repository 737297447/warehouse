package com.lingdian.ui.sale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.lingdian.ui.BaseActivity;
import com.lingdian.ui.SqlHelpdemo;
import com.lingdian.warehouse.R;


/**
 * 查询商品页面
 * 
 * @author 
 * 
 */

public class SaleSearchActivity extends BaseActivity {
	private ListView listview;

	String id[];
	String spname[];
	String spprise[];
	String spnum[];
	SqlHelpdemo db;
	int i = 0;
	SQLiteDatabase sDatabase = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_sale);
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
		title_back_layout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		title_name = (TextView)findViewById(R.id.title_name);
		title_name.setText("商品销售情况");
		
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		listview = (ListView) findViewById(R.id.shangplist);
		List<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
		String selectStr = "select * from sale";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);

		cursor.moveToFirst();

		int count = cursor.getCount();
		
		id = new String[count];
		spname = new String[count];
		spprise = new String[count];
		spnum = new String[count];
		do {
			try {
				id[i] = cursor.getString(0);
				spname[i] = cursor.getString(1);
				spprise[i] = cursor.getString(2);
				spnum[i] = cursor.getString(3);
				i++;

			} catch (Exception e) {
				// TODO: handle exception
			}

		} while (cursor.moveToNext());

		for (int i = 0; i < id.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("did", id[i]);
			map.put("spname", spname[i]);
			map.put("spprise", spprise[i]);
			map.put("spnum", spnum[i]);
			slist.add(map);
		}
		SimpleAdapter simple = new SimpleAdapter(this, slist,
				R.layout.adapter_sale_listview, new String[] { "did", "spname", "spprise",
						"spnum"}, new int[] { R.id.t1, R.id.t2, R.id.t3,
						R.id.t4 });
		listview.setAdapter(simple);
	}

}
