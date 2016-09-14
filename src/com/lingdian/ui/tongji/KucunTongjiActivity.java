package com.lingdian.ui.tongji;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.WriterException;
import com.lingdian.ui.BaseActivity;
import com.lingdian.ui.SqlHelpdemo;
import com.lingdian.warehouse.R;
import com.zxing.encoding.EncodingHandler;

public class KucunTongjiActivity extends BaseActivity {

	SqlHelpdemo db;
	int i = 0;
	int j = 0;
	SQLiteDatabase sDatabase = null;
	String cid[];
	String cgoodsName[];
	String cgoodsNum[];
	
	String rid[];
	String rgoodsName[];
	String rgoodsNum[];

	private ListView chuku_list;
	private ListView ruku_list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kucun_tongji);
		initView();
	}

	public void initView() {
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
		title_back_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		title_name = (TextView) findViewById(R.id.title_name);
		title_name.setText("库存查询");

		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		chuku_list = (ListView) findViewById(R.id.chuku_list);
		ruku_list = (ListView) findViewById(R.id.ruku_list);
		
		
		selectChuku();
		selectRuku();
	}

	/** 查询出库中的商品数量*/
	public void selectChuku() {
		List<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
		String selectStr = "select _id,products,num from chuku";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);

		cursor.moveToFirst();

		int count = cursor.getCount();
		cid = new String[count];
		cgoodsName = new String[count];
		cgoodsNum = new String[count];
		do {
			try {
				cid[i] = cursor.getString(0);
				cgoodsName[i] = cursor.getString(1);
				cgoodsNum[i] = cursor.getString(2);
				i++;

			} catch (Exception e) {
				// TODO: handle exception

			}

		} while (cursor.moveToNext());

		for (int i = 0; i < cid.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("cid", cid[i]);
			map.put("cgoodsName", cgoodsName[i]);
			map.put("cgoodsNum", cgoodsNum[i]);
			slist.add(map);
		}
		SimpleAdapter simple = new SimpleAdapter(this, slist,
				R.layout.tongji_kucun_dapter, new String[] { "cid", "cgoodsName",
						"cgoodsNum" }, new int[] { R.id.t1, R.id.t2,
						R.id.t3 });
		chuku_list.setAdapter(simple);
	}
	
	/** 查询入库中的商品数量*/
	public void selectRuku() {
		List<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
		String selectStr = "select _id,products,num from ruku";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);

		cursor.moveToFirst();

		int count = cursor.getCount();
		rid = new String[count];
		rgoodsName = new String[count];
		rgoodsNum = new String[count];
		do {
			try {
				rid[j] = cursor.getString(0);
				rgoodsName[j] = cursor.getString(1);
				rgoodsNum[j] = cursor.getString(2);
				j++;

			} catch (Exception e) {
				// TODO: handle exception

			}

		} while (cursor.moveToNext());

		for (int i = 0; i < rid.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rid", rid[i]);
			map.put("rgoodsName", rgoodsName[i]);
			map.put("rgoodsNum", rgoodsNum[i]);
			slist.add(map);
		}
		SimpleAdapter simple = new SimpleAdapter(this, slist,
				R.layout.tongji_kucun_dapter, new String[] { "rid", "rgoodsName",
						"rgoodsNum" }, new int[] { R.id.t1, R.id.t2,
						R.id.t3 });
		ruku_list.setAdapter(simple);
	}

}
