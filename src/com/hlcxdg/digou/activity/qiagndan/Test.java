package com.hlcxdg.digou.activity.qiagndan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.Goods;
import com.hlcxdg.digou.bean.RequestBean;
import com.hlcxdg.digou.bean.StorePLParam;
import com.hlcxdg.digou.bean.UserNeedsBean;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.net.NetUtilddgg;
import com.hlcxdg.digou.net.RequestVoddgg;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.utils.hykj.selectarealib.utils.GetRegion;
import com.hlcxdg.digou.vo.RequestVo;

import android.test.AndroidTestCase;

public class Test extends AndroidTestCase {
	
	// 过
	public void testwoxiangyao() {
		UserNeedsBean userNeedsBean = new UserNeedsBean();

		List<UserNeedsBean> unList = new ArrayList<UserNeedsBean>();
		unList.add(userNeedsBean);
		RequestBean reqbean = new RequestBean();
		reqbean.setReqCode(5001);
		reqbean.setReqMsg("添加我想要");
		// reqbean.setUserneedsList(unList);
		Gson gson = new Gson();
		String jsonString = gson.toJson(reqbean);
		RequestVoddgg vo = new RequestVoddgg(R.string.url_userneeds, getContext(),
				jsonString);
		String bresult = NetUtilddgg.post(vo);
		System.out.println("ok" + bresult);

	}

	public void testStoreXiangqing2() {
		PromptManager.showLogTest("PLAsynTaskdoInBackground", "PLAsynTask");
		// 变量替换
		String stno = "10001";
		StorePLParam plp = new StorePLParam();
		ArrayList<StorePLParam> plList = new ArrayList<StorePLParam>();
		plList.add(plp);

		Gson gson = new Gson();
		String json = gson.toJson(plList);
		String goodsno = "440012";
		String msg = "*AIS|" + stno + "|" + goodsno + "|AIE#";
		// PromptManager.showLogTest("PLAsynTaskdoInBackground-postmesssage",
		// msg);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "post");
		map.put("url", getContext().getString(R.string.url_pl01));
		map.put("dataType", "text");
		map.put("data", msg);
		RequestVo vo = new RequestVo(R.string.url_pl01, getContext(), map);

		String result = NetUtil.post(vo);
		PromptManager.showLogTest("plAsynTaskdoInBackground-ArrayList<Goods>",
				"go");
		String[] temp = NetUtil.split(result, "|");
		if (temp == null) {
			PromptManager.showLogTest("enginenet", "net-result-null");
		} else {

			if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
					&& "PD01".equals(temp[2])) {
				PromptManager.setSharedPreferenceEditor(getContext(),
						"testStoreXiangqing", "temp3", temp[3]);

				ArrayList<Goods> goodsList = gson.fromJson(temp[3],
						new TypeToken<List<Goods>>() {
						}.getType());

			}
		}
	}
}
