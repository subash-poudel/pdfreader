package com.subashpoudel.bookreader.utils;

import android.graphics.Bitmap;

/**
 * Created by Subash on 8/29/16.
 */

public class BitmapUtils {
  private static final int NO_OF_BITMAPS = 3;
  private static Bitmap[] bitmaps = new Bitmap[3];

  public static void init(int width, int height) {
    for (int i = 0; i < NO_OF_BITMAPS; i++) {
      Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
      bitmaps[i] = bitmap;
    }
  }

  public static Bitmap getBitmapForPosition(int position) {
    return bitmaps[position % NO_OF_BITMAPS];
  }
}
