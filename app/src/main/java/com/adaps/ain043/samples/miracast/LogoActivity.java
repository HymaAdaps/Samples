//package com.adaps.ain043.samples.miracast;
//
//import android.annotation.TargetApi;
//import android.content.Intent;
//import android.content.pm.PackageManager.NameNotFoundException;
//import android.content.pm.Signature;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.annotation.NonNull;
//import android.util.Base64;
//import android.util.Log;
//import com.facebook.ads.Ad;
//import com.facebook.ads.AdError;
//import com.facebook.ads.InterstitialAd;
//import com.facebook.ads.InterstitialAdListener;
//import com.google.android.gms.tasks.OnCompleteListener;
//import com.google.android.gms.tasks.Task;
//import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
//import com.phongphan.miracast.R;
//import com.phongphan.utils.IabHelper;
//import com.phongphan.utils.IabHelper.OnIabSetupFinishedListener;
//import com.phongphan.utils.IabHelper.QueryInventoryFinishedListener;
//import com.phongphan.utils.IabResult;
//import com.phongphan.utils.Inventory;
//import com.phongphan.utils.PermissionManager;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//import java.util.ArrayList;
//import java.util.Iterator;
//
//public class LogoActivity extends BaseActivity {
//    private final String TAG = LogoActivity.class.getSimpleName();
//    private FirebaseRemoteConfig firebaseRemoteConfig;
//    private boolean isActive = false;
//    private long mCount;
//    QueryInventoryFinishedListener mGotInventoryListener = new C09975();
//    private Handler mHandler;
//    IabHelper mHelper;
//
//    class C09931 implements OnCompleteListener<Void> {
//
//        class C09921 implements Runnable {
//            C09921() {
//            }
//
//            public void run() {
//                LogoActivity.this.loadADS();
//            }
//        }
//
//        C09931() {
//        }
//
//        public void onComplete(@NonNull Task<Void> task) {
//            LogoActivity.this.mHandler.removeCallbacksAndMessages(null);
//            if (task.isSuccessful()) {
//                Log.d(LogoActivity.this.TAG, "Fetch Succeeded");
//                LogoActivity.this.firebaseRemoteConfig.activateFetched();
//            } else {
//                Log.d(LogoActivity.this.TAG, "Fetch failed");
//            }
//            if (!LogoActivity.this.isActive) {
//                LogoActivity.this.isActive = true;
//                new Handler().postDelayed(new C09921(), 1000);
//            }
//        }
//    }
//
//    class C09942 implements Runnable {
//        C09942() {
//        }
//
//        public void run() {
//            if (!LogoActivity.this.isActive) {
//                LogoActivity.this.isActive = true;
//                LogoActivity.this.loadADS();
//            }
//        }
//    }
//
//    class C09964 implements OnIabSetupFinishedListener {
//        C09964() {
//        }
//
//        public void onIabSetupFinished(IabResult result) {
//            Log.d(LogoActivity.this.TAG, "Setup finished.");
//            if (!result.isSuccess()) {
//                LogoActivity.this.mHelper = null;
//                Log.d(LogoActivity.this.TAG, "Problem setting up in-app billing: " + result);
//                LogoActivity.this.loadRemoteConfig();
//            } else if (LogoActivity.this.mHelper == null) {
//                LogoActivity.this.loadRemoteConfig();
//            } else {
//                Log.d(LogoActivity.this.TAG, "Setup successful. Querying inventory.");
//                try {
//                    LogoActivity.this.mHelper.queryInventoryAsync(LogoActivity.this.mGotInventoryListener);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
//
//    class C09975 implements QueryInventoryFinishedListener {
//        C09975() {
//        }
//
//        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
//            Log.d(LogoActivity.this.TAG, "Query inventory finished.");
//            if (LogoActivity.this.mHelper == null) {
//                LogoActivity.this.loadRemoteConfig();
//                return;
//            }
//            if (result.isFailure()) {
//                Log.d(LogoActivity.this.TAG, "Query inventory was failed.");
//            } else {
//                CoreApplication.getInstance().premiumPurchase = inventory.getPurchase(LogoActivity.this.getString(R.string.SKU_PREMIUM));
//                Log.d(LogoActivity.this.TAG, "User is " + (CoreApplication.getInstance().isPremium() ? "PREMIUM" : "NOT PREMIUM"));
//            }
//            LogoActivity.this.loadRemoteConfig();
//        }
//    }
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView((int) R.layout.activity_logo);
//        ((SplashView) findViewById(R.id.spLogo)).runAnimation();
//        CoreApplication.getInstance().setupTestFAN();
//        this.mHandler = new Handler();
//        this.mCount = CoreApplication.getInstance().tinyDB.getLong("COUNTER") + 1;
//        CoreApplication.getInstance().tinyDB.putLong("COUNTER", this.mCount);
//        me();
//    }
//
//    private void loadRemoteConfig() {
//        this.firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
//        this.firebaseRemoteConfig.fetch((long) 1800).addOnCompleteListener(new C09931());
//        this.mHandler.postDelayed(new C09942(), 3000);
//    }
//
//    protected void onPostResume() {
//        super.onPostResume();
//    }
//
//    private void openMainScreen() {
//        Intent intent;
//        if (CoreApplication.getInstance().tinyDB.getBoolean("INTRO")) {
//            intent = new Intent(this, MainActivity.class);
//        } else {
//            intent = new Intent(this, IntroActivity.class);
//        }
//        startActivity(intent);
//        finish();
//    }
//
//    @TargetApi(23)
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case PermissionManager.EXTERNAL_STORAGE /*900*/:
//                if (grantResults.length > 0 && grantResults[0] == 0) {
//                    loadRemoteConfig();
//                    return;
//                }
//                return;
//            default:
//                return;
//        }
//    }
//
//    void loadFAN() {
//        final InterstitialAd interstitialAd = new InterstitialAd(this, getString(R.string.logo_fan));
//        interstitialAd.setAdListener(new InterstitialAdListener() {
//            public void onInterstitialDisplayed(Ad ad) {
//            }
//
//            public void onInterstitialDismissed(Ad ad) {
//            }
//
//            public void onError(Ad ad, AdError adError) {
//                LogoActivity.this.openMainScreen();
//            }
//
//            public void onAdLoaded(Ad ad) {
//                CoreApplication.getInstance().tinyDB.putLong("COUNTER", 0);
//                LogoActivity.this.openMainScreen();
//                interstitialAd.show();
//            }
//
//            public void onAdClicked(Ad ad) {
//            }
//
//            public void onLoggingImpression(Ad ad) {
//            }
//        });
//        interstitialAd.loadAd();
//    }
//
//    private void loadADS() {
//        if (CoreApplication.getInstance().isGodMode()) {
//            Log.d(this.TAG, "Run in god mode");
//            openMainScreen();
//            return;
//        }
//        long numberShow = this.firebaseRemoteConfig.getLong("LOGO_ADS");
//        if (numberShow == 0) {
//            openMainScreen();
//        } else if (this.mCount >= numberShow) {
//            loadFAN();
//        } else {
//            openMainScreen();
//        }
//    }
//
//    private void me() {
//        boolean isSuccess = false;
//        try {
//            ArrayList<String> hashList = new ArrayList();
//            for (Signature signature : getPackageManager().getPackageInfo(getPackageName(), 64).signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                hashList.add(Base64.encodeToString(md.digest(), 0));
//            }
//            String[] tmp = CoreApplication.getInstance().stringJNI().split("%");
//            Iterator it = hashList.iterator();
//            while (it.hasNext()) {
//                String hash = (String) it.next();
//                for (String key : tmp) {
//                    if (hash.trim().equalsIgnoreCase(key)) {
//                        isSuccess = true;
//                        break;
//                    }
//                }
//            }
//            if (!isSuccess) {
//                throw new RuntimeException("Package: " + getApplicationContext().getPackageName());
//            }
//        } catch (NameNotFoundException e) {
//        } catch (NoSuchAlgorithmException e2) {
//        }
//        initPurchase();
//    }
//
//    private void initPurchase() {
//        this.mHelper = new IabHelper(this, CoreApplication.getInstance().stringBillingJNI());
//        this.mHelper.enableDebugLogging(false);
//        Log.d(this.TAG, "Starting setup.");
//        this.mHelper.startSetup(new C09964());
//    }
//
//    public void onDestroy() {
//        super.onDestroy();
//        Log.d(this.TAG, "Destroying helper.");
//        if (this.mHelper != null) {
//            this.mHelper.disposeWhenFinished();
//            this.mHelper = null;
//        }
//    }
//}
