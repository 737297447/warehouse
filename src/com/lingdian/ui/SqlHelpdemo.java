package com.lingdian.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelpdemo extends SQLiteOpenHelper{
	/*
	 * �������
	 */
	// �����û���
	String createUserTable = "create table user_info(_id int auto_increment,username char(20),"
			+ "password char(20),num char(20),primary key('_id'));";
	// ������Ʒ��(��Ʒ���ƣ���Ʒ��ţ���Ʒ��λ���磺��������)
	String creatproductsTable = "create Table products(_id int auto_increment,"
			+ "pname char(40),pguige char(20),pdanwei char(20),comname char(40),primary key('_id'));";
	// �����˿ͱ�
	String createguke = "create table guke(_id int auto_increment,"
			+ "comname char(40),pername char(40),addr char(40),"
			+ "city char(20),diqu char(40),youbian char(20),tel char(20),"
			+ "chuangzhen char(20),web char(40),primary key('_id'));";
	// ������Ӧ�̱�
	String creategongyings = "create table gongys(_id int auto_increment,"
			+ "comname char(40),pername char(40),addr char(40),"
			+ "city char(20),diqu char(40),youbian char(20),tel char(20),"
			+ "chuangzhen char(20),web char(40),primary key('_id'));";
	//��������(products��Ʒ���ƣ�guige��Ʒ���)
	String createruku = "create table ruku(_id int auto_increment,"
			+ "comname char(40),pername char(40),tel char(40),"
			+ "products char(40),guige char(40),danwei char(20),danjia int,"
			+ "num int,date char(40),primary key('_id'));";
	//���������
	String createchuku = "create table chuku(_id int auto_increment,"
			+ "comname char(40),pername char(40),tel char(40),"
			+ "products char(40),guige char(40),danwei char(20),danjia int,"
			+ "num int,date char(40),primary key('_id'));";
	//��������
	String createkucun = "create Table kucun(_id int auto_increment,"
			+ "pname char(40),pguige char(20),pdanwei char(20),num int,primary key('_id'));";
	//�����۳������
	String createsale = "create Table sale(_id int auto_increment,"
				+ "spname char(40),spprise char(20),spnum int,primary key('_id'));";
	
	
	// �����û���������
	String insertStr = "insert into user_info(_id,username,password,num) values(?,?,?,?)";
	// ������Ʒ��������
	String insertproducts = "insert into products values(?,?,?,?,?);";
	// ����˿ͱ�������
	String insertguke = "insert into guke values(?,?,?,?,?,?,?,?,?,?);";
	// ���幩Ӧ�̱�������
	String insertgys = "insert into gongys values(?,?,?,?,?,?,?,?,?,?);";
	//��������������
	String insertruku = "insert into ruku values(?,?,?,?,?,?,?,?,?,?);";
	//��������������
	String insertchuku = "insert into chuku values(?,?,?,?,?,?,?,?,?,?);";
	//�������������
	String insertkucun = "insert into kucun values(?,?,?,?,?);";
	//������������
	String insertsale =  "insert into sale values(?,?,?,?);";


	public SqlHelpdemo(Context context, String name, CursorFactory factory,
			int version)
	{
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase arg0)
	{
		// ����ID
		int _id = 0;
		// �����û�������Ʒ���˿ͱ����������
		arg0.execSQL(createUserTable);
		arg0.execSQL(creatproductsTable);
        arg0.execSQL(createguke);
        arg0.execSQL(creategongyings);
        arg0.execSQL(createruku);
        arg0.execSQL(createchuku);
        arg0.execSQL(createkucun);
        arg0.execSQL(createsale);

		// �������data
		String[] insertValue = { "0", "pushaolong", "123456", "001", };
		String[] insertValuesaohua = { "1", "saohua", "123456", "002", };
		String[] insguke={"0","������","����","���ϴ�14��3��","435698547","pupu123","710200","13455698888","pupuzhifubao","435698547@qq.com"};
		String[] gongyingshang={"0","��Լ���汾��","��Լ��","�����к����������19��3��2¥����������ѧԺ���Ŷ��棩","������","��������","710200","0574-87343774","��","http://www.hui-ben.com/lianxi.asp"};
		String[] gongyingshang1={"1","��������","����","�����к����������19��3��2¥����������ѧԺ���Ŷ��棩","������","��������","710200","0574-87343774","��","http://www.hui-ben.com/lianxi.asp"};
		
		arg0.execSQL(insertStr, insertValue);
		arg0.execSQL(insertStr, insertValuesaohua);
		arg0.execSQL(insertguke, insguke);
		arg0.execSQL(insertgys, gongyingshang);
		arg0.execSQL(insertgys, gongyingshang1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2)
	{
		// TODO Auto-generated method stub

	}

}