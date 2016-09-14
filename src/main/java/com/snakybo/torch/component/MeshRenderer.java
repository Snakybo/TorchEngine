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
import com.snakybo.torch.graphics.mesh.Mesh;
import com.snakybo.torch.graphics.renderer.MeshRendererInternal;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.util.serialized.SerializedField;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

/**
 * <p>
 * A {@link Mesh} renderer.
 * </p>
 *
 * <p>
 * Renders a mesh with the specified material.
 * </p>
 *
 * @see Mesh
 * @see Material
 *
 * @author Snakybo
 * @since 1.0
 */
public final class MeshRenderer extends Renderer
{
	private MeshRendererInternal meshRenderer;
	
	@Override
	protected final void onCreate()
	{
		super.onCreate();
		
		meshRenderer = new MeshRendererInternal(meshFilter.getMesh());
	}
	
	@Override
	protected final void render()
	{
		meshRenderer.render(GL_TRIANGLES);
	}
	
	@Override
	protected final void onMeshUpdated()
	{
		meshRenderer.destroy();
		meshRenderer = new MeshRendererInternal(meshFilter.getMesh());
	}
	
	@Override
	protected final void onDestroy()
	{
		super.onDestroy();
		
		meshRenderer.destroy();
	}
}
