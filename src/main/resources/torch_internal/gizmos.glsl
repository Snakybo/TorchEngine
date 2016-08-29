#ifdef VERTEX_PASS

layout (location=0) in vec3 position;

uniform mat4 _model;
uniform mat4 _view;
uniform mat4 _projection;

void main()
{
    gl_Position = _projection * _view * _model * vec4(position, 1.0f);
}

#endif

#ifdef FRAGMENT_PASS

out vec4 fragColor;

uniform vec3 color;

void main()
{
	fragColor = vec4(color, 1);
}

#endif