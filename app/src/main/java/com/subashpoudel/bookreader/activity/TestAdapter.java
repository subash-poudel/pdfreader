package com.subashpoudel.bookreader.activity;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.subashpoudel.bookreader.R;
import com.subashpoudel.bookreader.model.Book;
import com.subashpoudel.bookreader.pdf.TableOfContentItem;
import java.util.List;

/**
 * Created by Subash on 9/2/16.
 */

public class TestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final int VIEW_TYPE_HEADER = 0;
  private static final int VIEW_TYPE_SECTION_HEADER = 1;
  private static final int VIEW_TYPE_CONTENT = 2;
  private static final String TAG = "RecyclerAdapter";
  private int itemCount;
  private LayoutInflater inflater;
  private List<String> items;

  public TestAdapter(List<String> items, Context context) {
    itemCount = items.size();
    this.items = items;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  private class ViewHolderHeader extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView textViewAuthor;
    public TextView textViewBookName;

    public ViewHolderHeader(View itemView) {
      super(itemView);
      imageView = (ImageView) itemView.findViewById(R.id.image_view_book_preview);
      textViewAuthor = (TextView) itemView.findViewById(R.id.tv_author_name);
      textViewBookName = (TextView) itemView.findViewById(R.id.tv_book_name);
    }

    public void setData(Book book) {
      Bitmap preview = book.getPreview();
      if (preview != null) {
        imageView.setImageBitmap(preview);
      }
      textViewAuthor.setText(book.getAuthor());
      textViewBookName.setText(book.getName());
    }
  }

  private class ViewHolderSectionHeader extends RecyclerView.ViewHolder {
    public TextView textViewSectionHeader;

    public ViewHolderSectionHeader(View itemView) {
      super(itemView);
      textViewSectionHeader = (TextView) itemView.findViewById(R.id.tv_section_header);
    }

    public void setData(String text) {
      textViewSectionHeader.setText(text);
    }
  }

  private class ViewHolderContent extends RecyclerView.ViewHolder {
    public TextView textViewTitle;
    public TextView textViewPageNumber;

    public ViewHolderContent(View itemView) {
      super(itemView);
      textViewTitle = (TextView) itemView.findViewById(R.id.tv_title);
      textViewPageNumber = (TextView) itemView.findViewById(R.id.tv_page_number);
    }

    public void setData(TableOfContentItem tableOfContent) {
      textViewTitle.setText(tableOfContent.getTitle());
      textViewPageNumber.setText(tableOfContent.getActualPageNumber() + "");
    }
  }

  @Override public int getItemViewType(int position) {
    // Just as an example, return 0 or 2 depending on position
    // Note that unlike in ListView adapters, types don't have to be contiguous
    int viewType = VIEW_TYPE_CONTENT;
    if (position == 0) {
      viewType = VIEW_TYPE_HEADER;
    } else if (position == 1) {
      viewType = VIEW_TYPE_SECTION_HEADER;
    }
    return viewType;
  }

  @Override public int getItemCount() {
    return itemCount;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    RecyclerView.ViewHolder holder;
    View view = inflater.inflate(R.layout.item_recycler_view_section_header, parent, false);
    holder = new ViewHolderSectionHeader(view);
    return holder;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    Log.e(TAG, "onBindViewHolder: " + position);
    ViewHolderSectionHeader vh = (ViewHolderSectionHeader) holder;
    vh.setData(items.get(position));
  }
}

