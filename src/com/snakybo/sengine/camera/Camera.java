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

import org.joml.Matrix4f;

import com.snakybo.sengine.object.Component;
import com.snakybo.sengine.util.Color;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Camera extends Component
{
	private final StandaloneCamera camera;
	
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
		camera = new StandaloneCamera(projection, clearFlags, clearColor);
	}
	
	@Override
	protected final void start()
	{
		camera.setTransform(getTransform());
	}
	
	/**
	 * @see StandaloneCamera#render()
	 */
	public final void render()
	{
		camera.render();
	}
	
	/**
	 * @see StandaloneCamera#setClearFlags(CameraClearFlags)
	 */
	public final void setClearFlags(CameraClearFlags clearFlags)
	{
		camera.setClearFlags(clearFlags);
	}
	
	/**
	 * @see StandaloneCamera#setProjection(Matrix4f)
	 */
	public final void setProjection(Matrix4f projection)
	{
		camera.setProjection(projection);
	}
	
	/**
	 * @see StandaloneCamera#setClearColor(Color)
	 */
	public final void setClearColor(Color clearColor)
	{
		camera.setClearColor(clearColor);
	}
	
	/**
	 *	@see StandaloneCamera#getClearFlags()
	 */
	public final CameraClearFlags getClearFlags()
	{
		return camera.getClearFlags();
	}
	
	/**
	 * @see StandaloneCamera#getProjection()
	 */
	public final Matrix4f getProjection()
	{
		return camera.getProjection();
	}
	
	/**
	 * @see StandaloneCamera#getViewProjection()
	 */
	public final Matrix4f getViewProjection()
	{
		return camera.getViewProjection();
	}
	
	/**
	 * @see StandaloneCamera#getClearColor()
	 */
	public final Color getClearColor()
	{
		return camera.getClearColor();
	}
}
