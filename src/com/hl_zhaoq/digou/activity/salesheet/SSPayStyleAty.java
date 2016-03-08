package com.hl_zhaoq.digou.activity.salesheet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import com.alipay.sdk.pay.demo.PayZhiActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.wallet.OutWalletZhifuActivity;
import com.hl_zhaoq.digou.activity.wallet.WalletLoginDDActivity;
import com.hl_zhaoq.digou.bean.CPUsing;
import com.hl_zhaoq.digou.bean.Coupon;
import com.hl_zhaoq.digou.bean.PAXiangdan;
import com.hl_zhaoq.digou.bean.PAZongdan;
import com.hl_zhaoq.digou.bean.PayStyleBean;
import com.hl_zhaoq.digou.bean.SaleSheet;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.ProgressBar;

public class SSPayStyleAty extends Activity implements OnClickListener {

	private ListView ss_paystyle_listview;
	private ImageButton iv_back_sspaystyle;
	private Context context;

	PASaleSheetAdapter pASaleSheetAdapter;
	SaleSheet ss;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.salesheet_paystyle_list);
		context = SSPayStyleAty.this;
		// new GetAllPayways(context).execute("");
		// init();

	}

	private void init() {
		if (ConstantValue.saleSheet_dingdan==null||ConstantValue.saleSheet_dingdan.size()==0) {
			PromptManager.showMyToast(context, "当前账单付款结束");
			
		}
		iv_back_sspaystyle = (ImageButton) findViewById(R.id.iv_back_sspaystyle2);
		iv_back_sspaystyle.setOnClickListener(this);
		ss_paystyle_listview = (ListView) findViewById(R.id.ss_paystyle_listview2);
		//zhifuliebiao
		pASaleSheetAdapter = new PASaleSheetAdapter(
				ConstantValue.saleSheet_dingdan, context);
		ss_paystyle_listview.setAdapter(pASaleSheetAdapter);
		ss_paystyle_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View view, int position,
					long arg3) {
				// PromptManager.showMyToast(context, "kkkkkk");
				ss = null;
				ss = ConstantValue.saleSheet_dingdan.get(position);
				ConstantValue.myss = ss;
				new GetAllPayways(context).execute("");

			}
		});

		// paystyleAdapter = new PayStyleAdapter(listPayStyles, context);
		// ss_paystyle_listview.setAdapter(paystyleAdapter);
		// ss_paystyle_listview.setOnItemClickListener(this);

	}

	public class GetCPUsingTask extends AsyncTask<String, ProgressBar, Boolean> {
		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				Intent intent;
				intent = new Intent();
				intent.setClass(context, CouponPaywayActivity.class);
				context.startActivity(intent);

			} else {
				PromptManager.showMyToast(context, "您没有优惠券");
			}
		}

		@Override
		protected Boolean doInBackground(String[] arg0) {
			try {
				ConstantValue.couponList = null;
				// ConstantValue.goodDatatemp.clear();
				// final String cGoodsNo = ConstantValue.goodsinfo.getcGoodsNo()
				// .trim();
				// if (cGoodsNo == null) {
				// return false;
				// }
				// String cStoreNo = ConstantValue.goodsinfo.getcStoreNo();
				// -----------------------------------------------
				CPUsing myCPUsing = new CPUsing();
				myCPUsing.setcStoreNo(ss.getcStoreNo());
				// myCPUsing.setcSaleSheetno(ss.getCSaleSheetno());
				myCPUsing.setUserNO(ConstantValue.userinfo.getUserNo());
				ArrayList<CPUsing> myCPUsingList = new ArrayList<CPUsing>();
				myCPUsingList.add(myCPUsing);
				Gson myGson = new Gson();
				String content = myGson.toJson(myCPUsingList);
				String msg = "*AIS|RQ|CPUserQuery|00|" + content + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_coupon));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_coupon, context,
						map);
				String result = NetUtil.post(vo);
				// *AIS|RS|CPUsing|00|Have No Ticket|AIE#
				// *AIS|RS|CPUserQuery|00||AIE#
				// *AIS|RS|CPUserQuery|01|[{"CouponNo":"201512182417-10001","cStoreNo":"10001",
				// "cStoreName":"乐尚","OwnerID":"01","OwnerName":"店铺","CouponType":"03",
				// "CouponName":"特定节日活动优惠券","Name":"店铺优惠","FMoney":10.0000,"TicketIndex":1,
				// "CGoodsTypeno":"02","CGoodsTypename":"奶/饮料/酒","Status":"1","Diqu":"  ",
				// "Riqi1":"2015-12-18 00:00:00.0","Riqi2":"2016-12-22 15:42:47.0","Beizhu":"null","Used":false}]|AIE#
