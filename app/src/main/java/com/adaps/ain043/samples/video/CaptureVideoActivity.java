package com.adaps.ain043.samples.video;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;

import com.adaps.ain043.samples.BuildConfig;
import com.adaps.ain043.samples.R;
import com.adaps.ain043.samples.databinding.ActivityCaptureVideoBinding;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by ain043 on 3/26/2018 at 2:11 PM
 */

public class CaptureVideoActivity extends AppCompatActivity implements VideosAdapter.IItemClick {
    private static final int ACTION_TAKE_VIDEO = 100;
    private ActivityCaptureVideoBinding binding;
    private File videoFile;
    private String storagePath;
    private int maxRecTime = 30;//30 sec
    private List<File> videosList = new ArrayList<>();
    private VideosAdapter videosAdapter;
    private MediaPlayer mediaPlayer;
    private MediaController mediaController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_capture_video);

        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        File dir = new File(dirPath, File.separator + "RecordVideo");
        if (!dir.exists()) {
            dir.mkdir();
        }
        storagePath = dir.getAbsolutePath();
        setAdapterForVideos();
        getVideos();
        binding.btnRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent dialogIntent = new Intent(
                        android.provider.Settings.ACTION_SECURITY_SETTINGS);

// put EXTRA from PreferenceActivity
//                dialogIntent.putExtra(PreferenceActivity.EXTRA_SHOW_FRAGMENT, mDeviceAdminFragment);

                dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(dialogIntent);
//                recordVideoClick();
            }
        });
    }

    private void setAdapterForVideos() {
        videosAdapter = new VideosAdapter(this, (VideosAdapter.IItemClick) this);
        binding.rvVideos.setLayoutManager(new GridLayoutManager(this, 3));
        binding.rvVideos.setAdapter(videosAdapter);
    }

    private void playVideo(String path) {
        mediaController = new MediaController(this);
        mediaController.setAnchorView(binding.videoview);
        mediaController.setMediaPlayer(binding.videoview);
        Uri video = Uri.parse(path);
        binding.videoview.setMediaController(mediaController);
        if (binding.videoview.isPlaying()) {
            binding.videoview.stopPlayback();
        }
        binding.videoview.setVideoURI(video);
        binding.videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                if (binding.rlVideo.getVisibility() == View.VISIBLE) {
                    mediaPlayer = mp;
                    binding.videoview.start();
                }
            }
        });
        binding.videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                binding.rlVideo.setVisibility(View.GONE);
                binding.rvVideos.setVisibility(View.VISIBLE);
                binding.btnRecord.setVisibility(videosAdapter.getItemCount() < 3 ? View.VISIBLE : View.GONE);
                binding.videoview.stopPlayback();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getVideos() {
        File directory = new File(storagePath);
        File[] files = directory.listFiles();
        if (files != null && files.length > 0) {
            videosList.addAll(Arrays.asList(files));
            videosAdapter.addAll(videosList);
        }
        binding.btnRecord.setVisibility(videosAdapter.getItemCount() < 3 ? View.VISIBLE : View.GONE);
    }

    private void recordVideoClick() {
        videoFile = FilesFolders.setUpVideoFile(storagePath);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            if (takeVideoIntent.resolveActivity(this.getPackageManager()) != null) {
                try {
                    if (videoFile != null) {
                        Uri videoUri = FileProvider.getUriForFile(this,
                                BuildConfig.APPLICATION_ID + ".provider", videoFile);
                        takeVideoIntent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
                        takeVideoIntent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, maxRecTime);
                        startActivityForResult(takeVideoIntent, ACTION_TAKE_VIDEO);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Uri videoUri = Uri.fromFile(videoFile);
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, maxRecTime);
            startActivityForResult(intent, ACTION_TAKE_VIDEO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case ACTION_TAKE_VIDEO:
                videosAdapter.addItem(videoFile);
                binding.btnRecord.setVisibility(videosAdapter.getItemCount() < 3 ? View.VISIBLE : View.GONE);
                break;
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        mediaPlayer.stop();
        binding.videoview.stopPlayback();
        mediaController.hide();
        binding.rlVideo.setVisibility(View.GONE);
        binding.rvVideos.setVisibility(View.VISIBLE);
        binding.btnRecord.setVisibility(videosAdapter.getItemCount() < 3 ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onPlayClick(File path) {
        binding.rlVideo.setVisibility(View.VISIBLE);
        binding.rvVideos.setVisibility(View.GONE);
        binding.btnRecord.setVisibility(View.GONE);
        playVideo(path.getAbsolutePath());
    }

    @Override
    public void onDelelteClick(final File category) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CaptureVideoActivity.this);

        builder.setTitle("Are you sure you want to delete this video?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        File file = new File(category.getAbsolutePath());
                        if (file.exists()) {
                            FilesFolders.deleteFile(file);
                            videosAdapter.removeItem(category);
                        }
                        binding.btnRecord.setVisibility(videosAdapter.getItemCount() < 3 ? View.VISIBLE : View.GONE);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
