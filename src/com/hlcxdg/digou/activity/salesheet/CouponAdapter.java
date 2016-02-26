package com.hlcxdg.digou.activity.salesheet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.Coupon;
import com.hlcxdg.digou.bean.PayStyleBean;
import com.hlcxdg.digou.bean.SaleSheet;
import com.hlcxdg.digou.constant.ConstantIP;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.utils.PromptManager;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CouponAdapter extends BaseAdapter {
	private List<Coupon> mycouponList;

	private Context context;

	public CouponAdapter() {
		super();
	}

	public CouponAdapter(ArrayList<Coupon> couponList, Context context) {
		super();
		if (couponList == null) {
			this.mycouponList = new ArrayList<Coupon>();
		} else {
			this.mycouponList = couponList;
		}
		this.context = context;
	}

	public List<Coupon> getMycouponList() {
		return mycouponList;
	}

	public void setMycouponList(List<Coupon> couponList) {
		this.mycouponList = couponList;
	}

	public void addGoodsPL01List(List<Coupon> couponList) {
		this.mycouponList.addAll(couponList);
	}

	@Override
	public int getCount() {
		return mycouponList.size();
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

		TextView couponname;// 优惠名
		TextView fmoney;// 优惠金额
		// TextView ownername;// 优惠所属单位
		TextView coupon_item_date;// 优惠开始
		// TextView riqi2;// 优惠结诉

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ViewHolder holder = null;

		if (convertView == null) {
			view = View.inflate(context, R.layout.coupon_item_pic, null);
			holder = new ViewHolder();
			holder.couponname = (TextView) view
					.findViewById(R.id.coupon_item_name);
			holder.fmoney = (TextView) view
					.findViewById(R.id.coupon_item_money);
			// holder.ownername = (TextView) view.findViewById(R.id.ownername);
			holder.coupon_item_date = (TextView) view
					.findViewById(R.id.coupon_item_date);
			// holder.riqi2 = (TextView) view.findViewById(R.id.riqi2);
			holder.position = position;
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}

		// Coupon cCoupon = couponList.get(position);
		// coupon_item_name = (TextView) view
		// .findViewById(R.id.coupon_item_name);
		// fmoney = (TextView) view.findViewById(R.id.coupon_item_money);
		// coupon_item_date = (TextView) view
		// .findViewById(R.id.coupon_item_date);
		// // riqi1 = (TextView) view.findViewById(R.id.riqi1);
		// // riqi2 = (TextView) view.findViewById(R.id.riqi2);
		// coupon_item_name.setText(cCoupon.getName());
		// fmoney.setText("￥" + cCoupon.getFMoney().intValue());
		// coupon_item_date.setText(cCoupon.getRiqi1().substring(0, 10)
		// + "~" + cCoupon.getRiqi2().substring(0, 10));
		// // riqi1.setText(ConstantValue.couponList.get(position).getRiqi1());
		// // riqi2.setText(ConstantValue.couponList.get(position).getRiqi2());
		//

		try {
			Coupon coupon = mycouponList.get(position);
			holder.couponname.setText(coupon.getName());
			holder.fmoney.setText("￥" + coupon.getFMoney().intValue());
			holder.coupon_item_date.setText(coupon.getRiqi1().substring(0, 10)
					+ "~" + coupon.getRiqi2().substring(0, 10));
			// holder.ownername.setText(coupon.getOwnerName());
			//
			// holder.riqi1.setText(coupon.getRiqi1());

			// holder.riqi2.setText(coupon.getRiqi2());

		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

}
