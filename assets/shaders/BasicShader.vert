precision highp float;
uniform mat4 u_projection;
uniform mat4 u_view;
uniform mat4 u_model;

attribute vec4 in_position;
attribute vec4 in_normal;
attribute vec4 in_color;

varying vec4 pass_color;
varying vec3 pass_normal;
varying vec3 pass_lightDirection;

void main(void) {
	pass_lightDirection = vec3(0, 1, 0);
	gl_Position = u_projection * u_view * u_model * in_position;
	
	pass_color = in_color;
	
	mat3 normalMatrix = mat3(u_model);
	normalMatrix = inverse(normalMatrix);
	normalMatrix = transpose(normalMatrix);
	
	pass_normal = normalMatrix * in_normal.xyz;
	pass_normal = normalize(pass_normal);
	
	//pass_normal = in_normal.xyz;
}