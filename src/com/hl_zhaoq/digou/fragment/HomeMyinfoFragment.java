package com.hl_zhaoq.digou.fragment;

import java.math.BigDecimal;

import vivolibrary.QueryBalance;
import vivolibrary.SoftwareVerify;

import com.hl_zhaoq.digou.activity.MainActivity;
import com.hl_zhaoq.digou.activity.address.AddressActivity;
import com.hl_zhaoq.digou.activity.address.MineAddressActivity;
import com.hl_zhaoq.digou.activity.address.AddressActivity.AddAddress;
import com.hl_zhaoq.digou.activity.salesheet.SaleSheetMineActivity;
import com.hl_zhaoq.digou.activity.user.LoginActivity;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.tel.activity.BindActivity;
import com.hl_zhaoq.digou.tel.cst.ConstantTel;
import com.hl_zhaoq.digou.utils.PromptManager;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class HomeMyinfoFragment extends Fragment implements OnClickListener{
	private RelativeLayout fg_rl_addressupdate;
	private TextView homemy_loginreg;
	private TextView userinfo_username;
	private LinearLayout had_login;
	View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.home_myinfo_fg, null);
		initView();
		initListener();
		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (ConstantValue.userinfo != null) {
			had_login.setVisibility(View.VISIBLE);

			userinfo_username.setText(ConstantValue.userinfo.getUserName()
					.trim());

			homemy_loginreg.setText("退出登录");
		} else {
			had_login.setVisibility(View.GONE);

			userinfo_username.setText("");

			homemy_loginreg.setText("登录/注册");
		}
	}

	//
	public void initView() {
		had_login = (LinearLayout) view.findViewById(R.id.had_login);
		fg_rl_addressupdate=(RelativeLayout) view.findViewById(R.id.fg_rl_addressupdate);
		fg_rl_addressupdate.setOnClickListener(this);
		userinfo_username = (TextView) view
				.findViewById(R.id.userinfo_username);
		homemy_loginreg = (TextView) view.findViewById(R.id.homemy_loginreg);
	}

	//
	public void initListener() {

		homemy_loginreg.setOnClickListener(new MyOnClickListner());// �󶨰�ť����
	}

	//
	private class MyOnClickListner implements OnClickListener {

		Intent intent;

		@Override
		public void onClick(View v) {
			int _ViewID = v.getId();
			switch (_ViewID) {
			case R.id.homemy_loginreg:
				if (ConstantValue.userinfo != null) {
					had_login.setVisibility(View.GONE);
					userinfo_username.setText("");
					homemy_loginreg.setText("登录/注册");
					ConstantValue.userinfo = null;
					ConstantValue.cartSaleSheetList.clear();
					ConstantValue.total_Nums_cart=0;
					ConstantValue.total_price_cart=new BigDecimal(0.0);
					PromptManager.showMyToast(getActivity(), "退出成功");
				} else {
					intent = new Intent();
					intent.setClass(getActivity(), LoginActivity.class);
					startActivity(intent);
				}
				break;
			}
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.fg_rl_addressupdate://修改地址按钮
			if (ConstantValue.userinfo != null) {
				intent = new Intent();
				intent.setClass(getActivity(), MineAddressActivity.class);
//				intent.setClass(getActivity(), AddressActivity.class);
				startActivity(intent);
			} else {
				PromptManager.showMyToast(getActivity(), "请先登录");
				intent = new Intent();
				intent.setClass(getActivity(), LoginActivity.class);
				getActivity().startActivityForResult(intent, 1004);
			
			}
			break;

		default:
			break;
		}
		
	}
}
