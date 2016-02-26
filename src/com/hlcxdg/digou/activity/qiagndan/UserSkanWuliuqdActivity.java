package com.hlcxdg.digou.activity.qiagndan;


import java.util.List;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.InfoWindow.OnInfoWindowClickListener;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.Mapk;
import com.hlcxdg.digou.bean.RequestBean;
import com.hlcxdg.digou.bean.ResponseBean;
import com.hlcxdg.digou.bean.StoreBaojiaBean;
import com.hlcxdg.digou.bean.StoreInfoqdBean;
import com.hlcxdg.digou.constant.ConstantLocation;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.db.map.dao.MapDAO;
import com.hlcxdg.digou.db.map.dao.MyDatabaseHelper;
import com.hlcxdg.digou.db.map.dao.TestActivity;
import com.hlcxdg.digou.net.NetUtilddgg;
import com.hlcxdg.digou.net.RequestVoddgg;
import com.hlcxdg.digou.utils.PromptManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class UserSkanWuliuqdActivity extends Activity implements
		OnClickListener {
	private ImageView qiangdan_user_find_wuliu_back;
	private MapView mMapView = null;// qiangdan_user_find_wuliu_bmapview;
	private Context context;
	private BaiduMap mBaiduMap;
	private Marker mMarkerD;
	private Marker mMarkerCar;
	private BitmapDescriptor bdD;
	private BitmapDescriptor bdWuliu;
	private int i = 0;
	private InfoWindow mInfoWindow;
	StoreBaojiaBean storeBaojiaBean;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {

			case 1:
//				locwei = storeBaojiaBean.getWeidu()+0.12 - i * dxwei;
//				locjing = storeBaojiaBean.getJingdu()+0.12 - i * dxjing;
				moveCar();

			default:
				break;
			}
		};
	};
	SQLiteDatabase db;
	List<Mapk> mapkList;
	Mapk mapk;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qiangdan_user_find_wuliu_map);
		context = UserSkanWuliuqdActivity.this;

		
		db = new MyDatabaseHelper(context).getWritableDatabase();

		mapkList= new MapDAO(db).getMap(1);
		init();
		mapControler();
		LatLng llWuliu = new LatLng(116.322739,39.993529);
		ooWuliu = new MarkerOptions().position(llWuliu).title("滴购配送").icon(bdWuliu).zIndex(3);
		mMarkerCar = (Marker) (mBaiduMap.addOverlay(ooWuliu));
		final Thread mThread=new Thread(new ThreadShow());
		mThread.start();
		String baojiaID = getIntent().getStringExtra("baojiaID");
		new GetWuliuAsy(context).execute(baojiaID);

	}

	public class GetWuliuAsy extends
			AsyncTask<String, Process, StoreBaojiaBean> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}
		@Override
		protected void onPostExecute(StoreBaojiaBean result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (result != null) {
				storeBaojiaBean = result;
				//物流信息描绘
//				mapControler();
				initOverlay();
			}
		}

		private Context context;

		public GetWuliuAsy(Context context) {
			this.context = context;

		}

		@Override
		protected StoreBaojiaBean doInBackground(String... str) {
			try {
				String baojiaID = str[0];
				RequestBean reqbean = new RequestBean();
				reqbean.setReqCode(5107);
				reqbean.setBaojiaID(baojiaID);

				reqbean.setReqMsg("客户查找一条配送信息");
				Gson gson = new Gson();
				String jsonString = gson.toJson(reqbean);
				RequestVoddgg vo = new RequestVoddgg(R.string.url_storebaojia, context,
						jsonString);
				String bresult = NetUtilddgg.post(vo);
				ResponseBean responseBean = gson.fromJson(bresult,
						ResponseBean.class);
				if (responseBean.getRespCode() == 1) {
					return responseBean.getStoreBaojia();
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			return null;
		}

	}

	// 线程类
	class ThreadShow implements Runnable {

		@Override
		public void run() {
			while (i <mapkList.size()) {
				try {
					Thread.sleep(5000);
					mapk=mapkList.get(i);
					Message msg = new Message();
					msg.what = 1;
					mHandler.sendMessage(msg);
					System.out.println("send...");
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("thread error...");
				}
				i++;
			}
		}
	}

	OverlayOptions ooWuliu;
	double dxwei;
	double dxjing;
	private View view;
	public void initOverlay() {
		// add marker overlay添加各个覆盖物
		LatLng llD = new LatLng(storeBaojiaBean.getWeidu()+0.12,
				storeBaojiaBean.getJingdu()+0.12);
		OverlayOptions ooD = new MarkerOptions().position(llD).icon(bdD)
				.zIndex(3);
		mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));
		dxwei = (storeBaojiaBean.getWeidu()+0.12 - ConstantLocation.myLatitudeString) / 50;
		dxjing = (storeBaojiaBean.getJingdu()+0.12 - ConstantLocation.myLongitudeString) / 50;
