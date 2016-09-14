package com.lingdian.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lingdian.warehouse.R;

/**
 * 修改密码页面
 * 
 * @author 田志远
 * 
 */

public class Xiugai extends BaseActivity {
	private EditText jium;
	private EditText xinm;
	private EditText quer;
	String names;
	SqlHelpdemo db;
	SQLiteDatabase sDatabase = null;

	SharedPreferences mySharedPreferences;
	SharedPreferences.Editor editor;
	String  username;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xiugai);
		 mySharedPreferences = getSharedPreferences(
					"task.shiyong", 0);
			// 实例化SharedPreferences.Editor对象（第二步）
	    editor = mySharedPreferences.edit();
	    username = mySharedPreferences.getString("username", "");
	    
		title_back_layout = (LinearLayout) findViewById(R.id.title_back_layout);
		title_back_layout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		title_name = (TextView) findViewById(R.id.title_name);
		title_name.setText("修改密码");
		Intent inte = getIntent();
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		names = inte.getStringExtra("username");
		xinm = (EditText) findViewById(R.id.xinmima);
		jium = (EditText) findViewById(R.id.jiumima);
		quer = (EditText) findViewById(R.id.querenmima);

	}

	public void save(View v) {
		String selectStr = "select password from user_info where username='"
				+ username + "'";
		Cursor cursor = sDatabase.rawQuery(selectStr, null);
		cursor.moveToFirst();
		String pass = null;
		String jiumima = jium.getText().toString();
		String xinmima = xinm.getText().toString();
		String quemima = quer.getText().toString();
		if (jiumima.equals("") || xinmima.equals("") || quemima.equals("")) {
			DialogDemo.builder(Xiugai.this, "提示", "请填写完整信息");
		} else if (!xinm.getText().toString().equals(quer.getText().toString())) {
			DialogDemo.builder(Xiugai.this, "错误信息", "两次密码输入不一致！");
		} else {
			do {
				try {
					pass = cursor.getString(0);

				} catch (Exception e) {
					// TODO: handle exception
					pass = "";
				}
				if (!jiumima.equals(pass)) {
					DialogDemo.builder(Xiugai.this, "错误信息", "原始密码错误");
					cursor.close();
					break;

				}

			} while (cursor.moveToNext());
			if (jiumima.equals(pass)) {

				sDatabase.execSQL("update user_info set password='" + xinmima
						+ "' where username='" + username + "'");
				Toast.makeText(Xiugai.this, "修改成功", Toast.LENGTH_LONG).show();
				xinm.setText("");
				jium.setText("");
				quer.setText("");
			}

		}

	}

	public void back(View v) {
		finish();

	}

}
