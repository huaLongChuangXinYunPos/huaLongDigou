package com.hl_zhaoq.digou;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Application;
import android.content.ContentResolver;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.forlong401.log.transaction.log.manager.LogManager;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.bean.AdvertBean;
import com.hl_zhaoq.digou.bean.Constant;
import com.hl_zhaoq.digou.bean.UserInfo;
import com.hl_zhaoq.digou.constant.ConstantLocation;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.tel.cst.ConstantTel;
import com.hl_zhaoq.digou.utils.AppHelper;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;

public class HlmainApplication extends Application {
	
	//
	private SharedPreferences sp = null;

	@Override
	public void onCreate() {
		super.onCreate();
		// 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
		sp = this.getSharedPreferences("ipcfs", this.MODE_PRIVATE);
		SDKInitializer.initialize(this);
		getUserInfo();
		getOSVersion();
		new Thread(new Runnable() {

			@Override
			public void run() {
				getAdsHengTask();
			}

		}).start();
		new Thread(new Runnable() {

			@Override
			public void run() {
				getAdsShuTask();
			}

		}).start();

		// 读取手机联系人
		new Thread(new Runnable() {

			@Override
			public void run() {
				getLiaxiren();
			}
		}).start();
		LogManager.getManager(getApplicationContext()).registerCrashHandler();

	}
//获取硬件，系统，应用信息
	@SuppressWarnings("unused")
	private void getOSVersion() {
		String appversion=AppHelper.getAppVersion(getApplicationContext());
		String androidos="ANDROID:"+AppHelper.getDeviceModel();
		String serialNumber=AppHelper.getSerialNumber();//9ea5a635
		String AndroidOSVersion=AppHelper.getAndroidVersion();
		System.out.println("version");
		
	}

	private void getUserInfo() {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		String editStr = sp.getString("uinfo", "");
		ArrayList<UserInfo> userLists = gson.fromJson(editStr,
				new TypeToken<List<UserInfo>>() {
				}.getType());
		if (userLists != null && userLists.size() > 0) {
			ConstantValue.userinfo = userLists.get(0);
		}
	}

	// 横向播放json数据下载"*AIS|RQ|Advert|00|02|AIE#";
	public void getAdsShuTask() {
		// TODO Auto-generated method stub
		try {
			String msg = "*AIS|RQ|Advert|00|02|AIE#";
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("type", "post");
			map.put("url", getString(R.string.url_advs));
			map.put("dataType", "text");
			map.put("data", msg);
			RequestVo vo = new RequestVo(R.string.url_advs, this, map);
			String result = NetUtil.post(vo);
			// 2015-12-16
			// ----*AIS|RS|Advert|01|[{"adNo":"100003","cStoreNo":"10003","adImageName":"100003","adImagePath":"/advpic/100003.jpg","adcGoodsNo":"440031","adcSupNo":"","adWeizhi":"02","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":1,"adSkipUrl":""},{"adNo":"100001","cStoreNo":"10001","adImageName":"100001","adImagePath":"/advpic/100001.jpg","adcGoodsNo":"440062","adcSupNo":"","adWeizhi":"01","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":1,"adSkipUrl":""},{"adNo":"100002","cStoreNo":"10002","adImageName":"100002","adImagePath":"/advpic/100002.jpg","adcGoodsNo":"110026","adcSupNo":"","adWeizhi":"01","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":2,"adSkipUrl":""},{"adNo":"100008","cStoreNo":"10001","adImageName":"100008","adImagePath":"/advpic/100008.jpg","adcGoodsNo":"440062","adWeizhi":"02","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":2},{"adNo":"100009","cStoreNo":"10001","adImageName":"100009","adImagePath":"/advpic/100009.jpg","adcGoodsNo":"110026","adWeizhi":"02","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":3},{"adNo":"100004","cStoreNo":"10001","adImageName":"100004","adImagePath":"/advpic/100004.jpg","adcGoodsNo":"440031","adWeizhi":"01","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":3},{"adNo":"100005","cStoreNo":"10001","adImageName":"100005","adImagePath":"/advpic/100005.jpg","adcGoodsNo":"110026","adWeizhi":"01","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":4},{"adNo":"100010","cStoreNo":"10001","adImageName":"100010","adImagePath":"/advpic/100010.jpg","adcGoodsNo":"110026","adWeizhi":"02","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":4},{"adNo":"100011","cStoreNo":"10001","adImageName":"100011","adImagePath":"/advpic/100011.jpg","adcGoodsNo":"110026","adWeizhi":"02","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":5},{"adNo":"100006","cStoreNo":"10001","adImageName":"100006","adImagePath":"/advpic/100006.jpg","adcGoodsNo":"440062","adWeizhi":"01","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":5},{"adNo":"100007","cStoreNo":"10001","adImageName":"100007","adImagePath":"/advpic/100007.jpg","adcGoodsNo":"110026","adWeizhi":"01","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":6}]|AIE#

			// 2015-12-12------
			// *AIS|RS|Advert|01|[{"adNo":"100001","cStoreNo":"10001","adImageName":"100001",
			// "adImagePath":"/advpic/100001.png","adcGoodsNo":"440062","adcSupNo":"","adBgnDate":"2015-12-01 00:00:00.0",
			// "adEndDate":"2015-12-10 00:00:00.0","adPaixu":1,"adSkipUrl":""},
			// {"adNo":"100002","cStoreNo":"10001","adImageName":"100002","adImagePath":"/advpic/100002.png","adcGoodsNo":"110026","adcSupNo":"","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-10 00:00:00.0","adPaixu":2,"adSkipUrl":""},{"adNo":"100003","cStoreNo":"10001","adImageName":"100003","adImagePath":"/advpic/100003.jpg","adcGoodsNo":"440031","adcSupNo":"","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-10 00:00:00.0","adPaixu":3,"adSkipUrl":""}]|AIE#

			String[] temp = NetUtil.split(result, "|");
			if (temp == null) {
			} else if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
					&& "Advert".equals(temp[2]) && "01".equals(temp[3])) {

				Gson gson = new Gson();
				ArrayList<AdvertBean> listAdvert = gson.fromJson(temp[4],
						new TypeToken<List<AdvertBean>>() {
						}.getType());
				gson = null;
				Constant.listAdvertshu = listAdvert;
				if (Constant.listAdvertshu != null
						&& Constant.listAdvertshu.size() > 0) {
					Editor editor = sp.edit();
					editor.putString("listData", temp[4]);
					editor.commit();
				}
			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}
	}

