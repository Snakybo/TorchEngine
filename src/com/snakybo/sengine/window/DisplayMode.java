package com.snakybo.sengine.window;

import static org.lwjgl.glfw.GLFW.glfwGetVideoMode;

import org.lwjgl.glfw.GLFWVidMode;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class DisplayMode
{
	private final GLFWVidMode videoMode;
	private final long monitor;
	
	DisplayMode(long monitor, GLFWVidMode videoMode)
	{
		this.monitor = monitor;
		this.videoMode = videoMode;
	}
	
	@Override
	public String toString()
	{
		return    "Width=" + getWidth() +
				", Height=" + getHeight() +
				", Red Bits=" + getRedBits() +
				", Green Bits=" + getGreenBits() +
				", Blue Bits=" + getBlueBits() +
				", Refresh Rate=" + getRefreshRate();
	}
	
	/**
	 * @return Whether or not this is the native {@link DisplayMode} for this {@link Monitor}
	 */
	public final boolean isNativeDisplayMode()
	{
		GLFWVidMode n = glfwGetVideoMode(monitor);
		
		return n.width() == getWidth() &&
				n.height() == getHeight() &&
				n.redBits() == getRedBits() &&
				n.greenBits() == getGreenBits() &&
				n.blueBits() == getBlueBits() &&
				n.refreshRate() == getRefreshRate();
	}
	
	/**
	 * @return The width of this {@link DisplayMode}
	 */
	public final int getWidth()
	{
		return videoMode.width();
	}
	
	/**
	 * @return The height of this {@link DisplayMode}
	 */
	public final int getHeight()
	{
		return videoMode.height();
	}
	
	/**
	 * @return The amount of red bits this {@link DisplayMode} has
	 */
	public final int getRedBits()
	{
		return videoMode.redBits();
	}
	
	/**
	 * @return The amount of green bits this {@link DisplayMode} has
	 */
	public final int getGreenBits()
	{
		return videoMode.greenBits();
	}
	
	/**
	 * @return The amount of blue bits this {@link DisplayMode} has
	 */
	public final int getBlueBits()
	{
		return videoMode.blueBits();
	}
	
	/**
	 * @return The refresh rate of this {@link DisplayMode}
	 */
	public final int getRefreshRate()
	{
		return videoMode.refreshRate();
	}
}
