package com.adaps.ain043.samples.ScreenAds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        if (arg1 != null) {
            PlayVideoImageActivity activity = new PlayVideoImageActivity();
            activity.playAtTime(arg1.getExtras());
        }
    }
}