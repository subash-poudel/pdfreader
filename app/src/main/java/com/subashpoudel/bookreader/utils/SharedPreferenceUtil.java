package com.subashpoudel.bookreader.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;
import java.util.ArrayList;
import java.util.Arrays;

public class SharedPreferenceUtil {

  //Shared Preferences
  public static final String SHARED_PREF_NAME = "BOOK_PREFERENCES";
  public static final String TUTORIAL_COMPLETED = "TUTORIAL_COMPLETED";
  public static final String LAST_READ_PAGE = "LAST_READ_PAGE";
  public static final String BOOKMARKS = "BOOKMARKS";
  public static final String BOOKMARK_SEPARATOR = ",";
  private static SharedPreferenceUtil preferenceUtility;
  public SharedPreferences preferences;
  private float scaleFactor = 1.0f;

  private SharedPreferenceUtil(Context context) {
    preferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
  }

  public static SharedPreferenceUtil getInstance() {
    return preferenceUtility;
  }

  public static void init(Context context) {
    preferenceUtility = new SharedPreferenceUtil(context);
  }

  public void setTutorialCompleted(boolean completed) {
    SharedPreferences.Editor editor = preferences.edit();
    editor.putBoolean(TUTORIAL_COMPLETED, completed);
    editor.apply();
  }

  public boolean isTutorialComplete() {
    return preferences.getBoolean(TUTORIAL_COMPLETED, false);
  }

  public ArrayList<String> getBookmarks() {
    String str = preferences.getString(BOOKMARKS, null);
    ArrayList<String> bookmarks = new ArrayList<>();
    if(!TextUtils.isEmpty(str)){
      String[] temp = str.split(BOOKMARK_SEPARATOR);
      bookmarks.addAll(Arrays.asList(temp));
    }
    Log.e("Bookmark", str + "");
    return bookmarks;
  }

  public void addBookMarksPage(String pageNumber) {
    ArrayList<String> previousBookmarks = getBookmarks();
    if (previousBookmarks.contains(pageNumber)){
      // same page can be bookmarked only once
      return;
    }
    previousBookmarks.add(pageNumber);
    SharedPreferences.Editor editor = preferences.edit();
    String str = TextUtils.join(BOOKMARK_SEPARATOR, previousBookmarks);
    editor.putString(BOOKMARKS, str);
    editor.apply();
    Log.e("Bookmark add", str);
  }

  public void removeBookMarksPage(String pageNumber){
    if (pageNumber == null){
      Log.e("SharedPref", "Null sent to removeBookmarks");
      return;
    }
    ArrayList<String> previousBookmarks = getBookmarks();
    if (!previousBookmarks.contains(pageNumber)){
      // return if there is no bookmark saved
      return;
    }
    previousBookmarks.remove(pageNumber);
    SharedPreferences.Editor editor = preferences.edit();
    String str = TextUtils.join(BOOKMARK_SEPARATOR, previousBookmarks);
    editor.putString(BOOKMARKS, str);
    editor.apply();
    Log.e("Bookmark remove", str);
  }

  public int getLastReadPage() {
    return preferences.getInt(LAST_READ_PAGE, 0);
  }

  public void setLastReadPage(int page) {
    SharedPreferences.Editor editor = preferences.edit();
    editor.putInt(LAST_READ_PAGE, page);
    editor.apply();
  }
}
