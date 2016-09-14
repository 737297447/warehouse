package com.lingdian.ui;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class SqlHelpdemo extends SQLiteOpenHelper{
	/*
	 * 创建语句
	 */
	// 创建用户表
	String createUserTable = "create table user_info(_id int auto_increment,username char(20),"
			+ "password char(20),num char(20),primary key('_id'));";
	// 创建商品表(商品名称，商品编号，商品单位，如：本。。。)
	String creatproductsTable = "create Table products(_id int auto_increment,"
			+ "pname char(40),pguige char(20),pdanwei char(20),comname char(40),primary key('_id'));";
	// 创建顾客表
	String createguke = "create table guke(_id int auto_increment,"
			+ "comname char(40),pername char(40),addr char(40),"
			+ "city char(20),diqu char(40),youbian char(20),tel char(20),"
			+ "chuangzhen char(20),web char(40),primary key('_id'));";
	// 创建供应商表
	String creategongyings = "create table gongys(_id int auto_increment,"
			+ "comname char(40),pername char(40),addr char(40),"
			+ "city char(20),diqu char(40),youbian char(20),tel char(20),"
			+ "chuangzhen char(20),web char(40),primary key('_id'));";
	//创建入库表(products商品名称，guige商品编号)
	String createruku = "create table ruku(_id int auto_increment,"
			+ "comname char(40),pername char(40),tel char(40),"
			+ "products char(40),guige char(40),danwei char(20),danjia int,"
			+ "num int,date char(40),primary key('_id'));";
	//创建出库表
	String createchuku = "create table chuku(_id int auto_increment,"
			+ "comname char(40),pername char(40),tel char(40),"
			+ "products char(40),guige char(40),danwei char(20),danjia int,"
			+ "num int,date char(40),primary key('_id'));";
	//创建库存表
	String createkucun = "create Table kucun(_id int auto_increment,"
			+ "pname char(40),pguige char(20),pdanwei char(20),num int,primary key('_id'));";
	//创建售出货物表
	String createsale = "create Table sale(_id int auto_increment,"
				+ "spname char(40),spprise char(20),spnum int,primary key('_id'));";
	
	
	// 定义用户表插入语句
	String insertStr = "insert into user_info(_id,username,password,num) values(?,?,?,?)";
	// 定义商品表插入语句
	String insertproducts = "insert into products values(?,?,?,?,?);";
	// 定义顾客表插入语句
	String insertguke = "insert into guke values(?,?,?,?,?,?,?,?,?,?);";
	// 定义供应商表插入语句
	String insertgys = "insert into gongys values(?,?,?,?,?,?,?,?,?,?);";
	//定义入库表插入语句
	String insertruku = "insert into ruku values(?,?,?,?,?,?,?,?,?,?);";
	//定义出库表插入语句
	String insertchuku = "insert into chuku values(?,?,?,?,?,?,?,?,?,?);";
	//定义库存表插入语句
	String insertkucun = "insert into kucun values(?,?,?,?,?);";
	//卖出表插入语句
	String insertsale =  "insert into sale values(?,?,?,?);";


	public SqlHelpdemo(Context context, String name, CursorFactory factory,
			int version)
	{
		super(context, name, factory, version);

	}

	@Override
	public void onCreate(SQLiteDatabase arg0)
	{
		// 设置ID
		int _id = 0;
		// 创建用户表，用商品表，顾客表，入库表。出库表
		arg0.execSQL(createUserTable);
		arg0.execSQL(creatproductsTable);
        arg0.execSQL(createguke);
        arg0.execSQL(creategongyings);
        arg0.execSQL(createruku);
        arg0.execSQL(createchuku);
        arg0.execSQL(createkucun);
        arg0.execSQL(createsale);

		// 插入测试data
		String[] insertValue = { "0", "pushaolong", "123456", "001", };
		String[] insertValuesaohua = { "1", "saohua", "123456", "002", };
		String[] insguke={"0","蒲少龙","噗噗","铺上村14排3号","435698547","pupu123","710200","13455698888","pupuzhifubao","435698547@qq.com"};
		String[] gongyingshang={"0","老约翰绘本馆","老约翰","宁波市海曙区后河巷19号3幢2楼（宁波工程学院南门对面）","宁波市","西北区域","710200","0574-87343774","无","http://www.hui-ben.com/lianxi.asp"};
		String[] gongyingshang1={"1","天天向上","天天","宁波市海曙区后河巷19号3幢2楼（宁波工程学院南门对面）","宁波市","西北区域","710200","0574-87343774","无","http://www.hui-ben.com/lianxi.asp"};
		
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