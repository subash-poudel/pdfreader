package com.subashpoudel.bookreader.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.shockwave.pdfium.PdfDocument;
import com.shockwave.pdfium.PdfiumCore;
import com.subashpoudel.bookreader.R;
import com.subashpoudel.bookreader.adapter.DividerItemDecoration;
import com.subashpoudel.bookreader.adapter.RecyclerAdapter;
import com.subashpoudel.bookreader.model.Book;
import com.subashpoudel.bookreader.pdf.PdfActivity;
import com.subashpoudel.bookreader.pdf.PdfUtils;
import com.subashpoudel.bookreader.pdf.TableOfContent;
import com.subashpoudel.bookreader.pdf.TableOfContentItem;
import com.subashpoudel.bookreader.utils.FileUtils;
import com.subashpoudel.bookreader.utils.SharedPreferenceUtil;
import com.subashpoudel.bookreader.utils.Utility;
import java.io.File;
import java.io.IOException;

import static com.subashpoudel.bookreader.utils.FileUtils.getBasePath;

public class DashBoardActivity extends AppCompatActivity {
  private Button buttonContinueReading;
  private RecyclerView recyclerView;
  public static final String BOOK_NAME_GEETA = "shreemadbhagwadgeeta.pdf";
  public static final String TOC_GEETA = "geetatoc.json";
  private ParcelFileDescriptor fd;
  private PdfiumCore pdfiumCore;
  private PdfDocument pdfDocument;
  private Book book;
  private TableOfContent tableOfContent;
  private Toolbar toolbar;

  private ImageView imageView;
  private TextView textViewAuthor;
  private TextView textViewBookName;

  public static Intent createIntent(Context context) {
    return new Intent(context, DashBoardActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_dash_board);
    SharedPreferenceUtil util = SharedPreferenceUtil.getInstance();
    if (!util.isTutorialComplete()) {
      startActivity(IntroActivity.createIntent(this));
    }
    toolbar = (Toolbar) findViewById(R.id.my_toolbar);
    setSupportActionBar(toolbar);
    recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
    imageView = (ImageView) findViewById(R.id.image_view_book_preview);
    textViewAuthor = (TextView) findViewById(R.id.tv_author_name);
    textViewBookName = (TextView) findViewById(R.id.tv_book_name);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setItemAnimator(new DefaultItemAnimator());
    final String internalFilePath = getBasePath(this) + BOOK_NAME_GEETA;
    File f = new File(internalFilePath);
    if (!f.exists()) {
      try {
        FileUtils.copyAssets(DashBoardActivity.this, BOOK_NAME_GEETA, internalFilePath);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    setUpPdfReader(internalFilePath, TOC_GEETA);
    recyclerView.setNestedScrollingEnabled(false);
    recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider_line));
    buttonContinueReading = (Button) findViewById(R.id.button_continue_reading);
    buttonContinueReading.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        int lastReadPage = SharedPreferenceUtil.getInstance().getLastReadPage();
        Intent intent = PdfActivity.createIntent(DashBoardActivity.this, book, lastReadPage);
        startActivity(intent);
      }
    });
  }

  private void setUpPdfReader(String pdfPath, String jsonPath) {
    book = new Book(pdfPath);
    try {
      File f = new File(pdfPath);
      int mode = ParcelFileDescriptor.MODE_READ_ONLY;
      fd = ParcelFileDescriptor.open(f, mode);
      pdfiumCore = new PdfiumCore(DashBoardActivity.this);
      pdfDocument = pdfiumCore.newDocument(fd);
      PdfDocument.Meta meta = pdfiumCore.getDocumentMeta(pdfDocument);
      Bitmap bitmap = PdfUtils.getBitmap(pdfiumCore, pdfDocument, 0, 1);
      book.setPreview(bitmap);
      book.setTotalPage(pdfiumCore.getPageCount(pdfDocument));
      book.setAuthor(meta.getAuthor());
      book.setName(meta.getTitle());
      toolbar.setTitle(meta.getTitle());
      String json = Utility.readStringFromAsset(this, jsonPath);
      Log.e("json", json);
      tableOfContent = Utility.getObject(json, TableOfContent.class);
      setData(book);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void setData(Book book) {
    Bitmap preview = book.getPreview();
    if (preview != null) {
      imageView.setImageBitmap(preview);
    }
    textViewAuthor.setText(book.getAuthor());
    textViewBookName.setText(book.getName());
  }

  @Override protected void onResume() {
    super.onResume();
    setAdapter();
  }

  private void setAdapter() {
    RecyclerAdapter.OnItemClicked itemClicked = new RecyclerAdapter.OnItemClicked() {
      @Override public void onItemClicked(TableOfContentItem tableOfContent) {
        Intent intent = PdfActivity.createIntent(DashBoardActivity.this, book,
            tableOfContent.getActualPageNumber());
        Log.e("page numbers", "ac="
            + tableOfContent.getActualPageNumber()
            + " vs="
            + tableOfContent.getVisiblePageNumber());
        startActivity(intent);
      }

      @Override public void onBookMarkClicked(String pageNumber) {
        Intent intent =
            PdfActivity.createIntent(DashBoardActivity.this, book, Integer.parseInt(pageNumber));
        startActivity(intent);
      }

      @Override public void onBookmarkDeleteClicked(String pageNumber) {
        SharedPreferenceUtil util = SharedPreferenceUtil.getInstance();
        util.removeBookMarksPage(pageNumber);
        setAdapter();
      }
    };
    RecyclerAdapter adapter =
        new RecyclerAdapter(tableOfContent.getTableOfContents(), DashBoardActivity.this,
            itemClicked);
    recyclerView.setAdapter(adapter);
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_introduction:
        Intent intent = IntroActivity.createIntent(DashBoardActivity.this);
        startActivity(intent);
        break;
      default:
    }
    return true;
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    MenuInflater inflater = getMenuInflater();
    inflater.inflate(R.menu.menu_dash_board, menu);
    return true;
  }
}
