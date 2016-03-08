package com.hl_zhaoq.digou.activity.salesheet;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.alipay.sdk.pay.demo.PayZhiActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hl_zhaoq.digou.activity.comment.MyCommentWriteSS;
import com.hl_zhaoq.digou.activity.coupon.YouhuiquanActivity.Fenlei2AsynTask;
import com.hl_zhaoq.digou.activity.coupon.YouhuiquanActivity.Fenlei2AsynTask1;
import com.hl_zhaoq.digou.activity.user.LoginActivity;
import com.hl_zhaoq.digou.activity.wallet.PayWaysChangeActivity;
import com.hl_zhaoq.digou.activity.wallet.PayWaysChangeActivityList;
import com.hl_zhaoq.digou.bean.CouponYouhuiquan;
import com.hl_zhaoq.digou.bean.SaleSheet;
import com.hl_zhaoq.digou.bean.SaleSheetDetail;
import com.hl_zhaoq.digou.constant.ConstantValue;
import com.hl_zhaoq.digou.net.NetUtil;
import com.hl_zhaoq.digou.utils.PromptManager;
import com.hl_zhaoq.digou.vo.RequestVo;
import com.hl_zhaoq.digou.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class SaleSheetMineActivity extends Activity implements OnClickListener {
	private ListView lssalesheet;
	private ListView salesheet;
	MyDoingAdapter mzzzx;
	private ImageView salesheet_mine_back;
	int whatSS;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.salesheetminelist);
		whatSS=0;
	}

	public void kaishi() {
		if (ConstantValue.userinfo == null) {
			Toast.makeText(SaleSheetMineActivity.this, "请先登录", 0).show();
			Intent intent;
			intent = new Intent();
			intent.setClass(SaleSheetMineActivity.this, LoginActivity.class);
			SaleSheetMineActivity.this.startActivity(intent);
			SaleSheetMineActivity.this.finish();
		} else {
			initView();
		
			switch (whatSS) {
			case 0:
				new GetMySaleSheetTask().execute(SaleSheetMineActivity.this);
				break;
			case 1:
				new GetMyHistorySaleSheetTask().execute(SaleSheetMineActivity.this);
				break;
			default:
				new GetMySaleSheetTask().execute(SaleSheetMineActivity.this);
				break;
			}
			
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		kaishi();

	}

	public void initView() {
		lssalesheet = (ListView) findViewById(R.id.lssalesheet);
		salesheet = (ListView) findViewById(R.id.salesheet);
		salesheet_mine_back = (ImageView) findViewById(R.id.salesheet_mine_back);
		salesheet_mine_back.setOnClickListener(this);
	}

	// 正在执行订单
	public class GetMySaleSheetTask extends
			AsyncTask<Context, ProgressBar, Boolean> {
		Context context;

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			// PromptManager.closeProgressDialog();
			if (!result) {
				lssalesheet.setVisibility(View.GONE);
				salesheet.setVisibility(View.GONE);
				PromptManager.showToast(context, "无订单");
			} else if (ConstantValue.SSList != null
					&& ConstantValue.SSList.size() > 0) {
				salesheet.setVisibility(View.VISIBLE);
				lssalesheet.setVisibility(View.GONE);
				mzzzx = new MyDoingAdapter();
				salesheet.setAdapter(mzzzx);
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

		SharedPreferences sp;

		@Override
		protected Boolean doInBackground(Context[] arg0) {
			this.context = arg0[0];
			ConstantValue.SSList.clear();
			try {
				// // 00-锟斤拷锟斤拷执锟斤拷锟剿碉拷01--锟斤拷史锟剿碉拷
				String userNo = ConstantValue.userinfo.getUserNo().trim();
				String msg = "*AIS|RQ|OD03|00|" + userNo + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_od01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_od01, context, map);
				String result = NetUtil.post(vo);// *AIS|RS|OD03|01|[{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-09 10:51:57.0","cSaleSheetno":"0910515792464-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":1214.8200,"Stat_Id":"11","Stat_Name":"已付款,受理中","OverCut":10.0000,"FirstSheet":0.0000,"PeisongFee":0.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-08 12:18:59.0","cSaleSheetno":"0812185993585-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":288.0800,"Stat_Id":"00","Stat_Name":"未付款,请确认下单","OverCut":10.0000,"FirstSheet":0.0000,"PeisongFee":0.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-08 12:17:22.0","cSaleSheetno":"0812172297125-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":132.4800,"Stat_Id":"00","Stat_Name":"未付款,请确认下单","OverCut":10.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-08 12:15:19.0","cSaleSheetno":"0812151950981-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":39.4000,"Stat_Id":"11","Stat_Name":"已付款,受理中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10029","cStoreName":"VIVO手机专卖店","dSaleDate":"2016-01-07 16:45:58.0","cSaleSheetno":"0704455886219-10029","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":712.8000,"Stat_Id":"03","Stat_Name":"到货付款,发货中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":0.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10014","cStoreName":"好又多","dSaleDate":"2016-01-07 16:45:58.0","cSaleSheetno":"0704455880569-10014","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":143.5000,"Stat_Id":"03","Stat_Name":"到货付款,发货中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":0.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-07 16:45:58.0","cSaleSheetno":"0704455874050-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":1184.9000,"Stat_Id":"03","Stat_Name":"到货付款,发货中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10003","cStoreName":"新泉养生堂","dSaleDate":"2016-01-06 17:45:39.0","cSaleSheetno":"0605453924518-10003","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":120.0000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-06 17:28:52.0","cSaleSheetno":"0605285203390-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":157.0000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10003","cStoreName":"新泉养生堂","dSaleDate":"2016-01-06 17:06:10.0","cSaleSheetno":"0605061032222-10003","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":130.0000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10003","cStoreName":"新泉养生堂","dSaleDate":"2016-01-06 16:47:59.0","cSaleSheetno":"0604475934012-10003","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":210.0000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10003","cStoreName":"新泉养生堂","dSaleDate":"2016-01-06 16:41:17.0","cSaleSheetno":"0604411737186-10003","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":120.0000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10003","cStoreName":"新泉养生堂","dSaleDate":"2016-01-06 16:37:49.0","cSaleSheetno":"0604374955125-10003","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":57.0000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":20.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-06 16:34:17.0","cSaleSheetno":"0604341774261-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":280.0200,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-06 16:22:10.0","cSaleSheetno":"0604221054549-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":19.8000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-06 16:17:51.0","cSaleSheetno":"0604175134165-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":19.8000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-06 16:14:13.0","cSaleSheetno":"0604141379842-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":19.8000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-06 16:12:13.0","cSaleSheetno":"0604121357811-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":35.9000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":35.9000,"FirstSheet":35.9000,"PeisongFee":35.9000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-06 16:09:14.0","cSaleSheetno":"0604091408574-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":19.8000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":10.0000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-05 16:01:09.0","cSaleSheetno":"0504010927934-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":9.8000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":9.8000,"FirstSheet":9.8000,"PeisongFee":9.8000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-05 16:00:19.0","cSaleSheetno":"0504001938523-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":9.8000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":9.8000,"FirstSheet":9.8000,"PeisongFee":9.8000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-05 15:59:52.0","cSaleSheetno":"0503595233033-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":19.6000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":19.6000,"FirstSheet":19.6000,"PeisongFee":19.6000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-05 15:58:28.0","cSaleSheetno":"0503582878663-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":9.8000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":9.8000,"FirstSheet":9.8000,"PeisongFee":9.8000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-05 15:57:07.0","cSaleSheetno":"0503570773489-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":29.9000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":29.9000,"FirstSheet":29.9000,"PeisongFee":29.9000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2016-01-01 20:01:01.0","cSaleSheetno":"0108010143834-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":34.9000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":34.9000,"FirstSheet":34.9000,"PeisongFee":34.9000,"Beizhu":"","Fapiao":"  ","FapiaoTitle":"","FapiaoStat":"","cVipNo":"","AddressId":12,"SEvaluation":false},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2015-12-31 18:45:28.0","cSaleSheetno":"3106452821599-10001","userNo":"201510131706423796","fMoney":0.0000,"fLastMoney":14.8000,"Stat_Id":"01        ","Stat_Name":"到货付款,受理中","OverCut":0.0000,"FirstSheet":0.0000,"PeisongFee":5...
				// *AIS|RS|OD03|00|[{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2015-12-29 17:27:45.0","cSaleSheetno":"2905274508073-10001","userNo":"201510311654001849"
				// *AIS|RS|OD03|00|[{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2015-12-21 17:09:22.0","cSaleSheetno":"122117085953153142-10001","userNo":"201510131706423796","fLastMoney":6.0000,"AddressId":0,"Stat_Id":"00","Stat_Name":"未付款,请确认下单"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-21 17:06:10.0","cSaleSheetno":"122117054325508977-10006","userNo":"201510131706423796","fLastMoney":6.0000,"AddressId":0,"Stat_Id":"00","Stat_Name":"未付款,请确认下单"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-21 17:04:29.0","cSaleSheetno":"122117041612319101-10006","userNo":"201510131706423796","fLastMoney":6.0000,"AddressId":0,"Stat_Id":"00","Stat_Name":"未付款,请确认下单"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-21 16:31:25.0","cSaleSheetno":"122116282937386170-10006","userNo":"201510131706423796","fLastMoney":6.0000,"AddressId":0,"Stat_Id":"00","Stat_Name":"未付款,请确认下单"},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2015-12-21 16:13:41.0","cSaleSheetno":"122116131592254361-10001","userNo":"201510131706423796","fLastMoney":6.0000,"AddressId":0,"Stat_Id":"00","Stat_Name":"未付款,请确认下单"},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2015-12-21 13:33:44.0","cSaleSheetno":"122113333368369625-10001","userNo":"201510131706423796","fLastMoney":10.0000,"AddressId":0,"Stat_Id":"11","Stat_Name":"已付款,受理中"},{"cStoreNo":"10008","cStoreName":"大众和谐超市","dSaleDate":"2015-12-16 09:00:15.0","cSaleSheetno":"2015121609052752941248-10008","userNo":"201510131706423796","fLastMoney":46.0000,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10008","cStoreName":"大众和谐超市","dSaleDate":"2015-12-15 16:43:33.0","cSaleSheetno":"2015121516483958102904-10008","userNo":"201510131706423796","fLastMoney":46.0000,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-15 15:18:21.0","cSaleSheetno":"2015121515232652396997-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-15 15:15:48.0","cSaleSheetno":"2015121515205323331842-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 13:51:12.0","cSaleSheetno":"2015121413560887835971-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 13:50:24.0","cSaleSheetno":"2015121413551919341879-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 13:47:30.0","cSaleSheetno":"2015121413522648701670-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 13:45:42.0","cSaleSheetno":"2015121413503816146233-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 13:43:42.0","cSaleSheetno":"2015121413483736031698-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10002","cStoreName":"华隆","dSaleDate":"2015-12-14 13:34:11.0","cSaleSheetno":"121413342117331521-10002","userNo":"201510131706423796","fLastMoney":23.0000,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10001","cStoreName":"乐尚","dSaleDate":"2015-12-14 13:33:29.0","cSaleSheetno":"121413333212244229-10001","userNo":"201510131706423796","fLastMoney":9.8000,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10008","cStoreName":"大众和谐超市","dSaleDate":"2015-12-14 13:29:42.0","cSaleSheetno":"2015121413343889601778-10008","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10008","cStoreName":"大众和谐超市","dSaleDate":"2015-12-14 13:26:28.0","cSaleSheetno":"2015121413312369846456-10008","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 13:13:36.0","cSaleSheetno":"2015121413183199115741-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 13:07:24.0","cSaleSheetno":"2015121413121941587760-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 12:59:19.0","cSaleSheetno":"2015121413041463653428-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 12:58:02.0","cSaleSheetno":"2015121413025757168404-10006","userNo":"201510131706423796","fLastMoney":24.0000,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 12:55:56.0","cSaleSheetno":"2015121413005176725682-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 12:52:17.0","cSaleSheetno":"2015121412571255825048-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10008","cStoreName":"大众和谐超市","dSaleDate":"2015-12-14 12:50:41.0","cSaleSheetno":"2015121412553657411381-10008","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 12:33:41.0","cSaleSheetno":"2015121412383677036091-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 12:09:19.0","cSaleSheetno":"2015121412141480151515-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 12:02:23.0","cSaleSheetno":"2015121412071814651600-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10008","cStoreName":"大众和谐超市","dSaleDate":"2015-12-14 12:00:35.0","cSaleSheetno":"2015121412052968358274-10008","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10008","cStoreName":"大众和谐超市","dSaleDate":"2015-12-14 11:58:39.0","cSaleSheetno":"2015121412033484885651-10008","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 11:53:36.0","cSaleSheetno":"2015121411583150304959-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10008","cStoreName":"大众和谐超市","dSaleDate":"2015-12-14 11:38:25.0","cSaleSheetno":"2015121411432067698087-10008","userNo":"201510131706423796","fLastMoney":24.0000,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 11:38:04.0","cSaleSheetno":"2015121411425928172562-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 11:10:45.0","cSaleSheetno":"2015121411153947515426-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10008","cStoreName":"大众和谐超市","dSaleDate":"2015-12-14 11:02:22.0","cSaleSheetno":"2015121411071775911865-10008","userNo":"201510131706423796","fLastMoney":46.0000,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-14 11:01:47.0","cSaleSheetno":"2015121411064154735316-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-12 17:08:38.0","cSaleSheetno":"2015121217135226188105-10006","userNo":"201510131706423796","fLastMoney":24.0000,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-12 17:07:43.0","cSaleSheetno":"2015121217125731641779-10006","userNo":"201510131706423796","fLastMoney":24.0000,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10002","cStoreName":"华隆","dSaleDate":"2015-12-12 13:51:10.0","cSaleSheetno":"121213513651931241-10002","userNo":"201510131706423796","fLastMoney":39.0000,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10002","cStoreName":"华隆","dSaleDate":"2015-12-12 13:10:30.0","cSaleSheetno":"121213103987539111-10002","userNo":"201510131706423796","fLastMoney":23.0000,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-11 17:33:28.0","cSaleSheetno":"2015121117383426511546-10006","userNo":"201510131706423796","fLastMoney":33.1200,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-11 17:26:49.0","cSaleSheetno":"121115185823686512-10006","userNo":"201510131706423796","fLastMoney":24.0000,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-11 17:26:03.0","cSaleSheetno":"121115185880323058-10006","userNo":"201510131706423796","fLastMoney":24.0000,"AddressId":0,"Stat_Id":"01","Stat_Name":"到货付款,受理中"},{"cStoreNo":"10006","cStoreName":"华源百货","dSaleDate":"2015-12-11 17:16:21.0","cSaleShe...
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp[0].equalsIgnoreCase("*AIS") && temp[1].equals("RS")
						&& temp[2].equals("OD03")) {
					Log.i("json", temp[4]);
					Gson gson = new Gson();
					ArrayList<SaleSheet> sslist = gson.fromJson(temp[4],
							new TypeToken<List<SaleSheet>>() {
							}.getType());
					if (sslist != null && sslist.size() > 0) {
						ConstantValue.SSList.addAll(sslist);
						Log.i("goodlist", "" + ConstantValue.SSList.size());
						return true;
					}
				}

			} catch (Exception e) {
				return false;
			}
			return false;

		}
	}

	public void back_iv(View v) {
		SaleSheetMineActivity.this.finish();
	}

	// 正在执行订单
	public class MyDoingAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			View view;
			TextView ddriqi;
			TextView ddid;
			TextView ssitem_status_fahuo;
			TextView ddstate;
			TextView tprice;
			Button xq;
			Button sslist_fukuan;
			view = View.inflate(getApplicationContext(), R.layout.sslist_item,
					null);
			try {
				ddid = (TextView) view.findViewById(R.id.ddid);
				sslist_fukuan = (Button) view
						.findViewById(R.id.mysslistitem_fukuan);
				xq = (Button) view.findViewById(R.id.xq);
				tprice = (TextView) view.findViewById(R.id.tprice);
				ddstate = (TextView) view.findViewById(R.id.ddstate);
				ssitem_status_fahuo = (TextView) view
						.findViewById(R.id.ssitem_status_fahuo);
				ddriqi = (TextView) view.findViewById(R.id.ddriqi);
				int sizelist = ConstantValue.SSList.size();
				final SaleSheet ss = ConstantValue.SSList.get(position);
				sslist_fukuan.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						ConstantValue.saleSheetNoForZhifubao = new String[] { "" };
						ConstantValue.saleSheetNoForZhifubao[0] = ss
								.getcSaleSheetno();

						ConstantValue.myss = ss;
						ConstantValue.saleSheet_dingdan.clear();
						ConstantValue.saleSheet_dingdan.add(ss);
						ConstantValue.total_price_cart = ss.getfLastMoney();
						Intent i = new Intent(SaleSheetMineActivity.this,
								SSPayStyleAty.class);
						
						SaleSheetMineActivity.this.startActivity(i);
					}
				});
				xq.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						ConstantValue.myss = ss;
						ConstantValue.ssdnos = ss.getCSaleSheetno();
						Intent i = new Intent(SaleSheetMineActivity.this,
								SaleSheetDetailMineActivity.class);
						i.putExtra("ss", ss);
						SaleSheetMineActivity.this.startActivity(i);
					}
				});
				ddid.setText("订单号：" + ss.getCSaleSheetno());
				tprice.setText("总价："
						+ ss.getFLastMoney().setScale(2,
								BigDecimal.ROUND_HALF_UP));
				String[] states = ss.getStat_Name().split(",");
				
				if(ss.getStat_Id().trim().equals("00")){
					sslist_fukuan.setVisibility(View.VISIBLE);
				}else{
					sslist_fukuan.setVisibility(View.GONE);
				}
