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

package com.snakybo.sengine.camera;

import java.util.HashSet;
import java.util.Set;

import org.joml.Matrix4f;

import com.snakybo.sengine.object.Component;
import com.snakybo.sengine.util.Color;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Camera extends Component
{
	private static Set<Camera> cameras = new HashSet<Camera>();
	
	private final StandaloneCamera standaloneCamera;
	
	/**
	 * @see StandaloneCamera#StandaloneCamera(Matrix4f, CameraClearFlags)
	 */
	public Camera(Matrix4f projection, CameraClearFlags clearFlags)
	{
		this(projection, clearFlags, new Color());
	}
	
	/**
	 * @see StandaloneCamera#StandaloneCamera(Matrix4f, CameraClearFlags, Color)
	 */
	public Camera(Matrix4f projection, CameraClearFlags clearFlags, Color clearColor)
	{
		standaloneCamera = new StandaloneCamera(projection, clearFlags, clearColor);
	}
	
	@Override
	protected final void start()
	{
		cameras.add(this);
		standaloneCamera.setTransform(getTransform());
	}
	
	@Override
	protected void destroy()
	{
		cameras.remove(this);
		standaloneCamera.destroy();
	}
	
	/**
	 * @see StandaloneCamera#render()
	 */
	public final void render()
	{
		standaloneCamera.render();
	}
	
	/**
	 * @see StandaloneCamera#setClearFlags(CameraClearFlags)
	 */
	public final void setClearFlags(CameraClearFlags clearFlags)
	{
		standaloneCamera.setClearFlags(clearFlags);
	}
	
	/**
	 * @see StandaloneCamera#setProjection(Matrix4f)
	 */
	public final void setProjection(Matrix4f projection)
	{
		standaloneCamera.setProjection(projection);
	}
	
	/**
	 * @see StandaloneCamera#setClearColor(Color)
	 */
	public final void setClearColor(Color clearColor)
	{
		standaloneCamera.setClearColor(clearColor);
	}
	
	/**
	 *	@see StandaloneCamera#getClearFlags()
	 */
	public final CameraClearFlags getClearFlags()
	{
		return standaloneCamera.getClearFlags();
	}
	
	/**
	 * @see StandaloneCamera#getProjection()
	 */
	public final Matrix4f getProjection()
	{
		return standaloneCamera.getProjection();
	}
	
	/**
	 * @see StandaloneCamera#getViewProjection()
	 */
	public final Matrix4f getViewProjection()
	{
		return standaloneCamera.getViewProjection();
	}
	
	/**
	 * @see StandaloneCamera#getClearColor()
	 */
	public final Color getClearColor()
	{
		return standaloneCamera.getClearColor();
	}
	
	/**
	 * @return A collection of all cameras
	 */
	public static Iterable<Camera> getCameras()
	{
		return cameras;
	}
	
	/**
	 * Get the current camera, this will return {@code null} if the engine is rendering a {@link StandaloneCamera}
	 * @return The current camera or null
	 */
	public static Camera getCurrentCamera()
	{
		for(Camera camera : cameras)
		{
			if(camera.standaloneCamera == StandaloneCamera.getCurrentCamera())
			{
				return camera;
			}
		}
		
		return null;
	}
}
