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

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;


public class ShadersLW extends ApplicationAdapter {

	/** tag for log messages **/
	public static final String TAG = ShadersLW.class.getName();
	/** shared preferences name **/
	public static final String PREFS_NAME = "settings";
	/** speed preference name and default value **/
	public static final String SPEED_ALIAS = "speed";
	public static final float SPEED_DEFAULT = 0.5f;
	/** size preference name and default value **/
	public static final String SIZE_ALIAS = "size";
	public static final int SIZE_DEFAULT = 50;
	/** minimal size value,
	 * used to prevent shader items size less than that value **/
	public static final int SIZE_MIN_VALUE = 1;
	/** shader preferences name and default shader for run on app start **/
	public static final String SHADER_ALIAS = "shader";
	public static final int SHADER_DEFAULT = 0;

	/** shared preferences **/
	private Preferences preferences;

	/** fragment shaders holder **/
	private Shaders shaders;

	/** projection matrix **/
	private Matrix4 projection = new Matrix4();
	/** device screen resolution **/
	private Vector2 resolution = new Vector2();
	/** shader program **/
	private ShaderProgram shader;
	/** mesh to display shader **/
	private Mesh mesh;
	/** fragment and vertex shader source **/
	private String vert, frag;

	/** shader animation speed **/
	private float speed;
	/** shader items size **/
	private float size;
	/** counter of app run time, initial value on app start is zero **/
	private float time = 0f;
	/** pause/resume app flag **/
	private boolean paused;
	/** if true, shared preferences should be reloaded, default - false **/
	private static boolean preferencesChanged;


	// Constructor
	public ShadersLW(Shaders shaders) {
		// Receives ShadersHandler from Android app part
		this.shaders = shaders;

	}


	@Override
	public void create () {
		// In release  disable debug logging
		Gdx.app.setLogLevel(Application.LOG_DEBUG);
//		Gdx.app.setLogLevel(Application.LOG_DEBUG);

		// Load shared preferences
		preferences = Gdx.app.getPreferences(PREFS_NAME);
		setupPreferences();
		// App ready to run
		paused = false;


	}

	@Override
	public void render() {

		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);


		if(!paused) {
			// Update app running time counter
			time += Gdx.graphics.getDeltaTime();

			shader.begin();
			shader.setUniformMatrix("u_projTrans", projection);
			shader.setUniformf("u_time", time);
			shader.setUniformf("u_resolution", resolution);
			shader.setUniformf("u_speed", speed);
			shader.setUniformf("u_size", size);

			mesh.render(shader, GL20.GL_TRIANGLES);


			Gdx.app.log("FPSLogger","fps:" + Gdx.graphics.getFramesPerSecond());



			shader.end();

		}

	}

	@Override
	public void resize(int w, int h) {
		// Check if resolution was actually changed
		if(w != resolution.x) {
			// Save dimensions for future use
			resolution.set((float)w, (float)h);
			// Set projection matrix
			projection.setToOrtho2D(0f, 0f, resolution.x, resolution.y);
			// If resolution was changed we need to create new mesh with new resolution values
			if(mesh != null) mesh.dispose();
			mesh = setupMesh();

		}
	}

	@Override
	public void pause() {
		paused = true;
	}

	@Override
	public void resume() {

		if(preferencesChanged == true)
			setupPreferences();

		paused = false;
	}

	@Override
	public void dispose() {

		if(shader != null) shader.dispose();
		if(mesh != null) mesh.dispose();

	}

	// Setup shader
	private void setupShader(int id) {
		// If not loaded, load vertex shader from assets
		if(vert == null)
			vert = Gdx.files.internal("default.vert").readString();
		// Load fragment shader
		// Shader file name getting from ShaderHandler shaders array by shader id
		frag = Gdx.files.internal(shaders.getShaderFile(id)).readString();
		// Setup shader program
		shader = new ShaderProgram(vert, frag);

		if (shader.getLog().length() != 0)
			Gdx.app.log(TAG, shader.getLog());

		// In some shaders we aren't using some uniforms
		// To avoid shader error messages on that reason we set that option to false
		ShaderProgram.pedantic = false;

	}

	// Setter to force reload shared preferences
	public static void setPreferencesChanged(boolean state) {
		preferencesChanged = state;
	}

	// Setup shared preferences
	private void setupPreferences() {

		speed = preferences.getFloat(SPEED_ALIAS, SPEED_DEFAULT);
		size = (float)preferences.getInteger(SIZE_ALIAS, SIZE_DEFAULT);

		setupShader(preferences.getInteger(SHADER_ALIAS, SHADER_DEFAULT));

		preferencesChanged = false;

	}

	// Setup mesh to display shader
	// Mesh size is equal to device screen resolution
	private Mesh setupMesh() {

		Mesh mesh = new Mesh(true,
				4, 6,
				new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_position"));

		mesh.setVertices(new float[]{           // Vertices
				resolution.x, 0f, 0f,
				0f, 0f, 0f,
				0f, resolution.y, 0f,
				resolution.x, resolution.y, 0f});

		mesh.setIndices(new short[]{0, 1, 2, 2, 3, 0});

		return mesh;

	}

/**
  	// Example with a_texCoord0 attribute, if you need it in your shader
	 private Mesh setupMesh() {

		 Mesh mesh = new Mesh(true,
				 4, 6,
				 new VertexAttribute(VertexAttributes.Usage.Position, 3, "a_position"),
				 new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, "a_texCoord0"));

		 mesh.setVertices(new float[]{           // Vertices + UVs
				 resolution.x, 0f, 0f, 0f, 1f,
				 0f, 0f, 0f, 1f, 1f,
				 0f, resolution.y, 0f, 1f, 0f,
				 resolution.x, resolution.y, 0f, 0f, 0f});

		 mesh.setIndices(new short[]{0, 1, 2, 2, 3, 0});
 	}
**/
}
