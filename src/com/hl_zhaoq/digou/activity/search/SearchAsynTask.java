package com.hl_zhaoq.digou.activity.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.bean.Goods;
import com.hl_zhaoq.digou.bean.SearchBean;
import com.hl_zhaoq.digou.bean.StorePLParam;
import com.hl_zhaoq.digou.constant.ConstantLocation;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;

import android.content.Context;
import android.os.AsyncTask;

public class SearchAsynTask extends AsyncTask<Object, Void, List<Goods>> {
	private Context context;
	private SearchAdapter searchAdapter;

	public SearchAsynTask(Context context, SearchAdapter searchAdapter) {
		super();
		this.context = context;
		this.searchAdapter = searchAdapter;
	}

	@Override
	protected void onPostExecute(List<Goods> result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		PromptManager.closeSpotsDialog();
		if (result!=null) {
			searchAdapter.addGoodsSearchList(result);
		}
		searchAdapter.notifyDataSetChanged();
		SearchActivity.isLoading = false;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		PromptManager.showSpotsDialog(context);
	}

	@Override
	protected List<Goods> doInBackground(Object... arg0) {
		// 1 *AIS|RQ| Search |00|JSON|AIE#
		// String tStoreNo = ConstantValue.grouptype3list_goodno.trim();
		try {
			SearchBean searchParam = (SearchBean) arg0[0];
			List<SearchBean> searchParamList = new ArrayList<SearchBean>();
			searchParamList.add(searchParam);
			Gson gson = new Gson();
			String json = gson.toJson(searchParamList);
			String msg = "*AIS|RQ|Search|00|" + json + "|AIE#";
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("type", "post");
			map.put("url", context.getString(R.string.servlet_search));
			map.put("dataType", "text");
			map.put("data", msg);
			RequestVo vo = new RequestVo(R.string.servlet_search, context, map);
			String result = NetUtil.post(vo);
			// //*AIS|RS|Search|01|[{"cGoodsNo":"330037","cStoreNo":"10001","cStoreName":"乐尚","cCredit":"3 ","cLevel":"1 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":85.0000,"fVipPrice_student":85.0000,"MinIMG0":"/DOGOServer/pic/b330037.jpg"},{"cGoodsNo":"330037","cStoreNo":"10006","cStoreName":"华源百货","cCredit":"3 ","cLevel":"4 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":85.0000,"fVipPrice_student":85.0000,"MinIMG0":"/DOGOServer/pic/b330037.jpg"},{"cGoodsNo":"330037","cStoreNo":"10008","cStoreName":"大众和谐超市","cCredit":"3 ","cLevel":"5 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":85.0000,"fVipPrice_student":85.0000,"MinIMG0":"/DOGOServer/pic/b330037.jpg"},{"cGoodsNo":"330039","cStoreNo":"10001","cStoreName":"乐尚","cCredit":"3 ","cLevel":"1 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":209.0000,"fVipPrice_student":209.0000,"MinIMG0":"/DOGOServer/pic/b330039.jpg"},{"cGoodsNo":"330039","cStoreNo":"10006","cStoreName":"华源百货","cCredit":"3 ","cLevel":"4 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":209.0000,"fVipPrice_student":209.0000,"MinIMG0":"/DOGOServer/pic/b330039.jpg"},{"cGoodsNo":"330039","cStoreNo":"10008","cStoreName":"大众和谐超市","cCredit":"3 ","cLevel":"5 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":209.0000,"fVipPrice_student":209.0000,"MinIMG0":"/DOGOServer/pic/b330039.jpg"},{"cGoodsNo":"330040","cStoreNo":"10001","cStoreName":"乐尚","cCredit":"3 ","cLevel":"1 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":110.0000,"fVipPrice_student":110.0000,"MinIMG0":"/DOGOServer/pic/b330040.jpg"},{"cGoodsNo":"330040","cStoreNo":"10006","cStoreName":"华源百货","cCredit":"3 ","cLevel":"4 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":110.0000,"fVipPrice_student":110.0000,"MinIMG0":"/DOGOServer/pic/b330040.jpg"},{"cGoodsNo":"330040","cStoreNo":"10008","cStoreName":"大众和谐超市","cCredit":"3 ","cLevel":"5 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":110.0000,"fVipPrice_student":110.0000,"MinIMG0":"/DOGOServer/pic/b330040.jpg"}]|AIE#
			// *AIS|RS|Search|01|[{"cGoodsNo":"330037","cStoreNo":"10001","cGoodsName":"GreatValue惠宜盐烤味什锦坚果517g美国进口","cStoreName":"乐尚","cCredit":"3 ","cLevel":"1 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":85.0000,"fVipPrice_student":85.0000,"MinIMG0":"/DOGOServer/pic/b330037.jpg"},{"cGoodsNo":"330037","cStoreNo":"10006","cGoodsName":"GreatValue惠宜盐烤味什锦坚果517g美国进口","cStoreName":"华源百货","cCredit":"3 ","cLevel":"4 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":85.0000,"fVipPrice_student":85.0000,"MinIMG0":"/DOGOServer/pic/b330037.jpg"},{"cGoodsNo":"330037","cStoreNo":"10008","cGoodsName":"GreatValue惠宜盐烤味什锦坚果517g美国进口","cStoreName":"大众和谐超市","cCredit":"3 ","cLevel":"5 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":85.0000,"fVipPrice_student":85.0000,"MinIMG0":"/DOGOServer/pic/b330037.jpg"},{"cGoodsNo":"330039","cStoreNo":"10001","cGoodsName":"三只松鼠 大礼包 坚果零食特产B套餐 坚果礼盒2956g 礼盒装","cStoreName":"乐尚","cCredit":"3 ","cLevel":"1 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":209.0000,"fVipPrice_student":209.0000,"MinIMG0":"/DOGOServer/pic/b330039.jpg"},{"cGoodsNo":"330039","cStoreNo":"10006","cGoodsName":"三只松鼠 大礼包 坚果零食特产B套餐 坚果礼盒2956g 礼盒装","cStoreName":"华源百货","cCredit":"3 ","cLevel":"4 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":209.0000,"fVipPrice_student":209.0000,"MinIMG0":"/DOGOServer/pic/b330039.jpg"},{"cGoodsNo":"330039","cStoreNo":"10008","cGoodsName":"三只松鼠 大礼包 坚果零食特产B套餐 坚果礼盒2956g 礼盒装","cStoreName":"大众和谐超市","cCredit":"3 ","cLevel":"5 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":209.0000,"fVipPrice_student":209.0000,"MinIMG0":"/DOGOServer/pic/b330039.jpg"},{"cGoodsNo":"330040","cStoreNo":"10001","cGoodsName":"百草味 满圆春1328g 坚果礼盒装 零食大礼包食品送礼","cStoreName":"乐尚","cCredit":"3 ","cLevel":"1 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":110.0000,"fVipPrice_student":110.0000,"MinIMG0":"/DOGOServer/pic/b330040.jpg"},{"cGoodsNo":"330040","cStoreNo":"10006","cGoodsName":"百草味 满圆春1328g 坚果礼盒装 零食大礼包食品送礼","cStoreName":"华源百货","cCredit":"3 ","cLevel":"4 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":110.0000,"fVipPrice_student":110.0000,"MinIMG0":"/DOGOServer/pic/b330040.jpg"},{"cGoodsNo":"330040","cStoreNo":"10008","cGoodsName":"百草味 满圆春1328g 坚果礼盒装 零食大礼包食品送礼","cStoreName":"大众和谐超市","cCredit":"3 ","cLevel":"5 ","fDistance":"0.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"030201","cGoodsTypename":"干果礼盒","fVipPrice":110.0000,"fVipPrice_student":110.0000,"MinIMG0":"/DOGOServer/pic/b330040.jpg"}]|AIE#
			// *AIS|RS|Search|01|[{"cGoodsNo":"440091","cStoreNo":"10008","cGoodsName":"茅台 酱香型白酒 飞天茅台 53度 500ml*6瓶(整箱装）","cStoreName":"大众和谐超市","cCredit":"3 ","cLevel":"5 ","fDistance":"1.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"0241","cGoodsTypename":"白酒","fVipPrice":5328.0000,"fVipPrice_student":5328.0000,"MinIMG0":"/DOGOServer/pic/b440091.jpg"},{"cGoodsNo":"440092","cStoreNo":"10008","cGoodsName":"牛栏山 陈酿白酒 42度 500ml*12瓶（整箱装）","cStoreName":"大众和谐超市","cCredit":"3 ","cLevel":"5 ","fDistance":"1.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"0241","cGoodsTypename":"白酒","fVipPrice":156.0000,"fVipPrice_student":156.0000,"MinIMG0":"/DOGOServer/pic/b440092.jpg"},{"cGoodsNo":"440094","cStoreNo":"10008","cGoodsName":"剑南春 浓香型白酒 52度 500ML*6瓶（整箱装）","cStoreName":"大众和谐超市","cCredit":"3 ","cLevel":"5 ","fDistance":"1.0","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"0241","cGoodsTypename":"白酒","fVipPrice":2149.0000,"fVipPrice_student":2149.0000,"MinIMG0":"/DOGOServer/pic/b440094.jpg"},{"cGoodsNo":"440094","cStoreNo":"10001","cGoodsName":"剑南春 浓香型白酒 52度 500ML*6瓶（整箱装）","cStoreName":"乐尚","cCredit":"3 ","cLevel":"1 ","fDistance":"1.5","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"0241","cGoodsTypename":"白酒","fVipPrice":2149.0000,"fVipPrice_student":2149.0000,"MinIMG0":"/DOGOServer/pic/b440094.jpg"},{"cGoodsNo":"440092","cStoreNo":"10001","cGoodsName":"牛栏山 陈酿白酒 42度 500ml*12瓶（整箱装）","cStoreName":"乐尚","cCredit":"3 ","cLevel":"1 ","fDistance":"1.5","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"0241","cGoodsTypename":"白酒","fVipPrice":156.0000,"fVipPrice_student":156.0000,"MinIMG0":"/DOGOServer/pic/b440092.jpg"},{"cGoodsNo":"440091","cStoreNo":"10001","cGoodsName":"茅台 酱香型白酒 飞天茅台 53度 500ml*6瓶(整箱装）","cStoreName":"乐尚","cCredit":"3 ","cLevel":"1 ","fDistance":"1.5","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"0241","cGoodsTypename":"白酒","fVipPrice":5328.0000,"fVipPrice_student":5328.0000,"MinIMG0":"/DOGOServer/pic/b440091.jpg"},{"cGoodsNo":"440091","cStoreNo":"10006","cGoodsName":"茅台 酱香型白酒 飞天茅台 53度 500ml*6瓶(整箱装）","cStoreName":"华源百货","cCredit":"3 ","cLevel":"4 ","fDistance":"139.1","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"0241","cGoodsTypename":"白酒","fVipPrice":5328.0000,"fVipPrice_student":5328.0000,"MinIMG0":"/DOGOServer/pic/b440091.jpg"},{"cGoodsNo":"440092","cStoreNo":"10006","cGoodsName":"牛栏山 陈酿白酒 42度 500ml*12瓶（整箱装）","cStoreName":"华源百货","cCredit":"3 ","cLevel":"4 ","fDistance":"139.1","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"0241","cGoodsTypename":"白酒","fVipPrice":156.0000,"fVipPrice_student":156.0000,"MinIMG0":"/DOGOServer/pic/b440092.jpg"},{"cGoodsNo":"440094","cStoreNo":"10006","cGoodsName":"剑南春 浓香型白酒 52度 500ML*6瓶（整箱装）","cStoreName":"华源百货","cCredit":"3 ","cLevel":"4 ","fDistance":"139.1","cAddr":"北京市海淀区上地五街国际创业园5E","cGoodsTypeno":"0241","cGoodsTypename":"白酒","fVipPrice":2149.0000,"fVipPrice_student":2149.0000,"MinIMG0":"/DOGOServer/pic/b440094.jpg"}]|AIE#

			String[] temp = NetUtil.split(result, "|");
			if (temp[0].equalsIgnoreCase("*AIS") && temp[1].equals("RS")
					&& temp[2].equals("Search") && temp[3].equals("01")) {
				List<Goods> ps = gson.fromJson(temp[4],
						new TypeToken<ArrayList<Goods>>() {
						}.getType());
				if (ps.size() > 0) {
					if (ps.size() < 9) {
						SearchActivity.isEnd = true;
					}
					return ps;
				}
			}else if (temp[3].equals("00")) {
				SearchActivity.isEnd = true;
			}
		} catch (Exception e) {
			SearchActivity.isEnd = true;
			return null;
		}
//		SearchActivity.isEnd = true;
		return null;
	}

}
