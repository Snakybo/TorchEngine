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

package com.snakybo.torch.shader;

import com.snakybo.torch.asset.Asset;
import com.snakybo.torch.interfaces.IDestroyable;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
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
	 * Tell OpenGL to use this program
	 */
	public final void bind()
	{
		glUseProgram(asset.programId);
	}
	
	/**
	 * Tell OpenGL to stop using this program
	 */
	public final void unbind()
	{
		glUseProgram(0);
	}
	
	/**
	 * @param name The name of the uniform
	 * @return Whether or not the shader has a uniform with the specified name
	 */
	public final boolean hasUniform(String name)
	{
		return asset.uniforms.containsKey(name);
	}
	
	/**
	 * Set an integer value
	 * @param name The name of the uniform
	 * @param value The value of the uniform
	 */
	public final void setUniform1i(String name, int value)
	{
		glUniform1i(asset.uniforms.get(name), value);
	}
	
	/**
	 * Set a float value
	 * @param name The name of the uniform
	 * @param value The value of the uniform
	 */
	public final void setUniform1f(String name, float value)
	{
		glUniform1f(asset.uniforms.get(name), value);
	}
	
	/**
	 * Set a {@link Vector2f} value
	 * @param name The name of the uniform
	 * @param value The value of the uniform
	 */
	public final void setUniform2f(String name, Vector2f value)
	{
		glUniform2f(asset.uniforms.get(name), value.x, value.y);
	}
	
	/**
	 * Set a {@link Vector3f} value
	 * @param name The name of the uniform
	 * @param value The value of the uniform
	 */
	public final void setUniform3f(String name, Vector3f value)
	{
		glUniform3f(asset.uniforms.get(name), value.x, value.y, value.z);
	}
	
	/**
	 * Set a {@link Vector4f} value
	 * @param name The name of the uniform
	 * @param value The value of the uniform
	 */
	public final void setUniform2f(String name, Vector4f value)
	{
		glUniform4f(asset.uniforms.get(name), value.x, value.y, value.z, value.w);
	}
	
	/**
	 * Set a {@link Matrix3f} value
	 * @param name The name of the uniform
	 * @param value The value of the uniform
	 */
	public final void setUniform4fv(String name, Matrix3f value)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(9);
		value.get(buffer);
		
		glUniformMatrix3fv(asset.uniforms.get(name), false, buffer);
	}
	
	/**
	 * Set a {@link Matrix4f} value
	 * @param name The name of the uniform
	 * @param value The value of the uniform
	 */
	public final void setUniform4fv(String name, Matrix4f value)
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);
		
		glUniformMatrix4fv(asset.uniforms.get(name), false, buffer);
	}
	
	/**
	 * Get the type of a uniform, returns {@code null} if the shader has no uniform with the specified name
	 * @param name The name of the uniform
	 * @return The type of the uniform, or {@code null}
	 */
	public final String getUniformType(String name)
	{
		if(hasUniform(name))
		{
			return asset.uniformTypes.get(name);
		}
		
		return null;
	}
}
