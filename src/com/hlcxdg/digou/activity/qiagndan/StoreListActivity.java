package com.hlcxdg.digou.activity.qiagndan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.goods.GoodsListPL01Activity.GetPL01Asytask;
import com.hlcxdg.digou.bean.StoreItem;
import com.hlcxdg.digou.bean.StoreListParam;
import com.hlcxdg.digou.bean.StoreType;
import com.hlcxdg.digou.constant.ConstantLocation;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

public class StoreListActivity extends Activity implements OnClickListener,
		OnScrollListener, OnItemClickListener {
	private StoreTypeAdapter storeTypeAdapter;
	private StoreItemAdapter storeItemAdapter;
	public ImageView iv_back_store;
	public ListView storetypelistlv;
	public ListView storelistlv;
	private boolean isLoading = false;
	public int lastposition = 0; // storetype�������
	Context context;
	View footers;
	int page = 0;
	boolean isEnd = false;
	public String stTypeNo = "";

	private void init() {
		context = StoreListActivity.this;
		iv_back_store = (ImageView) findViewById(R.id.iv_back_store);
		iv_back_store.setOnClickListener(this);
		storeTypeAdapter = new StoreTypeAdapter(null, 0, context);
		storetypelistlv = (ListView) findViewById(R.id.storetype_list_sast);
		storetypelistlv.setAdapter(storeTypeAdapter);
		storetypelistlv.setOnItemClickListener(this);
		storeItemAdapter = new StoreItemAdapter(null, context);
		storelistlv = (ListView) findViewById(R.id.store_list_sast);
		footers = View.inflate(this, R.layout.foot, null);
		storelistlv.addFooterView(footers);
		storelistlv.setAdapter(storeItemAdapter);
		storelistlv.setOnScrollListener(this);
		storelistlv.setOnItemClickListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.storeandstoretype_list);
		init();
		isLoading = false;
		if (!isLoading) {
			isLoading = true;
			new GetStoreTypeAndListAsny().execute(page);
		}

	}

	public class GetStoreItemListAsynTask extends
			AsyncTask<String, ProgressBar, List<StoreItem>> {
		private Context context;

		public GetStoreItemListAsynTask(Context contexts) {
			this.context = contexts;

		}

		@Override
		protected void onPostExecute(List<StoreItem> result) {
			super.onPostExecute(result);

			if (result != null && result.size() > 0) {
				storeItemAdapter.addStoreItemList(result);
				storeItemAdapter.notifyDataSetChanged();
			}

			isLoading = false;
			PromptManager.closeSpotsDialog();
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
		protected List<StoreItem> doInBackground(String[] cont) {
			StoreListParam storeLp = new StoreListParam();
			try {
				if (ConstantLocation.myCityString != null
						&& ConstantLocation.myLatitudeString != 0
						&& ConstantLocation.myLongitudeString != 0) {
					storeLp.setArea(ConstantLocation.myCityString);
					storeLp.setIndexNo(page * 9);
					storeLp.setLatitude(ConstantLocation.myLatitudeString);
					storeLp.setLongitude(ConstantLocation.myLongitudeString);
					storeLp.setcStoreTypeNo(cont[0]);
				}
				Boolean network = NetUtil.hasNetwork(context);
				if (!network) {
					return null;
				}
				List<StoreListParam> stList = new ArrayList<StoreListParam>();
				stList.add(storeLp);
				Gson gson = new Gson();
				String jsonStr = gson.toJson(stList);
				String msg = "*AIS|RQ|ST01|00|" + jsonStr + "|AIE#";
				PromptManager.showLogTest(
						"PdAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_st01)); // �����
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_st01, context, map);
				String result = NetUtil.post(vo);
				// String[] temp = NetUtil.split(result, "|");Э�������⡣ֻ��json
				if (result == null || "]\r\n".equals(result)) {
					return null;
				} else {
					ArrayList<StoreItem> tmpStlist = gson.fromJson(result,
							new TypeToken<List<StoreItem>>() {
							}.getType());
					if (tmpStlist.size() > 0) {
						if (tmpStlist.size() < 9) {
							isEnd = true;
						}
						return tmpStlist;
					}

					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}
	}

	private class GetStoreTypeAndListAsny extends
			AsyncTask<Integer, ProgressBar, List<StoreType>> {

		@Override
		protected void onPostExecute(List<StoreType> result) {
			super.onPostExecute(result);

			if (result != null && result.size() > 0) {
				storeTypeAdapter.addStoreTypeList(result);
				storeTypeAdapter.notifyDataSetChanged();
				stTypeNo = storeTypeAdapter.getStoreTypeList().get(0)
						.getcStoreTypeNo();
				new GetStoreItemListAsynTask(context).execute(stTypeNo);
			} else {
				PromptManager.showMyToast(context, "商品下架了");
			}
			PromptManager.closeSpotsDialog();
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
		protected List<StoreType> doInBackground(Integer[] cont) {
			try {
				Boolean network = NetUtil.hasNetwork(context);
				if (!network) {
					return null;
				}
				Gson gson = new Gson();
				String msg = "*AIS|RQ|STTY|00|00|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_stty)); // �����
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_stty, context, map);
				String result = NetUtil.post(vo);
				if (result == null || "]\r\n".equals(result)) {
					return null;
				} else {
					String[] temp = NetUtil.split(result, "|");
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "STTY".equals(temp[2])
							&& "01".equals(temp[3])) {
						ArrayList<StoreType> storetypelisttmp = gson.fromJson(
								temp[4], new TypeToken<List<StoreType>>() {
								}.getType());
						return storetypelisttmp;
					}
					return null;
				}
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	@Override
	public void onItemClick(AdapterView<?> p, View view, int position, long arg3) {
		System.out.println("");
		switch (p.getId()) {
		case R.id.storetype_list_sast:
			if (!isLoading) {
				PromptManager.showSpotsDialog(context);
				isLoading = true;
				isEnd = false;
				page = 0;
				storeTypeAdapter.setLastposition(position);
				storeTypeAdapter.notifyDataSetChanged();
				storeItemAdapter.getStoreItemList().clear();
				new GetStoreItemListAsynTask(context).execute(storeTypeAdapter
						.getStoreTypeList().get(position).getcStoreTypeNo());
			}
			break;
		case R.id.store_list_sast:
			ConstantValue.storeItem = storeItemAdapter.getStoreItemList().get(
					position);
			finish();
			break;
		default:
			break;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (isEnd) {
//			footers.setVisibility(View.GONE);
			storelistlv.removeFooterView(footers);
			return;
		} else if (totalItemCount <= firstVisibleItem + visibleItemCount + 1
				&& isLoading == false) {
			isLoading = true;
			page++;
			new GetStoreItemListAsynTask(context).execute(stTypeNo);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 回退按钮
		case R.id.iv_back_store:
			finish();
			break;

		default:
			break;
		}
	}
}
