package com.hl_zhaoq.digou.tel.fragment;

import vivolibrary.QueryBalance;
import vivolibrary.SoftwareVerify;

import com.hl_zhaoq.digou.tel.activity.BindActivity;
import com.hl_zhaoq.digou.tel.cst.ConstantTel;
import com.hl_zhaoq.digou.R;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class UserinfoFragment extends Fragment {
	private String TAG = "BindActivity";
	private SoftwareVerify softwareVerify;
	private String iSoftwareVerity;
	private TextView bind_title;
	private TextView bind_content;
	private TextView balance;
	private TextView endtime;
	private Button bind_go;
	private Button chongzhi;
	// //�ײ��ؼ�����
	private QueryBalance queryBalance;// ��ѯ���
	private String iSubmitQuery;// ��ѯ����

	private ViewPager viewPager;

	public UserinfoFragment(ViewPager tmpPager) {
		super();
		this.viewPager = tmpPager;
	}

	public UserinfoFragment() {
		super();

	}

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 2:
				break;
			case 10:
				balance.setText("话费余额：" + yuer + "元");
				endtime.setText("有效期到：" + jiezhi);
			default:
				break;
			}
		}
	};

	String jiezhi = "";
	String yuer = "";

	// ��ѯ���
	View view;
	SharedPreferences sp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tel_myinfo, null);
		initView();
		initListener();
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sp = getActivity().getSharedPreferences("binds",
				getActivity().MODE_PRIVATE);
		String acc = sp.getString("iAccount", "");
		if (!TextUtils.isEmpty(acc)) {
			ConstantTel.iAccount = acc;
			ConstantTel.msPhoneNumber = sp.getString("msPhoneNumber", "");
			bind_content.setText("已绑定:" + ConstantTel.msPhoneNumber);
			bind_title.setText("用户账号:" + ConstantTel.iAccount);

			new Thread(new Runnable() {
				@Override
				public void run() {
					softwareVerify = new SoftwareVerify(
							ConstantTel.msPhoneNumber, getActivity());
					iSoftwareVerity = softwareVerify.SubmitSoftwareVerify();
					Log.i(TAG, "�����֤ = " + iSoftwareVerity);
					String[] tmp = ConstantTel.split(iSoftwareVerity, "|");
					if (tmp != null && tmp.length != 0 && tmp[0].equals("1")) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								/*
								 * Param1���������� Param2���ӿ�����
								 */
								queryBalance = new QueryBalance(
										ConstantTel.msPhoneNumber,
										ConstantTel.iAccount);
								Log.i("query", "yuerchaxun ="
										+ ConstantTel.msPhoneNumber
										+ " GetInfo.iAccount ="
										+ ConstantTel.iAccount);
								iSubmitQuery = queryBalance
										.SubmitQueryBalance();
								Log.i("query", "��ѯ���� =" + iSubmitQuery);
								if (iSubmitQuery != null) {
									String[] tmp = ConstantTel.split(
											iSubmitQuery, "|");
									if (tmp[0].equals("1")) { // cx�ɹ�
									// mHandler.sendEmptyMessage(2);
									// balance.setText(tmp[1]);
										yuer = tmp[1];
										jiezhi = tmp[2];
										// GetInfo.iAccount = tmp[1];// �����ʺ�
										Log.i("tmp[1]", tmp[1]);
										mHandler.sendEmptyMessage(10);
										Log.i("yue�ɹ�", "binbing = " + tmp[1]);
										if (tmp[2] != null) {
											// endtime.setText(tmp[2]) ;//
											// jizhiri��
										}
									}
									mHandler.sendEmptyMessage(2);
								}
							}
						}).start();

					} else {
						// ��֤ʧ��msg=1
						mHandler.sendEmptyMessage(1);
					}
				}
			}).start();
		}

	}

	public void initView() {
		bind_title = (TextView) view.findViewById(R.id.bind_title);
		bind_content = (TextView) view.findViewById(R.id.bind_content);
		balance = (TextView) view.findViewById(R.id.balance);
		endtime = (TextView) view.findViewById(R.id.endtime);
		bind_go = (Button) view.findViewById(R.id.bind_go2);
		chongzhi = (Button) view.findViewById(R.id.chongzhi);
	}

	public void initListener() {

		chongzhi.setOnClickListener(new MyOnClickListner());// �󶨰�ť����
		bind_go.setOnClickListener(new MyOnClickListner());// �󶨰�ť����
	}

	private class MyOnClickListner implements OnClickListener {

		Intent intent;

		@Override
		public void onClick(View v) {
			int _ViewID = v.getId();
			switch (_ViewID) {
			case R.id.bind_go2:
				intent = new Intent();
				intent.setClass(getActivity(), BindActivity.class);
				startActivity(intent);
				break;

			case R.id.chongzhi:
				viewPager.setCurrentItem(2);
			}
		}
	}
}
