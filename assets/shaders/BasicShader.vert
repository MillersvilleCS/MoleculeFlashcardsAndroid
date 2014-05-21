
uniform mat4 u_projection;
uniform mat4 u_view;
uniform mat4 u_model;

attribute vec4 in_position;
attribute vec4 in_normal;
attribute vec4 in_color;

varying mat4 pass_model;
varying vec4 pass_position;
varying vec4 pass_normal;
varying vec4 pass_color;

void main(void) {
	pass_model = u_model;
	pass_position = u_projection * u_view * u_model * in_position;
	pass_normal = in_normal;
	pass_color = in_color;
	
	gl_Position = u_projection * u_view * u_model * in_position;
}