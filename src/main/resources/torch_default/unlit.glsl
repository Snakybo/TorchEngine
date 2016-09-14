#ifdef VERTEX_PASS

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;

out vec2 texCoord0;

uniform mat4 _model;
uniform mat4 _view;
uniform mat4 _projection;

void main()
{
    gl_Position = _projection * _view * _model * vec4(position, 1.0f);
    
    texCoord0 = texCoord;
}

#endif

#ifdef FRAGMENT_PASS

in vec2 texCoord0;

out vec4 fragColor;

uniform sampler2D diffuse;

void main()
{
	fragColor = texture2D(diffuse, texCoord0);
}

#endif