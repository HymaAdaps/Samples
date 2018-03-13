//package com.adaps.ain043.samples.miracast;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.MenuItem;
//import android.webkit.WebView;
//import android.widget.LinearLayout;
//import android.widget.ScrollView;
//import com.facebook.ads.Ad;
//import com.facebook.ads.AdError;
//import com.facebook.ads.AdListener;
//import com.facebook.ads.NativeAd;
//import com.facebook.ads.NativeAdView;
//import com.facebook.ads.NativeAdView.Type;
//import com.google.android.gms.ads.AdRequest.Builder;
//import com.google.android.gms.ads.AdSize;
//import com.google.android.gms.ads.NativeExpressAdView;
//import com.phongphan.miracast.R;
//import com.phongphan.utils.FabricUtils;
//
//public class GuideActivity extends AppCompatActivity {
//    private static final String TAG = GuideActivity.class.getSimpleName();
//    LinearLayout adViewContainer;
//    private NativeAd nativeAd;
//    ScrollView svRoot;
//    WebView webView;
//
//    class C09912 implements AdListener {
//        C09912() {
//        }
//
//        public void onError(Ad ad, AdError adError) {
//            GuideActivity.this.loadNativeADMOB();
//        }
//
//        public void onAdLoaded(Ad ad) {
//            GuideActivity.this.adViewContainer.addView(NativeAdView.render(GuideActivity.this, GuideActivity.this.nativeAd, Type.HEIGHT_300));
//        }
//
//        public void onAdClicked(Ad ad) {
//        }
//
//        public void onLoggingImpression(Ad ad) {
//        }
//    }
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView((int) R.layout.activity_web);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle((CharSequence) "How to use?");
//        this.adViewContainer = (LinearLayout) findViewById(R.id.adViewContainer);
//        this.svRoot = (ScrollView) findViewById(R.id.svRoot);
//        this.webView = (WebView) findViewById(R.id.webView);
//        this.webView.getSettings().setJavaScriptEnabled(true);
//        this.webView.loadUrl("file:///android_asset/guide.html");
//        FabricUtils.customEvent("GUIDE SCREEN");
//        if (CoreApplication.getInstance().isGodMode()) {
//            Log.d(TAG, "Run in god mode");
//        } else {
//            loadNativeFAN();
//        }
//    }
//
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if (item.getItemId() != 16908332) {
//            return super.onOptionsItemSelected(item);
//        }
//        onBackPressed();
//        return true;
//    }
//
//    public void onBackPressed() {
//        super.onBackPressed();
//    }
//
//    private void loadNativeADMOB() {
//        this.adViewContainer.removeAllViews();
//        final NativeExpressAdView adViewNative = new NativeExpressAdView(this);
//        adViewNative.setAdSize(new AdSize(-1, 300));
//        adViewNative.setAdUnitId(getString(R.string.native_guide_admob));
//        adViewNative.setAdListener(new com.google.android.gms.ads.AdListener() {
//            public void onAdLoaded() {
//                super.onAdLoaded();
//            }
//
//            public void onAdFailedToLoad(int errorCode) {
//                adViewNative.setVisibility(8);
//            }
//        });
//        this.adViewContainer.addView(adViewNative);
//        adViewNative.loadAd(new Builder().addTestDevice(getString(R.string.device_id_1)).addTestDevice(getString(R.string.device_id_2)).addTestDevice(getString(R.string.device_id_3)).build());
//    }
//
//    private void loadNativeFAN() {
//        this.adViewContainer.removeAllViews();
//        this.nativeAd = new NativeAd(this, getString(R.string.native_guide_fan));
//        this.nativeAd.setAdListener(new C09912());
//        this.nativeAd.loadAd();
//    }
//}
