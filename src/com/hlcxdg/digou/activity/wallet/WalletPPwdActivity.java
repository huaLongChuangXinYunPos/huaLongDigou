package com.hlcxdg.digou.activity.wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.wallet.bean.BuyerR;
import com.hlcxdg.digou.activity.wallet.bean.PPwd;
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

public class WalletPPwdActivity extends Activity implements OnClickListener {
	private Context context;
	private Intent myIntent;
	private PPwd pPwd;
	private Button w_upp_cancel_btn;
	private Button w_upp_ok_btn;
	private BuyerR buyerR;
	private EditText w_upp_old_pwd;
	private EditText w_upp_new_pwd;
	private ImageView w_upp_iv_back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wallet_update_ppwd);
		context = WalletPPwdActivity.this;
		init();
	}

	private void init() {
		w_upp_cancel_btn = (Button) findViewById(R.id.w_upp_cancel_btn);
		w_upp_cancel_btn.setOnClickListener(this);
		w_upp_ok_btn = (Button) findViewById(R.id.w_upp_ok_btn);
		w_upp_ok_btn.setOnClickListener(this);
		w_upp_iv_back = (ImageView) findViewById(R.id.w_upp_iv_back);
		w_upp_iv_back.setOnClickListener(this);
		w_upp_old_pwd = (EditText) findViewById(R.id.w_upp_old_pwd);
		w_upp_new_pwd = (EditText) findViewById(R.id.w_upp_new_pwd);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.w_upp_iv_back:
			// 取消
			finish();
			break;
		case R.id.w_upp_cancel_btn:
			// 取消
			finish();
			break;

		case R.id.w_upp_ok_btn:
			// 进行变更
			try {
				toPwdUpdate();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;

		default:
			break;
		}
	}

	private void toPwdUpdate() {
		if (TextUtils.isEmpty(w_upp_old_pwd.getText().toString())) {
			PromptManager.showMyToast(context, "原密码不能为空");
			return;
		}
		if (TextUtils.isEmpty(w_upp_new_pwd.getText().toString())) {
			PromptManager.showMyToast(context, "新密码不能为空");
			return;
		}

		pPwd = new PPwd();
		pPwd.setWServerID(ConstantValue.wBuyerR.getWServerID());
		pPwd.setBuyerName(ConstantValue.wBuyerR.getBuyerName());
		pPwd.setPayPwdOld(w_upp_old_pwd.getText().toString());
		pPwd.setPayPwdNew(w_upp_new_pwd.getText().toString());

		new UpPPAsynctask().execute("");
	}

	public class UpPPAsynctask extends AsyncTask<String, Process, Integer> {

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 1) {//
				PromptManager.showMyToast(context, "支付密码修改成功");
			} else {//
				PromptManager.showMyToast(context, "支付密码修改失败");
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
				List<PPwd> buyerRlist = new ArrayList<PPwd>();
				buyerRlist.add(pPwd);
				Gson gson = new Gson();
				String userlistJson = gson.toJson(buyerRlist);
				String msg = "*AIS|RQ|PPwdUpdate|00|" + userlistJson + "|AIE#";

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url",
						getApplicationContext().getString(
								R.string.wallet_buymanager));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.wallet_buymanager, context,
						map);
				String result = NetUtilWallet.post(vo);// *AIS|RS|PPwdUpdate|00|err||AIE#
				if (result == null) {
					return -1;
				} else {
					String[] temp = NetUtil.split(result, "|");
					if ("01".equals(temp[3])) {
						return 1;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}

	}

}
