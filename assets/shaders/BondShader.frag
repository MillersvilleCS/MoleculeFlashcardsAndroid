precision mediump float;
uniform vec4 u_color1;
uniform vec4 u_color2;

varying vec4 pass_color;
varying vec4 pass_normal;

void main(void) {
	
	vec4 useColor;
	float color1Distance = abs(length(u_color1.xyz - pass_color.xyz));
	float color2Distance = abs(length(u_color2.xyz - pass_color.xyz));
	float proportion = color2Distance / color1Distance;
	if( color1Distance < color2Distance * proportion) {
		useColor = u_color1;
	} else {
		useColor = u_color2;
	}
	useColor = pass_color;
	float intensity = dot(vec3(1, 0, 0), pass_normal.xyz);
	vec4 color;
	if (intensity > 0.95)
		color = vec4(useColor.x, useColor.y, useColor.z, 1.0);
	else if (intensity > 0.5)
		color = vec4(useColor.x * 0.9, useColor.y * 0.9, useColor.z * 0.9, 1.0);
	else if (intensity > 0.25)
		color = vec4(useColor.x * 0.8,useColor.y* 0.8,useColor.z* 0.8,1.0);
	else
		color = vec4(useColor.x* 0.7,useColor.y* 0.7,useColor.z* 0.7,1.0);
	gl_FragColor = color;
}