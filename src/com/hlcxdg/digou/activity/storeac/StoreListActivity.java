package com.hlcxdg.digou.activity.storeac;

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
import com.hlcxdg.digou.adapter.StoreItemAdapter;
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
	private boolean isFirst = true;
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
				PromptManager.showLogTest(
						"PdAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_st01)); // �����
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_st01, context, map);
				String result = NetUtil.post(vo);
				// [{"cStoreNo":"10099","cStoreName":"测试","cCredit":"05","cLevel":"1 ","fDistance":0.2,"logPic":"null","cAddr":"海淀区上地七街","Pinpai":[],"OverCut":"null","FirstSheet":"null","GiftFree":"null","Over_Money":null,"Cut_Money":null,"FirstSheet_Over":null,"FirstSheet_Cut":null,"GiftFree_Over":null,"GiftFree_GiftGoodsNo":"null","PeisongFee_Over_Free":null,"PeisongFee":null,"cSameMonthSales":0,"cSameYearSales":0},{"cStoreNo":"10001","cStoreName":"乐尚","cCredit":"05","cLevel":"1 ","fDistance":1.5,"logPic":"null","cAddr":"北京市海淀区上地五街国际创业园5E","Pinpai":[{"cStoreNo":"10001","O_cPinpaiNo":"110001","O_cPinpaiName":"卡夫","cPinpaiGrantNo":"101011010","cPinpaiGrantPic":"/pinpaigrantpic/1001.jpg"},{"cStoreNo":"10001","O_cPinpaiNo":"110011","O_cPinpaiName":"光明","cPinpaiGrantNo":"3423414543","cPinpaiGrantPic":"/pinpaigrantpic/1011.jpg"},{"cStoreNo":"10001","O_cPinpaiNo":"110012","O_cPinpaiName":"子母","cPinpaiGrantNo":"2314353455","cPinpaiGrantPic":"/pinpaigrantpic/1012.jpg"}],"OverCut":"满减","FirstSheet":"","GiftFree":"","Over_Money":100.0000,"Cut_Money":10.0000,"FirstSheet_Over":0.0000,"FirstSheet_Cut":0.0000,"GiftFree_Over":0.0000,"GiftFree_GiftGoodsNo":"","PeisongFee_Over_Free":200.0000,"PeisongFee":10.0000,"cSameMonthSales":0,"cSameYearSales":0},{"cStoreNo":"10014","cStoreName":"好又多","cCredit":"05","cLevel":"3 ","fDistance":16.2,"logPic":"null","cAddr":"北京市海淀区上地五街国际创业园6E","Pinpai":[{"cStoreNo":"10014","O_cPinpaiNo":"110061","O_cPinpaiName":"文体","cPinpaiGrantNo":"938239100","cPinpaiGrantPic":"/pinpaigrantpic/110061.jpg"},{"cStoreNo":"10014","O_cPinpaiNo":"110062","O_cPinpaiName":"喜力","cPinpaiGrantNo":"77777777","cPinpaiGrantPic":"/pinpaigrantpic/110062.jpg"},{"cStoreNo":"10014","O_cPinpaiNo":"110063","O_cPinpaiName":"珠江","cPinpaiGrantNo":"88888888","cPinpaiGrantPic":"/pinpaigrantpic/110063.jpg"}],"OverCut":"null","FirstSheet":"null","GiftFree":"null","Over_Money":null,"Cut_Money":null,"FirstSheet_Over":null,"FirstSheet_Cut":null,"GiftFree_Over":null,"GiftFree_GiftGoodsNo":"null","PeisongFee_Over_Free":null,"PeisongFee":null,"cSameMonthSales":0,"cSameYearSales":0},{"cStoreNo":"10029","cStoreName":"VIVO手机专卖店","cCredit":"05","cLevel":"1 ","fDistance":113.1,"logPic":"null","cAddr":"北京市海淀区上地五街国际创业园2E","Pinpai":[{"cStoreNo":"10029","O_cPinpaiNo":"110007","O_cPinpaiName":"哇哈哈","cPinpaiGrantNo":"923819000","cPinpaiGrantPic":"/pinpaigrantpic/1007.jpg"},{"cStoreNo":"10029","O_cPinpaiNo":"110027","O_cPinpaiName":"娃哈哈","cPinpaiGrantNo":"626246262","cPinpaiGrantPic":"/pinpaigrantpic/1027.jpg"},{"cStoreNo":"10029","O_cPinpaiNo":"110028","O_cPinpaiName":"乐百氏","cPinpaiGrantNo":"26652546","cPinpaiGrantPic":"/pinpaigrantpic/1028.jpg"},{"cStoreNo":"10029","O_cPinpaiNo":"110095","O_cPinpaiName":"VIVO","cPinpaiGrantNo":"729239890","cPinpaiGrantPic":"/pinpaigrantpic/110095.jpg"}],"OverCut":"null","FirstSheet":"null","GiftFree":"null","Over_Money":null,"Cut_Money":null,"FirstSheet_Over":null,"FirstSheet_Cut":null,"GiftFree_Over":null,"GiftFree_GiftGoodsNo":"null","PeisongFee_Over_Free":null,"PeisongFee":null,"cSameMonthSales":0,"cSameYearSales":0},{"cStoreNo":"10034","cStoreName":"张师傅饭店","cCredit":"05","cLevel":"3 ","fDistance":133.5,"logPic":"null","cAddr":"北京市海淀区上地五街国际创业园3E","Pinpai":[{"cStoreNo":"10034","O_cPinpaiNo":"110001","O_cPinpaiName":"卡夫","cPinpaiGrantNo":"101011010","cPinpaiGrantPic":"/pinpaigrantpic/1001.jpg"},{"cStoreNo":"10034","O_cPinpaiNo":"110037","O_cPinpaiName":"喜力","cPinpaiGrantNo":"77777777","cPinpaiGrantPic":"/pinpaigrantpic/1037.jpg"},{"cStoreNo":"10034","O_cPinpaiNo":"110038","O_cPinpaiName":"珠江","cPinpaiGrantNo":"88888888","cPinpaiGrantPic":"/pinpaigrantpic/1038.jpg"}],"OverCut":"null","FirstSheet":"null","GiftFree":"null","Over_Money":null,"Cut_Money":null,"FirstSheet_Over":null,"FirstSheet_Cut":null,"GiftFree_Over":null,"GiftFree_GiftGoodsNo":"null","PeisongFee_Over_Free":null,"PeisongFee":null,"cSameMonthSales":0,"cSameYearSales":0},{"cStoreNo":"10035","cStoreName":"华帝厨卫","cCredit":"05","cLevel":"2 ","fDistance":136.0,"logPic":"null","cAddr":"北京市海淀区上地五街国际创业园3E","Pinpai":[{"cStoreNo":"10035","O_cPinpaiNo":"110001","O_cPinpaiName":"卡夫","cPinpaiGrantNo":"101011010","cPinpaiGrantPic":"/pinpaigrantpic/1001.jpg"},{"cStoreNo":"10035","O_cPinpaiNo":"110002","O_cPinpaiName":"蓝月亮","cPinpaiGrantNo":"202010200","cPinpaiGrantPic":"/pinpaigrantpic/1002.jpg"},{"cStoreNo":"10035","O_cPinpaiNo":"110039","O_cPinpaiName":"蓝带","cPinpaiGrantNo":"99999999","cPinpaiGrantPic":"/pinpaigrantpic/1039.jpg"},{"cStoreNo":"10035","O_cPinpaiNo":"110040","O_cPinpaiName":"蓝妹","cPinpaiGrantNo":"0","cPinpaiGrantPic":"/pinpaigrantpic/1040.jpg"}],"OverCut":"null","FirstSheet":"null","GiftFree":"null","Over_Money":null,"Cut_Money":null,"FirstSheet_Over":null,"FirstSheet_Cut":null,"GiftFree_Over":null,"GiftFree_GiftGoodsNo":"null","PeisongFee_Over_Free":null,"PeisongFee":null,"cSameMonthSales":0,"cSameYearSales":0},{"cStoreNo":"10039","cStoreName":"博洋家纺","cCredit":"05","cLevel":"1 ","fDistance":137.4,"logPic":"null","cAddr":"北京市海淀区上地五街国际创业园3E","Pinpai":[{"cStoreNo":"10039","O_cPinpaiNo":"110003","O_cPinpaiName":"单人间","cPinpaiGrantNo":"213923019","cPinpaiGrantPic":"/pinpaigrantpic/1003.jpg"},{"cStoreNo":"10039","O_cPinpaiNo":"110004","O_cPinpaiName":"立白","cPinpaiGrantNo":"938239100","cPinpaiGrantPic":"/pinpaigrantpic/1004.jpg"},{"cStoreNo":"10039","O_cPinpaiNo":"110051","O_cPinpaiName":"皇室","cPinpaiGrantNo":"43434343","cPinpaiGrantPic":"/pinpaigrantpic/1047.jpg"},{"cStoreNo":"10039","O_cPinpaiNo":"110052","O_cPinpaiName":"金禾","cPinpaiGrantNo":"45454545","cPinpaiGrantPic":"/pinpaigrantpic/1048.jpg"}],"OverCut":"null","FirstSheet":"null","GiftFree":"null","Over_Money":null,"Cut_Money":null,"FirstSheet_Over":null,"FirstSheet_Cut":null,"GiftFree_Over":null,"GiftFree_GiftGoodsNo":"null","PeisongFee_Over_Free":null,"PeisongFee":null,"cSameMonthSales":0,"cSameYearSales":0},{"cStoreNo":"10037","cStoreName":"东方家电","cCredit":"05","cLevel":"4 ","fDistance":138.4,"logPic":"null","cAddr":"北京市海淀区上地五街国际创业园3E","Pinpai":[{"cStoreNo":"10037","O_cPinpaiNo":"110002","O_cPinpaiName":"蓝月亮","cPinpaiGrantNo":"202010200","cPinpaiGrantPic":"/pinpaigrantpic/1002.jpg"},{"cStoreNo":"10037","O_cPinpaiNo":"110003","O_cPinpaiName":"单人间","cPinpaiGrantNo":"213923019","cPinpaiGrantPic":"/pinpaigrantpic/1003.jpg"},{"cStoreNo":"10037","O_cPinpaiNo":"110047","O_cPinpaiName":"多人间","cPinpaiGrantNo":"33322232","cPinpaiGrantPic":"/pinpaigrantpic/1043.jpg"},{"cStoreNo":"10037","O_cPinpaiNo":"110048","O_cPinpaiName":"陆羽","cPinpaiGrantNo":"23232323","cPinpaiGrantPic":"/pinpaigrantpic/1044.jpg"}],"OverCut":"null","FirstSheet":"null","GiftFree":"null","Over_Money":null,"Cut_Money":null,"FirstSheet_Over":null,"FirstSheet_Cut":null,"GiftFree_Over":null,"GiftFree_GiftGoodsNo":"null","PeisongFee_Over_Free":null,"PeisongFee":null,"cSameMonthSales":0,"cSameYearSales":0},{"cStoreNo":"10006","cStoreName":"华源百货","cCredit":"05","cLevel":"4 ","fDistance":139.1,"logPic":"null","cAddr":"北京市海淀区上地五街国际创业园5E","Pinpai":[{"cStoreNo":"10006","O_cPinpaiNo":"110006","O_cPinpaiName":"公牛","cPinpaiGrantNo":"729239890","cPinpaiGrantPic":"/pinpaigrantpic/1006.jpg"},{"cStoreNo":"10006","O_cPinpaiNo":"110021","O_cPinpaiName":"保利","cPinpaiGrantNo":"246462654625","cPinpaiGrantPic":"/pinpaigrantpic/1021.jpg"},{"cStoreNo":"10006","O_cPinpaiNo":"110022","O_cPinpaiName":"添美","cPinpaiGrantNo":"4265643456","cPinpaiGrantPic":"/pinpaigrantpic/1022.jpg"}],"OverCut":"null","FirstSheet":"null","GiftFree":"null","Over_Money":null,"Cut_Money":null,"FirstSheet_Over":null,"FirstSheet_Cut":null,"GiftFree_Over":null,"GiftFree_GiftGoodsNo":"null","PeisongFee_Over_Free":null,"PeisongFee":null,"cSameMonthSales":0,"cSameYearSales":0}]
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
			Intent intent;
			intent = new Intent();
			intent.setClass(StoreListActivity.this,
					StoreSingleFenleiActivity.class);
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
			// footers.setVisibility(View.GONE);
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
		case R.id.iv_back_store:
			finish();
			break;

		default:
			break;
		}
	}
}
