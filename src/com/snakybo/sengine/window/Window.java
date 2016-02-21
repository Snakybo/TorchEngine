package com.snakybo.sengine.window;

import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;

import java.nio.IntBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Window
{	
	private Window()
	{
		throw new AssertionError();
	}
	
	/**
	 * Set whether or not the {@link Window} shoud be fullscreen (or borderless fullscreen)
	 * @param fullscreen - Whether or not to be fullscreen
	 * @param borderless - Should the window be borderless fullscreen?
	 */
	public static void setFullscreen(boolean fullscreen, boolean borderless)
	{
		setFullscreen(fullscreen, borderless, Monitor.getPrimaryMonitor());
	}
	
	/**
	 * Set whether or not the {@link Window} shoud be fullscreen (or borderless fullscreen)
	 * @param fullscreen - Whether or not to be fullscreen
	 * @param borderless - Should the window be borderless fullscreen?
	 * @param monitor - The monitor to display the fullscreen window on
	 */
	public static void setFullscreen(boolean fullscreen, boolean borderless, Monitor monitor)
	{
		setFullscreen(fullscreen, borderless, monitor, monitor.getNativeDisplayMode());
	}
	
	/**
	 * Set whether or not the {@link Window} shoud be fullscreen (or borderless fullscreen)
	 * @param fullscreen - Whether or not to be fullscreen
	 * @param borderless - Should the window be borderless fullscreen?
	 * @param monitor - The {@link Monitor} to display the fullscreen {@link Window} on
	 * @param displayMode - The {@link DisplayMode} to use
	 */
	public static void setFullscreen(boolean fullscreen, boolean borderless, Monitor monitor, DisplayMode displayMode)
	{
		if(fullscreen && !borderless)
		{
			WindowInternal.createFullscreen(monitor, displayMode);
		}
		else if(fullscreen && borderless)
		{
			WindowInternal.createBorderlessFullscreen(displayMode);
		}
		else if(!fullscreen)
		{
			WindowInternal.createWindowed(1280, 720);
		}
	}
	
	/**
	 * Set the width of the window
	 * @param width - The new width
	 */
	public static void setWidth(int width)
	{
		glfwSetWindowSize(WindowInternal.window, width, getHeight());
	}
	
	/**
	 * Set the height of the window
	 * @param height - The new height
	 */
	public static void setHeight(int height)
	{
		glfwSetWindowSize(WindowInternal.window, getWidth(), height);
	}
	
	/**
	 * @return The {@link WindowMode}
	 */
	public static WindowMode getWindowMode()
	{
		return WindowInternal.getWindowMode();
	}

	/**
	 * @return The center of the window
	 */
	public static Vector2f getCenter()
	{
		return new Vector2f(getWidth() / 2f, getHeight() / 2f);
	}
	
	/**
	 * @return The aspect ratio of the window
	 */
	public static float getAspectRatio()
	{
		return (float)getWidth() / (float)getHeight();
	}
	
	/**
	 * @return The width of the window
	 */
	public static int getWidth()
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		
		glfwGetWindowSize(WindowInternal.window, width, height);
		return width.get();
	}
	
	/**
	 * @return The height of the window
	 */
	public static int getHeight()
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		
		glfwGetWindowSize(WindowInternal.window, width, height);
		return height.get();
	}
}
