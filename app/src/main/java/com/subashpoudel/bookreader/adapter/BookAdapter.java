package com.subashpoudel.bookreader.adapter;

/**
 * Created by Subash on 8/23/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.subashpoudel.bookreader.R;
import java.util.List;

/**
 * Created by Subash on 8/8/16.
 */

public class BookAdapter extends BaseAdapter {
  private List<CharSequence> list;
  private LayoutInflater mInflater;

  public BookAdapter(List<CharSequence> list, Context context) {
    this.list = list;
    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override public int getCount() {
    return list.size();
  }

  @Override public CharSequence getItem(int position) {
    return list.get(position);
  }

  @Override public long getItemId(int position) {
    return 0;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.item_book_reader, parent, false);
      holder = new ViewHolder();
      holder.textViewBook = (TextView) convertView.findViewById(R.id.list_item_textview);
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }
    CharSequence item = getItem(position);
    holder.textViewBook.setText(item);
    return convertView;
  }

  private static class ViewHolder {
    TextView textViewBook;
  }
}
