package com.subashpoudel.bookreader.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import com.subashpoudel.bookreader.R;

/**
 * Created by Subash on 8/29/16.
 */

public class ViewPagerAdapter extends PagerAdapter {
  private int numberOfPages;
  private LayoutInflater mInflater;
  private Point viewSize;
  private PdfiumCore pdfiumCore;
  private PdfDocument pdfDocument;

  public ViewPagerAdapter(int numberOfPages, Context context, Point viewSize, PdfiumCore pdfiumCore,
      PdfDocument pdfDocument) {
    this.numberOfPages = numberOfPages;
    this.viewSize = viewSize;
    this.pdfiumCore = pdfiumCore;
    this.pdfDocument = pdfDocument;
    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
  }

  @Override public int getCount() {
    return numberOfPages;
  }

  @Override public boolean isViewFromObject(View view, Object object) {
    return false;
  }

  @Override public Object instantiateItem(ViewGroup collection, int position) {
    ViewGroup layout =
        (ViewGroup) mInflater.inflate(R.layout.item_book_pdf, collection, false);
    ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
    imageView.setImageBitmap(getBitmapForPage(position));
    collection.addView(layout);
    return layout;
  }

  private Bitmap getBitmapForPage(int pageNumber) {
    //Bitmap bitmap = BitmapUtils.getBitmapForPosition(actualPageNumber);
    pageNumber = 0;
    Bitmap bitmap = Bitmap.createBitmap(viewSize.x, viewSize.y, Bitmap.Config.ARGB_8888);
    pdfiumCore.openPage(pdfDocument, pageNumber);
    pdfiumCore.renderPageBitmap(pdfDocument, bitmap, pageNumber, 0, 0, viewSize.x, viewSize.y);
    Log.e("page", pageNumber + "");
    return bitmap;
  }
}
