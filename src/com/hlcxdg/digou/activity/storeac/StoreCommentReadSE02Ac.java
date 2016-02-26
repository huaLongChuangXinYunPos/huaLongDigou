package com.hlcxdg.digou.activity.storeac;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.Gson;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.StoreCommentSE01xie;
import com.hlcxdg.digou.bean.StoreCommentSE02cha;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Window;
import android.widget.ProgressBar;

public class StoreCommentReadSE02Ac extends Activity {

	// TextView goods_shangjia;
	// TextView goods_liebie;
	// SmartImageView vi_goods;
	// Goods goods;
	// *AIS|RQ|SE02|00|00|AIE#
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.test_fugoods_xianqing);
		initView();
		new getStoreGoodscommentXq().execute("");
	}

	private void initView() {
		// goods_shangjia = (TextView) findViewById(R.id.goods_shangjia);
	}

	public class getStoreGoodscommentXq extends
			AsyncTask<String, ProgressBar, Integer> {
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(StoreCommentReadSE02Ac.this);
				break;
			case 0:
				PromptManager.showToast(StoreCommentReadSE02Ac.this,
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
				StoreCommentSE02cha scb = new StoreCommentSE02cha();
				ArrayList<StoreCommentSE02cha> scbList = new ArrayList<StoreCommentSE02cha>();
				scbList.add(scb);
				Gson gson = new Gson();
				String storeNo = "10003";
				String msg = "*AIS|RQ|SE02|00|" + gson.toJson(scbList) + "|AIE#";
				PromptManager.showLogTest("PLAsynTaskdoInBackground-postmesssage",
						msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", StoreCommentReadSE02Ac.this
						.getString(R.string.url_storeevaluation));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_storeevaluation,
						StoreCommentReadSE02Ac.this, map);
				String result = NetUtil.post(vo);
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
							&& "SE02".equals(temp[2]) && "AIE#".equals(temp[3])
							&& "01".equals(temp[3])) {
						// *AIS|RS|SE02|01|[{"cStoreNo":"10001","cUserNo":"201509221021385855","fEvaluation":4.0,"cEvaluationProjDesc":"����","cEvaluationTime":"2015-09-30 11:50:22.0"},{"cStoreNo":"10001","cUserNo":"201509221021385855","fEvaluation":3.0,"cEvaluationProjDesc":"g��������Y","cEvaluationTime":"2015-09-30 11:31:02.0"},{"cStoreNo":"10001","cUserNo":"201509221021385855","fEvaluation":5.0,"cEvaluationProjDesc":"�������","cEvaluationTime":"2015-09-30 11:27:54.0"},{"cStoreNo":"10001","cUserNo":"201509221021385855","fEvaluation":4.0,"cEvaluationProjDesc":"����̶�����","cEvaluationTime":"2015-09-30 11:24:28.0"},{"cStoreNo":"10001","cUserNo":"2015090898989","fEvaluation":5.0,"cEvaluationProjDesc":"ͼ��ݹ�˾���ʺ�ʱ��","cEvaluationTime":"2015-09-30 11:06:18.0"},{"cStoreNo":"10001","cUserNo":"2015090898989","fEvaluation":5.0,"cEvaluationProjDesc":"��ǺǺ�","cEvaluationTime":"2015-09-30 10:56:07.0"},{"cStoreNo":"10001","cUserNo":"2015090898989","fEvaluation":5.0,"cEvaluationProjDesc":"�ܺã��ܺ�..","cEvaluationTime":"2015-09-25 17:26:45.0"}]|AIE#
						// }*AIS|RS|SE02|01|[{"cStoreNo":"10003","cUserNo":"20150908989894","fEvaluation":2.0,"cEvaluationProjDesc":"�ܺã��ܺ�..","cEvaluationTime":"2015-09-30 17:02:25.0"}]|AIE#

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
