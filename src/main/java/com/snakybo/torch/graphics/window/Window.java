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

package com.snakybo.torch.graphics.window;

import com.snakybo.torch.graphics.monitor.DisplayMode;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.glfwFocusWindow;
import static org.lwjgl.glfw.GLFW.glfwGetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwIconifyWindow;
import static org.lwjgl.glfw.GLFW.glfwRestoreWindow;
import static org.lwjgl.glfw.GLFW.glfwSetWindowSize;
import static org.lwjgl.glfw.GLFW.glfwSwapInterval;

/**
 * @author Snakybo
 * @since 1.0
 */
public final class Window
{
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
		WindowInternal.create(displayMode, windowMode);
	}
	
	/**
	 * Bring the window to the front and give it focus.
	 */
	public static void focus()
	{
		glfwFocusWindow(WindowInternal.getNativeId());
	}
	
	public static void restore()
	{
		glfwRestoreWindow(WindowInternal.getNativeId());
	}
	
	public static void iconify()
	{
		glfwIconifyWindow(WindowInternal.getNativeId());
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
		glfwSetWindowSize(WindowInternal.getNativeId(), (int)size.x, (int)size.y);
	}
	
	/**
	 * Enable or disable vsync.
	 * @param enabled Whether or not to enable vsync
	 */
	public static void setVSyncEnabled(boolean enabled)
	{
		vsyncEnabled = enabled;
		
		glfwSwapInterval(enabled ? 1 : 0);
	}
	
	/**
	 * @return The size of the window.
	 */
	public static Vector2f getSize()
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		
		glfwGetWindowSize(WindowInternal.getNativeId(), width, height);
		return new Vector2f(width.get(), height.get());
	}
	
	/**
	 * @return The aspect ratio of the window.
	 */
	public static float getAspectRatio()
	{
		Vector2f size = getSize();
		return size.x / size.y;
	}
	
	/**
	 * @return The center of the window.
	 */
	public static Vector2f getCenter()
	{
		return getSize().mul(0.5f);
	}
}
