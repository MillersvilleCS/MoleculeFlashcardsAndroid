uniform sampler2D texture_diffuse;

varying vec4 pass_color;
varying vec4 pass_normal;
varying vec2 pass_textureCoord;

void main(void) {

	gl_FragColor = pass_color;
}