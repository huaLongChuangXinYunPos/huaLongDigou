package com.hlcxdg.digou.activity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.address.MineAddressActivity;
import com.hlcxdg.digou.activity.coupon.CouponActivity;
import com.hlcxdg.digou.activity.cuxiao.STStoreListActivity;
import com.hlcxdg.digou.activity.goods.MingyouGoodsActivity;
import com.hlcxdg.digou.activity.qiagndan.UserMainqiangdanActivity;
import com.hlcxdg.digou.activity.salesheet.SaleSheetMineActivity;
import com.hlcxdg.digou.activity.salesheet.SalesheetActivity;
import com.hlcxdg.digou.activity.storeac.StoreListActivity;
import com.hlcxdg.digou.activity.user.LoginActivity;
import com.hlcxdg.digou.activity.wallet.WalletLoginActivity;
import com.hlcxdg.digou.bean.Goods;
import com.hlcxdg.digou.bean.SaleSheet;
import com.hlcxdg.digou.bean.SaleSheetDetail;
import com.hlcxdg.digou.bean.UserAddress;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.fragment.HomeCartFragment;
import com.hlcxdg.digou.fragment.HomeFenleiFragment;
import com.hlcxdg.digou.fragment.HomeMainFragment;
import com.hlcxdg.digou.fragment.HomeMyinfoFragment;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.tel.activity.TelActivity;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.utils.hykj.selectarealib.SelectAreaWheelPopW;
import com.hlcxdg.digou.vo.RequestVo;
import com.zxing.activity.CaptureActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity implements OnClickListener {
	// 导航栏按钮
	private LinearLayout ll_home_menu_main;
	private LinearLayout ll_sousuo_menu_main;
	private LinearLayout ll_dianhua_menu_main;
	private LinearLayout ll_cart_menu_main;
	private LinearLayout ll_myinfo_menu_main;
	// 四大界面
	public HomeMainFragment homeMainFragment;
	public HomeFenleiFragment homeFenleiFragment;
	public HomeCartFragment homeCartFragment;
	public HomeMyinfoFragment homeMyinfoFragment;
	private boolean isExit = false;// �Ƿ��˳�
	private TimerTask timeTask = null;
	private SharedPreferences settings = null;
	private Timer timer = null;

	private ImageView iv_home_page;
	private ImageView iv_sousuo_menu;
	private ImageView iv_myinfo_menu;
	private ImageView iv_cart_menu;

	private TextView tv_home_page;
	private TextView tv_sousuo_menu;
	private TextView tv_cart_menu;
	private TextView tv_myinfo_menu;
	public static TextView iv_cart_dian_menu;

	public void jianshe2(View view) {
		PromptManager.showToast(getApplicationContext(), "建设中");
	}

	public void go_fujindianpu(View view) {
		Intent intent;
		intent = new Intent();
		intent.setClass(MainActivity.this, StoreListActivity.class);
		startActivity(intent);
	}

	public void digou(View view) {
		Intent intent;
		intent = new Intent();
		intent.setClass(MainActivity.this, UserMainqiangdanActivity.class);
		startActivity(intent);
	}

	// lilaile
	public void lilaile(View view) {
		if (ConstantValue.userinfo == null) {
			//
			Intent intent;
			intent = new Intent();
			intent.setClass(MainActivity.this, LoginActivity.class);
			startActivityForResult(intent, 1000);

		} else {
			Intent intent;
			intent = new Intent();
			intent.setClass(MainActivity.this, CouponActivity.class);
			startActivity(intent);
		}
	}

	public void wallet(View view) {
		Intent intent;
		intent = new Intent();
		intent.setClass(MainActivity.this, WalletLoginActivity.class);
		startActivity(intent);
	}

	public void mingyou(View view) {
		Intent intent;
		intent = new Intent();
		intent.setClass(MainActivity.this, MingyouGoodsActivity.class);
		startActivity(intent);
	}

	public void dingdan_go(View v) {
		if (ConstantValue.userinfo == null) {

			Intent intent;
			intent = new Intent();
			intent.setClass(MainActivity.this, LoginActivity.class);
			startActivityForResult(intent, 1001);

		} else {
			Intent intent;
			intent = new Intent();
			intent.setClass(MainActivity.this, SaleSheetMineActivity.class);
			startActivity(intent);
		}

	}

	public void ddfromUser(View v) {
		Intent intent;
		intent = new Intent();
		intent.setClass(MainActivity.this, SaleSheetMineActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		Bundle b = intent.getExtras();
		if (b == null) {
			return;
		}
		int iPage = 0;
		iPage = b.getInt("indexMenu");
		switch (iPage) {
		case 21:
			goHomepage();
			break;
		case 22:
			goFenleipage();
			break;
		case 23:
			goCartpage();
			break;
		case 24:
			goMyinfoPage();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// int ii=getIntent().getIntExtra("indexMenu", 0);
		// Bundle extras=getIntent().getExtras();
		// if (extras!=null) {
		// int ii=extras.getInt("indexMenu", 0);
		// System.out.println(ii);
		// }

		gettotalnum();
		if (ConstantValue.total_Nums_cart != 0) {
			iv_cart_dian_menu.setVisibility(View.VISIBLE);
			iv_cart_dian_menu.setText(ConstantValue.total_Nums_cart + "");
		} else {
			iv_cart_dian_menu.setVisibility(View.GONE);
		}
		g = new Goods();
		g.setcStoreNo("10001");
		ConstantValue.goodsinfo = g;

	}

	public class AddrAsynTask extends AsyncTask<Context, ProgressBar, Boolean> {
		List<UserAddress> useraddrlist;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			PromptManager.closeSpotsDialog();
			if (!result) {
				// �����쳣
				ConstantValue.myaddr = null;
			} else {
				ConstantValue.myaddr = useraddrlist.get(0);

			}
			// --------------------------------------------------
			ConstantValue.cartSaleSureList.clear();
			ConstantValue.cartSaleSureList
					.addAll(ConstantValue.cartSaleSheetList);
			// ConstantValue.cartSaleSheetList.clear();
			Intent intent;
			intent = new Intent();
			intent.setClass(getApplicationContext(), SalesheetActivity.class);
			startActivity(intent);

			// Intent intent;
			// intent = new Intent();
			// intent.setClass(getApplicationContext(),
			// SalesheetActivity.class);
			// startActivity(intent);
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			PromptManager.showSpotsDialog(context);
		}

		@Override
		protected void onProgressUpdate(ProgressBar... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected Boolean doInBackground(Context[] arg0) {
			try {
				Boolean network = NetUtil.hasNetwork(context);
				if (!network) {
					return false;
				}

				PromptManager.showLogTest("SalesheetActivity",
						"doInBackground-yuwang");
				// �����滻
				String msg = "*AIS|RQ|AD01|00|"
						+ ConstantValue.userinfo.getUserNo() + "|AIE#";

				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_ad01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_ad01, context, map);

				String result = NetUtil.post(vo);
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else

				if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
						&& "AD01".equals(temp[2]) && "01".equals(temp[3])) {
					Gson gson = new Gson();
					UserAddress userAddress = new UserAddress();
					useraddrlist = gson.fromJson(temp[4],
							new TypeToken<List<UserAddress>>() {
							}.getType());
					if (useraddrlist != null && useraddrlist.size() > 0) {
						return true;
					}

				}
			} catch (Exception e) {
				return false;
			}

			return false;
		}
	}

	public void goToSaleSheet() {
		new AddrAsynTask().execute(getApplicationContext());

	}

	public void jiesuan(View view) {
		if (ConstantValue.cartSaleSheetList.isEmpty()) {
			Toast.makeText(getApplicationContext(), "购物车为空", 0);
			return;
		}
		goToSaleSheet();
		//
		// Intent intent;
		// intent = new Intent();
		// intent.setClass(getApplicationContext(), SalesheetActivity.class);
		// startActivity(intent);

	}

	@Override
	public void onBackPressed() {
		if (isExit) {
			finish();
		} else {
			isExit = true;
			Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT)
					.show();
			timeTask = new TimerTask() {

				@Override
				public void run() {
					isExit = false;
				}
			};
			timer.schedule(timeTask, 2000);
		}
	}

	public void goHomepage() {
		iv_home_page.setImageResource(R.drawable.ic_main_home_sel);
		iv_sousuo_menu.setImageResource(R.drawable.ic_main_sort_nor);
		iv_myinfo_menu.setImageResource(R.drawable.ic_main_my_nor);
		iv_cart_menu.setImageResource(R.drawable.ic_main_car_nor);
		tv_home_page
				.setTextColor(getResources().getColor(R.color.TextColorRed));
		tv_sousuo_menu.setTextColor(getResources().getColor(
				R.color.TextColorGray));
		tv_cart_menu.setTextColor(getResources()
				.getColor(R.color.TextColorGray));
		tv_myinfo_menu.setTextColor(getResources().getColor(
				R.color.TextColorGray));
		homeMainFragment = new HomeMainFragment();

		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
				android.R.anim.fade_out);
		fragmentTransaction.replace(R.id.activity_main_fg, homeMainFragment,
				"homeMainFragment");

		fragmentTransaction.commit();

		homeFenleiFragment = null;
		homeCartFragment = null;
		homeMyinfoFragment = null;

	}

	//
	public void goFenleipage() {
		iv_home_page.setImageResource(R.drawable.ic_main_home_nor);
		iv_sousuo_menu.setImageResource(R.drawable.ic_main_sort_sel);
		iv_myinfo_menu.setImageResource(R.drawable.ic_main_my_nor);
		iv_cart_menu.setImageResource(R.drawable.ic_main_car_nor);
		tv_home_page.setTextColor(getResources()
				.getColor(R.color.TextColorGray));
		tv_sousuo_menu.setTextColor(getResources().getColor(
				R.color.TextColorRed));
		tv_cart_menu.setTextColor(getResources()
				.getColor(R.color.TextColorGray));
		tv_myinfo_menu.setTextColor(getResources().getColor(
				R.color.TextColorGray));

		homeFenleiFragment = new HomeFenleiFragment();
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
				android.R.anim.fade_out);
		fragmentTransaction.replace(R.id.activity_main_fg, homeFenleiFragment,
				"homeFenleiFragment");

		fragmentTransaction.commit();
		homeMainFragment = null;
		homeCartFragment = null;
		homeMyinfoFragment = null;
	}

	public void goDianhuapage() {
		Intent intent = new Intent(MainActivity.this, TelActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.bounce_down_in_tantiao,
				R.anim.bounce_down_out_tantiao);
	}

	public void goCartpage() {
		if (NetUtil.hasNetwork(MainActivity.this)) {
			if (true) {
				// ��¼
			}
			iv_home_page.setImageResource(R.drawable.ic_main_home_nor);
			iv_sousuo_menu.setImageResource(R.drawable.ic_main_sort_nor);
			iv_myinfo_menu.setImageResource(R.drawable.ic_main_my_nor);
			iv_cart_menu.setImageResource(R.drawable.ic_main_car_sel);
			tv_home_page.setTextColor(getResources().getColor(
					R.color.TextColorGray));
			tv_sousuo_menu.setTextColor(getResources().getColor(
					R.color.TextColorGray));
			tv_cart_menu.setTextColor(getResources().getColor(
					R.color.TextColorRed));
			tv_myinfo_menu.setTextColor(getResources().getColor(
					R.color.TextColorGray));
			homeCartFragment = new HomeCartFragment();

			fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
					android.R.anim.fade_out);
			fragmentTransaction.replace(R.id.activity_main_fg,
					homeCartFragment, "homeCartFragment");

			fragmentTransaction.commit();
			homeMainFragment = null;
			homeFenleiFragment = null;
			homeMyinfoFragment = null;
		}
	}

	public void goMyinfoPage() {
		iv_home_page.setImageResource(R.drawable.ic_main_home_nor);
		iv_sousuo_menu.setImageResource(R.drawable.ic_main_sort_nor);
		iv_myinfo_menu.setImageResource(R.drawable.ic_main_my_sel);
		iv_cart_menu.setImageResource(R.drawable.ic_main_car_nor);
		tv_home_page.setTextColor(getResources()
				.getColor(R.color.TextColorGray));
		tv_sousuo_menu.setTextColor(getResources().getColor(
				R.color.TextColorGray));
		tv_cart_menu.setTextColor(getResources()
				.getColor(R.color.TextColorGray));
		tv_myinfo_menu.setTextColor(getResources().getColor(
				R.color.TextColorRed));

		homeMyinfoFragment = new HomeMyinfoFragment();

		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
				android.R.anim.fade_out);
		fragmentTransaction.replace(R.id.activity_main_fg, homeMyinfoFragment,
				"homeMyinfoFragment");

		fragmentTransaction.commit();
		homeMainFragment = null;
		homeFenleiFragment = null;
		homeCartFragment = null;
	}

	/**
	 * @param whichIsDefault
	 */
	private void setFragmentIndicator(int whichIsDefault) {

		homeMainFragment = new HomeMainFragment();
		fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(R.id.activity_main_fg, homeMainFragment,
				"homeMainFragment");
		fragmentTransaction.addToBackStack(null);

		fragmentTransaction.commit();

	}

	Goods g;
	FragmentManager fragmentManager;
	FragmentTransaction fragmentTransaction;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.home_menu_all);

		fragmentManager = getSupportFragmentManager();
		context = MainActivity.this;
		// ����һ���µķ��ػ����̶�ʱ��---GBK乱码
		timer = new Timer();
		setFragmentIndicator(0);
		initview();

		// 初始化
		// popW.getInstance(MainActivity.this);

	}

	private void gettotalnum() {
		BigDecimal totalnum = new BigDecimal("0");
		for (int i = 0; i < ConstantValue.cartSaleSheetList.size(); i++) {

			SaleSheet saleSheet = ConstantValue.cartSaleSheetList.get(i);
			List<SaleSheetDetail> saleSheetDetailList = saleSheet
					.getSaleSheetDetailList();
			for (int j = 0; j < saleSheetDetailList.size(); j++) {
				SaleSheetDetail cSSD = saleSheetDetailList.get(j);
				BigDecimal nums = cSSD.getfQuantity();
				totalnum = totalnum.add(nums);

			}
		}
		ConstantValue.total_Nums_cart = totalnum.intValue();
	}

	private void initview() {
		ll_home_menu_main = (LinearLayout) findViewById(R.id.ll_home_menu_main);
		ll_home_menu_main.setOnClickListener(this);
		ll_sousuo_menu_main = (LinearLayout) findViewById(R.id.ll_sousuo_menu_main);
		ll_sousuo_menu_main.setOnClickListener(this);
		ll_dianhua_menu_main = (LinearLayout) findViewById(R.id.ll_dianhua_menu_main);
		ll_dianhua_menu_main.setOnClickListener(this);
		ll_cart_menu_main = (LinearLayout) findViewById(R.id.ll_cart_menu_main);
		ll_cart_menu_main.setOnClickListener(this);
		ll_myinfo_menu_main = (LinearLayout) findViewById(R.id.ll_myinfo_menu_main);
		ll_myinfo_menu_main.setOnClickListener(this);
		iv_home_page = (ImageView) findViewById(R.id.iv_home_page);
		iv_sousuo_menu = (ImageView) findViewById(R.id.iv_sousuo_menu);
		iv_myinfo_menu = (ImageView) findViewById(R.id.iv_myinfo_menu);
		iv_cart_menu = (ImageView) findViewById(R.id.iv_cart_menu);
		iv_cart_dian_menu = (TextView) findViewById(R.id.iv_cart_dian_menu);
		tv_home_page = (TextView) findViewById(R.id.tv_home_page);
		tv_sousuo_menu = (TextView) findViewById(R.id.tv_sousuo_menu);
		tv_cart_menu = (TextView) findViewById(R.id.tv_cart_menu);
		tv_myinfo_menu = (TextView) findViewById(R.id.tv_myinfo_menu);
	}

	public void shaoyishao(View v) {

		Intent intent;
		intent = new Intent(MainActivity.this, CaptureActivity.class);
		startActivityForResult(intent, 0);

	}

	// cuxiaohuodong
	public void cuxiaohuodong(View v) {

		Intent intent;
		intent = new Intent(MainActivity.this, STStoreListActivity.class);
		startActivity(intent);

	}

	// 改变我所在城市
	public void changeLoc(View v) {
		// Intent intent;
		// intent = new Intent(MainActivity.this, Activity01.class);
		// startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			PromptManager.showMyToast(MainActivity.this, scanResult);

		}
		if (requestCode == 1001 && resultCode == 2001) {
			Intent intent;
			intent = new Intent();
			intent.setClass(MainActivity.this, SaleSheetMineActivity.class);
			startActivity(intent);
		}
		//修改地址
		if (requestCode == 1004 && resultCode == 2001) {
			Intent intent;
			intent = new Intent();
			intent.setClass(MainActivity.this, MineAddressActivity.class);
			startActivity(intent);
		}
		if (requestCode == 1000 && resultCode == 2001) {
			Intent intent;
			intent = new Intent();
			intent.setClass(MainActivity.this, CouponActivity.class);
			startActivity(intent);
		}
	}

	SelectAreaWheelPopW popW = new SelectAreaWheelPopW();

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

	}

	@Override
	public void onClick(View view) {

		switch (view.getId()) {

		case R.id.ll_home_menu_main:
			goHomepage();
			break;
		case R.id.ll_sousuo_menu_main:
			goFenleipage();
			break;
		case R.id.ll_dianhua_menu_main:
			goDianhuapage();
			break;
		case R.id.ll_cart_menu_main:
			goCartpage();
			break;
		case R.id.ll_myinfo_menu_main:
			goMyinfoPage();
			break;
		default:
			break;
		}
	}
}
