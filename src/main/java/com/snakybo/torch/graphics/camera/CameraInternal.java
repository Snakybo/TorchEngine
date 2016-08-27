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

import com.snakybo.torch.graphics.color.Color;
import com.snakybo.torch.graphics.renderer.Renderer;
import com.snakybo.torch.graphics.texture.Texture;
import com.snakybo.torch.object.Transform;
import com.snakybo.torch.scene.SceneInternal;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class CameraInternal
{
	private static List<CameraInternal> cameras = new ArrayList<>();
	private static CameraInternal current;
	private static CameraInternal main;
	
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
		
		cameras.add(this);
		
		if(main == null)
		{
			main = this;
		}
	}
	
	public final void destroy()
	{
		cameras.remove(this);
		
		if(main == this)
		{
			main = cameras.size() > 0 ? cameras.get(0) : null;
		}
	}
	
	public final void render()
	{
		current = this;
		
		switch(clearFlags)
		{
		case Skybox:
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			break;
		case SolidColor:
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			glClearColor(clearColor.getRed(), clearColor.getGreen(), clearColor.getBlue(), clearColor.getAlpha());
			break;
		case DepthOnly:
			glClear(GL_DEPTH_BUFFER_BIT);
			break;
		}
		
		SceneInternal.getAllInitializedGameObjects().forEach(Renderer::render);
		
		if(clearFlags == CameraClearFlags.Skybox)
		{
			skybox.render();
		}
		
		current = null;
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
		return new Matrix4f().translate(transform.getPosition()).rotate(transform.getRotation());
	}
	
	public final Transform getTransform()
	{
		return transform;
	}
	
	public final Color getClearColor()
	{
		return clearColor;
	}
	
	public static void setMainCamera(CameraInternal camera)
	{
		main = camera;
	}
	
	public static Iterable<CameraInternal> getCameras()
	{
		return cameras;
	}
	
	public static CameraInternal getMainCamera()
	{
		return main;
	}
	
	public static CameraInternal getCurrentCamera()
	{
		return current;
	}
}
