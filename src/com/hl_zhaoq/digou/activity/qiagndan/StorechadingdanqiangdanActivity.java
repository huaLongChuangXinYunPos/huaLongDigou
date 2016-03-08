package com.hl_zhaoq.digou.activity.qiagndan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hl_zhaoq.digou.activity.qiagndan.StoreWoBaoJiaMap.TaskSaveBaojia;
import com.hl_zhaoq.digou.activity.qiagndan.UserChaBaoJiaqdActivity.TaskSaveChengjiao;
import com.hl_zhaoq.digou.bean.RequestBean;
import com.hl_zhaoq.digou.bean.ResponseBean;
import com.hl_zhaoq.digou.bean.StoreBaojiaBean;
import com.hl_zhaoq.digou.bean.UserNeedsBean;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtilddgg;
import com.hl_zhaoq.digou.net.RequestVoddgg;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StorechadingdanqiangdanActivity extends Activity implements
		OnClickListener {
	List<StoreBaojiaBean> storeBaojiaList;
	private ImageView qiangdan_dd_store_back_iv;
	private ListView qiangdan_dd_peisong_lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qiangdan_dongdan_store);
		init();
		new GetAllBaojiaAscny(StorechadingdanqiangdanActivity.this).execute();

	}

	private class GetAllBaojiaAscny extends
			AsyncTask<Void, Process, List<StoreBaojiaBean>> {
		Context context;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}
		@Override
		protected void onPostExecute(List<StoreBaojiaBean> result) {
			super.onPostExecute(result);
		
			if (result == null) {
				PromptManager.showMyToast(context, "暂时没有订单");
			} else {
				storeBaojiaList = result;
				
				qiangdan_dd_peisong_lv.setAdapter(new storepeisongadapter());
			}
			PromptManager.closeSpotsDialog();
		}

		public GetAllBaojiaAscny(Context context) {
			this.context = context;
		}

		@Override
		protected List<StoreBaojiaBean> doInBackground(Void... arg0) {
			try {
				RequestBean reqbean = new RequestBean();
				reqbean.setReqCode(5105);
				reqbean.setReqMsg("查找成交列表");
				Gson gson = new Gson();
				String jsonString = gson.toJson(reqbean);
				RequestVoddgg vo = new RequestVoddgg(R.string.url_storebaojia, context,
						jsonString);
				String bresult = NetUtilddgg.post(vo);
				ResponseBean responseBean = gson.fromJson(bresult,
						ResponseBean.class);
				if (responseBean.getRespCode() == 1) {
					return responseBean.getStoreBaojiaList();
				}
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

	}

	public class storepeisongadapter extends BaseAdapter {

		@Override
		public View getView(final int position, View arg1, ViewGroup arg2) {
			View view;

			TextView qd_chabaojia_item_stname;
			TextView qd_chabaojia_item_price;
			TextView qd_chabaojia_item_juli;
			final TextView qd_chabaojia_item_baojiastatus;

			view = View.inflate(getApplicationContext(),
					R.layout.qiangdan_baojia_userget_item, null);

			qd_chabaojia_item_stname = (TextView) view
					.findViewById(R.id.qd_chabaojia_item_stname);
			qd_chabaojia_item_price = (TextView) view
					.findViewById(R.id.qd_chabaojia_item_price);
			qd_chabaojia_item_juli = (TextView) view
					.findViewById(R.id.qd_chabaojia_item_juli);
			qd_chabaojia_item_baojiastatus = (TextView) view
					.findViewById(R.id.qd_chabaojia_item_baojiastatus);

			qd_chabaojia_item_stname.setText(storeBaojiaList.get(position)
					.getUserNo());
			qd_chabaojia_item_price.setText(storeBaojiaList.get(position)
					.getStorebaojia().setScale(2, BigDecimal.ROUND_HALF_UP)
					+ "");
			qd_chabaojia_item_juli.setText("2.8km");
			qd_chabaojia_item_baojiastatus.setText(storeBaojiaList
					.get(position).getChengjiao());
			qd_chabaojia_item_baojiastatus
					.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View arg0) {

							setPeisong(position);
							storeBaojiaList.get(position).setChengjiao("已配送");
							qd_chabaojia_item_baojiastatus.setText("已配送");
							//
						}
					});

			return view;
		}

		public void setPeisong(final int position) {
			AlertDialog.Builder builder = new Builder(
					StorechadingdanqiangdanActivity.this);
			// 自定义一个布局文件
			View view = View.inflate(StorechadingdanqiangdanActivity.this,
					R.layout.qiangdan_peisong_dialog, null);

			final EditText storename = (EditText) view
					.findViewById(R.id.et_stname_qd1);
			final EditText storeqdprice = (EditText) view
					.findViewById(R.id.et_jiage_qd1);
			Button ok = (Button) view.findViewById(R.id.ok);
			Button cancel = (Button) view.findViewById(R.id.cancel);
			final AlertDialog dialog = builder.create();
			dialog.setView(view, 0, 0, 0, 0);
			dialog.show();
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					// 把这个对话框取消掉
					dialog.dismiss();
				}
			});
			ok.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String ip = storename.getText().toString().trim();
					String po = storeqdprice.getText().toString().trim();
					if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(po)) {
						Toast.makeText(StorechadingdanqiangdanActivity.this,
								"配送员，电话都不能为空", 0).show();
						return;
					}
					new TaskSavePeisong(ip, po).execute(storeBaojiaList
							.get(position));
					dialog.dismiss();
				}
			});
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
			return storeBaojiaList.size();
		}
	}

	public class TaskSavePeisong extends
			AsyncTask<StoreBaojiaBean, Process, Boolean> {
		private String name;
		private String tel;

		public TaskSavePeisong(String ip, String po) {
			name = ip;
			tel = po;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
			} else {
			}
		}

		@Override
		protected Boolean doInBackground(StoreBaojiaBean... stb) {
			try {
				StoreBaojiaBean sb = stb[0];
				Gson g = new Gson();
				RequestBean re = new RequestBean();
				re.setBaojiaID(sb.getBaojiaID());
				re.setPeisongTel(tel);
				re.setPeisongName(name);
				re.setReqCode(5104);
				re.setReqMsg("添加成交");
				re.setBaojiaID(sb.getBaojiaID());
				String jsonString = g.toJson(re);
				RequestVoddgg vo = new RequestVoddgg(R.string.url_storebaojia,
						StorechadingdanqiangdanActivity.this, jsonString);
				String bresult = NetUtilddgg.post(vo);
				ResponseBean responseBean = g.fromJson(bresult, ResponseBean.class);
				if (responseBean.getRespCode() == 1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

	}

	private void init() {
		qiangdan_dd_peisong_lv = (ListView) findViewById(R.id.qiangdan_dd_peisong_lv);
		qiangdan_dd_store_back_iv = (ImageView) findViewById(R.id.qiangdan_dd_store_back_iv);
		qiangdan_dd_store_back_iv.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.qiangdan_dd_store_back_iv:
			finish();
			break;
		default:
			break;
		}
	}

}
