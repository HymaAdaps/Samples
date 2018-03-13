//package com.adaps.ain043.samples.miracast;
//
//import android.content.Context;
//import android.support.annotation.Nullable;
//import android.util.AttributeSet;
//import android.view.View;
//import android.view.animation.Animation;
//import android.view.animation.Animation.AnimationListener;
//import android.view.animation.AnimationUtils;
//import android.widget.FrameLayout;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//import com.phongphan.miracast.R;
//
//public class SplashView extends LinearLayout {
//    FrameLayout divider;
//    private Context mContext;
//    TextView tvName;
//    TextView tvSlogan;
//
//    class C10061 implements AnimationListener {
//        C10061() {
//        }
//
//        public void onAnimationStart(Animation animation) {
//            SplashView.this.tvName.setVisibility(4);
//            SplashView.this.tvSlogan.setVisibility(4);
//        }
//
//        public void onAnimationEnd(Animation animation) {
//            SplashView.this.tvName.setVisibility(0);
//            SplashView.this.tvSlogan.setVisibility(0);
//            SplashView.this.tvName.startAnimation(AnimationUtils.loadAnimation(SplashView.this.mContext, R.anim.translate_name));
//            SplashView.this.tvSlogan.startAnimation(AnimationUtils.loadAnimation(SplashView.this.mContext, R.anim.translate_slogan));
//        }
//
//        public void onAnimationRepeat(Animation animation) {
//        }
//    }
//
//    public SplashView(Context context) {
//        super(context);
//        init(context);
//    }
//
//    public SplashView(Context context, @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        init(context);
//    }
//
//    public SplashView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//        init(context);
//    }
//
//    private void init(Context context) {
//        this.mContext = context;
//        View view = inflate(getContext(), R.layout.layout_splash, null);
//        addView(view);
//        this.tvName = (TextView) view.findViewById(R.id.tvName);
//        this.tvSlogan = (TextView) view.findViewById(R.id.tvSlogan);
//        this.divider = (FrameLayout) view.findViewById(R.id.divider);
//    }
//
//    public void runAnimation() {
//        Animation animationDivider = AnimationUtils.loadAnimation(this.mContext, R.anim.translate_divider);
//        animationDivider.setAnimationListener(new C10061());
//        this.divider.startAnimation(animationDivider);
//    }
//}
