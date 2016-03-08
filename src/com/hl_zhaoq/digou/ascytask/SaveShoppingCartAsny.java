package com.hl_zhaoq.digou.ascytask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.storeac.StoreTypeAdapter;
import com.hl_zhaoq.digou.adapter.StoreItemAdapter;
import com.hl_zhaoq.digou.bean.StoreType;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;

public class SaveShoppingCartAsny extends
		AsyncTask<Integer, ProgressBar, List<StoreType>> {
	private Context context;
	private int page;
	private boolean isLoading;
	private boolean isEnd;
	private StoreItemAdapter storeItemAdapter;
	private StoreTypeAdapter storeTypeAdapter;
	public String stTypeNo = "";

	public SaveShoppingCartAsny(Context context, int page, boolean isLoading,
			boolean isEnd, StoreItemAdapter storeItemAdapter,
			StoreTypeAdapter storeTypeAdapter, String stTypeNo) {
		super();
		this.context = context;
		this.page = page;
		this.isLoading = isLoading;
		this.isEnd = isEnd;
		this.storeItemAdapter = storeItemAdapter;
		this.storeTypeAdapter = storeTypeAdapter;
		this.stTypeNo = stTypeNo;
	}

	@Override
	protected void onPostExecute(List<StoreType> result) {
		super.onPostExecute(result);

		if (result != null && result.size() > 0) {
			storeTypeAdapter.addStoreTypeList(result);
			storeTypeAdapter.notifyDataSetChanged();
			stTypeNo = storeTypeAdapter.getStoreTypeList().get(0)
					.getcStoreTypeNo();
			new GetStoreItemListAsynTask(context, page, isLoading, isEnd,
					storeItemAdapter).execute(stTypeNo);
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
				if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
						&& "STTY".equals(temp[2]) && "01".equals(temp[3])) {
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