//				switch () {
//				case "00":
//					
//
//					break;
//				// case "01":
//				// ddstate.setText("到货付款");
//				// sslist_fukuan.setVisibility(View.VISIBLE);
//				//
//				// sslist_pingjia.setVisibility(View.GONE);
//				// break;
//				// case "01":
//				// sslist_fukuan.setVisibility(View.VISIBLE);
//				// sslist_pingjia.setVisibility(View.GONE);
//				// break;
//				// case "11":
//				// break;
//				default:
//					
//					// sslist_pingjia.setVisibility(View.VISIBLE);
//					break;
//				}

				ddriqi.setText("日期：" + ss.getDSaleDate());
				if (states.length == 2) {
					ddstate.setText("付款状态：" + states[0]);
					ssitem_status_fahuo.setText("发货状态：" + states[1]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return view;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public int getCount() {
			return ConstantValue.SSList.size();
		}
	}

	public void zzzx(View view) {
		Log.i("zzzx", "zzzx");
		whatSS=0;
		new GetMySaleSheetTask().execute(SaleSheetMineActivity.this);
	}

	// 历史订单的
	public void lsdd(View view) {
		whatSS=1;
		new GetMyHistorySaleSheetTask().execute(SaleSheetMineActivity.this);
	}

	// lishi订单
	public class MyDoneAdapter extends BaseAdapter {

		@Override
		public View getView(int position, View arg1, ViewGroup arg2) {
			View view;
			TextView ddriqi;
			TextView ddid;
			TextView ssitem_status_fahuo;
			TextView ddstate;
			TextView tprice;
			Button xq;
			Button sslist_pingjia;
			Button sslist_fukuan;
			view = View.inflate(getApplicationContext(), R.layout.sslist_item,
					null);
			try {
				ddid = (TextView) view.findViewById(R.id.ddid);
				sslist_fukuan = (Button) view
						.findViewById(R.id.mysslistitem_fukuan);
				sslist_fukuan.setVisibility(View.GONE);
				sslist_pingjia = (Button) view
						.findViewById(R.id.sslist_pingjia);

				xq = (Button) view.findViewById(R.id.xq);
				tprice = (TextView) view.findViewById(R.id.tprice);
				ddstate = (TextView) view.findViewById(R.id.ddstate);
				ssitem_status_fahuo = (TextView) view
						.findViewById(R.id.ssitem_status_fahuo);
				ddriqi = (TextView) view.findViewById(R.id.ddriqi);
				int sizelist = ConstantValue.SSList.size();
				final SaleSheet ss = ConstantValue.SSList.get(position);
				if (ss.SEvaluation) {
					sslist_pingjia.setVisibility(View.GONE);
				} else {
					sslist_pingjia.setVisibility(View.VISIBLE);
				}
				
				sslist_pingjia.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						ConstantValue.myss = ss;
						ConstantValue.ssdnos = ss.getCSaleSheetno();
						Intent i = new Intent(SaleSheetMineActivity.this,
								MyCommentWriteSS.class);
						SaleSheetMineActivity.this.startActivity(i);
					}
				});
				xq.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View arg0) {
						ConstantValue.myss = ss;
						ConstantValue.ssdnos = ss.getCSaleSheetno();
						Intent i = new Intent(SaleSheetMineActivity.this,
								SaleSheetDetailMineActivity.class);
//						Bundle bundle=new Bundle();
//						bundle.putSerializable("ss", ss);
						i.putExtra("ss", ss);
						SaleSheetMineActivity.this.startActivity(i);
					}
				});
				ddid.setText("订单号：" + ss.getCSaleSheetno());
				tprice.setText("总价："
						+ ss.getFLastMoney().setScale(2,
								BigDecimal.ROUND_HALF_UP));
				String[] states = ss.getStat_Name().split(",");
				
				if(ss.getStat_Id().trim().equals("00")){
					sslist_fukuan.setVisibility(View.VISIBLE);

					sslist_pingjia.setVisibility(View.GONE);
				}else{
					sslist_fukuan.setVisibility(View.GONE);

					sslist_pingjia.setVisibility(View.VISIBLE);
				}
				
