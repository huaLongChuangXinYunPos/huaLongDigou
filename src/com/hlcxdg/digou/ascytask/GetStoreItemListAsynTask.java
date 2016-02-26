package com.hlcxdg.digou.ascytask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.adapter.StoreItemAdapter;
import com.hlcxdg.digou.bean.StoreItem;
import com.hlcxdg.digou.bean.StoreListParam;
import com.hlcxdg.digou.constant.ConstantLocation;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

public class GetStoreItemListAsynTask extends
		AsyncTask<String, ProgressBar, List<StoreItem>> {
	private Context context;
	private int page;
	private boolean isLoading;
	private boolean isEnd;
	private StoreItemAdapter storeItemAdapter;

	public GetStoreItemListAsynTask(Context contexts, int page,
			boolean isLoading, boolean isEnd, StoreItemAdapter storeItemAdapter) {
		this.context = contexts;
		this.page = page;
		this.isEnd = isEnd;
		this.isLoading = isLoading;
		this.storeItemAdapter = storeItemAdapter;
	}

	@Override
	protected void onPostExecute(List<StoreItem> result) {
		super.onPostExecute(result);

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
			PromptManager.showLogTest("PdAsynTaskdoInBackground-postmesssage",
					msg);
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