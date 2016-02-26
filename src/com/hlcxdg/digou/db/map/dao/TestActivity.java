package com.hlcxdg.digou.db.map.dao;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hlcxdg.digou.constant.ConstantLocation;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class TestActivity extends Activity {

	public LocationClient mLocationClient = null;
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
//			new MapDAO(db).insertMap();

			
			
			// if (ConstantLocation.myCityString != null) {
			// // tv_mycity_homefg.setText(ConstantLocation.myCityString);
			// mLocationClient.stop();
			// }
		}

	}

	public void getLocation() {
		mLocationClient = new LocationClient(getApplicationContext());//
		mLocationClient.registerLocationListener(myListener);// ע���������

		LocationClientOption option = new LocationClientOption();// �����������
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//
		option.setCoorType("bd09ll");// ���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02
		option.setScanSpan(6000);// ���÷���λ����ļ��ʱ��Ϊ5000ms
		option.setIsNeedAddress(true);// ���صĶ�λ���������ַ��Ϣ
		option.setIgnoreKillProcess(false);
		option.setNeedDeviceDirect(true);// ���صĶ�λ��������ֻ���ͷ�ķ���
		mLocationClient.setLocOption(option);
		mLocationClient.start();// ��������
		if (mLocationClient != null && mLocationClient.isStarted())
			mLocationClient.requestLocation();
	}

	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		db = new MyDatabaseHelper(TestActivity.this).getWritableDatabase();

//		 new MapDAO(db).getMap(1);
//		getLocation();
	}

}
