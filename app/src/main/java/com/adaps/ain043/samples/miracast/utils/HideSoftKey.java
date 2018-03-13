package com.adaps.ain043.samples.miracast.utils;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class HideSoftKey {
    private Activity mActivity;

    class C10121 implements OnTouchListener {
        C10121() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            HideSoftKey.this.hideSoftKeyboard();
            return false;
        }
    }

    public HideSoftKey(Activity activity) {
        this.mActivity = activity;
    }

    public void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new C10121());
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                setupUI(((ViewGroup) view).getChildAt(i));
            }
        }
    }

    public void hideSoftKeyboard() {
        try {
            ((InputMethodManager) this.mActivity.getSystemService("input_method")).hideSoftInputFromWindow(this.mActivity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
        }
    }
}
