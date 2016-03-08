package com.hl_zhaoq.digou.activity.storeac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alipay.sdk.pay.demo.PayZhiActivity;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.salesheet.SalesheetActivity;
import com.hl_zhaoq.digou.bean.StoreCommentSE01xie;
import com.hl_zhaoq.digou.bean.StoreCommentSE02cha;
import com.hl_zhaoq.digou.bean.StoreItem;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StoreMainInPingjia extends Activity implements OnClickListener {
	private ListView store_main_list_pingjia;
	private View view;
	private ImageView store_main_head_back;
	private TextView stname_main_head;
	private Button store_bt_in_head;
	private TextView store_loa_head_tv;
	private Typeface fontFace1;
	private List<StoreCommentSE01xie> stCommSE01List;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.store_main_in);
		initView();
		stCommSE01List = new ArrayList<StoreCommentSE01xie>();
		store_main_list_pingjia.addHeaderView(view);
		// store_main_list_pingjia.setAdapter(new StorePingjiaAdapter());
		new getStorecommentXq().execute("");
	}

	private void initView() {
		store_main_list_pingjia = (ListView) findViewById(R.id.store_main_list_pingjia);
		view = View.inflate(StoreMainInPingjia.this, R.layout.store_main_head,
				null);
		store_main_head_back = (ImageView) findViewById(R.id.store_main_head_back);
		stname_main_head = (TextView) findViewById(R.id.stname_storemain_head);
		store_bt_in_head = (Button) view.findViewById(R.id.store_bt_in_head);
		store_loa_head_tv = (TextView) view
				.findViewById(R.id.store_loa_head_tv);
		store_bt_in_head.setOnClickListener(this);
		store_main_head_back.setOnClickListener(this);
		stname_main_head.setText(ConstantValue.storeItem.getcStoreName());
		store_loa_head_tv.setText(ConstantValue.storeItem.getcAddr());

	}

	public class getStorecommentXq extends
			AsyncTask<String, ProgressBar, Integer> {
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(StoreMainInPingjia.this);
				break;
			case 0:
				break;
			case 1:
				store_main_list_pingjia.setAdapter(new StorePingjiaAdapter());
				break;
			default:
				break;
			}
		}

		@Override
		protected Integer doInBackground(String... arg0) {

			// �����滻
			try {
				StoreCommentSE02cha scb = new StoreCommentSE02cha();
				scb.setcStoreNo(ConstantValue.storeItem.getcStoreNo());
				ArrayList<StoreCommentSE02cha> scbList = new ArrayList<StoreCommentSE02cha>();
				scbList.add(scb);
				Gson gson = new Gson();
				String msg = "*AIS|RQ|SE02|00|" + gson.toJson(scbList) + "|AIE#";
				PromptManager.showLogTest("PLAsynTaskdoInBackground-postmesssage",
						msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", StoreMainInPingjia.this
						.getString(R.string.url_storeevaluation));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_storeevaluation,
						StoreMainInPingjia.this, map);
				String result = NetUtil.post(vo);
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
							&& "SE02".equals(temp[2]) && "01".equals(temp[3])) {
						// *AIS|RS|SE02|01|[{"cStoreNo":"10001","cUserNo":"201509221021385855","fEvaluation":4.0,"cEvaluationProjDesc":"����","cEvaluationTime":"2015-09-30 11:50:22.0"},{"cStoreNo":"10001","cUserNo":"201509221021385855","fEvaluation":3.0,"cEvaluationProjDesc":"g��������Y","cEvaluationTime":"2015-09-30 11:31:02.0"},{"cStoreNo":"10001","cUserNo":"201509221021385855","fEvaluation":5.0,"cEvaluationProjDesc":"�������","cEvaluationTime":"2015-09-30 11:27:54.0"},{"cStoreNo":"10001","cUserNo":"201509221021385855","fEvaluation":4.0,"cEvaluationProjDesc":"����̶�����","cEvaluationTime":"2015-09-30 11:24:28.0"},{"cStoreNo":"10001","cUserNo":"2015090898989","fEvaluation":5.0,"cEvaluationProjDesc":"ͼ��ݹ�˾���ʺ�ʱ��","cEvaluationTime":"2015-09-30 11:06:18.0"},{"cStoreNo":"10001","cUserNo":"2015090898989","fEvaluation":5.0,"cEvaluationProjDesc":"��ǺǺ�","cEvaluationTime":"2015-09-30 10:56:07.0"},{"cStoreNo":"10001","cUserNo":"2015090898989","fEvaluation":5.0,"cEvaluationProjDesc":"�ܺã��ܺ�..","cEvaluationTime":"2015-09-25 17:26:45.0"}]|AIE#
						// }*AIS|RS|SE02|01|[{"cStoreNo":"10003","cUserNo":"20150908989894","fEvaluation":2.0,"cEvaluationProjDesc":"�ܺã��ܺ�..","cEvaluationTime":"2015-09-30 17:02:25.0"}]|AIE#
						stCommSE01List = gson.fromJson(temp[4],
								new TypeToken<List<StoreCommentSE01xie>>() {
								}.getType());
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

	private class StorePingjiaAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return stCommSE01List.size();
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
			TextView listitem_st_pingjia_fen;
			TextView listitem_st_pingjia_content;
			TextView listitem_st_pingjia_riqi;
			view = View.inflate(StoreMainInPingjia.this,
					R.layout.store_main_in_listpingjia_item, null);
			fontFace1 = Typeface
					.createFromAsset(getAssets(), "fonts/kaiti.ttf");

			listitem_st_pingjia_fen = (TextView) view
					.findViewById(R.id.listitem_st_pingjia_fen);
			listitem_st_pingjia_riqi = (TextView) view
					.findViewById(R.id.listitem_st_pingjia_riqi);
			listitem_st_pingjia_riqi.setText(stCommSE01List.get(position)
					.getcEvaluationTime().substring(0, 16));
			listitem_st_pingjia_content = (TextView) view
					.findViewById(R.id.listitem_st_pingjia_content);
			listitem_st_pingjia_content.setTypeface(fontFace1);
			int fEvaluation = (int) stCommSE01List.get(position)
					.getfEvaluation();
			switch (fEvaluation) {
			case 5:
				listitem_st_pingjia_fen.setText("��");
				break;
			case 4:
				listitem_st_pingjia_fen.setText("��");
				break;
			case 3:
				listitem_st_pingjia_fen.setText("��");
				break;
			default:
				listitem_st_pingjia_fen.setText("��");
				break;
			}
			listitem_st_pingjia_content.setText(stCommSE01List.get(position)
					.getcEvaluationProjDesc());
			return view;
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.store_bt_in_head: // �������
//			Intent intent;
//			intent = new Intent();
//			intent.setClass(StoreMainInPingjia.this,
//					StoreSingleFenleiActivity.class);
//			StoreMainInPingjia.this.startActivity(intent);
			StoreMainInPingjia.this.finish();
			break;
		case R.id.store_main_head_back: // ����
			StoreMainInPingjia.this.finish();
			break;
		default:
			break;
		}
	}
}
