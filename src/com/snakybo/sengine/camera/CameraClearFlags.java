package com.snakybo.sengine.camera;

/**
 * @author Kevin
 * @since Feb 18, 2016
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
