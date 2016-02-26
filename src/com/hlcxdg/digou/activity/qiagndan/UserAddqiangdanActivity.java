package com.hlcxdg.digou.activity.qiagndan;

import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.StoreInfoqdBean;
import com.hlcxdg.digou.bean.UserNeedsBean;
import com.hlcxdg.digou.constant.ConstantLocation;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.utils.PromptManager;

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

public class UserAddqiangdanActivity extends Activity implements
		OnClickListener {
//商品库选择商品-用户发布
//收火热
	private UserNeedsBean userqdInfo;
	private Context context;
	private Intent myIntent;
	private UserNeedsBean qdUserInfo;
	private ImageView qiangdan_user_back_iv;
	private EditText qiangdan_user_name_ed;
	private EditText qiangdan_user_tel_ed;
	private EditText qiangdan_user_ddname_ed;
	private EditText qiangdan_user_ddprice_ed;
	private EditText qiangdan_user_ddnum_ed;
	private Button qiangdan_user_kuaidiskan;
	private Button qiangdan_user_add_sub_bt;
	private Button qiangdan_user_miaoqiang_bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qiangdan_user_add_dd);
		context = UserAddqiangdanActivity.this;
		init();
	}

	private void init() {

		qiangdan_user_back_iv = (ImageView) findViewById(R.id.qiangdan_user_add_back_iv);
		qiangdan_user_back_iv.setOnClickListener(this);
		qiangdan_user_name_ed = (EditText) findViewById(R.id.qiangdan_user_name_ed);
		qiangdan_user_tel_ed = (EditText) findViewById(R.id.qiangdan_user_tel_ed);
		qiangdan_user_ddname_ed = (EditText) findViewById(R.id.qiangdan_user_ddname_ed);
		qiangdan_user_ddprice_ed = (EditText) findViewById(R.id.qiangdan_user_ddprice_ed);
		qiangdan_user_ddnum_ed = (EditText) findViewById(R.id.qiangdan_user_ddnum_ed);
		qiangdan_user_kuaidiskan = (Button) findViewById(R.id.qiangdan_user_kuaidiskan);
		qiangdan_user_add_sub_bt = (Button) findViewById(R.id.qiangdan_user_add_sub_bt);
		qiangdan_user_miaoqiang_bt = (Button) findViewById(R.id.qiangdan_user_miaoqiang_bt);
		qiangdan_user_kuaidiskan.setOnClickListener(this);
		qiangdan_user_add_sub_bt.setOnClickListener(this);
		qiangdan_user_miaoqiang_bt.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.qiangdan_user_add_back_iv:
			finish();
			break;
		case R.id.qiangdan_user_add_sub_bt:
			// 提交预约定单
			submitUserNeeds();
			break;
		case R.id.qiangdan_user_miaoqiang_bt:
			// 进入地图秒抢
			if (ConstantValue.storeqdList == null
					|| ConstantValue.storeqdList.size() == 0) {
				PromptManager.showMyToast(context, "商户还没有出单");
				break;
			}

			myIntent = new Intent(context, UserZhaoTeJiaqiangdanActivity.class);
			startActivity(myIntent);
			break;
		case R.id.qiangdan_user_kuaidiskan:
			// 用户查看配送信息
//			if (ConstantValue.storeInfoqdok == null) {
//				PromptManager.showMyToast(getApplicationContext(), "您还没有抢单");
//				break;
//			} else if ("未发货".equals(ConstantValue.storeInfoqdok.getZhuangtai())) {
//				PromptManager.showMyToast(getApplicationContext(), "您的下单还未发货");
//				break;
//			}
			// 进入物流配送
			// myIntent = new Intent(context, UserSkanWuliuqdActivity.class);
			// startActivity(myIntent);
			break;
		default:
			break;
		}
	}

	private void submitUserNeeds() {
		qiangdan_user_name_ed = (EditText) findViewById(R.id.qiangdan_user_name_ed);
		qiangdan_user_tel_ed = (EditText) findViewById(R.id.qiangdan_user_tel_ed);
		qiangdan_user_ddname_ed = (EditText) findViewById(R.id.qiangdan_user_ddname_ed);
		qiangdan_user_ddprice_ed = (EditText) findViewById(R.id.qiangdan_user_ddprice_ed);
		qiangdan_user_ddnum_ed = (EditText) findViewById(R.id.qiangdan_user_ddnum_ed);
		String userName = qiangdan_user_name_ed.getText().toString().trim();
		String userTel = qiangdan_user_tel_ed.getText().toString().trim();
		String userGoodsName = qiangdan_user_ddname_ed.getText().toString()
				.trim();
		String userGoodsPrice = qiangdan_user_ddprice_ed.getText().toString()
				.trim();
		String userGoodsNum = qiangdan_user_ddnum_ed.getText().toString()
				.trim();
		if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(userTel)
				|| TextUtils.isEmpty(userGoodsName)
				|| TextUtils.isEmpty(userGoodsPrice)
				|| TextUtils.isEmpty(userGoodsNum)) {
			PromptManager.showMyToast(context, "输入不可为空");
			return;
		} else {

//			ConstantValue.userqdList.clear();
//
//			userqdInfo = new UserNeedsBean();
//			userqdInfo.setJingdu(ConstantLocation.myLongitudeString + 0.012);
//			userqdInfo.setWeidu(ConstantLocation.myLatitudeString - 0.013);
//			userqdInfo.setUsernameqd(userName);
//			userqdInfo.setUsertelqd(userTel);
//			userqdInfo.setUserqdGoodsname(userGoodsName);
//			userqdInfo.setUserqdprice(userGoodsPrice);
//			userqdInfo.setUserqdnum(userGoodsNum);
//			ConstantValue.userqdList.add(userqdInfo);
//
//			userqdInfo = new UserNeedsBean();
//			userqdInfo.setJingdu(ConstantLocation.myLongitudeString - 0.015);
//			userqdInfo.setWeidu(ConstantLocation.myLatitudeString - 0.016);
//			userqdInfo.setUsernameqd(userName);
//			userqdInfo.setUsertelqd(userTel);
//			userqdInfo.setUserqdGoodsname(userGoodsName);
//			userqdInfo.setUserqdprice(userGoodsPrice);
//			userqdInfo.setUserqdnum(userGoodsNum);
//			ConstantValue.userqdList.add(userqdInfo);
//
//			userqdInfo = new UserNeedsBean();
//			userqdInfo.setJingdu(ConstantLocation.myLongitudeString - 0.015);
//			userqdInfo.setWeidu(ConstantLocation.myLatitudeString + 0.016);
//			userqdInfo.setUsernameqd(userName);
//			userqdInfo.setUsertelqd(userTel);
//			userqdInfo.setUserqdGoodsname(userGoodsName);
//			userqdInfo.setUserqdprice(userGoodsPrice);
//			userqdInfo.setUserqdnum(userGoodsNum);
//			ConstantValue.userqdList.add(userqdInfo);
//
//			userqdInfo = new UserNeedsBean();
//			userqdInfo.setJingdu(ConstantLocation.myLongitudeString + 0.015);
//			userqdInfo.setWeidu(ConstantLocation.myLatitudeString + 0.016);
//			userqdInfo.setUsernameqd(userName);
//			userqdInfo.setUsertelqd(userTel);
//			userqdInfo.setUserqdGoodsname(userGoodsName);
//			userqdInfo.setUserqdprice(userGoodsPrice);
//			userqdInfo.setUserqdnum(userGoodsNum);
//			ConstantValue.userqdList.add(userqdInfo);
//			PromptManager.showMyToast(context, "发布成功");
		}
	}

}
