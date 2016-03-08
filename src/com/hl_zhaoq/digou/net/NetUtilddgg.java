package com.hl_zhaoq.digou.net;

import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.hl_zhaoq.digou.bean.ResponseBean;
import com.hl_zhaoq.digou.constant.ConstantIP;
import com.hl_zhaoq.digou.utils.PromptManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**网络工具
 * @author li
 */
public class NetUtilddgg {
	private static Header[] headers = new BasicHeader[11];
	static {
		headers[0] = new BasicHeader("Appkey", "");
		headers[1] = new BasicHeader("Udid", "");
		headers[2] = new BasicHeader("Os", "");
		headers[3] = new BasicHeader("Osversion", "");
		headers[4] = new BasicHeader("Appversion", "");
		headers[5] = new BasicHeader("Sourceid", "");
		headers[6] = new BasicHeader("Ver", "");
		headers[7] = new BasicHeader("Userid", "");
		headers[8] = new BasicHeader("Usersession", "");
		headers[9] = new BasicHeader("Unique", "");
		headers[10] = new BasicHeader("Cookie", "");

	}

	/*
	 * post
	 */
	public static String post(RequestVoddgg vo) {
		DefaultHttpClient client = new DefaultHttpClient();
		
//		String url = "http://221.221.154.34" + ":8080"
//				+ "/ddgg".concat(vo.context.getString(vo.requestUrl));
//		String url = "http://192.168.3.39" + ":8080"
//				+ "/ddgg".concat(vo.context.getString(vo.requestUrl));
		String url = "http://114.112.64.110" + ":9080"
				+ "/ddgg".concat(vo.context.getString(vo.requestUrl));
		PromptManager.showLogTest("Net", url);
		HttpPost post = new HttpPost(url);
		post.setHeaders(headers);
	
   
		try {
			if (vo.jsonData != null) {
				ArrayList<BasicNameValuePair> pairList = new ArrayList<BasicNameValuePair>();

				BasicNameValuePair pair = new BasicNameValuePair("jsonStrings",
						vo.jsonData);
				pairList.add(pair);
				HttpEntity entity = new UrlEncodedFormEntity(pairList,
						ConstantIP.ENCODING);
				post.setEntity(entity);
			}
			HttpResponse response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String result = EntityUtils.toString(response.getEntity(),
						ConstantIP.ENCODING);
				Gson gson = new Gson();
				ResponseBean resbean = gson.fromJson(result, ResponseBean.class);

				PromptManager.showLogTest("Net-result", result+"----"+resbean.toString());
				return result;
			}
		} catch (Exception e) {
//			PromptManager.showMyToast(vo.context, "网络不通畅");
			return null;
		}
		return null;
	}

	/**
	 * 检查网络连接
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