	// 横向播放json数据下载"*AIS|RQ|Advert|00|01|AIE#";
	public void getAdsHengTask() {
		// TODO Auto-generated method stub
		try {
			String msg = "*AIS|RQ|Advert|00|01|AIE#";
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("type", "post");
			map.put("url", getString(R.string.url_advs));
			map.put("dataType", "text");
			map.put("data", msg);
			RequestVo vo = new RequestVo(R.string.url_advs, this, map);
			String result = NetUtil.post(vo);
			// 2015-12-16
			// ----*AIS|RS|Advert|01|[{"adNo":"100003","cStoreNo":"10003","adImageName":"100003","adImagePath":"/advpic/100003.jpg","adcGoodsNo":"440031","adcSupNo":"","adWeizhi":"02","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":1,"adSkipUrl":""},{"adNo":"100001","cStoreNo":"10001","adImageName":"100001","adImagePath":"/advpic/100001.jpg","adcGoodsNo":"440062","adcSupNo":"","adWeizhi":"01","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":1,"adSkipUrl":""},{"adNo":"100002","cStoreNo":"10002","adImageName":"100002","adImagePath":"/advpic/100002.jpg","adcGoodsNo":"110026","adcSupNo":"","adWeizhi":"01","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":2,"adSkipUrl":""},{"adNo":"100008","cStoreNo":"10001","adImageName":"100008","adImagePath":"/advpic/100008.jpg","adcGoodsNo":"440062","adWeizhi":"02","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":2},{"adNo":"100009","cStoreNo":"10001","adImageName":"100009","adImagePath":"/advpic/100009.jpg","adcGoodsNo":"110026","adWeizhi":"02","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":3},{"adNo":"100004","cStoreNo":"10001","adImageName":"100004","adImagePath":"/advpic/100004.jpg","adcGoodsNo":"440031","adWeizhi":"01","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":3},{"adNo":"100005","cStoreNo":"10001","adImageName":"100005","adImagePath":"/advpic/100005.jpg","adcGoodsNo":"110026","adWeizhi":"01","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":4},{"adNo":"100010","cStoreNo":"10001","adImageName":"100010","adImagePath":"/advpic/100010.jpg","adcGoodsNo":"110026","adWeizhi":"02","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":4},{"adNo":"100011","cStoreNo":"10001","adImageName":"100011","adImagePath":"/advpic/100011.jpg","adcGoodsNo":"110026","adWeizhi":"02","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":5},{"adNo":"100006","cStoreNo":"10001","adImageName":"100006","adImagePath":"/advpic/100006.jpg","adcGoodsNo":"440062","adWeizhi":"01","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":5},{"adNo":"100007","cStoreNo":"10001","adImageName":"100007","adImagePath":"/advpic/100007.jpg","adcGoodsNo":"110026","adWeizhi":"01","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-20 00:00:00.0","adPaixu":6}]|AIE#

			// 2015-12-12------
			// *AIS|RS|Advert|01|[{"adNo":"100001","cStoreNo":"10001","adImageName":"100001",
			// "adImagePath":"/advpic/100001.png","adcGoodsNo":"440062","adcSupNo":"","adBgnDate":"2015-12-01 00:00:00.0",
			// "adEndDate":"2015-12-10 00:00:00.0","adPaixu":1,"adSkipUrl":""},
			// {"adNo":"100002","cStoreNo":"10001","adImageName":"100002","adImagePath":"/advpic/100002.png","adcGoodsNo":"110026","adcSupNo":"","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-10 00:00:00.0","adPaixu":2,"adSkipUrl":""},{"adNo":"100003","cStoreNo":"10001","adImageName":"100003","adImagePath":"/advpic/100003.jpg","adcGoodsNo":"440031","adcSupNo":"","adBgnDate":"2015-12-01 00:00:00.0","adEndDate":"2015-12-10 00:00:00.0","adPaixu":3,"adSkipUrl":""}]|AIE#

			String[] temp = NetUtil.split(result, "|");
			if (temp == null) {
			} else if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
					&& "Advert".equals(temp[2]) && "01".equals(temp[3])) {
				Gson gson = new Gson();
				ArrayList<AdvertBean> listAdvert = gson.fromJson(temp[4],
						new TypeToken<List<AdvertBean>>() {
						}.getType());
				gson = null;
				Constant.listAdvertheng = listAdvert;
				Editor editor = sp.edit();
				if (Constant.listAdvertheng != null
						&& Constant.listAdvertheng.size() > 0) {
					editor.putString("viewPagerData", temp[4]);
					editor.commit();
				}

			}
		} catch (JsonSyntaxException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		LogManager.getManager(getApplicationContext()).unregisterCrashHandler();
	}

