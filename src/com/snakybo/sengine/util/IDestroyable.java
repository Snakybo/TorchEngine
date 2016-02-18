package com.snakybo.sengine.util;

/**
 * @author Snakybo
 * @since 1.0
 */
public interface IDestroyable
{
	/**
	 * Destroy the object, this method is not called automatically.
	 * You'll have to call it manually when you want to destroy the object
	 */
	void destroy();
}
