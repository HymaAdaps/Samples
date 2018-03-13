package com.adaps.ain043.samples.miracast.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionManager {
    public static final int EXTERNAL_STORAGE = 900;
    private static PermissionManager permissionManager;
    private Activity mActivity;

    class C10191 implements OnClickListener {
        C10191() {
        }

        public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.parse("package:" + PermissionManager.this.mActivity.getPackageName()));
            PermissionManager.this.mActivity.startActivityForResult(intent, 9012);
        }
    }

    public static PermissionManager getInstance() {
        if (permissionManager == null) {
            permissionManager = new PermissionManager();
        }
        return permissionManager;
    }

    public PermissionManager withActivity(Activity context) {
        this.mActivity = context;
        return permissionManager;
    }

    private boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this.mActivity, "android.permission.READ_EXTERNAL_STORAGE") == 0 && ContextCompat.checkSelfPermission(this.mActivity, "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            return true;
        }
        return false;
    }

    @TargetApi(23)
    public boolean requestStoragePermission() {
        if (checkStoragePermission()) {
            return false;
        }
        boolean GPSPermission = this.mActivity.shouldShowRequestPermissionRationale("android.permission.READ_EXTERNAL_STORAGE") && this.mActivity.shouldShowRequestPermissionRationale("android.permission.WRITE_EXTERNAL_STORAGE");
        if (GPSPermission) {
            ActivityCompat.requestPermissions(this.mActivity, new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, EXTERNAL_STORAGE);
            return true;
        }
        Alert.showWithOK(this.mActivity, "PERMISSION GAIN", "App can not working without read write storage permission", new C10191());
        return true;
    }
}
