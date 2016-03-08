package com.hl_zhaoq.digou.activity.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.MainActivity;
import com.hl_zhaoq.digou.activity.goods.GoodsListPL01Activity.GetPL01Asytask;
import com.hl_zhaoq.digou.bean.Goods;
import com.hl_zhaoq.digou.bean.StorePLParam;
import com.hl_zhaoq.digou.constant.ConstantIP;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.utils.swiperefreshlayoutdemo.RefreshLayout;
import com.hl_zhaoq.digou.utils.swiperefreshlayoutdemo.RefreshLayout.OnLoadListener;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class QdGoodsListPL01Activity  extends Activity implements OnClickListener,
OnScrollListener, OnItemClickListener {
	private boolean isLoading = true;
	Context context;
	public ListView pl01_list_goodlist;
	public ImageView iv_back_pl01;
	public ImageView cart_pl01_iv;
	View footers;
	int page = 0;
	boolean isEnd = false;
	GoodsListPl01Adapter goodsListAdapter;
	public void toTopIndex(View view) {

		pl01_list_goodlist.setSelection(0);
	}
	
	public void tocart(View view) {

		Intent intent;
		intent = new Intent();
		Bundle extras = new Bundle();
		extras.putInt("indexMenu", 23);
		intent.putExtras(extras);
		intent.setClass(context, MainActivity.class);
		startActivity(intent);
		finish();
	}
	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		// 回退按钮
		case R.id.iv_back_pl011:
			finish();
			break;
		case R.id.cart_pl01_iv:
			finish();
			break;

		default:
			break;
		}
	}

	public void initView() {
		cart_pl01_iv = (ImageView) findViewById(R.id.iv_homeback_pl02);
		cart_pl01_iv.setVisibility(View.GONE);
//		cart_pl01_iv.setOnClickListener(this);
		iv_back_pl01 = (ImageView) findViewById(R.id.iv_back_pl011);
		iv_back_pl01.setOnClickListener(this);
		pl01_list_goodlist = (ListView) findViewById(R.id.pl01_list_goodlist);
		goodsListAdapter = new GoodsListPl01Adapter(null, this);
		footers=View
				.inflate(this, R.layout.foot, null);
		pl01_list_goodlist.addFooterView(footers);
		pl01_list_goodlist.setAdapter(goodsListAdapter);
		pl01_list_goodlist.setOnScrollListener(this);
		pl01_list_goodlist.setOnItemClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pl01goodlist);
		context = QdGoodsListPL01Activity.this;
		initView();
		isLoading = true;
		new GetPL01Asytask().execute(page);

	}

	public class GetPL01Asytask extends
			AsyncTask<Integer, ProgressBar, List<Goods>> {

		@Override
		protected void onPostExecute(List<Goods> result) {
			super.onPostExecute(result);

			if (result != null && result.size() > 0) {
				goodsListAdapter.addGoodsPL01List(result);
				goodsListAdapter.notifyDataSetChanged();
				page++;
				if (result.size()<9) {
					pl01_list_goodlist.removeFooterView(footers);
				}
			}else{
				pl01_list_goodlist.removeFooterView(footers);
				PromptManager.showMyToast(context, "商品下架了");
			}
			isLoading = false;

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
		protected List<Goods> doInBackground(Integer... pages) {

			String grno = ConstantValue.grouptype3list_goodno.trim();
			try {
				StorePLParam plp = new StorePLParam();
				plp.setGroupNo3(grno);
				plp.setStoreNo("00");
				plp.setIndexOrder(pages[0] * 9);
				ArrayList<StorePLParam> plList = new ArrayList<StorePLParam>();
				plList.add(plp);
				Gson gson = new Gson();
				String json = gson.toJson(plList);
				String msg = "*AIS|RQ|PL01|00|" + json + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_pl01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_pl01, context, map);
				String result = NetUtil.post(vo);
				String[] temp = NetUtil.split(result, "|");
				if (temp[0].equalsIgnoreCase("*AIS") && temp[1].equals("RS")
						&& temp[2].equals("PL01") && temp[3].equals("01")) {
					ArrayList<Goods> ps = gson.fromJson(temp[4],
							new TypeToken<List<Goods>>() {
							}.getType());
					if (ps.size() > 0) {
						if (ps.size() < 9) {
							isEnd = true;
						}
						return ps;
					}
				}
			} catch (Exception e) {

				return null;
			}

			return null;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (isEnd) {
			pl01_list_goodlist.removeFooterView(footers);
			return;
		} else if (totalItemCount <= firstVisibleItem + visibleItemCount + 1
				&& isLoading == false) {
			isLoading = true;
			new GetPL01Asytask().execute(page);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position,
			long arg3) {

		if (view.getId() != R.id.foot_view) {
			ConstantValue.goodsinfo = goodsListAdapter.getGoodsPL01List().get(position);
			PromptManager.showMyToast(getApplicationContext(), "自选商品成功");
			finish();
		}
	}

}
