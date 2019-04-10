package com.subashpoudel.bookreader.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Subash on 8/11/16.
 */

public class FontUtils {
  private static Typeface robotoFont;

  public static void init(Context context) {
    robotoFont = Typeface.createFromAsset(context.getAssets(), "fonts/Roboto-Light.ttf");
    //robotoFont = Typeface.createFromAsset(context.getAssets(), "fonts/RobotoCondensed-BoldItalic.ttf");
  }

  public static Typeface getRobotoFont() {
    return robotoFont;
  }
}
