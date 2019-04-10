package com.subashpoudel.bookreader.utils;

import android.content.Context;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Subash on 8/2/16.
 * <p>
 * /Users/Subash/Desktop/filename.txt
 */
public class FileUtils {
  public static final String DESKTOP_PATH = "/Users/Subash/Desktop/";

  public static File createFile(String path) {
    File f = new File(path);
    try {
      if (f.createNewFile()) {
        return f;
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return f;
  }

  public static void writeToFile(File f, String data) {
    writeToFile(f.getAbsolutePath(), data);
  }

  public static void writeToFile(String path, String data) {
    try {
      PrintWriter writer = new PrintWriter(path, "UTF-8");
      writer.print(data);
      writer.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String convertStreamToString(InputStream is) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    StringBuilder sb = new StringBuilder();
    String line;
    try {
      while ((line = reader.readLine()) != null) {
        sb.append(line).append("\n");
      }
      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return sb.toString();
  }

  public static String getStringFromFile(String filePath) {
    File fl = new File(filePath);
    try {
      FileInputStream fin = new FileInputStream(fl);
      String ret = convertStreamToString(fin);
      fin.close();
      return ret;
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void copyAssets(Context context, String inputFile, String outputFile)
      throws IOException {
    InputStream in;
    OutputStream out;
    in = context.getAssets().open(inputFile);
    File outFile = new File(outputFile);
    out = new FileOutputStream(outFile);
    copyFile(in, out);
    in.close();
    out.flush();
    out.close();
  }

  public static void copyFile(InputStream in, OutputStream out) throws IOException {
    byte[] buffer = new byte[1024];
    int read;
    while ((read = in.read(buffer)) != -1) {
      out.write(buffer, 0, read);
    }
  }

  public static String getBasePath(Context context) {
    return context.getFilesDir() + File.separator;
  }

  public static List<File> getAllFiles(String... extension) {
    List<File> result = new ArrayList<>();
    File rootDirectory = Environment.getExternalStorageDirectory();
    getFile(rootDirectory, result, extension);
    return result;
  }

  public static void getFile(File file, List<File> list, String... extensions) {
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      if (files == null) {
        return;
      }
      for (File f : files) {
        getFile(f, list, extensions);
      }
    } else {
      for (String extension : extensions) {
        if (file.getAbsolutePath().toLowerCase().endsWith(extension)) {
          list.add(file);
          return;
        }
      }
    }
  }
}
