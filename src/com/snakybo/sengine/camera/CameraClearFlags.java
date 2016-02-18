package com.snakybo.sengine.camera;

/**
 * @author Snakybo
 * @since 1.0
 */
public enum CameraClearFlags
{
	/**
	 * Make the camera render a skybox
	 */
	Skybox,
	
	/**
	 * Make the camera only render the depth component
	 */
	DepthOnly,
	
	/**
	 * Make the camera render a solid color
	 */
	SolidColor,
	
	/**
	 * Don't clear at all
	 */
	NoClear
}
