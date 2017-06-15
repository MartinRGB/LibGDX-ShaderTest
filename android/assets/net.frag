// shader from http://glslsandbox.com/e#3452.5


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

#define PI 3.1415926535
#define TWOPI (PI * 2.0)
#define m mat2(0.80,  0.60, -0.60,  0.80)

uniform vec2 u_resolution;
uniform float u_speed;
uniform float u_size;

varying float v_time;


float hash( float n ) {

    return fract(sin(n) * 43758.5453);

}

float noise( in vec2 x ) {

    vec2 p = floor(x);
    vec2 f = fract(x);
    f = f * f * (3.0 - 2.0 * f);
    float n = p.x + p.y * 57.0;
    float res = mix(mix(hash(n + 0.0), hash(n + 1.0), f.x), mix(hash(n + 57.0), hash(n + 58.0), f.x), f.y);

    return res;
}

float fbm( vec2 p ) {
    float f = 0.0;
    f += 0.50000*noise( p ); p = m*p*2.02;
    f += 0.25000*noise( p ); p = m*p*2.03;
    f += 0.12500*noise( p ); p = m*p*2.01;
    f += 0.06250*noise( p ); p = m*p*2.04;
    f += 0.03125*noise( p );

    return f/0.984375;
}

float unsin(float t) {

	return sin(t)*0.5+0.5;

}

float curve( float x) {

	return smoothstep( 0.5, 0.0, cos(x*PI) );

}

float thing(vec2 pos) {

	float n = fbm(pos*0.4);
	float offset = 0.;
	float row = floor((pos.y)/2.);
	if (mod(row, 2.0) < 1.0)
		offset = 1.0 * n;

	float a = curve(pos.x + offset + n);
	float b = curve(pos.y);
	float c = (a * b);

	float d = curve(pos.x + offset - n);
	float e = curve(pos.y);
	float f = (d * e + offset + n) * (sqrt(d*e*fbm(pos * 0.1 - 14.)));

	return smoothstep(2.0 - n * fbm(pos*10.), c - n, f + n);
}

void main(void) {

    float speed = v_time * u_speed;
    float size = u_size * 0.5;

	vec2 position = gl_FragCoord.xy / u_resolution.xy;
	vec2 world = position * size;
	world.x *= u_resolution.x / u_resolution.y;
	world += speed;
	float dist = thing(world);

	gl_FragColor = vec4(dist, dist, dist, 1.0 );
}
