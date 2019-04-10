package com.subashpoudel.bookreader.pdf;

/**
 * Created by Subash on 8/29/16.
 */

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.widget.ImageView;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import com.subashpoudel.bookreader.R;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PdfUtils {
  public static final String TAG = "PdfUtils";

  public void openPdf(Activity activity, String path) {
    ImageView iv = (ImageView) activity.findViewById(R.id.imageView);
    int pageNum = 1;
    try {
      File f = new File(path);
      int mode = ParcelFileDescriptor.MODE_READ_ONLY;
      ParcelFileDescriptor fd = ParcelFileDescriptor.open(f, mode);
      PdfiumCore pdfiumCore = new PdfiumCore(activity);

      PdfDocument pdfDocument = pdfiumCore.newDocument(fd);

      pdfiumCore.openPage(pdfDocument, pageNum);

      int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNum);
      int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNum);

      Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
      pdfiumCore.renderPageBitmap(pdfDocument, bitmap, pageNum, 1, 0, width, height);

      iv.setImageBitmap(bitmap);

      printInfo(pdfiumCore, pdfDocument);

      pdfiumCore.closeDocument(pdfDocument); // important!
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public static Bitmap getBitmap(PdfiumCore pdfiumCore, PdfDocument pdfDocument, int pageNum, int scaleFactor) {
    pdfiumCore.openPage(pdfDocument, pageNum);
    int width = pdfiumCore.getPageWidthPoint(pdfDocument, pageNum) * scaleFactor;
    int height = pdfiumCore.getPageHeightPoint(pdfDocument, pageNum) * scaleFactor;
    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
    pdfiumCore.renderPageBitmap(pdfDocument, bitmap, pageNum, 1, 0, width, height);
    return bitmap;
  }

  public void printInfo(PdfiumCore core, PdfDocument doc) {
    PdfDocument.Meta meta = core.getDocumentMeta(doc);
    Log.e(TAG, "title = " + meta.getTitle());
    Log.e(TAG, "author = " + meta.getAuthor());
    Log.e(TAG, "subject = " + meta.getSubject());
    Log.e(TAG, "keywords = " + meta.getKeywords());
    Log.e(TAG, "creator = " + meta.getCreator());
    Log.e(TAG, "producer = " + meta.getProducer());
    Log.e(TAG, "creationDate = " + meta.getCreationDate());
    Log.e(TAG, "modDate = " + meta.getModDate());

    printBookmarksTree(core.getTableOfContents(doc), "-");
  }

  public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
    for (PdfDocument.Bookmark b : tree) {

      Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

      if (b.hasChildren()) {
        printBookmarksTree(b.getChildren(), sep + "-");
      }
    }
  }
}
