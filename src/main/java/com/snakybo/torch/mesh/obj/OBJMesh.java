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

package com.snakybo.torch.mesh.obj;

import com.snakybo.torch.mesh.IMeshLoader;
import com.snakybo.torch.mesh.Mesh;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class OBJMesh implements IMeshLoader
{	
	private final OBJParser parser;
	
	public OBJMesh(List<String> lines)
	{
		parser = new OBJParser();		
		parser.parse(lines);
	}
	
	@Override
	public final Mesh toModel(Mesh result)
	{
		Mesh normalMesh = new Mesh();

		Map<OBJIndex, Integer> resultIndexMap = new HashMap<>();
		Map<Integer, Integer> normalIndexMap = new HashMap<>();
		Map<Integer, Integer> indexMap = new HashMap<>();

		for(OBJIndex index : parser.indices)
		{
			Vector3f vertex = parser.vertices.get(index.vertex);
			Vector2f texCoord = new Vector2f();
			Vector3f normal = new Vector3f();
			
			if(parser.hasTexCoords)
			{
				texCoord = parser.texCoords.get(index.texCoord);
			}
			
			if(parser.hasNormals)
			{
				normal = parser.normals.get(index.normal);
			}
			
			Integer modelVertexIndex = resultIndexMap.get(index);
			if(modelVertexIndex == null)
			{
				modelVertexIndex = result.getVertices().size();
				resultIndexMap.put(index, modelVertexIndex);
				
				result.addVertex(vertex);
				result.addTexCoord(texCoord);
				
				if(parser.hasNormals)
				{
					result.addNormal(normal);
				}
			}
			
			Integer normalModelIndex = normalIndexMap.get(index.vertex);
			if(normalModelIndex == null)
			{
				normalModelIndex = normalMesh.getVertices().size();
				normalIndexMap.put(index.vertex, normalModelIndex);
				
				normalMesh.addVertex(vertex);
				normalMesh.addTexCoord(texCoord);
				normalMesh.addNormal(normal);
				normalMesh.addTangent(new Vector3f());
			}
			
			result.addIndex(modelVertexIndex);
			normalMesh.addIndex(normalModelIndex);
			indexMap.put(modelVertexIndex, normalModelIndex);
		}
		
		if(!parser.hasNormals)
		{
			normalMesh.generateNormals();
			for(int i = 0; i < result.getVertices().size(); i++)
			{
				result.addNormal(normalMesh.getNormal(indexMap.get(i)));
			}
		}
		
		normalMesh.generateTangents();
		for(int i = 0; i < result.getVertices().size(); i++)
		{
			result.addTangent(normalMesh.getTangent(indexMap.get(i)));
		}
		
		return result;
	}
}
