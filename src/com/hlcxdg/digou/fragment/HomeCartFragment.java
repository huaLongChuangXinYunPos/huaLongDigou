package com.hlcxdg.digou.fragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vivolibrary.MakeCall;
import vivolibrary.SoftwareVerify;

import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.MainActivity;
import com.hlcxdg.digou.activity.address.AddressActivity;
import com.hlcxdg.digou.activity.salesheet.SalesheetActivity;
import com.hlcxdg.digou.bean.SaleSheet;
import com.hlcxdg.digou.bean.SaleSheetDetail;
import com.hlcxdg.digou.constant.ConstantIP;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.tel.cst.ConstantTel;
import com.hlcxdg.digou.utils.PromptManager;
import com.loopj.android.image.SmartImageView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class HomeCartFragment extends Fragment {
	View view;
	private ListView cartlist1;
	private TextView tv_total_price;
	private Button cart_jiesuan_submit;
	ImageView cartlist_empty;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.cartlist, null);

		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		initView();
		getcartlist();
		gettotalprice();

	}

	public void initView() {
		cartlist_empty = (ImageView) view.findViewById(R.id.cartlist_empty);
		tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);
		cartlist1 = (ListView) view.findViewById(R.id.cartlist1);
		cart_jiesuan_submit = (Button) view
				.findViewById(R.id.cart_jiesuan_submit);
		if (ConstantValue.cartSaleSheetList != null
				&& ConstantValue.cartSaleSheetList.size() == 0) {
			cart_jiesuan_submit.setBackgroundColor(Color.GRAY);
		}

	}

	private void gettotalprice() {
		if (ConstantValue.cartSaleSheetList.size() == 0) {
			tv_total_price.setText("总价：￥0元");
			cartlist_empty.setVisibility(View.VISIBLE);
		} else {
			BigDecimal totalprice1 = new BigDecimal("0");
			for (int i = 0; i < ConstantValue.cartSaleSheetList.size(); i++) {
				SaleSheet saleSheet = ConstantValue.cartSaleSheetList.get(i);
				List<SaleSheetDetail> saleSheetDetailList = saleSheet
						.getSaleSheetDetailList();
				for (int j = 0; j < saleSheetDetailList.size(); j++) {
					SaleSheetDetail cSSD = saleSheetDetailList.get(j);
					BigDecimal money = cSSD.getfLastMoney().multiply(
							cSSD.getfQuantity());
					totalprice1 = totalprice1.add(money);
				}
			}
			ConstantValue.total_price_cart = totalprice1;
			tv_total_price.setText("总价：￥" + totalprice1.setScale(2, BigDecimal.ROUND_HALF_UP) + "元");
			

		}

	}

	public void getcartlist() {
		if (ConstantValue.cartSaleSheetList.size() == 0) {
			// PromptManager.showMyToast(getActivity(), "���ﳵΪ��..");
		} else {
			initCartListAdapter();

		}
	}

	/*
	 * ���ﳵ��������
	 */
	public class MyCartListAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return ConstantValue.cartSaleSheetList.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parentView) {
			// ����Ƕ�׵�cartListItem
			View view;
			TextView storename_ssitem_cart;
			TextView storeprice_ssitem_cart;
			LinearLayout cart_add_item;
			view = View.inflate(getActivity(), R.layout.cart_item_od01, null);
			cart_add_item = (LinearLayout) view
					.findViewById(R.id.cart_add_item);
			storename_ssitem_cart = (TextView) view
					.findViewById(R.id.storename_ssitem_cart);
			storeprice_ssitem_cart = (TextView) view
					.findViewById(R.id.storeprice_ssitem_cart);
			final SaleSheet saleSheet = ConstantValue.cartSaleSheetList
					.get(position);
			storename_ssitem_cart.setText(saleSheet.getcStoreName());
			storeprice_ssitem_cart.setText(saleSheet.getFMoney().setScale(
					2, BigDecimal.ROUND_HALF_UP)+ "");
			final List<SaleSheetDetail> saleSheetDetailList = saleSheet
					.getSaleSheetDetailList();
			for (int i = 0; i < saleSheetDetailList.size(); i++) {
				final SaleSheetDetail ssd1 = saleSheetDetailList.get(i);
				View viewssdetail;
				final int ssdposition = i;
				viewssdetail = View.inflate(getActivity(),
						R.layout.cartlist_ssd_item, null);
				TextView cart_ssd_goods_name;
				TextView cart_ssd_goods_price21;
				TextView cart_ssd_num_edit;
				SmartImageView cart_ssd_goods_icon;
				cart_ssd_goods_icon = (SmartImageView) viewssdetail
						.findViewById(R.id.cart_ssd_goods_icon);
				cart_ssd_goods_icon.setImageUrl(ConstantIP.ImageviewURL
						+ ssd1.getMinIMG0());
				cart_ssd_goods_name = (TextView) viewssdetail
						.findViewById(R.id.cart_ssd_goods_name);
				cart_ssd_goods_price21 = (TextView) viewssdetail
						.findViewById(R.id.cart_ssd_goods_price21);
				cart_ssd_num_edit = (TextView) viewssdetail
						.findViewById(R.id.cart_ssd_num_edit);

				cart_ssd_goods_name.setText(ssd1.getcGoodsName());
				cart_ssd_goods_price21.setText(ssd1.getfLastMoney().setScale(2,
						BigDecimal.ROUND_HALF_UP)
						+ "");
				cart_ssd_num_edit.setText(ssd1.getfQuantity().intValue() + "");
				final ImageButton jia;
				final ImageButton jian;
				jia = (ImageButton) viewssdetail
						.findViewById(R.id.jia_cart_ssd);
				jian = (ImageButton) viewssdetail
						.findViewById(R.id.jian_cart_ssd);
				jia.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						ssd1.setfQuantity(ssd1.getfQuantity().add(
								new BigDecimal("1")));

						ConstantValue.total_Nums_cart += 1;
						MainActivity.iv_cart_dian_menu
								.setText(ConstantValue.total_Nums_cart + "");
						gettotalprice();
						BigDecimal saleSheetMoney = new BigDecimal("0.00");
						for (int j = 0; j < saleSheetDetailList.size(); j++) {
							SaleSheetDetail cSSD = saleSheetDetailList.get(j);
							BigDecimal money = cSSD.getfLastMoney().multiply(
									cSSD.getfQuantity());
							saleSheetMoney = saleSheetMoney.add(money);
						}
						saleSheet.setFLastMoney(saleSheetMoney);
						saleSheet.setFMoney(saleSheetMoney);
						notifyDataSetChanged();
					}

				});

				jian.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						ssd1.setfQuantity(ssd1.getfQuantity().subtract(
								new BigDecimal("1")));
						if (ssd1.getfQuantity().intValue() == 0) {
							saleSheetDetailList.remove(ssdposition);

							if (saleSheetDetailList.size() == 0) {
								ConstantValue.cartSaleSheetList
										.remove(position);
							}

						}
						ConstantValue.total_Nums_cart -= 1;
						if (ConstantValue.total_Nums_cart == 0) {
							MainActivity.iv_cart_dian_menu
									.setVisibility(View.GONE);
						} else {
							MainActivity.iv_cart_dian_menu
									.setText(ConstantValue.total_Nums_cart + "");

						}
						BigDecimal saleSheetMoney = new BigDecimal("0.00");
						for (int j = 0; j < saleSheetDetailList.size(); j++) {
							SaleSheetDetail cSSD = saleSheetDetailList.get(j);
							BigDecimal money = cSSD.getfLastMoney().multiply(
									cSSD.getfQuantity());
							saleSheetMoney = saleSheetMoney.add(money);
						}
						saleSheet.setFLastMoney(saleSheetMoney);
						saleSheet.setFMoney(saleSheetMoney);
						gettotalprice();
						notifyDataSetChanged();
					}
				});
				cart_add_item.addView(viewssdetail);
			}

			return view;

		}

	}

	private void initCartListAdapter() {
		cartlist1.setAdapter(new MyCartListAdapter());

	}

}
