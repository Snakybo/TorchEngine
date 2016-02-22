package com.snakybo.sengine.window;

import static org.lwjgl.glfw.GLFW.GLFW_BLUE_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_DECORATED;
import static org.lwjgl.glfw.GLFW.GLFW_GREEN_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_RED_BITS;
import static org.lwjgl.glfw.GLFW.GLFW_REFRESH_RATE;
import static org.lwjgl.glfw.GLFW.GLFW_RESIZABLE;
import static org.lwjgl.glfw.GLFW.GLFW_VISIBLE;
import static org.lwjgl.glfw.GLFW.glfwCreateWindow;
import static org.lwjgl.glfw.GLFW.glfwDefaultWindowHints;
import static org.lwjgl.glfw.GLFW.glfwDestroyWindow;
import static org.lwjgl.glfw.GLFW.glfwGetVersionString;
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
import static org.lwjgl.opengl.GL11.GL_EXTENSIONS;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_RENDERER;
import static org.lwjgl.opengl.GL11.GL_TRUE;
import static org.lwjgl.opengl.GL11.GL_VENDOR;
import static org.lwjgl.opengl.GL11.GL_VERSION;
import static org.lwjgl.opengl.GL11.glGetString;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;

import com.snakybo.sengine.SEngine;
import com.snakybo.sengine.debug.Logger;
import com.snakybo.sengine.debug.LoggerInternal;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class WindowInternal
{
	private static GLFWErrorCallback errorCallback;
	
	private static WindowMode windowMode;
	
	public static long window;
	
	private static boolean loggedOGL;
	
	static
	{
		LoggerInternal.log("Initializing GLFW", "Window");
		
		glfwSetErrorCallback(errorCallback = GLFWErrorCallback.createPrint(System.err));
		
		if(glfwInit() != GL_TRUE)
		{
			Logger.logException(new RuntimeException("Unable to initialize GLFW"), "Window");
		}
		
		logGLFWInfo();
	}
	
	private WindowInternal()
	{
		throw new AssertionError();
	}
	
	/**
	 * Create a windowed window
	 * @param width - The width of the new window
	 * @param height - The height of the new window
	 */
	public static void createWindowed(int width, int height)
	{
		if(isCreated())
		{
			glfwDestroyWindow(window);
		}
		
		windowMode = WindowMode.Windowed;
		
		glfwDefaultWindowHints();
		create(width, height, NULL);
	}
	
	/**
	 * Create a borderless fullscreen window
	 * @param displayMode - The {@link DisplayMode} to use
	 */
	public static void createBorderlessFullscreen(DisplayMode displayMode)
	{
		if(isCreated())
		{
			glfwDestroyWindow(window);
		}
		
		windowMode = WindowMode.Borderless;
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_RED_BITS, displayMode.getRedBits());
		glfwWindowHint(GLFW_GREEN_BITS, displayMode.getGreenBits());
		glfwWindowHint(GLFW_BLUE_BITS, displayMode.getBlueBits());
		glfwWindowHint(GLFW_REFRESH_RATE, displayMode.getRefreshRate());
		
		glfwWindowHint(GLFW_DECORATED, GL_FALSE);
		
		create(displayMode.getWidth(), displayMode.getHeight(), NULL);
	}
	
	/**
	 * Create a fullscreen window
	 * @param monitor - The {@link Monitor} to display the window on
	 * @param displayMode - The {@link DisplayMode} to use 
	 */
	public static void createFullscreen(Monitor monitor, DisplayMode displayMode)
	{
		if(isCreated())
		{
			glfwDestroyWindow(window);
		}
		
		windowMode = WindowMode.Fullscreen;
		
		glfwDefaultWindowHints();
		create(displayMode.getWidth(), displayMode.getHeight(), monitor.id);
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
		LoggerInternal.log("Terminating GLFW", "Window");
		
		glfwDestroyWindow(window);
		glfwTerminate();
		
		errorCallback.release();
	}
	
	/**
	 * Actually create the window
	 * @param width - The width of the window
	 * @param height - The height of the window
	 * @param monitor - The monitor to display the window on, {@code NULL} for anything other than fullscreen windows
	 */
	private static void create(int width, int height, long monitor)
	{
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		
		window = glfwCreateWindow(width, height, SEngine.getGameName(), monitor, NULL);
		if(window == NULL)
		{
			Logger.logException(new RuntimeException("Unable to create GLFW window"), "Window");
			return;
		}
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		createCapabilities();
		
		logOpenGLInfo();
	}

	/**
	 * Log information about GLFW
	 */
	private static void logGLFWInfo()
	{
		LoggerInternal.log("Version: " + glfwGetVersionString(), "GLFW");
	}
	
	/**
	 * Log information about OpenGL
	 */
	private static void logOpenGLInfo()
	{
		if(!loggedOGL)
		{
			LoggerInternal.log("Vendor: " + glGetString(GL_VENDOR), "OpenGL");
			LoggerInternal.log("Renderer: " + glGetString(GL_RENDERER), "OpenGL");
			LoggerInternal.log("Version: " + glGetString(GL_VERSION), "OpenGL");
			LoggerInternal.log("Extensions: " + glGetString(GL_EXTENSIONS), "OpenGL");
			
			loggedOGL = true;
		}
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
	
	/**
	 * @return The {@link WindowMode}
	 */
	public static WindowMode getWindowMode()
	{
		return windowMode;
	}
}
