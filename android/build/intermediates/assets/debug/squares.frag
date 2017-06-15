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


// Random function from
// http://byteblacksmith.com/improvements-to-the-canonical-one-liner-glsl-rand-for-opengl-es-2-0/
float randomize(vec2 coords){

	float a = 12.9898;
    float b = 78.233;
    float c = 43758.5453;
    float dt= dot(coords.xy ,vec2(a,b));
    float sn= mod(dt,3.14);

    return fract(sin(sn) * c);
}

// Generate random color
vec3 color(vec2 f){
	return vec3(randomize(f.xy),
                randomize(f.xy * 20.0),
                randomize(f.xy * 37.0));
}

bool square(vec2 g, float s, vec2 f){
    // Square center
	vec2 c = f + (s / 2.0);
	// Magic
	float q = (v_time * u_speed)+(randomize(f * 98.0) * 2.0);
	// Triangle wave to grow and shrink squares
	// https://www.wolframalpha.com/input/?i=abs(mod(x,2)-1)
	float w = (abs(mod(q, 2.0) - 1.0) / 2.0) * s;

    // If coords of pixel in square return true, otherwise return false
    return (abs(g.x - c.x) < w && abs(g.y - c.y) < w);
}

void main( void ) {
    float s = u_size * 2.0;
    // Divide screen to squares
	vec2 f = gl_FragCoord.xy-mod(gl_FragCoord.xy, s);
    // Convert bool to float: 1-true, 0-false
    // choose between black color and square color
    // this maided to avoid if statement: if(true) { BLACK } else { SQUARE_COLOR }
    gl_FragColor = vec4(mix(vec3(0.0), color(f), float(square(gl_FragCoord.xy, s, f))), 1.0);

}