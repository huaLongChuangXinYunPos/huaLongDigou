package com.hlcxdg.digou.activity.salesheet;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.CPUsing;
import com.hlcxdg.digou.bean.Coupon;
import com.hlcxdg.digou.bean.PAXiangdan;
import com.hlcxdg.digou.bean.PAZongdan;
import com.hlcxdg.digou.bean.SaleSheetDetail;
import com.hlcxdg.digou.constant.ConstantIP;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/*
 * 我的订单详情
 */
public class CouponPaywayActivity extends Activity {
	ListView cpouponListview;
	Context context;
	CouponAdapter couponAdapter;
	BigDecimal payOverMoney;
	BigDecimal money;
	Coupon coupon;

	// 回退
	public void goback(View v) {
		CouponPaywayActivity.this.finish();
	}

	// 初始化view
	public void initView() {
		cpouponListview = (ListView) findViewById(R.id.paycpoupon);
		couponAdapter = new CouponAdapter(ConstantValue.couponList, context);
		if (cpouponListview != null) {
			cpouponListview.setAdapter(couponAdapter);
			cpouponListview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> av, View v,
						int position, long pp) {
					money = ConstantValue.couponList.get(position).getFMoney();
					payOverMoney = ConstantValue.couponList.get(position)
							.getPayOverMoney();
					if (ConstantValue.myss.getfLastMoney().floatValue() >= payOverMoney
							.floatValue()) {
						coupon = ConstantValue.couponList.get(position);
						new PA02CPUsing().execute();
					} else {
						PromptManager.showMyToast(
								context,
								"支付失败，消费最低需要"
										+ payOverMoney.setScale(2,
												BigDecimal.ROUND_HALF_UP) + "元");
					}

				}
			});
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.paycouponlist);
		context = CouponPaywayActivity.this;
		initView();

		// new SaleSheetDetailMineTask().execute(CouponActivity.this);
	}

	/*
	 * 优惠券支付
	 */
	public class PA02CPUsing extends AsyncTask<Void, ProgressBar, Boolean> {
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
				BigDecimal mymoney = ConstantValue.myss.getFLastMoney();
				ConstantValue.myss.setFLastMoney(mymoney.subtract(money));
				PromptManager.showMyToast(context, "支付成功");
			} else {
				PromptManager.showMyToast(context, "支付失败");
			}

		}

		@Override
		protected Boolean doInBackground(Void... arg0) {
			//

			try {
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String datenow = sdf.format(d);
				List<PAXiangdan> pa02List = new ArrayList<PAXiangdan>();

				PAXiangdan paXiangdan = new PAXiangdan();
				paXiangdan.setcPayTime(datenow);
				paXiangdan
						.setcSaleSheetNo(ConstantValue.myss.getcSaleSheetno());
				paXiangdan.setfPayMoney(money);
				paXiangdan.setIlineNo("1");
				paXiangdan.setcPayStyleNo("09");
				paXiangdan.setcPayStyleDetail("");// 卡号或编号
				pa02List.add(paXiangdan);
				Gson gson = new Gson();
				String strgson = gson.toJson(pa02List);
				String msg = "*AIS|RQ|PA02|00|" + strgson + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url",
						context.getString(R.string.servlet_paysheetdetail));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_paysheetdetail,
						context, map);
				String result = NetUtil.post(vo);// *AIS|RS|PA02|01|[{"cSaleSheetNo":"1110481357598-10001","fPayMoney":20.0,"fUnPayMoney":14.0,"fLastMoney":34.0}]|AIE#
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else if ("*AIS".equalsIgnoreCase(temp[0])
						&& "RS".equals(temp[1]) && "PA02".equals(temp[2])
						&& "01".equals(temp[3])) {// &&
					// "01".equals(temp[3])
					// *AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#*AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#*AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#
					// *AIS|RQ| CPUsing |00|JSONData|AIE#-----提交使用优惠券
					CPUsing cPUsing = new CPUsing();
					cPUsing.setUserNO(ConstantValue.userinfo.getUserNo());
					cPUsing.setCouponNo(coupon.getCouponNo());
					cPUsing.setOwnerID(coupon.getOwnerID());
					cPUsing.setCouponType(coupon.getCouponType());
					cPUsing.setcStoreNo(coupon.getcStoreNo());
					cPUsing.FMoney = coupon.getFMoney();
					cPUsing.setcSaleSheetno(ConstantValue.myss
							.getCSaleSheetno());
					List<CPUsing> CPUsingList = new ArrayList<CPUsing>();

					CPUsingList.add(cPUsing);
					strgson = gson.toJson(CPUsingList);
					msg = "*AIS|RQ|CPUsing|00|" + strgson + "|AIE#";
					map.put("type", "post");
					map.put("url", context.getString(R.string.servlet_coupon));
					map.put("dataType", "text");
					map.put("data", msg);
					vo = new RequestVo(R.string.servlet_coupon, context, map);
					result = null;
					result = NetUtil.post(vo);// *AIS|RS|CPUsing|01|ok|AIE#
					// *AIS|RS|CPUsing|01|ok|AIE#*AIS|RS|CPUsing|01|ok|AIE#
					String[] temp2 = NetUtil.split(result, "|");
					if (temp2 == null) {
						PromptManager.showLogTest("enginenet",
								"net-result-null");
					} else if ("*AIS".equalsIgnoreCase(temp2[0])
							&& "RS".equals(temp2[1])
							&& "CPUsing".equals(temp2[2])
							&& "01".equals(temp2[3])) {
						return true;
					}
					return false;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}

}
