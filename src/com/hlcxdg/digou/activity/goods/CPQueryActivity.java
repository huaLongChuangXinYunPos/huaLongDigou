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
import com.hlcxdg.digou.bean.CPGet;
import com.hlcxdg.digou.bean.CPUsing;
import com.hlcxdg.digou.bean.Coupon;
import com.hlcxdg.digou.bean.Goods;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/***
 * 18.1处于派发活动期间的优惠券查询 18.1.1请求指令 AIS|RQ|CPQuery|00|content|AIE#
 * AIS|RQ|CPQuery|00|null|AIE#
 * 
 * AIS:开始标志 RQ：请求类型 CP01：用户已经领取的优惠券 00: 占位(必须是00)
 * Content:内容，CPQuery查询没有用,用空的或null代替 AIE#：指令结束
 * 
 * 
 * 
 * 8.1.2请求响应 AIS|RS|CPQuery|00|content|AIE# AIS:开始标志 RS：请求类型 CPQuery：优惠券查询 00:
 * 占位(必须是00/01)，00查询没有记录，01查询有记录。 Content:
 * 响应内容，查询订单用JSON表示查询的结果是还没有使用的优惠券，每个useno只能领取一张。在领取前必须先登陆，也就是说要查询这个用户是否已经领取过
 * AIE#：指令结束
 * 
 * @author you
 * 
 */
