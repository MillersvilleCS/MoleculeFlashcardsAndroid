uniform sampler2D texture_diffuse;

varying vec4 pass_color;
varying vec2 pass_textureCoord;

void main(void) {
	gl_FragColor = pass_color;
	// Override out_Color with our texture pixel
	//out_Color = texture(texture_diffuse, pass_TextureCoord);
}