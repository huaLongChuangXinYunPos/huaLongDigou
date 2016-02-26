package com.hlcxdg.digou.db.map.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.hlcxdg.digou.bean.Mapk;
import com.hlcxdg.digou.bean.Userk;
import com.hlcxdg.digou.constant.ConstantLocation;

public class MapDAO {
	private SQLiteDatabase db;

	public MapDAO(SQLiteDatabase db) {
		super();
		this.db = db;
	}

	/**
	 * 添加信息
	 */
	public void insert(Userk user) {

	}

	public int getUser() {
		List<Userk> list = new ArrayList<Userk>();
		String sql = "SELECT  userNo FROM " + MyDatabaseHelper.USERTABLE;
		Cursor result = this.db.rawQuery(sql, null);
		for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
			Log.i("userNO",""+ result.getInt(0));
			
		}
		return result.getCount();
	}

	public void insertMap() {

		ContentValues cv = new ContentValues();
		cv.put("lonj", ConstantLocation.myLongitudeString);
		cv.put("law", ConstantLocation.myLatitudeString);
		cv.put("user", 1);
		this.db.insert("mapk", null, cv);

	}
	public List<Mapk> getMap(int userid) {
		List<Mapk> mapkl=new ArrayList<Mapk>();
		String sql = "SELECT  lonj,law  FROM  " + MyDatabaseHelper.MAPTABLE+" where user="+userid;
		Cursor result = this.db.rawQuery(sql, null);
		for (result.moveToFirst(); !result.isAfterLast(); result.moveToNext()) {
			Mapk mapk=new Mapk();
			mapk.setLonj(result.getDouble(0));
			mapk.setLaw(result.getDouble(1));
			mapkl.add(mapk);
			
		}
		return mapkl;
	}
}
