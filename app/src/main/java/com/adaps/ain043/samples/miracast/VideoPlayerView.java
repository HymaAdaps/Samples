package com.adaps.ain043.samples.miracast;

import android.content.Context;
import android.net.Uri;
import android.util.AttributeSet;
import android.widget.VideoView;

public class VideoPlayerView extends VideoView {
    private Context mContext;

    public VideoPlayerView(Context context) {
        super(context);
        init(context);
    }

    public VideoPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public VideoPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
    }

    public void play(int video) {
        setVideoURI(Uri.parse("android.resource://" + this.mContext.getPackageName() + "/" + video));
        requestFocus();
        start();
    }
}
