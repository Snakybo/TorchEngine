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

package com.snakybo.sengine.mesh;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import com.snakybo.sengine.camera.Camera;
import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.object.GameObject;
import com.snakybo.sengine.shader.Shader;
import com.snakybo.sengine.texture.Texture;
import com.snakybo.sengine.util.Color;
import com.snakybo.sengine.util.Color32;
import com.snakybo.sengine.util.IDestroyable;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Material implements IDestroyable
{
	private Map<String, Object> values;
	
	private GameObject gameObject;
	private Shader shader;
	
	public Material(String shader)
	{
		this.values = new HashMap<String, Object>();
		this.shader = new Shader(shader);
	}
	
	@Override
	public final void destroy()
	{
		values.clear();
		
		shader.destroy();
	}
	
	/**
	 * Bind the material's shader
	 */
	public final void bind()
	{
		shader.bind();
	}

	/**
	 * Unbind the material's shader
	 */
	public final void unbind()
	{
		shader.unbind();
	}
	
	/**
	 * Update the material's shader
	 */
	public final void update()
	{
		if(shader.hasUniform("mvp"))
		{
			Matrix4f mvp = Camera.getCurrentCamera().getViewProjection().mul(gameObject.getTransform().getTransformation(), new Matrix4f());
			shader.setUniform4fv("mvp", mvp);
		}
		
		for(Map.Entry<String, Object> value : values.entrySet())
		{
			String type = shader.getUniformType(value.getKey());
			
			if(type == null)
			{
				continue;
			}
			
			switch(type)
			{
			case "sampler2D":
				((Texture)value.getValue()).bind();
				shader.setUniform1i(value.getKey(), 0);
				break;
			default:
				Logger.log("Invalid uniform type: " + type);
				break;
			}
		}
	}
	
	/**
	 * Set a {@link Vector2f} value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setVector2f(String name, Vector2f value)
	{
		values.put(name, value);
	}
	
	/**
	 * Set a {@link Vector3f} value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setVector3f(String name, Vector3f value)
	{
		values.put(name, value);
	}
	
	/**
	 * Set a {@link Vector4f} value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setVector4f(String name, Vector4f value)
	{
		values.put(name, value);
	}
	
	/**
	 * Set a {@link Color} value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setColor(String name, Color value)
	{
		values.put(name, value);
	}
	
	/**
	 * Set a {@link Color32} value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setColor(String name, Color32 value)
	{
		values.put(name, value.toColor());
	}
	
	/**
	 * Set a {@link Texture} value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setTexture(String name, Texture value)
	{
		values.put(name, value);
	}
	
	/**
	 * Set a float value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setFloat(String name, float value)
	{
		values.put(name, value);
	}
	
	/**
	 * Set an integer value
	 * @param name The name of the uniform in the shader
	 * @param value The value of the uniform
	 */
	public final void setInt(String name, int value)
	{
		values.put(name, value);
	}
	
	/**
	 * Set the parent {@link GameObject} of this material
	 * @param gameObject
	 */
	final void setGameObject(GameObject gameObject)
	{
		this.gameObject = gameObject;
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
	 * Convenience method to get values from the {@link #values} map
	 * @param clazz The type of the object to retrieve
	 * @param name The name of the object
	 * @return The object casted to {@code clazz}
	 */
	private final <T> T get(Class<T> clazz, String name)
	{
		if(values.containsKey(name) && values.get(name).getClass() == clazz)
		{
			return clazz.cast(values.get(name));
		}
		
		Logger.logError("Material does not contain a value for: " + name, this);
		return null;
	}
}
