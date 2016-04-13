#ifdef VERTEX_PASS

layout (location=0) in vec3 position;

void main()
{
    gl_Position = vec4(position, 1.0);
}

#endif

#ifdef FRAGMENT_PASS

out vec4 fragColor;

void main()
{
    fragColor = vec4(0.0, 0.5, 0.5, 1.0);
}

#endif