package com.hlcxdg.digou.activity.coupon;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.Goods;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class UserCouponActivity extends Activity {
	ListView mycpoupon;

	public void initView() {
		mycpoupon = (ListView) findViewById(R.id.mycpoupon);
	}

	public void goback(View view) {
		UserCouponActivity.this.finish();
	}

	public void initListener() {

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.usercouponlist);

		initView();
		initListener();
		mybase1 = new MyBaseAdapter1();
		mycpoupon.setAdapter(mybase1);
		
	}

	public class Get extends
			AsyncTask<Context, ProgressBar, Boolean> {
		Context context;
		ListView list;

		Socket s = null;// ����Socket������
		DataOutputStream dout = null;// �����
		DataInputStream din = null;// ������

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// PromptManager.closeProgressDialog();
			if (!result) {
				PromptManager.showToast(context, "����ʧ��");
			} else if (ConstantValue.couponList != null
					&& ConstantValue.couponList.size() > 0) {
				mybase1.notifyDataSetChanged();
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(ProgressBar... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			// PromptManager.showProgressDialog(context);
		}

		SharedPreferences sp;

		@Override
		protected Boolean doInBackground(Context[] arg0) {
			/**
			 * 
			
			this.context = arg0[0];

			ConstantValue.usercouponList.clear();
			
			try {
				s = new Socket(ConstantValue.ip, ConstantValue.ip_port);
				dout = new DataOutputStream(s.getOutputStream());
				din = new DataInputStream(s.getInputStream());

				Log.i("newsocket", "FEnlei2Asyntask");

				// String msg = "*AIS|RQ|PC01|03|00|AIE#";����03��Ϣ
				// String msg = "*AIS|RQ|PC01|00|00|AIE#";����˵����Ϣ

				String msg = "*AIS|RQ|PL01|"  + "|00|AIE#";
				// String grno="03";
				// String msg = "*AIS|RQ|PC02|"+grno+"|00|AIE#"; ��������
				byte[] cmd = msg.getBytes();
				Log.i("cmdxq", msg);
				dout.write(cmd);// �����������ע����Ϣ
				//
				byte b[] = new byte[10 * 1024];
				int a = din.read(b);// ���շ���������������Ϣ
				String result = new String(b, 0, a, "gb2312");
				Log.i("getgoodlistbefore", result);
				int maxQ = 0;
				while (result.indexOf("AIE#") < 1 && maxQ < 30) {
					// dout.write(cmd);// �����������ע����Ϣ

					int j = din.read(b);// ���շ���������������Ϣ
					result += new String(b, 0, j, "gb2312");
					maxQ++;
				}

				int i = result.indexOf("AIE#");
				result = result.substring(0, i);
				Log.i("getgoodlistafter", result);
				String[] temp = NetUtil.split(result, "|");
				if (temp[0].equalsIgnoreCase("*AIS") && temp[1].equals("RS")
						&& temp[2].equals("PL01")) {
					Log.i("json", temp[3]);
					Goods gt1 = new Goods();
					Gson gson = new Gson();
					ArrayList<Goods> ps = gson.fromJson(temp[3],
							new TypeToken<List<Goods>>() {
							}.getType());

					if (ps.size() > 0) {
						ConstantValue.goodData.addAll(ps);
						
						return true;
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				try {
					s.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				return true;
			} */
			return true;

		}
	}

	public class MyBaseAdapter1 extends BaseAdapter {

		@Override
		public int getCount() {
			return ConstantValue.couponList.size();
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
			TextView couponid;
			TextView orderid;
			TextView coupondate;
			TextView cstatus;
			view = View.inflate(UserCouponActivity.this,
					R.layout.usercoupon_item, null);
			couponid = (TextView) view.findViewById(R.id.couponid);
			orderid = (TextView) view.findViewById(R.id.orderid);
			coupondate = (TextView) view.findViewById(R.id.coupondate);
			cstatus = (TextView) view.findViewById(R.id.cstatus);

//			couponid.setText(ConstantValue.usercouponList.get(position)
//					.getCoupon_ID());
//			orderid.setText(ConstantValue.usercouponList.get(position)
//					.getcSaleSheetno());
//			coupondate.setText(ConstantValue.usercouponList.get(position)
//					.getcSaleDate());
//			cstatus.setText(ConstantValue.usercouponList.get(position)
//					.getStatus());

			return view;
		}
	}

	MyBaseAdapter1 mybase1;
}