public class CPQueryActivity extends Activity {
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
		context=CPQueryActivity.this;
		 new GetFirstSheetTask().execute();
//		new GetMM01Task().execute();

	}

	public class GetFirstSheetTask extends
			AsyncTask<String, ProgressBar, Boolean> {
	
		@Override
		protected Boolean doInBackground(String[] arg0) {
			try {
				ConstantValue.goodDatatemp.clear();
				final String cGoodsNo = ConstantValue.goodsinfo.getcGoodsNo()
						.trim();
				if (cGoodsNo == null) {
					return false;
				}
				String UserNo = "201509281430231022";
				String msg = "*AIS|RQ|FirstSheet|00|" + UserNo + "|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_firstsheet));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_firstsheet,
						context, map);
				String result = NetUtil.post(vo);
				// *AIS|RS|FirstSheet|00|Err|AIE#

				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1])
							&& "FirstSheet".equals(temp[2])
							&& temp[3].equals("01")) {

						// ConstantValue.isFirstSheet=true;
					} else {
						// ConstantValue.isFirstSheet=false;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	}

	// 20 是否是首单用户 FirstSheet
	// type : "post",//使用post方法访问后台
	// dataType : "text",//返回String格式的数据
	// url : "servlet：FirstSheet ",//要访问的后台地址(servlet)
	// data : {
	// CMD:*AIS|……|AIE#
	// },//要发送的数据
	// type : "post",//使用post方法访问后台
	// dataType : "text",//返回String格式的数据
	// url : "servlet：FirstSheet ",//要访问的后台地址(servlet)
	// data : {
	// PC: FirstSheet
	// CMD:*AIS|……|AIE#,
	// },//要发送的数据
	//
	// 20.1 FirstSheet：处于有效期或将来有效的促销活动查询
	// 20.1.1请求指令
	// *AIS|RQ| FirstSheet |00|UserNo|AIE#
	// *AIS:开始标志
	// RQ：请求类型
	// FirstSheet：用户是否是首单
	// 00: 占位(必须是00)
	// Content:内容，FirstSheet查询没有用,用空的或null代替
	// AIE#：指令结束
	//
	// 19.1.2请求响应
	// *AIS|RS| FirstSheet|01|TRUE/FALSE|AIE#
	// *AIS|RS| FirstSheet|01|ERR|AIE#
	//
	// *AIS:开始标志
	// RS：请求类型
	// FirstSheet：首单查询
	// AIE#：指令结束
	/**
	 * 优惠券查询
	 * 
	 * @author you
	 * 
	 */
	public class GetCPUserQueryTask extends
			AsyncTask<String, ProgressBar, Boolean> {
	
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
				String msg = "*AIS|RQ|CPUserQuery|00|null|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_coupon));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_coupon, context,
						map);
				String result = NetUtil.post(vo);
				// *AIS|RS|CPQuery|01|[{"cStoreNo":"10001","cStoreName":"乐尚","OwnerID":"01","CouponNo":"20151211809--00","CouponType":"02","Name":"满赠","FMoney":10.0000,"Qty":1000,"Qty_Left":1000,"Qty_Index":1,"Diqu":"  ","CGoodsTypeno":"0231","CGoodsTypename":"果汁","Riqi1":"2015-12-11 00:00:00.0","Riqi2":"2015-12-18 00:00:00.0","ValidDays":0,"CStatus":"0","CouponName":"购买满送优惠券","OwnerName":"店铺","PayOverMoney":100.0000},{"cStoreNo":"02","OwnerID":"02","CouponNo":"20151211856-02","CouponType":"01","Name":"注册","FMoney":10.0000,"Qty":100,"Qty_Left":100,"Qty_Index":1,"Diqu":"  ","CGoodsTypeno":"023","CGoodsTypename":"饮料","Riqi1":"2015-12-11 00:00:00.0","Riqi2":"2016-12-10 00:00:00.0","ValidDays":30,"CStatus":"1","CouponName":"注册赠送优惠券","OwnerName":"GoodsProvider"},{"cStoreNo":"00","OwnerID":"00","CouponNo":"201512112677-00","CouponType":"01","Name":"注册","FMoney":10.0000,"Qty":1000,"Qty_Left":22,"Qty_Index":6,"Diqu":"  ","CGoodsTypeno":"0224","CGoodsTypename":"豆奶/豆浆粉","Riqi1":"2015-12-11 00:00:00.0","Riqi2":"2015-12-18 00:00:00.0","ValidDays":0,"CStatus":"1","CouponName":"注册赠送优惠券","OwnerName":"O2O平台"},{"cStoreNo":"02","OwnerID":"02","CouponNo":"201512112505-00","CouponType":"03","Name":"特定节日","FMoney":20.0000,"Qty":100,"Qty_Left":100,"Qty_Index":1,"Diqu":"  ","CGoodsTypeno":"0224","CGoodsTypename":"豆奶/豆浆粉","Riqi1":"2015-12-11 00:00:00.0","Riqi2":"2016-01-23 00:00:00.0","ValidDays":0,"CStatus":"1","CouponName":"特定节日活动优惠券","OwnerName":"GoodsProvider"}]|AIE#

				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1])
							&& "CPUserQuery".equals(temp[2])
							&& temp[3].equals("01")) {
						Gson gson = new Gson();
						ArrayList<Coupon> couponList = gson.fromJson(temp[4],
								new TypeToken<List<Coupon>>() {
								}.getType());
						if (couponList != null) {
							// ConstantValue.goodDatatemp.addAll(goodsList);
							// ConstantValue.goodsinfo = goodsList.get(0);
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

	public class GetCPGetTask extends AsyncTask<String, ProgressBar, Boolean> {
		
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
				// -----------------------------------------------
				CPGet nCPGet = new CPGet();
				ArrayList<CPGet> couponList = new ArrayList<CPGet>();
				couponList.add(nCPGet);
				Gson myGson = new Gson();
				String content = myGson.toJson(couponList);
				String msg = "*AIS|RQ|CPGet|00|" + content + "|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_coupon));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_coupon, context,
						map);
				String result = NetUtil.post(vo);
				// *AIS|RS|CPGet|00|Have No Ticket To give|AIE#

				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "CPGet".equals(temp[2])
							&& temp[3].equals("01")) {
						Gson gson = new Gson();
						ArrayList<Coupon> couponLists = gson.fromJson(temp[4],
								new TypeToken<List<Coupon>>() {
								}.getType());
						if (couponList != null) {
							// ConstantValue.goodDatatemp.addAll(goodsList);
							// ConstantValue.goodsinfo = goodsList.get(0);
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

	public class GetCPUsingTask extends AsyncTask<String, ProgressBar, Boolean> {
		

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
				// -----------------------------------------------
				CPUsing myCPUsing = new CPUsing();
				ArrayList<CPUsing> myCPUsingList = new ArrayList<CPUsing>();
				myCPUsingList.add(myCPUsing);
				Gson myGson = new Gson();
				String content = myGson.toJson(myCPUsingList);
				String msg = "*AIS|RQ|CPUsing|00|" + content + "|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_coupon));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_coupon, context,
						map);
				String result = NetUtil.post(vo);
				// *AIS|RS|CPUsing|00|Have No Ticket|AIE#

				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1])
							&& "CPUsing".equals(temp[2])
							&& temp[3].equals("01")) {
						Gson gson = new Gson();
						ArrayList<Coupon> mycouponList = gson.fromJson(temp[4],
								new TypeToken<List<Coupon>>() {
								}.getType());
						if (mycouponList != null) {
							// ConstantValue.goodDatatemp.addAll(goodsList);
							// ConstantValue.goodsinfo = goodsList.get(0);
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

	public class GetCPQueryTask extends AsyncTask<String, ProgressBar, Boolean> {
		

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
				String msg = "*AIS|RQ|CPQuery|00|null|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_coupon));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_coupon, context,
						map);
				String result = NetUtil.post(vo);
				// *AIS|RS|CPQuery|01|[{"cStoreNo":"10001","cStoreName":"乐尚","OwnerID":"01","CouponNo":"20151211809--00","CouponType":"02","Name":"满赠","FMoney":10.0000,"Qty":1000,"Qty_Left":1000,"Qty_Index":1,"Diqu":"  ","CGoodsTypeno":"0231","CGoodsTypename":"果汁","Riqi1":"2015-12-11 00:00:00.0","Riqi2":"2015-12-18 00:00:00.0","ValidDays":0,"CStatus":"0","CouponName":"购买满送优惠券","OwnerName":"店铺","PayOverMoney":100.0000},{"cStoreNo":"02","OwnerID":"02","CouponNo":"20151211856-02","CouponType":"01","Name":"注册","FMoney":10.0000,"Qty":100,"Qty_Left":100,"Qty_Index":1,"Diqu":"  ","CGoodsTypeno":"023","CGoodsTypename":"饮料","Riqi1":"2015-12-11 00:00:00.0","Riqi2":"2016-12-10 00:00:00.0","ValidDays":30,"CStatus":"1","CouponName":"注册赠送优惠券","OwnerName":"GoodsProvider"},{"cStoreNo":"00","OwnerID":"00","CouponNo":"201512112677-00","CouponType":"01","Name":"注册","FMoney":10.0000,"Qty":1000,"Qty_Left":22,"Qty_Index":6,"Diqu":"  ","CGoodsTypeno":"0224","CGoodsTypename":"豆奶/豆浆粉","Riqi1":"2015-12-11 00:00:00.0","Riqi2":"2015-12-18 00:00:00.0","ValidDays":0,"CStatus":"1","CouponName":"注册赠送优惠券","OwnerName":"O2O平台"},{"cStoreNo":"02","OwnerID":"02","CouponNo":"201512112505-00","CouponType":"03","Name":"特定节日","FMoney":20.0000,"Qty":100,"Qty_Left":100,"Qty_Index":1,"Diqu":"  ","CGoodsTypeno":"0224","CGoodsTypename":"豆奶/豆浆粉","Riqi1":"2015-12-11 00:00:00.0","Riqi2":"2016-01-23 00:00:00.0","ValidDays":0,"CStatus":"1","CouponName":"特定节日活动优惠券","OwnerName":"GoodsProvider"}]|AIE#

				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1])
							&& "CPQuery".equals(temp[2])
							&& temp[3].equals("01")) {
						Gson gson = new Gson();
						ArrayList<Coupon> couponList = gson.fromJson(temp[4],
								new TypeToken<List<Coupon>>() {
								}.getType());
						if (couponList != null) {
							// ConstantValue.goodDatatemp.addAll(goodsList);
							// ConstantValue.goodsinfo = goodsList.get(0);
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

	// MM0115.1 0xMM01：满减首减免运费查询 ，处于有效期的
	// 15.1.1请求指令
	// *AIS|RQ|MM01|00|cStoreNo|AIE#
	// *AIS:开始标志
	// RQ：请求类型
	// MM01：
	// 00:占位(必须是00)
	// 00:占位
	// AIE#：指令结束
	public class GetMM01Task extends AsyncTask<Void, ProgressBar, Boolean> {

		@Override
		protected Boolean doInBackground(Void[] arg0) {
			try {
				// MM0115.1 0xMM01：满减首减免运费查询 ，处于有效期的
				String cStoreNo = "10002";
				String msg = "*AIS|RQ|MM01|00|" + cStoreNo + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_coupon));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_coupon, context,
						map);
				String result = NetUtil.post(vo);
				// *AIS|RS|CPQuery|01|[{"cStoreNo":"10001","cStoreName":"乐尚","OwnerID":"01","CouponNo":"20151211809--00","CouponType":"02","Name":"满赠","FMoney":10.0000,"Qty":1000,"Qty_Left":1000,"Qty_Index":1,"Diqu":"  ","CGoodsTypeno":"0231","CGoodsTypename":"果汁","Riqi1":"2015-12-11 00:00:00.0","Riqi2":"2015-12-18 00:00:00.0","ValidDays":0,"CStatus":"0","CouponName":"购买满送优惠券","OwnerName":"店铺","PayOverMoney":100.0000},{"cStoreNo":"02","OwnerID":"02","CouponNo":"20151211856-02","CouponType":"01","Name":"注册","FMoney":10.0000,"Qty":100,"Qty_Left":100,"Qty_Index":1,"Diqu":"  ","CGoodsTypeno":"023","CGoodsTypename":"饮料","Riqi1":"2015-12-11 00:00:00.0","Riqi2":"2016-12-10 00:00:00.0","ValidDays":30,"CStatus":"1","CouponName":"注册赠送优惠券","OwnerName":"GoodsProvider"},{"cStoreNo":"00","OwnerID":"00","CouponNo":"201512112677-00","CouponType":"01","Name":"注册","FMoney":10.0000,"Qty":1000,"Qty_Left":22,"Qty_Index":6,"Diqu":"  ","CGoodsTypeno":"0224","CGoodsTypename":"豆奶/豆浆粉","Riqi1":"2015-12-11 00:00:00.0","Riqi2":"2015-12-18 00:00:00.0","ValidDays":0,"CStatus":"1","CouponName":"注册赠送优惠券","OwnerName":"O2O平台"},{"cStoreNo":"02","OwnerID":"02","CouponNo":"201512112505-00","CouponType":"03","Name":"特定节日","FMoney":20.0000,"Qty":100,"Qty_Left":100,"Qty_Index":1,"Diqu":"  ","CGoodsTypeno":"0224","CGoodsTypename":"豆奶/豆浆粉","Riqi1":"2015-12-11 00:00:00.0","Riqi2":"2016-01-23 00:00:00.0","ValidDays":0,"CStatus":"1","CouponName":"特定节日活动优惠券","OwnerName":"GoodsProvider"}]|AIE#
				String[] temp = NetUtil.split(result, "|");

				if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
						&& "MM01".equals(temp[2]) && temp[3].equals("01")) {

					return true;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		}
	}

}
