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

import com.snakybo.torch.asset2.Assets2;
import com.snakybo.torch.event.Events;
import com.snakybo.torch.event.IWindowResizeEvent;
import com.snakybo.torch.graphics.RenderingEngine;
import com.snakybo.torch.graphics.texture.Cubemap;
import com.snakybo.torch.graphics.texture.Texture;
import com.snakybo.torch.graphics.window.Window;
import com.snakybo.torch.util.Rect;
import com.snakybo.torch.util.color.Color;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private static Set<CameraInternal> cameras = new HashSet<>();
	
	private static CameraInternal main;
	private static CameraInternal current;
	
	private IWindowResizeEvent windowResizeEvent;
	
	private CameraClearFlags clearFlags;
	private Color clearColor;
	
	private Vector3f position;
	private Quaternionf rotation;
	
	private Matrix4f projection;
	private Rect viewport;
	
	private float fieldOfView;
	private float zNear;
	private float zFar;
	
	private int depth;
	
	public CameraInternal(Rect viewport, float fieldOfView, float zNear, float zFar, int depth)
	{
		Events.onWindowResize.addListener(windowResizeEvent = () -> updateProjection());
		
		// TODO; Change skybox to default skybox
		Skybox.setTexture(Assets2.load(Cubemap.class, "skybox/skybox.jpg"));
		
		this.clearFlags = CameraClearFlags.SolidColor;
		this.clearColor = Color.BLACK;
		
		this.position = new Vector3f();
		this.rotation = new Quaternionf();
		
		this.viewport = viewport;
		this.fieldOfView = fieldOfView;
		this.zNear = zNear;
		this.zFar = zFar;
		this.depth = depth;
		
		updateProjection();
		
		main = main == null ? this : main;
		cameras.add(this);
	}
	
	public final void render()
	{
		current = this;
		
		RenderingEngine.render(this);
		
		current = null;
	}
	
	public final void destroy()
	{
		Events.onWindowResize.removeListener(windowResizeEvent);
		
		main = main == this ? null : main;
		cameras.remove(this);
	}
	
	private void updateProjection()
	{
		float fovRadians = (float)Math.toRadians(fieldOfView);
		float aspect = (float)Window.getWidth() / (float)Window.getHeight();
		
		Matrix4f projection = new Matrix4f().perspective(fovRadians, aspect, getNearClipPlane(), getFarClipPlane());
		setProjection(projection);
	}
	
	public final void setClearFlags(CameraClearFlags clearFlags)
	{
		this.clearFlags = clearFlags;
	}
	
	public final void setSkybox(Cubemap skybox)
	{
		Skybox.setTexture(skybox);
	}
	
	public final void setClearColor(Color clearColor)
	{
		this.clearColor = clearColor;
	}
	
	public final void setPosition(Vector3f position)
	{
		this.position = position;
	}
	
	public final void setRotation(Quaternionf rotation)
	{
		this.rotation = rotation;
	}
	
	public final void setProjection(Matrix4f projection)
	{
		this.projection = projection;
	}
	
	public final void setViewport(Rect viewport)
	{
		this.viewport = viewport;
		updateProjection();
	}
	
	public final void setFieldOfView(float fieldOfView)
	{
		this.fieldOfView = fieldOfView;
		updateProjection();
	}
	
	public final void setNearClipPlane(float zNear)
	{
		this.zNear = zNear;
		updateProjection();
	}
	
	public final void setFarClipPlane(float zFar)
	{
		this.zFar = zFar;
		updateProjection();
	}
	
	public final void setDepth(int depth)
	{
		this.depth = depth;
	}
	
	public final CameraClearFlags getClearFlags()
	{
		return clearFlags;
	}
	
	public final Cubemap getSkybox()
	{
		return Skybox.getTexture();
	}
	
	public final Color getClearColor()
	{
		return clearColor;
	}
	
	public final Vector3f getPosition()
	{
		return new Vector3f(position);
	}
	
	public final Quaternionf getRotation()
	{
		return new Quaternionf(rotation);
	}
	
	public final Matrix4f getProjection()
	{
		return new Matrix4f(projection);
	}
	
	public final Matrix4f getViewMatrix()
	{
		return new Matrix4f().rotate(rotation).translate(position);
	}
	
	public final Rect getViewport()
	{
		return new Rect(viewport);
	}
	
	public final float getFieldOfView()
	{
		return fieldOfView;
	}
	
	public final float getNearClipPlane()
	{
		return zNear;
	}
	
	public final float getFarClipPlane()
	{
		return zFar;
	}
	
	public final int getDepth()
	{
		return depth;
	}
	
	public static void setMainCamera(CameraInternal camera)
	{
		main = camera;
	}
	
	public static CameraInternal getMainCamera()
	{
		return main;
	}
	
	public static CameraInternal getCurrentCamera()
	{
		return current;
	}
	
	public static Iterable<CameraInternal> getAllCameras()
	{
		return cameras;
	}
	
	public static CameraInternal[] getAllCamerasSorted()
	{
		List<CameraInternal> result = new ArrayList<>(cameras);
		
		Collections.sort(result, (o1, o2) ->
		{
			if(o1.getDepth() == o2.getDepth())
			{
				return 0;
			}
			
			return o1.getDepth() < o2.getDepth() ? -1 : 1;
		});
		
		return result.toArray(new CameraInternal[result.size()]);
	}
}
