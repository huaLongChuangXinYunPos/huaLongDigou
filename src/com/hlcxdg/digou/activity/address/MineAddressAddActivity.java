package com.hlcxdg.digou.activity.address;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.BaseActivity;
import com.hlcxdg.digou.ascytask.AddressAddOneAsynTask;
import com.hlcxdg.digou.bean.UserAddress;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.utils.PromptManager;

public class MineAddressAddActivity extends BaseActivity implements
		OnClickListener {

	public Button dz_add_btn_save;
	public EditText dz_add_et_receiver_name;
	public String receiver_name;
	public EditText dz_add_et_mobile;
	public String et_mobile;
	public String et_addr;
	public EditText dz_add_et_addr;
	public UserAddress userAddress;
	public AddressAddOneAsynTask addAddressTask;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dizhioneadd);
		initView();
	}

	ImageView iv_morenaddr;
	String flag = "0";

	public void initView() {
		iv_morenaddr = (ImageView) findViewById(R.id.iv_morenaddr);
		dz_add_et_receiver_name = (EditText) findViewById(R.id.dz_add_et_receiver_name);
		dz_add_et_mobile = (EditText) findViewById(R.id.dz_add_et_mobile);
		dz_add_et_addr = (EditText) findViewById(R.id.dz_add_et_addr);
		dz_add_btn_save = (Button) findViewById(R.id.dz_add_btn_save);
		dz_add_btn_save.setOnClickListener(this);
	}
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (addAddressTask != null && !addAddressTask.isCancelled()) {
			addAddressTask.cancel(true);
		}
	}
	@Override
	public void onClick(View views) {
		switch (views.getId()) {
		case R.id.dz_add_btn_save:
			if(verify()){
				//tianjiadizhi
				userAddress=new UserAddress();
				userAddress.UserNo=ConstantValue.userinfo.getUserNo();
				userAddress.AddUser_Name=receiver_name;
				userAddress.Tel=et_mobile;
				userAddress.Address_Name=et_addr;
				addAddressTask=new AddressAddOneAsynTask(context, dz_add_et_receiver_name, dz_add_et_addr, dz_add_et_mobile);
				addAddressTask.execute(userAddress);
				
			}
			
			break;
		default:
			break;
		}
	}

	private boolean verify() {
		receiver_name=dz_add_et_receiver_name.getText().toString();
		et_mobile=dz_add_et_mobile.getText().toString();
		et_addr=dz_add_et_addr.getText().toString();
		if (TextUtils.isEmpty(et_mobile)||TextUtils.isEmpty(receiver_name)||TextUtils.isEmpty(et_addr)) {
			PromptManager.showMyToast(context, "输入不能为空");
			return false;
		}
		return true;
	}

}