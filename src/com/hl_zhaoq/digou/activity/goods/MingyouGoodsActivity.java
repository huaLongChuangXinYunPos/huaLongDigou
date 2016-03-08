package com.hl_zhaoq.digou.activity.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.MainActivity;
import com.hl_zhaoq.digou.bean.Goods;
import com.hl_zhaoq.digou.bean.PL02ParamGoodsList;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class MingyouGoodsActivity extends Activity implements OnScrollListener,
		OnItemClickListener {
	private boolean isLoading = true;
	private MingyouGoodsAdapter fsAdapter;
	private Context context;
	private ListView ming_list_goodlist;
	//
	private View footers;
	private int page = 0;
	private boolean isEnd = false;

	public void tocart(View view) {

		Intent intent;
		intent = new Intent();
		Bundle extras = new Bundle();
		extras.putInt("indexMenu", 23);
		// intent.putIntegerArrayListExtra(name, value)Extra("indexMenu", 1);
		intent.putExtras(extras);

		intent.setClass(context, MainActivity.class);
		startActivity(intent);
		finish();
	}

	public void goback(View v) {
		MingyouGoodsActivity.this.finish();
	}

	public void initView() {
		ming_list_goodlist = (ListView) findViewById(R.id.ming_list_goodlist1);
		ming_list_goodlist.setOnItemClickListener(this);
		ming_list_goodlist.setOnScrollListener(this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return super.onTouchEvent(event);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.mingyougoodlist);
		context = MingyouGoodsActivity.this;
		initView();
		footers = View.inflate(this, R.layout.foot, null);
		ming_list_goodlist.addFooterView(footers);
		fsAdapter = new MingyouGoodsAdapter(null, context);
		ming_list_goodlist.setAdapter(fsAdapter);
		isLoading = true;

		new GetFsListTask().execute(0);

	}

	public class GetFsListTask extends
			AsyncTask<Integer, ProgressBar, List<Goods>> {
		@Override
		protected void onPostExecute(List<Goods> result) {
			super.onPostExecute(result);

			if (result != null && result.size() > 0) {
				fsAdapter.addGoodsFsList(result);
				fsAdapter.notifyDataSetChanged();
				page++;
				if (isEnd) {
					ming_list_goodlist.removeFooterView(footers);
				}
			} else {
				ming_list_goodlist.removeFooterView(footers);
				PromptManager.showMyToast(context, "商品下架了");
			}
			isLoading = false;

			PromptManager.closeSpotsDialog();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}

		@Override
		protected void onProgressUpdate(ProgressBar... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected List<Goods> doInBackground(Integer[] str) {

			try {
				PL02ParamGoodsList pl02p;
				int indexNo = str[0];
				pl02p = new PL02ParamGoodsList();
				pl02p.setIndexOrder(indexNo);
				ArrayList<PL02ParamGoodsList> plList = new ArrayList<PL02ParamGoodsList>();
				plList.add(pl02p);
				Gson gson = new Gson();
				String json = gson.toJson(plList);
				String msg = "*AIS|RQ|FS01|01|" + json + "|AIE#";

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url",
						context.getString(R.string.servlet_famousspecialty));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_famousspecialty,
						context, map);// *AIS|RS|FS01|01|[{"cGoodsNo":"110001","cGoodsName":"汰渍净白洗衣粉","cGoodsTypeno":"0511","cGoodsTypename":"洗衣粉类","fNormalPrice":5.0000,"fVipPrice":4.0000,"fVipPrice_student":4.0000,"MinIMG0":"/DOGOServer/pic/b110001.jpg","O_Famousspecialty":"01"},{"cGoodsNo":"110001","cGoodsName":"汰渍净白洗衣粉","cGoodsTypeno":"0511","cGoodsTypename":"洗衣粉类","fNormalPrice":5.0000,"fVipPrice":4.0000,"fVipPrice_student":4.0000,"MinIMG0":"/DOGOServer/pic/b110001.jpg","O_Famousspecialty":"01"},{"cGoodsNo":"110001","cGoodsName":"汰渍净白洗衣粉","cGoodsTypeno":"0511","cGoodsTypename":"洗衣粉类","fNormalPrice":5.0000,"fVipPrice":4.0000,"fVipPrice_student":4.0000,"MinIMG0":"/DOGOServer/pic/b110001.jpg","O_Famousspecialty":"01"},{"cGoodsNo":"110001","cGoodsName":"汰渍净白洗衣粉","cGoodsTypeno":"0511","cGoodsTypename":"洗衣粉类","fNormalPrice":5.0000,"fVipPrice":4.0000,"fVipPrice_student":4.0000,"MinIMG0":"/DOGOServer/pic/b110001.jpg","O_Famousspecialty":"01"},{"cGoodsNo":"110002","cGoodsName":"洁84洗衣粉","cGoodsTypeno":"0511","cGoodsTypename":"洗衣粉类","fNormalPrice":6.0000,"fVipPrice":5.0000,"fVipPrice_student":5.0000,"MinIMG0":"/DOGOServer/pic/b110002.jpg","O_Famousspecialty":"01"},{"cGoodsNo":"110002","cGoodsName":"洁84洗衣粉","cGoodsTypeno":"0511","cGoodsTypename":"洗衣粉类","fNormalPrice":6.0000,"fVipPrice":5.0000,"fVipPrice_student":5.0000,"MinIMG0":"/DOGOServer/pic/b110002.jpg","O_Famousspecialty":"01"},{"cGoodsNo":"110002","cGoodsName":"洁84洗衣粉","cGoodsTypeno":"0511","cGoodsTypename":"洗衣粉类","fNormalPrice":6.0000,"fVipPrice":5.0000,"fVipPrice_student":5.0000,"MinIMG0":"/DOGOServer/pic/b110002.jpg","O_Famousspecialty":"01"}]|AIE#

				String result = NetUtil.post(vo);
				// *AIS|RS|FS01|01|[{"cStoreNo":"10001","cStoreName":"乐尚","cGoodsNo":"440061",
				// "cGoodsName":"纯典 100%番茄胡萝卜汁 250ml/盒 混合果蔬汁 无添加 100%纯果蔬汁","cGoodsTypeno":"0231",
				// "cGoodsTypename":"果汁","fNormalPrice":2.0000,"fVipPrice":2.0000,"fVipPrice_student":2.0000,
				// "O_PriceFlag":" ","O_bNew":"1","O_Famousspecialty":"01","MinIMG0":"/DOGOServer/pic/b440061.jpg"},

				String[] temp = NetUtil.split(result, "|");
				if (temp[0].equalsIgnoreCase("*AIS") && temp[1].equals("RS")
						&& temp[2].equals("FS01") && temp[3].equals("01")) {
					List<Goods> storegoodDatatmp = null;
					storegoodDatatmp = gson.fromJson(temp[4],
							new TypeToken<List<Goods>>() {
							}.getType());

					if (null != storegoodDatatmp && storegoodDatatmp.size() > 0) {
						if (storegoodDatatmp.size() < 9) {
							isEnd = true;
						}
						return storegoodDatatmp;
					}
					return storegoodDatatmp;
				}
				return null;
			} catch (Exception e) {

				return null;
			}
		}

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {
		ConstantValue.goodsinfo = fsAdapter.getGoodsFsList().get(position);
		Intent i = new Intent();
		i.setClass(context, GoodSingleActivity.class);
		startActivity(i);
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (isEnd) {
			ming_list_goodlist.removeFooterView(footers);
			return;
		} else if (totalItemCount <= firstVisibleItem + visibleItemCount + 1
				&& isLoading == false) {
			isLoading = true;
			new GetFsListTask().execute(page);
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {

	}

}
