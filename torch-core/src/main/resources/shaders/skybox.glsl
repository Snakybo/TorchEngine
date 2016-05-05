#ifdef VERTEX_PASS

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 normal;

out vec2 texCoord0;

uniform mat4 mvp;

void main()
{
    gl_Position = mvp * vec4(position, 1.0);
    
    texCoord0 = texCoord;
}

#endif

#ifdef FRAGMENT_PASS

in vec2 texCoord0;

out vec4 fragColor;

uniform sampler2D skybox;

void main()
{
	fragColor = texture(skybox, texCoord0);
}

#endif