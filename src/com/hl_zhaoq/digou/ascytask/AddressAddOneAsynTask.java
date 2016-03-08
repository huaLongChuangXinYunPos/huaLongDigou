package com.hl_zhaoq.digou.ascytask;

import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.bean.UserAddress;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ListView;

public class AddressAddOneAsynTask extends AsyncTask<Object, Void, Boolean> {
	private Context context;
	public EditText dz_add_et_addr;
	public EditText dz_add_et_mobile;
	public EditText dz_add_et_receiver_name;

	public AddressAddOneAsynTask(Context context,
			EditText dz_add_et_receiver_name, EditText dz_add_et_addr,
			EditText dz_add_et_mobile) {
		this.context = context;
		this.dz_add_et_receiver_name = dz_add_et_receiver_name;
		this.dz_add_et_mobile = dz_add_et_mobile;
		this.dz_add_et_addr = dz_add_et_addr;
	}

	@Override
	protected Boolean doInBackground(Object... uaddress) {
		// TODO Auto-generated method stub
		UserAddress useraddress = (UserAddress) uaddress[0];
		try {
			Gson gson2 = new Gson();
			String jsonStr = gson2.toJson(useraddress);
			String msg = "*AIS|RQ|AD01|02|[" + jsonStr + "]|AIE#";// chaxunfanhuikongbukong
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
					&& "AD01".equals(temp[2]) && "03".equals(temp[3])) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		PromptManager.closeSpotsDialog();
		if (result) {
			this.dz_add_et_receiver_name.setText("");
			this.dz_add_et_mobile.setText("");
			this.dz_add_et_addr.setText("");
			PromptManager.showMyToast(context, "添加成功");
		} else {
			PromptManager.showMyToast(context, "添加失败");
		}
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		PromptManager.showSpotsDialog(context);

	}

}
