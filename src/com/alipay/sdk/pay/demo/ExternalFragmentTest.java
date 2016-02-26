package com.alipay.sdk.pay.demo;


import java.math.BigDecimal;

import com.hlcxdg.digou.R;
import com.hlcxdg.digou.constant.ConstantValue;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ExternalFragmentTest extends Fragment {
	TextView pay_store_name;
	TextView pay_price_num;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.paybyzhifubao_fg, container,
				false);

		pay_store_name = (TextView) view.findViewById(R.id.pay_store_name);
		pay_price_num = (TextView) view.findViewById(R.id.pay_price_num);
		if (ConstantValue.myss != null) {
			pay_store_name.setText(ConstantValue.myss.getcStoreName());
			pay_price_num.setText(ConstantValue.myss.getFLastMoney()
					.setScale(2, BigDecimal.ROUND_HALF_UP).toString());

		}
		return view;
	}
}
