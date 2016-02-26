package com.hlcxdg.digou.activity.payway;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.payway.PaywayPA03.PA03AsynTask;
import com.hlcxdg.digou.bean.PAXiangdan;
import com.hlcxdg.digou.constant.ConstantIP;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PaywayPA04 extends Activity {
	private List<PAXiangdan> paywayxqPA04;
	private ListView pa04_list_fukuanxq;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pa04_fukuanxangqingye);
		initViewListener();
		new PA04AsynTask().execute(PaywayPA04.this);
	}

	private void initViewListener() {
		pa04_list_fukuanxq = (ListView) findViewById(R.id.pa04_list_fukuanxq);
	}

	public class PA04AsynTask extends AsyncTask<Context, ProgressBar, Integer> {
		Context context;

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(context);
				break;
			case 0:
				PromptManager.showToast(context, "无订单支付记录");
				break;
			case 1:
				if (paywayxqPA04 != null && paywayxqPA04.size() > 0) {
					PA04xqAdapter pa04xqad = new PA04xqAdapter(context);
					pa04_list_fukuanxq.setAdapter(pa04xqad);
				}
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
				
				String strno = "20150929134605_20150809121202kk";
				String msg = "*AIS|RQ|PA04|00|" + strno + "|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url",
						context.getString(R.string.servlet_paysheetdetail));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_paysheetdetail,
						context, map);

				String result = NetUtil.post(vo);
				// *AIS|RS|PA04|01|[{"cSaleSheetNo":"20150929134605_20150809121202kk","ilineNo":"2","cPayStyleNo":"01","fPayMoney":100.0000,"cPayStyleDetail":"122015092913460512"},{"cSaleSheetNo":"20150929134605_20150809121202kk","ilineNo":"3","cPayStyleNo":"02","fPayMoney":100.0000,"cPayStyleDetail":"122015092913460512"},{"cSaleSheetNo":"20150929134605_20150809121202kk","ilineNo":"4","cPayStyleNo":"03","fPayMoney":100.0000,"cPayStyleDetail":"122015092913460512"},{"cSaleSheetNo":"20150929134605_20150809121202kk","ilineNo":"5","cPayStyleNo":"03","fPayMoney":100.0000,"cPayStyleDetail":"122015092913460512"}]|AIE#
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else

				if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
						&& "PA04".equals(temp[2]) && "01".equals(temp[3])
						&& "AIE#".equals(temp[5])) {
//					paywayxqPA04 = gson.fromJson(temp[4],
//							new TypeToken<List<PAXiangdan>>() {
//							}.getType());
//					if (paywayxqPA04 != null) {
//						return 1;
//					}
				}
			} catch (Exception e) {
				return 0;
			}
			return 0;
		}
	}

	private class PA04xqAdapter extends BaseAdapter {
		private Context context;

		public PA04xqAdapter(Context context2) {
			super();
			this.context = context2;
		}

		@Override
		public int getCount() {
			return paywayxqPA04.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View cv, ViewGroup par) {
			View view;
			TextView pa04_yinhang_tv;
			TextView pa04_jine_tv;
			TextView pa04_date_tv;
			view = View.inflate(context, R.layout.pa04_fukuanxq_item, null);
			pa04_yinhang_tv = (TextView) view
					.findViewById(R.id.pa04_yinhang_tv);
			pa04_jine_tv = (TextView) view.findViewById(R.id.pa04_jine_tv);
			pa04_date_tv = (TextView) view.findViewById(R.id.pa04_date_tv);
			pa04_yinhang_tv.setText(paywayxqPA04.get(position)
					.getcPayStyleDetail());
			pa04_jine_tv.setText(paywayxqPA04.get(position).getfPayMoney()
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			pa04_date_tv.setText(paywayxqPA04.get(position).getcPayTime());
			return view;
		}

	}
}
