//package com.adaps.ain043.samples.miracast;
//
//import android.app.Activity;
//import android.content.ActivityNotFoundException;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.AppCompatButton;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.widget.LinearLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.adaps.ain043.samples.R;
//import com.adaps.ain043.samples.miracast.network.API;
//import com.adaps.ain043.samples.miracast.network.RequestListener;
//import com.adaps.ain043.samples.miracast.utils.Alert;
//import com.adaps.ain043.samples.miracast.utils.FabricUtils;
//import com.adaps.ain043.samples.miracast.utils.GooglePlay;
//import com.adaps.ain043.samples.miracast.utils.IabHelper;
//import com.adaps.ain043.samples.miracast.utils.IabResult;
//import com.adaps.ain043.samples.miracast.utils.MyJson;
//import com.adaps.ain043.samples.miracast.utils.Purchase;
//import com.adaps.ain043.samples.miracast.utils.TouchEffect;
//
//import butterknife.Bind;
//import butterknife.ButterKnife;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//
//import org.json.JSONArray;
//import org.json.JSONObject;
//
//public class AboutActivity extends BaseActivity implements RequestListener, OnClickListener {
//    final int RC_PURCHASE_REQUEST = 10001;
//    private final String TAG = AboutActivity.class.getSimpleName();
//    @Bind({2131492969})
//    AppCompatButton btnDonate;
//    @Bind({2131492973})
//    AppCompatButton btnOtherApp;
//    @Bind({2131492970})
//    AppCompatButton btnPurchase;
//    @Bind({2131492968})
//    AppCompatButton btnRate;
//    @Bind({2131492964})
//    LinearLayout fanContainer;
//    @Bind({2131492971})
//    LinearLayout insertPoint;
//    private ArrayList<AppModel> mData;
//    IabHelper mHelper;
//    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener();
//    @Bind({2131492963})
//    NativeExpressAdView nativeADMOB;
//    private NativeAd nativeAd;
//    @Bind({2131492972})
//    ProgressWheel progress;
//    @Bind({2131492928})
//    ScrollView scrollView;
//    @Bind({2131492967})
//    TextView tvCopyright;
//    @Bind({2131492965})
//    TextView tvDescription;
//    @Bind({2131492966})
//    TextView tvEmail;
//
//    class C09792 implements IabHelper.OnIabSetupFinishedListener {
//        C09792() {
//        }
//
//        public void onIabSetupFinished(IabResult result) {
//            Log.d(AboutActivity.this.TAG, "Setup finished.");
//            if (!result.isSuccess()) {
//                AboutActivity.this.mHelper = null;
//                AboutActivity.this.btnPurchase.setVisibility(8);
//            } else if (AboutActivity.this.mHelper != null) {
//            }
//        }
//    }
//
//    class C09803 extends AdListener {
//        C09803() {
//        }
//
//        public void onAdFailedToLoad(int i) {
//            super.onAdFailedToLoad(i);
//            AboutActivity.this.nativeADMOB.setVisibility(8);
//        }
//
//        public void onAdLoaded() {
//            super.onAdLoaded();
//            AboutActivity.this.scrollView.scrollTo(0, 0);
//        }
//    }
//
//    class C09814 implements com.facebook.ads.AdListener {
//        C09814() {
//        }
//
//        public void onError(Ad ad, AdError adError) {
//            AboutActivity.this.loadNativeADMOB();
//        }
//
//        public void onAdLoaded(Ad ad) {
//            AboutActivity.this.fanContainer.addView(NativeAdView.render(AboutActivity.this, AboutActivity.this.nativeAd, Type.HEIGHT_300));
//            AboutActivity.this.scrollView.scrollTo(0, 0);
//        }
//
//        public void onAdClicked(Ad ad) {
//        }
//
//        public void onLoggingImpression(Ad ad) {
//        }
//    }
//
//    class C09835 implements OnIabPurchaseFinishedListener {
//
//        class C09821 implements DialogInterface.OnClickListener {
//            C09821() {
//            }
//
//            public void onClick(DialogInterface dialog, int which) {
//                Intent i = AboutActivity.this.getBaseContext().getPackageManager().getLaunchIntentForPackage(AboutActivity.this.getBaseContext().getPackageName());
//                i.addFlags(67108864);
//                i.addFlags(268435456);
//                AboutActivity.this.startActivity(i);
//                AboutActivity.this.finish();
//            }
//        }
//
//        OnIabPurchaseFinishedListener() {
//        }
//
//        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
//            Log.d(AboutActivity.this.TAG, "Purchase finished: " + result + ", purchase: " + purchase);
//            if (AboutActivity.this.mHelper != null && !result.isFailure()) {
//                if (CoreApplication.getInstance().verifyDeveloperPayload(purchase)) {
//                    Log.d(AboutActivity.this.TAG, "Purchase successful.");
//                    if (purchase.getSku().equals(AboutActivity.this.getString(R.string.SKU_PREMIUM))) {
//                        FabricUtils.purchaseNoADSEvent();
//                        Log.d(AboutActivity.this.TAG, "Purchase is premium upgrade. Congratulating user.");
//                        Alert.showWithOK(AboutActivity.this, "PURCHASE", "Thank you for supporting us! Press OK to reopen app", new C09821());
//                        return;
//                    }
//                    return;
//                }
//                Alert.show(AboutActivity.this, "PURCHASE", "Error purchasing. Authenticity verification failed.");
//            }
//        }
//    }
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView((int) R.layout.activity_about);
//        ButterKnife.bind((Activity) this);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setTitle((CharSequence) "About");
//        }
//        this.tvDescription.setSelected(true);
//        this.tvCopyright.setText(String.format("Copyright © %d by The Tree", new Object[]{Integer.valueOf(Calendar.getInstance().get(1))}));
//        this.tvEmail.setText("Email: " + getString(R.string.email));
//        TouchEffect.attach(this.tvEmail);
//        this.btnRate.setOnClickListener(this);
//        this.tvEmail.setOnClickListener(this);
//        this.btnPurchase.setOnClickListener(this);
//        this.btnOtherApp.setOnClickListener(this);
//        FabricUtils.customEvent("ABOUT SCREEN");
//        new API().getApp(this);
//        if (FirebaseRemoteConfig.getInstance().getBoolean("RATE")) {
//            this.btnRate.setVisibility(0);
//        } else {
//            this.btnRate.setVisibility(8);
//        }
//        String donate = FirebaseRemoteConfig.getInstance().getString("DONATE");
//        if (TextUtils.isEmpty(donate)) {
//            this.btnDonate.setVisibility(8);
//        } else {
//            final String[] url = donate.split("-");
//            if (url.length == 2) {
//                try {
//                    this.btnDonate.setVisibility(0);
//                    this.btnDonate.setText(url[0]);
//                    this.btnDonate.setOnClickListener(new OnClickListener() {
//                        public void onClick(View v) {
//                            GooglePlay.open(AboutActivity.this, url[1]);
//                        }
//                    });
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        if (CoreApplication.getInstance().isGodMode()) {
//            Log.d(this.TAG, "Run in God Mode");
//            this.nativeADMOB.setVisibility(8);
//            this.fanContainer.setVisibility(8);
//            this.btnPurchase.setVisibility(8);
//        } else {
//            loadNativeFAN();
//            if (FirebaseRemoteConfig.getInstance().getBoolean("PURCHASE")) {
//                this.btnPurchase.setVisibility(0);
//            } else {
//                this.btnPurchase.setVisibility(8);
//            }
//        }
//        initPurchase();
//    }
//
//    private void initPurchase() {
//        Log.d(this.TAG, "Creating IAB helper.");
//        this.mHelper = new IabHelper(this, CoreApplication.getInstance().stringBillingJNI());
//        this.mHelper.enableDebugLogging(false);
//        Log.d(this.TAG, "Starting setup.");
//        this.mHelper.startSetup(new C09792());
//    }
//
//    private void loadNativeADMOB() {
//        this.fanContainer.setVisibility(8);
//        this.nativeADMOB.setVisibility(0);
//        this.nativeADMOB.setAdListener(new C09803());
//        this.nativeADMOB.loadAd(new Builder().addTestDevice(getString(R.string.device_id_1)).addTestDevice(getString(R.string.device_id_2)).addTestDevice(getString(R.string.device_id_3)).build());
//    }
//
//    private void loadNativeFAN() {
//        this.fanContainer.removeAllViews();
//        this.fanContainer.setVisibility(0);
//        this.nativeADMOB.setVisibility(8);
//        this.nativeAd = new NativeAd(this, getString(R.string.native_fan));
//        this.nativeAd.setAdListener(new C09814());
//        this.nativeAd.loadAd();
//    }
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_about, menu);
//        return true;
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == 16908332) {
//            onBackPressed();
//            return true;
//        } else if (id == R.id.action_send) {
//            sendMail();
//            return true;
//        } else if (id != R.id.action_share_friend) {
//            return super.onOptionsItemSelected(item);
//        } else {
//            FabricUtils.shareEvent(true);
//            shareApp();
//            return true;
//        }
//    }
//
//    public void onClick(View view) {
//        int id = view.getId();
//        if (id == this.btnRate.getId()) {
//            GooglePlay.open(this, getApplicationContext().getPackageName());
//        } else if (id == this.tvEmail.getId()) {
//            sendMail();
//        } else if (id == this.btnPurchase.getId()) {
//            buyPremium();
//        } else if (id == this.btnOtherApp.getId()) {
//            GooglePlay.openAppList(this);
//        }
//    }
//
//    private void buyPremium() {
//        try {
//            this.mHelper.launchPurchaseFlow(this, getString(R.string.SKU_PREMIUM), 10001, this.mPurchaseFinishedListener, CoreApplication.getInstance().stringPayloadJNI());
//        } catch (IabAsyncInProgressException e) {
//            Alert.toast(this, "Error launching purchase flow. Another async operation in progress.");
//        }
//    }
//
//    private void sendMail() {
//        FabricUtils.feedbackEvent();
//        Intent i = new Intent("android.intent.action.SEND");
//        i.setType("text/plain");
//        i.putExtra("android.intent.extra.EMAIL", new String[]{getString(R.string.email)});
//        i.putExtra("android.intent.extra.SUBJECT", getString(R.string.app_name));
//        try {
//            startActivity(Intent.createChooser(i, "Send Email Feedback..."));
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(this, "There are no email clients installed.", 0).show();
//        }
//    }
//
//    public void onRequestStart() {
//        this.progress.setVisibility(0);
//    }
//
//    public void onRequestSuccess(String response) {
//        this.mData = new ArrayList();
//        try {
//            JSONArray arr = new JSONArray(new String(response));
//            for (int i = 0; i < arr.length(); i++) {
//                JSONObject obj = arr.getJSONObject(i);
//                AppModel appModel = new AppModel();
//                appModel.setName(MyJson.getString(obj, "name"));
//                appModel.setIconUrl(MyJson.getString(obj, SettingsJsonConstants.APP_ICON_KEY));
//                appModel.setStoreId(MyJson.getString(obj, "store"));
//                this.mData.add(appModel);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        this.progress.setVisibility(8);
//        addAppItem();
//    }
//
//    public void onRequestFail(VolleyError error) {
//        this.progress.setVisibility(8);
//    }
//
//    public void addAppItem() {
//        this.insertPoint.removeAllViews();
//        if (this.mData != null) {
//            for (int i = this.mData.size() - 1; i >= 0; i--) {
//                AppView appView = new AppView(this);
//                appView.setData((AppModel) this.mData.get(i));
//                this.insertPoint.addView(appView, 0, new LayoutParams(-1, -1));
//            }
//        }
//    }
//
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 10001 && this.mHelper != null) {
//            if (this.mHelper.handleActivityResult(requestCode, resultCode, data)) {
//                Log.d(this.TAG, "onActivityResult handled by IABUtil.");
//            } else {
//                super.onActivityResult(requestCode, resultCode, data);
//            }
//        }
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
