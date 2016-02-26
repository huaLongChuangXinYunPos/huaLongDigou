package com.hlcxdg.digou.activity.qiagndan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
/**
 * 完成通过传来经纬度来进行
 * 地图上显示我的位置
 * 
 */
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.user.LoginActivity;
import com.hlcxdg.digou.bean.RequestBean;
import com.hlcxdg.digou.bean.ResponseBean;
import com.hlcxdg.digou.bean.StoreInfoqdBean;
import com.hlcxdg.digou.bean.UserNeedsBean;
import com.hlcxdg.digou.constant.ConstantLocation;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtilddgg;
import com.hlcxdg.digou.net.RequestVoddgg;
import com.hlcxdg.digou.utils.DecimalUtils;
import com.hlcxdg.digou.utils.PromptManager;

public class UserWoXiangYaoqdActivity extends Activity implements
		OnClickListener {
	private ImageView qiangdan_user_woxiangyao_back;
	private ImageView qiangdan_user_add_goods_iv;
	private EditText qiangdan_user_add_goods_et;
	private EditText qiangdan_user_add_num_et2;
	private Button qiangdan_user_main7_iv;
	private StoreInfoqdBean qdStoreInfo;
	private UserNeedsBean userNeedsBean;
	// 百度地图控件
	private MapView mMapView = null;
	// 初始化全局 bitmap 信息，不用时及时 recycle
	private BitmapDescriptor bdA;
	private BaiduMap mBaiduMap;
	private Context context;
	private Marker mMarkerA;
	private Marker mMarkerB;
	private Marker mMarkerC;
	private Marker mMarkerD;
	private InfoWindow mInfoWindow;
	private View view;
	Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			PromptManager.closeSpotsDialog();
			switch (msg.what) {

			case 5:
				break;
			case 4:
				break;
			case 3:
				break;
			case 2:
				break;

			case 1:
				PromptManager
						.showMyToast(UserWoXiangYaoqdActivity.this, "发布成功");
				break;
			case 0:
				PromptManager
						.showMyToast(UserWoXiangYaoqdActivity.this, "发布失败");
				break;

			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.qiangdan_user_find_dd_map);
		context = UserWoXiangYaoqdActivity.this;
		init();

		mapControler();
	}

	public void setMyLoc() {
		mBaiduMap.setMyLocationEnabled(true);

		MyLocationData locData = new MyLocationData.Builder().direction(180)
				.latitude(ConstantLocation.myLatitudeString)
				.longitude(ConstantLocation.myLongitudeString).build();
		mBaiduMap.setMyLocationData(locData);

		LatLng ll = new LatLng(ConstantLocation.myLatitudeString,
				ConstantLocation.myLongitudeString);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
		mBaiduMap.animateMapStatus(u);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu);
	}

	public void mapControler() {
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		setMyLoc();
		initOverlay();
		// makeLestener();

	}

	// 下单
	public void qiangdan(StoreInfoqdBean stqd) {
		PromptManager.showMyToast(UserWoXiangYaoqdActivity.this, "下单成功");
		stqd.setZhuangtai("成交");
		ConstantValue.storeqdListBaojia.add(stqd);

	}

	public void makeLestener() {
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(final Marker marker) {
				view = View.inflate(getApplicationContext(),
						R.layout.ditu_pupwindow_infostore, null);
				TextView tv_ditu_pup_goodsname = (TextView) view
						.findViewById(R.id.mapqd_pup_goodsname);
				TextView tv_ditu_pup_goodsprice = (TextView) view
						.findViewById(R.id.mapqd_pup_goodsprice);
				TextView tv_ditu_pup_goodsnum = (TextView) view
						.findViewById(R.id.mapqd_pup_goodsnum);
				TextView tv_ditu_pup_storename = (TextView) view
						.findViewById(R.id.qd_storename_haibao);
				// TextView ditu_pup_goodsname ditu_pup_goodsprice
				// ditu_pup_goodsprice ditu_pup_storename
				view.setBackgroundResource(R.drawable.select_god_btn);
				OnInfoWindowClickListener listener = null;
				if (marker == mMarkerA) {
					tv_ditu_pup_goodsname.setText(ConstantValue.storeqdList
							.get(0).getStoreqdGoodsname());
					tv_ditu_pup_goodsprice.setText(ConstantValue.storeqdList
							.get(0).getStoreqdprice().floatValue()
							+ "");
					tv_ditu_pup_goodsnum.setText(ConstantValue.storeqdList.get(
							0).getStoreqdnum());
					tv_ditu_pup_storename.setText(ConstantValue.storeqdList
							.get(0).getStorenameqd());

					listener = new OnInfoWindowClickListener() {
						public void onInfoWindowClick() {

							// PromptManager.showMyToast(getApplicationContext(),
							// "抢单成功");
							StoreInfoqdBean stqd = ConstantValue.storeqdList
									.get(0);
							// userqd.setZhuangtai("已发货");
							qiangdan(stqd);
							mBaiduMap.hideInfoWindow();
						}

					};
					LatLng ll = marker.getPosition();
					mInfoWindow = new InfoWindow(BitmapDescriptorFactory
							.fromView(view), ll, -47, listener);
					mBaiduMap.showInfoWindow(mInfoWindow);

				} else if (marker == mMarkerB) {
					tv_ditu_pup_goodsname.setText(ConstantValue.storeqdList
							.get(1).getStoreqdGoodsname());
					tv_ditu_pup_goodsprice.setText(ConstantValue.storeqdList
							.get(1).getStoreqdprice().floatValue()
							+ "");
					tv_ditu_pup_goodsnum.setText(ConstantValue.storeqdList.get(
							1).getStoreqdnum());
					tv_ditu_pup_storename.setText(ConstantValue.storeqdList
							.get(1).getStorenameqd());

					listener = new OnInfoWindowClickListener() {
						public void onInfoWindowClick() {

							// PromptManager.showMyToast(getApplicationContext(),
							// "抢单成功");
							StoreInfoqdBean stqd = ConstantValue.storeqdList
									.get(1);
							// userqd.setZhuangtai("已发货");
							qiangdan(stqd);
							mBaiduMap.hideInfoWindow();
						}

					};
					LatLng ll = marker.getPosition();
					mInfoWindow = new InfoWindow(BitmapDescriptorFactory
							.fromView(view), ll, -47, listener);
					mBaiduMap.showInfoWindow(mInfoWindow);
				} else if (marker == mMarkerC) {
					tv_ditu_pup_goodsname.setText(ConstantValue.storeqdList
							.get(2).getStoreqdGoodsname());
					tv_ditu_pup_goodsprice.setText(ConstantValue.storeqdList
							.get(2).getStoreqdprice().floatValue()
							+ "");
					tv_ditu_pup_goodsnum.setText(ConstantValue.storeqdList.get(
							2).getStoreqdnum());
					tv_ditu_pup_storename.setText(ConstantValue.storeqdList
							.get(2).getStorenameqd());

					listener = new OnInfoWindowClickListener() {
						public void onInfoWindowClick() {

							// PromptManager.showMyToast(getApplicationContext(),
							// "抢单成功");
							StoreInfoqdBean stqd = ConstantValue.storeqdList
									.get(2);
							// userqd.setZhuangtai("已发货");
							qiangdan(stqd);
							mBaiduMap.hideInfoWindow();
						}

					};
					LatLng ll = marker.getPosition();
					mInfoWindow = new InfoWindow(BitmapDescriptorFactory
							.fromView(view), ll, -47, listener);
					mBaiduMap.showInfoWindow(mInfoWindow);
				} else if (marker == mMarkerD) {
					tv_ditu_pup_goodsname.setText(ConstantValue.storeqdList
							.get(3).getStoreqdGoodsname());
					tv_ditu_pup_goodsprice.setText(ConstantValue.storeqdList
							.get(3).getStoreqdprice().floatValue()
							+ "");
					tv_ditu_pup_goodsnum.setText(ConstantValue.storeqdList.get(
							3).getStoreqdnum());
					tv_ditu_pup_storename.setText(ConstantValue.storeqdList
							.get(3).getStorenameqd());

					listener = new OnInfoWindowClickListener() {
						public void onInfoWindowClick() {

							// PromptManager.showMyToast(getApplicationContext(),
							// "抢单成功");
							StoreInfoqdBean stqd = ConstantValue.storeqdList
									.get(3);
							// userqd.setZhuangtai("已发货");
							qiangdan(stqd);
							mBaiduMap.hideInfoWindow();
						}

					};
					LatLng ll = marker.getPosition();
					mInfoWindow = new InfoWindow(BitmapDescriptorFactory
							.fromView(view), ll, -47, listener);
					mBaiduMap.showInfoWindow(mInfoWindow);
				}

				return true;
			}
		});
	}

	private void init() {
		if (ConstantValue.storeqdList.size() != 4) {
			setJiaShuju();
		}
		qiangdan_user_main7_iv = (Button) findViewById(R.id.qiangdan_user_main7_iv);
		qiangdan_user_add_goods_et = (EditText) findViewById(R.id.qiangdan_user_add_goods_et);
		qiangdan_user_add_num_et2 = (EditText) findViewById(R.id.qiangdan_user_add_num_et2);
		qiangdan_user_add_goods_iv = (ImageView) findViewById(R.id.qiangdan_user_add_goods_iv);
		qiangdan_user_add_goods_iv.setOnClickListener(this);
		qiangdan_user_main7_iv.setOnClickListener(this);
		qiangdan_user_woxiangyao_back = (ImageView) findViewById(R.id.qiangdan_user_woxiangyao_back);
		qiangdan_user_woxiangyao_back.setOnClickListener(this);
		mMapView = (MapView) findViewById(R.id.qiangdan_user_bmapview);
		bdA = BitmapDescriptorFactory
				.fromResource(R.drawable.qiangdan_ditu_house);

	}

	public void setJiaShuju() {

		qdStoreInfo = new StoreInfoqdBean();
		qdStoreInfo.setJingdu(ConstantLocation.myLongitudeString - 0.0111);
		qdStoreInfo.setWeidu(ConstantLocation.myLatitudeString + 0.0112);
		qdStoreInfo.setStorenameqd("百货1号");
		qdStoreInfo.setStoreaddrqd(ConstantLocation.myDistrictString);
		qdStoreInfo.setStoreqdGoodsname("洗衣液");
		qdStoreInfo.setStoreqdprice(new BigDecimal(137));
		qdStoreInfo.setStoreqdnum(2);
		ConstantValue.storeqdList.add(qdStoreInfo);
		qdStoreInfo = new StoreInfoqdBean();
		qdStoreInfo.setJingdu(ConstantLocation.myLongitudeString - 0.0115);
		qdStoreInfo.setWeidu(ConstantLocation.myLatitudeString - 0.0115);
		qdStoreInfo.setStorenameqd("超市");
		qdStoreInfo.setStoreaddrqd(ConstantLocation.myDistrictString);
		qdStoreInfo.setStoreqdGoodsname("洗发水");
		qdStoreInfo.setStoreqdprice(new BigDecimal(420));
		qdStoreInfo.setStoreqdnum(2);
		ConstantValue.storeqdList.add(qdStoreInfo);
		qdStoreInfo = new StoreInfoqdBean();
		qdStoreInfo.setJingdu(ConstantLocation.myLongitudeString + 0.0113);
		qdStoreInfo.setWeidu(ConstantLocation.myLatitudeString - 0.0113);
		qdStoreInfo.setStorenameqd("烟酒店");
		qdStoreInfo.setStoreaddrqd(ConstantLocation.myDistrictString);
		qdStoreInfo.setStoreqdGoodsname("中华香烟");
		qdStoreInfo.setStoreqdprice(new BigDecimal(345));
		qdStoreInfo.setStoreqdnum(2);
		ConstantValue.storeqdList.add(qdStoreInfo);
		qdStoreInfo = new StoreInfoqdBean();
		qdStoreInfo.setJingdu(ConstantLocation.myLongitudeString + 0.0113);
		qdStoreInfo.setWeidu(ConstantLocation.myLatitudeString + 0.0113);
		qdStoreInfo.setStorenameqd("烟酒店");
		qdStoreInfo.setStoreaddrqd(ConstantLocation.myDistrictString);
		qdStoreInfo.setStoreqdGoodsname("中华香烟");
		qdStoreInfo.setStoreqdprice(new BigDecimal(768));
		qdStoreInfo.setStoreqdnum(2);
		ConstantValue.storeqdList.add(qdStoreInfo);
		qdStoreInfo = new StoreInfoqdBean();
		qdStoreInfo.setJingdu(ConstantLocation.myLongitudeString + 0.013);
		qdStoreInfo.setWeidu(ConstantLocation.myLatitudeString - 0.013);
		qdStoreInfo.setStorenameqd("烟酒店");
		qdStoreInfo.setStoreaddrqd(ConstantLocation.myDistrictString);
		qdStoreInfo.setStoreqdGoodsname("中华香烟");
		qdStoreInfo.setStoreqdprice(new BigDecimal(555));
		qdStoreInfo.setStoreqdnum(2);
		ConstantValue.storeqdList.add(qdStoreInfo);
		qdStoreInfo = new StoreInfoqdBean();
		qdStoreInfo.setJingdu(ConstantLocation.myLongitudeString + 0.013);
		qdStoreInfo.setWeidu(ConstantLocation.myLatitudeString + 0.013);
		qdStoreInfo.setStorenameqd("烟酒店");
		qdStoreInfo.setStoreaddrqd(ConstantLocation.myDistrictString);
		qdStoreInfo.setStoreqdGoodsname("中华香烟");
		qdStoreInfo.setStoreqdprice(new BigDecimal(150));
		qdStoreInfo.setStoreqdnum(2);
		ConstantValue.storeqdList.add(qdStoreInfo);
		qdStoreInfo = new StoreInfoqdBean();
		qdStoreInfo.setJingdu(ConstantLocation.myLongitudeString - 0.013);
		qdStoreInfo.setWeidu(ConstantLocation.myLatitudeString - 0.013);
		qdStoreInfo.setStorenameqd("烟酒店");
		qdStoreInfo.setStoreaddrqd(ConstantLocation.myDistrictString);
		qdStoreInfo.setStoreqdGoodsname("中华香烟");
		qdStoreInfo.setStoreqdprice(new BigDecimal(150));
		qdStoreInfo.setStoreqdnum(1);
		ConstantValue.storeqdList.add(qdStoreInfo);
		qdStoreInfo = new StoreInfoqdBean();
		qdStoreInfo.setJingdu(ConstantLocation.myLongitudeString - 0.013);
		qdStoreInfo.setWeidu(ConstantLocation.myLatitudeString + 0.013);
		qdStoreInfo.setStorenameqd("烟酒店");
		qdStoreInfo.setStoreaddrqd(ConstantLocation.myDistrictString);
		qdStoreInfo.setStoreqdGoodsname("中华香烟");
		qdStoreInfo.setStoreqdprice(new BigDecimal(2095));
		qdStoreInfo.setStoreqdnum(1);
		ConstantValue.storeqdList.add(qdStoreInfo);
		// qdStoreInfo.setStoreqdprice(new BigDecimal(storeddprice));
		// qdStoreInfo.setStoreqdnum(Integer.parseInt(storeddnum));
	}

	public void initOverlay() {
		// add marker overlay添加各个覆盖物

		// add marker overlay添加各个覆盖物
		LatLng llA = new LatLng(ConstantValue.storeqdList.get(0).getWeidu(),
				ConstantValue.storeqdList.get(0).getJingdu());
		LatLng llB = new LatLng(ConstantValue.storeqdList.get(1).getWeidu(),
				ConstantValue.storeqdList.get(1).getJingdu());
		LatLng llC = new LatLng(ConstantValue.storeqdList.get(2).getWeidu(),
				ConstantValue.storeqdList.get(2).getJingdu());
		LatLng llD = new LatLng(ConstantValue.storeqdList.get(3).getWeidu(),
				ConstantValue.storeqdList.get(3).getJingdu());

		OverlayOptions ooA = new MarkerOptions().position(llA).icon(bdA)
				.zIndex(9).draggable(false);
		mMarkerA = (Marker) (mBaiduMap.addOverlay(ooA));
		OverlayOptions ooB = new MarkerOptions().position(llB).icon(bdA)
				.zIndex(5);
		mMarkerB = (Marker) (mBaiduMap.addOverlay(ooB));
		OverlayOptions ooC = new MarkerOptions().position(llC).icon(bdA)
				.zIndex(7);
		mMarkerC = (Marker) (mBaiduMap.addOverlay(ooC));
		OverlayOptions ooD = new MarkerOptions().position(llD).icon(bdA)
				.zIndex(3);
		mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));

	}

	/**
	 * 清除所有Overlay
	 * 
	 * @param view
	 */
	public void clearOverlay(View view) {
		mBaiduMap.clear();
	}

	/**
	 * 重新添加Overlay
	 * 
	 * @param view
	 */
	public void resetOverlay(View view) {
		clearOverlay(null);
		initOverlay();
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
		if (ConstantValue.goodsinfo != null) {
			qiangdan_user_add_goods_et.setText(ConstantValue.goodsinfo
					.getcGoodsName());
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
	}

	@Override
	protected void onDestroy() {
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		// MapView的生命周期与Activity同步，当activity销毁时需调用MapView.destroy()
		mMapView.onDestroy();
		mMapView = null;

		// 回收 bitmap 资源
		bdA.recycle();

		super.onDestroy();

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		// 用户界面会退操作
		case R.id.qiangdan_user_woxiangyao_back:
			finish();
			break;
		// 用户提交需求
		case R.id.qiangdan_user_add_goods_iv:
			userAddXuqiu();
			break;
		case R.id.qiangdan_user_main7_iv:
			zixuanGoods();
			break;

		}
	}

	private void zixuanGoods() {
		Intent intent = new Intent();
		intent.setClass(context, FenleiActivity.class);
		startActivity(intent);
	}

	public void userAddXuqiu() {
		String goodsName = qiangdan_user_add_goods_et.getText().toString()
				.trim();
		String goodsNum = qiangdan_user_add_num_et2.getText().toString().trim();
		if (TextUtils.isEmpty(goodsName) || TextUtils.isEmpty(goodsNum)) {
			PromptManager.showMyToast(context, "输入不可为空");
			return;
		} else if (ConstantValue.userinfo == null) {
			Toast.makeText(UserWoXiangYaoqdActivity.this, "请先登录", 0).show();
			Intent intent;
			intent = new Intent();
			intent.setClass(UserWoXiangYaoqdActivity.this, LoginActivity.class);
			UserWoXiangYaoqdActivity.this.startActivity(intent);
		} else {
			PromptManager.showSpotsDialog(context);
			// PromptManager.closeSpotsDialog();
			userNeedsBean = new UserNeedsBean();
			try {
				userNeedsBean.setUserNo(ConstantValue.userinfo.getUserNo());
				userNeedsBean.setGoodsName(goodsName);
				if (ConstantValue.goodsinfo != null) {
					userNeedsBean.setGoodsNo(ConstantValue.goodsinfo
							.getcGoodsNo());
				}

				userNeedsBean.setQdnum(new BigDecimal(goodsNum));
			} catch (Exception e1) {
				e1.printStackTrace();
				mHandler.sendEmptyMessage(0);
			}
			userNeedsBean
					.setJingdu((float) (ConstantLocation.myLongitudeString + DecimalUtils
							.getRandomDouble()));
			userNeedsBean
					.setWeidu((float) (ConstantLocation.myLatitudeString - DecimalUtils
							.getRandomDouble()));
			new Thread(new Runnable() {
				public void run() {
					try {
						List<UserNeedsBean> unList = new ArrayList<UserNeedsBean>();
						unList.add(userNeedsBean);
						/*
						 * 待查
						 */
						RequestBean reqbean = new RequestBean();
						reqbean.setReqCode(5001);
						reqbean.setReqMsg("添加我想要");
						reqbean.setUserneedsList(unList);
						Gson gson = new Gson();
						String jsonString = gson.toJson(reqbean);
						RequestVoddgg vo = new RequestVoddgg(
								R.string.url_userneeds,
								UserWoXiangYaoqdActivity.this, jsonString);

						/*
						 * 待查
						 */
						String bresult = NetUtilddgg.post(vo);
						ResponseBean responseBean = gson.fromJson(bresult,
								ResponseBean.class);
						if (responseBean != null
								&& responseBean.getRespCode() == 1) {
							mHandler.sendEmptyMessage(1);
						} else {
							mHandler.sendEmptyMessage(0);
						}
					} catch (Exception e) {
						e.printStackTrace();
						mHandler.sendEmptyMessage(0);
					}
				}
			}).start();
		}
	}

}
