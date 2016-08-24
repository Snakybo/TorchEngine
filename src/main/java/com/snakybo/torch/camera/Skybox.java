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

package com.snakybo.torch.camera;

import com.snakybo.torch.asset.Assets;
import com.snakybo.torch.component.Camera;
import com.snakybo.torch.material.Material;
import com.snakybo.torch.mesh.Mesh;
import com.snakybo.torch.object.Transform;
import com.snakybo.torch.renderer.MeshRendererInternal;
import com.snakybo.torch.texture.Texture;
import org.joml.Vector3f;

/**
 * <p>
 * The skybox of a camera, handles all rendering of the skybox.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
final class Skybox
{
	private MeshRendererInternal meshRenderer;
	private Transform transform;
	private Material material;
	
	public Skybox()
	{
		transform = new Transform();
		transform.setLocalScale(new Vector3f(50));
		
		material = new Material("unlit.glsl");
		material.setTransform(transform);
		
		meshRenderer = new MeshRendererInternal(Assets.load(Mesh.class, "skybox.obj"), material);
		meshRenderer.create();
	}
	
	/**
	 * Render the skybox, should happen after the camera has finished rendering the scene.
	 */
	public final void render()
	{
		Vector3f position = new Vector3f();
		
		if(Camera.getMainCamera() != null)
		{
			position = Camera.getMainCamera().getTransform().getPosition();
		}
		
		transform.setLocalPosition(position);
		meshRenderer.render();
	}
	
	/**
	 * Set the texture of the skybox.
	 * @param texture The new texture.
	 */
	public final void setTexture(Texture texture)
	{
		material.setTexture("diffuse", texture);
	}
}
