package com.hlcxdg.digou.activity.coupon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.CPUsing;
import com.hlcxdg.digou.bean.Coupon;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CouponActivity extends Activity implements OnClickListener {
	ListView cpoupon;
	Context context;
	ImageView coupon_empty_state_iv;
	Button bt_coupon_could;
	Button bt_coupon_had;
	Coupon coupon;
	int state = 1;
	List<Coupon> couponList;

	public void init() {
		coupon_empty_state_iv = (ImageView) findViewById(R.id.coupon_empty_state_iv);

		cpoupon = (ListView) findViewById(R.id.cpoupon);
		bt_coupon_could = (Button) findViewById(R.id.bt_coupon_could);
		bt_coupon_had = (Button) findViewById(R.id.bt_coupon_had);
		bt_coupon_could.setOnClickListener(this);
		bt_coupon_had.setOnClickListener(this);
	}

	public void goback(View view) {
		CouponActivity.this.finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.couponlist);
		context = CouponActivity.this;
		state = 1;
		services();

	}

	public void services() {
		init();
		couponList = new ArrayList<Coupon>();
		mybase1 = new MyBaseAdapter1();
		cpoupon.setAdapter(mybase1);
		new GetCPQuery().execute(1);
		cpoupon.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long p) {

				if (state == 1) {
					coupon = couponList.get(position);
					new GetCPGet().execute();
				}

			}
		});
	}

	// GetCPGet 领取优惠券，得到礼券
	public class GetCPGet extends AsyncTask<Void, ProgressBar, Boolean> {
		ListView list;
		// C:\Users\Administrator\Downloads\新建文件夹\取色器
		String msgresult;

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			// PromptManager.closeProgressDialog();
			if (!result) {

				PromptManager.showMyToast(context, msgresult);
			} else {
				PromptManager.showMyToast(context, msgresult);
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}

		SharedPreferences sp;

		@Override
		protected Boolean doInBackground(Void[] arg0) {
			try {

				// -----------------------------------------------
				CPUsing myCPUsing = new CPUsing();
				myCPUsing.setOwnerID(coupon.getOwnerID());
				myCPUsing.setCouponType(coupon.getCouponType());
				myCPUsing.setCouponNo(coupon.getCouponNo());
				myCPUsing.setcStoreNo(coupon.getcStoreNo());
				myCPUsing.setUserNO(ConstantValue.userinfo.getUserNo());
				// --------------------------------------------------------userNo假数据
				// myCPUsing.setUserNO("201509241217045962");
				ArrayList<CPUsing> myCPUsingList = new ArrayList<CPUsing>();
				myCPUsingList.add(myCPUsing);
				Gson myGson = new Gson();
				String content = myGson.toJson(myCPUsingList);
				String msg = "*AIS|RQ|CPGet|00|" + content + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_coupon));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_coupon, context,
						map);
				String result = NetUtil.post(vo);
				// *AIS|RS|CPGet|00|Have Got Ticket|AIE#
				// *AIS|RS|CPUsing|00|Have No Ticket|AIE#
				// *AIS|RS|CPUserQuery|00||AIE#
				// *AIS|RS|CPUserQuery|01|[{"CouponNo":"201512182417-10001","cStoreNo":"10001",
				// "cStoreName":"乐尚","OwnerID":"01","OwnerName":"店铺","CouponType":"03",
				// "CouponName":"特定节日活动优惠券","Name":"店铺优惠","FMoney":10.0000,"TicketIndex":1,
				// "CGoodsTypeno":"02","CGoodsTypename":"奶/饮料/酒","Status":"1","Diqu":"  ",
				// "Riqi1":"2015-12-18 00:00:00.0","Riqi2":"2016-12-22 15:42:47.0","Beizhu":"null","Used":false}]|AIE#
				// *AIS|RS|CPQuery|01|[
				// {"cStoreNo":"10001","cStoreName":"乐尚","OwnerID":"01","CouponNo":"201512182417-10001",
				// "CouponType":"03","Name":"店铺优惠","FMoney":10.0000,
				// "Qty":50,"Qty_Left":50,"Qty_Index":4,"Diqu":"  ",
				// "CGoodsTypeno":"02","CGoodsTypename":"奶/饮料/酒",
				// "Riqi1":"2015-12-18 00:00:00.0","Riqi2":"2016-12-25 00:00:00.0",
				// "ValidDays":5,"CStatus":"1","CouponName":"特定节日活动优惠券","OwnerName":"店铺"},
				//
				// {"cStoreNo":"00","OwnerID":"00","CouponNo":"201512187633-00","CouponType":"01","Name":"注册","FMoney":5.0000,"Qty":100,"Qty_Left":100,"Qty_Index":5,"Diqu":"  ","CGoodsTypeno":"02","CGoodsTypename":"奶/饮料/酒","Riqi1":"2015-12-18 00:00:00.0","Riqi2":"2016-12-25 00:00:00.0","ValidDays":5,"CStatus":"1","CouponName":"注册赠送优惠券","OwnerName":"O2O平台"}]|AIE#

				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "CPGet".equals(temp[2])
							&& temp[3].equals("01")) {
						msgresult = "领取成功";

						return true;

					} else {
						if (temp[4].equalsIgnoreCase("Have No Ticket To give")) {
							msgresult = "失败,没有券可领取!";
						}
						if (temp[4].equalsIgnoreCase("Have Got Ticket")) {
							msgresult = "失败,已经领取过了!";
						}
						if (temp[4].equalsIgnoreCase("Have Used Ticket")) {
							msgresult = "失败,已经领取并使用了!";
						}
						if (temp[4].equalsIgnoreCase("Fail")) {
							msgresult = "失败,领取失败!";
						}
						if (temp[4].equalsIgnoreCase("Err")) {
							msgresult = "失败,领取异常!";
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;

		}
	}

	public class GetCPQuery extends AsyncTask<Integer, ProgressBar, Boolean> {
		ListView list;

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			// PromptManager.closeProgressDialog();
			if (!result) {
				PromptManager.showMyToast(context, "暂时没有礼券");
				coupon_empty_state_iv.setVisibility(View.VISIBLE);
			} else if (couponList != null && couponList.size() > 0) {
				coupon_empty_state_iv.setVisibility(View.GONE);
			}
			mybase1.notifyDataSetChanged();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}

		SharedPreferences sp;

		@Override
		protected Boolean doInBackground(Integer[] i) {
			try {
				couponList.clear();
				switch (i[0]) {
				case 1:
					// 不是自己的 是全部礼券
					String msg = "*AIS|RQ|CPQuery|00|null|AIE#";
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("type", "post");
					map.put("url", context.getString(R.string.servlet_coupon));
					map.put("dataType", "text");
					map.put("data", msg);
					RequestVo vo = new RequestVo(R.string.servlet_coupon,
							context, map);
					String result = NetUtil.post(vo);
					// *AIS|RS|CPQuery|01|[
					// {"cStoreNo":"10001","cStoreName":"乐尚","OwnerID":"01","CouponNo":"201512182417-10001",
					// "CouponType":"03","Name":"店铺优惠","FMoney":10.0000,
					// "Qty":50,"Qty_Left":50,"Qty_Index":4,"Diqu":"  ",
					// "CGoodsTypeno":"02","CGoodsTypename":"奶/饮料/酒",
					// "Riqi1":"2015-12-18 00:00:00.0","Riqi2":"2016-12-25 00:00:00.0",
					// "ValidDays":5,"CStatus":"1","CouponName":"特定节日活动优惠券","OwnerName":"店铺"},
					//
					// {"cStoreNo":"00","OwnerID":"00","CouponNo":"201512187633-00","CouponType":"01","Name":"注册","FMoney":5.0000,"Qty":100,"Qty_Left":100,"Qty_Index":5,"Diqu":"  ","CGoodsTypeno":"02","CGoodsTypename":"奶/饮料/酒","Riqi1":"2015-12-18 00:00:00.0","Riqi2":"2016-12-25 00:00:00.0","ValidDays":5,"CStatus":"1","CouponName":"注册赠送优惠券","OwnerName":"O2O平台"}]|AIE#

					String[] temp = NetUtil.split(result, "|");
					if (temp == null) {
						PromptManager.showLogTest("enginenet",
								"net-result-null");
					} else {
						if ("*AIS".equalsIgnoreCase(temp[0])
								&& "RS".equals(temp[1])
								&& "CPQuery".equals(temp[2])
								&& temp[3].equals("01")) {
							Gson gson = new Gson();
							ArrayList<Coupon> mycouponList = gson.fromJson(
									temp[4], new TypeToken<List<Coupon>>() {
									}.getType());
							if (mycouponList != null && mycouponList.size() > 0) {

								couponList = mycouponList;
								return true;
							}

						}
					}
					break;
				case 2:
					// 我的优惠券
					CPUsing myCPUsing = new CPUsing();
					// myCPUsing.setcStoreNo(ConstantValue.myss.getcStoreNo());
					// // myCPUsing.setcSaleSheetno(ss.getCSaleSheetno());
					// myCPUsing.setUserNO(ConstantValue.userinfo.getUserNo());
					// 假数据
					myCPUsing.setUserNO(ConstantValue.userinfo.getUserNo());
					myCPUsing.cStoreNo = "00";

					ArrayList<CPUsing> myCPUsingList = new ArrayList<CPUsing>();
					myCPUsingList.add(myCPUsing);
					Gson myGson = new Gson();
					String content = myGson.toJson(myCPUsingList);
					String msg2 = "*AIS|RQ|CPUserQuery|00|" + content + "|AIE#";
					HashMap<String, String> map2 = new HashMap<String, String>();
					map2.put("type", "post");
					map2.put("url", context.getString(R.string.servlet_coupon));
					map2.put("dataType", "text");
					map2.put("data", msg2);
					RequestVo vo2 = new RequestVo(R.string.servlet_coupon,
							context, map2);
					String result2 = NetUtil.post(vo2);
					// *AIS|RS|CPUsing|00|Have No Ticket|AIE#
					// *AIS|RS|CPUserQuery|00||AIE#
					// *AIS|RS|CPUserQuery|01|[{"CouponNo":"201512182417-10001","cStoreNo":"10001",
					// "cStoreName":"乐尚","OwnerID":"01","OwnerName":"店铺","CouponType":"03",
					// "CouponName":"特定节日活动优惠券","Name":"店铺优惠","FMoney":10.0000,"TicketIndex":1,
					// "CGoodsTypeno":"02","CGoodsTypename":"奶/饮料/酒","Status":"1","Diqu":"  ",
					// "Riqi1":"2015-12-18 00:00:00.0","Riqi2":"2016-12-22 15:42:47.0","Beizhu":"null","Used":false}]|AIE#

					String[] temp2 = NetUtil.split(result2, "|");
					if (temp2 == null) {
						PromptManager.showLogTest("enginenet",
								"net-result-null");
					} else {
						if ("*AIS".equalsIgnoreCase(temp2[0])
								&& "RS".equals(temp2[1])
								&& "CPUserQuery".equals(temp2[2])
								&& temp2[3].equals("01")) {
							Gson gson = new Gson();
							ArrayList<Coupon> mycouponList = gson.fromJson(
									temp2[4], new TypeToken<List<Coupon>>() {
									}.getType());
							if (mycouponList != null && mycouponList.size() > 0) {

								couponList = mycouponList;
								return true;
							}

						}
					}
					break;
				default:
					break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;

		}
	}

	public class MyBaseAdapter1 extends BaseAdapter {

		@Override
		public int getCount() {
			return couponList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			View view;
			TextView coupon_item_name;
			TextView fmoney;
			TextView coupon_item_date;
			// TextView riqi1;
			// TextView riqi2;
			view = View.inflate(CouponActivity.this, R.layout.coupon_item_pic,
					null);
			try {
				Coupon cCoupon = couponList.get(position);
				coupon_item_name = (TextView) view
						.findViewById(R.id.coupon_item_name);
				fmoney = (TextView) view.findViewById(R.id.coupon_item_money);
				coupon_item_date = (TextView) view
						.findViewById(R.id.coupon_item_date);
				// riqi1 = (TextView) view.findViewById(R.id.riqi1);
				// riqi2 = (TextView) view.findViewById(R.id.riqi2);
				coupon_item_name.setText(cCoupon.getName());
				fmoney.setText("￥" + cCoupon.getFMoney().intValue());
				coupon_item_date.setText(cCoupon.getRiqi1().substring(0, 10)
						+ "~" + cCoupon.getRiqi2().substring(0, 10));
				// riqi1.setText(ConstantValue.couponList.get(position).getRiqi1());
				// riqi2.setText(ConstantValue.couponList.get(position).getRiqi2());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return view;
		}
	}

	MyBaseAdapter1 mybase1;

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bt_coupon_could:
			bt_coupon_had
					.setBackgroundResource(R.drawable.btn_bg_baidihuibianyuanjiao_nor);
			bt_coupon_could
					.setBackgroundResource(R.drawable.btn_bg_judihuibianyuanjiao_nor);

			state = 1;
			new GetCPQuery().execute(1);
			break;
		case R.id.bt_coupon_had:
			bt_coupon_could
					.setBackgroundResource(R.drawable.btn_bg_baidihuibianyuanjiao_nor);
			bt_coupon_had
					.setBackgroundResource(R.drawable.btn_bg_judihuibianyuanjiao_nor);

			state = 2;
			new GetCPQuery().execute(2);
			break;
		default:
			break;
		}
	}
}
