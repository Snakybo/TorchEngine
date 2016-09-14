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

import com.snakybo.torch.asset.AssetData;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Kevin
 * @since 1.0
 */
final class MeshAsset extends AssetData
{
	static Map<String, MeshAsset> all = new HashMap<>();
	
	List<Vector3f> vertices;
	List<Vector2f> texCoords;
	List<Vector3f> normals;
	List<Vector3f> tangents;
	List<Integer> indices;
	
	String name;
	
	public MeshAsset(String name)
	{
		super(name);
		
		vertices = new ArrayList<>();
		texCoords = new ArrayList<>();
		normals = new ArrayList<>();
		tangents = new ArrayList<>();
		indices = new ArrayList<>();
		
		if(name != null && !name.isEmpty())
		{
			all.put(name, this);
		}
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(this == o)
		{
			return true;
		}
		
		if(o == null || getClass() != o.getClass())
		{
			return false;
		}
		
		MeshAsset meshAsset = (MeshAsset)o;
		
		if(vertices != null ? !vertices.equals(meshAsset.vertices) : meshAsset.vertices != null)
		{
			return false;
		}
		
		if(texCoords != null ? !texCoords.equals(meshAsset.texCoords) : meshAsset.texCoords != null)
		{
			return false;
		}
		
		if(normals != null ? !normals.equals(meshAsset.normals) : meshAsset.normals != null)
		{
			return false;
		}
		
		if(tangents != null ? !tangents.equals(meshAsset.tangents) : meshAsset.tangents != null)
		{
			return false;
		}
		
		if(indices != null ? !indices.equals(meshAsset.indices) : meshAsset.indices != null)
		{
			return false;
		}
		
		return name != null ? name.equals(meshAsset.name) : meshAsset.name == null;
	}
	
	@Override
	public int hashCode()
	{
		int result = vertices != null ? vertices.hashCode() : 0;
		
		result = 31 * result + (texCoords != null ? texCoords.hashCode() : 0);
		result = 31 * result + (normals != null ? normals.hashCode() : 0);
		result = 31 * result + (tangents != null ? tangents.hashCode() : 0);
		result = 31 * result + (indices != null ? indices.hashCode() : 0);
		result = 31 * result + (name != null ? name.hashCode() : 0);
		
		return result;
	}
	
	@Override
	public final void destroy()
	{
		if(name != null && !name.isEmpty())
		{
			all.remove(name);
		}
	}
}
