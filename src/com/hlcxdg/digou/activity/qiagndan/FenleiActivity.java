package com.hlcxdg.digou.activity.qiagndan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.MGridView;
import com.hlcxdg.digou.activity.MainActivity;
import com.hlcxdg.digou.activity.goods.GoodsListPL01Activity;
import com.hlcxdg.digou.activity.goods.QdGoodsListPL01Activity;
import com.hlcxdg.digou.bean.GroupType1;
import com.hlcxdg.digou.bean.GroupType123List;
import com.hlcxdg.digou.bean.GroupType2;
import com.hlcxdg.digou.bean.GroupType3;
import com.hlcxdg.digou.bean.PC03params;
import com.hlcxdg.digou.constant.ConstantIP;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;
import com.loopj.android.image.SmartImageView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class FenleiActivity extends Activity implements OnClickListener {
	String groupNO2;
	View view;
	Context context;
	private ImageView index_saomiao;
	public static HorizontalScrollView hsView;
	private MGridView sanleilistview;
	public String yileiid;
	public int yileiposition = 0;
	public int erleiposition = 0;
	public DaleiAdapter daleiadpter;
	public ErleiAdapter erleiadpter;
	public SanleiAdapter sanleiadpter;
	private LinearLayout llPlayback;
	public GestureDetector detector;
	long mTime;
	boolean visib = true;
	ListView yileilistview;
	GridView erleilistview;
	RelativeLayout loading_bg;
	RelativeLayout loading_bg3;
	RelativeLayout fenlei_data;
	RelativeLayout tts;
	public ArrayList<GroupType1> grouptype1list;
	public ArrayList<GroupType2> grouptype2list;
	public ArrayList<GroupType3> grouptype3list;

	private void initView() {
		index_saomiao = (ImageView) findViewById(R.id.index_saomiao);
		index_saomiao.setOnClickListener(this);
		tts = (RelativeLayout) findViewById(R.id.tts);
		loading_bg = (RelativeLayout) findViewById(R.id.loading_bg);
		loading_bg3 = (RelativeLayout) findViewById(R.id.loading_bg3);
		fenlei_data = (RelativeLayout) findViewById(R.id.fenlei_data);
		hsView = (HorizontalScrollView) findViewById(R.id.hsView);
		sanleilistview = (MGridView) findViewById(R.id.gv_sanlei);
		yileilistview = (ListView) findViewById(R.id.yilei);
		erleilistview = (GridView) findViewById(R.id.gv_erlei);
		llPlayback = (LinearLayout) findViewById(R.id.llPlayback);
	}

	public void goHomepage(View view) {
		this.finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.fenlei);
		context = FenleiActivity.this;
		initView();
		yileiposition = 0;
		erleiposition = 0;
		grouptype1list = new ArrayList<GroupType1>();
		grouptype2list = new ArrayList<GroupType2>();
		grouptype3list = new ArrayList<GroupType3>();
		daleiadpter = new DaleiAdapter(context);
		erleiadpter = new ErleiAdapter(context);
		sanleiadpter = new SanleiAdapter(context);
		new DaleiGetAllAsynTask().execute(context);
		sanleilistview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View erview,
					int position, long arg3) {
				ConstantValue.grouptype3list_goodno = grouptype3list.get(
						position).getGroupNO3();
				Intent i = new Intent();
				i.setClass(context, QdGoodsListPL01Activity.class);
				startActivity(i);
				FenleiActivity.this.finish();
			}
		});

		erleilistview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View erview,
					int position, long arg3) {
				erleiposition = position;
				erleiadpter.notifyDataSetChanged();
				groupNO2 = grouptype2list.get(position).getGroupNO2();
				// Toast.makeText(getActivity(), "dianerlei" + grNo2, 0)
				// .show();
				new SanleiGetAllAsynTask().execute(context);

			}
		});
		yileilistview.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View lview,
					int position, long arg3) {
				// Toast.makeText(getActivity(), "kaishidian" +
				// position,
				// 0).show();
				yileiposition = position;
				erleiposition = 0;
				daleiadpter.notifyDataSetChanged();
				// erleiadpter.notifyDataSetChanged();
				new ErleiGetAllAsynTask().execute(context);
			}
		});
		detector = new GestureDetector(context, new OnGestureListener() {

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
				if (Math.abs((e2.getRawX() - e1.getRawX())) < 20) {

					return false;
				}

				if ((e2.getRawX() - e1.getRawX()) > 80) {
					// ��ʾ��һ��ҳ�棺�������һ���

					showpre();
					return true;

				}

				if ((e1.getRawX() - e2.getRawX()) > 80) {
					// ��ʾ��һ��ҳ�棺�������󻬶�
					// System.out.println("��ʾ��һ��ҳ�棺�������󻬶�");

					shownext();
					return true;
				}

				return true;
			}

			private void shownext() {
				// Toast.makeText(getApplicationContext(), "��", 0).show();
				if (erleiposition == grouptype2list.size() - 1) {
					return;
				} else {
					groupNO2 = grouptype2list.get(erleiposition + 1)
							.getGroupNO2();
					erleiposition++;

					new SanleiGetAllAsynTask().execute(context);
					float scale = context.getResources().getDisplayMetrics().density;
					//
					int pxs = (int) (80 * scale + 0.5f);
					erleiadpter.notifyDataSetChanged();
					hsView.scrollTo(erleiposition * pxs, 0);
				}
			}

			private void showpre() {
				// Toast.makeText(getApplicationContext(), "ǰ", 0).show();
				if (erleiposition == 0) {
					return;
				} else {
					groupNO2 = grouptype2list.get(erleiposition - 1)
							.getGroupNO2();
					erleiposition--;
					new SanleiGetAllAsynTask().execute(context);
					erleiadpter.notifyDataSetChanged();
					float scale = context.getResources().getDisplayMetrics().density;
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

		sanleilistview.setGestureDetector(detector);
	}

	public class DaleiAdapter extends BaseAdapter {
		private Context context;

		public DaleiAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return grouptype1list.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup arg2) {
			View view;
			TextView tv_yilei_gt1name;
			view = View.inflate(context, R.layout.leibie_yilei_item, null);
			tv_yilei_gt1name = (TextView) view
					.findViewById(R.id.tv_yilei_gt1name);
			if (position == yileiposition) {
				tv_yilei_gt1name.setTextColor(Color.RED);
				tv_yilei_gt1name.setBackgroundResource(R.drawable.left);
			}
			tv_yilei_gt1name.setText(grouptype1list.get(position).getGrName1());

			return view;
		}

	}

	public class ErleiAdapter extends BaseAdapter {
		private Context context;

		public ErleiAdapter(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int getCount() {
			return grouptype2list.size();
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
			TextView tv_erlei_gt2name;
			view = View.inflate(context, R.layout.leibie_erlei_item, null);
			tv_erlei_gt2name = (TextView) view
					.findViewById(R.id.tv_erlei_gt2name);
			if (position == erleiposition) {
				tv_erlei_gt2name.setTextColor(Color.RED);
				tv_erlei_gt2name
						.setBackgroundResource(R.drawable.drawable_bottom);
			}
			tv_erlei_gt2name.setText(grouptype2list.get(position).getGrName2());
			return view;
		}

	}

	public class SanleiAdapter extends BaseAdapter {
		private Context context;

		public SanleiAdapter(Context context) {
			super();
			this.context = context;

		}

		@Override
		public int getCount() {
			return grouptype3list.size();
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
			SmartImageView iv_sanlei_gt3Img;
			TextView tv_sanlei_gtname;
			view = View.inflate(context, R.layout.leibie_sanlei_item, null);
			if (grouptype3list.size() > 0) {
				tv_sanlei_gtname = (TextView) view
						.findViewById(R.id.tv_sanlei_gtname);
				iv_sanlei_gt3Img = (SmartImageView) view
						.findViewById(R.id.iv_sanlei_gt3Img);
				iv_sanlei_gt3Img.setImageUrl(ConstantIP.ImageviewURL
						+ grouptype3list.get(position).getcIMG());
				tv_sanlei_gtname.setText(grouptype3list.get(position)
						.getGrName3());

			}
			return view;
		}

	}

	public class DaleiGetAllAsynTask extends
			AsyncTask<Context, ProgressBar, Integer> {
		Context context;

		public ArrayList<GroupType123List> grouptypealllist;

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			PromptManager.showLogTest("DaleiGetAllPost", "post_in" + result);
			switch (result) {
			case -1:
				break;
			case 0:
				// PromptManager.showMyToast(context, "网络不给力");
				break;
			case 1:

				float scale = context.getResources().getDisplayMetrics().density;
				int pxs = (int) (80 * scale + 0.5f);
				llPlayback.setLayoutParams(new FrameLayout.LayoutParams(pxs
						* grouptype2list.size(), LayoutParams.WRAP_CONTENT));
				erleilistview.setNumColumns(grouptype2list.size());

				yileilistview.setAdapter(daleiadpter);
				erleilistview.setAdapter(erleiadpter);
				sanleilistview.setAdapter(sanleiadpter);
				break;
			default:
				break;
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
		protected Integer doInBackground(Context[] con) {
			// return 1;
			grouptype1list.clear();
			grouptype2list.clear();
			grouptype3list.clear();
			this.context = con[0];
			try {
				PromptManager.showLogTest("DaleiAsynTaskdoInBackground",
						"DaleiAsynTask");
				Boolean network = NetUtil.hasNetwork(context);
				if (!network) {
					return -1;
				}

				String msg = "*AIS|RQ|PC01|00|00|AIE#";
				PromptManager.showLogTest(
						"DaleiAsynTaskdoInBackground-postmesssage", msg);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_pc01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_pc01, context, map);
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
						grouptype1list
								.addAll(grouptypealllist.get(0).getPC01());
						grouptype2list
								.addAll(grouptypealllist.get(0).getPC02());
						grouptype3list
								.addAll(grouptypealllist.get(0).getPC03());
						PromptManager.showLogTest("fanhui-net", "net-not-null");
						return 1;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return 0;
			}
			return 0;

		}
	}

	public class SanleiGetAllAsynTask extends
			AsyncTask<Context, ProgressBar, Integer> {
		private Context context;
		ArrayList<GroupType3> tmpgrouptype3list;
		public ArrayList<GroupType123List> grouptypealllist;

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(context);
				break;
			case 0:
				break;
			case 1:
				// shipeiqi
				sanleiadpter = new SanleiAdapter(context);
				sanleilistview.setAdapter(sanleiadpter);
				break;

			default:
				break;
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
		protected Integer doInBackground(Context[] cont) {
			// return 1;
			PromptManager.showLogTest("SanleiAsynTaskdoInBackground",
					"SanleiAsynTask");
			this.context = cont[0];
			Boolean network = NetUtil.hasNetwork(context);
			if (!network) {
				return -1;
			}
			grouptype3list.clear();
			ArrayList<PC03params> pc03list = new ArrayList<PC03params>();
			PC03params pc03p = new PC03params();
			pc03p.setStoreNo("00");
			pc03p.setGroupNo2(groupNO2);
			pc03list.add(pc03p);
			Gson gson = new Gson();
			String jsonStr = gson.toJson(pc03list);
			String msg = "*AIS|RQ|PC03|00|" + jsonStr + "|AIE#";

			PromptManager.showLogTest(
					"ErleiAsynTaskdoInBackground-postmesssage", msg);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("type", "post");
			map.put("url", context.getString(R.string.url_pc02));
			map.put("dataType", "text");
			map.put("data", msg);
			RequestVo vo = new RequestVo(R.string.url_pc02, context, map);
			String result = NetUtil.post(vo);

			if (result == null) {
				PromptManager.showLogTest("enginenet", "net-result-null");
			} else {
				String[] temp = NetUtil.split(result, "|");
				if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
						&& "PC03".equals(temp[2])) {
					// t3

					grouptypealllist = gson.fromJson(temp[3],
							new TypeToken<List<GroupType123List>>() {
							}.getType());
					grouptype3list.addAll(grouptypealllist.get(0).getPC03());
					PromptManager.showLogTest("fanhui-net", "net-result-null");
					return 1;
				}
			}
			return 0;
		}
	}

	public class ErleiGetAllAsynTask extends
			AsyncTask<Context, ProgressBar, Integer> {
		Context context;
		public ArrayList<GroupType123List> grouptypealllist;

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(context);
				break;
			case 0:
				PromptManager.showMyToast(context, "网络不给力");
				break;
			case 1:
				// shipeiqi
				PromptManager.showLogTest("erleipostexcute", "wancheng");
				float scale = context.getResources().getDisplayMetrics().density;
				int pxs = (int) (80 * scale + 0.5f);
				llPlayback.setLayoutParams(new FrameLayout.LayoutParams(pxs
						* grouptype2list.size(), LayoutParams.WRAP_CONTENT));
				erleilistview.setNumColumns(grouptype2list.size());
				// ConstantValue.erleilistview.smoothScrollToPosition(0);
				hsView.scrollTo(0, 0);

				erleilistview.setAdapter(erleiadpter);
				sanleilistview.setAdapter(sanleiadpter);
				break;

			default:
				break;
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
		protected Integer doInBackground(Context[] cont) {

			// return 33;
			PromptManager.showLogTest("ErleiAsynTaskdoInBackground",
					"ErleiAsynTask");
			this.context = cont[0];
			Boolean network = NetUtil.hasNetwork(context);
			if (!network) {
				return -1;
			}
			grouptype2list.clear();
			grouptype3list.clear();
			String gt1no = grouptype1list.get(yileiposition).getGroupNO1();
			String msg = "*AIS|RQ|PC02|" + gt1no + "|00|AIE#";
			PromptManager.showLogTest(
					"ErleiAsynTaskdoInBackground-postmesssage", msg);
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("type", "post");
			map.put("url", context.getString(R.string.url_pc02));
			map.put("dataType", "text");
			map.put("data", msg);
			RequestVo vo = new RequestVo(R.string.url_pc02, context, map);
			String result = NetUtil.post(vo);
			if (result == null) {
				PromptManager.showLogTest("enginenet", "net-result-null");
			} else {
				String[] temp = NetUtil.split(result, "|");
				if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
						&& "PC02".equals(temp[2])) {
					Gson gson = new Gson();
					grouptypealllist = gson.fromJson(temp[3],
							new TypeToken<List<GroupType123List>>() {
							}.getType());
					grouptype2list.addAll(grouptypealllist.get(0).getPC02());
					grouptype3list.addAll(grouptypealllist.get(0).getPC03());
					return 1;
				}
			}
			return 0;
		}
	}

	@Override
	public void onClick(View view) {
		Intent intent;
		switch (view.getId()) {
		case R.id.index_saomiao:

			break;

		default:
			break;
		}
	}
}
