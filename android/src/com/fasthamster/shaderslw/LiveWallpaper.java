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

import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidLiveWallpaperService;
import com.badlogic.gdx.backends.android.AndroidWallpaperListener;



public class LiveWallpaper extends AndroidLiveWallpaperService {

    /** Shared preferences handler **/
    public static SettingsHandler settings;
    /** Shaders preferences handler **/
    public static ShadersHandler shaders;
    private ShadersLWListener listener;


    @Override
    public void onCreateApplication() {

        super.onCreateApplication();

        // Settings
        settings = new SettingsHandler(getApplicationContext());

        // Shaders
        shaders = new ShadersHandler();

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useCompass = false;
        config.useWakelock = false;
        config.getTouchEventsForLiveWallpaper = false;
        config.useAccelerometer = false;

        listener = new ShadersLWListener(shaders);

        initialize(listener, config);

    }

    @Override
    public Engine onCreateEngine() {
        return new AndroidWallpaperEngine(){
            @Override
            public void onPause(){
                super.onPause();
                listener.pause();
            }

            @Override
            public void onResume(){
                super.onResume();
            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    public static class ShadersLWListener extends ShadersLW implements AndroidWallpaperListener {

        public ShadersLWListener(Shaders shaders) {
            super(shaders);
        }

        @Override
        public void offsetChange(float xOffset, float yOffset, float xOffsetStep, float yOffsetStep, int xPixelOffset, int yPixelOffset) {

        }

        @Override
        public void previewStateChange(boolean isPreview) {

        }
    }
}

