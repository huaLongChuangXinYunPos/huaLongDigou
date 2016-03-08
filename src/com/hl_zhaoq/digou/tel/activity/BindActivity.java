package com.hl_zhaoq.digou.tel.activity;

import com.hl_zhaoq.digou.tel.cst.ConstantTel;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.R;

import vivolibrary.BindingNumber;
import vivolibrary.SoftwareVerify;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class BindActivity extends Activity {
	private String iSubmitRequest;// �ύ����
	private ImageView bind_back;
	private EditText ed_bind;
	private Button bt_bind;
	private BindingNumber bindingNumber;
	private String TAG="BindActivity";
	private SoftwareVerify softwareVerify;
	private String iSoftwareVerity;
	private String msInterfaceType = "";
	private String msAdvertUrl;
	private String msCert = "";
	private int msAdvertType = 9; /* * 1��������绰���棻 2�����ż�����; 3����������棻 9�����Ž��档 */
	

	
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			 case 1:
				 Toast.makeText(BindActivity.this, "验证失败", Toast.LENGTH_SHORT).show();
					
			 break;
			 case 2:
				 Toast.makeText(BindActivity.this, "绑定无返回结果", Toast.LENGTH_SHORT).show();
					
				 break;
			 case 3:
				 Toast.makeText(BindActivity.this, "绑定失败", Toast.LENGTH_SHORT).show();
					
				 break;
			 case 4:
					Toast.makeText(BindActivity.this, "绑定成功", Toast.LENGTH_SHORT).show();
				 break;
			default:
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tel_bind_phone);
		initView();
		initListener();
		PromptManager.showLogTest(TAG, "ConstantValue.sModel = " + ConstantTel.sModel);
	}

	protected void writebendi(String msPhoneNumber, String iAccount) {
		// TODO Auto-generated method stub
		SharedPreferences sf=getSharedPreferences("binds", MODE_PRIVATE);
		Editor ed=sf.edit();
		ed.putString("msPhoneNumber", msPhoneNumber);
		ed.putString("iImsi", ConstantTel.iImsi);
		ed.putString("sModel", ConstantTel.sModel);
		ed.putString("iAccount", iAccount);
		ed.commit();
	}

	private void initListener() {
		// TODO Auto-generated method stub
		bt_bind.setOnClickListener(new MyOnClickListner());// �󶨰�ť����
		bind_back.setOnClickListener(new MyOnClickListner());// �󶨰�ť����
	}

	private class MyOnClickListner implements OnClickListener {

		Intent intent;

		@Override
		public void onClick(View v) {
			int _ViewID = v.getId();
			switch (_ViewID) {
			case R.id.bt_bind:
				new Thread(new Runnable() {
					@Override
					public void run() {
						softwareVerify = new SoftwareVerify(ConstantTel.msPhoneNumber,
								BindActivity.this);
						iSoftwareVerity = softwareVerify.SubmitSoftwareVerify();
						PromptManager.showLogTest(TAG, "�����֤ = " + iSoftwareVerity);
						String[] tmp = ConstantTel.split(iSoftwareVerity, "|");
						if (tmp.length != 0 && tmp[0].equals("1")) {
							msInterfaceType = tmp[1];
							msAdvertUrl = tmp[2];
							new Thread(new Runnable() {
								@Override
								public void run() {
									/*
									 * Param1���������� Param2��MODEL Param2��ISMI
									 */
									bindingNumber = new BindingNumber(ed_bind.getText().toString(),
											ConstantTel.sModel, ConstantTel.iImsi);
									PromptManager.showLogTest(TAG, "msPhoneNumber =" + ed_bind.getText().toString()
											+ " msInterfaceType =" + msInterfaceType);
									iSubmitRequest = bindingNumber.SubmitBindingNumber();
									PromptManager.showLogTest(TAG, "�󶨷��� =" + iSubmitRequest);
									if (iSubmitRequest != null) {
										String[] tmp = ConstantTel.split(iSubmitRequest, "|");
										if (tmp[0].equals("1")) { 
											mHandler.sendEmptyMessage(4);
											// �󶨳ɹ�msg=4
											
											ConstantTel.msPhoneNumber = ed_bind.getText().toString();
											ConstantTel.iAccount = tmp[1];// �����ʺ�
											BindActivity.this.writebendi(ConstantTel.msPhoneNumber,ConstantTel.iAccount);
											if (tmp[2] != null) {
												msCert = tmp[2];// ֤���
											}
										}else{
											//��ʧ��msg=3
											mHandler.sendEmptyMessage(3);
										}
									}else{
										//����Ϊ��msg=2
										mHandler.sendEmptyMessage(2);
									}
								}
							}).start();
						}else{
							//��֤ʧ��msg=1
							mHandler.sendEmptyMessage(1);
						}
					}
				}).start();

				break;

			case R.id.bind_back:
				BindActivity.this.finish();
				break;

			}
		}
	}

	private void initView() {
		// TODO Auto-generated method stub

		bind_back = (ImageView) findViewById(R.id.bind_back);
		ed_bind = (EditText) findViewById(R.id.ed_bind);
		bt_bind = (Button) findViewById(R.id.bt_bind);

	}
	
	
	
	

	
}
