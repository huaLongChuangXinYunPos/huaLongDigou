package com.hlcxdg.digou.activity.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.UserInfo;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

/*
 * 用户注册
 * 
 */
public class RegActivity extends Activity {
	public EditText et_username;
	public Button bt_reg;
	public ImageView iv_back;
	public EditText ed_password;
	public EditText ed_tel_reg;
	public EditText ed_cardid_reg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register);
		initView();

	}

	public void initView() {
		ed_tel_reg = (EditText) findViewById(R.id.ed_tel_reg);
		ed_cardid_reg = (EditText) findViewById(R.id.ed_cardid_reg);
		bt_reg = (Button) findViewById(R.id.bt_reg);
		et_username = (EditText) findViewById(R.id.et_username);
		ed_password = (EditText) findViewById(R.id.ed_password);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		bt_reg.setOnClickListener(new MyOnClickListner());
		iv_back.setOnClickListener(new MyOnClickListner());
	}

	public class RegAsynctask extends AsyncTask<String, Process, Integer> {

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result == 1) {
				PromptManager.showMyToast(RegActivity.this,
						getString(R.string.reg_success));
				RegActivity.this.finish();
			}
			if (result == 0) {

				PromptManager.showMyToast(RegActivity.this,
						getString(R.string.reg_failure));
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(RegActivity.this);
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {
				UserInfo u = new UserInfo();
				u.setUserName(et_username.getText().toString().trim());
				u.setUserPwd(ed_password.getText().toString().trim());
				//------------------------------------
				u.setTel(ed_tel_reg.getText().toString().trim());
				u.setCVipNo(ed_cardid_reg.getText().toString().trim());
				//------------------------------------
				List<UserInfo> userlist = new ArrayList<UserInfo>();
				userlist.add(u);
				Gson gson = new Gson();
				String userlistJson = gson.toJson(userlist);
				String msg = "*AIS|RQ|RG01|00|" + userlistJson + "|AIE#";
				PromptManager.showLogTest(
						"testLoginAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url",
						getApplicationContext().getString(R.string.url_login));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_login,
						RegActivity.this, map);

				String result = NetUtil.post(vo);//*AIS|RQ|RG01|00|Err|AIE#//*AIS|RS|RG01|02|[{"UserNo":"*AIS|RQ|RG01|00|[{\"CVipNo\":\"66\",\"Credit","UserPwd":"88","CVipNo":"66","UserName":"22","Tel":"33","OCurValue":0,"PosCurValue":0,"Credit_rating":"0"}]|AIE#
				//*AIS|RS|RG01|02|[{"UserNo":"201601111504071850","UserPwd":"5678","CVipNo":"710900999000","UserName":"5858","Tel":"1398563214","OCurValue":0,"PosCurValue":0,"Credit_rating":""}]|AIE#
//01没有优惠券02注册赠优惠券
				if (result == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					String[] temp = NetUtil.split(result, "|");
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "RG01".equals(temp[2])) {
//						PromptManager
//								.showLogTest(
//										"DaleiAsynTaskdoInBackground-ArrayList<GroupType1>",
//										temp[4]);
//						ArrayList<UserInfo> userList = gson.fromJson(temp[4],
//								new TypeToken<List<UserInfo>>() {
//								}.getType());
//						ConstantValue.userinfo = userList.get(0);
//						if (ConstantValue.userinfo != null) {
//							//保持本地状态
//							SharedPreferences sp = getSharedPreferences("ipcfs", MODE_PRIVATE);
//							Editor edit = sp.edit();
//							edit.putString("uinfo", temp[4]);
//							return 1;
//						}
						if ("01".equals(temp[3])||"02".equals(temp[3])) {
							return 1;
						}
						
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return 0;
		}

	}

	private class MyOnClickListner implements OnClickListener {
		Intent intent;

		@Override
		public void onClick(View v) {
			int _ViewID = v.getId();
			switch (_ViewID) {
			// 点击回退按钮
			case R.id.iv_back:
				RegActivity.this.finish();
				break;
			// 点击注册按钮
			case R.id.bt_reg:
				final String name = et_username.getText().toString().trim();
				final String pwd = ed_password.getText().toString().trim();

				if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
					PromptManager.showMyToast(RegActivity.this,
							getString(R.string.no_empty_user_password));
					break;
				} else if (NetUtil.hasNetwork(RegActivity.this)) {
					new RegAsynctask().execute("");
					break;
				}

			}
		}
	}

}