//				switch (ss.getStat_Id().trim()) {
//				case "00":
//					sslist_fukuan.setVisibility(View.VISIBLE);
//
//					sslist_pingjia.setVisibility(View.GONE);
//					break;
//				// case "01":
//				// ddstate.setText("到货付款");
//				// sslist_fukuan.setVisibility(View.VISIBLE);
//				//
//				// sslist_pingjia.setVisibility(View.GONE);
//				// break;
//				// case "01":
//				// sslist_fukuan.setVisibility(View.VISIBLE);
//				// sslist_pingjia.setVisibility(View.GONE);
//				// break;
//				// case "11":
//				// break;
//				default:
//					sslist_fukuan.setVisibility(View.GONE);
//
//					sslist_pingjia.setVisibility(View.VISIBLE);
//					break;
//				}

				ddriqi.setText("日期：" + ss.getDSaleDate());
				if (states.length == 2) {
					ddstate.setText("付款状态：" + states[0]);
					ssitem_status_fahuo.setText("发货状态：" + states[1]);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return view;
		}

		@Override
		public long getItemId(int arg0) {
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public int getCount() {
			return ConstantValue.SSList.size();
		}
	}

	public class GetMyHistorySaleSheetTask extends
			AsyncTask<Context, ProgressBar, Integer> {
		Context context;
		MyDoneAdapter myDoneAdapter;
		Socket s = null;// 锟斤拷锟斤拷Socket锟斤拷锟斤拷锟斤拷
		DataOutputStream dout = null;// 锟斤拷锟斤拷锟�
		DataInputStream din = null;// 锟斤拷锟斤拷锟斤拷

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			lssalesheet.setVisibility(View.VISIBLE);
			salesheet.setVisibility(View.GONE);
			// PromptManager.closeProgressDialog();
			switch (result) {
			case -3:
				PromptManager.showMyToast(context, "数据异常");
				break;
			case -2: // 查询失败
				PromptManager.showMyToast(context, "查询失败");
				break;
			case 0:
				PromptManager.showMyToast(context, "没有记录");// 没有记录
				break;
			case 1:
				myDoneAdapter = new MyDoneAdapter();
				lssalesheet.setAdapter(myDoneAdapter);
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
		protected Integer doInBackground(Context[] arg0) {
			// TODO Auto-generated method stub
			this.context = arg0[0];

			ConstantValue.SSList.clear();

			try {
				//
				String userNo = ConstantValue.userinfo.getUserNo();
				String msg = "*AIS|RQ|OD03|01|" + userNo + "|AIE#";
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", context.getString(R.string.url_od01));
				map.put("dataType", "text");
				map.put("data", msg);
				RequestVo vo = new RequestVo(R.string.url_od01, context, map);
				String result = NetUtil.post(vo);
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");

				if (temp[0].equalsIgnoreCase("*AIS") && temp[1].equals("RS")
						&& temp[2].equals("OD03")) {
					Log.i("json", temp[4]);
					if ("null".equalsIgnoreCase(temp[4].trim())) {
						return 0;// 没有记录
					} else if ("err".equalsIgnoreCase(temp[4].trim())) {
						return -2;// 查询失败
					}

					Gson gson = new Gson();
					ArrayList<SaleSheet> sslist = gson.fromJson(temp[4],
							new TypeToken<List<SaleSheet>>() {
							}.getType());
					if (sslist != null && sslist.size() > 0) {
						ConstantValue.SSList.addAll(sslist);
						Log.i("goodlist", "" + ConstantValue.SSList.size());
						return 1;
					} else {
						return -3;// 数据异常
					}

				}
				return 0;// 网络异常

			} catch (Exception e) {
				return 0;// 网络异常
			}
		}
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.salesheet_mine_back:
			finish();
			break;

		default:
			break;
		}
	}
}
