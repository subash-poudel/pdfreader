package com.subashpoudel.bookreader.customview;

import android.content.Context;
import android.util.AttributeSet;
import library.SubsamplingScaleImageView;

/**
 * Created by Subash on 9/1/16.
 */

public class CustomZoomImageView extends SubsamplingScaleImageView {
  public CustomZoomImageView(Context context, AttributeSet attr) {
    super(context, attr);
  }

  public CustomZoomImageView(Context context) {
    super(context);
  }

  //@Override public boolean onTouchEvent(@NonNull MotionEvent event) {
  //  Log.e("customzoomview", event.getPointerCount() + "");
  //  if (event.getPointerCount() == 1){
  //
  //  }
  //  return super.onTouchEvent(event);
  //}
}
