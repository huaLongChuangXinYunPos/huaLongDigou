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
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class GoodsListPl02Adapter extends BaseAdapter {
	private List<Goods> goodsPL02List;
	private String datessno;
	private String buyriqi;
	private Context context;
	SimpleDateFormat sdfssno ;
	SimpleDateFormat sdfriqi;
	public GoodsListPl02Adapter() {
		super();
	}

	public GoodsListPl02Adapter(List<Goods> goodsPL02List, Context context) {
		super();
		sdfssno= new SimpleDateFormat("MMddHHmmss");
		sdfriqi = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date d = new Date();
		datessno = sdfssno.format(d);
		buyriqi = sdfriqi.format(d);
		if (goodsPL02List == null) {
			this.goodsPL02List = new ArrayList<Goods>();
		} else {
			this.goodsPL02List = goodsPL02List;
		}
		this.context = context;
	}

	public List<Goods> getGoodsPL01List() {
		return goodsPL02List;
	}

	public void addGoodsPL01List(List<Goods> goodsPL02List) {
		this.goodsPL02List.addAll(goodsPL02List);
	}

	@Override
	public int getCount() {
		return goodsPL02List.size();
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
		ImageButton pl02_cart_ib;
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
			final Goods goods = goodsPL02List.get(position);
			holder.pl02_new_goods_iv = (ImageView) view
					.findViewById(R.id.pl02_new_goods_iv);
			holder.pl02_cart_ib = (ImageButton) view
					.findViewById(R.id.pl02_cart_ib);
			holder.pl02_top_ranking_tv = (TextView) view
					.findViewById(R.id.pl02_top_ranking_tv);
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
			holder.goods_distance_it = (TextView) view
					.findViewById(R.id.goods_distance_it);
			holder.pl02_cart_ib.setOnClickListener(new OnClickListener() {
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
			holder.tv_store_name_pl02.setText(goods.getcStoreName());
			holder.goods_sale_price_pl02.setText(goods.getfVipPrice_student()
					.setScale(2, BigDecimal.ROUND_HALF_UP) + "");
			holder.goods_beforepric_pl02.setText(goods.getfNormalPrice()
					.setScale(2, BigDecimal.ROUND_HALF_UP) + "");
			if (null != goods.getO_PriceFlag()) {
				holder.pl02_top_ranking_tv.setVisibility(View.VISIBLE);
			}
			if ("1".equals(goods.getO_bNew())) {
				holder.pl02_new_goods_iv.setVisibility(View.VISIBLE);
			}
			if (!TextUtils.isEmpty(goods.getO_distence())) {
				holder.goods_distance_it.setText(goods.getO_distence() + "公里");
			}

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
