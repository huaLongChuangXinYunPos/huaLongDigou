package com.hl_zhaoq.digou.db.map.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/*
 * 增删改查操作
 */
public class MytabOperate {

	private static final String TABLENAME = "user";
	private SQLiteDatabase db = null;

	public MytabOperate(SQLiteDatabase db) {
		this.db = db;
	}

	/**
	 * query all dt_category, return list
	 * 
	 * @return List<Person>
	 */
	// public List<category> query() {
	// ArrayList<category> dt_categorys = new ArrayList<category>();
	// Cursor c = queryTheCursor(strwhere);
	// while (c.moveToNext()) {
	// category category1 = new category();
	// category1.set_id(c.getInt(c.getColumnIndex("_id")));
	// category1.set_title(c.getString(c.getColumnIndex("_title")));
	// category1.set_content(c.getString(c.getColumnIndex("_content")));
	// //category1._buynumber = c.getInt(c.getColumnIndex("_buynumber"));
	// categorys.add(category1);
	// }
	// c.close();
	// return dt_categorys;
	// }
	/*
	 * 插入文件信息 String sql = "CREATE TABLE " + TABLENAME + " (" +
	 * "id       INTEGER(10)    PRIMARY KEY ," +
	 * "name       VARCHAR(50)    NULL," + "addr       VARCHAR(50)    NULL," +
	 * "tel       VARCHAR(50)     NULL," + "age       VARCHAR(50)    NULL," +
	 * "sex       VARCHAR(50)     NULL," + "pic       VARCHER(50)        NULL)";
	 * db.execSQL(sql);
	 */
	public void insert(String name, String addr, String tel, String age,
			String sex, String pic, String b1, String b2, String b3, String b4) {
		ContentValues cv = new ContentValues();
		cv.put("name", name);
		cv.put("addr", addr);
		cv.put("tel", tel);
		cv.put("age", age);
		cv.put("sex", sex);
		cv.put("pic", pic);
		cv.put("b1", b1);
		cv.put("b2", b2);
		cv.put("b3", b3);
		cv.put("b4", b4);
		this.db.insert(TABLENAME, null, cv);
		this.db.close();
	}

	public void insertall(String name, String addr, String tel, String age,
			String sex, String pic, String b1, String b2, String b3, String b4) {
		for (int i = 0; i < 100000; i++) {
			ContentValues cv = new ContentValues();
			cv.put("name", name);
			cv.put("addr", addr);
			cv.put("tel", tel);
			cv.put("age", age);
			cv.put("sex", sex);
			cv.put("pic", pic);
			cv.put("b1", b1);
			cv.put("b2", b2);
			cv.put("b3", b3);
			cv.put("b4", b4);
			this.db.insert(TABLENAME, null, cv);
		}
		this.db.close();
	}

	/*
	 * 根据文件名主键删除文件信息
	 */
	public void delete(int id) {
		String whereClause = "id=?";
		String[] whereArgs = new String[] { id + "" };
		this.db.delete(TABLENAME, whereClause, whereArgs);
		this.db.close();
	}

	/*
	 * 根据文件名字段更新文件信息
	 */
	public void updata(int username, int _id) {
		String UPDATE_DATA = "update " + TABLENAME + " set name=" + username
				+ " where _id = " + _id;
		db.execSQL(UPDATE_DATA);
	}

	/*
	 * 根据文件名字段删除文件信息
	 */
	public void deleteByKey(String mkey, String mvalue) {
		String whereClause = mkey + "=?";
		String[] whereArgs = new String[] { mvalue };
		this.db.delete(TABLENAME, whereClause, whereArgs);
		this.db.close();
	}

	/**
	 * 条件查询
	 * 
	 * @return Cursor
	 * 
	 *         public List<FileInfo> queryOne(String strwhere) { List<FileInfo>
	 *         list = new ArrayList<FileInfo>(); // String sql="select * from" +
	 *         TABLENAME; String sql =
	 *         "SELECT  name,  addr, tel,  age, sex,  pic, b1, b2, b3, b4 FROM "
	 *         + TABLENAME + " where " + strwhere; Cursor result =
	 *         this.db.rawQuery(sql, null); FileInfo fileInfo = null; for
	 *         (result.moveToFirst(); !result.isAfterLast();
	 *         result.moveToNext()) { fileInfo = new
	 *         FileInfo(result.getString(0), result.getString(1),
	 *         result.getString(2), result.getString(3), result.getString(4),
	 *         result.getString(5), result.getString(6), result.getString(7),
	 *         result.getString(8), result.getString(9)); list.add(fileInfo); }
	 *         this.db.close(); return list;
	 * 
	 *         }
	 */
	/*
	 * 文本信息查询
	 * 
	 * public List<FileInfo> findAll() { List<FileInfo> list = new
	 * ArrayList<FileInfo>(); // String sql="select * from" + TABLENAME; String
	 * sql = "SELECT  name,  addr, tel,  age, sex,  pic, b1, b2, b3, b4 FROM " +
	 * TABLENAME; Cursor result = this.db.rawQuery(sql, null); FileInfo fileInfo
	 * = null; for (result.moveToFirst(); !result.isAfterLast();
	 * result.moveToNext()) { fileInfo = new FileInfo(result.getString(0),
	 * result.getString(1), result.getString(2), result.getString(3),
	 * result.getString(4), result.getString(5), result.getString(6),
	 * result.getString(7), result.getString(8), result.getString(9));
	 * list.add(fileInfo); } this.db.close(); return list; }
	 */
}
