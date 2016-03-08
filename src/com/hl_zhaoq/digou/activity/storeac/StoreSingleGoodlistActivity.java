package com.hl_zhaoq.digou.activity.storeac;

//136
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.MListView;
import com.hl_zhaoq.digou.activity.MainActivity;
import com.hl_zhaoq.digou.activity.goods.GoodSingleActivity;
import com.hl_zhaoq.digou.activity.goods.GoodsListPL01Activity;
import com.hl_zhaoq.digou.activity.user.LoginActivity;
import com.hl_zhaoq.digou.bean.Goods;
import com.hl_zhaoq.digou.bean.SaleSheet;
import com.hl_zhaoq.digou.bean.SaleSheetDetail;
import com.hl_zhaoq.digou.bean.StoreItem;
import com.hl_zhaoq.digou.bean.StoreListParam;
import com.hl_zhaoq.digou.bean.StorePLParam;
import com.hl_zhaoq.digou.bean.StoreType;
import com.hl_zhaoq.digou.constant.ConstantIP;
import com.hl_zhaoq.digou.constant.ConstantLocation;
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
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/*��Ҫ��Ϊ��ʽ
 * ���������б���Ϣ
 */
public class StoreSingleGoodlistActivity extends Activity implements
		OnClickListener {
	private ListView lv;
	private GridView gv;
	TextView storename_goodslist;
	Context context;
	private ImageView iv_change_view;

	public void goback(View v) {
		StoreSingleGoodlistActivity.this.finish();
	}

	public void toTopIndex(View view) {
		if (ConstantValue.goodData != null && ConstantValue.goodData.size() > 0) {
			lv.setSelection(0);
			gv.setSelection(0);
		}
	}

	public void tocart(View view) {

		Intent intent;
		intent = new Intent();
		Bundle extras = new Bundle();
		extras.putInt("indexMenu", 23);
		// intent.putIntegerArrayListExtra(name, value)Extra("indexMenu", 1);
		intent.putExtras(extras);

		intent.setClass(context, MainActivity.class);
		startActivity(intent);
		finish();
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.test_fujin_dandiangoodlist);
		context = StoreSingleGoodlistActivity.this;
		lv = (ListView) findViewById(R.id.st_lvgoodslist);
		gv = (GridView) findViewById(R.id.st_gvgoodslist);
		iv_change_view = (ImageView) findViewById(R.id.iv_change_view);
		iv_change_view.setOnClickListener(this);
		storename_goodslist = (TextView) findViewById(R.id.storename_goodslist);
		if (ConstantValue.storeItem != null) {
			storename_goodslist
					.setText(ConstantValue.storeItem.getcStoreName());
		}
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				ConstantValue.goodsinfo = ConstantValue.goodData.get(position);
				ConstantValue.goodsinfo.setcStoreNo(ConstantValue.storeItem
						.getcStoreNo());
				ConstantValue.goodsinfo.setcStoreName(ConstantValue.storeItem
						.getcStoreName());
				Intent i = new Intent();
				i.setClass(getApplicationContext(), GoodSingleActivity.class);
				// i.setClass(getApplicationContext(),
				// StoreGoodsXqActivity.class);
				startActivity(i);
			}
		});
		gv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				ConstantValue.goodsinfo = ConstantValue.goodData.get(position);
				ConstantValue.goodsinfo.setcStoreNo(ConstantValue.storeItem
						.getcStoreNo());
				ConstantValue.goodsinfo.setcStoreName(ConstantValue.storeItem
						.getcStoreName());
				Intent i = new Intent();
				i.setClass(getApplicationContext(), GoodSingleActivity.class);
				// i.setClass(getApplicationContext(),
				// StoreGoodsXqActivity.class);
				startActivity(i);
			}
		});

		new StoreGoodsListTask().execute("");
	}

	public class StoreSingleGoodlistlvAdapter extends BaseAdapter {
		SimpleDateFormat sdfssno;
		SimpleDateFormat sdfriqi;
		private String datessno;
		private String buyriqi;

		@Override
		public int getCount() {
			return ConstantValue.goodData.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		public void addtoLastCartList() {

			SaleSheetDetail ssd = new SaleSheetDetail();
			Goods g = ConstantValue.goodsinfo;
			ssd.setdSaleDate(buyriqi);
			ssd.setcSaleSheetno(datessno);
			ssd.setMinIMG0(g.getMinIMG0());
			ssd.setcGoodsNo(g.getcGoodsNo());
			ssd.setcGoodsName(g.getcGoodsName());
			ssd.setfQuantity(new BigDecimal("1"));
			ssd.setfLastMoney(g.getfVipPrice_student().setScale(2,
					BigDecimal.ROUND_HALF_UP));
			ArrayList<SaleSheetDetail> cartItemDataSSD = new ArrayList<SaleSheetDetail>();
			cartItemDataSSD.add(ssd);
			SaleSheet ss = new SaleSheet();
			ss.setcStoreNo(g.getcStoreNo());
			ss.setcStoreName(g.getcStoreName());
			ss.setDSaleDate(buyriqi);
			ss.setfMoney(g.getfVipPrice_student().setScale(2,
					BigDecimal.ROUND_HALF_UP));
			ss.setCSaleSheetno(datessno);
			ss.setUserNo(ConstantValue.userinfo.getUserNo());
			ss.setFLastMoney(g.getfVipPrice_student().setScale(2,
					BigDecimal.ROUND_HALF_UP));
			ss.setSaleSheetDetailList(cartItemDataSSD);
			ConstantValue.cartSaleSheetList.add(ss);
		}

		public void dengluqu() {
			PromptManager.showMyToast(context, "请您先登陆");
			Intent intent;
			intent = new Intent();
			intent.setClass(context, LoginActivity.class);
			context.startActivity(intent);
		}

		public void jiarugouwuche() {
			sdfssno = new SimpleDateFormat("MMddHHmmss");
			sdfriqi = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = new Date();
			datessno = sdfssno.format(d);
			buyriqi = sdfriqi.format(d);
			if (ConstantValue.userinfo == null) {
				dengluqu();
			} else if (ConstantValue.cartSaleSheetList != null) {
				PromptManager.showMyToast(context, "已经添加到购物车");
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
					insertIntoCart(indexGoodsInCart);
				} else {
					addtoLastCartList();
				}
				ConstantValue.total_Nums_cart += 1;
				// if (ConstantValue.total_Nums_cart != 0) {
				// tv_pd_shuliang.setVisibility(View.VISIBLE);
				// tv_pd_shuliang.setText("" + ConstantValue.total_Nums_cart);
				// } else {
				// tv_pd_shuliang.setVisibility(View.GONE);
				// }
			}
		}

		private void insertIntoCart(int indexGoodsInCart) {
			SaleSheet saleSheet = ConstantValue.cartSaleSheetList
					.get(indexGoodsInCart);
			List<SaleSheetDetail> saleSheetDetailList = saleSheet
					.getSaleSheetDetailList();
			int indexGoodsInSSD = -1;
			for (int j = 0; j < saleSheetDetailList.size(); j++) {
				SaleSheetDetail ssd1 = saleSheetDetailList.get(j);
				if (ssd1.getcGoodsNo().equals(
						ConstantValue.goodsinfo.getcGoodsNo())) {
					indexGoodsInSSD = j;
					break;
				}

			}
			if (indexGoodsInSSD != -1) {
				SaleSheetDetail ssd = saleSheetDetailList.get(indexGoodsInSSD);
				BigDecimal q = ssd.getfQuantity();
				BigDecimal totle = q.add(new BigDecimal(1));
				ssd.setfQuantity(totle);
			} else {
				SaleSheetDetail ssd = new SaleSheetDetail();
				Goods g = ConstantValue.goodsinfo;
				ssd.setdSaleDate(buyriqi);
				ssd.setcSaleSheetno(datessno);
				ssd.setMinIMG0(g.getMinIMG0());
				ssd.setcGoodsNo(g.getcGoodsNo());
				ssd.setcGoodsName(g.getcGoodsName());
				ssd.setfQuantity(new BigDecimal("1"));
				ssd.setfLastMoney(g.getfVipPrice_student().setScale(2,
						BigDecimal.ROUND_HALF_UP));
				saleSheetDetailList.add(ssd);
			}
			BigDecimal fMoney = saleSheet.fLastMoney
					.add(ConstantValue.goodsinfo.getfVipPrice_student()
							.setScale(2, BigDecimal.ROUND_HALF_UP));
			saleSheet.fLastMoney = fMoney;
			saleSheet.fMoney = fMoney;
		}

		@Override
		public View getView(final int position, View cv, ViewGroup par) {
			View view;
			TextView goodsname_pl_store;
			TextView store_saleprice_pl;
			TextView store_beforeprice_pl;
			SmartImageView goodicon_pl_store;
			TextView st_top_ranking_tv;
			ImageButton stpl00_cart_ib;
			ImageView st_new_goods_iv;
			view = View.inflate(getApplicationContext(),
					R.layout.storepl00goodlist_item, null);
			goodsname_pl_store = (TextView) view
					.findViewById(R.id.goodsname_pl_store);
			store_saleprice_pl = (TextView) view
					.findViewById(R.id.store_saleprice_pl);
			store_beforeprice_pl = (TextView) view
					.findViewById(R.id.store_beforeprice_pl);
			st_top_ranking_tv = (TextView) view
					.findViewById(R.id.st_top_ranking_tv);
			st_new_goods_iv = (ImageView) view
					.findViewById(R.id.st_new_goods_iv);
			stpl00_cart_ib = (ImageButton) view
					.findViewById(R.id.stpl00_cart_ib);
			stpl00_cart_ib.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					try {
						ConstantValue.goodsinfo = ConstantValue.goodData
								.get(position);
						jiarugouwuche();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			if (null != ConstantValue.goodData.get(position)
					.getO_Famousspecialty()) {
				st_top_ranking_tv.setVisibility(View.VISIBLE);
			}
			if ("1".equals(ConstantValue.goodData.get(position).getO_bNew())) {
				st_new_goods_iv.setVisibility(View.VISIBLE);
			}

			goodicon_pl_store = (SmartImageView) view
					.findViewById(R.id.goodicon_pl_store);

			goodsname_pl_store.setText(ConstantValue.goodData.get(position)
					.getcGoodsName());
			store_saleprice_pl.setText(ConstantValue.goodData.get(position)
					.getfVipPrice_student()
					.setScale(2, BigDecimal.ROUND_HALF_UP)
					+ "");
			store_beforeprice_pl.setText(ConstantValue.goodData.get(position)
					.getfNormalPrice().setScale(2, BigDecimal.ROUND_HALF_UP)
					+ "");
			goodicon_pl_store.setImageUrl(ConstantIP.ImageviewURL
					+ ConstantValue.goodData.get(position).getMinIMG0());
			//
			// if (ConstantValue.goodData.get(position).get) {
			// new_goods_iv.setVisibility(View.VISIBLE);
			// }
			return view;
		}

	}

	public class StoreSingleGoodlistgvAdapter extends BaseAdapter {
		SimpleDateFormat sdfssno;
		SimpleDateFormat sdfriqi;
		private String datessno;
		private String buyriqi;

		@Override
		public int getCount() {
			return ConstantValue.goodData.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		public void addtoLastCartList() {

			SaleSheetDetail ssd = new SaleSheetDetail();
			Goods g = ConstantValue.goodsinfo;
			ssd.setdSaleDate(buyriqi);
			ssd.setcSaleSheetno(datessno);
			ssd.setMinIMG0(g.getMinIMG0());
			ssd.setcGoodsNo(g.getcGoodsNo());
			ssd.setcGoodsName(g.getcGoodsName());
			ssd.setfQuantity(new BigDecimal("1"));
			ssd.setfLastMoney(g.getfVipPrice_student().setScale(2,
					BigDecimal.ROUND_HALF_UP));
			ArrayList<SaleSheetDetail> cartItemDataSSD = new ArrayList<SaleSheetDetail>();
			cartItemDataSSD.add(ssd);
			SaleSheet ss = new SaleSheet();
			ss.setcStoreNo(g.getcStoreNo());
			ss.setcStoreName(g.getcStoreName());
			ss.setDSaleDate(buyriqi);
			ss.setfMoney(g.getfVipPrice_student().setScale(2,
					BigDecimal.ROUND_HALF_UP));
			ss.setCSaleSheetno(datessno);
			ss.setUserNo(ConstantValue.userinfo.getUserNo());
			ss.setFLastMoney(g.getfVipPrice_student().setScale(2,
					BigDecimal.ROUND_HALF_UP));
			ss.setSaleSheetDetailList(cartItemDataSSD);
			ConstantValue.cartSaleSheetList.add(ss);
		}

		public void dengluqu() {
			PromptManager.showMyToast(context, "请您先登陆");
			Intent intent;
			intent = new Intent();
			intent.setClass(context, LoginActivity.class);
			context.startActivity(intent);
		}

		public void jiarugouwuche() {
			sdfssno = new SimpleDateFormat("MMddHHmmss");
			sdfriqi = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date d = new Date();
			datessno = sdfssno.format(d);
			buyriqi = sdfriqi.format(d);
			if (ConstantValue.userinfo == null) {
				dengluqu();
			} else if (ConstantValue.cartSaleSheetList != null) {
				PromptManager.showMyToast(context, "已经添加到购物车");
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
					insertIntoCart(indexGoodsInCart);
				} else {
					addtoLastCartList();
				}
				ConstantValue.total_Nums_cart += 1;
				// if (ConstantValue.total_Nums_cart != 0) {
				// tv_pd_shuliang.setVisibility(View.VISIBLE);
				// tv_pd_shuliang.setText("" + ConstantValue.total_Nums_cart);
				// } else {
				// tv_pd_shuliang.setVisibility(View.GONE);
				// }
			}
		}

		private void insertIntoCart(int indexGoodsInCart) {
			SaleSheet saleSheet = ConstantValue.cartSaleSheetList
					.get(indexGoodsInCart);
			List<SaleSheetDetail> saleSheetDetailList = saleSheet
					.getSaleSheetDetailList();
			int indexGoodsInSSD = -1;
			for (int j = 0; j < saleSheetDetailList.size(); j++) {
				SaleSheetDetail ssd1 = saleSheetDetailList.get(j);
				if (ssd1.getcGoodsNo().equals(
						ConstantValue.goodsinfo.getcGoodsNo())) {
					indexGoodsInSSD = j;
					break;
				}

			}
			if (indexGoodsInSSD != -1) {
				SaleSheetDetail ssd = saleSheetDetailList.get(indexGoodsInSSD);
				BigDecimal q = ssd.getfQuantity();
				BigDecimal totle = q.add(new BigDecimal(1));
				ssd.setfQuantity(totle);
			} else {
				SaleSheetDetail ssd = new SaleSheetDetail();
				Goods g = ConstantValue.goodsinfo;
				ssd.setdSaleDate(buyriqi);
				ssd.setcSaleSheetno(datessno);
				ssd.setMinIMG0(g.getMinIMG0());
				ssd.setcGoodsNo(g.getcGoodsNo());
				ssd.setcGoodsName(g.getcGoodsName());
				ssd.setfQuantity(new BigDecimal("1"));
				ssd.setfLastMoney(g.getfVipPrice_student().setScale(2,
						BigDecimal.ROUND_HALF_UP));
				saleSheetDetailList.add(ssd);
			}
			BigDecimal fMoney = saleSheet.fLastMoney
					.add(ConstantValue.goodsinfo.getfVipPrice_student()
							.setScale(2, BigDecimal.ROUND_HALF_UP));
			saleSheet.fLastMoney = fMoney;
			saleSheet.fMoney = fMoney;
		}

		@Override
		public View getView(final int position, View cv, ViewGroup par) {
			View view;
			TextView goodsname_pl_store;
			TextView store_saleprice_pl;
			TextView store_beforeprice_pl;
			SmartImageView goodicon_pl_store;
			TextView st_top_ranking_tv;
			ImageButton stpl00_cart_ib;
			ImageView st_new_goods_iv;
			view = View.inflate(getApplicationContext(),
					R.layout.storepl00goodlist_item_gv, null);
			goodsname_pl_store = (TextView) view
					.findViewById(R.id.goodsname_pl_store_gv);
			store_saleprice_pl = (TextView) view
					.findViewById(R.id.store_saleprice_pl_gv);

			st_top_ranking_tv = (TextView) view
					.findViewById(R.id.st_top_ranking_tv_gv);
			st_new_goods_iv = (ImageView) view
					.findViewById(R.id.st_new_goods_iv_gv);
			stpl00_cart_ib = (ImageButton) view
					.findViewById(R.id.stpl00_cart_ib_gv);
			stpl00_cart_ib.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					try {
						ConstantValue.goodsinfo = ConstantValue.goodData
								.get(position);
						jiarugouwuche();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});

			if (null != ConstantValue.goodData.get(position)
					.getO_Famousspecialty()) {
				st_top_ranking_tv.setVisibility(View.VISIBLE);
			}
			if ("1".equals(ConstantValue.goodData.get(position).getO_bNew())) {
				st_new_goods_iv.setVisibility(View.VISIBLE);
			}

			goodicon_pl_store = (SmartImageView) view
					.findViewById(R.id.goodicon_pl_store_gv);

			goodsname_pl_store.setText(ConstantValue.goodData.get(position)
					.getcGoodsName());
			store_saleprice_pl.setText(ConstantValue.goodData.get(position)
					.getfVipPrice_student()
					.setScale(2, BigDecimal.ROUND_HALF_UP)
					+ "");

			goodicon_pl_store.setImageUrl(ConstantIP.ImageviewURL
					+ ConstantValue.goodData.get(position).getMinIMG0());
			//
			// if (ConstantValue.goodData.get(position).get) {
			// new_goods_iv.setVisibility(View.VISIBLE);
			// }
			return view;
		}

	}

	private class StoreGoodsListTask extends
			AsyncTask<String, ProgressBar, Integer> {

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(StoreSingleGoodlistActivity.this);
				break;
			case 0:
				PromptManager.showToast(StoreSingleGoodlistActivity.this,
						R.string.nodata);
				break;
			case 1:
				// shipeiqi
				if (ConstantValue.goodData != null) {
					StoreSingleGoodlistlvAdapter stgllv = new StoreSingleGoodlistlvAdapter();
					lv.setAdapter(stgllv);
					StoreSingleGoodlistgvAdapter stglgv = new StoreSingleGoodlistgvAdapter();
					gv.setAdapter(stglgv);
				}

				break;

			default:
				break;
			}
			PromptManager.closeSpotsDialog();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(StoreSingleGoodlistActivity.this);// �ý��ȶԻ�����ʾ
		}

		@Override
		protected void onProgressUpdate(ProgressBar... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected Integer doInBackground(String[] cont) {
			// return 1;
			try {
				Boolean network = NetUtil
						.hasNetwork(StoreSingleGoodlistActivity.this);
				if (!network) {
					return -1;
				}
				PromptManager.showLogTest("PLAsynTaskdoInBackground",
						"PLAsynTask");
				// �����滻
				String plno = ConstantValue.storeItem.getcStoreNo();// "10001";

				StorePLParam plp = new StorePLParam();
				plp.setGroupNo3(ConstantValue.grouptype3list_goodno);
				plp.setStoreNo(ConstantValue.storeItem.getcStoreNo());
				ArrayList<StorePLParam> plList = new ArrayList<StorePLParam>();
				plList.add(plp);

				Gson gson = new Gson();
				String json = gson.toJson(plList);
				String msg = "*AIS|RQ|PL01|" + plno + "|" + json + "|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", StoreSingleGoodlistActivity.this
						.getString(R.string.url_pl01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_pl01,
						StoreSingleGoodlistActivity.this, map);

				String result = NetUtil.post(vo);
				// *AIS|RS|PL01|01|[{"cGoodsNo":"440005","cGoodsName":"Meadow Fresh纽麦福 全脂纯牛奶 1L 新西兰进口","cGoodsTypeno":"0211","cGoodsTypename":"纯牛奶","fNormalPrice":9.8000,"fVipPrice":9.8000,"fVipPrice_student":9.8000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440005.jpg"},{"cGoodsNo":"440004","cGoodsName":"伊利 百利包纯牛奶 200mlX16","cGoodsTypeno":"0211","cGoodsTypename":"纯牛奶","fNormalPrice":24.0000,"fVipPrice":24.0000,"fVipPrice_student":24.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440004.jpg"},{"cGoodsNo":"440007","cGoodsName":"Binggrae 宾格瑞 香蕉味牛奶饮料 200ml*6 韩国进口","cGoodsTypeno":"0211","cGoodsTypename":"纯牛奶","fNormalPrice":29.9000,"fVipPrice":29.9000,"fVipPrice_student":29.9000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440007.jpg"},{"cGoodsNo":"440008","cGoodsName":"三元 纯牛奶（小福） 227ml*16包","cGoodsTypeno":"0211","cGoodsTypename":"纯牛奶","fNormalPrice":30.8000,"fVipPrice":30.8000,"fVipPrice_student":30.8000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440008.jpg"},{"cGoodsNo":"440003","cGoodsName":"伊利 利乐枕纯牛奶(1*16*240ML)","cGoodsTypeno":"0211","cGoodsTypename":"纯牛奶","fNormalPrice":30.8000,"fVipPrice":30.8000,"fVipPrice_student":30.8000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440003.jpg"},{"cGoodsNo":"440009","cGoodsName":"MODERN FARMING现代牧业 纯牛奶 250ml*12盒礼盒装 6.24月新货","cGoodsTypeno":"0211","cGoodsTypename":"纯牛奶","fNormalPrice":39.9000,"fVipPrice":39.9000,"fVipPrice_student":39.9000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440009.jpg"},{"cGoodsNo":"440002","cGoodsName":"蒙牛特仑苏纯牛奶250ml x12","cGoodsTypeno":"0211","cGoodsTypename":"纯牛奶","fNormalPrice":55.0000,"fVipPrice":55.0000,"fVipPrice_student":55.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440002.jpg"},{"cGoodsNo":"440001","cGoodsName":"三元 小方白纯牛奶250ml*24包","cGoodsTypeno":"0211","cGoodsTypename":"纯牛奶","fNormalPrice":63.0000,"fVipPrice":63.0000,"fVipPrice_student":63.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440001.jpg"},{"cGoodsNo":"440010","cGoodsName":"伊利 金典有机纯牛奶12*250ml/盒","cGoodsTypeno":"0211","cGoodsTypename":"纯牛奶","fNormalPrice":66.5000,"fVipPrice":66.5000,"fVipPrice_student":66.5000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440010.jpg"}]|AIE#
				// *AIS|RS|PL01|01|[{"cGoodsNo":"440028","cGoodsName":"蒙牛 原麦早餐奶 250ml*2小盒 调制牛奶","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":6.0000,"fVipPrice":6.0000,"fVipPrice_student":6.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440028.jpg"},{"cGoodsNo":"440029","cGoodsName":"蒙牛 核桃早餐奶 250ml*2小盒 早餐丰富营养牛奶","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":6.0000,"fVipPrice":6.0000,"fVipPrice_student":6.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440029.jpg"},{"cGoodsNo":"440030","cGoodsName":"杨协成 黑豆豆奶 1L 马来西亚进口","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":11.0000,"fVipPrice":11.0000,"fVipPrice_student":11.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440030.jpg"},{"cGoodsNo":"440022","cGoodsName":"维他奶 黑豆奶 250ml*6/组","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":17.0000,"fVipPrice":17.0000,"fVipPrice_student":17.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440022.jpg"},{"cGoodsNo":"440023","cGoodsName":"维他奶 巧克力味豆奶饮料 250ml*6/组","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":17.0000,"fVipPrice":17.0000,"fVipPrice_student":17.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440023.jpg"},{"cGoodsNo":"440024","cGoodsName":"维他奶 香草味豆奶饮料 250ml*6/组","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":17.0000,"fVipPrice":17.0000,"fVipPrice_student":17.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440024.jpg"},{"cGoodsNo":"440021","cGoodsName":"维他奶 原味豆奶 250ml*16/箱","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":45.0000,"fVipPrice":45.0000,"fVipPrice_student":45.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440021.jpg"},{"cGoodsNo":"440025","cGoodsName":"维他奶 香草口味 豆奶 250ml×24盒/箱[Nwtn8011]","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":58.0000,"fVipPrice":58.0000,"fVipPrice_student":58.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440025.jpg"},{"cGoodsNo":"440026","cGoodsName":"维他奶 原味豆奶 250ml×24盒/箱[Nwtn8009]","cGoodsTypeno":"0213","cGoodsTypename":"豆奶/豆浆","fNormalPrice":58.0000,"fVipPrice":58.0000,"fVipPrice_student":58.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440026.jpg"}]|AIE#
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else

				if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
						&& "PL01".equals(temp[2]) && "01".equals(temp[3])) {
					ArrayList<Goods> goodsList = gson.fromJson(temp[4],
							new TypeToken<List<Goods>>() {
							}.getType());
					if (goodsList != null) {
						ConstantValue.goodData.clear();
						ConstantValue.goodData.addAll(goodsList);
					}
					return 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return 0;
			}
			return 0;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_change_view:
			if (lv.getVisibility() == View.VISIBLE) {
				iv_change_view.setImageResource(R.drawable.list_normal);
				lv.setVisibility(View.GONE);
				gv.setVisibility(View.VISIBLE);

			} else {
				iv_change_view.setImageResource(R.drawable.grid_normal);
				gv.setVisibility(View.GONE);
				lv.setVisibility(View.VISIBLE);
			}
			break;

		default:
			break;
		}
	}

}
