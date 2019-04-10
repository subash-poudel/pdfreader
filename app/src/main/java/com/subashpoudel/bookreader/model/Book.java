package com.subashpoudel.bookreader.model;

import android.graphics.Bitmap;

/**
 * Created by Subash on 8/23/16.
 */

public class Book {

  private String path;
  private int lastReadPage;
  private int totalPage;
  private String name;
  private Bitmap preview;
  private String author;

  public void setName(String name) {
    this.name = name;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public Bitmap getPreview() {
    return preview;
  }

  public void setPreview(Bitmap preview) {
    this.preview = preview;
  }

  public Book(String path) {
    this.path = path;
    lastReadPage = 0;
    String[] components = path.split("/");
    name = components[components.length - 1];
  }

  public String getPath() {
    return path;
  }

  public int getLastReadPage() {
    return lastReadPage;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public String getName() {
    return name;
  }

}
