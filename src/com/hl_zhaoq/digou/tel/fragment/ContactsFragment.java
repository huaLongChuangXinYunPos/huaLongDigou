package com.hl_zhaoq.digou.tel.fragment;

import java.util.Map;

import vivolibrary.MakeCall;
import vivolibrary.SoftwareVerify;

import com.hl_zhaoq.digou.tel.activity.BindActivity;
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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ContactsFragment extends Fragment {
	private ListView lianxirenlist;
	public SharedPreferences sp;
	private String iSubmitCall;// ���з���
	private String iSoftwareVerity;
	private MakeCall makeCall;// ����
	private SoftwareVerify softwareVerify;
		private Button bind_go_lianxiren;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			PromptManager.closeTelProgressDialog();
			switch (msg.what) {
			case 89:
				Toast.makeText(getActivity(), "请先绑定手机", 0).show();
				break;
			case 88:
				Toast.makeText(getActivity(), "呼叫失败请重试", 0).show();
				break;
			case 11:
				Intent i = new Intent();
				i.setClass(getActivity(),
						com.hl_zhaoq.digou.tel.activity.TonghuaActivity.class);
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
			case R.id.bind_go_lianxiren:
				intent = new Intent();
				intent.setClass(ContactsFragment.this.getActivity(), BindActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.tel_contact, null);
		bind_go_lianxiren = (Button) view.findViewById(R.id.bind_go_lianxiren);
		bind_go_lianxiren.setOnClickListener(new MyOnClickListner());// �󶨰�ť����
		
		lianxirenlist = (ListView) view.findViewById(R.id.lianxirenlist);
		lianxirenlist.setAdapter(new MyAdapter());
		try {
			lianxirenlist.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// System.out.println(position);
					PromptManager.showTelProgressDialog(getActivity());
					ConstantTel.beijiaonum = ConstantTel.contactList.get(position)
							.get("phone").trim();
					sp = getActivity().getSharedPreferences("binds",
							getActivity().MODE_PRIVATE);
					String acc = sp.getString("iAccount", "");
					if (TextUtils.isEmpty(acc)) {
						mHandler.sendEmptyMessage(89);// ���
					} else {
						ConstantTel.iAccount = acc;
						ConstantTel.msPhoneNumber = sp.getString("msPhoneNumber",
								"").trim();
						ConstantTel.iImsi = sp.getString("iImsi", "");
						ConstantTel.sModel = sp.getString("sModel", "");
						new Thread(new Runnable() {
							@Override
							public void run() {
								softwareVerify = new SoftwareVerify(
										ConstantTel.msPhoneNumber, getActivity());
								iSoftwareVerity = softwareVerify
										.SubmitSoftwareVerify();
								String[] tmp = ConstantTel.split(iSoftwareVerity,
										"|");
								if (tmp.length != 0 && tmp[0].equals("1")) {
									new Thread(new Runnable() {
										@Override
										public void run() {
											makeCall = new MakeCall(
													ConstantTel.msPhoneNumber,
													ConstantTel.beijiaonum,
													ConstantTel.iAccount);
											iSubmitCall = makeCall.SubmitMakeCall();
											String[] tmp2 = ConstantTel.split(
													iSoftwareVerity, "|");
											if (tmp2 != null && tmp2[0].equals("1")) {
												mHandler.sendEmptyMessage(11);// �ɹ�
											} else {
												mHandler.sendEmptyMessage(88);// ʧ��
											}
										}
									}).start();
								} else {
									mHandler.sendEmptyMessage(12);
								}
							}
						}).start();
					}
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return view;
	}

	private class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ConstantTel.contactList.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder holder = null;
			View view;
			Map<String, String> map = ConstantTel.contactList.get(position);
			if (convertView != null) {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			} else {
				view = View.inflate(getActivity(), R.layout.tel_item_lianxiren,
						null);
				holder = new ViewHolder();

				holder.call_name = (TextView) view.findViewById(R.id.call_name);
				holder.call_phone = (TextView) view
						.findViewById(R.id.call_phone);
				view.setTag(holder);
			}
			holder.call_name.setText(map.get("name"));
			holder.call_phone.setText(map.get("phone"));

			return view;
		}

	}

	static class ViewHolder {
		TextView call_name;
		TextView call_phone;
	}

}
