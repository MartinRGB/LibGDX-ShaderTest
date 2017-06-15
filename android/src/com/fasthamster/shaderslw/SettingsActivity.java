/*
 *  Copyright 2017 Alexey Smovzh (alexey.smovzh@gmail.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.fasthamster.shaderslw;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;



public class SettingsActivity extends Activity {

    private SeekBar speedBar, sizeBar;
    private TextView speedResult, sizeResult;
    private LinearLayout selectShader;
    private TextView shaderName;
    private LinearLayout buttonRateMe;


    private static final int SHADER_DIALOG_RESULT_CODE = 0;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        // Layouts
        setContentView(R.layout.settings);
        speedBar = (SeekBar)findViewById(R.id.sb_speed);
        speedResult = (TextView)findViewById(R.id.tv_speed_result);
        sizeBar = (SeekBar)findViewById(R.id.sb_size);
        sizeResult = (TextView)findViewById(R.id.tv_size_result);
        selectShader = (LinearLayout)findViewById(R.id.ll_select_shader);
        shaderName = (TextView)findViewById(R.id.tv_shader_name);
        buttonRateMe = (LinearLayout) findViewById(R.id.btn_rate);


        // Speed SeekBar listener
        speedBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                // Check if human change value
                if(b == true) {
                    LiveWallpaper.settings.setSpeed(speed2float(progress));
                    speedResult.setText(Integer.toString(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Size SeekBar listener
        sizeBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                // Check if human change value
                if(b == true) {
                    // Because shader item size can't be 0, set it to SIZE_MIN_VALUE = 1
                    if(progress < ShadersLW.SIZE_MIN_VALUE)
                        progress = ShadersLW.SIZE_MIN_VALUE;

                    LiveWallpaper.settings.setSize(progress);
                    sizeResult.setText(Integer.toString(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Shader choose listener
        selectShader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Run ShaderDialog activity
                Intent intent = new Intent(SettingsActivity.this, ShadersDialog.class);
                intent.putExtra(ShadersLW.SHADER_ALIAS, LiveWallpaper.settings.getShader());
                startActivityForResult(intent, SHADER_DIALOG_RESULT_CODE);
            }
        });

        // RateMe button listener
        buttonRateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent market = new Intent(Intent.ACTION_VIEW);
                market.setData(Uri.parse("market://details?id=" + this.getClass().getPackage().getName()));

                Intent website = new Intent(Intent.ACTION_VIEW);
                website.setData(Uri.parse("https://play.google.com/store/apps/details?id=" + this.getClass().getPackage().getName()));

                // Trying to open app page in Google Play Store App,
                // if it fails, open app page in internet browser
                try {
                    startActivity(market);
                } catch (ActivityNotFoundException e) {
                    startActivity(website);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SHADER_DIALOG_RESULT_CODE)
            shaderName.setText(LiveWallpaper.shaders.getName(LiveWallpaper.settings.getShader()).toUpperCase());

    }

    @Override
    public void onStart() {

        super.onStart();
        // Load states
        speedBar.setProgress(speed2int(LiveWallpaper.settings.getSpeed()));
        speedResult.setText(Integer.toString(speed2int(LiveWallpaper.settings.getSpeed())));
        sizeBar.setProgress(LiveWallpaper.settings.getSize());
        sizeResult.setText(Integer.toString(LiveWallpaper.settings.getSize()));
        shaderName.setText(LiveWallpaper.shaders.getName(LiveWallpaper.settings.getShader()).toUpperCase());

    }

    @Override
    public void onPause() {
        // Check if preferences was actually changed
        if(LiveWallpaper.settings.isPreferencesChanged() == true) {
            LiveWallpaper.settings.save();
            ShadersLW.setPreferencesChanged(true);
        }

        super.onPause();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    // Convert values from float range 0..1, to int range 0..100
    private int speed2int(float s) {
        return (int)(s * 100);
    }

    // Convert values from int range 0..100, to float range 0..1
    private float speed2float(int s) {
        return (float)s / 100f;
    }

}
