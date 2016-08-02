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

import com.snakybo.torch.Game;
import com.snakybo.torch.debug.LoggerInternal;
import com.snakybo.torch.input.joystick.JoystickController;
import com.snakybo.torch.input.keyboard.KeyboardController;
import com.snakybo.torch.input.mouse.Mouse;
import com.snakybo.torch.input.mouse.MouseController;
import com.snakybo.torch.monitor.DisplayMode;
import com.snakybo.torch.object.GameObject;
import com.snakybo.torch.opengl.OpenGL;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.Callbacks;

import java.nio.IntBuffer;

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
import static org.lwjgl.glfw.GLFW.glfwFocusWindow;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwMakeContextCurrent;
import static org.lwjgl.glfw.GLFW.glfwPollEvents;
import static org.lwjgl.glfw.GLFW.glfwSetCharCallback;
import static org.lwjgl.glfw.GLFW.glfwSetCursorEnterCallback;
import static org.lwjgl.glfw.GLFW.glfwSetScrollCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowFocusCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowIconifyCallback;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwShowWindow;
import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;
import static org.lwjgl.glfw.GLFW.glfwWindowHint;
import static org.lwjgl.glfw.GLFW.glfwWindowShouldClose;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Window
{
	private static DisplayMode displayMode;
	
	private static long nativeId;
	
	private static boolean vsyncEnabled;
	
	private Window()
	{
		throw new AssertionError();
	}
	
	/**
	 * Create a new window.
	 * @param displayMode The window properties.
	 * @param windowMode The window mode.
	 */
	public static void create(DisplayMode displayMode, WindowMode windowMode)
	{
		if(Window.displayMode != null || nativeId != NULL)
		{
			destroy();
		}
		
		LoggerInternal.log("Creating window: " + displayMode, "Window");
		
		Window.displayMode = displayMode;
		
		long monitor = NULL;
		
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GL_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);
		
		if(windowMode == WindowMode.Borderless)
		{
			glfwWindowHint(GLFW_RED_BITS, displayMode.getBitsPerPixel());
			glfwWindowHint(GLFW_GREEN_BITS, displayMode.getBitsPerPixel());
			glfwWindowHint(GLFW_BLUE_BITS, displayMode.getBitsPerPixel());
			glfwWindowHint(GLFW_REFRESH_RATE, displayMode.getFrequency());
			
			glfwWindowHint(GLFW_DECORATED, GL_FALSE);
		}
		else if(windowMode == WindowMode.Fullscreen)
		{
			monitor = displayMode.getMonitor().getNativeId();
		}
		
		nativeId = glfwCreateWindow(displayMode.getWidth(), displayMode.getHeight(), Game.getName(), monitor, NULL);
		if(nativeId == NULL)
		{
			throw new RuntimeException("Unable to create GLFW window");
		}
		
		glfwSetWindowFocusCallback(nativeId, (window, focus) -> GameObject.notifyAll("onWindowFocus", focus));
		glfwSetWindowIconifyCallback(nativeId, (window, iconified) -> GameObject.notifyAll("onWindowIconify", iconified));
		
		glfwSetCharCallback(nativeId, (window, codepoint) -> GameObject.notifyAll("onCharPressed", (char)codepoint));
		
		glfwSetCursorEnterCallback(nativeId, (window, entered) -> GameObject.notifyAll("onCursorEnter", entered));
		glfwSetScrollCallback(nativeId, (window, x, y) -> Mouse.setScrollDelta(new Vector2f((float)x, (float)y)));
		
		glfwMakeContextCurrent(nativeId);
		glfwShowWindow(nativeId);
		setVSyncEnabled(true);
		
		OpenGL.create();
		
		KeyboardController.create();
		MouseController.create();
		JoystickController.create();
	}
	
	/**
	 * Update the window.
	 */
	public static void update()
	{
		glfwSwapBuffers(nativeId);
		glfwPollEvents();
	}
	
	/**
	 * Destroy the active window.
	 */
	public static void destroy()
	{
		LoggerInternal.log("Destroying window", "Window");
		
		Callbacks.glfwFreeCallbacks(nativeId);
		
		glfwDestroyWindow(nativeId);
		
		displayMode = null;
		nativeId = NULL;
	}
	
	/**
	 * Bring the window to the front and give it focus.
	 */
	public static void focus()
	{
		glfwFocusWindow(nativeId);
	}
	
	/**
	 * Check whether or not the window wants to close.
	 * @return Whether or not the window wants to close.
	 */
	public static boolean isCloseRequested()
	{
		return glfwWindowShouldClose(nativeId);
	}
	
	/**
	 * Check whether or not vsync is enabled.
	 * @return Whether or not vsync is enabled.
	 */
	public static boolean isVSyncEnabled()
	{
		return vsyncEnabled;
	}
	
	/**
	 * Set the size of the window.
	 * @param size The new size of the window.
	 */
	public static void setSize(Vector2f size)
	{
		glfwSetWindowSize(nativeId, (int)size.x, (int)size.y);
	}
	
	/**
	 * Enable or disable vsync.
	 * @param enabled Whether or not to enable vsync
	 */
	public static void setVSyncEnabled(boolean enabled)
	{
		Window.vsyncEnabled = enabled;
		
		glfwSwapInterval(enabled ? 1 : 0);
	}
	
	/**
	 * Set the size of the window.
	 */
	public static Vector2f getSize()
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		
		glfwGetWindowSize(nativeId, width, height);
		return new Vector2f(width.get(), height.get());
	}
	
	/**
	 * Get the aspect ratio of the window.
	 * @return The aspect ratio of the window.
	 */
	public static float getAspectRatio()
	{
		Vector2f size = getSize();
		return size.x / size.y;
	}
	
	/**
	 * Get the center of the window.
	 * @return The center of the window.
	 */
	public static Vector2f getCenter()
	{
		return getSize().mul(0.5f);
	}
	
	/**
	 * Get the native ID of the window.
	 * @return The native ID of the window.
	 */
	public static long getNativeId()
	{
		return nativeId;
	}
}
