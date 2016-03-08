package com.hl_zhaoq.digou.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;

public class MListView extends ListView {

	public MListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	GestureDetector gestureDetector;

	public MListView(Context context) {
		super(context);
	}

	public void setGestureDetector(GestureDetector gestureDetector) {
		this.gestureDetector = gestureDetector;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		super.onTouchEvent(ev);
		return gestureDetector.onTouchEvent(ev);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		gestureDetector.onTouchEvent(ev);
		super.dispatchTouchEvent(ev);
		return true;
	}

}
