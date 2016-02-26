package com.hlcxdg.digou.activity.payway;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.PAXiangdan;
import com.hlcxdg.digou.bean.PAZongdan;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class PaywayPA03 extends Activity {
	private List<PAZongdan> paywayzdPA03;
	private ListView pa03_list_fukuanzd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pa03_fukuanzongdan);
		initViewListener();
		new PA03AsynTask().execute(PaywayPA03.this);
	}

	private void initViewListener() {
		pa03_list_fukuanzd = (ListView) findViewById(R.id.pa03_list_fukuanzd);
	}

	public class PA03AsynTask extends AsyncTask<Context, ProgressBar, Integer> {
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
				if (paywayzdPA03 != null && paywayzdPA03.size() > 0) {
					PA03zdAdapter pa03zdad = new PA03zdAdapter(context);
					pa03_list_fukuanzd.setAdapter(pa03zdad);
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
				PAZongdan paZongdan = new PAZongdan();
				List<PAZongdan> pa03List = new ArrayList<PAZongdan>();
				pa03List.add(paZongdan);
				Gson gson = new Gson();
				String strgson = "20150929134605_20150809121202kk";// gson.toJson(pa01List);
				String msg = "*AIS|RQ|PA03|00|" + strgson + "|AIE#";
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
				// *AIS|RS|PA03|01|[{"cSaleSheetNo":"20150929134605_20150809121202kk","cPayTime":"2015-09-29 15:04:31.0","fPayMoney":300.0000,"Beizhu":"          "}]|AIE#

				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else

				if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
						&& "PA03".equals(temp[2]) && "01".equals(temp[3])
						&& "AIE#".equals(temp[5])) {
					paywayzdPA03 = gson.fromJson(temp[4],
							new TypeToken<List<PAZongdan>>() {
							}.getType());
					if (paywayzdPA03 != null) {
						return 1;
					}
				}
			} catch (Exception e) {
				return 0;
			}
			return 0;
		}
	}

	private class PA03zdAdapter extends BaseAdapter {
		private Context context;

		public PA03zdAdapter(Context context2) {
			super();
			this.context = context2;
		}

		@Override
		public int getCount() {
			return paywayzdPA03.size();
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
			Button pa03_zhifuxiangqing_bt;
			TextView pa03_yinhang_tv;
			TextView pa03_jine_tv;
			TextView pa03_date_tv;
			TextView pa03_zhifubianhao_tv;
			view = View.inflate(context, R.layout.pa03_fukuanzd_item, null);
			pa03_zhifubianhao_tv = (TextView) view
					.findViewById(R.id.pa03_zhifubianhao_tv);
			pa03_yinhang_tv = (TextView) view
					.findViewById(R.id.pa03_yinhang_tv);
			pa03_jine_tv = (TextView) view.findViewById(R.id.pa03_jine_tv);
			pa03_date_tv = (TextView) view.findViewById(R.id.pa03_date_tv);
			pa03_zhifubianhao_tv.setText(paywayzdPA03.get(position)
					.getcSaleSheetNo());
			pa03_yinhang_tv.setText(paywayzdPA03.get(position).getBeizhu());
			pa03_jine_tv.setText(paywayzdPA03.get(position).getfPayMoney()
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString());
			pa03_date_tv.setText(paywayzdPA03.get(position).getcPayTime());
			pa03_zhifuxiangqing_bt= (Button) view
					.findViewById(R.id.pa03_zhifuxiangqing_bt);
			pa03_zhifuxiangqing_bt.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					
				}
			});
			return view;
		}

	}

}
