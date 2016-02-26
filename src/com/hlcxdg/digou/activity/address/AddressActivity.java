package com.hlcxdg.digou.activity.address;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.bean.UserAddress;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

public class AddressActivity extends Activity implements OnClickListener {

	LinearLayout ll_list;
	LinearLayout ll_add;
	ListView lv_addr;
	public Button btn_save;
	public Button btn_addoneAddress;
	public EditText et_receiver_name;
	public EditText et_mobile;
	public EditText et_addr;
	public boolean istoAdd = false;

	static UserAddress userAddress;
	UserAddress uAddress;
	List<UserAddress> useraddrlist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dizhiadd);
		new AddrAsynTask().execute(AddressActivity.this);
		initView();
		userAddress = new UserAddress();
	}

	ImageView iv_morenaddr;
	String flag = "0";

	public void morendizhi(View v) {
		if (flag.equals("0")) {
			iv_morenaddr.setBackgroundResource(R.drawable.collection_check_sel);
			flag = "1";

		} else {
			iv_morenaddr.setBackgroundResource(R.drawable.collection_check_nor);
			flag = "0";
		}
	}

	public void saveAddr(View v) {

		String user_name = et_receiver_name.getText().toString().trim();
		String addr_name = et_addr.getText().toString().trim();
		String tel = et_mobile.getText().toString().trim();

		userAddress.setAddUser_Name(user_name);
		userAddress.setAddress_Name(addr_name);
		userAddress.setTel(tel);
		userAddress.setAddress_Status(flag);
		userAddress.setUserNo(ConstantValue.userinfo.getUserNo());
		new AddAddress().execute(AddressActivity.this);

	}
	private void backtoListAddr() {
		btn_addoneAddress.setText("新增地址");
		ll_list.setVisibility(View.VISIBLE);
		ll_add.setVisibility(View.GONE);
	}

	public void addOneAddr() {
		btn_addoneAddress.setText("取消添加");
		ll_list.setVisibility(View.GONE);
		ll_add.setVisibility(View.VISIBLE);

	}

	public void back_iv(View v) {
		AddressActivity.this.finish();
	}

	public void initView() {
		iv_morenaddr = (ImageView) findViewById(R.id.iv_morenaddr);
		et_receiver_name = (EditText) findViewById(R.id.et_receiver_name);
		et_mobile = (EditText) findViewById(R.id.et_mobile);
		et_addr = (EditText) findViewById(R.id.et_addr);
		ll_list = (LinearLayout) findViewById(R.id.ll_addrlist);
		ll_add = (LinearLayout) findViewById(R.id.ll_add);
		lv_addr = (ListView) findViewById(R.id.lv_addr);
		btn_save = (Button) findViewById(R.id.btn_save);
		btn_addoneAddress = (Button) findViewById(R.id.btn_addoneAddress);
		btn_addoneAddress.setOnClickListener(this);
	}

	public class AddAddress extends AsyncTask<Context, ProgressBar, Boolean> {
		Context context;
		ListView list;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (result) {
//				ConstantValue.myaddr = useraddrlist.get(0);
//				AddressActivity.this.finish();
				PromptManager.showMyToast(getApplicationContext(), "添加成功");
				
			} else {
				Toast.makeText(getApplicationContext(), "格式不正确，请重填", 0);
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(ProgressBar... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected Boolean doInBackground(Context[] arg0) {
			// TODO Auto-generated method stub
			this.context = arg0[0];
			try {
				Gson gson2 = new Gson();
				String jsonStr = gson2.toJson(AddressActivity.userAddress);
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

					useraddrlist = gson2.fromJson(temp[4],
							new TypeToken<List<UserAddress>>() {
							}.getType());
					return true;
				}
			} catch (Exception e) {
				return false;
			}
			return false;
		}
	}

	public class AddrAsynTask extends AsyncTask<Context, ProgressBar, Boolean> {
		Context context;
		ListView list;

		Socket s = null;// 声明Socket的引用
		DataOutputStream dout = null;// 输出流
		DataInputStream din = null;// 输入流

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (!result) {
				// 网络异常
				ll_list.setVisibility(View.GONE);
				ll_add.setVisibility(View.VISIBLE);
			} else if (ConstantValue.useraddrlist == null
					|| ConstantValue.useraddrlist.size() == 0) {
				ll_list.setVisibility(View.GONE);
				ll_add.setVisibility(View.VISIBLE);

			} else if (ConstantValue.useraddrlist != null
					|| ConstantValue.useraddrlist.size() > 0) {
				ll_list.setVisibility(View.VISIBLE);
				ll_add.setVisibility(View.GONE);
				lv_addr.setAdapter(new BaseAdapter() {

					@Override
					public View getView(int position, View cv, ViewGroup p) {
						View view;

						TextView user;
						TextView phone;
						TextView address_detail;
						ImageView add_triangle;
						view = View.inflate(context, R.layout.dizhi_item, null);
						user = (TextView) view.findViewById(R.id.user);
						phone = (TextView) view.findViewById(R.id.phone);
						phone.setText(ConstantValue.useraddrlist.get(position)
								.getTel());
						address_detail = (TextView) view
								.findViewById(R.id.address_detail);
						address_detail.setText(ConstantValue.useraddrlist.get(
								position).getAddress_Name());
						user.setText(ConstantValue.useraddrlist.get(position)
								.getAddUser_Name());
						add_triangle = (ImageView) view
								.findViewById(R.id.add_triangle);
						if (position == 0) {
							add_triangle.setVisibility(View.VISIBLE);
						}

						Log.i("sank", "创建新的view对象" + position);

						return view;
					}

					@Override
					public long getItemId(int arg0) {
						// TODO Auto-generated method stub
						return 0;
					}

					@Override
					public Object getItem(int arg0) {
						// TODO Auto-generated method stub
						return null;
					}

					@Override
					public int getCount() {
						// TODO Auto-generated method stub
						return ConstantValue.useraddrlist.size();
					}
				});
				lv_addr.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						ConstantValue.myaddr = ConstantValue.useraddrlist
								.get(position);
						// Intent intent;
						// intent = new Intent();
						// intent.setClass(AddressActivity.this,
						// SalesheetActivity.class);
						// startActivity(intent);
						AddressActivity.this.finish();
					}
				});
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
		}

		@Override
		protected void onProgressUpdate(ProgressBar... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
		}

		@Override
		protected Boolean doInBackground(Context[] arg0) {
			// TODO Auto-generated method stub
			this.context = arg0[0];
			try {
				Boolean network = NetUtil.hasNetwork(context);
				if (!network) {
					return false;
				}
				PromptManager.showLogTest("SalesheetActivity",
						"doInBackground-yuwang");
				// 变量替换
				// Log.i("jsons", jsons);
				String msg = "*AIS|RQ|AD01|00|"
						+ ConstantValue.userinfo.getUserNo() + "|AIE#";
				// String msg = "*AIS|RQ|OD01|"+plno+"|[" + jsons + "]|AIE#";

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
					List<UserAddress> useraddrlist = gson.fromJson(temp[4],
							new TypeToken<List<UserAddress>>() {
							}.getType());
					ConstantValue.useraddrlist = useraddrlist;
					return true;
				}
				//
			} catch (Exception e) {
				return false;
			}

			return false;
		}
	}

	@Override
	public void onClick(View views) {
		switch (views.getId()) {
		case R.id.btn_addoneAddress:
			if (!istoAdd) {
				istoAdd = true;
				addOneAddr();
			} else {
				istoAdd = false;
				backtoListAddr();
			}
			break;
		default:
			break;
		}
	}

	
}