package com.adaps.ain043.samples.ScreenAds;

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
import android.widget.VideoView;

import com.adaps.ain043.samples.R;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_video_image);

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
        if (isImage) {
            long totalTime = playImage.getTotalTime();
            long curTime = playImage.getCurTime();
            Log.e("Tag", "totalTime " + totalTime + " curTime " + curTime);
        } else {// video
            binding.videoView.pause();
        }
        //pause the current loop, start the special item to play
        VideoImage videoImage = data.getParcelable("item");
        Log.e("Tag", videoImage.contentType);
        playSpclItem(videoImage);
    }

    private void fixTimeFrameForSpclItems() {
        for (int i = 0; i < spclVideoImageList.size(); i++) {
            String[] time = spclVideoImageList.get(i).startAtTime.split(":");
            Calendar calSet = Calendar.getInstance();
            calSet.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[2]));
            calSet.set(Calendar.MINUTE, Integer.parseInt(time[1]));
            calSet.set(Calendar.SECOND, 0);
            calSet.set(Calendar.MILLISECOND, 0);

            Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("item", spclVideoImageList.get(i));
            intent.putExtras(bundle);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), 1, intent, 0);
            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.set(AlarmManager.RTC_WAKEUP, calSet.getTimeInMillis(), pendingIntent);
        }
    }

    private String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("json_image_video.json");
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
        if (videoImage.contentType.equalsIgnoreCase("image")) {
            Glide.with(this).load(videoImage.url).placeholder(R.mipmap.ic_launcher).into(binding.ivPicture);
            playImage = new PlayImage(this, videoImage.timeToDisplay);
            playImage.start();
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
        }
    }

    private void startPlaying() {
        for (int i = 0; i < videoImageList.size(); i++) {
            if (videoImageList.get(count).contentType.equalsIgnoreCase("image")) {
                isImage = true;
                Glide.with(this).load(videoImageList.get(count).url).placeholder(R.mipmap.ic_launcher).into(binding.ivPicture);
                playImage = new PlayImage(this, videoImageList.get(count).timeToDisplay);
                playImage.start();
            } else {//video
                isImage = false;
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
            }
            count++;
        }
    }

    @Override
    public void onFinished() {
        if (count == videoImageList.size()) count = 0;
        if (isSpclItem) isSpclItem = !isSpclItem;
        startPlaying();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (count == videoImageList.size()) count = 0;
        if (isSpclItem) isSpclItem = !isSpclItem;
        startPlaying();
    }
}
