package com.snakybo.sengine.window;

import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;

import java.nio.IntBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

/**
 * @author Kevin
 * @since Feb 13, 2016
 */
public final class Window
{
	private Window()
	{
		throw new AssertionError();
	}
	
	/**
	 * Set the width of the window
	 * @param width - The new width
	 */
	public static void setWidth(int width)
	{
		glfwSetWindowSize(WindowImplementation.window, width, getHeight());
	}
	
	/**
	 * Set the height of the window
	 * @param height - The new height
	 */
	public static void setHeight(int height)
	{
		glfwSetWindowSize(WindowImplementation.window, getWidth(), height);
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
		
		glfwGetWindowSize(WindowImplementation.window, width, height);
		return width.get();
	}
	
	/**
	 * @return The height of the window
	 */
	public static int getHeight()
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		
		glfwGetWindowSize(WindowImplementation.window, width, height);
		return height.get();
	}
}
