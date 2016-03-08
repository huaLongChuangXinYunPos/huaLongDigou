package com.hl_zhaoq.digou.ascytask;

import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.adapter.AddressAdapter;
import com.hl_zhaoq.digou.bean.UserAddress;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;

public class AddressAllGetAsynTask extends
		AsyncTask<String, Void, List<UserAddress>> {
	private Context context;
	private ListView lv_alladdr;
	private AddressAdapter addressAdapter;
	public AddressAllGetAsynTask(Context context, ListView lv_alladdr, AddressAdapter addressAdapter) {
		this.context = context;
		this.lv_alladdr = lv_alladdr;
		this.addressAdapter = addressAdapter;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		PromptManager.showSpotsDialog(context);
	}

	@Override
	protected void onPostExecute(List<UserAddress> result) {
		super.onPostExecute(result);
		PromptManager.closeSpotsDialog();
		if (result==null) {
			PromptManager.showMyToast(context, "无收货地址");
		}else {
			this.addressAdapter.setData(result);
			this.addressAdapter.notifyDataSetChanged();
		}

	}

	@Override
	protected List<UserAddress> doInBackground(String... userNo) {
		Boolean network = NetUtil.hasNetwork(context);
		if (!network) {
			return null;
		}
		String userNoTmp = userNo[0];
		String msg = "*AIS|RQ|AD01|00|" + userNoTmp + "|AIE#";
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("type", "post");
		map.put("url", context.getString(R.string.url_ad01));
		map.put("dataType", "text");
		map.put("data", msg);
		RequestVo vo = new RequestVo(R.string.url_ad01, context, map);

		String result = NetUtil.post(vo);
		String[] temp = NetUtil.split(result, "|");
		if (temp == null) {
			PromptManager.showLogTest("enginenet", "net-result-null");
			return null;
		} else

		if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
				&& "AD01".equals(temp[2]) && "01".equals(temp[3])) {
			Gson gson = new Gson();
			List<UserAddress> useraddrlist = gson.fromJson(temp[4],
					new TypeToken<List<UserAddress>>() {
					}.getType());
			return useraddrlist;
		}
		return null;

	}

}
