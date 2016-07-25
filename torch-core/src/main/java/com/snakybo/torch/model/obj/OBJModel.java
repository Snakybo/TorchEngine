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

package com.snakybo.torch.model.obj;

import com.snakybo.torch.model.IModelLoader;
import com.snakybo.torch.model.Model;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class OBJModel implements IModelLoader
{	
	private final OBJParser parser;
	
	public OBJModel(List<String> lines)
	{
		parser = new OBJParser();		
		parser.parse(lines);
	}
	
	@Override
	public final Model toModel()
	{
		Model model = new Model();
		Model normalModel = new Model();

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
				modelVertexIndex = model.getVertices().size();
				resultIndexMap.put(index, modelVertexIndex);
				
				model.addVertex(vertex);
				model.addTexCoord(texCoord);
				
				if(parser.hasNormals)
				{
					model.addNormal(normal);
				}
			}
			
			Integer normalModelIndex = normalIndexMap.get(index.vertex);
			if(normalModelIndex == null)
			{
				normalModelIndex = normalModel.getVertices().size();
				normalIndexMap.put(index.vertex, normalModelIndex);
				
				normalModel.addVertex(vertex);
				normalModel.addTexCoord(texCoord);
				normalModel.addNormal(normal);
				normalModel.addTangent(new Vector3f());
			}
			
			model.addIndex(modelVertexIndex);
			normalModel.addIndex(normalModelIndex);
			indexMap.put(modelVertexIndex, normalModelIndex);
		}
		
		if(!parser.hasNormals)
		{
			normalModel.generateNormals();			
			for(int i = 0; i < model.getVertices().size(); i++)
			{
				model.addNormal(normalModel.getNormal(indexMap.get(i)));
			}
		}
		
		normalModel.generateTangents();		
		for(int i = 0; i < model.getVertices().size(); i++)
		{
			model.addTangent(normalModel.getTangent(indexMap.get(i)));
		}
		
		return model;
	}
}
