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

package com.snakybo.torch.renderer;

import org.joml.Vector3f;

import com.snakybo.torch.camera.Camera;
import com.snakybo.torch.mesh.Material;
import com.snakybo.torch.mesh.Mesh;
import com.snakybo.torch.mesh.MeshRenderer;
import com.snakybo.torch.object.Component;
import com.snakybo.torch.texture.Texture;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Skybox extends Component
{
	private Material material;
	
	public Skybox(Texture texture)
	{
		material = new Material("./src/test/resources/shaders/skybox.glsl");
		material.setTexture("skybox", texture);
	}
	
	@Override
	protected void start()
	{
		addComponent(new Mesh("./src/test/resources/skybox.obj"));
		addComponent(new MeshRenderer(material));
		
		getTransform().getLocalScale().set(20);
	}
	
	@Override
	protected void postUpdate()
	{
		Vector3f position = new Vector3f();
		
		if(Camera.getCurrentCamera() != null)
		{
			position = Camera.getCurrentCamera().getTransform().getPosition();
		}
		
		getTransform().getPosition().set(position);
	}
}
