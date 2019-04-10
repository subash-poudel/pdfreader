package com.subashpoudel.bookreader.model;

import java.util.ArrayList;

/**
 * Created by Subash on 8/23/16.
 */

public class BookList {
  public static final int COMPLETED_BOOKS = 0;
  public static final int UNREAD_BOOKS = 1;
  public static final int CONTINUE_READING_BOOKS = 2;

  private ArrayList<Book> unreadBooks;
  private ArrayList<Book> readingBooks;
  private ArrayList<Book> completedBooks;
  private Book lastReadBook;

  public BookList() {
    unreadBooks = new ArrayList<>();
    readingBooks = new ArrayList<>();
    completedBooks = new ArrayList<>();
    //String lastReadBookPath = SharedPreferenceUtil.getInstance().getLastReadBook();
    //if (lastReadBookPath != null) {
    //  lastReadBook = new Book(lastReadBookPath);
    //}
    //String[] bookArray = Utility.getAssetsFiles(MyApplication.getContext(), "books");
    //Set<String> completedBooksSet = SharedPreferenceUtil.getInstance().getCompletedBooks();
    //Set<String> readingBooksSet = SharedPreferenceUtil.getInstance().getReadingBooks();
    //for (String s : bookArray) {
    //  Book book = new Book(s);
    //  if (completedBooksSet != null && completedBooksSet.contains(s)) {
    //    completedBooks.add(book);
    //  } else if (readingBooksSet != null && readingBooksSet.contains(s)) {
    //    readingBooks.add(book);
    //  } else {
    //    unreadBooks.add(book);
    //  }
    //}
  }

  public Book getLastReadBook() {
    return lastReadBook;
  }

  public ArrayList<Book> getUnreadBooks() {
    return unreadBooks;
  }

  public ArrayList<Book> getReadingBooks() {
    return readingBooks;
  }

  public ArrayList<Book> getCompletedBooks() {
    return completedBooks;
  }
}
