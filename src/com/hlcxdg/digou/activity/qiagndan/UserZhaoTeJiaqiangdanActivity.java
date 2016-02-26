package com.hlcxdg.digou.activity.qiagndan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.RequestBean;
import com.hlcxdg.digou.bean.ResponseBean;
import com.hlcxdg.digou.bean.StoreInfoqdBean;
import com.hlcxdg.digou.bean.UserNeedsBean;
import com.hlcxdg.digou.constant.ConstantLocation;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtilddgg;
import com.hlcxdg.digou.net.RequestVoddgg;
import com.hlcxdg.digou.utils.PromptManager;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserZhaoTeJiaqiangdanActivity extends Activity implements
		OnClickListener {
	private ImageView qiangdan_user_woxiangyao_back;
	// 百度地图控件
	private MapView mMapView = null;
	// 初始化全局 bitmap 信息，不用时及时 recycle
	private BitmapDescriptor bdA;
	private BaiduMap mBaiduMap;
	private Context context;
	private TextView qiangdan_map_title_tv;
	private LinearLayout qiangdan_store_add_bottom_ll;
	private View view;
	private List<StoreInfoqdBean> storeInfoqdList;
	private InfoWindow mInfoWindow;
	List<Marker> markerList;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.qiangdan_user_find_dd_map);
		context = UserZhaoTeJiaqiangdanActivity.this;
		init();
		markerList = new ArrayList<Marker>();
		new GetAllTejiaAscny(UserZhaoTeJiaqiangdanActivity.this).execute("");

		mapControler();
	}

	private class GetAllTejiaAscny extends
			AsyncTask<String, Process, List<StoreInfoqdBean>> {
		Context context;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}

		public GetAllTejiaAscny(Context context) {
			this.context = context;
		}

		@Override
		protected List<StoreInfoqdBean> doInBackground(String... args) {
			try {
				RequestBean reqbean = new RequestBean();
				reqbean.setReqCode(5102);
				reqbean.setReqMsg("查所有特价列表");
				Gson gson = new Gson();
				String jsonString = gson.toJson(reqbean);
				RequestVoddgg vo = new RequestVoddgg(R.string.url_userneeds, context,
						jsonString);
				String bresult = NetUtilddgg.post(vo);
				ResponseBean responseBean = gson.fromJson(bresult,
						ResponseBean.class);
				if (responseBean.getRespCode() == 1) {
					return responseBean.getStoreInfoqdList();
				}
				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		@Override
		protected void onPostExecute(List<StoreInfoqdBean> result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result == null) {
				PromptManager.showMyToast(context, "用户暂时没有发布信息");
			} else {
				storeInfoqdList = result;
				initOverlay();
				mBaiduMap.setOnMapClickListener(new OnMapClickListener() {

					@Override
					public boolean onMapPoiClick(MapPoi arg0) {
						return false;
					}

					@Override
					public void onMapClick(LatLng arg0) {
						if (mInfoWindow != null) {
							mBaiduMap.hideInfoWindow();
						}

					}
				});
				mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
					public boolean onMarkerClick(final Marker marker) {
						view = View.inflate(context,
								R.layout.ditu_pupwindow_infostore, null);
						TextView tv_ditu_pup_goodsname = (TextView) view
								.findViewById(R.id.mapqd_pup_goodsname);
						TextView tv_ditu_pup_goodsprice = (TextView) view
								.findViewById(R.id.mapqd_pup_goodsprice);
						TextView tv_ditu_pup_goodsnum = (TextView) view
								.findViewById(R.id.mapqd_pup_goodsnum);
						TextView tv_ditu_pup_storename = (TextView) view
								.findViewById(R.id.qd_storename_haibao);
						view.setBackgroundResource(R.color.gray_white);
						OnInfoWindowClickListener listener = null;
						for (int i = 0; i < markerList.size(); i++) {
							if (marker == markerList.get(i)) {
								final StoreInfoqdBean unb = storeInfoqdList
										.get(i);
								tv_ditu_pup_goodsname.setText(unb
										.getStoreqdGoodsname());
								tv_ditu_pup_storename.setText(unb
										.getStorenameqd());
								tv_ditu_pup_goodsnum.setText(unb
										.getStoreqdnum()+"");
								tv_ditu_pup_goodsprice.setText(unb
										.getStoreqdprice().setScale(2,
												BigDecimal.ROUND_HALF_UP)
										+ "");
								listener = new OnInfoWindowClickListener() {
									public void onInfoWindowClick() {
										// setBaojia(unb);
										// mBaiduMap.hideInfoWindow();
									}
								};
								LatLng ll = marker.getPosition();
								mInfoWindow = new InfoWindow(
										BitmapDescriptorFactory.fromView(view),
										ll, -47, listener);
								mBaiduMap.showInfoWindow(mInfoWindow);

							}

						}

						return true;
					}
				});
			}
		}
	}

	// public void setBaojia(final StoreInfoqdBean userqd) {
	// AlertDialog.Builder builder = new
	// Builder(UserZhaoTeJiaqiangdanActivity.this);
	// // 自定义一个布局文件
	// View view = View.inflate(UserZhaoTeJiaqiangdanActivity.this,
	// R.layout.qiangdan_baojia_dialog, null);
	//
	// final EditText storename = (EditText) view
	// .findViewById(R.id.et_stname_qd);
	// final EditText storeqdprice = (EditText) view
	// .findViewById(R.id.et_jiage_qd);
	// storename.setText("乐尚购物广场");
	// Button ok = (Button) view.findViewById(R.id.ok);
	// Button cancel = (Button) view.findViewById(R.id.cancel);
	// final AlertDialog dialog = builder.create();
	// dialog.setView(view, 0, 0, 0, 0);
	// dialog.show();
	// cancel.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// // 把这个对话框取消掉
	// dialog.dismiss();
	// }
	// });
	// ok.setOnClickListener(new OnClickListener() {
	// @Override
	// public void onClick(View v) {
	// String ip = storename.getText().toString().trim();
	// String po = storeqdprice.getText().toString().trim();
	// if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(po)) {
	// Toast.makeText(UserZhaoTeJiaqiangdanActivity.this, "商店名和报价不能为空", 0)
	// .show();
	// return;
	// }
	// StoreBaojiaBean storeBaojia = new StoreBaojiaBean();
	// storeBaojia.setStoreNo("12323");
	// storeBaojia.setStoreName(storename.getText().toString());
	// storeBaojia.setJingdu((float)ConstantLocation.myLongitudeString-0.12f);
	// storeBaojia.setWeidu((float)ConstantLocation.myLatitudeString-0.15f);
	//
	// storeBaojia.setUserNeedsID(userqd.getUserNeedsID());
	// storeBaojia.setStorebaojia(new BigDecimal(po));
	// storeBaojia.setSdistance(13.3f);
	// storeBaojia.setChengjiao("未成交");
	// new TaskSaveBaojia().execute(storeBaojia);
	// dialog.dismiss();
	// }
	// });
	// }
	public void mapControler() {
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		setMyLoc();
		// initOverlay();
		makeLestener();

	}

	public void makeLestener() {
		// mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
		// public boolean onMarkerClick(final Marker marker) {
		// view = View.inflate(getApplicationContext(),
		// R.layout.ditu_pupwindow_infostore, null);
		// TextView tv_ditu_pup_goodsname = (TextView) view
		// .findViewById(R.id.ditu_pup_goodsname);
		// TextView tv_ditu_pup_goodsprice = (TextView) view
		// .findViewById(R.id.ditu_pup_goodsprice);
		// TextView tv_ditu_pup_goodsnum = (TextView) view
		// .findViewById(R.id.ditu_pup_goodsnum);
		// TextView tv_ditu_pup_storename = (TextView) view
		// .findViewById(R.id.ditu_pup_storename);
		// // TextView ditu_pup_goodsname ditu_pup_goodsprice
		// // ditu_pup_goodsprice ditu_pup_storename
		// view.setBackgroundResource(R.drawable.select_god_btn);
		// OnInfoWindowClickListener listener = null;
		// if (marker == mMarkerA) {
		// tv_ditu_pup_goodsname.setText(ConstantValue.storeqdList
		// .get(0).getStoreqdGoodsname());
		// tv_ditu_pup_goodsprice.setText(ConstantValue.storeqdList
		// .get(0).getStoreqdprice().floatValue()+"元");
		// tv_ditu_pup_goodsnum.setText(ConstantValue.storeqdList.get(
		// 0).getStoreqdnum());
		// tv_ditu_pup_storename.setText(ConstantValue.storeqdList
		// .get(0).getStorenameqd());
		//
		// listener = new OnInfoWindowClickListener() {
		// public void onInfoWindowClick() {
		//
		// // PromptManager.showMyToast(getApplicationContext(),
		// // "抢单成功");
		// StoreInfoqdBean stqd = ConstantValue.storeqdList
		// .get(0);
		// // userqd.setZhuangtai("已发货");
		// qiangdan(stqd);
		// mBaiduMap.hideInfoWindow();
		// }
		//
		// };
		// LatLng ll = marker.getPosition();
		// mInfoWindow = new InfoWindow(BitmapDescriptorFactory
		// .fromView(view), ll, -47, listener);
		// mBaiduMap.showInfoWindow(mInfoWindow);
		//
		// } else if (marker == mMarkerB) {
		// tv_ditu_pup_goodsname.setText(ConstantValue.storeqdList
		// .get(1).getStoreqdGoodsname());
		// tv_ditu_pup_goodsprice.setText(ConstantValue.storeqdList
		// .get(1).getStoreqdprice()+"元");
		// tv_ditu_pup_goodsnum.setText(ConstantValue.storeqdList.get(
		// 1).getStoreqdnum());
		// tv_ditu_pup_storename.setText(ConstantValue.storeqdList
		// .get(1).getStorenameqd());
		//
		// listener = new OnInfoWindowClickListener() {
		// public void onInfoWindowClick() {
		//
		// // PromptManager.showMyToast(getApplicationContext(),
		// // "抢单成功");
		// StoreInfoqdBean stqd = ConstantValue.storeqdList
		// .get(1);
		// // userqd.setZhuangtai("已发货");
		// qiangdan(stqd);
		// mBaiduMap.hideInfoWindow();
		// }
		//
		// };
		// LatLng ll = marker.getPosition();
		// mInfoWindow = new InfoWindow(BitmapDescriptorFactory
		// .fromView(view), ll, -47, listener);
		// mBaiduMap.showInfoWindow(mInfoWindow);
		// } else if (marker == mMarkerC) {
		// tv_ditu_pup_goodsname.setText(ConstantValue.storeqdList
		// .get(2).getStoreqdGoodsname());
		// tv_ditu_pup_goodsprice.setText(ConstantValue.storeqdList
		// .get(2).getStoreqdprice()+"元");
		// tv_ditu_pup_goodsnum.setText(ConstantValue.storeqdList.get(
		// 2).getStoreqdnum());
		// tv_ditu_pup_storename.setText(ConstantValue.storeqdList
		// .get(2).getStorenameqd());
		//
		// listener = new OnInfoWindowClickListener() {
		// public void onInfoWindowClick() {
		//
		// // PromptManager.showMyToast(getApplicationContext(),
		// // "抢单成功");
		// StoreInfoqdBean stqd = ConstantValue.storeqdList
		// .get(2);
		// // userqd.setZhuangtai("已发货");
		// qiangdan(stqd);
		// mBaiduMap.hideInfoWindow();
		// }
		//
		// };
		// LatLng ll = marker.getPosition();
		// mInfoWindow = new InfoWindow(BitmapDescriptorFactory
		// .fromView(view), ll, -47, listener);
		// mBaiduMap.showInfoWindow(mInfoWindow);
		// } else if (marker == mMarkerD) {
		// tv_ditu_pup_goodsname.setText(ConstantValue.storeqdList
		// .get(3).getStoreqdGoodsname());
		// tv_ditu_pup_goodsprice.setText(ConstantValue.storeqdList
		// .get(3).getStoreqdprice()+"元");
		// tv_ditu_pup_goodsnum.setText(ConstantValue.storeqdList.get(
		// 3).getStoreqdnum());
		// tv_ditu_pup_storename.setText(ConstantValue.storeqdList
		// .get(3).getStorenameqd());
		//
		// listener = new OnInfoWindowClickListener() {
		// public void onInfoWindowClick() {
		//
		// // PromptManager.showMyToast(getApplicationContext(),
		// // "抢单成功");
		// StoreInfoqdBean stqd = ConstantValue.storeqdList
		// .get(3);
		// // userqd.setZhuangtai("已发货");
		// qiangdan(stqd);
		// mBaiduMap.hideInfoWindow();
		// }
		//
		// };
		// LatLng ll = marker.getPosition();
		// mInfoWindow = new InfoWindow(BitmapDescriptorFactory
		// .fromView(view), ll, -47, listener);
		// mBaiduMap.showInfoWindow(mInfoWindow);
		// }
		//
		// return true;
		// }
		// });
	}

	// 下单
	public void qiangdan(StoreInfoqdBean stqd) {
		PromptManager.showMyToast(getApplication(), "下单成功");
		stqd.setZhuangtai("成交");
		ConstantValue.storeqdListBaojia.add(stqd);

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
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(13.0f);
		mBaiduMap.setMapStatus(msu);
	}

	private void init() {
		qiangdan_map_title_tv = (TextView) findViewById(R.id.qiangdan_map_title_tv);
		qiangdan_map_title_tv.setText("找特价");
		qiangdan_store_add_bottom_ll = (LinearLayout) findViewById(R.id.qiangdan_store_add_bottom_ll);
		qiangdan_store_add_bottom_ll.setVisibility(View.GONE);

		qiangdan_user_woxiangyao_back = (ImageView) findViewById(R.id.qiangdan_user_woxiangyao_back);
		qiangdan_user_woxiangyao_back.setOnClickListener(this);
		mMapView = (MapView) findViewById(R.id.qiangdan_user_bmapview);
		bdA = BitmapDescriptorFactory
				.fromResource(R.drawable.qiangdan_ditu_house);

	}



	public void initOverlay() {
		for (int i = 0; i < storeInfoqdList.size(); i++) {

			LatLng ll = new LatLng(storeInfoqdList.get(i).getWeidu(),
					storeInfoqdList.get(i).getJingdu());
			OverlayOptions oo = new MarkerOptions().position(ll).icon(bdA)
					.zIndex(9).draggable(false);
			Marker mMarker = (Marker) (mBaiduMap.addOverlay(oo));
			markerList.add(mMarker);
		}
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
		case R.id.qiangdan_user_woxiangyao_back:
			finish();
			break;
		// qiangdan_user_add_goods_iv
		case R.id.qiangdan_user_add_goods_iv:
			break;
		}
	}

}