//		LatLng llWuliu = new LatLng(116.322739,39.993529);
//		ooWuliu = new MarkerOptions().position(llWuliu).icon(bdWuliu).zIndex(3);
//		mMarkerCar = (Marker) (mBaiduMap.addOverlay(ooWuliu));
//		final Thread mThread=new Thread(new ThreadShow());
//		mThread.start();
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {
			public boolean onMarkerClick(final Marker marker) {
				
				OnInfoWindowClickListener listener = null;
				if (marker == mMarkerCar) {
					
					view = View.inflate(getApplicationContext(),
							R.layout.ditu_pupwindow_infowuliu, null);
					TextView tv_ditu_pup_goodsname = (TextView) view
							.findViewById(R.id.ditu_pup_wuliu_goodsname);
					tv_ditu_pup_goodsname.setText(storeBaojiaBean.getGoodsName());
					TextView ditu_pup_peisongname = (TextView) view
							.findViewById(R.id.ditu_pup_peisongname);
					ditu_pup_peisongname.setText(storeBaojiaBean.getPeisongName());
					TextView ditu_pup_peisongtel = (TextView) view
							.findViewById(R.id.ditu_pup_peisongtel);
					ditu_pup_peisongtel.setText(storeBaojiaBean.getPeisongTel());
					TextView ditu_pup_bjprice = (TextView) view
							.findViewById(R.id.ditu_pup_bjprice);
					ditu_pup_bjprice.setText(storeBaojiaBean.getBaojiaID());
					TextView ditu_pup_kdstname = (TextView) view
							.findViewById(R.id.ditu_pup_kdstname);
					ditu_pup_kdstname.setText(storeBaojiaBean.getStoreName());
					
					view.setBackgroundResource(R.drawable.select_god_btn);
					listener = new OnInfoWindowClickListener() {
						public void onInfoWindowClick() {
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

	double locwei;
	double locjing;

	public void moveCar() {
		locwei = mapk.getLaw();
		locjing =mapk.getLonj();
		LatLng llWuliu1 = new LatLng(locwei, locjing);
		mMarkerCar.setPosition(llWuliu1);
	}

	public void mapControler() {
		mBaiduMap = mMapView.getMap();
		// 开启定位图层
		mBaiduMap.setMyLocationEnabled(true);
		// 我的定位图层
		MyLocationData locData = new MyLocationData.Builder().direction(180)
				.latitude(ConstantLocation.myLatitudeString)
				.longitude(ConstantLocation.myLongitudeString).build();
		mBaiduMap.setMyLocationData(locData);

		LatLng ll = new LatLng(ConstantLocation.myLatitudeString,
				ConstantLocation.myLongitudeString);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
		mBaiduMap.animateMapStatus(u);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(12.0f);
		mBaiduMap.setMapStatus(msu);
		

	}

	private void init() {
		bdWuliu = BitmapDescriptorFactory.fromResource(R.drawable.shopping_car);

		bdD = BitmapDescriptorFactory
				.fromResource(R.drawable.qiangdan_ditu_house);
		qiangdan_user_find_wuliu_back = (ImageView) findViewById(R.id.qiangdan_user_find_wuliu_back);
		qiangdan_user_find_wuliu_back.setOnClickListener(this);
		mMapView = (MapView) findViewById(R.id.qiangdan_user_find_wuliu_bmapview);
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		// 退出界面
		case R.id.qiangdan_user_find_wuliu_back:
			finish();
			break;

		default:
			break;
		}
	}

}
