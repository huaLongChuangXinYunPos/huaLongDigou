package com.hl_zhaoq.digou.activity.wallet;

import java.math.BigDecimal;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.InitResultListener;
import com.example.digou.rsa.Base64;
import com.example.digou.rsa.RSAEncrypt;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.salesheet.SaleSheetMineActivity;
import com.hl_zhaoq.digou.activity.wallet.OutWalletZhifuActivity.GetKeys1;
import com.hl_zhaoq.digou.activity.wallet.bean.BuyerR;
import com.hl_zhaoq.digou.activity.wallet.bean.GetKeyParam;
import com.hl_zhaoq.digou.activity.wallet.bean.PaymentPara;
import com.hl_zhaoq.digou.activity.wallet.bean.QueryLost;
import com.hl_zhaoq.digou.activity.wallet.bean.Transfer;
import com.hl_zhaoq.digou.activity.wallet.bean.WPriKey;
import com.hl_zhaoq.digou.activity.wallet.bean.WPubKey;
import com.hl_zhaoq.digou.activity.wallet.bean.WTransferCertify;
import com.hl_zhaoq.digou.activity.wallet.bean.WTransferCertifyPara;
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
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ZhuanzhangActivity extends Activity implements OnClickListener {
	private Context context;
	private Spinner w_zhifu_spinner;
	private EditText w_zhuanzhang_out_account_tel;
	private EditText w_zhuanzhang_money_et;
	private EditText w_zhuanzhang_paypwd_et;
	private ImageView w_zhuanzhang_iv_back;
	private Button w_zhuanzhang_cancel_btn;
	private Button w_zhuanzhang_ok_btn;
	private Button w_zhuanzhang_certify_btn;
	private SharedPreferences sp;
	private TextView w_zhuanzhang_realname_et;
	WTransferCertify in_info = new WTransferCertify();
	private String privateKey;
	private String publicKey;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wallet_zhuanzhang);
		context = ZhuanzhangActivity.this;
		sp = getSharedPreferences("mybuyerR", MODE_PRIVATE);
		init();
		new GetKeys1().execute("");
	}
	public class GetKeys1 extends AsyncTask<String, Process, Integer> {
		String pwd = "";

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			if (result == 1) {//
			}
			if (result == 0) {//
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected Integer doInBackground(String... arg0) {

			try {
				GetKeyParam pp = new GetKeyParam();
				pp.setWServerID("1");
				pp.setcStoreNo("0");
				String BuyerName = sp.getString("BuyerName", "");
				pp.setBuyerName(BuyerName);
				List<GetKeyParam> buyerRlist = new ArrayList<GetKeyParam>();
				buyerRlist.add(pp);
				Gson gson = new Gson();
				String userlistJson = gson.toJson(buyerRlist);
				String msg = "*AIS|RQ|WPubKey|00|" + userlistJson + "|AIE#";

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.wallet_rsa));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.wallet_rsa, context, map);

				String result = NetUtilWallet.post(vo);//
				// *AIS|RS|WPay|01|Sucess|AIE#//
				if (result == null) {
					return -1;
				} else {
					String[] temp = NetUtil.split(result, "|");
					if ("01".equals(temp[3])) {
						ArrayList<WPubKey> tmpStlist = gson.fromJson(temp[4],
								new TypeToken<List<WPubKey>>() {
								}.getType());
						if (null != tmpStlist && tmpStlist.size() > 0) {

							Editor ed = sp.edit();
							ed.putString("WServerID", tmpStlist.get(0)
									.getWServerID().trim());
							ed.putString("WServerPubKey", tmpStlist.get(0)
									.getWServerPubKey());
							publicKey = tmpStlist.get(0).getWServerPubKey();
							ed.putString("WUpdateTime", tmpStlist.get(0)
									.getWUpdateTime());
							ed.commit();
						}

						msg = "*AIS|RQ|WPriKey|00|" + userlistJson + "|AIE#";

						map = new HashMap<String, String>();
						map.put("type", "post");
						map.put("url", context.getString(R.string.wallet_rsa));
						map.put("dataType", "text");
						map.put("data", msg);
						vo = new RequestVo(R.string.wallet_rsa, context, map);

						result = NetUtilWallet.post(vo);//
						// *AIS|RS|WPay|01|Sucess|AIE#
						if (result == null) {
							return -1;
						} else {
							String[] temp2 = NetUtil.split(result, "|");
							if ("01".equals(temp2[3])) {
								ArrayList<WPriKey> tmlist = gson.fromJson(temp2[4],
										new TypeToken<List<WPriKey>>() {
										}.getType());

								if (null != tmlist && tmlist.size() > 0) {

									Editor ed = sp.edit();
									ed.putString("WServerID", tmlist.get(0)
											.getWServerID().trim());
									ed.putString("WPriKey", tmlist.get(0)
											.getWStorePriKey());
									privateKey = tmlist.get(0).getWStorePriKey();
									// System.out.println(privateKey);
									ed.putString("WUpdateTime", tmlist.get(0)
											.getWUpdateTime());
									ed.commit();
								}

								return 1;
							}
							return 0;
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
	}

	private void init() {
		w_zhuanzhang_iv_back = (ImageView) findViewById(R.id.w_zhuanzhang_iv_back);
		w_zhuanzhang_iv_back.setOnClickListener(this);
		w_zhuanzhang_cancel_btn = (Button) findViewById(R.id.w_zhuanzhang_cancel_btn);
		w_zhuanzhang_cancel_btn.setOnClickListener(this);
		w_zhuanzhang_ok_btn = (Button) findViewById(R.id.w_zhuanzhang_ok_btn);
		w_zhuanzhang_ok_btn.setOnClickListener(this);
		w_zhuanzhang_certify_btn = (Button) findViewById(R.id.w_zhuanzhang_certify_btn);
		w_zhuanzhang_certify_btn.setOnClickListener(this);
		w_zhuanzhang_realname_et = (TextView) findViewById(R.id.w_zhuanzhang_realname_et);
		w_zhuanzhang_money_et = (EditText) findViewById(R.id.w_zhuanzhang_money_et);
		w_zhuanzhang_paypwd_et = (EditText) findViewById(R.id.w_zhuanzhang_paypwd_et);

		w_zhuanzhang_out_account_tel = (EditText) findViewById(R.id.w_zhuanzhang_out_account_tel);
//	
	}
	public class GetLostMoney extends AsyncTask<String, Process, Integer> {

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			PromptManager.showMyToast(context, "转账成功");
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		
		}

		@Override
		protected Integer doInBackground(String... arg0) {

			try {
				List<BuyerR> buyerRlist = new ArrayList<BuyerR>();
				buyerRlist.add(ConstantValue.wBuyerR);
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
	public class WZhuanzhangAsynctask extends
			AsyncTask<String, Process, Integer> {
		String money;
		String paypwd;

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result == 1) {//
				
				new GetLostMoney().execute("");
			}
			if (result == 0) {//
				PromptManager.showMyToast(context, "转账失败");
			}
		}

		public String getOutTradeNo(String tel) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss",
					Locale.getDefault());
			Date date = new Date();
			String key = format.format(date);
			key = key + tel;
			return key;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
			money = w_zhuanzhang_money_et.getText().toString();
			paypwd = w_zhuanzhang_paypwd_et.getText().toString();
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {
				Transfer tf = new Transfer();
				String Buyer_id = sp.getString("Buyer_id", "");
				String BuyerName = sp.getString("BuyerName", "");
				String Tel = sp.getString("Tel", "");
				tf.setIn_Account(Tel);
				tf.setIn_ID(in_info.getBuyer_id());
				tf.setIn_Name(in_info.getBuyerName());
				tf.setWTransferNo(getOutTradeNo(Tel));
				tf.setOut_Account(Tel);
				tf.setOut_ID(Buyer_id);
				tf.setOut_Name(BuyerName);
				tf.setWTransfer_Money(new BigDecimal(money));
				
				
				byte[] bt = paypwd.getBytes();
				System.out.println(publicKey);
				RSAPublicKey mRSAPublicKey = RSAEncrypt
						.loadPublicKeyByStr(publicKey);
				byte[] btresult = RSAEncrypt.encrypt(mRSAPublicKey, bt);
				String mypwd2 = Base64.encode(btresult);
				
				
				
				
				
				tf.setPayPwd(mypwd2);

				List<Transfer> buyerRlist = new ArrayList<Transfer>();
				buyerRlist.add(tf);
				Gson gson = new Gson();
				String userlistJson = gson.toJson(buyerRlist);
				String msg = "*AIS|RQ|WTransfer|00|" + userlistJson + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.wallet_wtransfer));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.wallet_wtransfer,
						context, map);

				String result = NetUtilWallet.post(vo);// *AIS|RS|WPay|01|Sucess|AIE#
				if (result == null) {
					return -1;
				} else {
					byte[] byteMyResult = Base64.decode(result);
					byte[] myRes = RSAEncrypt.decrypt(mRSAPublicKey,
							byteMyResult);
					String rr = new String(myRes);

					String[] temp = NetUtil.split(rr, "|");
//					String[] temp = NetUtil.split(result, "|");
					if ("01".equals(temp[3])) {
						return 1;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}

	}

	public class GetOutInfosAsynctask extends
			AsyncTask<String, Process, Integer> {
		String tel;
		String realname;

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result == 1) {
				w_zhuanzhang_realname_et.setText(realname);
			}
			if (result == 0) {
				PromptManager.showMyToast(context, "没有该用户");
			}
			if (result == -1) {
				PromptManager.showMyToast(context, "网络不给力");
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
			tel = w_zhuanzhang_out_account_tel.getText().toString();
		}

		@Override
		protected Integer doInBackground(String... arg0) {

			try {
				WTransferCertifyPara w = new WTransferCertifyPara("1", tel);
				List<WTransferCertifyPara> buyerRlist = new ArrayList<WTransferCertifyPara>();
				buyerRlist.add(w);
				Gson gson = new Gson();
				String userlistJson = gson.toJson(buyerRlist);
				String msg = "*AIS|RQ|WTransferCertify|00|" + userlistJson
						+ "|AIE#";

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.wallet_wtransfer));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.wallet_wtransfer,
						context, map);

				String result = NetUtilWallet.post(vo);
				if (result == null) {
					return -1;
				} else {
					String[] temp = NetUtil.split(result, "|");
					if ("01".equalsIgnoreCase(temp[3])) {//
						ArrayList<WTransferCertify> tmpStlist = gson.fromJson(
								temp[4],
								new TypeToken<List<WTransferCertify>>() {
								}.getType());
						in_info = tmpStlist.get(0);
						realname = in_info.getcRealName();
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

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.w_zhuanzhang_cancel_btn:
		case R.id.w_zhuanzhang_iv_back:
			finish();
			break;

		case R.id.w_zhuanzhang_certify_btn:
			try {
				new GetOutInfosAsynctask().execute("");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case R.id.w_zhuanzhang_ok_btn:
			try {
				if (TextUtils.isEmpty(w_zhuanzhang_money_et.getText()
						.toString())) {
					PromptManager.showMyToast(context, "用户名不能为空");
					break;
				}
				if (TextUtils.isEmpty(w_zhuanzhang_paypwd_et.getText()
						.toString())) {
					PromptManager.showMyToast(context, "用户密码不能为空");
					break;
				}

				new WZhuanzhangAsynctask().execute("");
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}

	}
}
