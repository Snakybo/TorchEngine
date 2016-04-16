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

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL32.GL_GEOMETRY_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_CONTROL_SHADER;
import static org.lwjgl.opengl.GL40.GL_TESS_EVALUATION_SHADER;
import static org.lwjgl.opengl.GL43.GL_COMPUTE_SHADER;

import java.util.HashMap;
import java.util.Map;

import com.snakybo.sengine.io.File;
import com.snakybo.sengine.resource.ResourceImporter;
import com.snakybo.sengine.shader.Shader.ShaderResource;

/**
 * @author Snakybo
 * @since 1.0
 */
class ShaderResourceImporter extends ResourceImporter<ShaderResource>
{
	private final String fileName;
	
	ShaderResourceImporter(String fileName)
	{
		this.fileName = fileName;
	}
	
	@Override
	protected final ShaderResource importResource()
	{
		Map<Integer, String> result = new HashMap<Integer, String>();
		String source = File.readLinesMerge(fileName);
		
		result.put(GL_VERTEX_SHADER, parseShader(source, "VERTEX_PASS"));
		result.put(GL_FRAGMENT_SHADER, parseShader(source, "FRAGMENT_PASS"));
		result.put(GL_GEOMETRY_SHADER, parseShader(source, "GEOMETRY_PASS"));
		result.put(GL_COMPUTE_SHADER, parseShader(source, "COMPUTE_PASS"));
		result.put(GL_TESS_CONTROL_SHADER, parseShader(source, "TESS_CONTROL_PASS"));
		result.put(GL_TESS_EVALUATION_SHADER, parseShader(source, "TESS_EVAL_PASS"));
		
		return new ShaderResource(fileName, source, result);
	}
	
	/**
	 * Attempt to parse a single shader from a shader source
	 * @param source The entire source code
	 * @param keyword The keyword of the current shader 
	 * @return The source of the shader, or an empty string
	 */
	private final String parseShader(String source, String keyword)
	{
		if(source.contains("#ifdef " + keyword))
		{
			return "#version " + GLSLVersion.GLSL_VERSION + "\n\n#define " + keyword + "\n\n" + source;
		}
		
		return "";
	}
}
