package com.adaps.ain043.samples.miracast;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.adaps.ain043.samples.R;
import com.adaps.ain043.samples.databinding.ActivityTempMiraBinding;

/**
 * Created by ain043 on 3/13/2018 at 11:38 AM
 */

public class TempMiraActivty extends AppCompatActivity implements View.OnClickListener {
    private ActivityTempMiraBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_temp_mira);

        binding.btnConnect.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        openWiFiDisplay();
    }

    public void openWiFiDisplay() {
        try {
            startActivity(new Intent("android.settings.WIFI_DISPLAY_SETTINGS"));
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                startActivity(new Intent("com.samsung.wfd.LAUNCH_WFD_PICKER_DLG"));
            } catch (Exception e) {
                try {
                    startActivity(new Intent("android.settings.CAST_SETTINGS"));
                } catch (Exception e2) {
                    Toast.makeText(this, "Your device do not supported", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
