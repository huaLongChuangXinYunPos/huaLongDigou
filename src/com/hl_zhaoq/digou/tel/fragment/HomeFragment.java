package com.hl_zhaoq.digou.tel.fragment;

import vivolibrary.MakeCall;
import vivolibrary.SoftwareVerify;

import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.tel.activity.BindActivity;
import com.hl_zhaoq.digou.tel.activity.TonghuaActivity;
import com.hl_zhaoq.digou.tel.cst.ConstantTel;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.R;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HomeFragment extends Fragment {
	
	private ImageView bt_callon;
	private String iSubmitCall;// ���з���
	private String iSoftwareVerity;
	private MakeCall makeCall;// ����
	private SoftwareVerify softwareVerify;
	// ͷ���ؼ�����
	private ImageView title_back;
	//�ײ��ؼ�����
	private Button bind_go;
	public SharedPreferences sp;
	// �в��ؼ�����
	private ImageView ed_del;
	private TextView tel_num;
	private ImageButton num1;
	private ImageButton num2;
	private ImageButton num3;
	private ImageButton num4;
	private ImageButton num5;
	private ImageButton num6;
	private ImageButton num7;
	private ImageButton num8;
	private ImageButton num9;
	private ImageButton num_x;
	private ImageButton num0;
	private ImageButton num_j;
	View view ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.tel_main_keyboard, null);
		
		initView();
		initListener();
		return view;
	}

	private void initView() {
		bt_callon = (ImageView) view.findViewById(R.id.bt_callon);
		title_back = (ImageView) view.findViewById(R.id.title_back);
		bind_go = (Button) view.findViewById(R.id.bind_go);
		// �в��ؼ�����
		ed_del = (ImageView) view.findViewById(R.id.ed_del);
		tel_num = (TextView) view.findViewById(R.id.tel_num);
		num1 = (ImageButton) view.findViewById(R.id.num1);
		num2 = (ImageButton) view.findViewById(R.id.num2);
		num3 = (ImageButton) view.findViewById(R.id.num3);
		num4 = (ImageButton) view.findViewById(R.id.num4);
		num5 = (ImageButton) view.findViewById(R.id.num5);
		num6 = (ImageButton) view.findViewById(R.id.num6);
		num7 = (ImageButton) view.findViewById(R.id.num7);
		num8 = (ImageButton) view.findViewById(R.id.num8);
		num9 = (ImageButton) view.findViewById(R.id.num9);
		num_x = (ImageButton) view.findViewById(R.id.num_x);
		num0 = (ImageButton) view.findViewById(R.id.num0);
		num_j = (ImageButton) view.findViewById(R.id.num_j);
	}
	public void initListener() {
		bind_go.setOnClickListener(new MyOnClickListner());// �󶨰�ť����
		bt_callon.setOnClickListener(new MyOnClickListner());
		ed_del.setOnClickListener(new MyOnClickListner());
		num0.setOnClickListener(new MyOnClickListner());
		num1.setOnClickListener(new MyOnClickListner());
		num2.setOnClickListener(new MyOnClickListner());
		num3.setOnClickListener(new MyOnClickListner());
		num4.setOnClickListener(new MyOnClickListner());
		num5.setOnClickListener(new MyOnClickListner());
		num6.setOnClickListener(new MyOnClickListner());
		num7.setOnClickListener(new MyOnClickListner());
		num8.setOnClickListener(new MyOnClickListner());
		num9.setOnClickListener(new MyOnClickListner());
		num_x.setOnClickListener(new MyOnClickListner());
		num_j.setOnClickListener(new MyOnClickListner());
		title_back.setOnClickListener(new MyOnClickListner());// �󶨰�ť����

	}
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			PromptManager.closeTelProgressDialog();
			switch (msg.what) {
			case 5:
				Toast.makeText(getActivity(), iSubmitCall, 0).show();
				break;
			case 88:
				Toast.makeText(getActivity(), "呼叫失败，请重试", 0).show();
				break;
			case 89:
				Toast.makeText(getActivity(), "请先绑定手机", 0).show();
				break;
			case 90:
				Toast.makeText(getActivity(), "电话输入错误", 0).show();
				break;

			case 1:
				Toast.makeText(getActivity(), "验证失败", 0).show();


			case 11:
				Intent i = new Intent();
				i.setClass(getActivity(), TonghuaActivity.class);
				startActivity(i);
				break;

			default:
				break;
			}
		}
	};
	private class MyOnClickListner implements OnClickListener {
		Intent intent;

		@Override
		public void onClick(View v) {
			int _ViewID = v.getId();
			switch (_ViewID) {
			case R.id.bind_go:
				intent = new Intent();
				intent.setClass(HomeFragment.this.getActivity(), BindActivity.class);
				startActivity(intent);
				break;
			case R.id.bt_callon:
				PromptManager.showTelProgressDialog(getActivity());
				sp = getActivity().getSharedPreferences("binds", getActivity().MODE_PRIVATE);
				String acc = sp.getString("iAccount", "");
				if (TextUtils.isEmpty(acc)) {
					mHandler.sendEmptyMessage(89);
				} else {
					ConstantTel.iAccount = acc;
					ConstantTel.msPhoneNumber = sp.getString("msPhoneNumber",
							"").trim();
					ConstantTel.iImsi = sp.getString("iImsi", "");
					ConstantTel.sModel = sp.getString("sModel", "");
					String telnum = tel_num.getText().toString().trim();
					int length = telnum.length();
					if (length == 0 || length > 12) {
						mHandler.sendEmptyMessage(90);
					} else {
						ConstantTel.beijiaonum = telnum;
						new Thread(new Runnable() {
							@Override
							public void run() {
								softwareVerify = new SoftwareVerify(
										ConstantTel.msPhoneNumber,
										getActivity());
								iSoftwareVerity = softwareVerify
										.SubmitSoftwareVerify();
								String[] tmp = ConstantTel.split(
										iSoftwareVerity, "|");
								if (tmp.length != 0 && tmp[0].equals("1")) {
									new Thread(new Runnable() {
										@Override
										public void run() {

											makeCall = new MakeCall(
													ConstantTel.msPhoneNumber,
													ConstantTel.beijiaonum,
													ConstantTel.iAccount);
											iSubmitCall = makeCall
													.SubmitMakeCall();

											String[] tmp2 = ConstantTel.split(
													iSoftwareVerity, "|");

											if (tmp2 != null
													&& tmp2[0].equals("1")) {
												mHandler.sendEmptyMessage(11);
											} else {
												mHandler.sendEmptyMessage(88);
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
				break;

			case R.id.ed_del:
				String num = tel_num.getText().toString();
				int len = num.length();
				if (len > 0) {
					tel_num.setText(num.substring(0, len - 1));
				}
				break;
			case R.id.num_x:
				if (tel_num.getText().toString().length() > 12) {
					break;
				}
				tel_num.setText(tel_num.getText().toString() + "*");
				break;
			case R.id.num_j:
				if (tel_num.getText().toString().length() > 12) {
					break;
				}
				tel_num.setText(tel_num.getText().toString() + "#");
				break;
			case R.id.num9:
				if (tel_num.getText().toString().length() > 12) {
					break;
				}
				tel_num.setText(tel_num.getText().toString() + "9");
				break;
			case R.id.num8:
				if (tel_num.getText().toString().length() > 12) {
					break;
				}
				tel_num.setText(tel_num.getText().toString() + "8");
				break;
			case R.id.num7:
				if (tel_num.getText().toString().length() > 12) {
					break;
				}
				tel_num.setText(tel_num.getText().toString() + "7");
				break;
			case R.id.num6:
				if (tel_num.getText().toString().length() > 12) {
					break;
				}
				tel_num.setText(tel_num.getText().toString() + "6");
				break;
			case R.id.num5:
				if (tel_num.getText().toString().length() > 12) {
					break;
				}
				tel_num.setText(tel_num.getText().toString() + "5");
				break;
			case R.id.num4:
				if (tel_num.getText().toString().length() > 12) {
					break;
				}
				tel_num.setText(tel_num.getText().toString() + "4");
				break;
			case R.id.num3:
				if (tel_num.getText().toString().length() > 12) {
					break;
				}
				tel_num.setText(tel_num.getText().toString() + "3");
				break;
			case R.id.num2:
				if (tel_num.getText().toString().length() > 12) {
					break;
				}
				tel_num.setText(tel_num.getText().toString() + "2");
				break;
			case R.id.num1:
				if (tel_num.getText().toString().length() > 12) {
					break;
				}
				tel_num.setText(tel_num.getText().toString() + "1");
				break;
			case R.id.num0:
				if (tel_num.getText().toString().length() > 12) {
					break;
				}
				tel_num.setText(tel_num.getText().toString() + "0");
				break;
			case R.id.title_back:
				HomeFragment.this.getActivity().finish();
				break;
			default:
				break;
			}
		}
	}


}
