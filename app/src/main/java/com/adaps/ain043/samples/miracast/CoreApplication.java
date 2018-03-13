package com.adaps.ain043.samples.miracast;

import android.app.NotificationManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Environment;
import android.provider.Settings.Secure;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;

import com.adaps.ain043.samples.R;
import com.adaps.ain043.samples.miracast.utils.Purchase;
import com.adaps.ain043.samples.miracast.utils.TinyDB;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;

import java.io.File;

public class CoreApplication extends MultiDexApplication {
    public static final String TAG = CoreApplication.class.getSimpleName();
    private static CoreApplication ourInstance;
    public static RetryPolicy policy;
    public LayoutInflater inflater;
    private RequestQueue mRequestQueue;
    public NotificationManager notificationManager;
    public Purchase premiumPurchase = null;
    private int socketTimeout = 60000;
    public TinyDB tinyDB;


    static {
        System.loadLibrary("native-lib");
//        System.loadLibrary("libnative-lib");
    }

    public static CoreApplication getInstance() {
        return ourInstance;
    }

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public void onCreate() {
        super.onCreate();
        ourInstance = this;
        this.tinyDB = new TinyDB(this);
        policy = new DefaultRetryPolicy(this.socketTimeout, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        this.notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        this.inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public File getAppDir() {
        File storageDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "TheTreeTeam" + File.separator + getApplicationContext().getPackageName());
        if (!storageDir.exists()) {
            storageDir.mkdirs();
        }
        Log.d("App Dir", storageDir.getAbsolutePath());
        return storageDir;
    }

    public String getDeviceId() {
        String androidId = Secure.getString(getContentResolver(), "android_id");
        if (TextUtils.isEmpty(androidId)) {
            return "";
        }
        return androidId;
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        req.setTag(tag);
        req.setRetryPolicy(policy);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        req.setRetryPolicy(policy);
        getRequestQueue().add(req);
    }

    public void copyText(String text) {
//        ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(MimeTypes.BASE_TYPE_TEXT, text));
    }

    public void setupTestFAN() {
        String id = "";
        try {
            Log.d(TAG, "#FAN ADS test id: " + getSharedPreferences("FBAdPrefs", 0).getString("deviceIdHash", ""));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
