// The MIT License(MIT)
//
// Copyright(c) 2016 Kevin Krol
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.snakybo.torch.graphics.material;

import com.snakybo.torch.graphics.camera.CameraInternal;
import com.snakybo.torch.graphics.shader.ShaderInternal;
import com.snakybo.torch.graphics.texture.Texture;
import com.snakybo.torch.graphics.texture.TextureInternal;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.util.Map;

import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class MaterialInternal
{
	private MaterialInternal()
	{
		throw new AssertionError();
	}
	
	public static void update(Material material)
	{
		for(Map.Entry<String, Object> property : material.asset.values.entrySet())
		{
			String type = ShaderInternal.getUniformType(material.asset.shader, property.getKey());
			
			if(type != null)
			{
				int loc = ShaderInternal.getUniformLocation(material.getShader(), property.getKey());
				Object value = property.getValue();
				
				switch(type)
				{
				case "int":
					ShaderInternal.uniform1i(loc, (int)value);
					break;
				case "float":
					ShaderInternal.uniform1f(loc, (float)value);
					break;
				case "vec2":
					ShaderInternal.uniform2f(loc, (Vector2f)value);
					break;
				case "vec3":
					ShaderInternal.uniform3f(loc, (Vector3f)value);
					break;
				case "vec4":
					ShaderInternal.uniform4f(loc, (Vector4f)value);
					break;
				case "mat3":
					ShaderInternal.uniform3fv(loc, (Matrix3f)value);
					break;
				case "mat4":
					ShaderInternal.uniform4fv(loc, (Matrix4f)value);
					break;
				case "sampler2D":
					int samplerSlotId = material.asset.textureSamplerSlotIds.indexOf(value);
					TextureInternal.bind((Texture)value, samplerSlotId);
					ShaderInternal.uniform1i(loc, samplerSlotId);
					break;
				}
			}
		}
	}
	
	public static void updateBuiltInUniforms(Material material, Matrix4f model)
	{
		if(ShaderInternal.hasUniform(material.asset.shader, "_model"))
		{
			material.setMatrix4f("_model", model);
		}
		
		if(ShaderInternal.hasUniform(material.asset.shader, "_view"))
		{
			material.setMatrix4f("_view", CameraInternal.getInstance().getViewMatrix());
		}
		
		if(ShaderInternal.hasUniform(material.asset.shader, "_projection"))
		{
			material.setMatrix4f("_projection", CameraInternal.getInstance().getProjectionMatrix());
		}
		
		if(ShaderInternal.hasUniform(material.asset.shader, "_cameraPosition"))
		{
			material.setVector3f("_cameraPosition", CameraInternal.getInstance().getTransform().getPosition());
		}
	}
}
