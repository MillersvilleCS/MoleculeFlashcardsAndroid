
varying mat4 pass_model;
varying vec4 pass_position;
varying vec4 pass_normal;
varying vec4 pass_color;

void main(void) {
	float intensity;
	vec4 color;
	intensity = dot(vec3(1,0,0),pass_normal.xyz);
	if (intensity > 0.95)
		color = vec4(pass_color.x, pass_color.y, pass_color.z, 1.0);
	else if (intensity > 0.5)
		color = vec4(pass_color.x * 0.9, pass_color.y * 0.9, pass_color.z * 0.9, 1.0);
	else if (intensity > 0.25)
		color = vec4(pass_color.x * 0.8, pass_color.y * 0.8, pass_color.z * 0.8, 1.0);
	else
		color = vec4(pass_color.x * 0.7, pass_color.y * 0.7, pass_color.z * 0.7, 1.0);
	gl_FragColor = color;
}