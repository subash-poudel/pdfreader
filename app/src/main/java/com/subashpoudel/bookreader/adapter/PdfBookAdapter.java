package com.subashpoudel.bookreader.adapter;

/**
 * Created by Subash on 8/23/16.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import com.subashpoudel.bookreader.R;
import com.subashpoudel.bookreader.pdf.PdfUtils;
import library.ImageSource;
import library.SubsamplingScaleImageView;

/**
 * Created by Subash on 8/8/16.
 */

public class PdfBookAdapter extends BaseAdapter {
  private int numberOfPages;
  private LayoutInflater mInflater;
  private Point viewSize;
  private PdfiumCore pdfiumCore;
  private PdfDocument pdfDocument;
  private Context context;
  private OnLongPress listener;

  public PdfBookAdapter(int numberOfPages, Context context, Point viewSize, PdfiumCore pdfiumCore,
      PdfDocument pdfDocument, OnLongPress listener) {
    this.listener = listener;
    this.context = context;
    this.numberOfPages = numberOfPages;
    this.viewSize = viewSize;
    this.pdfiumCore = pdfiumCore;
    this.pdfDocument = pdfDocument;
    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override public int getCount() {
    return numberOfPages;
  }

  @Override public Bitmap getItem(int position) {
    return getBitmapForPage(position);
  }

  @Override public long getItemId(int position) {
    return 0;
  }

  @Override public View getView(final int position, View convertView, ViewGroup parent) {
    ViewHolder holder;
    if (convertView == null) {
      convertView = mInflater.inflate(R.layout.item_book_pdf, parent, false);
      holder = new ViewHolder();
      holder.imageView = (SubsamplingScaleImageView) convertView.findViewById(R.id.imageView);
      holder.imageView.setMaxScale(3);
      holder.imageView.setOnLongClickListener(new View.OnLongClickListener() {
        @Override public boolean onLongClick(View view) {
          listener.onLongPress();
          return true;
        }
      });
      convertView.setTag(holder);
    } else {
      holder = (ViewHolder) convertView.getTag();
    }

    holder.imageView.setImage(ImageSource.bitmap(getItem(position)));
    return convertView;
  }

  private static class ViewHolder {
    SubsamplingScaleImageView imageView;
  }

  private Bitmap getBitmapForPage(int pageNumber) {
    return PdfUtils.getBitmap(pdfiumCore, pdfDocument, pageNumber, 2);
  }

  public interface OnLongPress {
    void onLongPress();
  }
}
