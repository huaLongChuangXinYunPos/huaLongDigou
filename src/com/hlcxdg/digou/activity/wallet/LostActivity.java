package com.hlcxdg.digou.activity.wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.wallet.OutWalletZhifuActivity.GetLostMoney;
import com.hlcxdg.digou.activity.wallet.bean.BuyerR;
import com.hlcxdg.digou.activity.wallet.bean.QueryLost;
import com.hlcxdg.digou.bean.StoreItem;
import com.hlcxdg.digou.bean.UserNeedsBean;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.net.NetUtilWallet;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LostActivity extends Activity implements OnClickListener {
	private TextView w_mylost_money_tv;
	private ImageView losts_iv_back_wallet;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wallet_query_lost);
		context = LostActivity.this;
		init();
		// new GetLostMoney().execute("");
	}

	private void init() {
		try {
			w_mylost_money_tv = (TextView) findViewById(R.id.w_mylost_money_tv);
			losts_iv_back_wallet = (ImageView) findViewById(R.id.losts_iv_back_wallet);
			losts_iv_back_wallet.setOnClickListener(this);
			if (ConstantValue.lostWMoney != null) {
				w_mylost_money_tv.setText(ConstantValue.lostWMoney + "");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.losts_iv_back_wallet:
			finish();
			break;

		default:
			break;
		}
	}

}
