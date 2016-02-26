package com.hlcxdg.digou.tel.activity;

import java.util.ArrayList;
import java.util.List;

import com.hlcxdg.digou.R;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.tel.cst.ConstantTel;
import com.hlcxdg.digou.tel.fragment.ContactsFragment;
import com.hlcxdg.digou.tel.fragment.HomeFragment;
import com.hlcxdg.digou.tel.fragment.RechargeFragment;
import com.hlcxdg.digou.tel.fragment.UserinfoFragment;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TelActivity extends FragmentActivity {
	private ViewPager viewPager;
	private ImageView imageView;
	private LinearLayout tel_home, tel_contact, tel_recharge, tel_my_info;
	private TextView tv_tel_home, tv_tel_contact, tv_tel_recharge, tv_my_info;
	private List<Fragment> fragments;
	private int offset = 0;
	private int currIndex = 0;
	private int bmpW;
	private int selectedColor, unSelectedColor;
	private static final int pageSize = 4;

	public void goback(View v) {
		viewPager.setCurrentItem(0);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.tel_main);
		initView();
		ConstantTel.androidOsInfo();
		ConstantTel.getImsi(TelActivity.this);
		NetUtil.hasNetwork(TelActivity.this);

	}

	private void initView() {
		selectedColor = getResources().getColor(R.color.TextColorRed);
		unSelectedColor = getResources().getColor(R.color.TextColorGray);
		InitImageView();
		InitTextView();
		InitLinearLayoutView();
		InitViewPager();
		selectHome();
	}

	private void InitImageView() {
		imageView = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(),
				R.drawable.tab_selected_bg).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / pageSize - bmpW) / 2;// 计算偏移量--(屏幕宽度/页卡总数-图片实际宽度)/2
													// = 偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		imageView.setImageMatrix(matrix);// 设置动画初始位置

	}

	private void InitLinearLayoutView() {
		tel_home = (LinearLayout) findViewById(R.id.tel_home);
		tel_contact = (LinearLayout) findViewById(R.id.tel_contact);
		tel_recharge = (LinearLayout) findViewById(R.id.tel_recharge);
		tel_my_info = (LinearLayout) findViewById(R.id.tel_my_info);
		tel_home.setOnClickListener(new MyOnClickListener(0));
		tel_contact.setOnClickListener(new MyOnClickListener(1));
		tel_recharge.setOnClickListener(new MyOnClickListener(2));
		tel_my_info.setOnClickListener(new MyOnClickListener(3));
	}

	private void InitViewPager() {
		viewPager = (ViewPager) findViewById(R.id.vPager);
		fragments = new ArrayList<Fragment>();
		fragments.add(new HomeFragment());
		fragments.add(new ContactsFragment());
		fragments.add(new RechargeFragment());
		fragments.add(new UserinfoFragment(viewPager));
		viewPager.setAdapter(new myPagerAdapter(getSupportFragmentManager(),
				fragments));
		viewPager.setCurrentItem(0);
		viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void InitTextView() {
		tv_tel_home = (TextView) findViewById(R.id.tv_tel_home);
		tv_tel_contact = (TextView) findViewById(R.id.tv_tel_contact);
		tv_tel_recharge = (TextView) findViewById(R.id.tv_tel_recharge);
		tv_my_info = (TextView) findViewById(R.id.tv_my_info);
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {
		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量

		public void onPageScrollStateChanged(int index) {
		}

		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		public void onPageSelected(int index) {
			Animation animation = new TranslateAnimation(one * currIndex, one
					* index, 0, 0);// 显然这个比较简洁，只有一行代码。
			currIndex = index;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			imageView.startAnimation(animation);
			switch (index) {
			case 0:
				TelActivity.this.selectHome();
				break;
			case 1:
				TelActivity.this.selectContact();
				break;
			case 2:
				TelActivity.this.selectRecharge();
				break;
			case 3:
				TelActivity.this.selectMyinfo();
				break;

			}
		}
	}

	class myPagerAdapter extends FragmentPagerAdapter {
		private List<Fragment> fragmentList;

		public myPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
			super(fm);
			this.fragmentList = fragmentList;
		}

		@Override
		public Fragment getItem(int arg0) {
			return (fragmentList == null || fragmentList.size() == 0) ? null
					: fragmentList.get(arg0);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return null;
		}

		@Override
		public int getCount() {
			return fragmentList == null ? 0 : fragmentList.size();
		}
	}

	private class MyOnClickListener implements OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		public void onClick(View v) {
			switch (index) {
			case 0:
				TelActivity.this.selectHome();
				break;
			case 1:
				TelActivity.this.selectContact();
				break;
			case 2:
				TelActivity.this.selectRecharge();
				break;
			case 3:
				TelActivity.this.selectMyinfo();
				break;
			}
			viewPager.setCurrentItem(index);
		}
	}

	public void selectMyinfo() {
		tv_tel_home.setTextColor(unSelectedColor);
		tv_tel_contact.setTextColor(unSelectedColor);
		tv_tel_recharge.setTextColor(unSelectedColor);
		tv_my_info.setTextColor(selectedColor);
	}

	public void selectRecharge() {
		tv_tel_home.setTextColor(unSelectedColor);
		tv_tel_contact.setTextColor(unSelectedColor);
		tv_tel_recharge.setTextColor(selectedColor);
		tv_my_info.setTextColor(unSelectedColor);
	}

	public void selectContact() {
		tv_tel_home.setTextColor(unSelectedColor);
		tv_tel_contact.setTextColor(selectedColor);
		tv_tel_recharge.setTextColor(unSelectedColor);
		tv_my_info.setTextColor(unSelectedColor);
	}

	public void selectHome() {
		tv_tel_home.setTextColor(selectedColor);
		tv_tel_contact.setTextColor(unSelectedColor);
		tv_tel_recharge.setTextColor(unSelectedColor);
		tv_my_info.setTextColor(unSelectedColor);
	}
}
