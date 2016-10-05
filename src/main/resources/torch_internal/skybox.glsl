#ifdef VERTEX_PASS

layout (location=0) in vec3 position;

out vec3 texCoord0;

uniform mat4 _view;
uniform mat4 _projection;

void main()
{
    vec4 pos = _projection * _view * vec4(position, 1.0f);

    gl_Position = pos.xyww;
    texCoord0 = position;
}

#endif

#ifdef FRAGMENT_PASS

in vec3 texCoord0;

out vec4 fragColor;

uniform samplerCube skybox;

void main()
{
	fragColor = texture(skybox, texCoord0);
}

#endif