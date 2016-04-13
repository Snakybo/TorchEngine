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

package com.snakybo.sengine.shader;

import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.util.Set;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.resource.Resource;

/**
 * @author Snakybo
 * @since 1.0
 */
class ShaderProgramResource extends Resource
{
	private Set<Shader> attached;
	
	private int id;
	
	public ShaderProgramResource(String fileName, Set<Shader> attached)
	{
		super(fileName);
		
		id = glCreateProgram();
		
		if(id == 0)
		{
			Logger.logError("Unable to create shader program", this);
			return;
		}
		
		for(Shader shader : attached)
		{
			glAttachShader(id, shader.id);
		}
		
		glLinkProgram(id);
		if(glGetProgrami(id, GL_LINK_STATUS) == 0)
		{
			Logger.logError("Unable to link shader program: " + glGetProgramInfoLog(id, 1024), this);
			return;
		}
		
		glValidateProgram(id);
		if(glGetProgrami(id, GL_VALIDATE_STATUS) == 0)
		{
			Logger.logError("Unable to validate shader program: " + glGetProgramInfoLog(id, 1024), this);
			return;
		}
	}
	
	@Override
	public void destroy()
	{
		if(id != 0)
		{
			for(Shader shader : attached)
			{
				glDetachShader(id, shader.id);
				shader.destroy();
			}			
			
			glDeleteProgram(id);
			
			id = 0;
			attached.clear();
		}
	}
	
	/**
	 * Tell OpenGL to use this program
	 */
	public void use()
	{
		glUseProgram(id);
	}
}
