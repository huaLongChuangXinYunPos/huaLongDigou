package com.hlcxdg.digou.activity.cuxiao;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.MainActivity;
import com.hlcxdg.digou.activity.salesheet.SalesheetActivity;
import com.hlcxdg.digou.activity.user.LoginActivity;
import com.hlcxdg.digou.bean.FirstSheet;
import com.hlcxdg.digou.bean.Goods;
import com.hlcxdg.digou.bean.GroupType123List;
import com.hlcxdg.digou.bean.PC03params;
import com.hlcxdg.digou.bean.SaleSheet;
import com.hlcxdg.digou.bean.SaleSheetDetail;
import com.hlcxdg.digou.bean.UserAddress;
import com.hlcxdg.digou.constant.ConstantIP;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.fragment.CartActivity;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class GoodSingleActivity extends Activity implements OnClickListener {
	private TextView tv_goodname;
	private TextView goods_price1;
	private TextView goods_price2;
	private ImageButton jian_cart_gd;
	private ImageButton jia_cart_gd;
	private EditText gd_num_edit;
	// private TextView goods_danwei;
	// private TextView goods_guige;
	// private TextView goods_shangjia;
	private TextView tv_storename_pd;
	private TextView tv_pd_shuliang;
	private SmartImageView goodicon_pd01_gs;
	private ImageView cart_pd01_iv;
	private ImageView back_goodsxq;
	private String datessno;
	private String buyriqi;
	protected int lastPosition;
	public boolean hasGoods;
	ScrollView sc;
	RelativeLayout loading_bg;
	TextView pd01_gs_top_ranking_tv;
	ImageView pd01_gs_new_goods_iv;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.goodsxiangqing);
		context = GoodSingleActivity.this;
		hasGoods = true;
		formatAllDate();
		sc = (ScrollView) findViewById(R.id.sc);
		loading_bg = (RelativeLayout) findViewById(R.id.loading_bg);
		new getGoodsXqAsnyTask(context).execute();

	}

	@Override
	protected void onResume() {
		super.onResume();
		initView();
	}

	public void tomainpage(View view) {

		Intent intent;
		intent = new Intent();
		Bundle extras = new Bundle();
		extras.putInt("indexMenu", 21);
		// intent.putIntegerArrayListExtra(name, value)Extra("indexMenu", 1);
		intent.putExtras(extras);

		intent.setClass(context, MainActivity.class);
		startActivity(intent);
		finish();
	}

	// 格式化时间数据
	public void formatAllDate() {
		SimpleDateFormat sdfssno = new SimpleDateFormat("MMddHHmmss");
		SimpleDateFormat sdfriqi = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		datessno = sdfssno.format(d);
		buyriqi = sdfriqi.format(d);
	}

	public class getGoodsXqAsnyTask extends
			AsyncTask<String, ProgressBar, Boolean> {
		Context context;
		ListView list;

		public getGoodsXqAsnyTask(Context context) {
			this.context = context;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
				PromptManager.showMyToast(context, "商品下架了");
				hasGoods = false;
			} else if (ConstantValue.goodDatatemp != null
					&& ConstantValue.goodDatatemp.size() > 0) {
				loading_bg.setVisibility(View.GONE);
				sc.setVisibility(View.VISIBLE);
				Goods goods = ConstantValue.goodDatatemp.get(0);
				setViewData(goods);
				hasGoods = true;
			}
			PromptManager.closeSpotsDialog();
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

		SharedPreferences sp;

		@Override
		protected Boolean doInBackground(String[] arg0) {
			try {
				ConstantValue.goodDatatemp.clear();
				final String cGoodsNo = ConstantValue.goodsinfo.getcGoodsNo()
						.trim();
				if (cGoodsNo == null) {
					return false;
				}
				String cStoreNo = ConstantValue.goodsinfo.getcStoreNo();
				// *AIS|RS| SPPD01|00/01|content|AIE# *AIS|RQ|SPPD01| 00 |
				// JSON|AIE#
				SPPD01 spSPPD01 = new SPPD01();
				spSPPD01.cStoreNo = cStoreNo;
				spSPPD01.cGoodsNo = cGoodsNo;
				Gson g = new Gson();
				String jsonstr = g.toJson(spSPPD01);

				String msg = "*AIS|RQ|SPPD01|" + "00" + "|[" + jsonstr
						+ "]|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_sppl01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_sppl01, context, map);
				String result = NetUtil.post(vo);// *AIS|RS|SPPD01|01|[{"cStoreNo":"10001","cStoreName":"乐尚","cGoodsNo":"440001","cGoodsName":"三元 小方白纯牛奶250ml*24包","cGoodsTypeno":"0211","cGoodsTypename":"纯牛奶","fNormalPrice":63.0000,"fVipPrice":63.0000,"fVipPrice_student":50.0000,"O_PriceFlag":"2","MinIMG1":"/DOGOServer/pic/d440001.gif"}]|AIE#
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "SPPD01".equals(temp[2])
							&& temp[3].equals("01")) {
						Gson gson = new Gson();
						ArrayList<Goods> goodsList = gson.fromJson(temp[4],
								new TypeToken<List<Goods>>() {
								}.getType());
						if (goodsList != null) {
							ConstantValue.goodDatatemp.addAll(goodsList);
							ConstantValue.goodsinfo = goodsList.get(0);
						}
						return true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	private void initView() {
		jian_cart_gd = (ImageButton) findViewById(R.id.jian_cart_gd);
		jian_cart_gd.setOnClickListener(this);
		jia_cart_gd = (ImageButton) findViewById(R.id.jia_cart_gd);
		jia_cart_gd.setOnClickListener(this);
		gd_num_edit = (EditText) findViewById(R.id.gd_num_edit);
		pd01_gs_top_ranking_tv = (TextView) findViewById(R.id.pd01_gs_top_ranking_tv1);
		pd01_gs_new_goods_iv = (ImageView) findViewById(R.id.pd01_gs_new_goods_iv);
		tv_pd_shuliang = (TextView) findViewById(R.id.tv_pd_shuliang);
		if (ConstantValue.total_Nums_cart != 0) {
			tv_pd_shuliang.setVisibility(View.GONE);
			tv_pd_shuliang.setText("" + ConstantValue.total_Nums_cart);
		} else {
			tv_pd_shuliang.setVisibility(View.GONE);
		}
		tv_storename_pd = (TextView) findViewById(R.id.tv_storename_pd);
		// goods_shangjia = (TextView) findViewById(R.id.goods_shangjia);
		back_goodsxq = (ImageView) findViewById(R.id.back_goodsxq);
		cart_pd01_iv = (ImageView) findViewById(R.id.cart_pd01_iv);
		cart_pd01_iv.setOnClickListener(this);
		back_goodsxq.setOnClickListener(this);
		goodicon_pd01_gs = (SmartImageView) findViewById(R.id.goodicon_pd01_gs);
		tv_goodname = (TextView) findViewById(R.id.tv_goodname);
		goods_price1 = (TextView) findViewById(R.id.goods_price1);
		goods_price2 = (TextView) findViewById(R.id.goods_price2);
		// goods_danwei = (TextView) findViewById(R.id.goods_danwei);
		// goods_guige = (TextView) findViewById(R.id.goods_guige);

	}

	public void setViewData(Goods goods) {

		try {
			if (null != goods.getO_Famousspecialty()) {
				pd01_gs_top_ranking_tv.setVisibility(View.VISIBLE);
			}
			if ("1".equals(goods.getO_bNew())) {
				pd01_gs_new_goods_iv.setVisibility(View.VISIBLE);
			}
			goodicon_pd01_gs.setImageUrl(ConstantIP.ImageviewURL
					+ goods.getMinIMG1());
			tv_goodname.setText(goods.getcGoodsName());
			goods_price1.setText("￥"
					+ goods.getfNormalPrice()
							.setScale(2, BigDecimal.ROUND_HALF_UP).toString()
					+ "元");
			goods_price2.setText("￥"
					+ goods.getfVipPrice_student()
							.setScale(2, BigDecimal.ROUND_HALF_UP).toString()
					+ "元");
			// goods_danwei.setText("件");
			// goods_guige.setText("件");
			// goods_shangjia.setText(goods.getcProductdDate());
			tv_storename_pd.setText(goods.getcStoreName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void goback(View v) {
		GoodSingleActivity.this.finish();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 101 && resultCode == 2001) {
			lijigouService();
		}
		if (requestCode == 102 && resultCode == 2001) {
			if (ConstantValue.cartSaleSheetList != null) {
				PromptManager.showMyToast(getApplicationContext(), "已经添加到购物车");
				int indexGoodsInCart = -1;
				String gdnum = gd_num_edit.getText().toString();
				int nums = Integer.parseInt(gdnum);
				// 查重cartSaleSheetList
				for (int i = 0; i < ConstantValue.cartSaleSheetList.size(); i++) {
					SaleSheet saleSheetItem = ConstantValue.cartSaleSheetList
							.get(i);

					if (saleSheetItem.getcStoreNo() != null
							&& ConstantValue.goodsinfo.getcStoreNo() != null
							&& saleSheetItem.getcStoreNo().equals(
									ConstantValue.goodsinfo.getcStoreNo())) {
						indexGoodsInCart = i;
						break;
					}
				}

				if (indexGoodsInCart != -1) {
					insertIntoCart(indexGoodsInCart, nums);
				} else {
					addtoLastCartList(nums);
				}
				ConstantValue.total_Nums_cart += 1;
				if (ConstantValue.total_Nums_cart != 0) {
					tv_pd_shuliang.setVisibility(View.GONE);
					tv_pd_shuliang.setText("" + ConstantValue.total_Nums_cart);
				} else {
					tv_pd_shuliang.setVisibility(View.GONE);
				}
			}
		}

	}

	public void lijigoumai(View v) {
		formatAllDate();
		if (!hasGoods) {
			return;
		}
		if (ConstantValue.userinfo == null) {
			dengluqu();
		} else if (ConstantValue.cartSaleSureList != null) {
			String gdnum = gd_num_edit.getText().toString();
			int nums = Integer.parseInt(gdnum);
			ConstantValue.cartSaleSureList.clear();
			ConstantValue.total_price_cart = ConstantValue.goodsinfo
					.getfVipPrice_student().multiply(new BigDecimal(nums));
			ConstantValue.total_Nums_cart = nums;
			SaleSheetDetail ssd = new SaleSheetDetail();
			Goods g = ConstantValue.goodsinfo;

			ssd.setdSaleDate(buyriqi);
			ssd.setcSaleSheetno(datessno);
			ssd.setMinIMG0(g.getMinIMG0());
			ssd.setcGoodsNo(g.getcGoodsNo());
			ssd.setcGoodsName(g.getcGoodsName());
			ssd.setfQuantity(new BigDecimal(nums));

			ssd.setfLastMoney(g.getfVipPrice_student());
			ArrayList<SaleSheetDetail> cartItemDataSSD = new ArrayList<SaleSheetDetail>();
			cartItemDataSSD.add(ssd);
			SaleSheet ss = new SaleSheet();
			ss.setDSaleDate(buyriqi);
			ss.setCSaleSheetno(datessno);
			ss.setcStoreNo(g.getcStoreNo());
			ss.setcStoreName(g.getcStoreName());
			ss.setUserNo(ConstantValue.userinfo.getUserNo());
			ss.setfMoney(ConstantValue.total_price_cart);
			ss.setFLastMoney(ConstantValue.total_price_cart);
			ss.setSaleSheetDetailList(cartItemDataSSD);
			ConstantValue.cartSaleSureList.add(ss);
			goToSaleSheet();
		}
	}

	private void lijigouService() {
		if (ConstantValue.cartSaleSureList != null) {
			String gdnum = gd_num_edit.getText().toString();
			int nums = Integer.parseInt(gdnum);
			ConstantValue.cartSaleSureList.clear();
			ConstantValue.total_price_cart = ConstantValue.goodsinfo
					.getfVipPrice_student().multiply(new BigDecimal(nums));
			ConstantValue.total_Nums_cart = nums;
			SaleSheetDetail ssd = new SaleSheetDetail();
			Goods g = ConstantValue.goodsinfo;

			ssd.setdSaleDate(buyriqi);
			ssd.setcSaleSheetno(datessno);
			ssd.setMinIMG0(g.getMinIMG0());
			ssd.setcGoodsNo(g.getcGoodsNo());
			ssd.setcGoodsName(g.getcGoodsName());
			ssd.setfQuantity(new BigDecimal(nums));
			ssd.setfLastMoney(g.getfVipPrice_student());
			ArrayList<SaleSheetDetail> cartItemDataSSD = new ArrayList<SaleSheetDetail>();
			cartItemDataSSD.add(ssd);
			SaleSheet ss = new SaleSheet();
			ss.setDSaleDate(buyriqi);
			ss.setCSaleSheetno(datessno);
			ss.setcStoreNo(g.getcStoreNo());
			ss.setcStoreName(g.getcStoreName());
			ss.setUserNo(ConstantValue.userinfo.getUserNo());
			ss.setFLastMoney(g.getfVipPrice_student().multiply(
					new BigDecimal(nums)));
			ss.setSaleSheetDetailList(cartItemDataSSD);
			ConstantValue.cartSaleSureList.add(ss);
			goToSaleSheet();
		}
		// ConstantValue.cartSaleSheetList.clear();
		// ConstantValue.total_price_cart = ConstantValue.goodsinfo
		// .getfVipPrice_student().setScale(2, BigDecimal.ROUND_HALF_UP);
		// ConstantValue.total_Nums_cart = 1;
		// addtoLastCartList();
		// goToSaleSheet();
	}

	public class GetAddrFirstsheetTask extends
			AsyncTask<Context, ProgressBar, Boolean> {
		Context context;
		List<UserAddress> useraddrlist;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
				// 网络异常
				ConstantValue.myaddr = null;
			} else {
				ConstantValue.myaddr = useraddrlist.get(0);

			}
			Intent intent;
			intent = new Intent();
			intent.setClass(GoodSingleActivity.this, SalesheetActivity.class);
			startActivity(intent);
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
				// ----------获取是否首单
				String UserNo = ConstantValue.userinfo.getUserNo();
				String cStoreNo = ConstantValue.goodsinfo.getcStoreNo();
				FirstSheet firstSheet = new FirstSheet(UserNo, cStoreNo);
				ArrayList<FirstSheet> firstSheetlist = new ArrayList<FirstSheet>();

				firstSheetlist.add(firstSheet);
				Gson mygson = new Gson();
				String jsonStr = mygson.toJson(firstSheetlist);

				String msg1 = "*AIS|RQ|FirstSheet|00|" + jsonStr + "|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg1);
				HashMap<String, String> map1 = new HashMap<String, String>();
				map1.put("type", "post");
				map1.put("url", context.getString(R.string.servlet_firstsheet));
				map1.put("dataType", "text");
				map1.put("data", msg1);
				RequestVo vo1 = new RequestVo(R.string.servlet_firstsheet,
						context, map1);
				String result1 = NetUtil.post(vo1);
				// *AIS|RS|FirstSheet|00|Err|AIE#-----错误
				// *AIS|RS|FirstSheet|00|FALSE|AIE#----没有记录

				String[] temp1 = NetUtil.split(result1, "|");
				if (temp1 == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else if ("*AIS".equalsIgnoreCase(temp1[0])
						&& "RS".equals(temp1[1])
						&& "FirstSheet".equals(temp1[2])
						&& temp1[3].equals("01")) {

				}
				// 变量替换
				String msg = "*AIS|RQ|AD01|00|"
						+ ConstantValue.userinfo.getUserNo() + "|AIE#";

				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_ad01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_ad01, context, map);

				String result = NetUtil.post(vo);
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else

				if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
						&& "AD01".equals(temp[2]) && "01".equals(temp[3])) {
					Gson gson = new Gson();
					UserAddress userAddress = new UserAddress();
					useraddrlist = gson.fromJson(temp[4],
							new TypeToken<List<UserAddress>>() {
							}.getType());
					if (useraddrlist != null && useraddrlist.size() > 0) {
						return true;
					}

				}
			} catch (Exception e) {
				return false;
			}

			return false;
		}
	}

	public void goToSaleSheet() {
		new GetAddrFirstsheetTask().execute(GoodSingleActivity.this);

	}

	public void dengluqu() {
		PromptManager.showMyToast(GoodSingleActivity.this, "请您先登陆");
		Intent intent;
		intent = new Intent();
		intent.setClass(GoodSingleActivity.this, LoginActivity.class);
		startActivityForResult(intent, 101);
	}

	public void dengluqu2() {
		PromptManager.showMyToast(GoodSingleActivity.this, "请您先登陆");
		Intent intent;
		intent = new Intent();
		intent.setClass(GoodSingleActivity.this, LoginActivity.class);
		startActivityForResult(intent, 102);
	}

	public void addtoLastCartList(int nums) {

		SaleSheetDetail ssd = new SaleSheetDetail();
		Goods g = ConstantValue.goodsinfo;
		ssd.setdSaleDate(buyriqi);
		ssd.setcSaleSheetno(datessno);
		ssd.setMinIMG0(g.getMinIMG0());
		ssd.setcGoodsNo(g.getcGoodsNo());
		ssd.setcGoodsName(g.getcGoodsName());
		ssd.setfQuantity(new BigDecimal(nums));
		ssd.setfLastMoney(g.getfVipPrice_student());
		ArrayList<SaleSheetDetail> cartItemDataSSD = new ArrayList<SaleSheetDetail>();
		cartItemDataSSD.add(ssd);
		SaleSheet ss = new SaleSheet();
		ss.setcStoreNo(g.getcStoreNo());
		ss.setcStoreName(g.getcStoreName());
		ss.setDSaleDate(buyriqi);
		ss.setCSaleSheetno(datessno);
		ss.setUserNo(ConstantValue.userinfo.getUserNo());
		ss.setFLastMoney(g.getfVipPrice_student()
				.multiply(new BigDecimal(nums)));
		ss.setfMoney(g.getfVipPrice_student().multiply(new BigDecimal(nums)));
		ss.setSaleSheetDetailList(cartItemDataSSD);
		ConstantValue.cartSaleSheetList.add(ss);
	}

	public void jiarugouwuche(View v) {
		String gdnum = gd_num_edit.getText().toString();
		int nums = Integer.parseInt(gdnum);
		formatAllDate();
		if (!hasGoods) {
			return;
		}
		if (ConstantValue.userinfo == null) {
			dengluqu2();
		} else if (ConstantValue.cartSaleSheetList != null) {
			PromptManager.showMyToast(getApplicationContext(), "已经添加到购物车");
			int indexGoodsInCart = -1;
			// 查重cartSaleSheetList
			for (int i = 0; i < ConstantValue.cartSaleSheetList.size(); i++) {
				SaleSheet saleSheetItem = ConstantValue.cartSaleSheetList
						.get(i);

				if (saleSheetItem.getcStoreNo() != null
						&& ConstantValue.goodsinfo.getcStoreNo() != null
						&& saleSheetItem.getcStoreNo().equals(
								ConstantValue.goodsinfo.getcStoreNo())) {
					indexGoodsInCart = i;
					break;
				}
			}

			if (indexGoodsInCart != -1) {
				insertIntoCart(indexGoodsInCart, nums);
			} else {
				addtoLastCartList(nums);
			}
			ConstantValue.total_Nums_cart += 1;
			if (ConstantValue.total_Nums_cart != 0) {
				tv_pd_shuliang.setVisibility(View.GONE);
				tv_pd_shuliang.setText("" + ConstantValue.total_Nums_cart);
			} else {
				tv_pd_shuliang.setVisibility(View.GONE);
			}
		}
	}

	private void insertIntoCart(int indexGoodsInCart, int nums) {
		SaleSheet saleSheet = ConstantValue.cartSaleSheetList
				.get(indexGoodsInCart);
		List<SaleSheetDetail> saleSheetDetailList = saleSheet
				.getSaleSheetDetailList();
		int indexGoodsInSSD = -1;
		for (int j = 0; j < saleSheetDetailList.size(); j++) {
			SaleSheetDetail ssd1 = saleSheetDetailList.get(j);
			if (ssd1.getcGoodsNo()
					.equals(ConstantValue.goodsinfo.getcGoodsNo())) {
				indexGoodsInSSD = j;
				break;
			}

		}
		if (indexGoodsInSSD != -1) {
			SaleSheetDetail ssd = saleSheetDetailList.get(indexGoodsInSSD);
			BigDecimal q = ssd.getfQuantity();
			BigDecimal totle = q.add(new BigDecimal(nums));
			ssd.setfQuantity(totle);
		} else {
			SaleSheetDetail ssd = new SaleSheetDetail();
			Goods g = ConstantValue.goodsinfo;
			ssd.setdSaleDate(buyriqi);
			ssd.setcSaleSheetno(datessno);
			ssd.setMinIMG0(g.getMinIMG0());
			ssd.setcGoodsNo(g.getcGoodsNo());
			ssd.setcGoodsName(g.getcGoodsName());
			ssd.setfQuantity(new BigDecimal(nums));
			ssd.setfLastMoney(g.getfVipPrice_student());
			saleSheetDetailList.add(ssd);
		}
		BigDecimal fMoney = saleSheet.fLastMoney.add(ConstantValue.goodsinfo
				.getfVipPrice_student().multiply(new BigDecimal(nums)));
		saleSheet.fLastMoney = fMoney;
		saleSheet.fMoney = fMoney;

	}

	// 各种点击事件监听
	@Override
	public void onClick(View v) {
		switch (v.getId()) {

		case R.id.jia_cart_gd:
			String goodsnum = gd_num_edit.getText().toString();
			int i = Integer.parseInt(goodsnum) + 1;
			gd_num_edit.setText(i + "");
			break;
		case R.id.jian_cart_gd:
			String goodsnum2 = gd_num_edit.getText().toString();
			int i2 = Integer.parseInt(goodsnum2) - 1;
			if (i2 < 1) {
				i2 = 1;
			}
			gd_num_edit.setText(i2 + "");
			break;
		// 跳到cartMainActivity
		case R.id.cart_pd01_iv:

			Intent intent;
			intent = new Intent();
			Bundle extras = new Bundle();
			extras.putInt("indexMenu", 23);
			intent.putExtras(extras);
			intent.setClass(context, MainActivity.class);
			startActivity(intent);

			// Intent intent = new Intent();
			// intent.setClass(GoodSingleActivity.this, CartActivity.class);
			// startActivity(intent);
			break;
		// 退出GoodSingleActivity
		case R.id.back_goodsxq:
			finish();
			break;
		default:
			break;
		}
	}

	public class SPPD01 {
		public String SPActivity = "1"; // "SPActivity":"1" ,1促销中，0预期促销
		public String cStoreNo;// : cStoreNo:商店编码
		public String cGoodsNo;// : cGoodsNo:商品编码

	}
}
