#ifdef GL_ES
    precision mediump float;
#endif

varying vec2 v_texCoords;
varying vec4 v_color;
varying vec4 v_position;

uniform sampler2D u_texture;
//uniform vec4 dimensions;
//uniform vec2 red;
//uniform vec2 green;
//uniform vec2 blue;


//Works with pre-multiplied alpha, but has black background

/*
void main(void){

      //gl_FragColor = v_color * texture2D(u_texture, v_texCoords.xy);
      gl_FragColor = v_color * texture2D(u_texture, v_texCoords);

      //gl_FragColor.r = v_color.r * texture2D(u_texture, (v_texCoords.xy) + (vec2(1.0, 2.0) / vec2(720.0, 1280.0))).r;
      //gl_FragColor.g = v_color.g * texture2D(u_texture, (v_texCoords.xy) + (vec2(-2.0, 0.0) / vec2(720.0, 1280.0))).g;
      //gl_FragColor.b = v_color.b * texture2D(u_texture, (v_texCoords.xy) + (vec2(-1.0, 1.0) / vec2(720.0, 1280.0))).b;
      //gl_FragColor.a = v_color.a;
      //gl_FragColor.a = (gl_FragColor.r + gl_FragColor.g + gl_FragColor.b) / (v_color.a * 3);
      //gl_FragColor.a = v_color.a * texture2D(u_texture, v_texCoords.xy).a;
      //gl_FragColor.a = texture2D(u_texture, v_texCoords.xy).a;

      //if (gl_FragColor.rgb == vec3(0.0,0.0,0.0)){
      //  discard;
      //}

      //gl_FragColor.a = (gl_FragColor.r + gl_FragColor.g + gl_FragColor.b) / 3;
      //gl_FragColor.a = 0.8;

}
*/

void main(void){

      vec2 redXY = (vec2(2.0, -2.0) / vec2(720.0, 1280.0));
      vec2 greenXY = (vec2(1.0, 2.0) / vec2(720.0, 1280.0));
      vec2 blueXY = (vec2(-2.0, 0.0) / vec2(720.0, 1280.0));
      vec2 allXY = redXY + greenXY + blueXY;

      gl_FragColor.r = texture2D(u_texture, v_texCoords + redXY).r;
      gl_FragColor.g = texture2D(u_texture, v_texCoords + greenXY).g;
      gl_FragColor.b = texture2D(u_texture, v_texCoords + blueXY).b;
      gl_FragColor.a = 0.5;

      //gl_FragColor.a = (gl_FragColor.r + gl_FragColor.g + gl_FragColor.b) / 3;
      //gl_FragColor = v_color * texture2D(u_texture, v_texCoords);

      //gl_FragColor.a = v_color.a;


      //gl_FragColor.a = v_color.a * texture2D(u_texture, v_texCoords.xy).a;
      //gl_FragColor.a = (gl_FragColor.r + gl_FragColor.g + gl_FragColor.b) / v_color.a;
      //gl_FragColor.a = v_color.a * texture2D(u_texture, v_texCoords.xy).a;
      //gl_FragColor.a = texture2D(u_texture, v_texCoords.xy).a;

      //if (gl_FragColor.a < 0.1){
      //   discard;
      //}

      //if (gl_FragColor.rgb == vec3(0.0,0.0,0.0)){
      //  discard;
      //}

}



/*
void main(void){

    gl_FragColor.r = texture2D(u_texture, (v_texCoords.xy) + (vec2(1.0, 2.0) / vec2(720.0, 1280.0))).r;
    gl_FragColor.g = texture2D(u_texture, (v_texCoords.xy) + (vec2(-2.0, 0.0) / vec2(720.0, 1280.0))).g;
    gl_FragColor.b = texture2D(u_texture, (v_texCoords.xy) + (vec2(-1.0, 1.0) / vec2(720.0, 1280.0))).b;
    gl_FragColor.a = (gl_FragColor.r + gl_FragColor.g + gl_FragColor.b) / 3;

}
*/







