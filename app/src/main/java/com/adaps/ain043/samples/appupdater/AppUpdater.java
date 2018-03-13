package com.adaps.ain043.samples.appupdater;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;

import com.adaps.ain043.samples.R;
import com.adaps.ain043.samples.appupdater.enums.AppUpdaterError;
import com.adaps.ain043.samples.appupdater.enums.Duration;
import com.adaps.ain043.samples.appupdater.objects.Update;


public class AppUpdater implements IAppUpdater {
    private Context context;
    private LibraryPreferences libraryPreferences;
    private String updateFrom;
    private Duration duration;
    private Integer showEvery;
    private Boolean showAppUpdated;
    private int iconResId;
    private UtilsAsync.LatestAppVersion latestAppVersion;


    public AppUpdater(Context context) {
        this.context = context;
        this.libraryPreferences = new LibraryPreferences(context);
        this.updateFrom = "GOOGLE_PLAY";
        this.duration = Duration.NORMAL;
        this.showEvery = 1;
        this.showAppUpdated = false;
        this.iconResId = R.drawable.ic_launcher_background;
    }


    @Override
    public AppUpdater showEvery(Integer times) {
        this.showEvery = times;
        return this;
    }

    @Override
    public AppUpdater showAppUpdated(Boolean res) {
        this.showAppUpdated = res;
        return this;
    }


    @Override
    public AppUpdater setIcon(@DrawableRes int iconRes) {
        this.iconResId = iconRes;
        return this;
    }


    @Override
    public AppUpdater init() {
        start();
        return this;
    }

    @Override
    public void start() {
        latestAppVersion = new UtilsAsync.LatestAppVersion(context, false, updateFrom, new LibraryListener() {
            @Override
            public void onSuccess(Update update) {
                if (context instanceof Activity && ((Activity) context).isFinishing()) {
                    return;
                }

                Update installedUpdate = new Update(UtilsLibrary.getAppInstalledVersion(context), UtilsLibrary.getAppInstalledVersionCode(context));
                if (UtilsLibrary.isUpdateAvailable(installedUpdate, update)) {
                    Integer successfulChecks = libraryPreferences.getSuccessfulChecks();
                    if (UtilsLibrary.isAbleToShow(successfulChecks, showEvery)) {
                        Log.e("Hyma", "showEvery " + showEvery);
                    }
                    libraryPreferences.setSuccessfulChecks(successfulChecks + 1);
                } else if (showAppUpdated) {
                    Log.e("Hyma", "showAppUpdated " + showAppUpdated);
                }
            }

            @Override
            public void onFailed(AppUpdaterError error) {
                if (error == AppUpdaterError.UPDATE_VARIES_BY_DEVICE) {
                    Log.e("AppUpdater", "UpdateFrom.GOOGLE_PLAY isn't valid: update varies by device.");
                } else if (error == AppUpdaterError.GITHUB_USER_REPO_INVALID) {
                    throw new IllegalArgumentException("GitHub user or repo is empty!");
                } else if (error == AppUpdaterError.XML_URL_MALFORMED) {
                    throw new IllegalArgumentException("XML file is not valid!");
                } else if (error == AppUpdaterError.JSON_URL_MALFORMED) {
                    throw new IllegalArgumentException("JSON file is not valid!");
                }
            }
        });

        latestAppVersion.execute();
    }

    @Override
    public void stop() {
        if (latestAppVersion != null && !latestAppVersion.isCancelled()) {
            latestAppVersion.cancel(true);
        }
    }

}
