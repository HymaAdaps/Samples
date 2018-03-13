package com.adaps.ain043.samples.appupdater;

import android.content.ActivityNotFoundException;
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
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.adaps.ain043.samples.R;
import com.adaps.ain043.samples.appupdater.enums.AppUpdaterError;
import com.adaps.ain043.samples.appupdater.objects.Update;
import com.adaps.ain043.samples.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        this.context = this;
        libraryPreferences = new LibraryPreferences(context);
        checkForAppStoreUpdate();
    }

    private void checkForAppStoreUpdate() {
        AppUpdaterUtils appUpdaterUtils = new AppUpdaterUtils(this)
                .withListener(new AppUpdaterUtils.UpdateListener() {
                    @Override
                    public void onSuccess(Update update, Boolean isUpdateAvailable) {
//                        isUpdateAvail = isUpdateAvailable;
                        if (isUpdateAvailable) {
                            Log.e(TAG, "Latest Update " + update.getLatestVersion());
                            showAppUpdateDialog(update.getLatestVersion());
                        } else {
                            Toast.makeText(MainActivity.this, "No update availabble", Toast.LENGTH_SHORT).show();
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

    private LibraryPreferences libraryPreferences;

    private void showAppUpdateDialog(String version) throws NumberFormatException {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this)
                    .setTitle("Update Available")
                    .setMessage("A new version " + version + " of this app is available.")
                    .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=packageName")));
                                    } catch (ActivityNotFoundException e) {
                                        e.printStackTrace();

//                                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.toString()));
//                                        context.startActivity(intent);
                                    }

                                }
                            }
                    )
                    .setNeutralButton("Don't show again", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    libraryPreferences.setAppUpdaterShow(false);
                                }
                            }
                    )
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
            builder.setCancelable(false);
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
