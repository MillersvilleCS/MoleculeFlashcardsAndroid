precision mediump float;
varying vec4 pass_color;
varying vec3 pass_normal;
varying vec3 pass_lightDirection;

void main(void) {
	
	float lambertCoef = max(dot(pass_normal, pass_lightDirection), 0.0);
        
    vec3 diffuse      = vec3(0.7, 0.7, 0.7);
    vec3 ambientColor = vec3(0.6, 0.6, 0.6);

    vec3 lightWeighting = ambientColor + diffuse * lambertCoef;

    vec3 color = pass_color.xyz * lightWeighting;

    gl_FragColor = vec4(color, 1.0);
}