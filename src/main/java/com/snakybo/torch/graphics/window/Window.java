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
 * <p>
 * The Window allows for manipulation of the low-level GLFW window.
 * </p>
 *
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
	 * <p>
	 * Create a new window.
	 * </p>
	 *
	 * @param displayMode The window properties.
	 * @param windowMode The window mode.
	 */
	public static void create(DisplayMode displayMode, WindowMode windowMode)
	{
		WindowInternal.create(displayMode, windowMode);
	}
	
	/**
	 * <p>
	 * Bring the window to the front and give it input focus.
	 * </p>
	 *
	 * <p>
	 * If the window is currently iconfied, this will also call {@link #restore()}.
	 * </p>
	 */
	public static void focus()
	{
		restore();
		glfwFocusWindow(WindowInternal.getNativeId());
	}
	
	/**
	 * <p>
	 * Restore the window from an iconified position.
	 * </p>
	 */
	public static void restore()
	{
		glfwRestoreWindow(WindowInternal.getNativeId());
	}
	
	/**
	 * <p>
	 * Iconify the window.
	 * </p>
	 */
	public static void iconify()
	{
		glfwIconifyWindow(WindowInternal.getNativeId());
	}
	
	/**
	 * <p>
	 * Check whether or not vsync is enabled.
	 * </p>
	 *
	 * @return Whether or not vsync is enabled.
	 */
	public static boolean isVSyncEnabled()
	{
		return vsyncEnabled;
	}
	
	/**
	 * <p>
	 * Set the size of the window.
	 * </p>
	 *
	 * @param size The new size.
	 */
	public static void setSize(Vector2f size)
	{
		glfwSetWindowSize(WindowInternal.getNativeId(), (int)size.x, (int)size.y);
	}
	
	/**
	 * <p>
	 * Enable or disable vsync.
	 * </p>
	 *
	 * @param enabled Whether or not to enable vsync.
	 */
	public static void setVSyncEnabled(boolean enabled)
	{
		vsyncEnabled = enabled;
		
		glfwSwapInterval(enabled ? 1 : 0);
	}
	
	/**
	 * <p>
	 * Get the size in pixels.
	 * </p>
	 *
	 * @return The size in pixels.
	 */
	public static Vector2f getSize()
	{
		IntBuffer width = BufferUtils.createIntBuffer(1);
		IntBuffer height = BufferUtils.createIntBuffer(1);
		
		glfwGetWindowSize(WindowInternal.getNativeId(), width, height);
		return new Vector2f(width.get(), height.get());
	}
	
	/**
	 * <p>
	 * Get the aspect ratio.
	 * </p>
	 *
	 * @return The aspect ratio.
	 */
	public static float getAspectRatio()
	{
		Vector2f size = getSize();
		return size.x / size.y;
	}
	
	/**
	 * <p>
	 * Get the center coordinates.
	 * </p>
	 *
	 * @return The coordinates of the pixel in the center.
	 */
	public static Vector2f getCenter()
	{
		return getSize().mul(0.5f);
	}
}
