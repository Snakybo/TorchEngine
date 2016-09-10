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

package com.snakybo.torch.graphics.gizmo;

import com.snakybo.torch.graphics.mesh.Mesh;
import com.snakybo.torch.graphics.renderer.MeshRendererInternal;
import org.joml.Vector3f;

/**
 * @author Snakybo
 * @since 1.0
 */
final class GizmoShapeSphere
{
	private static final MeshRendererInternal MESH;
	
	static
	{
		Mesh mesh = new Mesh();
		
		int rings = 24;
		int sectors = 48;
		
		final float R = 1.0f / (float)(rings - 1);
		final float S = 1.0f / (float)(sectors - 1);
		
		for(int r = 0; r < rings; r++)
		{
			for(int s = 0; s < sectors; s++)
			{
				float y = (float)Math.sin((-Math.PI / 2) + Math.PI * r * R) * 0.5f;
				float x = (float)(Math.cos(2 * Math.PI * s * S) * Math.sin(Math.PI * r * R)) * 0.5f;
				float z = (float)(Math.sin(2 * Math.PI * s * S) * Math.sin(Math.PI * r * R)) * 0.5f;
				
				mesh.addVertex(new Vector3f(x, y, z));
			}
		}
		
		for(int r = 0; r < rings - 1; r++)
		{
			for(int s = 0; s < sectors - 1; s++)
			{
				mesh.addIndex(r * sectors + s);
				mesh.addIndex(r * sectors + (s + 1));
				mesh.addIndex((r + 1) * sectors + (s + 1));
				mesh.addIndex((r + 1) * sectors + s);
			}
		}
		
		// Create renderer
		MESH = new MeshRendererInternal(mesh);
	}
	
	private GizmoShapeSphere()
	{
		throw new AssertionError();
	}
	
	static void render(int renderMode)
	{
		MESH.render(renderMode);
	}
}
