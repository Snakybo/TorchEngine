package com.snakybo.sengine.camera;

import org.joml.Matrix4f;

import com.snakybo.sengine.util.Color;

/**
 * @author Kevin
 * @since Feb 18, 2016
 */
public final class StandaloneCamera
{
	private CameraClearFlags clearFlags;
	
	private Matrix4f projection;
	
	private Color clearColor;
	
	/**
	 * Create a new camera
	 * @param projection - The projection of the camera
	 * @param clearFlags - The {@link CameraClearFlags} to use
	 */
	public StandaloneCamera(Matrix4f projection, CameraClearFlags clearFlags)
	{
		this(projection, clearFlags, new Color());
	}
	
	/**
	 * Create a new camera
	 * @param projection - The projection of the camera
	 * @param clearFlags - The {@link CameraClearFlags} to use
	 * @param clearColor - The clear color, only used in {@link CameraClearFlags#SolidColor}
	 */
	public StandaloneCamera(Matrix4f projection, CameraClearFlags clearFlags, Color clearColor)
	{
		this.projection = projection;
		this.clearFlags = clearFlags;
		this.clearColor = clearColor;
	}
	
	/**
	 * Make the camera render now
	 */
	public final void render()
	{
		clear();
	}
	
	/**
	 * Apply the camera's clear setting
	 */
	private void clear()
	{
		switch(clearFlags)
		{
		case Skybox:
			break;
		case SolidColor:
			break;
		case DepthOnly:
			break;
		case NoClear:
			break;
		}
	}
	
	/**
	 * Set the {@link CameraClearFlags} to use
	 * @param clearFlags - The new clear flags
	 */
	public final void setClearFlags(CameraClearFlags clearFlags)
	{
		this.clearFlags = clearFlags;
	}
	
	/**
	 * Set the projection of the camera
	 * @param projection - The new projection
	 */
	public final void setProjection(Matrix4f projection)
	{
		this.projection = projection;
	}
	
	/**
	 * Set the clear color of the camera, only used in {@link CameraClearFlags#SolidColor}
	 * @param clearColor - The new clear color
	 */
	public final void setClearColor(Color clearColor)
	{
		this.clearColor = clearColor;
	}
	
	/**
	 * @return The {@link CameraClearFlags} this camera is using
	 */
	public final CameraClearFlags getClearFlags()
	{
		return clearFlags;
	}
	
	/**
	 * @return The projection of the camera
	 */
	public final Matrix4f getProjection()
	{
		return projection;
	}
	
	/**
	 * @return The clear color of the camera
	 */
	public final Color getClearColor()
	{
		return clearColor;
	}
}
