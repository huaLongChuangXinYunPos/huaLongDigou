package com.hl_zhaoq.digou.activity.qiagndan;

import java.math.BigDecimal;

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

public class UserMainqiangdanActivity extends Activity implements
		OnClickListener {
	// 商品库选择商品-用户发布
	private Context context;
	private Intent myIntent;
	private ImageView qiangdan_user_main_back_iv;
	private LinearLayout qiangdan_user_main_woxiangyao_ll;
	private LinearLayout qiangdan_user_main_chabaojia_ll;
	private LinearLayout qiangdan_user_main_zainaer_ll;
	private LinearLayout qiangdan_user_main_tejia_ll;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qiangdan_user_main_dd);
		context = UserMainqiangdanActivity.this;
		init();
		ConstantValue.goodsinfo=null;
		if (ConstantValue.storeqdList==null||ConstantValue.storeqdList.size()==0) {
			jiadata();
		}

	}

	private void jiadata() {
		
		ConstantValue.storeqdList.clear();
		StoreInfoqdBean qdStoreInfo1 = new StoreInfoqdBean();
		qdStoreInfo1.setJingdu(ConstantLocation.myLongitudeString - 0.0131);
		qdStoreInfo1.setWeidu(ConstantLocation.myLatitudeString + 0.0132);
		qdStoreInfo1.setStorenameqd("百货1号");
		qdStoreInfo1.setStoreaddrqd(ConstantLocation.myDistrictString);
		qdStoreInfo1.setStoreqdGoodsname("洗衣液");
		qdStoreInfo1.setStoreqdprice(new BigDecimal(20).setScale(2, BigDecimal.ROUND_UP));
		qdStoreInfo1.setStoreqdnum(1);
		ConstantValue.storeqdList.add(qdStoreInfo1);
		StoreInfoqdBean qdStoreInfo2 = new StoreInfoqdBean();
		qdStoreInfo2.setJingdu(ConstantLocation.myLongitudeString - 0.0135);
		qdStoreInfo2.setWeidu(ConstantLocation.myLatitudeString - 0.0135);
		qdStoreInfo2.setStorenameqd("超市");
		qdStoreInfo2.setStoreaddrqd(ConstantLocation.myDistrictString);
		qdStoreInfo2.setStoreqdGoodsname("洗发水");
		qdStoreInfo1.setStoreqdprice(new BigDecimal(54).setScale(2, BigDecimal.ROUND_UP));
		qdStoreInfo1.setStoreqdnum(1);
		ConstantValue.storeqdList.add(qdStoreInfo2);
		StoreInfoqdBean qdStoreInfo3 = new StoreInfoqdBean();
		qdStoreInfo3.setJingdu(ConstantLocation.myLongitudeString + 0.013);
		qdStoreInfo3.setWeidu(ConstantLocation.myLatitudeString - 0.013);
		qdStoreInfo3.setStorenameqd("烟酒店");
		qdStoreInfo3.setStoreaddrqd(ConstantLocation.myDistrictString);
		qdStoreInfo3.setStoreqdGoodsname("中华香烟");
		qdStoreInfo1.setStoreqdprice(new BigDecimal(150).setScale(2, BigDecimal.ROUND_UP));
		qdStoreInfo1.setStoreqdnum(1);
		ConstantValue.storeqdList.add(qdStoreInfo3);

		StoreInfoqdBean qdStoreInfo4 = new StoreInfoqdBean();
		qdStoreInfo4.setJingdu(ConstantLocation.myLongitudeString + 0.015);
		qdStoreInfo4.setWeidu(ConstantLocation.myLatitudeString + 0.016);
		qdStoreInfo4.setStorenameqd("烟酒店");
		qdStoreInfo4.setStoreaddrqd(ConstantLocation.myDistrictString);
		qdStoreInfo4.setStoreqdGoodsname("茅台");
		qdStoreInfo1.setStoreqdprice(new BigDecimal(2095).setScale(2, BigDecimal.ROUND_UP));
		qdStoreInfo1.setStoreqdnum(1);
		ConstantValue.storeqdList.add(qdStoreInfo4);
	}

	private void init() {

		qiangdan_user_main_back_iv = (ImageView) findViewById(R.id.qiangdan_user_main_back_iv);
		qiangdan_user_main_back_iv.setOnClickListener(this);
		qiangdan_user_main_woxiangyao_ll = (LinearLayout) findViewById(R.id.qiangdan_user_main_woxiangyao_ll);
		qiangdan_user_main_chabaojia_ll = (LinearLayout) findViewById(R.id.qiangdan_user_main_chabaojia_ll);
		qiangdan_user_main_zainaer_ll = (LinearLayout) findViewById(R.id.qiangdan_user_main_zainaer_ll);
		qiangdan_user_main_tejia_ll = (LinearLayout) findViewById(R.id.qiangdan_user_main_tejia_ll);
		qiangdan_user_main_woxiangyao_ll.setOnClickListener(this);
		qiangdan_user_main_tejia_ll.setOnClickListener(this);
		qiangdan_user_main_chabaojia_ll.setOnClickListener(this);
		qiangdan_user_main_zainaer_ll.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.qiangdan_user_main_back_iv:
			finish();
			break;
		case R.id.qiangdan_user_main_woxiangyao_ll:
			// 特价海报
			toWoXiangYao();
			break;
		case R.id.qiangdan_user_main_chabaojia_ll:
			// 查报价
			toChaBaoJia();
			break;
		case R.id.qiangdan_user_main_zainaer_ll:
			// 在哪儿
			toZaina();
			break;
		case R.id.qiangdan_user_main_tejia_ll:
			// 找特价
			toTeJia();
			break;
		
		default:
			break;
		}
	}

	private void toWoXiangYao() {
		myIntent = new Intent(context, UserWoXiangYaoqdActivity.class);
		startActivity(myIntent);
	}

	private void toChaBaoJia() {
	
			myIntent = new Intent(context, UserChaBaoJiaqdActivity.class);
			startActivity(myIntent);
		
		
	}

	private void toZaina() {
		
			myIntent = new Intent(context, UserPeisongListActivity.class);
			startActivity(myIntent);
		
	}

	private void toTeJia() {
		
		if (ConstantValue.storeqdList == null||ConstantValue.storeqdList.size()<4 ) {
			PromptManager.showMyToast(getApplicationContext(), "抢单还未开始");
		}else {
			myIntent = new Intent(context, UserZhaoTeJiaqiangdanActivity.class);
			startActivity(myIntent);
		}
	}

	//
}
