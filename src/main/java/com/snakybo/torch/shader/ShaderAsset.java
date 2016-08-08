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
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.util.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glValidateProgram;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * @author Snakybo
 * @since 1.0
 */
final class ShaderAsset
{
	static Map<String, ShaderAsset> all = new HashMap<>();
	
	Map<String, Integer> uniforms;
	Map<String, String> uniformTypes;
	
	List<Integer> attachedShaders;
	
	String name;
	
	int programId;
	
	ShaderAsset(String name)
	{
		this.name = name;
		
		uniforms = new HashMap<>();
		uniformTypes = new HashMap<>();
		
		attachedShaders = new ArrayList<>();
		
		programId = glCreateProgram();
		if(programId == NULL)
		{
			Logger.logError("Unable to create shader program", this);
		}
		
		if(name != null && !name.isEmpty())
		{
			all.put(name, this);
		}
	}
	
	final void init()
	{
		String source = null;
		
		try
		{
			String target = name;
			try
			{
				FileUtils.toURI(target);
			}
			catch(NoSuchFileException ex)
			{
				target = "torch_internal/" + target;
			}
			
			Path p = Paths.get(FileUtils.toURI(target));
			List<String> sourceLines = Files.readAllLines(p);
			
			StringBuilder stringBuilder = new StringBuilder();
			for(String line : sourceLines)
			{
				stringBuilder.append(line).append("\n");
			}
			
			source = stringBuilder.toString();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		createShader(GL_VERTEX_SHADER, parseShader(source, "VERTEX_PASS"));
		createShader(GL_FRAGMENT_SHADER, parseShader(source, "FRAGMENT_PASS"));
		createShader(GL_GEOMETRY_SHADER, parseShader(source, "GEOMETRY_PASS"));
		createShader(GL_COMPUTE_SHADER, parseShader(source, "COMPUTE_PASS"));
		createShader(GL_TESS_CONTROL_SHADER, parseShader(source, "TESS_CONTROL_PASS"));
		createShader(GL_TESS_EVALUATION_SHADER, parseShader(source, "TESS_EVAL_PASS"));
		
		link();
		addUniforms(name, source);
	}
	
	private void createShader(int type, String source)
	{
		if(source.length() > 0)
		{
			int id = glCreateShader(type);
			if(id == NULL)
			{
				Logger.logError("Unable to create shader", "ShaderLoader");
				return;
			}
			
			glShaderSource(id, source);
			glCompileShader(id);
			
			if(glGetShaderi(id, GL_COMPILE_STATUS) == NULL)
			{
				Logger.logError("Unable to compile shader source: " + glGetShaderInfoLog(id, 1024), "ShaderLoader");
				return;
			}
			
			glAttachShader(programId, id);
			attachedShaders.add(id);
		}
	}
	
	private void link()
	{
		glLinkProgram(programId);
		if(glGetProgrami(programId, GL_LINK_STATUS) == NULL)
		{
			Logger.logError("Unable to link shader program: " + glGetProgramInfoLog(programId, 1024), "ShaderLoader");
			return;
		}
		
		glValidateProgram(programId);
		if(glGetProgrami(programId, GL_VALIDATE_STATUS) == NULL)
		{
			Logger.logError("Unable to validate shader program: " + glGetProgramInfoLog(programId, 1024), "ShaderLoader");
		}
	}
	
	private void addUniforms(String uri, String source)
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
					Logger.logError("Unable to find uniform: " + segments[1], "ShaderLoader");
					continue;
				}
				
				uniforms.put(segments[1], location);
				uniformTypes.put(segments[1], segments[0]);
				
				LoggerInternal.log("Added uniform: (" + segments[0] + ") " + segments[1] + " to shader: " + FileUtils.getSimpleName(uri), "ShaderLoader");
			}
		}
	}
	
	private String parseShader(String source, String keyword)
	{
		if(source.contains("#ifdef " + keyword))
		{
			return "#version " + GLSLVersion.GLSL_VERSION + "\n\n#define " + keyword + "\n\n" + source;
		}
		
		return "";
	}
}
