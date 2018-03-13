package com.adaps.ain043.samples.miracast.utils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build.VERSION;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AlphaAnimation;
import com.android.volley.DefaultRetryPolicy;

@SuppressLint({"NewApi"})
@TargetApi(12)
public class TouchEffect {

    private static class ASetOnTouchListener implements OnTouchListener {
        final int FIXED_DURATION = 50;
        final float HALF_ALPHA = 0.3f;
        final float ZERO_ALPHA = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;
        float alphaOrginally = DefaultRetryPolicy.DEFAULT_BACKOFF_MULT;

        public ASetOnTouchListener(View v) {
            if (VERSION.SDK_INT >= 11) {
                this.alphaOrginally = v.getAlpha();
            }
        }

        @SuppressLint({"ClickableViewAccessibility"})
        public boolean onTouch(View v, MotionEvent event) {
            AlphaAnimation animation;
            switch (event.getAction()) {
                case 0:
                    if (VERSION.SDK_INT >= 11) {
                        v.animate().setDuration(50).alpha(0.3f);
                        break;
                    }
                    animation = new AlphaAnimation(DefaultRetryPolicy.DEFAULT_BACKOFF_MULT, 0.3f);
                    animation.setDuration(50);
                    animation.setFillAfter(true);
                    v.startAnimation(animation);
                    break;
                case 1:
                case 3:
                    if (VERSION.SDK_INT >= 11) {
                        v.animate().setDuration(50).alpha(this.alphaOrginally);
                        break;
                    }
                    animation = new AlphaAnimation(0.3f, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    animation.setDuration(50);
                    animation.setFillAfter(true);
                    v.startAnimation(animation);
                    break;
            }
            return false;
        }
    }

    public static void attach(View view) {
        view.setOnTouchListener(new ASetOnTouchListener(view));
    }

    public static void attach(View view, OnClickListener onClickListener) {
        view.setOnTouchListener(new ASetOnTouchListener(view));
        view.setOnClickListener(onClickListener);
    }
}
