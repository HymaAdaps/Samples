package com.adaps.ain043.samples.miracast;

import android.content.Intent;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.adaps.ain043.samples.R;

public abstract class BaseActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.slide_in_activity, R.anim.slide_out_activity);
    }

    protected void exit() {
        if (VERSION.SDK_INT >= 16) {
            finishAffinity();
            return;
        }
        finish();
        System.exit(0);
    }

    protected void shareApp() {
        Toast.makeText(this, "Share app clicked", Toast.LENGTH_SHORT).show();
        String url = "https://play.google.com/store/apps/details?id=" + getPackageName();
//        IntentShare.with(this).chooserTitle("Select a sharing target: ").text(url).facebookBody(Uri.parse(url)).mailSubject(getString(R.string.app_name)).twitterBody(url).mailBody(url).deliver();
    }
}
