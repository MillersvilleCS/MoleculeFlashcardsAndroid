precision highp float;
uniform mat4 u_projection;
uniform mat4 u_view;
uniform mat4 u_model;

attribute vec4 in_position;
attribute vec4 in_color;
attribute vec3 in_normal;

varying vec4 pass_color;
varying vec3 pass_normal;
varying vec3 pass_lightDirection;

void main(void) {
	pass_lightDirection = vec3(1, 0, 0);
	gl_Position = u_projection * u_view * u_model * in_position;
	
	pass_color = in_color;
	
	mat3 normalMatrix = mat3(inverse(u_model));
	normalMatrix = transpose(normalMatrix);
	
	pass_normal = normalMatrix * in_normal;
}