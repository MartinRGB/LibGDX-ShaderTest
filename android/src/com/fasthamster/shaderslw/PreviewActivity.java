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
import android.app.WallpaperInfo;
import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;



public class PreviewActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        WallpaperManager manager = WallpaperManager.getInstance(this);
        WallpaperInfo info = manager.getWallpaperInfo();

        // If wallpaper already running, start wallpaper settings
        // otherwise open setup live wallpapers intent
        if (info != null && this.getPackageName().equals(info.getPackageName().toString())) {
            startActivity(setupSettingsIntent());
        } else {
            startActivity(setupWallpaperIntent());
        }

        finish();

    }

    private Intent setupWallpaperIntent() {

        Intent intent = new Intent();

        if(Build.VERSION.SDK_INT >= 16) {

            intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
            intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    new ComponentName(this, LiveWallpaper.class));

        } else {

            intent.setAction(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER);
            Resources res = getResources();
            String hint = res.getString(R.string.toast_instruct_lwp_list) + res.getString(R.string.app_name);
            Toast toast = Toast.makeText(this, hint, Toast.LENGTH_LONG);
            toast.show();

        }

        return intent;
    }

    private Intent setupSettingsIntent() {

        return new Intent(this, SettingsActivity.class);

    }
}
