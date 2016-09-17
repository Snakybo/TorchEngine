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

package com.snakybo.torch.graphics.shader;

import com.snakybo.torch.asset.Asset;
import com.snakybo.torch.graphics.material.Material;
import com.snakybo.torch.object.Component;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;

/**
 * <p>
 * The shader class is a direct link to the low-level GLSL shaders.
 * </p>
 *
 * <p>
 * A shader is usually attached to a {@link Material}, however it is possible to manually
 * use a shader in the {@link Component#onRenderObject()} method.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Shader extends Asset
{
	private ShaderAsset asset;
	
	Shader(ShaderAsset asset)
	{
		this.asset = asset;
		this.asset.addUsage();
	}
	
	Shader(String path)
	{
		asset = new ShaderAsset(path);
		asset.init();
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
	 * <p>
	 * Bind the shader for use in OpenGL.
	 * </p>
	 */
	public final void bind()
	{
		glUseProgram(asset.programId);
	}
	
	/**
	 * <p>
	 * Unbind the shader.
	 * </p>
	 */
	public final void unbind()
	{
		glUseProgram(0);
	}
	
	/**
	 * <p>
	 * Check whether or not the shader has an uniform with the given {@code name}.
	 * </p>
	 *
	 * @param name The name of the uniform.
	 * @return Whether or not the shader has a uniform with the specified .
	 */
	public final boolean hasUniform(String name)
	{
		return asset.uniforms.containsKey(name);
	}
	
	/**
	 * <p>
	 * Set the value of an {@code int} uniform.
	 * </p>
	 *
	 * @param name The name of the uniform.
	 * @param value The value of the uniform.
	 */
	public final void setUniform1i(String name, int value)
	{
		glUniform1i(asset.uniforms.get(name), value);
	}
	
	/**
	 * <p>
	 * Set the value of a {@code float} uniform.
	 * </p>
	 *
	 * @param name The name of the uniform.
	 * @param value The value of the uniform.
	 */
	public final void setUniform1f(String name, float value)
	{
		glUniform1f(asset.uniforms.get(name), value);
	}
	
	/**
	 * <p>
	 * Set the value of a {@link Vector2f} uniform.
	 * </p>
	 *
	 * @param name The name of the uniform.
	 * @param value The value of the uniform.
	 */
	public final void setUniform2f(String name, Vector2f value)
	{
		glUniform2f(asset.uniforms.get(name), value.x, value.y);
	}
	
	/**
	 * <p>
	 * Set the value of a {@link Vector3f} uniform.
	 * </p>
	 *
	 * @param name The name of the uniform.
	 * @param value The value of the uniform.
	 */
	public final void setUniform3f(String name, Vector3f value)
	{
		glUniform3f(asset.uniforms.get(name), value.x, value.y, value.z);
	}
	
	/**
	 * <p>
	 * Set the value of a {@link Vector4f} uniform.
	 * </p>
	 *
	 * @param name The name of the uniform.
	 * @param value The value of the uniform.
	 */
	public final void setUniform4f(String name, Vector4f value)
	{
		glUniform4f(asset.uniforms.get(name), value.x, value.y, value.z, value.w);
	}
	
	/**
	 * <p>
	 * Set the value of a {@link Matrix3f} uniform.
	 * </p>
	 *
	 * @param name The name of the uniform.
	 * @param value The value of the uniform.
	 */
	public final void setUniform3fv(String name, Matrix3f value)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(9);
		value.get(buffer);
		
		glUniformMatrix3fv(asset.uniforms.get(name), false, buffer);
	}
	
	/**
	 * <p>
	 * Set the value of a {@link Matrix4f} uniform.
	 * </p>
	 *
	 * @param name The name of the uniform.
	 * @param value The value of the uniform.
	 */
	public final void setUniform4fv(String name, Matrix4f value)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		
		glUniformMatrix4fv(asset.uniforms.get(name), false, buffer);
	}
	
	/**
	 * <p>
	 * Get the type of a uniform, returns {@code null} if the shader has no uniform with the given {@code name}.
	 * </p>
	 *
	 * @param name The name of the uniform.
	 * @return The type of the uniform as a {@code String}, or {@code null}.
	 */
	public final String getUniformType(String name)
	{
		if(hasUniform(name))
		{
			return asset.uniformTypes.get(name);
		}
		
		return null;
	}
	
	/**
	 * <p>
	 * Get the name of the shader.
	 * </p>
	 *
	 * @return The name of the shader.
	 */
	public final String getName()
	{
		return asset.getName();
	}
	
	/**
	 * <p>
	 * Load a shader.
	 * </p>
	 *
	 * @param path The path/name of the shader.
	 * @return The loaded shader.
	 */
	public static Shader load(String path)
	{
		if(ShaderAsset.all.containsKey(path))
		{
			return new Shader(ShaderAsset.all.get(path));
		}
		
		return new Shader(path);
	}
}
