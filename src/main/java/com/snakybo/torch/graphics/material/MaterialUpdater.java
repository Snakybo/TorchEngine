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
import com.snakybo.torch.graphics.shader.Shader;
import com.snakybo.torch.graphics.texture.Texture;
import com.snakybo.torch.graphics.texture.TextureInternal;
import com.snakybo.torch.util.debug.Logger;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Map;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class MaterialUpdater
{
	private MaterialUpdater()
	{
		throw new AssertionError();
	}
	
	public static void update(Material material, Matrix4f transformation)
	{
		Shader s = material.getShader();
		
		if(s.hasUniform("_model"))
		{
			s.setUniform4fv("_model", transformation);
		}
		
		if(s.hasUniform("_view"))
		{
			s.setUniform4fv("_view", CameraInternal.getInstance().getViewMatrix());
		}
		
		if(s.hasUniform("_projection"))
		{
			s.setUniform4fv("_projection", CameraInternal.getInstance().getProjectionMatrix());
		}
		
		if(s.hasUniform("_cameraPosition"))
		{
			s.setUniform3f("_cameraPosition", CameraInternal.getInstance().getTransform().getPosition());
		}
		
		for(Map.Entry<String, Object> value : material.asset.values.entrySet())
		{
			String type = s.getUniformType(value.getKey());
			
			if(type == null)
			{
				Logger.logWarning("No type found for: " + value.getKey());
				continue;
			}
			
			switch(type)
			{
			case "sampler2D":
				if(value.getKey().contains("mainTexture"))
				{
					TextureInternal.bind((Texture)value.getValue(), 0);
					s.setUniform1i(value.getKey(), 0);
				}
				else if(value.getKey().contains("specular"))
				{
					TextureInternal.bind((Texture)value.getValue(), 1);
					s.setUniform1i(value.getKey(), 1);
				}
				
				break;
			case "float":
				s.setUniform1f(value.getKey(), (float)value.getValue());
				break;
			case "vec2":
				s.setUniform2f(value.getKey(), (Vector2f)value.getValue());
				break;
			case "vec3":
				s.setUniform3f(value.getKey(), (Vector3f)value.getValue());
				break;
			case "vec4":
				s.setUniform4f(value.getKey(), (Vector4f)value.getValue());
				break;
			default:
				Logger.logWarning("Invalid uniform type: " + type);
				break;
			}
		}
	}
}
