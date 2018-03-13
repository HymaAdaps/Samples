//package com.adaps.ain043.samples.miracast.utils;
//
//import com.adaps.ain043.samples.R;
//import com.adaps.ain043.samples.miracast.CoreApplication;
//import com.crashlytics.android.answers.Answers;
//import com.crashlytics.android.answers.CustomEvent;
//import com.crashlytics.android.answers.PurchaseEvent;
//import java.math.BigDecimal;
//import java.util.Currency;
//
//public class FabricUtils {
//    private final String TAG = FabricUtils.class.getSimpleName();
//
//    public static void purchaseNoADSEvent() {
//        Answers.getInstance().logPurchase(new PurchaseEvent().putItemPrice(BigDecimal.valueOf(1)).putCurrency(Currency.getInstance("USD")).putItemName("No ADS").putItemType("Premium").putItemId(CoreApplication.getInstance().getApplicationContext().getString(R.string.SKU_PREMIUM)).putSuccess(true));
//    }
//
//    public static void rateEvent(boolean isAbout) {
//        Answers.getInstance().logCustom((CustomEvent) new CustomEvent("RATE").putCustomAttribute("Where", isAbout ? "About" : "Exit"));
//    }
//
//    public static void shareEvent(boolean isAbout) {
//        Answers.getInstance().logCustom((CustomEvent) new CustomEvent("SHARE").putCustomAttribute("Where", isAbout ? "About" : "Home"));
//    }
//
//    public static void feedbackEvent() {
//        Answers.getInstance().logCustom(new CustomEvent("FEEDBACK"));
//    }
//
//    public static void playStoreEvent() {
//        Answers.getInstance().logCustom(new CustomEvent("PLAY STORE"));
//    }
//
//    public static void playStoreEvent(String id) {
//        Answers.getInstance().logCustom((CustomEvent) new CustomEvent("PLAY STORE").putCustomAttribute("APP", id));
//    }
//
//    public static void customEvent(String event) {
//        Answers.getInstance().logCustom(new CustomEvent(event));
//    }
//}
