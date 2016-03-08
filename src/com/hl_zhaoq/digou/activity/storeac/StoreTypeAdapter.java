package com.hl_zhaoq.digou.activity.storeac;

import java.util.ArrayList;
import java.util.List;

import com.hl_zhaoq.digou.bean.Goods;
import com.hl_zhaoq.digou.bean.StoreItem;
import com.hl_zhaoq.digou.bean.StoreType;
import com.hl_zhaoq.digou.R;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class StoreTypeAdapter extends BaseAdapter {
	private List<StoreType> storeTypeList;
	public List<StoreType> getStoreTypeList() {
		return storeTypeList;
	}

	public void addStoreTypeList(List<StoreType> storeTypeList) {
		this.storeTypeList.addAll(storeTypeList);
	}

	private Context context;
	private int lastposition;

	public int getLastposition() {
		return lastposition;
	}

	public void setLastposition(int lastposition) {
		this.lastposition = lastposition;
	}

	public StoreTypeAdapter() {
		super();
	}

	public StoreTypeAdapter(List<StoreType> storeTypeList, int lastposition,
			Context context) {
		super();
		if (storeTypeList == null) {
			this.storeTypeList = new ArrayList<StoreType>();
		} else {
			this.storeTypeList = storeTypeList;
		}

		this.lastposition = lastposition;

		this.context = context;
	}

	@Override
	public int getCount() {
		return storeTypeList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup arg2) {
		View view;
		TextView tv_yilei_gt1name;
		view = View.inflate(context, R.layout.leibie_yilei_item, null);
		tv_yilei_gt1name = (TextView) view.findViewById(R.id.tv_yilei_gt1name);
		if (position == lastposition) {
			tv_yilei_gt1name.setTextColor(Color.RED);
			tv_yilei_gt1name.setBackgroundResource(R.drawable.left);
		}
		tv_yilei_gt1name.setText(storeTypeList.get(position)
				.getcStoreTypeName());
		return view;
	}

}
