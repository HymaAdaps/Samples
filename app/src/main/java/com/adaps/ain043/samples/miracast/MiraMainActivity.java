package com.adaps.ain043.samples.miracast;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AlertDialog.Builder;
import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.adaps.ain043.samples.R;
import com.adaps.ain043.samples.appupdater.MainActivity;
import com.adaps.ain043.samples.databinding.ActivityMiraMainBinding;
import com.adaps.ain043.samples.databinding.ActivityTempMiraBinding;
import com.adaps.ain043.samples.miracast.utils.Alert;
import com.adaps.ain043.samples.miracast.utils.GooglePlay;


public class MiraMainActivity extends BaseActivity implements OnClickListener {
    private static long back_pressed;
    private ActivityMiraMainBinding binding;

    private final String IS_RATE = "click_rate";
    private final String TAG = getClass().getName();
    //    IabHelper mHelper;
    SCREEN screen;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mira_main);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(R.string.app_name));
        }
        initPurchase();
        checkWifi();
        binding.tvCaution.setText(Html.fromHtml("<p><strong>Caution:</strong> some android device don't support screen cast</p>"));
        binding.btnConnect.setOnClickListener(this);
        CoreApplication.getInstance().tinyDB.putBoolean("click_rate", true);
    }

    class C09981 implements DialogInterface.OnClickListener {
        C09981() {
        }

        public void onClick(DialogInterface dialog, int which) {
        }
    }

//    class C10003 implements IabHelper.OnIabSetupFinishedListener {
//        C10003() {
//        }
//
//        public void onIabSetupFinished(IabResult result) {
//            Log.d(MiraMainActivity.this.TAG, "Setup finished.");
//            if (!result.isSuccess()) {
//                MiraMainActivity.this.mHelper = null;
//            } else if (MiraMainActivity.this.mHelper != null) {
//            }
//        }
//    }


    class C10035 implements DialogInterface.OnClickListener {
        C10035() {
        }

        public void onClick(DialogInterface dialog, int id) {
            CoreApplication.getInstance().tinyDB.putBoolean("click_rate", true);
            MiraMainActivity.this.exit();
        }
    }

    class C10046 implements DialogInterface.OnClickListener {
        C10046() {
        }

        public void onClick(DialogInterface dialog, int id) {
//            FabricUtils.rateEvent(false);
            CoreApplication.getInstance().tinyDB.putBoolean("click_rate", true);
            GooglePlay.open(MiraMainActivity.this, MiraMainActivity.this.getApplicationContext().getPackageName());
            MiraMainActivity.this.exit();
        }
    }

    enum SCREEN {
        ABOUT,
        GUIDE
    }


    private boolean checkWifi() {
        final WifiManager wifi = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (wifi.isWifiEnabled()) {
            return true;
        }
        TypedValue typedValue = new TypedValue();
//        getTheme().resolveAttribute(16843605, typedValue, true);
        new Builder(this).setTitle((CharSequence) "WARNING").setCancelable(false).setMessage((CharSequence) "App can not use if without WIFI, please it turn on.").setIcon(typedValue.resourceId).setPositiveButton((CharSequence) "Turn on wifi", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                wifi.setWifiEnabled(true);
                dialogInterface.dismiss();
            }
        }).setNegativeButton(getString(R.string.close), new C09981()).show();
        return false;
    }


    private void initPurchase() {
        Log.d(this.TAG, "Creating IAB helper.");
//        this.mHelper = new IabHelper(this, CoreApplication.getInstance().stringBillingJNI());
//        this.mHelper.enableDebugLogging(false);
//        Log.d(this.TAG, "Starting setup.");
//        this.mHelper.startSetup(new C10003());
    }


    public void onDestroy() {
        super.onDestroy();
//        Log.d(this.TAG, "Destroying helper.");
//        if (this.mHelper != null) {
//            this.mHelper.disposeWhenFinished();
//            this.mHelper = null;
//        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 10001 && this.mHelper != null) {
//            if (this.mHelper.handleActivityResult(requestCode, resultCode, data)) {
//                Log.d(this.TAG, "onActivityResult handled by IABUtil.");
//            } else {
//                super.onActivityResult(requestCode, resultCode, data);
//            }
//        }
    }

    public void onBackPressed() {
        if (!CoreApplication.getInstance().tinyDB.getBoolean("click_rate") /*&& FirebaseRemoteConfig.getInstance().getBoolean("RATE")*/) {
            new Builder(this).setMessage(getString(R.string.do_you_like)).setCancelable(true).setNegativeButton((CharSequence) "Rate it", new C10046()).setPositiveButton((CharSequence) "Exit", new C10035()).create().show();
        } else if (back_pressed + 2000 > System.currentTimeMillis()) {
            exit();
        } else {
            Toast.makeText(getBaseContext(), R.string.msg_press_one_again, Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

    public void onClick(View view) {
        openWiFiDisplay();
    }

    public void openWiFiDisplay() {
        try {
            startActivity(new Intent("android.settings.WIFI_DISPLAY_SETTINGS"));
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                startActivity(new Intent("com.samsung.wfd.LAUNCH_WFD_PICKER_DLG"));
            } catch (Exception e) {
                try {
                    startActivity(new Intent("android.settings.CAST_SETTINGS"));
                } catch (Exception e2) {
                    Alert.toast(this, "Your device do not supported");
                }
            }
        }
    }
}
