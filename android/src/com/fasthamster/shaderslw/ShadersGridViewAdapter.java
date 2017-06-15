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
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class ShadersGridViewAdapter extends BaseAdapter {

    private Context context;


    // Constructor
    public ShadersGridViewAdapter(Context context) {

        this.context = context;

    }

    @Override
    public int getCount() {

        return LiveWallpaper.shaders.getLength();

    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        TextView name;
        ImageView icon;
        View v = view;

        if (v == null) {

            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            v = inflater.inflate(R.layout.cell, viewGroup, false);
            name = (TextView)v.findViewById(R.id.tv_name);
            icon = (ImageView)v.findViewById(R.id.iv_cell);

/*          // Highlight current shader
            if(i == LiveWallpaper.settings.getShader()) {
                v.setBackgroundColor(Color.BLUE);
            } else {
                v.setBackgroundColor(Color.TRANSPARENT);
            }
*/
            v.setTag(R.id.tag_name, name);
            v.setTag(R.id.tag_icon, icon);

        } else {

            name = (TextView)v.getTag(R.id.tag_name);
            icon = (ImageView)v.getTag(R.id.tag_icon);

        }

        name.setText(LiveWallpaper.shaders.getName(i));
        icon.setImageResource(LiveWallpaper.shaders.getIcon(i));
        name.setId(i);
        icon.setId(i);

        return v;

    }
}

