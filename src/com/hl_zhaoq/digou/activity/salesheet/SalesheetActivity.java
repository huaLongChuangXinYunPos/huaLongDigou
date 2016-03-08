package com.hl_zhaoq.digou.activity.salesheet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.address.AddressActivity;
import com.hl_zhaoq.digou.bean.FirstSheet;
import com.hl_zhaoq.digou.bean.O_bStoreInfo;
import com.hl_zhaoq.digou.bean.SaleSheet;
import com.hl_zhaoq.digou.bean.SaleSheetDetail;
import com.hl_zhaoq.digou.constant.ConstantIP;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * 先获取优惠减免在进行处理
 * 
 * @author
 */
public class SalesheetActivity extends Activity implements OnClickListener {
	private ListView dingdanmingxi_ss;
	private TextView tel_dd;
	private TextView shouhuoname_dd;
	private TextView address_dd;
	private TextView sure_address_info;
	private Button modify_useraddress_sshead;
	private View view;
	private Context context;
	private boolean isback;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.salesheet_makesure);
		context = SalesheetActivity.this;
		// --------是否首单判断之
		isback = false;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (isback) {
			initView();
		} else {
			new getYouhuiTask().execute();
		}
	}

	/**
	 * --------获取是否首单,满减免优惠活动
	 * 
	 * @author you
	 */
	public class getYouhuiTask extends AsyncTask<Void, ProgressBar, Boolean> {

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			initView();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}

		@Override
		protected Boolean doInBackground(Void[] arg0) {
			try {
				Boolean network = NetUtil.hasNetwork(context);
				if (!network) {
					return false;
				}

				// ----------------------------获取是否首单---
				String UserNo = ConstantValue.userinfo.getUserNo();
				for (int i = 0; i < ConstantValue.cartSaleSureList.size(); i++) {
					boolean isFirstSheet = false;
					SaleSheet ssheet = ConstantValue.cartSaleSureList.get(i);
					String cStoreNo = ssheet.getcStoreNo();
					FirstSheet firstSheet = new FirstSheet(UserNo, cStoreNo);
					ArrayList<FirstSheet> firstSheetlist = new ArrayList<FirstSheet>();
					firstSheetlist.add(firstSheet);
					Gson mygson = new Gson();
					String jsonStr = mygson.toJson(firstSheetlist);

					String msg1 = "*AIS|RQ|FirstSheet|00|" + jsonStr + "|AIE#";

					HashMap<String, String> map1 = new HashMap<String, String>();
					map1.put("type", "post");
					map1.put("url",
							context.getString(R.string.servlet_firstsheet));
					map1.put("dataType", "text");
					map1.put("data", msg1);
					RequestVo vo1 = new RequestVo(R.string.servlet_firstsheet,
							context, map1);
					String result1 = NetUtil.post(vo1);
					// *AIS|RS|FirstSheet|00|Err|AIE#
					// *AIS|RS|FirstSheet|01|TRUE|AIE#
					// *AIS|RS|FirstSheet|01|false|AIE#
					PromptManager.showLogTest(
							"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
					String[] temp1 = NetUtil.split(result1, "|");
					if (temp1 == null) {

					} else {
						if ("*AIS".equalsIgnoreCase(temp1[0])
								&& "RS".equals(temp1[1])
								&& "FirstSheet".equals(temp1[2])
								&& temp1[3].equals("01")
								&& temp1[4].equals("TRUE")) {
							// ssheet.setFirstSheet(firstSheet);
							isFirstSheet = true;
							// 设置可以首单减免
						}
					}
					// ---------------------满减免优惠
					// String cStoreNos = ConstantValue.goodsinfo.getcStoreNo();
					String msg = "*AIS|RQ|MM01|00|" + cStoreNo + "|AIE#";
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("type", "post");
					map.put("url",
							context.getString(R.string.servlet_mansouman));
					map.put("dataType", "text");
					map.put("data", msg);
					RequestVo vo = new RequestVo(R.string.servlet_mansouman,
							context, map);
					String result = NetUtil.post(vo);
					// null
					// *AIS|RS|MM01|01|[{"cStoreNo":"10001","OverCut":"man200jian10",
					// "FirstSheet":"weqrewq","GiftFree":"zengsong","Over_Money":200.0000,
					// "Cut_Money":10.0000,"FirstSheet_Over":100.0000,"FirstSheet_Cut":10.0000,
					// "GiftFree_GiftGoodsNo":"111","GiftFree_GiftMoney":200.0000,
					// "PeisongFee_Over_Free":300.0000,"BasicPrice":10.0000,"PeisongFee":5.0000}]|AIE#

					String[] temp = NetUtil.split(result, "|");

					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "MM01".equals(temp[2])
							&& temp[3].equals("01")) {
						// 获取优惠与邮费
						List<O_bStoreInfo> bStoreInfo = mygson.fromJson(
								temp[4], new TypeToken<List<O_bStoreInfo>>() {
								}.getType());
//						BigDecimal flastMoney = new BigDecimal(0);
						// 信息非空进行加邮费和满减
						if (bStoreInfo != null && bStoreInfo.size() == 1) {

							if (bStoreInfo.get(0).getMM_Valid()) {

								// 满减
								if (bStoreInfo.get(0).getOver_Money() != null
										&& ssheet.getFLastMoney().floatValue() >= bStoreInfo
												.get(0).getOver_Money()
												.floatValue()) {
									ssheet.setOverCut(bStoreInfo.get(0)
											.getCut_Money());
									ssheet.setfLastMoney(ssheet.getfLastMoney().subtract(
											bStoreInfo.get(0).getCut_Money()));
								}
								// 首单折扣
								if (isFirstSheet
										&& ssheet.getFLastMoney().floatValue() >= bStoreInfo
												.get(0).getFirstSheet_Over()
												.floatValue()) {
									ssheet.setFirstSheet(bStoreInfo.get(0)
											.getFirstSheet_Cut());

									ssheet.setfLastMoney(ssheet.getfLastMoney().subtract(
											bStoreInfo.get(0)
													.getFirstSheet_Cut()));
								} else {
									ssheet.setFirstSheet(new BigDecimal(0));
								}
							}
							// 添加配送费
							BigDecimal peisongFee = new BigDecimal(0);
							if (bStoreInfo.get(0).getPeisongFee() != null)
								peisongFee = bStoreInfo.get(0).getPeisongFee();
							// 添加其他信息
							ssheet.setPeisongFee(bStoreInfo.get(0)
									.getPeisongFee());

							if (bStoreInfo.get(0).getPeisongFee_Over_Free() != null
									&& ssheet.getFLastMoney().floatValue() >= bStoreInfo
											.get(0).getPeisongFee_Over_Free()
											.floatValue()) {
								ssheet.setPeisongFee(new BigDecimal(0));

							} else {
								ssheet.setfLastMoney(ssheet.getfLastMoney().add(peisongFee));
							}

						}

					}
				}

				return true;
			} catch (Exception e) {
				return false;
			}

		}
	}

	public void initView() {

		ConstantValue.saleSheetList.clear();
		dingdanmingxi_ss = (ListView) findViewById(R.id.dingdanmingxi_ss);
		dingdanmingxi_ss.setVisibility(View.VISIBLE);
		if (view == null) {
			view = View.inflate(SalesheetActivity.this,
					R.layout.salesheet_head, null);
		}
		modify_useraddress_sshead = (Button) view
				.findViewById(R.id.modify_useraddress_sshead);
		modify_useraddress_sshead.setOnClickListener(this);

		sure_address_info = (TextView) view
				.findViewById(R.id.sure_address_info);
		tel_dd = (TextView) view.findViewById(R.id.tel_dd);
		shouhuoname_dd = (TextView) view.findViewById(R.id.shouhuoname_dd);
		address_dd = (TextView) view.findViewById(R.id.address_dd);

		if (ConstantValue.myaddr == null) {
			sure_address_info.setText("请先编辑收货地址! ");
		} else {
			sure_address_info.setText("请确认收货地址：");
			tel_dd.setText(ConstantValue.myaddr.getTel());
			shouhuoname_dd.setText(ConstantValue.myaddr.getAddUser_Name());
			address_dd.setText(ConstantValue.myaddr.getAddress_Name());
		}
		// 重新进行价格计算，剔除运费，满减，首单优惠活动等--------

		if (dingdanmingxi_ss.getHeaderViewsCount() == 0) {
			dingdanmingxi_ss.addHeaderView(view);
		}

		dingdanmingxi_ss.setAdapter(new BaseAdapter() {
			@Override
			public View getView(int position, View arg1, ViewGroup arg2) {
				View view;
				// view.findViewById(id)
				TextView firstsheet_ssitem_od01;
				TextView overcut_ssitem_od01;
				TextView peisong_ssitem_od01;
				TextView storename_ssitem_od01;
				TextView storeprice_ssitem_od01;
				TextView storesalesheetid_ssitem_od01;
				LinearLayout ss_od01_add_item;
				view = View.inflate(SalesheetActivity.this,
						R.layout.salesheet_item_od01, null);
				peisong_ssitem_od01 = (TextView) view
						.findViewById(R.id.peisong_ssitem_od01);
				ss_od01_add_item = (LinearLayout) view
						.findViewById(R.id.ss_od01_add_item);
				storename_ssitem_od01 = (TextView) view
						.findViewById(R.id.storename_ssitem_od01);
				overcut_ssitem_od01 = (TextView) view
						.findViewById(R.id.overcut_ssitem_od01);
				firstsheet_ssitem_od01 = (TextView) view
						.findViewById(R.id.firstsheet_ssitem_od01);
				storeprice_ssitem_od01 = (TextView) view
						.findViewById(R.id.storeprice_ssitem_od01);
				storesalesheetid_ssitem_od01 = (TextView) view
						.findViewById(R.id.storesalesheetid_ssitem_od01);
				SaleSheet saleSheet = ConstantValue.cartSaleSureList
						.get(position);
				overcut_ssitem_od01.setText("满减优惠："
						+ saleSheet.getOverCut().setScale(2,
								BigDecimal.ROUND_HALF_UP));
				firstsheet_ssitem_od01.setText("首减优惠："
						+ saleSheet.getFirstSheet().setScale(2,
								BigDecimal.ROUND_HALF_UP));
				peisong_ssitem_od01.setText("配送费："
						+ saleSheet.getPeisongFee().setScale(2,
								BigDecimal.ROUND_HALF_UP));
				storename_ssitem_od01.setText(saleSheet.getcStoreName());
				// -------------------------------------------------------------------
				// if (ConstantValue.isFirstSheetList) {
				//
				// }
				storeprice_ssitem_od01.setText(saleSheet.getfLastMoney()
						.setScale(2, BigDecimal.ROUND_HALF_UP) + "");
				storesalesheetid_ssitem_od01.setText(saleSheet.getdSaleDate());
				List<SaleSheetDetail> saleSheetDetailList = saleSheet
						.getSaleSheetDetailList();
				for (int i = 0; i < saleSheetDetailList.size(); i++) {
					SaleSheetDetail ssd1 = saleSheetDetailList.get(i);
					View viewssdetail;
					viewssdetail = View.inflate(SalesheetActivity.this,
							R.layout.ssd_item, null);
					TextView goods_name;
					TextView goods_price21;
					TextView count_tip;
					SmartImageView goods_icon;
					goods_icon = (SmartImageView) viewssdetail
							.findViewById(R.id.goods_icon);
					goods_icon.setImageUrl(ConstantIP.ImageviewURL
							+ ssd1.getMinIMG0());
					goods_name = (TextView) viewssdetail
							.findViewById(R.id.goods_name);
					goods_price21 = (TextView) viewssdetail
							.findViewById(R.id.goods_price21);
					count_tip = (TextView) viewssdetail
							.findViewById(R.id.count_tip_ss);

					goods_name.setText(ssd1.getcGoodsName());
					goods_price21.setText(ssd1.getfLastMoney().setScale(2,
							BigDecimal.ROUND_HALF_UP)
							+ "");
					count_tip.setText("" + ssd1.getfQuantity().intValue());

					ss_od01_add_item.addView(viewssdetail);
				}

				return view;
			}

			@Override
			public long getItemId(int arg0) {
				return 0;
			}

			@Override
			public Object getItem(int arg0) {
				return null;
			}

			@Override
			public int getCount() {
				return ConstantValue.cartSaleSureList.size();
			}
		});

	}

	public void goxiadan(View v) {
		if (ConstantValue.myaddr == null
				|| TextUtils.isEmpty(ConstantValue.myaddr.getAddress_Name())) {
			PromptManager.showMyToast(getApplicationContext(), "请先输入地址");
		} else {
			new SubmitOrderOD00AsynTask().execute(context);

		}
	}

	public class SubmitOrderOD00AsynTask extends
			AsyncTask<Context, ProgressBar, Boolean> {
		ListView list;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (!result) {
				PromptManager.showToast(context, "提交订单失败");
			} else {
				reflashConstantValue();
				startNextActivity();
			}

		}

		public void startNextActivity() {
			Intent intent = new Intent(context, SSPayStyleAty.class);
			startActivity(intent);
			SalesheetActivity.this.finish();
		}

		public void reflashConstantValue() {
			ConstantValue.cartSaleSheetList.clear();
			ConstantValue.saleSheet_dingdan.clear();
			ConstantValue.total_Nums_cart = 0;
			ConstantValue.saleSheet_dingdan
					.addAll(ConstantValue.cartSaleSureList);
			ConstantValue.cartSaleSureList.clear();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}

		@Override
		protected void onProgressUpdate(ProgressBar... values) {
			super.onProgressUpdate(values);

		}

		@Override
		protected Boolean doInBackground(Context[] arg0) {
			try {
				Boolean network = NetUtil.hasNetwork(context);
				if (!network) {
					return false;
				}

				String plno = "00";
				Gson g = new Gson();
				// 提交前更新订单信息
				for (int i = 0; i < ConstantValue.cartSaleSureList.size(); i++) {
					SaleSheet ss = ConstantValue.cartSaleSureList.get(i);
					ss.setAddressId(ConstantValue.myaddr.getAddress_id());
					ss.setSaleSheetDetailStr();
//					ss.setfMoney(ss.getfLastMoney());
					// ss.setFirstSheet(firstSheet);
					// ss.setOverCut(overCut);
					ss.setStat_Id("00");
				}
				String jsons = g.toJson(ConstantValue.cartSaleSureList);
				String msg = "*AIS|RQ|OD00|" + plno + "|" + jsons + "|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);//*AIS|RQ|OD00|00|[{"userNo":"201509241217045962","Beizhu":"","CouPonMoney":0.0,"Fapiao":"","FapiaoStat":"","FapiaoTitle":"","FirstSheet":0,"OverCut":10.0000,"PeisongFee":0,"SaleSheetDetailList":[],"SaleSheetDetailStr":"[{\"cGoodsNo\":\"440001\",\"cSaleSheetno\":\"0112172033\",\"cVipNo\":\"\",\"cWHno\":\"\",\"dSaleDate\":\"2016-01-12 17:20:33\",\"fLastMoney\":60.00,\"fPrice\":0.0,\"fPriceType\":\"\",\"fQuantity\":3,\"fVipPrice\":0.0,\"fVipScore_cur\":0.0,\"iSeed\":1},{\"cGoodsNo\":\"440010\",\"cSaleSheetno\":\"0112172039\",\"cVipNo\":\"\",\"cWHno\":\"\",\"dSaleDate\":\"2016-01-12 17:20:39\",\"fLastMoney\":66.50,\"fPrice\":0.0,\"fPriceType\":\"\",\"fQuantity\":3,\"fVipPrice\":0.0,\"fVipScore_cur\":0.0,\"iSeed\":1}]","Stat_Id":"00","bSent":"","cSaleSheetno":"0112172033","cStoreName":"乐尚","cStoreNo":"10001","cVipNo":"","dSaleDate":"2016-01-12 17:20:33","fLastMoney":369.5000,"fMoney":379.50,"AddressId":9}]|AIE#
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_od01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_od01, context, map);
				String result = NetUtil.post(vo);

				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else if ("*AIS".equalsIgnoreCase(temp[0])
						&& "RS".equals(temp[1]) && "OD00".equals(temp[2])
						&& "01".equals(temp[3])) {
					String salesheetstr = temp[4];
					String[] salesheetNo = NetUtil.split(salesheetstr, ",");
					if (salesheetNo != null
							&& salesheetNo.length == ConstantValue.cartSaleSureList
									.size()) {
						for (int i = 0; i < ConstantValue.cartSaleSureList
								.size(); i++) {
							SaleSheet ss = ConstantValue.cartSaleSureList
									.get(i);
							ss.setcSaleSheetno(salesheetNo[i]);

						}
					}

					// ConstantValue.saleSheetNoForZhifubao = salesheetNo;
					// ConstantValue.cstoreNo = ConstantValue.cartSaleSureList
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

	public void goback(View v) {
		SalesheetActivity.this.finish();
	}

	@Override
	public void onClick(View views) {

		switch (views.getId()) {
		// 修改地址
		case R.id.modify_useraddress_sshead:
			isback = true;
			Intent intent;
			intent = new Intent();
			intent.setClass(SalesheetActivity.this, AddressActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
