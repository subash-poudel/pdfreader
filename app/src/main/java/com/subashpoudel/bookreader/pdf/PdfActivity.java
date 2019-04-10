package com.subashpoudel.bookreader.pdf;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import com.subashpoudel.bookreader.R;
import com.subashpoudel.bookreader.adapter.PdfBookAdapter;
import com.subashpoudel.bookreader.customview.CustomFlipView;
import com.subashpoudel.bookreader.model.Book;
import com.subashpoudel.bookreader.utils.BitmapUtils;
import com.subashpoudel.bookreader.utils.SharedPreferenceUtil;
import com.subashpoudel.bookreader.utils.Utility;
import java.io.File;
import java.io.IOException;
import se.emilsjolander.flipview.FlipView;

public class PdfActivity extends AppCompatActivity {
  public static final String BOOK_PATH = "BOOK_PATH";
  public static final String PAGE_NUMBER = "PAGE_NUMBER";
  private CustomFlipView flipView;
  ParcelFileDescriptor fd;
  PdfiumCore pdfiumCore;
  PdfDocument pdfDocument;
  int noOfPages = 0;
  int width;
  int height;
  int startPageNumber;

  public static Intent createIntent(Context context, Book book, int startPageNumber) {
    Intent intent = new Intent(context, PdfActivity.class);
    intent.putExtra(BOOK_PATH, book.getPath());
    intent.putExtra(PAGE_NUMBER, startPageNumber);
    return intent;
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final String path = getIntent().getExtras().getString(BOOK_PATH);
    startPageNumber = getIntent().getExtras().getInt(PAGE_NUMBER, 0);
    flipView = (CustomFlipView) findViewById(R.id.activity_main_flipview);
    flipView.setOnLeftSwipeListener(new CustomFlipView.LeftSwipeListener() {
      @Override public void onLeftSwipe() {
        onBackPressed();
      }
    });
    flipView.setOnFlipListener(new FlipView.OnFlipListener() {
      @Override public void onFlippedToPage(FlipView v, int position, long id) {
        SharedPreferenceUtil.getInstance().setLastReadPage(position);
      }
    });
    flipView.getViewTreeObserver()
        .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
          @Override public void onGlobalLayout() {
            flipView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            Point viewSize = Utility.getViewSize(flipView);
            width = viewSize.x;
            height = viewSize.y;
            BitmapUtils.init(viewSize.x, viewSize.y);
            setUpPdfReader(path);
            Log.e("view size", viewSize.toString());
            PdfBookAdapter.OnLongPress listener = new PdfBookAdapter.OnLongPress() {
              @Override public void onLongPress() {
                int pageNumber = flipView.getCurrentPage();
                Toast.makeText(PdfActivity.this, "Book mark added " + pageNumber,
                    Toast.LENGTH_SHORT).show();
                SharedPreferenceUtil.getInstance().addBookMarksPage(pageNumber + "");
              }
            };
            PdfBookAdapter adapter =
                new PdfBookAdapter(noOfPages, PdfActivity.this, viewSize, pdfiumCore, pdfDocument,
                    listener);
            flipView.setAdapter(adapter);
            flipView.flipTo(startPageNumber);
          }
        });
  }

  private void setUpPdfReader(String pdfPath) {
    try {
      File f = new File(pdfPath);
      int mode = ParcelFileDescriptor.MODE_READ_ONLY;
      fd = ParcelFileDescriptor.open(f, mode);
      pdfiumCore = new PdfiumCore(PdfActivity.this);
      pdfDocument = pdfiumCore.newDocument(fd);
      noOfPages = pdfiumCore.getPageCount(pdfDocument);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    if (fd != null) {
      try {
        fd.close();
        pdfiumCore.closeDocument(pdfDocument);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public Animator getZoomAnimation(View view, float start, float end) {
    ObjectAnimator zoomAnimator = ObjectAnimator.ofFloat(view, "scale", 1f, 2f);
    zoomAnimator.setDuration(2000);
    return zoomAnimator;
  }

  public Animator getAlphaAnimation(View view) {
    ObjectAnimator zoomAnimator = ObjectAnimator.ofFloat(view, "alpha", 0.5f, 1f);
    zoomAnimator.setDuration(2000);
    return zoomAnimator;
  }
}
