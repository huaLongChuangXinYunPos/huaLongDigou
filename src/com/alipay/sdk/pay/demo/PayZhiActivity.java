package com.alipay.sdk.pay.demo;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alipay.apmobilesecuritysdk.face.APSecuritySdk.InitResultListener;
import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.address.AddressActivity;
import com.hlcxdg.digou.activity.goods.GoodSingleActivity;
import com.hlcxdg.digou.activity.payway.PaywayPA01;
import com.hlcxdg.digou.activity.payway.PaywayPA01.PA01And02AsynTask;
import com.hlcxdg.digou.activity.salesheet.SaleSheetMineActivity;
import com.hlcxdg.digou.bean.PAXiangdan;
import com.hlcxdg.digou.bean.PAZongdan;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

public class PayZhiActivity extends FragmentActivity implements OnClickListener {

	// 商户PID
	public static final String PARTNER = "20088912078550700";
	// 商户收款账号
	public static final String SELLER = "2429593687@qq.com";
	// 商户私钥，pkcs8格式
	public static final String RSA_PRIVATE = "MIIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAMBXz3twn9UfdFGrPBhfABZhjBcRkfoGvUC1j5xsjdsZYeGZTW2SBR4yHGm0DKGid4anqW8IT4+9O1NccNaDC+gfI4rkNT0cN9PVHyTj6smmu5ufaAy2Ywlzr5zwQGVjYkdJfjkSLzGrQw/mi0dnac1/E9JJKH6WiHmukbwjPAYTAgMBAAECgYAcjayWdZy0pcu9x/7O22OG7wo97nTOOivkLE/ad98DNJxsQjbVWkT6O5QuOPVbFWRLEB6+BezRuNTSxjlWDvQ4ShNo0I4EeYZ7x4aJzgwRi2urcRtGsPOEnVEy0W0GgoV9Yyq0LlcwI+Yhg28b4QQdMs0WkQoOpRjSHY30JjJ5oQJBAPX4/mAbF59M/+bXaEizjRAWZF/HwqTplE7RqzomgnXYJhLcmHDlR+FoHSODxj8m6cb3DN6f3YdIMBpvb5soK9ECQQDILyFYAX2Eaul0Y8BhRxaAJF1AIi0QtLMkWHsMmKohPqqM6NkLS1/GKg+WCzuVCi3fJ0lmM7LlUnbTyJRBziCjAkEAi3b1jDpwR2OaSSHjwC0GE5QOr3wNMgI6lAIv6tXA7N50oAcx6/kkq3qj4uopcs82iBi11Fiie70DgUmj5z58sQJBAKbwTtEyZPcRpWAvc5ZsDwKL0MwT9BrzZlVQCEWH5pDUzqu13pKBp/v8xYSuF2EB3SE0hQxa3rxI2dlhcumxuRsCQQDvZKFlYGf2PEfJ5fxjebjeIGhcJGSEZx/JsR4klwFKKbhHKHLB05Mo+QDl9bFpGXHqruyTohF+k0N9zlEWWgpq";
	// 支付宝公钥
	public static final String RSA_PUBLIC = "MIIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";
	private static final int SDK_PAY_FLAG = 1;
	private static final int SDK_CHECK_FLAG = 2;
	private ImageView iv_back_payzhi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.paybyzhifubao_main);
		init();
	}

	private void init() {
		iv_back_payzhi = (ImageView) findViewById(R.id.iv_back_payzhi);
		iv_back_payzhi.setOnClickListener(this);
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SDK_PAY_FLAG: {
				PayResult payResult = new PayResult((String) msg.obj);
				// 支付宝返回此次支付结果及加签，建议对支付宝签名信息拿签约时支付宝提供的公钥做验签
				String resultInfo = payResult.getResult();
				String resultStatus = payResult.getResultStatus();
				// 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
				if (TextUtils.equals(resultStatus, "9000")) {
					new PA02AsynTask().execute(PayZhiActivity.this);
					ConstantValue.total_Nums_cart = 0;
					ConstantValue.total_price_cart = new BigDecimal(0);

					// Intent intent;
					// intent = new Intent();
					// intent.setClass(PayZhiActivity.this,
					// com.example.digou.activity.salesheet.MyOrderActivity.class);
					// .startActivity(intent);
				} else {
					// 判断resultStatus 为非“9000”则代表可能支付失败
					// “8000”代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
					if (TextUtils.equals(resultStatus, "8000")) {
						Toast.makeText(PayZhiActivity.this, "支付结果确认中",
								Toast.LENGTH_SHORT).show();
					} else {
						// 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
						Toast.makeText(PayZhiActivity.this, "支付失败",
								Toast.LENGTH_SHORT).show();
					}
				}
				break;
			}
			case SDK_CHECK_FLAG: {
				Toast.makeText(PayZhiActivity.this, "检查结果为：" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
			case 999: {
				PromptManager.closeMyProgressDialog();
				PromptManager.showMyToast(getApplicationContext(), "支付宝支付成功");
				new PA02AsynTask().execute(PayZhiActivity.this);
				ConstantValue.total_Nums_cart = 0;
				ConstantValue.total_price_cart = new BigDecimal(0);
				break;
			}
			default:
				break;
			}
		};
	};

	public class PA02AsynTask extends AsyncTask<Context, ProgressBar, Integer> {
		Context context;

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(context);
				break;
			case 0:
				break;
			case 1:
				// Intent intent;
				// intent = new Intent();
				// intent.setClass(PayZhiActivity.this,
				// SaleSheetMineActivity.class);
				// PayZhiActivity.this.startActivity(intent);
				PayZhiActivity.this.setResult(1);
				PayZhiActivity.this.finish();
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
//				List<PAZongdan> pa01List = new ArrayList<PAZongdan>();
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String datenow = sdf.format(d);
//
//				PAZongdan paZongdan = new PAZongdan();
//				paZongdan.setcSaleSheetNo(ConstantValue.myss.getcSaleSheetno());
//				paZongdan.setcPayTime(datenow);
//				paZongdan.setfPayMoney(ConstantValue.myss.getFLastMoney());
//				pa01List.add(paZongdan);
//
				Gson gson = new Gson();
//				String strgson = gson.toJson(pa01List);
//				String msg = "*AIS|RQ|PA01|00|" + strgson + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
//				map.put("type", "post");
//				map.put("url", context.getString(R.string.servlet_paysheet));
//				map.put("dataType", "text");
//				map.put("data", msg);
//				RequestVo vo = new RequestVo(R.string.servlet_paysheet,
//						context, map);
//
//				String result = NetUtil.post(vo);
//				String[] temp = NetUtil.split(result, "|");
//				if (temp == null) {
//					PromptManager.showLogTest("enginenet", "net-result-null");
//				} else if ("*AIS".equalsIgnoreCase(temp[0])
//						&& "RS".equals(temp[1]) && "PA01".equals(temp[2])
//						&& "01".equals(temp[3])) {//
					// 村上付款*AIS|RS|PA01|01|20150929134605_20150809121207kk|AIE#
					List<PAXiangdan> pa02List = new ArrayList<PAXiangdan>();

					PAXiangdan paXiangdan = new PAXiangdan();
					paXiangdan.setcPayTime(datenow);
					paXiangdan.setcSaleSheetNo(ConstantValue.myss
							.getcSaleSheetno());
					paXiangdan.setfPayMoney(ConstantValue.myss.getFLastMoney());
					paXiangdan.setIlineNo("1");
					paXiangdan.setcPayStyleNo("02");
					paXiangdan.setcPayStyleDetail("");// 卡号或编号
					pa02List.add(paXiangdan);

					String strgson = gson.toJson(pa02List);
					String msg = "*AIS|RQ|PA02|00|" + strgson + "|AIE#";
					map = new HashMap<String, String>();
					map.put("type", "post");
					map.put("url",
							context.getString(R.string.servlet_paysheetdetail));
					map.put("dataType", "text");
					map.put("data", msg);
					RequestVo	vo = new RequestVo(R.string.servlet_paysheetdetail,
							context, map);
					String result = NetUtil.post(vo);//*AIS|RS|PA02|01|null|AIE#
					String[] temp = NetUtil.split(result, "|");//*AIS|RS|PA02|01|[{"cSaleSheetNo":"0905491838338-10002","fPayMoney":0.0,"fUnPayMoney":0.0,"fLastMoney":0.0}]|AIE#
					if (temp == null) {
						PromptManager.showLogTest("enginenet",
								"net-result-null");
					} else if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "PA02".equals(temp[2])
							&& "01".equals(temp[3])) {// &&
						// "01".equals(temp[3])
						// *AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#*AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#*AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#
						return 1;
					}
