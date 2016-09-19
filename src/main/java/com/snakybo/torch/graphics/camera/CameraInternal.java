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

import com.snakybo.torch.graphics.RenderingEngine;
import com.snakybo.torch.graphics.texture.Texture;
import com.snakybo.torch.object.Transform;
import com.snakybo.torch.util.color.Color;
import org.joml.Matrix4f;

/**
 * <p>
 * Used internally by the engine.
 * </p>
 *
 * @author Snakybo
 * @since 1.0
 */
public final class CameraInternal
{
	private static CameraInternal instance;
	
	private CameraClearFlags clearFlags;
	
	private Matrix4f projection;	
	private Transform transform;
	private Color clearColor;
	private Skybox skybox;
	
	public CameraInternal(Matrix4f projection, CameraClearFlags clearFlags)
	{
		this.projection = projection;
		this.clearFlags = clearFlags;
		
		clearColor = new Color(0, 0, 0, 0);
		skybox = new Skybox();
		
		transform = new Transform();
		
		instance = this;
	}
	
	public final void render()
	{
		RenderingEngine.render(this);
	}
	
	public final void destroy()
	{
		instance = null;
	}
	
	public final void setClearFlags(CameraClearFlags clearFlags)
	{
		this.clearFlags = clearFlags;
	}
	
	public final void setProjection(Matrix4f projection)
	{
		this.projection = projection;
	}
	
	public final void setTransform(Transform transform)
	{
		this.transform = transform;
	}
	
	public final void setSkybox(Texture texture)
	{
		skybox.setTexture(texture);
	}
	
	public final void setClearColor(Color clearColor)
	{
		this.clearColor = clearColor;
	}
	
	public final CameraClearFlags getClearFlags()
	{
		return clearFlags;
	}
	
	public final Matrix4f getProjectionMatrix()
	{
		return new Matrix4f(projection);
	}
	
	public final Matrix4f getViewMatrix()
	{
		return new Matrix4f().rotate(transform.getRotation()).translate(transform.getPosition());
	}
	
	public final Skybox getSkybox()
	{
		return skybox;
	}
	
	public final Transform getTransform()
	{
		return transform;
	}
	
	public final Color getClearColor()
	{
		return clearColor;
	}
	
	public static CameraInternal getInstance()
	{
		return instance;
	}
}
