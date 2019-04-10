package com.subashpoudel.bookreader.pdf;

/**
 * Created by Subash on 8/31/16.
 */

public class TableOfContentItem {
  public String title;
  public int actualPageNumber;
  public int visiblePageNumber;

  public TableOfContentItem(String title, int actualPageNumber, int visiblePageNumber) {
    setTitle(title);
    setActualPageNumber(actualPageNumber);
    setVisiblePageNumber(visiblePageNumber);
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    if (title == null) {
      this.title = " ";
      return;
    }
    this.title = title;
  }

  public int getActualPageNumber() {
    return actualPageNumber;
  }

  public void setActualPageNumber(int actualPageNumber) {
    if (actualPageNumber < 0) {
      actualPageNumber = 0;
    }
    this.actualPageNumber = actualPageNumber;
  }

  public int getVisiblePageNumber() {
    return visiblePageNumber;
  }

  public void setVisiblePageNumber(int visiblePageNumber) {
    if (visiblePageNumber < 0) {
      visiblePageNumber = 0;
    }
    this.visiblePageNumber = visiblePageNumber;
  }

  @Override public String toString() {
    return title + " pno=" + actualPageNumber;
  }
}
