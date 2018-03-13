//package com.adaps.ain043.samples.miracast;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import com.github.paolorotolo.appintro.AppIntro;
//import com.github.paolorotolo.appintro.AppIntroFragment;
//import com.phongphan.miracast.R;
//
//public class IntroActivity extends AppIntro {
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        addSlide(AppIntroFragment.newInstance("CAST", "Help you cast device screen to TV", R.drawable.ic_google_cast_logo, getResources().getColor(R.color.md_red_400)));
//        addSlide(AppIntroFragment.newInstance("SEARCH", "Search devices have wifi display in current network", R.drawable.ic_searcher, getResources().getColor(R.color.md_blue_400)));
//        addSlide(AppIntroFragment.newInstance("LET'S GO!", "", R.drawable.ic_rocket_start, getResources().getColor(R.color.md_green_400)));
//    }
//
//    public void onSkipPressed(Fragment currentFragment) {
//        super.onSkipPressed(currentFragment);
//        CoreApplication.getInstance().tinyDB.putBoolean("INTRO", true);
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//    }
//
//    public void onDonePressed(Fragment currentFragment) {
//        super.onDonePressed(currentFragment);
//        CoreApplication.getInstance().tinyDB.putBoolean("INTRO", true);
//        startActivity(new Intent(this, MainActivity.class));
//        finish();
//    }
//
//    public void onSlideChanged(@Nullable Fragment oldFragment, @Nullable Fragment newFragment) {
//        super.onSlideChanged(oldFragment, newFragment);
//    }
//}
