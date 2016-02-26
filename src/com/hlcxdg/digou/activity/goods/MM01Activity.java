package com.hlcxdg.digou.activity.goods;

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
import com.hlcxdg.digou.bean.Goods;
import com.hlcxdg.digou.bean.SaleSheet;
import com.hlcxdg.digou.bean.SaleSheetDetail;
import com.hlcxdg.digou.bean.UserAddress;
import com.hlcxdg.digou.bean.Youhui;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
/*
 * 满首赠查询
 */
public class MM01Activity extends Activity implements OnClickListener {
	private TextView tv_goodname;
	private TextView goods_price1;
	private TextView goods_price2;
//	private TextView goods_danwei;
//	private TextView goods_guige;
//	private TextView goods_shangjia;
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
		context = MM01Activity.this;
		hasGoods = true;
		formatAllDate();
		sc = (ScrollView) findViewById(R.id.sc);
		loading_bg = (RelativeLayout) findViewById(R.id.loading_bg);
		new GetMM01Task(context).execute();

	}

	@Override
	protected void onResume() {
		super.onResume();
		initView();
	}

	// 格式化时间数据
	public void formatAllDate() {
		SimpleDateFormat sdfssno = new SimpleDateFormat("MMddHHmmss");
		SimpleDateFormat sdfriqi = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		datessno = sdfssno.format(d);
		buyriqi = sdfriqi.format(d);
	}

	public class GetMM01Task extends AsyncTask<String, ProgressBar, Boolean> {
		Context context;
		ListView list;

		public GetMM01Task(Context context) {
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
				String msg = "*AIS|RQ|MM01|00|10001|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_mansouman));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_mansouman, context, map);
				String result = NetUtil.post(vo);
				//*AIS|RS|MM01|01|[{"cStoreNo":"10001","OverCut":"man200jian10","FirstSheet":"100","GiftFree":"zengsong","Over_Money":200.0000,"Cut_Money":10.0000,"FirstSheet_Over":100.0000,"FirstSheet_Cut":10.0000,"GiftFree_GiftGoodsNo":"111","GiftFree_GiftMoney":200.0000,"PeisongFee_Over_Free":300.0000,"BasicPrice":10.0000,"PeisongFee":5.0000}]|AIE#

				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "MM01".equals(temp[2])
							&& temp[3].equals("01")) {
						Gson gson = new Gson();
						ArrayList<Youhui> youhuiList = gson.fromJson(temp[4],
								new TypeToken<List<Youhui>>() {
								}.getType());
						if (youhuiList != null) {
//							ConstantValue.goodDatatemp.addAll(goodsList);
//							ConstantValue.goodsinfo = goodsList.get(0);
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

		pd01_gs_top_ranking_tv = (TextView) findViewById(R.id.pd01_gs_top_ranking_tv1);
		pd01_gs_new_goods_iv = (ImageView) findViewById(R.id.pd01_gs_new_goods_iv);
		tv_pd_shuliang = (TextView) findViewById(R.id.tv_pd_shuliang);
		if (ConstantValue.total_Nums_cart != 0) {
			tv_pd_shuliang.setVisibility(View.VISIBLE);
			tv_pd_shuliang.setText("" + ConstantValue.total_Nums_cart);
		} else {
			tv_pd_shuliang.setVisibility(View.GONE);
		}
		tv_storename_pd = (TextView) findViewById(R.id.tv_storename_pd);
//		goods_shangjia = (TextView) findViewById(R.id.goods_shangjia);
		back_goodsxq = (ImageView) findViewById(R.id.back_goodsxq);
		cart_pd01_iv = (ImageView) findViewById(R.id.cart_pd01_iv);
		cart_pd01_iv.setOnClickListener(this);
		back_goodsxq.setOnClickListener(this);
		goodicon_pd01_gs = (SmartImageView) findViewById(R.id.goodicon_pd01_gs);
		tv_goodname = (TextView) findViewById(R.id.tv_goodname);
		goods_price1 = (TextView) findViewById(R.id.goods_price1);
		goods_price2 = (TextView) findViewById(R.id.goods_price2);
//		goods_danwei = (TextView) findViewById(R.id.goods_danwei);
//		goods_guige = (TextView) findViewById(R.id.goods_guige);

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
//			goods_danwei.setText("件");
//			goods_guige.setText("件");
//			goods_shangjia.setText(goods.getcProductdDate());
			tv_storename_pd.setText(goods.getcStoreName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void goback(View v) {
		MM01Activity.this.finish();
	}

	public void lijigoumai(View v) {
		if (!hasGoods) {
			return;
		}
		if (ConstantValue.userinfo == null) {
			dengluqu();
		} else if (ConstantValue.cartSaleSheetList != null) {
			ConstantValue.cartSaleSheetList.clear();
			ConstantValue.total_price_cart = ConstantValue.goodsinfo
					.getfVipPrice_student().setScale(2,
							BigDecimal.ROUND_HALF_UP);
			ConstantValue.total_Nums_cart = 1;
			addtoLastCartList();
			goToSaleSheet();
		}
	}

	public class AddrAsynTask extends AsyncTask<Context, ProgressBar, Boolean> {
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
			intent.setClass(MM01Activity.this, SalesheetActivity.class);
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
				PromptManager.showLogTest("SalesheetActivity",
						"doInBackground-yuwang");
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
		new AddrAsynTask().execute(MM01Activity.this);

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
		PromptManager.showMyToast(MM01Activity.this, "请您先登陆");
		Intent intent;
		intent = new Intent();
		intent.setClass(MM01Activity.this, LoginActivity.class);
		startActivity(intent);
	}

	public void jiarugouwuche(View v) {
		if (!hasGoods) {
			return;
		}
		if (ConstantValue.userinfo == null) {
			dengluqu();
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
				insertIntoCart(indexGoodsInCart);
			} else {
				addtoLastCartList();
			}
			ConstantValue.total_Nums_cart += 1;
			if (ConstantValue.total_Nums_cart != 0) {
				tv_pd_shuliang.setVisibility(View.VISIBLE);
				tv_pd_shuliang.setText("" + ConstantValue.total_Nums_cart);
			} else {
				tv_pd_shuliang.setVisibility(View.GONE);
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
		BigDecimal fMoney = saleSheet.fLastMoney.add(ConstantValue.goodsinfo
				.getfVipPrice_student().setScale(2, BigDecimal.ROUND_HALF_UP));
		saleSheet.fLastMoney = fMoney;
		saleSheet.fMoney = fMoney;
	}

	// 各种点击事件监听
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 跳到cartMainActivity
		case R.id.cart_pd01_iv:
			Intent intent = new Intent();
			intent.setClass(MM01Activity.this, CartActivity.class);
			startActivity(intent);
			break;
		// 退出GoodSingleActivity
		case R.id.back_goodsxq:
			finish();
			break;
		default:
			break;
		}
	}

}
