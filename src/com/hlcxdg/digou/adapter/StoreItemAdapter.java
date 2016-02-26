package com.hlcxdg.digou.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.Goods;
import com.hlcxdg.digou.bean.StoreItem;
import com.hlcxdg.digou.bean.StoreType;
import com.hlcxdg.digou.constant.ConstantIP;
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
			holder.shoudanyouhui_ll = (LinearLayout) view
					.findViewById(R.id.shoudanyouhui_ll);
			holder.jianmianyouhui_ll = (LinearLayout) view
					.findViewById(R.id.jianmianyouhui_ll);
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
			if (storeItem.getPinpai()!=null) {
				switch (storeItem.getPinpai().size()) {
				case 0:
					holder.tv_pinpai_name_store1.setVisibility(View.INVISIBLE);
					holder.iv_pinpai_pic_store1.setVisibility(View.INVISIBLE);
					holder.tv_pinpai_name_store2.setVisibility(View.INVISIBLE);
					holder.iv_pinpai_pic_store2.setVisibility(View.INVISIBLE);
					holder.tv_pinpai_name_store3.setVisibility(View.INVISIBLE);
					holder.iv_pinpai_pic_store3.setVisibility(View.INVISIBLE);
					break;
				case 1:

					holder.tv_pinpai_name_store1.setText(storeItem.getPinpai()
							.get(0).getO_cPinpaiName());
					holder.iv_pinpai_pic_store1.setImageUrl(ConstantIP.ImageviewURL
							+ storeItem.getPinpai().get(0).getcPinpaiGrantPic());
					holder.tv_pinpai_name_store1.setVisibility(View.VISIBLE);
					holder.iv_pinpai_pic_store1.setVisibility(View.VISIBLE);
					holder.tv_pinpai_name_store2.setVisibility(View.INVISIBLE);
					holder.iv_pinpai_pic_store2.setVisibility(View.INVISIBLE);
					holder.tv_pinpai_name_store3.setVisibility(View.INVISIBLE);
					holder.iv_pinpai_pic_store3.setVisibility(View.INVISIBLE);

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
					holder.tv_pinpai_name_store1.setVisibility(View.VISIBLE);
					holder.iv_pinpai_pic_store1.setVisibility(View.VISIBLE);
					holder.tv_pinpai_name_store2.setVisibility(View.VISIBLE);
					holder.iv_pinpai_pic_store2.setVisibility(View.VISIBLE);
					holder.tv_pinpai_name_store3.setVisibility(View.INVISIBLE);
					holder.iv_pinpai_pic_store3.setVisibility(View.INVISIBLE);
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
					holder.tv_pinpai_name_store1.setVisibility(View.VISIBLE);
					holder.iv_pinpai_pic_store1.setVisibility(View.VISIBLE);
					holder.tv_pinpai_name_store2.setVisibility(View.VISIBLE);
					holder.iv_pinpai_pic_store2.setVisibility(View.VISIBLE);
					holder.tv_pinpai_name_store3.setVisibility(View.VISIBLE);
					holder.iv_pinpai_pic_store3.setVisibility(View.VISIBLE);
					break;
				}
			}
			//youzhexianshi
			if (storeItem.MM_Valid) {
				if (storeItem.getGiftFree()==null||storeItem.getGiftFree().equals("")||storeItem.getGiftFree().equals("null")||storeItem.getGiftFree_Over().intValue()==0) {
					holder.manzhengyouhui_ll.setVisibility(View.GONE);
				} else {
					holder.manzhengyouhui_ll.setVisibility(View.VISIBLE);
				}
				if (storeItem.getFirstSheet()==null||storeItem.getFirstSheet().equals("")||storeItem.getFirstSheet().equals("null")||storeItem.getFirstSheet_Cut().intValue()==0) {
					holder.shoudanyouhui_ll.setVisibility(View.GONE);
				} else {
					holder.shoudanyouhui_ll.setVisibility(View.VISIBLE);
				}
				if (storeItem.getOverCut()==null||storeItem.getOverCut().equals("")||storeItem.getOverCut().equals("null")||storeItem.getCut_Money().intValue()==0||storeItem.getOver_Money().intValue()==0) {
					holder.jianmianyouhui_ll.setVisibility(View.GONE);
				} else {
					holder.jianmianyouhui_ll.setVisibility(View.VISIBLE);
				}

				holder.shoudanyouhui_tv.setText("首单满"+storeItem.getFirstSheet_Over().setScale(2, BigDecimal.ROUND_HALF_UP)+"减"+storeItem.getFirstSheet_Cut().setScale(2, BigDecimal.ROUND_HALF_UP));
				holder.jianmianyouhui_tv.setText("满"+storeItem.getOver_Money().setScale(2, BigDecimal.ROUND_HALF_UP)+"减"+storeItem.getCut_Money().setScale(2, BigDecimal.ROUND_HALF_UP));

				holder.manzhengyouhui_tv.setText("满"+storeItem.getGiftFree_Over().setScale(2, BigDecimal.ROUND_HALF_UP)+"赠"+storeItem.getGiftFree_GiftGoodsNo());
			}
			
			holder.tv_addr_store_list.setText(storeItem.getcAddr());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	class ViewHolder {
		int position;
		LinearLayout shoudanyouhui_ll;
		LinearLayout jianmianyouhui_ll;
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
