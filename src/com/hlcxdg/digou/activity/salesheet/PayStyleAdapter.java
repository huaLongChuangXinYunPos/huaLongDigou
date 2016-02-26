package com.hlcxdg.digou.activity.salesheet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.PayStyleBean;
import com.hlcxdg.digou.constant.ConstantIP;
import com.hlcxdg.digou.constant.ConstantValue;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class PayStyleAdapter extends BaseAdapter {
	private List<PayStyleBean> payStyleList;

	private Context context;

	public PayStyleAdapter() {
		super();
	}

	public PayStyleAdapter(List<PayStyleBean> payStyleList, Context context) {
		super();
		if (payStyleList == null) {
			this.payStyleList = new ArrayList<PayStyleBean>();
		} else {
			this.payStyleList = payStyleList;
		}
		this.context = context;
	}

	public List<PayStyleBean> getGoodsPL01List() {
		return payStyleList;
	}

	public void setGoodsPL01List(List<PayStyleBean> payStyleList) {
		this.payStyleList = payStyleList;
	}

	public void addGoodsPL01List(List<PayStyleBean> payStyleList) {
		this.payStyleList.addAll(payStyleList);
	}

	@Override
	public int getCount() {
		return payStyleList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	class ViewHolder {
		int position;

		SmartImageView paystyle_pic_siv_item;
		ImageView paystyle_draw_iv_item;
		TextView paystyle_name_tv_item;
		TextView payway_lostmoneys_tv_sss;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ViewHolder holder = null;

		if (convertView == null) {
			view = View
					.inflate(context, R.layout.item_salesheet_paystyle, null);
			holder = new ViewHolder();
			holder.paystyle_pic_siv_item = (SmartImageView) view
					.findViewById(R.id.paystyle_pic_siv_item);
			holder.paystyle_draw_iv_item = (ImageView) view
					.findViewById(R.id.paystyle_draw_iv_item);
			holder.paystyle_name_tv_item = (TextView) view
					.findViewById(R.id.paystyle_name_tv_item);

			holder.payway_lostmoneys_tv_sss = (TextView) view
					.findViewById(R.id.payway_lostmoneys_tv_sss);
			holder.position = position;
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		try {

			PayStyleBean payStyleBean = payStyleList.get(position);

			holder.paystyle_pic_siv_item.setImageUrl(ConstantIP.ImageviewURL
					+ payStyleBean.getcPayStylePic());

			holder.paystyle_name_tv_item.setText(payStyleBean
					.getcPayStyleName());
			if (payStyleBean.getcPayStyleNo().equals("01")
					&& ConstantValue.lostWMoney != null) {
//				holder.payway_lostmoneys_tv_sss.setVisibility(View.VISIBLE);
//				holder.payway_lostmoneys_tv_sss.setText("余额："
//						+ ConstantValue.lostWMoney.setScale(2,
//								BigDecimal.ROUND_HALF_UP) + "");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

}
