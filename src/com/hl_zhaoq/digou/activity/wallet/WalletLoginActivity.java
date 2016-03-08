package com.hl_zhaoq.digou.activity.wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.baidu.navisdk.comapi.mapcontrol.MapParams.Const;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.MainActivity;
import com.hl_zhaoq.digou.activity.wallet.bean.BuyerR;
import com.hl_zhaoq.digou.activity.wallet.bean.QueryLost;
import com.hl_zhaoq.digou.bean.StoreItem;
import com.hl_zhaoq.digou.bean.UserInfo;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.net.NetUtilWallet;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class WalletLoginActivity extends Activity implements OnClickListener {
	private Context context;
	private Intent myIntent;
	private ImageView w_login_iv_back;
	private Button w_login_bt_reg;
	private Button w_login_bt_login;
	private BuyerR buyerR;
	private EditText w_log_BuyerName;
	private EditText w_log_BuyerPwd;
	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wallet_login);
		context = WalletLoginActivity.this;
		init();
		sp = getSharedPreferences("mybuyerR", MODE_PRIVATE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (ConstantValue.wBuyerR != null) {
			Intent intent;
			intent = new Intent();
			intent.setClass(context, WalletMainActivity.class);
			startActivity(intent);
			finish();
		}
	}

	private void init() {
		w_login_bt_reg = (Button) findViewById(R.id.w_login_bt_reg);
		w_login_bt_reg.setOnClickListener(this);
		w_login_bt_login = (Button) findViewById(R.id.w_login_bt_login);
		w_login_bt_login.setOnClickListener(this);
		w_login_iv_back = (ImageView) findViewById(R.id.w_login_iv_back);
		w_login_iv_back.setOnClickListener(this);
		w_log_BuyerName = (EditText) findViewById(R.id.w_log_BuyerName);
		w_log_BuyerPwd = (EditText) findViewById(R.id.w_log_BuyerPwd);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.w_login_iv_back:
			finish();
			break;
		case R.id.w_login_bt_reg:
			// 进行注册
			myIntent = new Intent(context, WalletRegisterActivity.class);
			startActivity(myIntent);
			break;

		case R.id.w_login_bt_login:
			// 进行注册
			try {
				toLoginBuyer();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	private void toLoginBuyer() {
		if (TextUtils.isEmpty(w_log_BuyerName.getText().toString())) {
			PromptManager.showMyToast(WalletLoginActivity.this, "用户名不能为空");
			return;
		}
		if (TextUtils.isEmpty(w_log_BuyerPwd.getText().toString())) {
			PromptManager.showMyToast(WalletLoginActivity.this, "用户密码不能为空");
			return;
		}

		buyerR = new BuyerR();
		buyerR.setBuyerName(w_log_BuyerName.getText().toString());
		buyerR.setBuyerPwd(w_log_BuyerPwd.getText().toString());

		new LoginAsynctask().execute("");
	}

	public class LoginAsynctask extends AsyncTask<String, Process, Integer> {

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result == 1) {
				PromptManager.showMyToast(context, "登录成功");
				Intent intent;
				intent = new Intent();
				intent.setClass(context, WalletMainActivity.class);
				startActivity(intent);
				finish();
			}
			if (result == 0) {
				PromptManager.showMyToast(context, "登录失败");
			}
			if (result == -1) {
				PromptManager.showMyToast(context, "网络不给力");
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}

		@Override
		protected Integer doInBackground(String... arg0) {

			try {
				List<BuyerR> buyerRlist = new ArrayList<BuyerR>();
				buyerRlist.add(buyerR);
				Gson gson = new Gson();
				String userlistJson = gson.toJson(buyerRlist);
				String msg = "*AIS|RQ|Login|00|" + userlistJson + "|AIE#";

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.wallet_buymanager));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.wallet_buymanager,
						context, map);
				String result = NetUtilWallet.post(vo);
				if (result == null) {
					return -1;
				} else {
					String[] temp = NetUtil.split(result, "|");
					if ("01".equalsIgnoreCase(temp[3])) {//
						ArrayList<BuyerR> tmpStlist = gson.fromJson(temp[4],
								new TypeToken<List<BuyerR>>() {
								}.getType());
						// BuyerR b = gson.fromJson(temp[4], BuyerR.class);
						ConstantValue.wBuyerR = tmpStlist.get(0);
						ConstantValue.wBuyerR.setBuyerPwd(buyerR.getBuyerPwd());
						ConstantValue.isWLogin=true;
						Editor ed = sp.edit();
						ed.putString("Buyer_id",
								ConstantValue.wBuyerR.getBuyer_id());
						ed.putString("BuyerName",
								ConstantValue.wBuyerR.getBuyerName());
						ed.putString("Tel", ConstantValue.wBuyerR.getTel());
						ed.commit();
						msg = "*AIS|RQ|WQuery|00|" + userlistJson + "|AIE#";
						map = new HashMap<String, String>();
						map.put("type", "post");
						map.put("url",
								context.getString(R.string.servlet_walletquery));
						map.put("dataType", "text");
						map.put("data", msg);
						vo = new RequestVo(R.string.servlet_walletquery,
								context, map);
						String result2 = NetUtilWallet.post(vo);
						// *AIS|RS|WQuery|01|{"WServerID":"1","BuyerName":"222","WMoney":0.0000}|AIE#
						if (result2 == null) {
						} else {
							String[] temp2 = NetUtil.split(result2, "|");
							if ("01".equals(temp2[3])) {
								ArrayList<QueryLost> tmplist = gson.fromJson(
										temp2[4],
										new TypeToken<List<QueryLost>>() {
										}.getType());
								ConstantValue.lostWMoney = tmplist.get(0)
										.getMyWMoney();
								
							}
						}
						return 1;
					} else {
						return 0;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}

	}

}
