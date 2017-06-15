// shader from http://glslsandbox.com/e#38675.3

#ifdef GL_ES
    #define LOWP lowp
    #define MED mediump
    #define HIGH highp
    precision highp float;
#else
    #define MED
    #define LOWP
    #define HIGH
#endif

uniform vec2 u_resolution;
uniform float u_speed;
uniform float u_size;

varying float v_time;


//Noise - Perlin
//float random (in vec2 _st) {
//    return fract(sin(dot(_st.xy,
//                         vec2(12.9898,78.233)))*
//        43758.5453123);
//}

// Based on Morgan McGuire @morgan3d
// https://www.shadertoy.com/view/4dS3Wd
//float noise (in vec2 _st) {
//    vec2 i = floor(_st);
//    vec2 f = fract(_st);
//
//    // Four corners in 2D of a tile
//    float a = random(i);
//    float b = random(i + vec2(1.0, 0.0));
//    float c = random(i + vec2(0.0, 1.0));
//    float d = random(i + vec2(1.0, 1.0));
//
//    vec2 u = f * f * (3.0 - 2.0 * f);
//
//    return mix(a, b, u.x) +
//            (c - a)* u.y * (1. - u.x) +
//            (d - b) * u.x * u.y;
//}


//Noise2 - Value2D
float noise( vec2 _st )
{
    //  https://github.com/BrianSharpe/Wombat/blob/master/Value2D.glsl

    //	establish our grid cell and unit position
    vec2 Pi = floor(_st);
    vec2 Pf = _st - Pi;

    //	calculate the hash.
    vec4 Pt = vec4( Pi.xy, Pi.xy + 1.0 );
    Pt = Pt - floor(Pt * ( 1.0 / 71.0 )) * 71.0;
    Pt += vec2( 26.0, 161.0 ).xyxy;
    Pt *= Pt;
    Pt = Pt.xzxz * Pt.yyww;
    vec4 hash = fract( Pt * ( 1.0 / 951.135664 ) );

    //	blend the results and return
    vec2 blend = Pf * Pf * Pf * (Pf * (Pf * 6.0 - 15.0) + 10.0);
    vec4 blend2 = vec4( blend, vec2( 1.0 - blend ) );
    return dot( hash, blend2.zxzx * blend2.wwyy );
}


#define NUM_OCTAVES 5

float fbm ( in vec2 _st) {
    float v = 0.0;
    float a = 0.5;
    vec2 shift = vec2(100.0);
    // Rotate to reduce axial bias
    mat2 rot = mat2(cos(0.5), sin(0.5),
                    -sin(0.5), cos(0.50));
    for (int i = 0; i < NUM_OCTAVES; ++i) {
        v += a * noise(_st);
        _st = rot * _st * 2.0 + shift;
        a *= 0.5;
    }
    return v;
}

void main(void) {
    vec2 st = gl_FragCoord.xy/u_resolution.x*2. + 0.01*u_size ;
    // st += st * abs(sin(u_time*0.1)*3.0);
    vec3 color = vec3(0.0);

    vec2 q = vec2(0.);
    q.x = fbm( st  );
    q.y = fbm( st + vec2(1.0));

    vec2 r = vec2(0.);
    r.x = fbm( st + 1.0*q + vec2(1.7,9.2)+ 0.15*v_time );
    r.y = fbm( st + 1.0*q + vec2(8.3,2.8)+ 0.126*v_time);

    float f = fbm(st+r);

    color = mix(vec3(0.101961,0.619608,0.666667),
                vec3(0.666667,0.666667,0.498039),
                clamp((f*f)*4.0,0.0,1.0));

    color = mix(color,
                vec3(0,0,0.164706),
                clamp(length(q),0.0,1.0));

    color = mix(color,
                vec3(0.666667,1,1),
                clamp(length(r.x),0.0,1.0));


    gl_FragColor = vec4((f*f*f+.6*f*f+.5*f)*color,1.);
}