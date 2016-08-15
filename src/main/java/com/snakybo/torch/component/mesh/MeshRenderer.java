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

package com.snakybo.torch.component.mesh;

import com.snakybo.torch.asset.Assets;
import com.snakybo.torch.material.Material;
import com.snakybo.torch.mesh.Mesh;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.renderer.MeshRendererInternal;
import com.snakybo.torch.serialized.Serialized;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class MeshRenderer extends Component
{
	@Serialized	private Mesh mesh = Assets.load(Mesh.class, "cube.obj");
	@Serialized private Material material = Assets.load(Material.class, "default.mtl");
	
	private MeshRendererInternal meshRenderer;
	
	@Override
	protected void start()
	{
		meshRenderer = new MeshRendererInternal(mesh, material);
		material.setTransform(getTransform());
		meshRenderer.create();
	}
	
	@Override
	protected void renderObject()
	{
		meshRenderer.render();
	}
	
	@Override
	protected void destroy()
	{
		meshRenderer.destroy();
	}
	
	public final void setMesh(Mesh mesh)
	{
		this.mesh = mesh;
		meshRenderer.destroy();
		meshRenderer = new MeshRendererInternal(mesh, material);
	}
	
	public final void setMaterial(Material material)
	{
		this.material = material;
		meshRenderer.setMaterial(material);
	}
	
	public final Mesh getMesh()
	{
		return mesh;
	}
	
	public final Material getMaterial()
	{
		return material;
	}
}
