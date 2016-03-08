package com.hl_zhaoq.digou.activity.goods;

import java.util.ArrayList;
import java.util.List;

import com.hl_zhaoq.digou.bean.Goods;
import com.hl_zhaoq.digou.constant.ConstantIP;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.R;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class GoodsListPl01Adapter extends BaseAdapter {
	private List<Goods> goodsPL01List;

	private Context context;

	public GoodsListPl01Adapter() {
		super();
	}

	public GoodsListPl01Adapter(List<Goods> goodsPL01List, Context context) {
		super();
		if (goodsPL01List == null) {
			this.goodsPL01List = new ArrayList<Goods>();
		} else {
			this.goodsPL01List = goodsPL01List;
		}
		this.context = context;
	}

	public List<Goods> getGoodsPL01List() {
		return goodsPL01List;
	}

	public void addGoodsPL01List(List<Goods> goodsPL01List) {
		this.goodsPL01List.addAll(goodsPL01List);
	}

	@Override
	public int getCount() {
		return goodsPL01List.size();
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
		SmartImageView goodicon;
		TextView goodsname;
		TextView top_ranking_tv;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ViewHolder holder = null;
		
		if (convertView == null) {
			view = View.inflate(context, R.layout.goodlist_item, null);
			holder = new ViewHolder();
			holder.position = position;
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		Goods goods = goodsPL01List.get(position);
		try {
			holder.goodicon = (SmartImageView) view.findViewById(R.id.goodicon);
			holder.goodicon.setImageUrl(ConstantIP.ImageviewURL
					+ goods.getMinIMG0());
			holder.top_ranking_tv = (TextView) view.findViewById(R.id.top_ranking_tv);
			holder.goodsname = (TextView) view.findViewById(R.id.goods_name);
			if (goods.getO_Famousspecialty()==null||goods.getO_Famousspecialty().trim().equals("0")) {
				holder.top_ranking_tv.setVisibility(View.GONE); 
			}else if (goods.getO_Famousspecialty()!=null&&goods.getO_Famousspecialty().trim().equals("1")){
				holder.top_ranking_tv.setVisibility(View.VISIBLE);
			}
			
			holder.goodsname.setText(goods
					.getcGoodsName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

}