//				}
			} catch (Exception e) {
				return 0;
			}

			return 0;
		}
	}

	public class PA01And02AsynTask extends
			AsyncTask<Context, ProgressBar, Integer> {
		Context context;

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(context);
				break;
			case 0:
				break;
			case 1:
				// Intent intent;
				// intent = new Intent();
				// intent.setClass(PayZhiActivity.this,
				// SaleSheetMineActivity.class);
				// PayZhiActivity.this.startActivity(intent);
				PayZhiActivity.this.finish();
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

				String result = NetUtil.post(vo);
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
					paXiangdan.setcSaleSheetNo(ConstantValue.myss
							.getcSaleSheetno());
					paXiangdan.setfPayMoney(ConstantValue.myss.getFLastMoney());
					paXiangdan.setIlineNo("1");
					paXiangdan.setcPayStyleNo("02");
					paXiangdan.setcPayStyleDetail("");// 卡号或编号
					pa02List.add(paXiangdan);

					strgson = gson.toJson(pa02List);
					msg = "*AIS|RQ|PA02|00|" + strgson + "|AIE#";
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
					} else if ("*AIS".equalsIgnoreCase(temp[0])
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

	/**
	 * call alipay sdk pay. 调用SDK支付
	 * 
	 */
	public void pay(View v) {
		// payon();
		// Toast.makeText(getApplicationContext(), "支付成功", 0).show();
		PromptManager.showMyProgressDialog(PayZhiActivity.this);
		mHandler.sendEmptyMessageDelayed(999, 4000);

	}

	public void payon() {
		if (TextUtils.isEmpty(PARTNER) || TextUtils.isEmpty(RSA_PRIVATE)
				|| TextUtils.isEmpty(SELLER)) {
			new AlertDialog.Builder(this)
					.setTitle("警告")
					.setMessage("需要配置PARTNER | RSA_PRIVATE| SELLER")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								public void onClick(
										DialogInterface dialoginterface, int i) {
									//
									finish();
								}
							}).show();
			return;
		}

		/**
		 * 订单补全rukou
		 * 
		 */
		// 订单
		String orderInfo = getOrderInfo(ConstantValue.saleSheet_dingdan.get(0)
				.getCSaleSheetno(), "商品订单", "0.02");

		// 对订单做RSA 签名
		String sign = sign(orderInfo);
		try {
			// 仅需对sign 做URL编码
			sign = URLEncoder.encode(sign, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		try {
			// 完整的符合支付宝参数规范的订单信息
			final String payInfo = orderInfo + "&sign=\"" + sign + "\"&"
					+ getSignType();

			Runnable payRunnable = new Runnable() {

				@Override
				public void run() {
					// 构造PayTask 对象
					PayTask alipay = new PayTask(PayZhiActivity.this);
					// 调用支付接口，获取支付结果
					String result = alipay.pay(payInfo);
					Message msg = new Message();
					msg.what = SDK_PAY_FLAG;
					msg.obj = result;
					mHandler.sendMessage(msg);
				}
			};

			// 必须异步调用
			Thread payThread = new Thread(payRunnable);
			payThread.start();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}
	}

	/**
	 * check whether the device has authentication alipay account.
	 * 查询终端设备是否存在支付宝认证账户
	 * 
	 */
	public void check(View v) {
		Runnable checkRunnable = new Runnable() {

			@Override
			public void run() {
				// 构造PayTask 对象
				PayTask payTask = new PayTask(PayZhiActivity.this);
				// 调用查询接口，获取查询结果
				boolean isExist = payTask.checkAccountIfExist();

				Message msg = new Message();
				msg.what = SDK_CHECK_FLAG;
				msg.obj = isExist;
				mHandler.sendMessage(msg);
			}
		};

		Thread checkThread = new Thread(checkRunnable);
		checkThread.start();

	}

	/**
	 * get the sdk version. 获取SDK版本号
	 * 
	 */
	public void getSDKVersion() {
		PayTask payTask = new PayTask(this);
		String version = payTask.getVersion();
		Toast.makeText(this, version, Toast.LENGTH_SHORT).show();
	}

	/**
	 * create the order info. 创建订单信息
	 * 
	 */
	public String getOrderInfo(String subject, String body, String price) {

		// 签约合作者身份ID
		String orderInfo = "partner=" + "\"" + PARTNER + "\"";

		// 签约卖家支付宝账号
		orderInfo += "&seller_id=" + "\"" + SELLER + "\"";

		// 商户网站唯一订单号
		orderInfo += "&out_trade_no=" + "\"" + getOutTradeNo() + "\"";

		// 商品名称
		orderInfo += "&subject=" + "\"" + subject + "\"";

		// 商品详情
		orderInfo += "&body=" + "\"" + body + "\"";

		// 商品金额
		orderInfo += "&total_fee=" + "\"" + price + "\"";

		// 服务器异步通知页面路径

		// 代写
		orderInfo += "&notify_url=" + "\"" + "http://notify.msp.hk/notify.htm"
				+ "\"";

		// 服务接口名称， 固定值
		orderInfo += "&service=\"mobile.securitypay.pay\"";

		// 支付类型， 固定值
		orderInfo += "&payment_type=\"1\"";

		// 参数编码， 固定值
		orderInfo += "&_input_charset=\"utf-8\"";

		// 设置未付款交易的超时时间
		// 默认30分钟，一旦超时，该笔交易就会自动被关闭。
		// 取值范围：1m～15d。
		// m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
		// 该参数数值不接受小数点，如1.5h，可转换为90m。
		orderInfo += "&it_b_pay=\"30m\"";

		// extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
		// orderInfo += "&extern_token=" + "\"" + extern_token + "\"";

		// 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
		// orderInfo += "&return_url=\"m.alipay.com\"";

		// 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
		// orderInfo += "&paymethod=\"expressGateway\"";

		return orderInfo;
	}

	/**
	 * get the out_trade_no for an order. 生成商户订单号，该值在商户端应保持唯一（可自定义格式规范）
	 * 
	 */
	public String getOutTradeNo() {
		SimpleDateFormat format = new SimpleDateFormat("MMddHHmmss",
				Locale.getDefault());
		Date date = new Date();
		String key = format.format(date);

		Random r = new Random();
		key = key + r.nextInt();
		key = key.substring(0, 15);
		return key;
	}

	/**
	 * sign the order info. 对订单信息进行签名
	 * 
	 * @param content
	 *            待签名订单信息
	 */
	public String sign(String content) {
		return SignUtils.sign(content, RSA_PRIVATE);
	}

	/**
	 * get the sign type we use. 获取签名方式
	 * 
	 */
	public String getSignType() {
		return "sign_type=\"RSA\"";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_back_payzhi:
			finish();
//			toMyOrderActivity();
			// new PA01And02AsynTask().execute(PayZhiActivity.this);

			break;

		default:
			break;
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
//		toMyOrderActivity();
	}

	private void toMyOrderActivity() {
		Intent intent;
		intent = new Intent();
		intent.setClass(PayZhiActivity.this, SaleSheetMineActivity.class);
		startActivity(intent);
		finish();
	}

}
