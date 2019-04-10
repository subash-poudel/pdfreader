package com.subashpoudel.bookreader.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;
import com.subashpoudel.bookreader.R;
import com.subashpoudel.bookreader.utils.SharedPreferenceUtil;

public class IntroActivity extends AppIntro {

  public static Intent createIntent(Context context) {
    return new Intent(context, IntroActivity.class);
  }

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    // Add your slide's fragments here.
    // AppIntro will automatically generate the dots indicator and buttons.
    //addSlide(first_fragment);
    //addSlide(second_fragment);
    //addSlide(third_fragment);
    //addSlide(fourth_fragment);

    // Instead of fragments, you can also use our default slide
    // Just set a title, description, background and image. AppIntro will do the rest.
    int blue = Color.parseColor("#00BCD4");
    int green = Color.parseColor("#4CAF50");
    int red = Color.parseColor("#E91E63");
    int yellow = Color.parseColor("#00BCD4");


    addSlide(AppIntroFragment.newInstance("Zoom", "Two finger zoom when in reader.",
        R.drawable.ic_zoom_in, blue));
    addSlide(AppIntroFragment.newInstance("Drag", "When zoomed use one finger to drag around",
        R.drawable.ic_drag_arrow, red));
    addSlide(AppIntroFragment.newInstance("Next Page", "Swipe up vertically on the screen",
        R.drawable.ic_arrow_up, green));
    addSlide(AppIntroFragment.newInstance("Previous Page", "Swipe down vertically the screen",
        R.drawable.ic_arrow_down, yellow));
    addSlide(AppIntroFragment.newInstance("Book Marks", "Long press any where on the screen to add bookmark.",
        R.drawable.ic_bookmark, blue));

    // OPTIONAL METHODS
    // Override bar/separator color.
    setBarColor(Color.parseColor("#3F51B5"));
    setSeparatorColor(Color.parseColor("#2196F3"));

    // Hide Skip/Done button.
    showSkipButton(true);
    setProgressButtonEnabled(true);

    // Turn vibration on and set intensity.
    // NOTE: you will probably need to ask VIBRATE permisssion in Manifest.
    setVibrate(false);
  }

  @Override public void onSkipPressed(Fragment currentFragment) {
    super.onSkipPressed(currentFragment);
    // Do something when users tap on Skip button.
    finishTutorial();
  }

  @Override public void onDonePressed(Fragment currentFragment) {
    super.onDonePressed(currentFragment);
    // Do something when users tap on Done button.
    finishTutorial();
  }

  @Override
  public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
    super.onSlideChanged(oldFragment, newFragment);
    // Do something when the slide changes.
  }

  private void finishTutorial() {
    SharedPreferenceUtil.getInstance().setTutorialCompleted(true);
    finish();
    Intent intent = DashBoardActivity.createIntent(this);
    startActivity(intent);
  }
}