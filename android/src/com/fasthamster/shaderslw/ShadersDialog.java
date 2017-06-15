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
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;



public class ShadersDialog extends Activity {

    private GridView shaders;
    private ShadersGridViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.shader_dialog);


        shaders = (GridView)findViewById(R.id.gv_icons);

        // Adapters
        adapter = new ShadersGridViewAdapter(this);
        shaders.setAdapter(adapter);

        // Shaders gridview listener
        shaders.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                LiveWallpaper.settings.setShader(i);

                finish();
            }
        });
    }

    @Override
    public void onPause() {

        LiveWallpaper.settings.save();
        ShadersLW.setPreferencesChanged(true);

        super.onPause();
    }
}

