package com.hlcxdg.digou.activity.cuxiao;

//136
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.MListView;
import com.hlcxdg.digou.activity.MainActivity;
import com.hlcxdg.digou.activity.cuxiao.GoodSingleActivity;
import com.hlcxdg.digou.activity.goods.GoodsListPL01Activity;
import com.hlcxdg.digou.activity.user.LoginActivity;
import com.hlcxdg.digou.bean.Goods;
import com.hlcxdg.digou.bean.SPStorePLParam;
import com.hlcxdg.digou.bean.SaleSheet;
import com.hlcxdg.digou.bean.SaleSheetDetail;
import com.hlcxdg.digou.bean.StoreItem;
import com.hlcxdg.digou.bean.StoreListParam;
import com.hlcxdg.digou.bean.StorePLParam;
import com.hlcxdg.digou.bean.StoreType;
import com.hlcxdg.digou.constant.ConstantIP;
import com.hlcxdg.digou.constant.ConstantLocation;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;
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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/*��Ҫ��Ϊ��ʽ
 * ���������б���Ϣ
 */
public class StoreSingleGoodlistActivity extends Activity {
	private ListView lv;
	TextView storename_goodslist;
	Context context;

	public void goback(View v) {
		StoreSingleGoodlistActivity.this.finish();
	}
	public void toTopIndex(View view) {

		lv.setSelection(0);
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

		new StoreGoodsListTask().execute("");
	}

	public class StoreSingleGoodlistAdapter extends BaseAdapter {
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
			ss.setCSaleSheetno(datessno);
			ss.setUserNo(ConstantValue.userinfo.getUserNo());
			ss.setFLastMoney(g.getfVipPrice_student().setScale(2,
					BigDecimal.ROUND_HALF_UP));
			ss.setfMoney(g.getfVipPrice_student().setScale(2,
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
			ImageButton stpl00_cart_ib;
			TextView st_top_ranking_tv;
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
					StoreSingleGoodlistAdapter stgl = new StoreSingleGoodlistAdapter();
					lv.setAdapter(stgl);

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
				String cStoreNos = ConstantValue.storeItem.getcStoreNo();// "10001";

				SPStorePLParam plp = new SPStorePLParam();
				plp.setGroupNo3(ConstantValue.grouptype3list_goodno);
				plp.setStoreNo(cStoreNos);
				plp.IndexNo = 0;

				ArrayList<SPStorePLParam> plList = new ArrayList<SPStorePLParam>();
				plList.add(plp);

				Gson gson = new Gson();
				String json = gson.toJson(plList);
				// *AIS|RQ|SPPL01| cStore|JSON数据|AIE#
				String msg = "*AIS|RQ|SPPL01|" + cStoreNos + "|" + json
						+ "|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", StoreSingleGoodlistActivity.this
						.getString(R.string.url_sppl01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_sppl01,
						StoreSingleGoodlistActivity.this, map);

				String result = NetUtil.post(vo);
				// *AIS|RS|SPPL01|01|[{"cGoodsNo":"440001","cGoodsName":"三元 小方白纯牛奶250ml*24包","cGoodsTypeno":"0211","cGoodsTypename":"纯牛奶","fNormalPrice":63.0000,"fVipPrice":63.0000,"fVipPrice_student":50.0000,"O_PriceFlag":" ","MinIMG0":"/DOGOServer/pic/b440001.jpg"}]|AIE#

				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else

				if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
						&& "SPPL01".equals(temp[2]) && "01".equals(temp[3])) {
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
}
