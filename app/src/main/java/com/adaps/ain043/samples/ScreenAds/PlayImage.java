package com.adaps.ain043.samples.ScreenAds;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

/**
 * Created by ain043 on 3/5/2018 at 1:58 PM
 */

public class PlayImage {
    private static final String TAG = PlayImage.class.getSimpleName();

    private int time = 5000;
    private long curTime = 1000;
    private onFinishListner onFinishListner;


    public interface onFinishListner {
        public void onFinished();
    }

    public PlayImage(onFinishListner onFinishListner, String time) {
        this.onFinishListner = onFinishListner;
        this.time = Integer.parseInt(time);
    }

    public void start() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            public void run() {
                if (onFinishListner != null) {
                    onFinishListner.onFinished();
                }
            }
        };
        handler.postDelayed(runnable, time);
        new CountDownTimer(time, 1000) {

            public void onTick(long millisUntilFinished) {
                curTime = millisUntilFinished / 1000;
                Log.e(TAG, "seconds remaining: " + millisUntilFinished / 1000);
            }

            public void onFinish() {
                curTime = time;
            }
        }.start();
    }

    public long getCurTime() {
        return curTime;
    }

    public long getTotalTime() {
        return time;
    }
}
