package com.hlcxdg.digou.activity.qiagndan;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.os.AsyncTask;
/**
 * 完成通过传来经纬度来进行
 * 地图上显示我的位置
 * 
 */
import android.os.Bundle;
import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMapClickListener;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.map.MapPoi;
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
import com.hlcxdg.digou.bean.RequestBean;
import com.hlcxdg.digou.bean.ResponseBean;
import com.hlcxdg.digou.bean.StoreBaojiaBean;
import com.hlcxdg.digou.bean.UserNeedsBean;
import com.hlcxdg.digou.constant.ConstantLocation;
import com.hlcxdg.digou.net.NetUtilddgg;
import com.hlcxdg.digou.net.RequestVoddgg;
import com.hlcxdg.digou.utils.PromptManager;

public class StoreWoBaoJiaMap extends Activity implements OnClickListener {
	private ImageView qiangdan_user_back_iv;
	private LinearLayout qiangdan_store_add_bottom_ll;
	// 百度地图控件
	private MapView mMapView = null;

	private InfoWindow mInfoWindow = null;
	// 初始化全局 bitmap 信息，不用时及时 recycle
	private BitmapDescriptor bdimg = null;

	List<Marker> markerList;
	private View view;
	private BaiduMap mBaiduMap;
	private TextView qiangdan_map_title_tv;
	List<UserNeedsBean> userNeedsList;

	private class GetAllUserNeedsAscny extends
			AsyncTask<String, Process, List<UserNeedsBean>> {
		Context context;

		public GetAllUserNeedsAscny(Context context) {
			this.context = context;

		}

		@Override
		protected List<UserNeedsBean> doInBackground(String... args) {
			try {
				RequestBean reqbean = new RequestBean();
				reqbean.setReqCode(5002);
				reqbean.setReqMsg("店家查找我想要列表");
				Gson gson = new Gson();
				String jsonString = gson.toJson(reqbean);
				RequestVoddgg vo = new RequestVoddgg(R.string.url_userneeds, context,
						jsonString);
				String bresult = NetUtilddgg.post(vo);
				ResponseBean responseBean = gson.fromJson(bresult,
						ResponseBean.class);
				if (responseBean.getRespCode() == 1) {
					return responseBean.getUserNeedsList();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}

		@Override
		protected void onPostExecute(List<UserNeedsBean> result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result == null) {
				PromptManager.showMyToast(context, "用户暂时没有发布信息");
			} else {
				userNeedsList = result;
				// PromptManager.showMyToast(context, "有发布信息");

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
						view = View.inflate(getApplicationContext(),
								R.layout.ditu_pup_baojiauser, null);
						TextView tv_ditu_pup_goodsname = (TextView) view
								.findViewById(R.id.ditu_pup_baojia_goodsname1);
						view.setBackgroundResource(R.drawable.select_god_btn);
						OnInfoWindowClickListener listener = null;
						for (int i = 0; i < markerList.size(); i++) {
							if (marker == markerList.get(i)) {
								final UserNeedsBean unb = userNeedsList.get(i);
								tv_ditu_pup_goodsname.setText(unb
										.getGoodsName());

								listener = new OnInfoWindowClickListener() {
									public void onInfoWindowClick() {
										setBaojia(unb);
//										mBaiduMap.hideInfoWindow();
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.qiangdan_user_find_dd_map);
		markerList = new ArrayList<Marker>();
		init();
		new GetAllUserNeedsAscny(StoreWoBaoJiaMap.this).execute("");
		mapControler();
	}

	public void mapControler() {
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		MyLocationData locData = new MyLocationData.Builder().direction(180)
				.latitude(ConstantLocation.myLatitudeString)
				.longitude(ConstantLocation.myLongitudeString).build();
		mBaiduMap.setMyLocationData(locData);
		// 我的位置添加标记建筑物
		LatLng ll = new LatLng(ConstantLocation.myLatitudeString,
				ConstantLocation.myLongitudeString);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
		mBaiduMap.animateMapStatus(u);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(12.0f);
		mBaiduMap.setMapStatus(msu);

	}

	public void setBaojia(final UserNeedsBean userqd) {
		AlertDialog.Builder builder = new Builder(StoreWoBaoJiaMap.this);
		// 自定义一个布局文件
		View view = View.inflate(StoreWoBaoJiaMap.this,
				R.layout.qiangdan_baojia_dialog, null);

		final EditText storename = (EditText) view
				.findViewById(R.id.et_stname_qd);
		final EditText storeqdprice = (EditText) view
				.findViewById(R.id.et_jiage_qd);
		storename.setText("乐尚购物广场");
		Button ok = (Button) view.findViewById(R.id.ok);
		Button cancel = (Button) view.findViewById(R.id.cancel);
		final AlertDialog dialog = builder.create();
		dialog.setView(view, 0, 0, 0, 0);
		dialog.show();
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 把这个对话框取消掉
				dialog.dismiss();
			}
		});
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String ip = storename.getText().toString().trim();
				String po = storeqdprice.getText().toString().trim();
				if (TextUtils.isEmpty(ip) || TextUtils.isEmpty(po)) {
					Toast.makeText(StoreWoBaoJiaMap.this, "商店名和报价不能为空", 0)
							.show();
					return;
				}
				StoreBaojiaBean storeBaojia = new StoreBaojiaBean();
				storeBaojia.setStoreNo("12323");
				storeBaojia.setStoreName(storename.getText().toString());
				storeBaojia
						.setJingdu((float) ConstantLocation.myLongitudeString - 0.12f);
				storeBaojia
						.setWeidu((float) ConstantLocation.myLatitudeString - 0.15f);

				storeBaojia.setUserNeedsID(userqd.getUserNeedsID());
				storeBaojia.setStorebaojia(new BigDecimal(po));
				storeBaojia.setSdistance(13.3f);
				storeBaojia.setChengjiao("未成交");
				new TaskSaveBaojia().execute(storeBaojia);
				dialog.dismiss();
			}
		});
	}

