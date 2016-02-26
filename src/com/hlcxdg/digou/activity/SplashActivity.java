package com.hlcxdg.digou.activity;

import com.hlcxdg.digou.R;
import com.hlcxdg.digou.constant.ConstantIP;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SplashActivity extends Activity {
	private Button splash_bt_in;
	EditText hlip;
	EditText hlport;
	Button ok;
	Button cancel;
	SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		sp = getSharedPreferences("ipcfs", MODE_PRIVATE);
		initView();

		initListener();
		NetUtil.hasNetwork(SplashActivity.this);
	}

	private AlertDialog dialog;
//ipcofig按钮
	public void setting_net(View v) {
		AlertDialog.Builder builder = new Builder(SplashActivity.this);
		// �Զ���һ�������ļ�
		View view = View.inflate(SplashActivity.this,
				R.layout.splash_activity_dialog, null);
		String tmpHLip = sp.getString("HLIP", "");
		String tmpHLport = sp.getString("HLPORT", "");

		hlip = (EditText) view.findViewById(R.id.hl_ip);
		hlport = (EditText) view.findViewById(R.id.hl_port);
		hlip.setText(tmpHLip);
		hlport.setText(tmpHLport);
		ok = (Button) view.findViewById(R.id.ok);
		cancel = (Button) view.findViewById(R.id.cancel);
		dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ������Ի���ȡ����
				dialog.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// ȡ������
				String HLIP = hlip.getText().toString().trim();
				String HLPORT = hlport.getText().toString().trim();
				if (TextUtils.isEmpty(HLIP) || TextUtils.isEmpty(HLPORT)) {
					PromptManager.showMyToast(SplashActivity.this, "不能为空");
					return;
				}

				Editor ed = sp.edit();
				ed.putString("HLIP", HLIP);
				ed.putString("HLPORT", HLPORT);

				//
				ed.commit();

				PromptManager.showMyToast(SplashActivity.this, HLIP + ":"
						+ HLPORT + "绑定成功");
				dialog.dismiss();
			}
		});
	}

	private void initListener() {
		splash_bt_in.setOnClickListener(new MyOnClickListner());
	}

	private void initView() {

		splash_bt_in = (Button) findViewById(R.id.bt_in_hualong_splash);
		hlip = (EditText) findViewById(R.id.et_ip);
		hlport = (EditText) findViewById(R.id.et_port);

	}

	private class MyOnClickListner implements OnClickListener {
		Intent intent;

		@Override
		public void onClick(View v) {
			int _ViewID = v.getId();
			switch (_ViewID) {
			case R.id.bt_in_hualong_splash:
				initURLinfo();

				startMainActivity();
				break;
			}
		}

		private void initURLinfo() {
			ConstantIP.URLHLSevlet = "http://"
					+ sp.getString("HLIP", "114.112.64.110") + ":"
					+ sp.getString("HLPORT", "7080") + "/DOGOServer";
			ConstantIP.ImageviewURL = "http://"
					+ sp.getString("HLIP", "114.112.64.110") + ":"
					+ sp.getString("HLPORT", "7080") + "/O2O";
		}

		private void startMainActivity() {
			intent = new Intent();
			intent.setClass(SplashActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
		}
	}
}
