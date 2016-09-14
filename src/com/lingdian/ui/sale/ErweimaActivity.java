package com.lingdian.ui.sale;

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

public class ErweimaActivity extends BaseActivity {

	SqlHelpdemo db;
	int i = 0;
	SQLiteDatabase sDatabase = null;
	String id[];
	String goodsName[];
	String goodsNum[];
	String prise[];

	private ListView erweima_list;
	private ImageView iv_qr_image;
    private TextView tiaoma_tishi;
    private TextView tiaoma_contact_text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_erweima);
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
		title_name.setText("生成二维码");

		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		erweima_list = (ListView) findViewById(R.id.erweima_list);
		iv_qr_image = (ImageView) findViewById(R.id.iv_qr_image);
		tiaoma_tishi = (TextView) findViewById(R.id.tiaoma_tishi);
		tiaoma_contact_text = (TextView) findViewById(R.id.tiaoma_contact_text);
		
		selectChuku();
	}

	public void selectChuku() {
		List<Map<String, Object>> slist = new ArrayList<Map<String, Object>>();
		String selectStr = "select _id,products,guige,danjia from chuku";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);

		cursor.moveToFirst();

		int count = cursor.getCount();
		id = new String[count];
		goodsName = new String[count];
		goodsNum = new String[count];
		prise = new String[count];

		do {
			try {
				id[i] = cursor.getString(0);
				goodsName[i] = cursor.getString(1);
				goodsNum[i] = cursor.getString(2);
				prise[i] = cursor.getString(3);
				i++;

			} catch (Exception e) {
				// TODO: handle exception

			}

		} while (cursor.moveToNext());

		for (int i = 0; i < id.length; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", id[i]);
			map.put("goodsName", goodsName[i]);
			map.put("goodsNum", goodsNum[i]);
			map.put("prise", prise[i]);
			slist.add(map);
		}
		SimpleAdapter simple = new SimpleAdapter(this, slist,
				R.layout.erweima_dapter, new String[] { "id", "goodsName",
						"goodsNum", "prise" }, new int[] { R.id.t1, R.id.t2,
						R.id.t3, R.id.t4 });
		erweima_list.setAdapter(simple);
		erweima_list.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				
			    for(int j=0;j<parent.getCount();j++){
		            View v=parent.getChildAt(j);
		            if (position == j) {
		                v.setBackgroundColor(Color.RED);
		            } else {
		                v.setBackgroundColor(Color.TRANSPARENT);
		            }
		        }
				
				try {
					String contentString = "$$"+","+goodsName[position]+","+goodsNum[position]+","+prise[position];
					tiaoma_tishi.setVisibility(View.GONE);
				    tiaoma_contact_text.setVisibility(View.VISIBLE);
				    tiaoma_contact_text.setText(contentString);
					if (contentString != null && contentString.trim().length() > 0) {
						//根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小（350*350）
						Bitmap qrCodeBitmap =EncodingHandler.createQRCode(contentString, 350);
						iv_qr_image.setImageBitmap(qrCodeBitmap);
					}else {
						Toast.makeText(ErweimaActivity.this, "这一条数据有误，其中，商品名称，商品编号，和单价都为空", Toast.LENGTH_SHORT).show();
					}
					
				} catch (WriterException e) {
					e.printStackTrace();
				}
				
			}
		});
	}

}