//*AIS|RS|CPUserQuery|01|[{"CouponNo":"201601085505-10001","cStoreNo":"10001","cStoreName":"乐尚","OwnerID":"01","OwnerName":"店铺","CouponType":"02","CouponName":"购买满送优惠券","Name":"满赠优惠券","FMoney":20.0000,"TicketIndex":14,"CGoodsTypeno":"02","CGoodsTypename":"奶/饮料/酒","Status":"True","Diqu":"  ","Riqi1":"2016-01-08 00:00:00.0","Riqi2":"2016-01-20 00:00:00.0","Beizhu":"null","PayOverMoney":100.0000,"Used":false}]|AIE#
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1])
							&& "CPUserQuery".equals(temp[2])
							&& temp[3].equals("01")) {
						Gson gson = new Gson();
						ArrayList<Coupon> mycouponList = gson.fromJson(temp[4],
								new TypeToken<List<Coupon>>() {
								}.getType());
						if (mycouponList != null && mycouponList.size() > 0) {

							ConstantValue.couponList = mycouponList;
							return true;
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	String paywayId;

	public class GetAllPayways extends AsyncTask<String, Process, Integer> {
		private Context context;
		Builder builder;
		AlertDialog alertDialog;
		ArrayList<PayStyleBean> listPayStyles;
		PayStyleAdapter pPayStyleAdapter;

		public GetAllPayways(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}

		@SuppressLint("NewApi") @Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();// potsDialog(context);
			if (ss != null) {
				if (result == 1) {
					pPayStyleAdapter = new PayStyleAdapter(listPayStyles,
							context);
					View popupWindow_view = getLayoutInflater().inflate(
							R.layout.salesheet_makesure2, null, false);
					ListView lv = (ListView) popupWindow_view
							.findViewById(R.id.dingdanmingxi_ss2);
					lv.setAdapter(pPayStyleAdapter);
					builder = new AlertDialog.Builder(context);
					builder.setView(popupWindow_view);

					// builder.show().setCanceledOnTouchOutside(false);
					alertDialog = builder.show();

					alertDialog.setCanceledOnTouchOutside(false);
					lv.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View view,
								int pisition, long arg3) {
							paywayId = null;
							if (ConstantValue.myss == null
									|| ConstantValue.myss.getfLastMoney()
											.intValue() == 0) {
								return;
							}
							String pt = listPayStyles.get(pisition)
									.getcPayStyleNo().trim();
							paywayId = pt;
							if (paywayId.equals("00")) {

								new SubmitOrderOD05().execute(context);// daofu
								alertDialog.dismiss();
								//-------------------------------------------------------------------------------------
								ConstantValue.saleSheet_dingdan.remove(ss);
								pASaleSheetAdapter.notifyDataSetChanged();
								//-------------------------------------------------------------------------------------
							} else if (paywayId.equals("02")) {// 支付宝

								Intent intent;
								intent = new Intent();
								intent.setClass(context, PayZhiActivity.class);
								SSPayStyleAty.this.startActivityForResult(intent, 2);
//								context.startActivity(intent);
								alertDialog.dismiss();
							} else if (paywayId.equals("01")) {// 钱包支付
								if (ConstantValue.lostWMoney == null
										|| ConstantValue.wBuyerR == null) {
									PromptManager.showMyToast(context,
											"请您先登录钱包"); //
									Intent intent = new Intent(context,
											WalletLoginDDActivity.class);
									
									startActivity(intent); //
								} else {

									Intent intent;
									intent = new Intent();
									intent.setClass(context,
											OutWalletZhifuActivity.class);
									SSPayStyleAty.this.startActivityForResult(intent, 1);
//									context.startActivity(intent);
									alertDialog.dismiss();
									// new SubmitOrderWalletAsynTask()
									// .execute(SSPayStyleAty.this);
								}
							} else if (paywayId.equals("09")) {
								new GetCPUsingTask().execute("");
							}

						}
					});

				}
			}

		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {
				Boolean network = NetUtil.hasNetwork(SSPayStyleAty.this);
				if (!network) {
					return -1;
				}
				// *AIS|RQ|PayStyle|00|00|AIE#
				Gson g = new Gson();
				String jsons = g.toJson(ConstantValue.cartSaleSheetList);
				String msg = "*AIS|RQ|PayStyle|00|00|AIE#";

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_paystyle));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_paystyle,
						context, map);
				String result = NetUtil.post(vo);
				// *AIS|RS|PayStyle|01|[{"cPayStyleNo":"00 ","cPayStylePic":"/cPayStylePic/b00.jpg","cPayStyleName":"到货付款"},{"cPayStyleNo":"01 ","cPayStylePic":"/cPayStylePic/b01.jpg","cPayStyleName":" 钱包支付"},{"cPayStyleNo":"02 ","cPayStylePic":"/cPayStylePic/b02.jpg","cPayStyleName":"支付宝"},{"cPayStyleNo":"03 ","cPayStylePic":"/cPayStylePic/b03.jpg","cPayStyleName":"微信"},{"cPayStyleNo":"04 ","cPayStylePic":"/cPayStylePic/b04.jpg","cPayStyleName":"银联"},{"cPayStyleNo":"09 ","cPayStylePic":"/cPayStylePic/b09.jpg","cPayStyleName":"优惠券"}]|AIE#
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else if ("*AIS".equalsIgnoreCase(temp[0])
						&& "RS".equals(temp[1]) && "PayStyle".equals(temp[2])
						&& "01".equals(temp[3])) {
					listPayStyles = g.fromJson(temp[4],
							new TypeToken<List<PayStyleBean>>() {
							}.getType());
					if (listPayStyles != null && listPayStyles.size() > 0) {
						return 1;
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			return 0;
		}

	}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	// TODO Auto-generated method stub
	super.onActivityResult(requestCode, resultCode, data);
	if (requestCode==2&&resultCode==1) {
		ConstantValue.saleSheet_dingdan.remove(ConstantValue.myss);
		pASaleSheetAdapter.notifyDataSetChanged();
	}
	if (requestCode==1&&resultCode==1) {
		ConstantValue.saleSheet_dingdan.remove(ConstantValue.myss);
		pASaleSheetAdapter.notifyDataSetChanged();
	}
	
}
	public class GetAllPaywaysTmp extends AsyncTask<String, Process, Integer> {
		private Context context;

		public GetAllPaywaysTmp(Context context) {
			this.context = context;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();// potsDialog(context);

			if (result == 1) {
				// paystyleAdapter.setGoodsPL01List(listPayStyles);
				// paystyleAdapter.notifyDataSetChanged();
			}

		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {
				Boolean network = NetUtil.hasNetwork(SSPayStyleAty.this);
				if (!network) {
					return -1;
				}
				// *AIS|RQ| PayStyle |00|00|AIE#
				Gson g = new Gson();
				String jsons = g.toJson(ConstantValue.cartSaleSheetList);
				String msg = "*AIS|RQ|PayStyle|00|00|AIE#";

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_paystyle));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_paystyle,
						context, map);
				String result = NetUtil.post(vo);
				// *AIS|RS|PayStyle|01|[{"cPayStyleNo":"00 ","cPayStylePic":"/cPayStylePic/b00.jpg","cPayStyleName":"到货付款"},{"cPayStyleNo":"01 ","cPayStylePic":"/cPayStylePic/b01.jpg","cPayStyleName":" 钱包支付"},{"cPayStyleNo":"02 ","cPayStylePic":"/cPayStylePic/b02.jpg","cPayStyleName":"支付宝"},{"cPayStyleNo":"03 ","cPayStylePic":"/cPayStylePic/b03.jpg","cPayStyleName":"微信"},{"cPayStyleNo":"04 ","cPayStylePic":"/cPayStylePic/b04.jpg","cPayStyleName":"银联"},{"cPayStyleNo":"09 ","cPayStylePic":"/cPayStylePic/b09.jpg","cPayStyleName":"优惠券"}]|AIE#
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else if ("*AIS".equalsIgnoreCase(temp[0])
						&& "RS".equals(temp[1]) && "PayStyle".equals(temp[2])
						&& "01".equals(temp[3])) {
					// listPayStyles = g.fromJson(temp[4],
					// new TypeToken<List<PayStyleBean>>() {
					// }.getType());
					// if (listPayStyles != null && listPayStyles.size() > 0) {
					// return 1;
					// }

				}
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			return 0;
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		init();
		// if ( ConstantValue.myss.getfMoney().floatValue()==0.0f) {
		// alertDialog.
		// }
	}

	@Override
	public void onClick(View views) {

		switch (views.getId()) {

		case R.id.iv_back_sspaystyle2:
			// new SubmitOrderOD05().execute(context);// 退出保存
			finish();
			break;

		default:
			break;
		}
	}

	public class SubmitOrderWalletAsynTask extends
			AsyncTask<Context, ProgressBar, Boolean> {
		Context context;
		ListView list;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
				PromptManager.showToast(context, "提交失败");
			} else {
				// reflashConstantValue();
				startNextActivity();
			}

		}

		public void startNextActivity() {
			Intent intent;
			intent = new Intent();
			intent.setClass(context, OutWalletZhifuActivity.class);
			context.startActivity(intent);
		}

		public void reflashConstantValue() {
			ConstantValue.saleSheet_dingdan.clear();
			ConstantValue.total_Nums_cart = 0;
			ConstantValue.saleSheet_dingdan
					.addAll(ConstantValue.cartSaleSheetList);
			ConstantValue.cartSaleSheetList.clear();
		}

		@Override
		protected Boolean doInBackground(Context[] arg0) {
			return true;
		}
	}

	public class SubmitOrderZhifubaoAsynTask extends
			AsyncTask<Context, ProgressBar, Boolean> {
		Context context;
		ListView list;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
				PromptManager.showToast(context, "提交失败");
			} else {
				reflashConstantValue();
				startNextActivity();
			}

		}

		public void startNextActivity() {
			Intent intent;
			intent = new Intent();
			intent.setClass(context, PayZhiActivity.class);
			context.startActivity(intent);
			SSPayStyleAty.this.finish();
		}

		public void reflashConstantValue() {
			ConstantValue.saleSheet_dingdan.clear();
			ConstantValue.total_Nums_cart = 0;
			ConstantValue.saleSheet_dingdan
					.addAll(ConstantValue.cartSaleSheetList);
			ConstantValue.cartSaleSheetList.clear();
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
					ss.setStat_Id("00");
				}
				String jsons = g.toJson(ConstantValue.cartSaleSheetList);
				String msg = "*AIS|RQ|OD00|" + plno + "|" + jsons + "|AIE#";
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
						&& "RS".equals(temp[1]) && "OD00".equals(temp[2])
						&& "01".equals(temp[3])) {
					String salesheetstr = temp[4];
					String[] salesheetNo = NetUtil.split(salesheetstr, ",");
					ConstantValue.saleSheetNoForZhifubao = salesheetNo;
					ConstantValue.cstoreNo = ConstantValue.cartSaleSheetList
							.get(0).getcStoreNo();
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return false;
		}
	}

	// 到付
	public class SubmitOrderOD05 extends
			AsyncTask<Context, ProgressBar, Boolean> {
		ListView list;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
				PromptManager.showMyToast(context, "提交失败");
			} else {
				// reflashConstantValue1();
				// startNextActivity2();
				PromptManager.showMyToast(context, "货到付款提交成功");
			}

		}

		public void startNextActivity2() {
			Intent intent;
			intent = new Intent();
			intent.setClass(context, SaleSheetMineActivity.class);
			context.startActivity(intent);
			SSPayStyleAty.this.finish();
		}

		public void reflashConstantValue1() {
			ConstantValue.saleSheet_dingdan.clear();
			ConstantValue.total_Nums_cart = 0;
			ConstantValue.saleSheet_dingdan
					.addAll(ConstantValue.cartSaleSheetList);
			ConstantValue.cartSaleSheetList.clear();
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
			try {
				Boolean network = NetUtil.hasNetwork(context);
				if (!network || ss == null) {
					return false;
				}
				List<PAZongdan> pa01List = new ArrayList<PAZongdan>();
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String datenow = sdf.format(d);

				PAZongdan paZongdan = new PAZongdan();
				paZongdan.setcSaleSheetNo(ss.getcSaleSheetno());
				paZongdan.setcPayTime(datenow);
				paZongdan.setfPayMoney(ss.getFLastMoney());
				pa01List.add(paZongdan);

				Gson gson = new Gson();
				String strgson = gson.toJson(pa01List);
				String msg = "*AIS|RQ|PA01|00|" + strgson + "|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_paysheet));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_paysheet,
						context, map);

				// String result = NetUtil.post(vo);
				String result = "*AIS|RS|PA01|01|20150929134605_20150809121207kk|AIE#";
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else

				if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
						&& "PA01".equals(temp[2]) && "01".equals(temp[3])) {//
					// 村上付款*AIS|RS|PA01|01|20150929134605_20150809121207kk|AIE#
					List<PAXiangdan> pa02List = new ArrayList<PAXiangdan>();

					PAXiangdan paXiangdan = new PAXiangdan();
					paXiangdan.setcPayTime(datenow);
					paXiangdan.setcSaleSheetNo(ss.getcSaleSheetno());
					paXiangdan.setfPayMoney(ss.getFLastMoney());
					paXiangdan.setIlineNo("1");
					paXiangdan.setcPayStyleNo(paywayId);
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
					PromptManager.showLogTest(
							"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
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
						return true;
					}
				}
			} catch (Exception e) {
				return false;
			}

			return false;
		}
	}

	// 未付款，未到付
	public class SubmitOrderOD06 extends
			AsyncTask<Context, ProgressBar, Boolean> {
		Context context;
		ListView list;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
				PromptManager.showMyToast(context, "提交失败");
			} else {
				reflashConstantValue1();
				startNextActivity2();
			}

		}

		public void startNextActivity2() {
			Intent intent;
			intent = new Intent();
			intent.setClass(context, SaleSheetMineActivity.class);
			context.startActivity(intent);
			SSPayStyleAty.this.finish();
		}

		public void reflashConstantValue1() {
			ConstantValue.saleSheet_dingdan.clear();
			ConstantValue.total_Nums_cart = 0;
			ConstantValue.saleSheet_dingdan
					.addAll(ConstantValue.cartSaleSheetList);
			ConstantValue.cartSaleSheetList.clear();
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
					ss.setStat_Id("01");
				}
				String jsons = g.toJson(ConstantValue.cartSaleSheetList);
				String msg = "*AIS|RQ|OD05|" + plno + "|" + jsons + "|AIE#";
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
						&& "RS".equals(temp[1]) && "OD05".equals(temp[2])
						&& "01".equals(temp[3])) {
					String salesheetstr = temp[4];

					String[] salesheetNo = NetUtil.split(salesheetstr, ",");
					ConstantValue.saleSheetNoForZhifubao = salesheetNo;
					ConstantValue.cstoreNo = ConstantValue.cartSaleSheetList
							.get(0).getcStoreNo();
					return true;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			return false;
		}
	}

}
