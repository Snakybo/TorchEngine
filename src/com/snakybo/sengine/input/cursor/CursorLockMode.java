package com.snakybo.sengine.input.cursor;

/**
 * @author Snakybo
 * @since 1.0
 */
public enum CursorLockMode
{
	/**
	 * No restrictions on the cursor
	 */
	None,
	
	/**
	 * Lock the cursor to the center of the window
	 */
	Locked,
	
	/**
	 * Prevent the cursor from leaving the window
	 */
	Confined
}
