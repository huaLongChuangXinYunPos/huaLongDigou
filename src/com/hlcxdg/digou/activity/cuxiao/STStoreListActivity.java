package com.hlcxdg.digou.activity.cuxiao;

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
import com.hlcxdg.digou.activity.storeac.StoreSingleFenleiActivity;
import com.hlcxdg.digou.adapter.STStoreItemAdapter;
import com.hlcxdg.digou.adapter.StoreItemAdapter;
import com.hlcxdg.digou.bean.StoreItem;
import com.hlcxdg.digou.bean.StoreListParam;
import com.hlcxdg.digou.bean.StoreType;
import com.hlcxdg.digou.constant.ConstantLocation;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

public class STStoreListActivity extends Activity implements OnClickListener,
		OnScrollListener, OnItemClickListener {
	private StoreTypeAdapter storeTypeAdapter;
	private STStoreItemAdapter storeItemAdapter;
	public ImageView iv_back_store;
	public ListView storetypelistlv;
	public ListView storelistlv;
	private boolean isLoading = false;
	private boolean isFirst = true;
	public int lastposition = 0; // storetype�������
	Context context;
	View footers;
	int page = 0;
	int sttype = 0;
	boolean isEnd = false;
	public String stTypeNo = "";

	private void init() {
		context = STStoreListActivity.this;
		iv_back_store = (ImageView) findViewById(R.id.iv_back_storest);
		iv_back_store.setOnClickListener(this);
		storeTypeAdapter = new StoreTypeAdapter(null, 0, context);
		storetypelistlv = (ListView) findViewById(R.id.storetype_list_sastst);
		storetypelistlv.setAdapter(storeTypeAdapter);
		storetypelistlv.setOnItemClickListener(this);
		storeItemAdapter = new STStoreItemAdapter(null, context);
		storelistlv = (ListView) findViewById(R.id.store_list_sastst);
		footers = View.inflate(this, R.layout.foot, null);
		storelistlv.addFooterView(footers);
		storelistlv.setAdapter(storeItemAdapter);
		
		storelistlv.setOnItemClickListener(this);
		storelistlv.setOnScrollListener(this);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.storeandstoretype_list_st);
		sttype = 1;
		init();
		isLoading = false;
		if (!isLoading) {
			isLoading = true;
			new GetStoreTypeAndListAsny().execute(sttype);
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

			if (isEnd) {
				footers.setVisibility(View.GONE);
				storelistlv.removeFooterView(footers);
			}
			if (result != null && result.size() > 0) {
				storeItemAdapter.addStoreItemList(result);
				storeItemAdapter.notifyDataSetChanged();
			}
			page++;
			isLoading = false;
			PromptManager.closeSpotsDialog();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// PromptManager.showSpotsDialog(context);
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
					storeLp.SPActivity = "1";
					storeLp.cStoreNo = "00";
				}
				Boolean network = NetUtil.hasNetwork(context);
				if (!network) {
					return null;
				}
				List<StoreListParam> stList = new ArrayList<StoreListParam>();
				stList.add(storeLp);
				Gson gson = new Gson();
				String jsonStr = gson.toJson(stList);
				String msg = "*AIS|RQ|SPST01|00|" + jsonStr + "|AIE#";

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_spstorest)); //
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_spstorest,
						context, map);
				String result = NetUtil.post(vo);
				// *AIS|RS|SPST01|02|[{"cStoreNo":"10002","cStoreName":"华隆","cSheetAddr":"null","cAddr":"北京市海淀区上地五街国际创业园5E","cManager":"null","cTel":"null","fLont":"116.32","fLant":"40.042164","cArea":"11","cStoreTypeNo":"1002","cStoreTypeName":"名烟名酒","cCredit":"3 ","cLevel":"2 ","fDistance":1.4,"Activity":[{"cPloyNo":"20151217","cPloyTypeNo":"101","cPloyTypeName":"乐尚策略","bLimitQty":false,"dDateStart":"2015-12-15 00:00:00.0","cTimeStart":"0:0       ","dDateEnd":"2016-12-28 00:00:00.0","cTimeEnd":"0:0       ","RemainingTime":521852,"Days":362,"Hours":9,"minutes":32}]}]|AIE#

				/** AIS|RS|SPST01|00|null|AIE# */
				/*
				 * AIS|RS|SPST01|01|[{"cStoreNo":"10001","cStoreName":"乐尚",
				 * "cSheetAddr"
				 * :"null","cAddr":"北京市海淀区上地五街国际创业园5E","cManager":"null"
				 * ,"cTel":"null"
				 * ,"fLont":"116.321304","fLant":"40.041164","cArea"
				 * :"11","cStoreTypeNo"
				 * :"1001","cStoreTypeName":"便利店","cCredit":"3 "
				 * ,"cLevel":"1 ","fDistance"
				 * :1.5,"Activity":[{"cPloyNo":"20151217"
				 * ,"cPloyTypeNo":"101","cPloyTypeName"
				 * :"乐尚策略","bLimitQty":false,
				 * "dDateStart":"2015-12-15 00:00:00.0"
				 * ,"cTimeStart":"0:0       "
				 * ,"dDateEnd":"2016-12-28 00:00:00.0",
				 * "cTimeEnd":"0:0       ","RemainingTime"
				 * :523062,"Days":363,"Hours"
				 * :5,"minutes":42},{"cPloyNo":"20151229"
				 * ,"cPloyTypeNo":"104","cPloyTypeName"
				 * :"乐尚策略1","bLimitQty":true,
				 * "fLimitQty":0.0000,"dDateStart":"2015-12-29 00:00:00.0"
				 * ,"cTimeStart"
				 * :"00:00:00  ","dDateEnd":"2015-12-31 00:00:00.0",
				 * "cTimeEnd":"00:00:00  "
				 * ,"RemainingTime":342,"Days":0,"Hours":5
				 * ,"minutes":42}]},{"cStoreNo"
				 * :"10014","cStoreName":"好又多","cSheetAddr"
				 * :"null","cAddr":"北京市海淀区上地五街国际创业园6E"
				 * ,"cManager":"null","cTel":"null"
				 * ,"fLont":"116.12321","fLant":"40.042164"
				 * ,"cArea":"11","cStoreTypeNo"
				 * :"1001","cStoreTypeName":"便利店","cCredit"
				 * :"3 ","cLevel":"3 ","fDistance"
				 * :16.1,"Activity":[{"cPloyNo":"20151217"
				 * ,"cPloyTypeNo":"103","cPloyTypeName"
				 * :"好又多策略","bLimitQty":false
				 * ,"dDateStart":"2015-12-15 00:00:00.0"
				 * ,"cTimeStart":"0:0       "
				 * ,"dDateEnd":"2016-12-28 00:00:00.0",
				 * "cTimeEnd":"0:0       ","RemainingTime"
				 * :523062,"Days":363,""Hours:5,"minutes":42}]}]|AIE#
				 */
				// *AIS|RS|SPST01||AIE#
				// *AIS|RS|SPST01|00|ERR|AIE#
				// String[] temp = NetUtil.split(result, "|");

				if (result == null || "]\r\n".equals(result)) {
					return null;
				} else {
					String[] temp = NetUtil.split(result, "|");
					// && "01".equals(temp[3])
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "SPST01".equals(temp[2])) {
						List<StoreItem> storetypelisttmp = gson.fromJson(
								temp[4], new TypeToken<List<StoreItem>>() {
								}.getType());
						if (storetypelisttmp.size() > 0) {
							if (storetypelisttmp.size() < 9) {
								isEnd = true;
							}
							return storetypelisttmp;
						}

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
		protected List<StoreType> doInBackground(Integer[] constant) {
			try {
				Boolean network = NetUtil.hasNetwork(context);
				if (!network) {
					return null;
				}
				Gson gson = new Gson();
				String msg = "*AIS|RQ|SPSTTY|00|" + constant[0] + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.servlet_spstoretype)); // �����
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.servlet_spstoretype,
						context, map);
				String result = NetUtil.post(vo);
				// *AIS|RS|SPSTTY|01|[]|AIE#-----后面跟0时
				// -----后面跟1时---------*AIS|RS|SPSTTY|01|[{"cStoreTypeNo":"1001","cStoreTypeName":"便利店"},{"cStoreTypeNo":"1002","cStoreTypeName":"名烟名酒"}]|AIE#
				// 114*AIS|RS|SPSTTY|01|[{"cStoreTypeNo":"1001","cStoreTypeName":"便利店"},{"cStoreTypeNo":"1002","cStoreTypeName":"名烟名酒"}]|AIE#
				// 153*AIS|RS|SPSTTY|00|[]|AIE#

				if (result == null || "]\r\n".equals(result)) {
					return null;
				} else {
					String[] temp = NetUtil.split(result, "|");
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "SPSTTY".equals(temp[2])
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
		System.out.println("onItemClick");
		switch (p.getId()) {
		case R.id.storetype_list_sastst:
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
		case R.id.store_list_sastst:
			ConstantValue.storeItem = storeItemAdapter.getStoreItemList().get(
					position);
			Intent intent;
			intent = new Intent();
			intent.setClass(STStoreListActivity.this,
					STStoreSingleFenleiActivity.class);
			startActivity(intent);
			break;
		default:
			break;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		if (isEnd) {
			footers.setVisibility(View.GONE);
			storelistlv.removeFooterView(footers);
			return;
		} else if (totalItemCount <= firstVisibleItem + visibleItemCount + 1
				&& isLoading == false && !isFirst) {
			isLoading = true;
			new GetStoreItemListAsynTask(context).execute(stTypeNo);
		}
		isFirst = false;
	}

	@Override
	public void onScrollStateChanged(AbsListView arg0, int arg1) {

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		// 回退按钮
		case R.id.iv_back_storest:
			finish();
			break;

		default:
			break;
		}
	}
}
