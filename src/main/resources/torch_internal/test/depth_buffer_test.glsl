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

float near = 1.0;
float far = 100.0;

float LinearizeDepth(float depth)
{
    float z = depth * 2.0 - 1.0;
    return (2.0 * near * far) / (far + near - z * (far - near));
}

void main()
{
    float depth = LinearizeDepth(gl_FragCoord.z) / far;
	fragColor = vec4(vec3(depth), 1.0);
}

#endif