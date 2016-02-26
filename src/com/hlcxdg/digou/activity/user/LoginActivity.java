package com.hlcxdg.digou.activity.user;

/*
 * 登录界面
 * 
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
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
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class LoginActivity extends Activity {
	private EditText et_username;
	private Button bt_login;
	private Button bt_reg;
	private ImageView iv_back;
	private EditText ed_password;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login);
		initView();

	}

	public void initView() {
		bt_login = (Button) findViewById(R.id.bt_login);
		bt_reg = (Button) findViewById(R.id.bt_reg);
		et_username = (EditText) findViewById(R.id.et_username);
		ed_password = (EditText) findViewById(R.id.ed_password);
		iv_back = (ImageView) findViewById(R.id.iv_back);
		bt_login.setOnClickListener(new MyOnClickListner());
		bt_reg.setOnClickListener(new MyOnClickListner());
		iv_back.setOnClickListener(new MyOnClickListner());
	}

	public class LoginAsyncTask extends AsyncTask<String, ProgressBar, Integer> {

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result == 1) {
				PromptManager.showMyToast(LoginActivity.this, "登录成功");
				LoginActivity.this.setResult(2001);
				LoginActivity.this.finish();
			}
			if (result == 0) {
				PromptManager.showMyToast(LoginActivity.this, "登录失败");
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(LoginActivity.this);
		}

		@Override
		protected Integer doInBackground(String... arg0) {

			try {
				UserInfo u = new UserInfo();
				u.setUserName(et_username.getText().toString().trim());
				u.setUserPwd(ed_password.getText().toString().trim());
				List<UserInfo> userlist = new ArrayList<UserInfo>();
				userlist.add(u);
				Gson gson = new Gson();
				String userlistJson = gson.toJson(userlist);
				String msg = "*AIS|RQ|LG01|00|" + userlistJson + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", LoginActivity.this.getString(R.string.url_login));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_login,
						LoginActivity.this, map);

				String result = NetUtil.post(vo);
				if (result != null) {

					String[] temp = NetUtil.split(result, "|");
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "LG01".equals(temp[2])
							&& "01".equals(temp[3])) {
						UserInfo usertmp = gson.fromJson(temp[4],
								UserInfo.class);
						SharedPreferences sp = getSharedPreferences("ipcfs", MODE_PRIVATE);
						Editor edit = sp.edit();
						edit.putString("uinfo", temp[4]);
						ConstantValue.userinfo = usertmp;
						return 1;
					}
				}
			} catch (Exception e) {
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
			case R.id.iv_back:
				LoginActivity.this.setResult(2000);
				LoginActivity.this.finish();

				break;

			case R.id.bt_login:
				// 登陆
				final String name = et_username.getText().toString().trim();
				final String pwd = ed_password.getText().toString().trim();
				if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd)) {
					PromptManager.showMyToast(LoginActivity.this, "用户名，密码不能为空");
					break;
				} else if (!NetUtil.hasNetwork(LoginActivity.this)) {
					break;
				} else {
					new LoginAsyncTask().execute("");
				}

				break;
			case R.id.bt_reg:
				// 注册
				intent = new Intent();
				intent.setClass(LoginActivity.this, RegActivity.class);
				startActivity(intent);
//				LoginActivity.this.finish();
				break;

			}
		}
	}

}
