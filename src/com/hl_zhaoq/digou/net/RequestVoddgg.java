package com.hl_zhaoq.digou.net;

import java.util.HashMap;

import android.content.Context;

//import com.itheima.redbaby.parser.BaseParser;

public class RequestVoddgg {
	public int requestUrl;
	public Context context;
	public String jsonData;

	public RequestVoddgg() {
	}
	

	public RequestVoddgg(int requestUrl, Context context, String jsonData) {
		super();
		this.requestUrl = requestUrl;
		this.context = context;
		this.jsonData = jsonData;
	}
}
