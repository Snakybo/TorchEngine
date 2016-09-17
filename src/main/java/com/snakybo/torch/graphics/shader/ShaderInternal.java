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

import static org.lwjgl.opengl.GL20.glUseProgram;

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
