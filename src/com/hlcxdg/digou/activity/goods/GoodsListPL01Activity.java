package com.hlcxdg.digou.activity.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.MainActivity;
import com.hlcxdg.digou.bean.Goods;
import com.hlcxdg.digou.bean.StorePLParam;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;

public class GoodsListPL01Activity extends Activity implements OnClickListener,
		OnScrollListener, OnItemClickListener {
	private boolean isLoading = true;
	Context context;
	public ListView pl01_list_goodlist;
	public ImageView iv_back_pl01;
	public ImageView cart_pl01_iv;
	public ImageView totop;
	View footers;
	int page = 0;
	boolean isEnd = false;
	GoodsListPl01Adapter goodsListAdapter;

	public void toTopIndex(View view) {

		pl01_list_goodlist.setSelection(0);
	}
	
	public void tocart(View view) {

		Intent intent;
		intent = new Intent();
		Bundle extras = new Bundle();
		extras.putInt("indexMenu", 23);
		intent.putExtras(extras);
		intent.setClass(context, MainActivity.class);
		startActivity(intent);
		finish();
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
		case R.id.iv_back_pl011:
			GoodsListPL01Activity.this.finish();
			break;
	
		default:
			break;
		}
	}

	public void initView() {
		// cart_pl01_iv = (ImageView) findViewById(R.id.cart_pl01_iv1);
		// cart_pl01_iv.setOnClickListener(this);
		iv_back_pl01 = (ImageView) findViewById(R.id.iv_back_pl011);
		totop = (ImageView) findViewById(R.id.totop);
		iv_back_pl01.setOnClickListener(this);
		pl01_list_goodlist = (ListView) findViewById(R.id.pl01_list_goodlist);
		goodsListAdapter = new GoodsListPl01Adapter(null, this);
		footers = View.inflate(this, R.layout.foot, null);
		pl01_list_goodlist.addFooterView(footers);
		pl01_list_goodlist.setAdapter(goodsListAdapter);
		pl01_list_goodlist.setOnScrollListener(this);
		pl01_list_goodlist.setOnItemClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pl01goodlist);
		context = GoodsListPL01Activity.this;
		initView();
		isLoading = true;
		new GetPL01Asytask().execute(page);

	}

	public class GetPL01Asytask extends
			AsyncTask<Integer, ProgressBar, List<Goods>> {

		@Override
		protected void onPostExecute(List<Goods> result) {
			super.onPostExecute(result);

			if (result != null && result.size() > 0) {
				goodsListAdapter.addGoodsPL01List(result);
				goodsListAdapter.notifyDataSetChanged();
				page++;
				if (result.size() < 9) {
					pl01_list_goodlist.removeFooterView(footers);
				}
			} else {
				pl01_list_goodlist.removeFooterView(footers);
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

			String grno = ConstantValue.grouptype3list_goodno.trim();
			try {
				StorePLParam plp = new StorePLParam();
				plp.setGroupNo3(grno);
				plp.setStoreNo("00");
				plp.setIndexOrder(pages[0] * 9);
				ArrayList<StorePLParam> plList = new ArrayList<StorePLParam>();
				plList.add(plp);
				Gson gson = new Gson();
				String json = gson.toJson(plList);
				String msg = "*AIS|RQ|PL01|00|" + json + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_pl01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_pl01, context, map);
				String result = NetUtil.post(vo);
				// *AIS|RS|PL01|01|[{"cGoodsNo":"440028","cGoodsName":"蒙牛 原麦早餐奶 250ml*2小盒 调制牛奶","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":6.0000,"fVipPrice":6.0000,"fVipPrice_student":6.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440028.jpg"},{"cGoodsNo":"440029","cGoodsName":"蒙牛 核桃早餐奶 250ml*2小盒 早餐丰富营养牛奶","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":6.0000,"fVipPrice":6.0000,"fVipPrice_student":6.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440029.jpg"},{"cGoodsNo":"440030","cGoodsName":"杨协成 黑豆豆奶 1L 马来西亚进口","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":11.0000,"fVipPrice":11.0000,"fVipPrice_student":11.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440030.jpg"},{"cGoodsNo":"440022","cGoodsName":"维他奶 黑豆奶 250ml*6/组","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":17.0000,"fVipPrice":17.0000,"fVipPrice_student":17.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440022.jpg"},{"cGoodsNo":"440023","cGoodsName":"维他奶 巧克力味豆奶饮料 250ml*6/组","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":17.0000,"fVipPrice":17.0000,"fVipPrice_student":17.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440023.jpg"},{"cGoodsNo":"440024","cGoodsName":"维他奶 香草味豆奶饮料 250ml*6/组","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":17.0000,"fVipPrice":17.0000,"fVipPrice_student":17.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440024.jpg"},{"cGoodsNo":"440021","cGoodsName":"维他奶 原味豆奶 250ml*16/箱","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":45.0000,"fVipPrice":45.0000,"fVipPrice_student":45.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440021.jpg"},{"cGoodsNo":"440025","cGoodsName":"维他奶 香草口味 豆奶 250ml×24盒/箱[Nwtn8011]","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":58.0000,"fVipPrice":58.0000,"fVipPrice_student":58.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440025.jpg"},{"cGoodsNo":"440026","cGoodsName":"维他奶 原味豆奶 250ml×24盒/箱[Nwtn8009]","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":58.0000,"fVipPrice":58.0000,"fVipPrice_student":58.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440026.jpg"}]|AIE#

				String[] temp = NetUtil.split(result, "|");
				if (temp[0].equalsIgnoreCase("*AIS") && temp[1].equals("RS")
						&& temp[2].equals("PL01") && temp[3].equals("01")) {
					ArrayList<Goods> ps = gson.fromJson(temp[4],
							new TypeToken<List<Goods>>() {
							}.getType());
					if (ps.size() > 0) {
						if (ps.size() < 9) {
							isEnd = true;
						}
						return ps;
					}
				}
			} catch (Exception e) {

				return null;
			}

			return null;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (firstVisibleItem>1) {
			totop.setVisibility(View.VISIBLE);
		}else {
			totop.setVisibility(View.GONE);
		}
		if (isEnd) {
			pl01_list_goodlist.removeFooterView(footers);
			return;
		} else if (totalItemCount <= firstVisibleItem + visibleItemCount + 1
				&& isLoading == false) {
			isLoading = true;
			new GetPL01Asytask().execute(page);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {

		if (view.getId() != R.id.foot_view) {
			ConstantValue.goodsinfo = goodsListAdapter.getGoodsPL01List().get(
					position);
			Intent i = new Intent();
			i.setClass(getApplicationContext(), GoodsListPL02Activity.class);
			startActivity(i);
		}
	}

}
