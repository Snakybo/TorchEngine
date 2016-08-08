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

package com.snakybo.torch.model;

import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.model.obj.OBJModel;
import com.snakybo.torch.util.FileUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.List;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Model
{
	private ModelAsset asset;
	
	private Model(ModelAsset asset)
	{
		this.asset = asset;
	}
	
	/**
	 * Create a new model
	 */
	public Model()
	{
		asset = new ModelAsset("");
	}
	
	/**
	 * Attempt to calculate the normals of a model automatically
	 */
	public final void generateNormals()
	{
		for(int i = 0; i < asset.indices.size(); i += 3)
		{
			int i0 = asset.indices.get(i);
			int i1 = asset.indices.get(i + 1);
			int i2 = asset.indices.get(i + 2);
			
			Vector3f v1 = new Vector3f();
			asset.vertices.get(i1).sub(asset.vertices.get(i0), v1);
			
			Vector3f v2 = new Vector3f();
			asset.vertices.get(i2).sub(asset.vertices.get(i0), v2);
			
			Vector3f normal = new Vector3f();
			v1.cross(v2, normal).normalize();
			
			asset.normals.get(i0).add(normal);
			asset.normals.get(i1).add(normal);
			asset.normals.get(i2).add(normal);
		}
		
		asset.normals.forEach(Vector3f::normalize);
	}
	
	/**
	 * Attempt to calculate the tangents of a model automatically
	 */
	public final void generateTangents()
	{
		for(int i = 0; i < asset.indices.size(); i += 3)
		{
			int i0 = asset.indices.get(i);
			int i1 = asset.indices.get(i + 1);
			int i2 = asset.indices.get(i + 2);

			Vector3f edge1 = new Vector3f();
			asset.vertices.get(i1).sub(asset.vertices.get(i0), edge1);
			
			Vector3f edge2 = new Vector3f();
			asset.vertices.get(i2).sub(asset.vertices.get(i0), edge2);

			float deltaU1 = asset.texCoords.get(i1).x - asset.texCoords.get(i0).x;
			float deltaV1 = asset.texCoords.get(i1).y - asset.texCoords.get(i0).y;
			float deltaU2 = asset.texCoords.get(i2).x - asset.texCoords.get(i0).x;
			float deltaV2 = asset.texCoords.get(i2).y - asset.texCoords.get(i0).y;

			float dividend = deltaU1 * deltaV2 - deltaU2 * deltaV1;
			float f = dividend == 0 ? 0 : 1 / dividend;

			Vector3f tangent = new Vector3f();
			tangent.x = f * (deltaV2 * edge1.x - deltaV1 * edge2.x);
			tangent.y = f * (deltaV2 * edge1.y - deltaV1 * edge2.y);
			tangent.z = f * (deltaV2 * edge1.z - deltaV1 * edge2.z);
			
			asset.tangents.get(i0).add(tangent);
			asset.tangents.get(i1).add(tangent);
			asset.tangents.get(i2).add(tangent);
		}
		
		asset.tangents.forEach(Vector3f::normalize);
	}
	
	/**
	 * Add a vertex to the model
	 * @param vertex The vertex to add
	 */
	public final void addVertex(Vector3f vertex)
	{
		asset.vertices.add(vertex);
	}
	
	/**
	 * Add a texture coordinate to the model
	 * @param texCoord The texture coordinate to add
	 */
	public final void addTexCoord(Vector2f texCoord)
	{
		asset.texCoords.add(texCoord);
	}
	
	/**
	 * Add a normal to the model
	 * @param normal The model to add
	 */
	public final void addNormal(Vector3f normal)
	{
		asset.normals.add(normal);
	}
	
	/**
	 * Add a tangent to the model
	 * @param tangent The tangent to add
	 */
	public final void addTangent(Vector3f tangent)
	{
		asset.tangents.add(tangent);
	}
	
	/**
	 * Add a single index to the model
	 * @param index The index to add
	 */
	public final void addIndex(int index)
	{
		asset.indices.add(index);
	}
	
	/**
	 * Add a face, or 3 indices to the model
	 * @param i0 The first index
	 * @param i1 The second index
	 * @param i2 The third index
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
		return asset.vertices;
	}
	
	/**
	 * @return The texture coordinates of the model
	 */
	public final List<Vector2f> getTexCoords()
	{
		return asset.texCoords;
	}
	
	/**
	 * @return The normals of the model
	 */
	public final List<Vector3f> getNormals()
	{
		return asset.normals;
	}
	
	/**
	 * @return The tangents of the model
	 */
	public final List<Vector3f> getTangents()
	{
		return asset.tangents;
	}
	
	/**
	 * @return The indices of the model
	 */
	public final List<Integer> getIndices()
	{
		return asset.indices;
	}
	
	/**
	 * @param index The index of the vertex
	 * @return The vertex at the specified index
	 */
	public final Vector3f getVertex(int index)
	{
		return asset.vertices.get(index);
	}
	
	/**
	 * @param index The index of the texCoord
	 * @return The texCoord at the specified index
	 */
	public final Vector2f getTexCoord(int index)
	{
		return asset.texCoords.get(index);
	}
	
	/**
	 * @param index The index of the normal
	 * @return The normal at the specified index
	 */
	public final Vector3f getNormal(int index)
	{
		return asset.normals.get(index);
	}
	
	/**
	 * @param index The index of the tangent
	 * @return The tangent at the specified index
	 */
	public final Vector3f getTangent(int index)
	{
		return asset.tangents.get(index);
	}
	
	/**
	 * @return The vertices array to a FloatBuffer
	 */
	public final FloatBuffer getVertexBuffer()
	{
		FloatBuffer result = BufferUtils.createFloatBuffer(asset.vertices.size() * 3);
		
		for(Vector3f vertex : asset.vertices)
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
		FloatBuffer result = BufferUtils.createFloatBuffer(asset.texCoords.size() * 2);
		
		for(Vector2f texCoord : asset.texCoords)
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
		FloatBuffer result = BufferUtils.createFloatBuffer(asset.normals.size() * 3);
		
		for(Vector3f normal : asset.normals)
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
		IntBuffer result = BufferUtils.createIntBuffer(asset.indices.size());
		
		asset.indices.forEach(result::put);
		
		result.flip();
		return result;
	}
	
	/**
	 * @return The number of indices the model contains
	 */
	public final int getNumIndices()
	{
		return asset.indices.size();
	}
	
	/**
	 * @return The number of triangles, or faces the model contains
	 */
	public final int getNumTriangles()
	{
		return asset.indices.size() / 3;
	}
	
	public static Model load(String path)
	{
		if(ModelAsset.all.containsKey(path))
		{
			return new Model(ModelAsset.all.get(path));
		}
		
		try
		{
			LoggerInternal.log("Importing " + FileUtils.getName(path));
			
			String extension = FileUtils.getExtension(path);
			
			String target = path;
			try
			{
				FileUtils.toURI(target);
			}
			catch(NoSuchFileException ex)
			{
				target = "torch_internal/" + target;
			}
			
			List<String> lines = Files.readAllLines(Paths.get(FileUtils.toURI(target)));
			IModelLoader loader = null;
			
			switch(extension)
			{
			case "obj":
				loader = new OBJModel(lines);
				break;
			}
			
			return loader.toModel();
		}
		catch(IOException e)
		{
			Logger.logError(e.toString(), e);
		}
		
		return null;
	}
}
