package com.hl_zhaoq.digou.activity.qiagndan;

import java.util.ArrayList;
import java.util.List;

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
public class StoreItemAdapter extends BaseAdapter {
	private List<StoreItem> storeItemList;
	private Context context;

	public StoreItemAdapter() {
		super();
	}

	public StoreItemAdapter(List<StoreItem> storeTypeList, Context context) {
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
			view = View.inflate(context, R.layout.test_fujindianpu_list_item,
					null);
			holder = new ViewHolder();
			holder.list_tv_store_name = (TextView) view
					.findViewById(R.id.list_tv_store_name);
			holder.list_tv_distance_store = (TextView) view
					.findViewById(R.id.list_tv_distance_store);
			holder.tv_addr_store_list = (TextView) view
					.findViewById(R.id.tv_addr_store_list);

			holder.tv_pinpai_name_store1 = (TextView) view
					.findViewById(R.id.tv_pinpai_name_store1);
			holder.tv_pinpai_name_store2 = (TextView) view
					.findViewById(R.id.tv_pinpai_name_store2);
			holder.tv_pinpai_name_store3 = (TextView) view
					.findViewById(R.id.tv_pinpai_name_store3);
			holder.iv_pinpai_pic_store1 = (SmartImageView) view
					.findViewById(R.id.iv_pinpai_pic_store1);
			holder.iv_pinpai_pic_store3 = (SmartImageView) view
					.findViewById(R.id.iv_pinpai_pic_store3);
			holder.iv_pinpai_pic_store2 = (SmartImageView) view
					.findViewById(R.id.iv_pinpai_pic_store2);
			holder.shoudanyouhui_tv = (TextView) view
					.findViewById(R.id.st_shoudanyouhui_tv);
			holder.jianmianyouhui_tv = (TextView) view
					.findViewById(R.id.st_jianmianyouhui_tv);
			holder.manzhengyouhui_tv = (TextView) view
					.findViewById(R.id.st_manzhengyouhui_tv);
			holder.manzhengyouhui_ll = (LinearLayout) view
					.findViewById(R.id.manzhengyouhui_ll);
			holder.position = position;
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		try {
			holder.list_tv_store_name.setText(storeItem.getcStoreName());
			holder.list_tv_distance_store.setText(storeItem.getfDistance()
					+ "km");
			switch (storeItem.getPinpai().size()) {
			case 1:
				holder.tv_pinpai_name_store1.setText(storeItem.getPinpai()
						.get(0).getO_cPinpaiName());
				holder.iv_pinpai_pic_store1.setImageUrl(ConstantIP.ImageviewURL
						+ storeItem.getPinpai().get(0).getcPinpaiGrantPic());
				break;
			case 2:
				holder.tv_pinpai_name_store1.setText(storeItem.getPinpai()
						.get(0).getO_cPinpaiName());
				holder.iv_pinpai_pic_store1.setImageUrl(ConstantIP.ImageviewURL
						+ storeItem.getPinpai().get(0).getcPinpaiGrantPic());
				holder.tv_pinpai_name_store2.setText(storeItem.getPinpai()
						.get(1).getO_cPinpaiName());
				holder.iv_pinpai_pic_store2.setImageUrl(ConstantIP.ImageviewURL
						+ storeItem.getPinpai().get(1).getcPinpaiGrantPic());
				break;
			default:
				holder.tv_pinpai_name_store1.setText(storeItem.getPinpai()
						.get(0).getO_cPinpaiName());
				holder.iv_pinpai_pic_store1.setImageUrl(ConstantIP.ImageviewURL
						+ storeItem.getPinpai().get(0).getcPinpaiGrantPic());
				holder.tv_pinpai_name_store2.setText(storeItem.getPinpai()
						.get(1).getO_cPinpaiName());
				holder.iv_pinpai_pic_store2.setImageUrl(ConstantIP.ImageviewURL
						+ storeItem.getPinpai().get(1).getcPinpaiGrantPic());
				holder.tv_pinpai_name_store3.setText(storeItem.getPinpai()
						.get(2).getO_cPinpaiName());
				holder.iv_pinpai_pic_store3.setImageUrl(ConstantIP.ImageviewURL
						+ storeItem.getPinpai().get(2).getcPinpaiGrantPic());

				break;
			}
			// holder.shoudanyouhui_tv.setText("�׵�����"
			// + storeItemlist.get(position).getFirstSheet_Cut()
			// .intValue() + "Ԫ");
			// holder.jianmianyouhui_tv.setText(storeItemlist.get(position)
			// .getOverCut());
			// if (storeItemlist.get(position).getGiftFree().equals("null")) {
			// holder.manzhengyouhui_ll.setVisibility(View.GONE);
			// } else {
			//
			// holder.manzhengyouhui_tv.setText(storeItemlist.get(position)
			// .getGiftFree());
			// }
			holder.tv_addr_store_list.setText(storeItem.getcAddr());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	class ViewHolder {
		int position;
		LinearLayout manzhengyouhui_ll;
		TextView list_tv_store_name;
		TextView list_tv_distance_store;
		TextView tv_pinpai_name_store1;
		TextView tv_pinpai_name_store2;
		TextView tv_pinpai_name_store3;
		TextView tv_addr_store_list;
		TextView shoudanyouhui_tv;
		TextView jianmianyouhui_tv;
		TextView manzhengyouhui_tv;
		SmartImageView iv_pinpai_pic_store1;
		SmartImageView iv_pinpai_pic_store3;
		SmartImageView iv_pinpai_pic_store2;
	}
}
