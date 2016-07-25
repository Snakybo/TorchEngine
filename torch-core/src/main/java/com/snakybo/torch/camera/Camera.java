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

import com.snakybo.torch.color.Color;
import com.snakybo.torch.object.Component;
import org.joml.Matrix4f;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Camera extends Component
{
	private static Set<Camera> cameras = new HashSet<>();
	
	private final CameraInternal camera;
	
	/**
	 * Create a new camera.
	 * @param projection The projection of the camera.
	 * @param clearFlags The {@link CameraClearFlags} to use.
	 */
	public Camera(Matrix4f projection, CameraClearFlags clearFlags)
	{
		this(projection, clearFlags, new Color());
	}
	
	/**
	 * Create a new camera.
	 * @param projection The projection of the camera.
	 * @param clearFlags The {@link CameraClearFlags} to use.
	 * @param clearColor The clear color, only used in {@link CameraClearFlags#SolidColor}.
	 */
	public Camera(Matrix4f projection, CameraClearFlags clearFlags, Color clearColor)
	{
		camera = new CameraInternal(projection, clearFlags, clearColor);
	}
	
	@Override
	protected final void start()
	{
		cameras.add(this);
		camera.setTransform(getTransform());
	}
	
	@Override
	protected void destroy()
	{
		cameras.remove(this);
		camera.destroy();
	}
	
	/**
	 * Make the camera render now.
	 */
	public final void render()
	{
		camera.render();
	}
	
	/**
	 * Set the {@link CameraClearFlags} to use.
	 * @param clearFlags The new clear flags.
	 */
	public final void setClearFlags(CameraClearFlags clearFlags)
	{
		camera.setClearFlags(clearFlags);
	}
	
	/**
	 * Set the projection of the camera.
	 * @param projection The new projection.
	 */
	public final void setProjection(Matrix4f projection)
	{
		camera.setProjection(projection);
	}
	
	/**
	 * Set the clear color of the camera, only used in {@link CameraClearFlags#SolidColor}.
	 * @param clearColor The new clear color.
	 */
	public final void setClearColor(Color clearColor)
	{
		camera.setClearColor(clearColor);
	}
	
	/**
	 * Get the {@link CameraClearFlags} this camera is using.
	 * @return The {@link CameraClearFlags} this camera is using.
	 */
	public final CameraClearFlags getClearFlags()
	{
		return camera.getClearFlags();
	}
	
	/**
	 * Get the projection of this camera.
	 * @return The projection of the camera.
	 */
	public final Matrix4f getProjection()
	{
		return camera.getProjection();
	}
	
	/**
	 * Get the view projection of this camera.
	 * @return The view projection of the camera.
	 */
	public final Matrix4f getViewProjection()
	{
		return camera.getViewProjection();
	}
	
	/**
	 * Get the clear color of this camera.
	 * @return The clear color of the camera.
	 */
	public final Color getClearColor()
	{
		return camera.getClearColor();
	}
	
	/**
	 * Get all cameras.
	 * @return All cameras.
	 */
	public static Iterable<Camera> getCameras()
	{
		return cameras;
	}
	
	/**
	 * Get the current camera, this will return {@code null} if the engine is rendering a {@link CameraInternal}.
	 * @return The current camera or null.
	 */
	public static Camera getCurrentCamera()
	{
		for(Camera camera : cameras)
		{
			if(camera.camera == CameraInternal.getCurrentCamera())
			{
				return camera;
			}
		}
		
		return null;
	}
}
