package com.hl_zhaoq.digou.adapter;

import java.util.ArrayList;
import java.util.List;

import com.hl_zhaoq.digou.bean.Activity;
import com.hl_zhaoq.digou.bean.Goods;
import com.hl_zhaoq.digou.bean.StoreItem;
import com.hl_zhaoq.digou.bean.StoreType;
import com.hl_zhaoq.digou.constant.ConstantIP;
import com.hl_zhaoq.digou.R;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class STStoreItemAdapter extends BaseAdapter {
	private List<StoreItem> storeItemList;
	private Context context;

	public STStoreItemAdapter() {
		super();
	}

	public STStoreItemAdapter(List<StoreItem> storeTypeList, Context context) {
		super();
		if (storeTypeList == null) {
			this.storeItemList = new ArrayList<StoreItem>();
		} else {
			this.storeItemList = storeTypeList;
		}

		this.context = context;
	}

	public List<StoreItem> getStoreItemList() {
		return storeItemList;
	}

	public void addStoreItemList(List<StoreItem> storeItemList) {
		this.storeItemList.addAll(storeItemList);
	}

	@Override
	public int getCount() {
		return storeItemList.size();
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
		StoreItem storeItem = storeItemList.get(position);
		if (convertView == null) {
			view = View.inflate(context, R.layout.test_fujindianpu_cuxiao_list_item,
					null);
			holder = new ViewHolder();

			holder.list_tv_distance_store_cx = (TextView) view
					.findViewById(R.id.list_tv_distance_store_cx);
			holder.list_tv_store_name_cx = (TextView) view
					.findViewById(R.id.list_tv_store_name_cx);
			
			holder.goods_promote_layout_cx= (LinearLayout) view
					.findViewById(R.id.goods_promote_layout_cx);
			
			
			holder.position = position;
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		if (storeItem.getfDistance() != 0.0f) {
			holder.list_tv_distance_store_cx.setText(storeItem.getfDistance()
					+ "公里");

		}
		holder.list_tv_store_name_cx.setText(storeItem.getcStoreName());
//		holder.name_cx.setText(storeItem.getActivity().get(0).getcPloyTypeName());
		
		for (int i = 0; i < storeItem.getActivity().size(); i++) {
			View v=View.inflate(context, R.layout.test_fujindianpu_cuxiao_list_item_celue, null);
			Activity activity1=storeItem.getActivity().get(i);
			TextView name_cx=(TextView) v.findViewById(R.id.name_cx);
			TextView num_cx=(TextView) v.findViewById(R.id.num_tv_cx);
			TextView over_cx=(TextView) v.findViewById(R.id.overtime_tv_cx);
			TextView days_cx=(TextView) v.findViewById(R.id.endtime_tv_cx);
			name_cx.setText(activity1.getcPloyTypeName());
			num_cx.setText(activity1.getfQuantity_Ploy()+"");
			over_cx.setText(activity1.getdDateStart().substring(0,10)+" 至 "+activity1.getdDateEnd().substring(0,10));
			days_cx.setText(activity1.Days+"天"+activity1.Hours+"时"+activity1.minutes+"分");
			
			holder.goods_promote_layout_cx.addView(v);
		}
//		holder.num_tv_cx.setText(storeItem.getActivity().get(0).getfQuantity_Ploy()+"");
//		holder.starttime_tv_cx.setText(storeItem.getActivity().get(0).getdDateStart().substring(0,10)+" 至 "+storeItem.getActivity().get(0).getdDateEnd().substring(0,10));;
//		holder.endtime_tv_cx.setText(storeItem.getActivity().get(0).Days+"天"+storeItem.getActivity().get(0).Hours+"时"+storeItem.getActivity().get(0).minutes+"分");;
//		
		return view;
	}

	class ViewHolder {
		int position;
		LinearLayout goods_promote_layout_cx;
		TextView list_tv_store_name_cx;

		TextView list_tv_distance_store_cx;

		TextView name_cx;

		TextView num_tv_cx;
		TextView starttime_tv_cx;
		TextView endtime_tv_cx;

	}
}
