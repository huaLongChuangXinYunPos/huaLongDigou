package com.hlcxdg.digou.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.AdvertBean;
import com.hlcxdg.digou.bean.Goods;
import com.hlcxdg.digou.bean.GroupType1;
import com.hlcxdg.digou.bean.GroupType2;
import com.hlcxdg.digou.bean.GroupType3;
import com.hlcxdg.digou.bean.PC03params;
import com.hlcxdg.digou.bean.PLParam;
import com.hlcxdg.digou.bean.StoreItem;
import com.hlcxdg.digou.bean.StoreListParam;
import com.hlcxdg.digou.bean.StorePLParam;
import com.hlcxdg.digou.bean.UserInfo;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.test.AndroidTestCase;
import android.util.Log;
import android.widget.ProgressBar;

public class Test extends AndroidTestCase {
	public void testads() {
		
		
		
	}

	// 过
	public void testStoreXiangqing() {
		PromptManager.showLogTest("PLAsynTaskdoInBackground", "PLAsynTask");
		// 变量替换
		String stno = "10001";
		StorePLParam plp = new StorePLParam();
		ArrayList<StorePLParam> plList = new ArrayList<StorePLParam>();
		plList.add(plp);
		Gson gson = new Gson();
		String json = gson.toJson(plList);
		String goodsno = "440012";
		String msg = "*AIS|RQ|PD01|" + stno + "|" + goodsno + "|AIE#";
		PromptManager.showLogTest("PLAsynTaskdoInBackground-postmesssage", msg);
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

	// 过
	public void testSanleiOfStore() {
		PromptManager.showLogTest("SanleiAsynTaskdoInBackground",
				"SanleiAsynTask");
		String stno = "10001";
		Gson json = new Gson();
		ArrayList<PC03params> pc03list = new ArrayList<PC03params>();
		pc03list.add(new PC03params());
		String jsonStr = json.toJson(pc03list);
		String msg = "*AIS|RQ|PC03|" + stno + "|" + jsonStr + "|AIE#";
		PromptManager.showLogTest("SanleiAsynTaskdoInBackground-postmesssage",
				msg);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "post");
		map.put("url", getContext().getString(R.string.url_pc03));
		map.put("dataType", "text");
		map.put("data", msg);
		RequestVo vo = new RequestVo(R.string.url_pc03, getContext(), map);
		String result = NetUtil.post(vo);
		if (result == null) {
			PromptManager.showLogTest("enginenet", "net-result-null");
		} else {
			String[] temp = NetUtil.split(result, "|");
			if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
					&& "PC03".equals(temp[2])) {
				// t3

				PromptManager.setSharedPreferenceEditor(getContext(),
						"testSanleiOfStore", "temp3", temp[3]);
				String t3 = PromptManager.getSharedPreferenceEditor(
						getContext(), "testSanleiOfStore", "temp3");

				Gson gson = new Gson();
				ArrayList<GroupType3> gt3list = gson.fromJson(temp[3],
						new TypeToken<List<GroupType3>>() {
						}.getType());
				PromptManager.showLogTest(
						"SanleiAsynTaskdoInBackground-ArrayList<GroupType3>",
						gt3list.toString() + "/" + temp[3]);
			}
		}
	}

	// 过
	public void testErleiOfStore() {
		PromptManager.showLogTest("ErleiAsynTaskdoInBackground",
				"ErleiAsynTask");
		String gt2no = "02";
		String stno = "10001";
		String msg = "*AIS|RQ|PC02|" + gt2no + "|" + stno + "|AIE#";
		PromptManager.showLogTest("ErleiAsynTaskdoInBackground-postmesssage",
				msg);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "post");
		map.put("url", getContext().getString(R.string.url_pc02));
		map.put("dataType", "text");
		map.put("data", msg);
		RequestVo vo = new RequestVo(R.string.url_pc02, getContext(), map);
		String result = NetUtil.post(vo);
		if (result == null) {
			PromptManager.showLogTest("enginenet", "net-result-null");
		} else {
			String[] temp = NetUtil.split(result, "|");
			if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
					&& "PC02".equals(temp[2])) {
				PromptManager.setSharedPreferenceEditor(getContext(),
						"testErleiOfStore", "temp3", temp[3]);
				String t3 = PromptManager.getSharedPreferenceEditor(
						getContext(), "testErleiOfStore", "temp3");
				Gson gson = new Gson();
				ArrayList<GroupType2> gt2list = gson.fromJson(temp[3],
						new TypeToken<List<GroupType2>>() {
						}.getType());
			}
		}
	}

	// 过
	public void testDaleiOfStore() {
		// sp = getContext().getSharedPreferences("baocuntesttmp3",
		// getContext().MODE_PRIVATE);
		// // sp.edit().putString("dalei", temp[3]).commit();
		PromptManager.showLogTest("DaleiAsynTaskdoInBackground",
				"DaleiAsynTask");
		String st1no = "10001";
		String msg = "*AIS|RQ|PC01|00|" + st1no + "|AIE#";
		PromptManager.showLogTest("testDaleiOfStore-msg", msg);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "post");
		map.put("url", getContext().getString(R.string.url_pc01));
		map.put("dataType", "text");
		map.put("data", msg);
		RequestVo vo = new RequestVo(R.string.url_pc01, getContext(), map);

		String result = NetUtil.post(vo);
		if (result == null) {
			PromptManager.showLogTest("fanhui-net", "net-result-null");
		} else {
			String[] temp = NetUtil.split(result, "|");
			if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
					&& "PC01".equals(temp[2])) {
				Gson gson = new Gson();
				sp.edit().putString("dalei", temp[3]).commit();
				// 3
				// PromptManager.setSharedPreferenceEditor(getContext(),
				// "testDaleiOfStore", "temp3", temp[3]);
				// String
				// t3=PromptManager.getSharedPreferenceEditor(getContext(),
				// "testDaleiOfStore", "temp3");
				ArrayList<GroupType1> grouptype1list = gson.fromJson(temp[3],
						new TypeToken<List<GroupType1>>() {
						}.getType());

			}
		}
	}

	SharedPreferences sp;

	/*
	 * 3.1、0xST01：商品一类 3.1.1请求指令AIS|RQ|ST01| 00| ST01JSON指令|00|AIE#AIS:开始标志
	 * RQ：请求类型 ST01：商品一类 00:占位(忽略)
	 * ST01JSON指令:占位(忽略)，6个参数（经度longitude，纬度Latitude，Area，
	 * cStoreTypeNo（类型编号）,IndexNo(0..1..2..n)，Count(9,12,15) ）
	 * Area:选择的区域，0表示没有，别的表示有区域参数， cStoreTypeNo（类型编号）：默认是0，表示用便利店 AIE#：指令结束
	 * 
	 * 3.1.2响应AIS|RS| ST01|01|商店JSON数据|商店展示数据|AIE#AIS:开始标志 RS：请求类型 ST01：商品一类
	 * 01:成功有，00没数据 content:响应内容，JSON格式数据；9个店铺的JSON| 9个店铺的经营展示商品 AIE#：指令结束
	 * 
	 * [{"groupNO1":"02","grName1":"奶/饮料/酒"},{"groupNO1":"03","grName1":"休闲食品"},{
	 * "groupNO1"
	 * :"04","grName1":"生鲜食品"},{"groupNO1":"05","grName1":"个人护理"},{"groupNO1"
	 * :"06"
	 * ,"grName1":"家庭清洁"},{"groupNO1":"07","grName1":"母婴天地"},{"groupNO1":"08"
	 * ,"grName1"
	 * :"文体"},{"groupNO1":"09","grName1":"油炸食品"},{"groupNO1":"10","grName1"
	 * :"即食食品"},{"groupNO1":"11","grName1":"家居"}]
	 * 
	 * [{"groupNO2":"021","grName2":"牛奶/乳品"},{"groupNO2":"022","grName2":"冲调/咖啡"}
	 * ,{"groupNO2":"023","grName2":"饮料"},{"groupNO2":"024","grName2":"酒"}],
	 * AIE#*AIS, RS, PC03, *AIS, RS, PC03,
	 * [{"groupNO3":"0211","grName3":"纯牛奶","cIMG"
	 * :"/DOGOServer/pic/b0211.jpg"},{"groupNO3"
	 * :"0212","grName3":"儿童牛奶","cIMG":"/DOGOServer/pic/b0212.jpg"
	 * },{"groupNO3":"0213"
	 * ,"grName3":"豆奶/豆浆","cIMG":"/DOGOServer/pic/b0213.jpg"}]
	 * 
	 * [{"groupNO3":"0231","grName3":"果汁","cIMG":"/DOGOServer/pic/x0231.jpg"},{
	 * "groupNO3"
	 * :"0232","grName3":"茶饮料","cIMG":"/DOGOServer/pic/x0232.jpg"},{"groupNO3"
	 * :"0233"
	 * ,"grName3":"碳酸饮料","cIMG":"/DOGOServer/pic/x0233.jpg"},{"groupNO3":"0234"
	 * ,"grName3"
	 * :"功能饮料","cIMG":"/DOGOServer/pic/x0234.jpg"},{"groupNO3":"0235","grName3"
	 * :"乳酸饮料"
	 * ,"cIMG":"/DOGOServer/pic/x0235.jpg"},{"groupNO3":"0236","grName3":"水"
	 * ,"cIMG":"/DOGOServer/pic/x0236.jpg"}]
	 * 
	 * //store的fujidianopu
	 * [{"cStoreNo":"10011","cStoreName":"福满园超市","cCredit":"05"
	 * ,"cLevel":"null","fDistance":null,"fDistance":null:[{[]}]} ]
	 * 
	 * [{"cStoreNo":"10001","cStoreName":"乐尚","cCredit":"05","cLevel":"null",
	 * "fDistance":null,"fDistance":null
	 * :[{[{"cStoreNo":"10001","O_cPinpaiNo":"110001",
	 * "cPinpaiGrantNo":"101011010"
	 * ,"cPinpaiGrantPic":"/pinpaigrantpic/1001.jpg"}]}]} ]
	 * 
	 * AIS|RQ|OD02|00|[{"bSent":"","bVipPrice":0.0,"bVipRate":"","cBarCode":"",
	 * "cGoodsName"
	 * :"维达 超韧系列4层自然无香型纸手帕*(10+2)包","cGoodsNo":"440123","cSaleSheetno"
	 * :"20150915093151_20150809121207kk"
	 * ,"cVipNo":"","cWHno":"","dSaleDate":"2015-08-08 09:09:40"
	 * ,"fAgio":0.0,"fLastMoney"
	 * :6.00,"fPrice":0.0,"fPriceType":"","fQuantity":1,
	 * "fVipPrice":0.0,"fVipRate":"","fVipScore_cur":0.0,"iSeed":1}]|AIE#
	 * 
	 * [[{"cStoreNo":"10001","cStoreName":"乐尚","cCredit":"05","cLevel":"null",
	 * "fDistance"
	 * :null,"logPic":null,Pinai:[{"cStoreNo":"10001","O_cPinpaiNo":"110001"
	 * ,"cPinpaiGrantNo"
	 * :"101011010","cPinpaiGrantPic":"/pinpaigrantpic/1001.jpg"}]}] ]
	 * 
	 * [{"cStoreNo":"10001","cStoreName":"乐尚","cCredit":"05","cLevel":"null",
	 * "fDistance":null,
	 * "logPic":null,Pinai:[{"cStoreNo":"10001","O_cPinpaiNo":"110001"
	 * ,"cPinpaiGrantNo":"101011010",
	 * "cPinpaiGrantPic":"/pinpaigrantpic/1001.jpg"}]}] StoreItem
	 * [{"cStoreNo":"10001","cStoreName":"乐尚","cCredit":"05","cLevel":"null",
	 * "fDistance"
	 * :null,"logPic":null,"Pinpai":[{"cStoreNo":"10001","O_cPinpaiNo":"110001",
	 * "cPinpaiGrantNo"
	 * :"101011010","cPinpaiGrantPic":"/pinpaigrantpic/1001.jpg"}]}]
	 * 
	 * [{"cStoreNo":"10001","cStoreName":"乐尚","cCredit":"05","cLevel":"null",
	 * "fDistance"
	 * :0.0,"logPic":null,"cAddr":null,"Pinpai":[{"cStoreNo":"10001",
	 * "O_cPinpaiNo" :
	 * "1001","O_cPinpaiName":"卡夫","cPinpaiGrantNo":"101011010","cPinpaiGrantPic"
	 * :"/pinpaigrantpic/1001.jpg"}]}]
	 * [{"cStoreNo":"10001","cStoreName":"乐尚","cCredit"
	 * :"05","cLevel":"3 ","fDistance"
	 * :55610.0,"logPic":null,"cAddr":北京市海淀区上地7街国际创业园5E
	 * ,"Pinpai":[{"cStoreNo":"10001"
	 * ,"O_cPinpaiNo":"1001","O_cPinpaiName":"卡夫","cPinpaiGrantNo"
	 * :"101011010","cPinpaiGrantPic":"/pinpaigrantpic/1001.jpg"}]}]
	 * 
	 * [{"cStoreNo":"10001","cStoreName":"乐尚","cCredit":"05","cLevel":"3 ",
	 * "fDistance"
	 * :55.6,"logPic":null,"cAddr":北京市海淀区上地7街国际创业园5E,"Pinpai":[{"cStoreNo"
	 * :"10001"
	 * ,"O_cPinpaiNo":"1001","O_cPinpaiName":"卡夫","cPinpaiGrantNo":"101011010"
	 * ,"cPinpaiGrantPic":"/pinpaigrantpic/1001.jpg"}]}]
	 * 
	 * [{"cStoreNo":"10001","cStoreName":"乐尚","cCredit":"05","cLevel":"3 ",
	 * "fDistance"
	 * :55.6,"logPic":null,"cAddr":北京市海淀区上地7街国际创业园5E,"Pinpai":[{"cStoreNo"
	 * :"10001"
	 * ,"O_cPinpaiNo":"1001","O_cPinpaiName":"卡夫","cPinpaiGrantNo":"101011010"
	 * ,"cPinpaiGrantPic":"/pinpaigrantpic/1001.jpg"}]}]
	 * 
	 * [{"cStoreNo":"10001","cStoreName":"乐尚超市","cCredit":"05","cLevel":"3 ",
	 * "fDistance"
	 * :55.6,"logPic":"null","cAddr":"北京市海淀区上地7街国际创业园5E","Pinpai":[{"cStoreNo"
	 * :"10001"
	 * ,"O_cPinpaiNo":"1001","O_cPinpaiName":"卡夫","cPinpaiGrantNo":"101011010"
	 * ,"cPinpaiGrantPic":"/pinpaigrantpic/1001.jpg"}]}]
	 */

	public void testStoreList() {
		sp = getContext().getSharedPreferences("baocuntesttmp3",
				getContext().MODE_PRIVATE);
		// sp.edit().putString("sanlei", temp[3]).commit();
		PromptManager.showLogTest("testStoreList", "go");
		// 变量替换
		StoreListParam storeLp = new StoreListParam();
		List<StoreListParam> stList = new ArrayList<StoreListParam>();
		stList.add(storeLp);
		Gson gson = new Gson();
		String jsonStr = gson.toJson(stList);
		String msg = "*AIS|RQ|ST01|00|" + jsonStr + "|AIE#";
		PromptManager.showLogTest("PdAsynTaskdoInBackground-postmesssage", msg);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "post");
		map.put("url", getContext().getString(R.string.url_st01)); // 必须变
		map.put("dataType", "text");
		map.put("data", msg);
		RequestVo vo = new RequestVo(R.string.url_st01, getContext(), map);
		String result = NetUtil.post(vo);
		String[] temp = NetUtil.split(result, "|");
		if (temp == null) {
			PromptManager.showLogTest("enginenet", "net-result-null");
		} else if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
				&& "ST01".equals(temp[2]) && "01".equals(temp[3])) {
			sp.edit().putString("sanlei", temp[4]).commit();
			PromptManager.showLogTest(
					"plAsynTaskdoInBackground-ArrayList<Goods>", temp[4]);
		}
	}

	public void testPD() {
		PromptManager.showLogTest("PLAsynTaskdoInBackground", "PLAsynTask");
		// 变量替换
		String pdno = "330019";
		String msg = "*AIS|RQ|PD01|" + pdno + "|00|AIE#";
		PromptManager.showLogTest("PdAsynTaskdoInBackground-postmesssage", msg);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "post");
		map.put("url", getContext().getString(R.string.url_pd01));
		map.put("dataType", "text");
		map.put("data", msg);
		RequestVo vo = new RequestVo(R.string.url_pl01, getContext(), map);
		String result = NetUtil.post(vo);
		if (result == null) {
			PromptManager.showLogTest("enginenet", "net-result-null");
		} else {
			String[] temp = NetUtil.split(result, "|");
			if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
					&& "PL01".equals(temp[2])) {
				Gson gson = new Gson();
				ArrayList<Goods> goodsList = gson.fromJson(temp[3],
						new TypeToken<List<Goods>>() {
						}.getType());
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>",
						goodsList.toString());
			}
		}
	}

	// 过

	public void testStorePL() {
		PromptManager.showLogTest("PLAsynTaskdoInBackground", "PLAsynTask");
		// 变量替换
		String plno = "10001";
		StorePLParam plp = new StorePLParam();
		ArrayList<StorePLParam> plList = new ArrayList<StorePLParam>();
		plList.add(plp);

		Gson gson = new Gson();
		String json = gson.toJson(plList);
		String msg = "*AIS|RQ|PL01|" + plno + "|" + json + "|AIE#";
		PromptManager.showLogTest("PLAsynTaskdoInBackground-postmesssage", msg);
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
					&& "PL01".equals(temp[2])) {
				PromptManager.setSharedPreferenceEditor(getContext(),
						"testPLOfStore", "temp3", temp[3]);

				ArrayList<Goods> goodsList = gson.fromJson(temp[3],
						new TypeToken<List<Goods>>() {
						}.getType());

			}
		}
	}

	public void testSanlei() {
		sp = getContext().getSharedPreferences("baocuntesttmp3",
				getContext().MODE_PRIVATE);
		// sp.edit().putString("sanlei", temp[3]).commit();
		PromptManager.showLogTest("SanleiAsynTaskdoInBackground",
				"SanleiAsynTask");
		// 变量替换
		String gt3no = "023";
		String msg = "*AIS|RQ|PC03|" + gt3no + "|00|AIE#";
		PromptManager.showLogTest("SanleiAsynTaskdoInBackground-postmesssage",
				msg);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "post");
		map.put("url", getContext().getString(R.string.url_pc03));
		map.put("dataType", "text");
		map.put("data", msg);
		RequestVo vo = new RequestVo(R.string.url_pc03, getContext(), map);

		String result = NetUtil.post(vo);
		if (result == null) {
			PromptManager.showLogTest("enginenet", "net-result-null");
		} else {
			String[] temp = NetUtil.split(result, "|");
			if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
					&& "PC03".equals(temp[2])) {
				// t3
				sp.edit().putString("sanlei", temp[3]).commit();
				Gson gson = new Gson();
				ArrayList<GroupType3> gt3list = gson.fromJson(temp[3],
						new TypeToken<List<GroupType3>>() {
						}.getType());
				PromptManager.showLogTest(
						"SanleiAsynTaskdoInBackground-ArrayList<GroupType3>",
						gt3list.toString() + "/" + temp[3]);
			}
		}
	}

	public void testErlei() {
		sp = getContext().getSharedPreferences("baocuntesttmp3",
				getContext().MODE_PRIVATE);
		// sp.edit().putString("dalei", temp[3]).commit();
		PromptManager.showLogTest("ErleiAsynTaskdoInBackground",
				"ErleiAsynTask");
		String gt2no = "02";
		String msg = "*AIS|RQ|PC02|" + gt2no + "|00|AIE#";
		PromptManager.showLogTest("ErleiAsynTaskdoInBackground-postmesssage",
				msg);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "post");
		map.put("url", getContext().getString(R.string.url_pc02));
		map.put("dataType", "text");
		map.put("data", msg);
		RequestVo vo = new RequestVo(R.string.url_pc02, getContext(), map);
		String result = NetUtil.post(vo);
		if (result == null) {
			PromptManager.showLogTest("enginenet", "net-result-null");
		} else {
			String[] temp = NetUtil.split(result, "|");
			if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
					&& "PC02".equals(temp[2])) {
				// t3
				// sp = getContext().getSharedPreferences("baocuntesttmp3",
				// getContext().MODE_PRIVATE);
				sp.edit().putString("erlei", temp[3]).commit();
				Gson gson = new Gson();
				ArrayList<GroupType2> gt2list = gson.fromJson(temp[3],
						new TypeToken<List<GroupType2>>() {
						}.getType());
				PromptManager.showLogTest(
						"ErleiAsynTaskdoInBackground-ArrayList<GroupType2>",
						gt2list.toString());
			}
		}
	}

	public void testDalei() {
		sp = getContext().getSharedPreferences("baocuntesttmp3",
				getContext().MODE_PRIVATE);
		// sp.edit().putString("dalei", temp[3]).commit();
		PromptManager.showLogTest("DaleiAsynTaskdoInBackground",
				"DaleiAsynTask");
		String gt1no = "00";
		String msg = "*AIS|RQ|PC01|" + gt1no + "|00|AIE#";
		PromptManager.showLogTest("DaleiAsynTaskdoInBackground-postmesssage",
				msg);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "post");
		map.put("url", getContext().getString(R.string.url_pc01));
		map.put("dataType", "text");
		map.put("data", msg);
		RequestVo vo = new RequestVo(R.string.url_pc01, getContext(), map);

		String result = NetUtil.post(vo);
		if (result == null) {
			PromptManager.showLogTest("fanhui-net", "net-result-null");
		} else {
			String[] temp = NetUtil.split(result, "|");
			if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
					&& "PC01".equals(temp[2])) {
				Gson gson = new Gson();
				sp.edit().putString("dalei", temp[3]).commit();
				// 3
				ArrayList<GroupType1> grouptype1list = gson.fromJson(temp[3],
						new TypeToken<List<GroupType1>>() {
						}.getType());
				PromptManager.showLogTest(
						"DaleiAsynTaskdoInBackground-ArrayList<GroupType1>",
						grouptype1list.toString());
			}
		}
	}

	public void testLogin() {
		PromptManager.showLogTest("LoginAsynTaskdoInBackground",
				"LoginAsynTask");
		UserInfo u = new UserInfo();
		u.setUserName("222");
		u.setUserPwd("222");
		List<UserInfo> userlist = new ArrayList<UserInfo>();
		userlist.add(u);
		Gson gson = new Gson();
		String userlistJson = gson.toJson(userlist);
		String msg = "*AIS|RQ|LG01|00|" + userlistJson + "|AIE#";
		PromptManager.showLogTest(
				"testLoginAsynTaskdoInBackground-postmesssage", msg);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "post");
		map.put("url", getContext().getString(R.string.url_login));
		map.put("dataType", "text");
		map.put("data", msg);
		RequestVo vo = new RequestVo(R.string.url_pc01, getContext(), map);

		String result = NetUtil.post(vo);
		if (result == null) {
			PromptManager.showLogTest("enginenet", "net-result-null");
		} else {
			String[] temp = NetUtil.split(result, "|");
			if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
					&& "LG01".equals(temp[2])) {
				PromptManager.showLogTest(
						"DaleiAsynTaskdoInBackground-ArrayList<GroupType1>",
						temp[4]);
				ArrayList<UserInfo> userList = gson.fromJson(temp[4],
						new TypeToken<List<UserInfo>>() {
						}.getType());
				PromptManager.showLogTest(
						"DaleiAsynTaskdoInBackground-ArrayList<GroupType1>",
						userList.toString());
			}
		}
	}

	public void testReg2() {
		sp = getContext().getSharedPreferences("baocuntesttmp3",
				getContext().MODE_PRIVATE);
		PromptManager.showLogTest("RegAsynTaskdoInBackground", "RegAsynTask");
		UserInfo u = new UserInfo();
		u.setUserName("222");
		u.setUserPwd("222");
		List<UserInfo> userlist = new ArrayList<UserInfo>();
		userlist.add(u);
		Gson gson = new Gson();
		String userlistJson = gson.toJson(userlist);
		String msg = "*AIS|RQ|RG01|00|" + userlistJson + "|AIE#";
		PromptManager.showLogTest(
				"testLoginAsynTaskdoInBackground-postmesssage", msg);
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "post");
		map.put("url", getContext().getString(R.string.url_login));
		map.put("dataType", "text");
		map.put("data", msg);
		RequestVo vo = new RequestVo(R.string.url_pc01, getContext(), map);

		String result = NetUtil.post(vo);
		if (result == null) {
			PromptManager.showLogTest("enginenet", "net-result-null");
		} else {
			String[] temp = NetUtil.split(result, "|");
			if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
					&& "RG01".equals(temp[2])) {
				PromptManager.showLogTest(
						"DaleiAsynTaskdoInBackground-ArrayList<GroupType1>",
						temp[4]);
				ArrayList<UserInfo> userList = gson.fromJson(temp[4],
						new TypeToken<List<UserInfo>>() {
						}.getType());
				PromptManager.showLogTest(
						"DaleiAsynTaskdoInBackground-ArrayList<GroupType1>",
						userList.toString());
			}
		}
	}

	public void testGsonStoreSt01() {
		Gson json = new Gson();
		String jsonStr = "[{\"cStoreNo\":\"10001\",\"cStoreName\":\"乐尚超市\",\"cCredit\":\"05\",\"cLevel\":\"3\",\"fDistance\":55.6,\"logPic\":\"null\",\"cAddr\":\"北京市海淀区上地7街国际创业园5E\",\"Pinpai\":[{\"cStoreNo\":\"10001\",\"O_cPinpaiNo\":\"1001\",\"O_cPinpaiName\":\"卡夫\",\"cPinpaiGrantNo\":\"101011010\",\"cPinpaiGrantPic\":\"/pinpaigrantpic/1001.jpg\"}]}]";
		List<StoreItem> stList = json.fromJson(jsonStr,
				new TypeToken<List<StoreItem>>() {
				}.getType());
		PromptManager.showLogTest(stList.toString(), stList.get(0)
				.getcStoreName());
	}

	public void testGsonPC010203() {
		// Gson json = new Gson();
		// String jsonStr =
		// "[{\"cStoreNo\":\"10001\",\"cStoreName\":\"乐尚超市\",\"cCredit\":\"05\",\"cLevel\":\"3\",\"fDistance\":55.6,\"logPic\":\"null\",\"cAddr\":\"北京市海淀区上地7街国际创业园5E\",\"Pinpai\":[{\"cStoreNo\":\"10001\",\"O_cPinpaiNo\":\"1001\",\"O_cPinpaiName\":\"卡夫\",\"cPinpaiGrantNo\":\"101011010\",\"cPinpaiGrantPic\":\"/pinpaigrantpic/1001.jpg\"}]}]";
		// List<StoreItem> stList = json.fromJson(jsonStr,
		// new TypeToken<List<StoreItem>>() {
		// }.getType());
		// PromptManager.showLogTest(stList.toString(), stList.get(0)
		// .getcStoreName());
		PromptManager.setSharedPreferenceEditor(getContext(),
				"testSanleiOfStore", "temp3", "bubu");
		String t3 = PromptManager.getSharedPreferenceEditor(getContext(),
				"testSanleiOfStore", "temp3");
		System.out.println(t3 + "");

	}

	public void testG3() {

	}

}
