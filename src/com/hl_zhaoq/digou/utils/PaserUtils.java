package com.hl_zhaoq.digou.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hl_zhaoq.digou.bean.Ads;

public class PaserUtils {
	
	public static Map<String,Object> parserHomeAdsJson(byte[] buff) {
		Map<String,Object> map=new HashMap<String, Object>();
		List<Ads> list0=null;
		List<Ads> list1=null;
		Ads ad=null;
		try {
			JSONObject jsonObject=new JSONObject(new String(buff));
			list0=new ArrayList<Ads>();
			JSONArray ja1=jsonObject.getJSONArray("locationAds");
			for (int i = 0; i < ja1.length(); i++) {
				JSONObject jo=ja1.getJSONObject(i);
				ad=new Ads();
				ad.setAdPicUrl(jo.getString("adPicUrl"));
				ad.setAdPrdUrl(jo.getString("adPrdUrl"));
				list0.add(ad);
			}
			map.put("locationAds", list0);
			
			list1=new ArrayList<Ads>();
			JSONArray ja2=jsonObject.getJSONArray("scrollAds");
			for (int i = 0; i < ja2.length(); i++) {
				JSONObject jo=ja2.getJSONObject(i);
				ad=new Ads();
				ad.setAdPicUrl(jo.getString("adPicUrl"));
				ad.setAdPrdUrl(jo.getString("adPrdUrl"));
				list1.add(ad);
			}
			map.put("scrollAds", list1);
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return map;
	}

	
	public static List<String> parserHomeHotJson(byte[] buff){
		List<String> list=null;
		try {
			JSONObject jsonObject=new JSONObject(new String(buff));
			list=new ArrayList<String>();
			list.add(jsonObject.getString("defalutSearchWord"));
			JSONArray jsonArray=jsonObject.getJSONArray("hotWordList");
			for (int i = 0; i < jsonArray.length(); i++) {
				list.add(jsonArray.getString(i));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	


}







