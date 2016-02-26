package com.hlcxdg.digou.activity.goods;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.user.LoginActivity;
import com.hlcxdg.digou.bean.Goods;
import com.hlcxdg.digou.bean.SaleSheet;
import com.hlcxdg.digou.bean.SaleSheetDetail;
import com.hlcxdg.digou.constant.ConstantIP;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.utils.PromptManager;
import com.loopj.android.image.SmartImageView;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MingyouGoodsAdapter extends BaseAdapter {
	private List<Goods> goodsFsList;
	private String datessno;
	private String buyriqi;
	SimpleDateFormat sdfssno ;
	SimpleDateFormat sdfriqi;
	private Context context;

	public MingyouGoodsAdapter() {
		super();
	}

	public List<Goods> getGoodsFsList() {
		return goodsFsList;
	}

	public void addGoodsFsList(List<Goods> goodsFsList) {
		this.goodsFsList.addAll(goodsFsList);
	}

	public MingyouGoodsAdapter(List<Goods> goodsFsList, Context context) {
		super();
		if (goodsFsList == null) {
			this.goodsFsList = new ArrayList<Goods>();
		} else {
			this.goodsFsList = goodsFsList;
		}
		this.context = context;
		sdfssno= new SimpleDateFormat("MMddHHmmss");
		sdfriqi = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		datessno = sdfssno.format(d);
		buyriqi = sdfriqi.format(d);
	}

	@Override
	public int getCount() {
		return goodsFsList.size();
	}

	@Override
	public Object getItem(int arg0) {
		return arg0;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	class ViewHolder {
		SmartImageView fs_goodicon;
		TextView fs_goods_name;
		TextView goods_name_nor;
		TextView goods_name_st;
		TextView storename_fs;
		TextView fs_top_ranking_tv;
		ImageView fs_new_goods_iv;
		ImageButton mingyou_cart_fs;
		int position;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		ViewHolder holder = null;

		if (convertView == null) {
			view = View.inflate(context, R.layout.goodlist_item_fs, null);
			holder = new ViewHolder();
			holder.position = position;
			view.setTag(holder);
		} else {
			view = convertView;
			holder = (ViewHolder) view.getTag();
		}
		try {
			final Goods goods = goodsFsList.get(position);
			holder.fs_new_goods_iv = (ImageView) view
					.findViewById(R.id.fs_new_goods_iv);
			holder.fs_top_ranking_tv = (TextView) view
					.findViewById(R.id.fs_top_ranking_tv);
			holder.fs_goodicon = (SmartImageView) view
					.findViewById(R.id.fs_goodicon);
			holder.mingyou_cart_fs = (ImageButton) view
					.findViewById(R.id.mingyou_cart_fs);
			if (null != goodsFsList.get(position).getO_bNew()) {
				holder.fs_new_goods_iv.setVisibility(View.VISIBLE);
			}
			if (null != goodsFsList.get(position).getO_Famousspecialty()) {
				holder.fs_top_ranking_tv.setVisibility(View.VISIBLE);
			}

			holder.fs_goodicon.setImageUrl(ConstantIP.ImageviewURL
					+ goods.getMinIMG0());
			holder.storename_fs = (TextView) view
					.findViewById(R.id.storename_fs);
			holder.fs_goods_name = (TextView) view
					.findViewById(R.id.fs_goods_name);
			holder.goods_name_st = (TextView) view
					.findViewById(R.id.goods_name_st);
			holder.goods_name_nor = (TextView) view
					.findViewById(R.id.goods_name_nor);
			holder.fs_goods_name.setText(goods.getcGoodsName());
			holder.storename_fs.setText(goods.getcStoreName());
			holder.goods_name_st.setText(goods.getfVipPrice_student()
					.setScale(2, BigDecimal.ROUND_HALF_UP) + "");
			holder.goods_name_nor.setText(goods.getfNormalPrice().setScale(
					2, BigDecimal.ROUND_HALF_UP)
					+ "");
			holder.mingyou_cart_fs.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					try {
						ConstantValue.goodsinfo=goods;
						jiarugouwuche();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}
	public void addtoLastCartList() {

		SaleSheetDetail ssd = new SaleSheetDetail();
		Goods g = ConstantValue.goodsinfo;
		ssd.setdSaleDate(buyriqi);
		ssd.setcSaleSheetno(datessno);
		ssd.setMinIMG0(g.getMinIMG0());
		ssd.setcGoodsNo(g.getcGoodsNo());
		ssd.setcGoodsName(g.getcGoodsName());
		ssd.setfQuantity(new BigDecimal("1"));
		ssd.setfLastMoney(g.getfVipPrice_student().setScale(2,
				BigDecimal.ROUND_HALF_UP));
		ArrayList<SaleSheetDetail> cartItemDataSSD = new ArrayList<SaleSheetDetail>();
		cartItemDataSSD.add(ssd);
		SaleSheet ss = new SaleSheet();
		ss.setcStoreNo(g.getcStoreNo());
		ss.setcStoreName(g.getcStoreName());
		ss.setDSaleDate(buyriqi);
		ss.setCSaleSheetno(datessno);
		ss.setUserNo(ConstantValue.userinfo.getUserNo());
		ss.setFLastMoney(g.getfVipPrice_student().setScale(2,
				BigDecimal.ROUND_HALF_UP));
		ss.setfMoney(g.getfVipPrice_student().setScale(2,
				BigDecimal.ROUND_HALF_UP));
		ss.setSaleSheetDetailList(cartItemDataSSD);
		ConstantValue.cartSaleSheetList.add(ss);
	}
	public void dengluqu() {
		PromptManager.showMyToast(context, "请您先登陆");
		Intent intent;
		intent = new Intent();
		intent.setClass(context, LoginActivity.class);
		context.startActivity(intent);
	}

	public void jiarugouwuche() {
		
		if (ConstantValue.userinfo == null) {
			dengluqu();
		} else if (ConstantValue.cartSaleSheetList != null) {
			PromptManager.showMyToast(context, "已经添加到购物车");
			int indexGoodsInCart = -1;
			// 查重cartSaleSheetList
			for (int i = 0; i < ConstantValue.cartSaleSheetList.size(); i++) {
				SaleSheet saleSheetItem = ConstantValue.cartSaleSheetList
						.get(i);

				if (saleSheetItem.getcStoreNo() != null
						&& ConstantValue.goodsinfo.getcStoreNo() != null
						&& saleSheetItem.getcStoreNo().equals(
								ConstantValue.goodsinfo.getcStoreNo())) {
					indexGoodsInCart = i;
					break;
				}
			}

			if (indexGoodsInCart != -1) {
				insertIntoCart(indexGoodsInCart);
			} else {
				addtoLastCartList();
			}
			ConstantValue.total_Nums_cart += 1;
//			if (ConstantValue.total_Nums_cart != 0) {
//				tv_pd_shuliang.setVisibility(View.VISIBLE);
//				tv_pd_shuliang.setText("" + ConstantValue.total_Nums_cart);
//			} else {
//				tv_pd_shuliang.setVisibility(View.GONE);
//			}
		}
	}

	private void insertIntoCart(int indexGoodsInCart) {
		SaleSheet saleSheet = ConstantValue.cartSaleSheetList
				.get(indexGoodsInCart);
		List<SaleSheetDetail> saleSheetDetailList = saleSheet
				.getSaleSheetDetailList();
		int indexGoodsInSSD = -1;
		for (int j = 0; j < saleSheetDetailList.size(); j++) {
			SaleSheetDetail ssd1 = saleSheetDetailList.get(j);
			if (ssd1.getcGoodsNo()
					.equals(ConstantValue.goodsinfo.getcGoodsNo())) {
				indexGoodsInSSD = j;
				break;
			}

		}
		if (indexGoodsInSSD != -1) {
			SaleSheetDetail ssd = saleSheetDetailList.get(indexGoodsInSSD);
			BigDecimal q = ssd.getfQuantity();
			BigDecimal totle = q.add(new BigDecimal(1));
			ssd.setfQuantity(totle);
		} else {
			SaleSheetDetail ssd = new SaleSheetDetail();
			Goods g = ConstantValue.goodsinfo;
			ssd.setdSaleDate(buyriqi);
			ssd.setcSaleSheetno(datessno);
			ssd.setMinIMG0(g.getMinIMG0());
			ssd.setcGoodsNo(g.getcGoodsNo());
			ssd.setcGoodsName(g.getcGoodsName());
			ssd.setfQuantity(new BigDecimal("1"));
			ssd.setfLastMoney(g.getfVipPrice_student().setScale(2,
					BigDecimal.ROUND_HALF_UP));
			saleSheetDetailList.add(ssd);
		}
		BigDecimal fMoney = saleSheet.fLastMoney.add(ConstantValue.goodsinfo
				.getfVipPrice_student().setScale(2, BigDecimal.ROUND_HALF_UP));
		saleSheet.fLastMoney = fMoney;
		saleSheet.fMoney = fMoney;
	}

}
