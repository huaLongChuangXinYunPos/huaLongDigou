package com.hl_zhaoq.digou.activity.wallet;

import java.math.BigDecimal;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.alipay.sdk.pay.demo.PayZhiActivity;
import com.alipay.sdk.pay.demo.PayZhiActivity.PA01And02AsynTask;
import com.example.digou.rsa.Base64;
import com.example.digou.rsa.RSAEncrypt;
import com.example.digou.rsa.RSASignature;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.salesheet.SaleSheetMineActivity;
import com.hl_zhaoq.digou.activity.wallet.bean.BuyerR;
import com.hl_zhaoq.digou.activity.wallet.bean.GetKeyParam;
import com.hl_zhaoq.digou.activity.wallet.bean.PaymentPara;
import com.hl_zhaoq.digou.activity.wallet.bean.QueryLost;
import com.hl_zhaoq.digou.activity.wallet.bean.WPriKey;
import com.hl_zhaoq.digou.activity.wallet.bean.WPubKey;
import com.hl_zhaoq.digou.bean.PAXiangdan;
import com.hl_zhaoq.digou.bean.PAZongdan;
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
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class OutWalletZhifuActivity extends Activity implements OnClickListener {
	private Context context;
	SharedPreferences sp;
	private EditText passwordInputView2;
	private ImageView w_payfor_iv_back;
	private Button w_payfor_ok_btn;
	private String privateKey;
	private String publicKey;

	// private String
	// tmpprivateKey="MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAm8/Dn+cvlF+2etk4+rlnoSzimygdXdGoKOXn2K5smW9rn06LI7bHHFyfM5sIBCz07UKG+gtodk1LxlHt2vCRMQIDAQABAkBflCGCk4SiVdxQm7PSSa2CN1CCsSbiiFvJSs7gIo9HxxUxsBYL6jCvkANBc3KFnKTYsu4Vymrv2O6kUlLcxxBhAiEA3dmoVWjBcHOkrcZexAQKguxketYSQHQ6j7PrMKYwWPUCIQCzy8EVeIcLLR1AD2t9Xjfm69ET100eNnFyY1FV6aLhzQIhAMX33uEoc8XXTFLNu+8K9B5UuG1s7iiaD1AA65UkF+ypAiEAlpsZvRN+A3Wh59RC1PxEVXG5bCZQWft+4jOhoRaZMw0CIQCFbkeY/0PfCeXPEaGINR4ALyd6UbNkERs/tDh75fQtIw==";
	// private String
	// tmppublicKey="MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAJvPw5/nL5RftnrZOPq5Z6Es4psoHV3RqCjl59iubJlva59OiyO2xxxcnzObCAQs9O1ChvoLaHZNS8ZR7drwkTECAwEAAQ==";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.wallet_payfor);
		context = OutWalletZhifuActivity.this;
		init();
		sp = getSharedPreferences("mybuyerR", MODE_PRIVATE);
		new GetKeys1().execute("");
	}

	public class GetKeys extends AsyncTask<String, Process, Integer> {
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
				pp.setcStoreNo(ConstantValue.cstoreNo);
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

						msg = "*AIS|RQ|WSPriKey|00|" + userlistJson + "|AIE#";

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
								ArrayList<WPriKey> tmlist = gson.fromJson(
										temp2[4],
										new TypeToken<List<WPriKey>>() {
										}.getType());

								if (null != tmlist && tmlist.size() > 0) {

									Editor ed = sp.edit();
									ed.putString("WServerID", tmlist.get(0)
											.getWServerID().trim());
									ed.putString("WPriKey", tmlist.get(0)
											.getWServerPriKey());
									privateKey = tmlist.get(0)
											.getWServerPriKey();
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
		w_payfor_ok_btn = (Button) findViewById(R.id.w_payfor_ok_btn);
		w_payfor_ok_btn.setOnClickListener(this);
		w_payfor_iv_back = (ImageView) findViewById(R.id.w_payfor_iv_back);
		w_payfor_iv_back.setOnClickListener(this);
		passwordInputView2 = (EditText) findViewById(R.id.w_payforpassword_text);
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
				pp.setcStoreNo(ConstantValue.myss.getcStoreNo());
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
								ArrayList<WPriKey> tmlist = gson.fromJson(
										temp2[4],
										new TypeToken<List<WPriKey>>() {
										}.getType());

								if (null != tmlist && tmlist.size() > 0) {

									Editor ed = sp.edit();
									ed.putString("WServerID", tmlist.get(0)
											.getWServerID().trim());
									ed.putString("WPriKey", tmlist.get(0)
											.getWStorePriKey());
									privateKey = tmlist.get(0)
											.getWStorePriKey();
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

	public class PA01And02AsynTask extends
			AsyncTask<Context, ProgressBar, Integer> {
		Context context;

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			new GetLostMoney().execute("");
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(context);
				break;
			case 0:
				break;
			case 1:
				OutWalletZhifuActivity.this.setResult(1);
				OutWalletZhifuActivity.this.finish();
				break;
			default:
				break;
			}
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
		protected Integer doInBackground(Context[] cont) {
			this.context = cont[0];
			try {
				Boolean network = NetUtil.hasNetwork(context);
				if (!network) {
					return -1;
				}
				List<PAZongdan> pa01List = new ArrayList<PAZongdan>();
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String datenow = sdf.format(d);
				PAZongdan paZongdan = new PAZongdan();
				paZongdan.setcSaleSheetNo(ConstantValue.myss.getcSaleSheetno());
				paZongdan.setcPayTime(datenow);
				paZongdan.setfPayMoney(ConstantValue.myss.getFLastMoney());
				pa01List.add(paZongdan);
				Gson gson = new Gson();
				String strgson = gson.toJson(pa01List);
				String msg = "*AIS|RQ|PA01|00|" + strgson + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_paysheet));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_paysheet,
						context, map);

//				String result = NetUtil.post(vo);
				String result = "*AIS|RS|PA01|01|20150929134605_20150809121207kk|AIE#";
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else if ("*AIS".equalsIgnoreCase(temp[0])
						&& "RS".equals(temp[1]) && "PA01".equals(temp[2])
						&& "01".equals(temp[3])) {//
					// 村上付款*AIS|RS|PA01|01|20150929134605_20150809121207kk|AIE#
					List<PAXiangdan> pa02List = new ArrayList<PAXiangdan>();

					PAXiangdan paXiangdan = new PAXiangdan();
					paXiangdan.setcPayTime(datenow);
					paXiangdan
							.setcSaleSheetNo(ConstantValue.myss.getcSaleSheetno());
					paXiangdan.setfPayMoney(ConstantValue.myss.getFLastMoney());
					paXiangdan.setIlineNo("1");
					paXiangdan.setcPayStyleNo("01");
					paXiangdan.setcPayStyleDetail("");// 卡号或编号
					pa02List.add(paXiangdan);

					strgson = gson.toJson(pa02List);
					msg = "*AIS|RQ|PA02|00|" + strgson + "|AIE#";
					PromptManager.showLogTest(
							"PLAsynTaskdoInBackground-postmesssage", msg);
					map = new HashMap<String, String>();
					map.put("type", "post");
					map.put("url",
							context.getString(R.string.servlet_paysheetdetail));
					map.put("dataType", "text");
					map.put("data", msg);
					vo = new RequestVo(R.string.servlet_paysheetdetail,
							context, map);
					result = NetUtil.post(vo);
					temp = NetUtil.split(result, "|");
					if (temp == null) {
						PromptManager.showLogTest("enginenet",
								"net-result-null");
					} else

					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "PA02".equals(temp[2])
							&& "01".equals(temp[3])) {// &&
						// "01".equals(temp[3])
						// *AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#*AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#*AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#
						return 1;
					}
				}
			} catch (Exception e) {
				return 0;
			}

			return 0;
		}
	}

	public class GetLostMoney extends AsyncTask<String, Process, Integer> {

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			Intent intent;
			intent = new Intent();
			intent.setClass(context, SaleSheetMineActivity.class);
			context.startActivity(intent);
			finish();
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

	public class WZhifuAsynctask extends AsyncTask<String, Process, Integer> {
		String pwd = "";
		String resultMsg = "";

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result == 1) {//
				PromptManager.showMyToast(context, "支付成功");

				new PA01And02AsynTask().execute(context);
				ConstantValue.total_Nums_cart = 0;
				ConstantValue.total_price_cart = new BigDecimal(0);
			} else {//

				PromptManager.showMyToast(context, "支付失败,");
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pwd = passwordInputView2.getText().toString();
			PromptManager.showSpotsDialog(context);
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {
				PaymentPara pp = new PaymentPara();
				byte[] bt = pwd.getBytes();
				// System.out.println(publicKey);
				RSAPublicKey mRSAPublicKey = RSAEncrypt
						.loadPublicKeyByStr(publicKey);
				byte[] btresult = RSAEncrypt.encrypt(mRSAPublicKey, bt);
				String mypwd2 = Base64.encode(btresult);
				pp.setPayPwd(mypwd2);
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String datenow = sdf.format(d);
				pp.setT_createtime(datenow);
				pp.setT_paytime(datenow);
				String Buyer_id = sp.getString("Buyer_id", "");
				String BuyerName = sp.getString("BuyerName", "");
				pp.setBuyer_id(Buyer_id);
				pp.setcStoreNo(ConstantValue.myss.getcStoreNo());
				pp.setfLastMoney(ConstantValue.myss.getFLastMoney());
				pp.setcSaleSheetNo(ConstantValue.myss.getcSaleSheetno());
				List<PaymentPara> buyerRlist = new ArrayList<PaymentPara>();
				buyerRlist.add(pp);
				privateKey = sp.getString("WPriKey", "rsa");
				Gson gson = new Gson();
				String userlistJson = gson.toJson(buyerRlist);
				String msg = "*AIS|RQ|WPay|00|" + userlistJson + "|AIE#";
				String msgSign = "";
				// System.out.println(pwd);
				msgSign = RSASignature.sign(msg, privateKey);

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_walletpay));
				map.put("dataType", "text");
				map.put("data", msg);
				map.put("sign", msgSign);
				RequestVo vo = new RequestVo(R.string.servlet_walletpay,
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
					if ("01".equals(temp[3])) {
						resultMsg = temp[4];
						return 1;
					} else {
						resultMsg = temp[4];
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
	public void onBackPressed() {
		super.onBackPressed();
//		toMyOrderActivity();
	}

	private void toMyOrderActivity() {
//		Intent intent;
//		intent = new Intent();
//		intent.setClass(context, SaleSheetMineActivity.class);
//		startActivity(intent);
		finish();
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.w_payfor_iv_back:
//			toMyOrderActivity();
			finish();
			break;
		case R.id.w_payfor_ok_btn:
			//
			new WZhifuAsynctask().execute("");
			break;

		default:
			break;
		}
	}
}
