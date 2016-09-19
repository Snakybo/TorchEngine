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

package com.snakybo.torch.graphics.camera;

import com.snakybo.torch.asset.Assets;
import com.snakybo.torch.component.Camera;
import com.snakybo.torch.graphics.material.Material;
import com.snakybo.torch.graphics.material.MaterialInternal;
import com.snakybo.torch.graphics.mesh.Mesh;
import com.snakybo.torch.graphics.renderer.MeshRendererInternal;
import com.snakybo.torch.graphics.shader.ShaderInternal;
import com.snakybo.torch.graphics.texture.Texture;
import com.snakybo.torch.object.Transform;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Skybox
{
	private MeshRendererInternal meshRenderer;
	private Transform transform;
	private Material material;
	
	public Skybox()
	{
		transform = new Transform();
		transform.setScale(new Vector3f(50));
		
		material = new Material("unlit.glsl");
		meshRenderer = new MeshRendererInternal(Assets.load(Mesh.class, "torch_internal/skybox.obj"));
	}
	
	public final void render()
	{
		Vector3f position = new Vector3f();
		
		if(Camera.getInstance() != null)
		{
			position = Camera.getInstance().getTransform().getPosition();
		}
		
		transform.setPosition(position);
		
		ShaderInternal.bind(material.getShader());
		
		MaterialInternal.updateBuiltInUniforms(material, transform);
		MaterialInternal.update(material);
		
		meshRenderer.render(GL_TRIANGLES);
		
		ShaderInternal.unbind();
	}
	
	public final void setTexture(Texture texture)
	{
		material.setTexture("diffuse", texture);
	}
}
