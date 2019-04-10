package com.subashpoudel.bookreader.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import se.emilsjolander.flipview.FlipView;

/**
 * Created by Subash on 8/8/16.
 */

public class CustomFlipView extends FlipView {
  private float x1, x2, y1, y2;
  private float MIN_DISTANCE_DP = 100;
  private float Y_ERROR_RANGE = 25;
  private float errorRange;
  private float minDistance;
  private LeftSwipeListener leftSwipeListener;

  public CustomFlipView(Context context) {
    super(context);
    init();
  }

  public CustomFlipView(Context context, AttributeSet attrs) {
    super(context, attrs);
    init();
  }

  public CustomFlipView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    init();
  }

  private void init() {
    minDistance = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MIN_DISTANCE_DP,
        getResources().getDisplayMetrics());
    errorRange = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, Y_ERROR_RANGE,
        getResources().getDisplayMetrics());
  }

  public interface LeftSwipeListener {
    void onLeftSwipe();
  }

  public void setOnLeftSwipeListener(LeftSwipeListener listener) {
    this.leftSwipeListener = listener;
  }

  //@Override public boolean onTouchEvent(MotionEvent event) {
  //  if (leftSwipeListener == null) {
  //    return super.onTouchEvent(event);
  //  }
  //  switch (event.getAction()) {
  //    case MotionEvent.ACTION_DOWN:
  //      x1 = event.getX();
  //      y1 = event.getY();
  //      break;
  //    case MotionEvent.ACTION_UP:
  //      x2 = event.getX();
  //      y2 = event.getY();
  //      float deltaX = Math.abs(x2 - x1);
  //      float deltaY = Math.abs(y2 - y1);
  //      if (x2 > x1 && deltaX > minDistance && deltaY < errorRange) {
  //        leftSwipeListener.onLeftSwipe();
  //        return false;
  //      }
  //      break;
  //  }
  //  return super.onTouchEvent(event);
  //}

  //@Override public boolean onInterceptTouchEvent(MotionEvent ev) {
  //  if (ev.getPointerCount() > 1) {
  //    return false;
  //  } else {
  //    return super.onInterceptTouchEvent(ev);
  //  }
  //}
}