	public class TaskSaveBaojia extends
			AsyncTask<StoreBaojiaBean, Process, Boolean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(StoreWoBaoJiaMap.this);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result) {
				PromptManager.showMyToast(getApplicationContext(), "提交成功");
			} else {
				PromptManager.showMyToast(getApplicationContext(), "提交失败");
			}
		}

		@Override
		protected Boolean doInBackground(StoreBaojiaBean... stb) {
			try {
				StoreBaojiaBean sb = stb[0];
				List<StoreBaojiaBean> sbl = new ArrayList<StoreBaojiaBean>();
				sbl.add(sb);
				Gson g = new Gson();
				RequestBean re = new RequestBean();
				re.setStoreBaojiaList(sbl);
				re.setReqCode(5101);
				re.setReqMsg("添加报价");
				String jsonString = g.toJson(re);
				RequestVoddgg vo = new RequestVoddgg(R.string.url_storebaojia,
						StoreWoBaoJiaMap.this, jsonString);
				String bresult = NetUtilddgg.post(vo);
				ResponseBean responseBean = g.fromJson(bresult,
						ResponseBean.class);
				if (responseBean.getRespCode() == 1) {
					return true;
				} else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}

	}

	private void init() {
		qiangdan_map_title_tv = (TextView) findViewById(R.id.qiangdan_map_title_tv);
		qiangdan_map_title_tv.setText("我报价");
		qiangdan_store_add_bottom_ll = (LinearLayout) findViewById(R.id.qiangdan_store_add_bottom_ll);
		qiangdan_store_add_bottom_ll.setVisibility(View.GONE);
		qiangdan_user_back_iv = (ImageView) findViewById(R.id.qiangdan_user_woxiangyao_back);
		qiangdan_user_back_iv.setOnClickListener(this);
		mMapView = (MapView) findViewById(R.id.qiangdan_user_bmapview);
		bdimg = BitmapDescriptorFactory.fromResource(R.drawable.ditu_gouwuche);

	}

	public void initOverlay() {
		// add marker overlay添加各个覆盖物
		if (userNeedsList != null) {
			for (int i = 0; i < userNeedsList.size(); i++) {

				LatLng ll = new LatLng(userNeedsList.get(i).getWeidu(),
						userNeedsList.get(i).getJingdu());
				OverlayOptions oo = new MarkerOptions().position(ll)
						.icon(bdimg).zIndex(9).draggable(false);
				Marker mMarker = (Marker) (mBaiduMap.addOverlay(oo));
				markerList.add(mMarker);
			}
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
		bdimg.recycle();
		super.onDestroy();

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.qiangdan_user_woxiangyao_back:
			finish();
			break;
		default:
			break;
		}
	}

}
