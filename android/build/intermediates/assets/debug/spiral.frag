// shader from http://glslsandbox.com/e#38956.2

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

#define COLOR vec3(0.2, 0.5, 1.0)

uniform vec2 u_resolution;
uniform float u_speed;
uniform float u_size;

varying float v_time;


vec4 k(vec2 uv) {

    float t = v_time * u_speed * 5.0;
    float j = sin(uv.y * 3.14 + t * 5.0);
    float i = sin(uv.x * 15.0 - uv.y * 2.0 * 3.14 + t * 3.0);
    float n = -clamp(i, -0.2, 0.0) - 0.0 * clamp(j, -0.2, 0.0);

    return 3.5 * (vec4(COLOR, 1.0) * n);
}

void main( void ) {

    float s = u_size * 0.02 + 0.5;

    float aspectRatio = u_resolution.x / u_resolution.y;
    vec2 p = -1.0 + 2.0 * gl_FragCoord.xy / u_resolution.xy;
    p.x *= aspectRatio;
    vec2 uv;

    float r = sqrt(dot(p, p));
    float a = atan(p.y * (0.3 + 0.1 * cos(v_time * 2.0 + p.y)),
                   p.x * (0.3 + 0.1 * sin(v_time + p.x))) + v_time;

    uv.x = v_time + s / (r + 0.01);
    uv.y = 4.0 * a / 3.1416;

    gl_FragColor = mix(vec4(0.0), k(uv) * r * r * 2.0, 1.0);
}