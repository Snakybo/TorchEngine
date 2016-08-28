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

package com.snakybo.torch.graphics.mesh;

import com.snakybo.torch.asset.Asset;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * A mesh represents a 3D model the engine can read, it can contain {@code vertices}, {@code texCoords},
 * {@code normals}, {@code tangents}, and {@code indices}.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class Mesh extends Asset
{
	private MeshAsset asset;
	
	Mesh(MeshAsset asset)
	{
		this.asset = asset;
		this.asset.addUsage();
	}
	
	Mesh(String name)
	{
		asset = new MeshAsset(name);
	}
	
	/**
	 * Create a new, empty mesh.
	 */
	public Mesh()
	{
		asset = new MeshAsset("");
	}
	
	@Override
	public final void finalize() throws Throwable
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
	public void destroy()
	{
		if(asset != null)
		{
			asset.removeUsage();
			asset = null;
		}
	}
	
	/**
	 * <p>
	 * Attempt to calculate the normals automatically
	 * </p>
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
	 * <p>
	 * Attempt to calculate the tangents automatically.
	 * </p>
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
	 * <p>
	 * Add a new {@code vertex}.
	 * </p>
	 *
	 * @param vertex The vertex to add
	 */
	public final void addVertex(Vector3f vertex)
	{
		asset.vertices.add(vertex);
	}
	
	/**
	 * <p>
	 * Add a new {@code texCoord}.
	 * </p>
	 *
	 * @param texCoord The {@code texCoord} to add.
	 */
	public final void addTexCoord(Vector2f texCoord)
	{
		asset.texCoords.add(texCoord);
	}
	
	/**
	 * <p>
	 * Add a new {@code normal}.
	 * </p>
	 *
	 * @param normal The {@code normal} to add.
	 */
	public final void addNormal(Vector3f normal)
	{
		asset.normals.add(normal);
	}
	
	/**
	 * <p>
	 * Add a new {@code tangent}.
	 * </p>
	 *
	 * @param tangent The {@code tangent} to add.
	 */
	public final void addTangent(Vector3f tangent)
	{
		asset.tangents.add(tangent);
	}
	
	/**
	 * <p>
	 * Add a new {@code index}.
	 * </p>
	 *
	 * @param index The {@code index} to add.
	 */
	public final void addIndex(int index)
	{
		asset.indices.add(index);
	}
	
	/**
	 * <p>
	 * Add a new face.
	 * </p>
	 *
	 * <p>
	 * This is a shortcut for calling {@link #addIndex(int)} 3 times.
	 * </p>
	 *
	 * @param i0 The first {@code index}.
	 * @param i1 The second {@code index}.
	 * @param i2 The third {@code index}.
	 */
	public final void addFace(int i0, int i1, int i2)
	{
		addIndex(i0);
		addIndex(i1);
		addIndex(i2);
	}
	
	/**
	 * <p>
	 * Get a list containing all {@code vertices}.
	 * </p>
	 *
	 * @return All {@code Vertices}.
	 */
	public final List<Vector3f> getVertices()
	{
		return new ArrayList<>(asset.vertices);
	}
	
	/**
	 * <p>
	 * Get a list containing all {@code texCoords}.
	 * </p>
	 *
	 * @return All {@code TexCoords}.
	 */
	public final List<Vector2f> getTexCoords()
	{
		return new ArrayList<>(asset.texCoords);
	}
	
	/**
	 * <p>
	 * Get a list containing all {@code indices}.
	 * </p>
	 *
	 * @return All {@code normals}.
	 */
	public final List<Vector3f> getNormals()
	{
		return new ArrayList<>(asset.normals);
	}
	
	/**
	 * <p>
	 * Get a list containing all {@code tangents}.
	 * </p>
	 *
	 * @return All {@code tangents}.
	 */
	public final List<Vector3f> getTangents()
	{
		return new ArrayList<>(asset.tangents);
	}
	
	/**
	 * <p>
	 * Get a list containing all {@code indices}.
	 * </p>
	 *
	 * @return All {@code indices}.
	 */
	public final List<Integer> getIndices()
	{
		return new ArrayList<>(asset.indices);
	}
	
	/**
	 * <p>
	 * Get the {@code vertex} at the specified {@code index}.
	 * </p>
	 *
	 * @param index The index of the vertex.
	 * @return The vertex at the specified index.
	 */
	public final Vector3f getVertex(int index)
	{
		return asset.vertices.get(index);
	}
	
	/**
	 * <p>
	 * Get the {@code texCoord} at the specified {@code index}.
	 * </p>
	 *
	 * @param index The index of the texCoord.
	 * @return The texCoord at the specified index.
	 */
	public final Vector2f getTexCoord(int index)
	{
		return asset.texCoords.get(index);
	}
	
	/**
	 * <p>
	 * Get the {@code normal} at the specified {@code index}.
	 * </p>
	 *
	 * @param index The index of the normal.
	 * @return The normal at the specified index.
	 */
	public final Vector3f getNormal(int index)
	{
		return asset.normals.get(index);
	}
	
	/**
	 * <p>
	 * Get the {@code tangent} at the specified {@code index}.
	 * </p>
	 *
	 * @param index The index of the tangent.
	 * @return The tangent at the specified index.
	 */
	public final Vector3f getTangent(int index)
	{
		return asset.tangents.get(index);
	}
	
	/**
	 * <p>
	 * Get the vertices array as an {@code FloatBuffer}.
	 * </p>
	 *
	 * @return The vertices array as an {@code FloatBuffer}.
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
	 * <p>
	 * Get the texCoords array as an {@code FloatBuffer}.
	 * </p>
	 *
	 * @return The texCoords array as an {@code FloatBuffer}.
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
	 * <p>
	 * Get the normals array as an {@code FloatBuffer}.
	 * </p>
	 *
	 * @return The normals array as an {@code FloatBuffer}.
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
	 * <p>
	 * Get the tangents array as an {@code FloatBuffer}.
	 * </p>
	 *
	 * @return The tangents array as an {@code FloatBuffer}.
	 */
	public final FloatBuffer getTangentBuffer()
	{
		FloatBuffer result = BufferUtils.createFloatBuffer(asset.vertices.size() * 3);
		
		for(Vector3f tangent : asset.tangents)
		{
			result.put(tangent.x);
			result.put(tangent.y);
			result.put(tangent.z);
		}
		
		result.flip();
		return result;
	}
	
	/**
	 * <p>
	 * Get the indices array as an {@code IntBuffer}.
	 * </p>
	 *
	 * @return The indices array as an {@code IntBuffer}.
	 */
	public final IntBuffer getIndexBuffer()
	{
		IntBuffer result = BufferUtils.createIntBuffer(asset.indices.size());
		
		asset.indices.forEach(result::put);
		
		result.flip();
		return result;
	}
	
	/**
	 * <p>
	 * Get the number of indices the mesh contains.
	 * </p>
	 *
	 * @return The number of indices the mesh contains.
	 */
	public final int getNumIndices()
	{
		return asset.indices.size();
	}
	
	/**
	 * <p>
	 * Get the number of triangles this mesh contains.
	 * </p>
	 *
	 * @return The number of triangles, or faces the mesh contains.
	 */
	public final int getNumTriangles()
	{
		return asset.indices.size() / 3;
	}
}
