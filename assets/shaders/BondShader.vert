precision mediump float;
uniform mat4 u_projection;
uniform mat4 u_view;
uniform mat4 u_model;

attribute vec4 in_position;
attribute vec4 in_normal;
attribute vec4 in_color;

varying vec4 pass_color;
varying vec4 pass_normal;

void main(void) {
	gl_Position = u_projection * u_view * u_model * in_position;
	
	pass_color = in_color;
	pass_normal = in_normal;
}