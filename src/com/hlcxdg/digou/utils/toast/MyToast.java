package com.hlcxdg.digou.utils.toast;


import com.hlcxdg.digou.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 鑷畾涔夋彁绀篢oast
 * 
 * @author zihao
 */
public class MyToast extends Toast {

	public MyToast(Context context) {
		super(context);
	}

	/**
	 * 鐢熸垚涓�涓浘鏂囧苟瀛樼殑Toast
	 * 
	 * @param context
	 *            // 涓婁笅鏂囧璞�
	 * @param drawable
	 *            // 瑕佹樉绀虹殑鍥剧墖
	 * @param text
	 *            // 瑕佹樉绀虹殑鏂囧瓧
	 * @param duration
	 *            // 鏄剧ず鏃堕棿
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static MyToast makeImgAndTextToast(Context context,
			Drawable drawable, CharSequence text, int duration) {
		MyToast result = new MyToast(context);

		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.view_tips, null);

		ImageView img = (ImageView) v.findViewById(R.id.tips_icon);
		img.setBackgroundDrawable(drawable);
		TextView tv = (TextView) v.findViewById(R.id.tips_msg);
		tv.setText(text);

		result.setView(v);
		// setGravity鏂规硶鐢ㄤ簬璁剧疆浣嶇疆锛屾澶勪负鍨傜洿灞呬腑
		result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		result.setDuration(duration);

		return result;
	}

	/**
	 * 鐢熸垚涓�涓彧鏈夋枃鏈殑鑷畾涔塗oast
	 * 
	 * @param context
	 *            // 涓婁笅鏂囧璞�
	 * @param text
	 *            // 瑕佹樉绀虹殑鏂囧瓧
	 * @param duration
	 *            // Toast鏄剧ず鏃堕棿
	 * @return
	 */
	public static MyToast makeTextToast(Context context, CharSequence text,
			int duration) {
		MyToast result = new MyToast(context);

		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.view_tips, null);
		TextView tv = (TextView) v.findViewById(R.id.tips_msg);
		tv.setText(text);

		result.setView(v);
		// setGravity鏂规硶鐢ㄤ簬璁剧疆浣嶇疆锛屾澶勪负鍨傜洿灞呬腑
		result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		result.setDuration(duration);

		return result;
	}

	/**
	 * 鐢熸垚涓�涓彧鏈夊浘鐗囩殑鑷畾涔塗oast
	 * 
	 * @param context
	 *            // 涓婁笅鏂囧璞�
	 * @param drawable
	 *            // 鍥剧墖瀵硅薄
	 * @param duration
	 *            // Toast鏄剧ず鏃堕棿
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static MyToast makeImgToast(Context context, Drawable drawable,
			int duration) {
		MyToast result = new MyToast(context);
		LayoutInflater inflate = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View v = inflate.inflate(R.layout.view_tips, null);
		ImageView img = (ImageView) v.findViewById(R.id.tips_icon);
		img.setBackgroundDrawable(drawable);
		result.setView(v);
		// setGravity鏂规硶鐢ㄤ簬璁剧疆浣嶇疆锛屾澶勪负鍨傜洿灞呬腑
		result.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		result.setDuration(duration);

		return result;
	}

}