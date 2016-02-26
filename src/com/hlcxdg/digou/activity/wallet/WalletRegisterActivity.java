package com.hlcxdg.digou.activity.wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.wallet.bean.BuyerR;
import com.hlcxdg.digou.bean.UserInfo;
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
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class WalletRegisterActivity extends Activity implements OnClickListener {
	private Context context;
	private Intent myIntent;
	private Button w_reg_bt_reg;
	private BuyerR buyerR;
	private EditText w_reg_Cid;
	private EditText w_reg_BuyerName;
	private EditText w_reg_BuyerPwd;
	private EditText w_reg_PayPwd;
	private EditText w_reg_Email;
	private EditText w_reg_Tel;
	private EditText w_reg_cRealName;
	private ImageView w_reg_iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wallet_register);
		context = WalletRegisterActivity.this;
		init();
	}

	private void init() {
		w_reg_bt_reg = (Button) findViewById(R.id.w_reg_bt_reg);
		w_reg_bt_reg.setOnClickListener(this);
		w_reg_iv_back = (ImageView) findViewById(R.id.w_reg_iv_back);
		w_reg_iv_back.setOnClickListener(this);

		w_reg_BuyerName = (EditText) findViewById(R.id.w_reg_BuyerName);
		w_reg_BuyerPwd = (EditText) findViewById(R.id.w_reg_BuyerPwd);
		w_reg_PayPwd = (EditText) findViewById(R.id.w_reg_PayPwd);
		w_reg_Email = (EditText) findViewById(R.id.w_reg_Email);
		w_reg_Tel = (EditText) findViewById(R.id.w_reg_Tel);
		w_reg_cRealName = (EditText) findViewById(R.id.w_reg_cRealName);
		w_reg_Cid = (EditText) findViewById(R.id.w_reg_Cid);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		// 页面返回上一级
		case R.id.w_reg_iv_back:
			finish();
			break;
		case R.id.w_reg_bt_reg:
			// 进行注册
			try {
				toRegisterBuyer();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	private void toRegisterBuyer() {
		if (TextUtils.isEmpty(w_reg_BuyerName.getText().toString())) {
			PromptManager.showMyToast(WalletRegisterActivity.this, "用户名不能为空");
			return;
		}
		if (TextUtils.isEmpty(w_reg_BuyerPwd.getText().toString())) {
			PromptManager.showMyToast(WalletRegisterActivity.this, "用户密码不能为空");
			return;
		}
		if (TextUtils.isEmpty(w_reg_Tel.getText().toString())) {
			PromptManager.showMyToast(WalletRegisterActivity.this, "手机号不能为空");
			return;
		}
		if (TextUtils.isEmpty(w_reg_PayPwd.getText().toString())) {
			PromptManager.showMyToast(WalletRegisterActivity.this, "支付密码不能为空");
			return;
		}
		if (w_reg_PayPwd.getText().toString().length()!=6) {
			PromptManager.showMyToast(WalletRegisterActivity.this, "支付密码必须6位");
			return;
		}
		buyerR = new BuyerR();
		buyerR.setBuyerName(w_reg_BuyerName.getText().toString());
		buyerR.setBuyerPwd(w_reg_BuyerPwd.getText().toString());
		buyerR.setTel(w_reg_Tel.getText().toString());
		buyerR.setPayPwd(w_reg_PayPwd.getText().toString());
		buyerR.setEmail(w_reg_Email.getText().toString());
		buyerR.setcRealName(w_reg_cRealName.getText().toString());
		buyerR.setCid(w_reg_Cid.getText().toString());
		new RegAsynctask().execute("");
	}

	public class RegAsynctask extends AsyncTask<String, Process, Integer> {

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result == 1) {//
				WalletRegisterActivity.this.finish();
				PromptManager.showMyToast(context, "注册成功");
			} else if (result == 0) {//
				PromptManager.showMyToast(context, "该用户名已经存在");
			} else {//
				PromptManager.showMyToast(context, "注册失败");
			}
			PromptManager.closeSpotsDialog();
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
				String msg = "*AIS|RQ|BuyerR|00|" + userlistJson + "|AIE#";

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.wallet_buyreg));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.wallet_buyreg, context,
						map);

				String result = NetUtilWallet.post(vo);
				if (result == null) {
					return -1;
				} else {
					String[] temp = NetUtil.split(result, "|");
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "BuyerR".equals(temp[2])
							&& "01".equals(temp[3])) {
						String id = temp[4];
						buyerR.setBuyer_id(id);
						ConstantValue.wBuyerR = buyerR;
						return 1;
					}
				}
			} catch (JsonSyntaxException e) {
				e.printStackTrace();
				return 0;
			}
			return 0;
		}

	}

}
