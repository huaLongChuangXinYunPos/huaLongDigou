package com.hlcxdg.digou.activity.wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.wallet.bean.BuyerR;
import com.hlcxdg.digou.activity.wallet.bean.PPwd;
import com.hlcxdg.digou.activity.wallet.bean.UTel;
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

public class WalletTelActivity extends Activity implements OnClickListener {
	private Context context;
	private UTel pPwd;
	private Intent myIntent;
	private Button w_tel_cancel_btn;
	private Button w_tel_ok_up;
	private BuyerR buyerR;
	private EditText w_tel_old_up;
	private EditText w_tel_new_up;
	private EditText w_tel_pwd_up;
	private ImageView w_tel_iv_back_up;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wallet_update_bind_tel);
		context = WalletTelActivity.this;
		init();
	}

	private void init() {
		w_tel_cancel_btn = (Button) findViewById(R.id.w_tel_cancel_up);
		w_tel_cancel_btn.setOnClickListener(this);
		w_tel_ok_up = (Button) findViewById(R.id.w_tel_ok_up);
		w_tel_ok_up.setOnClickListener(this);
		w_tel_iv_back_up = (ImageView) findViewById(R.id.w_tel_iv_back_up);
		w_tel_iv_back_up.setOnClickListener(this);
		w_tel_old_up = (EditText) findViewById(R.id.w_tel_old_up);
		w_tel_new_up = (EditText) findViewById(R.id.w_tel_new_up);
		w_tel_pwd_up = (EditText) findViewById(R.id.w_tel_pwd_up);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.w_tel_iv_back_up:
			// 取消
			finish();
			break;
		case R.id.w_tel_cancel_up:
			// 取消
			finish();
			break;

		case R.id.w_tel_ok_up:
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
		if (TextUtils.isEmpty(w_tel_old_up.getText().toString())) {
			PromptManager.showToast(context, "原手机号不能为空");
			return;
		}
		if (TextUtils.isEmpty(w_tel_new_up.getText().toString())) {
			PromptManager.showToast(context, "新手机号不能为空");
			return;
		}
		if (TextUtils.isEmpty(w_tel_pwd_up.getText().toString())) {
			PromptManager.showToast(context, "密码不能为空");
			return;
		}
		pPwd = new UTel();
		pPwd.setWServerID(ConstantValue.wBuyerR.getWServerID());
		pPwd.setBuyerName(ConstantValue.wBuyerR.getBuyerName());
		pPwd.setTelOld(w_tel_old_up.getText().toString());
		pPwd.setTelNew(w_tel_new_up.getText().toString());
		pPwd.setBuyerPwd(w_tel_pwd_up.getText().toString());

		new UpTelAsynctask().execute("");
	}

	public class UpTelAsynctask extends AsyncTask<String, Process, Integer> {

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 1) {//
				PromptManager.showMyToast(context, "手机号修改成功");
				w_tel_old_up.setText("");
				w_tel_new_up.setText("");
				w_tel_pwd_up.setText("");
			} else {//
				PromptManager.showMyToast(context, "手机号修改失败");
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
				List<UTel> buyerRlist = new ArrayList<UTel>();
				buyerRlist.add(pPwd);
				Gson gson = new Gson();
				String userlistJson = gson.toJson(buyerRlist);
				String msg = "*AIS|RQ|TelUpdate|00|" + userlistJson + "|AIE#";

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url",
						getApplicationContext().getString(
								R.string.wallet_buymanager));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.wallet_buymanager, context,
						map);

				String result = NetUtilWallet.post(vo);// *AIS|RS|TelUpdate|01|ok||AIE#
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
