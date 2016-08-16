#ifdef VERTEX_PASS

layout (location=0) in vec3 position;
layout (location=1) in vec2 texCoord;
layout (location=2) in vec3 normal;

out vec2 texCoord0;
out vec3 normal0;
out vec3 fragPos;

uniform mat4 _model;
uniform mat4 _view;
uniform mat4 _projection;

void main()
{
    gl_Position = _projection * _view * _model * vec4(position, 1.0f);

    texCoord0 = texCoord;
    normal0 = mat3(transpose(inverse(_model))) * normal;

    fragPos = vec3(_model * vec4(position, 1.0f));
}

#endif

#ifdef FRAGMENT_PASS

struct Material
{
    sampler2D diffuse;
    sampler2D specular;
    float shininess;
};

struct Light
{
    vec3 position;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

in vec2 texCoord0;
in vec3 normal0;
in vec3 fragPos;

out vec4 fragColor;

uniform vec3 _cameraPosition;

uniform Material material;
uniform Light light;

void main()
{
    // Ambient
    vec3 ambient = light.ambient * vec3(texture(material.diffuse, texCoord0));

    // Diffuse
    vec3 norm = normalize(normal0);
    vec3 lightDir = normalize(light.position - fragPos);

    float diff = max(dot(norm, lightDir), 0.0f);
    vec3 diffuse = light.diffuse * diff * vec3(texture(material.diffuse, texCoord0));

    // Specular
    vec3 viewDir = normalize(_cameraPosition - fragPos);
    vec3 reflectDir = reflect(-lightDir, norm);

    float spec = pow(max(dot(viewDir, reflectDir), 0.0f), material.shininess);
    vec3 specular = light.specular * spec * vec3(texture(material.specular, texCoord0));

    // Result
	fragColor = vec4(ambient + diffuse + specular, 1.0f);
}

#endif