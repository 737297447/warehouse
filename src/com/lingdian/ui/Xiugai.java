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
 * �޸�����ҳ��
 * 
 * @author ��־Զ
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
			// ʵ����SharedPreferences.Editor���󣨵ڶ�����
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
		title_name.setText("�޸�����");
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
			DialogDemo.builder(Xiugai.this, "��ʾ", "����д������Ϣ");
		} else if (!xinm.getText().toString().equals(quer.getText().toString())) {
			DialogDemo.builder(Xiugai.this, "������Ϣ", "�����������벻һ�£�");
		} else {
			do {
				try {
					pass = cursor.getString(0);

				} catch (Exception e) {
					// TODO: handle exception
					pass = "";
				}
				if (!jiumima.equals(pass)) {
					DialogDemo.builder(Xiugai.this, "������Ϣ", "ԭʼ�������");
					cursor.close();
					break;

				}

			} while (cursor.moveToNext());
			if (jiumima.equals(pass)) {

				sDatabase.execSQL("update user_info set password='" + xinmima
						+ "' where username='" + username + "'");
				Toast.makeText(Xiugai.this, "�޸ĳɹ�", Toast.LENGTH_LONG).show();
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
