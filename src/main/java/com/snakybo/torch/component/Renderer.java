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

package com.snakybo.torch.component;

import com.snakybo.torch.asset.Assets;
import com.snakybo.torch.graphics.material.Material;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.util.serialized.SerializedField;

/**
 * <p>
 * Base renderer class, all renderers should extend this class to get full functionality.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public abstract class Renderer extends Component
{
	@SerializedField private Material material = Assets.load(Material.class, "default.mtl");
	
	protected MeshFilter meshFilter;
	
	private int cachedMeshHashCode;
	
	@Override
	protected void onCreate()
	{
		meshFilter = getComponent(MeshFilter.class);
		if(meshFilter == null)
		{
			throw new RuntimeException("GameObject has a Renderer component but does not have a MeshFilter component.");
		}
		
		cachedMeshHashCode = meshFilter.getMesh().hashCode();
		
		material.setTransform(getTransform());
	}
	
	@Override
	protected void onPreRenderObject()
	{
		int hashCode = meshFilter.getMesh().hashCode();
		
		if(cachedMeshHashCode != hashCode)
		{
			cachedMeshHashCode = hashCode;
			onMeshUpdated();
		}
	}
	
	@Override
	protected void onRenderObject()
	{
		material.bind();
		material.update();
		
		render();
		
		material.unbind();
	}
	
	@Override
	protected void onDestroy()
	{
		material.destroy();
	}
	
	/**
	 * <p>
	 * Called after the material has been updated.
	 * </p>
	 */
	protected abstract void render();
	
	/**
	 * <p>
	 * Called every time the mesh has been changed, this allows subclasses to update their mesh data.
	 * </p>
	 */
	protected abstract void onMeshUpdated();
	
	/**
	 * <p>
	 * Set the {@link Material}.
	 * </p>
	 *
	 * @param material The new material.
	 */
	public final void setMaterial(Material material)
	{
		this.material = material;
	}
	
	/**
	 * <p>
	 * Get the {@link Material}.
	 * </p>
	 *
	 * @return The current material.
	 */
	public final Material getMaterial()
	{
		return material;
	}
}
