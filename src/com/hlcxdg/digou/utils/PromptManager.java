package com.hlcxdg.digou.utils;

import com.hlcxdg.digou.R;
import com.hlcxdg.digou.utils.spotdialog.SpotsDialog;
import com.hlcxdg.digou.utils.toast.MyToast;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.widget.Toast;

public class PromptManager {
	private static ProgressDialog dialog;
	private static SpotsDialog sSpotsDialog;
	public static void showMyToast(Context context, String msg) {
		MyToast.makeImgAndTextToast(context,
				context.getResources().getDrawable(R.drawable.tips_smile50), msg,
				1000).show();
	}

	/**
	 * 数据加载中
	 */
	public static void showSpotsDialog(Context context) {
		try {
			sSpotsDialog=new SpotsDialog(context);
			sSpotsDialog.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void showMyProgressDialog(Context context) {
		dialog = new ProgressDialog(context);
		dialog.setIcon(R.drawable.tips_smile);
		dialog.setTitle("进度提示：");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("正在支付中……");
		dialog.show();
	}
	/**
	 * 关闭数据加载中
	 */
	public static void closeMyProgressDialog() {
		 if (dialog != null && dialog.isShowing()) {
			 dialog.dismiss();
		 }
	}
	public static void closeSpotsDialog() {
		 if (sSpotsDialog != null && sSpotsDialog.isShowing()) {
			 sSpotsDialog.dismiss();
		 }
	}
	/**
	 * 数据加载中
	 */
	public static void showTestProgressDialog(Context context) {
		dialog = new ProgressDialog(context);
		dialog.setIcon(R.drawable.ic_launcher);
		dialog.setTitle("进度提示：");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("请等候，数据加载中……");
		dialog.show();
	}

	/**
	 * 关闭数据加载中
	 */
	public static void closeTestProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	public static void showTelProgressDialog(Context context) {
		dialog = new ProgressDialog(context);
		dialog.setIcon(R.drawable.set_shouye);
		dialog.setTitle("提示：");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		dialog.setMessage("请等候，正在为您接通电话...");
		dialog.show();
	}

	public static void closeTelProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * 无法连接到网络
	 * 
	 * @param context
	 */
	public static void showNoNetWork(final Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.app_name).setMessage("无法连接到网络")
				.setPositiveButton("开启网络", new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent(
								"android.settings.WIRELESS_SETTINGS");
						context.startActivity(intent);
					}
				}).setNegativeButton("取消", null).show();
	}

	/**
	 * 您是否要退出系统
	 * 
	 * @param context
	 */
	public static void showExitSystem(Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle(R.string.app_name)
				//
				.setMessage("您是否要退出系统")
				.setPositiveButton("是的", new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						android.os.Process.killProcess(android.os.Process
								.myPid());

					}
				})//
				.setNegativeButton("取消", null)//
				.show();

	}

	/**
	 * 错误警告对话框
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showErrorDialog(Context context, String msg) {
		new AlertDialog.Builder(context).setTitle(R.string.app_name)//
				.setMessage(msg)//
				.setNegativeButton("返回", null)//
				.show();
	}

	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}

	public static void showToast(Context context, int msgResId) {
		Toast.makeText(context, msgResId, Toast.LENGTH_SHORT).show();
	}

	// 
	private static final boolean isShow = false;

	// 测试专用true
	// private static final boolean isShow = true;

	/**
	 * 测试专用
	 * 
	 * @param context
	 * @param msg
	 */
	public static void showToastTest(Context context, String msg) {
		if (isShow) {
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
		}
	}

	public static void showLogTest(String title, String msg) {
		if (isShow) {
			Log.i(title, msg);
		}
	}

	public static void setSharedPreferenceEditor(Context context,
			String filename, String paramname, String paramvalue) {
		if (isShow) {
			SharedPreferences sp = context.getSharedPreferences(filename,
					context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString(paramname, paramvalue);
			editor.commit();
		}
	}

	public static String getSharedPreferenceEditor(Context context,
			String filename, String paramname) {
		if (isShow) {
			SharedPreferences sp = context.getSharedPreferences(filename,
					context.MODE_PRIVATE);
			return sp.getString(paramname, "");
		}
		return null;
	}
}
