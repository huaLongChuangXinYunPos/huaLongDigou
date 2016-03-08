package com.hl_zhaoq.digou.tel.fragment;

import vivolibrary.RechargeAccount;
import vivolibrary.SoftwareVerify;

import com.hl_zhaoq.digou.tel.activity.BindActivity;
import com.hl_zhaoq.digou.tel.cst.ConstantTel;
import com.hl_zhaoq.digou.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RechargeFragment extends Fragment {

	private String TAG = "BindActivity";
	private SoftwareVerify softwareVerify;
	private String iSoftwareVerity;
	private Button bind_go;

	private EditText hao_bind;
	private EditText pwd_bind;
	private Button bt_bind;
	View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tel_recharge, null);
		// view = inflater.inflate(R.layout.test_main, null);
		initView();
		initListener();
		return view;
	}

	public void initView() {
		bind_go = (Button) view.findViewById(R.id.bind_go);
		hao_bind = (EditText) view.findViewById(R.id.hao_bind);
		pwd_bind = (EditText) view.findViewById(R.id.pwd_bind);
		bt_bind = (Button) view.findViewById(R.id.bt_bind);

	}

	public void initListener() {
		bind_go.setOnClickListener(new MyOnClickListner());// �󶨰�ť����
		bt_bind.setOnClickListener(new MyOnClickListner());
	}

	private RechargeAccount rechargeaccount;// ��ֵ
	private String iSubmitRecharge;// ��ֵ����

	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {

			case 1:
				Toast.makeText(getActivity(), "卡号或密码不能为空", 0).show();

				break;
			case 2:
				Toast.makeText(getActivity(), "请先绑定手机号再充值", 0).show();

				Intent intent = new Intent();
				intent.setClass(getActivity(), BindActivity.class);
				startActivity(intent);
				break;
			case 3:
				Toast.makeText(getActivity(), "充值无返回结果", Toast.LENGTH_LONG)
						.show();

				break;
			case 4:
				Toast.makeText(getActivity(), "充值结果失败", Toast.LENGTH_LONG)
						.show();

				break;
			case 5:
				Toast.makeText(getActivity(), "充值成功", Toast.LENGTH_LONG).show();
				break;

			default:
				break;
			}
		}
	};
	public String cz_id;
	public String cz_pwd;

	private class MyOnClickListner implements OnClickListener {
		Intent intent;

		@Override
		public void onClick(View v) {
			int _ViewID = v.getId();
			switch (_ViewID) {
			case R.id.bt_bind:

				cz_id = hao_bind.getText().toString();
				cz_pwd = pwd_bind.getText().toString();

				if (TextUtils.isEmpty(cz_id) || TextUtils.isEmpty(cz_pwd)) {
					mHandler.sendEmptyMessage(1);
				} else {

					new Thread(new Runnable() {
						@Override
						public void run() {
							softwareVerify = new SoftwareVerify(
									ConstantTel.msPhoneNumber, getActivity());
							iSoftwareVerity = softwareVerify
									.SubmitSoftwareVerify();
							Log.i(TAG, "�����֤ = " + iSoftwareVerity);
							String[] tmp = ConstantTel.split(iSoftwareVerity,
									"|");
							if (tmp.length != 0 && tmp[0].equals("1")) {

								new Thread(new Runnable() {
									@Override
									public void run() {
										SharedPreferences sf = getActivity()
												.getSharedPreferences(
														"binds",
														getActivity().MODE_PRIVATE);
										ConstantTel.msPhoneNumber = sf
												.getString("msPhoneNumber", "");
										ConstantTel.iAccount = sf.getString(
												"iAccount", "");
										Log.i("��ֵ", "��ֵ = "
												+ ConstantTel.msPhoneNumber
												+ cz_id + cz_pwd
												+ ConstantTel.iAccount);
										if (TextUtils
												.isEmpty(ConstantTel.msPhoneNumber)
												|| TextUtils
														.isEmpty(ConstantTel.iAccount)) {
											// ���Ȱ��ֻ��� 2
											mHandler.sendEmptyMessage(2);

										} else {
											rechargeaccount = new RechargeAccount(
													ConstantTel.msPhoneNumber,
													ConstantTel.iAccount);
											rechargeaccount.SetCardInfor(cz_id,
													cz_pwd);
											iSubmitRecharge = rechargeaccount
													.SubmitRechargeAccount();
											Log.i("��ֵ����", "��ֵ���� = "
													+ iSubmitRecharge);
											if (iSubmitRecharge != null) {
												String[] tmp = ConstantTel
														.split(iSubmitRecharge,
																"|");
												if (tmp[0].equals("1")) {
													mHandler.sendEmptyMessage(5);
													// ��ֵ�ɹ�msg=5

												} else {
													// ��ֵʧ��msg=4
													mHandler.sendEmptyMessage(4);

												}

											} else {
												// ����Ϊ��msg=3
												mHandler.sendEmptyMessage(3);

											}

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
				break;

			case R.id.bind_go:
				intent = new Intent();
				intent.setClass(getActivity(),
						com.hl_zhaoq.digou.tel.activity.BindActivity.class);
				startActivity(intent);
				break;

			}
		}
	}

}
