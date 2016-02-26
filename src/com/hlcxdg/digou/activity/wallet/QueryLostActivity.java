package com.hlcxdg.digou.activity.wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
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

public class QueryLostActivity extends Activity implements OnClickListener {
	private EditText wallet_account_lost;
	private EditText wallet_pwd_lost;
	private ImageView lost_iv_back_wallet;
	private Button wallet_bt_sub;
	private Button wallet_bt_cancel;
	private String ownerType = "User";
	private Context context;
	private BuyerR buyerR;
	private Intent myIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wallet_query_lost_login);
		context = QueryLostActivity.this;
		init();
		if (ConstantValue.lostWMoney != null) {
			myIntent = new Intent(context, LostActivity.class);
			startActivity(myIntent);
			finish();
		}
	}

	private void init() {
		wallet_bt_sub = (Button) findViewById(R.id.wallet_bt_sub);
		wallet_bt_cancel = (Button) findViewById(R.id.wallet_bt_cancel);
		lost_iv_back_wallet = (ImageView) findViewById(R.id.lost_iv_back_wallet);
		lost_iv_back_wallet.setOnClickListener(this);
		wallet_pwd_lost = (EditText) findViewById(R.id.wallet_pwd_lost);
		wallet_account_lost = (EditText) findViewById(R.id.wallet_account_lost);
		wallet_bt_sub.setOnClickListener(this);
		wallet_bt_cancel.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.wallet_bt_cancel:
		case R.id.lost_iv_back_wallet:
			finish();
			break;
		case R.id.wallet_bt_sub:
			if (TextUtils.isEmpty(wallet_pwd_lost.getText().toString().trim())
					|| TextUtils.isEmpty(wallet_account_lost.getText()
							.toString().trim())) {
				PromptManager.showMyToast(context, "用户名或支付密码不能为空");
				break;
			}
			buyerR = new BuyerR();
			buyerR.setBuyerName(wallet_account_lost.getText().toString().trim());
			buyerR.setBuyerPwd(wallet_pwd_lost.getText().toString().trim());

			new QuerywalletAscny(context).execute("");
			break;
		case R.id.wallet_isStore_iv:
			if (ownerType.equals("User")) {
				ownerType = "Store";
			} else {
				ownerType = "User";
			}
			break;

		default:
			break;
		}
	}

	private class QuerywalletAscny extends AsyncTask<String, Process, Integer> {
		Context context;

		public QuerywalletAscny(Context context) {
			this.context = context;
		}

		@Override
		protected Integer doInBackground(String... args) {
			try {
				// public String WServerID;
				// public String BuyerName;
				// public String PayPwd;
				List<BuyerR> buyerRlist = new ArrayList<BuyerR>();
				buyerRlist.add(buyerR);
				Gson gson = new Gson();
				String userlistJson = gson.toJson(buyerRlist);
				String msg = "*AIS|RQ|WQuery|00|" + userlistJson + "|AIE#";

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_walletquery));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_walletquery,
						context, map);

				String result = NetUtilWallet.post(vo);
				// *AIS|RS|WQuery|01|{"WServerID":"1","BuyerName":"222","WMoney":0.0000}|AIE#
				if (result == null) {
					return -1;
				} else {
					String[] temp = NetUtil.split(result, "|");
					if ("01".equals(temp[3])) {
						ArrayList<QueryLost> tmplist = gson.fromJson(temp[4],
								new TypeToken<List<QueryLost>>() {
								}.getType());
						ConstantValue.lostWMoney = tmplist.get(0).getMyWMoney();
						return 1;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 0;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result == 1) {
				myIntent = new Intent(context, LostActivity.class);
				startActivity(myIntent);
			} else {
				PromptManager.showMyToast(context, "查询失败");
			}
			// 去余额显示页面

		}
	}

}
