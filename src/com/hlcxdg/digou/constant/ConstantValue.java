package com.hlcxdg.digou.constant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.widget.ListView;

import com.hlcxdg.digou.activity.wallet.bean.BuyerR;
import com.hlcxdg.digou.bean.Coupon;
import com.hlcxdg.digou.bean.CouponYouhuiquan;
import com.hlcxdg.digou.bean.FirstSheet;
import com.hlcxdg.digou.bean.Goods;
import com.hlcxdg.digou.bean.O_bStoreInfo;
import com.hlcxdg.digou.bean.SaleSheet;
import com.hlcxdg.digou.bean.SaleSheetDetail;
import com.hlcxdg.digou.bean.StoreInfoqdBean;
import com.hlcxdg.digou.bean.StoreItem;
import com.hlcxdg.digou.bean.UserAddress;
import com.hlcxdg.digou.bean.UserCouponYouhq;
import com.hlcxdg.digou.bean.UserInfo;
import com.hlcxdg.digou.bean.UserNeedsBean;

public class ConstantValue {

	/**
	 * 优惠活动
	 */

	public static ArrayList<FirstSheet> isFirstSheetList=new ArrayList<FirstSheet>();
	
	public static ArrayList<Coupon> couponList=new ArrayList<Coupon>();
	
	
	
	/**
	 * 优惠活动
	 */

	public static StoreItem storeItem;
	public static List<O_bStoreInfo> youhuiStoreInfo;

	/**
	 */
	public static int spayway = 1;
	public static String ssdnos;
	// 用户全局信息
	public static UserInfo userinfo;
	public static Goods goodsinfo = new Goods();
	public static List<SaleSheet> SSList = new ArrayList<SaleSheet>();
//	public static List<UserCouponYouhq> usercouponList = new ArrayList<UserCouponYouhq>();
//支付时
	public static List<SaleSheet> saleSheet_dingdan = new ArrayList<SaleSheet>();
	public static List<SaleSheet> cartSaleSheetList = new ArrayList<SaleSheet>();
	public static List<SaleSheet> cartSaleSureList = new ArrayList<SaleSheet>();
	
	public static List<SaleSheet> saleSheetList = new ArrayList<SaleSheet>();
	public static String[] saleSheetNoForZhifubao;
	public static String tmp_goodNo = "220003";
	public static List<Map<String, String>> data;
	public static String sanleiid;
	public static List<Goods> goodData = new ArrayList<Goods>();
	public static List<Goods> goodDatatemp = new ArrayList<Goods>();

	public static List<Goods> cartgoodData = new ArrayList<Goods>();
	public static ListView goodlistview;
	public static String grouptype3list_goodno;
	public static String sgoodno;
	public static List<UserAddress> useraddrlist;
	public static UserAddress myaddr = new UserAddress();
	public static String locValue;
	public static Goods oneGood;
	public static int total_Nums_cart = 0;
	public static BigDecimal total_price_cart;
	public static SaleSheet myss;

	public static List<StoreInfoqdBean> storeqdList = new ArrayList<StoreInfoqdBean>();
	public static List<StoreInfoqdBean> storeqdListBaojia = new ArrayList<StoreInfoqdBean>();
	public static String storeqdListBaojiaStr = "";
	public static StoreInfoqdBean storeInfoqdok;
	public static UserNeedsBean userInfoqdok;
	public static List<UserNeedsBean> userqdList = new ArrayList<UserNeedsBean>();
	public static BigDecimal lostWMoney;
	public static boolean isWLogin = false;
	public static String cstoreNo;
	public static BuyerR wBuyerR;
}
