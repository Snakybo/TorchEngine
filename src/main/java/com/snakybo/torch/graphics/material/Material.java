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
import com.snakybo.torch.graphics.color.Color;
import com.snakybo.torch.graphics.mesh.Mesh;
import com.snakybo.torch.graphics.shader.Shader;
import com.snakybo.torch.graphics.shader.ShaderInternal;
import com.snakybo.torch.graphics.texture.Texture;
import com.snakybo.torch.graphics.texture.Texture2D;
import com.snakybo.torch.util.debug.Logger;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

/**
 * <p>
 * Materials contain the properties of how a {@link Mesh} should be rendered.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Material extends Asset
{
	MaterialAsset asset;
	
	Material(MaterialAsset asset)
	{
		this.asset = asset;
		this.asset.addUsage();
	}
	
	/**
	 * <p>
	 * Create a new Material with the specified {@link Shader}
	 * </p>
	 *
	 * @param shader The shader of the material.
	 */
	public Material(String shader)
	{
		asset = new MaterialAsset(shader, shader);
	}
	
	public Material(Material material)
	{
		asset = new MaterialAsset("", material.asset.shader.getName());
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
	public final int hashCode()
	{
		int hashCode = 884;
		
		hashCode += 54 * asset.shader.hashCode();
		hashCode += 54 * asset.values.hashCode();
		
		return hashCode;
	}
	
	@Override
	public final boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		
		if(o == null || getClass() != o.getClass())
		{
			return false;
		}
		
		Material m = (Material)o;
		
		if(!asset.shader.equals(m.asset.shader))
		{
			return false;
		}
		
		return asset.values.equals(m.asset.values);
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
	 * <p>
	 * Set an {@code int} value.
	 * </p>
	 * @param name The name of the uniform in the shader.
	 * @param value The value of the uniform.
	 */
	public final void setInt(String name, int value)
	{
		if(!ShaderInternal.hasUniform(asset.shader, name) || !ShaderInternal.getUniformType(asset.shader, name).equals("int"))
		{
			Logger.logWarning("Shader: " + getShader().getName() + " does not contain a int with the name: " + name);
			return;
		}
		
		asset.values.put(name, value);
	}
	
	/**
	 * <p>
	 * Set a {@code float} value.
	 * </p>
	 *
	 * @param name The name of the uniform in the shader.
	 * @param value The value of the uniform.
	 */
	public final void setFloat(String name, float value)
	{
		if(!ShaderInternal.hasUniform(asset.shader, name) || !ShaderInternal.getUniformType(asset.shader, name).equals("float"))
		{
			Logger.logWarning("Shader: " + getShader().getName() + " does not contain a float with the name: " + name);
			return;
		}
		
		asset.values.put(name, value);
	}
	
	/**
	 * <p>
	 * Set a {@code vec2} value.
	 * </p>
	 *
	 * @param name The name of the uniform in the shader.
	 * @param value The value of the uniform.
	 */
	public final void setVector2f(String name, Vector2f value)
	{
		if(!ShaderInternal.hasUniform(asset.shader, name) || !ShaderInternal.getUniformType(asset.shader, name).equals("vec2"))
		{
			Logger.logWarning("Shader: " + getShader().getName() + " does not contain a vec2 with the name: " + name);
			return;
		}
		
		asset.values.put(name, value);
	}
	
	/**
	 * <p>
	 * Set a {@code vec3} value.
	 * </p>
	 *
	 * @param name The name of the uniform in the shader.
	 * @param value The value of the uniform.
	 */
	public final void setVector3f(String name, Vector3f value)
	{
		if(!ShaderInternal.hasUniform(asset.shader, name) || !ShaderInternal.getUniformType(asset.shader, name).equals("vec3"))
		{
			Logger.logWarning("Shader: " + getShader().getName() + " does not contain a vec3 with the name: " + name);
			return;
		}
		
		asset.values.put(name, value);
	}
	
	/**
	 * <p>
	 * Set a {@code vec4} value.
	 * </p>
	 *
	 * @param name The name of the uniform in the shader.
	 * @param value The value of the uniform.
	 */
	public final void setVector4f(String name, Vector4f value)
	{
		if(!ShaderInternal.hasUniform(asset.shader, name) || !ShaderInternal.getUniformType(asset.shader, name).equals("vec4"))
		{
			Logger.logWarning("Shader: " + getShader().getName() + " does not contain a vec4 with the name: " + name);
			return;
		}
		
		asset.values.put(name, value);
	}
	
	public final void setMatrix3f(String name, Matrix3f value)
	{
		if(!ShaderInternal.hasUniform(asset.shader, name) || !ShaderInternal.getUniformType(asset.shader, name).equals("mat3"))
		{
			Logger.logWarning("Shader: " + getShader().getName() + " does not contain a mat3 with the name: " + name);
			return;
		}
		
		asset.values.put(name, value);
	}
	
	public final void setMatrix4f(String name, Matrix4f value)
	{
		if(!ShaderInternal.hasUniform(asset.shader, name) || !ShaderInternal.getUniformType(asset.shader, name).equals("mat4"))
		{
			Logger.logWarning("Shader: " + getShader().getName() + " does not contain a mat4 with the name: " + name);
			return;
		}
		
		asset.values.put(name, value);
	}
	
	/**
	 * <p>
	 * Set a {@code vec3} or {@code vec4} value.
	 * </p>
	 *
	 * @param name The name of the uniform in the shader.
	 * @param value The value of the uniform.
	 */
	public final void setColor(String name, Color value)
	{
		if(!ShaderInternal.hasUniform(asset.shader, name) || !ShaderInternal.getUniformType(asset.shader, name).equals("vec3") && !ShaderInternal.getUniformType(asset.shader, name).equals("vec4"))
		{
			Logger.logWarning("Shader: " + getShader().getName() + " does not contain a vec3 or vec4 with the name: " + name);
			return;
		}
		
		String type = ShaderInternal.getUniformType(asset.shader, name);
		
		if(type != null)
		{
			switch(type)
			{
			case "vec3":
				setVector3f(name, new Vector3f(value.getRed(), value.getGreen(), value.getBlue()));
				break;
			case "vec4":
				setVector4f(name, new Vector4f(value.getRed(), value.getGreen(), value.getBlue(), value.getAlpha()));
				break;
			}
		}
	}
	
	/**
	 * <p>
	 * Set a {@code sampler2D} value.
	 * </p>
	 *
	 * @param name The name of the uniform in the shader.
	 * @param value The value of the uniform.
	 */
	public final void setTexture(String name, Texture value)
	{
		if(!ShaderInternal.hasUniform(asset.shader, name) || !ShaderInternal.getUniformType(asset.shader, name).equals("sampler2D"))
		{
			Logger.logWarning("Shader: " + getShader().getName() + " does not contain a sampler2D with the name: " + name);
			return;
		}
		
		asset.values.put(name, value);
		asset.textureSamplerSlotIds.add(value);
	}
	
	/**
	 * <p>
	 * Get an {@code int} value.
	 * </p>
	 *
	 * @param name The name of the uniform in the shader.
	 * @return The value of the uniform.
	 */
	public final int getInt(String name)
	{
		return get(int.class, name);
	}
	
	/**
	 * <p>
	 * Get a {@code float} value.
	 * </p>
	 *
	 * @param name The name of the uniform in the shader.
	 * @return The value of the uniform.
	 */
	public final float getFloat(String name)
	{
		return get(float.class, name);
	}
	
	/**
	 * <p>
	 * Get a {@code vec2} value.
	 * </p>
	 *
	 * @param name The name of the uniform in the shader.
	 * @return The value of the uniform.
	 */
	public final Vector2f getVector2f(String name)
	{
		return get(Vector2f.class, name);
	}
	
	/**
	 * <p>
	 * Get a {@code vec3} value.
	 * </p>
	 *
	 * @param name The name of the uniform in the shader.
	 * @return The value of the uniform.
	 */
	public final Vector3f getVector3f(String name)
	{
		return get(Vector3f.class, name);
	}
	
	/**
	 * <p>
	 * Get a {@code vec4} value.
	 * </p>
	 *
	 * @param name The name of the uniform in the shader.
	 * @return The value of the uniform.
	 */
	public final Vector4f getVector4f(String name)
	{
		return get(Vector4f.class, name);
	}
	
	public final Matrix3f getMatrix3f(String name)
	{
		return get(Matrix3f.class, name);
	}
	
	public final Matrix4f getMatrix4f(String name)
	{
		return get(Matrix4f.class, name);
	}
	
	/**
	 * <p>
	 * Get a {@code vec3}  or {@code vec4} value.
	 * </p>
	 *
	 * @param name The name of the uniform in the shader.
	 * @return The value of the uniform.
	 */
	public final Color getColor(String name)
	{
		String type = ShaderInternal.getUniformType(asset.shader, name);
		
		if(type != null)
		{
			switch(type)
			{
			case "vec3":
				Vector3f vec3 = get(Vector3f.class, name);
				return new Color(vec3.x, vec3.y, vec3.z);
			case "vec4":
				Vector4f vec4 = get(Vector4f.class, name);
				return new Color(vec4.x, vec4.y, vec4.z, vec4.w);
			}
		}
		
		return null;
	}
	
	/**
	 * <p>
	 * Get a {@code sampler2D} value.
	 * </p>
	 *
	 * @param name The name of the uniform in the shader.
	 * @return The value of the uniform.
	 */
	public final Texture getTexture(String name)
	{
		Object value = asset.values.get(name);
		
		if(value.getClass().equals(Texture2D.class))
		{
			return (Texture2D)value;
		}
		
		Logger.logError("Unknown texture type for name: " + name + " (is of type: " + value.getClass() + ")");
		return null;
	}
	
	/**
	 * <p>
	 * Get the shader used by the material.
	 * </p>
	 *
	 * @return The shader used by the material.
	 */
	public final Shader getShader()
	{
		return asset.shader;
	}
	
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
