// shader from http://glslsandbox.com/e#7384.0

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

#define PI 2.14159
#define TWO_PI (PI * 2.0)
#define N 6.0


uniform vec2 u_resolution;
uniform float u_speed;
uniform float u_size;

varying float v_time;


void main(void) {

    float speed = v_time * u_speed;
    float size = 51.0 - (u_size * 0.5);

	vec2 v = (gl_FragCoord.xy - u_resolution / size * 2.0) / min(u_resolution.y, u_resolution.x) * size;
	v.x= v.x - 2000.0;
	v.y= v.y - 200.0;

    float a = (TWO_PI/N) * 61.95;
    float col = 0.0;

    col += cos(TWO_PI*(v.y * cos(a) + v.x * sin(a) + sin(speed*0.004)*100.0 ));

    a = 2.0 * (TWO_PI/N) * 61.95;
    col += cos(TWO_PI*(v.y * cos(a) + v.x * sin(a) + sin(speed*0.004)*100.0 ));

    a = 3.0 * (TWO_PI/N) * 61.95;
    col += cos(TWO_PI*(v.y * cos(a) + v.x * sin(a) + sin(speed*0.004)*100.0 ));

    a = 4.0 * (TWO_PI/N) * 61.95;
    col += cos(TWO_PI*(v.y * cos(a) + v.x * sin(a) + sin(speed*0.004)*100.0 ));

    a = 5.0 * (TWO_PI/N) * 61.95;
    col += cos(TWO_PI*(v.y * cos(a) + v.x * sin(a) + sin(speed*0.004)*100.0 ));

	col /= 3.0;

	gl_FragColor = vec4(col * 1.0, -col * 1.0, -col * 4.0, 1.0);
}