precision mediump float;
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
	//pass_lightDirection = mat3(transpose(inverse(u_model))) * vec3(1,0,0);
	pass_lightDirection = vec3(1,0,0);
	gl_Position = u_projection * u_view * u_model * in_position;
	
	//weird - taking normals and using them like points for position
	//pass_normal = mat3(transpose(inverse(u_model))) * in_normal.xyz;
	//gl_Position = u_projection * u_view * u_model * vec4(pass_normal.x, pass_normal.y, pass_normal.z, 1);
	
	pass_color = in_color;
	//pass_normal = normalize(mat3(transpose(inverse(u_model))) * in_normal.xyz);
	//pass_normal = mat3(u_model) * in_normal.xyz;
	pass_normal = in_normal.xyz;
}