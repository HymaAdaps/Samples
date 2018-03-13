//package com.adaps.ain043.samples.miracast;
//
//import android.app.Activity;
//import android.content.Context;
//import android.text.TextUtils;
//import android.util.AttributeSet;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import com.adaps.ain043.samples.R;
//import com.adaps.ain043.samples.miracast.utils.GooglePlay;
//import com.bumptech.glide.Glide;
//
//
//
//public class AppView extends LinearLayout {
//    public AppView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init();
//    }
//
//    public AppView(Context context) {
//        super(context);
//        init();
//    }
//
//    private void init() {
//        setOrientation(0);
//        setGravity(16);
//        ButterKnife.bind(((LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.app_items, this, true), (View) this);
//    }
//
//    public void setData(final AppModel app) {
//        if (app != null) {
//            if (!TextUtils.isEmpty(app.getIconUrl())) {
//                Glide.with(getContext()).load(app.getIconUrl()).into(this.ivIcon);
//            }
//            this.tvName.setText(app.getName());
//            setOnClickListener(new OnClickListener() {
//                public void onClick(View v) {
//                    GooglePlay.open((Activity) AppView.this.getContext(), app.getStoreId());
//                }
//            });
//        }
//    }
//}
