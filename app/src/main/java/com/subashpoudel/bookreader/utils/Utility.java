package com.subashpoudel.bookreader.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.subashpoudel.bookreader.MyApplication;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Subash on 8/2/16.
 */
public class Utility {

  public static String listToString(List<String> list, String separator) {
    StringBuilder builder = new StringBuilder();
    for (String s : list) {
      builder.append(s);
      if (separator != null) {
        builder.append(separator);
      }
    }
    return builder.toString();
  }

  public static String getString(Object object, boolean prettyPrint) {
    if (object == null) {
      throw new NullPointerException("Cannot pass null to Utility.getString() method");
    }
    Gson gson;
    if (prettyPrint) {
      gson = new GsonBuilder().setPrettyPrinting().create();
    } else {
      gson = new Gson();
    }
    String json = gson.toJson(object);
    Log.e("json", json);
    return json;
  }

  public static <T> T getObject(String json, Class<T> cls) {
    return new Gson().fromJson(json, cls);
  }

  public static String[] getAssetsFiles(Context context, String folder) {
    AssetManager am = context.getAssets();
    String fileList[] = new String[0];
    try {
      if (folder == null) {
        folder = "";
      }
      fileList = am.list(folder);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fileList;
  }

  public static String readStringFromAsset(Context context, String path) {
    try {
      InputStream is = context.getAssets().open(path);
      ByteArrayOutputStream result = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int length;
      while ((length = is.read(buffer)) != -1) {
        result.write(buffer, 0, length);
      }
      return result.toString("UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String readString(String filePath) {
    try {
      InputStream is = new FileInputStream(filePath);
      ByteArrayOutputStream result = new ByteArrayOutputStream();
      byte[] buffer = new byte[1024];
      int length;
      while ((length = is.read(buffer)) != -1) {
        result.write(buffer, 0, length);
      }
      return result.toString("UTF-8");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void startActivity(Context context, Class cls) {
    context.startActivity(new Intent(context, cls));
  }

  public static int dpToPx(int dp) {
    DisplayMetrics displayMetrics = MyApplication.getContext().getResources().getDisplayMetrics();
    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    return px;
  }

  public static int pxToDp(int px) {
    DisplayMetrics displayMetrics = MyApplication.getContext().getResources().getDisplayMetrics();
    int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    return dp;
  }

  public static Point getViewSize(View view) {
    return new Point(view.getWidth(), view.getHeight());
  }

  public static Drawable getVectorDrawable(Context context, int resId) {
    Drawable drawable = VectorDrawableCompat.create(context.getResources(), resId, context.getTheme());
    return drawable;
  }
}
