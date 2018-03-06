package com.adaps.ain043.samples;

import android.content.Context;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;

/**
 * Created by AIN043 on 1/17/2018 at 1:23 PM
 */

public class AnimUtil {
    public static LayoutAnimationController rightToLeft() {
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(50);
        set.addAnimation(animation);

        animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 1.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f
        );
        animation.setDuration(100);
        set.addAnimation(animation);

        return new LayoutAnimationController(set, 0.5f);
    }

    public static Animation faceIn(Context context) {
        AnimationSet animation = new AnimationSet(true);
        Animation fadeIn = new AlphaAnimation(1, 0);
        fadeIn.setInterpolator(new DecelerateInterpolator()); //add this
        fadeIn.setDuration(1000);

//        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 0, 1f, 0);
//        scaleAnimation.setDuration(1000);
//        TranslateAnimation scaleAnimation = new TranslateAnimation(-25, 25, -25, 25);
//        scaleAnimation.setInterpolator(new AccelerateInterpolator());
//        scaleAnimation.setDuration(1000);
//        scaleAnimation.setFillEnabled(true);
//        scaleAnimation.setFillAfter(true);

//        Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_out);

//        animation.addAnimation(scaleAnimation);
        animation.addAnimation(fadeIn);
        return animation;
    }

    public static Animation faceOut(Context context) {
        AnimationSet animation = new AnimationSet(true);
        Animation fadeOut = new AlphaAnimation(0, 1);
//        fadeOut.setInterpolator(new AccelerateInterpolator()); //and this
//        fadeOut.setStartOffset(1000);
        fadeOut.setDuration(600);

//        ScaleAnimation scaleAnimation = new ScaleAnimation(0f, 1, 0f, 1);
//        scaleAnimation.setDuration(1000);
//        TranslateAnimation scaleAnimation = new TranslateAnimation(25, -25, -25, 25);
//        scaleAnimation.setInterpolator(new AccelerateInterpolator());
//        scaleAnimation.setDuration(1000);
//        scaleAnimation.setFillEnabled(true);
//        scaleAnimation.setFillAfter(true);

        Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.zoom_in_animation);
        animation.addAnimation(scaleAnimation);
        animation.addAnimation(fadeOut);
        return animation;
    }
}