	// 手机联系人
	public void getLiaxiren() {
		ConstantTel.contactList.clear();
		List<Map<String, String>> data = new ArrayList<Map<String, String>>();
		ContentResolver resolver = getContentResolver();
		Uri uri = Uri.parse("content://com.android.contacts/raw_contacts");
		Uri datauri = Uri.parse("content://com.android.contacts/data");
		// ��ѯraw_contact�� ȡ��ϵ��id
		Cursor cursor = resolver.query(uri, new String[] { "contact_id" },
				null, null, null);
		while (cursor.moveToNext()) {
			String id = cursor.getString(0);
			String name = "";
			String num = "";
			if (id != null) {

				Map<String, String> map = new HashMap<String, String>();
				// ��ѯdata�� �ѵ�ǰ��ϵ�˵����ݸ�ȡ����.
				Cursor dataCursor = resolver.query(datauri, null,
						"raw_contact_id=?", new String[] { id }, null);
				while (dataCursor.moveToNext()) {
					String data1 = dataCursor.getString(dataCursor
							.getColumnIndex("data1"));
					String mimetype = dataCursor.getString(dataCursor
							.getColumnIndex("mimetype"));
					if ("vnd.android.cursor.item/phone_v2".equals(mimetype)) {
						// System.out.println("�绰:" + data1);
						num = data1;

					} else if ("vnd.android.cursor.item/name".equals(mimetype)) {
						// System.out.println("����:" + data1);
						name = data1;

					}
				}
				dataCursor.close();
				if (TextUtils.isEmpty(num)) {
					continue;
				} else if (TextUtils.isEmpty(name)) {
					name = "未知";
				}
				map.put("phone", num);
				map.put("name", name);
				ConstantTel.contactList.add(map);
			}
		}
		cursor.close();

		// ConstantTel.contactList.addAll(data);
	}

}