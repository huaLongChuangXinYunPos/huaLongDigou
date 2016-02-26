package com.hlcxdg.digou.activity.comment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;

import com.google.gson.Gson;
import com.hlcxdg.digou.R;
import com.hlcxdg.digou.activity.storeac.StoreCommentWriteSE01Ac;
import com.hlcxdg.digou.activity.storeac.StoreCommentWriteSE01Ac.getStoreGoodsXq;
import com.hlcxdg.digou.bean.StoreCommentSE01xie;
import com.hlcxdg.digou.constant.ConstantValue;
import com.hlcxdg.digou.net.NetUtil;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

public class MyCommentWriteSS extends Activity implements OnClickListener {

	private Button btn_tijiao_add_pingjia;
	private Button bt_comment_back;
	private EditText et_add_comment_content;
	// private RatingBar ratingbar_comment_add;
	private RadioGroup rg_pingjia_zhi;
	private int pingjiazhi = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.ss_add_comment);
		initView();
	}

	public void initView() {
		rg_pingjia_zhi = (RadioGroup) findViewById(R.id.rg_pingjia_zhi);
		// ratingbar_comment_add = (RatingBar)
		// findViewById(R.id.ratingbar_comment_add);
		et_add_comment_content = (EditText) findViewById(R.id.et_add_comment_content);
		bt_comment_back = (Button) findViewById(R.id.bt_comment_back);
		btn_tijiao_add_pingjia = (Button) findViewById(R.id.btn_tijiao_add_pingjia);
		bt_comment_back.setOnClickListener(this);
		btn_tijiao_add_pingjia.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.bt_comment_back:
			MyCommentWriteSS.this.finish();
			break;

		case R.id.btn_tijiao_add_pingjia:
//			PromptManager.showMyToast(getApplicationContext(),
//					((RadioButton) findViewById(rg_pingjia_zhi
//							.getCheckedRadioButtonId())).getText().toString());
			commitUsersComment();
			break;

		default:
			break;
		}
	}

	float numPingfen = 0f;
	String content_add_comment;

	private void commitUsersComment() {
		// numPingfen = (int) ratingbar_comment_add.getRating();

		switch (rg_pingjia_zhi.getCheckedRadioButtonId()) {
		case R.id.rg_pingjia_you:
			numPingfen = 5;
			break;
		case R.id.rg_pingjia_liang:
			numPingfen = 4;
			break;
		case R.id.rg_pingjia_zhong:
			numPingfen = 3;
			break;
		case R.id.rg_pingjia_cha:
			numPingfen = 1;
			break;
		default:
			numPingfen = 1;
			break;
		}
		content_add_comment = et_add_comment_content.getText().toString()
				.trim();
		new CommitStoreCommentAsy().execute("");
	}

	public class CommitStoreCommentAsy extends
			AsyncTask<String, ProgressBar, Integer> {
		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			switch (result) {
			case -1:
				PromptManager.showNoNetWork(MyCommentWriteSS.this);
				break;
			case 0:
				PromptManager.showMyToast(MyCommentWriteSS.this, "失败");
				break;
			case 1:
				// shipeiqi
				PromptManager.showMyToast(MyCommentWriteSS.this, "成功");
				MyCommentWriteSS.this.finish();
				break;
			default:
				break;
			}
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			Boolean network = NetUtil.hasNetwork(MyCommentWriteSS.this);
			if (!network) {
				return -1;
			}
			PromptManager.showLogTest("PLAsynTaskdoInBackground", "PLAsynTask");
			// �����滻
			try {
				StoreCommentSE01xie scb = new StoreCommentSE01xie();
				scb.setcPaySheetNo(ConstantValue.ssdnos);
				scb.setcStoreNo(ConstantValue.myss.getcStoreNo());
				scb.setcEvaluationProjDesc(content_add_comment);
				Date d = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String datenow = sdf.format(d);
				scb.setcEvaluationTime(datenow);
				scb.setcUserNo(ConstantValue.myss.getUserNo());
				 scb.setfEvaluation(numPingfen);
				ArrayList<StoreCommentSE01xie> scbList = new ArrayList<StoreCommentSE01xie>();
				scbList.add(scb);
				
				Gson gson = new Gson();

				String msg = "*AIS|RQ|SE01|00|" + gson.toJson(scbList) + "|AIE#";
				PromptManager.showLogTest("PLAsynTaskdoInBackground-postmesssage",
						msg);
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("type", "post");
				map.put("url", MyCommentWriteSS.this
						.getString(R.string.url_storeevaluation));
				map.put("dataType", "text");
				map.put("data", msg);
				
				RequestVo vo = new RequestVo(R.string.url_storeevaluation,
						MyCommentWriteSS.this, map);
				String result = NetUtil.post(vo);
				
				PromptManager.showLogTest(
						"plAsynTaskdoInBackground-ArrayList<Goods>", "go");
				String[] temp = NetUtil.split(result, "|");
				if (temp == null) {
					PromptManager.showLogTest("enginenet", "net-result-null");
				} else {
					if ("*AIS".equalsIgnoreCase(temp[0]) && "RS".equals(temp[1])
							&& "SE01".equals(temp[2]) && "01".equals(temp[3])) {
						return 1;// [*AIS, RS, SE01, 00,//// [{"ReEvaluationFlag":"0","cEvaluationProjDesc":"�ܺã��ܺ�..","cEvaluationResponce":"лл�ݹ�..","cEvaluationTime":"2015-09-24 17:19:18","cPaySheetNo":"234211187238","cResponceTime":"2015-09-24 17:19:18","cStoreNo":"10001","cUserNo":"2015090898989","fEvaluation":"5"}],AIE#
					} else if ("00".equals(temp[3])) {
						return 0;
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return -1;
		}
	}
}