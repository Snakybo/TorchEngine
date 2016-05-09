// The MIT License(MIT)
//
// Copyright(c) 2016 Kevin Krol
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files (the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in all
// copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
// SOFTWARE.

package com.snakybo.torch.window;

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
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwTerminate;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.system.MemoryUtil.NULL;

import org.lwjgl.glfw.GLFWErrorCallback;

import com.snakybo.torch.TorchGame;
import com.snakybo.torch.debug.Debug;
import com.snakybo.torch.debug.Logger;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.glfw.GLFWCallbacks;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class WindowInternal
{
	private static WindowMode windowMode;
	
	public static long window;
	
	static
	{
		LoggerInternal.log("Initializing GLFW", "Window");
		
		GLFWErrorCallback errorCB = GLFWErrorCallback.createPrint(System.err);
		glfwSetErrorCallback(errorCB);
		
		if(!glfwInit())
		{
			Logger.logException(new RuntimeException("Unable to initialize GLFW"), "WindowInternal");
		}
		
		logGLFWInfo();
	}
	
	private WindowInternal()
	{
		throw new AssertionError();
	}
	
	/**
	 * Create a windowed window
	 * @param width The width of the new window
	 * @param height The height of the new window
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
	 * @param displayMode The {@link DisplayMode} to use
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
	 * @param monitor The {@link Monitor} to display the window on
	 * @param displayMode The {@link DisplayMode} to use 
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
		glfwSwapBuffers(window);
		glfwPollEvents();		
	}
	
	/**
	 * Destroy the window
	 */
	public static void destroy()
	{
		LoggerInternal.log("Terminating GLFW", "WindowInternal");
		
		GLFWCallbacks.destroy();
		
		glfwDestroyWindow(window);
		glfwTerminate();		
		glfwSetErrorCallback(null).free();
	}
	
	/**
	 * Actually create the window
	 * @param width The width of the window
	 * @param height The height of the window
	 * @param monitor The monitor to display the window on, {@code NULL} for anything other than fullscreen windows
	 */
	private static void create(int width, int height, long monitor)
	{
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		
		window = glfwCreateWindow(width, height, TorchGame.getName(), monitor, NULL);
		if(window == NULL)
		{
			Logger.logException(new RuntimeException("Unable to create GLFW window"), "WindowInternal");
			return;
		}
		
		GLFWCallbacks.initialize();
		
		glfwMakeContextCurrent(window);
		glfwShowWindow(window);
		
		glfwSwapInterval(0);
	}

	/**
	 * Log information about GLFW
	 */
	private static void logGLFWInfo()
	{
		if(Debug.LOG_LIBRARY_INFO)
		{
			LoggerInternal.log("Version: " + glfwGetVersionString(), "GLFW");
		}
	}
	
	/**
	 * @return Whether or not the window wants to close
	 */
	public static boolean isCloseRequested()
	{
		return glfwWindowShouldClose(window);
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
