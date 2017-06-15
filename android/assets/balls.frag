// shader from http://glslsandbox.com/e#38675.3

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


vec3 ball(vec2 p, float fx, float fy, float ax, float ay, vec3 color, float t, float l) {

    vec2 r = vec2(p.x + cos(t * fx) * ax, p.y + sin(t * fy) * ay);
    float rlen = length(r) * l;

    return color * 0.01 / pow(rlen, 2.0) / length(color);
}



void main(void) {

    vec2 q = gl_FragCoord.xy / u_resolution.xy;
    vec2 p = -1.0 + 2.0 * q;
    p.x	*= u_resolution.x / u_resolution.y;

    float t = v_time * u_speed;
    float l = 1.5 - (u_size * 0.01);

    vec3 col = vec3(0.0, 0.0, 0.0);
    col += ball(p, 1.0, 2.0, 0.1, 0.2, vec3(1.0, 0.1, 0.1), t, l);
    col += ball(p, 1.5, 2.5, 0.2, 0.3, vec3(0.1, 1.0, 1.0), t, l);
    col += ball(p, 2.0, 3.0, 0.3, 0.4, vec3(0.6, 0.3, 0.8), t, l);
    col += ball(p, 2.5, 3.5, 0.4, 0.5, vec3(1.0, 0.5, 0.1), t, l);
    col += ball(p, 1.5, 0.5, 0.6, 0.7, vec3(0.3, 0.3, 1.0), t, l);
    col += ball(p, 3.0, 4.0, 0.5, 0.6, vec3(1.0, 1.0, 0.3), t, l);
    col += ball(p, 0.5, 3.1, 1.6, 0.9, vec3(0.1, 0.1, 1.0), t, l);
    col += ball(p, 0.5, 2.1, 1.0, 0.0, vec3(0.1, 1.0, 0.1), t, l);

    col *= 0.3;

    gl_FragColor = vec4(col, 1.0);
}