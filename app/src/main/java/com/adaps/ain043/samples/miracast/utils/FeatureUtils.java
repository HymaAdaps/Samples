package com.adaps.ain043.samples.miracast.utils;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build.VERSION;

public class FeatureUtils {
    private static FeatureUtils instance;

    public static FeatureUtils getInstance() {
        if (instance == null) {
            instance = new FeatureUtils();
        }
        return instance;
    }

    public boolean getGPSPresentStatus(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().hasSystemFeature("android.hardware.location.gps");
    }

    public boolean IsNFCPresent(Context context) {
        if (context == null) {
            return false;
        }
        PackageManager pm = context.getPackageManager();
        if (VERSION.SDK_INT < 17 || !pm.hasSystemFeature("android.hardware.nfc")) {
            return false;
        }
        return true;
    }

    public boolean IsCameraPresent(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    public boolean IsBluetoothPresent(Context context) {
        if (context == null) {
            return false;
        }
        return context.getPackageManager().hasSystemFeature("android.hardware.bluetooth");
    }

    public boolean isBluetoothEnabled(Context context) {
        if (context == null || !IsBluetoothPresent(context)) {
            return false;
        }
        return BluetoothAdapter.getDefaultAdapter().isEnabled();
    }
}
