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

struct DirectionalLight
{
    vec3 direction;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;
};

struct PointLight
{
    vec3 position;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;

    float constant;
    float linear;
    float quadratic;
};

struct SpotLight
{
    vec3 position;
    vec3 direction;

    float cutOff;
    float outerCutOff;

    vec3 ambient;
    vec3 diffuse;
    vec3 specular;

    float constant;
    float linear;
    float quadratic;
};

in vec2 texCoord0;
in vec3 normal0;
in vec3 fragPos;

out vec4 fragColor;

uniform vec3 _cameraPosition;

uniform Material material;

uniform DirectionalLight directionalLight;
uniform PointLight pointLights[4];
uniform SpotLight spotLight;

vec3 CalcDirectionalLight(DirectionalLight light, vec3 normal, vec3 viewDirection)
{
    vec3 lightDir = normalize(-light.direction);

    float diff = max(dot(normal, lightDir), 0.0);

    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDirection, reflectDir), 0.0), material.shininess);

   vec3 ambient  = light.ambient  * vec3(texture(material.diffuse, texCoord0));
   vec3 diffuse  = light.diffuse  * diff * vec3(texture(material.diffuse, texCoord0));
   vec3 specular = light.specular * spec * vec3(texture(material.specular, texCoord0));

   return ambient + diffuse + specular;
}

vec3 CalcPointLight(PointLight light, vec3 normal, vec3 fragPos, vec3 viewDirection)
{
    vec3 lightDir = normalize(light.position - fragPos);

    float diff = max(dot(normal, lightDir), 0.0);

    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDirection, reflectDir), 0.0), material.shininess);

    float distance = length(light.position - fragPos);
    float attenuation = 1.0f / (light.constant + light.linear * distance + light.quadratic * (distance * distance));

    vec3 ambient = (light.ambient * vec3(texture(material.diffuse, texCoord0))) * attenuation;
    vec3 diffuse = (light.diffuse * diff * vec3(texture(material.diffuse, texCoord0))) * attenuation;
    vec3 specular = (light.specular * spec * vec3(texture(material.specular, texCoord0))) * attenuation;

    return ambient + diffuse + specular;
}

vec3 CalcSpotLight(SpotLight light, vec3 normal, vec3 fragPos, vec3 viewDirection)
{
    vec3 lightDir = normalize(light.position - fragPos);

    float diff = max(dot(normal, lightDir), 0.0);

    vec3 reflectDir = reflect(-lightDir, normal);
    float spec = pow(max(dot(viewDirection, reflectDir), 0.0), material.shininess);

    float distance = length(light.position - fragPos);
    float attenuation = 1.0f / (light.constant + light.linear * distance + light.quadratic * (distance * distance));

    float theta = dot(lightDir, normalize(-light.direction));
    float epsilon = light.cutOff - light.outerCutOff;
    float intensity = clamp((theta - light.outerCutOff) / epsilon, 0.0, 1.0);

    vec3 ambient = (light.ambient * vec3(texture(material.diffuse, texCoord0))) * attenuation * intensity;
    vec3 diffuse = (light.diffuse * diff * vec3(texture(material.diffuse, texCoord0))) * attenuation * intensity;
    vec3 specular = (light.specular * spec * vec3(texture(material.specular, texCoord0))) * attenuation * intensity;

    return ambient + diffuse + specular;
}

void main()
{
    vec3 norm = normalize(normal0);
    vec3 viewDir = normalize(_cameraPosition - fragPos);

    vec3 result = CalcDirectionalLight(directionalLight, norm, viewDir);

    for(int i = 0; i < 4; i++)
    {
        result += CalcPointLight(pointLights[i], norm, fragPos, viewDir);
    }

    result += CalcSpotLight(spotLight, norm, fragPos, viewDir);

	fragColor = vec4(result, 1.0f);
}

#endif