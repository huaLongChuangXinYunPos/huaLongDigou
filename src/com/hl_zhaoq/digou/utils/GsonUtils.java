package com.hl_zhaoq.digou.utils;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.bean.UserInfo;

public class GsonUtils {
	public static  Gson gson = new Gson();
	
	public static String listToJsonString(List list) {
		return gson.toJson(list);
	}

	public static String ObjectToJsonString(Object o) {
		return gson.toJson(o);
	}
//	public static List<T extends Object> JsonStringToList(Class<T extends Object> T,String jsonStr) {
//		return gson.fromJson(jsonStr,
//				new TypeToken<List<User>>() {
//				}.getType());
//	}
//	public static List<T extends Object> JsonStringToList(Class<T extends Object> T,String jsonStr) {
//		return gson.fromJson(jsonStr,
//				new TypeToken<List<User>>() {
//				}.getType());
//	}
}
