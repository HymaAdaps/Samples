package com.adaps.ain043.samples.ScreenAds;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.VideoView;

import com.adaps.ain043.samples.R;
import com.adaps.ain043.samples.appupdater.MainActivity;
import com.adaps.ain043.samples.databinding.ActivityVideoImageBinding;
import com.bumptech.glide.Glide;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by ain043 on 3/5/2018 at 1:07 PM
 */

public class PlayVideoImageActivity extends AppCompatActivity implements PlayImage.onFinishListner, MediaPlayer.OnCompletionListener {

    private Handler handler;
    private Runnable runnable;
    private List<VideoImage> videoImageList, spclVideoImageList;
    private ActivityVideoImageBinding binding;
    private int count = 0;
    private PlayImage playImage;
    private boolean isImage = true, isSpclItem = false;
    private View splView;
    private boolean isPlaying = false;
    public static PlayVideoImageActivity parent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_image);


//        initComponents();
        playVideoInMiddle();
//        Playimageinmiddle();
    }

    private void Playimageinmiddle() {
        Glide.with(this).load("http://slappedham.com/wp-content/uploads/2014/06/Fluffy-small-dog.jpg").placeholder(R.mipmap.ic_launcher).into(binding.ivPicture);
        playImage = new PlayImage(this, "10000");
        playImage.start();
        makeViewVisible(binding.ivPicture);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                isSpclItem = true;
                playImage.stop();
                Glide.with(PlayVideoImageActivity.this).load("http://kittentoob.com/wp-content/uploads/2012/04/cat-pics18.jpg")
                        .placeholder(R.mipmap.ic_launcher).into(binding.ivPictureTime);
                playImage = new PlayImage(PlayVideoImageActivity.this, "5000");
                playImage.start();
                makeViewVisibleOnItem(binding.ivPictureTime);
            }
        }, 2000);
    }

    private void playVideoInMiddle() {
        isImage = false;
        String path = "android.resource://" + getPackageName() + "/" + R.raw.sample_video;
        Uri video = Uri.parse(path);
        binding.videoView.setVideoURI(video);
//                binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                        binding.videoView.start();
//                    }
//                });
        binding.videoView.start();
        binding.videoView.setOnCompletionListener(this);
        makeViewVisible(binding.videoView);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isSpclItem = true;
                if (binding.videoView.isPlaying())
                    binding.videoView.pause();
                String path = "android.resource://" + getPackageName() + "/" + R.raw.sample_video;
                Uri video = Uri.parse(path);
                binding.videoViewTime.setVideoURI(video);
                binding.videoViewTime.start();
                binding.videoViewTime.setOnCompletionListener(PlayVideoImageActivity.this);
                makeViewVisibleOnItem(binding.videoViewTime);
            }
        }, 2000);
    }

    private void initComponents() {
        parent = this;

        String input = loadJSONFromAsset();
        videoImageList = new ArrayList<>();
        spclVideoImageList = new ArrayList<>();
        try {
            JSONObject object = new JSONObject(input);
            JSONArray array = object.getJSONArray("concierge");
            for (int i = 0; i < array.length(); i++) {
                VideoImage category = new GsonBuilder().create().fromJson(array.get(i).toString(), VideoImage.class);
                if (!TextUtils.isEmpty(category.startAtTime))
                    spclVideoImageList.add(category);
                else
                    videoImageList.add(category);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        startPlaying();
        fixTimeFrameForSpclItems();
    }

    public void playAtTime(Bundle data) {
        //check video/image is playing
        if (isImage && playImage != null) {
            long totalTime = playImage.getTotalTime();
            long curTime = playImage.getCurTime();
            Log.e("Tag", "totalTime " + totalTime + " curTime " + curTime);
        } else {// video
            if (binding.videoView.isPlaying())
                binding.videoView.pause();
        }
        //pause the current loop, start the special item to play

//        VideoImage videoImage = (VideoImage) data.getSerializable("item");
        VideoImage videoImage = new VideoImage();
        videoImage.name = data.getString("name");
        videoImage.contentType = data.getString("contentType");
        videoImage.tileId = data.getString("tileId");
        videoImage.timeToDisplay = data.getString("timeToDisplay");
        videoImage.url = data.getString("url");
        videoImage.startAtTime = data.getString("startAtTime");
        playSpclItem(videoImage);
    }

    private void fixTimeFrameForSpclItems() {
        for (int i = 0; i < spclVideoImageList.size(); i++) {
            String[] time = spclVideoImageList.get(i).startAtTime.split(":");
            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
            calendar.set(Calendar.MINUTE, Integer.parseInt(time[1]));
            calendar.set(Calendar.SECOND, 0);

            Bundle bundle = new Bundle();
            bundle.putString("message", "play this at " + time[0] + time[1]);
//            bundle.putSerializable("item", videoImageList.get(i));
//            bundle.putParcelable("item", spclVideoImageList.get(i));
            bundle.putString("name", spclVideoImageList.get(i).name);
            bundle.putString("contentType", spclVideoImageList.get(i).contentType);
            bundle.putString("tileId", spclVideoImageList.get(i).tileId);
            bundle.putString("timeToDisplay", spclVideoImageList.get(i).timeToDisplay);
            bundle.putString("url", spclVideoImageList.get(i).url);
            bundle.putString("startAtTime", spclVideoImageList.get(i).startAtTime);

            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtras(bundle);

            PendingIntent pi = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager am = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);
        }
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getResources().getAssets().open("temp_list.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    private void playSpclItem(VideoImage videoImage) {
        isSpclItem = true;
        if (isActivityRunning(PlayVideoImageActivity.class)) {
            if (videoImage.contentType.equalsIgnoreCase("image")) {
                Glide.with(this).load(videoImage.url).placeholder(R.mipmap.ic_launcher).into(binding.ivPicture);
                playImage = new PlayImage(this, videoImage.timeToDisplay);
                playImage.start();
                makeViewVisibleOnItem(binding.ivPictureTime);
            } else {//video
                String path = "android.resource://" + getPackageName() + "/" + R.raw.sample_video;
                Uri video = Uri.parse(path);
                binding.videoView.setVideoURI(video);
                binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        binding.videoView.start();
                    }
                });
                binding.videoView.setOnCompletionListener(this);
                makeViewVisibleOnItem(binding.ivPictureTime);
            }
        } else {
            Log.e("Activity", "not running");
        }
    }

    protected Boolean isActivityRunning(Class activityClass) {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = activityManager.getRunningTasks(Integer.MAX_VALUE);

        for (ActivityManager.RunningTaskInfo task : tasks) {
            if (activityClass.getCanonicalName().equalsIgnoreCase(task.baseActivity.getClassName()))
                return true;
        }

        return false;
    }

    private void startPlaying() {
//        for (int i = 0; i < videoImageList.size(); i++) {
        if (videoImageList.get(count).contentType.equalsIgnoreCase("image")) {
            isImage = true;
            Glide.with(this).load(videoImageList.get(count).url).placeholder(R.mipmap.ic_launcher).into(binding.ivPicture);
            playImage = new PlayImage(this, videoImageList.get(count).timeToDisplay);
            playImage.start();
            makeViewVisible(binding.ivPicture);
        } else {//video
            isImage = false;
            String path = "android.resource://" + getPackageName() + "/" + R.raw.sample_video;
            Uri video = Uri.parse(path);
            binding.videoView.setVideoURI(video);
//                binding.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                    @Override
//                    public void onPrepared(MediaPlayer mp) {
//                        binding.videoView.start();
//                    }
//                });
            binding.videoView.start();
            binding.videoView.setOnCompletionListener(this);
            makeViewVisible(binding.videoView);
        }
        count++;
//        }
    }

    private void makeViewVisible(View picture) {
        binding.ivPicture.setVisibility(View.GONE);
        binding.videoView.setVisibility(View.GONE);
        binding.ivPictureTime.setVisibility(View.GONE);
        binding.videoViewTime.setVisibility(View.GONE);

        picture.setVisibility(View.VISIBLE);
    }

    private void makeViewVisibleOnItem(View picture) {
//        binding.ivPicture.setVisibility(View.GONE);
//        binding.videoView.setVisibility(View.GONE);
//        binding.ivPictureTime.setVisibility(View.GONE);
//        binding.videoViewTime.setVisibility(View.GONE);
        splView = picture;
        picture.setVisibility(View.VISIBLE);
        binding.videoView.setVisibility(View.GONE);
    }

    @Override
    public void onFinished() {
//        if (count == videoImageList.size()) count = 0;
        if (isSpclItem) {
            isSpclItem = !isSpclItem;
            splView.setVisibility(View.GONE);
            if (isImage) {//consider previosly image is shown
//                Glide.with(this).load("http://slappedham.com/wp-content/uploads/2014/06/Fluffy-small-dog.jpg").placeholder(R.mipmap.ic_launcher).into(binding.ivPicture);
//                playImage = new PlayImage(this, String.valueOf(playImage.getTotalTime() - playImage.getCurTime()));
//                playImage.start();
                splView.setVisibility(View.GONE);

                playImage.setTotalTime(10000);
                playImage.start();
            } else {
                binding.videoView.resume();
            }
        } else {
            Log.e("Done", "done");
//            startPlaying();
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
//        if (count == videoImageList.size()) count = 0;
        if (isSpclItem) {
            isSpclItem = !isSpclItem;
            splView.setVisibility(View.GONE);
            binding.videoView.setVisibility(View.VISIBLE);
            if (isImage) {//consider previosly image is shown
                Glide.with(this).load(videoImageList.get(count).url).placeholder(R.mipmap.ic_launcher).into(binding.ivPicture);
                playImage = new PlayImage(this, String.valueOf(playImage.getTotalTime() - playImage.getCurTime()));
                playImage.start();
            } else {
                String path = "android.resource://" + getPackageName() + "/" + R.raw.sample_video;
                Uri video = Uri.parse(path);
                binding.videoView.setVideoURI(video);
                binding.videoView.seekTo(5000);
                binding.videoView.start();
            }
        } else {
            Log.e("Done", "done");
//            startPlaying();
        }

    }

}
