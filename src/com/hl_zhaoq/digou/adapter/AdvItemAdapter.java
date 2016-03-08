package com.hl_zhaoq.digou.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hl_zhaoq.digou.bean.AdvertBean;
import com.hl_zhaoq.digou.bean.StoreItem;
import com.hl_zhaoq.digou.constant.ConstantIP;
import com.hl_zhaoq.digou.R;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AdvItemAdapter extends BaseAdapter {
	private List<AdvertBean> advertList;
	private Context context;

	public AdvItemAdapter() {
		super();
	}

	public AdvItemAdapter(List<AdvertBean> advertLists, Context context) {
		super();
		if (advertLists == null) {
			this.advertList = new ArrayList<AdvertBean>();
		} else {
			this.advertList = advertLists;
		}

		this.context = context;
	}

	public List<AdvertBean> getStoreItemList() {
		return advertList;
	}

	public void addStoreItemList(List<AdvertBean> storeItemList) {
		this.advertList.addAll(storeItemList);
	}

	@Override
	public int getCount() {
		return advertList.size();
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
	public View getView(int position, View convertView, ViewGroup par) {

		View view = null;
		ViewHolder holder = null;
		AdvertBean advertItem = advertList.get(position);
		if (convertView == null) {
			view = View.inflate(context, R.layout.fg_ads_shu_item, null);
			holder = new ViewHolder();

			holder.fg_advimg = (SmartImageView) view
					.findViewById(R.id.fg_advimg);
			holder.position = position;
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		holder.fg_advimg.setImageUrl("http://114.112.64.110:7080/O2O"
				+ advertItem.getAdImagePath());

		return view;
	}

	class ViewHolder {
		int position;
		SmartImageView fg_advimg;

	}
}
