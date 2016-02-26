package com.hlcxdg.digou.fragment;

import java.util.ArrayList;
import java.util.List;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.MainActivity;
import com.hlcxdg.digou.activity.coupon.CouponActivity;
import com.hlcxdg.digou.activity.goods.GoodSingleActivity;
import com.hlcxdg.digou.activity.qiagndan.StoreMainqiangdanActivity;
import com.hlcxdg.digou.activity.qiagndan.UserMainqiangdanActivity;
import com.hlcxdg.digou.activity.storeac.StoreListActivity;
import com.hlcxdg.digou.activity.storeac.StoreSingleFenleiActivity;
import com.hlcxdg.digou.adapter.AdvItemAdapter;
import com.hlcxdg.digou.ascytask.DownSaveImgAsync;
import com.hlcxdg.digou.bean.AdvertBean;
import com.hlcxdg.digou.bean.Constant;
import com.hlcxdg.digou.bean.StoreItem;
import com.hlcxdg.digou.constant.ConstantLocation;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;

public class HomeMainFragment extends Fragment implements OnClickListener {
	private View view;
	private View viewFragment;
	private HomeViewAd homeViewAd;
	private Intent myIntent;
	private TextView tv_mycity_homefg;
	int viewPagerSize = 0;
	public LocationClient mLocationClient = null;

	private SharedPreferences sp = null;
	private ArrayList<ImageView> viewContainer = new ArrayList<ImageView>();

	public BDLocationListener myListener = new MyLocationListener();

	public class MyLocationListener implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null)
				return;
			String myStreet = location.getStreet();
			String myProvince = location.getProvince();
			String myCity = location.getCity();
			String myDistrict = location.getDistrict();
			double myLongitude = location.getLongitude();
			double myLatitude = location.getLatitude();
			ConstantLocation.myProvinceString = myProvince;
			ConstantLocation.myCityString = myCity;
			ConstantLocation.myDistrictString = myDistrict;
			ConstantLocation.myStreetString = myStreet;
			ConstantLocation.myLongitudeString = myLongitude;
			ConstantLocation.myLatitudeString = myLatitude;

			if (ConstantLocation.myCityString != null) {
				tv_mycity_homefg.setText(ConstantLocation.myCityString);
				mLocationClient.stop();
			}
		}

	}

	private AdvItemAdapter advAdapter;
	private ListView advListView;
	protected int lastPosition;
