package com.lingdian.ui;



import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;

import com.lingdian.ui.main.MenuFragmentActivity;
import com.lingdian.warehouse.R;

/**
 * ��¼ҳ��
 * 
 * @author 
 * 
 */
public class StoreManagementActivity extends Activity {
	private EditText use;// �û���
	private EditText password;// ����
	SqlHelpdemo db;
	SQLiteDatabase sDatabase = null;
	SharedPreferences mySharedPreferences;
	SharedPreferences.Editor editor;
	
	/** Called when the activity is first created. */

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		 mySharedPreferences = getSharedPreferences(
					"task.shiyong", 0);
			// ʵ����SharedPreferences.Editor���󣨵ڶ�����
			editor = mySharedPreferences.edit();
		use = (EditText) findViewById(R.id.edi1);
		password = (EditText) findViewById(R.id.edi2);
		db = new SqlHelpdemo(getApplicationContext(), "store.db", null, 1);
		sDatabase = db.getWritableDatabase();
		
		String  username = mySharedPreferences.getString("username", "");
		String  userPw = mySharedPreferences.getString("userPw", "");
		use.setText(username);
		password.setText(userPw);
	
	}

	// ��¼��ť����
	public void onload(View v)
	{
		// ����ȡ���ݵ��ַ���
		String userName = "";
		String userPw = "";

		String i = use.getText().toString();
//		��д���ݿ����
		String select_sql = "select username,password from user_info where username = '"
				+ i + "'";
//		ִ�����
		Cursor cursor = sDatabase.rawQuery(select_sql, null);
		cursor.moveToFirst();
		// ����������ȡ�����û��������븳ֵ�������ַ�������
		try
		{
			userName = cursor.getString(0);
			userPw = cursor.getString(1);
		} catch (Exception e)
		{
			// TODO: handle exception
			userName = "";
			userPw = "";
		}

//		�ж��û����Ƿ�Ϊ��
		if (use.getText().toString().equals(""))
		{
			DialogDemo.builder(StoreManagementActivity.this, "������Ϣ",
					"�û�������Ϊ�գ�");
		}
//		�ж������Ƿ�Ϊ��
		else if (password.getText().toString().equals(""))
		{
			DialogDemo.builder(StoreManagementActivity.this, "������Ϣ",
					"���벻��Ϊ�գ�");
		} 
//		�ж��û����������Ƿ���ȷ
		else if (!(use.getText().toString().equals(userName) && password
				.getText().toString().equals(userPw)))
		{
			DialogDemo.builder(StoreManagementActivity.this, "������Ϣ",
					"�û����������������������");
		}
//		ȫ����ȷ��ת����������
		else
		{
			cursor.close();
			// ��putString�ķ�����������
			editor.putString("username", userName);
			editor.putString("userPw", userPw);
			// �ύ��ǰ����
			editor.commit();
			
			Intent intent = new Intent(this,MenuFragmentActivity.class);
//			Bundle bundle = new Bundle();
//			bundle.putString("username",userName);
//			intent.putExtras(bundle);
//			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
            finish();
		}
	}
	//ע�ᰴť����

	public void onres(View v) {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), UserRegister.class);
		startActivity(intent);
	
	}

}