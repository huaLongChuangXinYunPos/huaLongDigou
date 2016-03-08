package com.hl_zhaoq.digou.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hl_zhaoq.digou.bean.UserAddress;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.R;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AddressAdapter extends BaseAdapter {
	private List<UserAddress> useraddrlist;
	private Context context;

	public AddressAdapter(List<UserAddress> useraddrlist, Context context) {
		super();
		this.context = context;
		if (useraddrlist == null) {
			useraddrlist = new ArrayList<UserAddress>();
		}
		this.useraddrlist = useraddrlist;
	}

	@Override
	public View getView(int position, View cv, ViewGroup p) {
		View view;

		TextView user;
		TextView phone;
		TextView address_detail;
		ImageView add_triangle;
		view = View.inflate(context, R.layout.dizhi_item, null);
		user = (TextView) view.findViewById(R.id.user);
		phone = (TextView) view.findViewById(R.id.phone);
		phone.setText(useraddrlist.get(position).getTel());
		address_detail = (TextView) view.findViewById(R.id.address_detail);
		address_detail.setText(useraddrlist.get(position).getAddress_Name());
		user.setText(useraddrlist.get(position).getAddUser_Name());
		add_triangle = (ImageView) view.findViewById(R.id.add_triangle);
		if (position == 0) {
			add_triangle.setVisibility(View.VISIBLE);
		}

		return view;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public int getCount() {
		return useraddrlist.size();
	}

	public void setData(List<UserAddress> result) {
		useraddrlist=result;
	}

}
