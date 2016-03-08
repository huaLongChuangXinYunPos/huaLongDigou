package com.hl_zhaoq.digou.activity.storeac;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.MGridView;
import com.hl_zhaoq.digou.activity.MainActivity;
import com.hl_zhaoq.digou.activity.search.SearchActivity;
import com.hl_zhaoq.digou.bean.GroupType1;
import com.hl_zhaoq.digou.bean.GroupType123List;
import com.hl_zhaoq.digou.bean.GroupType2;
import com.hl_zhaoq.digou.bean.GroupType3;
import com.hl_zhaoq.digou.bean.PC03paramSt;
import com.hl_zhaoq.digou.bean.PC03params;
import com.hl_zhaoq.digou.constant.ConstantIP;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;
import com.loopj.android.image.SmartImageView;
import com.zxing.activity.CaptureActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/*��Ҫ��Ϊ��ʽ
 * ���������б���Ϣ
 */
public class StoreSingleFenleiActivity extends Activity implements
		OnClickListener {
	EditText fenlei_search;
	ImageButton index_sousuo, i_search_stfenlei;

	public String stno = "";
	private ImageView backfenleistore;
	private ImageView index_saomiao;
	Context conts;
	private HorizontalScrollView hsView;
	private MGridView sanleilistview;
	public String yileiid;
	private int yileiposition = 0;
	private int erleiposition = 0;
	private int sanposition = 0;
	private String grNo1;
	private String grNo2;
	public List<GroupType1> yileiData;
	public List<GroupType2> erleiData;
	public List<GroupType3> saneiData;
	MyBaseAdapter1 mybase1 = new MyBaseAdapter1();
	MyBaseAdapter2 mybase2 = new MyBaseAdapter2();
	MyBaseAdapter3 mybase3 = new MyBaseAdapter3();
	private LinearLayout llPlayback;
	public GestureDetector detector;
	private long mTime;
	boolean visib = true;
	Context cont;
	ListView yileilistview;
	GridView erleilistview;
	RelativeLayout loading_bg;
	RelativeLayout loading_bg3;
	RelativeLayout fenlei_data;
	RelativeLayout tts;
	RelativeLayout st_fenlei_rl;
	TextView storenames_fenlei;
	TextView st_fenlei_getcomm;

	public class MyBaseAdapter1 extends BaseAdapter {

		@Override
		public int getCount() {
			return yileiData.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			View view;
			final TextView name;
			view = View.inflate(cont, R.layout.item_yilei, null);
			name = (TextView) view.findViewById(R.id.yileiname);
			if (position == yileiposition) {
				name.setTextColor(Color.RED);
				name.setBackgroundResource(R.drawable.left);
			}
			name.setText(yileiData.get(position).getGrName1());
			name.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					yileiposition = position;
					grNo1 = yileiData.get(position).getGroupNO1();
					new Fenlei1t2AsynTask().execute(cont);
					notifyDataSetChanged();

				}
			});
			return view;
		}
	}

	public class MyBaseAdapter3 extends BaseAdapter {

		@Override
		public int getCount() {
			return saneiData.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			View view;
			TextView name;
			SmartImageView img;
			view = View.inflate(cont, R.layout.item_sanlei1, null);
			name = (TextView) view.findViewById(R.id.item_name);
			name.setText(saneiData.get(position).getGrName3());
			img = (SmartImageView) view.findViewById(R.id.item_img);
			img.setImageUrl(ConstantIP.ImageviewURL
					+ saneiData.get(position).getcIMG());
			return view;
		}
	}

	public class MyBaseAdapter2 extends BaseAdapter {

		@Override
		public int getCount() {
			return erleiData.size();
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public View getView(final int position, View convertView, ViewGroup arg2) {
			View view;
			TextView name;
			view = View.inflate(cont, R.layout.item_erlei, null);
			TextView item_name = (TextView) view.findViewById(R.id.item_name);
			if (position == erleiposition) {
				item_name.setTextColor(Color.RED);
				item_name.setBackgroundResource(R.drawable.drawable_bottom);
			}
			item_name.setText(erleiData.get(position).getGrName2());

			item_name.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					erleiposition = position;
					grNo2 = erleiData.get(position).getGroupNO2();
					new Fenlei2t3AsynTask().execute(cont);
					notifyDataSetChanged();

				}
			});

			return view;
		}
	}

	public class Fenlei1t2AsynTask extends
			AsyncTask<Context, ProgressBar, Boolean> {
		Context context;
		ListView list;

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// PromptManager.closeProgressDialog();
			hsView.scrollTo(0, 0);
			if (!result) {
				mybase2.notifyDataSetChanged();
				mybase3.notifyDataSetChanged();
			} else {
				float scale = context.getResources().getDisplayMetrics().density;
				//
				int pxs = (int) (80 * scale + 0.5f);
				llPlayback.setLayoutParams(new FrameLayout.LayoutParams(pxs
						* erleiData.size(), LayoutParams.WRAP_CONTENT));
				erleilistview.setNumColumns(erleiData.size());
				// ConstantValue.erleilistview.smoothScrollToPosition(0);
				erleiposition = 0;
				mybase2.notifyDataSetChanged();
				mybase3.notifyDataSetChanged();
				loading_bg3.setVisibility(View.GONE);
				// tts.setVisibility(View.VISIBLE);
				sanleilistview.setVisibility(View.VISIBLE);
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			// tts.setVisibility(View.GONE);
			sanleilistview.setVisibility(View.GONE);
			loading_bg3.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(ProgressBar... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			// PromptManager.showProgressDialog(context);
		}

		SharedPreferences sp;

		@Override
		protected Boolean doInBackground(Context[] arg0) {
			// TODO Auto-generated method stub
			try {
				this.context = arg0[0];
				erleiData.clear();
				saneiData.clear();
				String stno = ConstantValue.storeItem.getcStoreNo();
				String msg = "*AIS|RQ|PC02|" + grNo1 + "|" + stno + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", conts.getString(R.string.url_pc02));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_pc02, conts, map);
				String result = NetUtil.post(vo);
				if (result == null) {
				} else {
					String[] temp = NetUtil.split(result, "|");
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "PC02".equals(temp[2])) {
						Gson gson = new Gson();
						grouptypealllist = gson.fromJson(temp[3],
								new TypeToken<List<GroupType123List>>() {
								}.getType());
						erleiData.addAll(grouptypealllist.get(0).getPC02());
						saneiData.addAll(grouptypealllist.get(0).getPC03());
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		}
	}

	public class Fenlei2t3AsynTask extends
			AsyncTask<Context, ProgressBar, Boolean> {
		Context context;
		ListView list;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
			} else {
				mybase3.notifyDataSetChanged();
				loading_bg3.setVisibility(View.GONE);
				sanleilistview.setVisibility(View.VISIBLE);
			}

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			sanleilistview.setVisibility(View.GONE);
			loading_bg3.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onProgressUpdate(ProgressBar... values) {
			super.onProgressUpdate(values);
		}

		SharedPreferences sp;

		@Override
		protected Boolean doInBackground(Context[] arg0) {
			try {
				this.context = arg0[0];
				saneiData.clear();
				String stno = ConstantValue.storeItem.getcStoreNo();
				Gson json = new Gson();
				ArrayList<PC03paramSt> pc03list = new ArrayList<PC03paramSt>();
				PC03paramSt pc03p = new PC03paramSt();
				pc03p.setStoreNo(stno);
				pc03p.setGroupNo2(grNo2);
				pc03list.add(pc03p);
				String jsonStr = json.toJson(pc03list);
				String msg = "*AIS|RQ|PC03|" + stno + "|" + jsonStr + "|AIE#";
				PromptManager.showLogTest(
						"SanleiAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_pc03));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_pc03, context, map);
				String result = NetUtil.post(vo);
				if (result == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					String[] temp = NetUtil.split(result, "|");
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "PC03".equals(temp[2])) {
						// t3
						Gson gson = new Gson();

						grouptypealllist = gson.fromJson(temp[3],
								new TypeToken<List<GroupType123List>>() {
								}.getType());
						saneiData.addAll(grouptypealllist.get(0).getPC03());
						PromptManager.showLogTest("fanhui-net",
								"net-result-null");
						return true;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;

		}
	}

	public class Fenlei1AsynTask extends
			AsyncTask<Context, ProgressBar, Boolean> {
		Context context;
		ListView list;

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			if (!result) {
			} else {
				yileilistview.setAdapter(mybase1);

				float scale = context.getResources().getDisplayMetrics().density;
				//
				int pxs = (int) (80 * scale + 0.5f);
				llPlayback.setLayoutParams(new FrameLayout.LayoutParams(pxs
						* erleiData.size(), LayoutParams.WRAP_CONTENT));
				erleilistview.setNumColumns(erleiData.size());
				erleilistview.setAdapter(mybase2);
				sanleilistview.setAdapter(mybase3);
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
			try {
				this.context = arg0[0];
				yileiData.clear();
				erleiData.clear();
				saneiData.clear();
				PromptManager.showLogTest("DaleiAsynTaskdoInBackground",
						"DaleiAsynTask");
				String st1no = ConstantValue.storeItem.getcStoreNo();// "10001";
				String msg = "*AIS|RQ|PC01|00|" + st1no + "|AIE#";
				PromptManager.showLogTest("testDaleiOfStore-msg", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", conts.getString(R.string.url_pc01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_pc01, conts, map);

				String result = NetUtil.post(vo);
				if (result == null) {
					PromptManager.showLogTest("null-net", "net-result-null");
				} else {
					String[] temp = NetUtil.split(result, "|");
					if ("*AIS".equalsIgnoreCase(temp[0])
							&& "RS".equals(temp[1]) && "PC01".equals(temp[2])) {
						Gson gson = new Gson();

						grouptypealllist = gson.fromJson(temp[3],
								new TypeToken<List<GroupType123List>>() {
								}.getType());
						yileiData.addAll(grouptypealllist.get(0).getPC01());
						erleiData.addAll(grouptypealllist.get(0).getPC02());
						saneiData.addAll(grouptypealllist.get(0).getPC03());
						PromptManager.showLogTest("fanhui-net", "net-not-null");
						return true;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				return false;
			}
			return false;
		}
	}

	public static ArrayList<GroupType123List> grouptypealllist;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// detector.onTouchEvent(event);
		return super.onTouchEvent(event);
	}

	public void tomainpage(View view) {

		Intent intent;
		intent = new Intent();
		Bundle extras = new Bundle();
		extras.putInt("indexMenu", 21);
		// intent.putIntegerArrayListExtra(name, value)Extra("indexMenu", 1);
		intent.putExtras(extras);
		
		intent.setClass(context, MainActivity.class);
		startActivity(intent);
		finish();
	}
	Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fujindianpu_dandianfenlei);
		context=StoreSingleFenleiActivity.this;
		initView();
		yileiData = new ArrayList<GroupType1>();
		erleiData = new ArrayList<GroupType2>();
		saneiData = new ArrayList<GroupType3>();
		detector = new GestureDetector(this, new OnGestureListener() {
			/**
			 * �����ǵ���ָ�����滬����ʱ��ص�
			 */
			@Override
			public boolean onFling(MotionEvent e1, MotionEvent e2,
					float velocityX, float velocityY) {

				// ����б���������
				if (Math.abs((e2.getRawY() - e1.getRawY())) > 120) {

					return false;
				}
				if (Math.abs((e2.getRawX() - e1.getRawX())) < 10) {

					return false;
				}

				if ((e2.getRawX() - e1.getRawX()) > 80) {
					// ��ʾ��һ��ҳ�棺�������һ���

					showpre();
					return true;

				}

				if ((e1.getRawX() - e2.getRawX()) > 80) {
					shownext();
					return true;
				}

				return true;
			}

			private void shownext() {
				// Toast.makeText(getApplicationContext(), "��", 0).show();
				if (erleiposition == erleiData.size() - 1) {
					return;
				} else {
					grNo2 = erleiData.get(erleiposition + 1).getGroupNO2();
					erleiposition++;
					new Fenlei2t3AsynTask().execute(cont);

					float scale = cont.getResources().getDisplayMetrics().density;
					//
					int pxs = (int) (80 * scale + 0.5f);
					mybase2.notifyDataSetChanged();
					hsView.scrollTo(erleiposition * pxs, 0);
				}
			}

			private void showpre() {
				// Toast.makeText(getApplicationContext(), "ǰ", 0).show();
				if (erleiposition == 0) {
					return;
				} else {
					grNo2 = erleiData.get(erleiposition - 1).getGroupNO2();
					erleiposition--;
					new Fenlei2t3AsynTask().execute(cont);
					mybase2.notifyDataSetChanged();
					float scale = cont.getResources().getDisplayMetrics().density;
					//
					int pxs = (int) (80 * scale + 0.5f);
					hsView.scrollTo(erleiposition * pxs, 0);
				}
			}

			@Override
			public boolean onDown(MotionEvent arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onLongPress(MotionEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onScroll(MotionEvent arg0, MotionEvent arg1,
					float arg2, float arg3) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public void onShowPress(MotionEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean onSingleTapUp(MotionEvent arg0) {
				// TODO Auto-generated method stub
				return false;
			}

		});
		conts = getApplicationContext();
		new Fenlei1AsynTask().execute(cont);
		sanleilistview.setGestureDetector(detector);
		sanleilistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				ConstantValue.grouptype3list_goodno = null;
				sanposition = position;
				ConstantValue.grouptype3list_goodno = saneiData.get(position)
						.getGroupNO3();

				Intent i = new Intent();
				i.setClass(getApplicationContext(),
						StoreSingleGoodlistActivity.class);
				startActivity(i);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			PromptManager.showMyToast(StoreSingleFenleiActivity.this,
					scanResult);

		}
	}

	@Override
	public void onClick(View v) {
		Intent intent;
		switch (v.getId()) {
		case R.id.st_index_sousuo:
			// 搜索选项
			String searchKey = fenlei_search.getText().toString();
			if (TextUtils.isEmpty(searchKey)) {
				return;
			}
			Bundle bundle = new Bundle();

			bundle.putString("cStoreNo", ConstantValue.storeItem.getcStoreNo());
			bundle.putString("searchKey", searchKey);
			intent = new Intent(this, SearchActivity.class);
			intent.putExtras(bundle);
			startActivity(intent);
			break;

		// ���˰�ť
		case R.id.backfenleistore:
			StoreSingleFenleiActivity.this.finish();
			break;
		// ����������۰�ť
		case R.id.st_fenlei_getcomm:
		case R.id.storenames_fenlei:
			intent = new Intent(StoreSingleFenleiActivity.this,
					StoreMainInPingjia.class);
			startActivity(intent);
			break;
		//
		case R.id.i_search_stfenlei:
			// 展开搜索专项
			Animation animation;
			if (searchvisible) {
				st_fenlei_rl.setVisibility(View.VISIBLE);
				animation = AnimationUtils.loadAnimation(
						StoreSingleFenleiActivity.this, R.anim.tran_top_in);

			} else {
				st_fenlei_rl.setVisibility(View.GONE);
				animation = AnimationUtils.loadAnimation(
						StoreSingleFenleiActivity.this, R.anim.tran_top_out);

			}
			st_fenlei_rl.startAnimation(animation);
			searchvisible = !searchvisible;
			break;

		case R.id.index_saomiao_stfenlei:
			// 扫码专项
			intent = new Intent(StoreSingleFenleiActivity.this,
					CaptureActivity.class);
			startActivityForResult(intent, 0);

			break;

		default:
			break;
		}
	}

	boolean searchvisible = true;

	private void initView() {
		fenlei_search = (EditText) findViewById(R.id.st_fenlei_search);
		index_sousuo = (ImageButton) findViewById(R.id.st_index_sousuo);
		index_sousuo.setOnClickListener(this);

		i_search_stfenlei = (ImageButton) findViewById(R.id.i_search_stfenlei);
		i_search_stfenlei.setOnClickListener(this);
		st_fenlei_getcomm = (TextView) findViewById(R.id.st_fenlei_getcomm);
		storenames_fenlei = (TextView) findViewById(R.id.storenames_fenlei);
		storenames_fenlei.setOnClickListener(this);
		st_fenlei_getcomm.setOnClickListener(this);
		try {
			storenames_fenlei.setText(ConstantValue.storeItem.getcStoreName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		cont = StoreSingleFenleiActivity.this;
		tts = (RelativeLayout) findViewById(R.id.tts);
		st_fenlei_rl = (RelativeLayout) findViewById(R.id.st_fenlei_rl);
		loading_bg = (RelativeLayout) findViewById(R.id.loading_bg);
		loading_bg3 = (RelativeLayout) findViewById(R.id.loading_bg3);
		fenlei_data = (RelativeLayout) findViewById(R.id.fenlei_data);
		hsView = (HorizontalScrollView) findViewById(R.id.hsView);
		sanleilistview = (MGridView) findViewById(R.id.gv_sanlei);
		yileilistview = (ListView) findViewById(R.id.yilei);
		erleilistview = (GridView) findViewById(R.id.gv_erlei);
		llPlayback = (LinearLayout) findViewById(R.id.llPlayback);
		backfenleistore = (ImageView) findViewById(R.id.backfenleistore);
		backfenleistore.setOnClickListener(this);
		index_saomiao = (ImageView) findViewById(R.id.index_saomiao_stfenlei);
		index_saomiao.setOnClickListener(this);

	}

}
