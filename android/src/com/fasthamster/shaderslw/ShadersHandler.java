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


public class ShadersHandler implements Shaders {

    // Hold shader information
    class Shader {
        // Displayed shader name in ShadersDialog and SettingsActivity
        public String name;
        // Shader source file name
        public String file;
        // Shader icon resource id
        public int icon;

        public Shader(String name, String file, int icon) {

            this.name = name;
            this.file = file;
            this.icon = icon;

        }
    }
/** To add new shader:
 *  1. create file with shader source code in android/assets folder
 *  2. create shader icons and place it to appropriate android/res/drawable folders
 *  3. add information about shader to shaders array */
    private Shader[] shaders = {
            new Shader("Squares", "squares.frag", R.drawable.squares),
            new Shader("Rings", "rings.frag", R.drawable.rings),
            new Shader("Net", "net.frag", R.drawable.net),
            new Shader("Dune", "dune.frag", R.drawable.dune),
            new Shader("Squiggles", "squiggles.frag", R.drawable.squiggles),
            new Shader("Balls", "balls.frag", R.drawable.balls),
            new Shader("Spiral", "spiral.frag", R.drawable.spiral),
            new Shader("Foggy", "foggy.frag", R.drawable.spiral)
    };

    @Override
    public String getShaderFile(int id) {

        return shaders[id].file;

    }

    public int getLength() {

        return shaders.length;

    }

    public String getName(int id) {

        return shaders[id].name;

    }

    public int getIcon(int id) {

        return shaders[id].icon;

    }
}
