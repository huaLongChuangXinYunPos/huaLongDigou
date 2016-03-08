package com.hl_zhaoq.digou.activity.storeac;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.MainActivity;
import com.hl_zhaoq.digou.activity.address.AddressActivity;
import com.hl_zhaoq.digou.activity.goods.GoodSingleActivity;
import com.hl_zhaoq.digou.activity.user.LoginActivity;
import com.hl_zhaoq.digou.bean.Goods;
import com.hl_zhaoq.digou.bean.SaleSheet;
import com.hl_zhaoq.digou.bean.SaleSheetDetail;
import com.hl_zhaoq.digou.bean.StorePLParam;
import com.hl_zhaoq.digou.constant.ConstantIP;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/*��Ҫ��Ϊ��ʽ
 * ����������Ʒ������Ϣ
 */
public class StoreGoodsXqActivity extends Activity implements OnClickListener {
	TextView tv_storename_pd;
	TextView tv_goodname;
	TextView goods_price1;
	TextView goods_price2;
//	TextView goods_danwei;
//	TextView goods_guige;
//	TextView goods_shangjia;
	TextView goods_liebie;
	private SmartImageView goodicon_pd01_gs;
	ImageView cart_pd01_iv;
	Goods goods;
	TextView pd01_gs_top_ranking_tv;
	ImageView pd01_gs_new_goods_iv;
	private String datessno;
	private String buyriqi;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.goodsxiangqing);
		initView();
		SimpleDateFormat sdfssno = new SimpleDateFormat("MMddHHmmss");
		SimpleDateFormat sdfriqi = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		datessno = sdfssno.format(d);
		buyriqi = sdfriqi.format(d);
		new getStoreGoodsXq().execute("");
	}

	public void goback(View v) {
		StoreGoodsXqActivity.this.finish();
	}

	public void goAddAddress() {
		Intent intent;
		intent = new Intent();
		intent.setClass(StoreGoodsXqActivity.this, AddressActivity.class);
		startActivity(intent);
	}

	public void dengluqu() {
		PromptManager.showMyToast(StoreGoodsXqActivity.this, "请先登录");
		Intent intent;
		intent = new Intent();
		intent.setClass(StoreGoodsXqActivity.this, LoginActivity.class);
		startActivity(intent);
	}

	public void addtoLastCartList() {

		ConstantValue.total_Nums_cart = 1;
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
		ss.setDSaleDate(buyriqi);
		ss.setCSaleSheetno(datessno);
		ss.setcStoreNo(g.getcStoreNo());
		ss.setcStoreName(g.getcStoreName());
		ss.setUserNo(ConstantValue.userinfo.getUserNo());
		ss.setFLastMoney(g.getfVipPrice_student().setScale(2,
				BigDecimal.ROUND_HALF_UP));
		ss.setSaleSheetDetailList(cartItemDataSSD);
		ConstantValue.cartSaleSheetList.add(ss);
	}

	public void lijigou(View v) {
		if (ConstantValue.userinfo == null) {
			dengluqu();
		} else if (ConstantValue.cartSaleSureList != null) {
			ConstantValue.cartSaleSureList.clear();
			ConstantValue.total_price_cart = ConstantValue.goodsinfo
					.getfVipPrice_student().setScale(2,
							BigDecimal.ROUND_HALF_UP);
			ConstantValue.total_Nums_cart = 1;
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
			ss.setDSaleDate(buyriqi);
			ss.setCSaleSheetno(datessno);
			ss.setcStoreNo(g.getcStoreNo());
			ss.setcStoreName(g.getcStoreName());
			ss.setUserNo(ConstantValue.userinfo.getUserNo());
			ss.setFLastMoney(g.getfVipPrice_student().setScale(2,
					BigDecimal.ROUND_HALF_UP));
			ss.setSaleSheetDetailList(cartItemDataSSD);
			ConstantValue.cartSaleSureList.add(ss);
			goAddAddress();
		}

	}

	public void gouwu(View v) {
		if (ConstantValue.userinfo == null) {
			dengluqu();
		} else if (ConstantValue.cartSaleSheetList != null) {
			int indexGoodsInCart = -1;
			// ����cartSaleSheetList
			for (int i = 0; i < ConstantValue.cartSaleSheetList.size(); i++) {
				SaleSheet saleSheetItem = ConstantValue.cartSaleSheetList
						.get(i);
				if (saleSheetItem.getcStoreNo().equals(
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
			if (ssd1.getcGoodsNo()
					.equals(ConstantValue.goodsinfo.getcGoodsNo())) {
				indexGoodsInSSD = j;
				break;
			}

		}
		if (indexGoodsInSSD != -1) {
			SaleSheetDetail ssd = saleSheetDetailList.get(indexGoodsInSSD);
			BigDecimal q = ssd.getfQuantity();
			BigDecimal totle = q.add(new BigDecimal(1));
			ssd.setfQuantity(totle);
			saleSheetDetailList.add(indexGoodsInSSD, ssd);
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

		ConstantValue.cartSaleSheetList.add(indexGoodsInCart, saleSheet);
	}

	private void initView() {
		pd01_gs_top_ranking_tv = (TextView) findViewById(R.id.pd01_gs_top_ranking_tv1);
		pd01_gs_new_goods_iv = (ImageView) findViewById(R.id.pd01_gs_new_goods_iv);
		
		cart_pd01_iv = (ImageView) findViewById(R.id.cart_pd01_iv);
		cart_pd01_iv.setOnClickListener(this);
		goodicon_pd01_gs = (SmartImageView) findViewById(R.id.goodicon_pd01_gs);
		tv_storename_pd = (TextView) findViewById(R.id.tv_storename_pd);
		tv_goodname = (TextView) findViewById(R.id.tv_goodname);
		goods_price1 = (TextView) findViewById(R.id.goods_price1);
		goods_price2 = (TextView) findViewById(R.id.goods_price2);
//		goods_danwei = (TextView) findViewById(R.id.goods_danwei);
//		goods_guige = (TextView) findViewById(R.id.goods_guige);
//		goods_shangjia = (TextView) findViewById(R.id.goods_shangjia);
	}

	public class getStoreGoodsXq extends
			AsyncTask<String, ProgressBar, Integer> {
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(StoreGoodsXqActivity.this);
				break;
			case 0:
				PromptManager.showToast(StoreGoodsXqActivity.this,
						R.string.nodata);
				break;
			case 1:
				// shipeiqi
				if (ConstantValue.goodsinfo != null) {
					if (null!=goods.getO_Famousspecialty()) {
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
									.setScale(2, BigDecimal.ROUND_HALF_UP)
									.toString() + "元");
					goods_price2.setText("￥"
							+ goods.getfVipPrice_student()
									.setScale(2, BigDecimal.ROUND_HALF_UP)
									.toString() + "元");
//					goods_danwei.setText("个");
//					goods_guige.setText("个");
//					goods_shangjia.setText(goods.getcProductdDate().substring(0, 19));
					tv_storename_pd.setText(goods.getcStoreName());
				}
				break;
			default:
				break;
			}
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			PromptManager.showLogTest("PLAsynTaskdoInBackground", "PLAsynTask");
			try {
				// �����滻
				String stno = ConstantValue.storeItem.getcStoreNo();
				StorePLParam plp = new StorePLParam();
				ArrayList<StorePLParam> plList = new ArrayList<StorePLParam>();
				plList.add(plp);
				Gson gson = new Gson();
				String goodsno = ConstantValue.goodsinfo.getcGoodsNo();
				String msg = "*AIS|RQ|PD01|" + stno + "|" + goodsno + "|AIE#";
				PromptManager.showLogTest("PLAsynTaskdoInBackground-postmesssage",
						msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url",
						StoreGoodsXqActivity.this.getString(R.string.url_pl01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_pl01,
						StoreGoodsXqActivity.this, map);
				String result = NetUtil.post(vo);
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
							&& "PD01".equals(temp[2]) && "01".equals(temp[3])) {
						ArrayList<Goods> goodsList = gson.fromJson(temp[4],
								new TypeToken<List<Goods>>() {
								}.getType());
						if (goodsList != null) {
							goods = goodsList.get(0);
						}
						return 1;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cart_pd01_iv:
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

}
