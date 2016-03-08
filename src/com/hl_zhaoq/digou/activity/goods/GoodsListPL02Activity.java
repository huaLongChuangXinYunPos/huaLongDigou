package com.hl_zhaoq.digou.activity.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.MainActivity;
import com.hl_zhaoq.digou.activity.qiagndan.UserMainqiangdanActivity;
import com.hl_zhaoq.digou.bean.Goods;
import com.hl_zhaoq.digou.bean.PL02ParamGoodsList;
import com.hl_zhaoq.digou.constant.ConstantIP;
import com.hl_zhaoq.digou.constant.ConstantLocation;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.fragment.CartActivity;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class GoodsListPL02Activity extends Activity implements OnClickListener,
		OnScrollListener, OnItemClickListener {
	private boolean isLoading = true;
	Context context;
	View footers;
	int page = 0;
	boolean isEnd = false;

	public ListView pl02_list_goodlist;
	public ImageView iv_back_pl02;
	public ImageView cart_pl02_iv;

	GoodsListPl02Adapter goodsListPL02Adapter;

	// tomainpage
	// public void tomainpage(View view) {
	// Intent intent;
	// intent = new Intent();
	// intent.setClass(context, MainActivity.class);
	// startActivity(intent);
	// finish();
	// }
	public void toTopIndex(View view) {
		pl02_list_goodlist.setSelection(0);
		// pl01_list_goodlist.sc
	}

	public void tomainpage(View view) {

		Intent intent;
		intent = new Intent();
		Bundle extras = new Bundle();
		extras.putInt("indexMenu", 21);
		// intent.putIntegerArrayListExtra(name, value)Extra("indexMenu", 1);
		intent.putExtras(extras);
		intent.setClass(context, MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		// 回退按钮
		case R.id.iv_back_pl02:
			GoodsListPL02Activity.this.finish();
			break;
		case R.id.cart_icon_pl02_iv:
			Intent intent;
			intent = new Intent();
			Bundle extras = new Bundle();
			extras.putInt("indexMenu", 23);
			intent.putExtras(extras);
			intent.setClass(context, MainActivity.class);
			startActivity(intent);
			finish();
			break;

		default:
			break;
		}
	}

	public void initView() {
		cart_pl02_iv = (ImageView) findViewById(R.id.cart_icon_pl02_iv);
		cart_pl02_iv.setOnClickListener(this);
		iv_back_pl02 = (ImageView) findViewById(R.id.iv_back_pl02);
		iv_back_pl02.setOnClickListener(this);
		pl02_list_goodlist = (ListView) findViewById(R.id.pl02_list_goodlist);

		goodsListPL02Adapter = new GoodsListPl02Adapter(null, this);
		footers = View.inflate(this, R.layout.foot, null);
		pl02_list_goodlist.addFooterView(footers);
		pl02_list_goodlist.setAdapter(goodsListPL02Adapter);
		pl02_list_goodlist.setOnScrollListener(this);
		pl02_list_goodlist.setOnItemClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pl02goodlist);
		context = GoodsListPL02Activity.this;
		initView();
		isLoading = true;
		new PL02GoodsListAsny().execute(page);

	}

	public class PL02GoodsListAsny extends
			AsyncTask<Integer, ProgressBar, List<Goods>> {

		@Override
		protected void onPostExecute(List<Goods> result) {
			super.onPostExecute(result);

			if (result != null && result.size() > 0) {
				goodsListPL02Adapter.addGoodsPL01List(result);
				goodsListPL02Adapter.notifyDataSetChanged();
				page++;
				if (isEnd) {
					pl02_list_goodlist.removeFooterView(footers);
				}
			} else {
				pl02_list_goodlist.removeFooterView(footers);
				PromptManager.showMyToast(context, "商品下架了");
			}
			isLoading = false;

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(ProgressBar... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected List<Goods> doInBackground(Integer... pages) {
			try {
				PL02ParamGoodsList pl02p;
				pl02p = new PL02ParamGoodsList();
				String goodsNo = ConstantValue.goodsinfo.getcGoodsNo();
				ArrayList<PL02ParamGoodsList> plList = new ArrayList<PL02ParamGoodsList>();
				pl02p.setcGoodsNo(goodsNo);
				pl02p.Latitude = (float) ConstantLocation.myLatitudeString;

				pl02p.Longitude = (float) ConstantLocation.myLongitudeString;
				pl02p.setIndexOrder(pages[0] * 9);
				plList.add(pl02p);
				Gson gson = new Gson();
				String json = gson.toJson(plList);
				String msg = "*AIS|RQ|PL02|" + goodsNo + "|" + json + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_pl01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_pl01, context, map);
				String result = NetUtil.post(vo);
				// *AIS|RS|FS01|01|[{"cStoreNo":"10001","cStoreName":"乐尚","cGoodsNo":"440061","cGoodsName":"纯典 100%番茄胡萝卜汁 250ml/盒 混合果蔬汁 无添加 100%纯果蔬汁","cGoodsTypeno":"0231","cGoodsTypename":"果汁","fNormalPrice":2.0000,"fVipPrice":2.0000,"fVipPrice_student":2.0000,"O_PriceFlag":" ","O_bNew":"1","MinIMG0":"/DOGOServer/pic/b440061.jpg"},{"cStoreNo":"10006","cStoreName":"华源百货","cGoodsNo":"440061","cGoodsName":"纯典 100%番茄胡萝卜汁 250ml/盒 混合果蔬汁 无添加 100%纯果蔬汁","cGoodsTypeno":"0231","cGoodsTypename":"果汁","fNormalPrice":2.0000,"fVipPrice":2.0000,"fVipPrice_student":2.0000,"O_PriceFlag":" ","O_bNew":"1","MinIMG0":"/DOGOServer/pic/b440061.jpg"},{"cStoreNo":"10008","cStoreName":"大众和谐超市","cGoodsNo":"440061","cGoodsName":"纯典 100%番茄胡萝卜汁 250ml/盒 混合果蔬汁 无添加 100%纯果蔬汁","cGoodsTypeno":"0231","cGoodsTypename":"果汁","fNormalPrice":2.0000,"fVipPrice":2.0000,"fVipPrice_student":2.0000,"O_PriceFlag":" ","O_bNew":"1","MinIMG0":"/DOGOServer/pic/b440061.jpg"},{"cStoreNo":"10013","cStoreName":"大众和谐超市","cGoodsNo":"440396","cGoodsName":"学生算术棒","cGoodsTypeno":"080101","cGoodsTypename":"文体办公","fNormalPrice":2.0000,"fVipPrice":2.0000,"fVipPrice_student":2.0000,"O_PriceFlag":" ","O_bNew":"1"},{"cStoreNo":"10018","cStoreName":"开口笑形象店","cGoodsNo":"440430","cGoodsName":"浪木活动铅笔芯","cGoodsTypeno":"080101","cGoodsTypename":"文体办公","fNormalPrice":2.0000,"fVipPrice":2.0000,"fVipPrice_student":2.0000,"O_PriceFlag":" ","O_bNew":"1"},{"cStoreNo":"10001","cStoreName":"乐尚","cGoodsNo":"330002","cGoodsName":"金帝黑巧克力14g","cGoodsTypeno":"030101","cGoodsTypename":"巧克力","fNormalPrice":2.2000,"fVipPrice":2.2000,"fVipPrice_student":2.2000,"O_bNew":"1","MinIMG0":"/DOGOServer/pic/b330002.jpg"},{"cStoreNo":"10006","cStoreName":"华源百货","cGoodsNo":"330002","cGoodsName":"金帝黑巧克力14g","cGoodsTypeno":"030101","cGoodsTypename":"巧克力","fNormalPrice":2.2000,"fVipPrice":2.2000,"fVipPrice_student":2.2000,"O_bNew":"1","MinIMG0":"/DOGOServer/pic/b330002.jpg"},{"cStoreNo":"10008","cStoreName":"大众和谐超市","cGoodsNo":"330002","cGoodsName":"金帝黑巧克力14g","cGoodsTypeno":"030101","cGoodsTypename":"巧克力","fNormalPrice":2.2000,"fVipPrice":2.2000,"fVipPrice_student":2.2000,"O_bNew":"1","MinIMG0":"/DOGOServer/pic/b330002.jpg"},{"cStoreNo":"10001","cStoreName":"乐尚","cGoodsNo":"330003","cGoodsName":"金帝牛奶巧克力14g","cGoodsTypeno":"030101","cGoodsTypename":"巧克力","fNormalPrice":2.2000,"fVipPrice":2.2000,"fVipPrice_student":2.2000,"O_bNew":"1","MinIMG0":"/DOGOServer/pic/b330003.jpg"}]|AIE#
				// *AIS|RS|PL02|01|[{"cStoreNo":"10001","cStoreName":"乐尚","cGoodsNo":"440022","cGoodsName":"维他奶 黑豆奶 250ml*6/组","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":17.0000,"fVipPrice":17.0000,"fVipPrice_student":17.0000,"O_PriceFlag":" ","O_bNew":"1","MinIMG0":"/DOGOServer/pic/b440022.jpg"},{"cStoreNo":"10006","cStoreName":"华源百货","cGoodsNo":"440022","cGoodsName":"维他奶 黑豆奶 250ml*6/组","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":17.0000,"fVipPrice":17.0000,"fVipPrice_student":17.0000,"O_PriceFlag":" ","O_bNew":"1","MinIMG0":"/DOGOServer/pic/b440022.jpg"},{"cStoreNo":"10008","cStoreName":"大众和谐超市","cGoodsNo":"440022","cGoodsName":"维他奶 黑豆奶 250ml*6/组","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":17.0000,"fVipPrice":17.0000,"fVipPrice_student":17.0000,"O_PriceFlag":" ","O_bNew":"1","MinIMG0":"/DOGOServer/pic/b440022.jpg"}]|AIE#
				// *AIS|RS|PL02|01|[{"cStoreNo":"10001","cStoreName":"乐尚","cGoodsNo":"440022","cGoodsName":"维他奶 黑豆奶 250ml*6/组","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":17.0000,"fVipPrice":17.0000,"fVipPrice_student":17.0000,"O_PriceFlag":" ","O_bNew":"0","MinIMG0":"/DOGOServer/pic/b440022.jpg","O_distence":1.5},{"cStoreNo":"10006","cStoreName":"华源百货","cGoodsNo":"440022","cGoodsName":"维他奶 黑豆奶 250ml*6/组","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":17.0000,"fVipPrice":17.0000,"fVipPrice_student":17.0000,"O_PriceFlag":" ","O_bNew":"0","MinIMG0":"/DOGOServer/pic/b440022.jpg","O_distence":139.1},{"cStoreNo":"10008","cStoreName":"大众和谐超市","cGoodsNo":"440022","cGoodsName":"维他奶 黑豆奶 250ml*6/组","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":17.0000,"fVipPrice":17.0000,"fVipPrice_student":17.0000,"O_PriceFlag":" ","O_bNew":"0","MinIMG0":"/DOGOServer/pic/b440022.jpg","O_distence":1.0}]|AIE#

				String[] temp = NetUtil.split(result, "|");
				if (temp[0].equalsIgnoreCase("*AIS") && temp[1].equals("RS")
						&& temp[2].equals("PL02") && temp[3].equals("01")) {
					// *AIS|RS|PL02|[{"cStoreNo":"10001","cStoreName":"乐尚","cGoodsNo":"440027","cGoodsName":"维他奶 巧克力味 豆奶 250ml×24盒/箱[Nwtn8010]","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":58.0000,"fVipPrice":58.0000,"fVipPrice_student":58.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440027.jpg"},{"cStoreNo":"10002","cStoreName":"华隆","cGoodsNo":"440027","cGoodsName":"维他奶 巧克力味 豆奶 250ml×24盒/箱[Nwtn8010]","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":58.0000,"fVipPrice":58.0000,"fVipPrice_student":58.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440027.jpg"},{"cStoreNo":"10003","cStoreName":"新泉养生堂","cGoodsNo":"440027","cGoodsName":"维他奶 巧克力味 豆奶 250ml×24盒/箱[Nwtn8010]","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":58.0000,"fVipPrice":58.0000,"fVipPrice_student":58.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440027.jpg"},{"cStoreNo":"10004","cStoreName":"吴姐超市","cGoodsNo":"440027","cGoodsName":"维他奶 巧克力味 豆奶 250ml×24盒/箱[Nwtn8010]","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":58.0000,"fVipPrice":58.0000,"fVipPrice_student":58.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440027.jpg"},{"cStoreNo":"10005","cStoreName":"利客来超市","cGoodsNo":"440027","cGoodsName":"维他奶 巧克力味 豆奶 250ml×24盒/箱[Nwtn8010]","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":58.0000,"fVipPrice":58.0000,"fVipPrice_student":58.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440027.jpg"},{"cStoreNo":"10006","cStoreName":"华源百货","cGoodsNo":"440027","cGoodsName":"维他奶 巧克力味 豆奶 250ml×24盒/箱[Nwtn8010]","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":58.0000,"fVipPrice":58.0000,"fVipPrice_student":58.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440027.jpg"},{"cStoreNo":"10007","cStoreName":"书香雅超市","cGoodsNo":"440027","cGoodsName":"维他奶 巧克力味 豆奶 250ml×24盒/箱[Nwtn8010]","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":58.0000,"fVipPrice":58.0000,"fVipPrice_student":58.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440027.jpg"},{"cStoreNo":"10008","cStoreName":"大众和谐超市","cGoodsNo":"440027","cGoodsName":"维他奶 巧克力味 豆奶 250ml×24盒/箱[Nwtn8010]","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":58.0000,"fVipPrice":58.0000,"fVipPrice_student":58.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440027.jpg"},{"cStoreNo":"10009","cStoreName":"秀琦超市","cGoodsNo":"440027","cGoodsName":"维他奶 巧克力味 豆奶 250ml×24盒/箱[Nwtn8010]","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":58.0000,"fVipPrice":58.0000,"fVipPrice_student":58.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440027.jpg"}]|AIE#
					ArrayList<Goods> storegoodDatatmp = gson.fromJson(temp[4],
							new TypeToken<List<Goods>>() {
							}.getType());
					if (null != storegoodDatatmp && storegoodDatatmp.size() > 0) {
						if (storegoodDatatmp.size() < 9) {
							isEnd = true;
						}
						return storegoodDatatmp;
					}

				}
			} catch (Exception e) {
				return null;
			}
			return null;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		ConstantValue.goodsinfo = goodsListPL02Adapter.getGoodsPL01List().get(
				position);
		Intent i = new Intent();
		i.setClass(getApplicationContext(), GoodSingleActivity.class);
		startActivity(i);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (isEnd) {
			pl02_list_goodlist.removeFooterView(footers);
			return;
		} else if (totalItemCount <= firstVisibleItem + visibleItemCount + 1
				&& isLoading == false) {
			isLoading = true;
			new PL02GoodsListAsny().execute(page);
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {

	}

}
