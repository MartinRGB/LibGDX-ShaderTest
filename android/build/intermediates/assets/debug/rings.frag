// shader from http://glslsandbox.com/e#38710.0

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

uniform vec2 u_resolution;
uniform float u_speed;
uniform float u_size;

varying float v_time;


void main(void) {

    float size = 170.0 - u_size;
    float speed = v_time * u_speed;
	float scale = u_resolution.y / (size * 0.6);
	float ring = size * 0.2;
	float radius = u_resolution.x * 1.0;
	float gap = scale * 0.56;
	vec2 pos = gl_FragCoord.xy - u_resolution.xy * 0.5;

	float d = length(pos);

	// Create the wiggle
	d += (sin(pos.y * 0.25 / scale + speed) * sin(pos.x * 0.25 / scale + speed * 0.5)) * scale * 5.0;

	// Compute the distance to the closest ring
	float v = mod(d + radius / (ring * 2.0), radius / ring);
	v = abs(v - radius / (ring * 2.0));

	v = clamp(v-gap, 0.0, 1.0);

	d /= radius;
	vec3 m = fract((d - 1.0) * vec3(ring * -0.5, -ring, ring * 0.25) * 0.5);

	gl_FragColor = vec4(m * v, 1.0);
}