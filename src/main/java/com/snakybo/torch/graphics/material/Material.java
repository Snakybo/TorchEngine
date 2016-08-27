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

import com.snakybo.torch.asset.Asset;
import com.snakybo.torch.graphics.camera.CameraInternal;
import com.snakybo.torch.graphics.color.Color;
import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.object.Transform;
import com.snakybo.torch.graphics.texture.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.Map;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Material extends Asset
{
	private MaterialAsset asset;
	private Transform transform;
	
	Material(MaterialAsset asset)
	{
		this.asset = asset;
		this.asset.addUsage();
	}
	
	Material(String path, String shader)
	{
		asset = new MaterialAsset(path, shader);
	}
	
	public Material(String shader)
	{
		asset = new MaterialAsset("", shader);
	}
	
	@Override
	public final void finalize() throws Throwable
	{
		try
		{
			destroy();
		}
		finally
		{
			super.finalize();
		}
	}
	
	@Override
	public final void destroy()
	{
		if(asset != null)
		{
			asset.removeUsage();
			asset = null;
		}
	}
	
	/**
	 * Bind the material's shader
	 */
	public final void bind()
	{
		asset.shader.bind();
	}

	/**
	 * Unbind the material's shader
	 */
	public final void unbind()
	{
		asset.shader.unbind();
	}
	
	/**
	 * Update the material's shader
	 */
	public final void update()
	{
		if(asset.shader.hasUniform("_model"))
		{
			asset.shader.setUniform4fv("_model", transform.getTransformation());
		}
		
		if(asset.shader.hasUniform("_view"))
		{
			asset.shader.setUniform4fv("_view", CameraInternal.getCurrentCamera().getView());
		}
		
		if(asset.shader.hasUniform("_projection"))
		{
			asset.shader.setUniform4fv("_projection", CameraInternal.getCurrentCamera().getProjection());
		}
		
		if(asset.shader.hasUniform("_cameraPosition"))
		{
			asset.shader.setUniform3f("_cameraPosition", CameraInternal.getCurrentCamera().getTransform().getPosition());
		}
		
		for(Map.Entry<String, Object> value : asset.values.entrySet())
		{
			String type = asset.shader.getUniformType(value.getKey());
			
			if(type == null)
			{
				Logger.logWarning("No type found for: " + value.getKey());
				continue;
			}
			
			switch(type)
			{
			case "sampler2D":
				if(value.getKey().contains("diffuse"))
				{
					((Texture)value.getValue()).bind();
					asset.shader.setUniform1i(value.getKey(), 0);
				}
				else if(value.getKey().contains("specular"))
				{
					((Texture)value.getValue()).bind(1);
					asset.shader.setUniform1i(value.getKey(), 1);
				}
				
				break;
			case "float":
				asset.shader.setUniform1f(value.getKey(), (float)value.getValue());
				break;
			case "vec2":
				asset.shader.setUniform2f(value.getKey(), (Vector2f)value.getValue());
				break;
			case "vec3":
				asset.shader.setUniform3f(value.getKey(), (Vector3f)value.getValue());
				break;
			default:
				Logger.logWarning("Invalid uniform type: " + type);
				break;
			}
		}
	}
	
	public final void set(String name, Object value)
	{
		asset.values.put(name, value);
	}
	
	/**
	 * Set a {@link Vector2f} value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setVector2f(String name, Vector2f value)
	{
		set(name, value);
	}
	
	/**
	 * Set a {@link Vector3f} value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setVector3f(String name, Vector3f value)
	{
		set(name, value);
	}
	
	/**
	 * Set a {@link Vector4f} value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setVector4f(String name, Vector4f value)
	{
		set(name, value);
	}
	
	/**
	 * Set a {@link Color} value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setColor(String name, Color value)
	{
		set(name, value);
	}
	
	/**
	 * Set a {@link Texture} value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setTexture(String name, Texture value)
	{
		set(name, value);
	}
	
	/**
	 * Set a float value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setFloat(String name, float value)
	{
		set(name, value);
	}
	
	/**
	 * Set an integer value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setInt(String name, int value)
	{
		set(name, value);
	}
	
	/**
	 * Set the parent {@link Transform} of this material.
	 * @param transform The new transform of the material.
	 */
	public final void setTransform(Transform transform)
	{
		this.transform = transform;
	}
	
	/**
	 * Get a {@link Vector2f} value
	 * @param name The name of the uniform in the shader
	 * @return The value of the uniform
	 */
	public final Vector2f getVector2f(String name)
	{
		return get(Vector2f.class, name);
	}
	
	/**
	 * Get a {@link Vector3f} value
	 * @param name The name of the uniform in the shader
	 * @return The value of the uniform
	 */
	public final Vector3f getVector3f(String name)
	{
		return get(Vector3f.class, name);
	}
	
	/**
	 * Get a {@link Vector4f} value
	 * @param name The name of the uniform in the shader
	 * @return The value of the uniform
	 */
	public final Vector4f getVector4f(String name)
	{
		return get(Vector4f.class, name);
	}
	
	/**
	 * Get a {@link Color} value
	 * @param name The name of the uniform in the shader
	 * @return The value of the uniform
	 */
	public final Color getColor(String name)
	{
		return get(Color.class, name);
	}
	
	/**
	 * Get a {@link Texture} value
	 * @param name The name of the uniform in the shader
	 * @return The value of the uniform
	 */
	public final Texture getTexture(String name)
	{
		return get(Texture.class, name);
	}
	
	/**
	 * Get a float value
	 * @param name The name of the uniform in the shader
	 * @return The value of the uniform
	 */
	public final float getFloat(String name)
	{
		return get(float.class, name);
	}
	
	/**
	 * Get an integer value
	 * @param name The name of the uniform in the shader
	 * @return The value of the uniform
	 */
	public final int getInt(String name)
	{
		return get(int.class, name);
	}
	
	/**
	 * Convenience method to get values from the material
	 * @param clazz The type of the object to retrieve
	 * @param name The name of the object
	 * @return The object casted to {@code clazz}
	 */
	private <T> T get(Class<T> clazz, String name)
	{
		if(asset.values.containsKey(name) && asset.values.get(name).getClass() == clazz)
		{
			return clazz.cast(asset.values.get(name));
		}
		
		Logger.logError("Material does not contain a value for: " + name);
		return null;
	}
}
