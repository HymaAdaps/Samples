package com.adaps.ain043.samples.appupdater;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.adaps.ain043.samples.R;
import com.adaps.ain043.samples.databinding.ActivityMainBinding;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.AppUpdaterUtils;
import com.github.javiersantos.appupdater.enums.AppUpdaterError;
import com.github.javiersantos.appupdater.enums.Display;
import com.github.javiersantos.appupdater.enums.UpdateFrom;
import com.github.javiersantos.appupdater.objects.Update;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.context = this;


        checkForAppStoreUpdate();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/javiersantos/AppUpdater")));
            }
        });
//comment
//        binding.included.dialogUpdateChangelog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AppUpdater(context)
//                        //.setUpdateFrom(UpdateFrom.GITHUB)
//                        //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
//                        .setUpdateFrom(UpdateFrom.JSON)
//                        .setUpdateJSON("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update-changelog.json")
//                        .setDisplay(Display.DIALOG)
//                        .showAppUpdated(true)
//                        .start();
//            }
//        });
//
//        binding.included.dialogUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AppUpdater(context)
//                        //.setUpdateFrom(UpdateFrom.GITHUB)
//                        //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
//                        .setUpdateFrom(UpdateFrom.JSON)
//                        .setUpdateXML("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update.json")
//                        .setDisplay(Display.DIALOG)
//                        .showAppUpdated(true)
//                        .start();
//            }
//        });
//
//        binding.included.snackbarUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AppUpdater(context)
//                        //.setUpdateFrom(UpdateFrom.GITHUB)
//                        //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
//                        .setUpdateFrom(UpdateFrom.XML)
//                        .setUpdateXML("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update.xml")
//                        .setDisplay(Display.SNACKBAR)
//                        .showAppUpdated(true)
//                        .start();
//            }
//        });
//
//        binding.included.notificationUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AppUpdater(context)
//                        //.setUpdateFrom(UpdateFrom.GITHUB)
//                        //.setGitHubUserAndRepo("javiersantos", "AppUpdater")
//                        .setUpdateFrom(UpdateFrom.XML)
//                        .setUpdateXML("https://raw.githubusercontent.com/javiersantos/AppUpdater/master/app/update.xml")
//                        .setDisplay(Display.NOTIFICATION)
//                        .showAppUpdated(true)
//                        .start();
//            }
//        });
//
//        binding.included.dialogNoUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AppUpdater(context)
//                        .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
//                        .setDisplay(Display.DIALOG)
//                        .showAppUpdated(true)
//                        .start();
//            }
//        });
//
//        binding.included.snackbarNoUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AppUpdater(context)
//                        .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
//                        .setDisplay(Display.SNACKBAR)
//                        .showAppUpdated(true)
//                        .start();
//            }
//        });
//
//        binding.included.notificationNoUpdate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                new AppUpdater(context)
//                        .setUpdateFrom(UpdateFrom.GOOGLE_PLAY)
//                        .setDisplay(Display.NOTIFICATION)
//                        .showAppUpdated(true)
//                        .start();
//            }
//        });
//        binding.settings.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
//            }
//        });
    }

    private void checkForAppStoreUpdate() {
        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
//                .setUpdateFrom(UpdateFrom.GITHUB)
//                .setGitHubUserAndRepo("HymaAdaps", "Samples")
                .setUpdateFrom(UpdateFrom.JSON)
                    .setUpdateXML("https://raw.githubusercontent.com/HymaAdaps/Samples/master/app/update.json")
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
//                        isUpdateAvail = isUpdateAvailable;
                        if (isUpdateAvailable) {
                            Log.e(TAG, "Latest Update " + update.getLatestVersion());
                            showAppUpdateDialog(update.getLatestVersion());
                        } else {
                            Log.e(TAG, "AppUpdate Not Available");
                        }
                    }

                    @Override
                    public void onFailed(AppUpdaterError error) {
                        Log.e(TAG, "AppUpdate Error: " + error.toString());
                    }
                });
        appUpdaterUtils.start();
    }

    private void showAppUpdateDialog(String version) throws NumberFormatException {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Update Available")
                    .setMessage("A new version " + version + " of this app is available.")
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=packageName")));
                        }
                    });
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
