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
final class GizmoShapeCube
{
	private static final MeshRendererInternal MESH;
	
	static
	{
		Mesh mesh = new Mesh();
		
		mesh.addVertex(new Vector3f(-0.5f, -0.5f, -0.5f));  // 0 Bottom left front
		mesh.addVertex(new Vector3f(-0.5f,  0.5f, -0.5f));  // 1 Top left front
		mesh.addVertex(new Vector3f( 0.5f, -0.5f, -0.5f));  // 2 Bottom right front
		mesh.addVertex(new Vector3f( 0.5f,  0.5f, -0.5f));  // 3 Top right front
		mesh.addVertex(new Vector3f(-0.5f, -0.5f,  0.5f));  // 4 Bottom left back
		mesh.addVertex(new Vector3f(-0.5f,  0.5f,  0.5f));  // 5 Top left back
		mesh.addVertex(new Vector3f( 0.5f, -0.5f,  0.5f));  // 6 Bottom right back
		mesh.addVertex(new Vector3f( 0.5f,  0.5f,  0.5f));  // 7 Top right back
		
		// Front face
		mesh.addFace(0, 1, 2);
		mesh.addFace(1, 2, 3);
		
		// Back face
		mesh.addFace(4, 5, 6);
		mesh.addFace(5, 6, 7);
		
		// Left face
		mesh.addFace(4, 5, 1);
		mesh.addFace(0, 4, 1);
		
		// Right face
		mesh.addFace(6, 7, 3);
		mesh.addFace(2, 6, 3);
		
		// Top face
		mesh.addFace(1, 5, 3);
		mesh.addFace(5, 7, 3);
		
		// Bottom face
		mesh.addFace(0, 4, 2);
		mesh.addFace(4, 6, 2);
		
		// Create renderer
		MESH = new MeshRendererInternal(mesh);
	}
	
	private GizmoShapeCube()
	{
		throw new AssertionError();
	}
	
	static void render(int renderMode)
	{
		MESH.render(renderMode);
	}
}
