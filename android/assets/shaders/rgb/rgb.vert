attribute vec2 a_position;
attribute vec2 a_texCoord0;

uniform mat3 u_projTrans;

varying vec2 v_texCoords;

void main() {
    gl_Position = vec4((u_projTrans * vec3(a_position, 1.0)).xy, 0.0, 1.0);
    v_texCoords = a_texCoord0;
}