#ifdef GL_ES
    precision mediump float;
#endif

varying vec2 v_texCoords;
varying vec4 v_color;

uniform sampler2D u_texture;

void main(void){
    gl_FragColor.b = (v_color * texture2D(u_texture, v_texCoords)).b;
    gl_FragColor.a = (v_color * texture2D(u_texture, v_texCoords)).a;
}
