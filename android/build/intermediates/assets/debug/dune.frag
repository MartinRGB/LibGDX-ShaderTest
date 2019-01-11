#ifdef GL_ES
    #define LOWP lowp
    #define MED mediump
    #define HIGH highp
    precision mediump float;
#else
    #define MED
    #define LOWP
    #define HIGH
#endif

#define COLOR vec3(0.0, 0.3, 0.5)

uniform vec2 u_resolution;
uniform float u_speed;
uniform float u_size;

varying float v_time;


void main(void) {

    vec2 p = gl_FragCoord.xy / u_resolution.xy;
    float s = v_time * u_speed;
    float t = s
              + sin(gl_FragCoord.x/(u_size * 4.0)
              + sin(s + gl_FragCoord.y/(u_size * 28.0)) * 0.02)
              + cos(gl_FragCoord.y/(u_size * 4.0)
              + cos(s * 0.7 + gl_FragCoord.x/(u_size * 32.0)) * 3.0);

	float c = 0.0;
	c += sin(p.x * 42.0 + sin(t) * 8.0 * cos(t * 3.5)) * cos(p.y * 6.0 + cos(t) * 4.0 * cos(t * 1.75));

	gl_FragColor = vec4(vec3(c) * COLOR, 1.0);

}


