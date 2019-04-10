package com.subashpoudel.bookreader.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.subashpoudel.bookreader.R;
import com.subashpoudel.bookreader.pdf.TableOfContentItem;
import com.subashpoudel.bookreader.utils.SharedPreferenceUtil;
import java.util.ArrayList;

/**
 * Created by Subash on 9/1/16.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
  private static final int VIEW_TYPE_SECTION_HEADER = 0;
  private static final int VIEW_TYPE_CONTENT = 1;
  private static final int VIEW_TYPE_BOOKMARK = 2;
  private static final String TAG = "RecyclerAdapter";
  private int itemCount;
  private LayoutInflater inflater;
  private ArrayList<TableOfContentItem> tableOfContents;
  private OnItemClicked onItemClicked;
  ArrayList<String> bookmarks = null;

  public RecyclerAdapter(ArrayList<TableOfContentItem> tableOfContents, Context context,
      OnItemClicked listener) {
    this.onItemClicked = listener;
    this.tableOfContents = tableOfContents;
    bookmarks = SharedPreferenceUtil.getInstance().getBookmarks();
    int noOfBookmarks = bookmarks.size();
    itemCount = tableOfContents.size() + 1 + noOfBookmarks;
    inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public TableOfContentItem tableOfContent;

    public ViewHolderContent(View itemView) {
      super(itemView);
      textViewTitle = (TextView) itemView.findViewById(R.id.tv_title);
      textViewPageNumber = (TextView) itemView.findViewById(R.id.tv_page_number);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          onItemClicked.onItemClicked(tableOfContent);
        }
      });
    }

    public void setData(TableOfContentItem tableOfContent) {
      this.tableOfContent = tableOfContent;
      textViewTitle.setText(tableOfContent.getTitle());
      int visiblePageNumber = tableOfContent.getVisiblePageNumber();
      if (visiblePageNumber > 0) {
        textViewPageNumber.setText(visiblePageNumber + "");
      }
    }
  }

  private class ViewHolderBookMark extends RecyclerView.ViewHolder {
    public TextView textViewTitle;
    public String pageNumber;
    public Button deleteButton;

    public ViewHolderBookMark(View itemView) {
      super(itemView);
      textViewTitle = (TextView) itemView.findViewById(R.id.tv_bookmark_title);
      deleteButton = (Button) itemView.findViewById(R.id.btn_delete);
      itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          onItemClicked.onBookMarkClicked(pageNumber);
        }
      });
      deleteButton.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View view) {
          onItemClicked.onBookmarkDeleteClicked(pageNumber);
        }
      });
    }

    public void setData(String pageNumber) {
      this.pageNumber = pageNumber;
      textViewTitle.setText("Book Mark " + pageNumber);
    }
  }

  @Override public int getItemViewType(int position) {
    // Just as an example, return 0 or 2 depending on position
    // Note that unlike in ListView adapters, types don't have to be contiguous
    int viewType = VIEW_TYPE_CONTENT;
    if (position < tableOfContents.size()) {
      viewType = VIEW_TYPE_CONTENT;
    } else if (position == tableOfContents.size()) {
      viewType = VIEW_TYPE_SECTION_HEADER;
    } else {
      viewType = VIEW_TYPE_BOOKMARK;
    }
    return viewType;
  }

  @Override public int getItemCount() {
    return itemCount;
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    RecyclerView.ViewHolder holder = null;
    View view;
    switch (viewType) {
      case VIEW_TYPE_CONTENT:
        view = inflater.inflate(R.layout.item_recycler_view_table_of_contents, parent, false);
        holder = new ViewHolderContent(view);
        break;
      case VIEW_TYPE_SECTION_HEADER:
        view = inflater.inflate(R.layout.item_recycler_view_section_header, parent, false);
        holder = new ViewHolderSectionHeader(view);
        break;
      case VIEW_TYPE_BOOKMARK:
        view = inflater.inflate(R.layout.item_recycler_view_bookmark, parent, false);
        holder = new ViewHolderBookMark(view);
        break;
    }
    return holder;
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (position < tableOfContents.size()) {
      ViewHolderContent vh = (ViewHolderContent) holder;
      vh.setData(tableOfContents.get(position));
    } else if (position == tableOfContents.size()) {
      ViewHolderSectionHeader vh = (ViewHolderSectionHeader) holder;
      vh.setData("Book Marks");
    } else {
      ViewHolderBookMark vh = (ViewHolderBookMark) holder;
      int dataPosition = itemCount - position - 1;
      vh.setData(bookmarks.get(dataPosition));
    }
  }

  public interface OnItemClicked {
    void onItemClicked(TableOfContentItem tableOfContent);

    void onBookMarkClicked(String pageNumber);

    void onBookmarkDeleteClicked(String pageNumber);
  }
}
