package com.adaps.ain043.samples.screenonoff;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.adaps.ain043.samples.R;

/**
 * Created by ain043 on 2/27/2018 at 11:16 AM
 */

public class CustoNotifActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {
    private static int SCREEN_HEIGHT, SCREEN_WIDTH;

    private GestureDetector mDetector;
    private float defaultXAccept = 0f, defaultXReject = 0f;
    private boolean accept;
    private float dragDiffAccept, dragDiffReject;
    private ImageView imgEndCall, imgAccept, imgReject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_notif);

        findViewById(R.id.tvGetNotif).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
            }
        });

        setWindowDimensions(this);


    }

    private void sendNotification() {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.popup_swiping);

//        imgEndCall = (ImageView) findViewById(R.id.imgEndCall);
//        imgAccept = (ImageView) findViewById(R.id.imgAccept);
//        imgReject = (ImageView) findViewById(R.id.imgReject);
//        contentView.setRelativeScrollPosition(R.id.imgAccept, SCREEN_WIDTH / 2);

//        setImageAcceptTouchEvent(imgAccept);
//        setImageAcceptTouchEvent(imgReject);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContent(contentView);

        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(1, notification);
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private static void setWindowDimensions(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        SCREEN_WIDTH = size.x;
        SCREEN_HEIGHT = size.y;
    }

    private void setImageAcceptTouchEvent(ImageView imageView) {
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (v.getId() == R.id.imgAccept) {
                    accept = true;
                } else if (v.getId() == R.id.imgReject) {
                    accept = false;
                }
                if (mDetector.onTouchEvent(event)) {
                    return true;
                }
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (accept)
                        onAcceptActionDone();
                    else
                        onRejectActionDone();
                }
                return true;
            }
        });

        mDetector = new GestureDetector(CustoNotifActivity.this, this);
    }


    private void onAcceptActionDone() {
        if (imgAccept.getX() >= SCREEN_WIDTH / 2 + SCREEN_WIDTH / 3) {
            //Accept Call
            acceptCall();
        } else {
            //place the accept image in its original position
            translateX(imgAccept, 200, imgAccept.getX(), defaultXAccept);
        }
    }

    private void acceptCall() {
        imgEndCall.setVisibility(View.VISIBLE);

        imgAccept.setVisibility(View.GONE);
        imgReject.setVisibility(View.GONE);
        Intent leftIntent = new Intent(this, NotificationIntentService.class);
        leftIntent.setAction("left");


        Intent rightIntent = new Intent(this, NotificationIntentService.class);
        rightIntent.setAction("right");


        PendingIntent left = PendingIntent.getService(getApplicationContext(), 0, leftIntent, 0);
        PendingIntent right = PendingIntent.getService(getApplicationContext(), 0, rightIntent, 0);
    }

    private void onRejectActionDone() {
        if (imgReject.getX() <= SCREEN_WIDTH / 2 - SCREEN_WIDTH / 3) {
            //reject call
            rejectCall();
        } else {
            //place the accept image in its original position
            translateX(imgReject, 200, imgReject.getX(), defaultXReject);
        }
    }

    private void rejectCall() {
        imgEndCall.setVisibility(View.GONE);

        imgAccept.setVisibility(View.GONE);
        imgReject.setVisibility(View.GONE);
        Intent leftIntent = new Intent(this, NotificationIntentService.class);
        leftIntent.setAction("left");


        Intent rightIntent = new Intent(this, NotificationIntentService.class);
        rightIntent.setAction("right");


        PendingIntent left = PendingIntent.getService(getApplicationContext(), 0, leftIntent, 0);
        PendingIntent right = PendingIntent.getService(getApplicationContext(), 0, rightIntent, 0);
    }

    private void translateX(final View view, long duration, float fromX, final float targetX) {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(fromX, targetX);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float currentX = (float) animation.getAnimatedValue();
                view.setX(currentX);
            }
        });

        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    @Override
    public boolean onDown(MotionEvent e) {
        if (accept) {
            if (defaultXAccept == 0f)
                defaultXAccept = imgAccept.getX();
        } else {
            if (defaultXReject == 0f)
                defaultXReject = imgReject.getX();
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (accept) {
            dragDiffAccept = e2.getX() - e1.getX();
            if (dragDiffAccept > 0)
                imgAccept.setX(imgAccept.getX() + dragDiffAccept);
        } else {
            dragDiffReject = e2.getX() - e1.getX();
            if (dragDiffReject < 0)
                imgReject.setX(imgReject.getX() + dragDiffReject);
        }
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    private void sendNotification1() {
        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.view_notification);
        contentView.setImageViewResource(R.id.image, R.mipmap.ic_launcher);
        contentView.setTextViewText(R.id.title, "Custom notification");
        contentView.setTextViewText(R.id.text, "This is a custom layout");

        Intent leftIntent = new Intent(this, NotificationIntentService.class);
        leftIntent.setAction("left");


        Intent rightIntent = new Intent(this, NotificationIntentService.class);
        rightIntent.setAction("right");


        PendingIntent left = PendingIntent.getService(getApplicationContext(), 0, leftIntent, 0);
        PendingIntent right = PendingIntent.getService(getApplicationContext(), 0, rightIntent, 0);

        contentView.setOnClickPendingIntent(R.id.tvPositive, left);
        contentView.setOnClickPendingIntent(R.id.tvNegative, right);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContent(contentView);

        Notification notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notificationManager.notify(1, notification);
    }
}
//http://code.hootsuite.com/custom-notifications-for-android/
