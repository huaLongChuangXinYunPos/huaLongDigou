package com.hl_zhaoq.digou.utils.swiperefreshlayoutdemo;

import com.hl_zhaoq.digou.R;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

/**
 * @author mrsimple
 */
public class RefreshLayout extends SwipeRefreshLayout implements
		OnScrollListener {

	/**
	 * 婊戝姩鍒版渶涓嬮潰鏃剁殑涓婃媺鎿嶄綔
	 */
	private int mTouchSlop;
	/**
	 * listview瀹炰緥
	 */
	private ListView mListView;

	/**
	 * 涓婃媺鐩戝惉鍣�, 鍒颁簡鏈�搴曢儴鐨勪笂鎷夊姞杞芥搷浣�
	 */
	private OnLoadListener mOnLoadListener;

	/**
	 * ListView鐨勫姞杞戒腑footer
	 */
	private View mListViewFooter;

	/**
	 * 鎸変笅鏃剁殑y鍧愭爣
	 */
	private int mYDown;
	/**
	 * 鎶捣鏃剁殑y鍧愭爣, 涓巑YDown涓�璧风敤浜庢粦鍔ㄥ埌搴曢儴鏃跺垽鏂槸涓婃媺杩樻槸涓嬫媺
	 */
	private int mLastY;
	/**
	 * 鏄惁鍦ㄥ姞杞戒腑 ( 涓婃媺鍔犺浇鏇村)
	 */
	private boolean isLoading = false;

	/**
	 * @param context
	 */
	public RefreshLayout(Context context) {
		this(context, null);
	}

	public RefreshLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();

		mListViewFooter = LayoutInflater.from(context).inflate(
				R.layout.listview_footer_reflashandupdate, null, false);
	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right,
			int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		// 鍒濆鍖朙istView瀵硅薄
		if (mListView == null) {
			getListView();
		}
	}

	/**
	 * 鑾峰彇ListView瀵硅薄
	 */
	private void getListView() {
		int childs = getChildCount();
		if (childs > 0) {
			View childView = getChildAt(0);
			if (childView instanceof ListView) {
				mListView = (ListView) childView;
				// 璁剧疆婊氬姩鐩戝惉鍣ㄧ粰ListView, 浣垮緱婊氬姩鐨勬儏鍐典笅涔熷彲浠ヨ嚜鍔ㄥ姞杞�
				mListView.setOnScrollListener(this);
				Log.d(VIEW_LOG_TAG, "### 鎵惧埌listview");
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.ViewGroup#dispatchTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		final int action = event.getAction();

		switch (action) {
		case MotionEvent.ACTION_DOWN:
			// 鎸変笅
			mYDown = (int) event.getRawY();
			break;

		case MotionEvent.ACTION_MOVE:
			// 绉诲姩
			mLastY = (int) event.getRawY();
			break;

		case MotionEvent.ACTION_UP:
			// 鎶捣
			if (canLoad()) {
				loadData();
			}
			break;
		default:
			break;
		}

		return super.dispatchTouchEvent(event);
	}

	/**
	 * 鏄惁鍙互鍔犺浇鏇村, 鏉′欢鏄埌浜嗘渶搴曢儴, listview涓嶅湪鍔犺浇涓�, 涓斾负涓婃媺鎿嶄綔.
	 * 
	 * @return
	 */
	private boolean canLoad() {
		return isBottom() && !isLoading && isPullUp();
	}

	/**
	 * 鍒ゆ柇鏄惁鍒颁簡鏈�搴曢儴
	 */
	private boolean isBottom() {

		if (mListView != null && mListView.getAdapter() != null) {
			return mListView.getLastVisiblePosition() == (mListView
					.getAdapter().getCount() - 1);
		}
		return false;
	}

	/**
	 * 鏄惁鏄笂鎷夋搷浣�
	 * 
	 * @return
	 */
	private boolean isPullUp() {
		return (mYDown - mLastY) >= mTouchSlop;
	}

	/**
	 * 濡傛灉鍒颁簡鏈�搴曢儴,鑰屼笖鏄笂鎷夋搷浣�.閭ｄ箞鎵цonLoad鏂规硶
	 */
	private void loadData() {
		if (mOnLoadListener != null) {
			// 璁剧疆鐘舵��
			setLoading(true);
			//
			mOnLoadListener.onLoad();
		}
	}

	/**
	 * @param loading
	 */
	public void setLoading(boolean loading) {
		isLoading = loading;
		if (isLoading) {
			mListView.addFooterView(mListViewFooter);
		} else {
			mListView.removeFooterView(mListViewFooter);
			mYDown = 0;
			mLastY = 0;
		}
	}

	/**
	 * @param loadListener
	 */
	public void setOnLoadListener(OnLoadListener loadListener) {
		mOnLoadListener = loadListener;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {

	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// 婊氬姩鏃跺埌浜嗘渶搴曢儴涔熷彲浠ュ姞杞芥洿澶�
		if (canLoad()) {
			loadData();
		}
	}

	/**
	 * 鍔犺浇鏇村鐨勭洃鍚櫒
	 * 
	 * @author mrsimple
	 */
	public static interface OnLoadListener {
		public void onLoad();
	}
}