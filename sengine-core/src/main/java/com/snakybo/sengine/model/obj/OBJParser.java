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

package com.snakybo.sengine.model.obj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;

import com.snakybo.sengine.util.StringUtils;

/**
 * @author Snakybo
 * @since 1.0
 */
final class OBJParser
{
	private static final boolean INVERSE_NORMALS = true;
	
	List<Vector3f> vertices;
	List<Vector2f> texCoords;
	List<Vector3f> normals;	
	List<OBJIndex> indices;

	boolean hasTexCoords;
	boolean hasNormals;
	
	OBJParser()
	{
		indices = new ArrayList<OBJIndex>();
		vertices = new ArrayList<Vector3f>();
		texCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
	}
	
	/**
	 * Parse a model
	 * @param lines The lines of the target file
	 */
	public final void parse(List<String> lines)
	{
		for(String line : lines)
		{
			String[] tokens = StringUtils.removeEmptyStrings(Arrays.asList(line.split(" ")));
			
			if(tokens.length == 0 || tokens[0].equals("#"))
			{
				continue;
			}
			
			if(parseVertex(tokens))
			{
				continue;
			}
			
			if(parseTexCoord(tokens))
			{
				continue;
			}
			
			if(parseNormal(tokens))
			{
				continue;
			}
			
			if(parseFace(tokens))
			{
				continue;
			}
		}
	}
	
	private final boolean parseVertex(String[] tokens)
	{
		if(!tokens[0].equals("v"))
		{
			return false;
		}
		
		float x = Float.valueOf(tokens[1]);
		float y = Float.valueOf(tokens[2]);
		float z = Float.valueOf(tokens[3]);
		
		vertices.add(new Vector3f(x, y, z));
		
		return true;
	}
	
	private final boolean parseTexCoord(String[] tokens)
	{
		if(!tokens[0].equals("vt"))
		{
			return false;
		}
		
		float x = Float.valueOf(tokens[1]);
		float y = Float.valueOf(tokens[2]);
		
		if(INVERSE_NORMALS)
		{
			y = 1 - y;
		}
		
		texCoords.add(new Vector2f(x, y));		
		return true;
	}
	
	private final boolean parseNormal(String[] tokens)
	{
		if(!tokens[0].equals("vn"))
		{
			return false;
		}
		
		float x = Float.valueOf(tokens[1]);
		float y = Float.valueOf(tokens[2]);
		float z = Float.valueOf(tokens[3]);
		
		normals.add(new Vector3f(x, y, z));
		
		return true;
	}
	
	private final boolean parseFace(String[] tokens)
	{
		if(!tokens[0].equals("f"))
		{
			return false;
		}
		
		for(int i = 0; i < tokens.length - 3; i++)
		{
			OBJIndex i1 = parseOBJIndex(tokens[1]);
			OBJIndex i2 = parseOBJIndex(tokens[2 + i]);
			OBJIndex i3 = parseOBJIndex(tokens[3 + i]);
			
			indices.add(i1);
			indices.add(i2);
			indices.add(i3);
		}
		
		return true;
	}
	
	private final OBJIndex parseOBJIndex(String token)
	{
		String[] tokens = token.split("/");		

		OBJIndex result = new OBJIndex();
		result.vertex = Integer.parseInt(tokens[0]) - 1;

		if(tokens.length > 1)
		{
			if(!tokens[1].isEmpty())
			{
				hasTexCoords = true;
				result.texCoord = Integer.parseInt(tokens[1]) - 1;
			}

			if(tokens.length > 2)
			{
				hasNormals = true;
				result.normal = Integer.parseInt(tokens[2]) - 1;
			}
		}

		return result;
	}
}
