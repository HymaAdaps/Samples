package com.adaps.ain043.samples.appupdater;

import android.content.DialogInterface;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.view.Display;

import com.adaps.ain043.samples.appupdater.enums.AppUpdaterError;
import com.adaps.ain043.samples.appupdater.enums.Duration;
import com.adaps.ain043.samples.appupdater.objects.Update;


public interface IAppUpdater {


    /**
     * Set the times the app ascertains that a new update is available and display a dialog, Snackbar or notification. It makes the updates less invasive. Default: 1.
     *
     * @param times every X times
     * @return this
     */
    AppUpdater showEvery(Integer times);

    /**
     * Set if the dialog, Snackbar or notification is displayed although there aren't updates. Default: false.
     *
     * @param res true to show, false otherwise
     * @return this
     */
    AppUpdater showAppUpdated(Boolean res);


    AppUpdater setIcon(@DrawableRes int iconRes);

    /**
     * Execute AppUpdater in background.
     *
     * @return this
     * @deprecated use {@link #start()} instead
     */
    AppUpdater init();

    /**
     * Execute AppUpdater in background.
     */
    void start();

    /**
     * Stops the execution of AppUpdater.
     */
    void stop();

    interface LibraryListener {
        void onSuccess(Update update);

        void onFailed(AppUpdaterError error);
    }
}
