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

attribute vec4 a_position;

uniform mat4 u_projTrans;
uniform float u_time;

varying float v_time;

void main() {
    v_time = u_time;
    gl_Position = u_projTrans * a_position;
}