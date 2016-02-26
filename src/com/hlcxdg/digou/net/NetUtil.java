package com.hlcxdg.digou.net;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import com.hlcxdg.digou.constant.ConstantIP;
import com.hlcxdg.digou.utils.PromptManager;
import com.hlcxdg.digou.vo.RequestVo;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * @author Mathew
 */
public class NetUtil {
	private static final String NOT_LOGIN = "notlogin";
	private static final String TAG = "NetUtil";
//	private static Header[] headers = new BasicHeader[11];
//	static {
//		headers[0] = new BasicHeader("Appkey", "");
//		headers[1] = new BasicHeader("Udid", "");
//		headers[2] = new BasicHeader("Os", "");
//		headers[3] = new BasicHeader("Osversion", "");
//		headers[4] = new BasicHeader("Appversion", "");
//		headers[5] = new BasicHeader("Sourceid", "");
//		headers[6] = new BasicHeader("Ver", "");
//		headers[7] = new BasicHeader("Userid", "");
//		headers[8] = new BasicHeader("Usersession", "");
//		headers[9] = new BasicHeader("Unique", "");
//		headers[10] = new BasicHeader("Cookie", "");
//
//	}

	/*http://warelucent.zicp.net
	 * post "+ConstantIP.IP+":"+ConstantIP.PORT+
	 */
	public static String post(RequestVo vo) {
		DefaultHttpClient client = new DefaultHttpClient();
		String url = ConstantIP.URLHLSevlet.concat(vo.context
				.getString(vo.requestUrl));
		PromptManager.showLogTest("Net", url);
		HttpPost post = new HttpPost(url);
//		post.setHeaders(headers);
		// 处理超时
		HttpParams httpParams = new BasicHttpParams(); //
		HttpConnectionParams.setConnectionTimeout(httpParams, 16000);
		HttpConnectionParams.setSoTimeout(httpParams, 15000);
		post.setParams(httpParams);

		try {
			if (vo.requestDataMap != null) {
				HashMap<String, String> map = vo.requestDataMap;
				ArrayList<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();
				for (Map.Entry<String, String> entry : map.entrySet()) {
					PromptManager.showLogTest("entry.getKey()", entry.getKey()
							+ ":" + entry.getValue());
					BasicNameValuePair pair = new BasicNameValuePair(
							entry.getKey(), entry.getValue());
					pairList.add(pair);
				}
				HttpEntity entity = new UrlEncodedFormEntity(pairList,
						ConstantIP.ENCODING);
				post.setEntity(entity);
			}
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity(),
						ConstantIP.ENCODING);
				PromptManager.showLogTest("Net-result", result);
				return result;
			}
		} catch (Exception e) {
			PromptManager.showLogTest(TAG + "-error", "wrongnet");
			return null;
		}
		return null;
	}

	/**
	 * 检查网络连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean hasNetwork(Context context) {
		ConnectivityManager con = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo workinfo = con.getActiveNetworkInfo();
		if (workinfo == null || !workinfo.isAvailable()) {
			PromptManager.showNoNetWork(context);
			return false;
		}
		return true;
	}

	/*
	 * 截取拆分提取字符串
	 */
	public static String[] split(String str, String splitsign) {
		int index;
		if (str == null || splitsign == null)
			return null;
		ArrayList<String> al = new ArrayList<String>();
		while ((index = str.indexOf(splitsign)) != -1) {
			al.add(str.substring(0, index));
			str = str.substring(index + splitsign.length());
		}
		al.add(str);
		return al.toArray(new String[0]);
	}
}