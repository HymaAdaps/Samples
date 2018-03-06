package com.adaps.ain043.samples;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.adaps.ain043.samples.databinding.ActivityAnimationsBinding;

/**
 * Created by ain043 on 3/6/2018 at 2:39 PM
 */

public class AnimActivity extends AppCompatActivity {
    private ActivityAnimationsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_animations);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showAnimations();
            }
        }, 2000);
    }

    private void showAnimations() {
        binding.llFirst.setAnimation(AnimUtil.faceIn(this));
        binding.llFirst.setVisibility(View.GONE);
        binding.llSecond.setAnimation(AnimUtil.faceOut(this));
        binding.llSecond.setVisibility(View.VISIBLE);
    }
}
