package com.subashpoudel.bookreader.pdf;

import java.util.ArrayList;

/**
 * Created by Subash on 9/20/16.
 */

public class TableOfContent {
  String book;
  ArrayList<TableOfContentItem> tableOfContents;

  public String getBook() {
    return book;
  }

  public void setBook(String book) {
    this.book = book;
  }

  public ArrayList<TableOfContentItem> getTableOfContents() {
    return tableOfContents;
  }

  public void setTableOfContents(ArrayList<TableOfContentItem> tableOfContents) {
    this.tableOfContents = tableOfContents;
  }
}
