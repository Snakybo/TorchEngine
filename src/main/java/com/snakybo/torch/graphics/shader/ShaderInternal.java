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

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3fv;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniform4fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengles.GLES20.glUniform3f;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class ShaderInternal
{
	private ShaderInternal()
	{
		throw new AssertionError();
	}
	
	public static void bind(Shader shader)
	{
		glUseProgram(shader.asset.programId);
	}
	
	public static void unbind()
	{
		glUseProgram(0);
	}
	
	public static boolean hasUniform(Shader shader, String name)
	{
		return shader.asset.uniforms.containsKey(name);
	}
	
	public static void uniform1i(int location, int value)
	{
		glUniform1i(location, value);
	}
	
	public static void uniform1f(int location, float value)
	{
		glUniform1f(location, value);
	}
	
	public static void uniform2f(int location, Vector2f value)
	{
		glUniform2f(location, value.x, value.y);
	}
	
	public static void uniform3f(int location, Vector3f value)
	{
		glUniform3f(location, value.x, value.y, value.z);
	}
	
	public static void uniform4f(int location, Vector4f value)
	{
		glUniform4f(location, value.x, value.y, value.z, value.w);
	}
	
	public static void uniform3fv(int location, Matrix3f value)
	{
		glUniformMatrix3fv(location, false, value.get(BufferUtils.createFloatBuffer(9)));
	}
	
	public static void uniform4fv(int location, Matrix4f value)
	{
		glUniformMatrix4fv(location, false, value.get(BufferUtils.createFloatBuffer(16)));
	}
	
	public static String getUniformType(Shader shader, String name)
	{
		if(hasUniform(shader, name))
		{
			return shader.asset.uniformTypes.get(name);
		}
		
		return null;
	}
	
	public static int getUniformLocation(Shader shader, String name)
	{
		if(shader.asset.uniforms.containsKey(name))
		{
			return shader.asset.uniforms.get(name);
		}
		
		return -1;
	}
}
