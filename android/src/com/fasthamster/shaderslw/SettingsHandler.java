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

import android.content.Context;
import android.content.SharedPreferences;



public class SettingsHandler {

    private Context context;
    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    private int shader;
    private float speed;
    private int size;

    private boolean preferencesChanged;


    public SettingsHandler(Context context) {
        this.context = context;
        load();
    }

    private void load() {

        settings = context.getSharedPreferences(ShadersLW.PREFS_NAME, 0);
        shader = settings.getInt(ShadersLW.SHADER_ALIAS, ShadersLW.SHADER_DEFAULT);
        speed = settings.getFloat(ShadersLW.SPEED_ALIAS, ShadersLW.SPEED_DEFAULT);
        size = settings.getInt(ShadersLW.SIZE_ALIAS, ShadersLW.SIZE_DEFAULT);

        preferencesChanged = false;

    }

    public void save() {

        editor = settings.edit();
        editor.putInt(ShadersLW.SHADER_ALIAS, shader);
        editor.putFloat(ShadersLW.SPEED_ALIAS, speed);
        editor.putInt(ShadersLW.SIZE_ALIAS, size);
        editor.commit();

        preferencesChanged = false;
    }

    public boolean isPreferencesChanged() {

        return preferencesChanged;

    }


    public int getShader() {
        return shader;
    }
    public float getSpeed() {
        return speed;
    }
    public int getSize() {
        return size;
    }


    public void setShader(int t) {
        this.shader = t;
        this.preferencesChanged = true;
    }
    public void setSpeed(float s) {
        this.speed = s;
        this.preferencesChanged = true;
    }
    public void setSize(int z) {
        this.size = z;
        this.preferencesChanged = true;
    }
}
