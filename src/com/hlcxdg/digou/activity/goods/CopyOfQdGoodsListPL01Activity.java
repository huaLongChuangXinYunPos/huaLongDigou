package com.hlcxdg.digou.activity.goods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.Goods;
import com.hlcxdg.digou.bean.StorePLParam;
import com.hlcxdg.digou.constant.ConstantIP;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.utils.swiperefreshlayoutdemo.RefreshLayout;
import com.hlcxdg.digou.utils.swiperefreshlayoutdemo.RefreshLayout.OnLoadListener;
import com.hlcxdg.digou.vo.RequestVo;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CopyOfQdGoodsListPL01Activity extends Activity implements OnClickListener,
		OnRefreshListener, OnLoadListener {
	public ListView pl01_list_goodlist;
	private RefreshLayout swipeLayout;
	public ImageView iv_back_pl01;
	public ImageView cart_pl01_iv;
	private StorePLParam plp;
	int page = 0;
	boolean flagDaodi = false;
	GoodsListPl01Adapter goodsListAdapter;

	@Override
	public void onRefresh() {
		swipeLayout.postDelayed(new Runnable() {

			@Override
			public void run() {
				swipeLayout.setRefreshing(false);
			}
		}, 1000);
	}

	@Override
	public void onLoad() {
		swipeLayout.postDelayed(new Runnable() {

			@Override
			public void run() {
				if (!flagDaodi) {
					plp.setIndexOrder(++page * 9);
					new GetPL01Asytask().execute(CopyOfQdGoodsListPL01Activity.this);
				}
				swipeLayout.setLoading(false);
			}
		}, 1000);
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		// 回退按钮
		case R.id.iv_back_pl01:
			CopyOfQdGoodsListPL01Activity.this.finish();
			break;
		case R.id.cart_pl01_iv:
			CopyOfQdGoodsListPL01Activity.this.finish();
			break;

		default:
			break;
		}
	}

	public void initView() {
		cart_pl01_iv = (ImageView) findViewById(R.id.cart_pl01_iv);
		cart_pl01_iv.setOnClickListener(this);
		iv_back_pl01 = (ImageView) findViewById(R.id.iv_back_pl01);
		iv_back_pl01.setOnClickListener(this);
		pl01_list_goodlist = (ListView) findViewById(R.id.pl01_list_goodlist);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setOnLoadListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.pl01goodlist);
		initView();
		goodsListAdapter = new GoodsListPl01Adapter();
		pl01_list_goodlist.setAdapter(goodsListAdapter);
		ConstantValue.goodData.clear();
		plp = new StorePLParam();
		new GetPL01Asytask().execute(CopyOfQdGoodsListPL01Activity.this);

		pl01_list_goodlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				ConstantValue.goodsinfo = ConstantValue.goodData.get(position);
				PromptManager.showMyToast(getApplicationContext(), "自选商品成功");
				finish();
			}
		});

	}

	public class GoodsListPl01Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ConstantValue.goodData.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}
		class ViewHolder {
			int position;
			SmartImageView goodicon;
			TextView goodsname;
		}
		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			View view = null;
			ViewHolder holder = null;
			if (convertView == null) {
				view = View.inflate(getApplicationContext(),
						R.layout.goodlist_item, null);
				holder = new ViewHolder();
				holder.position = position;
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}
			
			
			holder.goodicon = (SmartImageView) view.findViewById(R.id.goodicon);
			holder.goodicon.setImageUrl(ConstantIP.ImageviewURL
					+ ConstantValue.goodData.get(position).getMinIMG0());

			holder.goodsname = (TextView) view.findViewById(R.id.goods_name);

			holder.goodsname.setText(ConstantValue.goodData.get(position)
					.getcGoodsName());

			return view;
		}
	}
	public void reflashListview() {
		goodsListAdapter.notifyDataSetChanged();
	}
	public class GetPL01Asytask extends
			AsyncTask<Context, ProgressBar, Boolean> {
		Context context;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
				flagDaodi = true;
			} else if (ConstantValue.goodData != null
					&& ConstantValue.goodData.size() > 0) {
				reflashListview();
				
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

			final String grno = ConstantValue.grouptype3list_goodno.trim();

			if (ConstantValue.grouptype3list_goodno == null) {
				return false;
			}
			try {

				plp.setGroupNo3(grno);
				plp.setStoreNo("00");
				ArrayList<StorePLParam> plList = new ArrayList<StorePLParam>();
				plList.add(plp);
				Gson gson = new Gson();
				String json = gson.toJson(plList);
				String msg = "*AIS|RQ|PL01|00|" + json + "|AIE#";
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

				if (temp[0].equalsIgnoreCase("*AIS") && temp[1].equals("RS")
						&& temp[2].equals("PL01") && temp[3].equals("01")) {
					Log.i("json", temp[3]);
					Goods gt1 = new Goods();
					ArrayList<Goods> ps = gson.fromJson(temp[4],
							new TypeToken<List<Goods>>() {
							}.getType());
					if (ps.size() > 0) {
						if (ps.size() < 9) {
							flagDaodi = true;
						}
						ConstantValue.goodData.addAll(ps);
						return true;
					}
				}
			} catch (Exception e) {

				return false;
			}
			return false;
		}
	}

}
