package com.hlcxdg.digou.activity.wallet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.wallet.WalletPPwdActivity.UpPPAsynctask;
import com.hlcxdg.digou.activity.wallet.bean.PPwd;
import com.hlcxdg.digou.activity.wallet.bean.PaymentPara;
import com.hlcxdg.digou.bean.UserInfo;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.net.NetUtilWallet;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class WalletZhifuActivity extends Activity {
	private Context context;
	private Spinner w_zhifu_spinner;
	private EditText w_zhifu_tousername;
	private EditText w_zhifu_tomoney;
	private EditText w_zhifu_myzhipass;
	private ImageView w_zhi_iv_back;
	private Button w_zhi_cancel_btn;
	private Button w_zhi_ok_btn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wallet_zhifu);
		context = WalletZhifuActivity.this;
//		new WZhifuAsynctask().execute("");
	}

	public class WZhifuAsynctask extends AsyncTask<String, Process, Integer> {

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result == 1) {//
				PromptManager.showMyToast(context, "成功");

			}
			if (result == 0) {//

				PromptManager.showMyToast(context, "失败");
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
				PaymentPara pp = new PaymentPara();

				List<PaymentPara> buyerRlist = new ArrayList<PaymentPara>();
				buyerRlist.add(pp);
				Gson gson = new Gson();
				String userlistJson = gson.toJson(buyerRlist);
				String msg = "*AIS|RQ|WPay|00|" + userlistJson + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url",
						context.getString(
								R.string.servlet_walletpay));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_walletpay, context,
						map);

				String result = NetUtilWallet.post(vo);// *AIS|RS|WPay|01|Sucess|AIE#
				if (result == null) {
					return -1;
				} else {
					String[] temp = NetUtil.split(result, "|");
					if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
							&& "RG01".equals(temp[2])) {
						PromptManager
								.showLogTest(
										"DaleiAsynTaskdoInBackground-ArrayList<GroupType1>",
										temp[4]);
						ArrayList<UserInfo> userList = gson.fromJson(temp[4],
								new TypeToken<List<UserInfo>>() {
								}.getType());
						ConstantValue.userinfo = userList.get(0);
						if (ConstantValue.userinfo != null) {
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
}
