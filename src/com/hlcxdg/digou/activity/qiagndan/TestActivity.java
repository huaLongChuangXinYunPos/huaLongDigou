package com.hlcxdg.digou.activity.qiagndan;

import java.util.Timer;
import java.util.TimerTask;

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
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.StoreInfoqdBean;
import com.hlcxdg.digou.constant.ConstantLocation;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.utils.PromptManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class TestActivity extends Activity implements OnClickListener {
	private ImageView qiangdan_user_find_wuliu_back;
	private MapView mMapView = null;// qiangdan_user_find_wuliu_bmapview;
	private Context context;
	private Intent myIntent;
	private BaiduMap mBaiduMap;
	private Marker mMarkerD;
	private Marker mMarkerCar;
	private BitmapDescriptor bdD;
	private BitmapDescriptor bdWuliu;
	private Timer timer = null;
	private int i = 0;
	private TimerTask timeTask = null;
	private Handler mHandler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			switch (msg.what) {

			case 1:
				locwei = 40.097530 - i  * dxwei;
				locjing = 116.347530 - i  * dxjing;
				moveCar();

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.qiangdan_user_find_wuliu_map);
		context = TestActivity.this;

		init();
		mapControler();

	}

	OverlayOptions ooWuliu;
	double dxwei;
	double dxjing;

	// �߳���
	class ThreadShow implements Runnable {

		@Override
		public void run() {
			while (i<20) {
				try {
					Thread.sleep(5000);
					Message msg = new Message();
					msg.what = 1;
					mHandler.sendMessage(msg);
					System.out.println("send...");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					System.out.println("thread error...");
				}
				i++;
			}
		}
	}

	public void initOverlay() {
		// add marker overlay��Ӹ���������
		LatLng llD = new LatLng(40.097530, 116.347530);
		OverlayOptions ooD = new MarkerOptions().position(llD).icon(bdD)
				.zIndex(3);
		mMarkerD = (Marker) (mBaiduMap.addOverlay(ooD));
		 dxwei = (40.097530 - 40.068926) /20;
		 dxjing = (116.347530 - 116.327530) / 20;
		LatLng llWuliu = new LatLng(40.097530, 116.347530);
		ooWuliu = new MarkerOptions().position(llWuliu).icon(bdWuliu).zIndex(3);
		mMarkerCar = (Marker) (mBaiduMap.addOverlay(ooWuliu));
		new Thread(new ThreadShow()).start();

		
	}

	double locwei;
	double locjing;

	public void moveCar() {
		LatLng llWuliu1 = new LatLng(locwei, locjing);
		mMarkerCar.setPosition(llWuliu1);
	}

	public void mapControler() {
		mBaiduMap = mMapView.getMap();
		// ������λͼ��
		mBaiduMap.setMyLocationEnabled(true);
		// �ҵĶ�λͼ��
		MyLocationData locData = new MyLocationData.Builder().direction(180)
				.latitude(40.068926).longitude(116.327530).build();
		mBaiduMap.setMyLocationData(locData);
		LatLng ll = new LatLng(40.068926, 116.327530);
		MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
		mBaiduMap.animateMapStatus(u);
		MapStatusUpdate msu = MapStatusUpdateFactory.zoomTo(15.0f);
		mBaiduMap.setMapStatus(msu);
		initOverlay();

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
		// �˳�����
		case R.id.qiangdan_user_find_wuliu_back:
			finish();
			break;

		default:
			break;
		}
	}

}
