package com.hlcxdg.digou.utils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Random;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hlcxdg.digou.bean.UserInfo;

public class DecimalUtils {

	public static Random random = new Random();
	public static DecimalFormat df = new DecimalFormat("0.000");

	public static double getRandomDouble() {
		double result = random.nextDouble() / 10;
		return Double.parseDouble(df.format(result));
	}
}
