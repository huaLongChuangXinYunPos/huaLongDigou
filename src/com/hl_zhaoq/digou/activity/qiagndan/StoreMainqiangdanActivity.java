package com.hl_zhaoq.digou.activity.qiagndan;

import com.hl_zhaoq.digou.bean.StoreInfoqdBean;
import com.hl_zhaoq.digou.bean.UserNeedsBean;
import com.hl_zhaoq.digou.constant.ConstantLocation;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class StoreMainqiangdanActivity extends Activity implements
		OnClickListener {
	// 商品库选择商品-用户发布
	private Context context;
	private Intent myIntent;
	private ImageView qiangdan_store_main_back_iv;
	private LinearLayout qiangdan_store_main_tejia_ll;
	private LinearLayout qiangdan_store_main_chadd_ll;
	private LinearLayout qiangdan_store_main_wobaojia_ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qiangdan_store_main_dd);
		context = StoreMainqiangdanActivity.this;
		init();
	}

	private void init() {

		qiangdan_store_main_back_iv = (ImageView) findViewById(R.id.qiangdan_store_main_back_iv);
		qiangdan_store_main_back_iv.setOnClickListener(this);
		qiangdan_store_main_tejia_ll = (LinearLayout) findViewById(R.id.qiangdan_store_main_tejia_ll);
		qiangdan_store_main_tejia_ll.setOnClickListener(this);
		qiangdan_store_main_chadd_ll = (LinearLayout) findViewById(R.id.qiangdan_store_main_chadd_ll);
		qiangdan_store_main_chadd_ll.setOnClickListener(this);
		qiangdan_store_main_wobaojia_ll = (LinearLayout) findViewById(R.id.qiangdan_store_main_wobaojia_ll);
		qiangdan_store_main_wobaojia_ll.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		// 页面返回上一级
		case R.id.qiangdan_store_main_back_iv:
			finish();
			break;
		case R.id.qiangdan_store_main_tejia_ll:
			// 特价海报
			toTeJiaHaiBao();
			break;
		case R.id.qiangdan_store_main_chadd_ll:
			toChaDingDan();
			// 查订单
			break;
		case R.id.qiangdan_store_main_wobaojia_ll:
			// 我报价
			toBaoJia();
			break;

		default:
			break;
		}
	}

	private void toChaDingDan() {
		myIntent = new Intent(context, StorechadingdanqiangdanActivity.class);
		startActivity(myIntent);
	}

	private void toBaoJia() {
		myIntent = new Intent(context, StoreWoBaoJiaMap.class);
		startActivity(myIntent);
	}

	// 特价海报
	private void toTeJiaHaiBao() {
		myIntent = new Intent(context, StoreTeJiaHaiBaoqiangdanActivity.class);
		startActivity(myIntent);
	}

	//
}
