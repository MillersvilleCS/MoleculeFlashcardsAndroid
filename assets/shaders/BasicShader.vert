uniform mat4 u_projection;
uniform mat4 u_view;
uniform mat4 u_model;

attribute vec4 in_position;
attribute vec4 in_color;
attribute vec2 in_textureCoord;

varying vec4 pass_color;
varying vec2 pass_textureCoord;

void main(void) {
	gl_Position = u_projection * u_view * u_model * in_position;
	
	pass_color = in_color;
	pass_textureCoord = in_textureCoord;
}