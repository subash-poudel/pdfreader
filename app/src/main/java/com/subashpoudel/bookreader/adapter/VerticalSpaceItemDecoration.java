package com.subashpoudel.bookreader.adapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Subash on 9/2/16.
 */

public class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {

  private final int mVerticalSpaceHeight;

  public VerticalSpaceItemDecoration(int mVerticalSpaceHeight) {
    this.mVerticalSpaceHeight = mVerticalSpaceHeight;
  }

  @Override public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
      RecyclerView.State state) {
    outRect.bottom = mVerticalSpaceHeight;
  }

  @Override public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
    super.onDraw(c, parent, state);
  }
}
