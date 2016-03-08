package com.hl_zhaoq.digou.activity.payway;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.hl_zhaoq.digou.bean.PAXiangdan;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

public class PaywayPA02 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.paywayxuanzheye);
		initViewListener();
//		new PA02AsynTask().execute(PaywayPA02.this);
	}

	private void initViewListener() {

	}

	public class PA02AsynTask extends AsyncTask<Context, ProgressBar, Integer> {
		Context context;

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(context);
				break;
			case 0:
				PromptManager.showToast(context, "支付详单提交失败");
				break;
			case 1:
				PromptManager.showToast(context, "支付详单提交成功");
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
				PAXiangdan paXiangdan = new PAXiangdan();
				List<PAXiangdan> pa01List = new ArrayList<PAXiangdan>();
				pa01List.add(paXiangdan);
				Gson gson = new Gson();
				String strgson = gson.toJson(pa01List);
				String msg = "*AIS|RQ|PA02|00|" + strgson + "|AIE#";
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
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else

				if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
						&& "PA02".equals(temp[2]) && "01".equals(temp[3])
						&& "AIE#".equals(temp[5])) {
					// *AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#*AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#*AIS|RS|PA02|01|20150929134605_20150809121202kk|AIE#
					return 1;
				}
			} catch (Exception e) {
				return 0;
			}

			return 0;
		}
	}

}
