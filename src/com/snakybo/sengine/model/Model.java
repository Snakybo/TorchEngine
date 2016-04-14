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

package com.snakybo.sengine.model;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.debug.LoggerInternal;
import com.snakybo.sengine.io.File;
import com.snakybo.sengine.io.FileUtils;
import com.snakybo.sengine.model.obj.OBJModel;
import com.snakybo.sengine.resource.Resource;
import com.snakybo.sengine.resource.ResourceDatabase;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Model extends Resource
{
	private List<Vector3f> vertices;
	private List<Vector2f> texCoords;
	private List<Vector3f> normals;
	private List<Vector3f> tangents;
	private List<Integer> indices;
	
	public Model()
	{
		this("");
	}
	
	/**
	 * Create a new model
	 */
	public Model(String name)
	{
		super(name);
		
		vertices = new ArrayList<Vector3f>();
		texCoords = new ArrayList<Vector2f>();
		normals = new ArrayList<Vector3f>();
		tangents = new ArrayList<Vector3f>();
		indices = new ArrayList<Integer>();
	}
	
	@Override
	public final void destroy()
	{
	}
	
	/**
	 * Attempt to calculate the normals of a model automatically
	 */
	public final void generateNormals()
	{
		for(int i = 0; i < indices.size(); i += 3)
		{
			int i0 = indices.get(i);
			int i1 = indices.get(i + 1);
			int i2 = indices.get(i + 2);
			
			Vector3f v1 = new Vector3f();
			vertices.get(i1).sub(vertices.get(i0), v1);
			
			Vector3f v2 = new Vector3f();			
			vertices.get(i2).sub(vertices.get(i0), v2);
			
			Vector3f normal = new Vector3f();
			v1.cross(v2, normal).normalize();
			
			normals.get(i0).add(normal);
			normals.get(i1).add(normal);
			normals.get(i2).add(normal);
		}
		
		for(int i = 0; i < normals.size(); i++)
		{
			normals.get(i).normalize();
		}
	}
	
	/**
	 * Attempt to calculate the tangents of a model automatically
	 */
	public final void generateTangents()
	{
		for(int i = 0; i < indices.size(); i += 3)
		{
			int i0 = indices.get(i);
			int i1 = indices.get(i + 1);
			int i2 = indices.get(i + 2);

			Vector3f edge1 = new Vector3f();
			vertices.get(i1).sub(vertices.get(i0), edge1);
			
			Vector3f edge2 = new Vector3f();
			vertices.get(i2).sub(vertices.get(i0), edge2);

			float deltaU1 = texCoords.get(i1).x - texCoords.get(i0).x;
			float deltaV1 = texCoords.get(i1).y - texCoords.get(i0).y;
			float deltaU2 = texCoords.get(i2).x - texCoords.get(i0).x;
			float deltaV2 = texCoords.get(i2).y - texCoords.get(i0).y;

			float dividend = deltaU1 * deltaV2 - deltaU2 * deltaV1;
			float f = dividend == 0 ? 0 : 1 / dividend;

			Vector3f tangent = new Vector3f();
			tangent.x = f * (deltaV2 * edge1.x - deltaV1 * edge2.x);
			tangent.y = f * (deltaV2 * edge1.y - deltaV1 * edge2.y);
			tangent.z = f * (deltaV2 * edge1.z - deltaV1 * edge2.z);

			tangents.get(i0).add(tangent);
			tangents.get(i1).add(tangent);
			tangents.get(i2).add(tangent);
		}

		for(int i = 0; i < tangents.size(); i++)
		{
			tangents.get(i).normalize();
		}
	}
	
	/**
	 * Add a vertex to the model
	 * @param vertex - The vertex to add
	 */
	public final void addVertex(Vector3f vertex)
	{
		vertices.add(vertex);
	}
	
	/**
	 * Add a texture coordinate to the model
	 * @param texCoord - The texture coordinate to add
	 */
	public final void addTexCoord(Vector2f texCoord)
	{
		texCoords.add(texCoord);
	}
	
	/**
	 * Add a normal to the model
	 * @param normal - The model to add
	 */
	public final void addNormal(Vector3f normal)
	{
		normals.add(normal);
	}
	
	/**
	 * Add a tangent to the model
	 * @param tangent - The tangent to add
	 */
	public final void addTangent(Vector3f tangent)
	{
		tangents.add(tangent);
	}
	
	/**
	 * Add a single index to the model
	 * @param index - The index to add
	 */
	public final void addIndex(int index)
	{
		indices.add(index);
	}
	
	/**
	 * Add a face, or 3 indices to the model
	 * @param i0 - The first index
	 * @param i1 - The second index
	 * @param i2 - The third index
	 */
	public final void addFace(int i0, int i1, int i2)
	{
		addIndex(i0);
		addIndex(i1);
		addIndex(i2);
	}
	
	/**
	 * @return The vertices of the model
	 */
	public final List<Vector3f> getVertices()
	{
		return vertices;
	}
	
	/**
	 * @return The texture coordinates of the model
	 */
	public final List<Vector2f> getTexCoords()
	{
		return texCoords;
	}
	
	/**
	 * @return The normals of the model
	 */
	public final List<Vector3f> getNormals()
	{
		return normals;
	}
	
	/**
	 * @return The tangents of the model
	 */
	public final List<Vector3f> getTangents()
	{
		return tangents;
	}
	
	/**
	 * @return The indices of the model
	 */
	public final List<Integer> getIndices()
	{
		return indices;
	}
	
	/**
	 * @param index - The index of the vertex
	 * @return The vertex at the specified index
	 */
	public final Vector3f getVertex(int index)
	{
		return vertices.get(index);
	}
	
	/**
	 * @param index - The index of the texCoord
	 * @return The texCoord at the specified index
	 */
	public final Vector2f getTexCoord(int index)
	{
		return texCoords.get(index);
	}
	
	/**
	 * @param index - The index of the normal
	 * @return The normal at the specified index
	 */
	public final Vector3f getNormal(int index)
	{
		return normals.get(index);
	}
	
	/**
	 * @param index - The index of the tangent
	 * @return The tangent at the specified index
	 */
	public final Vector3f getTangent(int index)
	{
		return tangents.get(index);
	}
	
	/**
	 * @return The vertices array to a FloatBuffer
	 */
	public final FloatBuffer getVertexBuffer()
	{
		FloatBuffer result = BufferUtils.createFloatBuffer(vertices.size() * 3);
		
		for(Vector3f vertex : vertices)
		{
			result.put(vertex.x);
			result.put(vertex.y);
			result.put(vertex.z);
		}
		
		result.flip();
		return result;
	}
	
	/**
	 * @return The texCoord array to a FloatBuffer
	 */
	public final FloatBuffer getTexCoordBuffer()
	{
		FloatBuffer result = BufferUtils.createFloatBuffer(texCoords.size() * 2);
		
		for(Vector2f texCoord : texCoords)
		{
			result.put(texCoord.x);
			result.put(texCoord.y);
		}
		
		result.flip();
		return result;
	}
	
	/**
	 * @return The normal array to a FloatBuffer
	 */
	public final FloatBuffer getNormalBuffer()
	{
		FloatBuffer result = BufferUtils.createFloatBuffer(normals.size() * 3);
		
		for(Vector3f normal : normals)
		{
			result.put(normal.x);
			result.put(normal.y);
			result.put(normal.z);
		}
		
		result.flip();
		return result;
	}
	
	/**
	 * @return The indices array to a FloatBuffer
	 */
	public final IntBuffer getIndexBuffer()
	{
		IntBuffer result = BufferUtils.createIntBuffer(indices.size());
		
		for(Integer index : indices)
		{
			result.put(index);
		}
		
		result.flip();
		return result;
	}
	
	/**
	 * @return The number of indices the model contains
	 */
	public final int getNumIndices()
	{
		return indices.size();
	}
	
	/**
	 * @return The number of triangles, or faces the model contains
	 */
	public final int getNumTriangles()
	{
		return indices.size() / 3;
	}
	
	/**
	 * Attempt to load a model, if it's been loaded before; it won't load it again
	 * @param fileName - The path to the model
	 * @return The imported model
	 */
	public static Model load(String fileName)
	{
		fileName = "./res/" + fileName;
		
		if(ResourceDatabase.hasResource(fileName))
		{
			LoggerInternal.log("Already imported " + fileName, "ModelLoader");
			return (Model)ResourceDatabase.get(fileName);
		}
		else
		{
			LoggerInternal.log("Importing " + fileName, "ModelLoader");
			
			if(!File.exists(fileName))
			{
				Logger.logError("No model found at: " + fileName, "ModelLoader");
				return null;
			}
			
			String extension = FileUtils.getFileExtension(fileName);
			
			List<String> lines = File.readLines(fileName);
			IModelLoader loader = null;
			
			switch(extension)
			{
			case "obj":
				loader = new OBJModel(lines);
				break;
			default:
				Logger.logError("Unsupported model format: " + extension, "ModelLoader");
				return null;
			}
			
			Model result = loader.toModel();
			ResourceDatabase.register(fileName, result, null);
			return result;
		}
	}
}
