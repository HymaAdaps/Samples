package com.adaps.ain043.samples;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.adaps.ain043.samples.databinding.ActivityAnimationsBinding;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by ain043 on 3/6/2018 at 2:39 PM
 */

public class AnimActivity extends AppCompatActivity {
    private ActivityAnimationsBinding binding;
    private Timer timer;
    private int count = 3540000;//59 sec
//GOOGLE_PLAY
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animations);


//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                showAnimations();
//            }
//        }, 2000);
        startTimer();
        binding.tvStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopTimer();
            }
        });
    }

    private void stopTimer() {
        timer.cancel();
    }

    public void startTimer() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Log.e("/Anim", " count " + count);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        binding.tvtimer.setText(getTime(count));
                    }
                });
                count = count + 1000;
            }
        }, 1000, 1000);
    }

    public String getTime(long millis) {
        try {
            String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis),
                    TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                    TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
            return hms;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private void showAnimations() {
        binding.llFirst.setAnimation(AnimUtil.faceIn(this));
        binding.llFirst.setVisibility(View.GONE);
        binding.llSecond.setAnimation(AnimUtil.faceOut(this));
        binding.llSecond.setVisibility(View.VISIBLE);
    }
}
