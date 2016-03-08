package com.hl_zhaoq.digou.activity.coupon;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.user.LoginActivity;
import com.hl_zhaoq.digou.bean.CouponYouhuiquan;
import com.hl_zhaoq.digou.bean.Goods;
import com.hl_zhaoq.digou.bean.UserInfo;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class YouhuiquanActivity extends Activity {
	Socket s = null;// ����Socket������
	DataOutputStream dout = null;// �����
	DataInputStream din = null;// ������
	ListView weilinglist;
	ListView yilinglist;

	ImageView iv_back;
	EditText ed_password;

	public void initView() {

		iv_back = (ImageView) findViewById(R.id.iv_back);
		ListView weilinglist = (ListView) findViewById(R.id.weilinglist);
		ListView yilinglist = (ListView) findViewById(R.id.yilinglist);
	}

	public void initListener() {

		iv_back.setOnClickListener(new MyOnClickListner());
	}

	private class MyOnClickListner implements OnClickListener {
		Intent intent;

		@Override
		public void onClick(View v) {
			int _ViewID = v.getId();
			switch (_ViewID) {
			case R.id.iv_back:
				YouhuiquanActivity.this.finish();

				break;

			}
		}
	}

	String keyStr = "";

	// 没有领取
	public void weiling(View view) {
		keyStr = "00";
		System.out.println(keyStr);
		new Fenlei2AsynTask().execute(YouhuiquanActivity.this);
	}

	// 已经领取
	public void yiling(View view) {
		keyStr = ConstantValue.userinfo.getUserNo();
		System.out.println(keyStr);
		new Fenlei2AsynTask1().execute(YouhuiquanActivity.this);
	}

	public class CouponAdapter1 extends BaseAdapter {

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
			view = View.inflate(YouhuiquanActivity.this, R.layout.coupon_item,
					null);
			couponid = (TextView) view.findViewById(R.id.couponid);
			orderid = (TextView) view.findViewById(R.id.orderid);
			coupondate = (TextView) view.findViewById(R.id.coupondate);
			cstatus = (TextView) view.findViewById(R.id.cstatus);

			// couponid.setText(ConstantValue.couponList.get(position)
			// .getCoupon_ID());
			// orderid.setText(ConstantValue.couponList.get(position)
			// .getcSaleSheetno());
			// coupondate.setText(ConstantValue.couponList.get(position)
			// .getcSaleDate());
			// cstatus.setText(ConstantValue.couponList.get(position)
			// .getStatus());

			return view;
		}
	}

	public class MyCouponAdapter1 extends BaseAdapter {

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
			view = View.inflate(YouhuiquanActivity.this, R.layout.coupon_item,
					null);
			couponid = (TextView) view.findViewById(R.id.couponid);
			orderid = (TextView) view.findViewById(R.id.orderid);
			coupondate = (TextView) view.findViewById(R.id.coupondate);
			cstatus = (TextView) view.findViewById(R.id.cstatus);

			// couponid.setText(ConstantValue.couponList.get(position)
			// .getCoupon_ID());
			// orderid.setText(ConstantValue.couponList.get(position)
			// .getcSaleSheetno());
			// coupondate.setText(ConstantValue.couponList.get(position)
			// .getcSaleDate());
			// cstatus.setText(ConstantValue.couponList.get(position)
			// .getStatus());

			return view;
		}
	}

	MyCouponAdapter1 mybase1;
	CouponAdapter1 cbase2;

	public class Fenlei2AsynTask extends
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
				PromptManager.showToast(context, "û���Ż�ȯ");
			} else if (ConstantValue.couponList != null
					&& ConstantValue.couponList.size() > 0) {
				weilinglist.setVisibility(View.VISIBLE);
				yilinglist.setVisibility(View.GONE);

				cbase2 = new CouponAdapter1();
				weilinglist.setAdapter(cbase2);
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
			 this.context = arg0[0]; ConstantValue.couponList.clear(); try { s
			 * = new Socket(ConstantValue.ip, ConstantValue.ip_port);
			 * 
			 * dout = new DataOutputStream(s.getOutputStream()); din = new
			 * DataInputStream(s.getInputStream());
			 * 
			 * // String youhuiqNo = "00"; String msg = "*AIS|RQ|CP01|00|" +
			 * keyStr + "|AIE#"; byte[] cmd = msg.getBytes(); Log.i("cmdxq",
			 * msg); dout.write(cmd);// �����������ע����Ϣ // byte b[] = new
			 * byte[4 * 1024]; int zhi = din.read(b); String results = new
			 * String(b, 0, zhi, "gb2312"); int maxQ = 0; while
			 * (results.indexOf("AIE#") < 1 && maxQ < 30) { //
			 * dout.write(cmd);// �����������ע����Ϣ
			 * 
			 * int j = din.read(b);// ���շ���������������Ϣ results += new
			 * String(b, 0, j, "gb2312"); maxQ++; } Log.i("getgoodlistafter",
			 * results); String[] temp = NetUtil.split(results, "|");
			 * 
			 * if (temp[0].equalsIgnoreCase("*AIS") && temp[1].equals("RS") &&
			 * temp[2].equals("CP01") && temp[3].equals("01")) { Log.i("json",
			 * temp[4]); Gson gson = new Gson(); ArrayList<Coupon> coupon =
			 * gson.fromJson(temp[4], new TypeToken<List<Coupon>>() {
			 * }.getType()); if (coupon.size() > 0) {
			 * ConstantValue.couponList.addAll(coupon); Log.i("couponList", "" +
			 * ConstantValue.couponList.size()); return true; } } } catch
			 * (Exception e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			return false;

		}
	}

	public class Fenlei2AsynTask1 extends
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
				PromptManager.showToast(context, "没有信息");
			} else if (ConstantValue.couponList != null
					&& ConstantValue.couponList.size() > 0) {
				yilinglist.setVisibility(View.VISIBLE);
				weilinglist.setVisibility(View.GONE);
				mybase1 = new MyCouponAdapter1();
				yilinglist.setAdapter(mybase1);

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
			 * 
			 * ConstantValue.usercouponList.clear();
			 * 
			 * try { s = new Socket(ConstantValue.ip, ConstantValue.ip_port);
			 * dout = new DataOutputStream(s.getOutputStream()); din = new
			 * DataInputStream(s.getInputStream()); // String userNo =
			 * "2015-03-01"; // 01 or 02
			 * 
			 * String msg = "*AIS|RQ|CP02|00|" + keyStr + "|AIE#"; byte[] cmd =
			 * msg.getBytes(); Log.i("cmdxq", msg); dout.write(cmd);//
			 * �����������ע����Ϣ // byte b[] = new byte[4 * 1024]; int zhi =
			 * din.read(b); String results = new String(b, 0, zhi, "gb2312");
			 * int maxQ = 0; while (results.indexOf("AIE#") < 1 && maxQ < 30) {
			 * // dout.write(cmd);// �����������ע����Ϣ
			 * 
			 * int j = din.read(b);// ���շ���������������Ϣ results += new
			 * String(b, 0, j, "gb2312"); maxQ++; }
			 * 
			 * Log.i("getgoodlistafter", results); String[] temp =
			 * NetUtil.split(results, "|");
			 * 
			 * if (temp[0].equalsIgnoreCase("*AIS") && temp[1].equals("RS") &&
			 * temp[2].equals("CP02") && temp[3].equals("01")) { Log.i("json",
			 * temp[4]); Gson gson = new Gson(); ArrayList<Coupon> coupon =
			 * gson.fromJson(temp[4], new TypeToken<List<Coupon>>() {
			 * }.getType()); if (coupon.size() > 0) {
			 * ConstantValue.couponList.addAll(coupon); Log.i("couponList", "" +
			 * ConstantValue.couponList.size()); return true; } return false; }
			 * 
			 * } catch (Exception e) { e.printStackTrace(); try { s.close(); }
			 * catch (Exception e1) { e1.printStackTrace(); } return false; }
			 */
			return false;

		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.youhuijuan);
		if (ConstantValue.userinfo == null) {
			Toast.makeText(YouhuiquanActivity.this, "�����ȵ�½", 0).show();
			Intent intent;
			intent = new Intent();
			intent.setClass(YouhuiquanActivity.this, LoginActivity.class);
			YouhuiquanActivity.this.startActivity(intent);

		}
		initView();
		initListener();
		new Fenlei2AsynTask().execute(YouhuiquanActivity.this);

	}
}
