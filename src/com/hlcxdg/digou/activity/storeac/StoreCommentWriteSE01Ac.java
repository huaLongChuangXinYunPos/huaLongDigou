package com.hlcxdg.digou.activity.storeac;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.StoreCommentSE01xie;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;


public class StoreCommentWriteSE01Ac extends Activity {

//	TextView goods_shangjia;
//	TextView goods_liebie;
//	SmartImageView vi_goods;
//	Goods goods;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.test_fugoods_xianqing);
		initView();
		new getStoreGoodsXq().execute("");
	}

	private void initView() {
//		goods_shangjia = (TextView) findViewById(R.id.goods_shangjia);
	}

	public class getStoreGoodsXq extends
			AsyncTask<String, ProgressBar, Integer> {
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(StoreCommentWriteSE01Ac.this);
				break;
			case 0:
				PromptManager.showToast(StoreCommentWriteSE01Ac.this,
						R.string.nodata);
				break;
			case 1:
				// shipeiqi
			
				break;
			default:
				break;
			}
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			
			try {
				PromptManager.showLogTest("PLAsynTaskdoInBackground", "PLAsynTask");
				// �����滻
				StoreCommentSE01xie scb = new StoreCommentSE01xie();
				ArrayList<StoreCommentSE01xie> scbList = new ArrayList<StoreCommentSE01xie>();
				scbList.add(scb);
				Gson gson = new Gson();
				
				String msg = "*AIS|RQ|SE01|00|"+gson.toJson(scbList)+"|AIE#";
				PromptManager.showLogTest("PLAsynTaskdoInBackground-postmesssage",
						msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url",
						StoreCommentWriteSE01Ac.this.getString(R.string.url_storeevaluation));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_storeevaluation,
						StoreCommentWriteSE01Ac.this, map);
				String result = NetUtil.post(vo);
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
							&& "SE01".equals(temp[2])&& "01".equals(temp[3])&& "AIE#".equals(temp[5])&& "OK".equals(temp[4])) {
//					
//					*AIS|RS|SE01|01|[{"ReEvaluationFlag":"0","cEvaluationProjDesc":"�ܺã��ܺ�..","cEvaluationResponce":"","cEvaluationTime":"2015-09-24 17:19:18","cPaySheetNo":"2342111872381","cResponceTime":"2015-09-24 17:19:18","cStoreNo":"10003","cUserNo":"20150908989894","fEvaluation":2.0}]|AIE#

//					
						return 1;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
	}
}
