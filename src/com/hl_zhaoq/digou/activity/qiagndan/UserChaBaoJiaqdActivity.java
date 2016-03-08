package com.hl_zhaoq.digou.activity.qiagndan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
/**
 * 完成通过传来经纬度来进行
 * 地图上显示我的位置
 * 
 */
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alipay.sdk.pay.demo.PayZhiActivityTest;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hl_zhaoq.digou.bean.RequestBean;
import com.hl_zhaoq.digou.bean.ResponseBean;
import com.hl_zhaoq.digou.bean.StoreBaojiaBean;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtilddgg;
import com.hl_zhaoq.digou.net.RequestVoddgg;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.R;

public class UserChaBaoJiaqdActivity extends Activity implements
		OnClickListener {

	private ImageView qiangdan_baojia_user_back_iv;
	private ListView qiangdan_baojia_user_lv;
	List<StoreBaojiaBean> storeBaojiaList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qiangdan_baojia_userget);
		init();
		new GetAllBaojiaAscny(UserChaBaoJiaqdActivity.this).execute();

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
				PromptManager.showMyToast(context, "商户暂时未保价");
			} else {
				storeBaojiaList = result;
				// PromptManager.showMyToast(context, "有发布信息");
				qiangdan_baojia_user_lv.setAdapter(new Userchabaojiaadapter());

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
				reqbean.setReqCode(5102);
				if (ConstantValue.userinfo!=null) {
					reqbean.setUserNo(ConstantValue.userinfo.getUserNo());
				}else {
					reqbean.setUserNo("23823872");
				}
				reqbean.setReqMsg("客户查找报价列表");
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
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

	}

	public class Userchabaojiaadapter extends BaseAdapter {

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
					.getStoreNo());
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
							qd_chabaojia_item_baojiastatus.setText("已成交");
							storeBaojiaList.get(position).setChengjiao("已成交");
//							storeBaojiaList.get(position).setPeisongTel(
//									"13299995944");
							new TaskSaveChengjiao().execute(storeBaojiaList.get(position));
							ConstantValue.storeqdListBaojiaStr=storeBaojiaList.get(position).getStorebaojia().setScale(2, BigDecimal.ROUND_UP)+"";
							Intent i = new Intent(UserChaBaoJiaqdActivity.this,
									PayZhiActivityTest.class);
							UserChaBaoJiaqdActivity.this.startActivity(i);
						}
					});
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
			return storeBaojiaList.size();
		}
	}

	public class TaskSaveChengjiao extends
			AsyncTask<StoreBaojiaBean, Process, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(UserChaBaoJiaqdActivity.this);
		}
		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result) {
//				PromptManager.showMyToast(getApplicationContext(), "提交成功");
			} else {
//				PromptManager.showMyToast(getApplicationContext(), "提交失败");
			}
		}

		@Override
		protected Boolean doInBackground(StoreBaojiaBean... stb) {
			StoreBaojiaBean sb = stb[0];
			List<StoreBaojiaBean> sbl = new ArrayList<StoreBaojiaBean>();
			sbl.add(sb);
			Gson g = new Gson();
			RequestBean re = new RequestBean();
			re.setStoreBaojiaList(sbl);
			re.setReqCode(5103);
			re.setReqMsg("添加成交");
			re.setBaojiaID(sb.getBaojiaID());
			String jsonString = g.toJson(re);
			RequestVoddgg vo = new RequestVoddgg(R.string.url_storebaojia,
					UserChaBaoJiaqdActivity.this, jsonString);
			String bresult = NetUtilddgg.post(vo);
			ResponseBean responseBean = g.fromJson(bresult, ResponseBean.class);
			if (responseBean.getRespCode() == 1) {
				return true;
			} else {
				return false;
			}
		}

	}

	private void init() {
		qiangdan_baojia_user_lv = (ListView) findViewById(R.id.qiangdan_baojia_user_lv);
		qiangdan_baojia_user_back_iv = (ImageView) findViewById(R.id.qiangdan_baojia_user_back_iv);
		qiangdan_baojia_user_back_iv.setOnClickListener(this);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.qiangdan_baojia_user_back_iv:
			finish();
			break;
		default:
			break;
		}
	}

}
