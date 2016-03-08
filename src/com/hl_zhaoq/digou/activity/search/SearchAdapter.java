package com.hl_zhaoq.digou.activity.search;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.hl_zhaoq.digou.bean.Goods;
import com.hl_zhaoq.digou.constant.ConstantIP;
import com.hl_zhaoq.digou.R;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SearchAdapter extends BaseAdapter {
	private List<Goods> goodsSearchList;

	private Context context;

	public SearchAdapter() {
		super();
	}

	public SearchAdapter(List<Goods> goodsSearchList, Context context) {
		super();
		if (goodsSearchList == null) {
			this.goodsSearchList = new ArrayList<Goods>();
		} else {
			this.goodsSearchList = goodsSearchList;
		}
		this.context = context;
	}

	public List<Goods> getGoodsSearchList() {
		return goodsSearchList;
	}

	public void addGoodsSearchList(List<Goods> goodsSearchList) {
		this.goodsSearchList.addAll(goodsSearchList);
	}

	@Override
	public int getCount() {
		return goodsSearchList.size();
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

		SmartImageView goodicon_pl02;
		ImageView pl02_new_goods_iv;
		TextView pl02_top_ranking_tv;
		TextView tv_store_name_pl02;
		TextView goods_sale_price_pl02;
		TextView goods_beforepric_pl02;
		TextView goods_name_pl02;
		TextView goods_distance_it;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ViewHolder holder = null;

		if (convertView == null) {
			view = View.inflate(context, R.layout.pl02goodlist_item, null);
			holder = new ViewHolder();
			holder.position = position;
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		try {
			Goods goods = goodsSearchList.get(position);
			holder.pl02_new_goods_iv = (ImageView) view
					.findViewById(R.id.pl02_new_goods_iv);
			holder.pl02_top_ranking_tv = (TextView) view
					.findViewById(R.id.pl02_top_ranking_tv);
			holder.goods_distance_it= (TextView) view
					.findViewById(R.id.goods_distance_it);
			holder.goodicon_pl02 = (SmartImageView) view
					.findViewById(R.id.goodicon_pl02);
			holder.goodicon_pl02.setImageUrl(ConstantIP.ImageviewURL
					+ goods.getMinIMG0());
			holder.tv_store_name_pl02 = (TextView) view
					.findViewById(R.id.tv_store_name_pl02);
			holder.goods_name_pl02 = (TextView) view
					.findViewById(R.id.goods_name_pl02);
			holder.goods_sale_price_pl02 = (TextView) view
					.findViewById(R.id.goods_sale_price_pl02);
			holder.goods_beforepric_pl02 = (TextView) view
					.findViewById(R.id.goods_beforepric_pl02);
			holder.goods_name_pl02.setText(goods.getcGoodsName());
			holder.tv_store_name_pl02.setText(goods.getcStoreName());
			holder.goods_sale_price_pl02.setText(goods.getfVipPrice_student()
					.setScale(2, BigDecimal.ROUND_HALF_UP) + "");
			holder.goods_beforepric_pl02.setText("原价："+goods.getfVipPrice().setScale(
					2, BigDecimal.ROUND_HALF_UP)+ "");
			if (null != goods.getO_PriceFlag()) {
				holder.pl02_top_ranking_tv.setVisibility(View.VISIBLE);
			}
			if ("1".equals(goods.getO_bNew())) {
				holder.pl02_new_goods_iv.setVisibility(View.VISIBLE);
			}
			if (!TextUtils.isEmpty(goods.getO_distence())) {
				holder.goods_distance_it.setText(goods.getO_distence()+"km");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

}
