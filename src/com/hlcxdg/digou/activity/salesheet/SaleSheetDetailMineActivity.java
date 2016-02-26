package com.hlcxdg.digou.activity.salesheet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.PAXiangdan;
import com.hlcxdg.digou.bean.PAZongdan;
import com.hlcxdg.digou.bean.SaleSheet;
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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/*
 * 我的订单详情
 */
public class SaleSheetDetailMineActivity extends Activity {
	ArrayList<SaleSheetDetail> ssdlist;
	Context context;
	SaleSheet ss;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		context = SaleSheetDetailMineActivity.this;
		setContentView(R.layout.myorderdetaillist);
		ss = (SaleSheet) getIntent().getSerializableExtra("ss");
		initView();
		new SaleSheetDetailMineTask().execute(SaleSheetDetailMineActivity.this);
	}

	public void back_iv(View v) {
		SaleSheetDetailMineActivity.this.finish();
	}

	/*
	 * 获取订单详情
	 */
	public class SaleSheetDetailMineTask extends
			AsyncTask<Context, ProgressBar, Boolean> {
		Context context;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
				PromptManager.showToast(context, "网络不给力");
			} else {

				salesheetdetail.setAdapter(new BaseAdapter() {

					@Override
					public View getView(int position, View arg1, ViewGroup arg2) {
						View view;
						TextView goods_name;
						TextView goods_price2;
						TextView count_tip;
						SmartImageView iv;

						view = View.inflate(getApplicationContext(),
								R.layout.ssd_item, null);
						iv = (SmartImageView) view
								.findViewById(R.id.goods_icon);
						goods_name = (TextView) view
								.findViewById(R.id.goods_name);
						goods_price2 = (TextView) view
								.findViewById(R.id.goods_price21);
						count_tip = (TextView) view
								.findViewById(R.id.count_tip_ss);
						// Log.i(TAG, "�����µ�view����" + position);
						SaleSheetDetail ssd = ssdlist.get(position);
						iv.setImageUrl(ConstantIP.ImageviewURL
								+ ssd.getMinIMG0());

						count_tip.setText("" + ssd.getfQuantity().intValue());
						goods_name.setText(ssd.getcGoodsName());
						goods_price2.setText(ssd.getfLastMoney()
								.setScale(2, BigDecimal.ROUND_HALF_UP)
								.toString());
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
						return ssdlist.size();
					}
				});
			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(ProgressBar... values) {
			super.onProgressUpdate(values);
			// PromptManager.showProgressDialog(context);
		}

		@Override
		protected Boolean doInBackground(Context[] arg0) {
			this.context = arg0[0];

			String ssNo = ss.getcSaleSheetno();
			String msg = "*AIS|RQ|OD04|00|" + ssNo + "|AIE#";
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("type", "post");
			map.put("url", context.getString(R.string.url_od02));
			map.put("dataType", "text");
			map.put("data", msg);
			RequestVo vo = new RequestVo(R.string.url_od02, context, map);
			String result = NetUtil.post(vo);
			PromptManager.showLogTest(
					"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
			String[] temp = NetUtil.split(result, "|");

			if (temp[0].equalsIgnoreCase("*AIS") && temp[1].equals("RS")
					&& temp[2].equals("OD04") && temp[3].equals("01")) {
				Log.i("json", temp[4]);
				Gson gson = new Gson();
				ssdlist = gson.fromJson(temp[4],
						new TypeToken<List<SaleSheetDetail>>() {
						}.getType());
				if (ssdlist.size() > 0) {
					return true;
				}
			}
			return false;
		}
	}

	ListView salesheetdetail;

	TextView ssd_id, ssd_stname, ssd_befprice, ssd_firstsheet, ssd_overcut,
			ssd_coupon, ssd_peisong, ssd_flastmoney;

	public void initView() {
		View v = initHeader();

		salesheetdetail = (ListView) findViewById(R.id.salesheetdetail);
		salesheetdetail.addHeaderView(v);
	}

	private View initHeader() {
		View v = View.inflate(context, R.layout.ssdlist_head, null);
		ssd_id = (TextView) v.findViewById(R.id.ssd_id);
		ssd_stname = (TextView) v.findViewById(R.id.ssd_stname);
		ssd_befprice = (TextView) v.findViewById(R.id.ssd_befprice);
		ssd_firstsheet = (TextView) v.findViewById(R.id.ssd_firstsheet);
		ssd_overcut = (TextView) v.findViewById(R.id.ssd_overcut);
		ssd_coupon = (TextView) v.findViewById(R.id.ssd_coupon);
		ssd_peisong = (TextView) v.findViewById(R.id.ssd_peisong);
		ssd_flastmoney = (TextView) v.findViewById(R.id.ssd_flastmoney);
		ssd_id.setText("订单编号：" + ss.getCSaleSheetno());
		ssd_stname.setText("店铺：" + ss.getcStoreName());
		ssd_befprice.setText("商品原价：" + ss.getfMoney() + "元");
		ssd_firstsheet.setText("首单减：" + ss.getFirstSheet() + "元");
		ssd_overcut.setText("满减：" + ss.getOverCut() + "元");
		ssd_coupon.setText("优惠券减免：" + ss.getCouPonMoney() + "元");
		ssd_peisong.setText("配送费：" + ss.getPeisongFee() + "元");
		ssd_flastmoney.setText("实付金额：" + ss.getfLastMoney() + "元");
		return v;
	}

}
