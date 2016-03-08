package com.hl_zhaoq.digou.activity.goods;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.address.AddressActivity;
import com.hl_zhaoq.digou.activity.user.LoginActivity;
import com.hl_zhaoq.digou.bean.Goods;
import com.hl_zhaoq.digou.bean.GroupType3;
import com.hl_zhaoq.digou.bean.SaleSheet;
import com.hl_zhaoq.digou.bean.SaleSheetDetail;
import com.hl_zhaoq.digou.bean.UserInfo;
import com.hl_zhaoq.digou.constant.ConstantIP;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class TuijianGoodActivity extends Activity {
	Socket s = null;// 声明Socket的引用
	DataOutputStream dout = null;// 输出流
	DataInputStream din = null;// 输入流
	Goods goods;
	private TextView tv_goodname;
	private TextView goods_price1;
	private TextView goods_price2;
//	private TextView goods_danwei;
//	private TextView goods_guige;
//	private TextView goods_shangjia;
	SmartImageView vi_goods;
	protected int lastPosition;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.goodsxiangqing);
		initView();
		new MyAsTask().execute(TuijianGoodActivity.this);

	}

	ScrollView sc;
	RelativeLayout loading_bg;

	public class MyAsTask extends AsyncTask<Context, ProgressBar, Boolean> {
		Context context;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
				PromptManager.showToast(context, "连接失败");
			} else {
				loading_bg.setVisibility(View.GONE);
				sc.setVisibility(View.VISIBLE);
				vi_goods.setImageUrl(ConstantIP.ImageviewURL
						+ ConstantValue.goodsinfo.getMinIMG1());

				tv_goodname.setText(ConstantValue.goodsinfo.getcGoodsName());
				goods_price1.setText("￥"
						+ ConstantValue.goodsinfo.getfNormalPrice()
								.setScale(2, BigDecimal.ROUND_HALF_UP)
								.toString() + "元");
				goods_price2.setText("￥"
						+ ConstantValue.goodsinfo.getfVipPrice_student()
								.setScale(2, BigDecimal.ROUND_HALF_UP)
								.toString() + "元");
//				goods_danwei.setText("件");
//				goods_guige.setText("件");
//				goods_shangjia.setText(goods.getcProductdDate().substring(0, 19));

			}

		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(ProgressBar... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected Boolean doInBackground(Context[] arg0) {
			this.context = arg0[0];
			try {
				final String grno = ConstantValue.sgoodno;
				if (grno == null) {
					return false;
				}
				String msg = "*AIS|RQ|PD01|" + "10001" + "|" + grno + "|AIE#";
				PromptManager.showLogTest(
						"PLAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_pl01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_pl01, context, map);
				String result = NetUtil.post(vo);
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");

				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "PD01".equals(temp[2]) && "01".equals(temp[3])) {
						Gson gson = new Gson();
						ArrayList<Goods> goodsList = gson.fromJson(temp[4],
								new TypeToken<List<Goods>>() {
								}.getType());
						if (goodsList != null) {
							ConstantValue.goodsinfo = goodsList.get(0);
							return true;
						}

					}
				}
			} catch (Exception e) {
				e.printStackTrace();

				return false;
			}

			return false;
		}
	}

	private void initView() {
		sc = (ScrollView) findViewById(R.id.sc);
		vi_goods = (SmartImageView) findViewById(R.id.iv_goods);
		loading_bg = (RelativeLayout) findViewById(R.id.loading_bg);
		tv_goodname = (TextView) findViewById(R.id.tv_goodname);
		goods_price1 = (TextView) findViewById(R.id.goods_price1);
		goods_price2 = (TextView) findViewById(R.id.goods_price2);
//		goods_danwei = (TextView) findViewById(R.id.goods_danwei);
//		goods_guige = (TextView) findViewById(R.id.goods_guige);
//		goods_shangjia = (TextView) findViewById(R.id.goods_shangjia);

	}

	public void goback(View v) {
		TuijianGoodActivity.this.finish();
	}

	public void lijigou(View v) {
	
	}

	public void gouwu(View v) {

		

	}

}
