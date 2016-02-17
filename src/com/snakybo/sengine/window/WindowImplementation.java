package com.snakybo.sengine.window;

import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwInit;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetErrorCallback;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL.createCapabilities;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;

import com.snakybo.sengine.SEngine;
import com.snakybo.sengine.debug.Logger;

/**
 * @author Kevin
 * @since Feb 14, 2016
 */
public final class WindowImplementation
{
	private static GLFWErrorCallback errorCallback;
	
	public static long window;
	
	static
	{
		Logger.log("Initializing GLFW", "Window");
		
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		
		if(glfwInit() != GL_TRUE)
		{
			Logger.logException(new RuntimeException("Unable to initialize GLFW"), "Window");
		}
	}
	
	private WindowImplementation()
	{
		throw new AssertionError();
	}
	
	/**
	 * Create a window
	 */
	public static void create()
	{
		if(isCreated())
		{
			Logger.logException(new RuntimeException("Already created"), "Window");
			return;
		}
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		
		// TODO: Allow the user to modify the window
		Logger.log("Creating window width=" + 1280 + " height=" + 720 + " title=" + SEngine.getGameName(), "Window");
		window = glfwCreateWindow(1280, 720, SEngine.getGameName(), NULL, NULL);
		if(window == NULL)
		{
			Logger.logException(new RuntimeException("Unable to create GLFW window"), "Window");
			return;
		}
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		createCapabilities();
	}
	
	/**
	 * Update the window
	 */
	public static void update()
	{
		glfwPollEvents();
		glfwSwapBuffers(window);
	}
	
	/**
	 * Destroy the window
	 */
	public static void destroy()
	{
		Logger.log("Destroying window", "Window");
		Logger.log("Terminating GLFW", "Window");
		
		glfwDestroyWindow(window);
		glfwTerminate();
		
		errorCallback.release();
	}
	
	/**
	 * @return Whether or not the window wants to close
	 */
	public static boolean isCloseRequested()
	{
		return glfwWindowShouldClose(window) == 0 ? false : true;
	}
	
	/**
	 * @return Whether or not the window has been created
	 */
	public static boolean isCreated()
	{
		return window != NULL;
	}
}
