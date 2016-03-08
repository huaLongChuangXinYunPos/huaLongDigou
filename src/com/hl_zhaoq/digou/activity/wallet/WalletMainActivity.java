package com.hl_zhaoq.digou.activity.wallet;

import com.hl_zhaoq.digou.activity.qiagndan.UserZhaoTeJiaqiangdanActivity;
import com.hl_zhaoq.digou.R;

import android.app.Activity;
import android.app.DownloadManager.Request;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class WalletMainActivity extends Activity implements OnClickListener {
	private RelativeLayout w_user_main_lost_ll;
	private RelativeLayout w_user_main_back_ll;
	private RelativeLayout w_user_mainw_up_ppwd;
	private RelativeLayout w_up_loginpwd;
	private RelativeLayout w_user_main_telup_ll;
	private ImageView w_zzhang_img;
	private ImageView w_pay_zhi_img;
	private Context context;
	private Intent myIntent;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wallet_main);
		context=WalletMainActivity.this;
		initView();
	}

	public void initView() {
		w_user_mainw_up_ppwd = (RelativeLayout) findViewById(R.id.w_user_mainw_up_ppwd);
		w_user_mainw_up_ppwd.setOnClickListener(this);
		w_user_main_telup_ll = (RelativeLayout) findViewById(R.id.w_user_main_telup_ll);
		w_user_main_telup_ll.setOnClickListener(this);
		w_user_main_lost_ll = (RelativeLayout) findViewById(R.id.w_user_main_lost_ll);
		w_user_main_lost_ll.setOnClickListener(this);
		w_user_main_back_ll = (RelativeLayout) findViewById(R.id.w_user_main_back_ll);
		w_user_main_back_ll.setOnClickListener(this);
		w_up_loginpwd= (RelativeLayout) findViewById(R.id.w_up_loginpwd);
		w_up_loginpwd.setOnClickListener(this);
		w_zzhang_img = (ImageView) findViewById(R.id.w_zzhang_img);
		w_pay_zhi_img = (ImageView) findViewById(R.id.w_pay_zhi_img);

		w_zzhang_img.setOnClickListener(this);
		w_pay_zhi_img.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {
		
		case R.id.w_user_main_telup_ll:
			myIntent = new Intent(context, WalletTelActivity.class);
			startActivity(myIntent);
			break;
		case R.id.w_user_mainw_up_ppwd:
			myIntent = new Intent(context, WalletPPwdActivity.class);
			startActivity(myIntent);
			break;
		case R.id.w_up_loginpwd:
			myIntent = new Intent(context, WalletLPwdActivity.class);
			startActivity(myIntent);
			break;
		case R.id.w_user_main_lost_ll:
			myIntent = new Intent(context, QueryLostActivity.class);
			startActivity(myIntent);
			break;
		case R.id.w_user_main_back_ll:
//			myIntent = new Intent(context, BackupMoneyActivity.class);
//			startActivity(myIntent);
			break;
		case R.id.w_zzhang_img:
			myIntent = new Intent(context, ZhuanzhangActivity.class);
			startActivity(myIntent);
			break;
		case R.id.w_pay_zhi_img:
//			myIntent = new Intent(context, WalletZhifuActivity.class);
//			startActivity(myIntent);
			break;
		default:
			break;
		}
	}

}
