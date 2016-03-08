package com.hl_zhaoq.digou.activity.salesheet;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.hl_zhaoq.digou.bean.PayStyleBean;
import com.hl_zhaoq.digou.bean.SaleSheet;
import com.hl_zhaoq.digou.constant.ConstantIP;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.R;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class PASaleSheetAdapter extends BaseAdapter {
	private List<SaleSheet> payStyleList;

	private Context context;

	public PASaleSheetAdapter() {
		super();
	}

	public PASaleSheetAdapter(List<SaleSheet> payStyleList, Context context) {
		super();
		if (payStyleList == null) {
			this.payStyleList = new ArrayList<SaleSheet>();
		} else {
			this.payStyleList = payStyleList;
		}
		this.context = context;
	}

	public List<SaleSheet> getGoodsPL01List() {
		return payStyleList;
	}

	public void setGoodsPL01List(List<SaleSheet> payStyleList) {
		this.payStyleList = payStyleList;
	}

	public void addGoodsPL01List(List<SaleSheet> payStyleList) {
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
		Button sslist_fukuan;
		TextView pa_ss_tv_ssno;
		TextView pa_ss__tv_fprice;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ViewHolder holder = null;

		if (convertView == null) {
			view = View
					.inflate(context, R.layout.item_salesheet_pa_zhifu, null);
			holder = new ViewHolder();
			holder.pa_ss_tv_ssno = (TextView) view
					.findViewById(R.id.pa_ss_tv_ssno);
			holder.pa_ss__tv_fprice = (TextView) view
					.findViewById(R.id.pa_ss__tv_fprice);
			holder.sslist_fukuan = (Button) view
					.findViewById(R.id.sslist_fukuan);
			holder.position = position;
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		try {
			
			
			SaleSheet bSaleSheet = payStyleList.get(position);
			holder.pa_ss_tv_ssno.setText("订单："
					+bSaleSheet.getcSaleSheetno());
			holder.pa_ss__tv_fprice.setText("金额："
					+ bSaleSheet.getFLastMoney().setScale(2,
							BigDecimal.ROUND_HALF_UP) + "");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

}
