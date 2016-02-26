package com.hlcxdg.digou.activity;

import com.hlcxdg.digou.activity.address.MineAddressAddActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class BaseActivity extends Activity {

	protected Context context;
	public void backpage(View view){
		finish();
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		context=BaseActivity.this;
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		
	}
	protected  void startActivityNofinish(Class clazz){
		Intent intent=new Intent();
		intent.setClass(context, clazz);
		startActivity(intent);
	}
	protected  void startActivityAndfinish(Class clazz){
		Intent intent=new Intent();
		intent.setClass(context, clazz);
		startActivity(intent);
		finish();
	}
}
