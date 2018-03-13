package com.adaps.ain043.samples.screenonoff;

import android.Manifest;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.adaps.ain043.samples.R;
import com.adaps.ain043.samples.appupdater.AppUpdater;

import java.util.ArrayList;
import java.util.List;

public class SwipeScreenExample extends Activity implements GestureDetector.OnGestureListener {

    private static int SCREEN_HEIGHT, SCREEN_WIDTH;

    private ImageView imgEndCall, imgAccept, imgReject;
    private GestureDetector mDetector;
    private float defaultXAccept = 0f, defaultXReject = 0f;
    private boolean accept;
    private float dragDiffAccept, dragDiffReject;

    int count = 0;

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_swiping);
        setWindowDimensions(this);
        imgEndCall = (ImageView) findViewById(R.id.imgEndCall);
        imgAccept = (ImageView) findViewById(R.id.imgAccept);
        imgReject = (ImageView) findViewById(R.id.imgReject);
        setImageAcceptTouchEvent(imgAccept);
        setImageAcceptTouchEvent(imgReject);
        mPermissionState = new SparseArray<PermissionRecord>() {
            {
                append(REQUEST_CAMERA,
                        new PermissionRecord(
                                Manifest.permission.CAMERA,
                                R.string.permission_camera_rationale,
                                R.string.permission_camera_granted
                        )
                );
                append(REQUEST_INTERNET,
                        new PermissionRecord(
                                Manifest.permission.INTERNET,
                                R.string.permission_internet_rationale,
                                R.string.permission_internet_granted
                        )
                );
                append(REQUEST_RECORDAUDIO,
                        new PermissionRecord(
                                Manifest.permission.RECORD_AUDIO,
                                R.string.permission_audiorecord_rationale,
                                R.string.permission_audiorecord_granted
                        )
                );
                append(REQUEST_AUDIOSETTINGS,
                        new PermissionRecord(
                                Manifest.permission.MODIFY_AUDIO_SETTINGS,
                                R.string.permission_audiosettings_rationale,
                                R.string.permission_audiosettings_granted
                        )
                );
                append(WRITE_EXTERNALSTORAGE,
                        new PermissionRecord(
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                R.string.permission_writeexternalstorage_rationale,
                                R.string.permission_writeexternalstorage_granted
                        )
                );
            }
        };

        requestPermissions(count);
    }

    private void requestPermissions(int permissionIndex) {

        if (permissionIndex < mPermissionState.size()) {
            final PermissionRecord permissionInfo = mPermissionState.valueAt(permissionIndex);
            final int permissionTag = mPermissionState.keyAt(permissionIndex);

            Log.d("swipe", "Requesting permission: " + permissionInfo.getPermission());
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, permissionInfo.getPermission())) {
                final String permissionArray[] = new String[]{permissionInfo.getPermission()};
                /*
                if (ActivityCompat.shouldShowRequestPermissionRationale(((MainActivity) getActivity()), permissionInfo.getPermission())) {
                    Snackbar.make(
                            layout,
                            permissionInfo.getRequestMsgId(),
                            Snackbar.LENGTH_INDEFINITE
                    ).setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(
                                    ((MainActivity) getActivity()),
                                    permissionArray,
                                    permissionTag
                            );
                        }
                    }).show();
                } else {
                    ActivityCompat.requestPermissions(
                            ((MainActivity) getActivity()),
                            permissionArray,
                            permissionTag
                    );
                }
                */
            } else {
                permissionInfo.grantPermission();
                requestPermissions(permissionIndex + 1);
            }
        }
    }

    private static class PermissionRecord {
        private String mPermission;
        private int mRequestMsgId;
        private int mGrantMsgId;
        private boolean mIsGranted;

        public PermissionRecord(String permission, int requestMsgId, int grantMsgId) {
            mPermission = permission;
            mRequestMsgId = requestMsgId;
            mGrantMsgId = grantMsgId;
            mIsGranted = false;
        }

        public String getPermission() {
            return mPermission;
        }

        public int getRequestMsgId() {
            return mRequestMsgId;
        }

        public int getGrantMsgId() {
            return mGrantMsgId;
        }

        public boolean isPermissionGranted() {
            return mIsGranted;
        }

        public void grantPermission() {
            mIsGranted = true;
        }
    }

    private static final int REQUEST_CAMERA = 0xf5;
    private static final int REQUEST_INTERNET = 0x11;
    private static final int REQUEST_RECORDAUDIO = 0x35;
    private static final int REQUEST_AUDIOSETTINGS = 0xe9;
    private static final int WRITE_EXTERNALSTORAGE = 0x50;

    private SparseArray<PermissionRecord> mPermissionState;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        final int requestKey = requestCode;
        //final View              layout      = findViewById(R.id.listview);
        final PermissionRecord permission = mPermissionState.get(requestCode);
        /*
        Snackbar.Callback       snackBarCB  = new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar snackbar, int event) {
                super.onDismissed(snackbar, event);
                requestPermissions(mPermissionState.indexOfKey(requestKey) + 1);
            }
        };

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            permission.isPermissionGranted();
            Snackbar.make(
                    layout,
                    permission.getGrantMsgId(),
                    Snackbar.LENGTH_SHORT
            ).setCallback(snackBarCB).show();
        } else {
            Snackbar.make(
                    layout,
                    "Permissions were not granted",
                    Snackbar.LENGTH_SHORT
            ).setCallback(snackBarCB).show();
        }
        */
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

        mDetector = new GestureDetector(SwipeScreenExample.this, this);
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

        Toast.makeText(this, "Call Answered", Toast.LENGTH_LONG).show();
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

        Toast.makeText(this, "Call Rejected", Toast.LENGTH_LONG).show();
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
}