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

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDeleteShader;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform2f;
import static org.lwjgl.opengl.GL20.glUniform3f;
import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix3fv;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.debug.LoggerInternal;
import com.snakybo.sengine.resource.Resource;
import com.snakybo.sengine.resource.ResourceDatabase;
import com.snakybo.sengine.util.IDestroyable;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Shader implements IDestroyable
{
	static class ShaderResource extends Resource
	{
		Map<String, Integer> uniforms;
		Map<String, String> uniformTypes;
		
		List<Integer> attachedShaders;
		
		int programId;
		
		public ShaderResource(String fileName, String source, Map<Integer, String> shaders)
		{
			super(fileName);
			
			programId = glCreateProgram();
			
			uniforms = new HashMap<String, Integer>();
			uniformTypes = new HashMap<String, String>();
			
			if(programId == 0)
			{
				Logger.logError("Unable to create shader program", this);
				return;
			}
			
			createShaders(shaders);
			link();
			addUniforms(fileName, source);
		}
		
		@Override
		public final void destroy()
		{
			if(programId != 0)
			{
				glUseProgram(0);
				
				for(Integer shader : attachedShaders)
				{
					glDetachShader(programId, shader);
					glDeleteShader(shader);
				}			
				
				glDeleteProgram(programId);
				
				programId = 0;
				attachedShaders.clear();
			}
		}
		
		/**
		 * Create and add shaders
		 * @param shaders A list of all shaders in the shader file
		 */
		private final void createShaders(Map<Integer, String> shaders)
		{
			attachedShaders = new ArrayList<Integer>();
			
			for(Map.Entry<Integer, String> shader : shaders.entrySet())
			{
				if(shader.getValue().length() > 0)
				{				
					int id = glCreateShader(shader.getKey());
					
					if(id == 0)
					{
						Logger.logError("Unable to create shader", this);
						return;
					}
					
					glShaderSource(id, shader.getValue());
					glCompileShader(id);
					
					if(glGetShaderi(id, GL_COMPILE_STATUS) == 0)
					{
						Logger.logError("Unable to compile shader source: " + glGetShaderInfoLog(id, 1024), this);
						return;
					}
					
					glAttachShader(programId, id);
					
					attachedShaders.add(id);
				}
			}
		}
		
		/**
		 * Link the program
		 */
		private final void link()
		{
			glLinkProgram(programId);
			if(glGetProgrami(programId, GL_LINK_STATUS) == 0)
			{
				Logger.logError("Unable to link shader program: " + glGetProgramInfoLog(programId, 1024), this);
				return;
			}
			
			glValidateProgram(programId);
			if(glGetProgrami(programId, GL_VALIDATE_STATUS) == 0)
			{
				Logger.logError("Unable to validate shader program: " + glGetProgramInfoLog(programId, 1024), this);
				return;
			}
		}
		
		/**
		 * Find all uniforms and store them 
		 * @param fileName The source file name
		 * @param source The contents of the {@code fileName}
		 */
		private final void addUniforms(String fileName, String source)
		{
			String[] lines = source.split("\n");
			
			for(String line : lines)
			{
				if(line.startsWith("uniform "))
				{
					String[] segments = line.substring("uniform ".length(), line.length() - 1).split(" ");
					int location = glGetUniformLocation(programId, segments[1]);
					
					if(location < 0)
					{
						Logger.logError("Unable to find uniform: " + segments[1], this);
						continue;
					}
					
					this.uniforms.put(segments[1], location);
					this.uniformTypes.put(segments[1], segments[0]);
					
					LoggerInternal.log("Added uniform: (" + segments[0] + ") " + segments[1] + " to: " + fileName, this);
				}
			}
		}
	}
	
	private ShaderResource resource;
	
	public Shader(String fileName)
	{
		resource = ResourceDatabase.load(ShaderResource.class, fileName, this, new ShaderResourceImporter(fileName));
	}
	
	@Override
	protected final void finalize() throws Throwable
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
		resource.destroy();
	}
	
	/**
	 * Tell OpenGL to use this program
	 */
	public final void bind()
	{
		glUseProgram(resource.programId);
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
		return resource.uniforms.containsKey(name);
	}
	
	/**
	 * Set an integer value
	 * @param name The name of the uniform
	 * @param value The value of the uniform
	 */
	public final void setUniform1i(String name, int value)
	{
		glUniform1i(resource.uniforms.get(name), value);
	}
	
	/**
	 * Set a float value
	 * @param name The name of the uniform
	 * @param value The value of the uniform
	 */
	public final void setUniform1f(String name, float value)
	{
		glUniform1f(resource.uniforms.get(name), value);
	}
	
	/**
	 * Set a {@link Vector2f} value
	 * @param name The name of the uniform
	 * @param value The value of the uniform
	 */
	public final void setUniform2f(String name, Vector2f value)
	{
		glUniform2f(resource.uniforms.get(name), value.x, value.y);
	}
	
	/**
	 * Set a {@link Vector3f} value
	 * @param name The name of the uniform
	 * @param value The value of the uniform
	 */
	public final void setUniform3f(String name, Vector3f value)
	{
		glUniform3f(resource.uniforms.get(name), value.x, value.y, value.z);
	}
	
	/**
	 * Set a {@link Vector4f} value
	 * @param name The name of the uniform
	 * @param value The value of the uniform
	 */
	public final void setUniform2f(String name, Vector4f value)
	{
		glUniform4f(resource.uniforms.get(name), value.x, value.y, value.z, value.w);
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
		
		glUniformMatrix3fv(resource.uniforms.get(name), false, buffer);
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
		
		glUniformMatrix4fv(resource.uniforms.get(name), false, buffer);
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
			return resource.uniformTypes.get(name);
		}
		
		return null;
	}
}
