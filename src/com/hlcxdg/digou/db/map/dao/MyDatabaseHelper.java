package com.hlcxdg.digou.db.map.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * sqlite 数据库创建,包含多个表
 * 
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {
	private static final String DATABASENAME = "mapdata.db"; // 数据库名字
	private static final int DATABASEVERSION = 8;// 版本
	public static final String USERTABLE = "userk";// 快递员表名，统一常量
	public static final String MAPTABLE = "mapk";// 快递map表名，统一常量
	
	private static final String Test = "user";// 表名，统一常量

	public MyDatabaseHelper(Context context) {
		super(context, DATABASENAME, null, DATABASEVERSION);
	}

	/*
	 * 创建数据库
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		String usersql = "CREATE TABLE " + USERTABLE + " ("
				+ "userNo       INTEGER    NOT NULL      PRIMARY KEY ,"
				+ "tel       VARCHAR(32)    NULL,"
				+ "name       VARCHAR(32)    NULL)";
		String mapsql = "CREATE TABLE " + MAPTABLE + " ("
				+ "locNo       INTEGER    NOT NULL   PRIMARY KEY ,"
				+ "lonj       DOUBLE(32)    NULL,"
				+ "law       DOUBLE(32)    NULL,"
				+ "user       VARCHAR(32)    NULL)";

		db.execSQL(usersql);
		db.execSQL(mapsql);
		String insertuser="insert into  "+USERTABLE+"(tel,name) values ('13489876543','小')"
				+ ",('13489876545','小'),('13489876546','小');";
		db.execSQL(insertuser);
		String insertmap="insert into  "+MAPTABLE+"(lonj,law,user) values "
				+ "(116.323026,39.98845,1)"
				+ ",(116.323026,39.99044,1)"
		+ ",(116.322882,39.991877,1)"
		+ ",(116.322739,39.992817,1)"
		+ ",(116.32281,39.994199,1)"
		+ ",(116.322451,39.995747,1)"
		+ ",(116.322451,39.996686,1)"
		+ ",(116.322325,39.998317,1)"
		+ ",(116.322253,39.99934,1)"
		+ ",(116.322092,40.000321,1)"
		+ ",(116.322128,40.001039,1)"
		+ ",(116.322056,40.002324,1)"
		+ ",(116.321876,40.003402,1)"
		+ ",(116.321804,40.004051,1)"
		+ ",(116.32175,40.005309,1)"
		+ ",(116.321697,40.00629,1)"
		+ ",(116.32175,40.007243,1)"
		+ ",(116.321795,40.009091,1)"
		+ ",(116.321795,40.010127,1)"
		+ ",(116.322191,40.011219,1)"
		+ ",(116.32343,40.011854,1)"
		+ ",(116.325155,40.012434,1)"
		+ ",(116.325407,40.012849,1)"
		+ ",(116.325424,40.01383,1)"
		+ ",(116.325281,40.014811,1)"
		+ ",(116.3252,40.016278,1)"
		+ ",(116.3252,40.017052,1)"
		+ ",(116.324948,40.018682,1)"
		+ ",(116.324769,40.019732,1)"
		+ ",(116.324625,40.020726,1)"
		+ ",(116.324338,40.021776,1)"
		+ ",(116.323727,40.022964,1)"
		+ ",(116.322469,40.024125,1)"
		+ ",(116.321319,40.025064,1)"
		+ ",(116.320816,40.026721,1)"
		+ ",(116.320349,40.029346,1)"
		+ ",(116.320169,40.030699,1)"
		+ ",(116.31999,40.032246,1)"
		+ ",(116.319882,40.03371,1)"
		+ ",(116.319594,40.035367,1)"
		+ ",(116.319235,40.036638,1)"
		+ ",(116.318876,40.037549,1)"
		+ ",(116.318588,40.038185,1)"
		+ ",(116.318121,40.039234,1)"
		+ ",(116.318049,40.039787,1)"
		+ ",(116.317726,40.040891,1)"
		+ ",(116.317331,40.041582,1)"
		+ ",(116.317187,40.042272,1)"
		+ ",(116.3169,40.042825,1)"
		+ ",(116.316468,40.043597,1);";
		db.execSQL(insertuser);
		db.execSQL(insertmap);

	}

	/*
	 * 更新数据库
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sqluser = "DROP TABLE IF EXISTS " + USERTABLE;
		String sqlmap = "DROP TABLE IF EXISTS " + MAPTABLE;
		db.execSQL(sqluser);
		db.execSQL(sqlmap);
		this.onCreate(db);
	}

}