//	RelativeLayout photo_layouthldg;
//	RelativeLayout camera_layoutqdps;

	public void initView() {
		// homeViewAd.home_text_vpdesc = (TextView) view
		// .findViewById(R.id.home_text_vpdesc);
		advListView = (ListView) viewFragment.findViewById(R.id.lv_home_fg_adv);
		tv_mycity_homefg = (TextView) viewFragment
				.findViewById(R.id.tv_mycity_homefg);
//		photo_layouthldg = (RelativeLayout) view
//				.findViewById(R.id.photo_layouthldg);
//		camera_layoutqdps = (RelativeLayout) view
//				.findViewById(R.id.camera_layoutqdps);
//		photo_layouthldg.setOnClickListener(this);
//		camera_layoutqdps.setOnClickListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		viewFragment = inflater.inflate(R.layout.home_main_fg, null);
		view = View
				.inflate(getActivity(), R.layout.home_main_fg_listhead, null);
		homeViewAd = new HomeViewAd();
		sp = getActivity().getSharedPreferences("ipcfs",
				getActivity().MODE_PRIVATE);

		initView();
		String jsonViewPager = sp.getString("viewPagerData", "");
		String jsonListView = sp.getString("listData", "");
		if (!TextUtils.isEmpty(jsonViewPager)) {
			Gson gson = new Gson();
			ArrayList<AdvertBean> listAdvert = gson.fromJson(jsonViewPager,
					new TypeToken<List<AdvertBean>>() {
					}.getType());
			gson = null;
			Constant.listAdvertheng = listAdvert;
		}
		if (!TextUtils.isEmpty(jsonListView)) {
			Gson gson = new Gson();
			ArrayList<AdvertBean> listAdvert = gson.fromJson(jsonListView,
					new TypeToken<List<AdvertBean>>() {
					}.getType());
			gson = null;
			Constant.listAdvertshu = listAdvert;
		}
		if (Constant.listAdvertheng != null
				&& Constant.listAdvertheng.size() > 0) {
			viewPager();
		}
		advListView.addHeaderView(view);
		if (Constant.listAdvertshu == null || Constant.listAdvertshu.size() < 1) {
			AdvertBean AdvertBean = new AdvertBean();
			ArrayList<AdvertBean> listAdvert = new ArrayList<AdvertBean>();
			listAdvert.add(AdvertBean);
			Constant.listAdvertshu = listAdvert;
		}
		advAdapter = new AdvItemAdapter(Constant.listAdvertshu, getActivity());
		advListView.setAdapter(advAdapter);
		advListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View arg1,
					int position, long arg3) {
				AdvertBean advertBean = Constant.listAdvertshu
						.get(position - 1);
				ConstantValue.goodsinfo.setcGoodsNo(advertBean.getAdcGoodsNo());
				ConstantValue.goodsinfo.setcStoreNo(advertBean.getcStoreNo());
				ConstantValue.goodsinfo.setcStoreName(advertBean
						.getcStoreName());
				Intent intent;
				intent = new Intent();
				intent.setClass(getActivity(), GoodSingleActivity.class);
				startActivity(intent);
			}
		});

		mLocationClient = new LocationClient(getActivity()
				.getApplicationContext());// ����LocationClient��
		mLocationClient.registerLocationListener(myListener);// ע���������

		LocationClientOption option = new LocationClientOption();// �����������
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);// ���ö�λģʽ
		option.setCoorType("bd09ll");
		option.setScanSpan(5000); 
		option.setIsNeedAddress(true);
		option.setIgnoreKillProcess(false);
		option.setNeedDeviceDirect(true);
		mLocationClient.setLocOption(option);

		mLocationClient.start();

		if (mLocationClient != null && mLocationClient.isStarted())
			mLocationClient.requestLocation();
		return viewFragment;
	}

	public void viewPager() {
		homeViewAd.pointGroup = (LinearLayout) view
				.findViewById(R.id.point_group);
		homeViewAd.vp = (ViewPager) view.findViewById(R.id.viewpager_home_img);

		if (Constant.listAdvertheng != null
				&& Constant.listAdvertheng.size() > 0) {
			viewPagerSize = Constant.listAdvertheng.size();
		}

		for (int i = 0; i < viewPagerSize; i++) {
			final ImageView iv = new ImageView(getActivity());
			iv.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));
			iv.setScaleType(ScaleType.FIT_XY);
			iv.setContentDescription(Constant.listAdvertheng.get(i)
					.getcStoreNo()
					+ ","
					+ Constant.listAdvertheng.get(i).getcStoreName());
			iv.setImageResource(R.drawable.creenshot2);
			new DownSaveImgAsync(getActivity(),
					new DownSaveImgAsync.CallBack() {
						public void sendImage(Bitmap bm, String key) {
							iv.setImageBitmap(bm);
							iv.setOnClickListener(new OnClickListener() {
								public void onClick(View v) {
									// 进入店铺
									// Intent
									String strStore = iv
											.getContentDescription().toString();
									String[] storeInfo = NetUtil.split(
											strStore, ",");
									StoreItem storeItem = new StoreItem();
									storeItem.setcStoreNo(storeInfo[0]);
									storeItem.setcStoreName(storeInfo[1]);
									ConstantValue.storeItem = storeItem;

									Intent intent;
									intent = new Intent();
									intent.setClass(getActivity(),
											StoreSingleFenleiActivity.class);
									getActivity().startActivity(intent);
								}
							});
						}
					}).execute("http://114.112.64.110:7080/O2O"
					+ Constant.listAdvertheng.get(i).getAdImagePath());

			viewContainer.add(iv);
		
			ImageView point = new ImageView(getActivity());
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			params.rightMargin = 10;
			point.setLayoutParams(params);

			point.setBackgroundResource(R.drawable.point_bg);
			if (i == 0) {
				point.setEnabled(true);
			} else {
				point.setEnabled(false);
			}
			homeViewAd.pointGroup.addView(point);
		}
		homeViewAd.vp.setAdapter(new PagerAdapter() {

			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object) {
				container.removeView((View) object);
				object = null;
			}

			@Override
			public Object instantiateItem(ViewGroup container,
					final int position) {
				container.addView(viewContainer.get(position % viewPagerSize));
				
				return viewContainer.get(position % viewPagerSize);
			}

			@Override
			public boolean isViewFromObject(View view, Object object) {
				if (view == object) {
					return true;
				} else {
					return false;
				}
			}

			@Override
			public int getCount() {
				return Integer.MAX_VALUE;
			}
		});
		homeViewAd.vp.setCurrentItem(Integer.MAX_VALUE / 2
				- (Integer.MAX_VALUE / 2 % viewContainer.size()));
		homeViewAd.vp.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			/**
			 * ҳ���л������ 
			 * position  �µ�ҳ��λ��
			 */
			public void onPageSelected(int position) {

				position = position % viewContainer.size();

				homeViewAd.pointGroup.getChildAt(position).setEnabled(true);
				
				homeViewAd.pointGroup.getChildAt(lastPosition)
						.setEnabled(false);
				lastPosition = position;

			}

			@Override
			/**
			 * ҳ�����ڻ�����ʱ�򣬻ص�
			 */
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
			}

			@Override
			/**
			 * ��ҳ��״̬�����仯��ʱ�򣬻ص�
			 */
			public void onPageScrollStateChanged(int state) {

			}
		});
		// homeViewAd.vp.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// int positn = (lastPosition + 1) % viewContainer.size();
		//
		// StoreItem storeItem = new StoreItem();
		// storeItem.setcStoreNo(Constant.listAdvertheng.get(positn)
		// .getcStoreNo());
		// storeItem.setcStoreName(Constant.listAdvertheng.get(positn)
		// .getcStoreName());
		// ConstantValue.storeItem = storeItem;
		//
		// Intent intent;
		// intent = new Intent();
		// intent.setClass(getActivity(), StoreSingleFenleiActivity.class);
		// getActivity().startActivity(intent);
		// }
		// });
		isRunning = true;
		handler.sendEmptyMessageDelayed(0, 5000);
	}

	/**
	 * �ж��Ƿ��Զ�����
	 */
	private boolean isRunning = false;

	public Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			// ��viewPager ��������һҳ

			if (isRunning) {
				homeViewAd.vp
						.setCurrentItem(homeViewAd.vp.getCurrentItem() + 1);
				handler.sendEmptyMessageDelayed(0, 6000);
			}
		};
	};

	@Override
	public void onDestroy() {
		super.onDestroy();
		isRunning = false;
		handler = null;
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
//		case R.id.photo_layouthldg:
//			// myIntent = new Intent(getActivity(),
//			// UserMainqiangdanActivity.class);
//			// getActivity().startActivity(myIntent);
//			break;
//			
//		
//		case R.id.camera_layoutqdps:
//			myIntent = new Intent(getActivity(),
//					StoreMainqiangdanActivity.class);
//			getActivity().startActivity(myIntent);
//			break;
		default:
			break;
		}
	};

	// STATIC CLASS广告单元
	public static class HomeViewAd {

		public ViewPager vp;
		public LinearLayout pointGroup;

	}
}
