package com.snakybo.sengine.camera;

import org.joml.Matrix4f;

import com.snakybo.sengine.object.Component;
import com.snakybo.sengine.util.Color;

/**
 * @author Kevin
 * @since Feb 18, 2016
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
	 * @see StandaloneCamera#getClearColor()
	 */
	public final Color getClearColor()
	{
		return camera.getClearColor();
	}
}
