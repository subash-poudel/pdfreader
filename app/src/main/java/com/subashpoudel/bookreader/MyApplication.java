package com.subashpoudel.bookreader;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import com.subashpoudel.bookreader.utils.SharedPreferenceUtil;

/**
 * Created by Subash on 8/23/16.
 */

public class MyApplication extends Application {
  private static Context appContext;
  public static Context getContext(){
    return appContext;
  }

  @Override public void onCreate() {
    super.onCreate();
    Log.e(getClass().getSimpleName(), "called");
    appContext = this;
    SharedPreferenceUtil.init(this);
  }
}
