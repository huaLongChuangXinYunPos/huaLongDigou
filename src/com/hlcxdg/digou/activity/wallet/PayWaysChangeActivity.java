package com.hlcxdg.digou.activity.wallet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alipay.sdk.pay.demo.PayZhiActivity;
import com.baidu.navisdk.comapi.mapcontrol.MapParams.Const;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.MainActivity;
import com.hlcxdg.digou.activity.salesheet.SaleSheetMineActivity;
import com.hlcxdg.digou.activity.salesheet.SalesheetActivity;
import com.hlcxdg.digou.activity.wallet.bean.BuyerR;
import com.hlcxdg.digou.bean.OD06;
import com.hlcxdg.digou.bean.SaleSheet;
import com.hlcxdg.digou.bean.StoreItem;
import com.hlcxdg.digou.bean.UserInfo;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.net.NetUtilWallet;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PayWaysChangeActivity extends Activity implements OnClickListener {
	private Context context;
	private Intent myIntent;
	private ImageView w_paywaychange_iv_back;

	private LinearLayout c_payway_zhi_ll_ss;
	private LinearLayout c_payway_wallet_ll_ss;
	private LinearLayout c_payway_daofu_ll_ss;
	private ImageView c_payway_zhi_iv_ss;
	private ImageView c_payway_wallet_iv_ss;
	private ImageView c_payway_daofu_iv_ss;
	private TextView c_payway_wallet_lostmoney_ss;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.payway_change_activity);
		context = PayWaysChangeActivity.this;
		init();

	}

	private void init() {

		c_payway_zhi_ll_ss = (LinearLayout) findViewById(R.id.c_payway_zhi_ll_ss);
		c_payway_zhi_ll_ss.setOnClickListener(this);
		c_payway_wallet_ll_ss = (LinearLayout) findViewById(R.id.c_payway_wallet_ll_ss);
		c_payway_wallet_ll_ss.setOnClickListener(this);
		c_payway_daofu_ll_ss = (LinearLayout) findViewById(R.id.c_payway_daofu_ll_ss);
		c_payway_daofu_ll_ss.setOnClickListener(this);
		c_payway_zhi_iv_ss = (ImageView) findViewById(R.id.c_payway_zhi_iv_ss);
		c_payway_wallet_iv_ss = (ImageView) findViewById(R.id.c_payway_wallet_iv_ss);
		c_payway_daofu_iv_ss = (ImageView) findViewById(R.id.c_payway_daofu_iv_ss);
		c_payway_wallet_lostmoney_ss = (TextView) findViewById(R.id.c_payway_wallet_lostmoney_ss);
		if (ConstantValue.lostWMoney != null && ConstantValue.wBuyerR != null) {
			c_payway_wallet_lostmoney_ss.setText("余额:￥"
					+ ConstantValue.lostWMoney.setScale(2,
							BigDecimal.ROUND_HALF_UP));
		}
		w_paywaychange_iv_back = (ImageView) findViewById(R.id.w_paywaychange_iv_back);
		w_paywaychange_iv_back.setOnClickListener(this);

	}

	public class Daofu extends AsyncTask<Context, ProgressBar, Boolean> {
		Context context;
		ListView list;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
				PromptManager.showMyToast(context, "提交失败");
			} else {
				PromptManager.showMyToast(context, "提交成功");
				startNextActivity2();
			}

		}

		public void startNextActivity2() {

			finish();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(ProgressBar... values) {
			super.onProgressUpdate(values);

		}

		@Override
		protected Boolean doInBackground(Context[] arg0) {
			this.context = arg0[0];
			try {
				Boolean network = NetUtil.hasNetwork(context);
				if (!network) {
					return false;
				}
				PromptManager.showLogTest("SalesheetActivity",
						"doInBackground-yuwang");
				String plno = "00";
				Gson g = new Gson();

				for (int i = 0; i < ConstantValue.cartSaleSheetList.size(); i++) {
					SaleSheet ss = ConstantValue.cartSaleSheetList.get(i);
					ss.setAddressId(ConstantValue.myaddr.getAddress_id());
					ss.setSaleSheetDetailStr();
					switch (3) {
					case 1:// 未支付
						ss.setStat_Id("00");
						break;
					case 2:// 未支付
						ss.setStat_Id("00");
						break;
					case 3:// 到付
						ss.setStat_Id("01");
						break;
					default:
						break;
					}
				}
				// String jsons = g.toJson(ConstantValue.myss);
				OD06 od = new OD06();
				od.setcSaleSheetno(ConstantValue.saleSheetNoForZhifubao[0]);
				od.setUserNo(ConstantValue.userinfo.getUserNo());// ConstantValue.saleSheetNoForZhifubao[0]
				String jsons = g.toJson(od);
				String msg = "*AIS|RQ|OD06|" + plno + "|[" + jsons + "]|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_od01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_od01, context, map);
				String result = NetUtil.post(vo);
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else if ("*AIS".equalsIgnoreCase(temp[0])
						&& "RS".equals(temp[1])
						&& "OD06".equals(temp[2])
						&& "01".equals(temp[3])) {
					// String salesheetstr = temp[4];
					// String[] salesheetNo = NetUtil.split(salesheetstr, ",");
					// ConstantValue.saleSheetNoForZhifubao = salesheetNo;
					// ConstantValue.cstoreNo = ConstantValue.cartSaleSheetList
					// .get(0).getcStoreNo();
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return false;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.w_paywaychange_iv_back:
			finish();
			break;

		case R.id.c_payway_zhi_ll_ss:
			// 进行注册
			myIntent = new Intent(context, PayZhiActivity.class);
			startActivity(myIntent);
			break;
		case R.id.c_payway_wallet_ll_ss:
			// 进行注册
			myIntent = new Intent(context, OutWalletZhifuActivity.class);
			startActivity(myIntent);
			break;
		case R.id.c_payway_daofu_ll_ss:
			// 进行注册
			new Daofu().execute(context);
			break;

		default:
			break;
		}
	}

}
