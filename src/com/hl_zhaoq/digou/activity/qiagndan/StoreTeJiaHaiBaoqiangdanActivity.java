package com.hl_zhaoq.digou.activity.qiagndan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hl_zhaoq.digou.bean.RequestBean;
import com.hl_zhaoq.digou.bean.ResponseBean;
import com.hl_zhaoq.digou.bean.StoreBaojiaBean;
import com.hl_zhaoq.digou.bean.StoreInfoqdBean;
import com.hl_zhaoq.digou.constant.ConstantLocation;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtilddgg;
import com.hl_zhaoq.digou.net.RequestVoddgg;
import com.hl_zhaoq.digou.utils.DecimalUtils;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
//用户：我想要，查报价（成交），在哪儿，附近特价
//商户：我报价，查订单，                        特价海报，
//
//

public class StoreTeJiaHaiBaoqiangdanActivity extends Activity implements
		OnClickListener {
	private Context context;
	private Intent myIntent;
	private StoreInfoqdBean qdStoreInfo;
	private ImageView qiangdan_store_back_iv;
	private EditText qiangdan_store_name_ed;
	private EditText qiangdan_store_ddname_ed;
	private EditText qiangdan_store_ddprice_ed;
	private EditText qiangdan_store_ddnum_ed;
	private Button qiangdan_store_add_sub;
	ImageButton qd_store_names_find;
	ImageButton qd_store_goods_find;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qiangdan_store_tejiahaibao_dd);
		context = StoreTeJiaHaiBaoqiangdanActivity.this;
		init();

	}

	private void zixuanGoods() {
		Intent intent = new Intent();
		intent.setClass(context, FenleiActivity.class);
		startActivity(intent);
	}

	private void init() {
		qd_store_names_find = (ImageButton) findViewById(R.id.qd_store_names_find);
		qd_store_names_find.setOnClickListener(this);
		qd_store_goods_find = (ImageButton) findViewById(R.id.qd_store_goods_find);
		qd_store_goods_find.setOnClickListener(this);

		qiangdan_store_back_iv = (ImageView) findViewById(R.id.qiangdan_store_back_iv);
		qiangdan_store_back_iv.setOnClickListener(this);
		qiangdan_store_name_ed = (EditText) findViewById(R.id.qiangdan_store_name_ed);
		qiangdan_store_ddname_ed = (EditText) findViewById(R.id.qiangdan_store_ddname_ed);
		qiangdan_store_ddprice_ed = (EditText) findViewById(R.id.qiangdan_store_ddprice_ed);
		qiangdan_store_ddnum_ed = (EditText) findViewById(R.id.qiangdan_store_ddnum_ed);
		qiangdan_store_add_sub = (Button) findViewById(R.id.qiangdan_store_add_sub);
		qiangdan_store_add_sub.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {

		case R.id.qd_store_names_find:
			//
			zixuanStore();
			break;

		case R.id.qiangdan_store_back_iv:
			finish();
			break;
		case R.id.qiangdan_store_add_sub:
			// 提交抢单
			submitqiangdan();
			break;
		case R.id.qd_store_goods_find:
			zixuanGoods();
			break;
		default:
			break;
		}
	}

	private void zixuanStore() {
		// TODO Auto-generated method stub
		Intent intent = new Intent();
		intent.setClass(context, StoreListActivity.class);
		startActivity(intent);
	}

	private void submitqiangdan() {
		String storeName = qiangdan_store_name_ed.getText().toString().trim();
		String storeddname = qiangdan_store_ddname_ed.getText().toString()
				.trim();
		String storeddprice = qiangdan_store_ddprice_ed.getText().toString()
				.trim();
		String storeddnum = qiangdan_store_ddnum_ed.getText().toString().trim();
		if (TextUtils.isEmpty(storeName) || TextUtils.isEmpty(storeddname)
				|| TextUtils.isEmpty(storeddprice)
				|| TextUtils.isEmpty(storeddnum)) {
			PromptManager.showMyToast(context, "输入不可为空");
			return;
		} else {

			qdStoreInfo = new StoreInfoqdBean();

			qdStoreInfo.setJingdu(ConstantLocation.myLongitudeString
					+ DecimalUtils.getRandomDouble());
			qdStoreInfo.setWeidu(ConstantLocation.myLatitudeString
					+ DecimalUtils.getRandomDouble());
			qdStoreInfo.setStorenameqd(storeName);
			qdStoreInfo.setStoreqdGoodsname(storeddname);
			qdStoreInfo.setStoreqdprice(new BigDecimal(storeddprice));
			try {
				qdStoreInfo.setStoreqdnum(Integer.parseInt(storeddnum));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			new AddHaiBao().execute(qdStoreInfo);

		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (ConstantValue.goodsinfo != null) {
			qiangdan_store_ddname_ed.setText(ConstantValue.goodsinfo
					.getcGoodsName());
		}
		if (ConstantValue.storeItem != null) {
			qiangdan_store_name_ed.setText(ConstantValue.storeItem
					.getcStoreName());
		}
	}

	public class AddHaiBao extends AsyncTask<StoreInfoqdBean, Process, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result) {
				PromptManager.showMyToast(getApplicationContext(), "提交成功");
			} else {
				PromptManager.showMyToast(getApplicationContext(), "提交失败");
			}
		}

		@Override
		protected Boolean doInBackground(StoreInfoqdBean... stb) {
			try {
				StoreInfoqdBean sb = stb[0];

				Gson g = new Gson();
				RequestBean re = new RequestBean();
				re.setStoreInfoqdBean(sb);
				re.setReqCode(5101);
				re.setReqMsg("添加海报");
				String jsonString = g.toJson(re);
				RequestVoddgg vo = new RequestVoddgg(R.string.url_userneeds,
						StoreTeJiaHaiBaoqiangdanActivity.this, jsonString);
				String bresult = NetUtilddgg.post(vo);
				ResponseBean responseBean = g.fromJson(bresult,
						ResponseBean.class);
				if (responseBean.getRespCode() == 1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}

	}
}
