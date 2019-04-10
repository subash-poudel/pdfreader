package com.subashpoudel.bookreader.pdf;

import android.os.AsyncTask;
import com.subashpoudel.bookreader.utils.FileUtils;
import java.io.File;
import java.util.List;

/**
 * Created by Subash on 8/31/16.
 */

public class SearchFileAsyncTask extends AsyncTask<String, Void, Void> {
  private OnFileSearched onFileSearched;
  private List<File> files;

  public SearchFileAsyncTask(OnFileSearched onFileSearched) {
    this.onFileSearched = onFileSearched;
  }

  @Override protected void onPreExecute() {

  }

  @Override protected Void doInBackground(String... extensions) {
    files = FileUtils.getAllFiles(extensions);
    return null;
  }

  @Override protected void onPostExecute(Void aVoid) {
    onFileSearched.files(files);
  }

  public interface OnFileSearched {
    void files(List<File> files);
  }
}
