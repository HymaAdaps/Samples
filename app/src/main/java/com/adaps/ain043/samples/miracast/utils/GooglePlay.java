package com.adaps.ain043.samples.miracast.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class GooglePlay {
    public static void open(Activity activity, String appPackageName) {
//        FabricUtils.playStoreEvent(appPackageName);
        try {
            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + appPackageName)));
        } catch (Exception e) {
            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }

    public static void openAppList(Activity activity) {
//        FabricUtils.playStoreEvent();
        String id = "The+Tree+Team";
        try {
            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://search?q=pub:" + id)));
        } catch (Exception e) {
            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("http://play.google.com/store/apps/developer?id=" + id)));
        }
    }
}
