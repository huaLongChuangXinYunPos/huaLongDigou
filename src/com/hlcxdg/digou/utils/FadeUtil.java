package com.hlcxdg.digou.utils;

import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

/**
 * 娣″叆娣″嚭鐨勫垏鎹�
 * 
 * @author Administrator
 * 
 */
public class FadeUtil {
	// 褰撳墠姝ｅ湪灞曠ず鐨勬贰鍑猴紝鍔ㄧ敾鐨勬墽琛屾椂闂�
	// 鍦ㄨ繖涓墽琛岃繃绋嬩腑锛岀浜岀晫闈㈠浜庣瓑寰呯姸鎬�
	// 绗簩涓晫闈㈡贰鍏ワ紝鍔ㄧ敾鐨勬墽琛屾椂闂�

	private static Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			View view = (View) msg.obj;
			ViewGroup parent = (ViewGroup) view.getParent();
			parent.removeView(view);
		}
	};

	/**
	 * 娣″嚭
	 * 
	 * @param view
	 *            锛氭墽琛屽姩鐢荤殑鐣岄潰
	 * @param duration
	 *            锛氭墽琛岀殑鏃堕棿
	 */
	public static void fadeOut(final View view, long duration) {
		AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
		alphaAnimation.setDuration(duration);

		// 鍔ㄧ敾鎵ц瀹屾垚涔嬪悗锛屽仛鍒犻櫎view鐨勬搷浣�
		// 澧炲姞鍔ㄧ敾鎵ц瀹屾垚涔嬪悗鐨勭洃鍚�
		alphaAnimation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// 鍋氬垹闄iew鐨勬搷浣�
				// 鑾峰彇鍒皏iew鐨勭埗瀹瑰櫒鈥斺�擱elativeLayout锛宺emoveView
				// ViewGroup parent = (ViewGroup) view.getParent();
				// parent.removeView(view);

				// 2.3妯℃嫙鍣紝鎶涘紓甯革紝浣嗘槸鍦�4.0鐨勬ā鎷熷櫒

				Message msg = Message.obtain();
				msg.obj = view;
				handler.sendMessage(msg);

			}
		});

		view.startAnimation(alphaAnimation);
	}

	/**
	 * 娣″叆
	 * 
	 * @param view
	 *            锛氭墽琛屽姩鐢荤殑鐣岄潰
	 * @param delay
	 *            锛氱瓑寰呮椂闂达紙娣″嚭鐨勭晫闈㈡墽琛屽姩鐢荤殑鏃堕棿鐩稿悓锛�
	 * @param duration
	 *            锛氭墽琛岀殑鏃堕棿
	 */
	public static void fadeIn(View view, long delay, long duration) {
		AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

		// 璁剧疆寤舵椂鐨勬椂闂�
		alphaAnimation.setStartOffset(delay);

		alphaAnimation.setDuration(duration);
		view.startAnimation(alphaAnimation);
	}
}
