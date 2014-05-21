
varying vec4 pass_color;
varying vec4 pass_normal;

void main(void) {
	
	float intensity;
	
	intensity = dot(vec3(1, 0, 0), pass_normal.xyz);
	
	vec4 magnitudeColor = vec4(pass_color);
	vec3 normalized = normalize(vec3(pass_color.xyz));
	if( (magnitudeColor.x + magnitudeColor.y + magnitudeColor.z) * 0.333 > 0.73) {
		magnitudeColor = vec4(1,1,1,1);
	} else {
		magnitudeColor = vec4(normalized, 1);
	}
	
	vec4 color;
	if (intensity > 0.95)
		color = vec4(magnitudeColor.x, magnitudeColor.y, magnitudeColor.z, 1.0);
	else if (intensity > 0.5)
		color = vec4(magnitudeColor.x * 0.9, magnitudeColor.y * 0.9, magnitudeColor.z * 0.9, 1.0);
	else if (intensity > 0.25)
		color = vec4(magnitudeColor.x * 0.8,magnitudeColor.y* 0.8,magnitudeColor.z* 0.8,1.0);
	else
		color = vec4(magnitudeColor.x* 0.7,magnitudeColor.y* 0.7,magnitudeColor.z* 0.7,1.0);
	gl_FragColor = color;
}