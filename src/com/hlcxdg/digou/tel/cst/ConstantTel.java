package com.hlcxdg.digou.tel.cst;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

public class ConstantTel {
	public static List<Map<String, String>> contactList=new ArrayList<Map<String,String>>();
	public static String sModel = "";// �ֻ��ͺ�
	public static String iImsi = "";// �ֻ�iismi��
	public static String msPhoneNumber = "";// �ֻ�����
	public static String iAccount = "";// �˺�
	public static String beijiaonum = "";// ����
	public static int iSim_Type;// iSim_Type
	public static String PROXY_IP;// iSim_Type PROXY_PORT
	public static int PROXY_PORT;
	public static String sOSVersionRelease = "";// ϵͳ�汾
	public static String sPlatform = "Android";

	public static void androidOsInfo() {

		try {

			sModel = Build.MODEL.trim();

			sOSVersionRelease = Build.VERSION.RELEASE;

			sOSVersionRelease = sOSVersionRelease.trim();

			if ((sModel == null)
					|| (sModel.length() == 0)) {

				sModel = sPlatform + ":V" + sOSVersionRelease;

				String str = new StringBuilder(
						String.valueOf(new StringBuilder(
								String.valueOf(new StringBuilder(
										String.valueOf(new StringBuilder(
												String.valueOf(new StringBuilder(
														String.valueOf(new StringBuilder(
																String.valueOf(new StringBuilder(
																		String.valueOf(new StringBuilder(
																				String.valueOf(new StringBuilder(
																						String.valueOf(new StringBuilder(
																								String.valueOf(new StringBuilder(
																										String.valueOf(new StringBuilder(
																												String.valueOf(new StringBuilder(
																														String.valueOf(new StringBuilder(
																																"Product: ")
																																.append(Build.PRODUCT)
																																.toString()))
																														.append(", CPU_ABI: ")
																														.append(Build.CPU_ABI)
																														.toString()))
																												.append(", TAGS: ")
																												.append(Build.TAGS)
																												.toString()))
																										.append(", VERSION_CODES.BASE: 1")
																										.toString()))
																								.append(", MODEL: ")
																								.append(Build.MODEL)
																								.toString()))
																						.append(", SDK: ")
																						.append(Build.VERSION.SDK)
																						.toString()))
																				.append(", VERSION.RELEASE: ")
																				.append(Build.VERSION.RELEASE)
																				.toString()))
																		.append(", DEVICE: ")
																		.append(Build.DEVICE)
																		.toString()))
																.append(", DISPLAY: ")
																.append(Build.DISPLAY)
																.toString()))
														.append(", BRAND: ")
														.append(Build.BRAND)
														.toString()))
												.append(", BOARD: ")
												.append(Build.BOARD).toString()))
										.append(", FINGERPRINT: ")
										.append(Build.FINGERPRINT).toString()))
								.append(", ID: ").append(Build.ID).toString()))
						.append(", MANUFACTURER: ").append(Build.MANUFACTURER)
						.toString()
						+ ", USER: " + Build.USER;

				return;
			}
			sModel = sModel + ":V"
					+ sOSVersionRelease;

		} catch (Exception localException) {

			localException.printStackTrace();
		}
	}

	public static void getImsi(Context context) {
		TelephonyManager localTelephonyManager = (TelephonyManager) context
				.getSystemService(context.TELEPHONY_SERVICE);
		if (localTelephonyManager != null) {
			/* �ֻ���(��Щ�ֻ����޷���ȡ������Ϊ��Ӫ����SIM��û��д���ֻ���) */

			/* ��ȡ�ͻ�id����gsm����imsi�� */
			iImsi = localTelephonyManager.getSubscriberId();
			if (iImsi == null) {
				iSim_Type = -1;
			} else if ((!iImsi.startsWith("46000"))
					|| (!iImsi.startsWith("46002"))) {
				// �й��ƶ�
				iSim_Type = 1;
			} else if (iImsi.startsWith("46001")) {
				iSim_Type = 2;
				// ��ͨ
			} else if (iImsi.startsWith("46003")) {
				// �й�����
				iSim_Type = 3;
			} else
				iSim_Type = 100;
		}
	}

	/* ��ȡ�����ȡ�ַ��� */
	public static String[] split(String str, String splitsign) {
		int index;
		if (str == null || splitsign == null)
			return null;
		ArrayList<String> al = new ArrayList();
		while ((index = str.indexOf(splitsign)) != -1) {
			al.add(str.substring(0, index));
			str = str.substring(index + splitsign.length());
		}
		al.add(str);
		return (String[]) al.toArray(new String[0]);
	}

}
