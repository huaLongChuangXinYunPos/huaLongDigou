package com.hl_zhaoq.digou.tel.activity;

import com.hl_zhaoq.digou.tel.cst.ConstantTel;
import com.hl_zhaoq.digou.R;

import vivolibrary.QueryBalance;
import vivolibrary.SoftwareVerify;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class TonghuaActivity extends Activity {
	private TextView tel_tonghua;
	public final static String TAG = "TonghuaActivity";
	TelephonyManager telephony ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tel_tonghua);
		tel_tonghua = (TextView) findViewById(R.id.tel_tonghua);
		if (!TextUtils.isEmpty(ConstantTel.beijiaonum)) {
			tel_tonghua.setText(ConstantTel.beijiaonum);
		}
		telephony = (TelephonyManager) getSystemService(this.TELEPHONY_SERVICE);
		telephony.listen(new OnePhoneStateListener(),
				PhoneStateListener.LISTEN_CALL_STATE);

	}

	/**
	 * �绰״̬����.
	 * 
	 * @author stephen
	 * 
	 */
	class OnePhoneStateListener extends PhoneStateListener {
		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			switch (state) {
			case TelephonyManager.CALL_STATE_RINGING:
				TonghuaActivity.this.finish();
				break;
			case TelephonyManager.CALL_STATE_IDLE:
				break;
			case TelephonyManager.CALL_STATE_OFFHOOK:
				break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}
	}
}