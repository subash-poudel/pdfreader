package com.subashpoudel.bookreader.adapter;

/**
 * Created by Subash on 8/23/16.
 */

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import com.subashpoudel.bookreader.R;
import com.subashpoudel.bookreader.model.BookList;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

  private Context context;
  private BookList bookList;
  private String[] listHeaders;
  private LayoutInflater infalInflater;

  public ExpandableListAdapter(Context context, String[] listHeaders, BookList bookList) {
    this.context = context;
    this.bookList = bookList;
    this.listHeaders = listHeaders;
    this.infalInflater =
        (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override public String getChild(int groupPosition, int childPosititon) {
    String str = "";
    switch (groupPosition) {
      case BookList.COMPLETED_BOOKS:
        str = bookList.getCompletedBooks().get(childPosititon).getName();
        break;
      case BookList.CONTINUE_READING_BOOKS:
        str = bookList.getReadingBooks().get(childPosititon).getName();
        break;
      case BookList.UNREAD_BOOKS:
        str = bookList.getUnreadBooks().get(childPosititon).getName();
        break;
    }
    return str;
  }

  @Override public long getChildId(int groupPosition, int childPosition) {
    return childPosition;
  }

  @Override
  public View getChildView(int groupPosition, final int childPosition, boolean isLastChild,
      View convertView, ViewGroup parent) {
    final String childText = getChild(groupPosition, childPosition);
    if (convertView == null) {
      convertView = infalInflater.inflate(R.layout.item_book_group_child, parent, false);
    }
    TextView txtListChild = (TextView) convertView.findViewById(R.id.list_child_title);
    txtListChild.setText(childText);
    return convertView;
  }

  @Override public int getChildrenCount(int groupPosition) {
    int size = 0;
    switch (groupPosition) {
      case BookList.COMPLETED_BOOKS:
        size = bookList.getCompletedBooks().size();
        break;
      case BookList.CONTINUE_READING_BOOKS:
        size = bookList.getReadingBooks().size();
        break;
      case BookList.UNREAD_BOOKS:
        size = bookList.getUnreadBooks().size();
        break;
    }
    return size;
  }

  @Override public String getGroup(int groupPosition) {
    return this.listHeaders[groupPosition];
  }

  @Override public int getGroupCount() {
    return this.listHeaders.length;
  }

  @Override public long getGroupId(int groupPosition) {
    return groupPosition;
  }

  @Override public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
      ViewGroup parent) {
    String headerTitle = getGroup(groupPosition);
    if (convertView == null) {
      convertView = infalInflater.inflate(R.layout.item_book_group, parent, false);
    }
    TextView lblListHeader = (TextView) convertView.findViewById(R.id.list_book_name);
    lblListHeader.setTypeface(null, Typeface.BOLD);
    lblListHeader.setText(headerTitle);

    return convertView;
  }

  @Override public boolean hasStableIds() {
    return false;
  }

  @Override public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }
}