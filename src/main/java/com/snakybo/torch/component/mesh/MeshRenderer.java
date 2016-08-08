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

import com.snakybo.torch.mesh.Material;
import com.snakybo.torch.mesh.MeshRendererInternal;
import com.snakybo.torch.model.Model;
import com.snakybo.torch.object.Component;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class MeshRenderer extends Component
{
	private MeshRendererInternal meshRenderer;
	private Material material;
	
	public MeshRenderer(Model model, Material material)
	{
		this.meshRenderer = new MeshRendererInternal(model, material);
		this.material = material;
	}
	
	@Override
	protected void start()
	{
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
}
