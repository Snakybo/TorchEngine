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

import com.snakybo.torch.debug.Logger;

import static org.lwjgl.opengl.GL11.glGetInteger;
import static org.lwjgl.opengl.GL30.GL_MAJOR_VERSION;
import static org.lwjgl.opengl.GL30.GL_MINOR_VERSION;

/**
 * @author Snakybo
 * @since 1.0
 */
final class GLSLVersion
{
	/**
	 * The GLSL version to append to shaders
	 */
	static final int GLSL_VERSION;
	
	static
	{
		int majorVersion = glGetInteger(GL_MAJOR_VERSION);
		int minorVersion = glGetInteger(GL_MINOR_VERSION);
		int glVersion = majorVersion * 100 + minorVersion * 10;

		if(glVersion >= 330)
		{
			GLSL_VERSION = glVersion;
		}
		else if(glVersion >= 320)
		{
			GLSL_VERSION = 150;
		}
		else if(glVersion >= 310)
		{
			GLSL_VERSION = 140;
		}
		else if(glVersion >= 300)
		{
			GLSL_VERSION = 130;
		}
		else if(glVersion >= 210)
		{
			GLSL_VERSION = 120;
		}
		else if(glVersion >= 200)
		{
			GLSL_VERSION = 110;
		}
		else
		{
			Logger.log("OpenGL " + majorVersion + "." + minorVersion + " does not support shaders", "GLSLVersion");
			GLSL_VERSION = 0;			
		}
	}
	
	private GLSLVersion()
	{
		throw new AssertionError();
	}
}
