package com.hlcxdg.digou.activity.payway;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.salesheet.SaleSheetMineActivity;
import com.hlcxdg.digou.activity.salesheet.SalesheetActivity;
import com.hlcxdg.digou.bean.PAXiangdan;
import com.hlcxdg.digou.bean.PAZongdan;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

public class PaywayPA01 extends Activity implements OnClickListener {
	private Spinner spinner1;
	private Spinner spinner2;
	private TextView zongjia_pay;
	private TextView goodsnum_pay;
	private TextView riqi_pay;
	private TextView bianhao_pay;
	private Button btn_lijifukuan_pay;

	public class PA01 extends AsyncTask<String, Process, Integer> {
		private Context context;

		public PA01(Context context) {
			this.context = context;
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {
				List<PAZongdan> pa01List = new ArrayList<PAZongdan>();
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String datenow = sdf.format(d);

//				PAZongdan paZongdan = new PAZongdan();
//				paZongdan.setcSaleSheetNo(ss.getcSaleSheetno());
//				paZongdan.setcPayTime(datenow);
//				paZongdan.setfPayMoney(ss.getFLastMoney());
//				pa01List.add(paZongdan);
//
//				Gson gson = new Gson();
//				String strgson = gson.toJson(pa01List);
//				String msg = "*AIS|RQ|PA01|00|" + strgson + "|AIE#";
//				HashMap<String, String> map = new HashMap<String, String>();
//				map.put("type", "post");
//				map.put("url", context.getString(R.string.servlet_paysheet));
//				map.put("dataType", "text");
//				map.put("data", msg);
//				RequestVo vo = new RequestVo(R.string.servlet_paysheet,
//						context, map);
//
//				String result = NetUtil.post(vo);
				//
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
			return 0;
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.paywayxuanzheye);
		initView();
		// initListener();

	}

	private void initListener() {
		btn_lijifukuan_pay.setOnClickListener(this);
	}

	private void initView() {
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		spinner2 = (Spinner) findViewById(R.id.spinner2);
		zongjia_pay = (TextView) findViewById(R.id.zongjia_pay);
		goodsnum_pay = (TextView) findViewById(R.id.goodsnum_pay);
		riqi_pay = (TextView) findViewById(R.id.riqi_pay);
		bianhao_pay = (TextView) findViewById(R.id.bianhao_pay);
//		zongjia_pay.setText("共计￥" + ConstantValue.ss.getFLastMoney() + "元");
		goodsnum_pay.setText("共" + ConstantValue.total_Nums_cart + "件商品");
//		riqi_pay.setText(ConstantValue.ss.getDSaleDate());
//		bianhao_pay.setText(ConstantValue.ss.getCSaleSheetno());
		btn_lijifukuan_pay = (Button) findViewById(R.id.btn_lijifukuan_pay);
	}

	public class PA01And02AsynTask extends
			AsyncTask<Context, ProgressBar, Integer> {
		Context context;

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(context);
				break;
			case 0:
				break;
			case 1:
				PromptManager.showMyToast(context, "付款成功");
				Intent intent;
				intent = new Intent();
				intent.setClass(context, SaleSheetMineActivity.class);
				context.startActivity(intent);
				PaywayPA01.this.finish();

				break;
			default:
				break;
			}
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
		protected Integer doInBackground(Context[] cont) {
			this.context = cont[0];
			try {
				Boolean network = NetUtil.hasNetwork(context);
				if (!network) {
					return -1;
				}
				PromptManager.showLogTest("SalesheetActivity",
						"doInBackground-yuwang");
				PAZongdan paZongdan = new PAZongdan();
//				paZongdan.setcSaleSheetNo(ConstantValue.ss.getCSaleSheetno());
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				String datenow = sdf.format(d);
//				paZongdan.setfPayMoney(ConstantValue.ss.getFLastMoney());
				paZongdan.setcPayTime(datenow);
				List<PAZongdan> pa01List = new ArrayList<PAZongdan>();
				pa01List.add(paZongdan);
				Gson gson = new Gson();
				String strgson = gson.toJson(pa01List);
				String msg = "*AIS|RQ|PA01|00|" + strgson + "|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_paysheet));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_paysheet,
						context, map);

				String result = NetUtil.post(vo);
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else

				if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
						&& "PA01".equals(temp[2]) && "01".equals(temp[3])) {
					// 村上付款*AIS|RS|PA01|01|20150929134605_20150809121207kk|AIE#
					PAXiangdan paXiangdan = new PAXiangdan();
					paXiangdan.setcPayTime(datenow);
//					paXiangdan.setcSaleSheetNo(ConstantValue.ss
//							.getCSaleSheetno());
//					paXiangdan.setfPayMoney(ConstantValue.ss.getFLastMoney());
					paXiangdan.setIlineNo("1");
					paXiangdan.setcPayStyleNo("03");
					;
					paXiangdan.setcPayStyleDetail("122015092913460512");// 卡号或编号
					List<PAXiangdan> pa02List = new ArrayList<PAXiangdan>();
					pa02List.add(paXiangdan);
					strgson = gson.toJson(pa02List);
					msg = "*AIS|RQ|PA02|00|" + strgson + "|AIE#";
					PromptManager.showLogTest(
							"PLAsynTaskdoInBackground-postmesssage", msg);
					map = new HashMap<String, String>();
					map.put("type", "post");
					map.put("url",
							context.getString(R.string.servlet_paysheetdetail));
					map.put("dataType", "text");
					map.put("data", msg);
					vo = new RequestVo(R.string.servlet_paysheetdetail,
							context, map);
					result = NetUtil.post(vo);
					PromptManager.showLogTest(
							"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
					temp = NetUtil.split(result, "|");
					if (temp == null) {
						PromptManager.showLogTest("enginenet",
								"net-result-null");
					} else

					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "PA02".equals(temp[2])
							&& "01".equals(temp[3])) {
						// *AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#*AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#*AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#
						return 1;
					}
				}
			} catch (Exception e) {
				return 0;
			}

			return 0;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_lijifukuan_pay:
			new PA01And02AsynTask().execute(PaywayPA01.this);
			break;

		default:
			break;
		}
	}

}
