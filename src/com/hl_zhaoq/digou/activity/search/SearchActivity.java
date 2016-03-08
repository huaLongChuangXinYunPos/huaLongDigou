package com.hl_zhaoq.digou.activity.search;

import com.hl_zhaoq.digou.activity.MainActivity;
import com.hl_zhaoq.digou.activity.goods.GoodSingleActivity;
import com.hl_zhaoq.digou.activity.goods.GoodsListPL02Activity;
import com.hl_zhaoq.digou.bean.SearchBean;
import com.hl_zhaoq.digou.constant.ConstantLocation;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.fragment.CartActivity;
import com.hl_zhaoq.digou.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SearchActivity extends Activity implements OnClickListener,
		OnScrollListener, OnItemClickListener {
	public static boolean isLoading = true;
	Context context;
	String searchKey;
	String cStoreNo;
	public ListView pl01_list_goodlist;
	public ImageView iv_back_pl01;
	public ImageView cart_pl01_iv;
	View footers;
	int indexpage = 0;
	public static boolean isEnd = false;
	SearchAdapter goodsSearchAdapter;
	SearchBean searchParam;

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
		Bundle extras=new Bundle();
		extras.putInt("indexMenu", 21);
//		intent.putIntegerArrayListExtra(name, value)Extra("indexMenu", 1);
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
			SearchActivity.this.finish();
			break;
		case R.id.cart_pl02_iv:
			Intent intent = new Intent();
			intent.setClass(context, CartActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	public void initView() {
		cart_pl01_iv = (ImageView) findViewById(R.id.cart_pl02_iv);
		cart_pl01_iv.setOnClickListener(this);
		iv_back_pl01 = (ImageView) findViewById(R.id.iv_back_pl02);
		iv_back_pl01.setOnClickListener(this);
		pl01_list_goodlist = (ListView) findViewById(R.id.pl02_list_goodlist);
		goodsSearchAdapter = new SearchAdapter(null, this);
		footers = View.inflate(this, R.layout.foot, null);
		pl01_list_goodlist.addFooterView(footers);
		pl01_list_goodlist.setAdapter(goodsSearchAdapter);
		pl01_list_goodlist.setOnScrollListener(this);
		pl01_list_goodlist.setOnItemClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pl02goodlist);
		context = SearchActivity.this;
		Bundle bundle = getIntent().getExtras();
		searchKey = bundle.getString("searchKey");
		cStoreNo = bundle.getString("cStoreNo");

		searchParam = new SearchBean();
		if (!TextUtils.isEmpty(cStoreNo)) {
			searchParam.setStoreNo(cStoreNo);
		}
		searchParam.setLongitude((float) (ConstantLocation.myLongitudeString));
		searchParam.setLatitude((float) (ConstantLocation.myLatitudeString));
		searchParam.setName(searchKey); // ------------
		searchParam.setIndexNo(indexpage);
		isLoading = true;
		initView();
		new SearchAsynTask(context, goodsSearchAdapter).execute(searchParam);

		isLoading = true;
		// new GetPL01Asytask().execute(page);

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (isEnd) {
			pl01_list_goodlist.removeFooterView(footers);
			return;
		} else if (totalItemCount <= firstVisibleItem + visibleItemCount + 1
				&& isLoading == false) {
			isLoading = true;
			searchParam.IndexNo += 9;
			new SearchAsynTask(context, goodsSearchAdapter)
					.execute(searchParam);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {

		if (view.getId() != R.id.foot_view) {
			ConstantValue.goodsinfo = goodsSearchAdapter.getGoodsSearchList()
					.get(position);
			Intent i = new Intent();
			i.setClass(getApplicationContext(), GoodSingleActivity.class);
			startActivity(i);
		}
	}

}
