package com.adaps.ain043.samples.ScreenAds;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        Log.e("received", "yes");
        Bundle bundle = arg1.getExtras();
        if (bundle != null && bundle.containsKey("message")) {
            PlayVideoImageActivity.parent.playAtTime(arg1.getExtras());
        }
    }
}