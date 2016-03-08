package com.hl_zhaoq.digou.utils.hykj.selectarealib;

import com.hl_zhaoq.digou.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

	SelectAreaWheelPopW popW = new SelectAreaWheelPopW();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main1);
//		Button button1 = (Button) findViewById(R.id.button1);
//
//		// 初始化
//		popW.getInstance(MainActivity.this);
//
//		button1.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//
//				// 显示popw
//				popW.showPopw(v, new SelectAreaWheelPopWOnClick() {
//					@Override
//					public void sureOnClick(int provinceId, int cityId,
//							int regionId, String provinceName, String cityName,
//							String regionName) {
//						// 确认操作
//						Toast.makeText(getApplicationContext(),
//								provinceName + cityName + regionName,
//								Toast.LENGTH_SHORT).show();
//					}
//
//					@Override
//					public void cancleOnClick() {
//						// 取消操作
//					}
//				});
//			}
//		});
	}
}
