package com.hl_zhaoq.digou.activity.address;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.BaseActivity;
import com.hl_zhaoq.digou.adapter.AddressAdapter;
import com.hl_zhaoq.digou.ascytask.AddressAllGetAsynTask;
import com.hl_zhaoq.digou.bean.UserAddress;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;

public class MineAddressActivity extends BaseActivity implements
		OnClickListener, OnItemClickListener {

	private ListView lv_alladdr;
	private Button dz_btn_addAddress;
	private AddressAdapter addressAdapter;
	private UserAddress userAddress;
	private List<UserAddress> useraddrlist;
	private AddressAllGetAsynTask addressAllGetTask;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dizhialllist);

		initView();
		initData();
	}

	private void initData() {
		useraddrlist = new ArrayList<UserAddress>();
		addressAdapter=new AddressAdapter(useraddrlist, context);
		lv_alladdr.setAdapter(addressAdapter);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		addressAllGetTask = new AddressAllGetAsynTask(context, lv_alladdr,addressAdapter);
		addressAllGetTask.execute(ConstantValue.userinfo.getUserNo());
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (addressAllGetTask != null && !addressAllGetTask.isCancelled()) {
			addressAllGetTask.cancel(true);
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	public void initView() {
		lv_alladdr = (ListView) findViewById(R.id.lv_alladdr);
		lv_alladdr.setOnItemClickListener(this);
		dz_btn_addAddress = (Button) findViewById(R.id.dz_btn_addAddress);
		dz_btn_addAddress.setOnClickListener(this);
	}

	@Override
	public void onClick(View views) {
		switch (views.getId()) {
		case R.id.dz_btn_addAddress:
			startActivityNofinish(MineAddressAddActivity.class);
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
	}

}